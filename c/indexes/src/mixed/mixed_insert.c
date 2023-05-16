#include "mixed_insert.h"

#include "mixed_util.h"
#include "../shared/lexema.h"

int mixed_insert(struct mixed_index *mix, record *rec) {
    mixed_record_key *key = mix->mixed_key_from_record(rec);
    struct list_node *lexemas = lexemas_from_str(key), *next_lexema_temp;

    struct mixed_record *mr = NULL;

    mr = mixed_init_record(rec, key, true);
    btree_insert(mix->btree, mr);

    while (lexemas) {
        mr = mixed_init_record(rec, lexemas->value, false);
        if (!mr) { continue; }
        btree_insert(mix->btree, mr);
        next_lexema_temp = lexemas->tail;
        free(lexemas);
        lexemas = next_lexema_temp;
    }

    return 0;
}
