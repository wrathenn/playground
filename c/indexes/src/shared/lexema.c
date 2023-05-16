#include "lexema.h"

#include <stdlib.h>
#include <string.h>

static bool is_lexema_symbol(char c) {
    return (
        (c >= 'a' && c <= 'z') ||
        (c >= 'A' && c <= 'Z') ||
        (c >= '0' && c <= '9') || c < 0
    )
    ? true
    : false;
}

/*
 * Выделить следующее слово из строки.
 * Возвращает: смещение относительно начальной строки
 * res_lexema -- указатель на выделенное слово
 */
static const char *lexema_get_next(const char *s, char **res_lexema) {
    char c = s[0];
    size_t res_begin = 0;
    bool lexema_started = false;

    // Поиск начала слова
    while (c != '\0') {
        lexema_started = is_lexema_symbol(c);
        if (lexema_started) break;
        ++res_begin;
        c = s[res_begin];
    }
    if (!lexema_started) {
        *res_lexema = NULL;
        return s + res_begin;
    }

    // Поиск конца слова
    // res_end -- индекс на 1 символ после конца слова
    size_t res_end = res_begin + 1;
    while (is_lexema_symbol(s[res_end])) {
        ++res_end;
    }

    // Создание нового слова
    size_t res_len = res_end - res_begin;
    char *res = calloc(res_len + 1, sizeof(char));
    strncpy(res, s + res_begin, res_len);
    *res_lexema = res;

    return s + res_end + 1;
}

static bool lexemas_eq(const void *v1, const void *v2) {
    return strcmp(v1, v2) == 0
    ? true
    : false;
}

struct list_node* lexemas_from_str(const char *s) {
    struct list_node *res = NULL;
    char *cur_lexema = NULL;
    const char *s_end_p = s + strlen(s);

    const char *cur_string_p = lexema_get_next(s, &cur_lexema);
    while (cur_lexema != NULL) {
        if (!list_exists(res, cur_lexema, lexemas_eq)) {
            res = list_prepend(res, cur_lexema);
        } else {
            free(cur_lexema);
        }
        // Чтобы не было чтения за границами выделенной памяти
        if (cur_string_p < s_end_p) {
            cur_string_p = lexema_get_next(cur_string_p, &cur_lexema);
        } else {
            cur_lexema = NULL;
        }
    }
    return res;
}

bool lexema_validate(const char *s) {
    while (*s != '\0') {
        if (!is_lexema_symbol(*s)) {
            return false;
        }
        ++s;
    }
    return true;
}

bool lexema_contains(const char *str, const char *lexema) {
    bool res = false;
    if (!lexema_validate(lexema)) {
        return false;
    }

    struct list_node *lexemas = lexemas_from_str(str);
    const struct list_node *cur = lexemas;
    while (cur) {
        if (lexemas_eq(cur->value, lexema)) {
            res = true;
            break;
        }
        cur = cur->tail;
    }
    list_free(lexemas, free);
    return res;
}

