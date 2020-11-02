package com.dongyang.root.T.O;

/**
 * <p>Title: 字母类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.6
 * @version 1.0
 */
public class C
        implements java.io.Serializable
{
    /**
     * 汉字
     */
    public final static int CHINESE = 1;
    /**
     * 字母
     */
    public final static int CHAR = 2;
    /**
     * 数字
     */
    public final static int NUMBER = 3;
    /**
     * 符号
     */
    public final static int OPERATOR = 4;
    /**
     * 词
     */
    public final static int WORD = 5;
    /**
     * 空
     */
    public final static int NULL = -1;
    /**
     * 无法确定
     */
    public final static int NO = 0;
    /**
     * 字符
     */
    private String c;
    /**
     * 类型
     */
    private int type = NULL;
    /**
     * 构造器
     */
    public C()
    {

    }
    /**
     * 构造器
     * @param c String
     */
    public C(String c)
    {
        setC(c);
    }
    /**
     * 设置字符
     * @param c String
     */
    public void setC(String c)
    {
        this.c = c;
        setType(LX(c));
    }
    /**
     * 得到字符
     * @return String
     */
    public String getC()
    {
        return c;
    }
    /**
     * 设置类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getTypeString()
    {
        return getTypeString(type);
    }
    /**
     * 得到类型中文
     * @param type int
     * @return String
     */
    public static String getTypeString(int type)
    {
        switch(type)
        {
        case CHINESE:
            return "汉字";
        case CHAR:
            return "字母";
        case NUMBER:
            return "数字";
        case OPERATOR:
            return "符号";
        case WORD:
            return "词";
        }
        return "无法确定";
    }
    /**
     * 判断字符类型
     * @param s String
     * @return int
     */
    public static int LX(String s)
    {
        if(s == null || s.length() == 0)
            return NULL;
        if(s.length() > 1)
            return WORD;
        char c = s.charAt(0);
        if(c >= '0' && c <= '9')
            return NUMBER;
        if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return CHAR;
        if(c > 255)
            return CHINESE;
        return NO;
    }
    public String toString()
    {
        return getC() + ">>" + getTypeString();
    }
}
