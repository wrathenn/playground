#ifndef INDEXES_TABLE_FIND_H
#define INDEXES_TABLE_FIND_H

#include "table.h"

struct list_node *table_find_all(struct table *t);
struct list_node *table_find_by_key(struct table *t, struct table_search_ops *sops, record_key *key);
struct list_node *table_find_by_key_starts_with(struct table *t, struct table_search_ops *sops, record_key *sub);
struct list_node *table_find_by_key_contains(struct table *t, struct table_search_ops *sops, void *sub);

#endif //INDEXES_TABLE_FIND_H
