package com.dongyang.ui;

import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList;
import javax.print.PrintService;
import java.util.List;
import java.util.ArrayList;
import java.awt.print.PrinterJob;

public class TPrintListCombo extends TComboBox
{
    /**
     * ������
     */
    public TPrintListCombo()
    {
        setCanEdit(true);
        List list = new ArrayList();
        PrintService[] services = PrinterJob.lookupPrintServices();
        for(int i=0;i<services.length;i++){
            list.add(services[i].getName().trim());
        }
        String s[] = (String[])list.toArray(new String[]{});
        setData(s);
    }
    /**
     * �½�����ĳ�ʼֵ
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        if(object == null)
            return;
        object.setValue("Width","350");
        object.setValue("Height","23");
        object.setValue("Editable","Y");
        object.setValue("Tip","ѡ���ӡ��");
    }
    /**
     * �õ�ѡ�д�ӡ��
     * @return PrintService
     */
    public PrintService getSelectPrint()
    {
        String name = getSelectedID();
        PrintService[] services = PrinterJob.lookupPrintServices();
        for(int i=0;i<services.length;i++){
            if(services[i].getName().trim().equals(name))
                return services[i];
        }
        return null;
    }
    /**
     * ������չ����
     * @param data TAttributeList
     */
    public void getEnlargeAttributes(TAttributeList data)
    {
    }
}
