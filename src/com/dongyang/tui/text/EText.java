package com.dongyang.tui.text;


import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.util.StringTool;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;

import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.ui.CTable;
import com.dongyang.util.TList;
import com.dongyang.util.TypeTool;
import java.awt.Font;


import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.ToolTipManager;


import com.dongyang.tui.DPoint;

/**
 *
 * <p>Title: �ı�Ԫ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @author whao 2013~
 * @version 1.0
 */
public class EText extends ETextSpecialIO implements EEvent, IExeAction {
    /**
     * ���óߴ����
     */
    private ResetText resetText;
    /**
     * ѡ���������
     */
    private CSelectedTextModel selectedModel;
    /**
     * �к�
     */
    private int rowID;
    /**
     * ������
     * @param panel EPanel
     */
    public EText(EPanel panel) {
        setParent(panel);
        //��ʼ��ѡ������
        setSelectedModel(new CSelectedTextModel(this));
        //����Ĭ����ʽ
        setStyle(getDefaultStyle());
        //�޸ı��
        setModify(true);
        setResetText(new ResetText(this));
    }

    /**
     * �õ����
     * @return EPanel
     */
    public EPanel getPanel() {
        EComponent component = getParent();
        if (component == null) {
            return null;
        }
        if (component instanceof EPanel) {
            return (EPanel) component;
        }
        return null;
    }

    /**
     * �õ�Ĭ�Ϸ��
     * @return DStyle
     */
    public DStyle getDefaultStyle() {
        MStyle style = getStyleManager();
        if (style == null) {
            return null;
        }
        return style.get("default");
    }

    /**
     * �õ��ָ������
     * @param index int
     * @return EText
     */
    public EText getSeparateNewText(int index) {
        return getPanel().newText(index);
    }

    /**
     * �ָ�λ��
     * @param width int
     * @return int
     */
    public int separateIndex(int width) {
        if (!canSeparate()) {
            return -1;
        }
        DStyle style = getStyle();
        if (style == null) {
            return -1;
        }
        return style.separateIndex(getString(), width);
    }

    /**
     * ���ÿ�ʼ����
     * @param com EComponent
     */
    public void setStartFocus(EComponent com) {
        //getFocusManager().setStartFocus(com);
    }

    /**
     * �õ�����
     * @return EComponent
     */
    public EComponent getStartFocus() {
        return null; //getFocusManager().getStartFocus();
    }

    /**
     * �Ƿ���ʾ���
     * @return boolean
     */
    public boolean isShowCursor() {
        return getFocusManager().isShowCursor();
    }

    /**
     * ���ÿ�ʼ����λ��
     * @param focusIndex int
     */
    public void setStartFocusIndex(int focusIndex) {
        //getFocusManager().setStartFocusIndex(focusIndex);
    }

    /**
     * �õ���ʼ����λ��
     * @return int
     */
    public int getStartFocusIndex() {
        return 0; //getFocusManager().getStartFocusIndex();
    }

    /**
     * �õ��ַ������
     * @param s String
     * @return int
     */
    public int stringWidth(String s) {
        if (getStyle() == null) {
            return 0;
        }
        return getStyle().stringWidth(s);
    }

    /**
     * �õ�ָ��λ�õĿ��
     * @param index int
     * @return int
     */
    public int getIndexWidth(int index) {
        return stringWidth(getString(index));
    }

    /**
     * ����
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g, int x, int y, int width, int height) {
        if (getViewManager().isTextDebug()) {
            g.setColor(new Color(0, 255, 0));
            g.drawRect(x, y, width, height);
        }
        if (!isSelectedDraw()) {
            //���ƽ��㱳��
            paintFocusBack(g, x, y, width, height);
        }
        String s = getShowString();
        DStyle style = getStyle();
        int textX = x + (int) (getShowLeftW() * getZoom() / 75.0 + 0.5);
        if (style != null) {
            style.paint(g, s, textX, y, width, height, this);
        }
        if (!isSelectedDraw()) {
            //���ƽ���
            paintFocus(g, textX, y, width, height);
        }
        //��������
        paintOther(g, x, y, width, height);

        //
        //paintRequired(g, x, y, width, height);
    }

    /**
     * �����±����
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param position int 0��,1�ϱ�,2�±�
     */
    public void paint(Graphics g, int x, int y, int width, int height,
                      int position) {
        if (getViewManager().isTextDebug()) {
            g.setColor(new Color(0, 255, 0));
            g.drawRect(x, y, width, height);
        }
        if (!isSelectedDraw()) {
            //���ƽ��㱳��
            paintFocusBack(g, x, y, width, height);
        }
        String s = getShowString();
        DStyle style = getStyle();
        int textX = x + (int) (getShowLeftW() * getZoom() / 75.0 + 0.5);
        if (style != null) {
            style.paint(g, s, textX, y, width, height, this,position);
        }
        if (!isSelectedDraw()) {
            //���ƽ���
            paintFocus(g, textX, y, width, height);
        }
        //��������
        paintOther(g, x, y, width, height);
    }

    /**
	 *
	 * @param g
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	private void paintRequired(Graphics g, int x, int y, int width, int height) {

		int dx = x + 7;
		int dy = y + height - 1;
		int dx2 = x + width - 2;

		dx = width < 14 ? dx - 4 : dx;

		Graphics g2 = g.create();
		g2.setColor(Color.DARK_GRAY);
		drawDashed(g2, dx, dy, dx2, dy);
		// drawDashed(g2,dx, dy+1, dx2,dy+1);
	}

	/**
	 *
	 * @param g
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 */
	private void drawDashed(Graphics g, int x1, int y1, int x2, int y2) {

		int x = x1, y = y1;
		int n = 3; // ʵ�߶γ���
		int m = 3; // ���߶γ���

		int tx = 0, ty = 0;

		int c = 0;
		boolean flag = true; // ��� ��û�л��꣨�ﵽҪ��ĳ��ȣ�

		int mark_x = 0; // ��� Ҫ������ ˮƽ�ߣ�ֵΪ1��
		int mark_y = 0; // ��� Ҫ������ ��ֱ�ߣ�ֵΪ1��
		// Ҫô0��Ҫô 1
		if (x2 - x1 != 0)
			mark_x = 1;
		else
			mark_y = 1;

		do {
			tx = (int) ((c * (n + m) - m) * mark_x + x1);
			ty = (int) ((c * (n + m) - m) * mark_y + y1);

			if (Math.abs(tx - x1) > Math.abs(x2 - x1)) {
				tx = x2;
				flag = false;
			}
			if (Math.abs(ty - y1) > Math.abs(y2 - y1)) {
				ty = y2;
				flag = false;
			}
			g.drawLine(x, y, tx, ty);
			x = (int) (c * (n + m) * mark_x + x1); // ���� ʵ�߶� + ���߶�
			y = (int) (c * (n + m) * mark_y + y1);

			if (x > x2 || y > y2)
				break;
			c++;
		} while (flag);

	}

    /**
	 * ��������
	 *
	 * @param g
	 *            Graphics
	 * @param x
	 *            int
	 * @param y
	 *            int
	 * @param width
	 *            int
	 * @param height
	 *            int
	 */
    public void paintOther(Graphics g, int x, int y, int width, int height) {

    }

    /**
     * ��ӡ
     * @param g Graphics
     * @param x int
     * @param y int
     */
    public void print(Graphics g, int x, int y) {
        DStyle style = getStyle();
        if (style != null) {	
            int textX = x + getShowLeftW();
            style.print(g, getShowString(), textX, y, getWidth(), getHeight(), this);
        }
        //��������
        paintOther(g, x, y, getWidth(), getHeight());
    }

    /**
     * ���ƽ��㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFocusBack(Graphics g, int x, int y, int width, int height) {

    }

    /**
     * ���ƽ���
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFocus(Graphics g, int x, int y, int width, int height) {
        if (getFocus() != this || !isShowCursor() ||
            getFocusManager().isSelected()) {
            return;
        }
        g.setColor(new Color(0, 0, 0));
        int indexWidth = (int) (getIndexWidth(getFocusIndex()) * getZoom() /
                                75.0);
        g.fillRect(x + indexWidth, y, 2, height);
    }

    /**
     * �������
     * @param mouseX int
     * @param mouseY int
     * @return int ��ѡ����
     * -1 û��ѡ��
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     */
    public int onMouseLeftPressed(int mouseX, int mouseY) {
        mouseX = (int) (mouseX / getZoom() * 75.0 + 0.5) - getShowLeftW();
        int index = getStyle().getIndex(getString(), mouseX);
        //���ý���
        setFocus(index);
        return 1;
    }

    /**
     * �Ҽ�����
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX, int mouseY) {

    }

    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX, int mouseY) {
        return false;
    }

    /**
     * ������һ�����
     * @return EText
     */
    public EText getNextText() {
        return getNextText(this, false);
    }

