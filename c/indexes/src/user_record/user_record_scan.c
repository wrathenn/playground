#include "user_record_scan.h"

#include <stdio.h>
#include <string.h>

#define DELIMITER ';'
size_t get_delimiter_i(const char *str, char del) {
    size_t delimiter_i = 0;
    char c = str[0];

    while (c != '\0' && c != '\n' && c != del) {
        ++delimiter_i;
        c = str[delimiter_i];
    }
    return delimiter_i;
}

const char *int_from_string(const char *str, int *res) {
    size_t delimiter_i = get_delimiter_i(str, DELIMITER);
    int r_cnt = sscanf(str, "%d", res);
    if (r_cnt == 0) { return NULL; }

    return str + delimiter_i + 1;
}

const char *str_from_string(const char *str, char **res) {
    size_t delimiter_i = get_delimiter_i(str, DELIMITER);
    *res = calloc(delimiter_i + 1, sizeof(char));
    strncpy(*res, str, delimiter_i);

    return str + delimiter_i + 1;
}

struct user_record *user_record_from_string(const char *str) {
    const char *initial_str = str;
    int id;
    char *name, *descr;

    str = int_from_string(str, &id);
    if (!str) {
        printf("Ошибка: Некорректный id в записи \"%s\"\n", initial_str);
        return NULL;
    }

    const char *initial_str_end = str + strlen(str);

    str = str_from_string(str, &name);
    if (str >= initial_str_end) {
        printf("Ошибка: Недостаточно данных в записи \"%s\"\n", initial_str);
        free(name);
        return NULL;
    }

    str = str_from_string(str, &descr);
    return user_record_init(id, name, descr);
}

#define MAX_BUFFER_SIZE 4096
struct list_node *user_read_from_file(const char *filename) {
    struct list_node *list_records = NULL;
    struct user_record *ur = NULL;

    FILE *file = fopen(filename, "r");
    if (file == NULL) {
        printf("Ошибка: Невозможно открыть файл \"%s\"\n", filename);
        return NULL;
    }

    char *buf = calloc(MAX_BUFFER_SIZE, sizeof(char));
    if (buf == NULL) {
        printf("Ошибка: Выделение памяти\n");
        goto close;
    }

    while(fgets(buf, MAX_BUFFER_SIZE, file)) {
        ur = user_record_from_string(buf);
        if (ur == NULL) {
            printf("Ошибка: Неудачное чтение записи \"%s\"\n", buf);
        } else {
            list_records = list_prepend(list_records, ur);
        }
    }

    free(buf);
close:
    fclose(file);
    return list_records;
}

int user_record_scan(struct user_record **ur, char *prefix) {
    int id;
    char *name, *descr;

    printf("%sВведите идентификатор (целое число): ", prefix);
    if (scan_int(&id) == -1) {
        printf("%sОшибка при вводе идентификатора (целое число)\n", prefix);
        return -1;
    }

    printf("%sВведите имя пользователя (любая строка): ", prefix);
    if (scan_str(&name) == -1) {
        printf("%sОшибка при вводе имени пользователя\n", prefix);
        return -1;
    }

    printf("%sВведите описание пользователя (любая строка): ", prefix);
    if (scan_str(&descr) == -1) {
        printf("%sОшибка при вводе описания пользователя\n", prefix);
        free(name);
        return -1;
    }

    *ur = user_record_init(id, name, descr);
    return 0;
}
