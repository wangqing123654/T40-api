package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;

/**
 *
 * <p>Title: 调成Text处理对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.9.15
 * @version 1.0
 */
public class ResetText
{
    /**
     * 父类Text
     */
    private EText text;
    /**
     * 尺寸范围
     */
    private DRectangle rectangle;
    /**
     * 启动开关
     */
    private boolean doing;
    /**
     * 状态
     */
    private int state;
    /**
     * 处理句柄
     */
    private int index;
    /**
     * 横坐标位置
     */
    private int x0;
    /**
     * 构造器
     * @param text EText
     */
    public ResetText(EText text)
    {
        setText(text);
    }
    /**
     * 设置Text
     * @param text EText
     */
    public void setText(EText text)
    {
        this.text = text;
    }
    /**
     * 得到Text
     * @return EText
     */
    public EText getText()
    {
        return text;
    }
    /**
     * 设置尺寸范围
     * @param rectangle DRectangle
     */
    public void setRectangle(DRectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    /**
     * 设置尺寸范围
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void setRectangle(int x,int y,int width,int height)
    {
        setRectangle(new DRectangle(x,y,width,height));
    }
    /**
     * 得到尺寸范围
     * @return DRectangle
     */
    public DRectangle getRectangle()
    {
        return rectangle;
    }
    /**
     * 设置句柄
     * @param index int
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    /**
     * 得到句柄
     * @return int
     */
    public int getIndex()
    {
        return index;
    }
    /**
     * 设置状态
     * @param state int
     */
    public void setState(int state)
    {
        this.state = state;
    }
    /**
     * 得到状态
     * @return int
     */
    public int getState()
    {
        return state;
    }
    /**
     * 清零
     */
    public void clear()
    {
        //句柄归零
        setIndex(0);
        //状态归零
        setState(0);
    }
    /**
     * 启动
     */
    public void start()
    {
        doing = true;
    }
    /**
     * 停止
     */
    public void stop()
    {
        doing = false;
    }
    /**
     * 是否运行
     * @return boolean
     */
    public boolean isDoing()
    {
        return doing;
    }
    /**
     * 得到页面风格
     * @return int
     * 0 普通
     * 1 页面
     */
    public int getViewStyle()
    {
        return getText().getViewManager().getViewStyle();
    }
    /**
     * 调整尺寸
     */
    public void reset()
    {
        //处理宏的固定尺寸
        if(resetMacroroutine())
            return;
        //启动
        start();
        //纵坐标位置归零
        x0 = getRectangle().getX();
        //扫描
        while(isDoing())
        {
            switch(getState())
            {
            case 0://整体扫描
                resetText_0();
                break;
            case 1://向下扫描字
                resetText_1();
                break;
            case 2://向上移动
                resetText_2();
                break;
            }
        }
    }
    /**
     * 处理宏的固定尺寸
     * @return boolean
     */
    public boolean resetMacroroutine()
    {
        if(!(getText() instanceof EMacroroutine))
            return false;
        EMacroroutine macroroutine = (EMacroroutine)getText();
        if(macroroutine.getModel() == null)
            return false;
        if(!macroroutine.getModel().isLockSize())
            return false;
        getText().setWidth(macroroutine.getModel().getWidth());
        getText().setHeight(macroroutine.getModel().getHeight());
        if(getText().getHeight() <= getRectangle().getHeight())
        {
            getText().setPosition(0);
            return true;
        }
        getText().setPosition(-1);
        return true;
    }
    /**
     * 测试高度
     * @return boolean
     */
    private boolean checkHeight()
    {
        int height = getText().getStyle().getFontMetrics().getHeight();
        if(getViewStyle() == 0)
            return true;
        if(height <= getRectangle().getHeight())
        {
            getText().setHeight(height);
            return true;
        }
        getText().setPosition(-1);
        stop();
        return false;
    }
    /**
     * 整体扫描
     */
    private void resetText_0()
    {
        if(!checkHeight())
            return;
        String s = getText().getString();
        //存在回车符开始扫描 s.indexOf("\r") != -1 ||s.indexOf("\r") == '\n'
        //System.out.println("=======整体扫描==========="+s);
        //System.out.println("=======s包含r======="+s.indexOf("\r"));
        //System.out.println("=======s包含n======="+s.indexOf("\n"));
        if(s.indexOf("\r") != -1)
        {
            setState(1);
            return;
        }
        //得到显示字符宽度
        int width = getText().getShowStringWidth() +
                    getText().getShowLeftW() +
                    getText().getShowRightW();
        if(getViewStyle() == 0)
        {
            x0 = width;
            setState(2);
            return;
        }
        if(width <= getRectangle().getWidth())
        {
            x0 = width;
            setState(2);
            return;
        }
        if(!getText().canSeparate())
        {
            getText().setPosition(0);
            stop();
        }
        setState(1);
    }
    /**
     * 向下扫描字
     */
    private void resetText_1()
    {
        String s = getText().getString();
        //System.out.println("=======向下扫描字 text string========"+s);
        //System.out.println("========向下扫描字==========="+s);

        if(index >= s.length())
        {
            setState(2);
            return;
        }
        if(index > 0)
        {
            //System.out.println("=======包含r========"+(s.charAt(index - 1) == '\r'));
            //System.out.println("=======包含n========"+(s.charAt(index - 1) == '\n'));
            //可能有问题
            //处理回车换行 if(s.charAt(index - 1) == '\r' ||s.charAt(index - 1) == '\n'
            if(s.charAt(index - 1) == '\r')
            {
                //分割
                getText().separateText(index,false);
                getText().setWidth(x0);
                getText().setPosition(2);
                stop();
                return;
            }
        }
        int w = getText().getStyle().charwidth(s.charAt(index));
        if(w > getRectangle().getWidth() - x0)
        {
            //一个字母也放不下
            if(index == 0)
            {
                //============过载保护=========begin=//
                if(getText().getPosition() == 1)
                {
                    index ++;
                    if(index < s.length())
                        getText().separateText(index,true);
                    getText().setPosition(2);
                    getText().setWidth(w);
                    stop();
                    return;
                }
                //============过载保护=========end=//
                //连接下一个Text
                //linkNextText();
                getText().setPosition(1);
                stop();
                return;
            }
            //分割
            getText().separateText(index,true);
            getText().setWidth(x0);
            getText().setPosition(2);
            stop();
            return;
        }
        x0 += w;
        index ++;
    }
    /**
     * 向上移动
     */
    public void resetText_2()
    {
        //得到下一个连接Text
        EText text = getText().getNextLinkText();
        if(text == null)
        {
            getText().setWidth(x0);
            getText().setHeight(getText().getStyle().getFontMetrics().getHeight());
            getText().setPosition(0);
            stop();
            return;
        }

        //System.out.println("=======向上移动string========"+text.getString());
        //test
        if(text.getString().equals("")){
            getText().setWidth(x0);
            getText().setHeight(getText().getStyle().getFontMetrics().getHeight());
            getText().setPosition(0);
            stop();
            return;
        }
        //test end
        char c = text.getString().charAt(text.getPanel() == getText().getPanel()?index:0);
        int w = getText().getStyle().charwidth(c);
        if(w > getRectangle().getWidth() - x0)
        {
            getText().setWidth(x0);
            getText().setPosition(2);
            stop();
            return;
        }
        if(text.getPanel() == getText().getPanel())
        {
            //在同一个父类面板上上移动
            getText().setEnd(getText().getEnd() + 1);
            text.setStart(text.getStart() + 1);
        }else
        {
            //跨页上移
            text.getDString().remove(0,1);
            getText().getDString().add(c);
            text.getPanel().indexMove(text,-1);
            text.setEnd(text.getEnd() - 1);
            getText().setEnd(getText().getEnd() + 1);
        }
        //选蓝移动
        if(text.isSelected())
            text.getSelectedModel().move(getText(), 1);
        //移动焦点
        if(text.getFocus() == text)
        {
            int focusIndex = text.getFocusIndex();
            if(focusIndex == 0)
            {
                getText().setFocus();
                getText().setFocusIndex(getText().getLength() - 1);
            }else
                text.setFocusIndex(text.getFocusIndex() - 1);
        }
        x0 += w;
        if(text.getLength() == 0)
        {
            getText().setNextLinkText(text.getNextLinkText());
            if(getText().getNextLinkText() != null)
                getText().getNextLinkText().setPreviousLinkText(getText());
            if(getText() instanceof EFixed && text instanceof EFixed)
            {
                EFixed e1 = (EFixed)getText();
                EFixed e2 = (EFixed)text;
                if(e1.getNextFixed() == e2)
                {
                    e1.setNextFixed(e2.getNextFixed());
                    if(e2.getNextFixed() != null)
                        e2.getNextFixed().setPreviousFixed(e1);
                }
            }
            EPanel panel = text.getPanel();
            text.removeThis();
            if(panel.size() == 0)
                panel.removeThis();
        }
    }
    /**
     * 连接下一个Text
     */
    /*public void linkNextText()
    {
        //得到下一个连接Text
        EText text = getText().getNextLinkText();
        if(text == null)
            return;
        if(text.getPanel() == getText().getPanel())
            getText().setEnd(text.getEnd());
        else
        {
            String s = text.getDString().getString();
            text.getDString().remove(text.getStart(),text.getEnd());
            getText().getDString().add(getText().getEnd(),s);
            getText().getPanel().indexMove(getText(),s.length());
            getText().setEnd(getText().getEnd() + s.length());
        }
        getText().setNextLinkText(text.getNextLinkText());
        if(getText().getNextLinkText() != null)
            getText().getNextLinkText().setPreviousLinkText(getText());
        //连接固定文本
        if(getText() instanceof EFixed && text instanceof EFixed)
        {
            ((EFixed)getText()).setNextFixed(((EFixed)text).getNextFixed());
            if(((EFixed)text).getNextFixed() != null)
                ((EFixed)text).getNextFixed().setPreviousFixed((EFixed)getText());
        }
        //连接宏
        if(getText() instanceof EMacroroutine && text instanceof EMacroroutine)
        {
            ((EMacroroutine)getText()).setNextMacroroutine(((EMacroroutine)text).getNextMacroroutine());
            if(((EMacroroutine)text).getNextMacroroutine() != null)
                ((EMacroroutine)text).getNextMacroroutine().setPreviousMacroroutine((EMacroroutine)getText());
        }
        text.removeThis();
    }*/
}
