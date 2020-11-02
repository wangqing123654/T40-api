package com.dongyang.tui.text;

import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;

/**
 *
 * <p>Title: ���洢��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.7
 * @author whao 2013~
 * @version 1.0
 */
public abstract class EPanelStore extends EPanelBase
{
    /**
     * �����
     * @return EText
     */
    public EText newText()
    {
        EText text = new EText((EPanel)this);
        add(text);
        return text;
    }
    /**
     * �����
     * @param index int
     * @return EText
     */
    public EText newText(int index)
    {
        EText text = new EText((EPanel)this);
        add(index,text);
        return text;
    }
    /**
     * �½���
     * @return EMacroroutine
     */
    public EMacroroutine newMacroroutine()
    {
        EMacroroutine macroroutine = new EMacroroutine((EPanel)this);
        add(macroroutine);
        return macroroutine;
    }
    /**
     * �½���
     * @param index int
     * @return EMacroroutine
     */
    public EMacroroutine newMacroroutine(int index)
    {
        EMacroroutine macroroutine = new EMacroroutine((EPanel)this);
        add(index,macroroutine);
        return macroroutine;
    }
    /**
     * �½��̶��ı�
     * @return EFixed
     */
    public EFixed newFixed()
    {
        EFixed fixed = new EFixed((EPanel)this);
        add(fixed);
        return fixed;
    }
    /**
     * �½��̶��ı�
     * @param index int
     * @return EFixed
     */
    public EFixed newFixed(int index)
    {
        EFixed fixed = new EFixed((EPanel)this);
        add(index,fixed);
        return fixed;
    }
    /**
     * ������ѡ����
     * @return ESingleChoose
     */
    public ESingleChoose newSingleChoose()
    {
        ESingleChoose single = new ESingleChoose((EPanel)this);
        add(single);
        return single;
    }
    /**
     * ������ѡ����
     * @param index int
     * @return ESingleChoose
     */
    public ESingleChoose newSingleChoose(int index)
    {
        ESingleChoose single = new ESingleChoose((EPanel)this);
        add(index,single);
        return single;
    }
    /**
     * �������ֶ���
     * @return ENumberChoose
     */
    public ENumberChoose newNumberChoose()
    {
        ENumberChoose number = new ENumberChoose((EPanel)this);
        add(number);
        return number;
    }
    /**
     * �������ֶ���
     * @param index int
     * @return ENumberChoose
     */
    public ENumberChoose newNumberChoose(int index)
    {
        ENumberChoose number = new ENumberChoose((EPanel)this);
        add(index,number);
        return number;
    }
    /**
     * ����ѡ���
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose newCheckBoxChoose()
    {
        ECheckBoxChoose checkBoxChoose = new ECheckBoxChoose((EPanel)this);
        add(checkBoxChoose);
        return checkBoxChoose;
    }
    /**
     * ����ѡ���
     * @param index int
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose newCheckBoxChoose(int index)
    {
        ECheckBoxChoose checkBoxChoose = new ECheckBoxChoose((EPanel)this);
        add(index,checkBoxChoose);
        return checkBoxChoose;
    }
    /**
     * ������ѡ����
     * @return EMultiChoose
     */
    public EMultiChoose newMultiChoose()
    {
        EMultiChoose multi = new EMultiChoose((EPanel)this);
        add(multi);
        return multi;
    }
    /**
     * ������ѡ����
     * @param index int
     * @return EMultiChoose
     */
    public EMultiChoose newMultiChoose(int index)
    {
        EMultiChoose multi = new EMultiChoose((EPanel)this);
        add(index,multi);
        return multi;
    }
    /**
     * �������޶���
     * @return EHasChoose
     */
    public EHasChoose newHasChoose()
    {
        EHasChoose has = new EHasChoose((EPanel)this);
        add(has);
        return has;
    }
    /**
     * �������޶���
     * @param index int
     * @return EHasChoose
     */
    public EHasChoose newHasChoose(int index)
    {
        EHasChoose has = new EHasChoose((EPanel)this);
        add(index,has);
        return has;
    }
    /**
     * ����ץȡ����
     * @return ECapture
     */
    public ECapture newCapture()
    {
        ECapture capture = new ECapture((EPanel)this);
        add(capture);
        return capture;
    }
    /**
     * ����ץȡ����
     * @param index int
     * @return ECapture
     */
    public ECapture newCapture(int index)
    {
        ECapture capture = new ECapture((EPanel)this);
        add(index,capture);
        return capture;
    }
    /**
     * ����������ʾ���
     * @return EInputText
     */
    public EInputText newInputText()
    {
        EInputText inputText = new EInputText((EPanel)this);
        add(inputText);
        return inputText;
    }
    /**
     * ����������ʾ���
     * @param index int
     * @return EInputText
     */
    public EInputText newInputText(int index)
    {
        EInputText inputText = new EInputText((EPanel)this);
        add(index,inputText);
        return inputText;
    }
    /**
     * ���������
     * @return EMicroField
     */
    public EMicroField newMicroField()
    {
        EMicroField microField = new EMicroField((EPanel)this);
        add(microField);
        return microField;
    }
    /**
     * ���������
     * @param index int
     * @return EMicroField
     */
    public EMicroField newMicroField(int index)
    {
        EMicroField microField = new EMicroField((EPanel)this);
        add(index,microField);
        return microField;
    }
    
