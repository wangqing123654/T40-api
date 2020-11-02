package com.dongyang.ui.base;

import com.dongyang.ui.TScrollPane;

import javax.swing.table.TableColumn;

import com.dongyang.ui.TComponent;
import java.util.Map;
import java.util.HashMap;
import javax.swing.event.ChangeEvent;
import java.awt.Component;
import java.awt.Color;
import java.awt.Font;

import com.dongyang.ui.TTableNode;
import javax.swing.table.TableModel;
import com.dongyang.ui.event.TCellEditorListener;
import com.dongyang.util.StringTool;
import com.dongyang.ui.event.TTableEvent;
import java.util.Vector;
import com.dongyang.data.TParm;
import com.dongyang.jdo.TJDOTool;
import com.dongyang.manager.TCM_Transform;
import java.util.Iterator;
import com.dongyang.data.TNull;
import java.sql.Timestamp;
import com.dongyang.data.TModifiedData;
import com.dongyang.jdo.TDataStore;
import com.dongyang.manager.TIOM_Database;
import com.dongyang.ui.event.ActionMessage;
import com.dongyang.jdo.TDS;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.util.TypeTool;
import java.awt.Rectangle;
import java.awt.Point;
import com.dongyang.ui.TComboBox;
import com.dongyang.ui.TTextFormat;

public class TTableBase extends TScrollPane
{
    /**
     * �ڲ�Table����
     */
    private JTableBase table;
    /**
     * ������ ���� "aaa,100,string;bbb,80,double"
     */
    private String header;
    /**
     * Ӣ����ͷ ���� "aaa;bbb;ccc"
     */
    private String enHeader;
    private String[] enHeaderList;
    /**
     * ����
     */
    private String lockColumns;
    /**
     * ����
     */
    private String lockRows;
    /**
     * ����Ԫ��
     */
    private Map lockCell;
    /**
     * ����༭ģʽ����Ĵ���
     */
    private int clickCountToStart = 1;
    /**
     * �ڲ����
     */
    private Map items;
    /**
     * ��ɫ�������
     */
    private int spacingRow = 1;
    /**
     * ��ɫ�������
     */
    private int spacingColumn = 0;
    /**
     * �س����� 0 ���ƶ� 1�������ƶ� 2 �����ƶ�
     */
    private int focusType = 1;
    /**
     * �����б�
     */
    private String focusIndexList;
    /**
     * ������Ծ true ��������β�Զ���Ծ����һ�С�false ����Ծ����һ��
     */
    private boolean focusIndexJump = true;
    /**
     * ��������
     */
    private Map rowColumnType;
    /**
     * ���ݰ�
     */
    private Object data;
    /**
     * ���ݺ�����ʾλ���б�
     */
    private Map horizontalAlignmentMap;
    /**
     * TParm����
     */
    private String parmMap;
    /**
     * Module�ļ���
     */
    private String moduleName;
    /**
     * Module������
     */
    private Map moduleMethodName;
    /**
     * Module����
     */
    private TParm moduleParm;
    /**
     * �����
     */
    private TParm parmValue;
    /**
     * Module��̬����
     */
    private String moduleParmString;
    /**
     * Module������̬����
     */
    private String moduleParmInsertString;
    /**
     * Module����Parm����
     */
    private TParm moduleParmInsert;
    /**
     * Module�޸�Parm����
     */
    private TParm moduleParmUpdate;
    /**
     * Module�޸ľ�̬����
     */
    private String moduleParmUpdateString;
    /**
     * Module��̬����
     */
    private String moduleParmTag;
    /**
     * �޸Ķ�Ӧ�����ǩ�б�
     */
    private String modifyTag;
    /**
     * �Զ����¶���
     */
    private boolean autoModifyObject;
    /**
     * ���ݴ洢
     */
    private TDataStore dataStore;
    /**
     * SQL���
     */
    private String sql;
    /**
     * �Զ��������ݴ洢
     */
    private boolean autoModifyDataStore;
    /**
     * ����Table����
     */
    private String localTableName;
    /**
     * ��ʾ����
     */
    private int showCount;
    /**
     * ���Xλ��(�һ�ˢ��)
     */
    private int mouseX;
    /**
     * ���Yλ��(�һ�ˢ��)
     */
    private int mouseY;
    /**
     * �����
     */
    private int clickedRow;
    /**
     * ��������
     */
    private String filterString;
    /**
     * �����б�
     */
    private ActionMessage actionList;
    /**
     * ��������ɫ�б�
     */
    private Map rowTextColorMap;
    /**
     * ����ɫ�б�
     */
    private Map rowColorMap;
    /**
     * �Ҽ��˵��﷨
     */
    private String popupMenuSyntax;
    /**
     * ����
     */
    private String language;
    /**
     * ���ֶ���
     */
    private String languageMap;
    
