package com.dongyang.util;

import java.util.GregorianCalendar;

/**
 *
 * <p>Title: ���ڹ���</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.14
 * @version 1.0
 */
public class DateTool
{
    private static GregorianCalendar calendar=new GregorianCalendar();
    /**
     * is leap year
     * @param year int
     * @return boolean
     */
    public static boolean isLeapYear(int year)
    {
        return calendar.isLeapYear(year);
    }
    /**
     * �õ����ڸ�ʽ��Ĭ�ϸ�ʽ
     * @param format String
     * @return String
     */
    public static String getDateDefaultFormat(String format)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        StringBuffer sb = new StringBuffer();
        int count = format.length();
        for(int i = 0;i < count;i ++)
        {
            char c = format.charAt(i);
            switch(c)
            {
            case 'y':
            case 'M':
            case 'd':
            case 'H':
            case 'm':
            case 's':
                sb.append('0');
                break;
            default:
                sb.append(c);
            }
        }
        return sb.toString();
    }
    /**
     * �����Ƿ��Ǳ��������һ���ַ�
     * @param format String
     * @param c char
     * @param index int
     * @return boolean
     */
    public static boolean isEndCharIndex(String format,char c,int index)
    {
        int x[] = getFormatPoint(c,format);
        if(x == null)
            return false;
        return index == x[1] - 1;
    }
    /**
     * ��������(�༭ר��)
     * @param date String
     * @param format String
     * @param c char
     * @param index int
     * @return boolean
     */
    public static boolean checkDate(String date,String format,char c,int index)
    {
        if(c == 'y')
            return checkYear(date,format,index) != -100;
        if(c == 'M')
            return checkMonth(date,format,index) != -100;
        if(c == 'd')
        {
            int year = checkYear(date,format,index);
            int month = checkMonth(date,format,index);
            return checkDay(date,year,month,format,index) != -100;
        }
        if(c == 'H')
            return checkHour(date,format) != -100;
        if(c == 'm')
            return checkMinute(date,format) != -100;
        if(c == 's')
            return checkSecond(date,format) != -100;
        return true;
    }
    /**
     * ������
     * @param date String
     * @param format String
     * @param index int
     * @return int
     */
    public static int checkYear(String date,String format,int index)
    {
        int x[] = getFormatPoint('y',format);
        if(x == null)
            return -1;
        int year = -1;
        try{
            year = Integer.parseInt(date.substring(x[0],x[1]));
        }catch(Exception e)
        {
            return -100;
        }
        if(year < 0)
            return -100;
        if((index == -1 || x[1] - 1 == index) && year == 0)
            return -100;
        return year;
    }
    /**
     * �����
     * @param date String
     * @param format String
     * @param index int
     * @return int
     */
    public static int checkMonth(String date,String format,int index)
    {
        int x[] = getFormatPoint('M',format);
        if(x == null)
            return -1;
        int month = -1;
        try{
            month = Integer.parseInt(date.substring(x[0],x[1]));
        }catch(Exception e)
        {
            return -100;
        }
        if(month < 0 || month > 12)
            return -100;
        if((index == -1 || x[1] - 1 == index) && month == 0)
            return -100;
        return month;
    }
    /**
     * �����·�
     * @param date String
     * @param year int
     * @param month int
     * @param format String
     * @param index int
     * @return int
     */
    public static int checkDay(String date,int year,int month,String format,int index)
    {
        int x[] = getFormatPoint('d',format);
        if(x == null)
            return -1;
        int day = -1;
        try{
            day = Integer.parseInt(date.substring(x[0],x[1]));
        }catch(Exception e)
        {
            return -100;
        }
        if(day < 0 || day > 31)
            return -100;
        if((index == -1 || x[1] - 1 == index) && day == 0)
            return -100;
        if(month <= 0)
            return day;

        if(month != 2)
        {
            if ((month < 8?(30 + month % 2):(31 - month % 2)) < day)
                return -100;
            return day;
        }

        if(year <= 0)
            return day;

        if(28 + (isLeapYear(year)?1:0) < day)
            return -100;

        return day;
    }
    /**
     * ����Сʱ
     * @param date String
     * @param format String
     * @return int
     */
    public static int checkHour(String date,String format)
    {
        int x[] = getFormatPoint('H',format);
        if(x == null)
            return -1;
        int hour = -1;
        try{
            hour = Integer.parseInt(date.substring(x[0],x[1]));
        }catch(Exception e)
        {
            return -100;
        }
        if(hour < 0 || hour > 23)
            return -100;
        return hour;
    }
    /**
     * ���Է���
     * @param date String
     * @param format String
     * @return int
     */
    public static int checkMinute(String date,String format)
    {
        int x[] = getFormatPoint('m',format);
        if(x == null)
            return -1;
        int minute = -1;
        try{
            minute = Integer.parseInt(date.substring(x[0],x[1]));
        }catch(Exception e)
        {
            return -100;
        }
        if(minute < 0 || minute > 59)
            return -100;
        return minute;
    }
    /**
     * ��������
     * @param date String
     * @param format String
     * @return int
     */
    public static int checkSecond(String date,String format)
    {
        int x[] = getFormatPoint('s',format);
        if(x == null)
            return -1;
        int second = -1;
        try{
            second = Integer.parseInt(date.substring(x[0],x[1]));
        }catch(Exception e)
        {
            return -100;
        }
        if(second < 0 || second > 59)
            return -100;
        return second;
    }
    /**
     * �����ַ�(�༭��)
     * @param date String
     * @param format String
     * @return String
     */
    public static String editString(String date,String format)
    {
        //������
        int year = checkYear(date,format,-1);
        if(year == -100)
        {
            int x[] = getFormatPoint('y',format);
            date = StringTool.editChar(date,x[1] - 1,'1');
            year = 1;
        }
        //������
        int month = checkMonth(date,format,-1);
        if(month == -100)
        {
            int x[] = getFormatPoint('M',format);
            date = StringTool.editChar(date,x[1] - 1,'1');
            month = 1;
        }
        //������
        int day = checkDay(date,year,month,format,-1);
        if(day == -100)
        {
            int x[] = getFormatPoint('d',format);
            date = StringTool.editChar(date,x[1] - 2,'0');
            date = StringTool.editChar(date,x[1] - 1,'1');
        }
        return date;
    }
    /**
     * �����ַ��������ڸ�ʽ�Ƿ�Ϸ�
     * @param date String
     * @param format String
     * @return boolean
     */
    public static boolean checkDate(String date,String format)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        //�ж����ڸ�ʽ�ַ��Ƿ���ȷ
        if(!checkFormatChar(date,format))
            return false;

        //������
        int year = checkYear(date,format,-1);
        if(year == -100)
            return false;

        //������
        int month = checkMonth(date,format,-1);
        if(month == -100)
            return false;

        //������
        int day = checkDay(date,year,month,format,-1);
        if(day == -100)
            return false;

        //����Сʱ
        int hour = checkHour(date,format);
        if(hour == -100)
            return false;

        //���Է���
        int minute = checkMinute(date,format);
        if(minute == -100)
            return false;

        //��������
        int second = checkSecond(date,format);
        if(second == -100)
            return false;
        return true;
    }
    /**
     * �ж����ڸ�ʽ�ַ��Ƿ���ȷ
     * @param date String
     * @param format String
     * @return boolean
     */
    public static boolean checkFormatChar(String date,String format)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        if(date == null || date.length() == 0)
            return false;
        if(date.length() != format.length())
            return false;
        int count = format.length();
        for(int i = 0;i < count;i++)
        {
            char c = format.charAt(i);
            if(isFormatChar(c))
                continue;
            if(c != date.charAt(i))
                return false;
        }
        return true;
    }
    /**
     * �Ƿ������ڸ�ʽ�ַ�
     * @param t char
     * @return boolean
     */
    public static boolean isFormatChar(char t)
    {
        if(t == 'y' || t == 'M' || t == 'd' || t == 'H' || t == 'm' || t == 's')
            return true;
        return false;
    }
    /**
     * �õ����ڸ�ʽԪ��λ��
     * @param t char
     * @param format String
     * @return int[]
     */
    public static int[] getFormatPoint(char t,String format)
    {
        if(format == null || format.length() == 0)
            return null;
        int count = format.length();
        boolean b = false;
        int p[] = new int[2];
        for(int i = 0;i < count;i ++)
        {
            char c = format.charAt(i);
            if(c == t)
            {
                if (!b)
                {
                    p[0] = i;
                    p[1] = -1;
                    b = true;
                    continue;
                }
            }
            else if(b)
            {
                p[1] = i;
                break;
            }
        }
        if(b)
        {
            if(p[1] == -1)
                p[1] = format.length();
            return p;
        }
        return null;
    }
    /**
     * �õ���һ������λ��
     * @param format String
     * @param index int
     * @return int
     */
    public static int getNextEditIndex(String format,int index)
    {
        if(format == null || format.length() == 0)
            return -1;
        for(int i = index;i < format.length();i++)
        {
            if(isFormatChar(format.charAt(i)))
                return i;
        }
        return -1;
    }
    /**
     * �õ���һ������λ��
     * @param format String
     * @param index int
     * @return int
     */
    public static int getPreviousEditIndex(String format,int index)
    {
        if(format == null || format.length() == 0)
            return -1;
        for(int i = index;i >= 0;i--)
        {
            if(isFormatChar(format.charAt(i)))
                return i;
        }
        return -1;
    }
    /**
     * ������
     * @param year int
     * @param text String
     * @param format String
     * @return String
     */
    public static String setYear(int year,String text,String format)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        if(text == null || text.length() != format.length())
            text = getDateDefaultFormat(format);
        int x[] = getFormatPoint('y',format);
        if(x == null)
            return text;
        return StringTool.editChar(text,x[0],StringTool.fill0("" + year,x[1] - x[0]));
    }
    /**
     * ������
     * @param month int
     * @param text String
     * @param format String
     * @return String
     */
    public static String setMonth(int month,String text,String format)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        if(text == null || text.length() != format.length())
            text = getDateDefaultFormat(format);
        int x[] = getFormatPoint('M',format);
        if(x == null)
            return text;
        return StringTool.editChar(text,x[0],StringTool.fill0("" + month,x[1] - x[0]));
    }
    /**
     * ������
     * @param day int
     * @param text String
     * @param format String
     * @return String
     */
    public static String setDay(int day,String text,String format)
    {
        if(format == null || format.length() == 0)
            format = "yyyy/MM/dd";
        if(text == null || text.length() != format.length())
            text = getDateDefaultFormat(format);
        int x[] = getFormatPoint('d',format);
        if(x == null)
            return text;
        return StringTool.editChar(text,x[0],StringTool.fill0("" + day,x[1] - x[0]));
    }
    /**
     * ����������
     * @param year int
     * @param month int
     * @param day int
     * @param text String
     * @param format String
     * @return String
     */
    public static String setDate(int year,int month,int day,String text,String format)
    {
        text = setYear(year,text,format);
        text = setMonth(month,text,format);
        return setDay(day,text,format);
    }
    public static void main(String args[])
    {
        System.out.println(setDate(1,2,3,"2001/10/31 00:59:59","yyyy/MM/dd HH:mm:ss"));
    }
}
