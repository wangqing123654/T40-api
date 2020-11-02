package com.dongyang.ui.text;

import java.util.Map;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.FontMetrics;
import com.dongyang.config.INode;

public interface IElement {
    /**
     * ����ID
     * @param ID String
     */
    public void setID(String ID);
    /**
     * �õ�ID
     * @return String
     */
    public String getID();
    /**
     * ��������
     * @param name String
     */
    public void setName(String name);
    /**
     * �õ�����
     * @return String
     */
    public String getName();
    /**
     * ��������
     * @param type String ��������
     */
    public void setType(String type);
    /**
     * �õ�����
     * @return String ��������
     */
    public String getType();
    /**
     * ������ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible);
    /**
     * �Ƿ���ʾ
     * @return boolean
     */
    public boolean isVisible();
    /**
     * ������Ч
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled);
    /**
     * �Ƿ���Ч
     * @return boolean
     */
    public boolean isEnabled();
    /**
     * ���ø���Ԫ��
     * @param element IElement
     */
    public void setParentElement(IElement element);
    /**
     * �õ�����Ԫ��
     * @return IElement
     */
    public IElement getParentElement();
    /**
      * �����ı�
      * @param text String
      */
     public void setText(String text);
     /**
      * �õ��ı�
      * @return String
      */
     public String getText();
     /**
      * ������ʼҳ��
      * @param page int
      */
     public void setStartPage(int page);
     /**
      * �õ���ʼҳ��
      * @return int
      */
     public int getStartPage();
     /**
      * ���ý���ҳ��
      * @param page int
      */
     public void setEndPage(int page);
     /**
      * �õ�����ҳ��
      * @return int
      */
     public int getEndPage();
     /**
      * �����к�
      * @param rowIndex int
      */
     public void setRowIndex(int rowIndex);
     /**
      * �õ��к�
      * @return int
      */
     public int getRowIndex();
     /**
      * ����x����
      * @param x int
      */
     public void setX(int x);
     /**
      * �õ�x����
      * @return int
      */
     public int getX();
     /**
      * ����y����
      * @param y int
      */
     public void setY(int y);
     /**
      * �õ�y����
      * @return int
      */
     public int getY();
     /**
      * ���ÿ��
      * @param width int
      */
     public void setWidth(int width);
     /**
      * �õ����
      * @return int
      */
     public int getWidth();
     /**
      * ���ø߶�
      * @param height int
      */
     public void setHeight(int height);
     /**
      * �õ��߶�
      * @return int
      */
     public int getHeight();
     /**
      * �õ��ڲ����
      * @return int
      */
     public int getInsideWidth();
     /**
      * �õ��ڲ��߶�
      * @return int
      */
     public int getInsideHeight();
     /**
      * ������߿�
      * @param borderLeft int
      */
     public void setBorderLeft(int borderLeft);
     /**
      * �õ���߿�
      * @return int
      */
     public int getBorderLeft();
     /**
      * �����ұ߿�
      * @param borderRight int
      */
     public void setBorderRight(int borderRight);
     /**
      * �õ��ұ߿�
      * @return int
      */
     public int getBorderRight();
     /**
      * ���ö��߿�
      * @param borderUp int
      */
     public void setBorderUp(int borderUp);
     /**
      * �õ����߿�
      * @return int
      */
     public int getBorderUp();
     /**
      * ���õױ߿�
      * @param borderDown int
      */
     public void setBorderDown(int borderDown);
     /**
      * �õ��ױ߿�
      * @return int
      */
     public int getBorderDown();
     /**
     * װ��Map
     * @param map Map
     */
    public void setMapValue(Map map);
    /**
     * ����Map
     * @param map Map
     */
    public void getMapValue(Map map);
    /**
     * ���ӳ�Ա
     * @param element IElement
     */
    public void addElement(IElement element);
    /**
     * ���ӳ�Ա
     * @param index int λ��
     * @param element IElement
     */
    public void addElement(int index,IElement element);
    /**
     * ɾ����Ա
     * @param index int λ��
     * @return IElement
     */
    public IElement removeElement(int index);
    /**
     * ɾ����Ա
     * @param element IElement
     * @return boolean
     */
    public boolean removeElement(IElement element);
    /**
     * ���ҳ�Ա
     * @param element IElement
     * @return int
     */
    public int elementIndexOf(IElement element);
    /**
     * ���ҳ�Ա
     * @param element IElement
     * @param index int ��ʼλ��
     * @return int
     */
    public int elementIndexOf(IElement element,int index);
    /**
     * ��Ա����
     * @return int
     */
    public int elementSize();
    /**
     * �õ���Ա
     * @param index int
     * @return IElement
     */
    public IElement getElement(int index);
    /**
     * ���ö��뷽ʽ
     * @param align String
     */
    public void setAlign(String align);
    /**
     * �õ����뷽ʽ
     * @return String
     */
    public String getAlign();
    /**
     * ��Ϣ����
     * @param message String
     * @return Object
     */
    public Object callMessage(String message);
    /**
     * ��Ϣ����
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message,Object parm);
    /**
     * ����ͼ��
     * @param g Graphics
     * @param pageIndex int
     */
    public void paint(Graphics g, int pageIndex);
    /**
     * �õ�����ߴ紦�����
     * @param font Font
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics(Font font);
    /**
     * ����xml
     * @param node INode
     */
    public void read(INode node);
    /**
     * ��������
     * @param node INode
     */
    public void readAttribute(INode node);
    /**
     * �õ���һ�����
     * @return IElement
     */
    public IElement getNextElement();
    /**
     * �õ�ǰһ�����
     * @return IElement
     */
    public IElement getPreviousElement();
    /**
     * �õ��Լ��ڸ����е�λ��
     * @return int
     */
    public int getIndex();
    /**
     * �õ��е����߶�
     * @param row int
     * @return int
     */
    public int getRowMaxHeight(int row);
    /**
     * �õ�������
     * @return int
     */
    public int getRowSize();
    /**
     * ������ѳߴ�
     */
    public void fine();
    /**
     * ����XML
     * @param index int
     * @return String
     */
    public String write(int index);
}
