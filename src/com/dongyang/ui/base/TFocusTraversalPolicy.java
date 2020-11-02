package com.dongyang.ui.base;

import java.awt.Container;
import java.awt.Component;
import java.awt.FocusTraversalPolicy;
import com.dongyang.util.StringTool;
import com.dongyang.ui.TContainer;
import com.dongyang.ui.TComponent;
import java.awt.Window;

public class TFocusTraversalPolicy extends FocusTraversalPolicy{
    String tags;
    private String[] compFocus = new String[]{};
    public TFocusTraversalPolicy() {
    }
    /**
     *
     * @param tags String
     */
    public TFocusTraversalPolicy(String tags) {
        setFocusList(tags);
    }
    /**
     * 设置tags 分号间隔
     * @param tags String
     */
    public void setFocusList(String tags)
    {
        if(tags != null && tags.equals(this.tags))
            return;
        this.tags = tags;
        compFocus = StringTool.parseLine(tags,";");
    }
    /**
     * 得到tags
     * @return String
     */
    public String getFocusList()
    {
        return tags;
    }
    /**
     * 得到焦点列表
     * @return String[]
     */
    public String[] getCompFocus()
    {
        return compFocus;
    }
    /**
     * 得到第一个组件
     * @param focusCycleRoot Container
     * @return Component
     */
    public Component getFirstComponent(Container focusCycleRoot) {
        if(compFocus == null)
            return null;
        return getComponent(focusCycleRoot,compFocus[0]);
    }
    /**
     * 得到最后一个组件
     * @param focusCycleRoot Container
     * @return Component
     */
    public Component getLastComponent(Container focusCycleRoot) {
        if(compFocus == null)
            return null;
        return getComponent(focusCycleRoot,compFocus[compFocus.length - 1]);
    }
    /**
     * 得到 Tag 得到对象
     * @param focusCycleRoot Container
     * @param tag String
     * @return Component
     */
    private Component getComponent(Container focusCycleRoot,String tag)
    {
        if(compFocus.length == 0)
            return null;
        if(! (focusCycleRoot instanceof TContainer))
            return null;
        TContainer container = (TContainer)focusCycleRoot;
        Component component = (Component)container.findObject(tag);
        if(component == null || !component.isVisible() || !component.isEnabled())
            return null;
        return component;
    }
    /**
     * 查找组件的位置
     * @param aComponent Component
     * @return int
     */
    private int indexOf(Component aComponent)
    {
        String tag = getTag(aComponent);
        if(tag == null)
            return -1;
        return indexOf(tag);
    }
    /**
     * 得到组件的Tag
     * @param aComponent Component
     * @return String
     */
    private String getTag(Component aComponent)
    {
        if(aComponent == null)
            return null;
        if(! (aComponent instanceof TComponent))
            return null;
        TComponent component = (TComponent)aComponent;
        return component.getTag();
    }
    /**
     * 查找组件的位置
     * @param tag String
     * @return int
     */
    private int indexOf(String tag)
    {
        if(tag == null)
            return - 1;
        for(int i = 0;i < compFocus.length;i++)
            if(tag.equalsIgnoreCase(compFocus[i]))
                return i;
        return -1;
    }
    /**
     * 得到下一个组件Tag
     * @param focusCycleRoot Container
     * @param tag String
     * @return String
     */
    public String getTagAfter(Container focusCycleRoot,String tag)
    {
        if(compFocus == null || tag == null)
            return null;
        return getTag(getComponentAfter(focusCycleRoot,tag));
    }
    /**
     * 得到下一个组件对象
     * @param focusCycleRoot Container
     * @param aComponent Component
     * @return Component
     */
    public Component getComponentAfter(Container focusCycleRoot, Component aComponent) {
        if(compFocus == null)
            return null;
        String tag = getTag(aComponent);
        if(tag == null)
            return null;
        return getComponentAfter(focusCycleRoot,tag);
    }
    /**
     * 得到下一个组件对象
     * @param focusCycleRoot Container
     * @param tag String
     * @return Component
     */
    public Component getComponentAfter(Container focusCycleRoot,String tag)
    {
        if(compFocus == null)
            return null;
        int oldindex = indexOf(tag);
        if(oldindex == -1)
            return null;
        int index = oldindex + 1;
        if(index == compFocus.length)
            index = 0;
        while(oldindex != index)
        {
            Component component = getComponent(focusCycleRoot,compFocus[index]);
            if(component != null)
                return component;
            index ++;
            if(index == compFocus.length)
                index = 0;
        }
        return getComponent(focusCycleRoot,tag);
    }
    /**
     * 得到前一个组件Tag
     * @param focusCycleRoot Container
     * @param tag String
     * @return String
     */
    public String getTagBefore(Container focusCycleRoot,String tag)
    {
        if(compFocus == null || tag == null)
            return null;
        return getTag(getComponentBefore(focusCycleRoot,tag));
    }
    /**
     * 得到前一个组件
     * @param focusCycleRoot Container
     * @param aComponent Component
     * @return Component
     */
    public Component getComponentBefore(Container focusCycleRoot, Component aComponent) {
        if(compFocus == null)
            return null;
        String tag = getTag(aComponent);
        if(tag == null)
            return null;
        return getComponentBefore(focusCycleRoot,tag);
    }
    /**
     * 得到前一个组件对象
     * @param focusCycleRoot Container
     * @param tag String
     * @return Component
     */
    public Component getComponentBefore(Container focusCycleRoot,String tag)
    {
        if(compFocus == null)
            return null;
        int oldindex = indexOf(tag);
        if(oldindex == -1)
            return null;
        int index = oldindex - 1;
        if(index < 0)
            index = compFocus.length - 1;
        while(oldindex != index)
        {
            Component component = getComponent(focusCycleRoot,compFocus[index]);
            if(component != null)
                return component;
            index --;
            if(index < 0)
                index = compFocus.length - 1;
        }
        return getComponent(focusCycleRoot,tag);
    }
    /**
     * 得到默认焦点组件
     * @param focusCycleRoot Container
     * @return Component
     */
    public Component getDefaultComponent(Container focusCycleRoot) {
        if(compFocus == null)
            return null;
        for(int i = 0;i < compFocus.length;i++)
        {
            Component component = getComponent(focusCycleRoot,compFocus[i]);
            if(component != null)
                return component;
        }
        return null;
    }
    public Component getInitialComponent(Window window) {
        Component com = super.getInitialComponent(window);
        return com;
    }
}
