#include "mixed_print.h"

#include <stdio.h>

void mixed_print(const struct mixed_index *mix) {
    if (mix->btree) {
        btree_print(mix->btree);
    } else { printf("Индекс пуст\n"); }
}
