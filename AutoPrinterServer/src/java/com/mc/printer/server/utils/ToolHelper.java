package com.mc.printer.server.utils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.codehaus.plexus.util.Base64;

public class ToolHelper {

    /**
     * BASE64解密
     */
    public static byte[] decryptBASE64(byte[] dest) {
        if (dest == null) {
            return null;
        }
        return Base64.decodeBase64(dest);
    }

    /**
     * BASE64加密
     */
    public static byte[] encryptBASE64(byte[] origin) {
        if (origin == null) {
            return null;
        }
        return Base64.encodeBase64(origin);
    }

    public static String dateToString(Date date, String format) {
        try {
            if (date != null) {
                return new SimpleDateFormat(format).format(date);
            }
        } catch (Exception localException) {
        }
        return "";
    }

    public static Object copyBean(Object targetBean, Object srcBean) {
        if ((targetBean != null) && (srcBean != null)) {
            Method[] methods = targetBean.getClass().getMethods();
            Method[] srcMethods = srcBean.getClass().getMethods();
            for (Method method : methods) {
                String name = method.getName();
                for (Method srcMethod : srcMethods) {
                    name.equals(srcMethod.getName());
                }
            }

        }

        return targetBean;
    }
}
