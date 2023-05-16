#ifndef INDEXES_RECORD_H
#define INDEXES_RECORD_H

#include <stdbool.h>

/*
 * Индексные записи для универсальности должны быть void *.
 * Ключ этих индексных записей также может быть любым => void *.
 * Тип, который лежит за этим void * должен быть известен конечному пользователю.
 * Пользователь должен определить несколько функций для работы со своим типом, скрытым за void *.
 */
typedef void record;
typedef void record_key;

/* Абстракция для взятия поля из user_record */
typedef record_key *(*record_key_getter_f)(const record *r);
/* Возвращает -1 если left < right, 0 если left = right, и 1 если left > right */
typedef int (*record_key_comparator_f)(const record_key *left, const record_key *right);

typedef void (*record_key_print_f)(const record_key *key);
typedef void (*record_print_f)(const record *rec);

// Операции для поиска:
/* Проверить, что "this" начинается с "that" */
typedef int (*record_key_starts_with_comparator_f)(const record_key *this, const record_key *that);
/* Проверить, что "this" содержит в себе "that" */
typedef bool (*record_key_contains_f)(const record_key *this, const void *that);

typedef void (*record_free)(record *rec);

#endif //INDEXES_RECORD_H
