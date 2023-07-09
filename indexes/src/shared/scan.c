#include "scan.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int scan_str(char **res) {
    char *buf = calloc(MAX_SCAN_BUFFER, sizeof(char)), *buf_scan_res;
    if (buf == NULL) { return -1; }
    buf_scan_res = fgets(buf, MAX_SCAN_BUFFER, stdin);
    if (buf_scan_res == NULL)  { free(buf); return -1; }

    int buf_len = strlen(buf);
    *res = calloc(strlen(buf), sizeof(char));
    if (*res == NULL) { free(buf); return -1; }

    strncpy(*res, buf, buf_len - 1);
//
//    char *t = buf;
//    while (*t != '\0') {
//        printf("char is %c -- %d\n", *t, *t);
//        ++t;
//    }
    free(buf);
    return 0;
}

int scan_int(int *res) {
    int temp_res = 0;
    char *s;
    if (scan_str(&s) == -1) { return -1; }

    int s_len = strlen(s);
    if (s_len == 0) { free(s); return -1; }
    for (int i = 0; i < s_len; ++i) {
        if (s[i] > '9' || s[i] < '0') { free(s); return -1; }
        temp_res = temp_res * 10 + s[i] - '0';
    }

    free(s);
    *res = temp_res;
    return 0;
}
