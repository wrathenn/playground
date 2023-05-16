#ifndef INDEXES_GIN_H
#define INDEXES_GIN_H

#include "../btree/btree_package.h"

typedef char gin_record_key;

/*
 * Записи для GIN индекса необходимо обернуть в эту структуру.
 *      Во внутренностях insert используются аналогичные gin_record,
 *      но key в них -- выделенная из индексируемого поля часть.
 * При создании print-функции необходимо приводить каждую запись
 * индекса к этому типу.
 */
struct gin_record {
    record *data;
    gin_record_key *key;
};

typedef gin_record_key *(*gin_key_from_record_f)(const record *rec);

struct gin {
    struct btree *btree;
    gin_key_from_record_f gin_key_from_record;
};

#endif //INDEXES_GIN_H
