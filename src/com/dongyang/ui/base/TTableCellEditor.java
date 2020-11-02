package com.dongyang.ui.base;

import javax.swing.JComboBox;
import javax.swing.tree.TreeCellEditor;
import javax.swing.AbstractCellEditor;
import javax.swing.table.TableCellEditor;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.EventObject;
import java.awt.event.ItemListener;
import java.io.Serializable;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.ItemEvent;
import javax.swing.JTree;
import javax.swing.JTable;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.TTable;
import com.dongyang.ui.TTextField;
import com.dongyang.ui.TCheckBox;
import com.dongyang.util.StringTool;
import com.dongyang.manager.TCM_Transform;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import com.dongyang.ui.event.TCellEditorListener;
import com.dongyang.ui.TComboBox;
import com.dongyang.ui.event.TComboBoxEvent;
import com.dongyang.ui.TNumberTextField;
import com.dongyang.ui.event.TNumberTextFieldEvent;
import com.dongyang.ui.TComboNode;
import com.dongyang.ui.TTableNode;
import com.dongyang.ui.event.TTableEvent;
import java.sql.Timestamp;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.ui.TTextFormat;
import java.awt.event.KeyEvent;
import com.dongyang.ui.event.TTextFormatEvent;

public class TTableCellEditor extends AbstractCellEditor
        implements TableCellEditor, TreeCellEditor{
    /**
     * �����¼�
     */
    protected EditorDelegate delegate;
    /**
     * ����
     */
    protected String type;

    /**
     * Table���
     */
    private TTableBase table;
    /**
     * �༭��������
     */
    TCellEditorListener cellEditorListenerObject;
    /**
     * data��ʾ��ʽ�б�
     */
    public String dataFormat;
    /**
     * �Ƿ�༭
     */
    boolean isEdit;
    /**
     * ������
     * @param table TTable
     */
    public TTableCellEditor(TTable table)
    {
        this(table,"String");
    }
    /**
     * ������
     * @param table TTable
     * @param type String
     */
    public TTableCellEditor(TTableBase table,String type)
    {
        setTable(table);
        delegate = new EditorDelegate();
        setType(type);
        initListeners();
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        //�����༭�¼�
        if(cellEditorListenerObject == null)
        {
            cellEditorListenerObject = new TCellEditorListener(getTable());
            addCellEditorListener(cellEditorListenerObject);
        }
    }
    /**
     * �Ƿ����ڱ༭
     * @return boolean
     */
    public boolean isEdit()
    {
        return isEdit;
    }
    /**
     * ����Table���
     * @param table TTableBase
     */
    public void setTable(TTableBase table)
    {
        this.table = table;
    }
    /**
     * �õ�Table���
     * @return TTableBase
     */
    public TTableBase getTable()
    {
        return table;
    }
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * ����Number��ʾ��ʽ�б�
     * @param dataFormat String
     */
    public void setDataFormat(String dataFormat)
    {
        this.dataFormat = dataFormat;
    }
    /**
     * �õ���ʾ��ʽ�б�
     * @return String
     */
    public String getDataFormat()
    {
        return dataFormat;
    }
    public EditorDelegate getDelegate()
    {
        return delegate;
    }
    public Object getCellEditorValue() {
        return delegate.getCellEditorValue();
    }
    public boolean isCellEditable(EventObject anEvent) {
        return delegate.isCellEditable(anEvent);
    }
    public boolean shouldSelectCell(EventObject anEvent) {
        return delegate.shouldSelectCell(anEvent);
    }
    public boolean stopCellEditing() {
        return delegate.stopCellEditing();
    }
    public void cancelCellEditing() {
        delegate.cancelCellEditing();
    }
    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf, int row) {
        /*String         stringValue = tree.convertValueToText(value, isSelected,
                                            expanded, leaf, row, false);
        delegate.createComponent(getType());
        delegate.setValue(stringValue);
        return (Component)delegate.getComponent();*/
        return null;
    }
    /**
     * �õ�ƥ��ı༭����(ϵͳTable�ӿڷ���,Ҳ�Ǻ��ĵı༭�������ӿڷ���)
     * @param table JTable
     * @param value Object
     * @param isSelected boolean
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected,
                                                 int row, int column) {
        delegate.setComponent(null);
        //�ж��Ƿ�������
        if(getTable().isLockColumn(getTable().getColumnModel().getColumnIndex(column)))
            return null;
        //�ж��Ƿ�������
        if(getTable().isLockRow(row))
            return null;
        //�жϵ�Ԫ���Ƿ�����
        if(getTable().isLockCell(row,column))
            return null;
        //�����༭����
        String type = getTable().getRowColumnType(row,column);
        if(type == null)
            type = getType();
        delegate.createComponent(type,row,column);
        Component component = (Component)delegate.getComponent();
        if(component == null)
            return null;
        delegate.setValue(value);
        getTable().callEvent(TTableEvent.CREATE_EDIT_COMPONENT,new Object[] {component,row,column},
                         new String[] {"java.awt.Component","int","int"});
        isEdit = true;
        return component;
    }

    public class EditorDelegate implements ActionListener, ItemListener, Serializable {
        /**
         * ���
         */
        private TComponent component;
        private int editRow;
        private int editColumn;
        protected Object value;
        private boolean isMouseInEdit;

        /**
         * �������
         * @param component TComponent
         */
        public void setComponent(TComponent component)
        {
            this.component = component;
        }
        /**
         * �õ����
         * @return TComponent
         */
        public TComponent getComponent()
        {
            return component;
        }
        /**
         * �����༭���
         * @param type String
         * @param row int
         * @param column int
         */
        public void createComponent(String type,int row,int column)
        {
            editRow = row;
            editColumn = column;
            if("String".equalsIgnoreCase(type))
            {
                TTextField text = new TTextField();
                text.setTable((TTable)getTable());
                setComponent(text);
                return;
            }
            if("Boolean".equalsIgnoreCase(type))
            {
                TCheckBox checkBox = new TCheckBox();
                checkBox.setTag("CHECK_BOX");
                checkBox.onInit();
                checkBox.addEventListener(checkBox.getTag() + "->" + TActionListener.ACTION_PERFORMED,this,"onCheckBoxAction");
                setComponent(checkBox);
                return;
            }
            if("Int".equalsIgnoreCase(type))
            {
                TNumberTextField numberTextField = new TNumberTextField();
                numberTextField.setFormat("#########0");
                setComponent(numberTextField);
                return;
            }
            if("Double".equalsIgnoreCase(type))
            {
                TNumberTextField numberTextField = new TNumberTextField();
                numberTextField.setFormat("#########0.00");
                setComponent(numberTextField);
                return;
            }
            //combo|A:�Զ�����|B:�ֶ�����
            if(type.startsWith("combo|"))
            {
                type = type.substring(6);
                String s[] = StringTool.parseLine(type,"|");
                TComboBox combo = new TComboBox();
                combo.setData(s,":");
                combo.setShowName(true);
                combo.setTableShowList("Text");
                combo.onInit();
                combo.changeLanguage(getTable().getLanguage());
                combo.tableBase = getTable().getTable();
                setComponent(combo);
                return;
            }
            if(type.toLowerCase().startsWith("timestamp"))
            {
                String format = getDataFormat();
                if(format == null || format.length() == 0)
                    format = "yyyy/MM/dd";
                TTextFormat tf = new TTextFormat(){
                    public void onEscape()
                    {
                        cancelCellEditing();
                    }
                    public void onEnter()
                    {
                        stopCellEditing();
                    }
                };
                tf.setTag("TableTTextFormatItem");
                tf.setFormatType("date");
                tf.setFormat(format);
                tf.setShowDownButton(true);
                tf.onInit();
                setComponent(tf);
                return;
            }
            TComponent c = getTable().getItem(type);
            if(c instanceof TNumberTextField)
            {
                TNumberTextField oc = (TNumberTextField)c;
                TNumberTextField nc = new TNumberTextField();
                nc.setFormat(oc.getFormat());
                c = nc;
            }
            if(c instanceof TComboBox)
                ((TComboBox)c).tableBase = getTable().getTable();
            if(c instanceof TTextFormat)
                ((TTextFormat)c).tableBase = getTable().getTable();
            //�Զ������
            setComponent(c);
        }
        /**
         * ��ѡ����
         * @param e ActionEvent
         */
        public void onCheckBoxAction(ActionEvent e)
        {
            table.callEvent(TTableEvent.CHECK_BOX_CLICKED,table);
        }
        /**
         * �õ��༭���
         * @return Object
         */
        public Object getCellEditorValue() {
            //int row = getTable().getSelectedRow();
            //int column = getTable().getSelectedColumn_();
            Object oldValue = getTable().getValueAt_(editRow,editColumn);
            Object newValue = getNewEditorValue();
            if((oldValue != null && newValue == null) || newValue != null && !newValue.equals(oldValue))
            {
                //����ֵ�ı��¼�
                final TTableNode node = new TTableNode();
                node.setRow(editRow);
                node.setColumn(getTable().getColumnModel().getColumnIndex(editColumn));
                node.setOldValue(oldValue);
                node.setValue(newValue);
                node.setTable((TTable)getTable());
                Object v = getTable().callEvent(getTable().getTag() + "->" +
                                                TTableEvent.CHANGE_VALUE,
                                                new Object[]
                                                {node}, new String[]
                                                {"com.dongyang.ui.TTableNode"});
                if(v instanceof Boolean && ((Boolean)v))
                    return oldValue;
                v = getTable().callMessage(getTable().getTag() + "->" +
                                           TTableEvent.CHANGE_VALUE, node);
                if(v instanceof Boolean && ((Boolean)v))
                    return oldValue;
                getTable().onChangeValuePrivate(node);
                newValue = node.getValue();
            }
            return newValue;
        }

        /**
         * �õ��±༭��ֵ
         * @return Object
         */
        public Object getNewEditorValue()
        {
            if(getComponent() == null)
                return "";
            if(getComponent() instanceof TCheckBox)
            {
                TCheckBox checkBox = (TCheckBox)getComponent();
                return checkBox.isSelected()?"Y":"N";
            }
            if(getComponent() instanceof TComboBox)
            {
                TComboBox comboBox = (TComboBox)getComponent();
                TComboNode node = comboBox.getEditorItem();
                if(node == null)
                    return "";
                return node.getID();
            }
            if(getComponent() instanceof TTextFormat)
            {
                TTextFormat textFormat = (TTextFormat)getComponent();
                return textFormat.getValue();
            }
            if(getComponent() instanceof TNumberTextField)
            {
                TNumberTextField numberTextField = (TNumberTextField)getComponent();
                Object value = numberTextField.formatText();
                return value;
            }
            return (String)getComponent().callFunction("getText");
        }
        /**
         * ���ñ༭��ԭʼֵ
         * @param value Object
         */
        public void setValue(Object value) {
            if(getComponent() == null)
                return;
            if(getComponent() instanceof TCheckBox)
            {
                TCheckBox checkBox = (TCheckBox)getComponent();
                checkBox.setHorizontalAlignment(SwingConstants.CENTER);
                checkBox.setSelected(TCM_Transform.getBoolean(value));
                return;
            }
            if(getComponent() instanceof TComboBox)
            {
                TComboBox comboBox = (TComboBox)getComponent();
                comboBox.setSelectedID(TCM_Transform.getString(value));
                comboBox.selectAll();
                getComponent().callFunction("initListeners");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TComboBoxEvent.EDIT_ENTER,EditorDelegate.this,"onEditEnter");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TComboBoxEvent.EDIT_ESC,EditorDelegate.this,"onEditEsc");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TComboBoxEvent.EDIT_TAB,EditorDelegate.this,"onEditTab");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TComboBoxEvent.EDIT_SHIFT_TAB,EditorDelegate.this,"onEditShiftTab");
                if(!comboBox.isEditable())
                    getComponent().callFunction("addActionListener",delegate);
                postSelectAll();
                return;
            }
            if(getComponent() instanceof TTextFormat)
            {
                final TTextFormat textFormat = (TTextFormat)getComponent();
                textFormat.setValue(value);
                textFormat.addEventListener(textFormat.getTag() + "->" + TTextFormatEvent.EDIT_ENTER,EditorDelegate.this,"onEditEnter");
                textFormat.addEventListener(textFormat.getTag() + "->" + TTextFormatEvent.EDIT_ESC,EditorDelegate.this,"onEditEsc");
                textFormat.addEventListener(textFormat.getTag() + "->" + TTextFormatEvent.EDIT_TAB,EditorDelegate.this,"onEditTab");
                textFormat.addEventListener(textFormat.getTag() + "->" + TTextFormatEvent.EDIT_SHIFT_TAB,EditorDelegate.this,"onEditShiftTab");
                SwingUtilities.invokeLater(new Runnable()
                {
                    public void run()
                    {
                        textFormat.selectAll();
                    }
                });
                return;
            }
            if(getComponent() instanceof TNumberTextField)
            {
                TNumberTextField numberTextField = (TNumberTextField)getComponent();
                if(getDataFormat() != null && getDataFormat().length() > 0)
                    numberTextField.setFormat(getDataFormat());
                numberTextField.setText(TCM_Transform.getString(value));
                getComponent().callFunction("selectAll");
                getComponent().callFunction("initListeners");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TNumberTextFieldEvent.EDIT_ENTER,EditorDelegate.this,"onEditEnter");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TNumberTextFieldEvent.EDIT_ESC,EditorDelegate.this,"onEditEsc");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TNumberTextFieldEvent.EDIT_TAB,EditorDelegate.this,"onEditTab");
                getComponent().callFunction("addEventListener", getComponent().getTag() + "->" + TNumberTextFieldEvent.EDIT_SHIFT_TAB,EditorDelegate.this,"onEditShiftTab");
                getComponent().callFunction("addActionListener",delegate);
                postSelectAll();
                return;
            }
            if(value instanceof Timestamp)
                value = StringTool.getString((Timestamp)value,"yyyy/MM/dd HH:mm:ss");
            if(value instanceof Integer || value instanceof Long ||value instanceof Double)
                value = TCM_Transform.getString(value);
            getComponent().callFunction("setText",value);
            getComponent().callFunction("selectAll");
            getComponent().callFunction("addActionListener",delegate);
            postSelectAll();
        }
        /**
         * �첽ȫ��ѡ��
         */
        public void postSelectAll()
        {
            if(!isMouseInEdit)
                return;
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    getComponent().callFunction("selectAll");
                }
            });
            isMouseInEdit = false;
        }
        /**
         * �༭�а���Tab
         */
        public void onEditTab()
        {
            stopCellEditing();
            getTable().onTabFocus();
        }
        /**
         * �༭�а��� Shift + Tab
         */
        public void onEditShiftTab()
        {
            stopCellEditing();
            int row = getTable().getSelectedRow();
            int column = getTable().getSelectedColumn_();
            if(column - 1 >= 0)
                getTable().setSelectedColumn_(column - 1);
            else
            {
                if(row - 1 >= 0)
                    getTable().setSelectedRow(row - 1);
                else
                    getTable().setSelectedRow(getTable().getRowCount() - 1);
                getTable().setSelectedColumn_(getTable().getColumnCount() - 1);
            }
        }
        /**
         * �༭�а��� Enter
         */
        public void onEditEnter()
        {
            stopCellEditing();
            getTable().onEnterFocus();
        }
        /**
         * �༭�а��� Esc
         */
        public void onEditEsc()
        {
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                    cancelCellEditing();
                }
            });
        }
        public boolean isCellEditable(EventObject anEvent) {
            if (anEvent instanceof MouseEvent) {
                if(((MouseEvent)anEvent).getClickCount() >= getTable().getClickCountToStart())
                {
                    isMouseInEdit = true;
                    return true;
                }
                return false;
            }
            return true;
        }
        public boolean shouldSelectCell(EventObject anEvent) {
            return true;
        }

        public boolean startCellEditing(EventObject anEvent) {
            return true;
        }

        public boolean stopCellEditing() {
            if(!isEdit)
                return true;
            isEdit = false;
            fireEditingStopped();
            //getTable().getTable().grabFocus();
            return true;
        }

        public void cancelCellEditing() {
            if(!isEdit)
                return;
            isEdit = false;
            fireEditingCanceled();
            //getTable().getTable().grabFocus();
        }

        public void actionPerformed(ActionEvent e) {
            TTableCellEditor.this.stopCellEditing();
        }

        public void itemStateChanged(ItemEvent e) {
            TTableCellEditor.this.stopCellEditing();
        }
    }
}
