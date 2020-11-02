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
		// ����
		if( isSelected  ){
			jp.setBackground( new Color(135,206,235) ); //����ɫ
		}else{
			jp.setBackground(null);
		}
		*/

		//˫��
        WMatrix wl = (WMatrix) list;
		if( null!= wl.getDoubleClick() && wl.getDoubleClick().equals(jp) ){
			jp.setBackground(Color.CYAN); //��ɫ
		}else{
			//if( !isSelected ){
				jp.setBackground(null);
			//}
		}

		return jp;
	}

}
