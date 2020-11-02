package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.Font;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: 风格管理器</p>
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
public class MStyle {

	/**
	 * 成员列表
	 */
	private TList components;

	/**
	 * 管理器
	 */
	private PM pm;

	/** */
	private boolean isReport = false;

	/**
	 * 构造器
	 */
	public MStyle(boolean isReport) {

		this.isReport = isReport;

		this.doInit();
	}

	/**
	 *
	 */
	private void doInit(){

		components = new TList();
		if ( this.isReport ) {
			initReportStyle();
		} else {
			//设置默认风格
			initDefaultStyle();
		}

		 //System.out.println( this );
	}

	/**
	 * 设置默认风格
	 */
	public void initDefaultStyle() {
		//如果是报表则
		//否则
		DStyle style = newStyle();
		style.setName("default");
		style.setColor(new Color(0, 0, 0));
		style.setFont(new Font("宋体", 0, 11));

	}

	/**
	 * 设置报表风格
	 */
	public void initReportStyle() {
		DStyle style = newStyle();
		style.setName("default");
		style.setColor(new Color(0, 0, 0));
		style.setFont(new Font("宋体", 0, 10));

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
	 * 增加成员
	 * @param s DStyle
	 */
	public void add(DStyle s) {
		if (s == null)
			return;
		components.add(s);
	}

	/**
	 * 删除成员
	 * @param s DStyle
	 */
	public void remove(DStyle s) {
		components.remove(s);
	}

	/**
	 * 成员个数
	 * @return int
	 */
	public int size() {
		return components.size();
	}

	/**
	 * 得到成员
	 * @param index int
	 * @return DStyle
	 */
	public DStyle get(int index) {
		return (DStyle) components.get(index);
	}

	/**
	 * 删除成员
	 * @param index int
	 */
	public void remove(int index) {
		components.remove(index);
	}

	/**
	 * 得到样式
	 * @param name String
	 * @return DStyle
	 */
	public DStyle get(String name) {
		if (name == null || name.length() == 0)
			return null;
		for (int i = 0; i < size(); i++) {
			DStyle style = get(i);
			if (style == null)
				continue;
			if (name.equals(style.getName()))
				return style;
		}
		return null;
	}

	/**
	 * 得到成员列表
	 * @return TList
	 */
	public TList getComponentList() {
		return components;
	}

	/**
	 * 设置父类
	 * @param parent EComponent
	 */
	public void setParent(EComponent parent) {
	}

	/**
	 * 得到父类
	 * @return EComponent
	 */
	public EComponent getParent() {
		return null;
	}

	/**
	 * 新样式
	 * @return DStyle
	 */
	public DStyle newStyle() {
		DStyle style = new DStyle(this);
		add(style);
		return style;
	}

	/**
	 * 得到新样式
	 * @param newStyle DStyle
	 * @return DStyle
	 */
	public DStyle getStyle(DStyle newStyle) {
		for (int i = 0; i < size(); i++) {
			DStyle s = get(i);
			if (s == null)
				continue;
			if (s.equals(newStyle))
				return s;
		}
		add(newStyle);
		return newStyle;
	}

	/**
	 * 清理多余的样式
	 */
	public void gcStyle() {
		for (int i = 1; i < size(); i++)
			get(i).setGC(true);
		getPM().getPageManager().checkGCStyle();
		for (int i = size() - 1; i > 0; i--)
			if (get(i).getGC())
				remove(i);
	}

	/**
	 * 查找位置
	 * @param style DStyle
	 * @return int
	 */
	public int indexOf(DStyle style) {
		if (style == null)
			return -1;
		return components.indexOf(style);
	}

	/**
	 * 写对象
	 * @param s DataOutputStream
	 * @throws IOException
	 */
	public void writeObject(DataOutputStream s) throws IOException {
		s.writeShort(-1);

		s.writeInt(size() - 1);
		for (int i = 1; i < size(); i++) {
			DStyle item = get(i);
			if (item == null)
				continue;
			item.writeObject(s);
		}
	}

	/**
	 * 读对象
	 * @param s DataInputStream
	 * @throws IOException
	 */
	public void readObject(DataInputStream s) throws IOException {

		this.doInit();

		//
		int id = s.readShort();
		while (id > 0) {
			id = s.readShort();
		}
		//读取样式
		int count = s.readInt();
		for (int i = 0; i < count; i++) {
			DStyle item = newStyle();
			item.readObject(s);
		}
	}

	/**
	 * 清除
	 */
	public void clear() {
		for (int i = size() - 1; i > 0; i--)
			remove(i);
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < size(); i++)
			sb.append(get(i) + "\n");
		return sb.toString();
	}
}
