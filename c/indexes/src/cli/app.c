#include "app.h"
#include "../shared/lexema.h"

#include <stdio.h>
#include <time.h>
#include <string.h>

#define PREFIX_LOAD_FILE "Загрузка из файла: "
#define PREFIX_SCAN_USER_RECORD "Ручной ввод: "
#define PREFIX_DELETE "Удаление пользователей: "
#define PREFIX_SEARCH           "Поиск: "
#define PREFIX_SEARCH_INDENT    "       "
#define PREFIX_PRINT "Печать структур: "
#define PREFIX_SWITCH_KEY           "Смена ключа: "
#define PREFIX_SWITCH_KEY_INDENT    "             "

#define MEASURE_SEARCH_TIME(search_f, res) \
    do { \
        struct list_node *__temp_res__ = NULL; \
        clock_t start = clock(); \
        __temp_res__ = search_f; \
        list_free(__temp_res__, NULL); \
        clock_t stop = clock(); \
        res = stop - start; \
    } while (0)

enum current_index_key {
    INDEX_KEY_NAME,
    INDEX_KEY_DESCRIPTION
};
enum current_index_key index_key = INDEX_KEY_DESCRIPTION;

static struct table *app_table;

static struct table_search_ops app_tsops_descr;
static struct btree *app_btree_descr;
static struct gin *app_gin_descr;
static struct mixed_index *app_mix_descr;

static struct table_search_ops app_tsops_name;
static struct btree *app_btree_name;
static struct gin *app_gin_name;
static struct mixed_index *app_mix_name;

#define app_tsops (index_key == INDEX_KEY_NAME ? &app_tsops_name : &app_tsops_descr)
#define app_btree (index_key == INDEX_KEY_NAME ? app_btree_name : app_btree_descr)
#define app_gin (index_key == INDEX_KEY_NAME ? app_gin_name : app_gin_descr)
#define app_mix (index_key == INDEX_KEY_NAME ? app_mix_name : app_mix_descr)

static void cli_load_from_file(void) {
    printf(PREFIX_LOAD_FILE"Введите название файла, содержащего записи пользователей: ");
    char *filename;
    while (scan_str(&filename) == -1) {
        printf(PREFIX_LOAD_FILE"Ошибка: некорректный ввод. Попробуйте еще раз.\n");
    }

    struct list_node *from_file = user_read_from_file(filename);

    printf(PREFIX_LOAD_FILE"Успешно загружено %d записей.\n", list_size(from_file));

    struct list_node *cur = from_file;
    while (cur) {
        table_insert(app_table, cur->value);
        btree_insert(app_btree_descr, cur->value);
        btree_insert(app_btree_name, cur->value);
        gin_insert(app_gin_descr, cur->value);
        gin_insert(app_gin_name, cur->value);
        mixed_insert(app_mix_descr, cur->value);
        mixed_insert(app_mix_name, cur->value);
        cur = cur->tail;
    }
    list_free(from_file, NULL);

    printf(PREFIX_LOAD_FILE"Записи добавлены в таблицу и индексы.\n");
}

static void cli_scan_user_record(void) {
    struct user_record *ur;
    if (user_record_scan(&ur, PREFIX_SCAN_USER_RECORD) == -1) {
        printf(PREFIX_SCAN_USER_RECORD"Ошибка: некорректный ввод.\n");
        return;
    }

    table_insert(app_table, ur);
    btree_insert(app_btree_descr, ur);
    btree_insert(app_btree_name, ur);
    gin_insert(app_gin_descr, ur);
    gin_insert(app_gin_name, ur);
    mixed_insert(app_mix_descr, ur);
    mixed_insert(app_mix_name, ur);
    printf(PREFIX_SCAN_USER_RECORD"Пользователь добавлен!\n");
}

