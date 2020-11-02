package com.dongyang.ui.base;

import javax.swing.JLabel;
import javax.swing.ListCellRenderer;
import java.io.Serializable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.Icon;
import com.dongyang.ui.TComboNode;
import com.dongyang.util.StringTool;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;

public class TComboBoxRenderer extends JLabel implements ListCellRenderer,
        Serializable {
    private TComboBoxBase comboBox;
    /**
     * 图标加载名称列表
     */
    private String pics;
    /**
    * 图标库
    */
   private Map icons = new HashMap();
   protected static Border noFocusBorder = new EmptyBorder(1, 1, 1, 1);

    public TComboBoxRenderer(TComboBoxBase comboBox) {
        super();
        setOpaque(true);
        setBorder(noFocusBorder);
        setComboBox(comboBox);
    }

    public void setComboBox(TComboBoxBase comboBox)
    {
        this.comboBox = comboBox;
    }
    public TComboBoxBase getComboBox()
    {
        return comboBox;
    }
    /**
     * 设置图标列表
     * @param pics String
     */
    public void setPics(String pics)
    {
        this.pics = pics;
        icons = new HashMap();
        String s[] = StringTool.parseLine(pics,';',"[]{}()");
        for(int i = 0;i < s.length;i++)
        {
            String parm[] = StringTool.parseLine(s[i],':',"[]{}()");
            if(parm.length < 2)
                continue;
            String type = parm[0];
            String iconName = parm[1];
            if(iconName.trim().length() == 0)
                continue;
            Icon icon1 = createImageIcon(iconName);
            //Icon icon2 = icon1;
            if(icon1 == null)
                continue;
            /*if(parm.length >= 3 && parm[2] != null && parm[2].trim().length() > 0)
            {
                icon2 = createImageIcon(parm[2]);
                if(icon2 == null)
                    icon2 = icon1;
            }*/
            icons.put(type,icon1);
            //icons.put(type + "_E",icon2);
        }
    }
    /**
    * 得到图标列表
    * @return String
    */
   public String getPics()
   {
       return pics;
   }
   /**
    * 得到图标
    * @param type String
    * @return Icon
    */
   public Icon getPic(String type)
   {
       return (Icon)icons.get(type);
   }
   /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" + filename);
        ImageIcon icon = FileTool.getImage("image/ImageIcon/" + filename);
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
        }catch(NullPointerException e)
        {
            err("没有找到图标" + path);
        }
        return icon;
    }

    public Dimension getPreferredSize() {
        Dimension size;

        if ((this.getText() == null) || (this.getText().equals(""))) {
            setText(" ");
            size = super.getPreferredSize();
            setText("");
        } else {
            size = super.getPreferredSize();
        }

        return size;
    }

    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {

        /**if (isSelected) {
            setBackground(UIManager.getColor("ComboBox.selectionBackground"));
            setForeground(UIManager.getColor("ComboBox.selectionForeground"));
             } else {
            setBackground(UIManager.getColor("ComboBox.background"));
            setForeground(UIManager.getColor("ComboBox.foreground"));
             }**/

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setFont(list.getFont());

        /*
         if (value instanceof Icon) {
            setIcon((Icon) value);
                 } else {
            setText((value == null) ? "" : value.toString());
                 }*/
        if(value == null)
        {
            setText("");
            return this;
        }
        if(value instanceof TComboNode)
        {
            TComboNode node = (TComboNode)value;
            StringBuffer sb = new StringBuffer();
            if(getComboBox().isShowID() && node.getID() != null && node.getID().length() > 0)
                sb.append(node.getID());
            if(getComboBox().isShowName())
            {
                if("en".equals(comboBox.getLanguage()) && node.getEnName() != null && node.getEnName().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(node.getEnName());
                }
                else if(node.getName() != null && node.getName().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(node.getName());
                }
            }
            if(getComboBox().isShowText() && node.getText() != null && node.getText().length() > 0)
            {
                if("en".equals(comboBox.getLanguage()) && node.getEnText() != null && node.getEnText().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(node.getEnText());
                }
                else if(node.getText() != null && node.getText().length() > 0)
                {
                    if (sb.length() > 0)
                        sb.append(" ");
                    sb.append(node.getText());
                }
            }
            if(getComboBox().isShowValue() && node.getValue() != null && node.getValue().length() > 0)
            {
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(node.getValue());
            }
            if(getComboBox().isShowPy1() && node.getPy1() != null && node.getPy1().length() > 0)
            {
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(node.getPy1());
            }
            if(getComboBox().isShowPy2() && node.getPy2() != null && node.getPy2().length() > 0)
            {
                if(sb.length() > 0)
                    sb.append(" ");
                sb.append(node.getPy2());
            }
            setText(sb.toString());
            //设置图标
            setIcon(getPic(node.getType()));
            if(node.getColor() != null)
                setForeground(node.getColor());
        }
        return this;
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
}
