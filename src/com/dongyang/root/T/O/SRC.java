package com.dongyang.root.T.O;

import java.io.BufferedReader;
import java.util.List;
import java.io.StringReader;
import java.util.ArrayList;

public class SRC
{
    /**
     * 数据
     */
    private String data;
    /**
     * 全部行
     */
    private String rows[];
    /**
     * 行号
     */
    private int row;
    /**
     * 当前行
     */
    private int nowRow;
    /**
     * 构造器
     * @param data String
     */
    public SRC(String data)
    {
        setData(data);
        rows = readLine(data);
    }
    /**
     * 设置数据
     * @param data String
     */
    public void setData(String data)
    {
        this.data = data;
    }
    /**
     * 得到数据
     * @return String
     */
    public String getData()
    {
        return data;
    }
    /**
     * 解析行
     * @param data String
     * @return String[]
     */
    public String[] readLine(String data)
    {
        String s = "";
        List list = new ArrayList();
        try {
            BufferedReader br = new BufferedReader(new StringReader(new String(
                    data)));
            while ((s = br.readLine()) != null)
            {
                list.add(s);
            }
            br.close();
        }catch(Exception e)
        {
            return null;
        }
        return (String[])list.toArray(new String[]{});
    }

    public boolean isEnd()
    {
        return getNowRow() >= rows.length;
    }
    public int getNowRow()
    {
        return nowRow;
    }
    public String getLine()
    {
        while (nowRow < rows.length)
        {
            row = nowRow;
            String s1 = rows[nowRow].trim();
            if (s1.length() == 0)
            {
                nowRow++;
                continue;
            }
            if (s1.startsWith("//"))
            {
                nowRow++;
                continue;
            }
            int index = s1.indexOf("/*");
            while (index > 0 && !isString(s1.substring(0, index)))
            {
                int indexEnd = s1.indexOf("*/", index + 2);
                if (indexEnd > 0)
                {
                    s1 = s1.substring(0, index) + s1.substring(indexEnd + 2);
                    index = s1.indexOf("/*");
                    nowRow++;
                    continue;
                }
                do
                {
                    nowRow++;
                    indexEnd = rows[nowRow].indexOf("*/");
                } while (nowRow < rows.length && indexEnd == -1);
                s1 = s1.substring(0, index) + rows[nowRow].substring(indexEnd + 2);
                index = s1.indexOf("/*");
            }
            index = s1.indexOf("//");
            if (index > 0 && !isString(s1.substring(0, index)))
                s1 = s1.substring(0, index);
            nowRow++;
            return s1;
        }
        return "";
    }
    public boolean isString(String s)
    {
        boolean is = false;
        for(int i = 0;i < s.length();i++)
        {
            if(s.charAt(i) == '"' && (!is || s.charAt(i - 1) != '\\'))
                is = !is;
        }
        return is;
    }
}
