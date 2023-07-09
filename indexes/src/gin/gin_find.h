#ifndef INDEXES_GIN_FIND_H
#define INDEXES_GIN_FIND_H

#include "gin.h"

struct list_node *gin_find_all(struct gin *gin);
struct list_node *gin_find_by_key(struct gin *gin, record_key *key);
struct list_node *gin_find_by_key_starts_with(struct gin *gin, record_key *sub);
struct list_node *gin_find_by_key_contains(struct gin *gin, gin_record_key *sub);

#endif //INDEXES_GIN_FIND_H
