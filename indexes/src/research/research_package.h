#ifndef INDEXES_RESEARCH_PACKAGE_H
#define INDEXES_RESEARCH_PACKAGE_H

#include "../user_record/user_record_package.h"
void do_research(char *filename, long step, long count, bool extra_info);

#include "../btree/btree_package.h"
double btree_time_find_by_key(struct btree *btree, record_key *key, long count);
double btree_time_find_by_key_begins(struct btree *btree, record_key *key, long count);
double btree_time_find_all(struct btree *btree, long count);
double btree_time_find_by_key_contains(struct btree *btree, record_key *key, long count);
double btree_test_all_keys(struct btree *btree, struct list_node *keys, long count);
double btree_test_all_beginnings(struct btree *btree, struct list_node *keys, long count);
double btree_test_all_contains(struct btree *btree, struct list_node *keys, long count);

#include "../gin/gin_package.h"
double gin_time_find_by_key(struct gin *gin, record_key *key, long count);
double gin_time_find_by_key_begins(struct gin *gin, record_key *key, long count);
double gin_time_find_all(struct gin *gin, long count);
double gin_time_find_by_key_contains(struct gin *gin, record_key *key, long count);
double gin_test_all_keys(struct gin *gin, struct list_node *keys, long count);
double gin_test_all_beginnings(struct gin *gin, struct list_node *keys, long count);
double gin_test_all_contains(struct gin *gin, struct list_node *keys, long count);

#include "../table/table_package.h"
double table_time_find_by_key(struct table *table, record_key *key, long count, struct table_search_ops *tsops);
double table_time_find_by_key_begins(struct table *table, record_key *key, long count, struct table_search_ops *tsops);
double table_time_find_all(struct table *table, long count);
double table_time_find_by_key_contains(struct table *table, record_key *key, long count, struct table_search_ops *tsops);
double table_test_all_keys(struct table *table, struct list_node *keys, long count, struct table_search_ops *tsops);
double table_test_all_beginnings(struct table *table, struct list_node *keys, long count, struct table_search_ops *tsops);
double table_test_all_contains(struct table *table, struct list_node *keys, long count, struct table_search_ops *tsops);

#include "../mixed/mixed_package.h"
double mixed_time_find_by_key(struct mixed_index *mix, record_key *key, long count);
double mixed_time_find_by_key_begins(struct mixed_index *mix, record_key *key, long count);
double mixed_time_find_all(struct mixed_index *mix, long count);
double mixed_time_find_by_key_contains(struct mixed_index *mix, record_key *key, long count);
double mixed_test_all_keys(struct mixed_index *mix, struct list_node *keys, long count);
double mixed_test_all_beginnings(struct mixed_index *mix, struct list_node *keys, long count);
double mixed_test_all_contains(struct mixed_index *mix, struct list_node *keys, long count);

#endif //INDEXES_RESEARCH_PACKAGE_H
