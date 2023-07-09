#ifndef INDEXES_USER_RECORD_INDEXES_H
#define INDEXES_USER_RECORD_INDEXES_H

#include "user_record.h"

#include "../list/list.h"
#include "../table/table_package.h"
#include "../btree/btree_package.h"
#include "../gin/gin_package.h"
#include "../mixed/mixed_package.h"

struct table_search_ops user_record_descr_table_search_ops(void);
struct table_search_ops user_record_name_table_search_ops(void);

struct table *user_record_table(struct list_node *content);
struct btree *user_record_descr_brtee(struct table *table);
struct btree *user_record_name_brtee(struct table *table);
struct gin *user_record_descr_gin(struct table *table);
struct gin *user_record_name_gin(struct table *table);
struct mixed_index *user_record_descr_mixed(struct table *table);
struct mixed_index *user_record_name_mixed(struct table *table);

#endif //INDEXES_USER_RECORD_INDEXES_H