static void cli_init_app(void) {
    app_table = user_record_table(NULL);

    app_tsops_descr = user_record_descr_table_search_ops();
    app_btree_descr = user_record_descr_brtee(app_table);
    app_gin_descr = user_record_descr_gin(app_table);
    app_mix_descr = user_record_descr_mixed(app_table);

    app_tsops_name = user_record_name_table_search_ops();
    app_btree_name = user_record_name_brtee(app_table);
    app_gin_name = user_record_name_gin(app_table);
    app_mix_name = user_record_name_mixed(app_table);
}

static void cli_exit_app(void) {
    table_free(app_table, user_record_free);
    btree_free(app_btree_descr, NULL);
    btree_free(app_btree_name, NULL);
    gin_free(app_gin_descr);
    gin_free(app_gin_name);
    mixed_free(app_mix_descr);
    mixed_free(app_mix_name);
}

static void cli_delete_all_users(void) {
    printf(PREFIX_DELETE"удаление %d пользователей...\n", list_size(app_table->content));
    cli_exit_app();
    cli_init_app();
    printf(PREFIX_DELETE"завершено.\n");
}

enum cli_search_t {
    EXIT,
    BY_KEY,
    BY_STARTS_WITH,
    BY_CONTAINS,
    BY_ALL
};

static void cli_query(enum cli_search_t query_type) {
    if (query_type == EXIT) { return; }
    char *key;
    bool input = true;

    switch (query_type) {
        case BY_KEY:
            printf(PREFIX_SEARCH"Введите значение ключа: ");
            while (input) {
                if (scan_str(&key) == -1) {
                    printf(PREFIX_SEARCH"Ошибка: некорректный ввод. Попробуйте еще раз: ");
                } else { input = false; }
            }
            break;
        case BY_STARTS_WITH:
            printf(PREFIX_SEARCH"Введите начало ключа: ");
            while (input) {
                if (scan_str(&key) == -1) {
                    printf(PREFIX_SEARCH"Ошибка: некорректный ввод. Попробуйте еще раз: ");
                } else { input = false; }
            }
            break;
        case BY_CONTAINS:
            printf(PREFIX_SEARCH"Введите слово для поиска (одно слово из букв и/или цифр): ");
            while (input) {
                if (scan_str(&key) == -1) {
                    printf(PREFIX_SEARCH"Ошибка: некорректный ввод. Попробуйте еще раз: ");
                } else if (!lexema_validate(key)) {
                    printf(PREFIX_SEARCH"Ошибка: введенная строка не является словом. Попробуйте еще раз: ");
                } else { input = false; }
            }
            break;
        case BY_ALL:
        case EXIT:
            break;
    }

    struct list_node *res = NULL;
    long table_time, btree_time, gin_time, mixed_time;
    switch (query_type) {
        case BY_KEY:
            MEASURE_SEARCH_TIME(table_find_by_key(app_table, app_tsops, key), table_time);
            MEASURE_SEARCH_TIME(btree_find_by_key(app_btree, key), btree_time);
            MEASURE_SEARCH_TIME(gin_find_by_key(app_gin, key), gin_time);
            MEASURE_SEARCH_TIME(mixed_find_by_key(app_mix, key), mixed_time);
            res = mixed_find_by_key(app_mix, key);
            break;
        case BY_STARTS_WITH:
            MEASURE_SEARCH_TIME(table_find_by_key_starts_with(app_table, app_tsops, key), table_time);
            MEASURE_SEARCH_TIME(btree_find_by_key_starts_with(app_btree, key), btree_time);
            MEASURE_SEARCH_TIME(gin_find_by_key_starts_with(app_gin, key), gin_time);
            MEASURE_SEARCH_TIME(mixed_find_by_key_starts_with(app_mix, key), mixed_time);
            res = mixed_find_by_key_starts_with(app_mix, key);
            break;
        case BY_CONTAINS:
            MEASURE_SEARCH_TIME(table_find_by_key_contains(app_table, app_tsops, key), table_time);
            MEASURE_SEARCH_TIME(btree_find_by_key_contains(app_btree, key), btree_time);
            MEASURE_SEARCH_TIME(gin_find_by_key_contains(app_gin, key), gin_time);
            MEASURE_SEARCH_TIME(mixed_find_by_key_contains(app_mix, key), mixed_time);
            res = mixed_find_by_key_contains(app_mix, key);
            break;
        case BY_ALL:
            MEASURE_SEARCH_TIME(table_find_all(app_table), table_time);
            MEASURE_SEARCH_TIME(btree_find_all(app_btree), btree_time);
            MEASURE_SEARCH_TIME(gin_find_all(app_gin), gin_time);
            MEASURE_SEARCH_TIME(mixed_find_all(app_mix), mixed_time);
            res = mixed_find_all(app_mix);
            break;
        case EXIT:
            break;
    }
    printf(PREFIX_SEARCH_INDENT"Результаты поиска:\n");
    struct list_node *cur = res;
    if (list_size(cur) == 0) { printf(PREFIX_SEARCH_INDENT"Подходящих записей не найдено\n"); }
    for (int i = 0; cur != NULL; ++i, cur = cur->tail) {
        printf(PREFIX_SEARCH_INDENT"%d: ", i);
        user_record_print(cur->value);
        printf("\n");
    }
    list_free(res, NULL);
    printf(PREFIX_SEARCH"Время выполнения запроса (мс):\n");
    printf(PREFIX_SEARCH_INDENT"1) поиск по таблице: %ld;\n", table_time);
    printf(PREFIX_SEARCH_INDENT"2) поиск по индексу на основе B-дерева: %ld;\n", btree_time);
    printf(PREFIX_SEARCH_INDENT"3) поиск по инвертированному индексу: %ld;\n", gin_time);
    printf(PREFIX_SEARCH_INDENT"4) поиск по комбинированному индексу: %ld.\n", mixed_time);
}

