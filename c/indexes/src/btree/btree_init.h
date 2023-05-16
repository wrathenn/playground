#ifndef INDEXES_BTREE_INIT_H
#define INDEXES_BTREE_INIT_H

#include "btree.h"
#include "../table/table_package.h"

#include <stdlib.h>

struct btree *btree_init(
    record_key_getter_f key_from_record,
    record_key_comparator_f comparator,
    record_key_print_f print_key,
    record_print_f print_record,
    record_key_starts_with_comparator_f starts_with,
    record_key_contains_f contains,
    struct table *table
);
void btree_free(struct btree *btree, record_free f);

#endif //INDEXES_BTREE_INIT_H
