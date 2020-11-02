package com.dongyang.tui.text.div;

import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.PM;

/**
 *
 * <p>Title: ��������ӿ�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.21
 * @version 1.0
 */
public interface DIV
{
    /**
     * ���ø���
     * @param parent MV
     */
    public void setParent(MV parent);
    /**
     * �õ�����
     * @return MV
     */
    public MV getParent();
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM();
    /**
     * ����
     * @param g Graphics
     */
    public void paint(Graphics g);
    /**
     * ��ӡ
     * @param g Graphics
     */
    public void print(Graphics g);
    /**
     * �����Ƿ���ʾ
     * @param visible boolean
     */
    public void setVisible(boolean visible);
    /**
     * �Ƿ���ʾ
     * @return boolean
     */
    public boolean isVisible();
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
     * �õ�����
     * @return String
     */
    public String getType();
    /**
     * �����ԶԻ���
     */
    public void openProperty();
    /**
     * ���ԶԻ����޸Ĳ���
     * @param name String
     */
    public void propertyModify(String name);
    /**
     * �ͷ�
     */
    public void DC();
    /**
     * �õ�����
     * @return int
     */
    public int getTypeID();
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException;
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException;
    /**
     * �����Ƿ��Ǻ�ģ��
     * @return boolean
     */
    public boolean isMacroroutineModel();
    /**
     * �õ�����
     * @param column String
     * @return Object
     */
    public Object getMacroroutineData(String column);
    /**
     * �õ�ҳ��
     * @return int
     */
    public int getPageIndex();
}
