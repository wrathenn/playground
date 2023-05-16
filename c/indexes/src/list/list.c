#include "list.h"

#include <stdlib.h>
#include <stdio.h>

static struct list_node *_list_new_node(void *value, struct list_node *tail) {
    struct list_node *new_head = calloc(1, sizeof(struct list_node));
    new_head->value = value;
    new_head->tail = tail;
    return new_head;
}

struct list_node *list_init(void *value) {
    return _list_new_node(value, NULL);
}

struct list_node *list_prepend(struct list_node *head, void *value) {
    return _list_new_node(value, head);
}

struct list_node *list_append(struct list_node *head, void *value) {
    struct list_node *cur = head;
    while (cur->tail != NULL) {
        cur = cur->tail;
    }
    cur->tail = _list_new_node(value, NULL);
    return head;
}

int list_size(const struct list_node *head) {
    int res;
    const struct list_node *cur = head;
    for (res = 0; cur != NULL ; ++res) {
        cur = cur->tail;
    }
    return res;
}

bool list_exists(const struct list_node *head, const void *value, eq_f equals) {
    const struct list_node *cur = head;
    while (cur != NULL) {
        if (equals(cur->value, value)) {
            return true;
        }
        cur = cur->tail;
    }
    return false;
}

struct list_node *list_find(struct list_node *head, void *value, eq_f equals) {
    struct list_node *cur = head;
    while (cur != NULL) {
        if (equals(cur->value, value)) {
            return cur;
        }
        cur = cur->tail;
    }
    return NULL;
}

void list_destroy(struct list_node *head) {
    if (head == NULL) return;
    list_destroy(head->tail);
    free(head);
}

void list_print(const struct list_node *head, void (*printer)(const void *value)) {
    const struct list_node *cur = head;
    printf("Список[");
    while (cur) {
        if (printer) { printer(cur->value); }
        printf(" -> ");
        cur = cur->tail;
    }
    printf("Nil]");
}

void list_free(struct list_node *head, void (*f)(void *value)) {
    struct list_node *temp;
    while (head) {
        temp = head->tail;
        if (f) { f(head->value); }
        free(head);
        head = temp;
    }
}

bool list_eq(const struct list_node *this, const struct list_node *that, eq_f eqf) {
    const struct list_node *t = this;
    while (t) {
        if (!list_exists(that, this->value, eqf)) {
            return false;
        }
        t = t->tail;
    }
    if (list_size(this) != list_size(that)) {
        return false;
    }
    return true;
}

struct list_node *list_copy(const struct list_node *head) {
    struct list_node *res = NULL;
    while (head) {
        res = list_prepend(res, head->value);
        head = head->tail;
    }
    return res;
}
