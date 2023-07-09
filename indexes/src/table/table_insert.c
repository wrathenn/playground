#include "table_insert.h"

struct table *table_insert(struct table *t, record *rec) {
    t->content = list_prepend(t->content, rec);
    return t;
}
