package dk.sdu.se4.common.data;

public class GameKeys {

    private static boolean[] keys;
    private static boolean[] previousKeys;

    private static final int NUM_KEYS = 4;

    public static final int UP = 0;
    public static final int LEFT = 1;
    public static final int RIGHT = 2;
    public static final int SPACE = 3;

    public GameKeys() {
        keys = new boolean[NUM_KEYS];
        previousKeys = new boolean[NUM_KEYS];
    }

    public void update() {
        for (int i = 0; i < NUM_KEYS; i++) {
            previousKeys[i] = keys[i];
        }
    }

    public void setKey(int key, boolean value) {
        keys[key] = value;
    }

    public boolean isDown(int key) {
        return keys[key];
    }

    public boolean isPressed(int key) {
        return keys[key] && !previousKeys[key];
    }
}