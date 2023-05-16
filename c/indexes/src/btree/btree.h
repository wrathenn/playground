#ifndef INDEXES_BTREE_H
#define INDEXES_BTREE_H

#include "../shared/record.h"

#include <stdbool.h>

struct node {
    // Массив указателей на данные:
    // Лист -> указатель на список с записями
    // Внутренний узел -> указатель на ребенка,
    // все значения в котором меньше, чем ключ
    void **pointers;
    // Массив, элементы которого отражают значения элементов потомка (они меньше)
    record_key **keys;
    // Родитель
    struct node *parent;
    // Является ли листом
    bool is_leaf;
    // Количество элементов в pointers и keys
    int num_keys;
};

// Order -- количество элементов, которые может содержать в себе один узел B+TREE
#define DEFAULT_ORDER 6

struct btree {
    struct node *root;
    int order;

    record_key_getter_f key_from_record;
    record_key_comparator_f comparator;
    record_key_print_f print_key;
    record_print_f print_record;

    record_key_starts_with_comparator_f starts_with;
    record_key_contains_f contains;
};

#endif //INDEXES_BTREE_H
