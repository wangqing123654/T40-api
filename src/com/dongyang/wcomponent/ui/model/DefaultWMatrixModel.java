package com.dongyang.wcomponent.ui.model;

import javax.swing.DefaultListModel;

import com.dongyang.wcomponent.ui.WPanel;


/**
 *
 * @author whaosoft
 *
 */
public class DefaultWMatrixModel extends DefaultListModel{


	/**
	 *
	 */
	private static final long serialVersionUID = 381023097573641643L;


	/**
	 *
	 * @param wp
	 */
	public void addElement(WPanel wp) {

		super.addElement(wp);
	}

    /**
     *
     */
	@Override
	public void addElement(Object obj) {

         if ( obj instanceof WPanel) {

             throw new RuntimeException(" ÷ªø…ÃÌº”[ WPanel ] . ");
		}else {

             super.addElement(obj);
		}
	}



}
