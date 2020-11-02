package com.dongyang.ui.base;

import javax.swing.ComboBoxEditor;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.ActionListener;
import javax.swing.border.Border;
import com.dongyang.ui.TComboNode;
import com.dongyang.ui.event.TKeyListener;

public class TComboBoxEditor implements ComboBoxEditor,FocusListener{
    protected JTextField editor;
    private Object oldValue;
    private TComboBoxBase comboBox;
    private String oldText = "";
    public TComboBoxEditor(TComboBoxBase comboBox) {
        setComboBox(comboBox);
        editor = new BorderlessTextField("",9);
        editor.setBorder(null);
        editor.addKeyListener(new TKeyListener(comboBox));
    }
    public void setComboBox(TComboBoxBase comboBox)
    {
        this.comboBox = comboBox;
    }
    public TComboBoxBase getComboBox()
    {
        return comboBox;
    }

    public Component getEditorComponent() {
        return editor;
    }

    /**
     * Sets the item that should be edited.
     *
     * @param anObject the displayed value of the editor
     */
    public void setItem(Object anObject) {
        oldText = getShowText(anObject);
        editor.setText(oldText);
        oldValue = anObject;
    }
    /**
     * 文本是否改变
     * @return boolean
     */
    public boolean isModifiedText()
    {
        return !oldText.equals(editor.getText());
    }
    public String getText()
    {
        return editor.getText();
    }
    public void setText(String text)
    {
        editor.setText(text);
    }
    /**
     * 显示语种
     * @param anObject Object
     */
    public void setLanguageText(Object anObject)
    {
        setText(getShowText(anObject));
    }
    /**
     * 得到显示文本
     * @param anObject Object
     * @return String
     */
    public String getShowText(Object anObject)
    {
        if(anObject == null)
            return "";
        if(!(anObject instanceof TComboNode))
            return anObject.toString();
        TComboNode node = (TComboNode)anObject;
        StringBuffer sb = new StringBuffer();
        if(getComboBox().getTableShowList() != null && getComboBox().getTableShowList().length() > 0)
        {
            String showList = getComboBox().getTableShowList();
            return node.getShowValue(showList,comboBox.getLanguage());
        }
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
            if(sb.length() > 0)
                sb.append(" ");
            sb.append(node.getText());
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
        return sb.toString();
    }

    public Object getItem() {
        String newValue = editor.getText();
        if(newValue.equalsIgnoreCase(getShowText(oldValue)))
            return oldValue;
        TComboBoxModel model = getComboBox().getModel();
        TComboNode selectItem = null;
        for(int i = 0;i < model.getSize();i++)
        {
            Object itemObject = model.getElementAt(i);
            if(!(itemObject instanceof TComboNode))
                continue;
            TComboNode item = (TComboNode)itemObject;
            if(item.getID() != null && item.getID().length() > 0 && item.getID().equalsIgnoreCase(newValue))
            {
                selectItem = item;
                break;
            }
            if(item.getName() != null && item.getName().length() > 0 && item.getName().equalsIgnoreCase(newValue))
            {
                selectItem = item;
                break;
            }
            if(item.getText() != null && item.getText().length() > 0 && item.getText().equalsIgnoreCase(newValue))
            {
                selectItem = item;
                break;
            }
            if(item.getValue() != null && item.getValue().length() > 0 && item.getValue().equalsIgnoreCase(newValue))
            {
                selectItem = item;
                break;
            }
            if(item.getPy1() != null && item.getPy1().length() > 0 && item.getPy1().equalsIgnoreCase(newValue))
            {
                selectItem = item;
                break;
            }
            if(item.getPy2() != null && item.getPy2().length() > 0 && item.getPy2().equalsIgnoreCase(newValue))
            {
                selectItem = item;
                break;
            }
        }
        if(selectItem != null)
        {
            oldText = getShowText(selectItem);
            editor.setText(oldText);
        }else if(!comboBox.canEdit())
            editor.setText("");
        oldValue = selectItem;
        return selectItem;
    }

    public void selectAll() {
        editor.selectAll();
        editor.requestFocus();
        //editor.grabFocus();
    }

    // This used to do something but now it doesn't.  It couldn't be
    // removed because it would be an API change to do so.
    public void focusGained(FocusEvent e) {}

    // This used to do something but now it doesn't.  It couldn't be
    // removed because it would be an API change to do so.
    public void focusLost(FocusEvent e) {}

    public void addActionListener(ActionListener l) {
        editor.addActionListener(l);
    }

    public void removeActionListener(ActionListener l) {
        editor.removeActionListener(l);
    }
     static class BorderlessTextField extends JTextField {
        public BorderlessTextField(String value,int n) {
            super(value,n);
        }

        // workaround for 4530952
        public void setText(String s) {
            if (getText().equals(s)) {
                return;
            }
            super.setText(s);
        }
        public void setBorder(Border b) {}
    }
}
