package de.michm.vin.lib;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        boolean run = true;
        boolean abs = false;
        boolean pos = false;

        final Scanner scanner = new Scanner(System.in);
        String input = "";
        Mouse mouse = new Mouse();

        printHelp();

        while (run) {
            if (pos) {
                input = "";
                mouse.getCursorPos((long x, long y, long button) -> System.out.printf("x: %s, y: %s, button: %s\n", x, y, button));
                Thread.sleep(1000);
            } else {
                System.out.print("> ");
                input = scanner.nextLine();
            }

            if (input.equalsIgnoreCase("q")) {
                run = false;
            } else if (!abs && input.matches("^-?\\d+, ?-?\\d+$")) {
                Point point = new Point(input);
                mouse.move(point.getX(), point.getY());
                //mouse.moveTo(point.getX(), point.getY(), 1f);
            } else if (abs && input.matches("^-?\\d+, ?-?\\d+$")) {
                Point point = new Point(input);
                mouse.moveABS(point.getX(), point.getY());
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
            } else if (input.equals("pos")) {
                pos = !pos;
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
