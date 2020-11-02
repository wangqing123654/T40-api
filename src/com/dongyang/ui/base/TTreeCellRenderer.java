package com.dongyang.ui.base;

import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.JTree;
import java.awt.Component;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import com.dongyang.util.Log;
import com.dongyang.util.StringTool;
import java.util.Map;
import java.util.HashMap;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import java.awt.BorderLayout;
import javax.swing.JRadioButton;
import com.dongyang.ui.TTreeNode;
import java.awt.Font;
import com.dongyang.util.TSystem;

public class TTreeCellRenderer extends DefaultTreeCellRenderer{
    /**
     * 图标库
     */
    private Map icons = new HashMap();
    public static JCheckBox checkBox = new JCheckBox();
    public static JRadioButton radioButton = new JRadioButton();
    private Log log;
    /**
     * 构造器
     */
    /**
     * 图标加载名称列表
     */
    private String pics;
    /**
     * 显示ID
     */
    private boolean showID;
    /**
     * 显示名称
     */
    private boolean showName;
    /**
     * 显示值
     */
    private boolean showValue;
    /**
     *  构造器
     */
    public TTreeCellRenderer()
    {
        log = new Log();
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
            Icon icon2 = icon1;
            if(icon1 == null)
                continue;
            if(parm.length >= 3 && parm[2] != null && parm[2].trim().length() > 0)
            {
                icon2 = createImageIcon(parm[2]);
                if(icon2 == null)
                    icon2 = icon1;
            }
            icons.put(type,icon1);
            icons.put(type + "_E",icon2);
        }
    }
    /**
     * 设置显示ID
     * @param showID boolean
     */
    public void setShowID(boolean showID)
    {
        this.showID = showID;
    }
    /**
     * 是否显示ID
     * @return boolean
     */
    public boolean isShowID()
    {
        return showID;
    }
    /**
     * 设置显示名称
     * @param showName boolean
     */
    public void setShowName(boolean showName)
    {
        this.showName = showName;
    }
    /**
     * 是否显示名称
     * @return boolean
     */
    public boolean isShowName()
    {
        return showName;
    }
    /**
     * 设置显示值
     * @param showValue boolean
     */
    public void setShowValue(boolean showValue)
    {
        this.showValue = showValue;
    }
    /**
     * 是否显示值
     * @return boolean
     */
    public boolean isShowValue()
    {
        return showValue;
    }
    /**
     * 得到图标列表
     * @return String
     */
    public String getPics()
    {
        return pics;
    }
    public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                  boolean sel,
                                                  boolean expanded,
                                                  boolean leaf, int row,
                                                  boolean hasFocus) {
        if(!(value instanceof TTreeNode))
            return super.getTreeCellRendererComponent(tree, value,sel,expanded,leaf, row,hasFocus);
        TTreeNode node = (TTreeNode)value;
        String type = node.getType();
        String showType = node.getShowType();
        //设置文字
        String text = "";
        String language = ((JTreeBase)tree).getLanguage();
        if(isShowID() && node.getID() != null)
            text += node.getID();
        if(isShowName())
        {
            if("en".equals(language) && node.getEnName() != null && node.getEnName().length() > 0)
            {
                if(text.length() > 0)
                    text += " ";
                text += node.getEnName();
            }else if(node.getName() != null)
            {
                if (text.length() > 0)
                    text += " ";
                text += node.getName();
            }
        }
        if("en".equals(language) && node.getEnText() != null && node.getEnText().length() > 0)
        {
            if(text.length() > 0)
                text += " ";
            text += node.getEnText();
        }else if(node.getText() != null)
        {

            if(text.length() > 0)
                text += " ";
            text += node.getText();
        }
        if(isShowValue() && node.getValue() != null)
        {
            if(text.length() > 0)
                text += " ";
            text += node.getValue();
        }
        this.setText(text);
        //设置字体
        if(node.getFont() != null)
            setFont(node.getFont());
        else
            setFont(tree.getFont());
        //设置颜色
        if(sel)
            setForeground(getTextSelectionColor());
        else if(node.getColor() != null)
            setForeground(node.getColor());
        else
            setForeground(getTextNonSelectionColor());
        //设置图标
        setIcon(getPic(type,expanded));
        setComponentOrientation(tree.getComponentOrientation());
        selected = sel;
        if("CheckBox".equalsIgnoreCase(showType))
            return getCheckBoxComponent(tree,node);
        if("RadioButton".equalsIgnoreCase(showType))
            return getRadioButtonComponent(tree,node);
        return this;
    }
    /**
     * 得到图标
     * @param type String
     * @param expanded boolean
     * @return Icon
     */
    public Icon getPic(String type,boolean expanded)
    {
        if(expanded)
            return (Icon)icons.get(type + "_E");
        else
            return (Icon)icons.get(type);
    }
    /**
     * CheckBox样式
     * @param tree JTree
     * @param node TTreeNode
     * @return Component
     */
    public Component getCheckBoxComponent(JTree tree,TTreeNode node)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(checkBox,BorderLayout.WEST);
        panel.add(this,BorderLayout.CENTER);
        panel.setBackground(tree.getBackground());
        checkBox.setBackground(tree.getBackground());
        checkBox.setSelected(StringTool.getBoolean(node.getValue()));
        return panel;
    }
    /**
     * RadioButton样式
     * @param tree JTree
     * @param node TTreeNode
     * @return Component
     */
    public Component getRadioButtonComponent(JTree tree,TTreeNode node)
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(radioButton,BorderLayout.WEST);
        panel.add(this,BorderLayout.CENTER);
        panel.setBackground(tree.getBackground());
        radioButton.setBackground(tree.getBackground());
        radioButton.setSelected(StringTool.getBoolean(node.getValue()));
        return panel;
    }
    public void setTypeIcon(String type)
    {
        setIcon((Icon)icons.get(type));
    }
    /**
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
        if(TIOM_AppServer.SOCKET != null)
            return TIOM_AppServer.getImage("%ROOT%/image/ImageIcon/" + filename);
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
    /**
     * 得到字体
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
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        log.out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        log.out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        log.err(text);
    }
}
