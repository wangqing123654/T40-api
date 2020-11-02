package com.dongyang.wcomponent.ui;

import javax.swing.JPanel;

import com.dongyang.wcomponent.ui.border.RoundedBorder;

/**
 *
 * 配合矩阵使用( 矩阵中只可放入此Panel )
 * @author whaosoft
 *
 */
public class WPanel extends JPanel {

	/**
	 *
	 */
	private static final long serialVersionUID = 1656876924493668750L;

	/** */
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

    //
	public void setRoundedBorder(){

		this.setBorder( new RoundedBorder(true) );
	}

	//

	@Override
	public boolean equals(Object obj) {

		if (null == obj)
			return false;
		if (!(obj instanceof WPanel))
			return false;
		else {
			WPanel po = (WPanel) obj;
			if (null == this.getId() || null == po.getId())
				return false;
			else
				return (this.getId().equals(po.getId()));
		}
	}

	@Override
    public String toString(){

    	return "WPanel,"+this.getId();
    }


}
