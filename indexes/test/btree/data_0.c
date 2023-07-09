#include "btree_tests_package.h"

#include <stdio.h>

#define DATA_FILENAME "test/data/btree_data_0.txt"

START_TEST(btree_data_0_first)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(1, "_", "a0"));

    struct list_node *res = btree_find_by_key(btree, "a0");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_0_second)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);
    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(2, "_", "a1"));

    struct list_node *res = btree_find_by_key(btree, "a1");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_0_third)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(3, "_", "a2"));

    struct list_node *res = btree_find_by_key(btree, "a2");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);
    ck_assert(success);
}
END_TEST

START_TEST(btree_data_0_fourth)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(4, "_", "a3"));

    struct list_node *res = btree_find_by_key(btree, "a3");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_0_null)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;

    struct list_node *res = btree_find_by_key(btree, "a4");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_0_all)
    {
        struct list_node *from_file = user_read_from_file(DATA_FILENAME);
        struct table *table = user_record_table(from_file);
        struct btree *btree = user_record_descr_brtee(table);

        int expect = 4;

        struct list_node *l_res = btree_find_by_key_starts_with(btree, "a");
        int res = list_size(l_res);

        btree_free(btree, NULL);
        table_free(table, user_record_free);
        list_free(l_res, NULL);

        ck_assert_int_eq(res, expect);
    }
END_TEST

Suite *btree_data_0_suite(void) {
    Suite *s;
    TCase *tc_pos;

    s = suite_create("btree tests 0");

    tc_pos = tcase_create("positives");
    tcase_add_test(tc_pos, btree_data_0_first);
    tcase_add_test(tc_pos, btree_data_0_second);
    tcase_add_test(tc_pos, btree_data_0_third);
    tcase_add_test(tc_pos, btree_data_0_fourth);
    tcase_add_test(tc_pos, btree_data_0_null);
    tcase_add_test(tc_pos, btree_data_0_all);

    /* Добавить кейс в набор */
    suite_add_tcase(s, tc_pos);

    return s;
}
