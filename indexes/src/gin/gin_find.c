#include "gin_find.h"
#include "../shared/lexema.h"

#include "../btree/btree_util.h"

#include <stdio.h>
#include <string.h>

#define GIN_CHECK_NOT_NULL(gin) if (!gin || !gin->btree) { return NULL; }

bool _gin_contains(const record_key *key, const gin_record_key *sub) {
    return strcmp(key, sub) == 0
    ? true
    : false;
}

int _gin_find_in_leaf(const struct node *leaf, const gin_record_key *sub) {
    for (int i = 0; i < leaf->num_keys; ++i) {
        if (strcmp(leaf->keys[i], sub) == 0) {
            return i;
        }
    }
    return -1;
}

/*
 * Возвращает list<user_record*>
 */
struct list_node *gin_find_by_key_contains(struct gin *gin, gin_record_key *sub) {
    GIN_CHECK_NOT_NULL(gin)
    if (!lexema_validate(sub)) {
        printf("Ошибка: Строка %s не является отдельным словом\n", sub);
        return NULL;
    }
    if (gin == NULL || gin->btree == NULL) { return NULL; }

    struct node *leaf = btree_util_find_leaf_by_key(gin->btree, sub);
    if (leaf == NULL) { return NULL; }

    int i = _gin_find_in_leaf(leaf, sub);
    if (i == -1) { return NULL; }

    struct list_node *res = NULL;
    struct list_node *cur_leaf_list = NULL;
    struct gin_record *gin_rec = NULL;
    bool contains = _gin_contains(leaf->keys[i], sub);

    while (contains) {
        cur_leaf_list = leaf->pointers[i];
        while (cur_leaf_list) {
            gin_rec = cur_leaf_list->value;
            res = list_prepend(res, gin_rec->data);
            cur_leaf_list = cur_leaf_list->tail;
        }
        ++i;
        if (i == leaf->num_keys) { leaf = leaf->pointers[gin->btree->order - 1]; i = 0; }
        if (leaf == NULL) { break; }
        contains = _gin_contains(leaf->keys[i], sub);
    }

    return res;
}

struct list_node *gin_find_all(struct gin *gin) {
    GIN_CHECK_NOT_NULL(gin)

    struct node *leaf = gin->btree->root;
    if (leaf == NULL) { return NULL; }
    while (leaf && !leaf->is_leaf) {
        leaf = leaf->pointers[0];
    }
    if (leaf == NULL) { return NULL; }

    struct list_node *res = NULL;
    struct list_node *cur_leaf_list = NULL;
    struct gin_record *gin_rec = NULL;
    while (leaf) {
        for (int i = 0; i < leaf->num_keys; i++) {
            cur_leaf_list = leaf->pointers[i];
            while (cur_leaf_list) {
                gin_rec = cur_leaf_list->value;
                res = list_prepend(res, gin_rec->data);
                cur_leaf_list = cur_leaf_list->tail;
            }
        }
        leaf = leaf->pointers[gin->btree->order - 1];
    }
    return res;
}

struct list_node *gin_find_by_key(struct gin *gin, record_key *key) {
    GIN_CHECK_NOT_NULL(gin)

    struct node *leaf = gin->btree->root;
    if (leaf == NULL) { return NULL; }
    while (leaf && !leaf->is_leaf) {
        leaf = leaf->pointers[0];
    }
    if (leaf == NULL) { return NULL; }

    struct list_node *res = NULL;
    struct list_node *cur_leaf_list = NULL;
    struct gin_record *gin_rec = NULL;
    while (leaf) {
        for (int i = 0; i < leaf->num_keys; i++) {
            cur_leaf_list = leaf->pointers[i];
            while (cur_leaf_list) {
                gin_rec = cur_leaf_list->value;
                if (strcmp(gin->btree->key_from_record(gin_rec->data), key) == 0) {
                    res = list_prepend(res, gin_rec->data);
                }
                cur_leaf_list = cur_leaf_list->tail;
            }
        }
        leaf = leaf->pointers[gin->btree->order - 1];
    }
    return res;
}

bool _gin_starts_with(const record_key *rec_key, const gin_record_key *sub) {
    const char *rec_key_s = (char *)rec_key;
    if (strlen(rec_key_s) < strlen(sub)) {
        return false;
    }
    if (strncmp(rec_key_s, sub, strlen(sub)) == 0) {
        return true;
    }
    return false;
}

struct list_node *gin_find_by_key_starts_with(struct gin *gin, record_key *sub) {
    GIN_CHECK_NOT_NULL(gin)

    struct node *leaf = gin->btree->root;
    if (leaf == NULL) { return NULL; }
    while (leaf && !leaf->is_leaf) {
        leaf = leaf->pointers[0];
    }
    if (leaf == NULL) { return NULL; }

    struct list_node *res = NULL;
    struct list_node *cur_leaf_list = NULL;
    struct gin_record *gin_rec = NULL;
    while (leaf) {
        for (int i = 0; i < leaf->num_keys; i++) {
            cur_leaf_list = leaf->pointers[i];
            while (cur_leaf_list) {
                gin_rec = cur_leaf_list->value;
                if (_gin_starts_with(gin->btree->key_from_record(gin_rec->data), sub)) {
                    res = list_prepend(res, gin_rec->data);
                }
                cur_leaf_list = cur_leaf_list->tail;
            }
        }
        leaf = leaf->pointers[gin->btree->order - 1];
    }
    return res;
}
