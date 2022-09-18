import algs.MatrixClassicMultiplier;
import algs.MatrixMultiplier;
import algs.MatrixVinogradMultiplier;
import algs.MatrixVinogradOptimisedMultiplier;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0 || args.length > 3) {
            System.out.println("Некорректные аргументы" +
                    "(u - пользовательский ввод, " +
                    "t - тесты по времени, " +
                    "n - unit тесты)");
            return;
        }
        if (Objects.equals(args[0], "t")) {
            if (args.length != 3) {
                System.out.println("Некорректные аргументы" +
                        "после t нужно написать количество повторов для аппроксимации" +
                        "и тип алгоритма - c/v/v+");
                return;
            }

            try {
                timeTest(Integer.parseInt(args[1]), args[2]);
            } catch (NumberFormatException e) {
                System.out.println("Некорректные аргументы" +
                        "после t нужно написать целое ЧИСЛО повторов");
            }
        } else if (Objects.equals(args[0], "n")) {
            unitTest();
        } else if (Objects.equals(args[0], "u")) {
            userTest();
        }
    }

    private static void timeTest(int repeats, String type) {
        if (!Objects.equals(type, "c") && !Objects.equals(type, "v") && !Objects.equals(type, "v+")) {
            System.out.println("Некорректный аргумент типа алгоритма (c/v/v+)");
            return;
        }

        // Просто чтобы забить память :)
        int[][] m1 = new int[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        int[][] m2 = new int[][]{{4, 8, -9, 5}, {-8, 7, 9, 0}, {7, 9, -1, 1}, {8, 5, 2, 8}};
        for (int i = 0; i < 3000000; i++) {
            new MatrixClassicMultiplier().multiply(m1, m2);
        }

        MatrixAlgTester tester;
        String name;

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        {
            System.out.println("Количество строк матрицы 1:");
            int m1rows = scanner.nextInt();
            System.out.println("Количество столбцов матрицы 1:");
            int m1cols = scanner.nextInt();

            m1 = new int[m1rows][m1cols];
            for (int i = 0; i < m1rows; i++) {
                for (int k = 0; k < m1cols; k++) {
                    m1[i][k] = random.nextInt() % 100;
                }
            }
        }

        {
            System.out.println("Количество строк матрицы 2:");
            int m2rows = scanner.nextInt();
            System.out.println("Количество столбцов матрицы 2:");
            int m2cols = scanner.nextInt();

            m2 = new int[m2rows][m2cols];
            for (int i = 0; i < m2rows; i++) {
                for (int k = 0; k < m2cols; k++) {
                    m2[i][k] = random.nextInt() % 100;
                }
            }
        }

        switch (type) {
            case "c" -> {
                tester = new MatrixAlgTester(new MatrixClassicMultiplier(), m1, m2, repeats);
                name = "Классический алгоритм:";
            }
            case "v" -> {
                tester = new MatrixAlgTester(new MatrixVinogradMultiplier(), m1, m2, repeats);
                name = "Алгоритм Винограда (обычный)";
            }
            case "v+" -> {
                tester = new MatrixAlgTester(new MatrixVinogradOptimisedMultiplier(), m1, m2, repeats);
                name = "Алгоритм Винограда (оптимизированный)";
            }
            default -> {
                return;
            }
        }

        tester.test(name);
    }

    private static void userTest() {
        int[][] m1 = null;
        int[][] m2 = null;

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первую матрицу");
        {
            System.out.println("Количество строк: ");
            int m1rows = scanner.nextInt();

            System.out.println("Количество столбцов: ");
            int m1cols = scanner.nextInt();

            m1 = new int[m1rows][m1cols];

            for (int i = 0; i < m1rows; i++) {
                System.out.println("Введите числа из строки №" + i);
                for (int j = 0; j < m1cols; j++) {
                    m1[i][j] = scanner.nextInt();
                }
            }
        }

        System.out.println("Введите вторую матрицу");
        {
            System.out.println("Количество строк: ");
            int m2rows = scanner.nextInt();

            System.out.println("Количество столбцов: ");
            int m2cols = scanner.nextInt();

            m2 = new int[m2rows][m2cols];

            for (int i = 0; i < m2rows; i++) {
                System.out.println("Введите числа из строки №" + i);
                for (int j = 0; j < m2cols; j++) {
                    m2[i][j] = scanner.nextInt();
                }
            }
        }

        MatrixMultiplier alg = new MatrixClassicMultiplier();
        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));
        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));
        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));
    }

    public static void unitTest() {
        int[][] m1 = new int[][]{{1, 5, 2}, {1, 2, 8}, {1, 3, 2}};
        int[][] m2 = new int[][]{{1, 4, 9}, {8, 8, 8}, {12, 21, 13}};
        MatrixMultiplier alg;

        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{9, 8, 7}, {6, 5, 4}};
        m2 = new int[][]{{3}, {2}, {1}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{12}};
        m2 = new int[][]{{17}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{-1, -2, 3}, {8, -9, 7}, {-4, -7, 5}};
        m2 = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, -9}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{8, 7}};
        m2 = new int[][]{{4, 2}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{1, -1, 2}, {-4, 7, -5}};
        m2 = new int[][]{{1, 8, 3}, {6, -9, 7}, {1, 4, 8}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        m2 = new int[][]{{1, 8, 6}, {3, 5, -4}, {6, 6, 6}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{1, 4}, {8, -3}};
        m2 = new int[][]{{0, 0}, {0, 0}};
        alg = new MatrixClassicMultiplier();
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{1, 0, 0, 0}, {0, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 0, 1}};
        m2 = new int[][]{{4, 8, -9, 5}, {-8, 7, 9, 0}, {7, 9, -1, 1}, {8, 5, 2, 8}};
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        alg = new MatrixClassicMultiplier();
        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        m1 = new int[][]{{7, 1, 3}, {-1, -1, -1}, {3, 5, 2}};
        m2 = new int[][]{{1, 0, 0}, {0, 1, 0}, {0, 0, 1}};
        System.out.println("__________________________________________");
        System.out.println("I матрица: " + Arrays.deepToString(m1));
        System.out.println("II матрица: " + Arrays.deepToString(m2));

        alg = new MatrixClassicMultiplier();
        System.out.println("Классический алгоритм");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradMultiplier();
        System.out.println("Алгоритм Винограда");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));

        alg = new MatrixVinogradOptimisedMultiplier();
        System.out.println("Алгоритм Винограда (оптимизированный)");
        System.out.println("\tОтвет: " + Arrays.deepToString(alg.multiply(m1, m2)));
    }
}
