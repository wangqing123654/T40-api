package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.DText;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: �������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class MMacroroutine
{
    /**
     * ��Ա�б�
     */
    private TList components;
    /**
     * ������
     */
    private PM pm;
    /**
     * ���Ƿ���������
     */
    private boolean isRun;
    /**
     * ������
     */
    public MMacroroutine()
    {
        components = new TList();
    }
    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * �õ���ʾ��
     * @return MView
     */
    public MView getViewManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getViewManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFocusManager();
    }
    /**
     * ���ý���λ��
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        if(getFocusManager() == null)
            return;
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getFocusIndex()
    {
        if(getFocusManager() == null)
            return 0;
        return getFocusManager().getFocusIndex();
    }
    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getFocus()
    {
        if(getFocusManager() == null)
            return null;
        return getFocusManager().getFocus();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * ���ӳ�Ա
     * @param model EMacroroutineModel
     */
    public void add(EMacroroutineModel model)
    {
        if (model == null)
            return;
        components.add(model);
        model.setManager(this);
    }
    /**
     * ɾ����Ա
     * @param model EMacroroutineModel
     */
    public void remove(EMacroroutineModel model)
    {
        components.remove(model);
    }
    /**
     * ȫ�����
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * ��Ա����
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * ����λ��
     * @param model EMacroroutineModel
     * @return int
     */
    public int indexOf(EMacroroutineModel model)
    {
        if(model == null)
            return -1;
        return components.indexOf(model);
    }

    /**
     * �õ���Ա
     * @param index int
     * @return EMacroroutineModel
     */
    public EMacroroutineModel get(int index)
    {
        return (EMacroroutineModel) components.get(index);
    }
    /**
     * �õ���Ա�б�
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * �����Ƿ���������
     * @param isRun boolean
     */
    public void setIsRun(boolean isRun)
    {
        this.isRun = isRun;
    }
    /**
     * �Ƿ���������
     * @return boolean
     */
    public boolean isRun()
    {
        return isRun;
    }
    /**
     * ��ʾ
     */
    public void show()
    {
        if(isRun())
            run();
        else
            edit();
    }
    /**
     * ���к�
     */
    public void run()
    {
        isRun = true;
        for(int i = 0;i < size();i++)
        {
            EMacroroutineModel model = get(i);
            if(model == null)
                continue;
            model.run();
        }
    }
    /**
     * �༭��
     */
    public void edit()
    {
        if(!isRun)
            return;
        isRun = false;
        for(int i = 0;i < size();i++)
        {
            EMacroroutineModel model = get(i);
            if(model == null)
                continue;
            model.edit();
        }
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
        s.WO("<MMacroroutine>",c);
        s.WO(1,"isRun",isRun(),"",c + 1);
        for(int i = 0;i < size();i ++)
        {
            EMacroroutineModel item = get(i);
            if(item == null)
                continue;
            item.writeObjectDebug(s,c + 1);
        }
        s.WO("</MMacroroutine>",c);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeBoolean(1,isRun(),false);
        s.writeShort( -1);

        s.writeInt(size());
        for(int i = 0;i < size();i ++)
        {
            EMacroroutineModel item = get(i);
            if(item == null)
                continue;
            item.writeObject(s);
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
        while (id > 0)
        {
            switch (id)
            {
            case 1:
                setIsRun(s.readBoolean());
                break;
            }
            id = s.readShort();
        }
        //��ȡ��ģ��
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EMacroroutineModel item = new EMacroroutineModel(this);
            add(item);
            item.readObject(s);
        }
    }
}
