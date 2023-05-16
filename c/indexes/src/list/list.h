#ifndef INDEXES_LIST_H
#define INDEXES_LIST_H

#include <stdbool.h>

struct list_node {
    void *value;
    struct list_node *tail;
};

typedef bool (*eq_f)(const void *v1, const void *v2);

struct list_node *list_init(void *value);
struct list_node *list_prepend(struct list_node *head, void *value);
struct list_node *list_append(struct list_node *head, void *value);
struct list_node *list_copy(const struct list_node *head);
int list_size(const struct list_node *head);
bool list_exists(const struct list_node *head, const void *value, eq_f);
struct list_node *list_find(struct list_node *head, void *value, eq_f);
void list_destroy(struct list_node *head);
void list_print(const struct list_node *head, void (*printer)(const void *value));
void list_foreach(struct list_node *head, void (*f)(const void *value));
void list_free(struct list_node *head, void (*f)(void *value));
bool list_eq(const struct list_node *this, const struct list_node *that, eq_f);

#endif //INDEXES_LIST_H
