#include "btree_util.h"
#include "../list/list.h"

#include "../user_record/user_record.h"

#include <stdio.h>
#include <stdlib.h>

struct node *btree_util_make_node(const int order) {
    struct node *new_node = calloc(1, sizeof(struct node));
    if (new_node == NULL) {
        printf("Ошибка: создание узла Б-дерева\n");
        exit(EXIT_FAILURE);
    }
    new_node->keys = calloc((order - 1), sizeof(record_key *));
    if (new_node->keys == NULL) {
        printf("Ошибка: создание массива ключей узла Б-дерева\n");
        exit(EXIT_FAILURE);
    }
    new_node->pointers = calloc(order, sizeof(void *));
    if (new_node->pointers == NULL) {
        printf("Ошибка: создание массива указателей узла Б-дерева");
        exit(EXIT_FAILURE);
    }
    new_node->is_leaf = false;
    new_node->num_keys = 0;
    new_node->parent = NULL;
    return new_node;
}

struct node *btree_util_make_leaf(const int order) {
    struct node * leaf = btree_util_make_node(order);
    leaf->is_leaf = true;
    return leaf;
}

/*
 * Найти элемент, который был левее, чем right в parent->pointers
 */
int btree_util_get_left_index(const struct node *parent, const struct node *right) {
    int left_index = 0;
    while (left_index <= parent->num_keys && parent->pointers[left_index] != right) {
        left_index++;
    }
    return left_index;
}


/*
 * При разделении узла на 2, необходимо найти новый размер узла
 * Возвращает размер одной из половин
 */
int btree_util_cut(int length) {
    return length % 2 == 0 ? length / 2 : length / 2 + 1;
}

/*
 * Создание нового дерева
 */
struct btree *btree_util_start_new_tree(struct btree *btree, record_key* key, record *pointer) {
    struct node *root = btree_util_make_leaf(btree->order);
    root->keys[0] = key;
    root->pointers[0] = list_init(pointer);
    root->pointers[btree->order - 1] = NULL;
    root->parent = NULL;
    root->num_keys++;

    btree->root = root;
    return btree;
}

/*
 * Найти лист, в котором должна содержаться запись с ключом key
 */
struct node *btree_util_find_leaf_by_key(const struct btree * btree, const record_key *key) {
    if (!btree || !btree->root) { return NULL; }
    int i;
    struct node *cur = btree->root;

    while (!cur->is_leaf) {
        i = 0;
        while (i < cur->num_keys) {
            if (btree->comparator(key, cur->keys[i]) >= 0) i++;
            else break;
        }
        cur = (struct node *)cur->pointers[i];
    }
    return cur;
}

/*
 * Найти индекс указателя на запись с ключом key в листе leaf
 */
int btree_util_find_index_in_leaf_by_key(const struct btree *btree, const struct node *leaf, const record_key *key) {
    int i;
    for (i = 0; i < leaf->num_keys; ++i) {
        if (btree->comparator(leaf->keys[i], key) == 0) return i;
    }
    return -1;
}

/*
 * Найти указатель на множество указателей на записей отношения в индексе по ключу key
 */
struct list_node *btree_util_find_value_in_leaf_by_key(const struct btree *btree, const record_key *key) {
    if (btree->root == NULL) { return NULL; }

    struct node *leaf = btree_util_find_leaf_by_key(btree, key);
    int i = btree_util_find_index_in_leaf_by_key(btree, leaf, key);

    if (i == -1) { return NULL; }
    return (struct list_node *)leaf->pointers[i];
}

/*
 * Найти индекс элемента в листе leaf, который начинается на key
 */
int btree_util_find_index_in_leaf_starts_with(
    const struct btree *btree,
    const struct node *leaf,
    const record_key *key
) {
    if (leaf == NULL) { return -1; }
    int i;
    for (i = 0; i < leaf->num_keys; ++i) {
        if (btree->starts_with(leaf->keys[i], key) == 0) {
            return i;
        }
    }
    return -1;
}

/*
 * Для поиска по началу необходимо дополнительно проверить, что запись может начинаться на искомую подстроку
 * Всегда верно, что если this начинается на that, то this >= that
 */
struct node *btree_util_find_leaf_starts_with(const struct btree *btree, const record_key *key) {
    int i;
    struct node *cur = btree->root;

    while (!cur->is_leaf) {
        i = 0;
        while (i < cur->num_keys) {
            if (btree->starts_with(cur->keys[i], key) < 0) {
                ++i;
            } else { break; }
        }
        cur = (struct node *)cur->pointers[i];
    }
    return cur;
}
