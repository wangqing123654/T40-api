package com.dongyang.ui.base;

import com.dongyang.ui.TDPanel;
import com.dongyang.tui.DText;
import com.dongyang.ui.TShowZoomCombo;
import com.dongyang.ui.event.TComboBoxEvent;
import com.dongyang.util.TList;
import com.dongyang.util.TypeTool;
import com.dongyang.ui.TComponent;
import com.dongyang.tui.text.CSelectTDModel;
import com.dongyang.tui.text.CSelectTextBlock;
import com.dongyang.tui.text.CSelectedModel;
import com.dongyang.tui.text.DStyle;
import com.dongyang.tui.text.EFixed;
import com.dongyang.tui.text.ESign;
import com.dongyang.tui.text.ETD;
import com.dongyang.tui.text.PM;
import com.dongyang.tui.text.MFile;
import com.dongyang.tui.text.MView;
import com.dongyang.tui.text.MPage;
import com.dongyang.tui.text.MFocus;
import com.dongyang.tui.text.ESingleChoose;
import com.dongyang.tui.text.EMultiChoose;
import com.dongyang.tui.text.EHasChoose;
import com.dongyang.tui.text.EMicroField;
import com.dongyang.tui.text.EComponent;
import com.dongyang.tui.text.MMicroField;
import com.dongyang.tui.text.ETable;
import com.dongyang.tui.text.ETR;
import com.dongyang.tui.text.MEvent;
import com.dongyang.ui.TFontCombo;
import com.dongyang.ui.TFontSizeCombo;
import com.dongyang.tui.text.EInputText;
import com.dongyang.ui.TToolButton;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.wcomponent.expression.ExpressionUtil;

import java.awt.Font;
import java.awt.event.ActionEvent;
import com.dongyang.tui.text.EText;
import com.dongyang.tui.text.ECapture;
import com.dongyang.tui.text.EPic;
import com.dongyang.tui.text.ECheckBoxChoose;
import com.dongyang.tui.text.MSyntax;
import com.dongyang.tui.text.EMacroroutine;
import com.dongyang.tui.text.ENumberChoose;
import com.dongyang.tui.text.EImage;
import java.util.ArrayList;
import java.util.List;
import com.dongyang.tui.DMessageIO;
import com.dongyang.tui.text.ETextFormat;
import com.dongyang.tui.text.EAssociateChoose;


/**
 *
 * <p>Title: word组件</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.5.26
 * @author whao 2013~
 * @version 1.0
 */
public class TWordBase extends TDPanel {

    private DText dText;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 显示比例缩放拉列表框
     */
    private TShowZoomCombo showZoomCombo;
    /**
     * 字体拉列表框
     */
    private TFontCombo fontCombo;
    /**
     * 字体拉列表框
     */
    private TFontSizeCombo fontSizeCombo;
    /**
     * 字体粗体按钮
     */
    private TToolButton fontBoldButton;
    /**
     * 字体斜体按钮
     */
    private TToolButton fontItalicButton;
    /**
     * 左对其方式按钮
     */
    private TToolButton alignmentLeftButton;
    /**
     * 中对其方式按钮
     */
    private TToolButton alignmentCenterButton;
    /**
     * 右对其方式按钮
     */
    private TToolButton alignmentRightButton;


    public TWordBase() {
        //System.out.println("==TWordBase default==");
        dText = new DText();
        dText.setTag("DTEXT");
        dText.setBorder("凹");
        dText.setAutoBaseSize(true);
        addDComponent(dText);
        getEventManager().getIOParm().addListener("CLICK_TEXT", this,
                                                  "onWordClickText");
    }

    /**
     * 得到Word面板
     * @return DText
     */
    public DText getWordText() {
        return dText;
    }

    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM() {
        return getWordText().getPM();
    }

    /**
     * 得到文件管理器
     * @return MFile
     */
    public MFile getFileManager() {
        return getPM().getFileManager();
    }

    /**
     * 得到试图管理器
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
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
     * 得到事件管理器
     * @return MEvent
     */
    public MEvent getEventManager() {
        return getPM().getEventManager();
    }

    /**
     * 得到插入宏管理器
     * @return MMicroField
     */
    public MMicroField getMicroFieldManager() {
        return getPM().getMicroFieldManager();
    }

    /**
     * 得到语法管理器
     * @return MSyntax
     */
    public MSyntax getSyntaxManager() {
        return getPM().getSyntaxManager();
    }

    /**
     * 设置文件名
     * @param fileName String
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
        if (fileName == null || fileName.length() == 0) {
            return;
        }
        getFileManager().onOpen(fileName);
    }

    /**
     * 得到文件名
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 打开文件
     * @param path String 路径
     * @param fileName String 文件名
     * @param type int 类型
     * 0 本地
     * 1 AppServer
     * 2 FileServer Emr模板文件
     * 3 FileServer Emr数据文件
     * @param state boolean true 阅览 false 编辑
     * @return boolean
     */
    public boolean onOpen(String path, String fileName, int type, boolean state) {

    	//初始化
    	this.expressionList.clear();
    	this.setNodeIndex(-1);
    	//
        if (!getFileManager().onOpen(path, fileName, type, state)) {
        	//泰心专用
        	//this.fileName = getFileManager().getPath() +
            //getFileManager().getFileName();
            return false;
        }
        this.fileName = getFileManager().getPath() +
                        getFileManager().getFileName();
        return true;
    }

    /**
     * 保存
     * @return boolean true 成功 false 失败
     */
    public boolean onSave() {
        return getFileManager().onSave();
    }

    /**
     * 另存为
     * @param path String
     * @param fileName String
     * @param type int
     * @return boolean
     */
    public boolean onSaveAs(String path, String fileName, int type) {
        return getFileManager().onSaveAs(path, fileName, type);
    }

