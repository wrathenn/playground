#include "user_record_indexes.h"

#include "../shared/record_key_string.h"

struct table_search_ops user_record_descr_table_search_ops(void) {
    struct table_search_ops ops = {
        user_record_get_descr,
        string_key_comparator,
        string_key_print,
        user_record_print,
        string_key_starts_with,
        string_key_contains
    };
    return ops;
}

struct table_search_ops user_record_name_table_search_ops(void) {
    struct table_search_ops ops = {
        user_record_get_name,
        string_key_comparator,
        string_key_print,
        user_record_print,
        string_key_starts_with,
        string_key_contains
    };
    return ops;
}

struct table *user_record_table(struct list_node *content) {
    return table_init(content);
}

struct btree *user_record_descr_brtee(struct table *table) {
    return btree_init(
        user_record_get_descr,
        string_key_comparator,
        string_key_print,
        user_record_print,
        string_key_starts_with,
        string_key_contains,
        table
    );
}

struct btree *user_record_name_brtee(struct table *table) {
    return btree_init(
        user_record_get_name,
        string_key_comparator,
        string_key_print,
        user_record_print,
        string_key_starts_with,
        string_key_contains,
        table
    );
}

struct gin *user_record_descr_gin(struct table *table) {
    return gin_init(
        user_record_print_gin,
        (gin_key_from_record_f) user_record_get_descr,
        table
    );
}

struct gin *user_record_name_gin(struct table *table) {
    return gin_init(
        user_record_print_gin,
        (gin_key_from_record_f) user_record_get_name,
        table
    );
}

struct mixed_index *user_record_descr_mixed(struct table *table) {
    return mixed_init(
        user_record_print_mixed,
        (mixed_key_from_record_f) user_record_get_descr,
        table
    );
}

struct mixed_index *user_record_name_mixed(struct table *table) {
    return mixed_init(
        user_record_print_mixed,
        (mixed_key_from_record_f) user_record_get_name,
        table
    );
}

