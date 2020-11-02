package com.dongyang.tui.text;

import com.dongyang.util.TList;
import java.awt.Font;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;

/**
 *
 * <p>Title: ��������</p>
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
	 * ��Ա�б�
	 */
	private TList components;

	/**
	 * ������
	 */
	private PM pm;

	/** */
	private boolean isReport = false;

	/**
	 * ������
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
			//����Ĭ�Ϸ��
			initDefaultStyle();
		}

		 //System.out.println( this );
	}

	/**
	 * ����Ĭ�Ϸ��
	 */
	public void initDefaultStyle() {
		//����Ǳ�����
		//����
		DStyle style = newStyle();
		style.setName("default");
		style.setColor(new Color(0, 0, 0));
		style.setFont(new Font("����", 0, 11));

	}

	/**
	 * ���ñ�����
	 */
	public void initReportStyle() {
		DStyle style = newStyle();
		style.setName("default");
		style.setColor(new Color(0, 0, 0));
		style.setFont(new Font("����", 0, 10));

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
	 * ���ӳ�Ա
	 * @param s DStyle
	 */
	public void add(DStyle s) {
		if (s == null)
			return;
		components.add(s);
	}

	/**
	 * ɾ����Ա
	 * @param s DStyle
	 */
	public void remove(DStyle s) {
		components.remove(s);
	}

	/**
	 * ��Ա����
	 * @return int
	 */
	public int size() {
		return components.size();
	}

	/**
	 * �õ���Ա
	 * @param index int
	 * @return DStyle
	 */
	public DStyle get(int index) {
		return (DStyle) components.get(index);
	}

	/**
	 * ɾ����Ա
	 * @param index int
	 */
	public void remove(int index) {
		components.remove(index);
	}

	/**
	 * �õ���ʽ
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
	 * �õ���Ա�б�
	 * @return TList
	 */
	public TList getComponentList() {
		return components;
	}

	/**
	 * ���ø���
	 * @param parent EComponent
	 */
	public void setParent(EComponent parent) {
	}

	/**
	 * �õ�����
	 * @return EComponent
	 */
	public EComponent getParent() {
		return null;
	}

	/**
	 * ����ʽ
	 * @return DStyle
	 */
	public DStyle newStyle() {
		DStyle style = new DStyle(this);
		add(style);
		return style;
	}

	/**
	 * �õ�����ʽ
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
	 * ����������ʽ
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
	 * ����λ��
	 * @param style DStyle
	 * @return int
	 */
	public int indexOf(DStyle style) {
		if (style == null)
			return -1;
		return components.indexOf(style);
	}

	/**
	 * д����
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
	 * ������
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
		//��ȡ��ʽ
		int count = s.readInt();
		for (int i = 0; i < count; i++) {
			DStyle item = newStyle();
			item.readObject(s);
		}
	}

	/**
	 * ���
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
