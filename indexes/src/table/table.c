#include "table.h"

#include <stdlib.h>

struct table *table_init(struct list_node *content) {
    struct table *rec = calloc(1, sizeof(struct table));
    rec->content = content;
    return rec;
}

void table_free(struct table *t, void (*f)(void *value)) {
    list_free(t->content, f);
    free(t);
}
