package com.dongyang.tui;

import com.dongyang.tui.text.PM;
import java.awt.Graphics;
import com.dongyang.tui.text.MView;
import com.dongyang.tui.text.MEvent;
import com.dongyang.tui.text.MFile;
import com.dongyang.tui.text.MFocus;

/**
 *
 * <p>Title: �ı��༭��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class DText extends DComponent
{
    /**
     * ������
     */
    private PM pm;
    /**
     * ��ʾ���������
     */
    private boolean isShowHScrollBar;
    /**
     * ��ʾ���������
     */
    private boolean isShowVScrollBar;
    /**
     * �Ƿ�����
     */
    private boolean isPreview;
    /**
     * ������
     */
    public DText()
    {
        pm = new PM(this);
        setBKColor("��");
        addPublicMethod("hScrollBarChangeValue",this);
        addPublicMethod("vScrollBarChangeValue",this);
        //���ļ�
        getFileManager().onNewFile();
    }

    /**
     * �Ǳ���
     * @param isReport boolean
     */
    public DText(boolean isReport){
        pm = new PM(this,isReport);
        setBKColor("��");
        addPublicMethod("hScrollBarChangeValue",this);
        addPublicMethod("vScrollBarChangeValue",this);
        //���ļ�
        getFileManager().onNewFile();

    }

    /**
     * �õ���ͼ������
     * @return MView
     */
    public MView getViewManager()
    {
        return getPM().getViewManager();
    }
    /**
     * �õ��¼�������
     * @return MEvent
     */
    public MEvent getEventManager()
    {
        return getPM().getEventManager();
    }
    /**
     * �õ��ļ�������
     * @return MFile
     */
    public MFile getFileManager()
    {
        return getPM().getFileManager();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
    }
    /**
     * ������ʾ���������
     * @param isShow boolean
     */
    public void setShowHScrollBar(boolean isShow)
    {
        if(isShowHScrollBar == isShow)
            return;
        isShowHScrollBar = isShow;
        if(!isShow)
        {
            DScrollBar hScrollBar = getHScrollBar();
            if(hScrollBar != null)
            {
                hScrollBar.removeLinkComponent();
                removeDComponent(hScrollBar);
                DScrollBar vScrollBar = getVScrollBar();
                if(vScrollBar != null)
                    vScrollBar.setLinkComponent(2,this,6,0);
                repaint();
            }
            return;
        }
        DScrollBar hScrollBar = new DScrollBar();
        hScrollBar.setOrientation(DScrollBar.HORIZONTAL);
        hScrollBar.setTag("HScrollBar");
        hScrollBar.setNoUIEdit(true);
        hScrollBar.setActionMap("onChangeValue","hScrollBarChangeValue");
        addDComponent(hScrollBar);
        hScrollBar.setLinkComponent(1,this,6,17);
        hScrollBar.setLinkComponent(3,this,7);
        hScrollBar.setLinkComponent(4,this,8,isShowVScrollBar?17:0);
        DScrollBar vScrollBar = getVScrollBar();
        if(vScrollBar != null)
            vScrollBar.setLinkComponent(2,this,6,17);
        repaint();
        return;
    }
    /**
     * �Ƿ���ʾ���������
     * @return boolean
     */
    public boolean isShowHScrollBar()
    {
        return isShowHScrollBar;
    }
    /**
     * ������ʾ���������
     * @param isShow boolean
     */
    public void setShowVScrollBar(boolean isShow)
    {
        if(isShowVScrollBar == isShow)
            return;
        isShowVScrollBar = isShow;
        if(!isShow)
        {
            DScrollBar vScrollBar = getVScrollBar();
            if(vScrollBar != null)
            {
                vScrollBar.removeLinkComponent();
                removeDComponent(vScrollBar);
                DScrollBar hScrollBar = getHScrollBar();
                if(hScrollBar != null)
                    hScrollBar.setLinkComponent(4,this,8,0);
                repaint();
            }
            return;
        }
        DScrollBar vScrollBar = new DScrollBar();
        vScrollBar.setMouseWheelValue(50);
        vScrollBar.setOrientation(DScrollBar.VERTICAL);
        vScrollBar.setTag("VScrollBar");
        vScrollBar.setNoUIEdit(true);
        vScrollBar.setActionMap("onChangeValue","vScrollBarChangeValue");
        addDComponent(vScrollBar);
        vScrollBar.setLinkComponent(1,this,5);
        vScrollBar.setLinkComponent(2,this,6,isShowHScrollBar?17:0);
        vScrollBar.setLinkComponent(3,this,8,17);
        DScrollBar hScrollBar = getHScrollBar();
        if(hScrollBar != null)
            hScrollBar.setLinkComponent(4,this,8,17);
        repaint();
        return;
    }
    /**
     * �Ƿ���ʾ���������
     * @return boolean
     */
    public boolean isShowVScrollBar()
    {
        return isShowVScrollBar;
    }
    /**
     * �õ�����������ؼ�
     * @return DScrollBar
     */
    public DScrollBar getVScrollBar()
    {
        return (DScrollBar)getDComponent("VScrollBar");
    }
    /**
     * �õ�����������ؼ�
     * @return DScrollBar
     */
    public DScrollBar getHScrollBar()
    {
        return (DScrollBar)getDComponent("HScrollBar");
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return pm;
    }
    /**
     * ���������ֵ�ı�
     */
    public void vScrollBarChangeValue()
    {
        //�¼�����
        getEventManager().onVScrollBarChangeValue(getVScrollBar().getValue());
        repaint();
    }
    /**
     * ���������ֵ�ı�
     */
    public void hScrollBarChangeValue()
    {
        repaint();
    }
    /**
     * �ߴ�ı�
     * @return boolean
     */
    public boolean onComponentResized()
    {
        super.onComponentResized();
        //�¼�����
        if(getEventManager() != null)
            getEventManager().onComponentResized();
        return true;
    }
    /**
     * ����Ĭ�Ϲ��
     */
    public void setDefaultCursor()
    {
    }
    /**
     * ����ƶ�
     * @return boolean
     */
    public boolean onMouseMoved()
    {
        if(super.onMouseMoved())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            if(getEventManager().onMouseMoved())
                return true;
        setCursor(DCursor.DEFAULT_CURSOR);
        return false;
    }
    /**
     * ������
     * @return boolean
     */
    public boolean onMouseEntered()
    {
        if(super.onMouseEntered())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseEntered();
        return false;
    }
    /**
     * ����뿪
     * @return boolean
     */
    public boolean onMouseExited()
    {
        if(super.onMouseExited())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseExited();
        return false;
    }
    /**
     * ˫��
     * @return boolean
     */
    public boolean onDoubleClickedS()
    {
        if(super.onDoubleClickedS())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onDoubleClickedS();
        return false;
    }
    /**
     * �������
     * @return boolean
     */
    public boolean onMouseLeftPressed()
    {
        if(super.onMouseLeftPressed())
            return true;
        //���ý���
        if(getFocus() != this)
            grabFocus();
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseLeftPressed();
        return false;
    }
    /**
     * ���̧��
     * @return boolean
     */
    public boolean onMouseLeftReleased()
    {
        if(super.onMouseLeftReleased())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseLeftReleased();
        return false;
    }
    /**
     * �Ҽ�����
     * @return boolean
     */
    public boolean onMouseRightPressed()
    {
        if(super.onMouseRightPressed())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseRightPressed();
        return false;
    }
    /**
     * ����϶�
     * @return boolean
     */
    public boolean onMouseDragged()
    {
        if(super.onMouseDragged())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onMouseDragged();
        return false;
    }
    /**
     * ��껬��
     * @return boolean
     */
    public boolean onMouseWheelMoved()
    {
        if(super.onMouseWheelMoved())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            if(getEventManager().onMouseWheelMoved())
                return true;
        //����������¼�����
        if(isShowVScrollBar)
            getVScrollBar().toMouseWheelMoved(getMouseWheelMoved());
        return false;
    }
    /**
     * �õ������¼�
     * @return boolean
     */
    public boolean onFocusGained()
    {
        if(super.onFocusGained())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onFocusGained();
        return false;
    }
    /**
     * ʧȥ�����¼�
     * @return boolean
     */
    public boolean onFocusLost()
    {
        if(super.onFocusLost())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onFocusLost();
        return false;
    }
    /**
     * ���̰���
     * @return boolean
     */
    public boolean onKeyPressed()
    {
        if(super.onKeyPressed())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onKeyPressed();
        return false;
    }
    /**
     * ����̧��
     * @return boolean
     */
    public boolean onKeyReleased()
    {
        if(super.onKeyReleased())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onKeyReleased();
        return false;
    }
    /**
     * ����
     * @return boolean
     */
    public boolean onKeyTyped()
    {
        if(super.onKeyTyped())
            return true;
        //�¼�����
        if(!isPreview() && getEventManager() != null)
            getEventManager().onKeyTyped();
        return false;
    }
    /**
     * ���Ʊ���
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int width,int height)
    {
        super.paintBackground(g,width,height);
        if(pm == null)
            return;
        Graphics gc = g;
        if(isShowHScrollBar && isShowVScrollBar)
        {
            width -= 17;
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowHScrollBar)
        {
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowVScrollBar)
        {
            width -= 17;
            gc = g.create(0,0,width,height);
        }
        //com.dongyang.util.DebugUsingTime.add("paintBackground start");
        if(getViewManager() != null)
            getViewManager().paintBackground(gc,width,height);
        //com.dongyang.util.DebugUsingTime.add("paintBackground end");
    }
    /**
     * ����ǰ��
     * @param g Graphics
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int width,int height)
    {
        Graphics gc = g;
        if(pm == null)
            return;
        if(isShowHScrollBar && isShowVScrollBar)
        {
            width -= 17;
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowHScrollBar)
        {
            height -= 17;
            gc = g.create(0,0,width,height);
        }else if(isShowVScrollBar)
        {
            width -= 17;
            gc = g.create(0,0,width,height);
        }
        if(getViewManager() != null)
            getViewManager().paintForeground(gc,width,height);
    }
    /**
     * �ܹ��õ�����
     * @return boolean
     */
    public boolean canFocus()
    {
        return isEnabled();
    }
    /**
     * ��������
     * @param isPreview boolean
     */
    public void setPreview(boolean isPreview)
    {
        this.isPreview = isPreview;
    }
    /**
     * �Ƿ�����
     * @return boolean
     */
    public boolean isPreview()
    {
        return isPreview;
    }
}
