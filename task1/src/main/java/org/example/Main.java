package org.example;
import java.util.Scanner;
import java.util.Arrays;

public class Main { // Изменено на Main
    public static void main(String[] args) { // Изменено на main
        Scanner sc = new Scanner(System.in);
        System.out.print("Введите количество элементов в массиве: ");
        int n = sc.nextInt();
        System.out.print("Введите длину интервала: ");
        int m = sc.nextInt();
        sc.close();

        if (n <= 0 || m <= 0) {
            System.out.println("Ошибка: количество элементов и длина интервала должны быть больше 0.");
            return;
        }

        int[] arr = new int[n];
        Arrays.setAll(arr, i -> i + 1);

        int current = 0;
        System.out.print("Интервалы и путь:\n");

        StringBuilder path = new StringBuilder();

        do {
            System.out.print("Интервал: [ ");
            for (int i = 0; i < m; i++) {
                System.out.print(arr[(current + i) % n] + " ");
            }
            System.out.println("]");

            path.append(arr[current]);

            current = (current + m - 1) % n;

        } while (current != 0);

        System.out.println("Полученный путь: " + path);
    }
}