#include "gin_util.h"

#include <stdlib.h>
#include <stdio.h>

/*
 * Создание индексной записи для GIN индекса
 */
struct gin_record *gin_init_record(record *data, gin_record_key *key) {
    struct gin_record *gr = calloc(1, sizeof(struct gin_record));
    if (!gr) { return NULL; }
    gr->data = data;
    gr->key = key;
    return gr;
}
