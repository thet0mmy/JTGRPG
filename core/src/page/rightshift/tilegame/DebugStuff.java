package page.rightshift.tilegame;

public abstract class DebugStuff {
    public static boolean debugEnabled = false;

    public static void enableDebug() {
        debugEnabled = true;
    }

    public static void debug_print(String s) {
        System.out.println("DEBUG ::: " + s);
    }
}
