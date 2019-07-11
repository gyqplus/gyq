package com.dw.health.eportal.util;


import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * Created by gyq on 2019-3-4.
 */
public class EStringUtil {

    /**
     * 不超指定长度地去重+加串(串以特定字符分割)
     *
     * @param strBase
     * @param strAdd
     * @param lenthLimit
     * @return
     * @author gyq 
     */
    public static String addStringByLengthLimit(String strBase, String strAdd, String separator, Integer lenthLimit) {
        if (StringUtils.isEmpty(separator) || lenthLimit == null || lenthLimit <= 0) return null;
        else if (StringUtils.isEmpty(strBase) && StringUtils.isEmpty(strAdd)) return null;
        else if (!StringUtils.isEmpty(strBase) && StringUtils.isEmpty(strAdd)) return strBase;
        else if (StringUtils.isEmpty(strBase) && !StringUtils.isEmpty(strAdd)) return strAdd;
        else return addStringByLengthLimitNotEmpty(strBase, strAdd, separator, lenthLimit);
    }

    /**
     * 不超指定长度地去重+加串(串以特定字符分割)
     * A:B:C + A   ==>B:C:A
     * a:b:c + d   ==>a:b:c:d
     *
     * @param strBase
     * @param strAdd
     * @param separator
     * @param lenthLimit
     * @return
     * @author gyq
     */
    private static String addStringByLengthLimitNotEmpty(String strBase, String strAdd, String separator, Integer lenthLimit) {

        if ((strBase + strAdd).length() >= lenthLimit) {
            String strBaseNew = StringUtils.substringAfter(strBase, ":");
            return addStringByLengthLimit(strBaseNew, strAdd, separator, lenthLimit);
        } else {
            return (strBase + separator).replace(strAdd + separator, "") + strAdd;
        }
    }

    public static String getUUID(){return null;}

    public static  String getNo(int seqNumber,Class <?> clazz){
        Date now = new Date();
        int day = now.getDate();
        int month = now.getMonth();
        int year = now.getYear();
        return null;
    }
    
    /**
     *
     * @ClassName: EStringUtil
     * @Description: 判断字符串中字符只包含数字 是 true
     * @author gyq 
     * @date 2019-3-6 15:30
     * @version V1.0 
     */
    public static boolean isFullNumberStr(String str){
        if (StringUtils.isEmpty(str)){return false;}
        return str.matches("[0-9]{1,}");
    }

    /**
     *
     * @ClassName: EStringUtil
     * @Description: 判断字符串中字符只包含英文 是 true
     * @author gyq 
     * @date 2019-3-6 15:32
     * @version V1.0 
     */
    public static boolean isFullEnglishStr(String str){
        if (StringUtils.isEmpty(str)){return false;}
        return str.matches("[a-zA-Z]");
    }

    /**
     *
     * @ClassName: EStringUtil
     * @Description: T判断字符串中字符只包含英文和数字 是 true
     * @author gyq 
     * @date 2019-3-6 15:33
     * @version V1.0 
     */
    public static boolean isFullEnglishAndNumberStr(String str){
        if (StringUtils.isEmpty(str)){return false;}
        return str.matches("[a-zA-Z0-9]");
    }
//
//    @Test
//    public void test() {
//        String Str = EStringUtil.addStringByLengthLimit("", "asdd", ":", 100);
//        System.out.print(Str);
//    }
}