    /**
     * 另存为
     * @param path String
     * @param fileName String
     * @return boolean true 成功 false 失败
     */
    public boolean onSaveAs(String path, String fileName) {
        return getFileManager().onSaveAs(path, fileName);
    }

    /**
     * 设置阅览状态
     * @param isPreview boolean
     */
    public void setPreview(boolean isPreview) {
        getWordText().setPreview(isPreview);
    }

    /**
     * 是否是阅览状态
     * @return boolean
     */
    public boolean isPreview() {
        return getWordText().isPreview();
    }

    /**
     * 设置缩放尺寸(字符参数)
     * @param zoom String
     */
    public void setZoomString(String zoom) {
        getViewManager().setZoomString(zoom);
    }

    /**
     * 得到缩放尺寸(字符参数)
     * @return String
     */
    public String getZoomString() {
        return getViewManager().getZoomString();
    }

    /**
     * 传参
     * @param parm Object
     */
    public void setWordParameter(Object parm) {
        getFileManager().setParameter(parm);
    }

    /**
     * 打印
     */
    public void print() {
        getPageManager().print();
    }

    /**
     * 页面设置对话框
     */
    public void printDialog() {
        printDialog(1);
    }

    /**
     * 页面设置对话框
     * @param size int
     */
    public void printDialog(int size) {
        getPageManager().printDialog(size);
    }

    /**
     * 打印设置对话框
     */
    public void printSetup() {
        getPageManager().printSetup();
    }

    /**
     * 须打对话框
     */
    public void printXDDialog() {
        getPageManager().printXDDialog();
    }

    /**
     * 设置显示行号
     * @param isShowRowID boolean
     */
    public void setShowRowID(boolean isShowRowID) {
        getViewManager().setShowRowID(isShowRowID);
    }

    /**
     * 是否显示行号
     * @return boolean
     */
    public boolean isShowRowID() {
        return getViewManager().isShowRowID();
    }

