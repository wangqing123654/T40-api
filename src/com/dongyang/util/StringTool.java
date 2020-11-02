package com.dongyang.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EmptyStackException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import com.dongyang.config.TConfigParm;
import com.dongyang.data.TParm;
import java.util.Calendar;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import com.dongyang.ui.border.TLineBorder;
import java.text.DecimalFormat;
import java.util.List;
import com.dongyang.ui.base.TTitledBorder;

public class StringTool
{

    /**
     * 处理转译字符
     * @param s String
     * @return String
     */
    public static String sign(String s)
    {
        s = s.replaceAll("#nbsp;", " ");
        s = s.replaceAll("#enter;", "\n");
        return s;
    }

    /**
     * 转译字符
     * @param s String
     * @return String
     */
    public static String setSign(String s)
    {
        s = s.replaceAll(" ", "#nbsp;");
        s = s.replaceAll("\n", "#enter;");
        return s;
    }

    /**
     * 字符串全部替换
     * @param s String 原始字符串
     * @param s1 String 查找字符串
     * @param s2 String 替换字符串
     * @return String 结果字符串
     */
    public static String replaceAll(String s, String s1, String s2)
    {
        if (s == null || s1 == null || s1.length() == 0 || s2 == null)
            return s;
        int count = s1.length();
        for (int i = s.length() - count; i >= 0; i--)
            if (s1.equals(s.substring(i, i + count)))
                s = s.substring(0, i) + s2 + s.substring(i + count, s.length());
        return s;
    }
    /**
     * 在一个字符串中删除指定的字符
     * @param s String
     * @param c char
     * @return String
     */
    public static String deleteChar(String s,char c)
    {
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < s.length();i++)
        {
            char sc = s.charAt(i);
            if (sc != c)
                sb.append(sc);
        }
        return sb.toString();
    }
    /**
     * 回车分割
     * @param s String
     * @return String[]
     */
    public static String[] separateEnter(String s)
    {
        s = StringTool.replaceAll(s,"\n\r","\r");
        s = StringTool.replaceAll(s,"\r\n","\r");
        return StringTool.parseLine(s,"\r");
    }
    /**
     * 分离出数据(竖线分割)
     * @param s String 源数据
     * @return String[] [0]数据头[1]数据体
     */
    public static String[] getHead(String s)
    {
        return getHead(s, "|");
    }

    /**
     * 分离出数据头(总最后查找)
     * @param s String 源数据
     * @param c char 分割符
     * @return String[] [0]数据头[1]数据体
     */
    public static String[] getLastHead(String s, char c)
    {
        String value[] = new String[]
                         {"", ""};
        if (s == null || s.length() == 0)
            return value;
        for (int i = s.length() - 1; i >= 0; i--)
        {
            if (c == s.charAt(i))
            {
                value[0] = s.substring(0, i);
                value[1] = s.substring(i + 1, s.length());
                return value;
            }
        }
        return value;
    }

    /**
     * 分离出数据头
     * @param s String 源数据
     * @param separator String 分割符
     * @return String[] [0]数据头[1]数据体
     */
    public static String[] getHead(String s, String separator)
    {
        String value[] = new String[]
                         {"", ""};
        if (s == null || s.length() == 0)
            return value;
        int index = s.indexOf(separator);
        if (index >= 0)
        {
            value[0] = s.substring(0, index).trim();
            value[1] = s.substring(index + separator.length(), s.length()).trim();
            if (value[1].startsWith("'") && value[1].endsWith("'"))
                value[1] = value[1].substring(1, value[1].length() - 1);
            if (value[1].startsWith("\"") && value[1].endsWith("\""))
                value[1] = value[1].substring(1, value[1].length() - 1);
            return value;
        }
        value[0] = s;
        return value;
    }

    /**
     * 字符转换为布尔
     * @param s String
     * @return boolean
     */
    public static boolean getBoolean(String s)
    {
        if (s == null)
            return false;
        if (s.length() == 0)
            return false;
        if (s.startsWith("<T>") || s.startsWith("<t>"))
            s = s.substring(3, s.length());
        else if (s.startsWith("<boolean>"))
            s = s.substring(9, s.length());
        if ("T".equalsIgnoreCase(s))
            return true;
        if ("True".equalsIgnoreCase(s))
            return true;
        if ("Y".equalsIgnoreCase(s))
            return true;
        if ("Yes".equalsIgnoreCase(s))
            return true;
        if ("1".equalsIgnoreCase(s))
            return true;
        if ("真".equalsIgnoreCase(s))
            return true;
        return false;
    }

    /**
     * 字符转转换成整形
     * @param s String
     * @return int
     */
    public static int getInt(String s)
    {
        //去掉数学类型定义
        s = removeNumberType(s);
        //去掉小数部分
        s = removeNumberDouble(s);
        try
        {
            return Integer.parseInt(s);
        } catch (Exception e)
        {
            //err("err:\"" + s + "\" " + e.getMessage());
            return 0;
        }
    }

    /**
     * 去掉小数
     * @param s String
     * @return String
     */
    private static String removeNumberDouble(String s)
    {
        int index = s.indexOf('.');
        if (index == -1)
            return s;
        return s.substring(0, index);
    }

    /**
     * 去掉数学类型定义
     * @param s String
     * @return String
     */
    private static String removeNumberType(String s)
    {
        if (s.startsWith("<I>") || s.startsWith("<i>"))
            s = s.substring(3, s.length());
        else if (s.startsWith("<int>"))
            s = s.substring(5, s.length());
        else if (s.startsWith("<L>") || s.startsWith("<l>"))
            s = s.substring(3, s.length());
        else if (s.startsWith("<long>"))
            s = s.substring(6, s.length());
        else if (s.startsWith("<D>") || s.startsWith("<d>"))
            s = s.substring(3, s.length());
        else if (s.startsWith("<double>"))
            s = s.substring(8, s.length());
        else if (s.startsWith("<short>"))
            s = s.substring(7, s.length());
        else if (s.startsWith("<F>") || s.startsWith("<f>"))
            s = s.substring(3, s.length());
        else if (s.startsWith("<B>") || s.startsWith("<b>"))
            s = s.substring(3, s.length());
        else if (s.startsWith("<byte>"))
            s = s.substring(6, s.length());
        else if (s.startsWith("<float>"))
            s = s.substring(7, s.length());
        return s;
    }

    /**
     * 字符转转换成长整形
     * @param s String
     * @return long
     */
    public static long getLong(String s)
    {
        //去掉数学类型定义
        s = removeNumberType(s);
        //去掉小数部分
        s = removeNumberDouble(s);
        try
        {
            return Long.parseLong(s);
        } catch (Exception e)
        {
            err("err:\"" + s + "\" " + e.getMessage());
            return 0;
        }
    }

    /**
     * 字符转转换成小数
     * @param s String
     * @return double
     */
    public static double getDouble(String s)
    {
        //去掉数学类型定义
        s = removeNumberType(s);
        try
        {
            return Double.parseDouble(s);
        } catch (Exception e)
        {
            //err("err:\"" + s + "\" " + e.getMessage());
            return 0.0;
        }
    }
    /**
     * 字符转转换成浮点
     * @param s String
     * @return float
     */
    public static float getFloat(String s)
    {
        //去掉数学类型定义
        s = removeNumberType(s);
        try
        {
            return Float.parseFloat(s);
        } catch (Exception e)
        {
            return 0.0f;
        }
    }

    /**
     * 字符转转换成short
     * @param s String
     * @return short
     */
    public static short getShort(String s)
    {
        //去掉数学类型定义
        s = removeNumberType(s);
        //去掉小数部分
        s = removeNumberDouble(s);
        try
        {
            return Short.parseShort(s);
        } catch (Exception e)
        {
            err("err:\"" + s + "\" " + e.getMessage());
            return 0;
        }
    }

    /**
     * 字符转转换成char
     * @param s String
     * @return char
     */
    public static char getChar(String s)
    {
        if (s == null || s.length() == 0)
            return ' ';
        if (s.startsWith("<C>") || s.startsWith("<c>"))
            return s.charAt(3);
        if (s.startsWith("<char>"))
            return s.charAt(6);
        return s.charAt(0);
    }

    /**
     * 字符转换键盘码
     * @param key String
     * @return int
     */
    public static int getKey(String key)
    {
        if (key == null || key.length() == 0)
            return 0;
        int index = key.lastIndexOf("+");
        if (index > 0)
            key = key.substring(index + 1, key.length());
        if ("0".equals(key))
            return 0x30;
        if ("1".equals(key))
            return 0x31;
        if ("2".equals(key))
            return 0x32;
        if ("3".equals(key))
            return 0x33;
        if ("4".equals(key))
            return 0x34;
        if ("5".equals(key))
            return 0x35;
        if ("6".equals(key))
            return 0x36;
        if ("7".equals(key))
            return 0x37;
        if ("8".equals(key))
            return 0x38;
        if ("9".equals(key))
            return 0x39;
        if ("A".equalsIgnoreCase(key))
            return 0x41;
        if ("B".equalsIgnoreCase(key))
            return 0x42;
        if ("C".equalsIgnoreCase(key))
            return 0x43;
        if ("D".equalsIgnoreCase(key))
            return 0x44;
        if ("E".equalsIgnoreCase(key))
            return 0x45;
        if ("F".equalsIgnoreCase(key))
            return 0x46;
        if ("G".equalsIgnoreCase(key))
            return 0x47;
        if ("H".equalsIgnoreCase(key))
            return 0x48;
        if ("I".equalsIgnoreCase(key))
            return 0x49;
        if ("J".equalsIgnoreCase(key))
            return 0x4A;
        if ("K".equalsIgnoreCase(key))
            return 0x4B;
        if ("L".equalsIgnoreCase(key))
            return 0x4C;
        if ("M".equalsIgnoreCase(key))
            return 0x4D;
        if ("N".equalsIgnoreCase(key))
            return 0x4E;
        if ("O".equalsIgnoreCase(key))
            return 0x4F;
        if ("P".equalsIgnoreCase(key))
            return 0x50;
        if ("Q".equalsIgnoreCase(key))
            return 0x51;
        if ("R".equalsIgnoreCase(key))
            return 0x52;
        if ("S".equalsIgnoreCase(key))
            return 0x53;
        if ("T".equalsIgnoreCase(key))
            return 0x54;
        if ("U".equalsIgnoreCase(key))
            return 0x55;
        if ("V".equalsIgnoreCase(key))
            return 0x56;
        if ("W".equalsIgnoreCase(key))
            return 0x57;
        if ("X".equalsIgnoreCase(key))
            return 0x58;
        if ("Y".equalsIgnoreCase(key))
            return 0x59;
        if ("Z".equalsIgnoreCase(key))
            return 0x5A;

        if ("F1".equalsIgnoreCase(key))
            return 0x70;
        if ("F2".equalsIgnoreCase(key))
            return 0x71;
        if ("F3".equalsIgnoreCase(key))
            return 0x72;
        if ("F4".equalsIgnoreCase(key))
            return 0x73;
        if ("F5".equalsIgnoreCase(key))
            return 0x74;
        if ("F6".equalsIgnoreCase(key))
            return 0x75;
        if ("F7".equalsIgnoreCase(key))
            return 0x76;
        if ("F8".equalsIgnoreCase(key))
            return 0x77;
        if ("F9".equalsIgnoreCase(key))
            return 0x78;
        if ("F10".equalsIgnoreCase(key))
            return 0x79;
        if ("F11".equalsIgnoreCase(key))
            return 0x7A;
        if ("F12".equalsIgnoreCase(key))
            return 0x7B;
        /* F13 - F24 are used on IBM 3270 keyboard; use random range for constants. */
        if ("F13".equalsIgnoreCase(key))
            return 0xF000;
        if ("F14".equalsIgnoreCase(key))
            return 0xF001;
        if ("F15".equalsIgnoreCase(key))
            return 0xF002;
        if ("F16".equalsIgnoreCase(key))
            return 0xF003;
        if ("F17".equalsIgnoreCase(key))
            return 0xF004;
        if ("F18".equalsIgnoreCase(key))
            return 0xF005;
        if ("F19".equalsIgnoreCase(key))
            return 0xF006;
        if ("F20".equalsIgnoreCase(key))
            return 0xF007;
        if ("F21".equalsIgnoreCase(key))
            return 0xF008;
        if ("F22".equalsIgnoreCase(key))
            return 0xF009;
        if ("F23".equalsIgnoreCase(key))
            return 0xF00A;
        if ("F24".equalsIgnoreCase(key))
            return 0xF00B;

        if ("ENTER".equalsIgnoreCase(key))
            return KeyEvent.VK_ENTER;
        if ("BACKSPACE".equalsIgnoreCase(key))
            return KeyEvent.VK_BACK_SPACE;
        if ("TAB".equalsIgnoreCase(key))
            return KeyEvent.VK_TAB;
        if ("PAUSE".equalsIgnoreCase(key))
            return KeyEvent.VK_PAUSE;
        if ("ESCAPE".equalsIgnoreCase(key) || "ESC".equalsIgnoreCase(key))
            return KeyEvent.VK_ESCAPE;
        if ("SPAC".equalsIgnoreCase(key))
            return KeyEvent.VK_SPACE;
        if ("PAGE_UP".equalsIgnoreCase(key))
            return KeyEvent.VK_PAGE_UP;
        if ("PAGE_DOWN".equalsIgnoreCase(key))
            return KeyEvent.VK_PAGE_DOWN;
        if ("END".equalsIgnoreCase(key))
            return KeyEvent.VK_END;
        if ("HOME".equalsIgnoreCase(key))
            return KeyEvent.VK_HOME;
        if ("DELETE".equalsIgnoreCase(key))
            return KeyEvent.VK_DELETE;
        if ("INSERT".equalsIgnoreCase(key))
            return KeyEvent.VK_INSERT;
        if ("INSERT".equalsIgnoreCase(key))
            return KeyEvent.VK_INSERT;

        return 0;
    }

    /**
     * 字符检测关键键
     * @param key String
     * @return int
     */
    public static int getControlKey(String key)
    {
        if (key == null || key.length() == 0)
            return 0;
        key = key.toUpperCase();
        int k = 0;
        if (key.indexOf("CTRL+") >= 0)
            k |= 0x2;
        if (key.indexOf("SHIFT+") >= 0)
            k |= 0x1;
        return k;
    }

    /**
     * 分割字符串
     * @param s String 字符串
     * @param separator String 分割符
     * @return String[] 分割后数组
     */
    public static String[] parseLine(String s, String separator)
    {
        if (s == null)
            return new String[]
                    {};
        ArrayList list = new ArrayList();
        int index = s.indexOf(separator);
        while (index >= 0)
        {
            list.add(s.substring(0, index).trim());
            s = s.substring(index + separator.length(), s.length());
            index = s.indexOf(separator);
        }
        if (s.length() > 0)
            list.add(s.trim());
        return (String[]) list.toArray(new String[]
                                       {});
    }
    /**
     * 空格分隔
     * @param s String
     * @return String[]
     */
    public static String[] parseSpace(String s)
    {
        if (s == null)
            return new String[]
                    {};
        ArrayList list = new ArrayList();
        s = s.trim();
        int index = s.indexOf(" ");
        while (index >= 0)
        {
            list.add(s.substring(0, index).trim());
            s = s.substring(index + 1, s.length()).trim();
            index = s.indexOf(" ");
        }
        if (s.length() > 0)
            list.add(s.trim());
        return (String[]) list.toArray(new String[]
                                       {});
    }

    /**
     * 分割字符串为int数组
     * @param s String 字符串
     * @param separator String 分割符
     * @return int[]
     */
    public static int[] parseLineInt(String s, String separator)
    {
        String[] s1 = parseLine(s, separator);
        int data[] = new int[s1.length];
        for (int i = 0; i < s1.length; i++)
        {
            int x = -1;
            try
            {
                x = Integer.parseInt(s1[i]);
            } catch (Exception e)
            {
            }
            data[i] = x;
        }
        return data;
    }

    /**
     * 分割字符串
     * @param s String
     * @param separator char '|'
     * @param ts String "[]{}()"
     * @return String[]
     */
    public static String[] parseLine(String s, char separator, String ts)
    {
        if (s == null)
            return new String[]
                    {};
        ArrayList list = new ArrayList();
        ArrayList endc = new ArrayList();
        StringBuffer sb = new StringBuffer();
        boolean b = false;
        FOR1:
                for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c == '\\')
            {
                if (!b)
                {
                    b = true;
                    continue FOR1;
                }
                sb.append(c);
                b = false;
                continue FOR1;
            }
            if (b)
            {
                sb.append(c);
                b = false;
                continue FOR1;
            }
            if (endc.size() == 0)
            {
                for (int j = 0; j < ts.length(); j += 2)
                    if (c == ts.charAt(j))
                    {
                        if (!b)
                            endc.add(new Character(ts.charAt(j + 1)));
                        sb.append(c);
                        continue FOR1;
                    }
                if (c == separator)
                {
                    list.add(sb.toString());
                    sb.setLength(0);
                    continue FOR1;
                }
                sb.append(c);
            } else
            {
                if (c == ((Character) endc.get(endc.size() - 1)).charValue())
                {
                    endc.remove(endc.size() - 1);
                    sb.append(c);
                    continue FOR1;
                }
                for (int j = 0; j < ts.length(); j += 2)
                    if (c == ts.charAt(j))
                    {
                        endc.add(new Character(ts.charAt(j + 1)));
                        sb.append(c);
                        continue FOR1;
                    }
                sb.append(c);
            }
        }
        list.add(sb.toString());
        return (String[]) list.toArray(new String[]
                                       {});
    }

    /**
     * 得到函数调用格式
     * @param s String
     * @return Parameter
     */
    public static Parameter getParameter(String s)
    {
        if (s == null || s.length() == 0)
            return null;
        String ps[] = StringTool.parseLine(s, "|");
        if (ps.length < 1)
            return null;
        Parameter parameter = new Parameter();
        parameter.name = ps[0];
        parameter.values = new Object[ps.length - 1];
        for (int i = 1; i < ps.length; i++)
            parameter.values[i - 1] = getObject(ps[i]);
        return parameter;
    }

    /**
     * 字符型转换为Vector
     * @param value String
     * @return Vector
     */
    public static Vector getVector(String value)
    {
        Object obj = getObject(value);
        if (!(obj instanceof Vector))
            return null;
        return (Vector) obj;
    }

    /**
     * 得到TParm对象
     * @param value String
     * @return TParm
     */
    public static TParm getParm(String value)
    {
        TParm parm = new TParm();
        if (value == null || value.length() == 0)
            return parm;
        String s[] = StringTool.parseLine(value, ";");
        int count = s.length;
        for (int i = 0; i < count; i++)
        {
            String v[] = StringTool.parseLine(s[i], ":");
            if (v.length < 2)
            {
                parm.setData(v[0], "");
                continue;
            }
            parm.setData(v[0], getObject(v[1]));
        }
        return parm;
    }

    /**
     * 字符型转换对象类型
     * @param value String
     * @return Object
     */
    public static Object getObject(String value)
    {
        value = value.trim();
        //字符型
        if (value.startsWith("\"") && value.endsWith("\"") ||
            value.startsWith("'") && value.endsWith("'"))
            return value.substring(1, value.length() - 1);
        //Vector型
        if (value.startsWith("[") && value.endsWith("]"))
        {
            value = value.substring(1, value.length() - 1);
            String cValue[] = StringTool.parseLine(value, ',', "[]\"\"''");
            Vector vector = new Vector();
            for (int i = 0; i < cValue.length; i++)
                vector.add(getObject(cValue[i]));
            return vector;
        }
        /*if(value.startsWith("{") && value.endsWith("}"))
               {
          ActionParm parm = new ActionParm();
          value = value.substring(1,value.length() -1);
          String cValue[] = StringTool.parseLine(value,',',"[]\"\"''");
          boolean isCommit = true;
          for(int i = 0;i < cValue.length;i++)
          {
            if(cValue[i].equalsIgnoreCase("commit"))
            {
              isCommit = true;
              continue;
            }
            if(cValue[i].equalsIgnoreCase("return"))
            {
              isCommit = false;
              continue;
            }
            int index = cValue[i].indexOf("=");
            if(index < 0)
              continue;
            String cname = cValue[i].substring(0,index);
            String cvalue = cValue[i].substring(index + 1,cValue[i].length());
            if(isCommit)
              parm.setCommitData(cname,getObject(cvalue));
            else
              parm.setReturnData(cname,getObject(cvalue));
          }
          return parm;
               }*/
        try
        {
            if (value.startsWith("<I>") || value.startsWith("<i>") ||
                value.startsWith("<int>"))
                return getInt(value);
            if (value.startsWith("<L>") || value.startsWith("<l>") ||
                value.startsWith("<long>"))
                return getLong(value);
            if (value.startsWith("<D>") || value.startsWith("<d>") ||
                value.startsWith("<double>"))
                return getDouble(value);
            if (value.startsWith("<short>"))
                return getShort(value);
            if (value.startsWith("<B>") || value.startsWith("<b>"))
                return new Byte(value.substring(3, value.length()));
            if (value.startsWith("<byte>"))
                return new Byte(value.substring(6, value.length()));
            if (value.startsWith("<C>") || value.startsWith("<c>") ||
                value.startsWith("<char>"))
                return getChar(value);
            if (value.startsWith("<T>") || value.startsWith("<t>") ||
                value.startsWith("<boolean>"))
                return getBoolean(value);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        return value;
    }

    /**
     * 比较两个数据类型是否相同
     * @param s String
     * @param object Object
     * @return boolean
     */
    public static boolean equalsObjectType(String s, Object object)
    {
        if (object == null)
            return true;
        if ("any".equals(s))
            return true;
        if ("java.lang.Object".equals(s))
            return true;
        return equalsObjectType(s, object.getClass().getName());
    }

    public static boolean equalsType(Class s, Object object)
    {
        if (object == null)
            return true;
        if (s.isInstance(object))
            return true;
        if (equalsObjectType(s.getName(), object.getClass().getName()))
            return true;
        return false;
    }
    /**
     * 比较对象相同
     * @param o1 Object
     * @param o2 Object
     * @return boolean
     */
    public static boolean equals(Object o1,Object o2)
    {
        if(o1 == null && o2 != null)
            return false;
        if(o1 != null && o2 == null)
            return false;
        if(o1 == null && o2 == null)
            return true;
        return o1.equals(o2);
    }
    /**
     * 比较两个数据类型是否相同
     * @param s1 String
     * @param s2 String
     * @return boolean
     */
    public static boolean equalsObjectType(String s1, String s2)
    {
        if (s1.equals(s2))
            return true;
        if ("boolean".equals(s1) && "java.lang.Boolean".equals(s2))
            return true;
        if ("boolean".equals(s2) && "java.lang.Boolean".equals(s1))
            return true;
        if ("char".equals(s1) && "java.lang.Character".equals(s2))
            return true;
        if ("char".equals(s2) && "java.lang.Character".equals(s1))
            return true;
        if ("double".equals(s1) && "java.lang.Double".equals(s2))
            return true;
        if ("double".equals(s2) && "java.lang.Double".equals(s1))
            return true;
        if ("float".equals(s1) && "java.lang.Float".equals(s2))
            return true;
        if ("float".equals(s2) && "java.lang.Float".equals(s1))
            return true;
        if ("int".equals(s1) && "java.lang.Integer".equals(s2))
            return true;
        if ("int".equals(s2) && "java.lang.Integer".equals(s1))
            return true;
        if ("long".equals(s1) && "java.lang.Long".equals(s2))
            return true;
        if ("long".equals(s2) && "java.lang.Long".equals(s1))
            return true;
        if ("short".equals(s1) && "java.lang.Short".equals(s2))
            return true;
        if ("short".equals(s2) && "java.lang.Short".equals(s1))
            return true;
        if ("String".equals(s1) && "java.lang.String".equals(s2))
            return true;
        if ("String".equals(s2) && "java.lang.String".equals(s1))
            return true;
        if ("Object".equals(s1) && "java.lang.Object".equals(s2))
            return true;
        if ("Object".equals(s2) && "java.lang.Object".equals(s1))
            return true;
        return false;
    }

    /**
     * 得到布局管理器位置
     * @param s String
     * @return String
     */
    public static String layoutConstraint(String s)
    {
        if ("NORTH".equalsIgnoreCase(s) || "北".equals(s) || "上".equals(s))
            return BorderLayout.NORTH;
        if ("SOUTH".equalsIgnoreCase(s) || "南".equals(s) || "下".equals(s))
            return BorderLayout.SOUTH;
        if ("EAST".equalsIgnoreCase(s) || "东".equals(s) || "右".equals(s))
            return BorderLayout.EAST;
        if ("WEST".equalsIgnoreCase(s) || "西".equals(s) || "左".equals(s))
            return BorderLayout.WEST;
        if ("CENTER".equalsIgnoreCase(s) || "居中".equals(s) || "中".equals(s))
            return BorderLayout.CENTER;
        return null;
    }

    /**
     * 得到文字横向位置
     * @param s String
     * @return int
     */
    public static int horizontalAlignment(String s)
    {
        if ("CENTER".equalsIgnoreCase(s) || "中".equals(s) || "0".equals(s))
            return SwingConstants.CENTER;
        if ("LEFT".equalsIgnoreCase(s) || "左".equals(s) || "2".equals(s))
            return SwingConstants.LEFT;
        if ("RIGHT".equalsIgnoreCase(s) || "右".equals(s) || "4".equals(s))
            return SwingConstants.RIGHT;
        return JLabel.CENTER;
    }

    /**
     * 得到文字纵向位置
     * @param s String
     * @return int
     */
    public static int verticalAlignment(String s)
    {
        if ("CENTER".equalsIgnoreCase(s) || "中".equals(s) || "0".equals(s))
            return SwingConstants.CENTER;
        if ("TOP".equalsIgnoreCase(s) || "上".equals(s) || "1".equals(s))
            return SwingConstants.TOP;
        if ("BOTTOM".equalsIgnoreCase(s) || "下".equals(s) || "3".equals(s))
            return SwingConstants.BOTTOM;
        return JLabel.CENTER;
    }

    /**
     * 字符转换成颜色
     * @param color String
     * @return Color
     */
    public static Color getColor(String color)
    {
        String c[] = parseLine(color, ",");
        if (c.length < 3)
            return (Color)null;
        return new Color(getInt(c[0]), getInt(c[1]), getInt(c[2]));
    }
    /**
     * 得到颜色的字符显现
     * @param color Color
     * @return String
     */
    public static String getString(Color color)
    {
        if (color == null)
            return "";
        return color.getRed() + "," + color.getGreen() + "," + color.getBlue();
    }
    /**
     * 转化成String型
     * @param obj Object
     * @param format String 数字format格式 ###0.00
     * @return String
     */
    public static String S(Object obj,String format)
    {
        try{
            DecimalFormat formatObject = new DecimalFormat(format);
            double d = D(obj);
            return formatObject.format(d);
        }catch(Exception e)
        {
            return "format err:";
        }
    }
    /**
     * 转换成double型
     * @param obj Object
     * @return double
     */
    public static double D(Object obj)
    {
        return TypeTool.getDouble(obj);
    }

    /**
     * 字符转换成颜色
     * @param color String 颜色
     * @param parm TConfigParm
     * @return Color
     */
    public static Color getColor(String color, TConfigParm parm)
    {
        if (color == null || color.length() == 0)
            return (Color)null;
        color = parm.getConfigColor().getString(parm.getSystemGroup(), color,
                                                color);
        return getColor(color);
    }

    /**
     * 边框
     * @param s String 边框
     * @param parm TConfigParm
     * @return Border
     */
    public static Border getBorder(String s, TConfigParm parm)
    {
        if (s.length() == 0)
            return (Border)null;
        String c[] = parseLine(s, "|");
        if ("Etched".equalsIgnoreCase(c[0]) || "蚀刻".equals(c[0]) ||
            "刻".equals(c[0]))
            return BorderFactory.createEtchedBorder();
        if ("Line".equalsIgnoreCase(c[0]) || "线框".equals(c[0]) ||
            "线".equals(c[0]))
        {
            Color color = Color.black;
            int thickness = 1;
            if (c.length > 1)
                color = getColor(c[1], parm);
            if (color == null)
                color = Color.black;
            if (c.length > 2)
                thickness = getInt(c[2]);
            if (thickness == 0)
                thickness = 1;
            return BorderFactory.createLineBorder(color, thickness);
        }
        if("Line1".equalsIgnoreCase(c[0]) || "线1".equals(c[0]))
        {
            return new TLineBorder();
        }
        if ("LoweredBevel".equalsIgnoreCase(c[0]) || "凹陷".equals(c[0]) ||
            "凹".equals(c[0]))
            return BorderFactory.createLoweredBevelBorder();
        if ("RaisedBevel".equalsIgnoreCase(c[0]) || "凸起".equals(c[0]) ||
            "凸".equals(c[0]))
            return BorderFactory.createRaisedBevelBorder();
        if ("Titled".equalsIgnoreCase(c[0]) || "组框".equals(c[0]) ||
            "组".equals(c[0]))
        {
            String title = "";
            if (c.length > 1)
            {
                c = getHead(s, "|");
                title = c[1];
            }
            return new TTitledBorder(BorderFactory.createEtchedBorder(), title);
        }
        return null;
    }

    /**
     * 将字符串数组连接成字符串
     * @param arg String[] 分割符
     * @return String
     */
    public static String getString(String[] arg)
    {
        return getString(arg, ",");
    }

    /**
     * 将字符串数组连接成字符串
     * @param arg String[]
     * @param fg String 分割符
     * @return String
     */
    public static String getString(String[] arg, String fg)
    {
        if(arg == null)
            return "null";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arg.length; i++)
        {
            if (sb.length() > 0)
                sb.append(fg);
            sb.append(arg[i]);
        }
        return sb.toString();
    }

    /**
     * 将数字数组连接成字符串
     * @param arg int[]
     * @return String
     */
    public static String getString(int[] arg)
    {
        return getString(arg, ",");
    }

    /**
     * 将数字数组连接成字符串
     * @param arg int[]
     * @param fg String 分割符
     * @return String
     */
    public static String getString(int[] arg, String fg)
    {
        if(arg == null)
            return "null";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arg.length; i++)
        {
            if (sb.length() > 0)
                sb.append(fg);
            sb.append(arg[i]);
        }
        return sb.toString();
    }
    /**
     * 将对象数组连接成字符串
     * @param arg Object[]
     * @return String
     */
    public static String getString(Object[] arg)
    {
        return getString(arg,",");
    }
    /**
     * 将对象数组连接成字符串
     * @param arg Object[]
     * @param fg String
     * @return String
     */
    public static String getString(Object[] arg, String fg)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < arg.length; i++)
        {
            if (sb.length() > 0)
                sb.append(fg);
            sb.append(arg[i]);
        }
        return sb.toString();
    }
    /**
     * 字符串转换为日期
     * @param s String
     * @param format String (yyyy/MM/dd HH:mm:ss)
     * @return Date
     */
    public static Date getDate(String s, String format)
    {
        if(s == null || s.length() == 0)
            return null;
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            Date d = sdf.parse(s);
            return d;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 得到年
     * @param d Date
     * @return int
     */
    public static int getYear(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.YEAR);
    }
    /**
     * 得到月
     * @param d Date
     * @return int
     */
    public static int getMonth(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MONTH) + 1;
    }
    /**
     * 得到日
     * @param d Date
     * @return int
     */
    public static int getDay(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.DATE);
    }
    /**
     * 得到小时
     * @param d Date
     * @return int
     */
    public static int getHour(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.HOUR);
    }
    /**
     * 得到分钟
     * @param d Date
     * @return int
     */
    public static int getMinute(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.MINUTE);
    }
    /**
     * 得到秒
     * @param d Date
     * @return int
     */
    public static int getSecond(Date d)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        return c.get(Calendar.SECOND);
    }
    /**
     * 得到日期
     * @param year int
     * @param month int
     * @param day int
     * @return Date
     */
    public static Date getDate(int year,int month,int day)
    {
        Calendar c = Calendar.getInstance();
        c.set(year,month - 1,day);
        return c.getTime();
    }
    public static String getString(Date d, String format)
    {
        if (d == null)
            return "";
        try
        {
            return new SimpleDateFormat(format).format(d);
        } catch (Exception e)
        {
            return "";
        }
    }

    public static Timestamp getTimestamp(Date d)
    {
        if (d == null)
            return null;
        return new Timestamp(d.getTime());
    }

    public static Timestamp getTimestamp(String s, String format)
    {
        return getTimestamp(getDate(s, format));
    }

    public static String getString(Timestamp d, String format)
    {
        if (d == null)
            return "";
        return getString(getDate(d), format);
    }

    public static Date getDate(Timestamp d)
    {
        return new Date(d.getTime());
    }

    public static java.sql.Date getSQLDate(String s, String format)
    {
        return getSQLDate(getDate(s, format));
    }

    public static java.sql.Date getSQLDate(Date d)
    {
        return new java.sql.Date(d.getTime());
    }

    /**
     * 得到日期 去掉时间
     * @param d Timestamp
     * @return Timestamp
     */
    public static Timestamp getTimestampDate(Timestamp d)
    {
        return getTimestamp(getString(d, "yyyyMMdd"), "yyyyMMdd");
    }

    /**
     * 产生填充字串<BR>
     * 例如 fill("0",3) return "000"
     * @param s String 填充字串
     * @param index int 个数
     * @return String
     */
    public static String fill(String s, int index)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < index; i++)
            sb.append(s);
        return sb.toString();
    }
    /**
     * 零填充
     * fill("12",4) return "0012";
     * @param s String
     * @param index int
     * @return String
     */
    public static String fill0(String s,int index)
    {
        if(s.length() > index)
            return s.substring(s.length() - index);
        return fill("0",index - s.length()) + s;
    }
    /**
     * 作补位
     * fillLeft("8",3,"0") return "008"
     * @param s String
     * @param index int
     * @param fillBy String
     * @return String
     */
    public static String fillLeft(String s, int index, String fillBy)
    {
        int i = index - s.length();
        if (i < 1)
            return s;
        return fill(fillBy, i) + s;
    }

    /**
     * 校验IP正确性
     * @param s String IP
     * @return boolean true 正确 false 错误
     */
    public static boolean isIP(String s)
    {
        String t[] = parseLine(s, ".");
        if (t.length != 4)
            return false;
        try
        {
            for (int i = 0; i < 4; i++)
            {
                int x = Integer.parseInt(t[i]);
                if (!t[i].equals("" + x))
                    return false;
                if (x < 0 || x > 255)
                    return false;
            }
        } catch (Exception e)
        {
            return false;
        }
        return true;
    }

    /**
     * 校验IP正确性 s1 < s2
     * @param s1 String
     * @param s2 String
     * @return boolean true 正确 false 错误
     */
    public static boolean isIPSeq(String s1, String s2)
    {
        if (!isIP(s1) || !isIP(s2))
            return false;
        return getIP0(s2).compareTo(getIP0(s1)) > 0;
    }

    private static String getIP0(String s)
    {
        String t[] = parseLine(s, ".");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4; i++)
        {
            sb.append(fillLeft(t[i], 3, "0"));
            if (i < 3)
                sb.append(".");
        }
        return sb.toString();
    }

    /**
     * 校验IP正确性 s 在 s1和s2之间
     * @param s1 String
     * @param s2 String
     * @param s String
     * @return boolean
     */
    public static boolean isIPBetween(String s1, String s2, String s)
    {
        if (!isIP(s1) || !isIP(s2) || !isIP(s))
            return false;
        if (getIP0(s2).compareTo(getIP0(s1)) <= 0)
            return false;
        if (getIP0(s).compareTo(getIP0(s1)) < 0)
            return false;
        if (getIP0(s2).compareTo(getIP0(s)) < 0)
            return false;
        return true;
    }

    /**
     * 校验IP正确性 s1,s2 和 s3,s4 不能相交
     * @param s1 String
     * @param s2 String
     * @param s3 String
     * @param s4 String
     * @return boolean
     */
    public static boolean isInteractIP(String s1, String s2, String s3,
                                       String s4)
    {
        if (!isIPSeq(s1, s2) || !isIPSeq(s3, s4))
            return false;
        if (isIPBetween(s1, s2, s3))
            return false;
        if (isIPBetween(s1, s2, s3))
            return false;
        if (isIPBetween(s3, s4, s1))
            return false;
        return true;

    }

    /**
     * 校验邮件地址正确性
     * @param email String
     * @return boolean true 成功 false 失败
     */
    public static boolean isEmail(String email)
    {
        Pattern pattern = Pattern.compile(
                "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$",
                Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


    /**
     * 验证身份证正确性
     * @param idcard String
     * @return boolean true 成功 false 失败
     */
    public static boolean isId(String idcard)
    {
        try
        {
            if (idcard == null || idcard.length() == 0)
                return false;
            if (idcard.length() == 15)
            {
                idcard = uptoeighteen(idcard);
            }
            if (idcard.length() != 18)
            {
                return false;
            }

            String verify = idcard.substring(17, 18);

            if (!verify.equalsIgnoreCase((getIDVerify(idcard))))
            {
                return false;
            }
            return true;

        } catch (EmptyStackException e)
        {
            return false;
        }
    }

    /**
     * 根据给入身份证返回生日
     * @param ID String
     * @return Timestamp
     */
    public static Timestamp getBirdayFromID(String ID)
    {
        if (!isId(ID))
            return null;
        if(ID.length() == 15)
            ID = uptoeighteen(ID);
        String birth = ID.substring(6, 10) + ID.substring(10, 12) +
                       ID.substring(12, 14);
        return StringTool.getTimestamp(birth, "yyyyMMdd");

    }

    /**
     * 男
     * @return String
     */
    public static String getMale()
    {
        return "1";
    }

    /**
     * 女
     * @return String
     */
    public static String getFemale()
    {
        return "2";
    }

    /**
     * 不确定
     * @return String
     */
    public static String getUnknowGender()
    {
        return "9";
    }

    /**
     * 根据给入ID返回男女
     * @param ID String
     * @return 0 String：女，1：男，-1：身份证号错误
     */
    public static String isMaleFromID(String ID)
    {
        if (!isId(ID))
            return getUnknowGender();
        if(ID.length() == 15)
            ID = uptoeighteen(ID);
        if (Integer.parseInt(ID.substring(16, 17)) % 2 == 1)
            return getMale();

        return getFemale();
    }

    /**
     * 身份证部位
     * @param eightcardid String
     * @return String
     */
    private static String getIDVerify(String eightcardid)
    {
        final int[] wi =
                {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
        final int[] vi =
                {1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2};
        int[] ai = new int[18];
        int remaining = 0;
        if (eightcardid.length() == 18)
        {
            eightcardid = eightcardid.substring(0, 17);
        }
        if (eightcardid.length() == 17)
        {
            int sum = 0;
            for (int i = 0; i < 17; i++)
            {
                String k = eightcardid.substring(i, i + 1);
                ai[i] = Integer.parseInt(k);
            }
            for (int i = 0; i < 17; i++)
            {
                sum = sum + wi[i] * ai[i];
            }
            remaining = sum % 11;
        }
        return remaining == 2 ? "X" : String.valueOf(vi[remaining]);
    }

    //15 update to 18
    private static String uptoeighteen(String fifteencardid)
    {
        String eightcardid = fifteencardid.substring(0, 6);
        eightcardid = eightcardid + "19";
        eightcardid = eightcardid + fifteencardid.substring(6, 15);
        eightcardid = eightcardid + getIDVerify(eightcardid);
        return eightcardid;
    }

    /**
     * 是否是Tag
     * @param text String "<ID>"
     * @return boolean true 是 false 否
     */
    public static boolean isTag(String text)
    {
        if (text == null || text.length() == 0)
            return false;
        return (text.startsWith("<") && text.endsWith(">"));
    }

    /**
     * 得到Tag
     * @param text String
     * @return String
     */
    public static String getTag(String text)
    {
        if (!isTag(text))
            return "";
        return text.substring(1, text.length() - 1);
    }
    /**
     * 编号号码增加
     * @param s String
     * @return String
     */
    public static String addString(String s)
    {
        String value = "";
        boolean flt = true;
        for (int i = s.length() - 1; i >= 0; i--)
        {
            char c = s.charAt(i);
            if (c < '0' || c > '9')
            {
                value = c + value;
                continue;
            }
            if (flt)
            {
                c++;
                if (c > '9')
                    c = '0';
                else
                    flt = false;
            }
            value = c + value;
        }
        return value;
    }
    /**
     * 编号号码减少
     * @param s String
     * @return String
     */
    public static String subString(String s)
    {
        String value = "";
        boolean flt = true;
        for (int i = s.length() - 1; i >= 0; i--)
        {
            char c = s.charAt(i);
            if (c < '0' || c > '9')
            {
                value = c + value;
                continue;
            }
            if (flt)
            {
                c--;
                if (c < '0')
                    c = '9';
                else
                    flt = false;
            }
            value = c + value;
        }
        return value;
    }
    /**
     * 计算字符串中存在重复项(票号使用)
     * @param s String[]
     * @return boolean
     */
    public static boolean repeatString(String s[])
    {
        for(int i = 0;i < s.length -1;i ++)
            for(int j = i + 1;j < s.length;j++)
                if(equals(s[i],s[j]))
                    return true;

        return false;
    }
    /**
     * 计算两个票号之间的差值
     * @param s1 String
     * @param s2 String
     * @return int
     */
    public static int bitDifferOfString(String s1, String s2)
    {
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() == 0 ||
            s1.length() != s2.length())
            return -1;
        if (s1.compareTo(s2) > 0)
            return -1;
        if (s1.equals(s2))
            return 0;
        boolean b = false;
        String s = "";
        for (int i = s1.length() - 1; i >= 0; i--)
        {
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(i);
            if (c1 < '0' || c1 > '9' || c2 < '0' || c2 > '9')
                continue;
            if (b)
            {
                b = false;
                c2--;
                if (c2 < '0')
                {
                    c2 = '9';
                    b = true;
                }
            }
            if (c2 > c1)
            {
                char c = (char) (c2 - c1 + '0');
                s = c + s;
                continue;
            }
            if (c2 < c1)
            {
                char c = (char) ('9' - c1 + '1');
                s = c + s;
                b = true;
                continue;
            }
            s = '0' + s;
        }
        return Integer.parseInt(s);
    }

    /**
     * 日期间个天数
     * @param t1 Timestamp
     * @param t2 Timestamp
     * @return int
     */
    public static int getDateDiffer(Timestamp t1, Timestamp t2)
    {
        return (int) ((t1.getTime() - t2.getTime()) / 1000.0 / 60.0 / 60.0 /
                      24.0);
    }

    /**
     * 日期偏移天数
     * @param t Timestamp
     * @param l long
     * @return Timestamp
     */
    public static Timestamp rollDate(Timestamp t, long l)
    {
        return new Timestamp(t.getTime() + l * 24 * 60 * 60 * 1000);
    }

    /**
     * 设置日期时间
     * @param t Timestamp 原始日期
     * @param time String 时间"14:59:59"
     * @return Timestamp
     */
    public static Timestamp setTime(Timestamp t, String time)
    {
        String date = getString(t, "yyyy/MM/dd") + " " + time;
        return getTimestamp(date, "yyyy/MM/dd HH:mm:ss");
    }

    /**
     * 得到星期
     * @param t Timestamp
     * @return int
     */
    public static int getWeek(Timestamp t)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(t);
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
    }

    /**
     * 四舍五入
     * @param d double
     * @param p int
     * @return double
     */
    public static double round(double d, int p)
    {
        BigDecimal b = new BigDecimal(Double.toString(d));
        return b.divide(new BigDecimal("1"), p, 4).doubleValue();
    }

    /**
     * 日志输出
     * @param text String 日志内容
     */
    public static void out(String text)
    {
        Log.getInstance().out(text);
    }

    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */


    public static void out(String text, boolean debug)
    {
        Log.getInstance().out(text, debug);
    }

    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public static void err(String text)
    {
        Log.getInstance().err(text);
    }

    /**
     * 字符数组排序
     * @param s String[]
     */
    public static void orderStringArray(String s[])
    {
        for (int i = s.length - 1; i > 0; i--)
            for (int j = i - 1; j >= 0; j--)
                if (compareTo(s[i], s[j]) < 0)
                {
                    String s1 = s[i];
                    s[i] = s[j];
                    s[j] = s1;
                }
    }

    /**
     * 比较两个字符串的大小
     * @param s1 String
     * @param s2 String
     * @return int s1 < s2 返回 -1 s1 > s2 返回 1
     */
    public static int compareTo(String s1, String s2)
    {
        if (s1 == null && s2 == null)
            return 0;
        if (s1 == null)
            return -1;
        if (s2 == null)
            return 1;
        return s1.compareTo(s2);
    }

    /**
     * 得到char数组
     * @param s String
     * @return char[]
     */
    public static char[] getCharArray(String s)
    {
        char c[] = new char[s.length()];
        for (int i = 0; i < s.length(); i++)
            c[i] = s.charAt(i);
        return c;
    }

    /**
     * 将char数组转换为字符串
     * @param c char[]
     * @return String
     */
    public static String getString(char c[])
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < c.length; i++)
            sb.append(c[i]);
        return sb.toString();
    }

    /**
     * 字符加法
     * @param s String
     * @return String
     */
    public static String stringADD(String s)
    {
        char[] cc = getCharArray(s);
        for (int i = cc.length - 1; i >= 0; i--)
        {
            char c = cc[i];
            if ((c >= '0' && c < '9') || (c >= 'A' && c < 'Z') ||
                c >= 'a' && c < 'z')
            {
                c++;
                cc[i] = c;
                break;
            }
            if (c == '9')
            {
                cc[i] = 'A';
                break;
            }
            if (c == 'Z' || c == 'z')
                cc[i] = '0';
        }
        return getString(cc);
    }
    /**
     * 字符减法
     * @param s String
     * @return String
     */
    public static String stringSub(String s)
    {
        char[] cc = getCharArray(s);
        for (int i = cc.length - 1; i >= 0; i--)
        {
            char c = cc[i];
            if ((c >= '1' && c <= '9') || (c > 'A' && c <= 'Z') ||
                c > 'a' && c <= 'z')
            {
                c--;
                cc[i] = c;
                break;
            }
            if (c == '0')
            {
                cc[i] = 'Z';
                break;
            }
            if (c == 'A' || c == 'a')
                cc[i] = '9';
        }
        return getString(cc);
    }

    /**
     * 得到计算年龄分别传出年月日(eh)
     * @param t1 Timestamp 患者生日
     * @param t2 Timestamp 截至日期
     * @return String[](0:年,1:月,2:日)
     */
    public static String[] CountAgeByTimestamp(Timestamp t1, Timestamp t2)
    {
        String ayear = "";
        String amonth = "";
        String aday = "";

        String[] rString =
                           {ayear, amonth, aday};
        if (t1 == null || t2 == null)
        {
            return rString;
        }

        // 取得今天的年月日
        GregorianCalendar today = new GregorianCalendar(getYear(t2),
                getMonth(t2), getDay(t2));
        // today.set(1973,6-1,30);
        int iyear2 = getYear(t2);
        int imonth2 = getMonth(t2);
        int iday2 = getDay(t2);
        int maxDays = today.getActualMaximum(today.DAY_OF_MONTH);
        // 取得输入的年月日
        GregorianCalendar birthday = new GregorianCalendar();
        int iyear1 = getYear(t1);
        int imonth1 = getMonth(t1);
        int iday1 = getDay(t1);
        birthday.set(iyear1, imonth1, iday1);
        int minDays = birthday.getActualMaximum(birthday.DAY_OF_MONTH);
        // 设定变数初始值
        int year = iyear2 - iyear1;
        int month = imonth2 - imonth1;
        int day = iday2 - iday1 + 1;
        if (day < 0)
        {
            day = maxDays + day;
            month--;
        } else
        {
            day = minDays + day - maxDays;
            if (day < 0)
            {
                day = maxDays + day;
                month--;
            }
        }
        if (month < 0)
        {
            month = 12 + month;
            year--;
        }
        rString[0] = String.valueOf(year);
        rString[1] = String.valueOf(month);
        rString[2] = String.valueOf(day);

        return rString;
    }

    /**
     * 根据给定年份判断是否是闰年
     * @param a String 年份
     * @return boolean 真：是，假：不是
     */
    public static boolean IsLeapYear(String a)
    {
        int yearleap = 0;
        yearleap = Integer.parseInt(a);
        GregorianCalendar gcalendar = new GregorianCalendar();
        return gcalendar.isLeapYear(yearleap);
    }
    /**
     * 数组拷贝方法
     * @param sql1 String[]
     * @param sql2 String[]
     * @return String[]
     */
    public static String[] copyArray(String sql1[],String sql2[]){
        if(sql1.length==0)
            return sql2;
        if(sql2.length==0)
            return sql1;
        String data[] = new String[sql1.length + sql2.length];
        System.arraycopy(sql1, 0, data, 0, sql1.length);
        System.arraycopy(sql2, 0, data, sql1.length, sql2.length);
        return data;
    }
    /**
     * 设置剪切板
     * @param s String
     */
    public static void setClipboard(String s)
    {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
      
        clipboard.setContents(stringSelection, null);
    }
    /**
     * 得到剪接板
     * @return String
     */
    public static String getClipboard()
    {
        Clipboard clipboard=Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents=clipboard.getContents(null);
        DataFlavor flavor=DataFlavor.stringFlavor;
        if(contents.isDataFlavorSupported(flavor))
            try{
                return (String) contents.getTransferData(flavor);
            }catch(Exception e)
            {

            }
        return "";
    }
    /**
     * 修改字符串中的一个字符
     * @param s String
     * @param index int
     * @param c char
     * @return String
     */
    public static String editChar(String s,int index,char c)
    {
        if(s == null||s.length() == 0)
            return s;
        if(index < 0 || index >= s.length())
            return s;
        return s.substring(0,index) + c + s.substring(index + 1);
    }
    /**
     * 修改字符串中的一个子串
     * @param s String
     * @param index int
     * @param c String
     * @return String
     */
    public static String editChar(String s,int index,String c)
    {
        if(s == null||s.length() == 0)
            return s;
        if(index < 0 || index + c.length() > s.length())
            return s;
        return s.substring(0,index) + c + s.substring(index + c.length());
    }
    /**
     * 加密字串
     * @param text String 源字串
     * @return String 加密后字串
     */
    public static String encrypt(String text)
    {
        String av_str = "";
        try
        {
            byte aa[] = text.getBytes("UTF-16BE");
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < aa.length; i++)
            {
                aa[i] = (byte)(~aa[i]);
                sb.append(Integer.toHexString(aa[i]).substring(6));
            }

            av_str = sb.toString();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return av_str;
    }
    /**
     * 解密字串
     * @param text String 源字串
     * @return String 解密后字串
     */
    public static String decrypt(String text)
    {
        String vb_str = "";
        try
        {
            byte bb[] = new byte[text.length() / 2];
            for(int i = 0; i < text.length(); i += 2)
            {
                bb[i / 2] = (byte)(Integer.parseInt(text.substring(i, i + 2), 16) + -256);
                bb[i / 2] = (byte)(~bb[i / 2]);
            }

            vb_str = new String(bb, "UTF-16BE");
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        return vb_str;
    }
}
