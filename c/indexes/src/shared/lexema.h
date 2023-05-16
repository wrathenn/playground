#ifndef INDEXES_LEXEMA_H
#define INDEXES_LEXEMA_H

#include "../list/list.h"

struct list_node* lexemas_from_str(const char *s);
bool lexema_validate(const char *s);
bool lexema_contains(const char *str, const char *lexema);

#endif //INDEXES_LEXEMA_H
