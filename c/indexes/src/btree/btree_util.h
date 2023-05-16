#ifndef INDEXES_BTREE_UTIL_H
#define INDEXES_BTREE_UTIL_H

#include "btree.h"

struct node *btree_util_make_node(int order);
struct node *btree_util_make_leaf(int order);
int btree_util_get_left_index(const struct node *parent, const struct node *right);
int btree_util_cut(int length);
struct btree *btree_util_start_new_tree(struct btree *btree, record_key* key, record *pointer);

struct node *btree_util_find_leaf_by_key(const struct btree * btree, const record_key *key);
int btree_util_find_index_in_leaf_by_key(const struct btree *btree, const struct node *leaf, const record_key *key);
struct list_node *btree_util_find_value_in_leaf_by_key(const struct btree *btree, const record_key *key);
int btree_util_find_index_in_leaf_starts_with(const struct btree *btree, const struct node *leaf, const record_key *key);
struct node *btree_util_find_leaf_starts_with(const struct btree *btree, const record_key *key);

#endif //INDEXES_BTREE_UTIL_H
