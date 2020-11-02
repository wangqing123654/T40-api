package com.dongyang.ui.base;

import java.util.Vector;
import javax.swing.MutableComboBoxModel;
import java.io.Serializable;
import javax.swing.AbstractListModel;
import com.dongyang.ui.TComboNode;
import com.dongyang.util.StringTool;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.data.TParm;
import java.util.Map;
import java.util.HashMap;

public class TComboBoxModel extends AbstractListModel implements MutableComboBoxModel, Serializable {
    Vector objects;
    Object selectedObject;
    /**
     * ComboBox对象
     */
    private TComboBoxBase comboBox;
    /**
     * 构造器
     */
    public TComboBoxModel()
    {
        setItems(new Vector());
        TComboNode node = new TComboNode();
        //node.setID("aa");
        //node.setName("bb");
        getItems().add(node);
    }
    /**
     * 设置ComboBox对象
     * @param comboBox TComboBoxBase
     */
    public void setComboBox(TComboBoxBase comboBox)
    {
        this.comboBox = comboBox;
    }
    /**
     * 得到ComboBox对象
     * @return TComboBoxBase
     */
    public TComboBoxBase getComboBox()
    {
        return comboBox;
    }
    /**
     * 设置项目值
     * @param parm TParm
     */
    public void setTParmData(TParm parm)
    {
        setItems(new Vector());
        TComboNode nodenull = new TComboNode();
        getItems().add(nodenull);
        if(parm == null)
            return;
        Map map = getParmMap();
        if(map.size() == 0)
            return;
        int count = 0;
        count = parm.getCount();
        if(count <= 0)
            return;
        for(int i = 0; i < count;i++)
        {
            TComboNode node = new TComboNode();
            String name = (String)map.get("ID");
            if(name != null)
                node.setID(parm.getValue(name,i));
            name = (String)map.get("NAME");
            if(name != null)
                node.setName(parm.getValue(name,i));
            name = (String)map.get("ENNAME");
            if(name != null)
                node.setEnName(parm.getValue(name,i));
            name = (String)map.get("TEXT");
            if(name != null)
                node.setText(parm.getValue(name,i));
            name = (String)map.get("VALUE");
            if(name != null)
                node.setValue(parm.getValue(name,i));
            name = (String)map.get("TYPE");
            if(name != null)
                node.setType(parm.getValue(name,i));
            name = (String)map.get("PY1");
            if(name != null)
                node.setPy1(parm.getValue(name,i));
            name = (String)map.get("PY2");
            if(name != null)
                node.setPy2(parm.getValue(name,i));
            getItems().add(node);
        }
    }
    /**
     * 设置数据
     * @param data String[]
     */
    public void setData(String data[])
    {
        setData(data,"");
    }
    /**
     * 设置数据
     * @param data String[]
     * @param fg String 分割符
     */
    public void setData(String data[],String fg)
    {
        if(data == null)
            return;
        for(int i = 0; i < data.length;i++)
            addData(data[i],fg);
    }
    /**
     * 增加数据
     * @param data String
     * @param fg String
     */
    public void addData(String data,String fg)
    {
        if(data == null)
            return;
        TComboNode node = new TComboNode();
        if(fg == null || fg.length() == 0)
        {
            node.setID(data);
            node.setText(data);
        }else
        {
            String s[] = StringTool.parseLine(data, fg);
            node.setID(s[0]);
            if(s.length > 1)
                node.setText(s[1]);
            if(s.length > 2)
                node.setEnText(s[2]);
        }
        getItems().add(node);
    }

