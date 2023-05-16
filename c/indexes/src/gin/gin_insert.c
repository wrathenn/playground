#include "gin_insert.h"

#include "../shared/lexema.h"
#include "gin_util.h"

#include <stdlib.h>

int gin_insert(struct gin *gin, record *rec) {
    gin_record_key *key = gin->gin_key_from_record(rec);
    struct list_node *lexemas = lexemas_from_str(key), *next_lexema_temp;
    struct gin_record *gr = NULL;

    while (lexemas) {
        gr = gin_init_record(rec, lexemas->value);
        if (!gr) { continue; }
        gin->btree = btree_insert(gin->btree, gr);
        next_lexema_temp = lexemas->tail;
        free(lexemas);
        lexemas = next_lexema_temp;
    }

    return 0;
}

