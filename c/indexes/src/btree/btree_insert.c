#include "btree_insert.h"
#include "btree_util.h"
#include "btree_find.h"
#include "../list/list.h"

#include <stdlib.h>
#include <stdio.h>

static struct btree *_insert_into_leaf_simple(struct btree *btree, struct node * leaf, record_key *key, record *record_p);
static struct btree *_insert_into_leaf_with_split(struct btree *btree, struct node *leaf, record_key *key, record *record_p);

static struct btree *_insert_into_node_simple(struct btree *btree, struct node *left, int left_index, record_key *key, struct node *new);
static struct btree *_insert_into_node_with_split(struct btree *btree, struct node * old_node, int left_index, record_key *key, struct node * right);

static struct btree *_insert_into_new_root(struct btree *btree, record_key *key, struct node * new);
static struct btree * _insert_into_parent(struct btree *btree, struct node *left, record_key *key, struct node *new);


// --------------------- LEAF ---------------------

static struct btree *_insert_into_leaf(
    struct btree *btree,
    struct node *leaf,
    record_key *key,
    record *record_p
) {
    if (leaf->num_keys < btree->order - 1) {
        return _insert_into_leaf_simple(btree, leaf, key, record_p);
    }
    return _insert_into_leaf_with_split(btree, leaf, key, record_p);
}

/*
 * Вставить указатель на новую запись в листовой элемент
 */
static struct btree *_insert_into_leaf_simple(
    struct btree *btree,
    struct node *leaf,
    record_key *key,
    record *record_p
) {
    int i, insertion_point;

    // insertion_point -- индекс, он должен быть <= leaf->num_keys
    // останавливаемся, если число в leaf->keys стало больше, чем вставляемое
    insertion_point = 0;
    while (insertion_point < leaf->num_keys && btree->comparator(leaf->keys[insertion_point], key) < 0) {
        insertion_point++;
    }

    // Сместить на 1 все числа после вставляемого
    for (i = leaf->num_keys; i > insertion_point; i--) {
        leaf->keys[i] = leaf->keys[i - 1];
        leaf->pointers[i] = leaf->pointers[i - 1];
    }
    leaf->keys[insertion_point] = key;
    leaf->pointers[insertion_point] = list_init(record_p);
    leaf->num_keys++;

    return btree;
}


/*
 * Вставить в дерево новый узел
 */
static struct btree *_insert_into_leaf_with_split(
    struct btree *btree,
    struct node *leaf,
    record_key *key,
    record *record_p
) {
    struct node *new_leaf;
    record_key *new_key;
    record_key **temp_keys;
    record **temp_pointers;
    int insertion_index, split, i, j;
    int order = btree->order;

    new_leaf = btree_util_make_leaf(order);

    temp_keys = calloc(order, sizeof(record_key *));
    if (temp_keys == NULL) {
        perror("Temporary keys array.");
        exit(EXIT_FAILURE);
    }

    temp_pointers = calloc(order, sizeof(void *));
    if (temp_pointers == NULL) {
        perror("Temporary pointers array.");
        exit(EXIT_FAILURE);
    }

    insertion_index = 0;
    while (insertion_index < order - 1 && btree->comparator(leaf->keys[insertion_index], key) < 0) {
        insertion_index++;
    }

    // Инициализируем новый массив ключей temp_keys
    //  В temp_keys записываем элементы из оригинального leaf->keys, со сдвигом после insertion_point
    for (i = 0, j = 0; i < leaf->num_keys; i++, j++) {
        if (j == insertion_index) j++;
        temp_keys[j] = leaf->keys[i];
        temp_pointers[j] = leaf->pointers[i];
    }
    temp_keys[insertion_index] = key;
    temp_pointers[insertion_index] = list_init(record_p);

    leaf->num_keys = 0;

    // split -- размер нового блока после разделения
    split = btree_util_cut(order - 1);
    // Левая часть -- в текущем leaf
    for (i = 0; i < split; i++) {
        leaf->pointers[i] = temp_pointers[i];
        leaf->keys[i] = temp_keys[i];
        leaf->num_keys++;
    }
    // Правая часть -- в новый leaf
    for (i = split, j = 0; i < order; i++, j++) {
        new_leaf->pointers[j] = temp_pointers[i];
        new_leaf->keys[j] = temp_keys[i];
        new_leaf->num_keys++;
    }

    free(temp_pointers);
    free(temp_keys);

    // Последний элемент в массиве pointers -- указатель на следующий лист
    new_leaf->pointers[order - 1] = leaf->pointers[order - 1];
    leaf->pointers[order - 1] = new_leaf;
    // Все ненужные в нуллы
    for (i = leaf->num_keys; i < order - 1; i++)
        leaf->pointers[i] = NULL;
    for (i = new_leaf->num_keys; i < order - 1; i++)
        new_leaf->pointers[i] = NULL;

    new_leaf->parent = leaf->parent;
    new_key = new_leaf->keys[0];

    return _insert_into_parent(btree, leaf, new_key, new_leaf);
}


// --------------------- NODE ---------------------

