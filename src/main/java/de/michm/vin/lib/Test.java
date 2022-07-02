package de.michm.vin.lib;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        int[] stops = { 0, 0, 0, 0};
        int iterations = 0;
        boolean run = true;
        boolean abs = false;
        Scanner scanner = new Scanner(System.in);
        Mouse mouse = new Mouse();

        printHelp();

        while (run) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                run = false;
            } else if (!abs && input.matches("^-?\\d+, ?-?\\d+$")) {
                Point point = new Point(input);
                mouse.move(point.getX(), point.getY());
            } else if (abs && input.matches("^-?\\d+, ?-?\\d+$")) {
                Point point = new Point(input);
                mouse.moveTo(point.getX(), point.getY());
            } else if (input.matches("^click .+$") || input.equalsIgnoreCase("click")) {
                String btnIn = input.substring(input.indexOf(' ') + 1);

                if (btnIn.equalsIgnoreCase("left") || btnIn.equalsIgnoreCase("click")) {
                    mouse.click();
                } else if (btnIn.equalsIgnoreCase("right")) {
                    mouse.click(Mouse.RIGHT_DOWN);
                } else if (btnIn.equalsIgnoreCase("middle")) {
                    mouse.click(Mouse.MIDDLE_DOWN);
                }
            } else if (input.toLowerCase().startsWith("abs ")) {
                abs = input.toLowerCase().endsWith(" true");
            } else if (input.equalsIgnoreCase("abs")) {
                System.out.printf("abs: %s\n", abs);
            } else if (input.matches("^circle .+$")) {
                String iterIn = input.substring(input.indexOf(' ') + 1);
                iterations = Integer.parseInt(iterIn);

                stops = new int[]{
                        iterations,
                        iterations / 2,
                        iterations / 3,
                        iterations / 4,
                };

            }

            if (iterations > 0) {
                if (iterations >= stops[0]) {
                    mouse.move(-5, -5);
                } else if (iterations >= stops[1]) {
                    mouse.move(5, -5);
                } else if (iterations >= stops[2]) {
                    mouse.move(-5, 5);
                } else {
                    mouse.move(-5, -5);
                }
                iterations--;
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        scanner.close();
    }

    private static void printHelp() {
        System.out.println("\nEnter coordinates to which the cursor should move. [x, y]");
        System.out.println("e.g.: 120, 80\n");
        System.out.println("Enter click <left|right|middle> to click with the");
        System.out.println("specified mouse button.\n");
        System.out.println("Enter abs <true|false> to toggle move mode");
        System.out.println("between absolute to screen size and relative to position.\n");
        System.out.println("Hit \"q\" to quit.\n");
    }

    private static class Point {
        private final long x;
        private final long y;

        Point(String cords) {
            cords = cords.replace(", ", ",");

            int separatorIndex = cords.indexOf(',');
            String pointX = cords.substring(0, separatorIndex);
            String pointY = cords.substring(separatorIndex + 1);

            x = Long.parseLong(pointX);
            y = Long.parseLong(pointY);
        }

        public long getX() {
            return x;
        }
        public long getY() {
            return y;
        }
    }
}
