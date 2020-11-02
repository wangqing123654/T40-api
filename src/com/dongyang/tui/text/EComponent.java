package com.dongyang.tui.text;

import java.awt.Graphics;

/**
 *
 * <p>Title: ����ӿ�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.3.11
 * @version 1.0
 */
public interface EComponent
{
    /**
     * �ı�����
     */
    public static final int TEXT_TYPE = 0;
    /**
     * �������
     */
    public static final int TABLE_TYPE = 1;
    /**
     * ͼ������
     */
    public static final int PIC_TYPE = 2;
    /**
     * �̶��ı�����
     */
    public static final int FIXED_TYPE = 3;
    /**
     * ��ѡ����
     */
    public static final int SINGLE_CHOOSE_TYPE = 4;
    /**
     * ��ѡ����
     */
    public static final int MULTI_CHOOSE_TYPE = 5;
    /**
     * ����ѡ������
     */
    public static final int HAS_CHOOSE_TYPE = 6;
    /**
     * ������ʾ����
     */
    public static final int INPUT_TEXT_TYPE = 7;
    /**
     * ���������
     */
    public static final int MICRO_FIELD_TYPE = 8;
    /**
     * ץȥ��������
     */
    public static final int CAPTURE_TYPE = 9;
    /**
     * ѡ�������
     */
    public static final int CHECK_BOX_CHOOSE_TYPE = 10;
    /**
     * ͼƬ�༭����
     */
    public static final int IMAGE_TYPE = 11;
    /**
     * �̶��ı����͹��˶�������
     */
    public static final int FIXED_ACTION_TYPE = 12;
    /**
     * ����������
     */
    public static final int TEXTFORMAT_TYPE = 13;
    /**
     * ��������
     */
    public static final int ASSOCIATE_CHOOSE_TYPE = 14;
    /**
     * ��������
     */
    public static final int NUMBER_CHOOSE_TYPE = 15;
       
    /**
     * ǩ������
     */
    public static final int SIGN_TYPE = 16;

    /**
     * ���ø���
     * @param parent EComponent
     */
    public void setParent(EComponent parent);
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getParent();
    /**
     * ���ú�����
     * @param x int
     */
    public void setX(int x);
    /**
     * �õ�������
     * @return int
     */
    public int getX();
    /**
     * ����������
     * @param y int
     */
    public void setY(int y);
    /**
     * �õ�������
     * @return int
     */
    public int getY();
    /**
     * ����λ��
     * @param x int
     * @param y int
     */
    public void setLocation(int x,int y);

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
     * �����ߴ�
     */
    public void reset();
    /**
     * �����ߴ�
     * @param index int
     */
    public void reset(int index);
    /**
     * ����
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g,int x,int y,int width,int height);
    /**
     * �����Ƿ��޸�״̬
     * @param modify boolean
     */
    public void setModify(boolean modify);
    /**
     * �Ƿ��޸�
     * @return boolean
     */
    public boolean isModify();
    /**
     * �õ�ҳ�����
     * @return EPage
     */
    public EPage getPage();
}
