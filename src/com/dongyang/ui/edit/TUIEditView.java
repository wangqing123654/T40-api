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
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ѡ�е�ʵ��
     */
    private Vector selectedTobjects;
    /**
     * ��סCtrl��
     */
    private boolean keyCtrl;
    /**
     * ����ѡ��
     */
    private boolean includeSelect;
    /**
     * ��ѡ������
     */
    private boolean selectAreaNow;
    /**
     * �������������
     */
    private String createTComponentName;
    /**
     * �������������
     */
    private Map newTComponentNameIndexMap;
    /**
     * ������
     */
    public TUIEditView()
    {
        selectedTobjects = new Vector();
        newTComponentNameIndexMap = new HashMap();
    }
    /**
     * ���ô������������
     * @param createTComponentName String
     */
    public void setCreateTComponentName(String createTComponentName)
    {
        this.createTComponentName = createTComponentName;
    }
    /**
     * �õ��������������
     * @return String
     */
    public String getCreateTComponentName()
    {
        return createTComponentName;
    }
    /**
     * ���������
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
        //�õ������������
        String newName = getCreateTComponentName(getCreateTComponentName(),tComponent);
        TObject newObj = obj.newItem(newName,getCreateTComponentName());
        setCreateTComponentName(null);
        newObj.setValue("X","" + x);
        newObj.setValue("Y","" + y);
        TComponent newComponent = (TComponent)tComponent.callFunction("addItemFromTObject",newObj,true);
        if(newComponent == null)
            return false;
        //����UI������
        newComponent.callFunction("startUIAdapter");
        callEvent("createTComponent",new Object[]{obj,newObj},
                  new String[]{"com.dongyang.config.TConfigParse$TObject",
                  "com.dongyang.config.TConfigParse$TObject"});

        //ѡ���½��Ķ���
        setSelected(newObj);
        tComponent.callFunction("repaint");
        return true;
    }
    /**
     * �õ������������
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
     * �����Ƿ��ǰ���ѡ��
     * @param includeSelect boolean
     */
    public void setIncludeSelect(boolean includeSelect)
    {
        this.includeSelect = includeSelect;
    }
    /**
     * �Ƿ��ǰ���ѡ��
     * @return boolean
     */
    public boolean isIncludeSelect()
    {
        return includeSelect;
    }
    /**
     * ѡ�е�ʵ�����
     * @return int
     */
    public int getSelectedCount()
    {
        return selectedTobjects.size();
    }
    /**
     * �õ�ѡ�е�ʵ�����
     * @param index int λ��
     * @return TObject
     */
    public TObject getSelected(int index)
    {
        return (TObject)selectedTobjects.get(index);
    }
    /**
     * ȡ��ȫ��ѡ��ʵ��
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
     * ɾ��һ��ѡ��ʵ��
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
     * ����ѡ��ʵ��
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
     * ����ѡ��ʵ��
     * @param tobject TObject
     */
    public void setSelected(TObject tobject)
    {
        //ȡ��ȫ��ѡ��ʵ��
        removeSelectedAll();
        if(tobject == null)
            return;
        //����ѡ��ʵ��
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
     * ����ѡ��ʵ��
     * @param tobject TObject[]
     */
    public void setSelected(TObject tobject[])
    {
        //��ǰ�Ƕ�ѡ������
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
        //��ǰ��ѡ���̽���
        setSelectAreaNow(false);//mutilSelected
        if(getSelectedCount() > 0)
            callEvent("Selected",new Object[]{getSelected(getSelectedCount() - 1)},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
    }
    /**
     * ʵʩ��סCtrl��
     * @param keyCtrl boolean
     */
    public void setKeyCtrl(boolean keyCtrl)
    {
        this.keyCtrl = keyCtrl;
    }
    /**
     * �Ƿ�סCtrl��
     * @return boolean
     */
    public boolean isKeyCtrl()
    {
        return keyCtrl;
    }
    /**
     * ���õ�ǰ�Ƿ����Ƕ�ѡ������
     * @param selectAreaNow boolean
     */
    public void setSelectAreaNow(boolean selectAreaNow)
    {
        this.selectAreaNow = selectAreaNow;
    }
    /**
     * ��ǰ�Ƿ��Ƕ�ѡ������
     * @return boolean
     */
    public boolean isSelectAreaNow()
    {
        return selectAreaNow;
    }
    /**
     *  �õ�key������
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
     * ���̰���
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
     * �ƶ��ؼ�
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
     * ���X���϶�
     * @param difference int ����
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
     * ���Y���϶�
     * @param difference int ����
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
     * �����ȱ��϶�
     * @param difference int ����
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
     * ����߶ȱ��϶�
     * @param difference int ����
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
     * ѡ������
     * @param x1 int
     * @param y1 int
     * @param x2 int
     * @param y2 int
     * @param parentTObject TObject
     */
    public void selectedArea(int x1,int y1,int x2,int y2,TObject parentTObject)
    {
        int count = parentTObject.getItemCount();
        //��ǰ�Ƕ�ѡ������
        setSelectAreaNow(true);
        //���һ��ѡ�еĶ���
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
        //��ǰ��ѡ���̽���
        setSelectAreaNow(false);//mutilSelected
        callEvent("Selected",new Object[]{endSelect},
                  new String[]{"com.dongyang.config.TConfigParse$TObject"});

    }
    /**
     * ����ѡ������
     * @param x int ���x
     * @param y int ���y
     * @param width int ������
     * @param height int ����߶�
     * @param x1 int ��Χ��ʼX
     * @param y1 int ��Χ��ʼY
     * @param x2 int ��Χ����X
     * @param y2 int ��Χ����Y
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
     * ������
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
     * �׶���
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
     * �����
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
     * �Ҷ���
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
     * ˮƽ����
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
     * ��ֱ����
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
     * ͬ��
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
     * ͬ��
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
     * ����ͬ���
     */
    public void onAlignWSpace()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        //����ܿ��
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
     * ����ͬ���
     */
    public void onAlignHSpace()
    {
        int count = getSelectedCount();
        if(count <= 1)
            return;
        //����ܿ��
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
     * ɾ��ѡ�����
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
            //����UI������
            com.callFunction("stopUIAdapter");
            Container container = (Container)com.callFunction("getParent");
            if(container == null)
                continue;
            container.remove((Component)com);
            container.repaint();
            //ɾ������
            parent.deleteItem(tobject);

            callEvent("deleteTComponent",new Object[]{tobject},
                      new String[]{"com.dongyang.config.TConfigParse$TObject"});
        }
    }
    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param methodName String ������
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }

    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},
                         new String[] {"java.lang.Object"});
    }
    /**
     * �齨�޸�Tag
     * @param tobject TObject
     */
    public void modifiedTag(TObject tobject)
    {
        callEvent("modifiedTag",new Object[]{tobject},
                  new String[]{"com.dongyang.config.TConfigParse$TObject"});

    }
}
