package lightframework.data.util;

/**
 *
 * @author Tom Deng <tom.deng@duomi.com>
 */
public class StringExtension {

    public static boolean isNullOrEmpty(String s) {
        return (s == null || s.equals("") || s.trim().isEmpty());
    }

    public static <T> String join(T... values) {
        return join(",", values);
    }

    public static <T> String join(String separator, T... values) {
        if (values == null) {
            throw new NullPointerException("values");
        }
        if ((values.length == 0) || (values[0] == null)) {
            return "";
        }
        if (separator == null) {
            separator = "";
        }

        StringBuilder builder = new StringBuilder();
        builder.append(values[0]);
        for (int i = 1; i < values.length; i++) {
            builder.append(separator);
            if (values[i] != null) {
                builder.append(values[i]);
            }
        }

        return builder.toString();
    }
}
