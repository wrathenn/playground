#include "mixed_tests_package.h"
#include "../../src/user_record/user_record_indexes.h"

#include <stdio.h>

#define DATA_FILENAME "test/data/mixed_data_0.txt"

START_TEST(mixed_data_0_by_key_begin)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct mixed_index *mix = user_record_descr_mixed(table);

    struct list_node *rres = mixed_find_by_key_starts_with(mix, "Rise");

    int res = list_size(rres);

    mixed_free(mix);
    table_free(table, user_record_free);
    list_free(rres, NULL);

    ck_assert(res == 2);
}
END_TEST

START_TEST(mixed_data_0_by_key_begin2)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct mixed_index *mix = user_record_descr_mixed(table);

    struct list_node *rres = mixed_find_by_key_starts_with(mix, "Life");

    int res = list_size(rres);

    mixed_free(mix);
    table_free(table, user_record_free);
    list_free(rres, NULL);

    ck_assert(res == 2);
}
END_TEST

START_TEST(mixed_data_0_by_key)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct mixed_index *mix = user_record_descr_mixed(table);

    struct list_node *rres = mixed_find_by_key(mix, "The dragons of their aeon");

    int res = list_size(rres);

    mixed_free(mix);
    table_free(table, user_record_free);
    list_free(rres, NULL);

    ck_assert(res == 2);
}
END_TEST

START_TEST(mixed_data_0_by_key_contains)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct mixed_index *mix = user_record_descr_mixed(table);

    struct list_node *rres = mixed_find_by_key_contains(mix, "rise");

    int res = list_size(rres);

    mixed_free(mix);
    table_free(table, user_record_free);
    list_free(rres, NULL);

    ck_assert(res == 26);
}
END_TEST

START_TEST(mixed_data_0_all)
{
    struct list_node *from_file = user_read_from_file(DATA_FILENAME);
    struct table *table = user_record_table(from_file);
    struct mixed_index *mix = user_record_descr_mixed(table);

    struct list_node *rres = mixed_find_all(mix);

    int res = list_size(rres);

    mixed_free(mix);
    table_free(table, user_record_free);
    list_free(rres, NULL);


    ck_assert(res == 133);
}
END_TEST


Suite *mixed_data_0_suite(void) {
    Suite *s;
    TCase *tc_pos;

    s = suite_create("mixed tests 0");

    tc_pos = tcase_create("mixed tests");
    tcase_add_test(tc_pos, mixed_data_0_by_key_begin);
    tcase_add_test(tc_pos, mixed_data_0_by_key_begin2);
    tcase_add_test(tc_pos, mixed_data_0_by_key);
    tcase_add_test(tc_pos, mixed_data_0_by_key_contains);
    tcase_add_test(tc_pos, mixed_data_0_all);

    /* Добавить кейс в набор */
    suite_add_tcase(s, tc_pos);

    return s;
}