    /**
     * �±��
     * @return ETable
     */
    public ETable newTable()
    {
        ETable table = new ETable((EPanel)this);
        add(table);
        table.setWidth(getWidth());
        return table;
    }
    /**
     * �±��
     * @param index int
     * @return ETable
     */
    public ETable newTable(int index)
    {
        ETable table = new ETable((EPanel)this);
        add(index,table);
        return table;
    }
    /**
     * ��ͼ��
     * @return EPic
     */
    public EPic newPic()
    {
        EPic pic = new EPic((EPanel)this);
        add(pic);
        return pic;
    }
    /**
     * ��ͼ��
     * @param index int
     * @return EPic
     */
    public EPic newPic(int index)
    {
        EPic pic = new EPic((EPanel)this);
        add(index,pic);
        return pic;
    }
    /**
     * ��ͼƬ�༭
     * @return EImage
     */
    public EImage newImage()
    {
        EImage image = new EImage((EPanel)this);
        add(image);
        return image;
    }
    /**
     * ��ͼƬ�༭
     * @param index int
     * @return EImage
     */
    public EImage newImage(int index)
    {
        EImage image = new EImage((EPanel)this);
        add(index,image);
        return image;
    }
    /**
     * д����
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        String link = "";
        if(getNextPageLinkPanel() != null)
        {
            int index = getSavePanelManager().add((EPanel)this);
            getNextPageLinkPanel().setPreviousPanelIndex(index);
        }
        if(getPreviousPageLinkPanel() != null)
        {
            link = " link=" + getPreviousPanelIndex();
        }
        s.WO("<EPanel Width=" + getWidth() + " Height=" + getHeight() + link + " >",c);
        for(int i = 0;i < size();i ++)
        {
            IBlock block = get(i);
            if(block == null)
                continue;
            if(block instanceof EMacroroutine)
            {
                EMacroroutine macroroutine = (EMacroroutine)block;
                macroroutine.writeObjectDebug(s,c+1);
                continue;
            }
            if(block instanceof EText)
            {
                EText text = (EText)block;
                text.writeObjectDebug(s,c+1);
                continue;
            }
            if(block instanceof ETable)
            {
                ETable table = (ETable)block;
                table.writeObjectDebug(s,c+1);
                continue;
            }
        }
        s.WO("</EPanel>",c);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(1,getAlignment(),0);
        if(getNextPageLinkPanel() != null)
        {
            int index = getSavePanelManager().add((EPanel)this);
            getNextPageLinkPanel().setPreviousPanelIndex(index);
            s.writeShort(2);
        }
        if(getPreviousPageLinkPanel() != null)
        {
            s.writeShort(3);
            s.writeInt(getPreviousPanelIndex());
        }
        s.writeInt(4,getParagraphForward(),0);
        s.writeInt(5,getParagraphAfter(),0);
        s.writeInt(6,getSpaceBetween(),0);
        if(getNextLinkPanel() != null)
        {
            int index = getSavePanelManager().add((EPanel)this);
            getNextLinkPanel().setPreviousPanelIndex(index);
            s.writeShort(7);
        }
        if(getPreviousLinkPanel() != null)
        {
            s.writeShort(8);
            s.writeInt(getPreviousPanelIndex());
        }
        s.writeInt(9,getType(),0);
        s.writeInt(10,getRetractLeft(),0);
        s.writeInt(11,getRetractRight(),0);
        s.writeInt(12,getRetractType(),0);
        s.writeInt(13,getRetractWidth(),0);
        s.writeBoolean(14,isElementEdit(),false);
        s.writeInt(15,getSexControl(),0);
        s.writeShort(-1);

        //����ҳ
        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            IBlock block = get(i);
            if(block == null)
                continue;
            if(block instanceof ESign)
            {
                s.writeShort(17);
                ESign sign = (ESign)block;
                sign.writeObject(s);
                continue;
            }
            if(block instanceof EAssociateChoose)
           {
               s.writeShort(16);
               EAssociateChoose eAssociateChoose = (EAssociateChoose)block;
               eAssociateChoose.writeObject(s);
               continue;
           }

            if(block instanceof ETextFormat)
            {
                s.writeShort(15);
                ETextFormat eTextFormat = (ETextFormat)block;
                eTextFormat.writeObject(s);
                continue;
            }
            if(block instanceof EMacroroutine)
            {
                s.writeShort(2);
                EMacroroutine macroroutine = (EMacroroutine)block;
                macroroutine.writeObject(s);
                continue;
            }
            if(block instanceof ESingleChoose)
            {
                s.writeShort(6);
                ESingleChoose single = (ESingleChoose)block;
                single.writeObject(s);
                continue;
            }
            if(block instanceof EMultiChoose)
            {
                s.writeShort(7);
                EMultiChoose multi = (EMultiChoose)block;
                multi.writeObject(s);
                continue;
            }
            if(block instanceof EHasChoose)
            {
                s.writeShort(8);
                EHasChoose has = (EHasChoose)block;
                has.writeObject(s);
                continue;
            }
            if(block instanceof EMicroField)
            {
                s.writeShort(9);
                EMicroField microField = (EMicroField)block;
                microField.writeObject(s);
                continue;
            }
            if(block instanceof EInputText)
            {
                s.writeShort(10);
                EInputText inputText = (EInputText)block;
                inputText.writeObject(s);
                continue;
            }
            if(block instanceof ECapture)
            {
                s.writeShort(11);
                ECapture capture = (ECapture)block;
                capture.writeObject(s);
                continue;
            }
            if(block instanceof ECheckBoxChoose)
            {
                s.writeShort(12);
                ECheckBoxChoose checkBoxChoose = (ECheckBoxChoose)block;
                checkBoxChoose.writeObject(s);
                continue;
            }
            if(block instanceof ENumberChoose)
            {
                s.writeShort(13);
                ENumberChoose numberChoose = (ENumberChoose)block;
                numberChoose.writeObject(s);
                continue;
            }
            if(block instanceof EFixed)
            {
                s.writeShort(5);
                EFixed fixed = (EFixed)block;
                fixed.writeObject(s);
                continue;
            }
            if(block instanceof EText)
            {
                s.writeShort(1);
                EText text = (EText)block;
                text.writeObject(s);
                continue;
            }
            if(block instanceof ETable)
            {
                s.writeShort(3);
                ETable table = (ETable)block;
                table.writeObject(s);
                continue;
            }
            if(block instanceof EPic)
            {
                s.writeShort(4);
                EPic pic = (EPic)block;
                pic.writeObject(s);
            }
            if(block instanceof EImage)
            {
                s.writeShort(14);
                EImage image = (EImage)block;
                image.writeObject(s);
            }
        }
    }

    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while(id > 0)
        {
            switch(id)
            {
            case 1:
                setAlignment(s.readInt());
                break;
            case 2:
                getSavePanelManager().add((EPanel)this);
                break;
            case 3:
                EPanel panel = getSavePanelManager().get(s.readInt());
                setPreviousPageLinkPanel(panel);
                if(panel!=null){
                    panel.setNextPageLinkPanel((EPanel)this);
                }
                break;
            case 4:
                setParagraphForward(s.readInt());
                break;
            case 5:
                setParagraphAfter(s.readInt());
                break;
            case 6:
                setSpaceBetween(s.readInt());
                break;
            case 7:
                getSavePanelManager().add((EPanel)this);
                break;
            case 8:
                panel = getSavePanelManager().get(s.readInt());
                setPreviousLinkPanel(panel);
                panel.setNextLinkPanel((EPanel)this);
                break;
            case 9:
                setType(s.readInt());
                break;
            case 10:
                setRetractLeft(s.readInt());
                break;
            case 11:
                setRetractRight(s.readInt());
                break;
            case 12:
                setRetractType(s.readInt());
                break;
            case 13:
                setRetractWidth(s.readInt());
                break;
            case 14:
                setElementEdit(s.readBoolean());
                break;
            case 15:
                setSexControl(s.readInt());
                break;
            }
            id = s.readShort();
        }
        //��ȡ���
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            int type = s.readShort();
            switch(type)
            {
            case 1:
                EText text = newText();
                text.readObject(s);
                break;
            case 2:
                EMacroroutine macroroutine = newMacroroutine();
                macroroutine.readObject(s);
                break;
            case 3:
                ETable table = newTable();
                table.readObject(s);
                break;
            case 4:
                EPic pic = newPic();
                pic.readObject(s);
                break;
            case 5:
                EFixed fixed = newFixed();
                fixed.readObject(s);
                break;
            case 6:
                ESingleChoose single = newSingleChoose();
                single.readObject(s);
                break;
            case 7:
                EMultiChoose multi = newMultiChoose();
                multi.readObject(s);
                break;
            case 8:
                EHasChoose has = newHasChoose();
                has.readObject(s);
                break;
            case 9:
                EMicroField microField = newMicroField();
                microField.readObject(s);
                break;
            case 10:
                EInputText inputText = newInputText();
                inputText.readObject(s);
                break;
            case 11:
                ECapture capture = newCapture();
                capture.readObject(s);
                break;
            case 12:
                ECheckBoxChoose checkBoxChoose = newCheckBoxChoose();
                checkBoxChoose.readObject(s);
                break;
            case 13:
                ENumberChoose numberChoose = newNumberChoose();
                numberChoose.readObject(s);
                break;
            case 14:
                EImage image = newImage();
                image.readObject(s);
                break;
            case 15:
                ETextFormat  eTextFormat = newTextFormat();
                eTextFormat.readObject(s);
                break;
            case 16:
                EAssociateChoose  eAssociateChoose = newAssociateChoose();
                eAssociateChoose.readObject(s);
                break;
            case 17:
            	ESign  sign = newSign();
            	sign.readObject(s);
                break;

            }
        }
    }
    /**
     * ������ѡ����
     * @param index int
     * @return ESingleChoose
     */
    public ETextFormat newTextFormat(int index)
    {
        ETextFormat eTextFormat = new ETextFormat((EPanel)this);
        add(index,eTextFormat);
        return eTextFormat;
    }
    public ETextFormat newTextFormat()
    {
        ETextFormat eTextFormat = new ETextFormat((EPanel)this);
        add(eTextFormat);
        return eTextFormat;
    }

    /**
     * ��������Ԫ��
     * @param index int
     * @return EAssociateChoose
     */
    public EAssociateChoose newAssociateChoose(int index){
        EAssociateChoose eAssociateChoose = new EAssociateChoose((EPanel)this);
       add(index,eAssociateChoose);
       return eAssociateChoose;

    }

    public EAssociateChoose newAssociateChoose(){
        EAssociateChoose eAssociateChoose = new EAssociateChoose((EPanel)this);
        add(eAssociateChoose);
        return eAssociateChoose;
    }
    
    /**
     * ����ǩ��
     * @param index
     * @return
     */
    public ESign newSign(int index)
    {
    	ESign sign = new ESign((EPanel)this);
        add(index,sign);
        return sign;
    }
    /**
     * 
     * @return
     */
    public ESign newSign()
    {
    	ESign sign = new ESign((EPanel)this);
        add(sign);
        return sign;
    }

}
