package com.dongyang.ui.base;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import com.dongyang.util.TSystem;

public class TTableSortColumn extends TableColumn{
	
	 public TTableSortColumn(int modelIndex) {
	        super(modelIndex);
	        setHeaderRenderer(createDefaultHeaderRenderer());
	    }
	    public void setHeaderRenderer(TableCellRenderer headerRenderer) {
	        super.setHeaderRenderer(headerRenderer);
	    }
	    public TableCellRenderer getHeaderRenderer() {
	        return headerRenderer;
	    }
	    protected TableCellRenderer createDefaultHeaderRenderer() {
	        DefaultTableCellRenderer label = new DefaultTableCellRenderer() {
	            /**
	             * µÃµ½×ÖÌå
	             * @return Font
	             */
	            public Font getFont() {
	                Font f = super.getFont();
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
	            public Component getTableCellRendererComponent(JTable table, Object value,
	                         boolean isSelected, boolean hasFocus, int row, int column) {
	                if (table != null) {
	                    JTableHeader header = table.getTableHeader();
	                    if (header != null) {
	                        setForeground(header.getForeground());
	                        setBackground(header.getBackground());
	                        setFont(header.getFont());
	                    }
	                }
	                TTableSortBase t = ((JTableSortBase)table).getTable();
	                String text = (value == null) ? "" : value.toString();
	                if("en".equals(t.getLanguage()) && t.getEnHeader() != null && t.getEnHeader().length() > 0)
	                {
	                    int c = t.getColumnModel().getColumnIndex(column);
	                    String s = t.getEnHeader(c);
	                    if(s.length() > 0)
	                        text = s;
	                }
	                setText(text);
	                setBorder(UIManager.getBorder("TableHeader.cellBorder"));
	                //setBackground(new Color(161,220,230));
	                return this;
	            }
	        };
	        label.setHorizontalAlignment(JLabel.CENTER);
	        return label;
	    }

}
