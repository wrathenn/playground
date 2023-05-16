#include "mixed_init.h"
#include "mixed_insert.h"
#include "../shared/record_key_string.h"

record_key *mixed_record_key_getter(const record *rec) {
    const struct mixed_record *mr = rec;
    return mr->key;
}

void mixed_free_mixed_record(record *rec) {
    struct mixed_record *mr = rec;
    if (!mr->is_original) {
        free(mr->key);
    }
    free(mr);
}

struct mixed_index *mixed_init(
    record_print_f print_record,
    mixed_key_from_record_f key_from_record,
    struct table *table
) {
    struct btree *btree = btree_init(
        mixed_record_key_getter,
        string_key_comparator,
        string_key_print,
        print_record,
        string_key_starts_with,
        string_key_contains,
        NULL
    );

    struct mixed_index *res = calloc(1, sizeof(struct mixed_index));
    res->btree = btree;
    res->mixed_key_from_record = key_from_record;

    if (table) {
        struct list_node *records = table->content;
        while (records) {
            mixed_insert(res, records->value);
            records = records->tail;
        }
    }

    return res;
}

void mixed_free(struct mixed_index *mix) {
    btree_free(mix->btree, mixed_free_mixed_record);
    free(mix);
}
