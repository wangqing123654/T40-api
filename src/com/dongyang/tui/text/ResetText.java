package com.dongyang.tui.text;

import com.dongyang.tui.DRectangle;

/**
 *
 * <p>Title: ����Text�������</p>
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
     * ����Text
     */
    private EText text;
    /**
     * �ߴ緶Χ
     */
    private DRectangle rectangle;
    /**
     * ��������
     */
    private boolean doing;
    /**
     * ״̬
     */
    private int state;
    /**
     * ������
     */
    private int index;
    /**
     * ������λ��
     */
    private int x0;
    /**
     * ������
     * @param text EText
     */
    public ResetText(EText text)
    {
        setText(text);
    }
    /**
     * ����Text
     * @param text EText
     */
    public void setText(EText text)
    {
        this.text = text;
    }
    /**
     * �õ�Text
     * @return EText
     */
    public EText getText()
    {
        return text;
    }
    /**
     * ���óߴ緶Χ
     * @param rectangle DRectangle
     */
    public void setRectangle(DRectangle rectangle)
    {
        this.rectangle = rectangle;
    }
    /**
     * ���óߴ緶Χ
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
     * �õ��ߴ緶Χ
     * @return DRectangle
     */
    public DRectangle getRectangle()
    {
        return rectangle;
    }
    /**
     * ���þ��
     * @param index int
     */
    public void setIndex(int index)
    {
        this.index = index;
    }
    /**
     * �õ����
     * @return int
     */
    public int getIndex()
    {
        return index;
    }
    /**
     * ����״̬
     * @param state int
     */
    public void setState(int state)
    {
        this.state = state;
    }
    /**
     * �õ�״̬
     * @return int
     */
    public int getState()
    {
        return state;
    }
    /**
     * ����
     */
    public void clear()
    {
        //�������
        setIndex(0);
        //״̬����
        setState(0);
    }
    /**
     * ����
     */
    public void start()
    {
        doing = true;
    }
    /**
     * ֹͣ
     */
    public void stop()
    {
        doing = false;
    }
    /**
     * �Ƿ�����
     * @return boolean
     */
    public boolean isDoing()
    {
        return doing;
    }
    /**
     * �õ�ҳ����
     * @return int
     * 0 ��ͨ
     * 1 ҳ��
     */
    public int getViewStyle()
    {
        return getText().getViewManager().getViewStyle();
    }
    /**
     * �����ߴ�
     */
    public void reset()
    {
        //�����Ĺ̶��ߴ�
        if(resetMacroroutine())
            return;
        //����
        start();
        //������λ�ù���
        x0 = getRectangle().getX();
        //ɨ��
        while(isDoing())
        {
            switch(getState())
            {
            case 0://����ɨ��
                resetText_0();
                break;
            case 1://����ɨ����
                resetText_1();
                break;
            case 2://�����ƶ�
                resetText_2();
                break;
            }
        }
    }
    /**
     * �����Ĺ̶��ߴ�
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
     * ���Ը߶�
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
     * ����ɨ��
     */
    private void resetText_0()
    {
        if(!checkHeight())
            return;
        String s = getText().getString();
        //���ڻس�����ʼɨ�� s.indexOf("\r") != -1 ||s.indexOf("\r") == '\n'
        //System.out.println("=======����ɨ��==========="+s);
        //System.out.println("=======s����r======="+s.indexOf("\r"));
        //System.out.println("=======s����n======="+s.indexOf("\n"));
        if(s.indexOf("\r") != -1)
        {
            setState(1);
            return;
        }
        //�õ���ʾ�ַ����
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
     * ����ɨ����
     */
    private void resetText_1()
    {
        String s = getText().getString();
        //System.out.println("=======����ɨ���� text string========"+s);
        //System.out.println("========����ɨ����==========="+s);

        if(index >= s.length())
        {
            setState(2);
            return;
        }
        if(index > 0)
        {
            //System.out.println("=======����r========"+(s.charAt(index - 1) == '\r'));
            //System.out.println("=======����n========"+(s.charAt(index - 1) == '\n'));
            //����������
            //����س����� if(s.charAt(index - 1) == '\r' ||s.charAt(index - 1) == '\n'
            if(s.charAt(index - 1) == '\r')
            {
                //�ָ�
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
            //һ����ĸҲ�Ų���
            if(index == 0)
            {
                //============���ر���=========begin=//
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
                //============���ر���=========end=//
                //������һ��Text
                //linkNextText();
                getText().setPosition(1);
                stop();
                return;
            }
            //�ָ�
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
     * �����ƶ�
     */
    public void resetText_2()
    {
        //�õ���һ������Text
        EText text = getText().getNextLinkText();
        if(text == null)
        {
            getText().setWidth(x0);
            getText().setHeight(getText().getStyle().getFontMetrics().getHeight());
            getText().setPosition(0);
            stop();
            return;
        }

        //System.out.println("=======�����ƶ�string========"+text.getString());
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
            //��ͬһ��������������ƶ�
            getText().setEnd(getText().getEnd() + 1);
            text.setStart(text.getStart() + 1);
        }else
        {
            //��ҳ����
            text.getDString().remove(0,1);
            getText().getDString().add(c);
            text.getPanel().indexMove(text,-1);
            text.setEnd(text.getEnd() - 1);
            getText().setEnd(getText().getEnd() + 1);
        }
        //ѡ���ƶ�
        if(text.isSelected())
            text.getSelectedModel().move(getText(), 1);
        //�ƶ�����
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
     * ������һ��Text
     */
    /*public void linkNextText()
    {
        //�õ���һ������Text
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
        //���ӹ̶��ı�
        if(getText() instanceof EFixed && text instanceof EFixed)
        {
            ((EFixed)getText()).setNextFixed(((EFixed)text).getNextFixed());
            if(((EFixed)text).getNextFixed() != null)
                ((EFixed)text).getNextFixed().setPreviousFixed((EFixed)getText());
        }
        //���Ӻ�
        if(getText() instanceof EMacroroutine && text instanceof EMacroroutine)
        {
            ((EMacroroutine)getText()).setNextMacroroutine(((EMacroroutine)text).getNextMacroroutine());
            if(((EMacroroutine)text).getNextMacroroutine() != null)
                ((EMacroroutine)text).getNextMacroroutine().setPreviousMacroroutine((EMacroroutine)getText());
        }
        text.removeThis();
    }*/
}