    /**
     * ������һ�����
     * @param text EText
     * @param includeTable boolean
     *  true �ҵ���һ�������Text
     *  false �����һ������Text ���� null
     * @return EText
     */
    public EText getNextText(EText text, boolean includeTable) {
        if (text == null) {
            return null;
        }
        if (getPanel() == null) {
            return null;
        }
        return getPanel().getNextText(text, includeTable);
    }

    /**
     * �õ�ǰһ�����
     * @return EText
     */
    public EText getPreviousText() {
        return getPreviousText(this, false);
    }

    /**
     * ����ǰһ�����
     * @param text EText
     * @param includeTable boolean
     * @return EText
     */
    public EText getPreviousText(EText text, boolean includeTable) {
        if (text == null || getPanel() == null) {
            return null;
        }
        return getPanel().getPreviousText(text, includeTable);
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getPreviousComponent() {
        EPanel panel = getPanel();
        if (panel == null) {
            return null;
        }
        int index = panel.indexOf(this);
        if (index <= 0) {
            return null;
        }

        return panel.get(index - 1);
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getNextComponent() {
        EPanel panel = getPanel();
        if (panel == null) {
            return null;
        }
        int index = panel.indexOf(this);
        if (index >= panel.size() - 1) {
            return null;
        }

        return panel.get(index + 1);
    }

    // �޸ı��
    private Integer modifyIndex = null;
    public Integer getModifyIndex() {
		return modifyIndex;
	}
	public void setModifyIndex(Integer modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

    // ������
    private String isSave = null;
    public String getIsSave() {
		return isSave;
	}
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	/**
     * ճ���ַ�
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c) {


        //���ܱ༭
        if (!canEdit()) {
            return false;
        }

        if (getModifyNodeIndex() == getKeyIndex() && !isDeleteFlg()) {

            this.addModify();

            return pasteChar_(c);
        }

        //
        if( isDeleteFlg() && null== this.getIsSave() && !this.getStyle().isDeleteLine() ){

        	this.setDeleteFlg(false);

            this.addModify();

            return pasteChar_(c);
        }

        if (getKeyIndex() > getModifyNodeIndex()) {
            return false;
        }

        if (this instanceof EFixed) {
            return false;
        }

        int index = getFocusIndex();
        EText text = insertText(index,
                                getStyle().modifyIsLine(true,
                getModifyNodeIndex() + 1));

        text.pasteChar(c);

        return true;
    }

    /**
     * ����޸ļ�¼
     */
    private void addModify() {

		if (getModifyNodeIndex() > -1) {

			String userId = getPM().getUserId();
			String userName = getPM().getUserName();

			if( null!=userId && !userId.equals("") && null!=userName && !userName.equals("") ){

				String dateStr = StringTool.getString(new Date(),
						"yyyy��MM��dd�� HHʱmm��ss��");

				//
				MModifyNode modifyNode = getPM().getModifyNodeManager();
				EModifyInf infDr = new EModifyInf();
				// �޸�ʱ��
				infDr.setModifyDate(dateStr);
				// �޸���
				infDr.setUserID( userId );
				infDr.setUserName( userName );

				modifyNode.add(infDr);
				this.modifyIndex = modifyNode.size();
				infDr.setID(this.modifyIndex);
			}
		}
	}


    /**
	 * �����ı�(ʹ�����޸ļ�¼����char��)
	 *
	 * @param index
	 *            int
	 * @param style
	 *            DStyle
	 * @return EText ��ע:û�п��ǹ̶��ı��ķָ����
	 */
    public EText insertText(int index, DStyle style) {

    	//
    	if (getLength() == 0) {
            setStyle(style);
            setKeyIndex(getModifyNodeIndex());
            return this;
        }

        //
        if (index == 0) {
            IBlock block = getPreviousBlock();
            if (block != null && block instanceof EText) {
                EText t = (EText) block;
                if (t.getStyle().equals(style)) {
                    t.setFocusLast();
                    return t;
                }
            }
            EText text = getPanel().newText(findIndex());
            text.setStart(getStart());
            text.setEnd(getStart() + index);
            setStart(getStart() + index);
            text.setStyle(style);
            if (getPreviousLinkText() != null) {
                getPreviousLinkText().setNextLinkText(null);
                setPreviousLinkText(null);
            }
            text.setKeyIndex(getModifyNodeIndex());
            text.setFocus();
            return text;
        }

        //
        if (index == getLength()) {
            IBlock block = getNextBlock();
            if (block != null && block instanceof EText) {
                EText t = (EText) block;
                if (t.getStyle().equals(style)) {
                    t.setFocus();
                    return t;
                }
            }
            EText text = getPanel().newText(findIndex() + 1);
            text.setStart(getStart() + index);
            text.setEnd(getEnd());
            setEnd(getStart() + index);
            text.setStyle(style);
            if (getNextLinkText() != null) {
                getNextLinkText().setPreviousLinkText(null);
                setNextLinkText(null);
            }
            text.setKeyIndex(getModifyNodeIndex());
            text.setFocus();
            return text;
        }

        //
        EText text = getPanel().newText(findIndex());
        text.setStart(getStart());
        text.setEnd(getStart() + index);
        setStart(getStart() + index);
        text.setStyle(getStyle());
        if (getPreviousLinkText() != null) {
            getPreviousLinkText().setNextLinkText(text);
            text.setPreviousLinkText(getPreviousLinkText());
            setPreviousLinkText(null);
        }
        text = getPanel().newText(findIndex());
        text.setStart(getStart());
        text.setEnd(getStart());
        text.setStyle(style);
        text.setKeyIndex(getModifyNodeIndex());
        text.setFocus();

        return text;
    }

    /**
	 * �����ı�
	 */
    public EText insertText(DStyle style) {

        EText text = getPanel().newText(findIndex());
        text.setStart(getStart());
        text.setEnd(getStart());
        setStart(getStart());
        text.setStyle(style);
        if (getPreviousLinkText() != null) {
            getPreviousLinkText().setNextLinkText(null);
            setPreviousLinkText(null);
        }
        text.setKeyIndex(getModifyNodeIndex());
        text.setFocus();

        //
        text.setModify( false );
        text.setIsSave(null);
        text.setDeleteFlg(false);
        text.setModifyIndex(null);

        return text;
    }

    private boolean pasteChar_(char c) {
        DString s = getDString();
        if (s == null) {
            return false;
        }
        //===�����ݴ��ҵ��ֲ�===begin
        if (s.size() == 0 && getStart() > 0) {
            setStart(0);
            setEnd(0);
            setFocusIndex(0);
        }
        //===�����ݴ��ҵ��ֲ�===end
        eventModify();
        int index = getFocusIndex();
        s.add(getStart() + index, c);
        setEnd(getEnd() + 1);
        getPanel().indexMove(this, 1);
        //��������ƶ�һ���ַ�
        setFocusIndex(getFocusIndex() + 1);
        return true;
    }

    /**
     * ɾ�����ؼ�ת�ƽ���
     * @return boolean
     */
    private boolean deleteGoToFocus() {
        //�����ƶ�����һ������Text�����
        EText text = getPreviousLinkText();
        if (text != null) {
            text.setFocusLast();
            return true;
        }
        //����ת��ǰһ�����
        text = getPreviousText();
        if (text != null) {
            setFocus(text, getFocusIndex() + getStart() - text.getStart());
            return true;
        }
        //����ת����һ�����
        text = getNextText();
        if (text != null) {
            setFocus(text, getFocusIndex() + getStart() - text.getStart());
            return true;
        }
        return false;
    }

    /**
     * ���ɾ��
     * @return boolean
     */
    public boolean deleteChar() {
        //���ܱ༭
        if (!canEdit()) {
            return false;
        }
        int index = getFocusIndex();
        if (index == getLength()) {
            return deleteChar_();
        }
        if (isDeleteFlg()) {
            onFocusToRight();
            return true;
        }
        if (getModifyNodeIndex() == getKeyIndex()) {
            return deleteChar_();
        }
        if (getKeyIndex() > getModifyNodeIndex()) {
            return false;
        }
        if (this instanceof EFixed) {
            return false;
        }
        if (index == 0) {
            DStyle style = getStyle().modifyIsDeleteLine(true,
                    getModifyNodeIndex() + 1);
            if (getLength() == 1) {
                //������ϲ�
                EText t = cutAction(1, style, index);
                if (t != null) {
                    removeThis();
                    int i = t.getLength() - 1;
                    EText t1 = t.cutAction(2, style, t.getLength());
                    if (t1 != null) {
                        t1.setFocus(t1.getLength() - i);
                        t.removeThis();
                        return true;
                    }
                    t.setFocus(1);
                    return true;
                }
            }
            EText t = cutAction(2, style, index + 1);
            //������ϲ�
            if (t != null) {
                t.setFocusLast();
                return true;
            }
            if (getLength() > 1) {
                EText text = getPanel().newText(findIndex());
                text.setStart(getStart());
                text.setEnd(getStart() + 1);
                setStart(getStart() + 1);
                text.setStyle(style);
                text.setKeyIndex(getModifyNodeIndex());
                text.setFocusLast();
                text.setDeleteFlg(true);
                return true;
            }
            setStyle(style);
            setKeyIndex(getModifyNodeIndex());
            setFocusLast();
            return true;
        }
        if (index == getLength() - 1) {
            DStyle style = getStyle().modifyIsDeleteLine(true,
                    getModifyNodeIndex() + 1);
            EText t = cutAction(1, style, index);
            //������ϲ�
            if (t != null) {
                t.setFocus(1);
                return true;
            }
            EText text = getPanel().newText(findIndex() + 1);
            text.setStart(getEnd() - 1);
            text.setEnd(getEnd());
            setEnd(getEnd() - 1);
            text.setStyle(style);
            text.setKeyIndex(getModifyNodeIndex());
            text.setFocus(1);
            text.setDeleteFlg(true);
            return true;
        }
        EText text = getPanel().newText(findIndex());
        text.setStart(getStart());
        text.setEnd(getStart() + index);
        setStart(getStart() + index);
        text.setStyle(getStyle());
        if (getPreviousLinkText() != null) {
            getPreviousLinkText().setNextLinkText(text);
            text.setPreviousLinkText(getPreviousLinkText());
            setPreviousLinkText(null);
        }
        text = getPanel().newText(findIndex());
        text.setStart(getStart());
        text.setEnd(getStart() + 1);
        text.setStyle(getStyle().modifyIsDeleteLine(true,
                getModifyNodeIndex() + 1));
        text.setKeyIndex(getModifyNodeIndex());
        text.setDeleteFlg(true);
        setStart(getStart() + 1);
        text.setFocusLast();
        return true;
    }

    /**
     * ����ɾ��
     * @return boolean
     */
    private boolean deleteChar_() {
        DString s = getDString();
        if (s == null) {
            return false;
        }
        eventModify();
        int index = getFocusIndex();
        if (index == getLength()) {
            EText text = getNextLinkText();
            if (text != null) {
                setFocus(text, 0);
                return text.deleteChar();
            }
            //ɾ����һ��Dtext�����ַ�
            text = getNextText();
            if (text != null) {
                setFocus(text, 0);
                return text.deleteChar();
            }
            EComponent com = getNextComponent();
            if (com != null) {
                //temp�ν�Table���������ɾ����
                return false;
            }
            //�ν���һ������
            if (!linkNextPanel()) {
                return false;
            }
            return true;
        }
        s.remove(getStart() + index, 1);
        getPanel().indexMove(this, -1);
        setEnd(getEnd() - 1);
        if (getLength() > 0) {
            return true;
        }
        //DText��ɾ��
        if (deleteGoToFocus()) {
            int i = findIndex();
            removeThis();
            //�ͷ��Լ�
            free();
            //�ϲ���ͬ��EText
            if (i < getPanel().size()) {
                IBlock b = getPanel().get(i);
                if (b instanceof EText) {
                    ((EText) b).linkText();
                }
            }
        }
        return true;
    }

    private EText cutAction(int type, DStyle style, int index) {
        //������ϲ�
        if (type == 1) {
            IBlock block = getNextBlock();
            if (block == null) {
                return null;
            }
            EText t = (EText) block;
            if (!t.getStyle().equals(style)) {
                return null;
            }
            int len = getLength() - index;
            t.setStart(t.getStart() - len);
            setEnd(getEnd() - len);
            return t;
        }
        //������ϲ�
        if (type == 2) {
            IBlock block = getPreviousBlock();
            if (block == null) {
                return null;
            }
            EText t = (EText) block;
            if (!t.getStyle().equals(style)) {
                return null;
            }
            t.setEnd(t.getEnd() + index);
            setStart(getStart() + index);
            return t;
        }
        return null;
    }

    /**
     * ��ǰɾ��
     * @return boolean
     */
    public boolean backspaceChar() {
        //���ܱ༭
        if (!canEdit()) {
            return false;
        }
        int index = getFocusIndex();//test
        if (index == 0) {
            return backspaceChar_();
        }

        /**
        //
        if ( null == getIsSave() && isDeleteFlg() ) {
        	return backspaceChar_();
        }
        */

        //
        if( null==this.getModifyIndex() && getModifyNodeIndex()==-1 && isDeleteFlg() ){
        	 return backspaceChar_();
        }

        //
        if ( isDeleteFlg() ) {
            onFocusToLeft();
            return true;
        }

        //
        if (getModifyNodeIndex() == getKeyIndex() ) {

        	if( null==this.getModifyIndex() ){
        		return backspaceChar_();
        	}
        }

        /**
        //
        if( null!=this.getModifyIndex() && null==this.getIsSave()){
        	 return backspaceChar_();
        }
        */

        if (getKeyIndex() > getModifyNodeIndex()) {
            return false;
        }
        if (this instanceof EFixed) {
            return false;
        }

        //1
        if (index == 1) {

            //
            this.addModify();

            DStyle style = getStyle().modifyIsDeleteLine(true,
                    getModifyNodeIndex() + 1);
            //������ϲ�
            if (getLength() == 1) {
                EText t = cutAction(1, style, index - 1);
                if (t != null) {
                    removeThis();
                    int i = t.getLength();
                    EText t1 = t.cutAction(2, style, t.getLength());
                    if (t1 != null) {
                        t1.setFocus(t1.getLength() - i);
                        t.removeThis();
                        return true;
                    }
                    t.setFocus();
                    return true;
                }
            }
            EText t = cutAction(2, style, index);
            //������ϲ�
            if (t != null) {
                t.setFocus(t.getLength() - 1);
                return true;
            }
            if (getLength() > 1) {
                EText text = getPanel().newText(findIndex());
                text.setStart(getStart());
                text.setEnd(getStart() + 1);
                setStart(getStart() + 1);
                text.setStyle(style);
                text.setKeyIndex(getModifyNodeIndex());
                text.setFocus();
                text.setDeleteFlg(true);
                return true;
            }
            setStyle(style);
            setKeyIndex(getModifyNodeIndex());
            setFocus();

            //
            this.setDeleteFlg(true);

            return true;
        }

        //2
        if (index == getLength()) {

            //
            this.addModify();

            DStyle style = getStyle().modifyIsDeleteLine(true,
                    getModifyNodeIndex() + 1);
            if (getLength() > 1) {
                EText t = cutAction(1, style, index - 1);
                //������ϲ�
                if (t != null) {
                    t.setFocus();
                    return true;
                }
                EText text = getPanel().newText(findIndex() + 1);
                text.setStart(getEnd() - 1);
                text.setEnd(getEnd());
                setEnd(getEnd() - 1);
                text.setStyle(style);
                text.setKeyIndex(getModifyNodeIndex());
                setFocusLast();
                text.setDeleteFlg(true);
                return true;
            }
            EText t = cutAction(2, style, index);
            //������ϲ�
            if (t != null) {
                return true;
            }
            setStyle(style);
            setKeyIndex(getModifyNodeIndex());
            setFocus();
            return false;
        }

        //3
        if (index > 1) {

            //
            this.addModify();

            EText text = getPanel().newText(findIndex());
            text.setStart(getStart());
            text.setEnd(getStart() + index - 1);
            setStart(getStart() + index - 1);
            text.setStyle(getStyle());
            if (getPreviousLinkText() != null) {
                getPreviousLinkText().setNextLinkText(text);
                text.setPreviousLinkText(getPreviousLinkText());
                setPreviousLinkText(null);
            }
            text.setFocusLast();

            text = getPanel().newText(findIndex());
            text.setStart(getStart());
            text.setEnd(getStart() + 1);
            text.setStyle(getStyle().modifyIsDeleteLine(true,
                    getModifyNodeIndex() + 1));
            text.setKeyIndex(getModifyNodeIndex());
            text.setDeleteFlg(true);
            setStart(getStart() + 1);
        }
        return false;
    }

    /**
     * ��ǰɾ��
     * @return boolean
     */
    private boolean backspaceChar_() {
        DString s = getDString();
        if (s == null) {
            return false;
        }
        eventModify();
        int index = getFocusIndex();
        if (index == 0) {
            //�ж��Ƿ������һ������Text
            EText text = getPreviousLinkText();
            if (text != null) {
                setFocus(text, text.getEnd() - text.getStart());
                return text.backspaceChar();
            }
            //���ڱ�������һ��Text
            text = getPreviousText();
            if (text != null) {
                setFocus(text, text.getEnd() - text.getStart());
                return text.backspaceChar();
            }
            EComponent com = getPreviousComponent();
            if (com != null) {
                //temp�ν�Table���������ɾ����
                return false;
            }
            //�ν���һ������
            if (!linkPreviousPanel()) {// lx �лس�Ӧ��������߼�
                return false;
            }
            return true;
        }
        s.remove(getStart() + index - 1, 1);
        getPanel().indexMove(this, -1);
        setEnd(getEnd() - 1);
        setFocusIndex(getFocusIndex() - 1);
        if (getLength() > 0) {
            return true;
        }
        //DText��ɾ��
        if (deleteGoToFocus()) {
            //�ָ�����
            if (getPreviousLinkText() != null) {
                getPreviousLinkText().setNextLinkText(getNextLinkText());
            }
            if (getNextLinkText() != null) {
                getNextLinkText().setPreviousLinkText(getPreviousLinkText());
            }
            int i = findIndex();
            removeThis();
            //�ͷ��Լ�
            free();
            //�ϲ���ͬ��EText
            if (i < getPanel().size()) {
                IBlock b = getPanel().get(i);
                if (b instanceof EText) {
                    ((EText) b).linkText();
                }
            }
        }
        return true;
    }

    /**
     * ������һ������
     * @return boolean
     */
    public boolean linkPreviousPanel() {
        EPanel panel = getPanel();
        if (panel == null) {
            return false;
        }
        return panel.linkPreviousPanel();
    }

    /**
     * ������һ��Text
     */
    public void linkText() {
        int index = findIndex();
        if (index == 0) {
            return;
        }
        IBlock block = getPanel().get(index - 1);
        if (!(block instanceof EText)) {
            return;
        }
        //�νӹ̶��ı�
        if (block instanceof EFixed) {
            if (!(this instanceof EFixed)) {
                return;
            }
            if (((EFixed) block).getNextFixed() != this) {
                return;
            }
        }
        //�νӺ�
        if (block instanceof EMacroroutine) {
            if (!(this instanceof EMacroroutine)) {
                return;
            }
            if (((EMacroroutine) block).getNextMacroroutine() != this) {
                return;
            }
        }
        EText p = (EText) block;
        if (p.getStyle() != getStyle()) {
            return;
        }
        if (getLength() == 0) {
            if (getFocus() == this) {
                p.setFocusLast();
            }
            removeThis();
            return;
        }
        if (p.getLength() == 0) {
            if (getFocus() == p) {
                setFocus();
            }
            p.removeThis();
            return;
        }
        p.setNextLinkText(this);
        setPreviousLinkText(p);
    }

    /**
     * ������һ������
     * @return boolean
     */
    public boolean linkNextPanel() {
        EPanel panel = getPanel();
        if (panel == null) {
            return false;
        }
        return panel.linkNextPanel();
    }

    /**
     * �����ƶ����
     * @return boolean
     */
    public boolean onFocusToLeft() {
        int index = getFocusIndex();
        //��Text���ƶ�����
        if (index > 0) {
            return moveFocus( -1);
        }

        //�����������ƶ�����
        EText previous = getPreviousLinkText();
        if (previous != null) {
            return previous.setFocus(previous.getLength() - 1);
        }

        //��ת��������һ�����
        IBlock block = getPreviousComponent();
        if (block != null) {
            //Text�õ�����
            if (block instanceof EText) {
                return ((EText) block).setFocus(((EText) block).getLength() - 1);
            }
            //Table�õ�����
            if (block instanceof ETable) {
                return ((ETable) block).setFocusLast();
            }
        }

        //�����ƶ���굽��һ���������
        if (getPanel().onLinkPanelFocusToLeft()) {
            return true;
        }
        //�����ƶ���굽��һ�����
        if (getPanel().onNextPanelFocusToLeft()) {
            return true;
        }

        return false;
    }

    /**
     * �����ƶ����
     * @return boolean
     */
    public boolean onFocusToRight() {
        int index = getFocusIndex();
        //��Text���ƶ�����
        if (index < getLength()) {
            return moveFocus(1);
        }

        //�����������ƶ�����
        EText text = getNextLinkText();
        if (text != null) {
            return text.setFocus();
        }

        //��ת��������һ�����
        IBlock block = getNextComponent();
        if (block != null) {
            //Text�õ�����
            if (block instanceof EText) {
                EText t = (EText) block;
                if (t.getLength() > 0) {
                    return t.setFocus(1);
                } else {
                    t.setFocus();
                }
            }
            //����ĩβ
            if (index < getLength()) {
                return moveFocus(1);
            }
            //Table�õ�����
            if (block instanceof ETable) {
                return ((ETable) block).setFocus();
            }
        }

        //�����ƶ���굽��һ���������
        if (getPanel().onLinkPanelFocusToRight()) {
            return true;
        }

        //����ĩβ
        if (index < getLength()) {
            return moveFocus(1);
        }

        //�����ƶ���굽��һ�����
        if (getPanel().onNextPanelFocusToRight()) {
            return true;
        }

        return false;
    }

    /**
     * �����ƶ����
     * @param x int
     */
    public void onFocusToUp(int x) {
        IBlock block = getPreviousRowHomeBlock();
        if (block == null) {
            return;
        }
        onFocusCheckX(block, x);
    }

    /**
     * �����ƶ����
     * @param x int
     */
    public void onFocusToDown(int x) {
        IBlock block = getNextRowFirstBlock();
        if (block == null) {
            return;
        }
        onFocusCheckX(block, x);
    }

    /**
     * ����Tab
     * @param flg boolean true ���� false ����
     * @return boolean
     */
    public boolean onFocusToTab(boolean flg) {
        return false;
    }

    /**
     * ������λ���ں�ʱ�����
     * @param block IBlock
     * @param x int
     */
    public void onFocusCheckX(IBlock block, int x) {
        if (block == null) {
            return;
        }
        if (block instanceof EText) {
            EText text = (EText) block;
            int x0 = text.getX() + text.getPanel().getPointX();
            //λ������������
            if (x <= x0) {
                setFocus(text, 0);
                return;
            }
            //λ����������Ҳ�
            if (x > x0 + text.getWidth()) {
                //�������Ǳ������һ�����
                if ((text.getPosition() & 2) == 2) {
                    setFocus(text, text.getLength());
                    return;
                }
                IBlock next = text.getNextBlock();
                if (next == null) {
                    return;
                }
                onFocusCheckX(next, x);
                return;
            }
            int index = text.getStyle().getIndex(text.getString(), x - x0);
            //���ý���
            setFocus(text, index);
            return;
        }
        if (block instanceof ETable) {

            return;
        }
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getNextBlock() {
        return getPanel().get(findIndex() + 1);
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getPreviousBlock() {
        return getPanel().get(findIndex() - 1);
    }

    /**
     * �õ�����ģ��
     * @return IBlock
     */
    public IBlock getHomeBlock() {
        return getPanel().getHomeBlock(this);
    }

    /**
     * �õ���βģ��
     * @return IBlock
     */
    public IBlock getEndBlock() {
        return getPanel().getEndBlock(this);
    }

    /**
     * �õ���һ������
     * @return IBlock
     */
    public IBlock getPreviousRowHomeBlock() {
        IBlock head = getHomeBlock();
        if (head == null) {
            return null;
        }
        head = getPanel().getHomeBlock(head.findIndex() - 1);
        if (head != null) {
            return head;
        }
        EPanel panel = getPanel().getPreviousPanel();
        if (panel != null) {
            EText home = panel.getLastText();
            if (home == null) {
                return null;
            }
            return home.getHomeBlock();
        }
        EText home = getPanel().getPreviousPageLastText();
        if (home != null) {
            return home.getHomeBlock();
        }
        //�ƶ�����һ��TD��
        if (getPanel().parentIsTD()) {
            return getPanel().getParentTD().getUpFocusBlock();
        }
        return null;
    }

    /**
     * �õ���һ������
     * @return IBlock
     */
    public IBlock getNextRowFirstBlock() {
        //�õ����е�ĩβ����
        IBlock end = getEndBlock();
        if (end == null) {
            return null;
        }
        //�õ������е��׶���
        end = getPanel().getHomeBlock(end.findIndex() + 1);
        if (end != null) {
            return end;
        }
        //�õ���һ�����
        EPanel panel = getPanel().getNextPanel();
        if (panel != null) {
            EText home = panel.getFirstText();
            if (home == null) {
                return null;
            }
            return home.getHomeBlock();
        }
        //�ƶ�����һҳ��
        EText home = getPanel().getNextPageFirstText();
        if (home != null) {
            return home.getHomeBlock();
        }
        //�ƶ�����һ��TD��
        if (getPanel().parentIsTD()) {
            return getPanel().getParentTD().getDownFocusBlock();
        }
        return null;
    }

    /**
     * �õ�����
     * @return EText
     */
    public EText getRowFirstText() {
        EText text = getPreviousText();
        while (text != null && (text.getPosition() & 1) != 1) {
            EText text1 = text.getPreviousText();
            if (text1 == null) {
                return text;
            }
            text = text1;
        }
        return text;
    }

    /**
     * �õ���β
     * @return EText
     */
    public EText getRowLastText() {
        EText text = getNextText();
        while (text != null && (text.getPosition() & 2) != 2) {
            EText text1 = text.getNextText();
            if (text1 == null) {
                return text;
            }
            text = text1;
        }
        return text;
    }

    /**
     * �ƶ���굽����
     */
    public void onFocusToHome() {
        if ((getPosition() & 1) == 1) {
            setFocusIndex(0);
            return;
        }
        EText text = getRowFirstText();
        if (text == null) {
            return;
        }
        setFocus(text, 0);
    }

    /**
     * �ƶ���굽��β
     */
    public void onFocusToEnd() {
        if ((getPosition() & 2) == 2) {
            setFocusIndex(getLength());
            return;
        }
        EText text = getRowLastText();
        if (text == null) {
            return;
        }
        setFocus(text, text.getLength());
    }

    /**
     * �س��¼�
     * @return boolean
     */
    public boolean onEnter() {
        //���ܱ༭
        if (!canEdit()) {
            return false;
        }
        EText text = this;
        text.setModify(true);
        int index = getFocusIndex();
        //����Table���лس�,��Tableǰ�����ն���
        if (index == 0 && findIndex() == 0 &&
            getPanel().findIndex() == 0 &&
            getPanel().parentIsTD() &&
            getPanel().getParentTD().findIndex() == 0 &&
            getPanel().getParentTD().getTR().findIndex() == 0 &&
            getPanel().getParentTD().getTable().getPreviousTable() == null &&
            getPanel().getParentTD().getTable().getPanel().findIndex() == 0 &&
            getPanel().getParentTD().getTable().getPanel().parentIsPage() &&
            getPanel().getParentTD().getTable().getPanel().getParentPage().
            findIndex() == 0) {
            EPanel panel = getPanel().getParentTD().getTable().getPanel().
                           getParentPage().newPanel(0);
            panel.newText().setFocus();
            return true;
        }

        if (index > 0) {
            if (index == getLength()) {
                /* IBlock block = getNextBlock();
                 if(block != null && block instanceof EText)
                 {
                     setFocus((EText)block,0);
                     return ((EText)block).onEnter();
                 }*/
                //Ĭ�ϻ��������޸�20110311 WangM
                text = getPanel().newText(findIndex() + 1);
                text.setStyle(getStyle());
                text.setBlock(getEnd(), getEnd());
            } else {
                //�ܷ�س�������
                if (!canEnterSeparate()) {
                    return false;
                }

                //������
                if (separate(index)) {
                    text = getNextText();
                }
            }
            setFocus(text, 0);
        }
        //del by lx 2013/03/20 �ָ�����
       /* if(text!=null){
            if (text.getPreviousLinkText() != null) {
                //System.out.println("=====getPreviousLinkText()===="+getPreviousLinkText());
                if(getPreviousLinkText()!=null){
                    getPreviousLinkText().setNextLinkText(null);
                    setPreviousLinkText(null);
                }
            }
        }*/
        //add by lx 2013/03/20 �ָ�����
        if(text!=null){
        	//���ηǵ�һ�е����
            if (text.getPreviousLinkText() != null) {
            	//�϶�
            	//text.getPreviousLinkText().setPreviousLinkText(null);
            	text.getPreviousLinkText().setNextLinkText(null);
            	//�¶�
            	text.setPreviousLinkText(null);
            	//text.setNextLinkText(null);
            }
        }

        //�س��ָ�
        getPanel().separateText(text);
        return true;
    }

    /**
     * �ܷ�س�������
     * @return boolean
     */
    public boolean canEnterSeparate() {
        return true;
    }

    /**
     * �¼��޸�
     */
    public void eventModify() {
        int row = getRowIndex();
        if (row != 0) {
            return;
        }
        if (!(getPanel().getParent() instanceof EPage)) {
            return;
        }
        if (getPanel().findIndex() != 0) {
            return;
        }
        int index = getPage().findIndex();
        if (index <= 0) {
            return;
        }
        EPage page = getPM().getPageManager().get(index - 1);
        if (page == null) {
            return;
        }
        page.get(page.size() - 1).setModify(true);
    }

    /**
     * �õ���ǰ���ڵ�����
     * @return int
     */
    public int getRowIndex() {
        return getRowIndex(this);
    }

    /**
     * �õ���ǰ���ڵ�����
     * @param head IBlock
     * @return int
     */
    public int getRowIndex(IBlock head) {
        if (head == null) {
            return -1;
        }
        head = getPanel().getHomeBlock(head);
        int index = getPanel().indexOf(head);
        int row = 0;
        while (index > 0) {
            head = getPanel().getHomeBlock(index - 1);
            index = getPanel().indexOf(head);
            row++;
        }
        return row;
    }

    /**
     * �����ߴ�
     */
    public void reset() {
        //if(!isModify())
        //    return;
        if (getStyle() == null) {
            return;
        }
        //����
        getResetText().clear();
        //���óߴ緶Χ
        getResetText().setRectangle(0,
                                    0,
                                    getMaxWidth(),
                                    getMaxHeight());
        //�����ߴ�
        getResetText().reset();
        //getStyle().resetSize(getString(),this);
        setModify(false);
    }

    /**
     * �����ߴ�
     * @param index int
     */
    public void reset(int index) {
    }

    /**
     * �ͷ��Լ�
     */
    public void free() {

    }

    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString() {
        EPanel panel = getPanel();
        if (panel == null) {
            return "Parent:Null";
        }
        return panel.getIndexString() + ",Text:" + findIndex();
    }

    /**
     * �������
     */
    public void clearString() {
        if (getLength() == 0) {
            return;
        }
        EPanel panel = getPanel();
        if (panel == null) {
            return;
        }
        DString s = getDString();
        s.remove(getStart(), getLength());
        panel.indexMove(this, -getLength());
        setEnd(getStart());
    }

    /**
     * �����ַ�
     * @param text String
     */
    public void setString(String text) {
        if (text == null) {
            text = "null";
        }
        EPanel panel = getPanel();
        if (panel == null) {
            return;
        }
        clearString();
        DString s = getDString();
        s.add(getStart(), text);
        setEnd(getStart() + text.length());
        panel.indexMove(this, getLength());
    }

    /**
     * ׷���ַ�
     * @param text String
     */
    public void addString(String text) {
        if (text == null) {
            text = "null";
        }
        EPanel panel = getPanel();
        if (panel == null) {
            return;
        }
        DString s = getDString();
        s.add(getEnd(), text);
        setEnd(getEnd() + text.length());
        panel.indexMove(this, text.length());
    }

    /**
     * �ָ��ı�
     * @param index int
     * @return EText
     */
    public EText carveText(int index) {
        EText text = getPanel().newText(findIndex() + 1);
        text.setStart(getStart() + index);
        text.setEnd(getEnd());
        text.setStyle(getStyle());
        setEnd(getStart() + index);
        //�ָ�����
        text.setNextLinkText(getNextLinkText());
        if (getNextLinkText() != null) {
            getNextLinkText().setPreviousLinkText(text);
        }
        setNextLinkText(null);
        return text;
    }

    /**
     * ճ���ַ���
     * @param index int
     * @param text String
     * @return boolean
     */
    public boolean pasteString(int index, String text,DStyle defineStyle) {
        if (text == null || text.length() == 0) {
            return false;
        }
        if (index < 0 || index > getLength()) {
            return false;
        }
        //�����ڻس���
        int enterIndex = text.indexOf("\n");
        if (enterIndex == -1) {
            getDString().add(index + getStart(), text);
            setEnd(getEnd() + text.length());
            getPanel().indexMove(this, text.length());
            
            if(defineStyle==null){
            	defineStyle=getDefaultStyle();
            }           
            //�����¶�Ӧ��text������            
            this.setFont(defineStyle.getFont());
            setFocus(index + text.length());
                        
            //
            setModify(true);
            return true;
        }
        String nextS = text.substring(enterIndex + 1);
        text = text.substring(0, enterIndex);
        EText nText = carveText(index);
        pasteString(index, text,defineStyle);
        getPanel().separateText(nText);
        nText.setModify(true);
        return nText.pasteString(0, nextS,defineStyle);
    }
 
    /**
     * ���Զ������
     * @param i int
     */
    public void debugShow(int i) {
        System.out.println(StringTool.fill(" ", i) + this);
    }

    /**
     * �����޸�
     * @param i int
     */
    public void debugModify(int i) {
        if (!isModify()) {
            return;
        }
        System.out.println(StringTool.fill(" ", i) + getIndexString() + " " + this);
    }

    /**
     * �ܷ�ϲ�
     * @param block IBlock
     * @return boolean
     */
    public boolean unite(IBlock block) {
        if (block == null) {
            return false;
        }
        if (!(block instanceof EText)) {
            return false;
        }
        EText text = (EText) block;
        if (block.getClass() != getClass()) {
            return text.unite(this);
        }
        if (getPanel() != text.getPanel()) {
            return false;
        }
        if (!getStyle().equals(text.getStyle())) {
            return false;
        }
        if (!canSeparate() || !text.canSeparate()) {
            return false;
        }
        if (getFocus() == text) {
            setFocus(this, getFocusIndex() + getLength());
        }
        if (getStartFocus() == text) {
            setStartFocus(this);
            setStartFocusIndex(getStartFocusIndex() + getLength());
        }
        setEnd(text.getEnd());
        text.removeThis();
        return true;
    }

    /**
     * �õ�ҳ
     * @return EPage
     */
    public EPage getPage() {
        EPanel panel = getPanel();
        if (panel == null) {
            return null;
        }
        return panel.getPage();
    }

    /**
     * �õ�ҳ��
     * @return int
     */
    public int getPageIndex() {
        EPage page = getPage();
        if (page == null) {
            return -1;
        }
        return page.findIndex();

    }

    public String toString() {
        return "<EText size=" + getLength() +
                ",width=" + getWidth() +
                ",height=" + getHeight() +
                ",start=" + getStart() +
                ",end=" + getEnd() +
                ",s=" + getString() +
                ",hasFocus=" + (getFocus() == this) +
                ",focusIndex=" + getFocusIndex() +
                ",position=" + getPosition() +
                ",modifyIndex=" + getModifyIndex() +
                ",isSave=" + getIsSave() +
                ",selected=" + getSelectedModel() +
                ",previousLinkText=" +
                (getPreviousLinkText() == null ? null :
                 getPreviousLinkText().getIndexString()) +
                ",nextLinkText=" +
                (getNextLinkText() == null ? null :
                 getNextLinkText().getIndexString()) +
                ",isModify=" + isModify() +
                ",DStyle=" + getStyle() +
                ",index=" + getIndexString() +
                ">";
    }

    /**
     * �õ�TD
     * @return ETD
     */
    public ETD getTD() {
        EPanel panel = getPanel();
        if (panel == null) {
            return null;
        }
        EComponent p = panel.getParent();
        if (p == null || !(p instanceof ETD)) {
            return null;
        }
        return (ETD) p;
    }

    /**
     * �õ�TR
     * @return ETR
     */
    public ETR getTR() {
        ETD td = getTD();
        if (td == null) {
            return null;
        }
        return td.getTR();
    }

    /**
     * �õ�TRModel
     * @return ETRModel
     */
    public ETRModel getTRModel() {
        ETR tr = getTR();
        if (tr == null) {
            return null;
        }
        return tr.getModel();
    }

    /**
     * �õ��к�
     * @return int
     */
    public int getRow() {
        ETRModel model = getTRModel();
        if (model == null) {
            return -1;
        }
        return model.getRow();
    }

    /**
     * �õ�������
     * @return int
     */
    public int getEndRow() {
        ETRModel model = getTRModel();
        if (model == null) {
            return -1;
        }
        return model.getEndRow();
    }

    /**
     * �õ�Table
     * @return ETable
     */
    public ETable getTable() {
        ETR tr = getTR();
        if (tr == null) {
            return null;
        }
        return tr.getTable();
    }

    /**
     * �õ�Table������
     * @return CTable
     */
    public CTable getCTable() {
        ETable table = getTable();
        if (table == null) {
            return null;
        }
        return table.getHeadTable().getCTable();
    }

    /**
     * д����
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s, int c) throws IOException {
        s.WO("<EText Start=" + getStart() + " >", c);
        s.WO(0, "String", getString(), "", c + 1);
        s.WO("</EText>", c);
    }

    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s) throws IOException {
        //д��������
        writeObjectAttribute(s);
        s.writeShort( -1);
    }

    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s) throws IOException {
        s.writeInt(1, getStart(), 0);
        s.writeString(2, getString(), "");
        s.writeInt(3, getStyle().findIndex(), -1);
        if (getNextLinkText() != null) {
            int index = getTextSaveManager().add(this);
            getNextLinkText().setPreviousTextIndex(index);
            s.writeShort(4);
        }
        if (getPreviousLinkText() != null) {
            s.writeShort(5);
            s.writeInt(getPreviousTextIndex());
        }
        s.writeBoolean(6, isDeleteFlg(), false);
        s.writeInt(7, getKeyIndex(), -1);

        //
        if(null!=getModifyIndex()){
        	 s.writeInt(8, getModifyIndex().intValue(),0);
        }
        //
        s.writeString(9, "Y", "");
    }

    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s) throws IOException {
        int id = s.readShort();
        while (id > 0) {
            //����������
            readObjectAttribute(id, s);
            id = s.readShort();
        }
        setModify(true);
    }

    /**
     * ����������
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id, DataInputStream s) throws
            IOException {
        switch (id) {
        case 1:
            setStart(s.readInt());
            setEnd(getStart());
            return true;
        case 2:
            setString(s.readString());
            return true;
        case 3:
            setStyle(s.readInt());
            return true;
        case 4:
            getTextSaveManager().add(this);
            break;
        case 5:
            EText text = getTextSaveManager().get(s.readInt());
            //$$========modified by lx 2012-07-10 �����ж�=========$$//
            if(text!=null){
	            setPreviousLinkText(text);
	            text.setNextLinkText(this);
            }
            break;
        case 6:
            setDeleteFlg(s.readBoolean());
        	//setDeleteFlg(false);
            break;
        case 7:
            setKeyIndex(s.readInt());
            break;

        //
        case 8:
             setModifyIndex(s.readInt());
             break;
        //
        case 9:
             setIsSave(s.readString());
             break;
        }

        return false;
    }

    /**
     * �õ�������ĺ�����λ��
     * @return int
     */
    public int getFocusPointX() {
        if (getFocus() != this) {
            return 0;
        }
        int index = getFocusIndex();
        return getStyle().stringWidth(getString(index)) + getX() +
                getPanel().getPointX();
    }

    /**
     * �õ�·��
     * @return TList
     */
    public TList getPath() {
        TList list = new TList();
        getPath(list);
        return list;
    }

    /**
     * �õ�·��
     * @param list TList
     */
    public void getPath(TList list) {
        if (list == null) {
            return;
        }
        list.add(0, this);
        getPanel().getPath(list);
    }

    /**
     * �õ�·������
     * @return TList
     */
    public TList getPathIndex() {
        TList list = new TList();
        getPathIndex(list);
        return list;
    }

    /**
     * �õ�·������
     * @param list TList
     */
    public void getPathIndex(TList list) {
        if (list == null) {
            return;
        }
        list.add(0, findIndex());
        getPanel().getPathIndex(list);
    }

    /**
     * �Ƚ�λ�ô�С
     * @param text EText
     * @return int
     * 1 �Լ���text֮��
     * -1 �Լ���text֮ǰ
     * 0 λ����ͬ
     */
    public int compareIndexTo(EText text) {
        if (text == null) {
            return 1;
        }
        if (text == this) {
            return 0;
        }
        TList thisList = getPathIndex();
        TList list = text.getPathIndex();
        int count = thisList.size();
        count = Math.min(count, list.size());
        for (int i = 0; i < count; i++) {
            int thisIndex = TypeTool.getInt(thisList.get(i));
            int index = TypeTool.getInt(list.get(i));
            if (thisIndex > index) {
                return 1;
            }
            if (thisIndex < index) {
                return -1;
            }
        }
        return 0;
    }

    /**
     * �õ������ı��б�
     * @param endText EText
     * @return TList
     */
    public TList getFindTextList(EText endText) {
        TList list = new TList();
        getFindTextList(list, endText);
        return list;
    }

    /**
     * �õ������ı��б�
     * @param list TList
     * @param endText EText
     */
    public void getFindTextList(TList list, EText endText) {
        if (list == null) {
            return;
        }
        list.add(this);
        if (this == endText) {
            return;
        }
        EComponent next = getNextComponent();
        if (next != null) {
            if (next instanceof EText) {
                ((EText) next).getFindTextList(list, endText);
            }
            //if(next instanceof ETable)
            //    ((ETable)next).getFindTextList(list,endText);
            return;
        }
        EPanel panel = getPanel();
        if (panel == null) {
            return;
        }
        panel.getFindNextTextList(list, endText);
    }

    /**
     * �޸���ɫ
     * @param c Color
     */
    public void modifyColor(Color c) {
        setStyle(getStyle().modifyColor(c));
        setModify(true);
    }

    /**
     * �޸�����
     * @param s String
     */
    public void modifyFont(String s) {
        setStyle(getStyle().modifyFont(s));
        setModify(true);
    }

    /**
     * �޸��������
     * @param bold boolean
     */
    public void modifyBold(boolean bold) {
        setStyle(getStyle().modifyBold(bold));
        setModify(true);
    }

    /**
     * �޸�����б��
     * @param italic boolean
     */
    public void modifyItalic(boolean italic) {
        setStyle(getStyle().modifyItalic(italic));
        setModify(true);
    }

    /**
     * �޸�����ߴ�
     * @param size int
     */
    public void modifyFontSize(int size) {
        setStyle(getStyle().modifyFontSize(size));
        setModify(true);
    }

    public void setFont(Font font) {
        setStyle(getStyle().modifyFont(font));
        setModify(true);
    }

    /**
     * ����ʹ����ʽ
     */
    public void checkGCStyle() {
        if (getStyle() == null) {
            return;
        }
        getStyle().setGC(false);
    }

    /**
     * ����ƶ�
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX, int mouseY) {
        String s = "";
        if (getModifyNodeIndex() > -1) {
            EModifyInf inf = getModifyNodeManager().get(getModifyNodeIndex());

            if(inf!=null){
                s = inf.getUserName() + " " + inf.getModifyDate();
            }else{
                s="";
            }
        }
        getPM().getUI().getParentTCBase().setToolTipText(s);

        //
        postToolTip(  getPM().getUI().getParentTCBase());

        return false;
    }

    /**
     * ����ƶ�
     * @param mouseX int
     * @param mouseY int
     * @param mindex int
     * @return boolean
     */
    public boolean onMouseMoved(int mouseX, int mouseY,Integer mindex) {

        String s = null;
        if (getModifyNodeIndex() > -1) {

        	if( null!=mindex ){
            	EModifyInf inf = getModifyNodeManager().get(mindex-1);

            	if( null!=inf.getUserName() ){
            		s = inf.getUserName() + " " + inf.getModifyDate();
            	}
        	}
        }

        //
        getPM().getUI().getParentTCBase().setToolTipText(s);

        //
        postToolTip(  getPM().getUI().getParentTCBase());

        return false;
    }

    /**
     * �޸�ʧȥ����'Tooltip'�Ͳ���ʾ������
     * @param comp
     */
	public void postToolTip(JComponent comp) {

		//ʧȥ����� action ��Ϊ����
		/**
		Action action = comp.getActionMap().get("postTip");
		if (action == null) // no tooltip
			return;
		*/

		Action action = comp.getActionMap().get("postTip");
		if( action == null && null!=comp.getToolTipText() ){
			ToolTipManager tt =	ToolTipManager.sharedInstance();
			tt.registerComponent(comp);
			tt.setEnabled(true);
			tt.setInitialDelay(100);
			tt.setReshowDelay(100);
		}else{
			// no tooltip
			return;
		}

		/**
		//
		if( null!= comp.getActionMap().get("postTip") ){
			ActionEvent ae = new ActionEvent(comp, ActionEvent.ACTION_PERFORMED,
					"postTip", EventQueue.getMostRecentEventTime(), 0);
			action.actionPerformed(ae);
		}
		**/

	}

    /**
     * �õ���Ļ����
     * @return DPoint
     */
    public DPoint getScreenPoint() {
        DPoint point = getPanel().getScreenPoint();
        point.setX(point.getX() + (int) (getX() * getZoom() / 75.0 + 0.5));
        point.setY(point.getY() + (int) (getY() * getZoom() / 75.0 + 0.5));
        return point;
    }

    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action) {
        if (isDeleteFlg() &&
            (action.getType() == EAction.PREVIEW_STATE ||
             action.getType() == EAction.EDIT_STATE)) {
            setModify(true);
            return;
        }
    }

    /**
     * ���óߴ��������
     * @param resetText ResetText
     */
    public void setResetText(ResetText resetText) {
        this.resetText = resetText;
    }

    /**
     * �õ��ߴ��������
     * @return ResetText
     */
    public ResetText getResetText() {
        return resetText;
    }

    /**
     * ����޸ĺۼ�
     */
    public void resetModify() {
        if (!isModify()) {
            return;
        }
        setModify(false);
    }

    /**
     * �Ƿ�ѡ�л���
     * @return boolean
     */
    public boolean isSelectedDraw() {
        return getPanel().isSelectedDraw();
    }

    /**
     * �Ƿ��Ǵ���
     * @return boolean
     */
    public boolean isBold() {
        return getStyle().getFont().isBold();
    }

    /**
     * �Ƿ���б��
     * @return boolean
     */
    public boolean isItalic() {
        return getStyle().getFont().isItalic();
    }

    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize() {
        return getStyle().getFont().getSize();
    }

    /**
     * �õ���������
     * @return String
     */
    public String getFontName() {
        return getStyle().getFont().getName();
    }

    /**
     * �õ�������ڵ�TD
     * @return ETD
     */
    public ETD getComponentInTD() {
        return getPanel().getComponentInTD();
    }

    /**
     * �õ���һ����Ч��Text�����������
     * @return EText
     */
    public EText getNextTrueText() {
        EPanel panel = getPanel();
        int index = findIndex() + 1;
        while (index < panel.size()) {
            IBlock block = panel.get(index);
            if (block instanceof EText) {
                return (EText) block;
            }
        }
        panel = panel.parentIsPage() ? panel.getNextPageLinkPanel() :
                panel.getNextLinkPanel();
        while (panel != null) {
            index = 0;
            while (index < panel.size()) {
                IBlock block = panel.get(index);
                if (block instanceof EText) {
                    return (EText) block;
                }
            }
        }
        return null;
    }

    /**
     * �õ�������ڵ����
     * @return EPanel
     */
    public EPanel getTablePanel() {
        EPanel panel = getPanel();
        if (!panel.parentIsTD()) {
            return null;
        }
        return panel.getParentTD().getPanel();
    }

    /**
     * �õ�Table�к�
     * @return int
     */
    public int getTableRow() {
        ETR tr = getTR();
        if (tr == null) {
            return -1;
        }
        return tr.getRow();
    }

    /**
     * �õ�Table�к�
     * @return int
     */
    public int getTableColumn() {
        ETD td = getTD();
        if (td == null) {
            return -1;
        }
        return td.findIndex();
    }

    /**
     * ����ѡ������
     * @param selectedModel CSelectedTextModel
     */
    public void setSelectedModel(CSelectedTextModel selectedModel) {
        this.selectedModel = selectedModel;
    }

    /**
     * �õ�ѡ������
     * @return CSelectedTextModel
     */
    public CSelectedTextModel getSelectedModel() {
        return selectedModel;
    }

    /**
     * ���ѡ��
     */
    public void clearSelected() {
        getSelectedModel().removeAll();
    }

    /**
     * ѡ��
     * @param start int
     * @param end int
     * @return CSelectTextBlock
     */
    public CSelectTextBlock selectText(int start, int end) {
        return getSelectedModel().add(start, end);
    }

    /**
     * ȫ��ѡ��
     * @return CSelectTextBlock
     */
    public CSelectTextBlock selectAll() {
        return getSelectedModel().add(0, getLength());
    }

    /**
     * �ַ��Ƿ�ѡ��
     * @param index int
     * @return boolean
     */
    public boolean isSelectedChar(int index) {
        return getSelectedModel().isSelectedChar(index);
    }

    /**
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isSelected() {
        return getSelectedModel().isSelected();
    }

    /**
     * �Ƿ�����������
     * @return boolean
     */
    public boolean hasPreviousLink() {
        return getPreviousLinkText() != null;
    }

    /**
     * �Ƿ�����������
     * @return boolean
     */
    public boolean hasNextLink() {
        return getNextLinkText() != null;
    }

    /**
     * �õ���Text
     * @return EText
     */
    public EText getHeadText() {
        if (hasPreviousLink()) {
            return getPreviousLinkText().getHeadText();
        }
        return this;
    }

    /**
     * �õ�βText
     * @return EText
     */
    public EText getEndText() {
        if (hasNextLink()) {
            return getNextLinkText().getEndText();
        }
        return this;
    }

    /**
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action) {
        if (action == null) {
            return;
        }
        EText text = getHeadText();
        while (text != null) {
            switch (action.getType()) {
            case EAction.FONT_NAME:
                text.modifyFont(action.getString(0));
                break;
            case EAction.FONT_SIZE:
                text.modifyFontSize(action.getInt(0));
                break;
            case EAction.FONT_BOLD:
                text.modifyBold(action.getBoolean(0));
                break;
            case EAction.FONT_ITALIC:
                text.modifyItalic(action.getBoolean(0));
                break;
            case EAction.COPY:
                CopyOperator copyOperator = action.getCopyOperator(0);
                copyOperator.addString(text.getString());
                break;

            }
            text = text.getNextLinkText();
        }
    }

    /**
     * �ָ�(�ⲿʹ��,�б���)
     * @param position int
     * @return boolean
     */
    public boolean separate(int position) {
        if (!canSeparate()) {
            return false;
        }
        if (position <= 0 || position >= getLength()) {
            return false;
        }
        return separateText(position, false);
    }

    /**
     * ����λ�÷ָ�Text(�ڲ�ʹ��)
     * @param position int
     * @param link boolean
     * @return boolean
     */
    public boolean separateText(int position, boolean link) {
        //�õ���һ������Text
        EText text = getNextLinkText();
        if (text == null) {
            text = getSeparateNewText(link);
        }
        //�ƶ�ѡ��λ��
        text.getSelectedModel().move(getLength() - position);
        if (text.getPanel() == getPanel()) {
            //ͬһ�����ָ�
            text.setStart(position + getStart());
            setEnd(getStart() + position);
        } else {
            //��ͬ���ָ�
            String s = getDString().getString(getStart() +
                                              position, getDString().size());
            getDString().remove(getStart() + position, getDString().size());
            text.getDString().add(0, s);
            text.getPanel().indexMove(text, s.length());
            text.setEnd(text.getEnd() + s.length());
            setEnd(getStart() + position);
        }
        //��������ڷָ����һ����,��������λ��
        if (getFocus() == this &&
            getFocusIndex() > position) {
            text.setFocus(text, getFocusIndex() - position);
        }
        //����ѡ��
        getSelectedModel().separate(position, text);
        return true;
    }

    /**
     * �õ��ָ�������
     * @param link boolean true ���� false ������
     * @return EText
     */
    public EText getSeparateNewText(boolean link) {
        //�����Լ���λ��
        int index = findIndex();
        //�õ��ָ������
        EText text = getSeparateNewText(index + 1);
        text.setStyle(getStyle());
        text.setEnd(getEnd());
        if (link) {
            text.setPreviousLinkText(this);
            setNextLinkText(text);
        }
        return text;
    }

    /**
     * �����������
     */
    public void clearPreviousLink() {
        if (!hasPreviousLink()) {
            return;
        }
        getPreviousLinkText().setNextLinkText(null);
        setPreviousLinkText(null);
    }

    /**
     * �����������
     */
    public void clearNextLink() {
        if (!hasNextLink()) {
            return;
        }
        getNextLinkText().setPreviousLinkText(null);
        setNextLinkText(null);
    }

    /**
     * ���ȫ������
     */
    public void clearLink() {
        clearPreviousLink();
        clearNextLink();
    }

    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setPreviousFocus() {
        if (getPreviousLinkText() != null) {
            return getPreviousLinkText().setFocusLast();
        }
        int index = findIndex() - 1;
        if (index >= 0) {
            IBlock block = getPanel().get(index);
            if (block instanceof ETable) {
                return ((ETable) block).setFocusLast();
            } else if (block instanceof EText) {
                return ((EText) block).setFocusLast();
            }
            return false;
        }
        return getPanel().setPreviousFocus();
    }

    /**
     * ������һ�����Ϊ����
     * @return boolean
     */
    public boolean setNextFocus() {
        if (getNextLinkText() != null) {
            return getNextLinkText().setFocus();
        }
        int index = findIndex() + 1;
        if (index < getPanel().size()) {
            IBlock block = getPanel().get(index);
            if (block instanceof ETable) {
                return ((ETable) block).setFocus();
            } else if (block instanceof EText) {
                return ((EText) block).setFocus();
            }
            return false;
        }
        return getPanel().setNextFocus();
    }

    /**
     * ɾ������
     * @param flg boolean true ȫ��ɾ�� false ��ͨɾ��
     */
    public void onDelete(boolean flg) {
        if (!flg && onDeleteActionIO(0, getLength())) {
            return;
        }
        onDeleteThis();
    }

    /**
     * ɾ���Լ�
     */
    public void removeThis() {
        EPanel panel = getPanel();
        if (panel == null) {
            return;
        }
        if (getNextLinkText() != null) {
            getNextLinkText().setPreviousLinkText(getPreviousLinkText());
        }
        if (getPreviousLinkText() != null) {
            getPreviousLinkText().setNextLinkText(getNextLinkText());
        }
        panel.remove(this);
        if (panel.size() == 0) {
            panel.removeThis();
        }
    }

    /**
     * ɾ���Լ�����
     */
    public void onDeleteThis() {
        setModify(true);
        boolean removeFlg = true;
        //��������ֻ��һ���������һ������
        if (!getPanel().hasLink() && getPanel().size() == 1) {
            if (getFocus() == this) {
                setFocus();
            }
            removeFlg = false;
        } else if (getFocus() == this) {
            if (!setPreviousFocus()) {
                if (!setNextFocus()) {
                    setFocus();
                    removeFlg = false;
                }
            }
        }

        //
        getSelectedModel().clear();

        //
        if ( getModifyNodeIndex() == -1 && null== getModifyIndex() ) {

        	this.removeString(removeFlg);

            return;
        }

        //
        if ( null!=this.getIsSave() && isDeleteFlg() ) {
            return;
        }

        if ( getKeyIndex() > getModifyNodeIndex() ) {
            return;
        }

        /**
        //
        if ( ( getModifyNodeIndex() == getKeyIndex() && null==this.getIsSave() ) ||
        		( getModifyNodeIndex() >0 && null==this.getIsSave() ) ||
        		( getModifyNodeIndex() == -1 && null== getModifyIndex()) ) {

        	this.removeString(removeFlg);

            return;
        }
        */

        if (getNextLinkText() != null) {
            getNextLinkText().setPreviousLinkText(null);
            setNextLinkText(null);
        }
        if (getPreviousLinkText() != null) {
            getPreviousLinkText().setNextLinkText(null);
            setPreviousLinkText(null);
        }

        //
        this.addModify();

        //
        DStyle style = getStyle().modifyIsDeleteLine(true,
                getModifyNodeIndex() + 1);
        setStyle(style);
        setKeyIndex(getModifyNodeIndex());
        setDeleteFlg(true);
    }

    /**
     *
     * @param removeFlg
     */
    private void removeString(boolean removeFlg){

        int size = getLength();
        getPanel().indexMove(this, -size);
        getDString().remove(getStart(), size);
        setEnd(getStart());
        if (removeFlg) {
            removeThis();
        }
        if (getNextLinkText() != null) {
            getNextLinkText().setPreviousLinkText(getPreviousLinkText());
        }
        if (getPreviousLinkText() != null) {
            getPreviousLinkText().setNextLinkText(getNextLinkText());
        }

    }

    /**
     * �õ���ֵ
     * @return String
     */
    public String getBlockValue() {
        StringBuffer sb = new StringBuffer();
        EText text = getHeadText();
        while (text != null) {
            sb.append(text.getString());
            text = text.getNextLinkText();
        }
        return sb.toString();
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getNextTrueBlock() {
        EText text = getEndText();
        return text.getNextBlock();
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock() {
        EText text = getHeadText();
        return text.getPreviousBlock();
    }

    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel) {
        EText text = new EText(panel);
        text.setStart(panel.getDString().size());
        text.setEnd(getStart());
        text.setString(getString());
        text.setKeyIndex(getKeyIndex());
        text.setDeleteFlg(isDeleteFlg());
        text.setStyleOther(getStyle());
        return text;
    }

    /**
     * ����
     */
    public void arrangement() {
        EText text = getNextLinkText();
        if (text == null) {
            return;
        }
        addString(text.getString());
        text.clearString();
        setNextLinkText(text.getNextLinkText());
        if (text.getNextLinkText() != null) {
            text.getNextLinkText().setPreviousLinkText(this);
        }
        text.setNextLinkText(null);
        if (this instanceof EFixed && text instanceof EFixed) {
            EFixed e = (EFixed)this;
            EFixed en = (EFixed) text;
            e.setNextFixed(en.getNextFixed());
            if (en.getNextFixed() != null) {
                en.getNextFixed().setPreviousFixed(e);
            }
            en.setNextFixed(null);
        }
        text.removeThis();
    }

    /**
     * �����к�
     * @param rowID int
     */
    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    /**
     * �õ��к�
     * @return int
     */
    public int getRowID() {
        return rowID;
    }

}
