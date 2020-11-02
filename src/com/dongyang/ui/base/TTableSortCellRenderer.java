package com.dongyang.ui.base;

import javax.swing.plaf.UIResource;
import javax.swing.table.TableCellRenderer;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import com.dongyang.manager.TCM_Transform;
import java.awt.Component;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.JCheckBox;
import com.dongyang.ui.TUIStyle;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.ui.TComboBox;
import com.dongyang.ui.TComponent;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicGraphicsUtils;
import java.text.DecimalFormat;
import com.dongyang.util.StringTool;
import com.dongyang.data.TNull;
import java.sql.Timestamp;
import java.awt.Font;
import com.dongyang.util.TSystem;

public class TTableSortCellRenderer extends JLabel implements TableCellRenderer, UIResource{
    Font font = TUIStyle.getTableDefaultFont();
    /**
     * �õ�����
     * @return Font
     */
    public Font getFont()
    {
        Font f = font;
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
    /**
     * Table����
     */
    private TTableSortBase table;
    /**
     * �б�����ɫMap
     */
    private Map columnBackColorMap;
    /**
     * ��ǰ����ɫMap
     */
    private Map columnForeColorMap;
    /**
     * �����λ��
     */
    private Map columnHorizontalAlignment;
    /**
     * CheckBox��ʾ����
     */
    private JCheckBox checkBox;
    private static final Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public TTableSortCellRenderer(TTableSortBase table) {
        setTable(table);
        columnBackColorMap = new HashMap();
        columnForeColorMap = new HashMap();
        columnHorizontalAlignment = new HashMap();
    }
    /**
     * ����Table����
     * @param table TTableBase
     */
    public void setTable(TTableSortBase table)
    {
        this.table = table;
    }
    /**
     * �õ�Table����
     * @return TTableBase
     */
    public TTableSortBase getTable()
    {
        return table;
    }
    /**
     * �����б�����ɫ
     * @param column int �к�
     * @param color Color ��ɫ
     */
    public void setColumnBackColor(int column,Color color)
    {
        if(color == null)
        {
            columnBackColorMap.remove(color);
            return;
        }
        columnBackColorMap.put(column,color);
    }
    /**
     * �õ��б�����ɫ
     * @param column int �к�
     * @return Color ��ɫ
     */
    public Color getColumnBackColor(int column)
    {
        return (Color)columnBackColorMap.get(column);
    }
    /**
     * ������ǰ����ɫ
     * @param column int �к�
     * @param color Color ��ɫ
     */
    public void setColumnForeColor(int column,Color color)
    {
        if(color == null)
        {
            columnForeColorMap.remove(color);
            return;
        }
        columnForeColorMap.put(column,color);
    }
    /**
     * �õ��б�����ɫ
     * @param column int �к�
     * @return Color ��ɫ
     */
    public Color getColumnForeColor(int column)
    {
        return (Color)columnForeColorMap.get(column);
    }
    /**
     * �����еĺ���λ��
     * @param column int �к�
     * @param alignment int
     */
    public void setColumnHorizontalAlignment(int column,int alignment)
    {
        columnHorizontalAlignment.put(column,alignment);
    }
    /**
     * �õ��еĺ���λ��
     * @param column int
     * @return int
     */
    public int getColumnHorizontalAlignment(int column)
    {
        Object obj = columnHorizontalAlignment.get(column);
        if(obj == null)
            return JLabel.CENTER;
        return (Integer)obj;
    }
    /**
     * �õ�CheckBox����
     * @return JCheckBox
     */
    public JCheckBox getCheckBox()
    {
        if(checkBox == null)
            initCheckBox();
        return checkBox;
    }
    /**
     * ��ʼ��CheckBox����
     */
    public void initCheckBox()
    {
        checkBox = new JCheckBox();
        checkBox.setBorderPainted(true);
        //checkBox.setHorizontalAlignment(JLabel.CENTER);
    }
    /**
     * �õ���������
     * @param row int ��
     * @param column int ��
     * @return String ����
     */
    public String getType(int row, int column)
    {
        String type = getTable().getRowColumnType(row,column);
        if(type != null)
            return type;
        TTableSortCellEditor cellEditor = getTable().getCellEditor(column);
        if(cellEditor == null)
            return null;
        return cellEditor.getType();
    }
    /**
     * �õ���ʾ���
     * @param row int
     * @param column int
     * @return Component
     */
    public Component getComponent(int row, int column)
    {
        String type = getType(row,column);
        if(type == null)
            return this;
        if("String".equalsIgnoreCase(type)||"Combo".equalsIgnoreCase(type))
            return this;
        if("Boolean".equalsIgnoreCase(type))
            return getCheckBox();
        return this;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component component = getComponent(row,column);
        //��������ĺ���λ��
        setComponentHorizontalAlignment(component,value,row,column);
        //���������ǰ����ɫ
        setComponentForeColor(component,isSelected,row,column);
        //��������ı�����ɫ
        setComponentBackColor(component,isSelected,hasFocus,row,column);
        //�������ֵ
        setComponentValue(component,value,row,column);
        //�����������
        setComponentFocus(component,hasFocus);
        return component;
    }

    /**
     * ��������ĺ���λ��
     * @param component Component
     * @param value Object
     * @param row int
     * @param column int
     */
    public void setComponentHorizontalAlignment(Component component, Object value,int row, int column)
    {
        int alignment = 0;
        String s = getTable().getHorizontalAlignment(row,getTable().getColumnModel().getColumnIndex(column));
        if(s != null && s.length() > 0)
            alignment = StringTool.horizontalAlignment(s);
        else
            alignment = getColumnHorizontalAlignment(getTable().getColumnModel().getColumnIndex(column));
        if(component instanceof JCheckBox)
        {
            ((JCheckBox) component).setHorizontalAlignment(alignment);
            return;
        }
        if(component instanceof JLabel)
        {
            ((JLabel) component).setHorizontalAlignment(alignment);
            return;
        }
    }
    /**
     * ���������ǰ����ɫ
     * @param component Component ���
     * @param isSelected boolean �Ƿ�ѡ��
     * @param row int �к�
     * @param column int �к�
     */
    public void setComponentForeColor(Component component,boolean isSelected,int row,int column)
    {
        if (isSelected)
        {
            component.setForeground(TUIStyle.getTableSelectionForeColor());
            return;
        }
        /**
         * �Զ�����������ɫ
         */
        Color color = getTable().getRowTextColor(row);
        if(color != null)
        {
            component.setForeground(color);
            return;
        }
        //�Զ�����ǰ����ɫ
        color = getColumnForeColor(getTable().getColumnModel().getColumnIndex(column));
        if(color != null)
        {
            component.setForeground(color);
            return;
        }
        component.setForeground(TUIStyle.getTableForeColor());
    }
    /**
     * ��������ı�����ɫ
     * @param component Component ���
     * @param isSelected boolean �Ƿ�ѡ��
     * @param hasFocus boolean �Ƿ��н���
     * @param row int �к�
     * @param column int �к�
     */
    public void setComponentBackColor(Component component,boolean isSelected,boolean hasFocus,int row,int column)
    {
        if (isSelected)
        {
            if(hasFocus)
                component.setBackground(TUIStyle.getTableHasFocusBackColor());
            else
                component.setBackground(TUIStyle.getTableSelectionBackColor());
            return;
        }
        /**
         * �Զ�������ɫ
         */
        Color color = getTable().getRowColor(row);
        if(color != null)
        {
            component.setBackground(color);
            return;
        }
        //�Զ����б�����ɫ
        color = getColumnBackColor(getTable().getColumnModel().getColumnIndex(column));
        if(color != null)
        {
            component.setBackground(color);
            return;
        }
        //�����зָ�����ɫ
        if(getTable().getSpacingRow() > 0)
        {
            int rowCount = getTable().getSpacingRow() + 1;
            if((row + rowCount) % rowCount == 0)
            {
                component.setBackground(TUIStyle.getTableSpacingRowBackColor());
                return;
            }
        }
        //�����зָ�����ɫ
        if(getTable().getSpacingColumn() > 0)
        {
            int columnCount = getTable().getSpacingColumn() + 1;
            if((column + columnCount) % columnCount == 0)
            {
                component.setBackground(TUIStyle.getTableSpacingColumnBackColor());
                return;
            }
        }
        component.setBackground(TUIStyle.getTableBackColor());
    }
    /**
     * �õ�comboBox���
     * @param row int ��
     * @param column int ��
     * @return TComboBox
     */
    public TComboBox getTComboBox(int row,int column)
    {
        TTableSortCellEditor cellEditor = getTable().getCellEditor(column);
        if(cellEditor == null)
            return null;
        TComponent component = cellEditor.getDelegate().getComponent();
        if(component == null||!(component instanceof TComboBox))
            return null;
        return (TComboBox)component;
    }
    /**
     * �õ�����ֵ
     * @param type String
     * @param value Object
     * @param column int
     * @return String
     */
    public String getValue(String type,Object value,int column)
    {
        String sValue = TCM_Transform.getString(value);
        TComponent tcomp = getTable().getItem(type);
        if(tcomp != null)
            sValue = (String)tcomp.callFunction("getTableShowValue","" + value);
        else if("Double".equalsIgnoreCase(type))
        {
            String format = getTable().getCellEditor(column).getDataFormat();
            if(format == null || format.length() == 0)
                format = "#########0.00";
            sValue = new DecimalFormat(format).format(TCM_Transform.getDouble(sValue));
        }
        else if(value instanceof Timestamp || "timestamp".equalsIgnoreCase(type))
        {
            String format = getTable().getCellEditor(column).getDataFormat();
            if(format == null || format.length() == 0)
                format = "yyyy/MM/dd";
            if(value instanceof Timestamp)
                sValue = StringTool.getString((Timestamp)value,format);
            if(value instanceof String)
                sValue = StringTool.getString(getTimestamp((String)value),format);

        }else if(type.startsWith("combo|"))
        {
            //combo|A:�Զ�����|B:�ֶ�����
            type = type.substring(6);
            String s[] = StringTool.parseLine(type,"|");
            for(int i = 0;i < s.length;i++)
            {
                if(s[i].startsWith(value + ":"))
                {
                    String s1 = s[i].substring(s[i].indexOf(":") + 1);
                    String s2[] = StringTool.parseLine(s1,":");
                    if(s2.length == 1)
                        sValue = s1;
                    if("en".equals(getTable().getLanguage()))
                        sValue = s2[1];
                    else
                        sValue = s2[0];
                    break;
                }javax.swing.plaf.basic.BasicTableUI a;
            }
        }
        if(sValue == null || "null".equals(sValue))
            sValue = "";
        return sValue;
    }
    public Timestamp getTimestamp(String date)
    {
        switch(date.length())
        {
        case 10:
            return StringTool.getTimestamp(date, "yyyy/MM/dd");
        case 19:
            return StringTool.getTimestamp(date, "yyyy/MM/dd HH:mm:ss");
        case 14:
            return StringTool.getTimestamp(date, "yyyyMMddHHmmss");
        case 8:
            return StringTool.getTimestamp(date, "yyyyMMdd");
        }
        return null;
    }
    /**
     * �������ֵ
     * @param component Component
     * @param value Object
     * @param row int
     * @param column int
     */
    public void setComponentValue(Component component,Object value,int row, int column)
    {
        if(value instanceof TNull)
            value = "";
        if(component instanceof JLabel)
        {
            JLabel label = (JLabel) component;
            String type = getType(row,column);
            String sValue = getValue(type,value,column);
            label.setText(sValue);
            return;
        }
        if(component instanceof JCheckBox)
        {
            JCheckBox checkBox = (JCheckBox) component;
            checkBox.setSelected(TCM_Transform.getBoolean(value));
        }
    }
    /**
     * �����������
     * @param component Component
     * @param hasFocus boolean
     */
    public void setComponentFocus(Component component,boolean hasFocus)
    {
        if(component instanceof JLabel)
        {
            JLabel label = (JLabel)component;
            if (hasFocus)
                label.setBorder(new THighlightBorder());//UIManager.getBorder("Table.focusCellHighlightBorder")
            else
                label.setBorder(noFocusBorder);
            return;
        }
        if(component instanceof JCheckBox)
        {
            JCheckBox checkBox = (JCheckBox)component;
            if (hasFocus)
                checkBox.setBorder(new THighlightBorder());
            else
                checkBox.setBorder(noFocusBorder);
            return;
        }
    }
    public void paint(Graphics g)
    {
        Rectangle r = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(r.x,r.y,r.width,r.height);
        super.paint(g);
    }
    static class THighlightBorder extends LineBorder implements UIResource {
        private Color origColor;
        private Color paintColor;

        public THighlightBorder() {
            super(null);
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            /*Color color = c.getBackground();

            if (origColor != color) {
                origColor = color;
                paintColor = new Color(~origColor.getRGB());
                         }*/
            paintColor = new Color(255,255,255);
            g.setColor(paintColor);
            BasicGraphicsUtils.drawDashedRect(g, x, y, width, height);
        }
    }
}
