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
 * <p>Title: word���</p>
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
     * �ļ���
     */
    private String fileName;
    /**
     * ��ʾ�����������б��
     */
    private TShowZoomCombo showZoomCombo;
    /**
     * �������б��
     */
    private TFontCombo fontCombo;
    /**
     * �������б��
     */
    private TFontSizeCombo fontSizeCombo;
    /**
     * ������尴ť
     */
    private TToolButton fontBoldButton;
    /**
     * ����б�尴ť
     */
    private TToolButton fontItalicButton;
    /**
     * ����䷽ʽ��ť
     */
    private TToolButton alignmentLeftButton;
    /**
     * �ж��䷽ʽ��ť
     */
    private TToolButton alignmentCenterButton;
    /**
     * �Ҷ��䷽ʽ��ť
     */
    private TToolButton alignmentRightButton;


    public TWordBase() {
        //System.out.println("==TWordBase default==");
        dText = new DText();
        dText.setTag("DTEXT");
        dText.setBorder("��");
        dText.setAutoBaseSize(true);
        addDComponent(dText);
        getEventManager().getIOParm().addListener("CLICK_TEXT", this,
                                                  "onWordClickText");
    }

    /**
     * �õ�Word���
     * @return DText
     */
    public DText getWordText() {
        return dText;
    }

    /**
     * �õ�������
     * @return PM
     */
    public PM getPM() {
        return getWordText().getPM();
    }

    /**
     * �õ��ļ�������
     * @return MFile
     */
    public MFile getFileManager() {
        return getPM().getFileManager();
    }

    /**
     * �õ���ͼ������
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
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
     * �õ��¼�������
     * @return MEvent
     */
    public MEvent getEventManager() {
        return getPM().getEventManager();
    }

    /**
     * �õ�����������
     * @return MMicroField
     */
    public MMicroField getMicroFieldManager() {
        return getPM().getMicroFieldManager();
    }

    /**
     * �õ��﷨������
     * @return MSyntax
     */
    public MSyntax getSyntaxManager() {
        return getPM().getSyntaxManager();
    }

    /**
     * �����ļ���
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
     * �õ��ļ���
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * ���ļ�
     * @param path String ·��
     * @param fileName String �ļ���
     * @param type int ����
     * 0 ����
     * 1 AppServer
     * 2 FileServer Emrģ���ļ�
     * 3 FileServer Emr�����ļ�
     * @param state boolean true ���� false �༭
     * @return boolean
     */
    public boolean onOpen(String path, String fileName, int type, boolean state) {

    	//��ʼ��
    	this.expressionList.clear();
    	this.setNodeIndex(-1);
    	//
        if (!getFileManager().onOpen(path, fileName, type, state)) {
        	//̩��ר��
        	//this.fileName = getFileManager().getPath() +
            //getFileManager().getFileName();
            return false;
        }
        this.fileName = getFileManager().getPath() +
                        getFileManager().getFileName();
        return true;
    }

    /**
     * ����
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onSave() {
        return getFileManager().onSave();
    }

    /**
     * ���Ϊ
     * @param path String
     * @param fileName String
     * @param type int
     * @return boolean
     */
    public boolean onSaveAs(String path, String fileName, int type) {
        return getFileManager().onSaveAs(path, fileName, type);
    }

    /**
     * ���Ϊ
     * @param path String
     * @param fileName String
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onSaveAs(String path, String fileName) {
        return getFileManager().onSaveAs(path, fileName);
    }

    /**
     * ��������״̬
     * @param isPreview boolean
     */
    public void setPreview(boolean isPreview) {
        getWordText().setPreview(isPreview);
    }

    /**
     * �Ƿ�������״̬
     * @return boolean
     */
    public boolean isPreview() {
        return getWordText().isPreview();
    }

    /**
     * �������ųߴ�(�ַ�����)
     * @param zoom String
     */
    public void setZoomString(String zoom) {
        getViewManager().setZoomString(zoom);
    }

    /**
     * �õ����ųߴ�(�ַ�����)
     * @return String
     */
    public String getZoomString() {
        return getViewManager().getZoomString();
    }

    /**
     * ����
     * @param parm Object
     */
    public void setWordParameter(Object parm) {
        getFileManager().setParameter(parm);
    }

    /**
     * ��ӡ
     */
    public void print() {
        getPageManager().print();
    }

    /**
     * ҳ�����öԻ���
     */
    public void printDialog() {
        printDialog(1);
    }

    /**
     * ҳ�����öԻ���
     * @param size int
     */
    public void printDialog(int size) {
        getPageManager().printDialog(size);
    }

    /**
     * ��ӡ���öԻ���
     */
    public void printSetup() {
        getPageManager().printSetup();
    }

    /**
     * ���Ի���
     */
    public void printXDDialog() {
        getPageManager().printXDDialog();
    }

    /**
     * ������ʾ�к�
     * @param isShowRowID boolean
     */
    public void setShowRowID(boolean isShowRowID) {
        getViewManager().setShowRowID(isShowRowID);
    }

    /**
     * �Ƿ���ʾ�к�
     * @return boolean
     */
    public boolean isShowRowID() {
        return getViewManager().isShowRowID();
    }

    //===============================��������===========================//
    /**
     * ������ʾ�����������б���Tag
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
     * ������ʾ�����������б��
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
     * ��ʾ�����������б��ı䶯��
     */
    public void onShowZoomComboSelectedAction() {
        String s = TypeTool.getString(showZoomCombo.getValue());
        setZoomString(s);
        if (s != null && s.length() > 0 && !s.endsWith("%")) {
            showZoomCombo.setValue(s + "%");
        }
    }

    /**
     * �õ���ʾ�����������б��
     * @return TShowZoomCombo
     */
    public TShowZoomCombo getShowZoomCombo() {
        return showZoomCombo;
    }

    //===============================��������===========================//
    /**
     * �����������б��Tag
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
     * �����������б��
     * @param fontCombo TFontCombo
     */
    public void setFontCombo(TFontCombo fontCombo) {
        this.fontCombo = fontCombo;
        if (fontCombo == null) {
            return;
        }
        fontCombo.addEventListener(TComboBoxEvent.SELECTED, this,
                                   "onModifyFontSelectedAction");
        fontCombo.setValue("����");
    }

    /**
     * �������嶯��
     */
    public void onModifyFontSelectedAction() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        String s = TypeTool.getString(fontCombo.getValue());
        modifyFont(s);
    }

    /**
     * ��������
     * @param font String
     * @return boolean
     */
    public boolean modifyFont(String font) {
        boolean flg = getFocusManager().modifyFont(font);
        //����
        update();
        return flg;
    }

    /**
     * �õ��������б��
     * @return TFontCombo
     */
    public TFontCombo getfontCombo() {
        return fontCombo;
    }

    //===============================��������ߴ�=======================//
    /**
     * ��������ߴ����б��Tag
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
     * ��������ߴ�
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
     * ��������ߴ綯��
     */
    public void onModifyFontSizeSelectedAction() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        int size = TypeTool.getInt(fontSizeCombo.getValue());
        modifyFontSize(size);
    }

    /**
     * ��������ߴ�
     * @param size int
     * @return boolean
     */
    public boolean modifyFontSize(int size) {
        boolean flg = getFocusManager().modifyFontSize(size);
        //����
        update();
        return flg;
    }

    /**
     * ����������尴ťTag
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
     * ����������尴ť
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
     * ������尴ť����
     * @param e ActionEvent
     */
    public void onModifyFontBoldAction(ActionEvent e) {
        if (fontBoldButton == null) {
            return;
        }
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        fontBoldButton.setSelected(!fontBoldButton.isSelected());
        modifyBold(fontBoldButton.isSelected());
        grabFocus();
    }

    /**
     * �õ�������尴ť
     * @return TToolButton
     */
    public TToolButton getFontBoldButton() {
        return fontBoldButton;
    }

    /**
     * �޸��������
     * @param bold boolean
     * @return boolean
     */
    public boolean modifyBold(boolean bold) {
        boolean flg = getFocusManager().modifyBold(bold);
        //����
        update();
        return flg;
    }

    /**
     * ��������б�尴ťTag
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
     * ��������б�尴ť
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
     * ����б�尴ť����
     * @param e ActionEvent
     */
    public void onModifyFontItalicAction(ActionEvent e) {

        if (fontItalicButton == null) {
            return;
        }
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        fontItalicButton.setSelected(!fontItalicButton.isSelected());
        modifyItalic(fontItalicButton.isSelected());
        grabFocus();
    }

    /**
     * �õ�����б�尴ť
     * @return TToolButton
     */
    public TToolButton getFontItalicButton() {
        return fontItalicButton;
    }

    /**
     * �޸�����б��
     * @param italic boolean
     * @return boolean
     */
    public boolean modifyItalic(boolean italic) {
        boolean flg = getFocusManager().modifyItalic(italic);
        //����
        update();
        return flg;
    }

    /**
     * �õ�����ߴ�
     * @return TFontSizeCombo
     */
    public TFontSizeCombo getFontSizeCombo() {
        return fontSizeCombo;
    }

    /**
     * ��������밴ťTag
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
     * ��������밴ť
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
     * ����밴ť����
     * @param e ActionEvent
     */
    public void onModifyAlignmentLeftAction(ActionEvent e) {
        if (alignmentLeftButton == null) {
            return;
        }
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        //alignmentLeftButton.setSelected(!alignmentLeftButton.isSelected());
        setAlignment(0);
        grabFocus();
    }

    /**
     * �õ�����밴ť
     * @return TToolButton
     */
    public TToolButton getAlignmentLeftButton() {
        return alignmentLeftButton;
    }

    /**
     * �����ж��밴ťTag
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
     * �����ж��밴ť
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
     * �ж��밴ť����
     * @param e ActionEvent
     */
    public void onModifyAlignmentCenterAction(ActionEvent e) {
        if (alignmentCenterButton == null) {
            return;
        }
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        //alignmentLeftButton.setSelected(!alignmentLeftButton.isSelected());
        setAlignment(1);
        grabFocus();
    }

    /**
     * �õ��ж��밴ť
     * @return TToolButton
     */
    public TToolButton getAlignmentCenterButton() {
        return alignmentCenterButton;
    }

    /**
     * �����Ҷ��밴ťTag
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
     * �����Ҷ��밴ť
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
     * �Ҷ��밴ť����
     * @param e ActionEvent
     */
    public void onModifyAlignmentRightAction(ActionEvent e) {
        if (alignmentRightButton == null) {
            return;
        }
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        //alignmentLeftButton.setSelected(!alignmentLeftButton.isSelected());
        setAlignment(2);
        grabFocus();
    }

    /**
     * �õ��Ҷ��밴ť
     * @return TToolButton
     */
    public TToolButton getAlignmentRightButton() {
        return alignmentRightButton;
    }

    /**
     * ���ö���λ��(��ǰλ��)
     * @param alignment int
     * 0 left
     * 1 center
     * 2 right
     * @return boolean
     */
    public boolean setAlignment(int alignment) {
        //���ܱ༭
        if (!canEdit()) {
            return false;
        }
        boolean flg = getFocusManager().setAlignment(alignment);
        //����
        update();
        return flg;
    }

    //===============================�̶��ı�������====================//
    /**
     * ����̶��ı�
     * @return EFixed
     */
    public EFixed insertFixed() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertFixed("�̶��ı�", "�̶��ı�");
    }

    /**
     * ����̶��ı�
     * @param name String ����
     * @param text String ��ʾ����
     * @return EFixed
     */
    public EFixed insertFixed(String name, String text) {
        EFixed fixed = getFocusManager().insertFixed(name, text);
        //����
        update();
        return fixed;
    }

    /**
     * ���뵥ѡ
     * @return ESingleChoose
     */
    public ESingleChoose insertSingleChoose() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertSingleChoose("��ѡ", "��ѡ");
    }

    /**
     * ���뵥ѡ
     * @param name String ����
     * @param text String ��ʾ����
     * @return ESingleChoose
     */
    public ESingleChoose insertSingleChoose(String name, String text) {
        ESingleChoose singleChoose = getFocusManager().insertSingleChoose(name,
                text);
        //����
        update();
        return singleChoose;
    }

    /**
     * �����ѡ
     * @return EMultiChoose
     */
    public EMultiChoose insertMultiChoose() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertMultiChoose("��ѡ", "��ѡ");
    }

    /**
     * �����ѡ
     * @param name String ����
     * @param text String ��ʾ����
     * @return EMultiChoose
     */
    public EMultiChoose insertMultiChoose(String name, String text) {
        EMultiChoose multiChoose = getFocusManager().insertMultiChoose(name,
                text);
        //����
        update();
        return multiChoose;
    }

    /**
     * ��������ѡ��
     * @return EHasChoose
     */
    public EHasChoose insertHasChoose() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertHasChoose("����ѡ��", "����ѡ��");
    }

    /**
     * ��������ѡ��
     * @param name String ����
     * @param text String ��ʾ����
     * @return EHasChoose
     */
    public EHasChoose insertHasChoose(String name, String text) {
        EHasChoose hasChoose = getFocusManager().insertHasChoose(name, text);
        //����
        update();
        return hasChoose;
    }


    /**
     * ����������ʾ���
     * @return EInputText
     */
    public EInputText insertInputText() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertInputText("������ʾ���", "������ʾ���");
    }

    /**
     * ����������ʾ���
     * @param name String ����
     * @param text String ��ʾ����
     * @return EInputText
     */
    public EInputText insertInputText(String name, String text) {
        EInputText inputText = getFocusManager().insertInputText(name, text);
        //����
        update();
        return inputText;
    }

    /**
     * �����
     * @return EMicroField
     */
    public EMicroField insertMicroField() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertMicroField("��", "��");
    }

    /**
     * �����
     * @param name String ����
     * @param text String ��ʾ����
     * @return EMicroField
     */
    public EMicroField insertMicroField(String name, String text) {
        EMicroField microField = getFocusManager().insertMicroField(name, text);
        //����
        update();
        return microField;
    }
    
	/**
	 * ����ǩ��
	 * 
	 * @param name
	 *            String ����
	 * @param text
	 *            String ��ʾ����
	 * @return EMicroField
	 */
	public ESign insertSign(String groupName, String name, String signCode,
			String text, String timestmp) {
		ESign sign = getFocusManager().insertSign(groupName, name, signCode,
				text, timestmp);
		// ����
		update();
		return sign;
	}

    /**
     * ����ץȡ
     * @return ECapture
     */
    public ECapture insertCaptureObject() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertCaptureObject("ץȡ", 0);
    }

    /**
     * ����ץȡ
     * @param name String
     * @param type int
     * @return ECapture
     */
    public ECapture insertCaptureObject(String name, int type) {
        ECapture capture = getFocusManager().insertCaptureObject(name, type);
        //����
        update();
        return capture;
    }

    /**
     * ����ѡ���
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose insertCheckBoxChoose() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertCheckBoxChoose("ѡ���", "ѡ���", false);
    }

    /**
     * ����ѡ���
     * @param name String
     * @param text String
     * @param isChecked boolean
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose insertCheckBoxChoose(String name, String text,
                                                boolean isChecked) {
        ECheckBoxChoose checkBoxChoose = getFocusManager().insertCheckBoxChoose(
                name, text, isChecked);
        //����
        update();
        return checkBoxChoose;
    }

    /**
     * ����ѡ��
     * @return ENumberChoose
     */
    public ENumberChoose insertNumberChoose() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertNumberChoose("����ѡ��", "0");
    }

    /**
     * ����ѡ��
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
        //����
        update();
        return numberChoose;
    }

    /**
     * ����ͼƬ
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        EMacroroutine macroroutine = getFocusManager().insertPicture();
        //����
        update();
        return macroroutine;
    }

    /**
     * ճ��ͼƬ;
     * @param object Object[]
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture(Object[] object) {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        EMacroroutine macroroutine = getFocusManager().insertPicture(object);
        //����
        update();
        return macroroutine;
    }


    /**
     * �̶��ı��������ԶԻ���
     */
    public void onOpenFixedProperty() {

        //���ܱ༭
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
     * ɾ���̶��ı�����(��ǰѡ�е�)
     */
    public void deleteFixed() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        EComponent com = getFocusManager().getFocus();
        if (com == null) {
            return;
        }

        //ץȡ��;
        if (com instanceof ECapture) {
            if (((ECapture) com).isLocked()) {
                return;
            }
        }

        //����Ԫ������ɾ��;
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
        //����
        update();
    }

    /**
     * �򿪺��������ԶԻ���
     */
    public void onOpenMicroFieldProperty() {
        getMicroFieldManager().openPropertyDialog();
    }

    /**
     * ���ú�
     * @param name String ����
     * @param value String ��ʾֵ
     */
    public void setMicroField(String name, String value) {
        getMicroFieldManager().setMicroField(name, value);
    }

    /**
     * ���ú�Code
     * @param name String ����
     * @param value String ��ʾֵ
     */
    public void setMicroFieldCode(String name, String value, String code) {
        getMicroFieldManager().setMicroField(name, value, code);
    }

    /**
     * �õ���
     * @param name String ����
     * @return String
     */
    public String getMicroField(String name) {
        return getMicroFieldManager().getMicroField(name);
    }

    /**
     * ���öԻ��򿪹�
     * @param messageBoxSwitch boolean
     */
    public void setMessageBoxSwitch(boolean messageBoxSwitch) {
        getFileManager().setMessageBoxSwitch(messageBoxSwitch);
    }

    /**
     * �õ��Ի��򿪹�
     * @return boolean
     */
    public boolean getMessageBoxSwitch() {
        return getFileManager().getMessageBoxSwitch();
    }

    //===============================�������========================//
    /**
     * ������(��ǰλ��)
     * @param rowCount int ����
     * @param columnCount int ����
     * @return ETable
     */
    public ETable insertTable(int rowCount, int columnCount) {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        ETable table = getFocusManager().insertTable(rowCount, columnCount);
        //����
        update();
        return table;
    }

    /**
     * �������Table�Ի���
     * @return ETable
     */
    public ETable insertBaseTableDialog() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        ETable table = getFocusManager().insertBaseTableDialog();
        if (table == null) {
            return null;
        }
        //����
        update();
        return table;
    }

    /**
     * ɾ�����
     */
    public void deleteTable() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        ETable table = getFocusManager().getFocusTable();
        if (table == null) {
            return;
        }
        table.removeThisAll();
        //����
        update();
    }

    /**
     * ��������
     * @return ETR
     */
    public ETR insertTR() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        ETR tr = getFocusManager().insertTR();
        if (tr == null) {
            return null;
        }
        //����
        update();
        return tr;
    }

    /**
     * ׷�ӱ����
     * @return ETR
     */
    public ETR appendTR() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        ETR tr = getFocusManager().appendTR();
        if (tr == null) {
            return null;
        }
        //����
        update();
        return tr;
    }

    /**
     * ɾ�������
     * @return boolean
     */
    public boolean deleteTR() {
        //���ܱ༭
        if (!canEdit()) {
            return false;
        }
        if (!getFocusManager().deleteTR()) {
            return false;
        }
        //����
        update();
        return true;
    }

    //===============================�ļ�������========================//
    /**
     * �õ����ļ���λ��
     * @return int
     * -1 ��ȷ��
     * 0 ����
     * 1 AppServer
     * 2 FileServer Emrģ���ļ�
     * 3 FileServer Emr�����ļ�
     */
    public int getFileOpenServerType() {
        return getFileManager().getOpenServerType();
    }

    /**
     * �õ����ļ���·��
     * @return String
     */
    public String getFileOpenPath() {
        return getFileManager().getPath();
    }

    /**
     * �õ����ļ����ļ���
     * @return String
     */
    public String getFileOpenName() {
        return getFileManager().getFileName();
    }

    /**
     * �����ļ�������
     * @param author String
     */
    public void setFileAuthor(String author) {
        getFileManager().setAuthor(author);
    }

    /**
     * �õ��ļ�������
     * @return String
     */
    public String getFileAuthor() {
        return getFileManager().getAuthor();
    }

    /**
     * �����ļ��Ĺ�˾
     * @param co String
     */
    public void setFileCo(String co) {
        getFileManager().setCo(co);
    }

    /**
     * �õ��ļ��Ĺ�˾
     * @return String
     */
    public String getFileCo() {
        return getFileManager().getCo();
    }

    /**
     * �����ļ��Ĵ�������
     * @param createDate String
     */
    public void setFileCreateDate(String createDate) {
        getFileManager().setCreateDate(createDate);
    }

    /**
     * �õ��ļ��Ĵ�������
     * @return String
     */
    public String getFileCreateDate() {
        return getFileManager().getCreateDate();
    }

    /**
     * �����ļ��ı���
     * @param title String
     */
    public void setFileTitle(String title) {
        getFileManager().setTitle(title);
    }

    /**
     * �õ��ļ��ı���
     * @return String
     */
    public String getFileTitle() {
        return getFileManager().getTitle();
    }

    /**
     * ����Ӣ�ı���
     * @param enTitle String
     */
    public void setFileEnTitle(String enTitle) {
        getFileManager().setEnTitle(enTitle);
    }

    /**
     * �õ�Ӣ�ı���
     * @return String
     */
    public String getFileEnTitle() {
        return getFileManager().getEnTitle();
    }

    /**
     * �����ļ��ı�ע
     * @param remark String
     */
    public void setFileRemark(String remark) {
        getFileManager().setRemark(remark);
    }

    /**
     * �õ��ļ��ı�ע
     * @return String
     */
    public String getFileRemark() {
        return getFileManager().getRemark();
    }

    /**
     * �����ļ��༭��
     * @param editLock boolean
     */
    public void setFileEditLock(boolean editLock) {
        getFileManager().setEditLock(editLock);
    }

    /**
     * �ļ��Ƿ��б༭��
     * @return boolean
     */
    public boolean isFileEditLock() {
        return getFileManager().isEditLock();
    }

    /**
     * �����ļ������û�
     * @param lockUser String
     */
    public void setFileLockUser(String lockUser) {
        getFileManager().setLockUser(lockUser);
    }

    /**
     * �õ��ļ������û�
     * @return String
     */
    public String getFileLockUser() {
        return getFileManager().getLockUser();
    }

    /**
     * �����ļ���������
     * @param lockDate String
     */
    public void setFileLockDate(String lockDate) {
        getFileManager().setLockDate(lockDate);
    }

    /**
     * �õ��ļ���������
     * @return String
     */
    public String getFileLockDate() {
        return getFileManager().getLockDate();
    }

    /**
     * �����ļ���������
     * @param lockDept String
     */
    public void setFileLockDept(String lockDept) {
        getFileManager().setLockDept(lockDept);
    }

    /**
     * �õ��ļ���������
     * @return String
     */
    public String getFileLockDept() {
        return getFileManager().getLockDept();
    }

    /**
     * �趨�ļ�����IP
     * @param lockIP String
     */
    public void setFileLockIP(String lockIP) {
        getFileManager().setLockIP(lockIP);
    }

    /**
     * �õ��ļ�����IP
     * @return String
     */
    public String getFileLockIP() {
        return getFileManager().getLockIP();
    }

    /**
     * �����ļ�����޸���
     * @param lastEditUser String
     */
    public void setFileLastEditUser(String lastEditUser) {
        getFileManager().setLastEditUser(lastEditUser);
    }

    /**
     * �õ��ļ�����޸���
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
     * �����ļ�����޸�����
     * @param lastEditDate String
     */
    public void setFileLastEditDate(String lastEditDate) {
        getFileManager().setLastEditDate(lastEditDate);
    }

    /**
     * �õ��ļ�����޸�����
     * @return String
     */
    public String getFileLastEditDate() {
        return getFileManager().getLastEditDate();
    }

    /**
     * �����ļ�����޸�IP
     * @param lastEditIP String
     */
    public void setFileLastEditIP(String lastEditIP) {
        getFileManager().setLastEditIP(lastEditIP);
    }

    /**
     * �õ��ļ�����޸�IP
     * @return String
     */
    public String getFileLastEditIP() {
        return getFileManager().getLastEditIP();
    }

    /**
     * �����ܷ�༭
     * @param canEdit boolean
     */
    public void setCanEdit(boolean canEdit) {
        getEventManager().setCanEdit(canEdit);
    }

    /**
     * �ܷ�༭
     * @return boolean
     */
    public boolean canEdit() {
        return getEventManager().canEdit();
    }


    /**
     * ����
     */
    public void update() {
        getFocusManager().update();
    }

    /**
     * ����ҳ����߿�ߴ�
     * @param pageBorderSize int
     */
    public void setPageBorderSize(int pageBorderSize) {
        getViewManager().setPageBorderSize(pageBorderSize);
    }

    /**
     * �õ�ҳ����߿�ߴ�
     * @return int
     */
    public int getPageBorderSize() {
        return getViewManager().getPageBorderSize();
    }

    /**
     * ����Ի���
     */
    public void onParagraphDialog() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        getFocusManager().onParagraphDialog();
    }

    /**
     * Table ���ԶԻ���
     */
    public void onTablePropertyDialog() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        getFocusManager().onTablePropertyDialog();
    }

    /**
     * ����
     */
    public void onCut() {
        getFocusManager().onCopy();
    }

    /**
     * ����
     */
    public void onCopy() {
        getFocusManager().onCopy();
    }

    /**
     * ճ��
     */
    public void onPaste() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        if (!getFocusManager().onPaste()) {
            return;
        }
    }

    /**
     * ճ���ַ���
     * @param s String
     * @return boolean
     */
    public boolean pasteString(String s) {
        //���ܱ༭
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
     * ɾ��
     */
    public void onDelete() {
        if (!getFocusManager().onDelete()) {
            return;
        }
        update();
    }

    /**
     * ����Text
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
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name, int type) {
        return getPageManager().findObject(name, type);
    }

    /**
     * ���˶���
     * @param name String
     * @param type int
     * @return List
     */
    public List filterObject(String name, int type) {
        return getPageManager().filterObject(name, type);
    }

    /**
     * ������
     * @param group String
     * @return EComponent
     */
    public EComponent findGroup(String group) {
        return getPageManager().findGroup(group);
    }

    /**
     * �õ���ֵ
     * @param group String
     * @return String
     */
    public String findGroupValue(String group) {
        return getPageManager().findGroupValue(group);
    }

    /**
     * ����ץȡ����
     * @param name String
     * @return ECapture
     */
    public ECapture findCapture(String name) {
        return (ECapture) findObject(name, EComponent.CAPTURE_TYPE);
    }

    /**
     * ���ҹ̶��ı�����
     * @param name String
     * @return EFixed
     */
    public EFixed findFixed(String name) {
        return (EFixed) findObject(name, EComponent.FIXED_TYPE);
    }

    /**
     * ���˹̶��ı�
     * @param name String
     * @return List
     */
    public List filterFixed(String name) {
        return filterObject(name, EComponent.FIXED_ACTION_TYPE);
    }

    /**
     * ���ץȡ�����ڵ�Ԫ��
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
     * ץȡ����
     * @param name String ����
     * @return String
     */
    public String getCaptureValue(String name) {

    	 return this.getCaptureValue(name, false);
    }

    /**
     * ץȡ����<��Ҫ��ɾ���ߵ�>
     * @param name String ����
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
            //System.out.println("err:[" + name + "]û���ҵ�����");
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
     * ������ץȡ����
     * @param name String
     * @return boolean
     */
    public boolean focusInCaptue(String name) {
        return getFocusManager().focusInCaptue(name);
    }

    /**
     * ����ͼ����ʾ
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
     * ͼ���Ƿ���ʾ
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
     * �༭״̬
     */
    public void onEditWord() {
        getFocusManager().onEditWord();
        update();
    }

    /**
     * ����
     */
    public void onPreviewWord() {
        getFocusManager().onPreviewWord();
        update();
    }

    /**
     * �Զ���ű��Ի���
     */
    public void onCustomScriptDialog() {
        getSyntaxManager().onCustomScriptDialog();
    }

    /**
     * �ϲ���Ԫ��
     */
    public void onUniteTD() {
        if (!getFocusManager().onUniteTD()) {
            return;
        }
        update();
    }

    /**
     * ���ļ�
     */
    public void onNewFile() {
        getFileManager().onNewFile();
    }

    /**
     * �����ļ��Ի���
     */
    public void onInsertFileDialog() {
        getFocusManager().onInsertFileDialog();
    }

    /**
     * �����ļ�
     * @param path String
     * @param fileName String
     * @param type int
     * @param state boolean
     * @return boolean
     * ����        onInsertFile("%ROOT%\\config\\prt","a1.jhw",1,false);
     */
    public boolean onInsertFile(String path, String fileName, int type,
                                boolean state) {
        return getFocusManager().onInsertFile(path, fileName, type, state);
    }

    /**
     * �����ļ��ڹ̶��ı�֮ǰ
     * @param name String
     * @param path String
     * @param fileName String
     * @param type int
     * @param state boolean
     * ����        onInsertFileFrontFixed("AAA","%ROOT%\\config\\prt","a1.jhw",1,false);
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
     * �����ļ��ڹ̶��ı�֮��
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
     * �ָ����
     * @return boolean
     */
    public boolean separatePanel() {
        return getFocusManager().separatePanel();
    }

    /**
     * ���ñ༭��ż�¼
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
     * �õ��༭��ż�¼
     * @return int
     */
    public int getNodeIndex() {
        return getPM().getModifyNodeManager().getIndex();
    }

    /**
     * ����ͼƬ�༭��
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
     * ɾ��ͼƬ�༭��
     */
    public void deleteImage() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        EImage image = getFocusManager().getFocusImage();
        if (image == null) {
            return;
        }
        image.removeThis();
        //����
        update();
    }

    /**
     * �����
     */
    public void insertGBlock() {
        //���ܱ༭
        if (!canEdit()) {
            return;
        }
        getFocusManager().setInsertImageAction(1);
    }

    /**
     * ������
     */
    public void insertGLine() {
        if (!canEdit()) {
            return;
        }
        getFocusManager().setInsertImageAction(2);
    }

    /**
     * ������ߴ�
     * @param index int
     */
    public void onSizeBlockMenu(int index) {
        EImage image = getFocusManager().getFocusImage();
        if (image != null) {
            image.onSizeBlockMenu(index);
        }
    }

    /**
     * ͼ������
     */
    public void onDIVProperty() {
        getPageManager().getMVList().openProperty();
    }

    /**
     * �Ա����
     * @param control int
     */
    public void setSexControl(int control) {
        getPM().setSexControl(control);
        update();
    }

    /**
     * �̶��ı�����ץȡ����
     * @param mrNo String
     * @param caseNo String
     */
    public void fixedTryReset(String mrNo, String caseNo) {
        getPM().fixedTryReset(mrNo, caseNo);
    }

    /**
     * �����˵�
     * @param syntax String �﷨
     * @param messageIO DMessageIO ��������
     */
    public void popupMenu(String syntax, DMessageIO messageIO) {
        getFocusManager().popupMenu(syntax, messageIO);
    }

    /**
     * д��������
     * @return ETextFormat
     */
    public ETextFormat insertTextFormat() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertTextFormat("������", "");
    }

    /**
     * д��������
     * @param name String
     * @param text String
     * @return ETextFormat
     */
    public ETextFormat insertTextFormat(String name, String text) {
        ETextFormat eTextFormat = getFocusManager().insertTextFormat(name, text);
        //����
        update();
        return eTextFormat;
    }


    public EAssociateChoose insertAssociateChoose() {
        //���ܱ༭
        if (!canEdit()) {
            return null;
        }
        return insertAssociateChoose("����ѡ��", "����ѡ��");
    }

    public EAssociateChoose insertAssociateChoose(String name, String text) {
        EAssociateChoose associateChoose = getFocusManager().
                                           insertAssociateChoose(name, text);
        //����
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
     * ���ʽ����
     * < 1.������ʽ�ؼ�ʱ 2.��ȡ�ı�����ʱ --����� >
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
     * ���±�������ԶԻ���
     */
    public void onOpenMarkProperty() {

        //���ܱ༭
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
