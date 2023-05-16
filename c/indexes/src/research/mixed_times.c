#include "research_package.h"
#include "../shared/cp_str.h"

#include <string.h>
#include <stdlib.h>
#include <time.h>


double mixed_time_find_by_key(struct mixed_index *mix, record *key, long count) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = mixed_find_by_key(mix, key);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}

double mixed_time_find_by_key_begins(struct mixed_index *mix, record *key, long count) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = mixed_find_by_key_starts_with(mix, key);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}

double mixed_time_find_all(struct mixed_index *mix, long count) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = mixed_find_all(mix);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}

double mixed_time_find_by_key_contains(struct mixed_index *mix, record *key, long count) {
    clock_t start_t, end_t;
    struct list_node *temp_res;
    double res = 0;

    for (int i = 0; i < count; ++i) {
        start_t = clock();
        temp_res = mixed_find_by_key_contains(mix, key);
        end_t = clock();
        res += (end_t - start_t);
        list_free(temp_res, NULL);
    }
    return res / count;
}


double mixed_test_all_keys(struct mixed_index *mix, struct list_node *keys, long count) {
    double res = 0;
    int keys_cnt = list_size(keys);

    struct list_node *cur_key = keys;
    while (cur_key) {
        res += mixed_time_find_by_key(mix, cur_key->value, count);
        cur_key = cur_key->tail;
    }

    return res / keys_cnt;
}

double mixed_test_all_beginnings(struct mixed_index *mix, struct list_node *keys, long count) {
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

        res += mixed_time_find_by_key(mix, cur_key->value, count);

        cur_key = cur_key->tail;
        free(truncated_key);
    }

    return res / keys_cnt;
}

double mixed_test_all_contains(struct mixed_index *mix, struct list_node *keys, long count) {
    double res = 0;
    int keys_cnt = list_size(keys);

    struct list_node *cur_key = keys;
    while (cur_key) {
        res += mixed_time_find_by_key_contains(mix, cur_key->value, count);
        cur_key = cur_key->tail;
    }

    return res / keys_cnt;
}
