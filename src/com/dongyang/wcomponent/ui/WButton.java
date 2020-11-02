package com.dongyang.wcomponent.ui;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

/**
  import java.awt.FlowLayout;
  import javax.swing.JFrame;
  import javax.swing.UIManager;
 */

/**
 *
 * @author whaosoft
 *
 */
public class WButton extends JButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -3528538077393488391L;

	//
	public WButton(String label) {

		super(label);

		this.doInit(label);
	}

	//
	public WButton(String label,boolean isRound) {

		super(label);

		this.isRound = isRound;

		this.doInit(label);

	}

	//
	public WButton(String label,boolean isRound,boolean isFull) {

		super(label);

		this.setMargin(isFull);

		this.isRound = isRound;

		this.doInit(label);

	}

	/**
	 *
	 * @param isFull
	 */
	private void setMargin(boolean isFull){

		if(isFull){
			Insets i = this.getMargin();
			i.set(i.top, 0, i.bottom, 0);
			this.setMargin(i);
		}
	}

	/**
	 *
	 * @param label
	 */
	private void doInit(String label){

		Dimension size = getPreferredSize();

		size.width = size.height = Math.max(size.width, size.height);
		setPreferredSize(size);

		setContentAreaFilled(false);
		this.setFocusPainted(false);
	}

	//
	private boolean isRound = false;

	@Override
	protected void paintComponent(Graphics g) {
		if (getModel().isArmed()) {
			g.setColor(Color.lightGray);
		} else {
			g.setColor(getBackground());
		}

		if( this.isRound ){
			g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 5, 5);
		}else{
			g.fillRect(0, 0, getSize().width - 1, getSize().height - 1);
		}


		super.paintComponent(g);
	}

	@Override
	protected void paintBorder(Graphics g) {
		g.setColor(getForeground());
		if( this.isRound ){
			g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 5, 5);
		}else{
			g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
		}

	}

	@Override
	public boolean contains(int x, int y) {

		Shape shape = null;
		if (shape == null || !shape.getBounds().equals(getBounds())) {
			shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
		}
		return shape.contains(x, y);
	}

	@Override
	public void setSize(Dimension d) {

		this.setPreferredSize(d);
	}

/**

   	// 测试程序
	public static void main(String[] args) {
		// /
		try {
			UIManager
					.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {

			e.printStackTrace();
		}

		// 产生一个带‘Jackpot’标签的按钮。
		JButton button = new WButton("你好你好",false,true);
		button.setBackground(Color.green);

		// button.setPreferredSize(new Dimension(60,36));
		button.setSize(new Dimension(60, 24));

		// 产生一个框架以显示这个按钮。
		JFrame frame = new JFrame();
		// frame.getContentPane().setBackground(Color.yellow);
		frame.getContentPane().add(button);
		frame.getContentPane().setLayout(new FlowLayout());
		frame.setSize(150, 150);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 关闭窗口时退出程序

		frame.setLocationRelativeTo(null);
	}
*/
}
