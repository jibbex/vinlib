package de.michm.vin.lib;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws IOException {
        boolean run = true;
        boolean abs = false;
        boolean pos = false;
        boolean isIndicating = false;
        final NbBufferedReader reader = new NbBufferedReader(System.in);
        Point pt = new Point(-1, -1);

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
                } else if (abs && input.matches("^-?\\d+, ?-?\\d+$")) {
                    Point point = new Point(input);
                    mouse.moveAbs(point.getX(), point.getY());
                } else if (input.matches("^click .+$") || input.equals("click")) {
                    String btnIn = input.substring(input.indexOf(' ') + 1);

                    if (btnIn.equals("left") || btnIn.equals("click")) {
                        mouse.click();
                    } else if (btnIn.equals("right")) {
                        mouse.click(Mouse.RIGHT_DOWN);
                    } else if (btnIn.equals("middle")) {
                        mouse.click(Mouse.MIDDLE_DOWN);
                    }
                } else if (input.startsWith("abs ")) {
                    abs = input.endsWith(" true");
                } else if (input.equals("abs")) {
                    System.out.printf("> abs: %s\n", abs);
                } else if (input.equals("pos") || input.equals("p")) {
                    pos = !pos;
                } else if (input.equals("help")) {
                    printHelp();
                }
            }

            if (input != null && input.isEmpty() && pos) {
                pos = false;
            } else if (pos) {
                Point mousePoint = mouse.nativeGetCursorPos();
                boolean hasChanged = mousePoint.getX() != pt.getX() || mousePoint.getY() != pt.getY();

                if (hasChanged) {
                    System.out.println(String.format("x: %s, y: %s", mousePoint.getX(), mousePoint.getY()));
                    isIndicating = false;
                    pt = mousePoint.clone();
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
        System.out.println("Enter pos to receive system mouse cursor position.");
        System.out.println("Hit enter to unhook.\n");
        System.out.println("Enter quit or hit \"q\" to quit.\n");
    }
}


