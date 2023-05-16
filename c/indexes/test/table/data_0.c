#include "table_tests_package.h"

#include <stdio.h>

#define DATA_FILENAME "test/data/table_data_0.txt"

START_TEST(table_data_0_hello)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct table_search_ops tsops = user_record_descr_table_search_ops();

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(1, "_", "hello, world!"));

    struct list_node *res = table_find_by_key_starts_with(table, &tsops, "hello");

    bool success = list_eq(expect, res, user_record_eq);

    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(table_data_0_multiple)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct table_search_ops tsops = user_record_descr_table_search_ops();

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(1, "_", "hello, world!"));
    expect = list_prepend(expect, user_record_init_cp(2, "_", "hell, heaven"));

    struct list_node *res = table_find_by_key_starts_with(table, &tsops, "hell");

    bool success = list_eq(expect, res, user_record_eq);

    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(table_data_0_all)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);

    struct list_node *rres = table_find_all(table);
    int res = list_size(rres);

    table_free(table, user_record_free);
    list_free(rres, NULL);

    ck_assert(res == 10);
}
END_TEST

START_TEST(table_data_0_by_key)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct table_search_ops tsops = user_record_descr_table_search_ops();

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(9, "_", "year 2001"));

    struct list_node *res = table_find_by_key(table, &tsops, "year 2001");

    bool success = list_eq(expect, res, user_record_eq);

    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(table_data_0_by_key_contains)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct table_search_ops tsops = user_record_descr_table_search_ops();

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(5, "_", "good evening"));
    expect = list_prepend(expect, user_record_init_cp(6, "_", "good gooh gool good"));
    expect = list_prepend(expect, user_record_init_cp(7, "_", "thanks im good"));

    struct list_node *res = table_find_by_key_contains(table, &tsops, "good");

    bool success = list_eq(expect, res, user_record_eq);

    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

Suite *table_data_0_suite(void) {
    Suite *s;
    TCase *tc_pos;

    s = suite_create("table tests 0");

    tc_pos = tcase_create("table data_0 tests");
    tcase_add_test(tc_pos, table_data_0_hello);
    tcase_add_test(tc_pos, table_data_0_multiple);
    tcase_add_test(tc_pos, table_data_0_all);
    tcase_add_test(tc_pos, table_data_0_by_key);
    tcase_add_test(tc_pos, table_data_0_by_key_contains);

    /* Добавить кейс в набор */
    suite_add_tcase(s, tc_pos);

    return s;
}
