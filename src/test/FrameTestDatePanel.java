package test;

import java.awt.*;

import javax.swing.*;
import com.dongyang.ui.base.TDateListBase;
import java.awt.BorderLayout;
import java.awt.Rectangle;
import javax.swing.BorderFactory;
import com.dongyang.ui.base.TDateEditBase;
import com.dongyang.ui.base.TTextFormatBase;
import com.dongyang.ui.TButton;
import com.dongyang.ui.base.TCBase;
import com.dongyang.tui.DLabel;
import java.util.List;
import com.dongyang.tui.DComponent;
import com.dongyang.tui.DRectangle;
import java.util.ArrayList;

public class FrameTestDatePanel extends JFrame
{
    TCBase tDateListBase1 = new TCBase();
    TButton b = new TButton();
    JScrollBar jScrollBar1 = new JScrollBar();

    public FrameTestDatePanel()
    {
        try
        {
            jbInit();
            this.setSize(800,800);
        } catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    private void jbInit() throws Exception
    {
        getContentPane().setLayout(null);
        tDateListBase1.setBorder(BorderFactory.createLoweredBevelBorder());
        tDateListBase1.setBounds(new Rectangle(117, 40, 800, 800));
        tDateListBase1.onInit();
        b.setBounds(10,10,30,22);
        jScrollBar1.setMaximum(30);
        jScrollBar1.setMinimum(10);
        jScrollBar1.setOrientation(JScrollBar.HORIZONTAL);
        jScrollBar1.setValue(20);
        jScrollBar1.setVisibleAmount(5);
        this.getContentPane().add(tDateListBase1);
        this.getContentPane().add(jScrollBar1);
        jScrollBar1.setBounds(new Rectangle(13, 125, 346, 29));
    }
    public static void main(String args[])
    {
        try {
            UIManager.setLookAndFeel(
                "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e) {
        }
        new FrameTestDatePanel().setVisible(true);

    }
}
