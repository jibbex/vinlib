package de.michm.vin.lib;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
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
            } else if (input.matches("^click .+$")) {
                String btnIn = input.substring(input.indexOf(' ') + 1);

                if (btnIn.equalsIgnoreCase("left")) {
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
