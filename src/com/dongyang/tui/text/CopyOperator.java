package com.dongyang.tui.text;

import com.dongyang.util.StringTool;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>Title: 拷贝对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2010.2.10
 * @version 1.0
 */
public class CopyOperator
{
    private List list = new ArrayList();
    private List now;
    StringBuffer sb;


    private static List comList=new ArrayList();
    private  String tempTextClass=null;
    private  String tempTextValue=null;
    private static String pasteSign= "";
    public static final String MR_NO_SIGN = "MR_NO@";

    /**
     * 设置剪切板
     */
    public void setClipbord(boolean isPreview)
    {
        if(list.size() == 0)
            return;
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < list.size();i++)
        {
            if(i > 0)
                sb.append("\n");
            List l = (List)list.get(i);
            for(int j = 0;j < l.size();j++)
            {
                if(j > 0)
                    sb.append("\n");
                //System.out.println("======内容为：======"+l.get(j));
                if(isPreview){
                   String s=procString(String.valueOf(l.get(j)));
                   //System.out.println("======浏览转化后内容为：======"+s);
                   sb.append(s);
                }else{
                   sb.append(l.get(j));
                }

            }
        }

        StringTool.setClipboard(sb.toString());
    }
    /**
     * 插入块
     */
    public void insertBlock()
    {
        sb = new StringBuffer();
        now = new ArrayList();
        now.add(sb);
        list.add(0,now);
    }
    /**
     * 增加块
     */
    public void addBlock()
    {
        sb = new StringBuffer();
        now = new ArrayList();
        //System.out.println("======now======="+now);
        now.add(sb);
        list.add(now);
    }
    /**
     * 增加行
     */
    public void addRow()
    {
        sb = new StringBuffer();
        now.add(sb);
    }
    /**
     * 增加字符
     * @param s String
     */
    public void addString(String s)
    {
        if(sb == null)
            return;
        sb.append(s);
    }
    public String toString()
    {
        return "" + list;
    }
    /**
     * 清除复制的控件;
     */
    public static void clearComList(){
        comList.clear();
    }
    /**
     * 增加复制控件
     * @param o Object
     */
    public static void addComList(Object o){
        if(o==null)
            return;
        //System.out.println("Object===="+o.getClass());
        comList.add(o);
    }

    public static List getComList(){
        return comList;
    }

    public  void setTempTextClass(String tempTextClass){
        this.tempTextClass=tempTextClass;
    }

    public  String getTempTextClass(){
       return this.tempTextClass;
   }


    public  void setTempTextValue(String tempTextValue){
       this.tempTextValue=tempTextValue;
    }

    public  String getTempTextValue(){
       return this.tempTextValue;
    }

	/**
	 * 取得pasteSign
	 * @return pasteSign
	 */
	public static String getPasteSign() {
		return pasteSign;
	}
	
	/**
	 * 设定pasteSign
	 * @param pasteSign pasteSign
	 */
	public static void setPasteSign(String pasteSign) {
		CopyOperator.pasteSign = pasteSign;
	}
	
	/**
     * 处理特殊字符
     * @param s String
     * @return String
     */
    private String procString(String s) {
        String str = s.replaceAll("\\(|\\)|（|）|【|】|\\{|\\}", "");
        return str;
    }




}
