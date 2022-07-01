package de.michm.vin.lib;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean run = true;
        Scanner scanner = new Scanner(System.in);
        Mouse mouse = new Mouse();

        printHelp();

        while (run) {
            System.out.print(" > ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("q")) {
                run = false;
            } else if (input.matches("^-?\\d+, ?-?\\d+$")) {
                Point point = new Point(input);
                mouse.move(point.getX(), point.getY());
            } else if (input.matches("^click .+$")) {
                int button = -1;
                String btnIn = input.substring(input.indexOf(' ') + 1);

                if (btnIn.equalsIgnoreCase("left")) {
                    button = Mouse.LEFT_BUTTON;
                } else if (btnIn.equalsIgnoreCase("right")) {
                    button = Mouse.RIGHT_BUTTON;
                } else if (btnIn.equalsIgnoreCase("middle")) {
                    button = Mouse.MIDDLE_BUTTON;
                }

                if (button >= 0) {
                    mouse.click(button);
                }
            }
        }

        scanner.close();
    }

    private static void printHelp() {
        System.out.println("Enter coordinates to which the cursor should move. [x, y]");
        System.out.println("\te.g.: 120, 80\n");
        System.out.println("Enter click <left|right|middle> to click with the");
        System.out.println("specified mouse button.\n");
        System.out.println("Hit \"q\" to quit.\n");
    }

    private static class Point {
        private final long x;
        private final long y;

        Point(String coord) {
            coord = coord.replace(", ", ",");

            int separatorIndex = coord.indexOf(',');
            String pointX = coord.substring(0, separatorIndex);
            String pointY = coord.substring(separatorIndex + 1);

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
