package utils;

public class log {
    private static boolean isShow = true;
    private static String TAG = "MINA_KING：";
    public static void e(Object string){
        if (isShow){
            System.out.println(TAG+string);
        }
    }
}
