#include <check.h>

#include "btree/btree_tests_package.h"
#include "gin/gin_tests_package.h"
#include "table/table_tests_package.h"
#include "mixed/mixed_tests_package.h"

void run_suite(Suite *s, int *err_cnt) {
    SRunner *runner;
    runner = srunner_create(s);
    srunner_run_all(runner, CK_VERBOSE);

    *err_cnt += srunner_ntests_failed(runner);
    srunner_free(runner);
}

int main(void) {
    int fail_cnt = 0;

    run_suite(btree_data_0_suite(), &fail_cnt);
    run_suite(btree_data_1_suite(), &fail_cnt);

    run_suite(gin_data_0_suite(), &fail_cnt);

    run_suite(table_data_0_suite(), &fail_cnt);

    run_suite(mixed_data_0_suite(), &fail_cnt);

    return (fail_cnt == 0) ? 0 : 1;
}
