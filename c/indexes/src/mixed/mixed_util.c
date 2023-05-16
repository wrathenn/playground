#include "mixed_util.h"

struct mixed_record *mixed_init_record(record *data, mixed_record_key *key, bool is_original) {
    struct mixed_record *mr = calloc(1, sizeof(struct mixed_record));
    if (!mr) { return NULL; }
    mr->data = data;
    mr->key = key;
    mr->is_original = is_original;
    return mr;
}
