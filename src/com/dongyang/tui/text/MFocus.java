package com.dongyang.tui.text;

import com.dongyang.tui.DText;
import com.dongyang.util.TList;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Stroke;
import com.dongyang.util.TypeTool;
import com.dongyang.util.StringTool;
import com.dongyang.data.TParm;
import com.dongyang.tui.text.div.MV;
import com.dongyang.tui.text.div.VPic;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

import com.dongyang.ui.TPopupMenu;
import com.dongyang.tui.DMessageIO;
import javax.swing.Icon;
import java.util.List;

/**
 *
 * <p>Title: 焦点控制器</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.13
 * @author whao 2013~
 * @version 1.0
 */
public class MFocus {
    /**
     * 管理器
     */
    private PM pm;
    /**
     * 光标闪烁线称
     */
    private CursorThread cursorThread;
    /**
     * 光标闪烁休眠时间
     */
    private int cursorSleepCount;
    /**
     * 显示光标
     */
    private boolean showCursor;
    /**
     * 选中对象
     */
    private CSelectedModel selectedModel;
    /**
     * 移动Table
     */
    private ETable moveTable;
    /**
     * 移动Table尺寸
     */
    private int moveTableIndex;
    /**
     * 移动Table X坐标
     */
    private int moveTableX;
    /**
     * 接口
     */
    private TParm ioParm;
    /**
     * 插入图片块动作
     */
    private int insertImageAction;
    /**
     * 构造器
     */
    public MFocus() {
        //初始化接口
        setIOParm(new TParm());
        //初始化选中对象
        setSelectedModel(new CSelectedModel(this));
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
     * 得到UI
     * @return DText
     */
    public DText getUI() {
        return getPM().getUI();
    }

    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager() {
        PM pm = getPM();
        if (pm == null) {
            return null;
        }
        return pm.getPageManager();
    }

    /**
     * 得到试图管理器
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
    }

    /**
     * 得到事件管理器
     * @return MEvent
     */
    public MEvent getEventManager() {
        return getPM().getEventManager();
    }

    /**
     * 得到当前页
     * @return int
     */
    public int getFocusPageIndex() {
        if (getFocus() == null) {
            return 0;
        }
        EPage page = getFocus().getPage();
        if (page == null) {
            return 0;
        }
        return getPageManager().indexOf(page);
    }

    /**
     * 调整焦点
     */
    public void reset() {
        MPage pageManager = getPageManager();
        if (pageManager == null) {
            return;
        }
        EText text = pageManager.getFirstText();
        if (text == null) {
            return;
        }
        setFocusText(text);
    }

    /**
     * 更新
     */
    public void update() {
        //getPM().getPageManager().debugShow();//temp
        getPageManager().reset();
        getUI().repaint();
    }

    /**
     * 显示光标
     */
    public void showCursor() {
        showCursor = true;
        cursorSleepCount = 0;
        repaintCursor();
    }

    /**
     * 是否显示光标
     * @return boolean
     */
    public boolean isShowCursor() {
        return showCursor;
    }

    /**
     * 重新绘制光标
     */
    public void repaintCursor() {
        if (getUI() == null) {
            return;
        }
        //临时
        getUI().repaint();
    }

    /**
     * 得到焦点
     */
    public void focusGained() {
        cursorThread = new CursorThread();
        cursorThread.start();
        showCursor = true;
        cursorSleepCount = 0;
        repaintCursor();
    }

    /**
     * 失去焦点
     */
    public void onFocusLost() {
        cursorThread = null;
        showCursor = false;
        cursorSleepCount = 0;
        repaintCursor();
    }

    class CursorThread extends Thread {
        public void run() {
            while (cursorThread != null) {
                try {
                    sleep(1);
                } catch (Exception e) {
                }
                cursorSleepCount++;
                if (cursorSleepCount < 400) {
                    continue;
                }
                showCursor = !showCursor;
                repaintCursor();
                cursorSleepCount = 0;
            }
        }
    }


    /**
     * 设置对其位置(当前位置)
     * @param alignment int
     * 0 left
     * 1 center
     * 2 right
     * @return boolean
     */
    public boolean setAlignment(int alignment) {
        TList list = getFocusPanels();
        if (list == null) {
            return true;
        }
        for (int i = 0; i < list.size(); i++) {
            ((EPanel) list.get(i)).setAlignmentAll(alignment);
        }
        return true;
    }

    /**
     * 分割
     * @return boolean true 成功 false 失败
     */
    public boolean separate() {
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return false;
        }
        EText eText = (EText) component;
        if (eText.getLength() == 0 || getFocusIndex() == 0) {
            return true;
        }
        if (getFocusIndex() == eText.getLength()) {
            EText text = eText.getNextText();
            if (text == null) {
                text = eText.getPanel().newText(eText.findIndex() + 1);
                text.setBlock(eText.getEnd(), eText.getEnd());
            }
            setFocusText(text);
            return true;
        }
        if (eText instanceof EFixed) {
            //测试可能有问题return false;
            return false;
        }
        eText.separate(getFocusIndex());
        setFocusText(eText.getNextText());
        return true;
    }

