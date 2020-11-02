package com.dongyang.wcomponent.ui;

import java.awt.Color;
import javax.swing.JLabel;



/**
 *
 * 床头卡中使用
 * @author whaosoft
 *
 */
public class WTiLabel extends JLabel {

	/**
	 *
	 */
	private static final long serialVersionUID = -3297601829065418335L;
	private boolean requirement;

	public WTiLabel() {
		setFont(new java.awt.Font("新宋体", 0, 14));
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setRequirement(boolean requirement) {
		this.requirement = requirement;
		if (this.requirement)
			setForeground(Color.blue);
		else
			setForeground(new Color(0, 0, 4));
	}

	public boolean getRequirement() {
		return requirement;
	}

	private void jbInit() throws Exception {
		setForeground(new Color(0, 0, 4));
	}
}
