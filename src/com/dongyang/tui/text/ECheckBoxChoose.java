package com.dongyang.tui.text;

import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import java.io.IOException;
import com.dongyang.tui.DText;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.BasicStroke;
import com.dongyang.tui.DCursor;
import com.dongyang.ui.base.TWordBase;
import com.dongyang.wcomponent.emr.IECValue;
import com.dongyang.wcomponent.util.TiString;

/**
 *
 * <p>Title: 选择框</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.30
 * @author whao 2013~
 * @version 1.0
 */
public class ECheckBoxChoose extends EFixed {

	/**
	 * 选择值
	 */
	private boolean checked;
	/**
	 * 能否编辑
	 */
	private boolean enabled = true;
	/**
	 * 选择框左面
	 */
	private boolean inLeft = true;
	/**
	 * 选中事件脚本
	 */
	private String checkedScript = "";
	/**
	 * 组名
	 */
	private String groupName = "";
	/**
	 * CDA组名
	 */
	private String cdaGroupName = "";

	/**
	 * 组件值
	 */
	private String cbValue = "0";


	/**
	 * 构造器
	 * @param panel EPanel
	 */
	public ECheckBoxChoose(EPanel panel) {
		super(panel);
	}

	/**
	 * 设置选择值
	 * @param checked boolean
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	/**
	 * 得到选择值
	 * @return boolean
	 */
	public boolean isChecked() {
		return checked;
	}

	/**
	 * 设置选择框是否在左面
	 * @param inLeft boolean
	 */
	public void setInLeft(boolean inLeft) {
		this.inLeft = inLeft;
	}

	/**
	 * 选择框是否在左面
	 * @return boolean
	 */
	public boolean inLeft() {
		return inLeft;
	}

	/**
	 * 设置能否编辑
	 * @param enabled boolean
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 是否可以编辑
	 * @return boolean
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 得到新对象
	 * @param index int
	 * @return EFixed
	 */
	public EFixed getNewObject(int index) {
		return getPanel().newCheckBoxChoose(index);
	}

	/**
	 * 得到坐标位置
	 * @return String
	 */
	public String getIndexString() {
		EPanel panel = getPanel();
		if (panel == null)
			return "Parent:Null";
		return panel.getIndexString() + ",ECheckBoxChoose:" + findIndex();
	}

	public String toString() {
		return "<ECheckBoxChoose name="
				+ getName()
				+ ",start="
				+ getStart()
				+ ",end="
				+ getEnd()
				+ ",s="
				+ getString()
				+ ",position="
				+ getPosition()
				+ ",index="
				+ getIndexString()
				+ ",p="
				+ (getPreviousFixed() != null ? "["
						+ getPreviousFixed().getIndexString() + "]" : "null")
				+ ",n="
				+ (getNextFixed() != null ? "["
						+ getNextFixed().getIndexString() + "]" : "null")
				+ ",ecvalue="
				+ getCbValue()
						+ ">";
	}

	/**
	 * 绘制固定文本焦点背景
	 * @param g Graphics
	 * @param x int
	 * @param y int
	 * @param width int
	 * @param height int
	 */
	public void paintFixedFocusBack(Graphics g, int x, int y, int width,
			int height) {
		super.paintFixedFocusBack(g, x, y, width, height);
	}

	/**
	 * 双击
	 * @param mouseX int
	 * @param mouseY int
	 * @return boolean
	 */
	public boolean onDoubleClickedS(int mouseX, int mouseY) {
		return false;
		//openVauePopupMenu();
	}

	/**
	 * 属性对话框
	 */
	public void openPropertyDialog() {
		EFixed fixed = getHeadFixed();
		String value = (String) getPM().getUI().openDialog(
				"%ROOT%\\config\\database\\CheckBoxChooseEdit.x", fixed);
		if (value == null || !"OK".equals(value))
			return;
		fixed.getPM().getFileManager().update();
	}

	/**
	 * 写对象属性
	 * @param s DataOutputStream
	 * @throws IOException
	 */
	public void writeObjectAttribute(DataOutputStream s) throws IOException {
		super.writeObjectAttribute(s);
		s.writeBoolean(100, isChecked(), false);
		s.writeBoolean(101, inLeft(), true);
		s.writeBoolean(102, isEnabled(), true);
		s.writeString(103, getCheckedScript(), "");
		s.writeString(104, getGroupName(), "");
		//
		s.writeString(105, this.getCbValue(), "");
	}

