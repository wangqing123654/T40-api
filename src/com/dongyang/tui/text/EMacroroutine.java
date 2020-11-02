package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.data.TParm;
import java.util.Vector;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.11
 * @version 1.0
 */
public class EMacroroutine extends EText
{
    /**
     * ǰһ������
     */
    private EMacroroutine previousMacroroutine;
    /**
     * ��һ������
     */
    private EMacroroutine nextMacroroutine;
    /**
     * ��ģ��
     */
    private EMacroroutineModel model;
    /**
     * �Ƿ��ͷ�Model
     */
    private boolean freeModel = true;
    /**
     * �Ƿ���ͼƬ
     */
    private boolean isPic;
    
    
    private int sum=0;

    /**
     * ��ʾ�ı�
     */
    String showValue;

    /**
     * ������
     * @param panel EPanel
     */
    public EMacroroutine(EPanel panel)
    {
        super(panel);
    }
    /**
     * �õ��������
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager()
    {
        return getPM().getMacroroutineManager();
    }
    /**
     * ����ǰһ������
     * @param previousMacroroutine EMacroroutine
     */
    public void setPreviousMacroroutine(EMacroroutine previousMacroroutine)
    {
        this.previousMacroroutine = previousMacroroutine;
    }
    /**
     * �õ�ǰһ������
     * @return EMacroroutine
     */
    public EMacroroutine getPreviousMacroroutine()
    {
        return previousMacroroutine;
    }
    /**
     * ������һ������
     * @param nextMacroroutine EMacroroutine
     */
    public void setNextMacroroutine(EMacroroutine nextMacroroutine)
    {
        this.nextMacroroutine = nextMacroroutine;
    }
    /**
     * �õ���һ������
     * @return EMacroroutine
     */
    public EMacroroutine getNextMacroroutine()
    {
        return nextMacroroutine;
    }
    /**
     * �����Ƿ���ͼƬ
     * @param isPic boolean
     */
    public void setIsPic(boolean isPic)
    {
        this.isPic = isPic;
    }
    /**
     * �Ƿ���ͼƬ
     * @return boolean
     */
    public boolean isPic()
    {
        return isPic;
    }
    /**
     * �Ƿ�����״̬
     * @return boolean
     */
    public boolean isPreview()
    {
        return getViewManager().isPreview();
    }
    /**
     * ����
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height)
    {
        if(getModel() != null)
        {
            getModel().setMacroroutineNow(this);
            //���Ʊ���
            getModel().paintBackground(g,x,y, 0);
            getModel().setMacroroutineNow(null);
        }
        if(!isPic())
            super.paint(g,x,y,width,height);
        if(getModel() != null)
        {
            getModel().setMacroroutineNow(this);
            //����ǰ��
            getModel().paintForeground(g,x,y, 0);
            getModel().setMacroroutineNow(null);
        }
    }
    /**
     * ���ƽ��㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFocusBack(Graphics g,int x,int y,int width,int height)
    {
        if(getViewManager().isMacroroutineDebug())
        {
            g.setColor(new Color(240, 168, 168));
            g.drawRect(x, y, width, height);
        }
        if(!isPreview())
        {
            g.setColor(new Color(240, 168, 168));
            g.drawRect(x, y, width, height);
        }
        if(isFocus())
            paintMacroroutineFocusBack(g,x,y,width,height);
    }
    /**
     * ���ƺ��ı����㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintMacroroutineFocusBack(Graphics g,int x,int y,int width,int height)
    {
        if(getModel() != null && getModel().isLockSize())
            return;
        g.setColor(new Color(240, 168, 168));
        g.fillRect(x, y, width, height);
    }
    /**
     * ��ǰ����
     * @return boolean
     */
    public boolean isFocus()
    {
        EComponent com = getFocus();
        if(!(com instanceof EMacroroutine))
            return false;
        EMacroroutine macroroutine = (EMacroroutine)com;
        if(macroroutine == this)
            return true;
        EMacroroutine n = macroroutine.getPreviousMacroroutine();
        while(n != null)
        {
            if(n == this)
                return true;
            n = n.getPreviousMacroroutine();
        }
        n = macroroutine.getNextMacroroutine();
        while(n != null)
        {
            if(n == this)
                return true;
            n = n.getNextMacroroutine();
        }
        return false;
    }
    /**
     * ��ӡ
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g,int x,int y)
    {
        if(getModel() != null)
        {
            getModel().setMacroroutineNow(this);
            //���Ʊ���
            getModel().printBackground(g, x, y, 0);
            getModel().setMacroroutineNow(null);
        }
        if(!isPic())
            super.print(g,x,y);
        if(getModel() != null)
        {
            //����ǰ��
            getModel().printForeground(g, x, y, 0);
            getModel().setMacroroutineNow(null);
        }
    }
    /**
     * �õ��ָ������
     * @param index int
     * @return EText
     */
    public EText getSeparateNewText(int index)
    {
        EMacroroutine macroroutine = getPanel().newMacroroutine(index);
        //��������
        if(getNextMacroroutine() != null)
        {
            macroroutine.setNextMacroroutine(getNextMacroroutine());
            getNextMacroroutine().setPreviousMacroroutine(macroroutine);
        }
        setNextMacroroutine(macroroutine);
        macroroutine.setPreviousMacroroutine(this);

        return macroroutine;
    }
    /**
     * �ܷ�ϲ�
     * @param block IBlock
     * @return boolean
     */
    public boolean unite(IBlock block)
    {
        if(block == null)
            return false;
        if(!(block instanceof EMacroroutine))
            return false;
        EMacroroutine macroroutine = (EMacroroutine)block;
        if(getPreviousMacroroutine() != null && getPreviousMacroroutine() == macroroutine)
        {
            macroroutine.setEnd(getEnd());
            if(getNextMacroroutine() != null)
            {
                macroroutine.setNextMacroroutine(getNextMacroroutine());
                getNextMacroroutine().setPreviousMacroroutine(macroroutine);
            }else
                macroroutine.setNextMacroroutine(null);
            removeThis();
            return true;
        }
        if(getNextMacroroutine() != null && getNextMacroroutine() == macroroutine)
        {
            setEnd(macroroutine.getEnd());
            if(macroroutine.getNextMacroroutine() != null)
            {
                setNextMacroroutine(macroroutine.getNextMacroroutine());
                macroroutine.getNextMacroroutine().setPreviousMacroroutine(this);
            }else
                setNextMacroroutine(null);
            macroroutine.removeThis();
            return true;
        }
        return false;
    }
    /**
     * �õ����ı����׶���
     * @return EMacroroutine
     */
    public EMacroroutine getHeadMacroroutine()
    {
        EMacroroutine n = getPreviousMacroroutine();
        if(n == null)
            return this;
        while(n.getPreviousMacroroutine() != null)
            n = n.getPreviousMacroroutine();
        return n;
    }
    /**
     * �ϲ�ȫ���ĺ�
     * @return EMacroroutine
     */
    public EMacroroutine uniteAll()
    {
        EMacroroutine macroroutine = getHeadMacroroutine();
        while(macroroutine.getNextMacroroutine() != null)
            macroroutine.unite(macroroutine.getNextMacroroutine());
        return macroroutine;
    }
    /**
     * �ܷ�س�������
     * @return boolean
     */
    public boolean canEnterSeparate()
    {
        return false;
    }
    /**
     * ����ģ��
     * @param model EMacroroutineModel
     */
    public void setModel(EMacroroutineModel model)
    {
        this.model = model;
    }
    /**
     * �õ�ģ��
     * @return EMacroroutineModel
     */
    public EMacroroutineModel getModel()
    {
        if(getPreviousMacroroutine() != null)
            return getPreviousMacroroutine().getModel();
        return model;
    }
    /**
     * �����﷨
     * @param index int λ��
     * @param b boolean true ��ֵ false ��ֵ
     */
    public void setModel(int index,boolean b)
    {
        if(index == -1)
            return;
        setModel(getMacroroutineManager().get(index));
        if(b)
            getModel().setMacroroutine(this);
        else
            getModel().addMacroroutine(this);

    }
    /**
     * �����Լ�ģ�͵�λ��
     * @return int
     */
    public int findModelIndex()
    {
        if(getPreviousMacroroutine() != null || getModel() == null)
            return -1;
        return getModel().findIndex();
    }
    /**
     * ����ģ��
     * @return EMacroroutineModel
     */
    public EMacroroutineModel createModel()
    {
        EMacroroutineModel model = new EMacroroutineModel(this);
        setModel(model);
        //֪ͨ�������
        getMacroroutineManager().add(model);
        return model;
    }
    /**
     * ɾ��
     */
    public void delete()
    {
        //�ϲ�ȫ���ĺ�
        EMacroroutine macroroutine = uniteAll();
        macroroutine.setModify(true);
        macroroutine.free();
        macroroutine.removeThis();
    }
    /**
     * �ͷ��Լ�
     */
    public void free()
    {
        if(getPreviousMacroroutine() == null && getModel() != null && isFreeModel())
            getModel().free();

        if(getNextMacroroutine() != null)
            getNextMacroroutine().setPreviousMacroroutine(getPreviousMacroroutine());
        if(getPreviousMacroroutine() != null)
            getPreviousMacroroutine().setNextMacroroutine(getNextMacroroutine());
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        if(isPic())
        {
            Object value = (Object)getPM().getUI().openDialog("%ROOT%\\config\\database\\PictureEditDialog.x",this);
            if(value == null)
                return true;
            setModify(true);
            getFocusManager().update();
            return true;
        }
        if(getModel() == null)
            return false;
        return getModel().onDoubleClickedS(mouseX,mouseY);
    }
    /**
     * �õ�Parm����
     * @return TParm
     */
    public TParm getParmData()
    {
    	//System.out.println("EMacroroutine getParmData===================");
        CTable cTable = getCTable();
        if(cTable == null)
        {
            Object obj = getPM().getFileManager().getParameter();
            if(obj instanceof TParm)
                return (TParm)obj;
            return null;
        }
        //System.out.println("EMacroroutine getCTable().getData()==================="+getCTable().getData());
        
        return getCTable().getData();
    }
    /**
     * �õ�����
     * @param column int
     * @return Object
     */
    public Object getData(int column)
    {
        ETRModel etrModel= getTRModel();
        if(etrModel == null)
            return null;
        switch(etrModel.getType())
        {
        case ETRModel.COLUMN_TYPE:       	
        case ETRModel.COLUMN_MAX:
            return getData(etrModel.getRow(),column);
        case ETRModel.GROUP_HEAD:
        case ETRModel.GROUP_SUM:
            return getData(etrModel.getRow(),etrModel.getEndRow(),column);
        }
        return null;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getDataRow()
    {
        ETRModel etrModel= getTRModel();
        if(etrModel == null)
            return -1;
        
        return etrModel.getRow();
    }
    /**
     * �õ�����
     * @param row int
     * @param column int
     * @return Object
     */
    public Object getData(int row,int column)
    {
        TParm parm = getParmData();
        if(parm == null)
            return null;
        //�����е�ȫ������
        if(row == -1)
            return parm.getData(column);
        
        return parm.getData(row,column);
    }
    /**
     * �õ�����
     * @param column String
     * @return Object
     */
    public Object getData(String column)
    {
        return getData(getDataRow(),column);
    }
    /**
     * �õ�����
     * @param row int
     * @param column String
     * @return Object
     */
    public Object getData(int row,String column)
    {
        TParm parm = getParmData();
        if(parm == null)
            return null;
        //�����е�ȫ������
        if(row == -1)
            return parm.getData(column);

        return parm.getData(column,row);
    }
    /**
     * �õ���������
     * @param startRow int
     * @param endRow int
     * @param column int
     * @return Vector
     */
    public Vector getData(int startRow,int endRow,int column)
    {
        TParm parm = getParmData();
        if(parm == null)
            return null;
        Object obj = parm.getData(column);
        if(obj == null || !(obj instanceof Vector))
            return null;
        Vector v = (Vector)obj;
        return new Vector(v.subList(startRow,endRow));
    }
    public String toString()
    {
        return "<EMacroroutine " +
                "width=" + getWidth() +
                ",height=" + getHeight() +
                ",start=" + getStart() +
                ",end=" + getEnd() +
                ",s=" + getString() +
                ",position=" + getPosition() +
                ",previousMacroroutine=" + (getPreviousMacroroutine()==null?null:getPreviousMacroroutine().getIndexString()) +
                ",nextMacroroutine=" + (getNextMacroroutine()== null?null:getNextMacroroutine().getIndexString()) +
                ",previousLinkText=" + (getPreviousLinkText() == null?null:getPreviousLinkText().getIndexString()) +
                ",nextLinkText=" + (getNextLinkText() == null?null:getNextLinkText().getIndexString()) +
                ",index=" + getIndexString() + ">";
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
        s.WO("<EMacroroutine>",c);
        s.WO(1,"Start",getStart(),"",c + 1);
        s.WO(2,"String",getString(),"",c + 1);
        s.WO(50,"ModelIndex",findModelIndex(),"",c + 1);
        s.WO("</EMacroroutine>",c);
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
        s.writeShort(50);
        s.writeInt(findModelIndex());
        s.writeBoolean(getModel()!=null?(getModel().getMacroroutine() == this):false);
        s.writeBoolean(51,getPreviousMacroroutine() != null,false);
        s.writeBoolean(52,isPic(),false);
        s.writeString(53,getShowValue(),"");
    }
    /**
     * ����������
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        if(super.readObjectAttribute(id,s))
            return true;
        switch (id)
        {
        case 50:
            setModel(s.readInt(),s.readBoolean());
            return true;
        case 51:
            if(s.readBoolean())
            {
                EComponent com = getPreviousComponent();
                if(com != null && com instanceof EMacroroutine)
                {
                    EMacroroutine macroroutine = (EMacroroutine)com;
                    setPreviousMacroroutine(macroroutine);
                    macroroutine.setNextMacroroutine(this);
                }
            }
            return true;
        case 52:
            setIsPic(s.readBoolean());
            return true;
        case 53:
            setShowValue(s.readString());
            return true;
        }
        return false;
    }
    /**
     * �����Ƿ��ͷ�Model
     * @param freeModel boolean
     */
    public void setFreeModel(boolean freeModel)
    {
        this.freeModel = freeModel;
    }
    /**
     * �Ƿ��ͷ�Model
     * @return boolean
     */
    public boolean isFreeModel()
    {
        return freeModel;
    }
    public void modifyFontSize(int size)
    {
        super.modifyFontSize(size);
        if(getModel()!= null)
            getModel().setFont(getStyle().getFont());
    }
    /**
     * ճ���ַ�
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c)
    {
        int index = getFocusIndex();
        //�������λ
        if (index == 0 && getPreviousMacroroutine() == null)
        {
            //���ǰһ������Text����һ��
            EText previousText = getPreviousText();
            if (previousText == null || previousText instanceof EFixed)
            {
                previousText = getPanel().newText(findIndex());
                previousText.setBlock(getStart(), getStart());
            }
            setFocus(previousText,previousText.getLength());
            previousText.pasteChar(c);
            setFocus(this,0);
            return true;
        }
        //�������ĩβ
        if (index == getLength() && getNextMacroroutine() == null)
        {
            //������û��Text����һ��
            EText nextText = getNextText();
            if (nextText == null || nextText instanceof EFixed)
            {
                nextText = getPanel().newText(findIndex() + 1);
                nextText.setBlock(getEnd(), getEnd());
            }
            setFocus(nextText,0);
            return nextText.pasteChar(c);
        }
        //�̶��ı������ܷ�༭
        if (canEdit(index, 1))
            return super.pasteChar(c);

        return false;
    }
    /**
     * ���ı������ܷ�༭
     * @param index int
     * @param type int
     *  1 �����ַ�
     *  2 ��ǰɾ��
     *  3 ���ɾ��
     * @return boolean true �ܱ༭ false ���ܱ༭
     */
    public boolean canEdit(int index,int type)
    {
        return false;
    }
    /**
     * ��ǰɾ��
     * �����ں��ı�������ִ������ɾ��������Ҫ���Σ��������λ�������ƶ�һ��
     * @return boolean
     */
    public boolean backspaceChar()
    {
        int index = getFocusIndex();
        if(index != 0)
        {
            //�̶��ı������ܷ���ǰɾ��
            if(canEdit(index,2))
                return super.backspaceChar();
            setFocusIndex(getFocusIndex() - 1);
            return true;
        }
        return super.backspaceChar();
    }
    /**
     * ���ɾ��
     * �����ں��ı�������ִ������ɾ��������Ҫ���Σ��������λ�������ƶ�һ��
     * @return boolean
     */
    public boolean deleteChar()
    {
        int index = getFocusIndex();
        if(index != getLength())
        {
            //���ı������ܷ����ɾ��
            if(canEdit(index,3))
                return super.deleteChar();
            setFocusIndex(getFocusIndex() + 1);
            return true;
        }
        return super.deleteChar();
    }
    /**
     * �س��¼�
     * �����ں��ı�������ִ�лس����зָ������Ҫ����
     * @return boolean
     */
    public boolean onEnter()
    {
        int index = getFocusIndex();
        if(index == 0 || index == getLength())
            return super.onEnter();
        return true;
    }
    /**
     * ������һ��Text
     * ���ӱ�����
     */
    public void linkText()
    {
    }
    /**
     * �õ�������
     * @return String
     */
    public String getName()
    {
        if(getModel() == null)
            return "";
        return getModel().getName();
    }
    /**
     * �޸ĺ�����
     * @param name String
     */
    public void setName(String name)
    {
        if(getModel() == null)
            return;
        getModel().setName(name);
    }

    /**
     * �ĵ���ʾ����
     * @return String
     */
    public String getShowValue(){
        return showValue;
    }

    /**
     * ������ʾ����
     * @param showValue String
     */
    public void setShowValue(String showValue){
        this.showValue = showValue;
    }
}
