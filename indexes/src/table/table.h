#ifndef INDEXES_TABLE_H
#define INDEXES_TABLE_H

#include "../shared/record.h"
#include "../list/list.h"

struct table_search_ops {
    record_key_getter_f key_from_record;
    record_key_comparator_f comparator;
    record_key_print_f print_key;
    record_print_f print_record;

    record_key_starts_with_comparator_f starts_with;
    record_key_contains_f contains;
};

// list of records
struct table {
    struct list_node *content;
};

struct table *table_init(struct list_node *content);
void table_free(struct table *t, void (*f)(void *value));

#endif //INDEXES_TABLE_H
