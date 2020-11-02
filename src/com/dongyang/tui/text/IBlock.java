package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.util.List;

/**
 *
 * <p>Title: �����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author 2009.4.3
 * @version 1.0
 */
public interface IBlock extends EComponent
{
    /**
     * ����λ��
     * @param position int
     */
    public void setPosition(int position);
    /**
     * �õ�λ��
     * @return int
     */
    public int getPosition();
    /**
     * �����Լ���λ��
     * @return int
     */
    public int findIndex();
    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString();
    /**
     * �õ�·��
     * @param list TList
     */
    public void getPath(TList list);
    /**
     * �����޸�
     * @param i int
     */
    public void debugModify(int i);
    /**
     * ���Զ������
     * @param i int
     */
    public void debugShow(int i);
    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action);
    /**
     * �õ���������
     * @return int
     */
    public int getObjectType();
    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name,int type);
    /**
     * ���˶���
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list,String name,int type);
    /**
     * ������
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group);
    /**
     * �õ���ֵ
     * @param group String
     * @return String
     */
    public String findGroupValue(String group);
    /**
     * �õ���ֵ
     * @return String
     */
    public String getBlockValue();
    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock();
    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getNextTrueBlock();
    /**
     * �õ����
     * @return EPanel
     */
    public EPanel getPanel();
    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel);
    /**
     * ����
     */
    public void arrangement();
}
