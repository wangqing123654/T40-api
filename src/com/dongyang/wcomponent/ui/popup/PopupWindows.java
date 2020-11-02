package com.dongyang.wcomponent.ui.popup;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.border.Border;


import com.dongyang.wcomponent.ui.WMatrix;
import com.dongyang.wcomponent.ui.WPanel;
import com.dongyang.wcomponent.ui.model.DefaultWMatrixModel;
import com.sun.awt.AWTUtilities;


/**
 *
 * @author whaosoft
 *
 */
public class PopupWindows {

	//
	private JDialog frame;
	private int width = 320;
	private int height = 240;

	private int xCoor = 290;// 窗口左上角的x坐标
	private int yCoor = 130;// 窗口弹出后最终的y坐标
	private int xCoor0 = 180;
	private float value = 1.0f;
	private Timer ti0;


	/**
	 *
	 * @param jc
	 */
	public PopupWindows(JComponent jc) {

		this.initWindow();
        this.addComponent(jc);
	}

    /**
     *
     * @param width
     * @param height
     * @param xMax
     * @param xMin
     * @param y
     * @param jc
     */
	public PopupWindows(int width, int height, int xMax, int xMin, int y,JComponent jc) {

		this.width = width;
		this.height = height;
		this.xCoor0 = xMin;
		this.yCoor = y;
		this.xCoor = xMax;

		//
		this.initWindow();
        this.addComponent(jc);
	}

	/**
	 *
	 */
	private void initWindow(){

		frame = new JDialog();
		//frame.setUndecorated(true);
		frame.setSize(width, height);
		frame.setLocation(xCoor0, yCoor);// 窗口的初始位置
		frame.setVisible(true);
		frame.setAlwaysOnTop(true);

		//
		ti0 = new Timer(15, new Tim_00(frame));
		ti0.start();
	}

	/**  */
	public JButton jb =	new PopButton(this);

	/**
	 *
	 */
	private void addComponent(JComponent jc){

		//int height_differential = 46;

		Border loweredbevel = BorderFactory.createLoweredBevelBorder();
		jc.setBorder(loweredbevel);
		//jc.setSize(width-18, height-22-height_differential);
		jc.setBackground(frame.getBackground());
		jc.setFont(new Font("微软雅黑", Font.PLAIN, 16));

		JScrollPane js = new JScrollPane(jc);
		//js.setBounds(0, 0, width-16, height-18-height_differential);

		frame.getContentPane().add(js, BorderLayout.CENTER);
		frame.getContentPane().add(jb, BorderLayout.SOUTH);

		/**
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = width;
		c.ipady = height;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(js, c);
        //
		c.weighty = 2.0;
		c.ipady = 0;
		c.anchor = GridBagConstraints.PAGE_END;
	    c.gridx = 0;
	    c.gridwidth = 0;
	    c.gridy = 0;
		frame.add(jb, c);
		*/

		//
		AWTUtilities.setWindowOpacity(frame, 0.8f);
	}

	/**
	 *
	 * @author whaosoft
	 *
	 */
	class Tim_00 implements ActionListener {

		private JDialog frame;
		public Tim_00( JDialog frame ) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {
			if (xCoor0 < xCoor) {
				xCoor0 += 4;
				frame.setLocation(xCoor0, yCoor);
			} else {
				frame.setLocation(xCoor, yCoor);
				ti0.stop();
			}
		}
	}

	/**
	 *
	 * @author whaosoft
	 *
	 */
	class Tim_01 implements ActionListener {

		private JDialog frame;
		public Tim_01( JDialog frame ) {
			this.frame = frame;
		}

		public void actionPerformed(ActionEvent e) {
			value -= 0.02f;
			if (value >= 0.02f) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						com.sun.awt.AWTUtilities.setWindowOpacity(frame, value);
					}
				});
			} else {
				if( null!=frame ){
					frame.setVisible(false);
					frame = null;
				}
			}
		}
	}

	public JDialog getFrame() {
		return frame;
	}

	public void setFrame(JDialog frame) {
		this.frame = frame;
	}


	/** */
	private static PopupWindows pop = null;

	/**
	 *
	 */
	static public PopupWindows showPopup(JComponent jc) {

		if (null == pop) {
			pop = new PopupWindows(jc);
			return pop;
		} else {
			if (!pop.getFrame().isVisible()) {
				pop = null;
				pop = new PopupWindows(jc);
				return pop;
			}
			return null;
		}
	}

	/**
	 *
	 */
	static public PopupWindows showPopup(int width, int height, int xMax,
			int xMin, int y, JComponent jc) {

		if (null == pop) {
			pop = new PopupWindows(width, height, xMax, xMin, y, jc);
			return pop;
		} else {
			if (!pop.getFrame().isVisible()) {
				pop = null;
				pop = new PopupWindows(width, height, xMax, xMin, y, jc);
				return pop;
			}
			return null;
		}
	}

	/**
	 *
	 * @author whaosoft
	 *
	 */
	public class PopButton extends JButton{

		/**
		 *
		 */
		private static final long serialVersionUID = -5920115150536639182L;

		/** */
		private PopupWindows pop;

		/**
		 *
		 * @param pop
		 */
		public PopButton(PopupWindows pop) {

			super();

			this.pop = pop;
		}

		/**
		 *
		 */
		public void closePop(){

			this.pop.getFrame().setVisible(false);
		}

		/**
		 *
		 * @return
		 */
		public JComponent getPopupComponent(){

			Container c = this.pop.getFrame().getContentPane();
			JScrollPane jsp = (JScrollPane) c.getComponent(0);
			JViewport jv =  (JViewport) jsp.getComponent(0);

			return (JComponent) jv.getComponent(0);
		}
	}

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args) {

        //
		String str ="mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+

		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+

		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"+
		"mol  106  U ℃μ mmHg  kPa  ⅠIIIIIIVV ± ℉ ‰ ～ α β γ ㏄ ㎡ ㎎ ㎏ ㎝ ㏑ ㏒"

		;

		//
		JTextArea ta = new JTextArea();
		ta.setText(str);
		ta.setEditable(false);
		ta.setLineWrap(true);

		//new PopupWindows(ta);

/*		PopupWindows pop = PopupWindows.showPopup(ta);
		pop.jb.setText("PB");
		pop.jb.addMouseListener( new MouseAdapter(){
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	   System.out.println("~~~~~~~~~~~");

	        	   ((PopButton)e.getComponent()).getPopupComponent();

	        	   ((PopButton)e.getComponent()).closePop();
	        }
		});*/

		DefaultWMatrixModel model = new DefaultWMatrixModel();

		for( int i=0;i<16;i++){
			WPanel jp = new WPanel();
			JLabel jl = new JLabel("["+i+"]");
			jp.add(jl, null);
			jp.setId("jp_" + i);
			model.addElement(jp);
			jp.setRoundedBorder();
		}


		final WMatrix jList = new WMatrix(model);
		jList.doProcessRowCount(6);



			jList.addMouseListener(new MouseAdapter() {

				//点击
				public void mouseClicked(MouseEvent e) {


					jList.setDoubleClick( jList.getSelectedValue());
					jList.setSelectedIndex(-1);

				}});




		PopupWindows pop = PopupWindows.showPopup(jList);
		pop.jb.setText("PB");
		pop.jb.addMouseListener( new MouseAdapter(){
	        @Override
	        public void mouseClicked(MouseEvent e) {
	        	   System.out.println("~~~~~~~~~~~");

	        	   ((PopButton)e.getComponent()).getPopupComponent();

	        	   ((PopButton)e.getComponent()).closePop();
	        }
		});
	}

}