    /**
     * ������
     */
    public TTableBase() {
        //��ʼ�������б�
        actionList = new ActionMessage();
        //��ʼ���ڲ����Map
        items = new HashMap();
        //��ʼ����������Map
        rowColumnType = new HashMap();
        //���ݺ�����ʾλ���б�
        horizontalAlignmentMap = new HashMap();
        //����ɫ�б�
        rowColorMap = new HashMap();
        //��������ɫ�б�
        rowTextColorMap = new HashMap();
        TableModel dm = new TTableModel();        
        //
        TTableColumnModel cm = new TTableColumnModel(this);
        TTableSelectionModel sm = new TTableSelectionModel(this);       
        JTableBase table=new JTableBase(dm,cm,sm);        

        setTable(table);

        //������ʾ������
        setCellRenderer(new TTableCellRenderer(this));
        dataStore = new TDS();
    }

    
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        super.initListeners();
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TCellEditorListener.EDITING_CANCELED,"onEditingCanceled");
        addEventListener(getTag() + "->" + TCellEditorListener.EDITING_STOPPED,"onEditingStopped");
        //addEventListener(getTag() + "->" + TTableEvent.CHANGE_VALUE,"onChangeValue");
        addEventListener(getTag() + "->" + TTableEvent.CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TTableEvent.DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TTableEvent.RIGHT_CLICKED,"onRightClicked");

    }
    /**
     * �е���¼�
     * @param row int
     */
    public void onClicked(int row)
    {
        if(row < 0)
            return;
        clickedRow = row;
        exeAction(getClickedAction());
        exeModifyClicked(row);
    }
    /**
     * ��˫���¼�
     * @param row int
     */
    public void onDoubleClicked(int row)
    {
        clickedRow = row;
        exeAction(getDoubleClickedAction());
    }
    /**
     * ���һ��¼�
     * @param row int
     * @param x int
     * @param y int
     */
    public void onRightClicked(int row,int x,int y)
    {
        clickedRow = row;
        mouseX = x;
        mouseY = y;
        exeAction(getRightClickedAction());
        //�Ҽ��˵�
        popupMenu(row,x,y);
    }
    /**
     * �õ����Xλ��(�һ�ˢ��)
     * @return int
     */
    public int getMouseX()
    {
        return mouseX;
    }
    /**
     * �õ����Xλ��(�һ�ˢ��)
     * @return int
     */
    public int getMouseY()
    {
        return mouseY;
    }
    /**
     * �õ������
     * @return int
     */
    public int getClickedRow()
    {
        return clickedRow;
    }
    /**
     * ��ʾDS������ʾ��
     */
    public void setDSOtherValue()
    {
        if(getDataStore() == null)
            return;
        int x[] = getDataStore().getOtherShowRowList();
        if(x == null)
            return;
        for(int i = 0;i < x.length;i++)
        {
            if(x[i] < 0 || x[i] >= getRowCount())
                continue;
            setDSValue(x[i]);
        }
        getDataStore().removeOtherShowRowList();
    }
    /**
     * ֵ�ı��¼�(�ڲ�ʹ��)
     * @param node TTableNode
     */
    public void onChangeValuePrivate(TTableNode node)
    {
        //�Ƿ��Զ��������Զ���
        if(isAutoModifyObject())
            setObjectValue(node.getRow(),node.getColumn(),node.getValue());
        if(isAutoModifyDataStore())
        {
            String columnName = getParmMap(node.getColumn());
            if(columnName == null || columnName.length() == 0)
                columnName = getDataStore().getColumns()[node.getColumn()];
            getDataStore().setItem(node.getRow(),columnName,node.getValue());
            setDSValue(node.getRow());
            setDSOtherValue();
        }
        if(getParmValue() != null)
        {
            String columnName = getParmMap(node.getColumn());
            if(columnName.length() == 0)
                return;
            getParmValue().setData(columnName,node.getRow(),node.getValue());
        }
        exeAction(getChangeValueAction());
    }
    public void onInit()
    {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        Iterator iterator = items.keySet().iterator();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            TComponent tComponent = getItem(name);
            if(tComponent != null)
                tComponent.callMessage("onInit");
        }
        onQuery();
        retrieve();
        //��ʼ��������
        if (getControl() != null)
            getControl().onInit();
        
        System.out.println();
        
    }
    /**
     * �༭ȡ��
     * @param e ChangeEvent
     */
    public void onEditingCanceled(ChangeEvent e)
    {
    }
    /**
     * �༭ֹͣ
     * @param e ChangeEvent
     */
    public void onEditingStopped(ChangeEvent e)
    {
        int row = getSelectedRow();
        int column = getSelectedColumn_();
        //Object obj = getValueAt_(row,column);
        //System.out.println(row + " " + column + " " + obj);

    }
    /**
     * ����ѡ����
     * @param row int
     */
    public void setSelectedRow(int row)
    {
        setRowSelectionInterval(row,row);
        Rectangle r = getTable().getCellRect(row, 0, true);
        Rectangle vr = getViewport().getViewRect();
        Point point = getViewport().getViewPosition();
        if(r.getY() + r.getHeight() - point.getY() > vr.getHeight())
        {
            point.y += r.getY() + r.getHeight() - vr.getHeight() - point.getY();
            getViewport().setViewPosition(point);
        }
        if(r.getY() - point.getY() < 0)
        {
            point.y += r.getY() - point.getY();
            getViewport().setViewPosition(point);
        }
    }
    /**
     * �õ�ѡ����
     * @return int
     */
    public int getSelectedRow()
    {
        if(getTable() == null)
            return -1;
        return getTable().getSelectedRow();
    }
    /**
     * ����ѡ����
     * @param column int
     */
    public void setSelectedColumn(int column)
    {
        setSelectedColumn_(getColumnModel().getColumnIndexNew(column));
    }
    /**
     * �õ�ѡ����
     * @return int
     */
    public int getSelectedColumn()
    {
        return getColumnModel().getColumnIndex(getSelectedColumn_());
    }
    /**
     * �õ�ѡ����(�ڲ�ʹ��)
     * @param column int
     */
    public void setSelectedColumn_(int column)
    {
        setColumnSelectionInterval(column,column);
    }
    /**
     * �õ�ѡ����(�ڲ�ʹ��)
     * @return int
     */
    public int getSelectedColumn_()
    {
        if(getTable() == null)
            return -1;
        return getTable().getSelectedColumn();
    }
    /**
     * ����ѡ���з�Χ
     * @param index0 int
     * @param index1 int
     */
    public void setRowSelectionInterval(int index0,int index1)
    {
        if(getTable() == null)
            return;
        getTable().setRowSelectionInterval(index0,index1);
    }
    /**
     * ������ѡ�з�Χ
     * @param index0 int
     * @param index1 int
     */
    public void setColumnSelectionInterval(int index0,int index1)
    {
        if(getTable() == null)
            return;
        getTable().setColumnSelectionInterval(index0,index1);
    }
    /**
     * ���ѡ��
     */
    public void clearSelection()
    {
        if(getTable() == null)
            return;
        getTable().clearSelection();
    }
    /**
     * �õ�������
     * @return int
     */
    public int getRowCount()
    {
        if(getTable() == null)
            return -1;
        return getTable().getRowCount();
    }
    /**
     * ����ĳ��ĳ�е�ֵ(�ڲ�ʹ��)
     * @param value Object ֵ
     * @param row int �к�
     * @param column int �к�
     */
    public void setValueAt_(Object value,int row,int column)
    {
        acceptText();
        getTable().setValueAt(value,row,column);
    }
    /**
     * �õ�ĳ��ĳ�е�ֵ(�ڲ�ʹ��)
     * @param row int �к�
     * @param column int �к�
     * @return Object
     */
    public Object getValueAt_(int row,int column)
    {
        return getTable().getValueAt(row,column);
    }
    /**
     * ����ĳ��ĳ�е�ֵ
     * @param value Object ֵ
     * @param row int �к�
     * @param column int �к�
     */
    public void setValueAt(Object value,int row,int column)
    {
        setValueAt_(value,row,getColumnModel().getColumnIndexNew(column));
    }
    /**
     * �õ�ĳ��ĳ�е�ֵ
     * @param row int �к�
     * @param column int �к�
     * @return Object
     */
    public Object getValueAt(int row,int column)
    {
        return getValueAt_(row,getColumnModel().getColumnIndexNew(column));
    }
    /**
     * ��������
     * @param lockColumns String
     */
    public void setLockColumns(String lockColumns)
    {
        this.lockColumns = lockColumns;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLockColumns()
    {
        return lockColumns;
    }
    /**
     * ��������
     * @param lockRows String
     */
    public void setLockRows(String lockRows)
    {
        this.lockRows = lockRows;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLockRows()
    {
        return lockRows;
    }
    /**
     * ��������Ԫ��Map
     * @param lockCell Map
     */
    public void setLockCellMap(Map lockCell)
    {
        this.lockCell = lockCell;
    }
    /**
     * �õ�����Ԫ��Map
     * @return Map
     */
    public Map getLockCellMap()
    {
        return lockCell;
    }
    /**
     * ��Ԫ����������
     * @param row int
     * @param b boolean true ����, false ����
     */
    public void setLockCellRow(int row,boolean b)
    {
        for(int i = 0;i < getColumnCount();i++)
            setLockCell(row,i,b);
    }
    /**
     * ��Ԫ����������
     * @param column int
     * @param b boolean true ����, false ����
     */
    public void setLockCellColumn(int column,boolean b)
    {
        for(int i = 0;i < this.getRowCount();i++)
            setLockCell(i,column,b);
    }
    /**
     * ���õ�Ԫ����
     * @param row int
     * @param columnName String
     * @param b boolean true ����, false ����
     */
    public void setLockCell(int row,String columnName,boolean b)
    {
        setLockCell(row,getColumnIndex(columnName),b);
    }
    /**
     * ���õ�Ԫ����
     * @param row int
     * @param column int
     * @param b boolean true ����, false ����
     */
    public void setLockCell(int row,int column,boolean b)
    {
        if(getLockCellMap() == null)
            setLockCellMap(new HashMap());
        if(!b)
        {
            getLockCellMap().remove(row + ":" + column);
            return;
        }
        getLockCellMap().put(row + ":" + column,true);
    }
    /**
     * ��Ԫ���Ƿ�����
     * @param row int
     * @param column int
     * @return boolean
     */
    public boolean isLockCell(int row,int column)
    {
        if(getLockCellMap() == null)
            return false;
        return TypeTool.getBoolean(getLockCellMap().get(row + ":" + column));
    }
    /**
     * �ж�ĳ���Ƿ�����
     * @param column int �к�
     * @return boolean true �� false ����
     */
    public boolean isLockColumn(int column)
    {
        if(getLockColumns() == null||getLockColumns().length() == 0)
            return false;
        if(getLockColumns().equalsIgnoreCase("all"))
            return true;
        String lockColumnsArray[] = StringTool.parseLine(getLockColumns(),",");
        for(int i = 0;i < lockColumnsArray.length;i++)
            if(("" + column).equals(lockColumnsArray[i]))
                return true;
        return false;
    }
    /**
     * �ж�ĳ���Ƿ�����
     * @param row int �к�
     * @return boolean true �� false ����
     */
    public boolean isLockRow(int row)
    {
        if(getLockRows() == null||getLockRows().length() == 0)
            return false;
        String lockRowsArray[] = StringTool.parseLine(getLockRows(),",");
        for(int i = 0;i < lockRowsArray.length;i++)
            if (("" + row).equals(lockRowsArray[i]))
                return true;
        return false;
    }
    /**
     * ���ý���༭ģʽ����Ĵ���
     * @param count int
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }
    /**
     * �õ�����༭ģʽ����Ĵ���
     * @return int
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }
    /**
     * ���ûس���������
     * @param focusType int 0 ���ƶ� 1�������ƶ� 2 �����ƶ�
     */
    public void setFocusType(int focusType)
    {
        this.focusType = focusType;
    }
    /**
     * �õ��س���������
     * @return int 0 ���ƶ� 1�������ƶ� 2 �����ƶ�
     */
    public int getFocusType()
    {
        return focusType;
    }
    /**
     * ���ý���ת���б�
     * @param focusIndexList String
     */
    public void setFocusIndexList(String focusIndexList)
    {
        this.focusIndexList = focusIndexList;
    }
    /**
     * �õ�����ת���б�
     * @return String
     */
    public String getFocusIndexList()
    {
        return focusIndexList;
    }
    /**
     * Enterת�ƽ���
     */
    public void onEnterFocus()
    {
        if(getFocusType() == 0)
            return;
        //�Զ����ƶ�����
        if(userNextFocus())
            return;
        if(getFocusType() == 1)
        {
            downFocus();
            return;
        }
        if(getFocusType() == 2)
        {
            onTabFocus();
            return;
        }
        downFocus();
    }
    /**
     * �����ƶ�����
     */
    public void downFocus()
    {
        int row = getSelectedRow();
        int column = getSelectedColumn_();
        if(row + 1 < getRowCount())
            setSelectedRow(row + 1);
        else
        {
            setSelectedRow(0);
            if(isFocusIndexJump())
            {
                if (column + 1 < getColumnCount())
                    setSelectedColumn_(column + 1);
                else
                    setSelectedColumn_(0);
            }
        }
    }
    /**
     * �û��Զ��役��ת��
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean userNextFocus()
    {
        if(getFocusIndexList() == null || getFocusIndexList().length() == 0)
            return false;
        int index[] = StringTool.parseLineInt(getFocusIndexList(), ",");
        if(index.length == 0)
            return false;
        int row = getSelectedRow();
        int column = getSelectedColumn();
        int x0 = -1;
        int x1 = -1;
        for (int i = 0; i < index.length; i++)
        {
            x0 = index[i];
            if(column < x0)
            {
                x1 = x0;
                break;
            }
            if(column == x0)
            {
                if(i == index.length - 1)
                    break;
                x1 = index[i + 1];
            }
        }
        if(x1 >= getColumnCount())
            x1 = -1;
        if(x1 < 0)
        {
            if(isFocusIndexJump())
            {
                row++;
                if (row >= getRowCount())
                    setSelectedRow(0);
                else
                    setSelectedRow(row);
                setSelectedColumn(index[0]);
            }
            return true;
        }
        setSelectedColumn(x1);
        return true;
    }
    /**
     * tabת�ƽ���
     */
    public void onTabFocus()
    {
        int row = getSelectedRow();
        int column = getSelectedColumn_();
        if(column + 1 < getColumnCount())
            setSelectedColumn_(column + 1);
        else
        {
            setSelectedColumn_(0);
            if(isFocusIndexJump())
            {
                if (row + 1 < getRowCount())
                    setSelectedRow(row + 1);
                else
                    setSelectedRow(0);
            }
        }
    }
    /**
     * �����и�
     * @param rowHeight int
     */
    public void setRowHeight(int rowHeight)
    {
        getTable().setRowHeight(rowHeight);
    }
    /**
     * �õ��и�
     * @return int
     */
    public int getRowHeight()
    {
        return getTable().getRowHeight();
    }
    /**
     * ������������
     * @param row int �к�
     * @param column int �к�
     * @param type String ����
     */
    public void addRowColumnType(int row,int column,String type)
    {
        rowColumnType.put(row + ":" + column,type);
    }
    /**
     * �õ���������
     * @param row int �к�
     * @param column int �к�
     * @return String ����
     */
    public String getRowColumnType(int row,int column)
    {
        return (String)rowColumnType.get(row + ":" + column);
    }
    /**
     * ������������
     * @param s String
     */
    public void setRowColumnTypeData(String s)
    {
        rowColumnType = new HashMap();
        String r[] = StringTool.parseLine(s, ";");
        for(int i = 0; i < r.length;i++)
        {
            String data[] = StringTool.parseLine(r[i], ",");
            if(data.length < 3)
                continue;
            try{
                addRowColumnType(Integer.parseInt(data[0]),
                                 Integer.parseInt(data[1]), data[2]);
            }catch(Exception e)
            {
                err(e.getMessage());
                continue;
            }
        }
    }
    /**
     * ���Ӻ�����ʾλ���趨
     * @param row int
     * @param column int
     * @param type String
     */
    public void addHorizontalAlignment(int row,int column,String type)
    {
        horizontalAlignmentMap.put(row + ":" + column,type);
    }
    /**
     * �õ�������ʾλ���趨
     * @param row int �к�
     * @param column int �к�
     * @return String ����
     */
    public String getHorizontalAlignment(int row,int column)
    {
        return (String)horizontalAlignmentMap.get(row + ":" + column);
    }
    /**
     * ���Ӻ�����ʾλ���趨
     * @param s String
     */
    public void setHorizontalAlignmentData(String s)
    {
        horizontalAlignmentMap = new HashMap();
        String r[] = StringTool.parseLine(s, ";");
        for(int i = 0; i < r.length;i++)
        {
            String data[] = StringTool.parseLine(r[i], ",");
            if(data.length < 3)
                continue;
            try{
                addHorizontalAlignment(Integer.parseInt(data[0]),
                                       Integer.parseInt(data[1]), data[2]);
            }catch(Exception e)
            {
                err(e.getMessage());
                continue;
            }
        }
        getTable().updateUI();
    }
    /**
     * �����ı�
     */
    public void acceptText()
    {
        int column = getSelectedColumn_();
        if(column < 0)
            return;
        getCellEditor(column).getDelegate().stopCellEditing();
    }
    /**
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message)
    {
        if(!super.filterInit(message))
            return false;
        String value[] = StringTool.getHead(message,"=");
        if("data".equalsIgnoreCase(value[0]))
            return false;
        return true;
    }
    /**
     * �����ڲ�Table����
     * @param table JTableBase
     */
    public void setTable(JTableBase table)
    {
        if(this.table == table)
            return;
        if(this.table != null)
            getViewport().remove(this.table);
        
        //test
        //table.getCellRenderer().setFont(new Font("����",0,18));
        //
        this.table = table;
        table.setTable(this);
        //getViewport().setFont(new Font("����",0,18));
        //
        getViewport().setBackground(new Color(255,255,255));
        getViewport().add(table);
        table.setAutoResizeMode(0);
        table.setSelectionMode(0);
    }
    /**
     * �õ��ڲ�Table����
     * @return JTableBase
     */
    public JTableBase getTable()
    {
        return table;
    }
    /**
     * �õ�����ģ�Ͷ���
     * @return TTableModel
     */
    public TTableModel getModel()
    {
        if(getTable() == null)
            return null;
        return (TTableModel)getTable().getModel();
    }
    /**
     * �õ�������
     * @param value String[]
     */
    public void setHeader(String value[])
    {
        TTableModel model = getModel();
        if(model == null)
            return;
        model.setHeader(value);
    }
    /**
     * �õ���ģ�Ͷ���
     * @return TTableColumnModel
     */
    public TTableColumnModel getColumnModel() {
        if(getTable() == null)
            return null;
        return (TTableColumnModel)getTable().getColumnModel();
    }
    /**
     * �õ��ж���
     * @param index int
     * @return TableColumn
     */
    public TableColumn getColumn(int index)
    {
        if(getColumnModel() == null)
            return null;
        return getColumnModel().getColumn(index);
    }
    /**
     * �õ��и���
     * @return int
     */
    public int getColumnCount()
    {
        if(getColumnModel() == null)
            return 0;
        return getColumnModel().getColumnCount();
    }
    /**
     * ��������ʾ����
     * @param index int �����
     * @param text String ����
     */
    public void setColumnText(int index,String text)
    {
        TableColumn column = getColumn(index);
        if(column == null)
            return;
        if(index >= getColumnCount())
            return;
        if(text == null)
            text = "";
        column.setIdentifier(text);
        
    }
    /**
     * �����п��
     * @param index int �����
     * @param width int ���
     */
    public void setColumnWidth(int index,int width)
    {
        TableColumn column = getColumn(index);
        if(column == null)
            return;
        if(index >= getColumnCount())
            return;
        column.setPreferredWidth(width);
    }
    /**
     * ����������
     * @param header String ���� "aaa,100,string;bbb,80,double"
     */
    public void setHeader(String header)
    {
        if(this.header != null && this.header.equals(header))
            return;
        this.header = header;
        String columns[] = StringTool.parseLine(header,';',"''\"\"");
        Vector columnNameData = new Vector();
        Vector columnWidthData = new Vector();
        Vector columnTypeData = new Vector();
        Map columnDataFormatList = new HashMap();
        for(int i = 0;i < columns.length;i++)
        {
            String value[] = StringTool.parseLine(columns[i],',',"''\"\"");
            columnNameData.add(value[0]);
            if(value.length < 2)
                columnWidthData.add(100);
            else
                columnWidthData.add(StringTool.getInt(value[1]));
            if(value.length < 3)
                columnTypeData.add("String");
            else
                columnTypeData.add(value[2]);
            if(value.length < 4)
                continue;
            if ("int".equalsIgnoreCase(value[2])
                ||"double".equalsIgnoreCase(value[2])
                ||"timestamp".equalsIgnoreCase(value[2]))
                columnDataFormatList.put(i, value[3]);

        }
        //���ñ�������
        setHeader((String[])columnNameData.toArray(new String[]{}));
        //���ñ�����
        for(int i = 0; i < columnWidthData.size();i++)
        {
            int width = (int)(Integer)columnWidthData.get(i);
            setColumnWidth(i,width);
        }
        //����ÿһ�еı༭��
        for(int i = 0;i < columns.length;i++)
        {
            TTableCellEditor editor = new TTableCellEditor(this,(String)columnTypeData.get(i));
            getColumn(i).setCellEditor(editor);
            //����Number��ʾ��ʽ�б�
            String dataFormat = (String)columnDataFormatList.get(i);
            if(dataFormat != null && dataFormat.length() > 0)
                editor.setDataFormat(dataFormat);
        }
        //��ʼ���и���
        getColumnModel().initColumnIndex(columns.length);
    }
    /**
     * �õ�������
     * @return String ���� "aaa,100,string;bbb,80,double"
     */
    public String getHeader()
    {
        return header;
    }
    /**
     * �����Զ��ߴ�ģʽ
     * @param mode int ģʽ
     * 0 off
     * 1 Column boundaries
     * 2 Subsequent columns
     * 3 Last column
     * 4 All columns
     */
    public void setAutoResizeMode(int mode)
    {
        if(mode < 0)
            return;
        if(getTable() == null)
            return;
        getTable().setAutoResizeMode(mode);
    }
    /**
     * �õ��Զ��ߴ�ģʽ
     * @return int ģʽ
     * 0 off
     * 1 Column boundaries
     * 2 Subsequent columns
     * 3 Last column
     * 4 All columns
     */
    public int getAutoResizeMode()
    {
        if(getTable() == null)
            return -1;
        return getTable().getAutoResizeMode();
    }
    /**
     * ������ѡ��ģʽ
     * @param mode int ģʽ
     * 0 Single
     * 1 One range
     * 2 Multiple ranges
     */
    public void setSelectionMode(int mode)
    {
        if(mode < 0)
            return;
        if(getTable() == null)
            return;
        getTable().setSelectionMode(mode);
    }
    /**
     * ������ѡ��ģʽ
     * @return int ģʽ
     * 0 Single
     * 1 One range
     * 2 Multiple ranges
     */
    public int getSelectionMode()
    {
        if(getTable() == null)
            return -1;
        return getTable().getSelectionModel().getSelectionMode();
    }
    /**
     * ����������ѡ��
     * @param rowSelectionAllowed boolean
     */
    public void setRowSelectionAllowed(boolean rowSelectionAllowed)
    {
        if(getTable() == null)
            return;
        getTable().setRowSelectionAllowed(rowSelectionAllowed);
    }
    /**
     * ������ѡ��
     * @return boolean
     */
    public boolean getRowSelectionAllowed()
    {
        if(getTable() == null)
            return false;
        return getTable().getRowSelectionAllowed();
    }
    /**
     * ����������ѡ��
     * @param columnSelectionAllowed boolean
     */
    public void setColumnSelectionAllowed(boolean columnSelectionAllowed)
    {
        if(getTable() == null)
            return;
        getTable().setColumnSelectionAllowed(columnSelectionAllowed);
    }
    /**
     * ������ѡ��
     * @return boolean
     */
    public boolean getColumnSelectionAllowed()
    {
        if(getTable() == null)
            return false;
        return getTable().getColumnSelectionAllowed();
    }
    /**
     * ����Parm����
     * @param parmValue TParm
     */
    public void setParmValue(TParm parmValue)
    {
        if(parmValue == null)
            return;
        setValue(parmValue.getVector(checkLanguage(getParmMap())));
        this.parmValue = parmValue;
    }
    /**
     * ���ֶ��ո���
     * @param names String
     * @return String
     */
    private String checkLanguage(String names)
    {
        if(!"en".equals(getLanguage()))
           return names;
        if(getLanguageMap() == null || getLanguageMap().length() == 0)
            return names;
        String list[] = StringTool.parseLine(getLanguageMap(),";");
        for(int i = 0;i < list.length;i++)
        {
            String s[] = StringTool.parseLine(list[i], "|");
            if (s.length == 2)
                names = StringTool.replaceAll(names, s[0], s[1]);
        }
        return names;
    }
    /**
     * �õ�Parm����
     * @return TParm
     */
    public TParm getParmValue()
    {
        return parmValue;
    }
    /**
     * �õ���ʾ���ݰ�
     * @return TParm
     */
    public TParm getShowParmValue()
    {
        TParm parm = new TParm();
        TTableCellRenderer cell = getCellRenderer();
        int count = getRowCount();
        String nameArray[] = StringTool.parseLine(getParmMap(),";");
        for(int row = 0;row < count;row++)
        {
            for(int column = 0;column < nameArray.length;column ++)
            {
                Object value = getValueAt(row, column);
                value = cell.getValue(cell.getType(row,column),value,column);
                parm.addData(nameArray[column],value);
            }
        }
        parm.setCount(count);
        return parm;
    }
    /**
     * �õ���������
     * @param column int
     * @return String
     */
    public String getParmMap(int column)
    {
        String nameArray[] = StringTool.parseLine(getParmMap(),";");
        if(column < 0 || column >= nameArray.length)
            return "";
        return nameArray[column];
    }
    /**
     * �õ���������λ��
     * @param name String ����
     * @return int
     */
    public int getColumnIndex(String name)
    {
        if(name == null || name.length() == 0)
            return -1;
        String nameArray[] = StringTool.parseLine(getParmMap(),";");
        for(int i = 0;i < nameArray.length;i++)
            if(name.equals(nameArray[i]))
                return i;
        return -1;
    }
    /**
     * ������ֵ
     * @param row int �к�
     * @param column int �к�
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setObjectValue(int row,int column)
    {
        Object value = getValueAt(row,column);
        return setObjectValue(row,column,value);
    }
    /**
     * ������ֵ
     * @param row int �к�
     * @param column int �к�
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setObjectValue(int row,int column,Object value)
    {
        TParm parm = getParmValue();
        if(parm == null)
        {
            err("parm is null " + (parm == null));
            return false;
        }
        String columnName = getParmMap(column);
        if(columnName.length() == 0)
        {
            err("column=" + column + " columnName= " + columnName);
            return false;
        }
        Object obj = parm.getData("OBJECT",row);
        if(!(obj instanceof TModifiedData))
        {
            err("TModifiedData " + (obj instanceof TModifiedData));
            return false;
        }
        TModifiedData modified = (TModifiedData)obj;
        return modified.modify(columnName,value);
    }
    /**
     * ����Parm����
     * @param parm TParm
     * @param names String "ID;NAME"
     */
    public void setParmValue(TParm parm,String names)
    {
        if(parm == null)
            return;
        setValue(parm.getVector(names));
        if(getParmMap() == null || !getParmMap().equals(names))
            setParmMap(names);
        this.parmValue = parm;
    }
    /**
     * ����һ�е�����
     * @param row int �к�
     * @param parm TParm
     */
    public void setRowParmValue(int row,TParm parm)
    {
        setRowParmValue(row,parm,getParmMap());
    }
    /**
     * ����һ�е�����
     * @param row int �к�
     * @param parm TParm
     * @param names String ���� "ID;NAME"
     */
    public void setRowParmValue(int row,TParm parm,String names)
    {
        if(parm == null)
            return;
        setRowValue(row,parm.getRowVector(row,names));
    }
    /**
     * ��������
     * @param data Vector
     */
    public void setValue(Vector data)
    {
        if(data == null)
            return;
        TTableModel model = getModel();
        if(model == null)
            return;
        int selectColumn = getSelectedColumn_();
        if(selectColumn >= 0)
            getTable().getTable().getCellEditor(selectColumn).getDelegate().stopCellEditing();
        model.setData(data);
        clearSelection();
        getTable().updateUI();
    }
    /**
     * ����������
     * @param row int �к�
     * @param data Vector ���ݰ�
     */
    public void setRowValue(int row,Vector data)
    {
        int count = getColumnCount();
        if(count > data.size())
            count = data.size();
        if(row < 0 && row >= getRowCount())
            return;
        for(int i = 0;i < count;i++)
        {
            Object value = data.get(i);
            if(value instanceof TNull)
                value = "";
            setValueAt(value, row, i);
        }
    }
    /**
     * ����һ������
     * @param parm TParm ���ݰ�
     * @return int
     */
    public int addRow(TParm parm)
    {
        return addRow(parm,-1);
    }
    /**
     * ����һ������
     * @param parm TParm ���ݰ�
     * @param names String �� "ID;NAME"
     * @return int �����к�
     */
    public int addRow(TParm parm,String names)
    {
        return addRow(parm,-1,names);
    }
    /**
     * ����һ������
     * @param parm TParm ���ݰ�
     * @param rowParm int ���ݰ��к�
     * @return int �����к�
     */
    public int addRow(TParm parm,int rowParm)
    {
        return addRow(parm,rowParm,getParmMap());
    }
    /**
     * ����һ������
     * @param parm TParm ���ݰ�
     * @param rowParm int ���ݰ��к�
     * @param names String �� "ID;NAME"
     * @return int �����к�
     */
    public int addRow(TParm parm,int rowParm,String names)
    {
        return addRow(-1,parm,rowParm,names);
    }
    /**
     * ����һ������
     * @param row int �����к�λ��
     * @param parm TParm ���ݰ�
     * @param names String �� "ID;NAME"
     * @return int �����к�
     */
    public int addRow(int row,TParm parm,String names)
    {
        return addRow(row,parm,-1,names);
    }
    /**
     * ����һ������
     * @param row int �����к�λ��
     * @param parm TParm ���ݰ�
     * @return int �����к�
     */
    public int addRow(int row,TParm parm)
    {
        return addRow(row,parm,-1);
    }
    /**
     * ����һ������
     * @param row int �����к�λ��
     * @param parm TParm ���ݰ�
     * @param rowParm int ���ݰ��к�
     * @return int �����к�
     */
    public int addRow(int row,TParm parm,int rowParm)
    {
        return addRow(row,parm,rowParm,getParmMap());
    }
    /**
     * ����һ������
     * @param row int �����к�λ��
     * @param parm TParm ���ݰ�
     * @param rowParm int ���ݰ��к�
     * @param names String �� "ID;NAME"
     * @return int �����к�
     */
    public int addRow(int row,TParm parm,int rowParm,String names)
    {
        if(parm == null)
            return -1;
        return addRow(row,parm.getRowVector(rowParm,names));
    }
    /**
     * ��������
     * @return int �����к�
     */
    public int addRow()
    {
        return addRow(-1);
    }
    /**
     * ����һ������
     * @param row int �����к�λ��
     * @return int �����к�
     */
    public int addRow(int row)
    {
        if(isAutoModifyDataStore())
        {
            int newRow = getDataStore().insertRow(row);
            return addRow(row,getDataStore().getRowVector(newRow,getParmMap()));
        }
        Vector data = new Vector();
        int count = getColumnCount();
        for(int i = 0;i < count;i++)
            data.add("");
        return addRow(row,data);
    }
    /**
     * ����һ������
     * @param data Vector ���ݰ�
     * @return int �����к�
     */
    public int addRow(Vector data)
    {
        return addRow(-1,data);
    }
    /**
     * ����һ������
     * @param row int �����к�
     * @param data Vector ���ݰ�
     * @return int �����к�
     */
    public int addRow(int row,Vector data)
    {
        return insertRowValue(row,data);
    }
    /**
     * ����һ������
     * @param row int �����к�
     * @param data Vector ���ݰ�
     * @return int �����к�
     */
    public int insertRowValue(int row,Vector data)
    {
        if(data == null)
            return -1;
        TTableModel model = getModel();
        if(model == null)
            return -1;
        acceptText();
        int newRow = model.insertRowValue(row,data);
        if(getParmValue() != null)
        {
            int nRow = getParmValue().insertRow(row);
            for(int i = 0;i < data.size();i++)
            {
                String columnName = getParmMap(i);
                if(columnName.length() == 0)
                    break;
                getParmValue().setData(columnName,nRow,data.get(i));
            }
            int count = getParmValue().getCount();
            if(count < 0)
                count = 0;
            getParmValue().setCount(count + 1);
        }
        getTable().updateUI();
        return newRow;
    }
    /**
     * �����ַ�����
     * @param stringData String
     */
    public void setStringData(String stringData)
    {
        setValue(StringTool.getVector(stringData));
    }
    /**
     * ɾ��ȫ����
     */
    public void removeRowAll()
    {
        for(int i = getRowCount() - 1;i >= 0;i--)
            removeRow(i);
        getTable().updateUI();
    }
    
    /**
     * ɾ����ǰ��
     */
    public void removeRow()
    {
        int row = getSelectedRow();
        if(row < 0)
            return;
        removeRow(row);
    }
    /**
     * ɾ����
     * @param row int �к�
     */
    public void removeRow(int row)
    {
        acceptText();
        getModel().removeRow(row);
        if(getParmValue() != null)
            getParmValue().removeRow(row);
        if(isAutoModifyDataStore())
            getDataStore().deleteRow(row);
    }

    /**
     * �õ�����
     * @return Vector
     */
    public Vector getValue()
    {
        TTableModel model = getModel();
        if(model == null)
            return null;
        return model.getDataVector();
    }
    /**
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        return null;
    }
    /**
     * ����˳��
     * @return String
     */
    public String getDownLoadIndex()
    {
        String s = super.getDownLoadIndex();
        if(s == null)
            s = "";
        if(s.length() > 0)
            s += ",";
        return s + "Header,Item,RowColumnTypeData,Value";
    }
    /**
     * ������Ŀ
     * @param message String
     * @return Object
     */
    public Object setItem(String message) {
        String s[] = StringTool.parseLine(message, ';', "[]{}()");
        for (int i = 0; i < s.length; i++)
            createItem(s[i]);
        return "OK";
    }
    /**
     * ���س�Ա���
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //���ID
        String cid = values[0];
        //�������
        String type = getConfigParm().getConfig().getString(getConfigParm().
                getSystemGroup(), cid + ".type");
        if (type.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(type);
        if (obj == null || !(obj instanceof Component))
            return;
        Component component = (Component) obj;
        if (component instanceof TComponent) {
            TComponent tComponent = (TComponent) component;
            tComponent.setTag(cid);
            tComponent.init(getConfigParm());
            //tComponent.callMessage("onInit");
            addItem(cid,tComponent);
        }
    }
    public TTableCellEditor getCellEditor(int column)
    {
        TableColumn columnObject = getColumn(column);
        if(columnObject == null)
            return null;
        return (TTableCellEditor)columnObject.getCellEditor();
    }
    /**
     * �������
     * @return int
     */
    public int getItemPrivateCount()
    {
        return items.size();
    }
    /**
     * �������
     * @param tag String
     * @param component TComponent
     */
    public void addItem(String tag,TComponent component)
    {
        items.put(tag,component);
    }
    /**
     * �õ����
     * @param tag String
     * @return TComponent
     */
    public TComponent getItem(String tag)
    {
        return (TComponent)items.get(tag);
    }
    /**
     * ������ʾ������
     * @param cellRenderer TTableCellRenderer
     */
    public void setCellRenderer(TTableCellRenderer cellRenderer)
    {
        JTableBase table = getTable();
        if(table == null)
            return;
        table.setCellRenderer(cellRenderer);
    }
    /**
     * �õ���ʾ��
     * @return TTableCellRenderer
     */
    public TTableCellRenderer getCellRenderer()
    {
        JTableBase table = getTable();
        if(table == null)
            return null;
        return table.getCellRenderer();
    }
    /**
     * ���÷�ɫ�������
     * @param spacingRow int
     */
    public void setSpacingRow(int spacingRow)
    {
        this.spacingRow = spacingRow;
        updateUI();
    }
    /**
     * �õ���ɫ�������
     * @return int
     */
    public int getSpacingRow()
    {
        return spacingRow;
    }
    /**
     * ���÷�ɫ�������
     * @param spacingColumn int
     */
    public void setSpacingColumn(int spacingColumn)
    {
        this.spacingColumn = spacingColumn;
        updateUI();
    }
    /**
     * �õ���ɫ�������
     * @return int
     */
    public int getSpacingColumn()
    {
        return spacingColumn;
    }
    /**
     * �����б�����ɫ
     * @param column int �к�
     * @param color Color ��ɫ
     */
    public void setColumnBackColor(int column,Color color)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setColumnBackColor(column,color);
    }
    /**
     * �õ��б�����ɫ
     * @param column int �к�
     * @return Color ��ɫ
     */
    public Color getColumnBackColor(int column)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return null;
        return cellRenderer.getColumnBackColor(column);
    }
    /**
     * �����еı�����ɫ
     * @param data String "1:255,0,0;2:��"
     */
    public void setColumnBackColorData(String data)
    {
        String s[] = StringTool.parseLine(data, ';', "[]{}()");
        for(int i = 0;i < s.length;i++)
        {
            String sdata = s[i].trim();
            if(sdata.length() == 0)
                continue;
            String cdata[] = StringTool.getHead(sdata,":");
            int column = 0;
            try{
                column = Integer.parseInt(cdata[0]);
            }catch(Exception e)
            {
                continue;
            }
            Color color = StringTool.getColor(cdata[1],getConfigParm());
            if(color == null)
                continue;
            setColumnBackColor(column,color);
        }
    }
    /**
     * ������ǰ����ɫ
     * @param column int �к�
     * @param color Color ��ɫ
     */
    public void setColumnForeColor(int column,Color color)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setColumnForeColor(column,color);
    }
    /**
     * �õ���ǰ����ɫ
     * @param column int �к�
     * @return Color ��ɫ
     */
    public Color getColumnForeColor(int column)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return null;
        return cellRenderer.getColumnForeColor(column);
    }
    /**
     * �����е�ǰ����ɫ
     * @param data String "1:255,0,0;2:��"
     */
    public void setColumnForeColorData(String data)
    {
        String s[] = StringTool.parseLine(data, ';', "[]{}()");
        for(int i = 0;i < s.length;i++)
        {
            String sdata = s[i].trim();
            if(sdata.length() == 0)
                continue;
            String cdata[] = StringTool.getHead(sdata,":");
            int column = 0;
            try{
                column = Integer.parseInt(cdata[0]);
            }catch(Exception e)
            {
                continue;
            }
            Color color = StringTool.getColor(cdata[1],getConfigParm());
            if(color == null)
                continue;
            setColumnForeColor(column,color);
        }
    }
    public void setColumnHorizontalAlignment(int column,int alignment)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setColumnHorizontalAlignment(column,alignment);
    }
   
    /**
     * ���������ֵĺ���λ��
     * @param data String
     */
    public void setColumnHorizontalAlignmentData(String data)
    {
        String s[] = StringTool.parseLine(data, ';', "[]{}()");
        for(int i = 0;i < s.length;i++)
        {
            String sdata = s[i].trim();
            if (sdata.length() == 0)
                continue;
            String cdata[] = StringTool.getHead(sdata, ",");
            int column = 0;
            try
            {
                column = Integer.parseInt(cdata[0]);
            } catch (Exception e)
            {
                continue;
            }
            int alignment = StringTool.horizontalAlignment(cdata[1]);
            setColumnHorizontalAlignment(column,alignment);
        }
        getTable().updateUI();
    }
    /**
     * �������ݰ�
     * @param data Object
     */
    public void setData(Object data)
    {
        this.data = data;
    }
    /**
     * �õ����ݰ�
     * @return Object
     */
    public Object getData()
    {
        return data;
    }
    /**
     * ���ý�����Ծ
     * @param focusIndexJump boolean��true ��������β�Զ���Ծ����һ�С�false ����Ծ����һ��
     */
    public void setFocusIndexJump(boolean focusIndexJump)
    {
        this.focusIndexJump = focusIndexJump;
    }
    /**
     * �õ�������Ծ
     * @return boolean��true ��������β�Զ���Ծ����һ�С�false ����Ծ����һ��
     */
    public boolean isFocusIndexJump()
    {
        return focusIndexJump;
    }
    /**
     * ����TParm����
     * @param parmMap String
     */
    public void setParmMap(String parmMap)
    {
        this.parmMap = parmMap;
    }
    /**
     * �õ�TParm����
     * @return String
     */
    public String getParmMap()
    {
        return parmMap;
    }
    /**
     * ����Module�ļ���
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * �õ�Module�ļ���
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
    }
    /**
     * ����Module������
     * @param names String
     */
    public void setModuleMethodName(String names)
    {
        if(moduleMethodName == null)
            moduleMethodName = new HashMap();
        String s[] = StringTool.parseLine(names,";");
        for(int i = 0; i < s.length;i++)
        {
            String v[] = StringTool.parseLine(s[i],":");
            if(v.length < 2)
                continue;
            moduleMethodName.put(v[0].toUpperCase(),v[1]);
        }
    }
    /**
     * �õ�Module������
     * @param name String
     * @return String
     */
    public String getModuleMethodName(String name)
    {
        if(moduleMethodName == null)
            return null;
        return (String)moduleMethodName.get(name);
    }
    /**
     * ����Module����
     * @param moduleParm TParm
     */
    public void setModuleParm(TParm moduleParm)
    {
        this.moduleParm = moduleParm;
    }
    /**
     * �õ�Module����
     * @return TParm
     */
    public TParm getModuleParm()
    {
        return moduleParm;
    }
    /**
     * ����Module��̬����
     * @param moduleParmString String ����"ID:01;AA:<I>12"
     */
    public void setModuleParmString(String moduleParmString)
    {
        this.moduleParmString = moduleParmString;
    }
    /**
     * �õ�Module��̬����
     * @return String
     */
    public String getModuleParmString()
    {
        return moduleParmString;
    }
    /**
     * ����Module��̬����
     * @param moduleParmTag String "ID:String:T_ID:L;"
     */
    public void setModuleParmTag(String moduleParmTag)
    {
        this.moduleParmTag = moduleParmTag;
    }
    /**
     * �õ�Module��̬����
     * @return String
     */
    public String getModuleParmTag()
    {
        return moduleParmTag;
    }
    /**
     * ���Tag��̬����
     * @param parm TParm
     */
    private void checkModuleParmTag(TParm parm)
    {
        if(getModuleParmTag() == null || getModuleParmTag().length() == 0)
            return;
        String names[] = StringTool.parseLine(getModuleParmTag(),";");
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            String type = "String";
            String tag = name;
            String LRC = "";
            if(v.length > 1)
                type = v[1];
            if(v.length > 2)
                tag = v[2];
            if(v.length > 3)
                LRC = v[3];
            Object value = this.callMessage(tag + "|getValue");
            if("string".equalsIgnoreCase(type))
            {
                String sValue = TCM_Transform.getString(value);
                if(LRC.equalsIgnoreCase("L"))
                    sValue += "%";
                if(LRC.equalsIgnoreCase("R"))
                    sValue = "%" + sValue;
                if(LRC.equalsIgnoreCase("C"))
                    sValue = "%" + sValue + "%";
                parm.setData(name,sValue);
            }
            else if("int".equalsIgnoreCase(type))
                parm.setData(name,TCM_Transform.getInt(value));
            else if("double".equalsIgnoreCase(type))
                parm.setData(name, TCM_Transform.getDouble(value));
        }
    }

    /**
     * ִ��Module��ѯ����
     */
    public void onQuery()
    {
        if(getModuleName() == null || getModuleName().length() == 0)
            return;
        String methodName = getModuleMethodName("QUERY");
        if(methodName == null || methodName.length() == 0)
            return;
        TParm parm = getModuleParm();
        if(parm == null)
        {
            parm = StringTool.getParm(getModuleParmString());
            checkModuleParmTag(parm);
        }
        TJDOTool tool = new TJDOTool();
        tool.setModuleName(getModuleName());
        TParm result = tool.query(methodName,parm);
        if(result == null)
        {
            err("result is null");
            return;
        }
        if(result.getErrCode() < 0)
        {
            err(result.getErrCode() + " " + result.getErrText());
            return;
        }
        setParmValue(result);
    }
    /**
     * �����޸Ķ�Ӧ�����ǩ�б�
     * @param modifyTag String
     */
    public void setModifyTag(String modifyTag)
    {
        this.modifyTag = modifyTag;
    }
    /**
     * �õ��޸Ķ�Ӧ�����ǩ�б�
     * @return String
     */
    public String getModifyTag()
    {
        return modifyTag;
    }
    /**
     * ִ�б༭���빦��
     * @param row int
     */
    public void exeModifyClicked(int row)
    {
        if(getModifyTag() == null || getModifyTag().length() == 0)
            return;
        if(getParmValue() == null)
            return;
        String names[] = StringTool.parseLine(getModifyTag(),";");
        if(names.length == 0)
            return;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            String type = "String";
            if(v.length > 1)
                name = v[1];
            if(v.length > 2)
                type = v[2];
            Object value = getParmValue().getData(name,row);
            if(value instanceof TNull)
                value = null;
            if("String".equalsIgnoreCase(type))
                callFunction(v[0] + "|setValue",TCM_Transform.getString(value));
            else if("Timestamp".equalsIgnoreCase(type))
                callFunction(v[0] + "|setValue",TCM_Transform.getTimestamp(value));
            else
                callFunction(v[0] + "|setValue",value);
        }
    }
    /**
     * ִ��Module���涯��
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onUpdate()
    {
        if(getModuleName() == null || getModuleName().length() == 0)
            return false;
        String methodName = getModuleMethodName("UPDATE");
        if(methodName == null || methodName.length() == 0)
            return false;
        if(getModifyTag() == null || getModifyTag().length() == 0)
            return false;
        if(getParmValue() == null)
            return false;
        int row = getSelectedRow();
        if(row < 0)
            return false;
        TParm parm = new TParm();
        //ȡ��������
        parm.setRowData(-1,getParmValue(),row);
        //ȡupdate��������
        parm.setRowData(StringTool.getParm(getModuleParmUpdateString()));
        if(getModuleParmUpdate() != null)
            parm.setRowData(getModuleParmUpdate());
        //ȡҳ���������
        parm.setRowData(getParmForTag(getModifyTag()));
        TJDOTool tool = new TJDOTool();
        tool.setModuleName(getModuleName());
        TParm result = tool.update(methodName,parm);
        if(result == null)
        {
            err("result is null");
            return false;
        }
        if(result.getErrCode() < 0)
        {
            err(result.getErrCode() + " " + result.getErrText());
            return false;
        }
        getParmValue().setRowData(row,parm);
        setRowParmValue(row,getParmValue());
        return true;
    }
    /**
     * ִ��Module���붯��
     * @return boolean
     */
    public boolean onInsert()
    {
        if(getModuleName() == null || getModuleName().length() == 0)
            return false;
        String methodName = getModuleMethodName("INSERT");
        if(methodName == null || methodName.length() == 0)
            return false;
        if(getModifyTag() == null || getModifyTag().length() == 0)
            return false;
        if(getParmValue() == null)
            return false;
        TParm parm = StringTool.getParm(getModuleParmInsertString());
        parm.setRowData(getParmForTag(getModifyTag()));
        if(getModuleParmInsert() != null)
            parm.setRowData(getModuleParmInsert());

        TJDOTool tool = new TJDOTool();
        tool.setModuleName(getModuleName());
        TParm result = tool.update(methodName,parm);
        if(result == null)
        {
            err("result is null");
            return false;
        }
        if(result.getErrCode() < 0)
        {
            err(result.getErrCode() + " " + result.getErrText());
            return false;
        }
        int newRow = addRow(parm);
        if(newRow == -1)
            return false;
        setSelectedRow(newRow);
        //getParmValue().insertRow();
        getParmValue().setRowData(newRow,parm);
        return true;
    }
    /**
     * ִ��Moduleɾ������
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onDelete()
    {
        if(getModuleName() == null || getModuleName().length() == 0)
            return false;
        String methodName = getModuleMethodName("DELETE");
        if(methodName == null || methodName.length() == 0)
            return false;
        int row = getSelectedRow();
        if(getParmValue() == null)
            return false;
        if(row < 0)
            return false;
        TParm parm = new TParm();
        parm.setRowData(-1,getParmValue(),row);
        TJDOTool tool = new TJDOTool();
        tool.setModuleName(getModuleName());
        TParm result = tool.update(methodName,parm);
        if(result == null)
        {
            err("result is null");
            return false;
        }
        if(result.getErrCode() < 0)
        {
            err(result.getErrCode() + " " + result.getErrText());
            return false;
        }
        removeRow(row);
        if(getRowCount() == 0)
            return true;
        if(row >= getRowCount())
            row = getRowCount() -1;
        setSelectedRow(row);
        exeModifyClicked(row);
        return true;
    }
    /**
     * ���±༭������
     */
    public void exeModifyUpdate()
    {
        if(getModifyTag() == null || getModifyTag().length() == 0)
            return;
        if(getParmValue() == null)
            return;
        int row = getSelectedRow();
        if(row < 0)
            return;
        TParm parm = getParmForTag(getModifyTag());
        getParmValue().setRowData(row,parm);
        setRowParmValue(row,getParmValue());
    }
    /**
     * ץȡҳ�����ֵ
     * @param tags String
     * @return TParm
     */
    public TParm getParmForTag(String tags)
    {
        TParm parm = new TParm();
        String names[] = StringTool.parseLine(tags,";");
        if(names.length == 0)
            return parm;
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String tag = v[0];
            String type = "String";
            String name = tag;
            if(v.length > 1)
                name = v[1];
            if(v.length > 2)
                type = v[2];
            Object value = this.callMessage(tag + "|getValue");
            if("string".equalsIgnoreCase(type))
                parm.setData(name, TCM_Transform.getString(value));
            else if("int".equalsIgnoreCase(type))
                parm.setData(name,TCM_Transform.getInt(value));
            else if("double".equalsIgnoreCase(type))
                parm.setData(name, TCM_Transform.getDouble(value));
            else if("Timestamp".equalsIgnoreCase(type))
            {
                if(value == null || (value instanceof String && ((String)value).trim().length() == 0))
                    parm.setData(name,new TNull(Timestamp.class));
                else
                    parm.setData(name, TCM_Transform.getTimestamp(value));
            }
            else
                parm.setData(name, value);
        }
        return parm;
    }
    /**
     * ����Module������̬����
     * @param moduleParmInsertString String
     */
    public void setModuleParmInsertString(String moduleParmInsertString)
    {
        this.moduleParmInsertString = moduleParmInsertString;
    }
    /**
     * �õ�Module������̬����
     * @return String
     */
    public String getModuleParmInsertString()
    {
        return moduleParmInsertString;
    }
    /**
     * ����Module����Parm����
     * @param parm TParm
     */
    public void setModuleParmInsert(TParm parm)
    {
        moduleParmInsert = parm;
    }
    /**
     * �õ�Module����Parm����
     * @return TParm
     */
    public TParm getModuleParmInsert()
    {
        return moduleParmInsert;
    }
    /**
     * ����Module�޸�Parm����
     * @param parm TParm
     */
    public void setModuleParmUpdate(TParm parm)
    {
        moduleParmUpdate = parm;
    }
    /**
     * �õ�Module�޸�Parm����
     * @return TParm
     */
    public TParm getModuleParmUpdate()
    {
        return moduleParmUpdate;
    }
    /**
     * ����Module�޸ľ�̬����
     * @param moduleParmUpdateString String
     */
    public void setModuleParmUpdateString(String moduleParmUpdateString)
    {
        this.moduleParmUpdateString = moduleParmUpdateString;
    }
    /**
     * �õ�Module�޸ľ�̬����
     * @return String
     */
    public String getModuleParmUpdateString()
    {
        return moduleParmUpdateString;
    }
    /**
     * �����Ƿ��Զ��������Զ���
     * @param autoModifyObject boolean
     */
    public void setAutoModifyObject(boolean autoModifyObject)
    {
        this.autoModifyObject = autoModifyObject;
    }
    /**
     * �Ƿ��Զ��������Զ���
     * @return boolean
     */
    public boolean isAutoModifyObject()
    {
        return autoModifyObject;
    }
    /**
     * �������ݴ洢
     * @param dataStore TDataStore
     */
    public void setDataStore(TDataStore dataStore)
    {
        this.dataStore = dataStore;
        if(dataStore instanceof TDS)
        {
            TDS ds = (TDS)dataStore;
            if(ds.getTable() != this)
                ds.setTable(this);
        }
    }
    /**
     * �õ����ݴ洢
     * @return TDataStore
     */
    public TDataStore getDataStore()
    {
        return dataStore;
    }
    /**
     * ����SQL
     * @param sql String
     */
    public void setSQL(String sql)
    {
        this.sql = sql;
    }
    /**
     * �õ�SQL
     * @return String
     */
    public String getSQL()
    {
        return sql;
    }
    /**
     * �������ֶ���
     * @param languageMap String zh|en
     */
    public void setLanguageMap(String languageMap)
    {
        this.languageMap = languageMap;
    }
    /**
     * �õ����ֶ���
     * @return String zh|en
     */
    public String getLanguageMap()
    {
        return languageMap;
    }
    /**
     * ��ȡ����
     * @return boolean
     */
    public boolean retrieve()
    {
        if(getLocalTableName() != null && getLocalTableName().length() > 0)
        {
            setDataStore(TIOM_Database.getLocalTable(getLocalTableName()));
        }else
        {
            if (sql == null || sql.length() == 0)
                return false;
            if (!getDataStore().setSQL(sql))
                return false;
            if (getDataStore().retrieve() == -1)
                return false;
        }
        if(getHeader() == null || getHeader().length() == 0)
        {
            StringBuffer sb = new StringBuffer();
            int count = getDataStore().getColumns().length;
            for(int i = 0;i < count;i++)
            {
                if(sb.length() > 0)
                    sb.append(";");
                sb.append(getDataStore().getColumnComment(i));
                sb.append(",100");
            }
            setHeader(sb.toString());
        }
        if(getDataStore().rowCount() >= 0)
            setDSValue();
        return true;
    }
    /**
     * ����
     * @return boolean
     */
    public boolean update()
    {
        if(!isAutoModifyDataStore())
            return false;
        acceptText();
        if(!getDataStore().update())
        {
            err(getDataStore().getErrText());
            return false;
        }
        return true;
    }
    /**
     * ����޸ļ�¼
     */
    public void resetModify()
    {
        if(!isAutoModifyDataStore())
            return;
        getDataStore().resetModify();
    }
    /**
     * �õ�SQL���
     * @return String[]
     */
    public String[] getUpdateSQL()
    {
        if(!isAutoModifyDataStore())
            return null;
        return getDataStore().getUpdateSQL();
    }
    /**
     * �����Զ��������ݴ洢
     * @param autoModifyDataStore boolean
     */
    public void setAutoModifyDataStore(boolean autoModifyDataStore)
    {
        this.autoModifyDataStore = autoModifyDataStore;
    }
    /**
     * �Ƿ��Զ��������ݴ洢
     * @return boolean
     */
    public boolean isAutoModifyDataStore()
    {
        return autoModifyDataStore;
    }
    /**
     * ���ñ���Table����
     * @param localTableName String
     */
    public void setLocalTableName(String localTableName)
    {
        this.localTableName = localTableName;
    }
    /**
     * �õ�����Table����
     * @return String
     */
    public String getLocalTableName()
    {
        return localTableName;
    }
    /**
     * ���ù�������
     * @param filter String
     */
    public void setFilter(String filter)
    {
        filterString = filter;
        if(getDataStore() != null)
            getDataStore().setFilter(filter);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getFilter()
    {
        return filterString;
    }
    /**
     * ����
     * @return boolean
     */
    public boolean filter()
    {
        if(getDataStore() == null)
            return false;
        if(getDataStore() instanceof TDS)
        {
            TDS ds = (TDS)getDataStore();
            if(ds.getTable() != this)
                ds.setTable(this);
        }
        if(!getDataStore().filter(getShowCount()))
            return false;
        setDSValue();
        return true;
    }
    /**
     * ͬ��DS����
     */
    public void showDSData()
    {
        if(getDataStore() == null)
            return;
        //TDS����
        if(getDataStore() instanceof TDS)
        {
            TDS ds = (TDS)getDataStore();
            if(ds.getTable() != this)
            {
                ds.setTable(this);
                getDataStore().setFilter(getFilter());
                getDataStore().filter(getShowCount());
                setDSValue();
            }
            return;
        }
        //��ͨDataStore����
        getDataStore().setFilter(getFilter());
        if(!getDataStore().filter(getShowCount()))
            return;
        if(getDataStore().rowCount() != this.getRowCount())
            setDSValue();
    }
    /**
     * ����DataStore����
     */
    public void setDSValue()
    {
        if(getDataStore() == null)
            return;
        getDataStore().setLanguage(getLanguage());
        getDataStore().setLanguageMap(getLanguageMap());
        setValue(getDataStore().getVector(getParmMap(),getShowCount()));
    }
    /**
     * ����DataStore��������
     * @param row int
     */
    public void setDSValue(int row)
    {
        if(getDataStore() == null)
            return;
        if(row < 0 || row >= getRowCount())
            return;
        getDataStore().setLanguage(getLanguage());
        getDataStore().setLanguageMap(getLanguageMap());
        Vector v = getDataStore().getVectorRow(row,getParmMap());
        int count = getColumnCount();
        for(int i = 0;i < count;i++)
            this.setValueAt(v.get(i),row,i);
        getTable().updateUI();
    }
    /**
     * ����
     * @param filterObject Object
     * @param methodName String
     * @return boolean
     */
    public boolean filterObject(Object filterObject,String methodName)
    {
        if(!getDataStore().filterObject(filterObject,methodName,getShowCount()))
            return false;
        setDSValue();
        return true;
    }
    /**
     * ������������
     * @param sort String "AAA ASC,BBB DESC"
     */
    public void setSort(String sort)
    {
        getDataStore().setSort(sort);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getSort()
    {
        return getDataStore().getSort();
    }
    /**
     * ����
     * @return boolean
     */
    public boolean sort()
    {
        if(!getDataStore().sort())
            return false;
        setValue(getDataStore().getVector(getParmMap()));
        return true;
    }
    /**
     * �õ��޸ĵ��к�
     * @return int[]
     */
    public int[] getModifiedRows()
    {
        return getDataStore().getModifiedRows();
    }
    /**
     * �õ��޸ĵ��к�(������)
     * @return int[]
     */
    public int[] getModifiedRowsFilter()
    {
        return getDataStore().getModifiedRows(TDataStore.FILTER);
    }
    /**
     * �Ƿ��޸�
     * @return boolean true ���޸� false û���޸�
     */
    public boolean isModified()
    {
        return getDataStore().isModified();
    }
    /**
     * ����ֵ
     * @param row int �к�
     * @param column int �к�
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setItem(int row,int column,Object value)
    {
        setValueAt(value,row,column);
        if(isAutoModifyDataStore())
        {
            String columnName = getDataStoreColumnName(column);
            if (!getDataStore().setItem(row, columnName, value))
                return false;
            setDSValue(row);
        }
        return true;
    }
    /**
     * ����ֵ
     * @param row int �к�
     * @param column String ����
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean setItem(int row,String column,Object value)
    {
        int c = getColumnIndex(column);
        if(c != -1)
            setValueAt(value,row,c);
        if(isAutoModifyDataStore())
        {
            if(!getDataStore().setItem(row,column,value))
                return false;
            setDSValue(row);
        }
        if(getParmValue() != null)
        {
            TParm parm = getParmValue();
            if(parm.existData(column))
                parm.setData(column,row,value);
        }
        return true;
    }
    /**
     * ����ֵ(������)
     * @param row int �к�
     * @param column int �к�
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��(����������ʾ����Ҳ����false)
     */
    public boolean setItemFilter(int row,int column,Object value)
    {
        if(!isAutoModifyDataStore())
            return false;
        String columnName = getDataStoreColumnName(column);
        int result = getDataStore().setItem(row, columnName, value,
                                            TDataStore.FILTER);
        if(result < 0)
            return false;
        setValueAt(value,result,column);
        return true;
    }
    /**
     * ����ֵ(������)
     * @param row int �к�
     * @param column String ����
     * @param value Object ֵ
     * @return boolean true �ɹ� false ʧ��(����������ʾ����Ҳ����false)
     */
    public boolean setItemFilter(int row,String column,Object value)
    {
        if(!isAutoModifyDataStore())
            return false;
        int result = getDataStore().setItem(row, column, value,TDataStore.FILTER);
        if(result < 0)
            return false;

        int c = getColumnIndex(column);
        if(c == -1)
            return false;
        setValueAt(value,result,c);
        return true;
    }
    /**
     * �õ����ݴ洢��Ӧ������
     * @param column int
     * @return String
     */
    public String getDataStoreColumnName(int column)
    {
        String columnName = getParmMap(column);
        if(columnName == null || columnName.length() == 0)
            columnName = getDataStore().getColumns()[column];
        return columnName;
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return Object
     */
    public Object getItemData(int row,int column)
    {
        return getItemData(row,getDataStoreColumnName(column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return Object
     */
    public Object getItemData(int row,String column)
    {
        if(!isAutoModifyDataStore())
        {
            int index = getColumnIndex(column);
            if(index == -1)
                return null;
            return getValueAt(row,index);
        }
        return getDataStore().getItemData(row,column);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int ����
     * @return String
     */
    public String getItemString(int row,int column)
    {
        return TCM_Transform.getString(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return String
     */
    public String getItemString(int row,String column)
    {
        return TCM_Transform.getString(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return int
     */
    public int getItemInt(int row,int column)
    {
        return getItemInt(row,getDataStoreColumnName(column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return int
     */
    public int getItemInt(int row,String column)
    {
        if (!isAutoModifyDataStore())
            return 0;
        return getDataStore().getItemInt(row, column);
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return double
     */
    public double getItemDouble(int row,int column)
    {
        return TCM_Transform.getDouble(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String ����
     * @return double
     */
    public double getItemDouble(int row,String column)
    {
        return TCM_Transform.getDouble(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column int �к�
     * @return Timestamp
     */
    public Timestamp getItemTimestamp(int row,int column)
    {
        return TCM_Transform.getTimestamp(getItemData(row,column));
    }
    /**
     * �õ�����
     * @param row int �к�
     * @param column String �к�
     * @return Timestamp
     */
    public Timestamp getItemTimestamp(int row,String column)
    {
        return TCM_Transform.getTimestamp(getItemData(row,column));
    }
    /**
     * �õ��Լ�����
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * ������ʾ����
     * @param showCount int
     */
    public void setShowCount(int showCount)
    {
        this.showCount = showCount;
    }
    /**
     * �õ���ʾ����
     * @return int
     */
    public int getShowCount()
    {
        return showCount;
    }
    /**
     * ���õ�������
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * �õ���������
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * ����˫������
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * �õ�˫������
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * �����һ�����
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * �õ��һ�����
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * ִ�ж���
     * @param message String ��Ϣ
     * @return Object
     */
    public Object exeAction(String message)
    {
        if (message == null || message.length() == 0)
            return null;
        String value[] = StringTool.parseLine(message, ';',"[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            String actionMessage = value[i];
            if ("call".equalsIgnoreCase(actionMessage))
                actionMessage = getText();
            callMessage(actionMessage);
        }
        return "OK";
    }
    /**
     * ��������ɫ
     * @param row int
     * @param color Color
     */
    public void setRowColor(int row,Color color)
    {
        rowColorMap.put(row,color);
    }
    /**
     * ɾ������ɫ
     * @param row int
     */
    public void removeRowColor(int row)
    {
        rowColorMap.remove(row);
    }
    /**
     * �õ�����ɫ
     * @param row int
     * @return Color
     */
    public Color getRowColor(int row)
    {
        return (Color)rowColorMap.get(row);
    }
    /**
     * ��������ɫMap
     * @param map Map
     */
    public void setRowColorMap(Map map)
    {
        rowColorMap = map;
    }
    /**
     * �õ�����ɫMap
     * @return Map
     */
    public Map getRowColorMap()
    {
        return rowColorMap;
    }
    /**
     * ������������ɫ
     * @param row int
     * @param color Color
     */
    public void setRowTextColor(int row,Color color)
    {
        rowTextColorMap.put(row,color);
    }
    /**
     * ɾ����������ɫ
     * @param row int
     */
    public void removeRowTextColor(int row)
    {
        rowTextColorMap.remove(row);
    }
    /**
     * �õ���������ɫ
     * @param row int
     * @return Color
     */
    public Color getRowTextColor(int row)
    {
        return (Color)rowTextColorMap.get(row);
    }
    /**
     * ������������ɫMap
     * @param map Map
     */
    public void setRowTextColorMap(Map map)
    {
        rowTextColorMap = map;
    }
    /**
     * �õ���������ɫMap
     * @return Map
     */
    public Map getRowTextColorMap()
    {
        return rowTextColorMap;
    }
    /**
     * �õ��иı䶯��
     * @return String
     */
    public String getRowChangeAction()
    {
        return actionList.getAction("rowChangeAction");
    }
    /**
     * �����иı䶯��
     * @param action String
     */
    public void setRowChangeAction(String action)
    {
        actionList.setAction("rowChangeAction",action);
    }
    /**
     * �õ��иı䶯��
     * @return String
     */
    public String getColumnChangeAction()
    {
        return actionList.getAction("columnChangeAction");
    }
    /**
     * �����иı䶯��
     * @param action String
     */
    public void setColumnChangeAction(String action)
    {
        actionList.setAction("columnChangeAction",action);
    }
    /**
     * �õ����иı䶯��
     * @return String
     */
    public String getChangeAction()
    {
        return actionList.getAction("changeAction");
    }
    /**
     * �������иı䶯��
     * @param action String
     */
    public void setChangeAction(String action)
    {
        actionList.setAction("changeAction",action);
    }
    /**
     * �иı��¼�
     */
    public void onRowChange()
    {
        exeAction(getRowChangeAction());
        onChange();
    }
    /**
     * �иı��¼�
     */
    public void onColumnChange()
    {
        exeAction(getColumnChangeAction());
        onChange();
    }
    /**
     * ���иı�
     */
    public void onChange()
    {
        exeAction(getChangeAction());
    }
    /**
     * �õ�ֵ�ı䶯��
     * @return String
     */
    public String getChangeValueAction()
    {
        return actionList.getAction("changeValueAction");
    }
    /**
     * ����ֵ�ı䶯��
     * @param action String
     */
    public void setChangeValueAction(String action)
    {
        actionList.setAction("changeValueAction",action);
    }
    /**
     * �����Ҽ��˵��﷨
     * @param syntax String
     */
    public void setPopupMenuSyntax(String syntax)
    {
        popupMenuSyntax = syntax;
    }
    /**
     * �õ��Ҽ��˵��﷨
     * @return String
     */
    public String getPopupMenuSyntax()
    {
        return popupMenuSyntax;
    }
    /**
     * �����˵�
     * @param row int
     * @param x int
     * @param y int
     */
    public void popupMenu(int row,int x,int y)
    {
        String syntax = getPopupMenuSyntax();
        if(syntax == null || syntax.length() == 0)
            return;
        TPopupMenu popup = TPopupMenu.createPopupMenu(getPopupMenuSyntax());
        popup.setParentComponent(this);
        popup.changeLanguage(getLanguage());
        popup.show(getTable(),x,y);
    }
    /**
     * ����������Ŀ����
     * @param row1 int
     * @param row2 int
     */
    public void sRowItem(int row1,int row2)
    {
        String nameArray[] = StringTool.parseLine(getParmMap(),";");
        for(int column = 0;column < nameArray.length;column ++)
            sRowItem(row1,row2,nameArray[column]);
    }
    /**
     * ����������Ŀ����
     * @param row1 int
     * @param row2 int
     * @param columnName String
     */
    public void sRowItem(int row1,int row2,String columnName)
    {
        Object temp0 = getItemData(row1,columnName);
        Object temp = getItemData(row2,columnName);
        setItem(row1,columnName,temp);
        setItem(row2,columnName,temp0);
    }

    /**
     * ����Ӣ��ͷ
     * @param enHeader String
     */
    public void setEnHeader(String enHeader)
    {
        this.enHeader = enHeader;
        if(enHeader == null)
            enHeaderList = null;
        enHeaderList = StringTool.parseLine(enHeader,";");
    }
    /**
     * �õ�Ӣ��ͷ
     * @return String
     */
    public String getEnHeader()
    {
        return enHeader;
    }
    public String getEnHeader(int index)
    {
        if(enHeaderList == null)
            return "";
        if(index < 0 || index >= enHeaderList.length)
            return "";
        return enHeaderList[index];
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if (language == null)
            return;
        if (language.equals(this.language))
            return;
        this.language = language;
        Iterator iterator = items.keySet().iterator();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            TComponent component = getItem(name);
            if(component instanceof TComboBox)
                ((TComboBox)component).changeLanguage(language);
            else if(component instanceof TTextFormat)
                ((TTextFormat)component).changeLanguage(language);
        }
        if(isAutoModifyDataStore())
            setDSValue();
        else if(parmValue != null)
            setParmValue(parmValue);
        validate();
        repaint();
        //getTable().validate();
        //getTable().repaint();
    }
    /**
     * �ͷ�
     */
    public void release()
    {
        super.release();
        table = null;
        header = null;
        lockColumns = null;
        lockRows = null;
        lockCell = null;
        items = null;
        focusIndexList = null;
        rowColumnType = null;
        data = null;
        horizontalAlignmentMap = null;
        parmMap = null;
        moduleName = null;
        moduleMethodName = null;
        moduleParm = null;
        parmValue = null;
        moduleParmString = null;
        moduleParmInsertString = null;
        moduleParmInsert = null;
        moduleParmUpdate = null;
        moduleParmUpdateString = null;
        moduleParmTag = null;
        modifyTag = null;
        dataStore = null;
        sql = null;
        localTableName = null;
        filterString = null;
        actionList = null;
        rowTextColorMap = null;
        rowColorMap = null;
        popupMenuSyntax = null;
    }
    
    public void setCellFont(String font,int fontStyle,int fontSize)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setCellFont(font, fontStyle, fontSize);
    }
    
	/**
	 * ��������
	 * 
	 * @param cellFont
	 */
	public void setCellFont(String cellFont) {
		String s[] = StringTool.parseLine(cellFont, ";");
		//System.out.println("����"+s[0]);
		//System.out.println("��ʽ"+s[1]);
		//System.out.println("��С"+s[2]);
		setCellFont(s[0], Integer.valueOf(s[1]), Integer.valueOf(s[2]));
		getTable().updateUI();
	}

    
    
    
}
