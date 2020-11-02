package com.dongyang.ui.base;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import com.dongyang.ui.TComboBox;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import com.dongyang.ui.event.TTableEvent;
import com.dongyang.ui.TUIStyle;
import javax.swing.DefaultListSelectionModel;
import javax.swing.plaf.TableUI;
import javax.swing.plaf.basic.BasicTableUI;

import com.dongyang.ui.TTextFormat;
import javax.swing.SwingUtilities;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import java.awt.Font;
import com.dongyang.util.TSystem;

public class JTableBase extends JTable{
    /**
     * TTable对象
     */
    private TTableBase table;
    /**
     * 显示适配器
     */
    TTableCellRenderer cellRenderer;
    /**
     * 字体
     */
    private Font tfont;
    /**
     * Constructs a default <code>JTableBase</code> that is initialized with a default
     * data model, a default column model, and a default selection
     * model.
     *
     * @see #createDefaultDataModel
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public JTableBase() {
        this(null, null, null);
    }

    /**
     * Constructs a <code>JTableBase</code> that is initialized with
     * <code>dm</code> as the data model, a default column model,
     * and a default selection model.
     *
     * @param dm        the data model for the table
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public JTableBase(TableModel dm) {
        this(dm, null, null);
    }

    /**
     * Constructs a <code>JTableBase</code> that is initialized with
     * <code>dm</code> as the data model, <code>cm</code>
     * as the column model, and a default selection model.
     *
     * @param dm        the data model for the table
     * @param cm        the column model for the table
     * @see #createDefaultSelectionModel
     */
    public JTableBase(TableModel dm, TableColumnModel cm) {
        this(dm, cm, null);
    }

    /**
     * Constructs a <code>JTableBase</code> that is initialized with
     * <code>dm</code> as the data model, <code>cm</code> as the
     * column model, and <code>sm</code> as the selection model.
     * If any of the parameters are <code>null</code> this method
     * will initialize the table with the corresponding default model.
     * The <code>autoCreateColumnsFromModel</code> flag is set to false
     * if <code>cm</code> is non-null, otherwise it is set to true
     * and the column model is populated with suitable
     * <code>TableColumns</code> for the columns in <code>dm</code>.
     *
     * @param dm        the data model for the table
     * @param cm        the column model for the table
     * @param sm        the row selection model for the table
     * @see #createDefaultDataModel
     * @see #createDefaultColumnModel
     * @see #createDefaultSelectionModel
     */
    public JTableBase(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        setTFont(TUIStyle.getTableDefaultFont());
        setAutoCreateColumnsFromModel(true);
        setSurrendersFocusOnKeystroke(true);
        addMouseMotionListener(new TTableMouseMotionAdapter());
        addMouseListener(new TTableMouseAdapter());
    }
    public void updateUI() {
        super.updateUI();
    }
    public void setUI(TableUI ui) {

    	//拖拽时用的TableUI不同
    	if( this.getDragEnabled() ){
    		 ui = new BasicTableUI();
    	}else{
    		 ui = new TTableUI();
    	}

        super.setUI(ui);
    }
    /**
     * 设置Table对象
     * @param table TTableBase
     */
    public void setTable(TTableBase table)
    {
        this.table = table;
    }
    /**
     * 得到Table对象
     * @return TTableBase
     */
    public TTableBase getTable()
    {
        return table;
    }
    /*protected JTableHeader createDefaultTableHeader() {
        return new TTableHeader(columnModel);
    }*/
    public void createDefaultColumnsFromModel() {
        TableModel m = getModel();
        if (m != null) {
            // Remove any current columns
            TableColumnModel cm = getColumnModel();
            while (cm.getColumnCount() > 0) {
                cm.removeColumn(cm.getColumn(0));
            }

            // Create new columns from the data model info
            for (int i = 0; i < m.getColumnCount(); i++) {
                TableColumn newColumn = new TTableColumn(i);
                addColumn(newColumn);
            }
        }
    }
    /**
     * 设置显示适配器
     * @param cellRenderer TTableCellRenderer
     */
    public void setCellRenderer(TTableCellRenderer cellRenderer)
    {
        this.cellRenderer = cellRenderer;
    }
    /**
     * 得到显示器
     * @return TTableCellRenderer
     */
    public TTableCellRenderer getCellRenderer()
    {
        return cellRenderer;
    }
    public TableCellRenderer getCellRenderer(int row, int column)
    {
        return getCellRenderer();
    }
    protected boolean processKeyBinding(KeyStroke ks, final KeyEvent e,
                                        int condition, boolean pressed) {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            if(!pressed)
                return true;
            int column = getTable().getSelectedColumn_();
            if(column >= 0)
                getTable().getCellEditor(column).getDelegate().stopCellEditing();
            getTable().onEnterFocus();
            return true;
        }
        boolean retValue = super.processKeyBinding(ks, e, condition, pressed);
        if(retValue)
            return true;
        if(condition != WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            return false;
        if(!isFocusOwner())
            return false;
        if(e.getKeyCode() == 0)
            return false;
        switch(e.getKeyCode())
        {
        case 9:
        case 37:
        case 38:
        case 39:
        case 40:
            return false;
        }
        Component editorComponent = getEditorComponent();
        if (editorComponent == null)
            return false;
        if (editorComponent instanceof TComboBox)
        {
            TComboBox comboBox = (TComboBox) editorComponent;
            comboBox.getEditorComponent().setText("" + e.getKeyChar());
            return false;
        }
        if(editorComponent instanceof TTextFormat)
        {
            final TTextFormat textFormat = (TTextFormat)editorComponent;
            SwingUtilities.invokeLater(new Runnable()
            {
                public void run()
                {
                    textFormat.keyTyped(e);
                    //textFormat.selectTextLast();
                }
            });
            return false;
        }
        return false;
    }
    class TTableMouseMotionAdapter extends MouseMotionAdapter
    {
        public void mouseDragged(MouseEvent e)
        {
            getTable().acceptText();
        }
    }
    class TTableMouseAdapter extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            int row = rowAtPoint(e.getPoint());
            if(row < 0)
                return;
            if(getTable() == null)
                return;
            if(getSelectedRow() != row)
                getTable().setSelectedRow(row);
            if(e.getButton() == 1 && e.getClickCount() == 2)
                getTable().callEvent(getTable().getTag() + "->" + TTableEvent.DOUBLE_CLICKED,new Object[]{row},new String[]{"int"});
            if(e.getButton() == 3)
            {
                getTable().acceptText();
                getTable().callEvent(getTable().getTag() + "->" +
                                     TTableEvent.RIGHT_CLICKED, new Object[]
                                     {row, e.getX(), e.getY()}, new String[]
                                     {"int", "int", "int"});
            }
        }

        public void mouseReleased(MouseEvent e)
        {
            int row = getTable().getSelectedRow();
            if(getTable() == null)
                return;
            if(e.getButton() == 1)
            {
                if(e.getClickCount() == 1)
                    getTable().callEvent(getTable().getTag() + "->" + TTableEvent.CLICKED,new Object[]{row},new String[]{"int"});
            }
        }
    }
    /**
     * 设置字体
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getFont() {
        Font f = getTFont();
        if(f == null)
            return super.getFont();
        String language = (String)TSystem.getObject("Language");
        if(language == null)
            return f;
        if("zh".equals(language))
        {
            Object obj = TSystem.getObject("ZhFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        if("en".equals(language))
        {
            Object obj = TSystem.getObject("EnFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        return f;
    }
}
