#include "btree_tests_package.h"
#include "../../src/user_record/user_record_indexes.h"

#include <stdio.h>

#define DATA_FILENAME "test/data/btree_data_1.txt"

START_TEST(btree_data_1_leaf1_p1)
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

START_TEST(btree_data_1_leaf1_p2)
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

START_TEST(btree_data_1_leaf1_p3)
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

START_TEST(btree_data_1_leaf1_p4)
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

START_TEST(btree_data_1_leaf2_p1)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(5, "_", "b0"));

    struct list_node *res = btree_find_by_key(btree, "b0");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_1_leaf2_p2)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(6, "_", "b1"));

    struct list_node *res = btree_find_by_key(btree, "b1");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_1_leaf2_p3)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;
    expect = list_prepend(expect, user_record_init_cp(7, "_", "b2"));

    struct list_node *res = btree_find_by_key(btree, "b2");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

START_TEST(btree_data_1_null)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct btree *btree = user_record_descr_brtee(table);

    struct list_node *expect = NULL;

    struct list_node *res = btree_find_by_key(btree, "does not exist in data_1");

    int success = list_eq(expect, res, user_record_eq);

    btree_free(btree, NULL);
    table_free(table, user_record_free);
    list_free(expect, user_record_free);
    list_free(res, NULL);

    ck_assert(success);
}
END_TEST

Suite *btree_data_1_suite(void) {
    Suite *s;
    TCase *tc_pos;

    s = suite_create("btree tests 1");

    tc_pos = tcase_create("positives");
    tcase_add_test(tc_pos, btree_data_1_leaf1_p1);
    tcase_add_test(tc_pos, btree_data_1_leaf1_p2);
    tcase_add_test(tc_pos, btree_data_1_leaf1_p3);
    tcase_add_test(tc_pos, btree_data_1_leaf1_p4);
    tcase_add_test(tc_pos, btree_data_1_leaf2_p1);
    tcase_add_test(tc_pos, btree_data_1_leaf2_p2);
    tcase_add_test(tc_pos, btree_data_1_leaf2_p3);
    tcase_add_test(tc_pos, btree_data_1_null);

    /* Добавить кейс в набор */
    suite_add_tcase(s, tc_pos);

    return s;
}
