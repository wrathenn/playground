#include "btree_init.h"

#include "btree_insert.h"

struct btree *btree_init(
    record_key_getter_f key_from_record,
    record_key_comparator_f comparator,
    record_key_print_f print_key,
    record_print_f print_record,
    record_key_starts_with_comparator_f starts_with,
    record_key_contains_f contains,
    struct table *table
) {
    struct btree *btree = calloc(1, sizeof(struct btree));
    if (!btree) { return NULL; }
    btree->root = NULL;
    btree->order = DEFAULT_ORDER;
    btree->key_from_record = key_from_record;
    btree->comparator = comparator;
    btree->print_key = print_key;
    btree->print_record = print_record;
    btree->starts_with = starts_with;
    btree->contains = contains;

    if (table) {
        struct list_node *records = table->content;
        while (records) {
            btree_insert(btree, records->value);
            records = records->tail;
        }
    }

    return btree;
}

/*
 * Рекурсивная очистка выделенной памяти под списки в указателях листа
 */
static void btree_free_leaf_lists(struct node *leaf, record_free f) {
    struct list_node *l_temp, *l_cur;
    for (int i = 0; i < leaf->num_keys; ++i) {
        l_cur = leaf->pointers[i];
        while (l_cur) {
            l_temp = l_cur->tail;
            if (f) { f(l_cur->value); }
            free(l_cur);
            l_cur = l_temp;
        }
    }
}

/*
 * Рекурсивная очистка выделенной памяти
 */
static void btree_free_nodes(struct node *cur, record_free f) {
    if (cur->is_leaf) {
        btree_free_leaf_lists(cur, f);
    } else {
        for (int i = 0; i <= cur->num_keys; ++i) {
            btree_free_nodes(cur->pointers[i], f);
        }
    }
    free(cur->pointers);
    free(cur->keys);
    free(cur);
}

/*
 * Очистить память, выделенную под дерево.
 * ! Не очистит память под записи.
 */
void btree_free(struct btree *btree, record_free f) {
    if (btree->root != NULL) {
        btree_free_nodes(btree->root, f);
    }
    free(btree);
}

