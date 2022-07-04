package de.michm.vin.lib;

import java.awt.*;

public class Mouse {
    final private static int CLICK_DURATION = 300;
    final public static long LEFT_DOWN = 0x0002;
    final public static long LEFT_UP = 0x0004;
    final public static long RIGHT_DOWN = 0x0008;
    final public static long RIGHT_UP = 0x0010;
    final public static long MIDDLE_DOWN = 0x0020;
    final public static long MIDDLE_UP = 0x0040;
    private MouseWinProc proc;

    static {
        System.loadLibrary("vinlib");
    }

    /**
     * Moves the mouse cursor relative to the
     * current position along x and y-axis.
     * @param x long value of x-axis offset
     * @param y long value of y-axis offset
     */
    protected native void move(long x, long y);

    /**
     * Moves the mouse cursor absolute to the
     * screen size along x and y-axis. Interpolates
     * the values between start- and endpoint.
     * @param x long value of x-axis position
     * @param y long value of y-axis position
     * @param speed float value of movement speed
     */
    protected void move(long x, long y, float speed) {
        moveTo(x, y, speed);
    }

    /**
     * Moves the mouse cursor absolute to the
     * screen size along x and y-axis.
     * @param x long value of x-axis position
     * @param y long value of y-axis position
     */
    protected void moveABS(long x, long y) {
        final long MAX_SIZE = 0xFFFF;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        double factorX = x / width;
        double factorY = y / height;

        x = Math.round(MAX_SIZE * factorX);
        y = Math.round(MAX_SIZE * factorY);
        moveAbs(x, y);
    }

    protected void moveABS(long x, long y, long speed) {
        final long MAX_SIZE = 0xFFFF;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        long pos[] = new long[2];
        long finalX = x;
        long finalY = y;

        while (pos[0] != x && pos[1] != y) {
            getCursorPos((long xPos, long yPos, long button) ->{
                try {
                    if (xPos > finalX) {
                        pos[0] = xPos + 1;
                    } else if (xPos < finalX) {
                        pos[0] = xPos - 1;
                    }

                    if (yPos < finalY) {
                        pos[1] = yPos + 1;
                    } else if (yPos > finalY) {
                        pos[1] = yPos - 1;
                    }

                    double factorX = x / width;
                    double factorY = y / height;

                    moveAbs(Math.round(MAX_SIZE * factorX), Math.round(MAX_SIZE * factorY));

                    Thread.sleep(speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    /**
     * Moves the mouse cursor absolute to the
     * screen size along x and y-axis.
     * @param x long value of x-axis position
     * @param y long value of y-axis position
     */
    private native void moveAbs(long x, long y);

    /**
     * Moves the mouse cursor absolute to the
     * screen size along x and y-axis. Interpolates
     * the values between start- and endpoint.
     * @param x long value of x-axis position
     * @param y long value of y-axis position
     * @param speed float value of movement speed
     */
    protected native void moveTo(long x, long y, float speed);

    /**
     * Sends left click event to the operating system.
     */
    protected void click() {
        click(LEFT_DOWN);
    }

    /**
     * Sends click event to the operating system.
     * @param button one of following final long values:
     *               LEFT_DOWN, LEFT_UP,
     *               RIGHT_DOWN, RIGHT_UP,
     *               MIDDLE_DOWN, MIDDLE_UP
     */
    protected void click(long button) {
        try {
            nativeClick(button);
            Thread.sleep(CLICK_DURATION);
            switch ((int) button) {
                case (int) LEFT_DOWN -> nativeClick(LEFT_UP);
                case (int) RIGHT_DOWN -> nativeClick(RIGHT_UP);
                case (int) MIDDLE_DOWN -> nativeClick(MIDDLE_UP);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    /**
     * Sends click event to the operating system.
     * @param button one of following final long values:
     *               LEFT_DOWN, LEFT_UP,
     *               RIGHT_DOWN, RIGHT_UP,
     *               MIDDLE_DOWN, MIDDLE_UP
     */
    private native void nativeClick(long button);

    protected void getCursorPos(MouseWinProc proc) {
        this.proc = proc;
        getCursorPos();
    }

    private native void getCursorPos();

    /**
     * Creates a low level mouse hook. The callback will
     * be called if a message was received.
     * @param proc
     */
    protected void hook(MouseWinProc proc) {
        this.proc = proc;
        hook();
    }

    /**
     * Creates a low level mouse hook.
     */
    private native void hook();

    /**
     * Unhooks callback from the low level mouse hook.
     */
    protected native void unhook();

    /**
     * Is called from native context.
     * @param x
     * @param y
     * @param button
     */
    protected void event(long x, long y, long button) {
        proc.callback(x, y, button);
    }
}