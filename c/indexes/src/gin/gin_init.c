#include "gin_init.h"
#include "gin_insert.h"
#include "../shared/record_key_string.h"

#include <stdlib.h>
#include <string.h>

record_key *gin_record_key_getter(const record *rec) {
    const struct gin_record *gin_rec = (const struct gin_record*)rec;
    return gin_rec->key;
}

void gin_free_gin_record(record *rec) {
    struct gin_record *gin_rec = rec;
    free(gin_rec->key);
    free(gin_rec);
}

/*
 * Создание индекса для индексируемого поля - строки
 */
struct gin *gin_init(
    record_print_f print_record,
    gin_key_from_record_f gin_key_from_record,
    struct table *table
) {
    struct btree *btree = btree_init(
        gin_record_key_getter,
        string_key_comparator,
        string_key_print,
        print_record,
        NULL,
        NULL,
        NULL
    );

    struct gin *res = calloc(1, sizeof(struct gin));
    res->btree = btree;
    res->gin_key_from_record = gin_key_from_record;

    if (table) {
        struct list_node *records = table->content;
        while (records) {
            gin_insert(res, records->value);
            records = records->tail;
        }
    }

    return res;
}

void gin_free(struct gin *gin) {
    btree_free(gin->btree, gin_free_gin_record);
    free(gin);
}
