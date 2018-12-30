package support.util;

public class BetterCheckerUtil {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    public static boolean checkFirst(int firstBetter) {
        if (firstBetter == TRUE)
            return true;
        return false;
    }

    public static boolean checkLast(int firstBetter) {
        if (firstBetter == FALSE)
            return true;
        return false;
    }
}
