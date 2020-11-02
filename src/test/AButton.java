package test;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JFrame;
import com.sun.java.swing.plaf.windows.WindowsButtonUI;
import javax.swing.Icon;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import com.dongyang.ui.util.TDrawTool;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Point;

public class AButton extends JComponent
{
    String text;
    public AButton(String s)
    {
        text = s;
        Image2 image = new Image2();
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        Cursor c = toolkit.createCustomCursor(image,new Point(0,0),"");
        this.setCursor(c);
        ImageIcon image1 = new ImageIcon("");
    }
    public void paint(Graphics g)
    {
        g.setColor(new Color(255,255,255));
        g.fillRect(0,0,getWidth(),getHeight());
        g.setColor(new Color(0,0,0));
        //g.drawLine(10,10,30,30);
        /*g.drawRect(30,30,10,10);
        g.fillRect(40,40,10,10);
        g.drawString(text,30,30);*/
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        //Image image=toolkit.getImage("close.gif");
        //System.out.println(image.getSource().getClass().getName());
        //System.out.println(image.getClass().getName());
        //sun.awt.image.ToolkitImage a;
        //javax.swing.ImageIcon a1;
        Image2 image = new Image2();
        //System.out.println(g.getClass().getName());
        //sun.java2d.SunGraphics2D a;


        g.drawImage(image,10,10,this);
    }
    class Image2 extends BufferedImage
    {
        public Image2()
        {
            super(100,100,TYPE_INT_ARGB);
            Graphics2D g2 = createGraphics();
            g2.setColor(new Color(255,0,0));
            g2.drawLine(10,10,50,50);
            g2.drawLine(50,10,10,50);
        }
    }
    public static void main(String args[])
    {
        JFrame f = new JFrame("≤‚ ‘");
        f.setLayout(null);
        AButton b = new AButton("≤‚ ‘◊ÈΩ®");
        b.setBounds(30,30,100,100);
        f.add(b);
        f.setSize(300,300);
        f.setVisible(true);
    }
}
