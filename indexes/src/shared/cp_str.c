#include "cp_str.h"

#include <string.h>
#include <stdlib.h>

char *cp_str(const char *s) {
    char *res = calloc(strlen(s) + 1, sizeof(char));
    strcpy(res, s);
    return res;
}