    /**
     * 分割面板
     * @return boolean
     */
    public boolean separatePanel() {
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return false;
        }
        EText eText = (EText) component;
        if (eText.getLength() == 0 || getFocusIndex() == 0) {
            if (eText.findIndex() == 0) {
                return true;
            }
            eText.getPanel().separateText(eText);
            return true;
        }
        if (getFocusIndex() == eText.getLength()) {
            EText text = eText.getNextText();
            if (text == null) {
                text = eText.getPanel().newText(eText.findIndex() + 1);
                text.setBlock(eText.getEnd(), eText.getEnd());
            }
            setFocusText(text);
            text.getPanel().separateText(text);
            return true;
        }
        if (eText instanceof EFixed) {
            return false;
        }
        eText.separate(getFocusIndex());
        EText text = eText.getNextText();
        setFocusText(text);
        text.getPanel().separateText(text);
        return true;
    }

    /**
     * 插入宏
     * @param name String 宏名称
     * @param script String 脚本
     * @param type int 类型
     * @return EMacroroutine
     */
    public EMacroroutine insertMacroroutine(String name, String script,
                                            int type) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EMacroroutine macroroutine = ePanel.newMacroroutine(eText.findIndex());
        macroroutine.setBlock(eText.getStart(), eText.getStart());
        EMacroroutineModel model = macroroutine.createModel();
        //创建语法
        ESyntaxItem syntax = model.createSyntax();
        syntax.setName(name);
        syntax.setSyntax(script);
        syntax.setType(type);
        syntax.setClassify(1);
        //编译程序
        if (!syntax.getManager().make()) {
            getUI().messageBox(syntax.getManager().getMakeErrText());
        }
        model.show();
        return macroroutine;
    }

    /**
     * 插入固定文本
     * @param name String 名称
     * @param text String 显示文字
     * @return EFixed
     */
    public EFixed insertFixed(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EFixed fixed = ePanel.newFixed(eText.findIndex());
        fixed.setBlock(eText.getStart(), eText.getStart());
        fixed.setText(text);
        fixed.setName(name);
        //fixed.setSub(false);
        //fixed.setSub(false);
        return fixed;
    }

    /**
     * 插入单选
     * @param name String 名称
     * @param text String 显示文字
     * @return ESingleChoose
     */
    public ESingleChoose insertSingleChoose(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        ESingleChoose singleChoose = ePanel.newSingleChoose(eText.findIndex());
        singleChoose.setBlock(eText.getStart(), eText.getStart());
        singleChoose.setText(text);
        singleChoose.setName(name);
        return singleChoose;
    }

    /**
     * 插入多选
     * @param name String 名称
     * @param text String 显示文字
     * @return EMultiChoose
     */
    public EMultiChoose insertMultiChoose(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EMultiChoose multiChoose = ePanel.newMultiChoose(eText.findIndex());
        multiChoose.setBlock(eText.getStart(), eText.getStart());
        multiChoose.setText(text);
        multiChoose.setName(name);
        return multiChoose;
    }

    /**
     * 插入有无选择
     * @param name String 名称
     * @param text String 显示文字
     * @return EHasChoose
     */
    public EHasChoose insertHasChoose(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EHasChoose hasChoose = ePanel.newHasChoose(eText.findIndex());
        hasChoose.setBlock(eText.getStart(), eText.getStart());
        hasChoose.setText(text);
        hasChoose.setName(name);
        return hasChoose;
    }

    /**
     * 插入宏
     * @param name String 名称
     * @param text String 显示文字
     * @return EMicroField
     */
    public EMicroField insertMicroField(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EMicroField microField = ePanel.newMicroField(eText.findIndex());
        microField.setBlock(eText.getStart(), eText.getStart());
        String value = getPM().getMicroFieldManager().getMicroField(name);
        if (value != null && value.length() > 0) {
            text = value;
        }
        microField.setText(text);
        microField.setName(name);
        return microField;
    }
    
	/**
	 * 插入签名
	 * 
	 * @param groupName
	 *            元素组名
	 * @param name
	 *            元素名
	 * @param signCode
	 *            签名
	 * @param text
	 *            签名显示名子
	 * @param timestmp
	 *            签名时间戳
	 * @return
	 */
	public ESign insertSign(String groupName, String name, String signCode,
			String text, String timestmp) {
		// 分割
		if (!separate()) {
			return null;
		}
		EComponent component = getFocus();
		if (component == null || !(component instanceof EText)) {
			return null;
		}
		//
		EText eText = (EText) component;
		EPanel ePanel = eText.getPanel();
		ESign sign = ePanel.newSign(eText.findIndex());
		sign.setBlock(eText.getStart(), eText.getStart());
		//
		sign.setGroupName(groupName);
		sign.setName(name);
		sign.setSignCode(signCode);
		sign.setText(text);
		sign.setTimestmp(timestmp);
		sign.setActionType("HR22.01.100");//签名
		//
		return sign;
	}

    /**
     * 插入抓取
     * @param name String
     * @param type int
     * @return ECapture
     */
    public ECapture insertCaptureObject(String name, int type) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        ECapture capture = ePanel.newCapture(eText.findIndex());
        capture.setBlock(eText.getStart(), eText.getStart());
        capture.setName(name);
        capture.setCaptureType(type);
        return capture;
    }

    /**
     * 数字选择
     * @param name String
     * @param text String
     * @return ECapture
     */
    public ENumberChoose insertNumberChoose(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        ENumberChoose numberChoose = ePanel.newNumberChoose(eText.findIndex());
        numberChoose.setBlock(eText.getStart(), eText.getStart());
        numberChoose.setName(name);
        numberChoose.setText(text);
        return numberChoose;
    }

    /**
     * 插入选择框
     * @param name String
     * @param text String
     * @param isChecked boolean
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose insertCheckBoxChoose(String name, String text,
                                                boolean isChecked) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        ECheckBoxChoose checkBoxChoose = ePanel.newCheckBoxChoose(eText.
                findIndex());
        checkBoxChoose.setBlock(eText.getStart(), eText.getStart());
        checkBoxChoose.setName(name);
        checkBoxChoose.setText(text);
        checkBoxChoose.setChecked(isChecked);
        return checkBoxChoose;
    }

    /**
     * 插入输入提示组件
     * @param name String 名称
     * @param text String 显示文字
     * @return EInputText
     */
    public EInputText insertInputText(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EInputText inputText = ePanel.newInputText(eText.findIndex());
        inputText.setBlock(eText.getStart(), eText.getStart());
        inputText.setText(text);
        inputText.setName(name);
        return inputText;
    }

    /**
     * 插入图区
     * @return EPic
     */
    public EPic insertPic() {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EPic pic = ePanel.newPic(eText.findIndex());
        EPicModel model = pic.createModel();
        model.initSyntax();
        pic.initNew();
        pic.setModify(true);
        return pic;
    }

    /**
     * 插入表格(当前位置)
     * @return ETable
     */
    public ETable insertTable() {
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        //禁止在表格中在插入表格
        if (eText.getPanel().parentIsTD()) {
            messageBox("表格中不能在插入表格!");
            return null;
        }
        if (eText.getLength() > 0 && getFocusIndex() > 0) {
            eText.onEnter();
            eText = (EText) getFocus();
        }
        eText.onEnter();
        eText = (EText) getFocus();
        eText.onFocusToLeft();

        eText = (EText) getFocus();
        EPanel ePanel = eText.getPanel();
        ePanel.remove(0);
        ETable table = ePanel.newTable();
        //设置面板位表格类型
        ePanel.setType(1);
        //创建模型
        table.createModel();
        ePanel.setModify(true);
        //删除首页table为首控件之前的空行
        /*if(ePanel.findIndex() == 1 &&
           ePanel.parentIsPage() &&
           ePanel.getParentPage().findIndex() == 0)
                 {
            EPanel panel = ePanel.getParentPage().get(0);
            if(panel.size() == 1 &&
               panel.get(0) instanceof EText &&
               ((EText)panel.get(0)).getLength() == 0)
                panel.removeThis();
                 }*/
        return table;
    }

    /**
     * 插入基本Table对话框
     * @return ETable
     */
    public ETable insertBaseTableDialog() {
        Object[] value = (Object[]) getPM().getUI().openDialog(
                "%ROOT%\\config\\database\\InsertTableBase.x");
        if (value == null) {
            return null;
        }
        String name = TypeTool.getString(value[0]);
        int row = TypeTool.getInt(value[1]);
        int column = TypeTool.getInt(value[2]);
        ETable table = insertTable();
        if (table == null) {
            return null;
        }
        table.setName(name);
        table.setShowBorder2(true);
        table.setShowWLine(true);
        table.setShowHLine(true);
        table.getInsets().setInsets(0, 0, 0, 0);
        table.getTRInsets().setInsets(0, 0, 0, 0);
        insertTable(table, row, column);
        table.setFocus();
        return table;
    }

    /**
     * 插入表格(当前位置)
     * @param rowCount int 行数
     * @param columnCount int 列数
     * @return ETable
     */
    public ETable insertTable(int rowCount, int columnCount) {
        return insertTable(null, rowCount, columnCount);
    }

    /**
     * 插入表格(当前位置)
     * @param table ETable
     * @param rowCount int
     * @param columnCount int
     * @return ETable
     */
    public ETable insertTable(ETable table, int rowCount, int columnCount) {
        if (table == null) {
            //新增Table
            table = insertTable();
        }
        if (table == null) {
            return null;
        }
        //得到模型
        ETableModel model = table.getModel();
        //初始化列数
        model.init(columnCount);
        //插入新行
        for (int i = 0; i < rowCount; i++) {
            table.insertRow(i);
        }
        return table;
    }

    /**
     * 得到焦点Table
     * @return ETable
     */
    public ETable getFocusTable() {
        EComponent com = getFocus();
        if (com == null || !(com instanceof EText)) {
            return null;
        }
        EText text = (EText) com;
        return text.getTable();
    }

    /**
     * 分割
     * @param text EText
     * @param state int
     * @return EText
     */
    public EText separate(EText text, int state) {
        if (text.getLength() == 0) {
            return text;
        }
        /*if(state == 1 && text.isSelectedState(1))
                 {
            if(text.separate(text.getSelectIndex()))
                return text.getNextText();
            else
                return text;
                 }
                 if(state == 2 && text.isSelectedState(2))
                 {
            text.separate(text.getSelectEndIndex());
            return text;
                 }*/
        return text;
    }

    /**
     * 分割选蓝
     */
    public void separateSelect() {
        /*if(selectTextList == null)
            return;
         selectTextList.set(0,separate((EText)selectTextList.get(0),1));
                 selectTextList.set(selectTextList.size() - 1,separate((EText)selectTextList.get(selectTextList.size() - 1),2));*/
    }

    /**
     * 修改字体
     * @param s String
     * @return boolean
     */
    public boolean modifyFont(String s) {
        exeAction(new EAction(EAction.FONT_NAME, s));
        return true;
    }

    /**
     * 修改字体斜体
     * @param bold boolean
     * @return boolean
     */
    public boolean modifyBold(boolean bold) {
        exeAction(new EAction(EAction.FONT_BOLD, bold));
        return true;
    }

    /**
     * 修改字体尺寸
     * @param size int
     * @return boolean
     */
    public boolean modifyFontSize(int size) {
        exeAction(new EAction(EAction.FONT_SIZE, size));
        return true;
    }

    /**
     * 修改字体斜体
     * @param italic boolean
     * @return boolean
     */
    public boolean modifyItalic(boolean italic) {
        exeAction(new EAction(EAction.FONT_ITALIC, italic));
        return true;
    }

    /**
     * 删除动作
     * @param flg int
     * 0 彻底删除
     * 1 普通删除
     * 2 清空
     * @return boolean
     */
    public boolean delete(int flg) {
        if (!isSelected()) {
            return false;
        }

        exeAction(new EAction(EAction.DELETE, flg));
        return true;
    }

    /**
     * 设置移动Table
     * @param moveTable ETable
     */
    public void setMoveTable(ETable moveTable) {
        this.moveTable = moveTable;
    }

    /**
     * 得到移动Table
     * @return ETable
     */
    public ETable getMoveTable() {
        return moveTable;
    }

    /**
     * 设置移动Table尺寸
     * @param moveTableIndex int
     */
    public void setMoveTableIndex(int moveTableIndex) {
        this.moveTableIndex = moveTableIndex;
    }

    /**
     * 得到移动Table尺寸
     * @return int
     */
    public int getMoveTableIndex() {
        return moveTableIndex;
    }

    /**
     * 设置移动Table X坐标
     * @param moveTableX int
     */
    public void setMoveTableX(int moveTableX) {
        this.moveTableX = moveTableX;
    }

    /**
     * 得到移动Table X坐标
     * @return int
     */
    public int getMoveTableX() {
        return moveTableX;
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
     * 得到缩放比例
     * @return double
     */
    public double getZoom() {
        return getViewManager().getZoom();
    }

    /**
     * 绘制页面
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param pageIndex int
     */
    public void paintPage(Graphics g, int x, int y, int width, int height,
                          int pageIndex) {
        if (!getEventManager().isMoveTableNow()) {
            return;
        }
        ETable table = getMoveTable();
        if (table == null) {
            return;
        }
        EPage page = getPageManager().get(pageIndex);
        if (page != table.getPage()) {
            return;
        }
        int x1 = getMouseX();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(1.0f));
        g2d.setColor(new Color(0, 0, 0));
        Stroke bs = new BasicStroke(1.0f, BasicStroke.CAP_BUTT,
                                    BasicStroke.JOIN_BEVEL,
                                    0, new float[] {3.f, 1.f, 1.f}, 0);
        g2d.setStroke(bs);
        g2d.drawLine(x1, 0, x1, height);
    }

    /**
     * 编辑
     */
    public void onEditWord() {
        getViewManager().setPreview(false);
        getPM().getCTableManager().showEdit();
        getPM().getMacroroutineManager().edit();
        getPM().getPageManager().resetData(new EAction(EAction.EDIT_STATE));
        getEventManager().setCanEdit(true);
    }

    /**
     * 阅览
     */
    public void onPreviewWord() {
        //清除选蓝
        clearSelected();
        getViewManager().setPreview(true);
        getPM().getCTableManager().retrieve();
        getPM().getCTableManager().showData();
        getPM().getMacroroutineManager().run();
        getPM().getPageManager().resetData(new EAction(EAction.PREVIEW_STATE));
        getEventManager().setCanEdit(false);
        //加入事件管理器
        getPM().setEventManager(new MEvent());
        //getPM().setStyleManager(new MStyle());
        getPM().setFocusManager(new MFocus());
        //
        update();
    }

    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action) {
        getPageManager().resetData(action);
    }

    /**
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox(Object message) {
        getPM().getUI().messageBox(message);
    }

    /**
     * 插入表格行
     * @return ETR
     */
    public ETR insertTR() {
        ETR tr = getFocusTR();
        if (tr == null) {
            return null;
        }
        tr = tr.getHeadTR();
        ETable table = tr.getTable();
        int index = table.indexOf(tr);
        table.insertRow(index);
        tr = table.get(index);
        tr.setFocus();
        return tr;
    }

    /**
     * 追加表格行
     * @return ETR
     */
    public ETR appendTR() {
        ETR tr = getFocusTR();
        if (tr == null) {
            return null;
        }
        tr = tr.getEndTR();
        ETable table = tr.getTable();
        int index = table.indexOf(tr) + 1;
        table.insertRow(index);
        tr = table.get(index);
        tr.setFocus();
        return tr;
    }

    /**
     * 删除表格行
     * @return boolean
     */
    public boolean deleteTR() {
        ETR tr = getFocusTR();
        if (tr == null) {
            return false;
        }
        tr = tr.getHeadTR();
        ETable table = tr.getTable();
        //Table被删光设置焦点
        if (table.getTableRowCount() == 1) {
            table.getPanel().setNewFocusPanel();
        }
        int index = tr.findIndex();
        ETR nextTR = tr.getNextLinkTR();
        tr.removeThis();
        while (nextTR != null) {
            tr = nextTR;
            nextTR = tr.getNextLinkTR();
            tr.removeThis();
        }
        table.setModify(true);
        if (table.size() == 0) {
            if (table.getPreviousTable() != null &&
                table.getPreviousTable().size() > 0) {
                table.getPreviousTable().get(table.getPreviousTable().size() -
                                             1).setFocus();
                return true;
            }
            if (table.getNextTable() != null && table.getNextTable().size() > 0) {
                table.getNextTable().get(0).setFocus();
                return true;
            }
            return true;
        }
        if (index < table.size()) {
            table.get(index).setFocus();
            return true;
        }
        if (table.getNextTable() != null) {
            table.getNextTable().setFocus();
            return true;
        }
        table.get(table.size() - 1).setFocus();
        return true;
    }

    /**
     * 段落对话框
     */
    public void onParagraphDialog() {
        if (getFocus() == null) {
            return;
        }
        getUI().openDialog("%ROOT%\\config\\database\\ParagraphEdit.x", this);
    }

    /**
     * Table 属性对话框
     */
    public void onTablePropertyDialog() {
        ETable table = getFocusTable();
        if (table == null) {
            return;
        }
        getUI().openDialog("%ROOT%\\config\\database\\TablePropertyDialog.x",
                           table);
    }

    /**
     * 能否编辑
     * @return boolean
     */
    public boolean canEdit() {
        return getEventManager().canEdit();
    }

    /**
     * 复制
     * @return boolean
     */
    public boolean onCopy() {
        //$$======Modified by lx 2011-09-06 加入结构化病历控件复制功能START===========$$//
        //每次重新复制控件
        CopyOperator.clearComList();
        //$$======Modified by lx 2011-09-06 加入结构化病历控件复制功能END===========$$//
        EImage image = getFocusImage();
        if (image != null) {
            image.exeAction(new EAction(EAction.COPY));
            return true;
        }
        if (!isSelected()) {
            return false;
        }
        CopyOperator copyOperator = new CopyOperator();
        exeAction(new EAction(EAction.COPY, copyOperator));
        //是浏览
        if(getViewManager().isPreview()){
            copyOperator.setClipbord(true);
        }else{
            copyOperator.setClipbord(false);
        }
        
        // add by wangb 2017/09/07 病历中内容复制时加入当前病历的病案号，以便使用粘贴功能时判断是否跨病患粘贴 STRAT
        String mrNo = CopyOperator.getPasteSign();
        if (mrNo != null && mrNo.contains(CopyOperator.MR_NO_SIGN)) {
			if (!mrNo.equals(getPM().getMrNo() + CopyOperator.MR_NO_SIGN)) {
				mrNo = getPM().getMrNo() + CopyOperator.MR_NO_SIGN;
				CopyOperator.setPasteSign(getPM().getMrNo()
						+ CopyOperator.MR_NO_SIGN);
			}
        	Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            Transferable contents = clipboard.getContents(null);
            DataFlavor flavor = DataFlavor.stringFlavor;
            try {
    			String strContents = (String) contents.getTransferData(flavor);
    			if (mrNo.length() > 0) {
    				strContents = mrNo + strContents;
    				StringTool.setClipboard(strContents);
    			}
    		} catch (UnsupportedFlavorException e) {
    			e.printStackTrace();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        }
		// add by wangb 2017/09/07 病历中内容复制时加入当前病历的病案号，以便使用粘贴功能时判断是否跨病患粘贴 END
        return true;
    }

    /**
     * 剪切
     * @return boolean
     */
    public boolean onCut() {
        EImage image = getFocusImage();
        if (image != null) {
            image.exeAction(new EAction(EAction.CUT));
            return true;
        }
        onCopy();
        onDelete();
        return true;
    }

    /**
     * 粘贴
     * @return boolean
     */
    public boolean onPaste() {
        //不能编辑
        if (!canEdit()) {
            return false;
        }
        EImage image = getFocusImage();
        if (image != null) {
            image.exeAction(new EAction(EAction.PASTE));
            return true;
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        DataFlavor flavor = DataFlavor.stringFlavor;
        DataFlavor imageFlavor = DataFlavor.imageFlavor;
        
        // add by wangb 2017/09/07 加入粘贴控制，不能跨病患粘贴 STRAT
        String targetMrNo = CopyOperator.getPasteSign();
        if (targetMrNo != null && targetMrNo.contains(CopyOperator.MR_NO_SIGN)) {
			if (!targetMrNo.equals(getPM().getMrNo() + CopyOperator.MR_NO_SIGN)) {
				targetMrNo = getPM().getMrNo() + CopyOperator.MR_NO_SIGN;
				CopyOperator.setPasteSign(getPM().getMrNo()
						+ CopyOperator.MR_NO_SIGN);
			}
        	try {
				String copyStr = (String) contents.getTransferData(flavor);
				if (copyStr.contains(CopyOperator.MR_NO_SIGN)) {
					String sourceMrNo = copyStr.substring(0, copyStr
							.indexOf(CopyOperator.MR_NO_SIGN)
							+ CopyOperator.MR_NO_SIGN.length());
					// 剪切板中的病案号如果和当前操作病历的病案号不同，则不允许粘贴
					if (!targetMrNo.equals(sourceMrNo)) {
						return false;
					}
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        // add by wangb 2017/09/07 加入粘贴控制，不能跨病患粘贴 END
        
        //$$====== modified by lx 2011-09-05 复制结构化模版的控件START=========== $$//
        //存在控件复制对象，则粘贴控件
        if (CopyOperator.getComList() != null &&
            CopyOperator.getComList().size() > 0) {
            pasteComponent();
        } else {
            if (contents.isDataFlavorSupported(flavor)) {
                try {
                    String s = (String) contents.getTransferData(flavor);
                    if (s == null || s.length() == 0) {
                        return false;
                    }
                    pasteString(s);
                    update();
                    showCursor();
                    return true;
                } catch (Exception e) {
                }
            }

        }
        //$$====== modified by lx 2011-09-05 复制结构化模版的控件END=========== $$//
        if (contents.isDataFlavorSupported(imageFlavor)) {
            try {
                Object obj = contents.getTransferData(imageFlavor);
                BufferedImage bufferimage = (BufferedImage) obj;
                if (obj == null) {
                    return false;
                }
                imsertPricture(bufferimage);
                return true;
            } catch (Exception e) {

            }
        }
        return true;
    }

    /**
     * 删除动作
     * @return boolean
     */
    public boolean onDelete() {
        //删除选蓝
        if (!delete(1)) {
            //删除行
            ETR tr = getFocusTR();
            if (tr == null) {
                return false;
            }
            tr.deleteTR();
            return false;
        }
        update();
        showCursor();
        return true;
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
     * 设置选中对象
     * @param selectedModel CSelectedModel
     */
    public void setSelectedModel(CSelectedModel selectedModel) {
        this.selectedModel = selectedModel;
    }

    /**
     * 得到选中对象
     * @return CSelectedModel
     */
    public CSelectedModel getSelectedModel() {
        return selectedModel;
    }

    /**
     * 设置焦点
     * @param type int
     * @param focus EComponent
     */
    public void setFocus(int type, EComponent focus) {
        setFocus(type, focus, -1);
    }

    /**
     * 设置文本焦点
     * @param text EText
     */
    public void setFocusText(EText text) {
        setFocusText(text, 0);
    }

    /**
     * 设置文本焦点
     * @param text EText
     * @param index int
     */
    public void setFocusText(EText text, int index) {
        setFocus(1, text, index);
    }

    /**
     * 设置图片焦点
     * @param pic EPic
     */
    public void setFocusPic(EPic pic) {
        setFocus(4, pic);
    }

    /**
     * 设置图片编辑区焦点
     * @param image EImage
     */
    public void setFocusImage(EImage image) {
        setFocus(5, image);
    }

    /**
     * 选中表格
     * @param tr ETR
     */
    public void setFocusTR(ETR tr) {
        setFocus(2, tr);
    }

    /**
     * 设置表格行回车焦点
     * @param tr ETR
     */
    public void setFocusTRE(ETR tr) {
        setFocus(3, tr);
    }

    /**
     * 设置焦点
     * @param type int
     * @param focus EComponent
     * @param index int
     */
    public void setFocus(int type, EComponent focus, int index) {
        getSelectedModel().setFocusType(type);
        getSelectedModel().setFocus(focus);
        getSelectedModel().setFocusIndex(index);
    }

    /**
     * 得到焦点类型
     * @return int
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     * 4 EPic
     */
    public int getFocusType() {
        return getSelectedModel().getFocusType();
    }

    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getFocus() {
        return getSelectedModel().getFocus();
    }

    /**
     * 得到焦点Text
     * @return EText
     */
    public EText getFocusText() {
        if (getFocusType() != 1) {
            return null;
        }
        return (EText) getFocus();
    }

    /**
     * 得到焦点TR
     * @return ETR
     */
    public ETR getFocusTR() {
        if (getFocusType() != 2) {
            return null;
        }
        return (ETR) getFocus();
    }

    /**
     * 得到焦点Image
     * @return EImage
     */
    public EImage getFocusImage() {
        if (getFocusType() != 5) {
            return null;
        }
        return (EImage) getFocus();
    }

    /**
     * 得到表格行回车焦点
     * @return ETR
     */
    public ETR getFocusTRE() {
        if (getFocusType() != 3) {
            return null;
        }
        return (ETR) getFocus();
    }

    /**
     * 设置焦点位置
     * @param index int
     */
    public void setFocusIndex(int index) {
        getSelectedModel().setFocusIndex(index);
    }

    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex() {
        return getSelectedModel().getFocusIndex();
    }

    /**
     * 设置开始选中
     */
    public void setStartSelected() {
        getSelectedModel().setStartSelected();
    }

    /**
     * 设置结束选中
     */
    public void setEndSelected() {
        getSelectedModel().setEndSelected();
    }

    /**
     * 清空选蓝
     */
    public void clearSelected() {
        getSelectedModel().clearSelected();
    }

    /**
     * 是否选中
     * @return boolean
     */
    public boolean isSelected() {
        return getSelectedModel().isSelected();
    }

    /**
     * 压制选蓝列表
     */
    public void putSelected() {
        getSelectedModel().putSelected();
    }

    /**
     * 得到焦点面板
     * @return TList
     */
    public TList getFocusPanels() {
        return getSelectedModel().getFocusPanels();
    }

    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action) {
        getSelectedModel().exeAction(action);
    }

    /**
     * 得到焦点层索引
     * @return String
     */
    public String getFocusLayerString() {
        return getSelectedModel().getFocusLayerString();
    }

    /**
     * 焦点是否在该面板中
     * @param panel EPanel
     * @return boolean
     */
    public boolean focusInPanel(EPanel panel) {
        if (panel == null) {
            return false;
        }
        return getFocusLayerString().startsWith(panel.getIndexString());
    }

    /**
     * 合并单元格
     * @return boolean
     */
    public boolean onUniteTD() {
        if (!getSelectedModel().canUniteTD()) {
            return false;
        }
        int startRow = getSelectedModel().getStartRow();
        int startColumn = getSelectedModel().getStartColumn();
        int endRow = getSelectedModel().getEndRow();
        int endColumn = getSelectedModel().getEndColumn();
        ETable table = null;
        if (getSelectedModel().getSelectedList() == null ||
            getSelectedModel().getSelectedList().size() == 0) {
            ETR tr = getSelectedModel().getFocusTR();
            if (tr == null) {
                return false;
            }
            startRow = tr.getRow();
            endRow = tr.getRow();
            startColumn = 0;
            endColumn = tr.size();
            table = tr.getTable().getHeadTable();
        } else {
            Object obj = getSelectedModel().getSelectedList().get(0);
            if (obj instanceof CSelectTDModel) {
                table = ((CSelectTDModel) obj).getTD().getTable().getHeadTable();
            } else if (obj instanceof CSelectTRModel) {
                table = ((CSelectTRModel) obj).getTR().getTable().getHeadTable();
            } else {
                return false;
            }
        }
        if (table == null) {
            return false;
        }
        if (endColumn < startColumn) {
            int temp = endColumn;
            endColumn = startColumn;
            startColumn = temp;
        }
        return table.uniteTD(startRow, startColumn, endRow, endColumn);
    }

    /**
     * 粘贴字符串
     * @param s String
     * @return boolean
     */
    public boolean pasteString(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        //如果存在选蓝先清除
        if (isSelected()) {
            delete(1);
        }
        
        // add by wangb 2017/09/08 去掉粘贴内容中的病案号注记
        if (s.contains(CopyOperator.MR_NO_SIGN)) {
			s = s.substring(s.indexOf(CopyOperator.MR_NO_SIGN)
					+ CopyOperator.MR_NO_SIGN.length());
        }
        
        s = StringTool.deleteChar(s, '\r');
        EText text = getFocusText();
        if (text == null) {
            return false;
        }
        text.pasteString(getFocusIndex(), s,null);
        if (s.endsWith("\n")) {
            EText t = getFocusText();
            if (t != null) {
                t.onFocusToRight();
            }
        }
        return true;
    }
    
    /**
     * 粘贴字符串
     * @param s String
     * @return boolean
     */
    public boolean pasteString(String s,DStyle defineStyle) {
        if (s == null || s.length() == 0) {
            return false;
        }
        //如果存在选蓝先清除
        if (isSelected()) {
            delete(1);
        }
        s = StringTool.deleteChar(s, '\r');
        EText text = getFocusText();
        if (text == null) {
            return false;
        }
        //text.setStyle(defineStyle);
        //text.setFont(defineStyle.getFont());
       /* System.out.println("====text====="+text);
        System.out.println("====font name====="+defineStyle.getFont().getFamily());
        System.out.println("====font size====="+defineStyle.getFont().getSize());*/
        
        text.pasteString(getFocusIndex(), s,defineStyle);
        
        if (s.endsWith("\n")) {
            EText t = getFocusText();
            if (t != null) {
                t.onFocusToRight();
            }
        }
        return true;
    }
    

    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
     * @return EComponent
     */
    public EComponent findObject(String name, int type) {
        return getPageManager().findObject(name, type);
    }

    /**
     * 查找抓取对象
     * @param name String
     * @return ECapture
     */
    public ECapture findCapture(String name) {
        return (ECapture) findObject(name, EComponent.CAPTURE_TYPE);
    }

    /**
     * 焦点在抓取框内
     * @param name String
     * @return boolean
     */
    public boolean focusInCaptue(String name) {
        ECapture capture = findCapture(name);
        if (capture == null) {
            return false;
        }
        return capture.focusInCaptue();
    }

    /**
     * 插入图片
     * @param image BufferedImage
     * @return EMacroroutine
     */
    public EMacroroutine imsertPricture(BufferedImage image) {
        if (image == null) {
            return null;
        }
        getPM().getImageManager().add(image);
        int imageIndex = getPM().getImageManager().size() - 1;
        EMacroroutine macroroutine = insertMacroroutine("图片", "", 1);
        macroroutine.getModel().setLockSize(true);
        macroroutine.getModel().setWidth((int) (image.getWidth() * 0.75));
        macroroutine.getModel().setHeight((int) (image.getHeight() * 0.75));
        macroroutine.setIsPic(true);
        MV mv = new MV();
        macroroutine.getModel().getMVList().add(mv);
        mv.setName("图层");
        VPic pic = mv.addPic(0, 0, 0, 0);
        pic.setName("图片");
        pic.setX2B(true);
        pic.setY2B(true);
        pic.setIconIndex(imageIndex);
        //pic.setIcon(new ImageIcon(image));
        pic.setScale(0.75);
        pic.setPictureName("");
        pic.setAutoScale(true);
        pic.getMV().removeAll();
        //更新
        update();
        return macroroutine;
    }

    /**
     * 插入图片
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture() {
        Object[] value = (Object[]) getPM().getUI().openDialog(
                "%ROOT%\\config\\database\\PictureEditDialog.x");
        if (value == null) {
            return null;
        }
        //插入宏
        EMacroroutine macroroutine = insertMacroroutine(TypeTool.getString(
                value[0]), "", 1);
        macroroutine.getModel().setLockSize(true);
        macroroutine.getModel().setWidth(TypeTool.getInt(value[2]));
        macroroutine.getModel().setHeight(TypeTool.getInt(value[3]));
        macroroutine.setIsPic(true);
        MV mv = new MV();
        macroroutine.getModel().getMVList().add(mv);
        mv.setName("图层");
        VPic pic = mv.addPic(0, 0, 0, 0);
        pic.setName("图片");
        pic.setX2B(true);
        pic.setY2B(true);
        pic.setPictureName(TypeTool.getString(value[1]));
        pic.setAutoScale(true);
        if (value.length >= 5) {
            pic.setIcon((Icon) value[4]);
        }
        //更新
        update();
        return macroroutine;
    }
    /**
     *
     * @param value Object[]0 路径,1 name, 2 width,3 height
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture(Object[] value) {
        //插入宏
        EMacroroutine macroroutine = insertMacroroutine(TypeTool.getString(
                value[0]), "", 1);
        macroroutine.getModel().setLockSize(true);
        macroroutine.getModel().setWidth(TypeTool.getInt(value[2]));
        macroroutine.getModel().setHeight(TypeTool.getInt(value[3]));
        macroroutine.setIsPic(true);
        MV mv = new MV();
        macroroutine.getModel().getMVList().add(mv);
        mv.setName("图层");
        VPic pic = mv.addPic(0, 0, 0, 0);
        pic.setName("图片");
        pic.setX2B(true);
        pic.setY2B(true);
        pic.setPictureName(TypeTool.getString(value[1]));
        pic.setAutoScale(true);
        if (value.length >= 5) {
            pic.setIcon((Icon) value[4]);
        }
        //更新
        update();
        return macroroutine;

    }

    /**
     * 插入文件
     * @param path String
     * @param fileName String
     * @param type int
     * @param state boolean
     * @return boolean
     * 例如        onInsertFile("%ROOT%\\config\\prt","a1.jhw",1,false);
     */
    public boolean onInsertFile(String path, String fileName, int type,
                                boolean state) {
        if (!canEdit()) {
            return false;
        }
        //新建管理器
        PM pm = new PM();
        pm.setUI(getUI());
        if (!pm.getFileManager().onOpen(path, fileName, type, state)) {
            return false;
        }
        insertFile(pm);
        update();
        getViewManager().resetSize();
        return true;
    }

    /**
     * 插入文件对话框
     */
    public void onInsertFileDialog() {
        if (!canEdit()) {
            return;
        }
        PM pm = new PM();
        pm.setUI(getUI());
        if (!pm.getFileManager().onOpenDialog()) {
            return;
        }
        insertFile(pm);
        update();
    }

    /**
     * 插入文件
     * @param pm PM
     */
    public void insertFile(PM pm) {
        //分割
        if (!separatePanel()) {
            return;
        }
        EComponent component = getFocus();

        if (component == null || !(component instanceof EText)) {
            return;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EPanel panel = null;
        //整理
        pm.getPageManager().arrangement();

        int count = pm.getPageManager().size();
        for (int i = 0; i < count; i++) {
            EPage page = pm.getPageManager().get(i);
            for (int j = 0; j < page.size(); j++) {
                EPanel otherPanel = page.get(j);

                if (panel == null) {
                    panel = ePanel.newPreviousPanel();
                } else {
                    panel = panel.newNextPanel();
                }
                panel.pastePanel(otherPanel);
            }
        }
    }

    /**
     * 插入图片编辑
     * @return EImage
     */
    public EImage insertImage() {
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        if (eText.getLength() > 0 && getFocusIndex() > 0) {
            eText.onEnter();
            eText = (EText) getFocus();
        }
        eText.onEnter();
        eText = (EText) getFocus();
        eText.onFocusToLeft();

        eText = (EText) getFocus();
        EPanel ePanel = eText.getPanel();
        ePanel.remove(0);
        EImage image = ePanel.newImage(eText.findIndex());
        image.initNew();
        image.setModify(true);
        return image;
    }

    /**
     * 设置插入图片块动作
     * @param insertImageAction int
     */
    public void setInsertImageAction(int insertImageAction) {
        this.insertImageAction = insertImageAction;
    }

    /**
     * 得到插入图片块动作
     * @return int
     */
    public int getInsertImageAction() {
        return insertImageAction;
    }

    /**
     * 弹出菜单
     * @param syntax String 语法
     * @param messageIO DMessageIO 监听对象
     */
    public void popupMenu(String syntax, DMessageIO messageIO) {
        if (syntax == null || syntax.length() == 0) {
            return;
        }
        TPopupMenu popup = TPopupMenu.createPopupMenu(syntax);
        popup.setMessageIO(messageIO);
        popup.show(getUI().getTCBase(), getUI().getMouseX(), getUI().getMouseY());
    }

    /**
     * 添加下拉框
     * @param name String
     * @param text String
     * @return ETextFormat
     */
    public ETextFormat insertTextFormat(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        ETextFormat eTextFormat = ePanel.newTextFormat(eText.findIndex());
        eTextFormat.setBlock(eText.getStart(), eText.getStart());
        eTextFormat.setText(text);
        eTextFormat.setName(name);
        return eTextFormat;
    }


    /**
     * 添加一个新文本
     * @param focusText 当前焦点文本
     * @return
     */
    public EText addText(EText focusText) {

    	//分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }

        EText eText = (EText) component;

        //2
        String str = eText.getString();
        if( null!=str && !str.equals("") ){

        	DStyle ds = eText.getStyle().copy();
        	ds.setIsDeleteLine(false);

        	//
        	ds.setColor(Color.BLACK);

        	//
			int mindex = this.getPM().getModifyNodeManager().getIndex();
			if ( !ds.isLine() && mindex > -1 ) {
				 ds.setIsLine(true);
				 ds.setLineCount(   mindex+1 );

				 //
				 if( ds.findIndex() ==-1 ){
					 this.getPM().getStyleManager().add(ds);
				 }
			}

            //
            EText newT = eText.insertText(ds);

            newT.setRowID( focusText.getRowID() );
            newT.setMaxWidth( focusText.getMaxWidth() );
            newT.setMaxHeight( focusText.getMaxHeight() );

            return newT;
        }

        //1
        eText.setRowID( focusText.getRowID() );
        eText.setMaxWidth( focusText.getMaxWidth() );
        eText.setMaxHeight( focusText.getMaxHeight() );
        eText.setIsSave(null);

        return eText;
    }

    /**
     * 粘贴控件功能
     */
    private void pasteComponent() {
        List components = CopyOperator.getComList();
        insertComponents(components,null);
    }

    /**
     * 当前焦点下插入元件
     * @param components List
     */
    public void insertComponents(List components,DStyle defineStyle) {
        for (int i = 0; i < components.size(); i++) { 
            Object com = components.get(i);
            String componentClass = com.getClass().toString();           

            //固定文本
            if (componentClass.equals( EFixed.class.toString()) ) {
            	EFixed ef = (EFixed) com;
            	//是删除的内容跳过
            	if(ef.getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                EFixed obj = this.insertFixed( ef.getName(), ef.getText() );
                obj.setGroupName( ef.getGroupName());
                obj.setMicroName( ef.getMicroName());
                obj.setDataElements( ef.isIsDataElements());
                obj.setTryFileName( ef.getTryFileName());
                obj.setTryName( ef.getTryName());
                obj.setAllowNull( ef.isAllowNull());
                if(defineStyle!=null){
	                obj.setStyle(defineStyle);
	                obj.setFont( defineStyle.getFont());
                }else{
                	obj.setStyle( ef.getStyle());
                    obj.setFont( ef.getStyle().getFont());
                }
                obj.setElementContent( ef.isIsElementContent());
                //
                obj.setExpression( ef.isExpression());
                obj.setExpressionDesc( ef.getExpressionDesc());
                obj.setScriptUp( ef.getScriptUp() );
                obj.setScriptDown( ef.getScriptDown() );

                obj.onFocusToRight();
                update();
            }

            //纯文本
            else if (componentClass.equals( EText.class.toString() )) {
            	//System.out.println("EText===="+((EText) com).getBlockValue());
            	//是删除的内容跳过
            	if(((EText) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//            	
            	if(defineStyle!=null){
	                this.getPM().getFocusManager().pasteString(((EText) com).
	                        getBlockValue(),defineStyle);
            	}else{
            		this.getPM().getFocusManager().pasteString(((EText) com).
	                        getBlockValue());
            	}
                update();
            }

            //单选
            else if (componentClass.equals( ESingleChoose.class.toString() )) {
            	//是删除的内容跳过
            	if(((ESingleChoose) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	// 
                ESingleChoose obj = this.insertSingleChoose(((ESingleChoose)
                        com).getName(), ((ESingleChoose) com).getText());
                obj.setGroupName(((ESingleChoose) com).getGroupName());
                obj.setData(((ESingleChoose) com).getData());
                obj.setAllowNull(((ESingleChoose) com).isAllowNull());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
            	}else{
            		 obj.setStyle(((ESingleChoose) com).getStyle());
                     obj.setFont(((ESingleChoose) com).getStyle().getFont());
            	}               
                obj.setElementContent(((ESingleChoose) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //多选
            else if (componentClass.equals( EMultiChoose.class.toString())) {
            	//是删除的内容跳过
            	if(((EMultiChoose) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	// 
                EMultiChoose obj = this.insertMultiChoose(((EMultiChoose) com).
                        getName(), ((EMultiChoose) com).getText());
                obj.setGroupName(((EMultiChoose) com).getGroupName());
                obj.setData(((EMultiChoose) com).getData());
                obj.setAllowNull(((EMultiChoose) com).isAllowNull());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((EMultiChoose) com).getStyle());
                    obj.setFont(((EMultiChoose) com).getStyle().getFont());
                }
                
                obj.setElementContent(((EMultiChoose) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //有无选择
            else if (componentClass.equals( EHasChoose.class.toString())) {
            	//是删除的内容跳过
            	if(((EHasChoose) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                EHasChoose obj = this.insertHasChoose(((EHasChoose) com).
                        getName(), ((EHasChoose) com).getText());
                obj.setGroupName(((EHasChoose) com).getGroupName());
                obj.setData(((EHasChoose) com).getData());
                obj.setAllowNull(((EHasChoose) com).isAllowNull());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((EHasChoose) com).getStyle());
                    obj.setFont(((EHasChoose) com).getStyle().getFont());
                }
                
                obj.setElementContent(((EHasChoose) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //宏
            else if (componentClass.equals( EMicroField.class.toString())) {
            	//是删除的内容跳过
            	if(((EMicroField) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                EMicroField obj = this.insertMicroField(((EMicroField) com).
                        getName(), ((EMicroField) com).getText());
                obj.setGroupName(((EMicroField) com).getGroupName());
                obj.setCode(((EMicroField) com).getCode());
                obj.setAllowNull(((EMicroField) com).isAllowNull());
                obj.setDataElements(((EMicroField) com).isIsDataElements());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((EMicroField) com).getStyle());
 	                obj.setFont(((EMicroField) com).getStyle().getFont());
                }
                //
                obj.setMicroName(((EMicroField) com).getMicroName());
                obj.setElementContent(((EMicroField) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //输入提示组件
            else if (componentClass.equals( EInputText.class.toString())) {
            	//是删除的内容跳过
            	if(((EInputText) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                EInputText obj = this.insertInputText(((EInputText) com).
                        getName(), ((EInputText) com).getText());
                obj.setGroupName(((EInputText) com).getGroupName());
                obj.setAllowNull(((EInputText) com).isAllowNull());
                obj.setMicroName(((EInputText) com).getMicroName());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((EInputText) com).getStyle());
                    obj.setFont(((EInputText) com).getStyle().getFont());
                }
                //
                obj.setDataElements(((EInputText) com).isIsDataElements());
                obj.setElementContent(((EInputText) com).isIsElementContent());
                obj.onFocusToRight();
                update();

            }

            //抓取框
            else if (componentClass.equals( ECapture.class.toString())) {
            	//是删除的内容跳过
            	if(((ECapture) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                ECapture obj = this.insertCaptureObject(((ECapture) com).
                        getName(), 0);
                obj.setCaptureType(((ECapture) com).getCaptureType());
                obj.setGroupName(((ECapture) com).getGroupName());
                obj.setMicroName(((ECapture) com).getMicroName());
                obj.setDataElements(((ECapture) com).isIsDataElements());
                obj.setAllowNull(((ECapture) com).isAllowNull());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((ECapture) com).getStyle());
                    obj.setFont(((ECapture) com).getStyle().getFont());
                }
                
                obj.setElementContent(((ECapture) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //选择框
            else if (componentClass.equals( ECheckBoxChoose.class.toString())) {
            	//是删除的内容跳过
            	if(((ECheckBoxChoose) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                ECheckBoxChoose obj = this.insertCheckBoxChoose(((
                        ECheckBoxChoose) com).getName(),
                        ((ECheckBoxChoose) com).getText(), false);
                obj.setGroupName(((ECheckBoxChoose) com).getGroupName());
                obj.setAllowNull(((ECheckBoxChoose) com).isAllowNull());
                obj.setDataElements(((ECheckBoxChoose) com).isIsDataElements());
                obj.setChecked(((ECheckBoxChoose) com).isChecked());
                obj.setEnabled(((ECheckBoxChoose) com).isEnabled());
                obj.setInLeft(((ECheckBoxChoose) com).inLeft());
                obj.setCheckedScript(((ECheckBoxChoose) com).getCheckedScript());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((ECheckBoxChoose) com).getStyle());
                    obj.setFont(((ECheckBoxChoose) com).getStyle().getFont());
                }
                
                obj.setElementContent(((ECheckBoxChoose) com).
                                      isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //数字选择
            else if (componentClass.equals( ENumberChoose.class.toString())) {
            	//是删除的内容跳过
            	if(((ENumberChoose) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                ENumberChoose obj = this.insertNumberChoose(((ENumberChoose)
                        com).getName(), ((ENumberChoose) com).getText());
                obj.setGroupName(((ENumberChoose) com).getGroupName());
                obj.setNumberType(((ENumberChoose) com).getNumberType());
                obj.setNumberStep(((ENumberChoose) com).getNumberStep());
                obj.setCheckMaxValue(((ENumberChoose) com).isCheckMaxValue());
                obj.setCheckMinValue(((ENumberChoose) com).isCheckMinValue());
                obj.setNumberMaxValue(((ENumberChoose) com).getNumberMaxValue());
                obj.setNumberMinValue(((ENumberChoose) com).getNumberMinValue());
                obj.setCanInputNegativeNumber(((ENumberChoose) com).
                                              canInputNegativeNumber());
                obj.setNumberFormat(((ENumberChoose) com).getNumberFormat());
                obj.setEnabled(((ENumberChoose) com).isEnabled());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((ENumberChoose) com).getStyle());
                    obj.setFont(((ENumberChoose) com).getStyle().getFont());
                }
                //
                obj.setElementContent(((ENumberChoose) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //下拉框
            else if (componentClass.equals( ETextFormat.class.toString())) {
            	//是删除的内容跳过
            	if(((ETextFormat) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                ETextFormat obj = this.insertTextFormat(((ETextFormat) com).
                        getName(), ((ETextFormat) com).getText());
                obj.setGroupName(((ETextFormat) com).getGroupName());
                obj.setAllowNull(((ETextFormat) com).isAllowNull());
                obj.setDataElements(((ETextFormat) com).isIsDataElements());
                obj.setCode(((ETextFormat) com).getCode());
                obj.setData(((ETextFormat) com).getData());
                obj.setMicroName(((ETextFormat) com).getMicroName());
                if(defineStyle!=null){
                	obj.setStyle(defineStyle);
                	obj.setFont(defineStyle.getFont());
                }else{
                	obj.setStyle(((ETextFormat) com).getStyle());
                    obj.setFont(((ETextFormat) com).getStyle().getFont());
                }
                //
                obj.setElementContent(((ETextFormat) com).isIsElementContent());
                obj.onFocusToRight();
                update();
            }

            //关联项
            else if (componentClass.equals( EAssociateChoose.class.toString())) {
            	//是删除的内容跳过
            	if(((EAssociateChoose) com).getStyle().isDeleteLine()){
            		continue;
            	}
            	//
                EAssociateChoose obj = this.insertAssociateChoose(((
                        EAssociateChoose)
                        com).getName(), ((EAssociateChoose) com).getText());
                obj.setGroupName(((EAssociateChoose) com).getGroupName());
                obj.setStartTag(((EAssociateChoose) com).getStartTag());
                obj.setEndTag(((EAssociateChoose) com).getEndTag());
                obj.setData(((EAssociateChoose) com).getData());
                obj.setAllowNull(((EAssociateChoose) com).isAllowNull());
                if(defineStyle!=null){
                	obj.setStyle(((EAssociateChoose) com).getStyle());
                    obj.setFont(((EAssociateChoose) com).getStyle().getFont());
                }else{
                	obj.setStyle(((ETextFormat) com).getStyle());
                    obj.setFont(((ETextFormat) com).getStyle().getFont());
                }
                //
                obj.setElementContent(((EAssociateChoose) com).
                                      isIsElementContent());
                obj.onFocusToRight();
                update();

            }

            //
            else if (com.getClass().toString().equals( String.class.toString())) {
                //加入换行
                if (((String) com).equals(CSelectPanelEnterModel.NEW_LINE)) {
                    this.getPM().getFocusManager().pasteString("\r\n");
                }
            }
        }

    }

    /**
     * 插入关联元件
     * @param name String
     * @param text String
     * @return EAssociateChoose
     */
    public EAssociateChoose insertAssociateChoose(String name, String text) {
        //分割
        if (!separate()) {
            return null;
        }
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        EPanel ePanel = eText.getPanel();
        EAssociateChoose associateChoose = ePanel.newAssociateChoose(eText.
                findIndex());
        associateChoose.setBlock(eText.getStart(), eText.getStart());
        associateChoose.setText(text);
        associateChoose.setName(name);
        return associateChoose;
    }

    /**
     * 粘贴剪切板
     * @return boolean
     */
    public boolean onTextPaste() {
    	 //不能编辑
        if (!canEdit()) {
            return false;
        }
        EImage image = getFocusImage();
        if (image != null) {
            image.exeAction(new EAction(EAction.PASTE));
            return true;
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable contents = clipboard.getContents(null);
        DataFlavor flavor = DataFlavor.stringFlavor;
        DataFlavor imageFlavor = DataFlavor.imageFlavor;
        
        // add by wangb 2017/09/07 加入粘贴控制，不能跨病患粘贴 STRAT
        String targetMrNo = CopyOperator.getPasteSign();
        if (targetMrNo != null && targetMrNo.contains(CopyOperator.MR_NO_SIGN)) {
			if (!targetMrNo.equals(getPM().getMrNo() + CopyOperator.MR_NO_SIGN)) {
				targetMrNo = getPM().getMrNo() + CopyOperator.MR_NO_SIGN;
				CopyOperator.setPasteSign(getPM().getMrNo()
						+ CopyOperator.MR_NO_SIGN);
			}
        	try {
				String copyStr = (String) contents.getTransferData(flavor);
				if (copyStr.contains(CopyOperator.MR_NO_SIGN)) {
					String sourceMrNo = copyStr.substring(0, copyStr
							.indexOf(CopyOperator.MR_NO_SIGN)
							+ CopyOperator.MR_NO_SIGN.length());
					// 剪切板中的病案号如果和当前操作病历的病案号不同，则不允许粘贴
					if (!targetMrNo.equals(sourceMrNo)) {
						return false;
					}
				}
			} catch (UnsupportedFlavorException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        // add by wangb 2017/09/07 加入粘贴控制，不能跨病患粘贴 END

		if (contents.isDataFlavorSupported(flavor)) {
			try {
				String s = (String) contents.getTransferData(flavor);
				// System.out.println("=========s=========="+s);
				if (s == null || s.length() == 0) {
					return false;
				}
				pasteString(s);
				update();
				showCursor();
				return true;
			} catch (Exception e) {
			}
		}
        //$$====== modified by lx 2011-09-05 复制结构化模版的控件END=========== $$//
        if (contents.isDataFlavorSupported(imageFlavor)) {
            try {
                Object obj = contents.getTransferData(imageFlavor);
                BufferedImage bufferimage = (BufferedImage) obj;
                if (obj == null) {
                    return false;
                }
                imsertPricture(bufferimage);
                return true;
            } catch (Exception e) {

            }
        }
        return true;

    }

}