static struct btree *_insert_into_node(
    struct btree *btree,
    struct node *left,
    int left_index,
    record_key *key,
    struct node * new
) {
    if (left->num_keys < btree->order - 1) {
        return _insert_into_node_simple(btree, left, left_index, key, new);
    }
    return _insert_into_node_with_split(btree, left, left_index, key, new);
}

/*
 * Вставить новый узел правее от left_index в узле left
 */
static struct btree *_insert_into_node_simple(
    struct btree *btree,
    struct node *left,
    int left_index,
    record_key *key,
    struct node *new
) {
    int i;

    // Сдвинуть все что правее left_index
    for (i = left->num_keys; i > left_index; i--) {
        left->pointers[i + 1] = left->pointers[i];
        left->keys[i] = left->keys[i - 1];
    }
    left->pointers[left_index + 1] = new;
    left->keys[left_index] = key;
    left->num_keys++;

    return btree;
}


/*
 * Вставка нового узла с разделением текущего на 2
 */
static struct btree *_insert_into_node_with_split(
    struct btree *btree,
    struct node * old_node,
    int left_index,
    record_key *key,
    struct node * right
) {
    int i, j, split;
    struct node * new_node, * child;
    record_key **temp_keys;
    record_key *k_prime;
    struct node ** temp_pointers;

    int order = btree->order;

    temp_pointers = calloc((order + 1), sizeof(void *));
    if (temp_pointers == NULL) {
        perror("Temporary pointers array for splitting nodes.");
        exit(EXIT_FAILURE);
    }
    temp_keys = calloc(order, sizeof(record_key *));
    if (temp_keys == NULL) {
        perror("Temporary keys array for splitting nodes.");
        exit(EXIT_FAILURE);
    }

    for (i = 0, j = 0; i < old_node->num_keys + 1; i++, j++) {
        if (j == left_index + 1) j++;
        temp_pointers[j] = old_node->pointers[i];
    }

    for (i = 0, j = 0; i < old_node->num_keys; i++, j++) {
        if (j == left_index) j++;
        temp_keys[j] = old_node->keys[i];
    }

    temp_pointers[left_index + 1] = right;
    temp_keys[left_index] = key;

    split = btree_util_cut(order);
    new_node = btree_util_make_node(order);
    old_node->num_keys = 0;
    for (i = 0; i < split - 1; i++) {
        old_node->pointers[i] = temp_pointers[i];
        old_node->keys[i] = temp_keys[i];
        old_node->num_keys++;
    }
    old_node->pointers[i] = temp_pointers[i];
    k_prime = temp_keys[split - 1];
    for (++i, j = 0; i < order; i++, j++) {
        new_node->pointers[j] = temp_pointers[i];
        new_node->keys[j] = temp_keys[i];
        new_node->num_keys++;
    }
    new_node->pointers[j] = temp_pointers[i];
    free(temp_pointers);
    free(temp_keys);
    new_node->parent = old_node->parent;
    for (i = 0; i <= new_node->num_keys; i++) {
        child = new_node->pointers[i];
        child->parent = new_node;
    }

    /* Insert a new key into the parent of the two
     * nodes resulting from the split, with
     * the old node to the left and the new to the right.
     */

    return _insert_into_parent(btree, old_node, k_prime, new_node);
}


// --------------------- ROOT ---------------------

/* Creates a new root for two subtrees
 * and inserts the appropriate key into
 * the new root.
 */
static struct btree *_insert_into_new_root(
    struct btree *btree,
    record_key *key,
    struct node *new
) {
    struct node *left = btree->root;

    struct node * new_root = btree_util_make_node(btree->order);
    new_root->keys[0] = key;
    new_root->pointers[0] = left;
    new_root->pointers[1] = new;
    new_root->num_keys++;
    new_root->parent = NULL;

    left->parent = new_root;
    new->parent = new_root;

    btree->root = new_root;
    return btree;
}


// --------------------- GENERAL ---------------------

/*
 * Добавить ребенка к детям
 * Inserts a new node (leaf or internal node) into the B+ tree.
 * Returns the root of the tree after insertion.
 */
static struct btree *_insert_into_parent(
    struct btree *btree,
    struct node *left,
    record_key *key,
    struct node *new
) {
    int left_index;

    /* left является корнем дерева -- необходим новый корень и сплит этого элемента */
    if (btree->root == left) {
        return _insert_into_new_root(btree, key, new);
    }

    left_index = btree_util_get_left_index(left->parent, left);
    return _insert_into_node(btree, left->parent, left_index, key, new);
}

// --------------------- FINAL INSERT ---------------------

/*
 * Вставка записи в индекс
 */
struct btree *btree_insert(struct btree* btree, record *rec) {
    record_key *key = btree->key_from_record(rec);
    // Дерево еще не было создано
    if (btree->root == NULL) {
        return btree_util_start_new_tree(btree, key, rec);
    }

    struct list_node *found = btree_util_find_value_in_leaf_by_key(btree, key);
    if (found) {
        list_append(found, rec);
        return btree;
    }
    // Дерево создано
    // Лист, в который необходимо добавить индексную запись
    struct node *leaf = btree_util_find_leaf_by_key(btree, key);
    return _insert_into_leaf(btree, leaf, key, rec);
}
