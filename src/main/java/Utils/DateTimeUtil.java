package Utils;


import org.apache.commons.lang3.StringUtils;

/**
 * Created by Yangyang Deng on 17-7-31.
 */
public class DateTimeUtil {

    private DateTimeUtil() {
        throw new IllegalAccessError("Utility class");
    }

    public static void main(String[] args) {
        // test1
        String s1 = "2012.1";
        dsp(transferDateTime(s1));

        // test2
        String s2 = "2012.01.05";
        dsp(transferDateTime(s2));

        // test3
        String s3 = "20120220";
        dsp(transferDateTime(s3));

        // test4
        String s4 = "2012.02";
        dsp(transferDateTime(s4));

        // test5
        String s5 = "2012年 3月";
        dsp(transferDateTime(s5));

        // test6
        String s6 = "2012/1/12 ";
        dsp(transferDateTime(s6));

        // test7
        String s7 = "2016年";
        dsp(transferDateTime(s7));

        // test8
        String s8 = "2016";
        dsp(transferDateTime(s8));

    }

    public static void dsp(Object o) {
        System.out.println(o);
    }

    public static String transferDateTime(String dt) {
        // 使用try的方式，防止有未考虑到的情况；
        try {
            String normalizeDt = transfer(dt);
            return  normalizeDt;
        }
        catch (Exception e) {
            return dt;
        }
    }

    private static String transfer(String dt) {
        // 第一步，把指定字符替换为"."，其中"日"在最尾，只用删除。
        dt = dt.replace(" ","");
        String originDt = dt;
        dt = dt.trim();
        dt = dt.replace("年",".").replace("月",".").replace("日","");
        dt = dt.replace("\\",".").replace("/",".").replace("-",".");
        String dateTime = dt;

        // 第二步，把日期切分，并检查每个元素是否都为数字，若不满足，则返回；
        String[] dateTimeList = dateTime.split("\\.");
        boolean flag = CheckValid(dateTimeList);
        if (!flag) {
            return originDt;
        }

        // 第三步，找到对应的年月，并返回规整好的结果
        String result = DateTimeNormalize(originDt,dateTimeList);
        if(StringUtils.endsWith(result,"年")){
            result = StringUtils.replace(result,"年","");
        }
        return result;
    }

    private static boolean CheckValid(String[] dateTimeList) {
        for (String element:dateTimeList) {
            for (int i=0;i<element.length();i++) {
                if (!Character.isDigit(element.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String DateTimeNormalize(String dt, String[] dateTimeList) {
        int dateTimeSplitLen = dateTimeList.length;
        int year,month;
        if (dateTimeSplitLen<1) {
            return dt;
        }
        // 如果数组的长度为1，则根据字符串的长度判断格式；
        else if (dateTimeSplitLen==1) {
            if(dateTimeList[0].length()<5 || dateTimeList[0].length()>8) {
                return dt;
            }
            else if (dateTimeList[0].length()==5) {
                year = Integer.parseInt(dt.substring(0,4));
                month = Integer.parseInt(dt.substring(4));
                dt = ConcatYearAndMonth(year,month);
            }
            else if (dateTimeList[0].length()==6) {
                year = Integer.parseInt(dt.substring(0,4));
                month = Integer.parseInt(dt.substring(4));
                if (month>12) {
                    month = Integer.parseInt(dt.substring(4,5));
                }
                dt = ConcatYearAndMonth(year,month);
            }
            else if (dateTimeList[0].length()==7) {
                year = Integer.parseInt(dt.substring(0,4));
                month = Integer.parseInt(dt.substring(4,6));
                if (month>12) {
                    month = Integer.parseInt(dt.substring(4,5));
                }
                dt = ConcatYearAndMonth(year,month);
            }
            else if (dateTimeList[0].length()==8) {
                year = Integer.parseInt(dt.substring(0,4));
                month = Integer.parseInt(dt.substring(4,6));
                dt = ConcatYearAndMonth(year,month);
            }
        }
        else if (dateTimeSplitLen>3) {
            return dt;
        }
        else  {
            year = Integer.parseInt(dateTimeList[0]);
            month = Integer.parseInt(dateTimeList[1]);
            dt = ConcatYearAndMonth(year,month);
        }
        return dt;
    }

    private static String  ConcatYearAndMonth(int year, int month) {
        String strMonth;
        String strYear;
        strYear = Integer.toString(year);
        if (month<10) {
            strMonth = "0"+Integer.toString(month);
        }
        else {
            strMonth = Integer.toString(month);
        }
        return strYear+"."+strMonth;
    }

}