	/**
	 * 读对象属性
	 * @param id int
	 * @param s DataInputStream
	 * @return boolean
	 * @throws IOException
	 */
	public boolean readObjectAttribute(int id, DataInputStream s)
			throws IOException {
		if (super.readObjectAttribute(id, s))
			return true;
		switch (id) {
		case 100:
			setChecked(s.readBoolean());
			return true;
		case 101:
			setInLeft(s.readBoolean());
			return true;
		case 102:
			setEnabled(s.readBoolean());
			return true;
		case 103:
			setCheckedScript(s.readString());
			return true;
		case 104:
			setGroupName(s.readString());
			return true;			
		case 105:
			this.setCbValue(s.readString());
			return true;
		}
		return false;
	}

	/**
	 * 固定文本区域能否编辑
	 * @param index int
	 * @param type int
	 *  1 输入字符
	 *  2 向前删除
	 *  3 向后删除
	 * @return boolean true 能编辑 false 不能编辑
	 */
	public boolean canEdit(int index, int type) {
		return false;
	}

	/**
	 * 得到对象类型
	 * @return int
	 */
	public int getObjectType() {
		return CHECK_BOX_CHOOSE_TYPE;
	}

	/**
	 * 刷新数据
	 * @param action EAction
	 */
	public void resetData(EAction action) {
	}

	/**
	 * 得到显示字串
	 * @return String
	 */
	public String getShowString() {
		return super.getShowString();
	}

	/**
	 * 得到显示左面孔穴宽度
	 * @return int
	 */
	public int getShowLeftW() {
		if (inLeft() && !hasPreviousFixed())
			return getStyle().getFontHeight();
		return 0;
	}

	/**
	 * 得到显示右面孔穴宽度
	 * @return int
	 */
	public int getShowRightW() {
		if (!inLeft() && !hasNextFixed())
			return getStyle().getFontHeight();
		return 0;
	}

	/**
	 * 能否拆分
	 * @return boolean
	 */
	public boolean canSeparate() {
		return false;
	}

	/**
	 * 得到UI
	 * @return DText
	 */
	public DText getUI() {
		PM pm = getPM();
		if (pm == null)
			return null;
		return pm.getUI();
	}

	/**
	 * 绘制其他
	 * @param g Graphics
	 * @param x int
	 * @param y int
	 * @param width int
	 * @param height int
	 */
	public void paintOther(Graphics g, int x, int y, int width, int height) {
		Stroke oldStroke = ((Graphics2D) g).getStroke();
		float xx = (height - 5) / 13;
		if (xx < 1)
			xx = 1;
		((Graphics2D) g).setStroke(new BasicStroke(xx));
		g.setColor(new Color(0, 0, 0));
		int h = height - 5;
		int x0 = x + 2;
		if (!inLeft())
			x0 = x + width - h - 2;
		g.drawRect(x0, y + 2, h, h);
		if (isChecked()) {
			g.drawLine(x0 + h / 4, y + 2 + h / 2, x0 + h / 2, y + 2 + h
							/ 4 * 3);
			g.drawLine(x0 + h / 2, y + 2 + h / 4 * 3, x0 + h / 4 * 3, y + 2 + h
					/ 4);
		}
		((Graphics2D) g).setStroke(oldStroke);
	}

	/**
	 * 鼠标移动
	 * @param mouseX int
	 * @param mouseY int
	 * @return boolean
	 */
	public boolean onMouseMoved(int mouseX, int mouseY) {
		if (inLeft()) {
			int w = (int) (getShowLeftW() * getZoom() / 75.0 + 0.5);
			if (mouseX < getShowLeftW()) {
				getUI().setCursor(DCursor.HAND_CURSOR);
				return true;
			}
		} else {
			int w = (int) (getWidth() * getZoom() / 75.0 + 0.5);
			int w1 = w - (int) (getShowRightW() * getZoom() / 75.0 + 0.5);
			if (mouseX > w1) {
				getUI().setCursor(DCursor.HAND_CURSOR);
				return true;
			}
		}
		return false;
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
		if (inLeft()) {
			int x = (int) (mouseX / getZoom() * 75.0 + 0.5);
			int w = getShowLeftW();
			if (x < w) {
				if (!isMouseDragged())
					onClicked();
				setFocus();
				return 1;
			}
		} else {
			int w = (int) (getWidth() * getZoom() / 75.0 + 0.5);
			int w1 = w - (int) (getShowRightW() * getZoom() / 75.0 + 0.5);
			if (mouseX > w1) {
				if (!isMouseDragged())
					onClicked();
				setFocusLast();
				return 1;
			}
		}
		return super.onMouseLeftPressed(mouseX, mouseY);
	}

