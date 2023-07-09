#include "user_record.h"

#include "../shared/cp_str.h"

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

struct user_record *user_record_init(int id, char *name, char *descr) {
    struct user_record *r = calloc(1, sizeof(struct user_record));
    r->id = id;
    r->name = name;
    r->descr = descr;
    return r;
}

struct user_record *user_record_init_cp(int id, char *name, char *descr) {
    struct user_record *r = calloc(1, sizeof(struct user_record));
    r->id = id;
    r->name = cp_str(name);
    r->descr = cp_str(descr);
    return r;
}

void user_record_free(record *ur) {
    struct user_record *t = ur;
    free(t->name);
    free(t->descr);
    free(t);
}

void user_record_print(const record *rec) {
    const struct user_record *r = rec;
    printf("Пользователь[идентификатор=%d, имя=\"%s\", описание=\"%s\"]", r->id, r->name, r->descr);
}

void user_record_print_gin(const record *rec) {
    const struct gin_record *gin_rec = rec;
    user_record_print(gin_rec->data);
}

void user_record_print_mixed(const record *rec) {
    const struct mixed_record *mr = rec;
    printf("Индексая Запись[ключ=\"%s\", оригинальная=\"%s\", данные=", mr->key, mr->is_original ? "Да" : "Нет");
    user_record_print(mr->data);
    printf("]");
}

bool user_record_eq(const record *this, const record *that) {
    const struct user_record *thi = this, *tha = that;
    if (thi->id != tha->id ||
        strcmp(thi->name, tha->name) != 0 ||
        strcmp(thi->descr, tha->descr) != 0
    ) { return false; }
    return true;
}

record_key *user_record_get_name(const record *record) {
    struct user_record *re = (struct user_record *)record;
    return re->name;
}

record_key *user_record_get_descr(const record *record) {
    struct user_record *re = (struct user_record *)record;
    return re->descr;
}
