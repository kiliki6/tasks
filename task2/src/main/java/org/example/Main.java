package org.example;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Использование: java Main <circle_file> <points_file>");
            return;
        }

        String circleFile = args[0];
        String pointsFile = args[1];

        try {
            Circle circle = readCircleData(circleFile);
            Point[] points = readPointsData(pointsFile);

            for (Point point : points) {
                int position = pointPosition(circle, point);
                System.out.println(position);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private static Circle readCircleData(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String[] circleData = br.readLine().split(" ");
            double centerX = Double.parseDouble(circleData[0]);
            double centerY = Double.parseDouble(circleData[1]);
            double radius = Double.parseDouble(br.readLine().trim());
            return new Circle(centerX, centerY, radius);
        }
    }

    private static Point[] readPointsData(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            return br.lines()
                    .map(line -> {
                        String[] coords = line.split(" ");
                        return new Point(Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
                    })
                    .toArray(Point[]::new);
        }
    }

    private static int pointPosition(Circle circle, Point point) {
        double dx = point.x - circle.centerX;
        double dy = point.y - circle.centerY;
        double distanceSquared = dx * dx + dy * dy;
        double radiusSquared = circle.radius * circle.radius;

        if (distanceSquared < radiusSquared) {
            return 1; // Точка внутри окружности
        } else if (distanceSquared == radiusSquared) {
            return 0; // Точка на окружности
        } else {
            return 2; // Точка снаружи окружности
        }
    }

    static class Circle {
        double centerX;
        double centerY;
        double radius;

        Circle(double centerX, double centerY, double radius) {
            this.centerX = centerX;
            this.centerY = centerY;
            this.radius = radius;
        }
    }

    static class Point {
        double x;
        double y;

        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }
}