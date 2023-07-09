#include "research_package.h"
#include "../shared/cp_str.h"

#include <string.h>
#include <stdlib.h>
#include <time.h>

double table_time_find_by_key(struct table *table, record_key *key, long count, struct table_search_ops *tsops) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = table_find_by_key(table, tsops, key);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}

double table_time_find_by_key_begins(struct table *table, record_key *key, long count, struct table_search_ops *tsops) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = table_find_by_key_starts_with(table, key, tsops);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}

double table_time_find_all(struct table *table, long count) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = table_find_all(table);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}

double table_time_find_by_key_contains(struct table *table, record_key *key, long count, struct table_search_ops *tsops) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = table_find_by_key_contains(table, tsops, key);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}


double table_test_all_keys(struct table *table, struct list_node *keys, long count, struct table_search_ops *tsops) {
    double res = 0;
    int keys_cnt = list_size(keys);

    struct list_node *cur_key = keys;
    while (cur_key) {
        res += table_time_find_by_key(table, cur_key->value, count, tsops);
        cur_key = cur_key->tail;
    }

    return res / keys_cnt;
}

double table_test_all_beginnings(struct table *table, struct list_node *keys, long count, struct table_search_ops *tsops) {
    double res = 0;
    int keys_cnt = list_size(keys);

    char *key = NULL, *truncated_key = NULL;
    int trunc_pos;
    struct list_node *cur_key = keys;
    while (cur_key) {
        key = cur_key->value;
        truncated_key = cp_str(key);
        trunc_pos = strlen(key) / 2 + 1;
        truncated_key[trunc_pos] = '\0';

        res += table_time_find_by_key(table, cur_key->value, count, tsops);

        cur_key = cur_key->tail;
        free(truncated_key);
    }

    return res / keys_cnt;
}

double table_test_all_contains(struct table *table, struct list_node *keys, long count, struct table_search_ops *tsops) {
    double res = 0;
    int keys_cnt = list_size(keys);

    struct list_node *cur_key = keys;
    while (cur_key) {
        res += table_time_find_by_key_contains(table, cur_key->value, count, tsops);
        cur_key = cur_key->tail;
    }

    return res / keys_cnt;
}
