package brcomkassin.dungeonsClass.utils;

import java.text.DecimalFormat;

public class DecimalFormatUtil {

    private static final DecimalFormat df = new DecimalFormat("0.0");

    public static String format(double value) {
        return df.format(value);
    }

}
