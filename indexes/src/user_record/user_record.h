#ifndef INDEXES_USER_RECORD_H
#define INDEXES_USER_RECORD_H

#include "../btree/btree_package.h"
#include "../gin/gin_package.h"
#include "../mixed/mixed_package.h"

struct user_record {
    int id;
    char *name;
    char *descr;
};

struct user_record *user_record_init(int id, char *name, char *descr);
struct user_record *user_record_init_cp(int id, char *name, char *descr);
void user_record_free(record *ur);
void user_record_print(const record *rec);
void user_record_print_gin(const record *rec);
void user_record_print_mixed(const record *rec);
bool user_record_eq(const record *this, const record *that);

// Keys getters
record_key *user_record_get_name(const record *record);
record_key *user_record_get_descr(const record *record);

#endif //INDEXES_USER_RECORD_H
