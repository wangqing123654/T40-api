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
     * 内部Table对象
     */
    private JTableBase table;
    /**
     * 列数据 例如 "aaa,100,string;bbb,80,double"
     */
    private String header;
    /**
     * 英文列头 例如 "aaa;bbb;ccc"
     */
    private String enHeader;
    private String[] enHeaderList;
    /**
     * 列锁
     */
    private String lockColumns;
    /**
     * 行锁
     */
    private String lockRows;
    /**
     * 锁单元格
     */
    private Map lockCell;
    /**
     * 进入编辑模式点击的次数
     */
    private int clickCountToStart = 1;
    /**
     * 内部组件
     */
    private Map items;
    /**
     * 分色间隔行数
     */
    private int spacingRow = 1;
    /**
     * 分色间隔列数
     */
    private int spacingColumn = 0;
    /**
     * 回车焦点 0 不移动 1　向下移动 2 向右移动
     */
    private int focusType = 1;
    /**
     * 焦点列表
     */
    private String focusIndexList;
    /**
     * 焦点跳跃 true 当到达行尾自动跳跃到下一行　false 不跳跃到下一行
     */
    private boolean focusIndexJump = true;
    /**
     * 行列类型
     */
    private Map rowColumnType;
    /**
     * 数据包
     */
    private Object data;
    /**
     * 数据横向显示位置列表
     */
    private Map horizontalAlignmentMap;
    /**
     * TParm对照
     */
    private String parmMap;
    /**
     * Module文件名
     */
    private String moduleName;
    /**
     * Module方法名
     */
    private Map moduleMethodName;
    /**
     * Module参数
     */
    private TParm moduleParm;
    /**
     * 结果集
     */
    private TParm parmValue;
    /**
     * Module静态参数
     */
    private String moduleParmString;
    /**
     * Module新增静态参数
     */
    private String moduleParmInsertString;
    /**
     * Module新增Parm参数
     */
    private TParm moduleParmInsert;
    /**
     * Module修改Parm参数
     */
    private TParm moduleParmUpdate;
    /**
     * Module修改静态参数
     */
    private String moduleParmUpdateString;
    /**
     * Module动态参数
     */
    private String moduleParmTag;
    /**
     * 修改对应组件标签列表
     */
    private String modifyTag;
    /**
     * 自动更新对象
     */
    private boolean autoModifyObject;
    /**
     * 数据存储
     */
    private TDataStore dataStore;
    /**
     * SQL语句
     */
    private String sql;
    /**
     * 自动更新数据存储
     */
    private boolean autoModifyDataStore;
    /**
     * 本地Table名称
     */
    private String localTableName;
    /**
     * 显示行数
     */
    private int showCount;
    /**
     * 鼠标X位置(右击刷新)
     */
    private int mouseX;
    /**
     * 鼠标Y位置(右击刷新)
     */
    private int mouseY;
    /**
     * 点击行
     */
    private int clickedRow;
    /**
     * 过滤条件
     */
    private String filterString;
    /**
     * 动作列表
     */
    private ActionMessage actionList;
    /**
     * 行字体颜色列表
     */
    private Map rowTextColorMap;
    /**
     * 行颜色列表
     */
    private Map rowColorMap;
    /**
     * 右键菜单语法
     */
    private String popupMenuSyntax;
    /**
     * 语种
     */
    private String language;
    /**
     * 语种对照
     */
    private String languageMap;
    
    /**
     * 构造器
     */
    public TTableBase() {
        //初始化动作列表
        actionList = new ActionMessage();
        //初始化内部组件Map
        items = new HashMap();
        //初始化行列类型Map
        rowColumnType = new HashMap();
        //数据横向显示位置列表
        horizontalAlignmentMap = new HashMap();
        //行颜色列表
        rowColorMap = new HashMap();
        //行字体颜色列表
        rowTextColorMap = new HashMap();
        TableModel dm = new TTableModel();        
        //
        TTableColumnModel cm = new TTableColumnModel(this);
        TTableSelectionModel sm = new TTableSelectionModel(this);       
        JTableBase table=new JTableBase(dm,cm,sm);        

        setTable(table);

        //设置显示适配器
        setCellRenderer(new TTableCellRenderer(this));
        dataStore = new TDS();
    }

    
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        super.initListeners();
        //监听Mouse事件
        addEventListener(getTag() + "->" + TCellEditorListener.EDITING_CANCELED,"onEditingCanceled");
        addEventListener(getTag() + "->" + TCellEditorListener.EDITING_STOPPED,"onEditingStopped");
        //addEventListener(getTag() + "->" + TTableEvent.CHANGE_VALUE,"onChangeValue");
        addEventListener(getTag() + "->" + TTableEvent.CLICKED,"onClicked");
        addEventListener(getTag() + "->" + TTableEvent.DOUBLE_CLICKED,"onDoubleClicked");
        addEventListener(getTag() + "->" + TTableEvent.RIGHT_CLICKED,"onRightClicked");

    }
    /**
     * 行点击事件
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
     * 行双击事件
     * @param row int
     */
    public void onDoubleClicked(int row)
    {
        clickedRow = row;
        exeAction(getDoubleClickedAction());
    }
    /**
     * 行右击事件
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
        //右键菜单
        popupMenu(row,x,y);
    }
    /**
     * 得到鼠标X位置(右击刷新)
     * @return int
     */
    public int getMouseX()
    {
        return mouseX;
    }
    /**
     * 得到鼠标X位置(右击刷新)
     * @return int
     */
    public int getMouseY()
    {
        return mouseY;
    }
    /**
     * 得到点击行
     * @return int
     */
    public int getClickedRow()
    {
        return clickedRow;
    }
    /**
     * 显示DS其他显示行
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
     * 值改变事件(内部使用)
     * @param node TTableNode
     */
    public void onChangeValuePrivate(TTableNode node)
    {
        //是否自动更新属性对象
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
        //初始化监听事件
        initListeners();
        //初始化参数准备
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
        //初始化控制类
        if (getControl() != null)
            getControl().onInit();
        
        System.out.println();
        
    }
    /**
     * 编辑取消
     * @param e ChangeEvent
     */
    public void onEditingCanceled(ChangeEvent e)
    {
    }
    /**
     * 编辑停止
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
     * 设置选中行
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
     * 得到选中行
     * @return int
     */
    public int getSelectedRow()
    {
        if(getTable() == null)
            return -1;
        return getTable().getSelectedRow();
    }
    /**
     * 设置选中列
     * @param column int
     */
    public void setSelectedColumn(int column)
    {
        setSelectedColumn_(getColumnModel().getColumnIndexNew(column));
    }
    /**
     * 得到选中列
     * @return int
     */
    public int getSelectedColumn()
    {
        return getColumnModel().getColumnIndex(getSelectedColumn_());
    }
    /**
     * 得到选中列(内部使用)
     * @param column int
     */
    public void setSelectedColumn_(int column)
    {
        setColumnSelectionInterval(column,column);
    }
    /**
     * 得到选中列(内部使用)
     * @return int
     */
    public int getSelectedColumn_()
    {
        if(getTable() == null)
            return -1;
        return getTable().getSelectedColumn();
    }
    /**
     * 设置选中行范围
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
     * 设置列选中范围
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
     * 清除选中
     */
    public void clearSelection()
    {
        if(getTable() == null)
            return;
        getTable().clearSelection();
    }
    /**
     * 得到总行数
     * @return int
     */
    public int getRowCount()
    {
        if(getTable() == null)
            return -1;
        return getTable().getRowCount();
    }
    /**
     * 设置某行某列的值(内部使用)
     * @param value Object 值
     * @param row int 行号
     * @param column int 列号
     */
    public void setValueAt_(Object value,int row,int column)
    {
        acceptText();
        getTable().setValueAt(value,row,column);
    }
    /**
     * 得到某行某列的值(内部使用)
     * @param row int 行号
     * @param column int 列号
     * @return Object
     */
    public Object getValueAt_(int row,int column)
    {
        return getTable().getValueAt(row,column);
    }
    /**
     * 设置某行某列的值
     * @param value Object 值
     * @param row int 行号
     * @param column int 列号
     */
    public void setValueAt(Object value,int row,int column)
    {
        setValueAt_(value,row,getColumnModel().getColumnIndexNew(column));
    }
    /**
     * 得到某行某列的值
     * @param row int 行号
     * @param column int 列号
     * @return Object
     */
    public Object getValueAt(int row,int column)
    {
        return getValueAt_(row,getColumnModel().getColumnIndexNew(column));
    }
    /**
     * 设置列锁
     * @param lockColumns String
     */
    public void setLockColumns(String lockColumns)
    {
        this.lockColumns = lockColumns;
    }
    /**
     * 得到列锁
     * @return String
     */
    public String getLockColumns()
    {
        return lockColumns;
    }
    /**
     * 设置行锁
     * @param lockRows String
     */
    public void setLockRows(String lockRows)
    {
        this.lockRows = lockRows;
    }
    /**
     * 得到行锁
     * @return String
     */
    public String getLockRows()
    {
        return lockRows;
    }
    /**
     * 设置锁单元格Map
     * @param lockCell Map
     */
    public void setLockCellMap(Map lockCell)
    {
        this.lockCell = lockCell;
    }
    /**
     * 得到锁单元格Map
     * @return Map
     */
    public Map getLockCellMap()
    {
        return lockCell;
    }
    /**
     * 单元格锁定整行
     * @param row int
     * @param b boolean true 加锁, false 解锁
     */
    public void setLockCellRow(int row,boolean b)
    {
        for(int i = 0;i < getColumnCount();i++)
            setLockCell(row,i,b);
    }
    /**
     * 单元格锁定整列
     * @param column int
     * @param b boolean true 加锁, false 解锁
     */
    public void setLockCellColumn(int column,boolean b)
    {
        for(int i = 0;i < this.getRowCount();i++)
            setLockCell(i,column,b);
    }
    /**
     * 设置单元格锁
     * @param row int
     * @param columnName String
     * @param b boolean true 加锁, false 解锁
     */
    public void setLockCell(int row,String columnName,boolean b)
    {
        setLockCell(row,getColumnIndex(columnName),b);
    }
    /**
     * 设置单元格锁
     * @param row int
     * @param column int
     * @param b boolean true 加锁, false 解锁
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
     * 单元格是否锁定
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
     * 判断某列是否被锁定
     * @param column int 列号
     * @return boolean true 锁 false 不锁
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
     * 判断某行是否被锁定
     * @param row int 行号
     * @return boolean true 锁 false 不锁
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
     * 设置进入编辑模式点击的次数
     * @param count int
     */
    public void setClickCountToStart(int count) {
        clickCountToStart = count;
    }
    /**
     * 得到进入编辑模式点击的次数
     * @return int
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }
    /**
     * 设置回车焦点类型
     * @param focusType int 0 不移动 1　向下移动 2 向右移动
     */
    public void setFocusType(int focusType)
    {
        this.focusType = focusType;
    }
    /**
     * 得到回车焦点类型
     * @return int 0 不移动 1　向下移动 2 向右移动
     */
    public int getFocusType()
    {
        return focusType;
    }
    /**
     * 设置焦点转移列表
     * @param focusIndexList String
     */
    public void setFocusIndexList(String focusIndexList)
    {
        this.focusIndexList = focusIndexList;
    }
    /**
     * 得到焦点转移列表
     * @return String
     */
    public String getFocusIndexList()
    {
        return focusIndexList;
    }
    /**
     * Enter转移焦点
     */
    public void onEnterFocus()
    {
        if(getFocusType() == 0)
            return;
        //自定义移动焦点
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
     * 向下移动焦点
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
     * 用户自定义焦点转移
     * @return boolean true 成功 false 失败
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
     * tab转移焦点
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
     * 设置行高
     * @param rowHeight int
     */
    public void setRowHeight(int rowHeight)
    {
        getTable().setRowHeight(rowHeight);
    }
    /**
     * 得到行高
     * @return int
     */
    public int getRowHeight()
    {
        return getTable().getRowHeight();
    }
    /**
     * 增加行列类型
     * @param row int 行号
     * @param column int 列号
     * @param type String 类型
     */
    public void addRowColumnType(int row,int column,String type)
    {
        rowColumnType.put(row + ":" + column,type);
    }
    /**
     * 得到行列类型
     * @param row int 行号
     * @param column int 列号
     * @return String 类型
     */
    public String getRowColumnType(int row,int column)
    {
        return (String)rowColumnType.get(row + ":" + column);
    }
    /**
     * 加载行列类型
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
     * 增加横向显示位置设定
     * @param row int
     * @param column int
     * @param type String
     */
    public void addHorizontalAlignment(int row,int column,String type)
    {
        horizontalAlignmentMap.put(row + ":" + column,type);
    }
    /**
     * 得到横向显示位置设定
     * @param row int 行号
     * @param column int 列号
     * @return String 类型
     */
    public String getHorizontalAlignment(int row,int column)
    {
        return (String)horizontalAlignmentMap.get(row + ":" + column);
    }
    /**
     * 增加横向显示位置设定
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
     * 接受文本
     */
    public void acceptText()
    {
        int column = getSelectedColumn_();
        if(column < 0)
            return;
        getCellEditor(column).getDelegate().stopCellEditing();
    }
    /**
     * 过滤初始化信息
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
     * 设置内部Table对象
     * @param table JTableBase
     */
    public void setTable(JTableBase table)
    {
        if(this.table == table)
            return;
        if(this.table != null)
            getViewport().remove(this.table);
        
        //test
        //table.getCellRenderer().setFont(new Font("宋体",0,18));
        //
        this.table = table;
        table.setTable(this);
        //getViewport().setFont(new Font("宋体",0,18));
        //
        getViewport().setBackground(new Color(255,255,255));
        getViewport().add(table);
        table.setAutoResizeMode(0);
        table.setSelectionMode(0);
    }
    /**
     * 得到内部Table对象
     * @return JTableBase
     */
    public JTableBase getTable()
    {
        return table;
    }
    /**
     * 得到数据模型对象
     * @return TTableModel
     */
    public TTableModel getModel()
    {
        if(getTable() == null)
            return null;
        return (TTableModel)getTable().getModel();
    }
    /**
     * 得到列数据
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
     * 得到列模型对象
     * @return TTableColumnModel
     */
    public TTableColumnModel getColumnModel() {
        if(getTable() == null)
            return null;
        return (TTableColumnModel)getTable().getColumnModel();
    }
    /**
     * 得到列对象
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
     * 得到列个数
     * @return int
     */
    public int getColumnCount()
    {
        if(getColumnModel() == null)
            return 0;
        return getColumnModel().getColumnCount();
    }
    /**
     * 设置列显示文字
     * @param index int 类序号
     * @param text String 文字
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
     * 设置列宽度
     * @param index int 类序号
     * @param width int 宽度
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
     * 设置列数据
     * @param header String 例如 "aaa,100,string;bbb,80,double"
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
        //设置标题文字
        setHeader((String[])columnNameData.toArray(new String[]{}));
        //设置标题宽度
        for(int i = 0; i < columnWidthData.size();i++)
        {
            int width = (int)(Integer)columnWidthData.get(i);
            setColumnWidth(i,width);
        }
        //设置每一列的编辑器
        for(int i = 0;i < columns.length;i++)
        {
            TTableCellEditor editor = new TTableCellEditor(this,(String)columnTypeData.get(i));
            getColumn(i).setCellEditor(editor);
            //处理Number显示格式列表
            String dataFormat = (String)columnDataFormatList.get(i);
            if(dataFormat != null && dataFormat.length() > 0)
                editor.setDataFormat(dataFormat);
        }
        //初始化列个数
        getColumnModel().initColumnIndex(columns.length);
    }
    /**
     * 得到列数据
     * @return String 例如 "aaa,100,string;bbb,80,double"
     */
    public String getHeader()
    {
        return header;
    }
    /**
     * 设置自动尺寸模式
     * @param mode int 模式
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
     * 得到自动尺寸模式
     * @return int 模式
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
     * 设置行选中模式
     * @param mode int 模式
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
     * 设置行选中模式
     * @return int 模式
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
     * 设置允许行选中
     * @param rowSelectionAllowed boolean
     */
    public void setRowSelectionAllowed(boolean rowSelectionAllowed)
    {
        if(getTable() == null)
            return;
        getTable().setRowSelectionAllowed(rowSelectionAllowed);
    }
    /**
     * 允许行选中
     * @return boolean
     */
    public boolean getRowSelectionAllowed()
    {
        if(getTable() == null)
            return false;
        return getTable().getRowSelectionAllowed();
    }
    /**
     * 设置允许列选中
     * @param columnSelectionAllowed boolean
     */
    public void setColumnSelectionAllowed(boolean columnSelectionAllowed)
    {
        if(getTable() == null)
            return;
        getTable().setColumnSelectionAllowed(columnSelectionAllowed);
    }
    /**
     * 允许列选中
     * @return boolean
     */
    public boolean getColumnSelectionAllowed()
    {
        if(getTable() == null)
            return false;
        return getTable().getColumnSelectionAllowed();
    }
    /**
     * 设置Parm数据
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
     * 语种对照更换
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
     * 得到Parm数据
     * @return TParm
     */
    public TParm getParmValue()
    {
        return parmValue;
    }
    /**
     * 得到显示数据包
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
     * 得到对照列名
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
     * 得到列名所在位置
     * @param name String 列名
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
     * 设置新值
     * @param row int 行号
     * @param column int 列号
     * @return boolean true 成功 false 失败
     */
    public boolean setObjectValue(int row,int column)
    {
        Object value = getValueAt(row,column);
        return setObjectValue(row,column,value);
    }
    /**
     * 设置新值
     * @param row int 行号
     * @param column int 列号
     * @param value Object 值
     * @return boolean true 成功 false 失败
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
     * 设置Parm数据
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
     * 设置一行的数据
     * @param row int 行号
     * @param parm TParm
     */
    public void setRowParmValue(int row,TParm parm)
    {
        setRowParmValue(row,parm,getParmMap());
    }
    /**
     * 设置一行的数据
     * @param row int 行号
     * @param parm TParm
     * @param names String 列名 "ID;NAME"
     */
    public void setRowParmValue(int row,TParm parm,String names)
    {
        if(parm == null)
            return;
        setRowValue(row,parm.getRowVector(row,names));
    }
    /**
     * 设置数据
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
     * 设置行数据
     * @param row int 行号
     * @param data Vector 数据包
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
     * 新增一行数据
     * @param parm TParm 数据包
     * @return int
     */
    public int addRow(TParm parm)
    {
        return addRow(parm,-1);
    }
    /**
     * 新增一行数据
     * @param parm TParm 数据包
     * @param names String 列 "ID;NAME"
     * @return int 插入行号
     */
    public int addRow(TParm parm,String names)
    {
        return addRow(parm,-1,names);
    }
    /**
     * 新增一行数据
     * @param parm TParm 数据包
     * @param rowParm int 数据包行号
     * @return int 插入行号
     */
    public int addRow(TParm parm,int rowParm)
    {
        return addRow(parm,rowParm,getParmMap());
    }
    /**
     * 新增一行数据
     * @param parm TParm 数据包
     * @param rowParm int 数据包行号
     * @param names String 列 "ID;NAME"
     * @return int 插入行号
     */
    public int addRow(TParm parm,int rowParm,String names)
    {
        return addRow(-1,parm,rowParm,names);
    }
    /**
     * 新增一行数据
     * @param row int 插入行号位置
     * @param parm TParm 数据包
     * @param names String 列 "ID;NAME"
     * @return int 插入行号
     */
    public int addRow(int row,TParm parm,String names)
    {
        return addRow(row,parm,-1,names);
    }
    /**
     * 新增一行数据
     * @param row int 插入行号位置
     * @param parm TParm 数据包
     * @return int 插入行号
     */
    public int addRow(int row,TParm parm)
    {
        return addRow(row,parm,-1);
    }
    /**
     * 新增一行数据
     * @param row int 插入行号位置
     * @param parm TParm 数据包
     * @param rowParm int 数据包行号
     * @return int 插入行号
     */
    public int addRow(int row,TParm parm,int rowParm)
    {
        return addRow(row,parm,rowParm,getParmMap());
    }
    /**
     * 新增一行数据
     * @param row int 插入行号位置
     * @param parm TParm 数据包
     * @param rowParm int 数据包行号
     * @param names String 列 "ID;NAME"
     * @return int 插入行号
     */
    public int addRow(int row,TParm parm,int rowParm,String names)
    {
        if(parm == null)
            return -1;
        return addRow(row,parm.getRowVector(rowParm,names));
    }
    /**
     * 新增空行
     * @return int 插入行号
     */
    public int addRow()
    {
        return addRow(-1);
    }
    /**
     * 插入一行数据
     * @param row int 插入行号位置
     * @return int 插入行号
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
     * 新增一行数据
     * @param data Vector 数据包
     * @return int 插入行号
     */
    public int addRow(Vector data)
    {
        return addRow(-1,data);
    }
    /**
     * 插入一行数据
     * @param row int 插入行号
     * @param data Vector 数据包
     * @return int 插入行号
     */
    public int addRow(int row,Vector data)
    {
        return insertRowValue(row,data);
    }
    /**
     * 插入一行数据
     * @param row int 插入行号
     * @param data Vector 数据包
     * @return int 插入行号
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
     * 设置字符数据
     * @param stringData String
     */
    public void setStringData(String stringData)
    {
        setValue(StringTool.getVector(stringData));
    }
    /**
     * 删除全部行
     */
    public void removeRowAll()
    {
        for(int i = getRowCount() - 1;i >= 0;i--)
            removeRow(i);
        getTable().updateUI();
    }
    
    /**
     * 删除当前行
     */
    public void removeRow()
    {
        int row = getSelectedRow();
        if(row < 0)
            return;
        removeRow(row);
    }
    /**
     * 删除行
     * @param row int 行号
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
     * 得到数据
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
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        return null;
    }
    /**
     * 加载顺序
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
     * 设置项目
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
     * 加载成员组件
     * @param value String
     */
    protected void createItem(String value) {
        String values[] = StringTool.parseLine(value, "|");
        if (values.length == 0)
            return;
        //组件ID
        String cid = values[0];
        //组件类型
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
     * 组件个数
     * @return int
     */
    public int getItemPrivateCount()
    {
        return items.size();
    }
    /**
     * 增加组件
     * @param tag String
     * @param component TComponent
     */
    public void addItem(String tag,TComponent component)
    {
        items.put(tag,component);
    }
    /**
     * 得到组件
     * @param tag String
     * @return TComponent
     */
    public TComponent getItem(String tag)
    {
        return (TComponent)items.get(tag);
    }
    /**
     * 设置显示适配器
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
     * 得到显示器
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
     * 设置分色间隔行数
     * @param spacingRow int
     */
    public void setSpacingRow(int spacingRow)
    {
        this.spacingRow = spacingRow;
        updateUI();
    }
    /**
     * 得到分色间隔行数
     * @return int
     */
    public int getSpacingRow()
    {
        return spacingRow;
    }
    /**
     * 设置分色间隔列数
     * @param spacingColumn int
     */
    public void setSpacingColumn(int spacingColumn)
    {
        this.spacingColumn = spacingColumn;
        updateUI();
    }
    /**
     * 得到分色间隔列数
     * @return int
     */
    public int getSpacingColumn()
    {
        return spacingColumn;
    }
    /**
     * 设置列背景颜色
     * @param column int 列号
     * @param color Color 颜色
     */
    public void setColumnBackColor(int column,Color color)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setColumnBackColor(column,color);
    }
    /**
     * 得到列背景颜色
     * @param column int 列号
     * @return Color 颜色
     */
    public Color getColumnBackColor(int column)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return null;
        return cellRenderer.getColumnBackColor(column);
    }
    /**
     * 设置列的背景颜色
     * @param data String "1:255,0,0;2:蓝"
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
     * 设置列前景颜色
     * @param column int 列号
     * @param color Color 颜色
     */
    public void setColumnForeColor(int column,Color color)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setColumnForeColor(column,color);
    }
    /**
     * 得到列前景颜色
     * @param column int 列号
     * @return Color 颜色
     */
    public Color getColumnForeColor(int column)
    {
        TTableCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return null;
        return cellRenderer.getColumnForeColor(column);
    }
    /**
     * 设置列的前景颜色
     * @param data String "1:255,0,0;2:蓝"
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
     * 设置列文字的横向位置
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
     * 设置数据包
     * @param data Object
     */
    public void setData(Object data)
    {
        this.data = data;
    }
    /**
     * 得到数据包
     * @return Object
     */
    public Object getData()
    {
        return data;
    }
    /**
     * 设置焦点跳跃
     * @param focusIndexJump boolean　true 当到达行尾自动跳跃到下一行　false 不跳跃到下一行
     */
    public void setFocusIndexJump(boolean focusIndexJump)
    {
        this.focusIndexJump = focusIndexJump;
    }
    /**
     * 得到焦点跳跃
     * @return boolean　true 当到达行尾自动跳跃到下一行　false 不跳跃到下一行
     */
    public boolean isFocusIndexJump()
    {
        return focusIndexJump;
    }
    /**
     * 设置TParm对照
     * @param parmMap String
     */
    public void setParmMap(String parmMap)
    {
        this.parmMap = parmMap;
    }
    /**
     * 得到TParm对照
     * @return String
     */
    public String getParmMap()
    {
        return parmMap;
    }
    /**
     * 设置Module文件名
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * 得到Module文件名
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
    }
    /**
     * 设置Module方法名
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
     * 得到Module方法名
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
     * 设置Module参数
     * @param moduleParm TParm
     */
    public void setModuleParm(TParm moduleParm)
    {
        this.moduleParm = moduleParm;
    }
    /**
     * 得到Module参数
     * @return TParm
     */
    public TParm getModuleParm()
    {
        return moduleParm;
    }
    /**
     * 设置Module静态参数
     * @param moduleParmString String 参数"ID:01;AA:<I>12"
     */
    public void setModuleParmString(String moduleParmString)
    {
        this.moduleParmString = moduleParmString;
    }
    /**
     * 得到Module静态参数
     * @return String
     */
    public String getModuleParmString()
    {
        return moduleParmString;
    }
    /**
     * 设置Module动态参数
     * @param moduleParmTag String "ID:String:T_ID:L;"
     */
    public void setModuleParmTag(String moduleParmTag)
    {
        this.moduleParmTag = moduleParmTag;
    }
    /**
     * 得到Module动态参数
     * @return String
     */
    public String getModuleParmTag()
    {
        return moduleParmTag;
    }
    /**
     * 检测Tag动态参数
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
     * 执行Module查询动作
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
     * 设置修改对应组件标签列表
     * @param modifyTag String
     */
    public void setModifyTag(String modifyTag)
    {
        this.modifyTag = modifyTag;
    }
    /**
     * 得到修改对应组件标签列表
     * @return String
     */
    public String getModifyTag()
    {
        return modifyTag;
    }
    /**
     * 执行编辑带入功能
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
     * 执行Module保存动作
     * @return boolean true 成功 false 失败
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
        //取旧行数据
        parm.setRowData(-1,getParmValue(),row);
        //取update静参数据
        parm.setRowData(StringTool.getParm(getModuleParmUpdateString()));
        if(getModuleParmUpdate() != null)
            parm.setRowData(getModuleParmUpdate());
        //取页面组件数据
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
     * 执行Module插入动作
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
     * 执行Module删除动作
     * @return boolean true 成功 false 失败
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
     * 更新编辑行数据
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
     * 抓取页面组件值
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
     * 设置Module新增静态参数
     * @param moduleParmInsertString String
     */
    public void setModuleParmInsertString(String moduleParmInsertString)
    {
        this.moduleParmInsertString = moduleParmInsertString;
    }
    /**
     * 得到Module新增静态参数
     * @return String
     */
    public String getModuleParmInsertString()
    {
        return moduleParmInsertString;
    }
    /**
     * 设置Module新增Parm参数
     * @param parm TParm
     */
    public void setModuleParmInsert(TParm parm)
    {
        moduleParmInsert = parm;
    }
    /**
     * 得到Module新增Parm参数
     * @return TParm
     */
    public TParm getModuleParmInsert()
    {
        return moduleParmInsert;
    }
    /**
     * 设置Module修改Parm参数
     * @param parm TParm
     */
    public void setModuleParmUpdate(TParm parm)
    {
        moduleParmUpdate = parm;
    }
    /**
     * 得到Module修改Parm参数
     * @return TParm
     */
    public TParm getModuleParmUpdate()
    {
        return moduleParmUpdate;
    }
    /**
     * 设置Module修改静态参数
     * @param moduleParmUpdateString String
     */
    public void setModuleParmUpdateString(String moduleParmUpdateString)
    {
        this.moduleParmUpdateString = moduleParmUpdateString;
    }
    /**
     * 得到Module修改静态参数
     * @return String
     */
    public String getModuleParmUpdateString()
    {
        return moduleParmUpdateString;
    }
    /**
     * 设置是否自动更新属性对象
     * @param autoModifyObject boolean
     */
    public void setAutoModifyObject(boolean autoModifyObject)
    {
        this.autoModifyObject = autoModifyObject;
    }
    /**
     * 是否自动更新属性对象
     * @return boolean
     */
    public boolean isAutoModifyObject()
    {
        return autoModifyObject;
    }
    /**
     * 设置数据存储
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
     * 得到数据存储
     * @return TDataStore
     */
    public TDataStore getDataStore()
    {
        return dataStore;
    }
    /**
     * 设置SQL
     * @param sql String
     */
    public void setSQL(String sql)
    {
        this.sql = sql;
    }
    /**
     * 得到SQL
     * @return String
     */
    public String getSQL()
    {
        return sql;
    }
    /**
     * 设置语种对照
     * @param languageMap String zh|en
     */
    public void setLanguageMap(String languageMap)
    {
        this.languageMap = languageMap;
    }
    /**
     * 得到语种对照
     * @return String zh|en
     */
    public String getLanguageMap()
    {
        return languageMap;
    }
    /**
     * 读取数据
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
     * 更新
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
     * 清除修改记录
     */
    public void resetModify()
    {
        if(!isAutoModifyDataStore())
            return;
        getDataStore().resetModify();
    }
    /**
     * 得到SQL语句
     * @return String[]
     */
    public String[] getUpdateSQL()
    {
        if(!isAutoModifyDataStore())
            return null;
        return getDataStore().getUpdateSQL();
    }
    /**
     * 设置自动更新数据存储
     * @param autoModifyDataStore boolean
     */
    public void setAutoModifyDataStore(boolean autoModifyDataStore)
    {
        this.autoModifyDataStore = autoModifyDataStore;
    }
    /**
     * 是否自动更新数据存储
     * @return boolean
     */
    public boolean isAutoModifyDataStore()
    {
        return autoModifyDataStore;
    }
    /**
     * 设置本地Table名称
     * @param localTableName String
     */
    public void setLocalTableName(String localTableName)
    {
        this.localTableName = localTableName;
    }
    /**
     * 得到本地Table名称
     * @return String
     */
    public String getLocalTableName()
    {
        return localTableName;
    }
    /**
     * 设置过滤条件
     * @param filter String
     */
    public void setFilter(String filter)
    {
        filterString = filter;
        if(getDataStore() != null)
            getDataStore().setFilter(filter);
    }
    /**
     * 得到过滤条件
     * @return String
     */
    public String getFilter()
    {
        return filterString;
    }
    /**
     * 过滤
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
     * 同步DS数据
     */
    public void showDSData()
    {
        if(getDataStore() == null)
            return;
        //TDS操作
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
        //普通DataStore操作
        getDataStore().setFilter(getFilter());
        if(!getDataStore().filter(getShowCount()))
            return;
        if(getDataStore().rowCount() != this.getRowCount())
            setDSValue();
    }
    /**
     * 设置DataStore数据
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
     * 设置DataStore单行数据
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
     * 过滤
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
     * 设置排序条件
     * @param sort String "AAA ASC,BBB DESC"
     */
    public void setSort(String sort)
    {
        getDataStore().setSort(sort);
    }
    /**
     * 得到排序条件
     * @return String
     */
    public String getSort()
    {
        return getDataStore().getSort();
    }
    /**
     * 排序
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
     * 得到修改的行号
     * @return int[]
     */
    public int[] getModifiedRows()
    {
        return getDataStore().getModifiedRows();
    }
    /**
     * 得到修改的行号(过滤区)
     * @return int[]
     */
    public int[] getModifiedRowsFilter()
    {
        return getDataStore().getModifiedRows(TDataStore.FILTER);
    }
    /**
     * 是否修改
     * @return boolean true 有修改 false 没有修改
     */
    public boolean isModified()
    {
        return getDataStore().isModified();
    }
    /**
     * 设置值
     * @param row int 行号
     * @param column int 列号
     * @param value Object 值
     * @return boolean true 成功 false 失败
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
     * 设置值
     * @param row int 行号
     * @param column String 列名
     * @param value Object 值
     * @return boolean true 成功 false 失败
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
     * 设置值(过滤区)
     * @param row int 行号
     * @param column int 列号
     * @param value Object 值
     * @return boolean true 成功 false 失败(包括不再显示区的也返回false)
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
     * 设置值(过滤区)
     * @param row int 行号
     * @param column String 列名
     * @param value Object 值
     * @return boolean true 成功 false 失败(包括不再显示区的也返回false)
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
     * 得到数据存储对应的列名
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
     * 得到数据
     * @param row int 行号
     * @param column int 列号
     * @return Object
     */
    public Object getItemData(int row,int column)
    {
        return getItemData(row,getDataStoreColumnName(column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column String 列名
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
     * 得到数据
     * @param row int 行号
     * @param column int 列名
     * @return String
     */
    public String getItemString(int row,int column)
    {
        return TCM_Transform.getString(getItemData(row,column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column String 列名
     * @return String
     */
    public String getItemString(int row,String column)
    {
        return TCM_Transform.getString(getItemData(row,column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column int 列号
     * @return int
     */
    public int getItemInt(int row,int column)
    {
        return getItemInt(row,getDataStoreColumnName(column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column String 列名
     * @return int
     */
    public int getItemInt(int row,String column)
    {
        if (!isAutoModifyDataStore())
            return 0;
        return getDataStore().getItemInt(row, column);
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column int 列号
     * @return double
     */
    public double getItemDouble(int row,int column)
    {
        return TCM_Transform.getDouble(getItemData(row,column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column String 列名
     * @return double
     */
    public double getItemDouble(int row,String column)
    {
        return TCM_Transform.getDouble(getItemData(row,column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column int 列号
     * @return Timestamp
     */
    public Timestamp getItemTimestamp(int row,int column)
    {
        return TCM_Transform.getTimestamp(getItemData(row,column));
    }
    /**
     * 得到数据
     * @param row int 行号
     * @param column String 列号
     * @return Timestamp
     */
    public Timestamp getItemTimestamp(int row,String column)
    {
        return TCM_Transform.getTimestamp(getItemData(row,column));
    }
    /**
     * 得到自己对象
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * 设置显示行数
     * @param showCount int
     */
    public void setShowCount(int showCount)
    {
        this.showCount = showCount;
    }
    /**
     * 得到显示行数
     * @return int
     */
    public int getShowCount()
    {
        return showCount;
    }
    /**
     * 设置单击动作
     * @param action String
     */
    public void setClickedAction(String action)
    {
        actionList.setAction("clickedAction",action);
    }
    /**
     * 得到单击动作
     * @return String
     */
    public String getClickedAction()
    {
        return actionList.getAction("clickedAction");
    }
    /**
     * 设置双击动作
     * @param action String
     */
    public void setDoubleClickedAction(String action)
    {
        actionList.setAction("doubleClickedAction",action);
    }
    /**
     * 得到双击动作
     * @return String
     */
    public String getDoubleClickedAction()
    {
        return actionList.getAction("doubleClickedAction");
    }
    /**
     * 设置右击动作
     * @param action String
     */
    public void setRightClickedAction(String action)
    {
        actionList.setAction("doubleRightdAction",action);
    }
    /**
     * 得到右击动作
     * @return String
     */
    public String getRightClickedAction()
    {
        return actionList.getAction("doubleRightdAction");
    }
    /**
     * 执行动作
     * @param message String 消息
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
     * 设置行颜色
     * @param row int
     * @param color Color
     */
    public void setRowColor(int row,Color color)
    {
        rowColorMap.put(row,color);
    }
    /**
     * 删除行颜色
     * @param row int
     */
    public void removeRowColor(int row)
    {
        rowColorMap.remove(row);
    }
    /**
     * 得到行颜色
     * @param row int
     * @return Color
     */
    public Color getRowColor(int row)
    {
        return (Color)rowColorMap.get(row);
    }
    /**
     * 设置行颜色Map
     * @param map Map
     */
    public void setRowColorMap(Map map)
    {
        rowColorMap = map;
    }
    /**
     * 得到行颜色Map
     * @return Map
     */
    public Map getRowColorMap()
    {
        return rowColorMap;
    }
    /**
     * 设置行字体颜色
     * @param row int
     * @param color Color
     */
    public void setRowTextColor(int row,Color color)
    {
        rowTextColorMap.put(row,color);
    }
    /**
     * 删除行字体颜色
     * @param row int
     */
    public void removeRowTextColor(int row)
    {
        rowTextColorMap.remove(row);
    }
    /**
     * 得到行字体颜色
     * @param row int
     * @return Color
     */
    public Color getRowTextColor(int row)
    {
        return (Color)rowTextColorMap.get(row);
    }
    /**
     * 设置行字体颜色Map
     * @param map Map
     */
    public void setRowTextColorMap(Map map)
    {
        rowTextColorMap = map;
    }
    /**
     * 得到行字体颜色Map
     * @return Map
     */
    public Map getRowTextColorMap()
    {
        return rowTextColorMap;
    }
    /**
     * 得到行改变动作
     * @return String
     */
    public String getRowChangeAction()
    {
        return actionList.getAction("rowChangeAction");
    }
    /**
     * 设置行改变动作
     * @param action String
     */
    public void setRowChangeAction(String action)
    {
        actionList.setAction("rowChangeAction",action);
    }
    /**
     * 得到列改变动作
     * @return String
     */
    public String getColumnChangeAction()
    {
        return actionList.getAction("columnChangeAction");
    }
    /**
     * 设置列改变动作
     * @param action String
     */
    public void setColumnChangeAction(String action)
    {
        actionList.setAction("columnChangeAction",action);
    }
    /**
     * 得到行列改变动作
     * @return String
     */
    public String getChangeAction()
    {
        return actionList.getAction("changeAction");
    }
    /**
     * 设置行列改变动作
     * @param action String
     */
    public void setChangeAction(String action)
    {
        actionList.setAction("changeAction",action);
    }
    /**
     * 行改变事件
     */
    public void onRowChange()
    {
        exeAction(getRowChangeAction());
        onChange();
    }
    /**
     * 行改变事件
     */
    public void onColumnChange()
    {
        exeAction(getColumnChangeAction());
        onChange();
    }
    /**
     * 行列改变
     */
    public void onChange()
    {
        exeAction(getChangeAction());
    }
    /**
     * 得到值改变动作
     * @return String
     */
    public String getChangeValueAction()
    {
        return actionList.getAction("changeValueAction");
    }
    /**
     * 设置值改变动作
     * @param action String
     */
    public void setChangeValueAction(String action)
    {
        actionList.setAction("changeValueAction",action);
    }
    /**
     * 设置右键菜单语法
     * @param syntax String
     */
    public void setPopupMenuSyntax(String syntax)
    {
        popupMenuSyntax = syntax;
    }
    /**
     * 得到右键菜单语法
     * @return String
     */
    public String getPopupMenuSyntax()
    {
        return popupMenuSyntax;
    }
    /**
     * 弹出菜单
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
     * 两行数据项目交换
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
     * 两行数据项目交换
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
     * 设置英文头
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
     * 得到英文头
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
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * 设置语种
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
     * 释放
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
	 * 设置字体
	 * 
	 * @param cellFont
	 */
	public void setCellFont(String cellFont) {
		String s[] = StringTool.parseLine(cellFont, ";");
		//System.out.println("字体"+s[0]);
		//System.out.println("样式"+s[1]);
		//System.out.println("大小"+s[2]);
		setCellFont(s[0], Integer.valueOf(s[1]), Integer.valueOf(s[2]));
		getTable().updateUI();
	}

    
    
    
}
