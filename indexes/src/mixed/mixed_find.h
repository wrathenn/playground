#ifndef INDEXES_MIXED_FIND_H
#define INDEXES_MIXED_FIND_H

#include "mixed.h"

struct list_node *mixed_find_all(const struct mixed_index *gin);
struct list_node *mixed_find_by_key(const struct mixed_index *gin, const record_key *key);
struct list_node *mixed_find_by_key_starts_with(const struct mixed_index *gin, const record_key *sub);
struct list_node *mixed_find_by_key_contains(const struct mixed_index *gin, const mixed_record_key *sub);

#endif //INDEXES_MIXED_FIND_H
