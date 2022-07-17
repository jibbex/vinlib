package de.michm.vin.lib;

import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Test {
    public static void main(String[] args) throws IOException, InterruptedException {
        Thread thread = null;
        boolean run = true;
        boolean abs = false;
        boolean pos = false;
        boolean isIndicating = false;
        final NbBufferedReader reader = new NbBufferedReader(System.in);
        AtomicReference<Point> pt = new AtomicReference<>(new Point(-1, -1));
        BlockingDeque<String> outBuffer = new LinkedBlockingDeque<>();

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

                if (input.equals("q") || input.equals("quit")) {
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
                } else if (input.equals("help")) {
                    printHelp();
                }
            }

            if (input != null && input.isEmpty() && pos) {
                pos = false;
            }

            if (pos) {
                Point mousePoint = mouse.nativeGetCursorPos();
                boolean hasChanged = mousePoint.getX() != pt.get().getX() || mousePoint.getY() != pt.get().getY();

                if (hasChanged) {
                    System.out.println(String.format("x: %s, y: %s", mousePoint.getX(), mousePoint.getY()));
                    isIndicating = false;
                    //pt.set(mousePoint);
                    //outBuffer.add(String.format("x: %s, y: %s\n", mousePoint.getX(), mousePoint.getY()));
                }
                /*mouse.getCursorPos((long x, long y, long button) -> {
                    try {
                        boolean hasChanged = x != pt.get().getX() || y != pt.get().getY();

                        if (hasChanged) {
                            pt.set(new Point(x, y));
                            outBuffer.add(String.format("x: %s, y: %s\n", x, y));
                        }

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });*/
            }

            //if (!outBuffer.isEmpty()) {
             //   String out = outBuffer.poll(250L, TimeUnit.NANOSECONDS);
             //   System.out.print(out);
             //   isIndicating = false;
            //}
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
        System.out.println("Enter pos to receive system mouse cursor position.");
        System.out.println("Hit enter to unhook.\n");
        System.out.println("Enter quit or hit \"q\" to quit.\n");
    }
}
