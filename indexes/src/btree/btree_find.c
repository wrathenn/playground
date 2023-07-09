#include "btree_util.h"
#include "../list/list.h"

#include <stdio.h>

#define BTREE_CHECK_NOT_NULL(btree) if (!btree || !btree->root) { return NULL; }

struct list_node *btree_find_by_key(const struct btree *btree, const record_key *key) {
    BTREE_CHECK_NOT_NULL(btree)

    struct node *leaf = btree_util_find_leaf_by_key(btree, key);
    int i = btree_util_find_index_in_leaf_by_key(btree, leaf, key);
    if (i == -1) { return NULL; }

    return list_copy(leaf->pointers[i]);
}

struct list_node *btree_find_by_key_starts_with(const struct btree *btree, const record_key *sub) {
    BTREE_CHECK_NOT_NULL(btree)
    struct list_node *res = NULL;

    struct node *cur_leaf = btree_util_find_leaf_starts_with(btree, sub);
    int i = btree_util_find_index_in_leaf_starts_with(btree, cur_leaf, sub);
    if (i == -1 && cur_leaf) {
        cur_leaf = cur_leaf->pointers[btree->order - 1];
        i = btree_util_find_index_in_leaf_starts_with(btree, cur_leaf, sub);
    }
    if (i == -1) {
        return NULL;
    }

    struct list_node *cur_recs = NULL;
    bool starts_with = btree->starts_with(cur_leaf->keys[i], sub) == 0;
    while (starts_with) {
        cur_recs = cur_leaf->pointers[i];
        while (cur_recs) {
            res = list_prepend(res, cur_recs->value);
            cur_recs = cur_recs->tail;
        }
        ++i;
        if (i == cur_leaf->num_keys) { cur_leaf = cur_leaf->pointers[btree->order - 1]; i = 0; }
        if (cur_leaf == NULL) { break; }
        starts_with = btree->starts_with(cur_leaf->keys[i], sub) == 0;
    }

    return res;
}

struct list_node *btree_find_all(const struct btree *btree) {
    BTREE_CHECK_NOT_NULL(btree)

    struct list_node *res = NULL;
    struct node *cur_node = btree->root;
    while(!cur_node->is_leaf) {
        cur_node = cur_node->pointers[0];
    }

    struct list_node *cur_node_p;
    while(cur_node) {
        for (int i = 0; i < cur_node->num_keys; ++i) {
            cur_node_p = cur_node->pointers[i];
            while (cur_node_p) {
                res = list_prepend(res, cur_node_p->value);
                cur_node_p = cur_node_p->tail;
            }
        }
        cur_node = cur_node->pointers[btree->order - 1];
    }

    return res;
}

struct list_node *btree_find_by_key_contains(const struct btree *btree, const void *sub) {
    BTREE_CHECK_NOT_NULL(btree)
    struct list_node *res = NULL;

    struct node *cur_node = btree->root;
    while(!cur_node->is_leaf) {
        cur_node = cur_node->pointers[0];
    }

    struct list_node *cur_node_p;
    while(cur_node) {
        for (int i = 0; i < cur_node->num_keys; ++i) {
            cur_node_p = cur_node->pointers[i];
            while (cur_node_p) {
                if (btree->contains(
                    btree->key_from_record(cur_node_p->value),
                    sub
                )) {
                    res = list_prepend(res, cur_node_p->value);
                }
                cur_node_p = cur_node_p->tail;
            }
        }
        cur_node = cur_node->pointers[btree->order - 1];
    }

    return res;
}
