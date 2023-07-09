#include "mixed_find.h"

#include "../shared/lexema.h"
#include "../btree/btree_util.h"

#include <stdio.h>
#include <string.h>

#define MIXED_CHECK_NOT_NULL(mix) if (!mix || !mix->btree || !mix->btree->root) { return NULL; }

/*
 * Возвращает list<record*>
 */
struct list_node *mixed_find_by_key_contains(const struct mixed_index *mix, const mixed_record_key *sub) {
    MIXED_CHECK_NOT_NULL(mix)
    if (!lexema_validate(sub)) {
        printf("Ошибка: Строка \"%s\" не является одним словом\n", sub);
        return NULL;
    }

    struct node *leaf = btree_util_find_leaf_by_key(mix->btree, sub);
    if (leaf == NULL) { return NULL; }

    int i = btree_util_find_index_in_leaf_by_key(mix->btree, leaf, sub);
    if (i == -1) { return NULL; }

    struct list_node *res = NULL;
    struct list_node *cur_records = NULL;
    struct mixed_record *mr = NULL;
    bool contains = mix->btree->comparator(leaf->keys[i], sub) == 0;

    while (contains) {
        cur_records = leaf->pointers[i];
        while (cur_records) {
            mr = cur_records->value;
            res = list_prepend(res, mr->data);
            cur_records = cur_records->tail;
        }
        ++i;
        if (i == leaf->num_keys) { leaf = leaf->pointers[mix->btree->order - 1]; i = 0; }
        if (leaf == NULL) { break; }
        contains = mix->btree->comparator(leaf->keys[i], sub) == 0;
    }

    return res;
}

struct list_node *mixed_find_all(const struct mixed_index *mix) {
    MIXED_CHECK_NOT_NULL(mix)

    struct node *leaf = mix->btree->root;
    if (leaf == NULL) { return NULL; }
    while (leaf && !leaf->is_leaf) {
        leaf = leaf->pointers[0];
    }
    if (leaf == NULL) { return NULL; }

    struct list_node *res = NULL;
    struct list_node *cur_records = NULL;
    struct mixed_record *mr = NULL;
    while (leaf) {
        for (int i = 0; i < leaf->num_keys; i++) {
            cur_records = leaf->pointers[i];
            while (cur_records) {
                mr = cur_records->value;
                if (mr->is_original) {
                    res = list_prepend(res, mr->data);
                }
                cur_records = cur_records->tail;
            }
        }
        leaf = leaf->pointers[mix->btree->order - 1];
    }
    return res;
}

struct list_node *mixed_find_by_key(const struct mixed_index *mix, const record_key *key) {
    MIXED_CHECK_NOT_NULL(mix)
    struct node *leaf = btree_util_find_leaf_by_key(mix->btree, key);
    int i = btree_util_find_index_in_leaf_by_key(mix->btree, leaf, key);
    if (i == -1) { return NULL; }

    struct list_node *res = NULL, *cur = leaf->pointers[i];
    struct mixed_record *mr;
    while (cur) {
        mr = cur->value;
        if (mr->is_original) {
            res = list_prepend(res, mr->data);
        }
        cur = cur->tail;
    }
    return res;
}

struct list_node *mixed_find_by_key_starts_with(const struct mixed_index *mix, const record_key *sub) {
    MIXED_CHECK_NOT_NULL(mix)
    struct list_node *res = NULL;

    struct node *cur_leaf = btree_util_find_leaf_starts_with(mix->btree, sub);
    int i = btree_util_find_index_in_leaf_starts_with(mix->btree, cur_leaf, sub);
    if (i == -1 && cur_leaf) {
        cur_leaf = cur_leaf->pointers[mix->btree->order - 1];
        i = btree_util_find_index_in_leaf_starts_with(mix->btree, cur_leaf, sub);
    }
    if (i == -1) {
        return NULL;
    }

    struct list_node *cur_records = NULL;
    struct mixed_record *mr;
    bool starts_with = mix->btree->starts_with(cur_leaf->keys[i], sub) == 0;
    while (starts_with) {
        cur_records = cur_leaf->pointers[i];
        while (cur_records) {
            mr = cur_records->value;
            if (mr->is_original) {
                res = list_prepend(res, mr->data);
            }
            cur_records = cur_records->tail;
        }
        ++i;
        if (i == cur_leaf->num_keys) { cur_leaf = cur_leaf->pointers[mix->btree->order - 1]; i = 0; }
        if (cur_leaf == NULL) { break; }
        starts_with = mix->btree->starts_with(cur_leaf->keys[i], sub) == 0;
    }

    return res;
}

