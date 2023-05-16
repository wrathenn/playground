#include "record_key_string.h"

#include "../shared/lexema.h"

#include <string.h>
#include <stdio.h>

int string_key_comparator(const record_key *left, const record_key *right) {
    char *l, *r;
    l = (char *)left;
    r = (char *)right;
    int res = strcmp(l, r);
    if (res < 0) return -1;
    if (res > 0) return 1;
    return 0;
}

int string_key_starts_with(const record_key *this, const record_key *that) {
    char *_this, *_that;
    _this = (char *)this;
    _that = (char *)that;

    size_t this_len = strlen(_this);
    size_t that_len = strlen(_that);

    if (this_len < that_len) { return -1; }

    int res = strncmp(_this, _that, that_len);
    if (res < 0) return -1;
    if (res > 0) return 1;
    return 0;
}

bool string_key_contains(const record_key *this, const void *that) {
    return lexema_contains(this, that);
}

void string_key_print(const record_key *key) {
    const char *k = key;
    printf("%s", k);
}