static void cli_search(void) {
    printf(PREFIX_SEARCH"Выберите тип поискового запроса:\n");
    printf(PREFIX_SEARCH_INDENT"%d) поиск по ключу;\n", BY_KEY);
    printf(PREFIX_SEARCH_INDENT"%d) поиск по началу ключа;\n", BY_STARTS_WITH);
    printf(PREFIX_SEARCH_INDENT"%d) поиск по содержанию слова;\n", BY_CONTAINS);
    printf(PREFIX_SEARCH_INDENT"%d) поиск всех записей;\n", BY_ALL);
    printf(PREFIX_SEARCH_INDENT"%d) отмена.\n", EXIT);
    int choice = -1;
    bool input = true;
    while (input) {
        if (scan_int(&choice) == -1 || choice < EXIT || choice > BY_ALL) {
            printf(PREFIX_SEARCH" Ошибка: некорректный ввод. Попробуйте еще раз: ");
        } else { input = false; }
    }
    cli_query(choice);
    printf(PREFIX_SEARCH"завершено.\n");
}

static void cli_switch_key(void) {
    printf(
        PREFIX_SWITCH_KEY"Выберите ключ для построения индекса и поиска записей (сейчас - \"%s\"):\n",
        index_key == INDEX_KEY_NAME ? "имя пользователя" : "описание пользователя"
   );
    printf(PREFIX_SWITCH_KEY_INDENT"%d) имя пользователя;\n", INDEX_KEY_NAME);
    printf(PREFIX_SWITCH_KEY_INDENT"%d) описание пользователя.\n", INDEX_KEY_DESCRIPTION);

    enum current_index_key choice = -1;
    bool input = true;
    while (input) {
        if (scan_int((int *)&choice) == -1 || choice < INDEX_KEY_NAME || choice > INDEX_KEY_DESCRIPTION) {
            printf(PREFIX_SWITCH_KEY_INDENT"Ошибка: некорректный ввод. Попробуйте еще раз: ");
        } else { input = false; }
    }
    index_key = choice;
    printf(PREFIX_SWITCH_KEY"завершено. \n");
}

static void cli_print(void) {
    printf("\n\n"PREFIX_PRINT"Таблица:\n");
    table_print(app_table, app_tsops);
    printf("\n\n"PREFIX_PRINT"Индекс на основе B-дерева:\n");
    btree_print(app_btree);
    printf("\n\n"PREFIX_PRINT"Инвертированный индекс:\n");
    gin_print(app_gin);
    printf("\n\n"PREFIX_PRINT"Комбинированный индекс:\n");
    mixed_print(app_mix);
    printf("\n"PREFIX_PRINT"завершено.\n");
}

