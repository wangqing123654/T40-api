package com.dongyang.tui.text;

import com.dongyang.tui.DText;
import java.awt.event.KeyEvent;
import com.dongyang.ui.TWindow;
import com.dongyang.wcomponent.expression.ExpressionUtil;
import com.dongyang.data.TParm;

/**
 *
 * <p>Title: 事件处理器</p>
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
     * 管理器
     */
    private PM pm;
    /**
     * 出发线程
     */
    TriggerThread triggerThread;
    /**
     * 存在焦点坐标定位尺寸
     */
    private boolean hasFocusPointX;
    /**
     * 坐标定位尺寸
     */
    private int focusPointX;
    /**
     * 正在拖动Table
     */
    private boolean moveTableNow;
    /**
     * 属性菜单窗口
     */
    private TWindow propertyMenuWindow;
    /**
     * 能否编辑
     */
    private boolean canEdit = true;
    /**
     * 接口
     */
    private TParm ioParm;
    /**
     * 鼠标拖动
     */
    private boolean isMouseDragged;
    /**
     * 移动Image
     */
    private EImage draggedImage;
    /**
     * 初始化
     */
    public MEvent() {
        //初始化接口
        setIOParm(new TParm());
    }

    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm) {
        this.pm = pm;
    }

    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM() {
        return pm;
    }

    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager() {
        return getPM().getPageManager();
    }

    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager() {
        return getPM().getFocusManager();
    }

    /**
     * 得到UI
     * @return DText
     */
    public DText getUI() {
        return getPM().getUI();
    }

    /**
     * 得到试图管理器
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
    }

    /**
     * 得到Table控制器管理器
     * @return MCTable
     */
    public MCTable getCTableManager() {
        return getPM().getCTableManager();
    }

    /**
     * 得到文件管理器
     * @return MFile
     */
    public MFile getFileManager() {
        return getPM().getFileManager();
    }

    /**
     * 得到键盘事件
     * @return KeyEvent
     */
    public KeyEvent getKeyEvent() {
        if (getUI() == null) {
            return null;
        }
        return getUI().getKeyEvent();
    }

    /**
     * 得到页面宽度
     * @return int
     */
    public int getPageWidth() {
        return getPageManager().getWidth();
    }

    /**
     * 得到页面高度
     * @return int
     */
    public int getPageHeight() {
        return getPageManager().getHeight();
    }

    /**
     * 得到鼠标X位置
     * @return int
     */
    public int getMouseX() {
        return getUI().getMouseX() - getUI().getInsets().left;
    }

    /**
     * 得到鼠标Y位置
     * @return int
     */
    public int getMouseY() {
        return getUI().getMouseY() - getUI().getInsets().top;
    }

    /**
     * 得到显示区横坐标
     * @return int
     */
    public int getViewX() {
        return getViewManager().getViewX();
    }

    /**
     * 得到显示区页面纵坐标
     * @param pageIndex int 页码
     * @return int
     */
    public int getViewY(int pageIndex) {
        return getViewManager().getViewY(pageIndex);
    }

    /**
     * 得到显示区的启示页号
     * @return int
     */
    public int getStartPageIndex() {
        return getViewManager().getStartPageIndex();
    }

    /**
     * 得到显示区的结束页号
     * @return int
     */
    public int getEndPageIndex() {
        return getViewManager().getEndPageIndex();
    }

    /**
     * 得到页面对象
     * @param index int
     * @return EPage
     */
    public EPage getPage(int index) {
        return getPageManager().get(index);
    }

    /**
     * 设置存在焦点坐标定位尺寸
     * @param hasFocusPointX boolean
     */
    public void setHasFocusPointX(boolean hasFocusPointX) {
        this.hasFocusPointX = hasFocusPointX;
    }

    /**
     * 是否存在焦点坐标定位尺寸
     * @return boolean
     */
    public boolean hasFocusPointX() {
        return hasFocusPointX;
    }

    /**
     * 设置坐标定位尺寸
     * @param focusPointX int
     */
    public void setFocusPointX(int focusPointX) {
        this.focusPointX = focusPointX;
    }

    /**
     * 得到坐标定位尺寸
     * @return int
     */
    public int getFocusPointX() {
        return focusPointX;
    }

    /**
     * 得到鼠标所在页面
     * @return int
     */
    public int getMouseInPageIndex() {
        //显示区的启示页号
        int startPage = getStartPageIndex();
        int pageHeight = (int) (getPageHeight() * getZoom() / 75.0 + 0.5);
        //显示区的结束页号
        int endPage = getEndPageIndex();
        //鼠标Y坐标
        int mouseY = getMouseY();
        //绘制显示区页面
        for (int i = startPage; i < endPage; i++) {
            //得到显示区页面纵坐标
            int pageY = getViewY(i);
            if (pageY < mouseY && mouseY < pageY + pageHeight) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 鼠标滑轮
     * @return boolean
     */
    public boolean onMouseWheelMoved() {
        return false;
    }

    /**
     * 尺寸改变事件
     */
    public void onComponentResized() {
        //调整滚动块尺寸
        getViewManager().resetSize();
    }

    /**
     * 得到缩放比例
     * @return double
     */
    public double getZoom() {
        return getViewManager().getZoom();
    }

    /**
     * 鼠标所在页面
     * @return int
     */
    public int getMouseInPageIndexXY() {
        //鼠标所在页面
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
     * 鼠标移动
     * @return boolean
     */
    public boolean onMouseMoved() {
        if (!canEdit()) {
            return false;
        }
        getFocusManager().setMoveTable(null);
        //鼠标所在页面
        int pageIndex = getMouseInPageIndexXY();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //事件传递
            if (page.onMouseMoved(x, y)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 鼠标进入
     */
    public void onMouseEntered() {
        //System.out.println("onMouseEntered");
    }

    /**
     * 鼠标离开
     */
    public void onMouseExited() {
        //System.out.println("onMouseExited");
    }

    /**
     * 执行事件
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListener(String action, Object ...parameters) {
        return getFileManager().runListenerArray(action, parameters);
    }

    /**
     * 鼠标双击
     * @return boolean
     */
    public boolean onDoubleClickedS() {
        //鼠标所在页面
        int pageIndex = getMouseInPageIndex();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //事件传递
            if (page.onDoubleClickedS(x, y)) {
                return true;
            }
            runListener("onDoubleClicked", pageIndex, x, y);
        }
        return false;
    }

    /**
     * 是否按下Control
     * @return boolean
     */
    public boolean isControlDown() {
        return getUI().isControlDown();
    }

    /**
     * 左键按下
     */
    public void onMouseLeftPressed() {

        if (!canEdit()) {
            return;
        }
        if (!startMoveTable()) {
            if (isControlDown()) {
                //压制选蓝列表
                getFocusManager().putSelected();
                //点击
                onClicked();
                //设置选中开始
                getFocusManager().setStartSelected();
            } else {
                if (getUI().isShiftDown()) {
                    if (!getFocusManager().isSelected()) {
                        //设置选中开始
                        getFocusManager().setStartSelected();
                    }
                    //点击
                    onClicked();
                    //设置选中开始
                    getFocusManager().setEndSelected();

                } else {
                    //清除选蓝
                    getFocusManager().clearSelected();
                    //点击
                    onClicked();
                    //设置选中开始
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
     * 开始移动Table
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
     * 停止移动Table
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
        //鼠标所在页面
        int pageIndex = getMouseInPageIndex();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //事件传递
            result = page.onMouseLeftPressed(x, y);
        }
        setHasFocusPointX(false);
        return result;
    }

    /**
     * 左键抬起
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
     * 右键按下
     */
    public void onMouseRightPressed() {

    	//
    	if( ExpressionUtil.doProcessExpression(getPageManager(),getFocusManager().getFocus() ) )
    		return;

    	//
        onClicked();
        //closePopMenu();
        //鼠标所在页面
        int pageIndex = getMouseInPageIndex();
        if (pageIndex != -1) {
            EPage page = getPage(pageIndex);
            int x = getMouseX() - getViewX();
            int y = getMouseY() - getViewY(pageIndex);
            //事件传递
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
     * 鼠标拖动
     */
    public void onMouseDragged() {
        if (!canEdit()) {
            return;
        }
        if (getDraggedImage() != null) {
            getDraggedImage().onMouseDragged(getMouseX(), getMouseY());
            return;
        }
        //正在拖动Table
        if (isMoveTableNow()) {
            getUI().repaint();
            return;
        }
        setMouseDragged(true);
        //点击
        onClicked();
        setMouseDragged(false);
        getFocusManager().setEndSelected();
    }

    /**
     * 纵向滚动条值改变
     * @param value int
     */
    public void onVScrollBarChangeValue(int value) {
        //事件传递
        getCTableManager().onVScrollBarChangeValue(value);
    }

    /**
     * 得到焦点事件
     * @return boolean
     */
    public boolean onFocusGained() {
        if (!canEdit()) {
            return false;
        }
        //事件传递
        if (getFocusManager() != null) {
            getFocusManager().focusGained();
        }
        return true;
    }

    /**
     * 失去焦点事件
     * @return boolean
     */
    public boolean onFocusLost() {
        //事件传递
        if (getFocusManager() != null) {
            getFocusManager().onFocusLost();
        }
        return true;
    }

    /**
     * 启动线程
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
     * 停止线程
     */
    public void stopThread() {
        if (triggerThread == null) {
            return;
        }
        triggerThread.stop();
        triggerThread = null;
    }

    /**
     * 键盘按下
     * @return boolean
     */
    public boolean onKeyPressed() {
        KeyEvent e = getKeyEvent();
        if (e.isControlDown()) {
            switch (e.getKeyCode()) {
            case KeyEvent.VK_C: //复制文本               
                getFocusManager().onCopy();
                break;
            case KeyEvent.VK_V: //粘贴
                if (!getFocusManager().getViewManager().isPreview()) {
                	getFocusManager().onTextPaste();
                   // getFocusManager().onPaste();
                }
                break;
            case KeyEvent.VK_X: //剪切
                if (!getFocusManager().getViewManager().isPreview()) {
                    getFocusManager().onCut();
                }
                break;
            case KeyEvent.VK_S: //保存(有问题)

                //getPM().getFileManager().onSave();
                break;
            case KeyEvent.VK_O: //打开
                if (!getFocusManager().getViewManager().isPreview()) {
                    getPM().getFileManager().onOpenDialog();
                }
                break;
            case KeyEvent.VK_F11: //纸张高度缩小
                if (!getFocusManager().getViewManager().isPreview()) {
                    pageHeightS();
                    setHasFocusPointX(false);
                }
                break;
            case KeyEvent.VK_F12: //纸张高度增大
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
                	//复制 控件
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
            case KeyEvent.VK_UP: //向上移动光标
                EImage image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(5);
                    break;
                }

                //设置选中开始
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToUp();

                //设置选中结束
                getFocusManager().setEndSelected();
                break;
            case KeyEvent.VK_DOWN: //向下移动光标
                image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(6);
                    break;
                }

                //设置选中开始
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToDown();

                //设置选中结束
                getFocusManager().setEndSelected();
                break;
            case KeyEvent.VK_LEFT: //向左移动光标
                image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(7);
                    break;
                }

                //设置选中开始
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToLeft();

                //设置选中结束
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_RIGHT: //向右移动光标
                image = getFocusManager().getFocusImage();
                if (image != null) {
                    image.onMoveKey(8);
                    break;
                }

                //设置选中开始
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToRight();

                //设置选中结束
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_HOME: //移动光标到行首

                //设置选中开始
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToHome();

                //设置选中结束
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_END: //移动光标到行尾

                //设置选中开始
                if (!getFocusManager().isSelected()) {
                    getFocusManager().setStartSelected();
                }
                onFocusToEnd();

                //设置选中结束
                getFocusManager().setEndSelected();
                setHasFocusPointX(false);
                break;
            case KeyEvent.VK_ENTER: //回车
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
        case KeyEvent.VK_F11: //纸张宽度缩小
            pageWidthS();
            setHasFocusPointX(false);
            break;
        case KeyEvent.VK_F12: //纸张宽度增大
            pageWidthB();
            setHasFocusPointX(false);
            break;
        case KeyEvent.VK_UP: //向上移动光标
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
        case KeyEvent.VK_DOWN: //向下移动光标
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
        case KeyEvent.VK_LEFT: //向左移动光标
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
        case KeyEvent.VK_RIGHT: //向右移动光标
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
        case KeyEvent.VK_HOME: //移动光标到行首
            if (!canEdit()) {
                return false;
            }
            getFocusManager().clearSelected();
            onFocusToHome();
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_END: //移动光标到行尾
            if (!canEdit()) {
                return false;
            }
            getFocusManager().clearSelected();
            onFocusToEnd();
            setHasFocusPointX(false);
            getFocusManager().showCursor();
            break;
        case KeyEvent.VK_ENTER: //回车
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
     * 记录焦点坐标定位尺寸
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
     * 向上移动光标
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
        //记录焦点坐标定位尺寸
        saveFocusPointX(text);
        text.onFocusToUp(getFocusPointX());
    }

    /**
     * 向下移动光标
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
        //记录焦点坐标定位尺寸
        saveFocusPointX(text);
        text.onFocusToDown(getFocusPointX());
    }

    /**
     * 按下Tab键
     * @param flg boolean true 向下 false 向上
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
     * 向左移动光标
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
     * 向右移动光标
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
     * 移动光标到行首
     */
    public void onFocusToHome() {
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        text.onFocusToHome();
    }

    /**
     * 移动光标到行尾
     */
    public void onFocusToEnd() {
        EText text = getFocusManager().getFocusText();
        if (text == null) {
            return;
        }
        text.onFocusToEnd();
    }

    /**
     * 回车
     */
    public void onEnter() {
        //Text 回车事件
        EText text = getFocusManager().getFocusText();
        if (text != null) {
            if (!text.onEnter()) {
                return;
            }
            getPageManager().reset();
            getUI().repaint();
            return;
        }
        //TR 回车事件
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
     * 键盘抬起
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
     * 页面宽度增加
     */
    public void pageWidthB() {
        getPageManager().setWidth(getPM().getPageManager().getWidth() + 1);
        getPageManager().reset();
        getViewManager().resetSize();
        getUI().repaint();
    }

    /**
     * 页面宽度减小
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
     * 页面高度增加
     */
    public void pageHeightB() {
        getPageManager().setHeight(getPM().getPageManager().getHeight() + 1);
        getPageManager().reset();
        getViewManager().resetSize();
        getUI().repaint();
    }

    /**
     * 页面高度减小
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
     * 显示光标
     */
    public void showCursor() {
        getFocusManager().showCursor();
    }

    /**
     * 击打
     * @return boolean
     */
    public boolean onKeyTyped() {
        KeyEvent e = getKeyEvent();
        if (e.isAltDown() || e.isControlDown()) {
            return false;
        }
        char c = e.getKeyChar();
        switch (c) {
        case 8: //向前删除

            //删除选蓝
            if (!getFocusManager().getViewManager().isPreview()) {
                if (getFocusManager().delete(1)) {
                    break;
                }
            }

            //删除行
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
        case 127: //向后删除

            //删除选蓝
             if (!getFocusManager().getViewManager().isPreview()) {
                 if (getFocusManager().delete(2)) {
                     break;
                 }

                 //清空行
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
            //先处理选蓝的删除
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
     * 设置是否正在拖动Table
     * @param moveTableNow boolean
     */
    public void setMoveTableNow(boolean moveTableNow) {
        this.moveTableNow = moveTableNow;
    }

    /**
     * 是否正在拖动Table
     * @return boolean
     */
    public boolean isMoveTableNow() {
        return moveTableNow;
    }

    /**
     * 关闭属性菜单窗口
     */
    public void closePopMenu() {
        if (getPropertyMenuWindow() == null) {
            return;
        }
        getPropertyMenuWindow().onClosed();
        setPropertyMenuWindow(null);
    }

    /**
     * 设置属性菜单窗口
     * @param propertyMenuWindow TWindow
     */
    public void setPropertyMenuWindow(TWindow propertyMenuWindow) {
        this.propertyMenuWindow = propertyMenuWindow;
    }

    /**
     * 得到属性菜单窗口
     * @return TWindow
     */
    public TWindow getPropertyMenuWindow() {
        return propertyMenuWindow;
    }

    /**
     * 设置能否编辑
     * @param canEdit boolean
     */
    public void setCanEdit(boolean canEdit) {
        this.canEdit = canEdit;
    }

    /**
     * 能否编辑
     * @return boolean
     */
    public boolean canEdit() {
        return canEdit;
    }

    /**
     * 设置接口
     * @param ioParm TParm
     */
    public void setIOParm(TParm ioParm) {
        this.ioParm = ioParm;
    }

    /**
     * 得到接口
     * @return TParm
     */
    public TParm getIOParm() {
        return ioParm;
    }

    /**
     * 设置鼠标拖动开始
     * @param isMouseDragged boolean
     */
    public void setMouseDragged(boolean isMouseDragged) {
        this.isMouseDragged = isMouseDragged;
    }

    /**
     * 是否鼠标拖动
     * @return boolean
     */
    public boolean isMouseDragged() {
        return isMouseDragged;
    }

    /**
     * 设置拖动image
     * @param image EImage
     */
    public void setDraggedImage(EImage image) {
        this.draggedImage = image;
    }

    /**
     * 得到拖动image
     * @return EImage
     */
    public EImage getDraggedImage() {
        return draggedImage;
    }
}
