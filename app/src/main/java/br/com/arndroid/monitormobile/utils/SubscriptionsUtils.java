package br.com.arndroid.monitormobile.utils;

public class SubscriptionsUtils {

    // Utility
    protected SubscriptionsUtils() {}

    // Mind the codes: they are stored in DB. Don't change it!
    public static final int MODE_TYPE_DONT_SUBSCRIBE = 0;
    public static final int MODE_TYPE_SUBSCRIBE = 1;
    public static final int MODE_TYPE_SUBSCRIBE_AND_FOLLOW = 2;

    public static String modeDescriptionFromModeType(int modeType) {
        switch (modeType) {
            case MODE_TYPE_DONT_SUBSCRIBE:
                return "Não assino";
            case MODE_TYPE_SUBSCRIBE:
                return "Assino";
            case MODE_TYPE_SUBSCRIBE_AND_FOLLOW:
                return "Assino e sigo novos incidentes automaticamente";
            default:
                throw new IllegalArgumentException("Invalid modeType=" + modeType);
        }
    }
}
