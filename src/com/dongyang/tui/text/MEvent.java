package com.dongyang.tui.text;

import com.dongyang.tui.DText;
import java.awt.event.KeyEvent;
import com.dongyang.ui.TWindow;
import com.dongyang.wcomponent.expression.ExpressionUtil;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: �¼�������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.12
 * @author whao 2013~
 * @version 1.0
 */
public class MEvent {
    /**
     * ������
     */
    private PM pm;
    /**
     * �����߳�
     */
    TriggerThread triggerThread;
    /**
     * ���ڽ������궨λ�ߴ�
     */
    private boolean hasFocusPointX;
    /**
     * ���궨λ�ߴ�
     */
    private int focusPointX;
    /**
     * �����϶�Table
     */
    private boolean moveTableNow;
    /**
     * ���Բ˵�����
     */
    private TWindow propertyMenuWindow;
    /**
     * �ܷ�༭
     */
    private boolean canEdit = true;
    /**
     * �ӿ�
     */
    private TParm ioParm;
    /**
     * ����϶�
     */
    private boolean isMouseDragged;
    /**
     * �ƶ�Image
     */
    private EImage draggedImage;
    /**
     * ��ʼ��
     */
    public MEvent() {
        //��ʼ���ӿ�
        setIOParm(new TParm());
    }

    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm) {
        this.pm = pm;
    }

    /**
     * �õ�������
     * @return PM
     */
    public PM getPM() {
        return pm;
    }

    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager() {
        return getPM().getPageManager();
    }

    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager() {
        return getPM().getFocusManager();
    }

    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI() {
        return getPM().getUI();
    }

    /**
     * �õ���ͼ������
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
    }

    /**
     * �õ�Table������������
     * @return MCTable
     */
    public MCTable getCTableManager() {
        return getPM().getCTableManager();
    }

    /**
     * �õ��ļ�������
     * @return MFile
     */
    public MFile getFileManager() {
        return getPM().getFileManager();
    }

    /**
     * �õ������¼�
     * @return KeyEvent
     */
    public KeyEvent getKeyEvent() {
        if (getUI() == null) {
            return null;
        }
        return getUI().getKeyEvent();
    }

    /**
     * �õ�ҳ����
     * @return int
     */
    public int getPageWidth() {
        return getPageManager().getWidth();
    }

    /**
     * �õ�ҳ��߶�
     * @return int
     */
    public int getPageHeight() {
        return getPageManager().getHeight();
    }

    /**
     * �õ����Xλ��
     * @return int
     */
    public int getMouseX() {
        return getUI().getMouseX() - getUI().getInsets().left;
    }

    /**
     * �õ����Yλ��
     * @return int
     */
    public int getMouseY() {
        return getUI().getMouseY() - getUI().getInsets().top;
    }

    /**
     * �õ���ʾ��������
     * @return int
     */
    public int getViewX() {
        return getViewManager().getViewX();
    }

    /**
     * �õ���ʾ��ҳ��������
     * @param pageIndex int ҳ��
     * @return int
     */
    public int getViewY(int pageIndex) {
        return getViewManager().getViewY(pageIndex);
    }

    /**
     * �õ���ʾ������ʾҳ��
     * @return int
     */
    public int getStartPageIndex() {
        return getViewManager().getStartPageIndex();
    }

    /**
     * �õ���ʾ���Ľ���ҳ��
     * @return int
     */
    public int getEndPageIndex() {
        return getViewManager().getEndPageIndex();
    }

    /**
     * �õ�ҳ�����
     * @param index int
     * @return EPage
     */
    public EPage getPage(int index) {
        return getPageManager().get(index);
    }

    /**
     * ���ô��ڽ������궨λ�ߴ�
     * @param hasFocusPointX boolean
     */
    public void setHasFocusPointX(boolean hasFocusPointX) {
        this.hasFocusPointX = hasFocusPointX;
    }

    /**
     * �Ƿ���ڽ������궨λ�ߴ�
     * @return boolean
     */
    public boolean hasFocusPointX() {
        return hasFocusPointX;
    }

    /**
     * �������궨λ�ߴ�
     * @param focusPointX int
     */
    public void setFocusPointX(int focusPointX) {
        this.focusPointX = focusPointX;
    }

    /**
     * �õ����궨λ�ߴ�
     * @return int
     */
    public int getFocusPointX() {
        return focusPointX;
    }

    /**
     * �õ��������ҳ��
     * @return int
     */
    public int getMouseInPageIndex() {
        //��ʾ������ʾҳ��
        int startPage = getStartPageIndex();
        int pageHeight = (int) (getPageHeight() * getZoom() / 75.0 + 0.5);
        //��ʾ���Ľ���ҳ��
        int endPage = getEndPageIndex();
        //���Y����
        int mouseY = getMouseY();
        //������ʾ��ҳ��
        for (int i = startPage; i < endPage; i++) {
            //�õ���ʾ��ҳ��������
            int pageY = getViewY(i);
            if (pageY < mouseY && mouseY < pageY + pageHeight) {
                return i;
            }
        }
        return -1;
    }

    /**
     * ��껬��
     * @return boolean
     */
    public boolean onMouseWheelMoved() {
        return false;
    }

    /**
     * �ߴ�ı��¼�
     */
    public void onComponentResized() {
        //����������ߴ�
        getViewManager().resetSize();
    }

    /**
     * �õ����ű���
     * @return double
     */
    public double getZoom() {
        return getViewManager().getZoom();
    }

    /**
     * �������ҳ��
     * @return int
     */
    public int getMouseInPageIndexXY() {
        //�������ҳ��
        int pageIndex = getMouseInPageIndex();
        if (pageIndex == -1) {
            return -1;
        }
        int mouseX = getMouseX();
        int x = getViewX();
        if (mouseX < x) {
            return -1;
        }
        if (mouseX > x + (int) (getPageManager().getWidth() * getZoom() / 75.0)) {
            return -1;
        }
        return pageIndex;
    }

    /**
     * ����ƶ�
     * @return boolean
     */
    public boolean onMouseMoved() {
        if (!canEdit()) {
            return false;
        }
        getFocusManager().setMoveTable(null);
        //�������ҳ��
        int pageIndex = getMouseInPageIndexXY();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //�¼�����
            if (page.onMouseMoved(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * ������
     */
    public void onMouseEntered() {
        //System.out.println("onMouseEntered");
    }

    /**
     * ����뿪
     */
    public void onMouseExited() {
        //System.out.println("onMouseExited");
    }

    /**
     * ִ���¼�
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListener(String action, Object ...parameters) {
        return getFileManager().runListenerArray(action, parameters);
    }

    /**
     * ���˫��
     * @return boolean
     */
    public boolean onDoubleClickedS() {
        //�������ҳ��
        int pageIndex = getMouseInPageIndex();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //�¼�����
            if (page.onDoubleClickedS(x, y)) {
                return true;
            }
            runListener("onDoubleClicked", pageIndex, x, y);
        }
        return false;
    }

    /**
     * �Ƿ���Control
     * @return boolean
     */
    public boolean isControlDown() {
        return getUI().isControlDown();
    }

    /**
     * �������
     */
    public void onMouseLeftPressed() {

        if (!canEdit()) {
            return;
        }
        if (!startMoveTable()) {
            if (isControlDown()) {
                //ѹ��ѡ���б�
                getFocusManager().putSelected();
                //���
                onClicked();
                //����ѡ�п�ʼ
                getFocusManager().setStartSelected();
            } else {
                if (getUI().isShiftDown()) {
                    if (!getFocusManager().isSelected()) {
                        //����ѡ�п�ʼ
                        getFocusManager().setStartSelected();
                    }
                    //���
                    onClicked();
                    //����ѡ�п�ʼ
                    getFocusManager().setEndSelected();

                } else {
                    //���ѡ��
                    getFocusManager().clearSelected();
                    //���
                    onClicked();
                    //����ѡ�п�ʼ
                    getFocusManager().setStartSelected();
                }
            }
            /*EText text = getFocusManager().getFocusText();
                         if(text != null)
                getIOParm().runListener("CLICK_TEXT",text);
                         ETR tr = getFocusManager().getFocusTR();
                         if(tr != null)
                getFocusManager().setEndSelected();*/
        }
        getFocusManager().showCursor();
    }

    /**
     * ��ʼ�ƶ�Table
     * @return boolean
     */
    public boolean startMoveTable() {
        if (getFocusManager().getMoveTable() == null) {
            return false;
        }
        setMoveTableNow(true);
        getUI().repaint();
        return true;
    }

    /**
     * ֹͣ�ƶ�Table
     * @return boolean
     */
    public boolean stopMoveTable() {
        if (!isMoveTableNow()) {
            return false;
        }
        setMoveTableNow(false);
        getFocusManager().getMoveTable().modifyYLine(getFocusManager().
                getMoveTableIndex(),
                (int) ((getMouseX() - getViewX() -
                        getFocusManager().getMoveTableX()) / getZoom() * 75.0 +
                       0.5));
        getFocusManager().setMoveTable(null);
        getFocusManager().update();
        return true;
    }

    /**
     * onClicked();
     * @return int
     */
    public int onClicked() {
        closePopMenu();
        int result = -1;
        //�������ҳ��
        int pageIndex = getMouseInPageIndex();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //�¼�����
            result = page.onMouseLeftPressed(x, y);
        }
        setHasFocusPointX(false);
        return result;
    }

    /**
     * ���̧��
     */
    public void onMouseLeftReleased() {
        if (getDraggedImage() != null) {
            getDraggedImage().onMouseLeftReleased();
        }
        setDraggedImage(null);
        if (!canEdit()) {
            return;
        }
        stopMoveTable();
        onMouseMoved();
    }

    /**
     * �Ҽ�����
     */
    public void onMouseRightPressed() {

    	//
    	if( ExpressionUtil.doProcessExpression(getPageManager(),getFocusManager().getFocus() ) )
    		return;

    	//
        onClicked();
        //closePopMenu();
        //�������ҳ��
        int pageIndex = getMouseInPageIndex();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //�¼�����
            page.onMouseRightPressed(x, y);

            //
            EComponent ec = getFocusManager().getFocus();
            if( ExpressionUtil.isExpression(ec) ){
            	ExpressionUtil.doProcessExpression(getPageManager(),ec );
            	return;
            }

            runListener("onMouseRightPressed");
        }
    }

    /**
     * ����϶�
     */
    public void onMouseDragged() {
        if (!canEdit()) {
            return;
        }
        if (getDraggedImage() != null) {
            getDraggedImage().onMouseDragged(getMouseX(), getMouseY());
            return;
        }
        //�����϶�Table
        if (isMoveTableNow()) {
            getUI().repaint();
            return;
        }
        setMouseDragged(true);
        //���
        onClicked();
        setMouseDragged(false);
        getFocusManager().setEndSelected();
    }

    /**
     * ���������ֵ�ı�
     * @param value int
     */
    public void onVScrollBarChangeValue(int value) {
        //�¼�����
        getCTableManager().onVScrollBarChangeValue(value);
    }

    /**
     * �õ������¼�
     * @return boolean
     */
    public boolean onFocusGained() {
        if (!canEdit()) {
            return false;
        }
        //�¼�����
        if (getFocusManager() != null) {
            getFocusManager().focusGained();
        }
        return true;
    }

    /**
     * ʧȥ�����¼�
     * @return boolean
     */
    public boolean onFocusLost() {
        //�¼�����
        if (getFocusManager() != null) {
            getFocusManager().onFocusLost();
        }
        return true;
    }

    /**
     * �����߳�
     * @param function String
     */
    public void startThread(String function) {
        stopThread();
        if (triggerThread == null) {
            triggerThread = new TriggerThread(this, function);
        }
        triggerThread.start();
    }

    /**
     * ֹͣ�߳�
     */
    public void stopThread() {
        if (triggerThread == null) {
            return;
        }
        triggerThread.stop();
        triggerThread = null;
    }

    /**
     * ���̰���
     * @return boolean
     */
    public boolean onKeyPressed() {
        KeyEvent e = getKeyEvent();
        if (e.isControlDown()) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_C: //�����ı�               
                getFocusManager().onCopy();
                break;
            case KeyEvent.VK_V: //ճ��
                if (!getFocusManager().getViewManager().isPreview()) {
                	getFocusManager().onTextPaste();
                   // getFocusManager().onPaste();
                }
                break;
            case KeyEvent.VK_X: //����
                if (!getFocusManager().getViewManager().isPreview()) {
                    getFocusManager().onCut();
                }
                break;
            case KeyEvent.VK_S: //����(������)

                //getPM().getFileManager().onSave();
                break;
            case KeyEvent.VK_O: //��
                if (!getFocusManager().getViewManager().isPreview()) {
                    getPM().getFileManager().onOpenDialog();
                }
                break;
            case KeyEvent.VK_F11: //ֽ�Ÿ߶���С
                if (!getFocusManager().getViewManager().isPreview()) {
                    pageHeightS();
                    setHasFocusPointX(false);
                }
                break;
            case KeyEvent.VK_F12: //ֽ�Ÿ߶�����
                if (!getFocusManager().getViewManager().isPreview()) {
                    pageHeightB();
                    setHasFocusPointX(false);
                }
                break;
            case KeyEvent.VK_1:
                EImage image = getFocusManager().getFocusImage();
                if (!getFocusManager().getViewManager().isPreview()) {
                    if (image != null) {
                        image.onSizeBlockMenu(1);
                    }
                }
                break;
            case KeyEvent.VK_2:
                if (!getFocusManager().getViewManager().isPreview()) {
                    image = getFocusManager().getFocusImage();
                    if (image != null) {
                        image.onSizeBlockMenu(2);
                    }
                }
                break;
            case KeyEvent.VK_3:
                if (!getFocusManager().getViewManager().isPreview()) {
                    image = getFocusManager().getFocusImage();
                    if (image != null) {
                        image.onSizeBlockMenu(3);
                    }
                }
                break;
            case KeyEvent.VK_4:
                if (!getFocusManager().getViewManager().isPreview()) {
                    image = getFocusManager().getFocusImage();
                    if (image != null) {
                        image.onSizeBlockMenu(4);
                    }
                }
                break;
            case KeyEvent.VK_5:
                if (!getFocusManager().getViewManager().isPreview()) {
                    image = getFocusManager().getFocusImage();
                    if (image != null) {
                        image.onSizeBlockMenu(5);
                    }
                }
                break;
            case KeyEvent.VK_6:
                if (!getFocusManager().getViewManager().isPreview()) {
                    image = getFocusManager().getFocusImage();
                    if (image != null) {
                        image.onSizeBlockMenu(6);
                    }
                }
                break;
            //TEST
            case KeyEvent.VK_W:
                if (!getFocusManager().getViewManager().isPreview()) {
                	//���� �ؼ�
                	getFocusManager().onPaste();
                }
                break;
            //TEST END
            }

            return true;
        }
        if (e.isShiftDown()) {
            if (!canEdit()) {
                return false;
            }
            switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: //�����ƶ����
                EImage image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(5);
                    break;
                }

                //����ѡ�п�ʼ
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToUp();

                //����ѡ�н���
                getFocusManager().setEndSelected();
                break;
            case KeyEvent.VK_DOWN: //�����ƶ����
                image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(6);
                    break;
                }

                //����ѡ�п�ʼ
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToDown();

                //����ѡ�н���
                getFocusManager().setEndSelected();
                break;
            case KeyEvent.VK_LEFT: //�����ƶ����
                image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(7);
                    break;
                }

                //����ѡ�п�ʼ
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToLeft();

                //����ѡ�н���
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_RIGHT: //�����ƶ����
                image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(8);
                    break;
                }

                //����ѡ�п�ʼ
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToRight();

                //����ѡ�н���
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_HOME: //�ƶ���굽����

                //����ѡ�п�ʼ
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToHome();

                //����ѡ�н���
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_END: //�ƶ���굽��β

                //����ѡ�п�ʼ
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToEnd();

                //����ѡ�н���
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_ENTER: //�س�
                if (!canEdit()) {
                    return false;
                }
                getFocusManager().clearSelected();
                onFocusToTab(false);
                setHasFocusPointX(false);
                getFocusManager().showCursor();
                break;
            }
            return true;
        }
        switch (e.getKeyCode()) {
        case KeyEvent.VK_F11: //ֽ�ſ����С
            pageWidthS();
            setHasFocusPointX(false);
            break;
        case KeyEvent.VK_F12: //ֽ�ſ������
            pageWidthB();
            setHasFocusPointX(false);
            break;
        case KeyEvent.VK_UP: //�����ƶ����
            if (!canEdit()) {
                return false;
            }
            EImage image = getFocusManager().getFocusImage();
            if (image != null) {
                image.onMoveKey(1);
                break;
            }
            getFocusManager().clearSelected();
            onFocusToUp();
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_DOWN: //�����ƶ����
            if (!canEdit()) {
                return false;
            }
            image = getFocusManager().getFocusImage();
            if (image != null) {
                image.onMoveKey(2);
                break;
            }
            getFocusManager().clearSelected();
            onFocusToDown();
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_LEFT: //�����ƶ����
            if (!canEdit()) {
                return false;
            }
            image = getFocusManager().getFocusImage();
            if (image != null) {
                image.onMoveKey(3);
                break;
            }
            getFocusManager().clearSelected();
            onFocusToLeft();
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_RIGHT: //�����ƶ����
            if (!canEdit()) {
                return false;
            }
            image = getFocusManager().getFocusImage();
            if (image != null) {
                image.onMoveKey(4);
                break;
            }
            getFocusManager().clearSelected();
            onFocusToRight();
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_HOME: //�ƶ���굽����
            if (!canEdit()) {
                return false;
            }
            getFocusManager().clearSelected();
            onFocusToHome();
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_END: //�ƶ���굽��β
            if (!canEdit()) {
                return false;
            }
            getFocusManager().clearSelected();
            onFocusToEnd();
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_ENTER: //�س�
            if (!canEdit()) {
                return false;
            }
            getFocusManager().clearSelected();
            if (!onFocusToTab(true)) {
                onEnter();
            }
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        }
        return true;
    }

    /**
     * ��¼�������궨λ�ߴ�
     * @param text EText
     */
    private void saveFocusPointX(EText text) {
        if (hasFocusPointX()) {
            return;
        }
        setHasFocusPointX(true);
        setFocusPointX(text.getFocusPointX());
    }

    /**
     * �����ƶ����
     */
    public void onFocusToUp() {
        ETR tr = getFocusManager().getFocusTRE();
        if (tr != null) {
            tr.onEocusToUp();
            return;
        }
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        //��¼�������궨λ�ߴ�
        saveFocusPointX(text);
        text.onFocusToUp(getFocusPointX());
    }

    /**
     * �����ƶ����
     */
    public void onFocusToDown() {
        ETR tr = getFocusManager().getFocusTRE();
        if (tr != null) {
            tr.onEocusToDown();
            return;
        }
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        //��¼�������궨λ�ߴ�
        saveFocusPointX(text);
        text.onFocusToDown(getFocusPointX());
    }

    /**
     * ����Tab��
     * @param flg boolean true ���� false ����
     * @return boolean
     */
    public boolean onFocusToTab(boolean flg) {
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return false;
        }
        return text.onFocusToTab(flg);
    }

    /**
     * �����ƶ����
     */
    public void onFocusToLeft() {
        ETR tr = getFocusManager().getFocusTRE();
        if (tr != null) {
            tr.onFocusToLeft();
            return;
        }
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        text.onFocusToLeft();
    }

    /**
     * �����ƶ����
     */
    public void onFocusToRight() {
        ETR tr = getFocusManager().getFocusTRE();
        if (tr != null) {
            tr.onFocusToRight();
            return;
        }
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        text.onFocusToRight();
    }

    /**
     * �ƶ���굽����
     */
    public void onFocusToHome() {
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        text.onFocusToHome();
    }

    /**
     * �ƶ���굽��β
     */
    public void onFocusToEnd() {
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        text.onFocusToEnd();
    }

    /**
     * �س�
     */
    public void onEnter() {
        //Text �س��¼�
        EText text = getFocusManager().getFocusText();
        if (text != null) {
            if (!text.onEnter()) {
                return;
            }
            getPageManager().reset();
            getUI().repaint();
            return;
        }
        //TR �س��¼�
        ETR tr = getFocusManager().getFocusTRE();
        if (tr != null) {
            if (!tr.onEnter()) {
                return;
            }
            getPageManager().reset();
            getUI().repaint();
            return;
        }
    }

    /**
     * ����̧��
     * @return boolean
     */
    public boolean onKeyReleased() {
        EComponent component = getFocusManager().getFocus();
        if (component == null && !(component instanceof EText)) {
            return false;
        }
        //EText text = (EText)component;
        KeyEvent e = getKeyEvent();
        switch (e.getKeyCode()) {
        case KeyEvent.VK_F11:
            break;
        case KeyEvent.VK_F12:
            break;
        case KeyEvent.VK_LEFT:
            break;
        default:
            if (!canEdit()) {
                return false;
            }
            if (getFocusManager().getFocusText() instanceof ETextFormat) {
                ((ETextFormat) getFocusManager().getFocusText()).update();
                ((ETextFormat) getFocusManager().getFocusText()).onKeyPressed();
            }
            break;
        }
        return true;
    }

    /**
     * ҳ��������
     */
    public void pageWidthB() {
        getPageManager().setWidth(getPM().getPageManager().getWidth() + 1);
        getPageManager().reset();
        getViewManager().resetSize();
        getUI().repaint();
    }

    /**
     * ҳ���ȼ�С
     */
    public void pageWidthS() {
        int width = getPM().getPageManager().getWidth() - 1;
        if (width < 0) {
            width = 0;
        }
        getPageManager().setWidth(width);
        getPageManager().reset();
        getUI().repaint();
    }

    /**
     * ҳ��߶�����
     */
    public void pageHeightB() {
        getPageManager().setHeight(getPM().getPageManager().getHeight() + 1);
        getPageManager().reset();
        getViewManager().resetSize();
        getUI().repaint();
    }

    /**
     * ҳ��߶ȼ�С
     */
    public void pageHeightS() {
        int height = getPM().getPageManager().getHeight() - 1;
        if (height < 0) {
            height = 0;
        }
        getPageManager().setHeight(height);
        getPageManager().reset();
        getUI().repaint();
    }

    /**
     * ��ʾ���
     */
    public void showCursor() {
        getFocusManager().showCursor();
    }

    /**
     * ����
     * @return boolean
     */
    public boolean onKeyTyped() {
        KeyEvent e = getKeyEvent();
        if (e.isAltDown() || e.isControlDown()) {
            return false;
        }
        char c = e.getKeyChar();
        switch (c) {
        case 8: //��ǰɾ��

            //ɾ��ѡ��
            if (!getFocusManager().getViewManager().isPreview()) {
                if (getFocusManager().delete(1)) {
                    break;
                }
            }

            //ɾ����
            ETR tr = getFocusManager().getFocusTR();
            if (tr != null) {
                if (!getFocusManager().getViewManager().isPreview()) {
                    tr.deleteTR();
                }
                break;
            }
            EText text = getFocusManager().getFocusText();
            if (!getFocusManager().getViewManager().isPreview()) {
                if (text instanceof EMacroroutine) {
                    EMacroroutine macroroutine = (EMacroroutine) text;
                    if (macroroutine.isPic()) {
                        macroroutine.delete();
                        getFocusManager().update();
                        return true;
                    }
                }
            }
            if (text == null) {
                return false;
            }
            if (!getFocusManager().getViewManager().isPreview()) {
                if (!text.backspaceChar()) {
                    break;
                }
            }
            break;
        case 127: //���ɾ��

            //ɾ��ѡ��
             if (!getFocusManager().getViewManager().isPreview()) {
                 if (getFocusManager().delete(2)) {
                     break;
                 }

                 //�����
                 tr = getFocusManager().getFocusTR();
                 if (tr != null) {
                     tr.clearTR();
                     break;
                 }
                 text = getFocusManager().getFocusText();
                 if (text instanceof EMacroroutine) {
                     EMacroroutine macroroutine = (EMacroroutine) text;
                     if (macroroutine.isPic()) {
                         macroroutine.delete();
                         getFocusManager().update();
                         return true;
                     }
                 }
                 if (text == null) {
                     return false;
                 }
                 if (!text.deleteChar()) {
                     break;
                 }
             }
            break;
        case 27:
        case 10:
            return true;
        default:
            //�ȴ���ѡ����ɾ��
             if (!getFocusManager().getViewManager().isPreview()) {
                 getFocusManager().delete(1);

                 //
                 EComponent ec = getFocusManager().getFocus();
                 if ( ec instanceof ENumberChoose ) {
                	  text = getFocusManager().getFocusText();
				 }else{
					  text = getFocusManager().addText( getFocusManager().getFocusText() );
				 }

                 //
                 if (text == null) {
                     return false;
                 }
                 if (!text.pasteChar(c)) {
                     getFocusManager().update();
                     return true;
                 }
             }
        }

        setHasFocusPointX(false);
        getPageManager().reset();
        showCursor();
        //getUI().repaint();
        return true;
    }

    /**
     * �����Ƿ������϶�Table
     * @param moveTableNow boolean
     */
    public void setMoveTableNow(boolean moveTableNow) {
        this.moveTableNow = moveTableNow;
    }

    /**
     * �Ƿ������϶�Table
     * @return boolean
     */
    public boolean isMoveTableNow() {
        return moveTableNow;
    }

    /**
     * �ر����Բ˵�����
     */
    public void closePopMenu() {
        if (getPropertyMenuWindow() == null) {
            return;
        }
        getPropertyMenuWindow().onClosed();
        setPropertyMenuWindow(null);
    }

    /**
     * �������Բ˵�����
     * @param propertyMenuWindow TWindow
     */
    public void setPropertyMenuWindow(TWindow propertyMenuWindow) {
        this.propertyMenuWindow = propertyMenuWindow;
    }

    /**
     * �õ����Բ˵�����
     * @return TWindow
     */
    public TWindow getPropertyMenuWindow() {
        return propertyMenuWindow;
    }

    /**
     * �����ܷ�༭
     * @param canEdit boolean
     */
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    /**
     * �ܷ�༭
     * @return boolean
     */
    public boolean canEdit() {
        return canEdit;
    }

    /**
     * ���ýӿ�
     * @param ioParm TParm
     */
    public void setIOParm(TParm ioParm) {
        this.ioParm = ioParm;
    }

    /**
     * �õ��ӿ�
     * @return TParm
     */
    public TParm getIOParm() {
        return ioParm;
    }

    /**
     * ��������϶���ʼ
     * @param isMouseDragged boolean
     */
    public void setMouseDragged(boolean isMouseDragged) {
        this.isMouseDragged = isMouseDragged;
    }

    /**
     * �Ƿ�����϶�
     * @return boolean
     */
    public boolean isMouseDragged() {
        return isMouseDragged;
    }

    /**
     * �����϶�image
     * @param image EImage
     */
    public void setDraggedImage(EImage image) {
        this.draggedImage = image;
    }

    /**
     * �õ��϶�image
     * @return EImage
     */
    public EImage getDraggedImage() {
        return draggedImage;
    }
}
