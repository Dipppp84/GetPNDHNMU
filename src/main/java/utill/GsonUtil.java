package utill;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class GsonUtil {
    private static volatile GsonUtil gsonUtil;
    private static volatile GsonBuilder builder;
    private static volatile Gson gson;

    private GsonUtil() {
    }

    public static GsonUtil getToGson() {
        synchronized (GsonUtil.class) {
            if (gsonUtil == null) {
                gsonUtil = new GsonUtil();
                builder = new GsonBuilder();
                //builder.excludeFieldsWithoutExposeAnnotation();
                gson = builder.create();
            }
            return gsonUtil;
        }
    }

    public String getGson(Object o) {
        if (o == null) {
            return null;
        }
        return gson.toJson(o);
    }

    public Object getObjectByGson(String s, Class clazz) {
        if (s == null || s.isEmpty()) {
             return null;
        }
        return gson.fromJson(s, clazz);
    }
}
