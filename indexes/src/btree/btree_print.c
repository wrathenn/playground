#include "btree_print.h"
#include "../list/list.h"

#include <string.h>
#include <stdio.h>
#include <stdlib.h>

#define PREFIX_SIZE 6
#define PREFIX_MID "├─"
#define PREFIX_LAST "└─"
#define PREFIX_FALL "│ \b "
#define PREFIX_EMPTY " \b  \b "

static void _btree_print(const struct btree *btree, const struct node *node, char *prefix);

/*
 * Создание нового префикса
 */
static char *_prefix_create(const char *old_prefix) {
    int add_size = PREFIX_SIZE * 2;
    const int next_prefix_len = strlen(old_prefix) + PREFIX_SIZE;
    // 1 под "\0"
    char *next_prefix = calloc(next_prefix_len + 1, sizeof(char));

    strcpy(next_prefix, old_prefix);
    // Заменить предыдущую часть префикса
    if (next_prefix_len >= add_size) {
        if (strncmp(next_prefix + next_prefix_len - add_size, PREFIX_LAST, PREFIX_SIZE) == 0) {
            strcpy(next_prefix + next_prefix_len - add_size,  PREFIX_EMPTY);
        } else {
            strcpy(next_prefix + next_prefix_len - add_size, PREFIX_FALL);
        }
    }
    // Вставить новую часть префикса
    strcpy(next_prefix + next_prefix_len - PREFIX_SIZE, PREFIX_MID);
    return next_prefix;
}

/*
 * Если элемент был последним, заменить начало префикса на пустое
 */
static void _prefix_cut_if_ended(char *prefix) {
    const int prefix_len = strlen(prefix);
    strcpy(prefix + prefix_len - PREFIX_SIZE, PREFIX_LAST);
}

/*
 * Вывести информацию об узле
 */
static void _btree_print_node(const struct btree *btree, const struct node *node, char *prefix) {
    if (!node->is_leaf) {
        for (int i = 0; i <= node->num_keys; ++i) {
            if (i == node->num_keys) {
                _prefix_cut_if_ended(prefix);
            }
            _btree_print(btree, node->pointers[i], prefix);
        }
    } else {
        for (int i = 0; i < node->num_keys; ++i) {
            if (i == node->num_keys - 1) {
                _prefix_cut_if_ended(prefix);
            }
            printf("%s", prefix);
            list_print(node->pointers[i], btree->print_record);
            printf("\n");
        }
    }
}

static void _btree_print_node_header(const struct btree *btree, const struct node *node) {
    node->is_leaf ?  printf("Лист(") : printf("Узел(");
    for (int i = 0; i < node->num_keys; ++i) {
        btree->print_key(node->keys[i]);
        if (i + 1 != node->num_keys) {
            printf(",");
        }
    }
    printf(")");
    printf("\n");
}

/*
 * Вывести все дерево
 */
static void _btree_print(const struct btree *btree, const struct node *node, char *prefix) {
    printf("%s", prefix);
    _btree_print_node_header(btree, node);

    char *next_prefix = _prefix_create(prefix);
    _btree_print_node(btree, node, next_prefix);

    free(next_prefix);
}

/*
 * Вывести дерево - внешняя функция
 */
void btree_print(struct btree *btree) {
    if (btree->root) {
        _btree_print(btree, btree->root, "");
    } else { printf("Индекс пуст\n"); }
}
