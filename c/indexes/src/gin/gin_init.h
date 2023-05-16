#ifndef INDEXES_GIN_INIT_H
#define INDEXES_GIN_INIT_H

#include "gin.h"

struct gin *gin_init(
    void (*gin_print_record)(const record *rec),
    gin_record_key *(*gin_key_from_record)(const record *rec),
    struct table *table
);
void gin_free(struct gin *gin);

#endif //INDEXES_GIN_INIT_H
