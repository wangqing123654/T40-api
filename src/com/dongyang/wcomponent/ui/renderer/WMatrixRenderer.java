package com.dongyang.wcomponent.ui.renderer;


import java.awt.Color;
import java.awt.Component;

import javax.swing.JList;
import javax.swing.ListCellRenderer;

import com.dongyang.wcomponent.ui.WMatrix;
import com.dongyang.wcomponent.ui.WPanel;



/**
 *
 * @author whaosoft
 *
 */
public class WMatrixRenderer implements ListCellRenderer {


	/**
	 *
	 */
	private static final long serialVersionUID = -7229339530579882821L;

    /**
     *
     */
	public WMatrixRenderer() {

	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		WPanel jp = (WPanel) value;

		/**
		// 单击
		if( isSelected  ){
			jp.setBackground( new Color(135,206,235) ); //天蓝色
		}else{
			jp.setBackground(null);
		}
		*/

		//双击
        WMatrix wl = (WMatrix) list;
		if( null!= wl.getDoubleClick() && wl.getDoubleClick().equals(jp) ){
			jp.setBackground(Color.CYAN); //青色
		}else{
			//if( !isSelected ){
				jp.setBackground(null);
			//}
		}

		return jp;
	}

}
