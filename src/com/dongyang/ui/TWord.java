package com.dongyang.ui;

import com.dongyang.ui.base.TWordBase;
import com.dongyang.ui.edit.TAttributeList;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.util.TypeTool;
import com.dongyang.config.TConfigParse.TObject;

/**
 *
 * <p>Title: Word</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.26
 * @author whao 2013~
 * @version 1.0
 */
public class TWord extends TWordBase
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7395426279361218806L;
	
	/** �����ۼ����� */
	static public boolean IsMark = false;


    /**
     * �½�����ĳ�ʼֵ
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        //System.out.println("==object=="+object.getClass());
        if (object == null)
            return;
        super.createInit(object);
        //object.setValue("PageBloderSize", "16");
    }
    /**
     * ������չ����
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data)
    {
        data.add(new TAttribute("FileName","String","","Left"));
        data.add(new TAttribute("Preview","boolean","N","Center"));
        data.add(new TAttribute("PageBorderSize","int","16","Right"));
    }
    /**
     * ��������
     * @param name String ������
     * @param value String ����ֵ
     */
    public void setAttribute(String name,String value)
    {
        if("FileName".equalsIgnoreCase(name))
        {
            setFileName(value);
            getTObject().setValue("FileName",value);
            return;
        }
        if("Preview".equalsIgnoreCase(name))
        {
            setPreview(TypeTool.getBoolean(value));
            getTObject().setValue("Preview",value);
            return;
        }
        if("PageBorderSize".equalsIgnoreCase(name))
        {
            setPageBorderSize(TypeTool.getInt(value));
            getTObject().setValue("PageBorderSize",value);
            return;
        }

        super.setAttribute(name,value);
    }
}
