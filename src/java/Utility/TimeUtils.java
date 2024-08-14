package Utility;

public class TimeUtils {
    public static String convertSecondsToHHMMSS(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }
}
