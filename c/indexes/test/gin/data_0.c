#include "gin_tests_package.h"

#include <stdio.h>

#define DATA_FILENAME "test/data/gin_data_0.txt"

START_TEST(gin_data_0_hello)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct gin *gin = user_record_descr_gin(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(1, "_", "hello, world!"));

    struct list_node *res = gin_find_by_key_contains(gin, cp_str("hello"));

    int success = list_eq(expect, res, user_record_eq);

    gin_free(gin);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(gin_data_0_hell)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct gin *gin = user_record_descr_gin(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(2, "_", "hell, heaven"));

    struct list_node *res = gin_find_by_key_contains(gin, cp_str("hell"));

    int success = list_eq(expect, res, user_record_eq);

    gin_free(gin);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(gin_data_0_multiple)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct gin *gin = user_record_descr_gin(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(5, "_", "good evening"));
    expect = list_prepend(expect, user_record_init_cp(6, "_", "good gooh gool good"));
    expect = list_prepend(expect, user_record_init_cp(7, "_", "thanks im good"));

    struct list_node *res = gin_find_by_key_contains(gin, cp_str("good"));

    int success = list_eq(expect, res, user_record_eq);

    gin_free(gin);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(gin_data_0_number)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct gin *gin = user_record_descr_gin(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(9, "_", "year 2001"));

    struct list_node *res = gin_find_by_key_contains(gin, cp_str("2001"));

    int success = list_eq(expect, res, user_record_eq);

    gin_free(gin);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(gin_data_0_null)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct gin *gin = user_record_descr_gin(table);

    struct list_node *res = gin_find_by_key_contains(gin, cp_str("thisdoesntexist"));

    gin_free(gin);
    table_free(table, user_record_free);
    list_free(res, NULL);

    ck_assert(res == NULL);
}
END_TEST

Suite *gin_data_0_suite(void) {
    Suite *s;
    TCase *tc_pos;

    s = suite_create("gin tests 0");

    tc_pos = tcase_create("gin tests");
    tcase_add_test(tc_pos, gin_data_0_hello);
    tcase_add_test(tc_pos, gin_data_0_hell);
    tcase_add_test(tc_pos, gin_data_0_multiple);
    tcase_add_test(tc_pos, gin_data_0_number);
    tcase_add_test(tc_pos, gin_data_0_null);

    /* Добавить кейс в набор */
    suite_add_tcase(s, tc_pos);

    return s;
}
