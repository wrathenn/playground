#include <stdio.h>
#include "gin_print.h"

void gin_print(struct gin *gin) {
    if (gin->btree) {
        btree_print(gin->btree);
    } else { printf("Индекс пуст\n"); }
}
