package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.DText;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 宏控制器</p>
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
     * 成员列表
     */
    private TList components;
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 宏是否正在运行
     */
    private boolean isRun;
    /**
     * 构造器
     */
    public MMacroroutine()
    {
        components = new TList();
    }
    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm)
    {
        this.pm = pm;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * 得到显示层
     * @return MView
     */
    public MView getViewManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getViewManager();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        if(getPM() == null)
            return null;
        return getPM().getFocusManager();
    }
    /**
     * 设置焦点位置
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        if(getFocusManager() == null)
            return;
        getFocusManager().setFocusIndex(focusIndex);
    }
    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex()
    {
        if(getFocusManager() == null)
            return 0;
        return getFocusManager().getFocusIndex();
    }
    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getFocus()
    {
        if(getFocusManager() == null)
            return null;
        return getFocusManager().getFocus();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        if(getPM() == null)
            return null;
        return getPM().getUI();
    }
    /**
     * 增加成员
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
     * 删除成员
     * @param model EMacroroutineModel
     */
    public void remove(EMacroroutineModel model)
    {
        components.remove(model);
    }
    /**
     * 全部清除
     */
    public void removeAll()
    {
        components.removeAll();
    }
    /**
     * 成员个数
     * @return int
     */
    public int size()
    {
        return components.size();
    }
    /**
     * 查找位置
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
     * 得到成员
     * @param index int
     * @return EMacroroutineModel
     */
    public EMacroroutineModel get(int index)
    {
        return (EMacroroutineModel) components.get(index);
    }
    /**
     * 得到成员列表
     * @return TList
     */
    public TList getComponentList()
    {
        return components;
    }
    /**
     * 设置是否正在运行
     * @param isRun boolean
     */
    public void setIsRun(boolean isRun)
    {
        this.isRun = isRun;
    }
    /**
     * 是否正在运行
     * @return boolean
     */
    public boolean isRun()
    {
        return isRun;
    }
    /**
     * 显示
     */
    public void show()
    {
        if(isRun())
            run();
        else
            edit();
    }
    /**
     * 运行宏
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
     * 编辑宏
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
     * 写对象
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
     * 写对象
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
     * 读对象
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
        //读取宏模型
        int count = s.readInt();
        for(int i = 0;i < count;i++)
        {
            EMacroroutineModel item = new EMacroroutineModel(this);
            add(item);
            item.readObject(s);
        }
    }
}
