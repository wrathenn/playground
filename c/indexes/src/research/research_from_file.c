#include "research_package.h"
#include "../shared/lexema.h"

#include <stdlib.h>
#include <string.h>
#include <stdio.h>

bool str_eq(const void *this, const void *that) {
    if (strcmp((char *)this, (char *)that) != 0) { return false; }
    return true;
}

struct list_node *get_all_lexemas(const struct list_node *content) {
    struct list_node *all_lexemas = NULL;
    struct list_node *cur_lexemas = NULL, *temp_cur_lexemas;
    const struct list_node *cur_rec = content;
    struct user_record *tr;
    char *cur_lexema;
    int inserted = 0;

    while (cur_rec && inserted < 1000) {
        tr = cur_rec->value;
        cur_lexemas = lexemas_from_str(tr->descr);
        temp_cur_lexemas = cur_lexemas;

        while (cur_lexemas && inserted < 1000) {
            cur_lexema = cur_lexemas->value;
            if (!list_exists(all_lexemas, cur_lexema, str_eq)) {
                all_lexemas = list_prepend(all_lexemas, cur_lexema);
                ++inserted;
            } else {
                free(cur_lexema);
            }
            cur_lexemas = cur_lexemas->tail;
        }
        list_free(temp_cur_lexemas, NULL);
        cur_rec = cur_rec->tail;
    }

    return all_lexemas;
}

struct list_node *get_all_keys(const struct list_node *content) {
    struct list_node *res = NULL;
    struct user_record *tr;
    const struct list_node *cur = content;

    int inserted = 0;
    while (cur && inserted < 1000) {
        tr = cur->value;
        res = list_prepend(res, tr->descr);
        cur = cur->tail;
        ++inserted;
    }
    return res;
}

const struct list_node *research_bulk_insert(
    const struct list_node *cur,
    long count,
    struct btree *btree,
    struct gin *gin,
    struct mixed_index *mix,
    long *counter
) {
    long i = 0;
    for(; i < count; ++i, ++(*counter)) {
        if (!cur) {
            break;
        }
        btree_insert(btree, cur->value);
        gin_insert(gin, cur->value);
        mixed_insert(mix, cur->value);
        cur = cur->tail;
    }
    return cur;
}

void do_research(char *filename, long step, long count, bool extra_info) {
    struct list_node *from_file = user_read_from_file(filename);
    struct list_node *all_lexemas = get_all_lexemas(from_file);
    struct list_node *all_keys = get_all_keys(from_file);

    struct table *table = user_record_table(NULL);
    struct btree *btree = user_record_descr_brtee(table);
    struct gin *gin = user_record_descr_gin(table);
    struct mixed_index *mix = user_record_descr_mixed(table);

    long current_size = 0;

    const struct list_node *cur_rec = from_file;
    while (cur_rec) {
        cur_rec = research_bulk_insert(cur_rec, step, btree, gin, mix, &current_size);

        if (extra_info) {
            printf("\nПредоставлено %ld записей.\n", current_size);
            printf("\nПоиск по совпадению:\n");
            printf("Индекс на основе B-дерева: %lf мс\n", btree_test_all_keys(btree, all_keys, count));
            printf("Комбинированный метод: %lf мс\n", mixed_test_all_keys(mix, all_keys, count));

            printf("\nПоиск по началу:\n");
            printf("Индекс на основе B-дерева: %lf мс\n", btree_test_all_beginnings(btree, all_keys, count));
            printf("Комбинированный метод: %lf мс\n", mixed_test_all_beginnings(mix, all_keys, count));

            printf("\nПоиск по вхождению слова:\n");
            printf("Инвертированный индекс: %lf мс\n", gin_test_all_contains(gin, all_lexemas, count));
            printf("Комбинированный метод: %lf мс\n", mixed_test_all_contains(mix, all_lexemas, count));
        } else {
            printf("{\n");
            printf("\"n\": \"%ld\",\n", current_size);
            printf("\"b_btree\": \"%lf\",\n", btree_test_all_keys(btree, all_keys, count));
            printf("\"b_mix\": \"%lf\",\n", mixed_test_all_keys(mix, all_keys, count));

            printf("\"s_btree\": \"%lf\",\n", btree_test_all_beginnings(btree, all_keys, count));
            printf("\"s_mix\": \"%lf\",\n", mixed_test_all_beginnings(mix, all_keys, count));

            printf("\"c_gin\": \"%lf\",\n", gin_test_all_contains(gin, all_lexemas, count));
            printf("\"c_mix\": \"%lf\",\n", mixed_test_all_contains(mix, all_lexemas, count));

            printf("}%s\n", cur_rec ? "," : "");
        }
    }

    list_free(from_file, user_record_free);
    btree_free(btree, NULL);
    gin_free(gin);
    table_free(table, NULL);
    mixed_free(mix);
    list_free(all_lexemas, free);
    list_free(all_keys, NULL);
}
