package com.dongyang.tui.text;

import com.dongyang.util.StringTool;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * <p>Title: ��������</p>
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
     * ���ü��а�
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
                //System.out.println("======����Ϊ��======"+l.get(j));
                if(isPreview){
                   String s=procString(String.valueOf(l.get(j)));
                   //System.out.println("======���ת��������Ϊ��======"+s);
                   sb.append(s);
                }else{
                   sb.append(l.get(j));
                }

            }
        }

        StringTool.setClipboard(sb.toString());
    }
    /**
     * �����
     */
    public void insertBlock()
    {
        sb = new StringBuffer();
        now = new ArrayList();
        now.add(sb);
        list.add(0,now);
    }
    /**
     * ���ӿ�
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
     * ������
     */
    public void addRow()
    {
        sb = new StringBuffer();
        now.add(sb);
    }
    /**
     * �����ַ�
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
     * ������ƵĿؼ�;
     */
    public static void clearComList(){
        comList.clear();
    }
    /**
     * ���Ӹ��ƿؼ�
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
	 * ȡ��pasteSign
	 * @return pasteSign
	 */
	public static String getPasteSign() {
		return pasteSign;
	}
	
	/**
	 * �趨pasteSign
	 * @param pasteSign pasteSign
	 */
	public static void setPasteSign(String pasteSign) {
		CopyOperator.pasteSign = pasteSign;
	}
	
	/**
     * ���������ַ�
     * @param s String
     * @return String
     */
    private String procString(String s) {
        String str = s.replaceAll("\\(|\\)|��|��|��|��|\\{|\\}", "");
        return str;
    }




}