    //===============================比例缩放===========================//
    /**
     * 设置显示比例缩放拉列表框的Tag
     * @param tag String
     */
    public void setShowZoomComboTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TShowZoomCombo)) {
            return;
        }
        setShowZoomCombo((TShowZoomCombo) tcomponent);
    }

    /**
     * 设置显示比例缩放拉列表框
     * @param showZoomCombo TShowZoomCombo
     */
    public void setShowZoomCombo(TShowZoomCombo showZoomCombo) {
        this.showZoomCombo = showZoomCombo;
        if (showZoomCombo == null) {
            return;
        }
        showZoomCombo.addEventListener(TComboBoxEvent.SELECTED, this,
                                       "onShowZoomComboSelectedAction");
        showZoomCombo.setValue(getZoomString());
    }

    /**
     * 显示比例缩放拉列表框改变动作
     */
    public void onShowZoomComboSelectedAction() {
        String s = TypeTool.getString(showZoomCombo.getValue());
        setZoomString(s);
        if (s != null && s.length() > 0 && !s.endsWith("%")) {
            showZoomCombo.setValue(s + "%");
        }
    }

    /**
     * 得到显示比例缩放拉列表框
     * @return TShowZoomCombo
     */
    public TShowZoomCombo getShowZoomCombo() {
        return showZoomCombo;
    }

    //===============================设置字体===========================//
    /**
     * 设置字体拉列表框Tag
     * @param tag String
     */
    public void setFontComboTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TFontCombo)) {
            return;
        }
        setFontCombo((TFontCombo) tcomponent);
    }

    /**
     * 设置字体拉列表框
     * @param fontCombo TFontCombo
     */
    public void setFontCombo(TFontCombo fontCombo) {
        this.fontCombo = fontCombo;
        if (fontCombo == null) {
            return;
        }
        fontCombo.addEventListener(TComboBoxEvent.SELECTED, this,
                                   "onModifyFontSelectedAction");
        fontCombo.setValue("宋体");
    }

    /**
     * 设置字体动作
     */
    public void onModifyFontSelectedAction() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        String s = TypeTool.getString(fontCombo.getValue());
        modifyFont(s);
    }

    /**
     * 设置字体
     * @param font String
     * @return boolean
     */
    public boolean modifyFont(String font) {
        boolean flg = getFocusManager().modifyFont(font);
        //更新
        update();
        return flg;
    }

    /**
     * 得到字体拉列表框
     * @return TFontCombo
     */
    public TFontCombo getfontCombo() {
        return fontCombo;
    }

    //===============================设置字体尺寸=======================//
    /**
     * 设置字体尺寸拉列表框Tag
     * @param tag String
     */
    public void setFontSizeComboTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TFontSizeCombo)) {
            return;
        }
        setFontSizeCombo((TFontSizeCombo) tcomponent);
    }

    /**
     * 设置字体尺寸
     * @param fontSizeCombo TFontSizeCombo
     */
    public void setFontSizeCombo(TFontSizeCombo fontSizeCombo) {
        this.fontSizeCombo = fontSizeCombo;
        if (fontSizeCombo == null) {
            return;
        }
        fontSizeCombo.addEventListener(TComboBoxEvent.SELECTED, this,
                                       "onModifyFontSizeSelectedAction");
    }

    /**
     * 设置字体尺寸动作
     */
    public void onModifyFontSizeSelectedAction() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        int size = TypeTool.getInt(fontSizeCombo.getValue());
        modifyFontSize(size);
    }

    /**
     * 设置字体尺寸
     * @param size int
     * @return boolean
     */
    public boolean modifyFontSize(int size) {
        boolean flg = getFocusManager().modifyFontSize(size);
        //更新
        update();
        return flg;
    }

    /**
     * 设置字体粗体按钮Tag
     * @param tag String
     */
    public void setFontBoldButtonTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TToolButton)) {
            return;
        }
        setFontBoldButton((TToolButton) tcomponent);
    }

    /**
     * 设置字体粗体按钮
     * @param fontBoldButton TToolButton
     */
    public void setFontBoldButton(TToolButton fontBoldButton) {
        this.fontBoldButton = fontBoldButton;
        if (fontBoldButton == null) {
            return;
        }
        fontBoldButton.addEventListener(fontBoldButton.getTag() + "->" +
                                        TActionListener.ACTION_PERFORMED, this,
                                        "onModifyFontBoldAction");
    }

    /**
     * 字体粗体按钮动作
     * @param e ActionEvent
     */
    public void onModifyFontBoldAction(ActionEvent e) {
        if (fontBoldButton == null) {
            return;
        }
        //不能编辑
        if (!canEdit()) {
            return;
        }
        fontBoldButton.setSelected(!fontBoldButton.isSelected());
        modifyBold(fontBoldButton.isSelected());
        grabFocus();
    }

    /**
     * 得到字体粗体按钮
     * @return TToolButton
     */
    public TToolButton getFontBoldButton() {
        return fontBoldButton;
    }

    /**
     * 修改字体粗体
     * @param bold boolean
     * @return boolean
     */
    public boolean modifyBold(boolean bold) {
        boolean flg = getFocusManager().modifyBold(bold);
        //更新
        update();
        return flg;
    }

    /**
     * 设置字体斜体按钮Tag
     * @param tag String
     */
    public void setFontItalicButtonTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TToolButton)) {
            return;
        }
        setFontItalicButton((TToolButton) tcomponent);
    }

    /**
     * 设置字体斜体按钮
     * @param fontItalicButton TToolButton
     */
    public void setFontItalicButton(TToolButton fontItalicButton) {
        this.fontItalicButton = fontItalicButton;
        if (fontItalicButton == null) {
            return;
        }
        fontItalicButton.addEventListener(fontItalicButton.getTag() + "->" +
                                          TActionListener.ACTION_PERFORMED, this,
                                          "onModifyFontItalicAction");
    }

    /**
     * 字体斜体按钮动作
     * @param e ActionEvent
     */
    public void onModifyFontItalicAction(ActionEvent e) {

        if (fontItalicButton == null) {
            return;
        }
        //不能编辑
        if (!canEdit()) {
            return;
        }
        fontItalicButton.setSelected(!fontItalicButton.isSelected());
        modifyItalic(fontItalicButton.isSelected());
        grabFocus();
    }

    /**
     * 得到字体斜体按钮
     * @return TToolButton
     */
    public TToolButton getFontItalicButton() {
        return fontItalicButton;
    }

    /**
     * 修改字体斜体
     * @param italic boolean
     * @return boolean
     */
    public boolean modifyItalic(boolean italic) {
        boolean flg = getFocusManager().modifyItalic(italic);
        //更新
        update();
        return flg;
    }

    /**
     * 得到字体尺寸
     * @return TFontSizeCombo
     */
    public TFontSizeCombo getFontSizeCombo() {
        return fontSizeCombo;
    }

    /**
     * 设置左对齐按钮Tag
     * @param tag String
     */
    public void setAlignmentLeftButtonTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TToolButton)) {
            return;
        }
        setAlignmentLeftButton((TToolButton) tcomponent);
    }

    /**
     * 设置左对齐按钮
     * @param alignmentLeftButton TToolButton
     */
    public void setAlignmentLeftButton(TToolButton alignmentLeftButton) {
        this.alignmentLeftButton = alignmentLeftButton;
        if (alignmentLeftButton == null) {
            return;
        }
        alignmentLeftButton.addEventListener(alignmentLeftButton.getTag() +
                                             "->" +
                                             TActionListener.ACTION_PERFORMED, this,
                                             "onModifyAlignmentLeftAction");
    }

    /**
     * 左对齐按钮动作
     * @param e ActionEvent
     */
    public void onModifyAlignmentLeftAction(ActionEvent e) {
        if (alignmentLeftButton == null) {
            return;
        }
        //不能编辑
        if (!canEdit()) {
            return;
        }
        //alignmentLeftButton.setSelected(!alignmentLeftButton.isSelected());
        setAlignment(0);
        grabFocus();
    }

    /**
     * 得到左对齐按钮
     * @return TToolButton
     */
    public TToolButton getAlignmentLeftButton() {
        return alignmentLeftButton;
    }

    /**
     * 设置中对齐按钮Tag
     * @param tag String
     */
    public void setAlignmentCenterButtonTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TToolButton)) {
            return;
        }
        setAlignmentCenterButton((TToolButton) tcomponent);
    }

    /**
     * 设置中对齐按钮
     * @param alignmentCenterButton TToolButton
     */
    public void setAlignmentCenterButton(TToolButton alignmentCenterButton) {
        this.alignmentCenterButton = alignmentCenterButton;
        if (alignmentCenterButton == null) {
            return;
        }
        alignmentCenterButton.addEventListener(alignmentCenterButton.getTag() +
                                               "->" +
                                               TActionListener.ACTION_PERFORMED, this,
                                               "onModifyAlignmentCenterAction");
    }

    /**
     * 中对齐按钮动作
     * @param e ActionEvent
     */
    public void onModifyAlignmentCenterAction(ActionEvent e) {
        if (alignmentCenterButton == null) {
            return;
        }
        //不能编辑
        if (!canEdit()) {
            return;
        }
        //alignmentLeftButton.setSelected(!alignmentLeftButton.isSelected());
        setAlignment(1);
        grabFocus();
    }

    /**
     * 得到中对齐按钮
     * @return TToolButton
     */
    public TToolButton getAlignmentCenterButton() {
        return alignmentCenterButton;
    }

    /**
     * 设置右对齐按钮Tag
     * @param tag String
     */
    public void setAlignmentRightButtonTag(String tag) {
        TComponent tcomponent = findTComponent(tag);
        if (tcomponent == null || !(tcomponent instanceof TToolButton)) {
            return;
        }
        setAlignmentRightButton((TToolButton) tcomponent);
    }

    /**
     * 设置右对齐按钮
     * @param alignmentRightButton TToolButton
     */
    public void setAlignmentRightButton(TToolButton alignmentRightButton) {
        this.alignmentRightButton = alignmentRightButton;
        if (alignmentRightButton == null) {
            return;
        }
        alignmentRightButton.addEventListener(alignmentRightButton.getTag() +
                                              "->" +
                                              TActionListener.ACTION_PERFORMED, this,
                                              "onModifyAlignmentRightAction");
    }

    /**
     * 右对齐按钮动作
     * @param e ActionEvent
     */
    public void onModifyAlignmentRightAction(ActionEvent e) {
        if (alignmentRightButton == null) {
            return;
        }
        //不能编辑
        if (!canEdit()) {
            return;
        }
        //alignmentLeftButton.setSelected(!alignmentLeftButton.isSelected());
        setAlignment(2);
        grabFocus();
    }

    /**
     * 得到右对齐按钮
     * @return TToolButton
     */
    public TToolButton getAlignmentRightButton() {
        return alignmentRightButton;
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
        //不能编辑
        if (!canEdit()) {
            return false;
        }
        boolean flg = getFocusManager().setAlignment(alignment);
        //更新
        update();
        return flg;
    }

    //===============================固定文本处理函数====================//
    /**
     * 插入固定文本
     * @return EFixed
     */
    public EFixed insertFixed() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertFixed("固定文本", "固定文本");
    }

    /**
     * 插入固定文本
     * @param name String 名称
     * @param text String 显示文字
     * @return EFixed
     */
    public EFixed insertFixed(String name, String text) {
        EFixed fixed = getFocusManager().insertFixed(name, text);
        //更新
        update();
        return fixed;
    }

    /**
     * 插入单选
     * @return ESingleChoose
     */
    public ESingleChoose insertSingleChoose() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertSingleChoose("单选", "单选");
    }

    /**
     * 插入单选
     * @param name String 名称
     * @param text String 显示文字
     * @return ESingleChoose
     */
    public ESingleChoose insertSingleChoose(String name, String text) {
        ESingleChoose singleChoose = getFocusManager().insertSingleChoose(name,
                text);
        //更新
        update();
        return singleChoose;
    }

    /**
     * 插入多选
     * @return EMultiChoose
     */
    public EMultiChoose insertMultiChoose() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertMultiChoose("多选", "多选");
    }

    /**
     * 插入多选
     * @param name String 名称
     * @param text String 显示文字
     * @return EMultiChoose
     */
    public EMultiChoose insertMultiChoose(String name, String text) {
        EMultiChoose multiChoose = getFocusManager().insertMultiChoose(name,
                text);
        //更新
        update();
        return multiChoose;
    }

    /**
     * 插入有无选择
     * @return EHasChoose
     */
    public EHasChoose insertHasChoose() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertHasChoose("有无选择", "有无选择");
    }

    /**
     * 插入有无选择
     * @param name String 名称
     * @param text String 显示文字
     * @return EHasChoose
     */
    public EHasChoose insertHasChoose(String name, String text) {
        EHasChoose hasChoose = getFocusManager().insertHasChoose(name, text);
        //更新
        update();
        return hasChoose;
    }


    /**
     * 插入输入提示组件
     * @return EInputText
     */
    public EInputText insertInputText() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertInputText("输入提示组件", "输入提示组件");
    }

    /**
     * 插入输入提示组件
     * @param name String 名称
     * @param text String 显示文字
     * @return EInputText
     */
    public EInputText insertInputText(String name, String text) {
        EInputText inputText = getFocusManager().insertInputText(name, text);
        //更新
        update();
        return inputText;
    }

    /**
     * 插入宏
     * @return EMicroField
     */
    public EMicroField insertMicroField() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertMicroField("宏", "宏");
    }

    /**
     * 插入宏
     * @param name String 名称
     * @param text String 显示文字
     * @return EMicroField
     */
    public EMicroField insertMicroField(String name, String text) {
        EMicroField microField = getFocusManager().insertMicroField(name, text);
        //更新
        update();
        return microField;
    }
    
	/**
	 * 插入签章
	 * 
	 * @param name
	 *            String 名称
	 * @param text
	 *            String 显示文字
	 * @return EMicroField
	 */
	public ESign insertSign(String groupName, String name, String signCode,
			String text, String timestmp) {
		ESign sign = getFocusManager().insertSign(groupName, name, signCode,
				text, timestmp);
		// 更新
		update();
		return sign;
	}

    /**
     * 插入抓取
     * @return ECapture
     */
    public ECapture insertCaptureObject() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertCaptureObject("抓取", 0);
    }

    /**
     * 插入抓取
     * @param name String
     * @param type int
     * @return ECapture
     */
    public ECapture insertCaptureObject(String name, int type) {
        ECapture capture = getFocusManager().insertCaptureObject(name, type);
        //更新
        update();
        return capture;
    }

    /**
     * 插入选择框
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose insertCheckBoxChoose() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertCheckBoxChoose("选择框", "选择框", false);
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
        ECheckBoxChoose checkBoxChoose = getFocusManager().insertCheckBoxChoose(
                name, text, isChecked);
        //更新
        update();
        return checkBoxChoose;
    }

    /**
     * 数字选择
     * @return ENumberChoose
     */
    public ENumberChoose insertNumberChoose() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertNumberChoose("数字选择", "0");
    }

    /**
     * 数字选择
     * @param name String
     * @param text String
     * @return ECapture
     */
    public ENumberChoose insertNumberChoose(String name, String text) {
        ENumberChoose numberChoose = getFocusManager().insertNumberChoose(name,
                text);
        if (numberChoose == null) {
            return null;
        }
        //更新
        update();
        return numberChoose;
    }

    /**
     * 插入图片
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        EMacroroutine macroroutine = getFocusManager().insertPicture();
        //更新
        update();
        return macroroutine;
    }

    /**
     * 粘帖图片;
     * @param object Object[]
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture(Object[] object) {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        EMacroroutine macroroutine = getFocusManager().insertPicture(object);
        //更新
        update();
        return macroroutine;
    }


    /**
     * 固定文本对象属性对话框
     */
    public void onOpenFixedProperty() {

        //不能编辑
        if (!canEdit()) {
            return;
        }
        EComponent com = getFocusManager().getFocus();
        if (com == null || !(com instanceof EFixed)) {
            return;
        }

        //
        if ( com != null && ExpressionUtil.isExpression(com) ) {
            return;
        }

        ((EFixed) com).openPropertyDialog();
    }

    /**
     * 删除固定文本对象(当前选中的)
     */
    public void deleteFixed() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        EComponent com = getFocusManager().getFocus();
        if (com == null) {
            return;
        }

        //抓取框;
        if (com instanceof ECapture) {
            if (((ECapture) com).isLocked()) {
                return;
            }
        }

        //锁定元件不能删除;
        if (com instanceof EFixed) {
            if (((EFixed) com).isLocked()) {
                return;
            }
        }

        if (com instanceof EFixed) {
            ((EFixed) com).deleteFixed();
        } else if (com instanceof EMacroroutine) {
            ((EMacroroutine) com).delete();
        } else {
            return;
        }
        //更新
        update();
    }

    /**
     * 打开宏数据属性对话框
     */
    public void onOpenMicroFieldProperty() {
        getMicroFieldManager().openPropertyDialog();
    }

    /**
     * 设置宏
     * @param name String 名称
     * @param value String 显示值
     */
    public void setMicroField(String name, String value) {
        getMicroFieldManager().setMicroField(name, value);
    }

    /**
     * 设置宏Code
     * @param name String 名称
     * @param value String 显示值
     */
    public void setMicroFieldCode(String name, String value, String code) {
        getMicroFieldManager().setMicroField(name, value, code);
    }

    /**
     * 得到宏
     * @param name String 名称
     * @return String
     */
    public String getMicroField(String name) {
        return getMicroFieldManager().getMicroField(name);
    }

    /**
     * 设置对话框开关
     * @param messageBoxSwitch boolean
     */
    public void setMessageBoxSwitch(boolean messageBoxSwitch) {
        getFileManager().setMessageBoxSwitch(messageBoxSwitch);
    }

    /**
     * 得到对话框开关
     * @return boolean
     */
    public boolean getMessageBoxSwitch() {
        return getFileManager().getMessageBoxSwitch();
    }

    //===============================表格处理函数========================//
    /**
     * 插入表格(当前位置)
     * @param rowCount int 行数
     * @param columnCount int 列数
     * @return ETable
     */
    public ETable insertTable(int rowCount, int columnCount) {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        ETable table = getFocusManager().insertTable(rowCount, columnCount);
        //更新
        update();
        return table;
    }

    /**
     * 插入基本Table对话框
     * @return ETable
     */
    public ETable insertBaseTableDialog() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        ETable table = getFocusManager().insertBaseTableDialog();
        if (table == null) {
            return null;
        }
        //更新
        update();
        return table;
    }

    /**
     * 删除表格
     */
    public void deleteTable() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        ETable table = getFocusManager().getFocusTable();
        if (table == null) {
            return;
        }
        table.removeThisAll();
        //更新
        update();
    }

    /**
     * 插入表格行
     * @return ETR
     */
    public ETR insertTR() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        ETR tr = getFocusManager().insertTR();
        if (tr == null) {
            return null;
        }
        //更新
        update();
        return tr;
    }

    /**
     * 追加表格行
     * @return ETR
     */
    public ETR appendTR() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        ETR tr = getFocusManager().appendTR();
        if (tr == null) {
            return null;
        }
        //更新
        update();
        return tr;
    }

    /**
     * 删除表格行
     * @return boolean
     */
    public boolean deleteTR() {
        //不能编辑
        if (!canEdit()) {
            return false;
        }
        if (!getFocusManager().deleteTR()) {
            return false;
        }
        //更新
        update();
        return true;
    }

    //===============================文件处理函数========================//
    /**
     * 得到打开文件的位置
     * @return int
     * -1 不确定
     * 0 本地
     * 1 AppServer
     * 2 FileServer Emr模板文件
     * 3 FileServer Emr数据文件
     */
    public int getFileOpenServerType() {
        return getFileManager().getOpenServerType();
    }

    /**
     * 得到打开文件的路径
     * @return String
     */
    public String getFileOpenPath() {
        return getFileManager().getPath();
    }

    /**
     * 得到打开文件的文件名
     * @return String
     */
    public String getFileOpenName() {
        return getFileManager().getFileName();
    }

    /**
     * 设置文件的作者
     * @param author String
     */
    public void setFileAuthor(String author) {
        getFileManager().setAuthor(author);
    }

    /**
     * 得到文件的作者
     * @return String
     */
    public String getFileAuthor() {
        return getFileManager().getAuthor();
    }

    /**
     * 设置文件的公司
     * @param co String
     */
    public void setFileCo(String co) {
        getFileManager().setCo(co);
    }

    /**
     * 得到文件的公司
     * @return String
     */
    public String getFileCo() {
        return getFileManager().getCo();
    }

    /**
     * 设置文件的创建日期
     * @param createDate String
     */
    public void setFileCreateDate(String createDate) {
        getFileManager().setCreateDate(createDate);
    }

    /**
     * 得到文件的创建日期
     * @return String
     */
    public String getFileCreateDate() {
        return getFileManager().getCreateDate();
    }

    /**
     * 设置文件的标题
     * @param title String
     */
    public void setFileTitle(String title) {
        getFileManager().setTitle(title);
    }

    /**
     * 得到文件的标题
     * @return String
     */
    public String getFileTitle() {
        return getFileManager().getTitle();
    }

    /**
     * 设置英文标题
     * @param enTitle String
     */
    public void setFileEnTitle(String enTitle) {
        getFileManager().setEnTitle(enTitle);
    }

    /**
     * 得到英文标题
     * @return String
     */
    public String getFileEnTitle() {
        return getFileManager().getEnTitle();
    }

    /**
     * 设置文件的备注
     * @param remark String
     */
    public void setFileRemark(String remark) {
        getFileManager().setRemark(remark);
    }

    /**
     * 得到文件的备注
     * @return String
     */
    public String getFileRemark() {
        return getFileManager().getRemark();
    }

    /**
     * 设置文件编辑锁
     * @param editLock boolean
     */
    public void setFileEditLock(boolean editLock) {
        getFileManager().setEditLock(editLock);
    }

    /**
     * 文件是否有编辑锁
     * @return boolean
     */
    public boolean isFileEditLock() {
        return getFileManager().isEditLock();
    }

    /**
     * 设置文件锁定用户
     * @param lockUser String
     */
    public void setFileLockUser(String lockUser) {
        getFileManager().setLockUser(lockUser);
    }

    /**
     * 得到文件锁定用户
     * @return String
     */
    public String getFileLockUser() {
        return getFileManager().getLockUser();
    }

    /**
     * 设置文件锁定日期
     * @param lockDate String
     */
    public void setFileLockDate(String lockDate) {
        getFileManager().setLockDate(lockDate);
    }

    /**
     * 得到文件锁定日期
     * @return String
     */
    public String getFileLockDate() {
        return getFileManager().getLockDate();
    }

    /**
     * 设置文件锁定部门
     * @param lockDept String
     */
    public void setFileLockDept(String lockDept) {
        getFileManager().setLockDept(lockDept);
    }

    /**
     * 得到文件锁定部门
     * @return String
     */
    public String getFileLockDept() {
        return getFileManager().getLockDept();
    }

    /**
     * 设定文件锁定IP
     * @param lockIP String
     */
    public void setFileLockIP(String lockIP) {
        getFileManager().setLockIP(lockIP);
    }

    /**
     * 得到文件锁定IP
     * @return String
     */
    public String getFileLockIP() {
        return getFileManager().getLockIP();
    }

    /**
     * 设置文件最后修改人
     * @param lastEditUser String
     */
    public void setFileLastEditUser(String lastEditUser) {
        getFileManager().setLastEditUser(lastEditUser);
    }

    /**
     * 得到文件最后修改人
     * @return String
     */
    public String getFileLastEditUser() {
        return getFileManager().getLastEditUser();
    }


    public void setFileContainPic(String containPic){
        getFileManager().setContainPic(containPic);
    }

    public String getFileContainPic(){
        return getFileManager().getContainPic();
    }

    /**
     * 设置文件最后修改日期
     * @param lastEditDate String
     */
    public void setFileLastEditDate(String lastEditDate) {
        getFileManager().setLastEditDate(lastEditDate);
    }

    /**
     * 得到文件最后修改日期
     * @return String
     */
    public String getFileLastEditDate() {
        return getFileManager().getLastEditDate();
    }

    /**
     * 设置文件最后修改IP
     * @param lastEditIP String
     */
    public void setFileLastEditIP(String lastEditIP) {
        getFileManager().setLastEditIP(lastEditIP);
    }

    /**
     * 得到文件最后修改IP
     * @return String
     */
    public String getFileLastEditIP() {
        return getFileManager().getLastEditIP();
    }

    /**
     * 设置能否编辑
     * @param canEdit boolean
     */
    public void setCanEdit(boolean canEdit) {
        getEventManager().setCanEdit(canEdit);
    }

    /**
     * 能否编辑
     * @return boolean
     */
    public boolean canEdit() {
        return getEventManager().canEdit();
    }


    /**
     * 更新
     */
    public void update() {
        getFocusManager().update();
    }

    /**
     * 设置页面风格边框尺寸
     * @param pageBorderSize int
     */
    public void setPageBorderSize(int pageBorderSize) {
        getViewManager().setPageBorderSize(pageBorderSize);
    }

    /**
     * 得到页面风格边框尺寸
     * @return int
     */
    public int getPageBorderSize() {
        return getViewManager().getPageBorderSize();
    }

    /**
     * 段落对话框
     */
    public void onParagraphDialog() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        getFocusManager().onParagraphDialog();
    }

    /**
     * Table 属性对话框
     */
    public void onTablePropertyDialog() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        getFocusManager().onTablePropertyDialog();
    }

    /**
     * 剪切
     */
    public void onCut() {
        getFocusManager().onCopy();
    }

    /**
     * 复制
     */
    public void onCopy() {
        getFocusManager().onCopy();
    }

    /**
     * 粘贴
     */
    public void onPaste() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        if (!getFocusManager().onPaste()) {
            return;
        }
    }

    /**
     * 粘贴字符串
     * @param s String
     * @return boolean
     */
    public boolean pasteString(String s) {
        //不能编辑
        if (!canEdit()) {
            return false;
        }
        if (!getFocusManager().pasteString(s)) {
            return false;
        }
        update();
        return true;
    }

    /**
     * 删除
     */
    public void onDelete() {
        if (!getFocusManager().onDelete()) {
            return;
        }
        update();
    }

    /**
     * 单击Text
     * @param text EText
     */
    public void onWordClickText(EText text) {
        if (text == null) {
            return;
        }
        if (getFontBoldButton() != null) {
            getFontBoldButton().setSelected(text.isBold());
        }
        if (getFontItalicButton() != null) {
            getFontItalicButton().setSelected(text.isItalic());
        }
        if (getFontSizeCombo() != null) {
            getFontSizeCombo().setText("" + text.getFontSize());
        }
        if (getfontCombo() != null) {
            getfontCombo().setText(text.getFontName());
        }
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
     * 过滤对象
     * @param name String
     * @param type int
     * @return List
     */
    public List filterObject(String name, int type) {
        return getPageManager().filterObject(name, type);
    }

    /**
     * 查找组
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group) {
        return getPageManager().findGroup(group);
    }

    /**
     * 得到组值
     * @param group String
     * @return String
     */
    public String findGroupValue(String group) {
        return getPageManager().findGroupValue(group);
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
     * 查找固定文本对象
     * @param name String
     * @return EFixed
     */
    public EFixed findFixed(String name) {
        return (EFixed) findObject(name, EComponent.FIXED_TYPE);
    }

    /**
     * 过滤固定文本
     * @param name String
     * @return List
     */
    public List filterFixed(String name) {
        return filterObject(name, EComponent.FIXED_ACTION_TYPE);
    }

    /**
     * 清空抓取对象内的元素
     * @param name String
     * @return ECapture
     */
    public ECapture clearCapture(String name) {
        ECapture capture = findCapture(name);
        if (capture == null) {
            return capture;
        }
        capture.clear();
        update();
        return capture;
    }

    /**
     * 抓取数据
     * @param name String 名称
     * @return String
     */
    public String getCaptureValue(String name) {

    	 return this.getCaptureValue(name, false);
    }

    /**
     * 抓取数据<不要带删除线的>
     * @param name String 名称
     * @return String
     */
    public String getCaptureValueNoDel(String name) {

        return this.getCaptureValue(name, true);
    }

    /**
     *
     * @param name
     * @param withnotDelText
     * @return
     */
    private String getCaptureValue(String name,boolean withnotDelText){

        ECapture capture = findCapture(name);
        if (capture == null) {
            //System.out.println("err:[" + name + "]没有找到对象");
            return "";
        }

        //
    	if( withnotDelText ){
    		return capture.getValue(true);
    	}else{
    		return capture.getValue();
    	}
    }

    /**
     * 焦点在抓取框内
     * @param name String
     * @return boolean
     */
    public boolean focusInCaptue(String name) {
        return getFocusManager().focusInCaptue(name);
    }

    /**
     * 设置图区显示
     * @param name String
     * @param visible boolean
     * @return boolean
     */
    public boolean setVisiblePic(String name, boolean visible) {
        EPic pic = (EPic) findObject(name, EComponent.PIC_TYPE);
        if (pic == null) {
            return false;
        }
        pic.setVisible(visible);
        update();
        return true;
    }

    /**
     * 图区是否显示
     * @param name String
     * @return boolean
     */
    public boolean isVisiblePic(String name) {
        EPic pic = (EPic) findObject(name, EComponent.PIC_TYPE);
        if (pic == null) {
            return false;
        }
        return pic.isVisible();
    }

    /**
     * 编辑状态
     */
    public void onEditWord() {
        getFocusManager().onEditWord();
        update();
    }

    /**
     * 阅览
     */
    public void onPreviewWord() {
        getFocusManager().onPreviewWord();
        update();
    }

    /**
     * 自定义脚本对话框
     */
    public void onCustomScriptDialog() {
        getSyntaxManager().onCustomScriptDialog();
    }

    /**
     * 合并单元格
     */
    public void onUniteTD() {
        if (!getFocusManager().onUniteTD()) {
            return;
        }
        update();
    }

    /**
     * 新文件
     */
    public void onNewFile() {
        getFileManager().onNewFile();
    }

    /**
     * 插入文件对话框
     */
    public void onInsertFileDialog() {
        getFocusManager().onInsertFileDialog();
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
        return getFocusManager().onInsertFile(path, fileName, type, state);
    }

    /**
     * 插入文件在固定文本之前
     * @param name String
     * @param path String
     * @param fileName String
     * @param type int
     * @param state boolean
     * 例如        onInsertFileFrontFixed("AAA","%ROOT%\\config\\prt","a1.jhw",1,false);
     * @return boolean
     */
    public boolean onInsertFileFrontFixed(String name, String path,
                                          String fileName, int type,
                                          boolean state) {
        EFixed fixed = findFixed(name);
        if (fixed == null) {
            return false;
        }
        fixed.setFocus();
        return getFocusManager().onInsertFile(path, fileName, type, state);
    }

    /**
     * 插入文件在固定文本之后
     * @param name String
     * @param path String
     * @param fileName String
     * @param type int
     * @param state boolean
     * @return boolean
     */
    public boolean onInsertFileBehindFixed(String name, String path,
                                           String fileName, int type,
                                           boolean state) {
        EFixed fixed = findFixed(name);
        if (fixed == null) {
            return false;
        }
        fixed.setFocusLast();
        return getFocusManager().onInsertFile(path, fileName, type, state);
    }

    /**
     * 分割面板
     * @return boolean
     */
    public boolean separatePanel() {
        return getFocusManager().separatePanel();
    }

    /**
     * 设置编辑序号记录
     * @param index int
     */
    public void setNodeIndex(int index) {
        getPM().getModifyNodeManager().setIndex(index);
    }

    /**
     *
     */
    public void setMarkNone(){
    	this.setNodeIndex(-1);
    }

    /**
     * 得到编辑序号记录
     * @return int
     */
    public int getNodeIndex() {
        return getPM().getModifyNodeManager().getIndex();
    }

    /**
     * 插入图片编辑区
     * @return EImage
     */
    public EImage insertImage() {
        EImage image = getFocusManager().insertImage();
        if (image == null) {
            return null;
        }
        update();
        return image;
    }

    /**
     * 删除图片编辑区
     */
    public void deleteImage() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        EImage image = getFocusManager().getFocusImage();
        if (image == null) {
            return;
        }
        image.removeThis();
        //更新
        update();
    }

    /**
     * 插入块
     */
    public void insertGBlock() {
        //不能编辑
        if (!canEdit()) {
            return;
        }
        getFocusManager().setInsertImageAction(1);
    }

    /**
     * 插入线
     */
    public void insertGLine() {
        if (!canEdit()) {
            return;
        }
        getFocusManager().setInsertImageAction(2);
    }

    /**
     * 调整块尺寸
     * @param index int
     */
    public void onSizeBlockMenu(int index) {
        EImage image = getFocusManager().getFocusImage();
        if (image != null) {
            image.onSizeBlockMenu(index);
        }
    }

    /**
     * 图层属性
     */
    public void onDIVProperty() {
        getPageManager().getMVList().openProperty();
    }

    /**
     * 性别控制
     * @param control int
     */
    public void setSexControl(int control) {
        getPM().setSexControl(control);
        update();
    }

    /**
     * 固定文本重新抓取数据
     * @param mrNo String
     * @param caseNo String
     */
    public void fixedTryReset(String mrNo, String caseNo) {
        getPM().fixedTryReset(mrNo, caseNo);
    }

    /**
     * 弹出菜单
     * @param syntax String 语法
     * @param messageIO DMessageIO 监听对象
     */
    public void popupMenu(String syntax, DMessageIO messageIO) {
        getFocusManager().popupMenu(syntax, messageIO);
    }

    /**
     * 写入下拉框
     * @return ETextFormat
     */
    public ETextFormat insertTextFormat() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertTextFormat("下拉框", "");
    }

    /**
     * 写入下拉框
     * @param name String
     * @param text String
     * @return ETextFormat
     */
    public ETextFormat insertTextFormat(String name, String text) {
        ETextFormat eTextFormat = getFocusManager().insertTextFormat(name, text);
        //更新
        update();
        return eTextFormat;
    }


    public EAssociateChoose insertAssociateChoose() {
        //不能编辑
        if (!canEdit()) {
            return null;
        }
        return insertAssociateChoose("关联选项", "关联选项");
    }

    public EAssociateChoose insertAssociateChoose(String name, String text) {
        EAssociateChoose associateChoose = getFocusManager().
                                           insertAssociateChoose(name, text);
        //更新
        update();
        return associateChoose;
    }

    //
    public void mergerCell(){

    	ETable eb = getFocusManager().getFocusTable();

    	if( null==eb ){
    		return;
    	}

    	// System.out.println( " ### "+ eb.getName() );

    	CSelectedModel sm = getFocusManager().getSelectedModel();
    	TList tl = sm.getSelectedList();

    	if( null==tl || tl.size()==0 ){
    		return;
    	}

    	if( tl.size()==1 ){
    		return;
    	}

        // System.out.println(" ## "+ tl);

    	CSelectTDModel tmp_1 = (CSelectTDModel) tl.get(0);
    	ETD etd_start = tmp_1.getTD();

    	CSelectTDModel tmp_2 = (CSelectTDModel) tl.get(tl.size()-1);
    	ETD etd_end = tmp_2.getTD();


    	eb.uniteTD( etd_start.getTR().findIndex(), etd_start.findIndex(),
    			    etd_end.getTR().findIndex(), etd_end.findIndex() );

    	getFocusManager().clearSelected();

    	eb.reset();
    }

    //
    private DStyle formatStyle = null;

    //
    public void onFormatSet(){

    	EText et = getFocusManager().getFocusText();

    	formatStyle = et.getStyle().copy();
    }

    //
    public void onFormatUse(){

    	if( null==formatStyle ){
    		return;
    	}

    	//
    	CSelectedModel ctm = getFocusManager().getSelectedModel();

    	TList tl = ctm.getSelectedList();

    	for( int i =0;i<tl.size();i++ ){

    		Object obj = tl.get(i);

    		if ( obj instanceof CSelectTextBlock ) {
				 EText tt = ((CSelectTextBlock) obj).getText();

				 if( null!=tt && !tt.equals("") ){

					 Font f = formatStyle.getFont();

                     tt.modifyBold( f.isBold() );
					 tt.modifyFontSize( f.getSize() );
					 tt.modifyItalic( f.isItalic() );
					// tt.modifyColor( formatStyle.getFontMetrics(). );
					 tt.setHeight( formatStyle.getFontHeight() );
					 tt.modifyFont( f.getFamily() );
				 }
			}
    	}

    	this.formatStyle = null;

    	//
    	update();
    }

    /**
     * 表达式集合
     * < 1.插入表达式控件时 2.读取文本对象时 --会插入 >
     */
    private List<EFixed> expressionList = new ArrayList<EFixed>();
	public List<EFixed> getExpressionList() {
		return expressionList;
	}

	//
    public void onCalculateExpression(){

    	if( null!=expressionList && expressionList.size()>0 ){

    		ExpressionUtil.doProcessExpression(this.getPageManager(), expressionList);
    	}
    }
    
    /**
     * 上下标对象属性对话框
     */
    public void onOpenMarkProperty() {

        //不能编辑
        if (!canEdit()) {
            return;
        }
        EComponent com = getFocusManager().getFocus();
        if (com == null || !(com instanceof EFixed)) {
            return;
        }
        //
        if (com != null && (com instanceof ECapture)) {
            return;
        }
        if (com != null && (com instanceof ECheckBoxChoose)) {
            return;
        }
        if (com != null && (com instanceof EHasChoose)) {
            return;
        }
        if (com != null && (com instanceof EInputText)) {
            return;
        }
        if (com != null && (com instanceof EMicroField)) {
            return;
        }
        if (com != null && (com instanceof EMultiChoose)) {
            return;
        }
        if (com != null && (com instanceof ENumberChoose)) {
            return;
        }
        if (com != null && (com instanceof ESingleChoose)) {
            return;
        }
        if (com != null && (com instanceof ETextFormat)) {
            return;
        }            
        //
        if ( com != null && ExpressionUtil.isExpression(com) ) {
            return;
        }

        ((EFixed) com).openMarkDialog();
    }
    

}
