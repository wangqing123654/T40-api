package com.dongyang.wcomponent.util;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;

/**
 *
 * @author whaosoft
 *
 */
public class DropTargetUtil {

	/**
	 *
	 * @param component
	 * @return
	 */
	static public DropTarget doDropTarget(Component component, IDropTarget idt) {

		return doProcessDropTarget(null, component, idt);
	}

	/**
	 *
	 * @param fromC
	 * @param toC
	 * @param idt
	 * @return
	 */
	static public DropTarget doDropTarget(Component fromC, Component toC,IDropTarget idt) {

		return doProcessDropTarget(fromC, toC, idt);
	}

	/**
	 *
	 * @param fromC
	 * @param toC
	 * @param idt
	 * @return
	 */
	static private DropTarget doProcessDropTarget(final Component fromC,final Component toC, final IDropTarget idt) {

		return new DropTarget(toC, DnDConstants.ACTION_COPY_OR_MOVE,
				new DropTargetAdapter() {
					@Override
					public void drop(DropTargetDropEvent dtde) {
						try {

							if (dtde.isDataFlavorSupported(DataFlavor.stringFlavor)) {
								dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
								String res = (String) (dtde.getTransferable()
										.getTransferData(DataFlavor.stringFlavor));

								idt.doDrop(res, dtde);

								dtde.dropComplete(true);// 指示拖拽操作已完成
							} else {
								dtde.rejectDrop();// 否则拒绝拖拽来的数据
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}

}
