package barsuift.simLife;


import java.util.Locale;

public final class Context {

    private Context() {
        // private constructor to enforce static access
    }

    private static Locale locale = Locale.getDefault();

    static {
        Percent.resetPercentFormat(locale);
    }

    public static Locale getLocale() {
        return locale;
    }

    public static void setLocale(Locale locale) {
        Context.locale = locale;
        Percent.resetPercentFormat(locale);
    }

}
