#ifndef INDEXES_BTREE_FIND_H
#define INDEXES_BTREE_FIND_H

#include "btree.h"
#include "../list/list.h"

struct list_node *btree_find_all(const struct btree *btree);
struct list_node *btree_find_by_key(const struct btree *btree, const record_key *key);
struct list_node *btree_find_by_key_starts_with(const struct btree *btree, const record_key *sub);
struct list_node *btree_find_by_key_contains(const struct btree *btree, const void *sub);

#endif //INDEXES_BTREE_FIND_H
