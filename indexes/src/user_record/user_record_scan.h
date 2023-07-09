#ifndef INDEXES_USER_RECORD_FROM_FILE_H
#define INDEXES_USER_RECORD_FROM_FILE_H

#include "user_record.h"
#include "../shared/scan.h"

struct list_node *user_read_from_file(const char *filename);
struct user_record *user_record_from_string(const char *str);
int user_record_scan(struct user_record **ur, char *prefix);

#endif //INDEXES_USER_RECORD_FROM_FILE_H
