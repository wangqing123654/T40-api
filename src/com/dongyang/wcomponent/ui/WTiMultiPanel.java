package com.dongyang.wcomponent.ui;

import java.awt.Dimension;

/**
 *
 * ��ͷ����ʹ��
 * @author whaosoft
 *
 */
public class WTiMultiPanel extends WTiPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = -6908010384666815253L;

	public WTiMultiPanel() {
	}


	/**
	 *
	 * @param width
	 * @param heigt
	 */
	public void setPreferredSize(int width,int height){

		this.setPreferredSize(new Dimension(width, height));
	}

}
