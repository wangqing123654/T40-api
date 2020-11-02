package com.dongyang.ui;

import com.dongyang.ui.base.TTableBase;
import com.dongyang.ui.edit.TAttributeList.TAttribute;
import com.dongyang.ui.edit.TUIAdapter;
import com.dongyang.config.TConfigParse.Row;
import com.dongyang.util.StringTool;
import com.dongyang.config.TConfigParse.TObject;
import com.dongyang.ui.edit.TAttributeList;

public class TTable extends TTableBase{
    /**
     * UI������
     */
    private TUIAdapter uiAdapter;
    /**
     * �Ƿ��Ǳ༭�װ�
     */
    private boolean editBoard;
    /**
     * ���ö���
     */
    private TObject tobject;
    /**
     * ����UI������
     * @param uiAdapter TUIAdapter
     */
    public void setUIAdapter(TUIAdapter uiAdapter)
    {
        this.uiAdapter = uiAdapter;
    }
    /**
     * �õ�UI������
     * @return TUIAdapter
     */
    public TUIAdapter getUIAdapter()
    {
        return uiAdapter;
    }
    /**
     * ���óɱ༭�װ�
     * @param editBoard boolean
     */
    public void setEditBoard(boolean editBoard)
    {
        this.editBoard = editBoard;
    }
    /**
     * �Ƿ��Ǳ༭�װ�
     * @return boolean
     */
    public boolean isEditBoard()
    {
        return editBoard;
    }
    /**
     * �������ö���
     * @param tobject TObject
     */
    public void setTObject(TObject tobject)
    {
        this.tobject = tobject;
    }
    /**
     * �õ����ö���
     * @return TObject
     */
    public TObject getTObject()
    {
        return tobject;
    }
    /**
     * ����UI������
     */
    public void createUIAdapter()
    {
        if(getUIAdapter() != null)
            destroyUIAdapter();
        setUIAdapter(new TUIAdapter(this));
    }
    /**
     * �ͷ�UI������
     */
    public void destroyUIAdapter()
    {
        if(getUIAdapter() == null)
            return;
        getUIAdapter().destroy();
        setUIAdapter(null);
    }
    /**
     * ����������
     */
    public void startUIAdapter()
    {
        startUIAdapter(false);
    }
    /**
     * ����������
     * @param isBoard boolean true �ǵװ� false ����
     */
    public void startUIAdapter(boolean isBoard)
    {
        //���ñ༭�װ�
        setEditBoard(isBoard);
        //����UI������
        createUIAdapter();
        if(isBoard)
            //�����ұߡ���������½ǿ�����������
            getUIAdapter().setListPanes("2,4,7");
        //��������
        getUIAdapter().setScreen(true);
    }
    /**
     * ֹͣ������
     */
    public void stopUIAdapter()
    {
        destroyUIAdapter();
    }
    /**
     * ���ص��Զ���
     * @param object TObject
     */
    public void loadTObject(TObject object)
    {
        if(object == null)
            return;
        //�������ö���
        setTObject(object);
        //�������ö���
        object.setComponent(this);
        //ִ�г���
        Row[] rows = object.getRows();
        for(int i = 0;i < rows.length;i++)
        {
            Row row = rows[i];
            String name = row.getName();
            if("ControlClassName".equalsIgnoreCase(name))
                continue;
            if("Item".equalsIgnoreCase(name))
                continue;
            String value = row.getValue();
            onBaseMessage(name + "=" + value,null);
        }
    }
    /**
     * �½�����ĳ�ʼֵ
     * @param object TObject
     */
    public void createInit(TObject object)
    {
        if(object == null)
            return;
        object.setValue("Width","81");
        object.setValue("Height","81");
        object.setValue("SpacingRow","1");
        object.setValue("RowHeight","20");
        
    }
    /**
     * �õ������б�
     * @return TAttributeList
     */
    public TAttributeList getAttributes()
    {
        TAttributeList data = new TAttributeList();
        data.add(new TAttribute("Tag","String","","Left"));
        data.add(new TAttribute("Visible","boolean","Y","Center"));
        data.add(new TAttribute("Enabled","boolean","Y","Center"));
        data.add(new TAttribute("Name","String","","Left"));
        data.add(new TAttribute("Text","String","","Left"));
        data.add(new TAttribute("Tip","String","","Left"));
        data.add(new TAttribute("ControlClassName","String","","Left"));
        data.add(new TAttribute("Header","String","","Left"));
        data.add(new TAttribute("enHeader","String","","Left"));
        data.add(new TAttribute("StringData","String","","Left"));
        data.add(new TAttribute("LockRows","String","","Left"));
        data.add(new TAttribute("LockColumns","String","","Left"));
        data.add(new TAttribute("SpacingRow","int","0","Right"));
        data.add(new TAttribute("SpacingColumn","int","0","Right"));
        data.add(new TAttribute("RowHeight","int","0","Right"));
        data.add(new TAttribute("FocusType","FocusTypeComboBox","1","Left"));
        data.add(new TAttribute("FocusIndexJump","boolean","Y","Center"));
        data.add(new TAttribute("FocusIndexList","String","","Left"));
        data.add(new TAttribute("AutoResizeMode","AutoResizeModeComboBox","0","Left"));
        data.add(new TAttribute("SelectionMode","SelectionModeComboBox","0","Left"));
        data.add(new TAttribute("RowSelectionAllowed","boolean","Y","Center"));
        data.add(new TAttribute("ColumnSelectionAllowed","boolean","N","Center"));
        data.add(new TAttribute("ColumnHorizontalAlignmentData","String","","Left"));
        data.add(new TAttribute("HorizontalAlignmentData","String","","Left"));
        data.add(new TAttribute("Item","String","","Left"));
        data.add(new TAttribute("ModuleName","String","","Left"));
        data.add(new TAttribute("ModuleMethodName","String","","Left"));
        data.add(new TAttribute("ModuleParmString","String","","Left"));
        data.add(new TAttribute("ModuleParmTag","String","","Left"));
        data.add(new TAttribute("ParmMap","String","","Left","TYPE:TextArea;TITLE:ParmMap[��λ�ö���Parm]"));
        data.add(new TAttribute("ModifyTag","String","","Left"));
        data.add(new TAttribute("ModuleParmInsertString","String","","Left"));
        data.add(new TAttribute("ModuleParmUpdateString","String","","Left"));
        data.add(new TAttribute("AutoModifyObject","boolean","N","Center"));
        data.add(new TAttribute("SQL","String","","Left"));
        data.add(new TAttribute("LocalTableName","String","","Left"));
        data.add(new TAttribute("AutoModifyDataStore","boolean","N","Center"));
        data.add(new TAttribute("LanguageMap","String","","Left"));
        data.add(new TAttribute("ShowCount","int","0","Right"));
        data.add(new TAttribute("PopupMenuSyntax","String","","Left"));
        data.add(new TAttribute("ClickedAction","String","","Left"));
        data.add(new TAttribute("RightClickedAction","String","","Left"));
        data.add(new TAttribute("DoubleClickedAction","String","","Left"));
        data.add(new TAttribute("RowChangeAction","String","","Left"));
        data.add(new TAttribute("ColumnChangeAction","String","","Left"));
        data.add(new TAttribute("ChangeAction","String","","Left"));
        data.add(new TAttribute("ChangeValueAction","String","","Left"));
        data.add(new TAttribute("X","int","0","Right"));
        data.add(new TAttribute("Y","int","0","Right"));
        data.add(new TAttribute("Width","int","0","Right"));
        data.add(new TAttribute("Height","int","0","Right"));
        data.add(new TAttribute("AutoX","boolean","N","Center"));
        data.add(new TAttribute("AutoY","boolean","N","Center"));
        data.add(new TAttribute("AutoWidth","boolean","N","Center"));
        data.add(new TAttribute("AutoHeight","boolean","N","Center"));
        data.add(new TAttribute("AutoSize","int","5","Right"));
        data.add(new TAttribute("CellFont","String","","Left"));
        //
       
        return data;
    }
    /**
     * ��������
     * @param name String ������
     * @param value String ����ֵ
     */
    public void setAttribute(String name,String value)
    {
        if("Tag".equalsIgnoreCase(name))
        {
            setTag(value);
            getTObject().setTag(value);
            if(getUIAdapter() != null)
                getUIAdapter().modifiedTag();
            return;
        }
        if("Visible".equalsIgnoreCase(name))
        {
            setVisible(StringTool.getBoolean(value));
            getTObject().setValue("Visible",value);
            return;
        }
        if("Enabled".equalsIgnoreCase(name))
        {
            setEnabled(StringTool.getBoolean(value));
            getTObject().setValue("Enabled",value);
            return;
        }
        if("Name".equalsIgnoreCase(name))
        {
            setName(value);
            getTObject().setValue("Name",value);
            return;
        }
        if("Tip".equalsIgnoreCase(name))
        {
            setToolTipText(value);
            getTObject().setValue("Tip",value);
            return;
        }
        if("ControlClassName".equalsIgnoreCase(name))
        {
            getTObject().setValue("ControlClassName",value);
            return;
        }
        if("Text".equalsIgnoreCase(name))
        {
            setText(value);
            getTObject().setValue("Text",value);
            return;
        }
        if("Header".equalsIgnoreCase(name))
        {
            setHeader(value);
            getTObject().setValue("Header",value);
            return;
        }
        if("enHeader".equalsIgnoreCase(name))
        {
            setEnHeader(value);
            getTObject().setValue("enHeader",value);
            return;
        }
        if("StringData".equalsIgnoreCase(name))
        {
            setStringData(value);
            getTObject().setValue("StringData",value);
            return;
        }
        if("LockRows".equalsIgnoreCase(name))
        {
            setLockRows(value);
            getTObject().setValue("LockRows",value);
            return;
        }
        if("LockColumns".equalsIgnoreCase(name))
        {
            setLockColumns(value);
            getTObject().setValue("LockColumns",value);
            return;
        }
        if("SpacingRow".equalsIgnoreCase(name))
        {
            setSpacingRow(StringTool.getInt(value));
            getTObject().setValue("SpacingRow",value);
            return;
        }
        if("SpacingColumn".equalsIgnoreCase(name))
        {
            setSpacingColumn(StringTool.getInt(value));
            getTObject().setValue("SpacingColumn",value);
            return;
        }
        if("RowHeight".equalsIgnoreCase(name))
        {
            setRowHeight(StringTool.getInt(value));
            getTObject().setValue("RowHeight",value);
            return;
        }
        if("FocusType".equalsIgnoreCase(name))
        {
            setFocusType(StringTool.getInt(value));
            getTObject().setValue("FocusType",value);
            return;
        }
        if("FocusIndexJump".equalsIgnoreCase(name))
        {
            setFocusIndexJump(StringTool.getBoolean(value));
            getTObject().setValue("FocusIndexJump",value);
            return;
        }
        if("FocusIndexList".equalsIgnoreCase(name))
        {
            setFocusIndexList(value);
            getTObject().setValue("FocusIndexList",value);
            return;
        }
        if("AutoResizeMode".equalsIgnoreCase(name))
        {
            setAutoResizeMode(StringTool.getInt(value));
            getTObject().setValue("AutoResizeMode",value);
            return;
        }
        if("SelectionMode".equalsIgnoreCase(name))
        {
            setSelectionMode(StringTool.getInt(value));
            getTObject().setValue("SelectionMode",value);
            return;
        }
        if("RowSelectionAllowed".equalsIgnoreCase(name))
        {
            setRowSelectionAllowed(StringTool.getBoolean(value));
            getTObject().setValue("RowSelectionAllowed",value);
            return;
        }
        if("ColumnSelectionAllowed".equalsIgnoreCase(name))
        {
            setColumnSelectionAllowed(StringTool.getBoolean(value));
            getTObject().setValue("ColumnSelectionAllowed",value);
            return;
        }
        if("HorizontalAlignmentData".equalsIgnoreCase(name))
        {
            setHorizontalAlignmentData(value);
            getTObject().setValue("HorizontalAlignmentData",value);
            return;
        }
        if("ColumnHorizontalAlignmentData".equalsIgnoreCase(name))
        {
            setColumnHorizontalAlignmentData(value);
            getTObject().setValue("ColumnHorizontalAlignmentData",value);
            return;
        }
        if("Item".equalsIgnoreCase(name))
        {
            setItem(value);
            getTObject().setValue("Item",value);
            return;
        }
        if("ModuleName".equalsIgnoreCase(name))
        {
            setModuleName(value);
            getTObject().setValue("ModuleName",value);
            return;
        }
        if("ModuleMethodName".equalsIgnoreCase(name))
        {
            setModuleMethodName(value);
            getTObject().setValue("ModuleMethodName",value);
            return;
        }
        if("ModuleParmString".equalsIgnoreCase(name))
        {
            setModuleParmString(value);
            getTObject().setValue("ModuleParmString",value);
            return;
        }
        if("ModuleParmTag".equalsIgnoreCase(name))
        {
            setModuleParmTag(value);
            getTObject().setValue("ModuleParmTag",value);
            return;
        }
        if("ParmMap".equalsIgnoreCase(name))
        {
            setParmMap(value);
            getTObject().setValue("ParmMap",value);
            return;
        }
        if("ModifyTag".equalsIgnoreCase(name))
        {
            setModifyTag(value);
            getTObject().setValue("ModifyTag",value);
            return;
        }
        if("ModuleParmInsertString".equalsIgnoreCase(name))
        {
            setModuleParmInsertString(value);
            getTObject().setValue("ModuleParmInsertString",value);
            return;
        }
        if("ModuleParmUpdateString".equalsIgnoreCase(name))
        {
            setModuleParmUpdateString(value);
            getTObject().setValue("ModuleParmUpdateString",value);
            return;
        }
        if("AutoModifyObject".equalsIgnoreCase(name))
        {
            setAutoModifyObject(StringTool.getBoolean(value));
            getTObject().setValue("AutoModifyObject",value);
            return;
        }
        if("SQL".equalsIgnoreCase(name))
        {
            setSQL(value);
            getTObject().setValue("SQL",value);
            return;
        }
        if("LocalTableName".equalsIgnoreCase(name))
        {
            setLocalTableName(value);
            getTObject().setValue("LocalTableName",value);
            return;
        }
        if("LanguageMap".equalsIgnoreCase(name))
        {
            setLanguageMap(value);
            getTObject().setValue("LanguageMap",value);
            return;
        }
        if("AutoModifyDataStore".equalsIgnoreCase(name))
        {
            setAutoModifyDataStore(StringTool.getBoolean(value));
            getTObject().setValue("AutoModifyDataStore",value);
            return;
        }
        if("ShowCount".equalsIgnoreCase(name))
        {
            setShowCount(StringTool.getInt(value));
            getTObject().setValue("ShowCount",value);
            return;
        }
        if("PopupMenuSyntax".equalsIgnoreCase(name))
        {
            setPopupMenuSyntax(value);
            getTObject().setValue("PopupMenuSyntax",value);
            return;
        }
        if("ClickedAction".equalsIgnoreCase(name))
        {
            setClickedAction(value);
            getTObject().setValue("ClickedAction",value);
            return;
        }
        if("DoubleClickedAction".equalsIgnoreCase(name))
        {
            setDoubleClickedAction(value);
            getTObject().setValue("DoubleClickedAction",value);
            return;
        }
        if("RightClickedAction".equalsIgnoreCase(name))
        {
            setRightClickedAction(value);
            getTObject().setValue("RightClickedAction",value);
            return;
        }
        if("RowChangeAction".equalsIgnoreCase(name))
        {
            setRowChangeAction(value);
            getTObject().setValue("RowChangeAction",value);
            return;
        }
        if("ColumnChangeAction".equalsIgnoreCase(name))
        {
            setColumnChangeAction(value);
            getTObject().setValue("ColumnChangeAction",value);
            return;
        }
        if("ChangeAction".equalsIgnoreCase(name))
        {
            setChangeAction(value);
            getTObject().setValue("ChangeAction",value);
            return;
        }
        if("ChangeValueAction".equalsIgnoreCase(name))
        {
            setChangeValueAction(value);
            getTObject().setValue("ChangeValueAction",value);
            return;
        }

        if("X".equalsIgnoreCase(name))
        {
            setX(StringTool.getInt(value));
            return;
        }
        if("Y".equalsIgnoreCase(name))
        {
            setY(StringTool.getInt(value));
            return;
        }
        if("Width".equalsIgnoreCase(name))
        {
            setWidth(StringTool.getInt(value));
            return;
        }
        if("Height".equalsIgnoreCase(name))
        {
            setHeight(StringTool.getInt(value));
            return;
        }
        if("AutoX".equalsIgnoreCase(name))
        {
            setAutoX(StringTool.getBoolean(value));
            getTObject().setValue("AutoX",value);
            return;
        }
        if("AutoY".equalsIgnoreCase(name))
        {
            setAutoY(StringTool.getBoolean(value));
            getTObject().setValue("AutoY",value);
            return;
        }
        if("AutoWidth".equalsIgnoreCase(name))
        {
            setAutoWidth(StringTool.getBoolean(value));
            getTObject().setValue("AutoWidth",value);
            return;
        }
        if("AutoHeight".equalsIgnoreCase(name))
        {
            setAutoHeight(StringTool.getBoolean(value));
            getTObject().setValue("AutoHeight",value);
            return;
        }
        if("AutoSize".equalsIgnoreCase(name))
        {
            setAutoSize(StringTool.getInt(value));
            getTObject().setValue("AutoSize",value);
            return;
        }
        if("CellFont".equalsIgnoreCase(name))
        {
            setColumnHorizontalAlignmentData(value);
            getTObject().setValue("CellFont",value);
            return;
        }
        
    }
    /**
     * ����X
     * @param x int
     */
    public void setX(int x)
    {
        int oldx = getX();
        if(oldx == x)
            return;
        super.setX(x);
        if(getUIAdapter() != null)
            getUIAdapter().setX(x,oldx);
        updateUI();
    }
    /**
     * ����Y
     * @param y int
     */
    public void setY(int y)
    {
        int oldy = getY();
        if(oldy == y)
            return;
        super.setY(y);
        if(getUIAdapter() != null)
            getUIAdapter().setY(y,oldy);
        updateUI();
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        int oldWidth = getWidth();
        if(oldWidth == width)
            return;
        super.setWidth(width);
        if(getUIAdapter() != null)
            getUIAdapter().setWidth(width,oldWidth);
        updateUI();
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        int oldHeight = getHeight();
        if(oldHeight == height)
            return;
        super.setHeight(height);
        if(getUIAdapter() != null)
            getUIAdapter().setHeight(height,oldHeight);
        updateUI();
    }
    
}
