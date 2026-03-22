package top.huajieyu001.holder;

/**
 * @Author huajieyu
 * @Date 2026/3/22 18:09
 * @Version 1.0
 * @Description TODO
 */
public class DatasourceContextHolder {

    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();

    public static void setDatasourceType(String context) {
        CONTEXT_HOLDER.set(context);
    }

    public static String getDatasourceType() {
        return CONTEXT_HOLDER.get();
    }

    public static void clearDatasourceType() {
        CONTEXT_HOLDER.remove();
    }
}
