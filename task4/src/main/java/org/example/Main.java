package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        String inputFile = "num.txt";
        int[] nums = readNumbersFromFile(inputFile);

        if (nums.length == 0) {
            System.out.println("Ошибка: файл пуст или не содержит чисел.");
            return;
        }

        Arrays.sort(nums);
        int median = nums[nums.length / 2]; // Находим медиану
        int moves = calculateMoves(nums, median);

        System.out.println("Минимальное количество ходов: " + moves);
    }

    private static int[] readNumbersFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return new int[0];
        }
    }

    private static int calculateMoves(int[] nums, int target) {
        int moves = 0;
        for (int num : nums) {
            moves += Math.abs(num - target);
        }
        return moves;
    }
}