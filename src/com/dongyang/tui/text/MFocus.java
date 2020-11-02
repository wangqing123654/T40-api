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
 * <p>Title: ���������</p>
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
     * ������
     */
    private PM pm;
    /**
     * �����˸�߳�
     */
    private CursorThread cursorThread;
    /**
     * �����˸����ʱ��
     */
    private int cursorSleepCount;
    /**
     * ��ʾ���
     */
    private boolean showCursor;
    /**
     * ѡ�ж���
     */
    private CSelectedModel selectedModel;
    /**
     * �ƶ�Table
     */
    private ETable moveTable;
    /**
     * �ƶ�Table�ߴ�
     */
    private int moveTableIndex;
    /**
     * �ƶ�Table X����
     */
    private int moveTableX;
    /**
     * �ӿ�
     */
    private TParm ioParm;
    /**
     * ����ͼƬ�鶯��
     */
    private int insertImageAction;
    /**
     * ������
     */
    public MFocus() {
        //��ʼ���ӿ�
        setIOParm(new TParm());
        //��ʼ��ѡ�ж���
        setSelectedModel(new CSelectedModel(this));
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
     * �õ�UI
     * @return DText
     */
    public DText getUI() {
        return getPM().getUI();
    }

    /**
     * �õ�ҳ�������
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
     * �õ���ͼ������
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
    }

    /**
     * �õ��¼�������
     * @return MEvent
     */
    public MEvent getEventManager() {
        return getPM().getEventManager();
    }

    /**
     * �õ���ǰҳ
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
     * ��������
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
     * ����
     */
    public void update() {
        //getPM().getPageManager().debugShow();//temp
        getPageManager().reset();
        getUI().repaint();
    }

    /**
     * ��ʾ���
     */
    public void showCursor() {
        showCursor = true;
        cursorSleepCount = 0;
        repaintCursor();
    }

    /**
     * �Ƿ���ʾ���
     * @return boolean
     */
    public boolean isShowCursor() {
        return showCursor;
    }

    /**
     * ���»��ƹ��
     */
    public void repaintCursor() {
        if (getUI() == null) {
            return;
        }
        //��ʱ
        getUI().repaint();
    }

    /**
     * �õ�����
     */
    public void focusGained() {
        cursorThread = new CursorThread();
        cursorThread.start();
        showCursor = true;
        cursorSleepCount = 0;
        repaintCursor();
    }

    /**
     * ʧȥ����
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
     * ���ö���λ��(��ǰλ��)
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
     * �ָ�
     * @return boolean true �ɹ� false ʧ��
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
            //���Կ���������return false;
            return false;
        }
        eText.separate(getFocusIndex());
        setFocusText(eText.getNextText());
        return true;
    }

    /**
     * �ָ����
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
     * �����
     * @param name String ������
     * @param script String �ű�
     * @param type int ����
     * @return EMacroroutine
     */
    public EMacroroutine insertMacroroutine(String name, String script,
                                            int type) {
        //�ָ�
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
        //�����﷨
        ESyntaxItem syntax = model.createSyntax();
        syntax.setName(name);
        syntax.setSyntax(script);
        syntax.setType(type);
        syntax.setClassify(1);
        //�������
        if (!syntax.getManager().make()) {
            getUI().messageBox(syntax.getManager().getMakeErrText());
        }
        model.show();
        return macroroutine;
    }

    /**
     * ����̶��ı�
     * @param name String ����
     * @param text String ��ʾ����
     * @return EFixed
     */
    public EFixed insertFixed(String name, String text) {
        //�ָ�
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
     * ���뵥ѡ
     * @param name String ����
     * @param text String ��ʾ����
     * @return ESingleChoose
     */
    public ESingleChoose insertSingleChoose(String name, String text) {
        //�ָ�
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
     * �����ѡ
     * @param name String ����
     * @param text String ��ʾ����
     * @return EMultiChoose
     */
    public EMultiChoose insertMultiChoose(String name, String text) {
        //�ָ�
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
     * ��������ѡ��
     * @param name String ����
     * @param text String ��ʾ����
     * @return EHasChoose
     */
    public EHasChoose insertHasChoose(String name, String text) {
        //�ָ�
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
     * �����
     * @param name String ����
     * @param text String ��ʾ����
     * @return EMicroField
     */
    public EMicroField insertMicroField(String name, String text) {
        //�ָ�
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
	 * ����ǩ��
	 * 
	 * @param groupName
	 *            Ԫ������
	 * @param name
	 *            Ԫ����
	 * @param signCode
	 *            ǩ��
	 * @param text
	 *            ǩ����ʾ����
	 * @param timestmp
	 *            ǩ��ʱ���
	 * @return
	 */
	public ESign insertSign(String groupName, String name, String signCode,
			String text, String timestmp) {
		// �ָ�
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
		sign.setActionType("HR22.01.100");//ǩ��
		//
		return sign;
	}

    /**
     * ����ץȡ
     * @param name String
     * @param type int
     * @return ECapture
     */
    public ECapture insertCaptureObject(String name, int type) {
        //�ָ�
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
     * ����ѡ��
     * @param name String
     * @param text String
     * @return ECapture
     */
    public ENumberChoose insertNumberChoose(String name, String text) {
        //�ָ�
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
     * ����ѡ���
     * @param name String
     * @param text String
     * @param isChecked boolean
     * @return ECheckBoxChoose
     */
    public ECheckBoxChoose insertCheckBoxChoose(String name, String text,
                                                boolean isChecked) {
        //�ָ�
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
     * ����������ʾ���
     * @param name String ����
     * @param text String ��ʾ����
     * @return EInputText
     */
    public EInputText insertInputText(String name, String text) {
        //�ָ�
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
     * ����ͼ��
     * @return EPic
     */
    public EPic insertPic() {
        //�ָ�
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
     * ������(��ǰλ��)
     * @return ETable
     */
    public ETable insertTable() {
        EComponent component = getFocus();
        if (component == null || !(component instanceof EText)) {
            return null;
        }
        EText eText = (EText) component;
        //��ֹ�ڱ�����ڲ�����
        if (eText.getPanel().parentIsTD()) {
            messageBox("����в����ڲ�����!");
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
        //�������λ�������
        ePanel.setType(1);
        //����ģ��
        table.createModel();
        ePanel.setModify(true);
        //ɾ����ҳtableΪ�׿ؼ�֮ǰ�Ŀ���
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
     * �������Table�Ի���
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
     * ������(��ǰλ��)
     * @param rowCount int ����
     * @param columnCount int ����
     * @return ETable
     */
    public ETable insertTable(int rowCount, int columnCount) {
        return insertTable(null, rowCount, columnCount);
    }

    /**
     * ������(��ǰλ��)
     * @param table ETable
     * @param rowCount int
     * @param columnCount int
     * @return ETable
     */
    public ETable insertTable(ETable table, int rowCount, int columnCount) {
        if (table == null) {
            //����Table
            table = insertTable();
        }
        if (table == null) {
            return null;
        }
        //�õ�ģ��
        ETableModel model = table.getModel();
        //��ʼ������
        model.init(columnCount);
        //��������
        for (int i = 0; i < rowCount; i++) {
            table.insertRow(i);
        }
        return table;
    }

    /**
     * �õ�����Table
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
     * �ָ�
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
     * �ָ�ѡ��
     */
    public void separateSelect() {
        /*if(selectTextList == null)
            return;
         selectTextList.set(0,separate((EText)selectTextList.get(0),1));
                 selectTextList.set(selectTextList.size() - 1,separate((EText)selectTextList.get(selectTextList.size() - 1),2));*/
    }

    /**
     * �޸�����
     * @param s String
     * @return boolean
     */
    public boolean modifyFont(String s) {
        exeAction(new EAction(EAction.FONT_NAME, s));
        return true;
    }

    /**
     * �޸�����б��
     * @param bold boolean
     * @return boolean
     */
    public boolean modifyBold(boolean bold) {
        exeAction(new EAction(EAction.FONT_BOLD, bold));
        return true;
    }

    /**
     * �޸�����ߴ�
     * @param size int
     * @return boolean
     */
    public boolean modifyFontSize(int size) {
        exeAction(new EAction(EAction.FONT_SIZE, size));
        return true;
    }

    /**
     * �޸�����б��
     * @param italic boolean
     * @return boolean
     */
    public boolean modifyItalic(boolean italic) {
        exeAction(new EAction(EAction.FONT_ITALIC, italic));
        return true;
    }

    /**
     * ɾ������
     * @param flg int
     * 0 ����ɾ��
     * 1 ��ͨɾ��
     * 2 ���
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
     * �����ƶ�Table
     * @param moveTable ETable
     */
    public void setMoveTable(ETable moveTable) {
        this.moveTable = moveTable;
    }

    /**
     * �õ��ƶ�Table
     * @return ETable
     */
    public ETable getMoveTable() {
        return moveTable;
    }

    /**
     * �����ƶ�Table�ߴ�
     * @param moveTableIndex int
     */
    public void setMoveTableIndex(int moveTableIndex) {
        this.moveTableIndex = moveTableIndex;
    }

    /**
     * �õ��ƶ�Table�ߴ�
     * @return int
     */
    public int getMoveTableIndex() {
        return moveTableIndex;
    }

    /**
     * �����ƶ�Table X����
     * @param moveTableX int
     */
    public void setMoveTableX(int moveTableX) {
        this.moveTableX = moveTableX;
    }

    /**
     * �õ��ƶ�Table X����
     * @return int
     */
    public int getMoveTableX() {
        return moveTableX;
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
     * �õ����ű���
     * @return double
     */
    public double getZoom() {
        return getViewManager().getZoom();
    }

    /**
     * ����ҳ��
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
     * �༭
     */
    public void onEditWord() {
        getViewManager().setPreview(false);
        getPM().getCTableManager().showEdit();
        getPM().getMacroroutineManager().edit();
        getPM().getPageManager().resetData(new EAction(EAction.EDIT_STATE));
        getEventManager().setCanEdit(true);
    }

    /**
     * ����
     */
    public void onPreviewWord() {
        //���ѡ��
        clearSelected();
        getViewManager().setPreview(true);
        getPM().getCTableManager().retrieve();
        getPM().getCTableManager().showData();
        getPM().getMacroroutineManager().run();
        getPM().getPageManager().resetData(new EAction(EAction.PREVIEW_STATE));
        getEventManager().setCanEdit(false);
        //�����¼�������
        getPM().setEventManager(new MEvent());
        //getPM().setStyleManager(new MStyle());
        getPM().setFocusManager(new MFocus());
        //
        update();
    }

    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action) {
        getPageManager().resetData(action);
    }

    /**
     * �����Ի�����ʾ��Ϣ
     * @param message Object
     */
    public void messageBox(Object message) {
        getPM().getUI().messageBox(message);
    }

    /**
     * ��������
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
     * ׷�ӱ����
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
     * ɾ�������
     * @return boolean
     */
    public boolean deleteTR() {
        ETR tr = getFocusTR();
        if (tr == null) {
            return false;
        }
        tr = tr.getHeadTR();
        ETable table = tr.getTable();
        //Table��ɾ�����ý���
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
     * ����Ի���
     */
    public void onParagraphDialog() {
        if (getFocus() == null) {
            return;
        }
        getUI().openDialog("%ROOT%\\config\\database\\ParagraphEdit.x", this);
    }

    /**
     * Table ���ԶԻ���
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
     * �ܷ�༭
     * @return boolean
     */
    public boolean canEdit() {
        return getEventManager().canEdit();
    }

    /**
     * ����
     * @return boolean
     */
    public boolean onCopy() {
        //$$======Modified by lx 2011-09-06 ����ṹ�������ؼ����ƹ���START===========$$//
        //ÿ�����¸��ƿؼ�
        CopyOperator.clearComList();
        //$$======Modified by lx 2011-09-06 ����ṹ�������ؼ����ƹ���END===========$$//
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
        //�����
        if(getViewManager().isPreview()){
            copyOperator.setClipbord(true);
        }else{
            copyOperator.setClipbord(false);
        }
        
        // add by wangb 2017/09/07 ���������ݸ���ʱ���뵱ǰ�����Ĳ����ţ��Ա�ʹ��ճ������ʱ�ж��Ƿ�粡��ճ�� STRAT
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
		// add by wangb 2017/09/07 ���������ݸ���ʱ���뵱ǰ�����Ĳ����ţ��Ա�ʹ��ճ������ʱ�ж��Ƿ�粡��ճ�� END
        return true;
    }

    /**
     * ����
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
     * ճ��
     * @return boolean
     */
    public boolean onPaste() {
        //���ܱ༭
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
        
        // add by wangb 2017/09/07 ����ճ�����ƣ����ܿ粡��ճ�� STRAT
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
					// ���а��еĲ���������͵�ǰ���������Ĳ����Ų�ͬ��������ճ��
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
        // add by wangb 2017/09/07 ����ճ�����ƣ����ܿ粡��ճ�� END
        
        //$$====== modified by lx 2011-09-05 ���ƽṹ��ģ��Ŀؼ�START=========== $$//
        //���ڿؼ����ƶ�����ճ���ؼ�
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
        //$$====== modified by lx 2011-09-05 ���ƽṹ��ģ��Ŀؼ�END=========== $$//
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
     * ɾ������
     * @return boolean
     */
    public boolean onDelete() {
        //ɾ��ѡ��
        if (!delete(1)) {
            //ɾ����
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
     * ����ѡ�ж���
     * @param selectedModel CSelectedModel
     */
    public void setSelectedModel(CSelectedModel selectedModel) {
        this.selectedModel = selectedModel;
    }

    /**
     * �õ�ѡ�ж���
     * @return CSelectedModel
     */
    public CSelectedModel getSelectedModel() {
        return selectedModel;
    }

    /**
     * ���ý���
     * @param type int
     * @param focus EComponent
     */
    public void setFocus(int type, EComponent focus) {
        setFocus(type, focus, -1);
    }

    /**
     * �����ı�����
     * @param text EText
     */
    public void setFocusText(EText text) {
        setFocusText(text, 0);
    }

    /**
     * �����ı�����
     * @param text EText
     * @param index int
     */
    public void setFocusText(EText text, int index) {
        setFocus(1, text, index);
    }

    /**
     * ����ͼƬ����
     * @param pic EPic
     */
    public void setFocusPic(EPic pic) {
        setFocus(4, pic);
    }

    /**
     * ����ͼƬ�༭������
     * @param image EImage
     */
    public void setFocusImage(EImage image) {
        setFocus(5, image);
    }

    /**
     * ѡ�б��
     * @param tr ETR
     */
    public void setFocusTR(ETR tr) {
        setFocus(2, tr);
    }

    /**
     * ���ñ���лس�����
     * @param tr ETR
     */
    public void setFocusTRE(ETR tr) {
        setFocus(3, tr);
    }

    /**
     * ���ý���
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
     * �õ���������
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
     * �õ�����
     * @return EComponent
     */
    public EComponent getFocus() {
        return getSelectedModel().getFocus();
    }

    /**
     * �õ�����Text
     * @return EText
     */
    public EText getFocusText() {
        if (getFocusType() != 1) {
            return null;
        }
        return (EText) getFocus();
    }

    /**
     * �õ�����TR
     * @return ETR
     */
    public ETR getFocusTR() {
        if (getFocusType() != 2) {
            return null;
        }
        return (ETR) getFocus();
    }

    /**
     * �õ�����Image
     * @return EImage
     */
    public EImage getFocusImage() {
        if (getFocusType() != 5) {
            return null;
        }
        return (EImage) getFocus();
    }

    /**
     * �õ�����лس�����
     * @return ETR
     */
    public ETR getFocusTRE() {
        if (getFocusType() != 3) {
            return null;
        }
        return (ETR) getFocus();
    }

    /**
     * ���ý���λ��
     * @param index int
     */
    public void setFocusIndex(int index) {
        getSelectedModel().setFocusIndex(index);
    }

    /**
     * �õ�����λ��
     * @return int
     */
    public int getFocusIndex() {
        return getSelectedModel().getFocusIndex();
    }

    /**
     * ���ÿ�ʼѡ��
     */
    public void setStartSelected() {
        getSelectedModel().setStartSelected();
    }

    /**
     * ���ý���ѡ��
     */
    public void setEndSelected() {
        getSelectedModel().setEndSelected();
    }

    /**
     * ���ѡ��
     */
    public void clearSelected() {
        getSelectedModel().clearSelected();
    }

    /**
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isSelected() {
        return getSelectedModel().isSelected();
    }

    /**
     * ѹ��ѡ���б�
     */
    public void putSelected() {
        getSelectedModel().putSelected();
    }

    /**
     * �õ��������
     * @return TList
     */
    public TList getFocusPanels() {
        return getSelectedModel().getFocusPanels();
    }

    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action) {
        getSelectedModel().exeAction(action);
    }

    /**
     * �õ����������
     * @return String
     */
    public String getFocusLayerString() {
        return getSelectedModel().getFocusLayerString();
    }

    /**
     * �����Ƿ��ڸ������
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
     * �ϲ���Ԫ��
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
     * ճ���ַ���
     * @param s String
     * @return boolean
     */
    public boolean pasteString(String s) {
        if (s == null || s.length() == 0) {
            return false;
        }
        //�������ѡ�������
        if (isSelected()) {
            delete(1);
        }
        
        // add by wangb 2017/09/08 ȥ��ճ�������еĲ�����ע��
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
     * ճ���ַ���
     * @param s String
     * @return boolean
     */
    public boolean pasteString(String s,DStyle defineStyle) {
        if (s == null || s.length() == 0) {
            return false;
        }
        //�������ѡ�������
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
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name, int type) {
        return getPageManager().findObject(name, type);
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
     * ������ץȡ����
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
     * ����ͼƬ
     * @param image BufferedImage
     * @return EMacroroutine
     */
    public EMacroroutine imsertPricture(BufferedImage image) {
        if (image == null) {
            return null;
        }
        getPM().getImageManager().add(image);
        int imageIndex = getPM().getImageManager().size() - 1;
        EMacroroutine macroroutine = insertMacroroutine("ͼƬ", "", 1);
        macroroutine.getModel().setLockSize(true);
        macroroutine.getModel().setWidth((int) (image.getWidth() * 0.75));
        macroroutine.getModel().setHeight((int) (image.getHeight() * 0.75));
        macroroutine.setIsPic(true);
        MV mv = new MV();
        macroroutine.getModel().getMVList().add(mv);
        mv.setName("ͼ��");
        VPic pic = mv.addPic(0, 0, 0, 0);
        pic.setName("ͼƬ");
        pic.setX2B(true);
        pic.setY2B(true);
        pic.setIconIndex(imageIndex);
        //pic.setIcon(new ImageIcon(image));
        pic.setScale(0.75);
        pic.setPictureName("");
        pic.setAutoScale(true);
        pic.getMV().removeAll();
        //����
        update();
        return macroroutine;
    }

    /**
     * ����ͼƬ
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture() {
        Object[] value = (Object[]) getPM().getUI().openDialog(
                "%ROOT%\\config\\database\\PictureEditDialog.x");
        if (value == null) {
            return null;
        }
        //�����
        EMacroroutine macroroutine = insertMacroroutine(TypeTool.getString(
                value[0]), "", 1);
        macroroutine.getModel().setLockSize(true);
        macroroutine.getModel().setWidth(TypeTool.getInt(value[2]));
        macroroutine.getModel().setHeight(TypeTool.getInt(value[3]));
        macroroutine.setIsPic(true);
        MV mv = new MV();
        macroroutine.getModel().getMVList().add(mv);
        mv.setName("ͼ��");
        VPic pic = mv.addPic(0, 0, 0, 0);
        pic.setName("ͼƬ");
        pic.setX2B(true);
        pic.setY2B(true);
        pic.setPictureName(TypeTool.getString(value[1]));
        pic.setAutoScale(true);
        if (value.length >= 5) {
            pic.setIcon((Icon) value[4]);
        }
        //����
        update();
        return macroroutine;
    }
    /**
     *
     * @param value Object[]0 ·��,1 name, 2 width,3 height
     * @return EMacroroutine
     */
    public EMacroroutine insertPicture(Object[] value) {
        //�����
        EMacroroutine macroroutine = insertMacroroutine(TypeTool.getString(
                value[0]), "", 1);
        macroroutine.getModel().setLockSize(true);
        macroroutine.getModel().setWidth(TypeTool.getInt(value[2]));
        macroroutine.getModel().setHeight(TypeTool.getInt(value[3]));
        macroroutine.setIsPic(true);
        MV mv = new MV();
        macroroutine.getModel().getMVList().add(mv);
        mv.setName("ͼ��");
        VPic pic = mv.addPic(0, 0, 0, 0);
        pic.setName("ͼƬ");
        pic.setX2B(true);
        pic.setY2B(true);
        pic.setPictureName(TypeTool.getString(value[1]));
        pic.setAutoScale(true);
        if (value.length >= 5) {
            pic.setIcon((Icon) value[4]);
        }
        //����
        update();
        return macroroutine;

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
        if (!canEdit()) {
            return false;
        }
        //�½�������
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
     * �����ļ��Ի���
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
     * �����ļ�
     * @param pm PM
     */
    public void insertFile(PM pm) {
        //�ָ�
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
        //����
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
     * ����ͼƬ�༭
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
     * ���ò���ͼƬ�鶯��
     * @param insertImageAction int
     */
    public void setInsertImageAction(int insertImageAction) {
        this.insertImageAction = insertImageAction;
    }

    /**
     * �õ�����ͼƬ�鶯��
     * @return int
     */
    public int getInsertImageAction() {
        return insertImageAction;
    }

    /**
     * �����˵�
     * @param syntax String �﷨
     * @param messageIO DMessageIO ��������
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
     * ���������
     * @param name String
     * @param text String
     * @return ETextFormat
     */
    public ETextFormat insertTextFormat(String name, String text) {
        //�ָ�
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
     * ���һ�����ı�
     * @param focusText ��ǰ�����ı�
     * @return
     */
    public EText addText(EText focusText) {

    	//�ָ�
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
     * ճ���ؼ�����
     */
    private void pasteComponent() {
        List components = CopyOperator.getComList();
        insertComponents(components,null);
    }

    /**
     * ��ǰ�����²���Ԫ��
     * @param components List
     */
    public void insertComponents(List components,DStyle defineStyle) {
        for (int i = 0; i < components.size(); i++) { 
            Object com = components.get(i);
            String componentClass = com.getClass().toString();           

            //�̶��ı�
            if (componentClass.equals( EFixed.class.toString()) ) {
            	EFixed ef = (EFixed) com;
            	//��ɾ������������
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

            //���ı�
            else if (componentClass.equals( EText.class.toString() )) {
            	//System.out.println("EText===="+((EText) com).getBlockValue());
            	//��ɾ������������
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

            //��ѡ
            else if (componentClass.equals( ESingleChoose.class.toString() )) {
            	//��ɾ������������
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

            //��ѡ
            else if (componentClass.equals( EMultiChoose.class.toString())) {
            	//��ɾ������������
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

            //����ѡ��
            else if (componentClass.equals( EHasChoose.class.toString())) {
            	//��ɾ������������
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

            //��
            else if (componentClass.equals( EMicroField.class.toString())) {
            	//��ɾ������������
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

            //������ʾ���
            else if (componentClass.equals( EInputText.class.toString())) {
            	//��ɾ������������
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

            //ץȡ��
            else if (componentClass.equals( ECapture.class.toString())) {
            	//��ɾ������������
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

            //ѡ���
            else if (componentClass.equals( ECheckBoxChoose.class.toString())) {
            	//��ɾ������������
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

            //����ѡ��
            else if (componentClass.equals( ENumberChoose.class.toString())) {
            	//��ɾ������������
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

            //������
            else if (componentClass.equals( ETextFormat.class.toString())) {
            	//��ɾ������������
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

            //������
            else if (componentClass.equals( EAssociateChoose.class.toString())) {
            	//��ɾ������������
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
                //���뻻��
                if (((String) com).equals(CSelectPanelEnterModel.NEW_LINE)) {
                    this.getPM().getFocusManager().pasteString("\r\n");
                }
            }
        }

    }

    /**
     * �������Ԫ��
     * @param name String
     * @param text String
     * @return EAssociateChoose
     */
    public EAssociateChoose insertAssociateChoose(String name, String text) {
        //�ָ�
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
     * ճ�����а�
     * @return boolean
     */
    public boolean onTextPaste() {
    	 //���ܱ༭
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
        
        // add by wangb 2017/09/07 ����ճ�����ƣ����ܿ粡��ճ�� STRAT
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
					// ���а��еĲ���������͵�ǰ���������Ĳ����Ų�ͬ��������ճ��
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
        // add by wangb 2017/09/07 ����ճ�����ƣ����ܿ粡��ճ�� END

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
        //$$====== modified by lx 2011-09-05 ���ƽṹ��ģ��Ŀؼ�END=========== $$//
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
