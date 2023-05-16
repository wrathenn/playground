#ifndef INDEXES_RECORD_KEY_STRING_H
#define INDEXES_RECORD_KEY_STRING_H

#include "record.h"

int string_key_starts_with(const record_key *this, const record_key *that);
bool string_key_contains(const record_key *this, const void *that);
int string_key_comparator(const record_key *left, const record_key *right);
void string_key_print(const record_key *key);

#endif //INDEXES_RECORD_KEY_STRING_H
