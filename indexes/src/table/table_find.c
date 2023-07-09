#include "table_find.h"

#include <stdio.h>

struct list_node *table_find_all(struct table *t) {
    struct list_node *res = NULL, *cur = t->content;
    while (cur) {
        res = list_prepend(res, cur->value);
        cur = cur->tail;
    }
    return res;
}

struct list_node *table_find_by_key(struct table *t, struct table_search_ops *sops, record_key *key) {
    struct list_node *res = NULL, *cur = t->content;
    while (cur) {
        if (sops->comparator(sops->key_from_record(cur->value), key) == 0) {
            res = list_prepend(res, cur->value);
        }
        cur = cur->tail;
    }
    return res;
}

struct list_node *table_find_by_key_starts_with(struct table *t, struct table_search_ops *sops, record_key *sub) {
    struct list_node *res = NULL, *cur = t->content;
    while (cur) {
        if (sops->starts_with(
            sops->key_from_record(cur->value),
            sub
        ) == 0) {
            res = list_prepend(res, cur->value);
        }
        cur = cur->tail;
    }
    return res;
}

struct list_node *table_find_by_key_contains(struct table *t, struct table_search_ops *sops, void *sub) {
    struct list_node *res = NULL, *cur = t->content;
    while (cur) {
        if (sops->contains(
             sops->key_from_record(cur->value),
             sub
        )) {
            res = list_prepend(res, cur->value);
        }
        cur = cur->tail;
    }
    return res;
}
