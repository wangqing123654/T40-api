package com.dongyang.ui.edit;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse;
import com.dongyang.ui.event.BaseEvent;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.awt.Container;
import java.awt.Component;

public class TUIEditView {
    /**
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 选中的实体
     */
    private Vector selectedTobjects;
    /**
     * 按住Ctrl键
     */
    private boolean keyCtrl;
    /**
     * 包含选择
     */
    private boolean includeSelect;
    /**
     * 多选过程中
     */
    private boolean selectAreaNow;
    /**
     * 创建组件的名称
     */
    private String createTComponentName;
    /**
     * 新组件名称索引
     */
    private Map newTComponentNameIndexMap;
    /**
     * 构造器
     */
    public TUIEditView()
    {
        selectedTobjects = new Vector();
        newTComponentNameIndexMap = new HashMap();
    }
    /**
     * 设置创建组件的名称
     * @param createTComponentName String
     */
    public void setCreateTComponentName(String createTComponentName)
    {
        this.createTComponentName = createTComponentName;
    }
    /**
     * 得到创建组件的名称
     * @return String
     */
    public String getCreateTComponentName()
    {
        return createTComponentName;
    }
    /**
     * 创建新组件
     * @param obj TObject
     * @param x int
     * @param y int
     * @return boolean
     */
    public boolean createTComponent(TObject obj,int x,int y)
    {
        if(obj == null)
            return false;
        TComponent tComponent = obj.getComponent();
        if(tComponent == null)
            return false;
        if(getCreateTComponentName() == null ||
           getCreateTComponentName().length() == 0)
            return false;
        //得到新组件的名称
        String newName = getCreateTComponentName(getCreateTComponentName(),tComponent);
        TObject newObj = obj.newItem(newName,getCreateTComponentName());
        setCreateTComponentName(null);
        newObj.setValue("X","" + x);
        newObj.setValue("Y","" + y);
        TComponent newComponent = (TComponent)tComponent.callFunction("addItemFromTObject",newObj,true);
        if(newComponent == null)
            return false;
        //启动UI适配器
        newComponent.callFunction("startUIAdapter");
        callEvent("createTComponent",new Object[]{obj,newObj},
                  new String[]{"com.dongyang.config.TConfigParse$TObject",
                  "com.dongyang.config.TConfigParse$TObject"});

        //选中新建的对象
        setSelected(newObj);
        tComponent.callFunction("repaint");
        return true;
    }
    /**
     * 得到新组件的名称
     * @param type String
     * @param tComponent TComponent
     * @return String
     */
    public String getCreateTComponentName(String type,TComponent tComponent)
    {
        int index = 0;
        type = type.substring(0,1).toLowerCase() + type.substring(1,type.length());
        Object oldIndex = newTComponentNameIndexMap.get(type);
        if(oldIndex != null)
            index = (Integer)oldIndex + 1;
        String name = type + "_" + index;
        while(name.equalsIgnoreCase((String)tComponent.callMessage(name + "|getTag")))
        {
            index ++;
            name = type + "_" + index;
        }
        newTComponentNameIndexMap.put(type,index);
        return name;
    }
    /**
     * 设置是否是包含选择
     * @param includeSelect boolean
     */
    public void setIncludeSelect(boolean includeSelect)
    {
        this.includeSelect = includeSelect;
    }
    /**
     * 是否是包含选择
     * @return boolean
     */
    public boolean isIncludeSelect()
    {
        return includeSelect;
    }
    /**
     * 选中的实体个数
     * @return int
     */
    public int getSelectedCount()
    {
        return selectedTobjects.size();
    }
    /**
     * 得到选中的实体对象
     * @param index int 位置
     * @return TObject
     */
    public TObject getSelected(int index)
    {
        return (TObject)selectedTobjects.get(index);
    }
    /**
     * 取消全部选中实体
     */
    public void removeSelectedAll()
    {
        int count = getSelectedCount();
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            TUIAdapter adapter = (TUIAdapter)component.callFunction("getUIAdapter");
            if(adapter == null)
                continue;
            adapter.setSelected(false);
        }
        selectedTobjects = new Vector();
    }
    /**
     * 删除一个选中实体
     * @param tobject TObject
     */
    public void removeSelected(TObject tobject)
    {
        if(tobject == null)
            return;
        selectedTobjects.remove(tobject);
        TComponent component = tobject.getComponent();
        if(component == null)
            return;
        TUIAdapter adapter = (TUIAdapter)component.callFunction("getUIAdapter");
        if(adapter == null)
            return;
        adapter.setSelected(false);
        if(!isSelectAreaNow())
            callEvent("undoSelected",new Object[]{tobject},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
    }
    /**
     * 增加选中实体
     * @param tobject TObject
     */
    public void addSelected(TObject tobject)
    {
        if(tobject == null)
            return;
        if(selectedTobjects.indexOf(tobject) != -1)
            return;
        selectedTobjects.add(tobject);
        TComponent component = tobject.getComponent();
        if(component == null)
            return;
        TUIAdapter adapter = (TUIAdapter)component.callFunction("getUIAdapter");
        if(adapter == null)
            return;
        adapter.setSelected(true);
        if(!isSelectAreaNow())
            callEvent("Selected",new Object[]{tobject},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
    }
    /**
     * 设置选中实体
     * @param tobject TObject
     */
    public void setSelected(TObject tobject)
    {
        //取消全部选中实体
        removeSelectedAll();
        if(tobject == null)
            return;
        //增加选中实体
        selectedTobjects.add(tobject);
        TComponent component = tobject.getComponent();
        if(component == null)
            return;
        TUIAdapter adapter = (TUIAdapter)component.callFunction("getUIAdapter");
        if(adapter == null)
            return;
        adapter.setSelected(true);
        if(!isSelectAreaNow())
            callEvent("Selected",new Object[]{tobject},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
    }
    /**
     * 设置选中实体
     * @param tobject TObject[]
     */
    public void setSelected(TObject tobject[])
    {
        //当前是多选过程中
        setSelectAreaNow(true);
        List list = new ArrayList();
        for(int i = 0;i < tobject.length;i++)
            list.add(tobject[i]);
        int count = getSelectedCount();
        for(int i = count - 1;i >= 0;i--)
        {
            TObject object = getSelected(i);
            if(list.indexOf(object) == -1)
            {
                removeSelected(object);
                continue;
            }
            list.remove(object);
        }
        for(int i = 0;i < list.size();i++)
            addSelected((TObject)list.get(i));
        //当前多选过程结束
        setSelectAreaNow(false);//mutilSelected
        if(getSelectedCount() > 0)
            callEvent("Selected",new Object[]{getSelected(getSelectedCount() - 1)},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
    }
    /**
     * 实施按住Ctrl键
     * @param keyCtrl boolean
     */
    public void setKeyCtrl(boolean keyCtrl)
    {
        this.keyCtrl = keyCtrl;
    }
    /**
     * 是否按住Ctrl键
     * @return boolean
     */
    public boolean isKeyCtrl()
    {
        return keyCtrl;
    }
    /**
     * 设置当前是否是是多选过程中
     * @param selectAreaNow boolean
     */
    public void setSelectAreaNow(boolean selectAreaNow)
    {
        this.selectAreaNow = selectAreaNow;
    }
    /**
     * 当前是否是多选过程中
     * @return boolean
     */
    public boolean isSelectAreaNow()
    {
        return selectAreaNow;
    }
    /**
     *  得到key适配器
     * @return KeyListener
     */
    public KeyListener getKeyAdapter()
    {
        return new KeyListener()
        {
            public void keyTyped(KeyEvent e)
            {
                onKeyTyped(e);
            }
            public void keyPressed(KeyEvent e){
                onKeyPressed(e);
            }
            public void keyReleased(KeyEvent e){
                onKeyReleased(e);
            }
        };
    }
    public void onKeyTyped(KeyEvent e)
    {
    }
    /**
     * 键盘按下
     * @param e KeyEvent
     */
    public void onKeyPressed(KeyEvent e){
        setKeyCtrl(e.isControlDown());
        switch (e.getKeyCode()) {
        case 127: //Delete
            onDelete();
            return;
        }
        if(e.isShiftDown())
        {
            switch (e.getKeyCode()) {
            case 38: //UP
                moveTObject(5);
                break;
            case 40: //DOWN:
                moveTObject(6);
                break;
            case 37: //LEFT:
                moveTObject(7);
                break;
            case 39: //RITHT:
                moveTObject(8);
                break;
            }
        }else
        {
            switch (e.getKeyCode()) {
            case 38: //UP
                moveTObject(1);
                break;
            case 40: //DOWN:
                moveTObject(2);
                break;
            case 37: //LEFT:
                moveTObject(3);
                break;
            case 39: //RITHT:
                moveTObject(4);
                break;
            }
        }
    }
    public void onKeyReleased(KeyEvent e){
        setKeyCtrl(e.isControlDown());
    }
    /**
     * 移动控件
     * @param index int
     */
    public void moveTObject(int index)
    {
        int count = getSelectedCount();
        if(count == 0)
            return;
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            int x = (Integer)component.callFunction("getX");
            int y = (Integer)component.callFunction("getY");
            int width = (Integer)component.callFunction("getWidth");
            int height = (Integer)component.callFunction("getHeight");
            switch(index)
            {
            case 1:
                component.callFunction("setY",y - 1);
                break;
            case 2:
                component.callFunction("setY",y + 1);
                break;
            case 3:
                component.callFunction("setX",x - 1);
                break;
            case 4:
                component.callFunction("setX",x + 1);
                break;
            case 5:
                component.callFunction("setHeight",height - 1);
                break;
            case 6:
                component.callFunction("setHeight",height + 1);
                break;
            case 7:
                component.callFunction("setWidth",width - 1);
                break;
            case 8:
                component.callFunction("setWidth",width + 1);
                break;
            }
        }
    }
    /**
     * 组件X被拖动
     * @param difference int 差异
     * @param obj TObject
     */
    public void draggedX(int difference,TObject obj)
    {
        if(difference == 0)
            return;
        int count = getSelectedCount();
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            if(tobject == obj)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            component.callFunction("setX$",difference);
        }
    }
    /**
     * 组件Y被拖动
     * @param difference int 差异
     * @param obj TObject
     */
    public void draggedY(int difference,TObject obj)
    {
        if(difference == 0)
            return;
        int count = getSelectedCount();
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            if(tobject == obj)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            component.callFunction("setY$",difference);
        }
    }
    /**
     * 组件宽度被拖动
     * @param difference int 差异
     * @param obj TObject
     */
    public void draggedWidth(int difference,TObject obj)
    {
        if(difference == 0)
            return;
        int count = getSelectedCount();
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            if(tobject == obj)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            component.callFunction("setWidth$",difference);
        }
    }
    /**
     * 组件高度被拖动
     * @param difference int 差异
     * @param obj TObject
     */
    public void draggedHeight(int difference,TObject obj)
    {
        if(difference == 0)
            return;
        int count = getSelectedCount();
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            if(tobject == obj)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            component.callFunction("setHeight$",difference);
        }
    }
    /**
     * 选择区域
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param parentTObject TObject
     */
    public void selectedArea(int x1,int y1,int x2,int y2,TObject parentTObject)
    {
        int count = parentTObject.getItemCount();
        //当前是多选过程中
        setSelectAreaNow(true);
        //最后一个选中的对象
        TObject endSelect = null;
        for(int i = 0;i < count;i++)
        {
            TObject tobject = parentTObject.getItem(i);
            if(tobject == null)
                continue;
            TComponent component = tobject.getComponent();
            if(component == null)
                continue;
            int x = (Integer)component.callFunction("getX");
            int y = (Integer)component.callFunction("getY");
            int width = (Integer)component.callFunction("getWidth");
            int height = (Integer)component.callFunction("getHeight");
            if(isSelectedPoint(x,y,width,height,x1,y1,x2,y2))
            {
                addSelected(tobject);
                endSelect = tobject;
            }
        }
        //当前多选过程结束
        setSelectAreaNow(false);//mutilSelected
        callEvent("Selected",new Object[]{endSelect},
                  new String[]{"com.dongyang.config.TConfigParse$TObject"});

    }
    /**
     * 计算选中坐标
     * @param x int 组件x
     * @param y int 组件y
     * @param width int 组件宽度
     * @param height int 组件高度
     * @param x1 int 范围起始X
     * @param y1 int 范围起始Y
     * @param x2 int 范围结束X
     * @param y2 int 范围结束Y
     * @return boolean
     */
    public boolean isSelectedPoint(int x,int y,int width,int height,
                                   int x1,int y1,int x2,int y2)
    {
        if(isIncludeSelect())
            return x >= x1 && x + width <= x2 && y >= y1 && y + height <= y2;
        return !(x + width < x1 || x > x2 || y + height < y1 || y > y2);
    }

    /**
     * 顶对齐
     */
    public void onAlignUP()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int y = (Integer)component.callFunction("getY");
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            com.callFunction("setY",y);
        }
    }
    /**
     * 底对齐
     */
    public void onAlignDown()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int data = (Integer)component.callFunction("getY") +
                   (Integer)component.callFunction("getHeight");
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            int y = data - (Integer)com.callFunction("getHeight");
            com.callFunction("setY",y);
        }
    }
    /**
     * 左对齐
     */
    public void onAlignLeft()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int x = (Integer)component.callFunction("getX");
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            com.callFunction("setX",x);
        }
    }
    /**
     * 右对齐
     */
    public void onAlignRight()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int data = (Integer)component.callFunction("getX") +
                   (Integer)component.callFunction("getWidth");
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            int x = data - (Integer)com.callFunction("getWidth");
            com.callFunction("setX",x);
        }
    }
    /**
     * 水平对齐
     */
    public void onAlignWCenter()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int data = (Integer)component.callFunction("getY") +
                   (Integer)component.callFunction("getHeight") / 2;
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            int y = data - (Integer)com.callFunction("getHeight") / 2;
            com.callFunction("setY",y);
        }
    }
    /**
     * 垂直对齐
     */
    public void onAlignHCenter()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int data = (Integer)component.callFunction("getX") +
                   (Integer)component.callFunction("getWidth") / 2;
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            int x = data - (Integer)com.callFunction("getWidth") / 2;
            com.callFunction("setX",x);
        }
    }
    /**
     * 同宽
     */
    public void onAlignWidth()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int width = (Integer)component.callFunction("getWidth");
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            com.callFunction("setWidth",width);
        }
    }
    /**
     * 同高
     */
    public void onAlignHeight()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        TObject tobjectBase = getSelected(0);
        if(tobjectBase == null)
            return;
        TComponent component = tobjectBase.getComponent();
        if(component == null)
            return;
        int height = (Integer)component.callFunction("getHeight");
        for(int i = 1;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            com.callFunction("setHeight",height);
        }
    }
    /**
     * 横向同间隔
     */
    public void onAlignWSpace()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        //组件总宽度
        int sumW = 0;
        int x1 = 0;
        int x2 = 0;
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            int x = (Integer)com.callFunction("getX");
            int w = (Integer)com.callFunction("getWidth");
            sumW += w;
            if(i == 0)
                x1 = x;
            if(x1 > x)
                x1 = x;
            if(x2 < x + w)
                x2 = x + w;
        }

        double space = ((double)(x2 - x1 - sumW)) / ((double)(count - 1));
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if (tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if (com == null)
                continue;
            com.callFunction("setX",x1);
            x1 += (Integer)com.callFunction("getWidth");
            x1 += (int)(space + 0.50001);
        }
    }
    /**
     * 纵向同间隔
     */
    public void onAlignHSpace()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        //组件总宽度
        int sumH = 0;
        int y1 = 0;
        int y2 = 0;
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            int y = (Integer)com.callFunction("getY");
            int h = (Integer)com.callFunction("getHeight");
            sumH += h;
            if(i == 0)
                y1 = y;
            if(y1 > y)
                y1 = y;
            if(y2 < y + h)
                y2 = y + h;
        }

        double space = ((double)(y2 - y1 - sumH)) / ((double)(count - 1));
        for(int i = 0;i < count;i++)
        {
            TObject tobject = getSelected(i);
            if (tobject == null)
                continue;
            TComponent com = tobject.getComponent();
            if (com == null)
                continue;
            com.callFunction("setY",y1);
            y1 += (Integer)com.callFunction("getHeight");
            y1 += (int)(space + 0.50001);
        }
    }
    /**
     * 删除选中组件
     */
    public void onDelete()
    {
        int count = getSelectedCount();
        if(count == 0)
            return;
        for(int i = count - 1;i >= 0;i--)
        {
            TObject tobject = getSelected(i);
            if(tobject == null)
                continue;
            TObject parent = tobject.getParent();
            if(parent == null)
                continue;
            TComponent com = tobject.getComponent();
            if(com == null)
                continue;
            //启动UI适配器
            com.callFunction("stopUIAdapter");
            Container container = (Container)com.callFunction("getParent");
            if(container == null)
                continue;
            container.remove((Component)com);
            container.repaint();
            //删除对象
            parent.deleteItem(tobject);

            callEvent("deleteTComponent",new Object[]{tobject},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
        }
    }
    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }
    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }

    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
    }
    /**
     * 组建修改Tag
     * @param tobject TObject
     */
    public void modifiedTag(TObject tobject)
    {
        callEvent("modifiedTag",new Object[]{tobject},
                  new String[]{"com.dongyang.config.TConfigParse$TObject"});

    }
}
