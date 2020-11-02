package com.dongyang.wcomponent.ui.border;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import javax.swing.border.Border;



/**
 *
 * Ô²½Ç±ß¿ò
 * @author whaosoft
 *
 */
public class RoundedBorder implements Border {

	protected int m_w = 6;
	protected int m_h = 6;
	protected Color m_topColor = Color.white;
	protected Color m_bottomColor = Color.gray;
	protected boolean roundc = false;


	/**
	 *
	 * @param round_corners
	 */
	public RoundedBorder(boolean round_corners) {
		roundc = round_corners;
	}

	/**
	 *
	 */
	@Override
	public Insets getBorderInsets(Component c) {
		return new Insets(m_h, m_w, m_h, m_w);
	}

	@Override
	public boolean isBorderOpaque() {
		return true;
	}

	/**
	 *
	 */
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int w, int h) {

	  /**
		w = w - 3;
		h = h - 3;
		x++;
		y++;
		if (roundc) {
			g.setColor(m_topColor);
			g.drawLine(x, y + 3, x, y + h - 3);
			g.drawLine(x + 3, y, x + w - 3, y);
			g.drawLine(x, y + 3, x + 3, y);
			g.drawLine(x, y + h - 3, x + 3, y + h);
			g.setColor(m_bottomColor);
			g.drawLine(x + w, y + 3, x + w, y + h - 3);
			g.drawLine(x + 3, y + h, x + w - 3, y + h);
			g.drawLine(x + w - 3, y, x + w, y + 3);
			g.drawLine(x + w, y + h - 3, x + w - 3, y + h);
		} else {
			g.setColor(m_topColor);
			g.drawLine(x, y, x, y + h);
			g.drawLine(x, y, x + w, y);
			g.setColor(m_bottomColor);
			g.drawLine(x + w, y, x + w, y + h);
			g.drawLine(x, y + h, x + w, y + h);
		}
		*/

	    g.setColor(m_bottomColor);
        g.drawRoundRect(x+6, y+6, w-14,h-14,16,16);

	}

}
