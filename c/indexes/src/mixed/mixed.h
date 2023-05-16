#ifndef INDEXES_MIXED_H
#define INDEXES_MIXED_H

#include "../btree/btree_package.h"

typedef char mixed_record_key;

struct mixed_record {
    record *data;
    mixed_record_key *key;
    bool is_original;
};

typedef mixed_record_key *(*mixed_key_from_record_f)(const record *rec);

struct mixed_index {
    struct btree *btree;
    mixed_key_from_record_f mixed_key_from_record;
};

#endif //INDEXES_MIXED_H
