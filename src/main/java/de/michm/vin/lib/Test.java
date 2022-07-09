package de.michm.vin.lib;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class Test {
    public static void main(String[] args) throws  IOException {
        Thread thread = null;
        boolean run = true;
        boolean abs = false;
        boolean pos = false;
        boolean isIndicating = false;
        final NbBufferedReader reader = new NbBufferedReader(System.in);
        AtomicReference<Point> oldPt = new AtomicReference<>(new Point(-1,1));
        AtomicReference<Point> pt = new AtomicReference<>(new Point(-1, -1));

        String input = null;
        Mouse mouse = new Mouse();

        printHelp();

        while (run) {
            if (!isIndicating) {
                isIndicating = true;
                System.out.print("> ");
            }
            input = reader.readLine();

            if (input != null) {
                isIndicating = false;

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
                } else if (input.equals("pos") || input.equals("p")) {
                    pos = !pos;

                    if (pos) {
                        thread = new Thread(() -> {
                            while (true) {
                                mouse.getCursorPos((long x, long y, long button) -> {
                                    try {
                                        pt.set(new Point(x, y));
                                        Thread.sleep(250);
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            }
                        });

                        thread.setDaemon(true);
                        thread.start();
                    }
                } else if (input.equals("help")) {
                    printHelp();
                }
            }

            boolean hasChanged = oldPt.get().getX() != pt.get().getX() || oldPt.get().getY() != pt.get().getY();

            if (hasChanged && pt.get().getX() != -1 && pt.get().getY() != -1) {
                System.out.printf("x: %s, y: %s\n", pt.get().getX(), pt.get().getY());
                oldPt.set(pt.get());
            }

            if (thread != null && input != null && input.isEmpty()) {
                pos = false;
                thread.interrupt();
                thread = null;
                pt.set(new Point(-1,-1));
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