	/**
	 * 点击事件
	 */
	public void onClicked() {
		if (!isEnabled())
			return;
		if (getGroupName() != null && getGroupName().length() > 0) {
			if (isChecked()) {
				setChecked(!isChecked());
				//add by lx 2015/10/22   表达式自动 进行计算
				TWordBase twb = (TWordBase) this.getPageManager().getUI().getParentTCBase();
				twb.onCalculateExpression();
				//
				return;
			}
			ECheckBoxChoose choose = (ECheckBoxChoose) getPageManager()
					.findGroup(getGroupName());
			if (choose != null && choose != this)
				choose.setChecked(false);
		}
		setChecked(!isChecked());
		//执行选中事件脚本
		invokeCheckedScript();
		
		//add by lx 2015/10/22   表达式自动 进行计算
		TWordBase twb = (TWordBase) this.getPageManager().getUI().getParentTCBase();
		twb.onCalculateExpression();
		//
	}

	/**
	 * 查询组
	 * @param groupName String
	 * @return EComponent
	 */
	public EComponent findGroup(String groupName) {
		if (groupName == null || groupName.length() == 0)
			return null;
		if (getGroupName() == null || getGroupName().length() == 0)
			return null;
		if (!getGroupName().equals(groupName))
			return null;
		if (!isChecked())
			return null;
		return this;
	}

	/**
	 * 查询组的值
	 * @param groupName String
	 * @return String
	 */
	public String findGroupValue(String groupName) {
		if (groupName == null || groupName.length() == 0)
			return null;
		if (getGroupName() == null || getGroupName().length() == 0)
			return null;
		if (!getGroupName().equals(groupName))
			return null;
		if (!isChecked())
			return null;
		return getName();
	}

	/**
	 * 执行选中事件脚本
	 */
	public void invokeCheckedScript() {
		if (getCheckedScript() == null || getCheckedScript().length() == 0)
			return;
		if (getCheckedScript().indexOf("|") > 0) {
			invokeMenthodString(getCheckedScript());
			return;
		}
		invokeMenthod(getCheckedScript(),
				new Object[] { getName(), isChecked() });
	}

	/**
	 * 执行方法
	 * @param methodName String
	 * @param parameters Object[]
	 * @return Object
	 */
	public Object invokeMenthod(String methodName, Object[] parameters) {
		return getSyntaxManager().invokeMenthod(methodName, parameters);
	}

	/**
	 * 执行方法
	 * @param message String
	 * @return Object
	 */
	public Object invokeMenthodString(String message) {
		return getSyntaxManager().invokeMenthodString(message);
	}

	/**
	 * 设置选中事件脚本
	 * @param checkedScript String
	 */
	public void setCheckedScript(String checkedScript) {
		this.checkedScript = checkedScript;
	}

	/**
	 * 得到选中事件脚本
	 * @return String
	 */
	public String getCheckedScript() {
		return checkedScript;
	}

	/**
	 * 克隆
	 * @param panel EPanel
	 * @return IBlock
	 */
	public IBlock clone(EPanel panel) {
		ECheckBoxChoose checkBoxChoose = new ECheckBoxChoose(panel);
		checkBoxChoose.setStart(panel.getDString().size());
		checkBoxChoose.setEnd(getStart());
		checkBoxChoose.setString(getString());
		checkBoxChoose.setKeyIndex(getKeyIndex());
		checkBoxChoose.setDeleteFlg(isDeleteFlg());
		checkBoxChoose.setStyleOther(getStyle());
		checkBoxChoose.setName(getName());
		checkBoxChoose.checked = checked;
		checkBoxChoose.enabled = enabled;
		checkBoxChoose.inLeft = inLeft;
		checkBoxChoose.checkedScript = checkedScript;
		checkBoxChoose.setGroupName(getGroupName());
		checkBoxChoose.setMicroName(getMicroName());
		checkBoxChoose.setDataElements(isIsDataElements());
		checkBoxChoose.setAllowNull(isAllowNull());
		return checkBoxChoose;
	}

	/**
	 * 设置组名
	 * @param groupName String
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	/**
	 * 得到组名
	 * @return String
	 */
	public String getGroupName() {
		return groupName;
	}

	public void setcCaGroupName(String cdaGroupName) {
		super.setGroupName(cdaGroupName);
	}

	public String getCdaGroupName() {
		return super.getGroupName();
	}


    //
	public String getCbValue() {
		return cbValue;
	}

	public void setCbValue(String cbValue) {
		this.cbValue = cbValue;
	}


}
