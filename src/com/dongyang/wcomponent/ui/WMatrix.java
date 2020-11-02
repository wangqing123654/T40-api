package com.dongyang.wcomponent.ui;




import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JList;

import com.dongyang.wcomponent.ui.model.DefaultWMatrixModel;
import com.dongyang.wcomponent.ui.renderer.WMatrixRenderer;


/**
 *
 * 矩阵( 一行多列的List )
 * @author whaosoft
 *
 */
public class WMatrix extends JList {


	/**
	 *
	 */
	private static final long serialVersionUID = -3236008317503880348L;


	/**
	 *
	 */
	public WMatrix() {

		super();

		this.init();
	}


	/**
	 *
	 * @param dataModel
	 */
	public WMatrix(DefaultWMatrixModel dataModel) {

		super(dataModel);

        this.init();
	}

	/**
	 *
	 */
	private void init(){

		this.setBackground( new Color(238,238,238));

		//this.setDragEnabled(true);

		this.setCellRenderer(new WMatrixRenderer());

		//this.setVisibleRowCount(2);

		this.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	}

	/**
	 *
	 */
    public void doProcessRowCount(int columnNum){

    	int size = this.getModel().getSize();

    	int ii= size%columnNum;
    	int jj= size/columnNum;

    	if( ii>0 ){
    		this.setVisibleRowCount(jj+1);
    	}else{
    		this.setVisibleRowCount(jj);
    	}

    }

    /** */
    private Object doubleClick;
	public Object getDoubleClick() {
		return doubleClick;
	}
	public void setDoubleClick(Object doubleClick) {
		this.doubleClick = doubleClick;
		this.updateUI();
	}

	/**
	 * 开启双击事件
	 */
    public void setDoubleClickListener(){

    	/**
    	 *
    	 */
    	this.addMouseListener(new MouseAdapter() {

    		/**
    		 *
    		 */
			public void mouseClicked(MouseEvent e) {

				WMatrix m = (WMatrix) e.getSource();

				if (m.getSelectedIndex() != -1) {

					if (e.getClickCount() == 2)

						twoClick(m,m.getSelectedValue());
				}
			}

			/**
			 *
			 * @param m
			 * @param value
			 */
			private void twoClick(WMatrix m,Object value) {

				m.setDoubleClick(value);
			}
    	});
    }

}
