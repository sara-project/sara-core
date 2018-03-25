package org.sara.shell;

public class Utils {

    public static String maxUnit( double size ) {
        String[] units = {"B", "kB", "MB", "GB", "TB", "PB"};
        int unit = 0;

        while (size > 1024) {
            size = size / 1024;
            unit++;
        }
        return String.format( "%.2f %s", size, units[unit] );
    }
}
