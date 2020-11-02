package com.dongyang.util;

import java.util.ArrayList;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 脚本处理</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class Script
{
    public boolean ok = true;
    public TParm parm;
    /**
     * 语法
     */
    private String data;
    /**
     * 符合
     */
    public int type;
    /**
     * 函数参数
     */
    public String functionParm;
    /**
     * 堆
     */
    public ArrayList list = new ArrayList();
    /**
     * 设置宏
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }
    /**
     * 得到宏
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }
    /**
     * 设置语法
     * @param data String
     */
    public void setData(String data)
    {
        this.data = data;
    }
    /**
     * 得到语法
     * @return String
     */
    public String getData()
    {
        return data;
    }
    /**
     * 得到一个字段
     * @return String
     */
    public String getWord()
    {
        type = 0;
        StringBuffer sb = new StringBuffer();
        //状态
        int state = 0;
        //层数
        int cs = 0;
        for(int i = 0;i < getData().length();i++)
        {
            char c = getData().charAt(i);
            switch(state)
            {
            case 0:
                if(c == ' ')
                    if(sb.length() == 0)
                        continue;
                    else
                    {
                        setData(getData().substring(i,getData().length()));
                        return sb.toString();
                    }
                if(c == '\'')
                {
                    state = 1;
                    continue;
                }
                if(c == '(')
                {
                    if(sb.length() > 0)
                    {
                        setData(getData().substring(i,getData().length()));
                        return sb.toString();
                    }
                    state = 4;
                    cs ++;
                    continue;
                }
                if(c == '+' || c == '-' || c == '*' || c == '/' ||
                   c == '>' || c == '<' || c == '=' ||
                   c == '&' || c == '|' ||
                   c == '~' || c == '~' || c == '^' ||
                   c == ',' || c == '.' || c == ';')
                {
                    if(sb.length() > 0)
                    {
                        setData(getData().substring(i, getData().length()));
                        return sb.toString();
                    }
                    setData(getData().substring(i + 1, getData().length()));
                    type = 3;
                    return "" + c;
                }
                sb.append(c);
                continue;
            case 1:
                if(c == '\\')
                {
                    state = 3;
                    continue;
                }
                if(c == '\'')
                {
                    setData(getData().substring(i + 1,getData().length()));
                    type = 1;
                    return sb.toString();
                }
                sb.append(c);
                continue;
            case 3:
                sb.append(c);
                state = 1;
                continue;
            case 4:
                if(c == '(')
                    cs ++;
                if(c == ')')
                    cs--;
                if(cs == 0)
                {
                    type = 2;
                    state = 0;
                    continue;
                }
                sb.append(c);
                continue;
            }
        }
        setData("");
        return sb.toString();
    }
    /**
     * 执行()
     * @param s String
     * @return boolean
     */
    public boolean exec2(String s)
    {
        list.add(getData());
        setData(s);
        boolean value = exec();
        setData((String) list.remove(list.size() - 1));
        return value;
    }
    /**
     * 执行function
     * @param s String
     * @return String
     */
    public String exef(String s)
    {
        if("length".equalsIgnoreCase(s))
            return "" + parm.getValue(functionParm).length();
        return "";
    }
    /**
     * 执行
     * @return boolean
     */
    public boolean exec()
    {
        boolean oldValue = false;
        String s = getWord();
        boolean value = false;
        while(s.length() > 0)
        {
            while (type == 2)
            {
                value = exec2(s);
                s = getWord();
                if (s.length() == 0)
                    return value && !oldValue;
                if (value && s.equalsIgnoreCase("OR"))
                {
                    if(!oldValue)
                        return true;
                    oldValue = false;
                }
                if (!value && s.equalsIgnoreCase("AND"))
                    oldValue = true;
                s = getWord();
            }
            String v = s;
            if (type == 0 && !isNumber(s))
                v = parm.getValue(s);
            String action = getAction();
            if(action.equals("function"))
            {
                v = exef(s);
                action = getAction();
            }
            s = getWord();
            if (type == 0 && !isNumber(s))
                s = parm.getValue(s);
            value = js(v, s, action);
            s = getWord();
            if(s.length() == 0)
                return value && !oldValue;
            if(value && s.equalsIgnoreCase("OR"))
            {
                if(!oldValue)
                    return true;
                oldValue = false;
            }
            if (!value && s.equalsIgnoreCase("AND"))
                oldValue = true;
            s = getWord();
        }
        return false;
    }
    public boolean isNumber(String s)
    {
        try{
            Integer.parseInt(s);
        }catch(Exception e)
        {
            return false;
        }
        return true;
    }
    /**
     * 计算
     * @param s1 String
     * @param s2 String
     * @param action String
     * @return boolean
     */
    public boolean js(String s1,String s2,String action)
    {
        if(action.equals("="))
        {
            if(s1 == null)
                return false;
            return s1.equals(s2);
        }
        if(action.equals("<>"))
        {
            if(s1 == null)
                return true;
            return !s1.equals(s2);
        }
        if(action.equalsIgnoreCase("like"))
        {
            if(s1 == null)
                return false;
            if(s2.startsWith("%") && s2.endsWith("%") && s2.length() > 1)
                return s1.indexOf(s2.substring(1,s2.length() - 1)) >= 0;
            if(s2.startsWith("%"))
                return s1.endsWith(s2.substring(1));
            if(s2.endsWith("%"))
                return s1.startsWith(s2.substring(0, s2.length() - 1));
            return js(s1,s2,"=");
        }
        if(action.equals("<"))
        {
            if(s1 == null)
                return false;
            try{
                return Double.parseDouble(s1) < Double.parseDouble(s2);
            }catch(Exception e)
            {
                return s1.compareTo(s2) < 0;
            }
        }
        if(action.equals(">"))
        {
            if(s1 == null)
                return false;
            try{
                return Double.parseDouble(s1) > Double.parseDouble(s2);
            }catch(Exception e)
            {
                return s1.compareTo(s2) > 0;
            }
        }
        if(action.equals("<="))
        {
            if(s1 == null)
                return false;
            try{
                return Double.parseDouble(s1) <= Double.parseDouble(s2);
            }catch(Exception e)
            {
                return s1.compareTo(s2) <= 0;
            }
        }
        if(action.equals(">="))
        {
            if(s1 == null)
                return false;
            try{
                return Double.parseDouble(s1) >= Double.parseDouble(s2);
            }catch(Exception e)
            {
                return s1.compareTo(s2) >= 0;
            }
        }
        return true;
    }
    public String getAction()
    {
        String s = getWord();
        if(type == 2)
        {
            functionParm = s;
            return "function";
        }
        if(s.equals("=") || s.equalsIgnoreCase("like"))
            return s;
        if(s.equals("<"))
        {
            if(getData().startsWith(">"))
            {
                setData(getData().substring(1));
                return "<>";
            }
            if(getData().startsWith("="))
            {
                setData(getData().substring(1));
                return "<=";
            }
            return s;
        }
        if(s.equals(">"))
        {
            if(getData().startsWith("="))
            {
                setData(getData().substring(1));
                return ">=";
            }
            return s;
        }
        if(s.equals("!"))
        {
            if(getData().startsWith("="))
            {
                setData(getData().substring(1));
                return "<>";
            }
        }
        ok = false;
        return "";
    }
    public static void main(String args[])
    {
        Script script = new Script();
        TParm parm =new TParm();
        parm.setData("aaa","a1");
        parm.setData("bbb","b1");
        script.setParm(parm);
        script.setData("length(aaa)=3 or aaa='a1'");
        System.out.println(script.exec());
    }
}
