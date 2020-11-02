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
 * <p>Title: 文本元素</p>
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
     * 设置尺寸调整
     */
    private ResetText resetText;
    /**
     * 选蓝处理对象
     */
    private CSelectedTextModel selectedModel;
    /**
     * 行号
     */
    private int rowID;
    /**
     * 构造器
     * @param panel EPanel
     */
    public EText(EPanel panel) {
        setParent(panel);
        //初始化选蓝对象
        setSelectedModel(new CSelectedTextModel(this));
        //设置默认样式
        setStyle(getDefaultStyle());
        //修改标记
        setModify(true);
        setResetText(new ResetText(this));
    }

    /**
     * 得到面板
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
     * 得到默认风格
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
     * 得到分割新组件
     * @param index int
     * @return EText
     */
    public EText getSeparateNewText(int index) {
        return getPanel().newText(index);
    }

    /**
     * 分割位置
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
     * 设置开始焦点
     * @param com EComponent
     */
    public void setStartFocus(EComponent com) {
        //getFocusManager().setStartFocus(com);
    }

    /**
     * 得到焦点
     * @return EComponent
     */
    public EComponent getStartFocus() {
        return null; //getFocusManager().getStartFocus();
    }

    /**
     * 是否显示光标
     * @return boolean
     */
    public boolean isShowCursor() {
        return getFocusManager().isShowCursor();
    }

    /**
     * 设置开始焦点位置
     * @param focusIndex int
     */
    public void setStartFocusIndex(int focusIndex) {
        //getFocusManager().setStartFocusIndex(focusIndex);
    }

    /**
     * 得到开始焦点位置
     * @return int
     */
    public int getStartFocusIndex() {
        return 0; //getFocusManager().getStartFocusIndex();
    }

    /**
     * 得到字符串宽度
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
     * 得到指定位置的宽度
     * @param index int
     * @return int
     */
    public int getIndexWidth(int index) {
        return stringWidth(getString(index));
    }

    /**
     * 绘制
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
            //绘制焦点背景
            paintFocusBack(g, x, y, width, height);
        }
        String s = getShowString();
        DStyle style = getStyle();
        int textX = x + (int) (getShowLeftW() * getZoom() / 75.0 + 0.5);
        if (style != null) {
            style.paint(g, s, textX, y, width, height, this);
        }
        if (!isSelectedDraw()) {
            //绘制焦点
            paintFocus(g, textX, y, width, height);
        }
        //绘制其他
        paintOther(g, x, y, width, height);

        //
        //paintRequired(g, x, y, width, height);
    }

    /**
     * 有上下标绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param position int 0无,1上标,2下标
     */
    public void paint(Graphics g, int x, int y, int width, int height,
                      int position) {
        if (getViewManager().isTextDebug()) {
            g.setColor(new Color(0, 255, 0));
            g.drawRect(x, y, width, height);
        }
        if (!isSelectedDraw()) {
            //绘制焦点背景
            paintFocusBack(g, x, y, width, height);
        }
        String s = getShowString();
        DStyle style = getStyle();
        int textX = x + (int) (getShowLeftW() * getZoom() / 75.0 + 0.5);
        if (style != null) {
            style.paint(g, s, textX, y, width, height, this,position);
        }
        if (!isSelectedDraw()) {
            //绘制焦点
            paintFocus(g, textX, y, width, height);
        }
        //绘制其他
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
		int n = 3; // 实线段长度
		int m = 3; // 虚线段长度

		int tx = 0, ty = 0;

		int c = 0;
		boolean flag = true; // 标记 有没有画完（达到要求的长度）

		int mark_x = 0; // 标记 要画的是 水平线（值为1）
		int mark_y = 0; // 标记 要画的是 垂直线（值为1）
		// 要么0，要么 1
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
			x = (int) (c * (n + m) * mark_x + x1); // 更新 实线段 + 虚线段
			y = (int) (c * (n + m) * mark_y + y1);

			if (x > x2 || y > y2)
				break;
			c++;
		} while (flag);

	}

    /**
	 * 绘制其他
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
     * 打印
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
        //绘制其他
        paintOther(g, x, y, getWidth(), getHeight());
    }

    /**
     * 绘制焦点背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFocusBack(Graphics g, int x, int y, int width, int height) {

    }

    /**
     * 绘制焦点
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
     * 左键按下
     * @param mouseX int
     * @param mouseY int
     * @return int 点选类型
     * -1 没有选中
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     */
    public int onMouseLeftPressed(int mouseX, int mouseY) {
        mouseX = (int) (mouseX / getZoom() * 75.0 + 0.5) - getShowLeftW();
        int index = getStyle().getIndex(getString(), mouseX);
        //设置焦点
        setFocus(index);
        return 1;
    }

    /**
     * 右键按下
     * @param mouseX int
     * @param mouseY int
     */
    public void onMouseRightPressed(int mouseX, int mouseY) {

    }

    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX, int mouseY) {
        return false;
    }

    /**
     * 查找下一个组件
     * @return EText
     */
    public EText getNextText() {
        return getNextText(this, false);
    }

    /**
     * 查找下一个组件
     * @param text EText
     * @param includeTable boolean
     *  true 找到下一个最近的Text
     *  false 如果下一个不是Text 返回 null
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
     * 得到前一个组件
     * @return EText
     */
    public EText getPreviousText() {
        return getPreviousText(this, false);
    }

    /**
     * 查找前一个组件
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
     * 得到上一个组件
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
     * 得到下一个组件
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

    // 修改标记
    private Integer modifyIndex = null;
    public Integer getModifyIndex() {
		return modifyIndex;
	}
	public void setModifyIndex(Integer modifyIndex) {
		this.modifyIndex = modifyIndex;
	}

    // 保存标记
    private String isSave = null;
    public String getIsSave() {
		return isSave;
	}
	public void setIsSave(String isSave) {
		this.isSave = isSave;
	}

	/**
     * 粘贴字符
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c) {


        //不能编辑
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
     * 添加修改记录
     */
    private void addModify() {

		if (getModifyNodeIndex() > -1) {

			String userId = getPM().getUserId();
			String userName = getPM().getUserName();

			if( null!=userId && !userId.equals("") && null!=userName && !userName.equals("") ){

				String dateStr = StringTool.getString(new Date(),
						"yyyy年MM月dd日 HH时mm分ss秒");

				//
				MModifyNode modifyNode = getPM().getModifyNodeManager();
				EModifyInf infDr = new EModifyInf();
				// 修改时间
				infDr.setModifyDate(dateStr);
				// 修改人
				infDr.setUserID( userId );
				infDr.setUserName( userName );

				modifyNode.add(infDr);
				this.modifyIndex = modifyNode.size();
				infDr.setID(this.modifyIndex);
			}
		}
	}


    /**
	 * 插入文本(使用在修改记录插入char中)
	 *
	 * @param index
	 *            int
	 * @param style
	 *            DStyle
	 * @return EText 备注:没有考虑固定文本的分割情况
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
	 * 插入文本
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
        //===旧数据错乱的弥补===begin
        if (s.size() == 0 && getStart() > 0) {
            setStart(0);
            setEnd(0);
            setFocusIndex(0);
        }
        //===旧数据错乱的弥补===end
        eventModify();
        int index = getFocusIndex();
        s.add(getStart() + index, c);
        setEnd(getEnd() + 1);
        getPanel().indexMove(this, 1);
        //焦点向后移动一个字符
        setFocusIndex(getFocusIndex() + 1);
        return true;
    }

    /**
     * 删除本控件转移焦点
     * @return boolean
     */
    private boolean deleteGoToFocus() {
        //焦点移动到上一个连接Text的最后
        EText text = getPreviousLinkText();
        if (text != null) {
            text.setFocusLast();
            return true;
        }
        //焦点转移前一个组件
        text = getPreviousText();
        if (text != null) {
            setFocus(text, getFocusIndex() + getStart() - text.getStart());
            return true;
        }
        //焦点转移下一个组件
        text = getNextText();
        if (text != null) {
            setFocus(text, getFocusIndex() + getStart() - text.getStart());
            return true;
        }
        return false;
    }

    /**
     * 向后删除
     * @return boolean
     */
    public boolean deleteChar() {
        //不能编辑
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
                //向右面合并
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
            //向左面合并
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
            //向右面合并
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
     * 向右删除
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
            //删除下一个Dtext的首字符
            text = getNextText();
            if (text != null) {
                setFocus(text, 0);
                return text.deleteChar();
            }
            EComponent com = getNextComponent();
            if (com != null) {
                //temp衔接Table和其它组件删除用
                return false;
            }
            //衔接下一个段落
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
        //DText被删除
        if (deleteGoToFocus()) {
            int i = findIndex();
            removeThis();
            //释放自己
            free();
            //合并相同的EText
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
        //向右面合并
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
        //向左面合并
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
     * 向前删除
     * @return boolean
     */
    public boolean backspaceChar() {
        //不能编辑
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
            //向右面合并
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
            //向左面合并
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
                //向右面合并
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
            //向左面合并
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
     * 向前删除
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
            //判断是否存在上一个连接Text
            EText text = getPreviousLinkText();
            if (text != null) {
                setFocus(text, text.getEnd() - text.getStart());
                return text.backspaceChar();
            }
            //存在本面板的上一个Text
            text = getPreviousText();
            if (text != null) {
                setFocus(text, text.getEnd() - text.getStart());
                return text.backspaceChar();
            }
            EComponent com = getPreviousComponent();
            if (com != null) {
                //temp衔接Table和其它组件删除用
                return false;
            }
            //衔接上一个段落
            if (!linkPreviousPanel()) {// lx 有回车应该走这段逻辑
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
        //DText被删除
        if (deleteGoToFocus()) {
            //恢复连接
            if (getPreviousLinkText() != null) {
                getPreviousLinkText().setNextLinkText(getNextLinkText());
            }
            if (getNextLinkText() != null) {
                getNextLinkText().setPreviousLinkText(getPreviousLinkText());
            }
            int i = findIndex();
            removeThis();
            //释放自己
            free();
            //合并相同的EText
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
     * 连接上一个段落
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
     * 连接上一个Text
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
        //衔接固定文本
        if (block instanceof EFixed) {
            if (!(this instanceof EFixed)) {
                return;
            }
            if (((EFixed) block).getNextFixed() != this) {
                return;
            }
        }
        //衔接宏
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
     * 连接下一个段落
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
     * 向左移动光标
     * @return boolean
     */
    public boolean onFocusToLeft() {
        int index = getFocusIndex();
        //在Text内移动焦点
        if (index > 0) {
            return moveFocus( -1);
        }

        //有连接折行移动焦点
        EText previous = getPreviousLinkText();
        if (previous != null) {
            return previous.setFocus(previous.getLength() - 1);
        }

        //调转本面板的上一个组件
        IBlock block = getPreviousComponent();
        if (block != null) {
            //Text得到焦点
            if (block instanceof EText) {
                return ((EText) block).setFocus(((EText) block).getLength() - 1);
            }
            //Table得到焦点
            if (block instanceof ETable) {
                return ((ETable) block).setFocusLast();
            }
        }

        //向左移动光标到上一个连接面板
        if (getPanel().onLinkPanelFocusToLeft()) {
            return true;
        }
        //向左移动光标到上一个面板
        if (getPanel().onNextPanelFocusToLeft()) {
            return true;
        }

        return false;
    }

    /**
     * 向右移动光标
     * @return boolean
     */
    public boolean onFocusToRight() {
        int index = getFocusIndex();
        //在Text内移动焦点
        if (index < getLength()) {
            return moveFocus(1);
        }

        //有连接折行移动焦点
        EText text = getNextLinkText();
        if (text != null) {
            return text.setFocus();
        }

        //调转本面板的下一个组件
        IBlock block = getNextComponent();
        if (block != null) {
            //Text得到焦点
            if (block instanceof EText) {
                EText t = (EText) block;
                if (t.getLength() > 0) {
                    return t.setFocus(1);
                } else {
                    t.setFocus();
                }
            }
            //调到末尾
            if (index < getLength()) {
                return moveFocus(1);
            }
            //Table得到焦点
            if (block instanceof ETable) {
                return ((ETable) block).setFocus();
            }
        }

        //向右移动光标到下一个连接面板
        if (getPanel().onLinkPanelFocusToRight()) {
            return true;
        }

        //调到末尾
        if (index < getLength()) {
            return moveFocus(1);
        }

        //向右移动光标到下一个面板
        if (getPanel().onNextPanelFocusToRight()) {
            return true;
        }

        return false;
    }

    /**
     * 向上移动光标
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
     * 向下移动光标
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
     * 按下Tab
     * @param flg boolean true 向下 false 向上
     * @return boolean
     */
    public boolean onFocusToTab(boolean flg) {
        return false;
    }

    /**
     * 计算光标位置在合时的组件
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
            //位置在组件的左侧
            if (x <= x0) {
                setFocus(text, 0);
                return;
            }
            //位置在组件的右侧
            if (x > x0 + text.getWidth()) {
                //如果组件是本行最后一个组件
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
            //设置焦点
            setFocus(text, index);
            return;
        }
        if (block instanceof ETable) {

            return;
        }
    }

    /**
     * 得到下一个组件
     * @return IBlock
     */
    public IBlock getNextBlock() {
        return getPanel().get(findIndex() + 1);
    }

    /**
     * 得到上一个组件
     * @return IBlock
     */
    public IBlock getPreviousBlock() {
        return getPanel().get(findIndex() - 1);
    }

    /**
     * 得到行首模块
     * @return IBlock
     */
    public IBlock getHomeBlock() {
        return getPanel().getHomeBlock(this);
    }

    /**
     * 得到行尾模块
     * @return IBlock
     */
    public IBlock getEndBlock() {
        return getPanel().getEndBlock(this);
    }

    /**
     * 得到上一行行首
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
        //移动到上一个TD中
        if (getPanel().parentIsTD()) {
            return getPanel().getParentTD().getUpFocusBlock();
        }
        return null;
    }

    /**
     * 得到下一行行首
     * @return IBlock
     */
    public IBlock getNextRowFirstBlock() {
        //得到本行的末尾对象
        IBlock end = getEndBlock();
        if (end == null) {
            return null;
        }
        //得到下移行的首对象
        end = getPanel().getHomeBlock(end.findIndex() + 1);
        if (end != null) {
            return end;
        }
        //得到下一个面板
        EPanel panel = getPanel().getNextPanel();
        if (panel != null) {
            EText home = panel.getFirstText();
            if (home == null) {
                return null;
            }
            return home.getHomeBlock();
        }
        //移动到下一页中
        EText home = getPanel().getNextPageFirstText();
        if (home != null) {
            return home.getHomeBlock();
        }
        //移动到下一个TD中
        if (getPanel().parentIsTD()) {
            return getPanel().getParentTD().getDownFocusBlock();
        }
        return null;
    }

    /**
     * 得到行首
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
     * 得到行尾
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
     * 移动光标到行首
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
     * 移动光标到行尾
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
     * 回车事件
     * @return boolean
     */
    public boolean onEnter() {
        //不能编辑
        if (!canEdit()) {
            return false;
        }
        EText text = this;
        text.setModify(true);
        int index = getFocusIndex();
        //处理Table首行回车,在Table前面插入空段落
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
                //默认换行字体修改20110311 WangM
                text = getPanel().newText(findIndex() + 1);
                text.setStyle(getStyle());
                text.setBlock(getEnd(), getEnd());
            } else {
                //能否回车拆分组件
                if (!canEnterSeparate()) {
                    return false;
                }

                //拆分组件
                if (separate(index)) {
                    text = getNextText();
                }
            }
            setFocus(text, 0);
        }
        //del by lx 2013/03/20 分割连接
       /* if(text!=null){
            if (text.getPreviousLinkText() != null) {
                //System.out.println("=====getPreviousLinkText()===="+getPreviousLinkText());
                if(getPreviousLinkText()!=null){
                    getPreviousLinkText().setNextLinkText(null);
                    setPreviousLinkText(null);
                }
            }
        }*/
        //add by lx 2013/03/20 分割连接
        if(text!=null){
        	//本段非第一行的情况
            if (text.getPreviousLinkText() != null) {
            	//上段
            	//text.getPreviousLinkText().setPreviousLinkText(null);
            	text.getPreviousLinkText().setNextLinkText(null);
            	//下段
            	text.setPreviousLinkText(null);
            	//text.setNextLinkText(null);
            }
        }

        //回车分割
        getPanel().separateText(text);
        return true;
    }

    /**
     * 能否回车拆分组件
     * @return boolean
     */
    public boolean canEnterSeparate() {
        return true;
    }

    /**
     * 事件修改
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
     * 得到当前所在的行数
     * @return int
     */
    public int getRowIndex() {
        return getRowIndex(this);
    }

    /**
     * 得到当前所在的行数
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
     * 调整尺寸
     */
    public void reset() {
        //if(!isModify())
        //    return;
        if (getStyle() == null) {
            return;
        }
        //清零
        getResetText().clear();
        //设置尺寸范围
        getResetText().setRectangle(0,
                                    0,
                                    getMaxWidth(),
                                    getMaxHeight());
        //调整尺寸
        getResetText().reset();
        //getStyle().resetSize(getString(),this);
        setModify(false);
    }

    /**
     * 调整尺寸
     * @param index int
     */
    public void reset(int index) {
    }

    /**
     * 释放自己
     */
    public void free() {

    }

    /**
     * 得到坐标位置
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
     * 清除文字
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
     * 设置字符
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
     * 追加字符
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
     * 分割文本
     * @param index int
     * @return EText
     */
    public EText carveText(int index) {
        EText text = getPanel().newText(findIndex() + 1);
        text.setStart(getStart() + index);
        text.setEnd(getEnd());
        text.setStyle(getStyle());
        setEnd(getStart() + index);
        //分割连接
        text.setNextLinkText(getNextLinkText());
        if (getNextLinkText() != null) {
            getNextLinkText().setPreviousLinkText(text);
        }
        setNextLinkText(null);
        return text;
    }

    /**
     * 粘贴字符串
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
        //不存在回车符
        int enterIndex = text.indexOf("\n");
        if (enterIndex == -1) {
            getDString().add(index + getStart(), text);
            setEnd(getEnd() + text.length());
            getPanel().indexMove(this, text.length());
            
            if(defineStyle==null){
            	defineStyle=getDefaultStyle();
            }           
            //设置下对应的text的字体            
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
     * 调试对象组成
     * @param i int
     */
    public void debugShow(int i) {
        System.out.println(StringTool.fill(" ", i) + this);
    }

    /**
     * 调试修改
     * @param i int
     */
    public void debugModify(int i) {
        if (!isModify()) {
            return;
        }
        System.out.println(StringTool.fill(" ", i) + getIndexString() + " " + this);
    }

    /**
     * 能否合并
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
     * 得到页
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
     * 得到页号
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
     * 得到TD
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
     * 得到TR
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
     * 得到TRModel
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
     * 得到行号
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
     * 得到结束行
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
     * 得到Table
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
     * 得到Table控制器
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
     * 写对象
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
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s) throws IOException {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
    }

    /**
     * 写对象属性
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
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s) throws IOException {
        int id = s.readShort();
        while (id > 0) {
            //读对象属性
            readObjectAttribute(id, s);
            id = s.readShort();
        }
        setModify(true);
    }

    /**
     * 读对象属性
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
            //$$========modified by lx 2012-07-10 加入判断=========$$//
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
     * 得到焦点光标的横坐标位置
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
     * 得到路径
     * @return TList
     */
    public TList getPath() {
        TList list = new TList();
        getPath(list);
        return list;
    }

    /**
     * 得到路径
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
     * 得到路径索引
     * @return TList
     */
    public TList getPathIndex() {
        TList list = new TList();
        getPathIndex(list);
        return list;
    }

    /**
     * 得到路径索引
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
     * 比较位置大小
     * @param text EText
     * @return int
     * 1 自己在text之后
     * -1 自己在text之前
     * 0 位置相同
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
     * 得到查找文本列表
     * @param endText EText
     * @return TList
     */
    public TList getFindTextList(EText endText) {
        TList list = new TList();
        getFindTextList(list, endText);
        return list;
    }

    /**
     * 得到查找文本列表
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
     * 修改颜色
     * @param c Color
     */
    public void modifyColor(Color c) {
        setStyle(getStyle().modifyColor(c));
        setModify(true);
    }

    /**
     * 修改字体
     * @param s String
     */
    public void modifyFont(String s) {
        setStyle(getStyle().modifyFont(s));
        setModify(true);
    }

    /**
     * 修改字体粗体
     * @param bold boolean
     */
    public void modifyBold(boolean bold) {
        setStyle(getStyle().modifyBold(bold));
        setModify(true);
    }

    /**
     * 修改字体斜体
     * @param italic boolean
     */
    public void modifyItalic(boolean italic) {
        setStyle(getStyle().modifyItalic(italic));
        setModify(true);
    }

    /**
     * 修改字体尺寸
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
     * 测试使用样式
     */
    public void checkGCStyle() {
        if (getStyle() == null) {
            return;
        }
        getStyle().setGC(false);
    }

    /**
     * 鼠标移动
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
     * 鼠标移动
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
     * 修复失去焦点'Tooltip'就不显示的问题
     * @param comp
     */
	public void postToolTip(JComponent comp) {

		//失去焦点后 action 就为空了
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
     * 得到屏幕坐标
     * @return DPoint
     */
    public DPoint getScreenPoint() {
        DPoint point = getPanel().getScreenPoint();
        point.setX(point.getX() + (int) (getX() * getZoom() / 75.0 + 0.5));
        point.setY(point.getY() + (int) (getY() * getZoom() / 75.0 + 0.5));
        return point;
    }

    /**
     * 刷新数据
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
     * 设置尺寸调整对象
     * @param resetText ResetText
     */
    public void setResetText(ResetText resetText) {
        this.resetText = resetText;
    }

    /**
     * 得到尺寸调整对象
     * @return ResetText
     */
    public ResetText getResetText() {
        return resetText;
    }

    /**
     * 清除修改痕迹
     */
    public void resetModify() {
        if (!isModify()) {
            return;
        }
        setModify(false);
    }

    /**
     * 是否选中绘制
     * @return boolean
     */
    public boolean isSelectedDraw() {
        return getPanel().isSelectedDraw();
    }

    /**
     * 是否是粗体
     * @return boolean
     */
    public boolean isBold() {
        return getStyle().getFont().isBold();
    }

    /**
     * 是否是斜体
     * @return boolean
     */
    public boolean isItalic() {
        return getStyle().getFont().isItalic();
    }

    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize() {
        return getStyle().getFont().getSize();
    }

    /**
     * 得到字体名称
     * @return String
     */
    public String getFontName() {
        return getStyle().getFont().getName();
    }

    /**
     * 得到组件所在的TD
     * @return ETD
     */
    public ETD getComponentInTD() {
        return getPanel().getComponentInTD();
    }

    /**
     * 得到下一个有效的Text包括连接面板
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
     * 得到表格所在的面板
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
     * 得到Table行号
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
     * 得到Table列号
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
     * 设置选蓝对象
     * @param selectedModel CSelectedTextModel
     */
    public void setSelectedModel(CSelectedTextModel selectedModel) {
        this.selectedModel = selectedModel;
    }

    /**
     * 得到选蓝对象
     * @return CSelectedTextModel
     */
    public CSelectedTextModel getSelectedModel() {
        return selectedModel;
    }

    /**
     * 清除选择
     */
    public void clearSelected() {
        getSelectedModel().removeAll();
    }

    /**
     * 选蓝
     * @param start int
     * @param end int
     * @return CSelectTextBlock
     */
    public CSelectTextBlock selectText(int start, int end) {
        return getSelectedModel().add(start, end);
    }

    /**
     * 全部选蓝
     * @return CSelectTextBlock
     */
    public CSelectTextBlock selectAll() {
        return getSelectedModel().add(0, getLength());
    }

    /**
     * 字符是否选中
     * @param index int
     * @return boolean
     */
    public boolean isSelectedChar(int index) {
        return getSelectedModel().isSelectedChar(index);
    }

    /**
     * 是否选蓝
     * @return boolean
     */
    public boolean isSelected() {
        return getSelectedModel().isSelected();
    }

    /**
     * 是否有向上连接
     * @return boolean
     */
    public boolean hasPreviousLink() {
        return getPreviousLinkText() != null;
    }

    /**
     * 是否有向下连接
     * @return boolean
     */
    public boolean hasNextLink() {
        return getNextLinkText() != null;
    }

    /**
     * 得到首Text
     * @return EText
     */
    public EText getHeadText() {
        if (hasPreviousLink()) {
            return getPreviousLinkText().getHeadText();
        }
        return this;
    }

    /**
     * 得到尾Text
     * @return EText
     */
    public EText getEndText() {
        if (hasNextLink()) {
            return getNextLinkText().getEndText();
        }
        return this;
    }

    /**
     * 执行动作
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
     * 分割(外部使用,有保护)
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
     * 按照位置分割Text(内部使用)
     * @param position int
     * @param link boolean
     * @return boolean
     */
    public boolean separateText(int position, boolean link) {
        //得到下一个连接Text
        EText text = getNextLinkText();
        if (text == null) {
            text = getSeparateNewText(link);
        }
        //移动选蓝位置
        text.getSelectedModel().move(getLength() - position);
        if (text.getPanel() == getPanel()) {
            //同一个面板分割
            text.setStart(position + getStart());
            setEnd(getStart() + position);
        } else {
            //不同面板分割
            String s = getDString().getString(getStart() +
                                              position, getDString().size());
            getDString().remove(getStart() + position, getDString().size());
            text.getDString().add(0, s);
            text.getPanel().indexMove(text, s.length());
            text.setEnd(text.getEnd() + s.length());
            setEnd(getStart() + position);
        }
        //如果焦点在分割的下一快中,调整焦点位置
        if (getFocus() == this &&
            getFocusIndex() > position) {
            text.setFocus(text, getFocusIndex() - position);
        }
        //处理选蓝
        getSelectedModel().separate(position, text);
        return true;
    }

    /**
     * 得到分割的新组件
     * @param link boolean true 连接 false 不连接
     * @return EText
     */
    public EText getSeparateNewText(boolean link) {
        //查找自己的位置
        int index = findIndex();
        //得到分割新组件
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
     * 清除向上连接
     */
    public void clearPreviousLink() {
        if (!hasPreviousLink()) {
            return;
        }
        getPreviousLinkText().setNextLinkText(null);
        setPreviousLinkText(null);
    }

    /**
     * 清除向下连接
     */
    public void clearNextLink() {
        if (!hasNextLink()) {
            return;
        }
        getNextLinkText().setPreviousLinkText(null);
        setNextLinkText(null);
    }

    /**
     * 清除全部连接
     */
    public void clearLink() {
        clearPreviousLink();
        clearNextLink();
    }

    /**
     * 设置上一个组件为焦点
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
     * 设置下一个组件为焦点
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
     * 删除动作
     * @param flg boolean true 全部删除 false 普通删除
     */
    public void onDelete(boolean flg) {
        if (!flg && onDeleteActionIO(0, getLength())) {
            return;
        }
        onDeleteThis();
    }

    /**
     * 删除自己
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
     * 删除自己动作
     */
    public void onDeleteThis() {
        setModify(true);
        boolean removeFlg = true;
        //如果面板中只有一个组件保留一个空行
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
     * 得到块值
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
     * 得到下一个真块
     * @return IBlock
     */
    public IBlock getNextTrueBlock() {
        EText text = getEndText();
        return text.getNextBlock();
    }

    /**
     * 得到上一个真块
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock() {
        EText text = getHeadText();
        return text.getPreviousBlock();
    }

    /**
     * 克隆
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
     * 整理
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
     * 设置行号
     * @param rowID int
     */
    public void setRowID(int rowID) {
        this.rowID = rowID;
    }

    /**
     * 得到行号
     * @return int
     */
    public int getRowID() {
        return rowID;
    }

}
