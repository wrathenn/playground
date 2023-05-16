#include "table_print.h"

#include <stdio.h>

void table_print(const struct table *t, struct table_search_ops *tsops) {
    int i = 1;
    const struct list_node *cur = t->content;
    if (list_size(cur) == 0) { printf("Таблица пуста"); }
    while (cur) {
        printf("Запись №%d: ", i);
        tsops->print_record(cur->value);
        printf("\n");
        ++i;
        cur = cur->tail;
    }
    printf("\n");
}
