#ifndef INDEXES_MIXED_INIT_H
#define INDEXES_MIXED_INIT_H

#include "mixed.h"

struct mixed_index *mixed_init(
    record_print_f print_record,
    mixed_key_from_record_f key_from_record,
    struct table *table
);
void mixed_free(struct mixed_index *mix);

#endif //INDEXES_MIXED_INIT_H