enum cli_command_t {
    CLI_COMMAND_EXIT,
    CLI_COMMAND_LOAD,
    CLI_COMMAND_USER_ADD,
    CLI_COMMAND_DELETE_ALL,
    CLI_COMMAND_SEARCH,
    CLI_COMMAND_SWITCH_KEY,
    CLI_COMMAND_PRINT,
    CLI_COMMAND_RESEARCH_DATA
};
#define CLI_COMMAND_FIRST CLI_COMMAND_EXIT
#define CLI_COMMAND_LAST CLI_COMMAND_RESEARCH_DATA

static void cli_print_menu(void) {
    printf("------- Список возможных действий -------\n");
    printf("%d. Загрузить в таблицу данные из файла.\n", CLI_COMMAND_LOAD);
    printf("%d. Добавить в таблицу запись вручную.\n", CLI_COMMAND_USER_ADD);
    printf("%d. Очистить таблицу.\n", CLI_COMMAND_DELETE_ALL);
    printf("%d. Выполнить поисковый запрос.\n", CLI_COMMAND_SEARCH);
    printf("%d. Поменять ключ индексов и поиска.\n", CLI_COMMAND_SWITCH_KEY);
    printf("%d. Вывести структуры на экран.\n", CLI_COMMAND_PRINT);
    printf("%d. Посмотреть результаты исследования.\n", CLI_COMMAND_RESEARCH_DATA);
    printf("%d. Выход.\n", CLI_COMMAND_EXIT);
    printf("-----------------------------------------\n");
}

static void cli_print_input_hint(bool hint) {
    if (hint) {
        printf("Номер действия - целое число от %d до %d\n", CLI_COMMAND_FIRST, CLI_COMMAND_LAST);
    }
    printf("Введите номер действия: ");
}

static void cli_do_research(bool extra_info) {
    if (!extra_info) printf("[\n");
    do_research("data_gen/dataset_500000.txt", 1000, 100, extra_info);
    if (!extra_info) printf("]\n");
    return;

    int count = 1;
    char *file_prefix = "data_gen/dataset_";
    char *file_suffix = ".txt";
    char filename[256] = { '\0' };
    int i = 100, step = 100;

    if (!extra_info) printf("[\n");
    for (; i <= 50000; i += step) {
        sprintf(filename, "%s%d%s", file_prefix, i, file_suffix);
//        do_research(filename, count, extra_info);
        if (!extra_info && i != 10000) {
            printf(",");
        }
    }
    if (!extra_info) printf("]\n");
}

void start_cli_app(void) {
    enum cli_command_t choice = -1;
    bool input = true;
    cli_init_app();
    while (input) {
        cli_print_menu();
        cli_print_input_hint(false);
        while (scan_int((int *) &choice) == -1 || choice < CLI_COMMAND_FIRST || choice > CLI_COMMAND_LAST) {
            printf("Ошибка: некорректный ввод. Попробуйте еще раз.\n");
            cli_print_input_hint(true);
        }

        switch (choice) {
            case CLI_COMMAND_EXIT:
                printf("Завершение программы.\n");
                cli_exit_app();
                input = false;
                break;
            case CLI_COMMAND_LOAD:
                cli_load_from_file();
                break;
            case CLI_COMMAND_USER_ADD:
                cli_scan_user_record();
                break;
            case CLI_COMMAND_DELETE_ALL:
                cli_delete_all_users();
                break;
            case CLI_COMMAND_PRINT:
                cli_print();
                break;
            case CLI_COMMAND_SEARCH:
                cli_search();
                break;
            case CLI_COMMAND_SWITCH_KEY:
                cli_switch_key();
                break;
            case CLI_COMMAND_RESEARCH_DATA:
                cli_do_research(true);
                break;
        }
    }
}
