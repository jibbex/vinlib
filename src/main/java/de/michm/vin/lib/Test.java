package de.michm.vin.lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) throws  IOException {
        Thread thread = null;
        boolean run = true;
        boolean abs = false;
        boolean pos = false;
        final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        final NbBufferedReader reader = new NbBufferedReader(stdin);


        String input = null;
        Mouse mouse = new Mouse();

        printHelp();

        while (run) {
            input = reader.readLine();

            if (input != null) {
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

                    if (pos) {
                        thread = new Thread(() -> {
                            while (true) {
                                mouse.getCursorPos((long x, long y, long button) -> System.out.printf("x: %s, y: %s, button: %s\n", x, y, button));
                                try {
                                    Thread.sleep(500);
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                        });

                        thread.setDaemon(true);
                        thread.start();
                    } else if (thread != null) {
                        thread.interrupt();
                        thread = null;
                    }
                }
            }
        }

        reader.close();
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

        Point(long x, long y) {
            this.x = x;
            this.y = y;
        }

        public long getX() {
            return x;
        }
        public long getY() {
            return y;
        }
    }
}
