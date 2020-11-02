package com.dongyang.tui.text;

import java.util.List;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.awt.Color;
/**
 *
 * <p>Title: 脚本管理器</p>
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
public class MSrcipt
{
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 系统关键词
     */
    private static Map systemWordMap = new HashMap();

    static{
        systemWordMap.put("abstract",1);
        systemWordMap.put("boolean",1);
        systemWordMap.put("break",1);
        systemWordMap.put("byte",1);
        systemWordMap.put("case",1);
        systemWordMap.put("catch",1);
        systemWordMap.put("char",1);
        systemWordMap.put("class",1);
        systemWordMap.put("continue",1);
        systemWordMap.put("default",1);
        systemWordMap.put("do",1);
        systemWordMap.put("double",1);
        systemWordMap.put("else",1);
        systemWordMap.put("extends",1);
        systemWordMap.put("false",1);
        systemWordMap.put("final",1);
        systemWordMap.put("finally",1);
        systemWordMap.put("float",1);
        systemWordMap.put("for",1);
        systemWordMap.put("if",1);
        systemWordMap.put("implements",1);
        systemWordMap.put("import",1);
        systemWordMap.put("instanceof",1);
        systemWordMap.put("int",1);
        systemWordMap.put("interface",1);
        systemWordMap.put("long",1);
        systemWordMap.put("native",1);
        systemWordMap.put("new",1);
        systemWordMap.put("null",1);
        systemWordMap.put("package",1);
        systemWordMap.put("private",1);
        systemWordMap.put("protected",1);
        systemWordMap.put("public",1);
        systemWordMap.put("return",1);
        systemWordMap.put("short",1);
        systemWordMap.put("static",1);
        systemWordMap.put("super",1);
        systemWordMap.put("switch",1);
        systemWordMap.put("synchronized",1);
        systemWordMap.put("this",1);
        systemWordMap.put("throw",1);
        systemWordMap.put("throws",1);
        systemWordMap.put("transient",1);
        systemWordMap.put("true",1);
        systemWordMap.put("try",1);
        systemWordMap.put("void",1);
        systemWordMap.put("volatile",1);
        systemWordMap.put("while",1);
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 设置文件名
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * 得到文件名
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager()
    {
        return getPM().getPageManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * 更新
     */
    public void update()
    {
        getFocusManager().update();
    }
    /**
     * 读取文件
     * @return List
     */
    private List loadFile()
    {
        List list = new ArrayList();
        if(fileName == null || fileName.length() == 0)
            return list;
        try{
            FileReader fr = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fr);
            String s = bf.readLine();
            while(s != null)
            {
                list.add(s);
                s = bf.readLine();
            }
            fr.close();
        }catch(Exception e)
        {
        }
        return list;
    }
    /**
     * 得到页面
     * @return EPage
     */
    public EPage getPage()
    {
        return getPageManager().get(0);
    }
    /**
     * 得到字符列表
     * @return List
     */
    public List getStringList()
    {
        List list = new ArrayList();
        EPage page = getPage();
        for(int i = 0;i < page.size();i++)
        {
            EPanel panel = page.get(i);
            String s = panel.getString();
            list.add(s);
        }
        return list;
    }
    /**
     * 加载文件
     */
    public void load()
    {
        List list = loadFile();
        List data = jx(list);
        System.out.println(data);
        createCreate(data);
        update();
    }
    public void createCreate(List list)
    {
        EPage page = getPage();
        page.removeAll();

        for(int i = 0;i < list.size();i++)
        {
            EPanel p = new EPanel(page);
            List row = (List)list.get(i);
            for(int j = 0;j < row.size();j++)
            {
                EText t = new EText(p);
                p.add(t);
                int length = p.getDString().size();
                t.setStart(length);
                t.setEnd(length);
                Word word = (Word)row.get(j);
                t.setString(word.s);
                switch(word.type)
                {
                case 0:
                    t.modifyColor(new Color(0,0,0));
                    break;
                case 1:
                    t.modifyColor(new Color(0,0,128));
                    t.modifyBold(true);
                    break;
                case 2:
                    t.modifyColor(new Color(0,128,0));
                    break;
                case 3:
                    t.modifyColor(new Color(0,0,255));
                    break;
                case 4:
                    t.modifyColor(new Color(255,0,0));
                    break;
                }
            }
            page.add(p);
        }
    }
    /**
     * 解析
     * @param slist List
     * @return List
     */
    public List jx(List slist)
    {
        List list = new ArrayList(slist.size());
        boolean note = false;
        AAA:
        for(int i = 0;i < slist.size();i++)
        {
            List data = new ArrayList();
            String s = (String)slist.get(i);
            if(s.trim().length() == 0)
            {
                Word word = new Word(s,0);
                data.add(word);
                list.add(data);
                continue;
            }
            int start0 = 0;
            int start = 0;
            int end = 0;
            int type = 0;
            boolean note1 = false;
            if(note)
            {
                int index = s.indexOf("*/");
                if(index == -1)
                {
                    Word word = new Word(s,2);
                    data.add(word);
                    list.add(data);
                    continue;
                }else if(index + 2 == s.length())
                {
                    Word word = new Word(s,2);
                    data.add(word);
                    list.add(data);
                    note = false;
                    continue;
                }
            }
            for(int j = 0;j < s.length();j++)
            {
                if(note)
                {
                    int index = s.indexOf("*/");
                    if(index != -1)
                    {
                        if(index != 0)
                        {
                            Word word = new Word(s.substring(0,index + 2), 2);
                            data.add(word);
                        }
                        j = index + 2;
                        start0 = j;
                        start = j;
                        type = 0;
                        note = false;
                    }
                }
                char c = s.charAt(j);
                switch(c)
                {
                case ' ':
                    if(type == 0)
                        continue;
                    if(type == 1)
                    {
                        end = j;
                        type = 2;
                        break;
                    }
                    break;
                case '/':
                    if(j < s.length() - 1 && s.charAt(j + 1) == '/')
                    {
                        note1 = true;
                        if(type == 1)
                        {
                            end = j;
                            type = 2;
                        }else
                            start = j;
                        break;
                    }
                    if(j < s.length() - 1 && s.charAt(j + 1) == '*')
                    {
                        note = true;
                        if(type == 1)
                        {
                            end = j;
                            type = 2;
                        }else
                            start = j;
                        break;
                    }
                    break;
                case '\'':
                case '"':
                    String s1 = s.substring(start0, j);
                    if(type == 1)
                    {
                        int t = getSystemWord(s1);
                        Word word = new Word(s1, t != -1?0:t);
                        data.add(word);
                    }else
                    {
                        Word word = new Word(s1, 0);
                        data.add(word);
                    }
                    int index = getString1(s,j,c);
                    if(index == -1)
                    {
                        s1 = s.substring(j);
                        Word word = new Word(s1, 4);
                        data.add(word);
                        list.add(data);
                        continue AAA;
                    }
                    s1 = s.substring(j,index + 1);
                    Word word = new Word(s1, 3);
                    data.add(word);
                    j = index + 1;
                    start = j;
                    start0 = j;
                    type = 0;
                    break;
                case '%':
                case '|':
                case '+':
                case '-':
                case '*':
                case '<':
                case '>':
                case '?':
                case '~':
                case '(':
                case ')':
                case '=':
                case '{':
                case '}':
                case '[':
                case ']':
                case '.':
                case ',':
                case ';':
                    if(type == 0 && j < s.length() - 1)
                        continue;
                    if(type == 1)
                    {
                        end = j;
                        type = 2;
                        break;
                    }
                    break;
                default:
                    if(type == 0)
                    {
                        start = j;
                        type = 1;
                    }
                }
                if(j == s.length() - 1)
                {
                    if(type == 1)
                    {
                        end = j + 1;
                        type = 2;
                    }
                }
                if(type == 2)
                {
                    if(start0 < start)
                    {
                        String s2 = s.substring(start0, start);
                        Word word = new Word(s2, 0);
                        data.add(word);
                        start0 = start;
                    }
                    String s1 = s.substring(start, end);
                    int t = getSystemWord(s1);
                    Word word = new Word(s1,t == -1?0:t);
                    data.add(word);
                    start0 = end;
                    type = 0;
                }
                if(note)
                {
                    int index = s.indexOf("*/",j + 2);
                    if(index != -1)
                    {
                        j = index + 2;
                        continue;
                    }
                    else
                        note1 = true;
                }
                //如果是//注释
                if(note1)
                {
                    if(start0 < j)
                    {
                        String s2 = s.substring(start0, j);
                        Word word = new Word(s2, 0);
                        data.add(word);
                    }
                    String s2 = s.substring(j, s.length());
                    Word word = new Word(s2, 2);
                    data.add(word);
                    break;
                }
                if(j == s.length() - 1)
                {
                    if(start0 <= s.length() - 1)
                    {
                        String s2 = s.substring(start0, s.length());
                        Word word = new Word(s2, 0);
                        data.add(word);
                    }
                }
            }
            list.add(data);
        }
        return list;
    }
    public int getString1(String s,int i,char c)
    {
        int index = s.indexOf(c,i + 1);
        if(index == -1)
            return -1;
        while(s.charAt(index - 1) == '\\')
        {
            index = s.indexOf(c,index + 1);
            if(index == -1)
                return -1;
        }
        return index;
    }
    public int getSystemWord(String s)
    {
        Object obj = systemWordMap.get(s);
        if(obj == null)
            return -1;
        return (Integer)obj;
    }
    public class Word
    {
        public String s;
        public int type;
        public Word(String s,int type)
        {
            this.s = s;
            this.type = type;
        }
        public String toString()
        {
            return s + " " + type;
        }
    }
}