    /**
     * 解析对照表
     * @return Map
     */
    public Map getParmMap()
    {
        String parmMap = getComboBox().getParmMap();
        String parms[] = StringTool.parseLine(parmMap,";");
        Map map = new HashMap();
        for(int i = 0;i < parms.length;i++)
        {
            String values[] = StringTool.getHead(parms[i],":");
            map.put(values[0].toUpperCase(),values[1]);
        }
        return map;
    }
    /**
     * 设置项目值
     * @param data Vector
     */
    public void setVectorData(Vector data)
    {
        if(data == null)
            return;
        if(data.size() == 0)
            return;
        if(!(data.get(0) instanceof Vector))
            return;
        Vector names = (Vector)data.get(0);
        setItems(new Vector());
        for(int i = 1;i < data.size();i++)
        {
            Vector row = (Vector)data.get(i);
            if(row == null)
                continue;
            TComboNode node = new TComboNode();
            int count = row.size();
            if(count > names.size())
                count = names.size();
            for(int j = 0;j < count;j++)
            {
                String name = TCM_Transform.getString(names.get(j));
                if("Data".equalsIgnoreCase(name))
                {
                    node.setData(row.get(j));
                    continue;
                }
                String value = TCM_Transform.getString(row.get(j));
                if("ID".equalsIgnoreCase(name))
                    node.setID(value);
                else if("Name".equalsIgnoreCase(name))
                    node.setName(value);
                else if("enName".equalsIgnoreCase(name))
                    node.setEnName(value);
                else if("Text".equalsIgnoreCase(name))
                    node.setText(value);
                else if("Value".equalsIgnoreCase(name))
                    node.setValue(value);
                else if("Py1".equalsIgnoreCase(name))
                    node.setPy1(value);
                else if("Py2".equalsIgnoreCase(name))
                    node.setPy2(value);
                else if("Type".equalsIgnoreCase(name))
                    node.setType(value);
            }
            getItems().add(node);
        }
    }
    /**
     * 设置项目列表
     * @param objects Vector
     */
    public void setItems(Vector objects)
    {
        this.objects = objects;
    }
    /**
     * 得到项目列表
     * @return Vector
     */
    public Vector getItems()
    {
        return objects;
    }
    /**
     * Set the value of the selected item. The selected item may be null.
     * <p>
     * @param anObject The combo box value or null for no selection.
     */
    public void setSelectedItem(Object anObject) {
        if ((selectedObject != null && !selectedObject.equals( anObject )) ||
            selectedObject == null && anObject != null) {
            selectedObject = anObject;
            fireContentsChanged(this, -1, -1);
        }
    }
    // implements javax.swing.ComboBoxModel
    public Object getSelectedItem() {
        if(selectedObject == null)
            selectedObject = objects.get(0);
        return selectedObject;
    }

    // implements javax.swing.ListModel
    public int getSize() {
        return objects.size();
    }

    // implements javax.swing.ListModel
    public Object getElementAt(int index) {
        if ( index < 0 || index >= objects.size() )
            return null;
        return objects.get(index);
    }

    /**
     * 得到对象的位置
     * @param anObject Object
     * @return int
     */
    public int getIndexOf(Object anObject) {
        return objects.indexOf(anObject);
    }

    // implements javax.swing.MutableComboBoxModel
    public void addElement(Object anObject) {
        objects.add(anObject);
        fireIntervalAdded(this,objects.size()-1, objects.size()-1);
        if ( objects.size() == 1 && selectedObject == null && anObject != null ) {
            setSelectedItem( anObject );
        }
    }

    // implements javax.swing.MutableComboBoxModel
    public void insertElementAt(Object anObject,int index) {
        objects.insertElementAt(anObject,index);
        fireIntervalAdded(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElementAt(int index) {
        if ( getElementAt( index ) == selectedObject ) {
            if ( index == 0 ) {
                setSelectedItem( getSize() == 1 ? null : getElementAt( index + 1 ) );
            }
            else {
                setSelectedItem( getElementAt( index - 1 ) );
            }
        }

        objects.removeElementAt(index);

        fireIntervalRemoved(this, index, index);
    }

    // implements javax.swing.MutableComboBoxModel
    public void removeElement(Object anObject) {
        int index = objects.indexOf(anObject);
        if ( index != -1 ) {
            removeElementAt(index);
        }
    }

    /**
     * Empties the list.
     */
    public void removeAllElements() {
        if ( objects.size() > 0 ) {
            int firstIndex = 0;
            int lastIndex = objects.size() - 1;
            objects.removeAllElements();
            selectedObject = null;
            fireIntervalRemoved(this, firstIndex, lastIndex);
        } else {
            selectedObject = null;
        }
    }
}
