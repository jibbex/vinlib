package de.michm.vin.lib;

public class Mouse {
    final public static int LEFT_BUTTON = 0;
    final public static int RIGHT_BUTTON = 1;
    final public static int MIDDLE_BUTTON = 2;

    protected native void move(long x, long y);
    protected native void click(int button);

    static {
        System.loadLibrary("vinlib");
    }
}
