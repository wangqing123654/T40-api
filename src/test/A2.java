package test;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.Image;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ComponentListener;
import java.awt.event.WindowFocusListener;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;


public class A2 extends JComponent implements ComponentListener, WindowFocusListener,Runnable
        // implements Runnable
{
    private JFrame frame;
    private Image background;

    public A2(JFrame frame) {
        this.frame = frame;
        new Thread(this).start();
        //frame.addComponentListener(this);
        //frame.addWindowFocusListener(this);

        updateBackground( );
    }

    public void updateBackground( ) {
        try {
            Robot rbt = new Robot( );
            Toolkit tk = Toolkit.getDefaultToolkit( );
            Dimension dim = tk.getScreenSize( );
            background = rbt.createScreenCapture(
            new Rectangle(0,0,(int)dim.getWidth( ),
                              (int)dim.getHeight( )));
        } catch (Exception ex) {
            ex.printStackTrace( );
        }
    }

    public void paintComponent(Graphics g) {
        Point pos = this.getLocationOnScreen( );
        Point offset = new Point(-pos.x,-pos.y);
        g.drawImage(background,offset.x,offset.y,null);
    }
    public void a()
    {
        //frame.hide();
        Point p = frame.getLocation();
        frame.setLocation(-frame.getWidth(),-frame.getHeight());
        updateBackground( );
        frame.setLocation(p);
        //frame.show();
    }
    public void componentShown(ComponentEvent evt) {a( ); }
    public void componentResized(ComponentEvent evt) { a( ); }
    public void componentMoved(ComponentEvent evt) { a( ); }
    public void componentHidden(ComponentEvent evt) { }

    public void windowGainedFocus(WindowEvent evt) { a( ); }
    public void windowLostFocus(WindowEvent evt) { a( ); }

    public void run( ) {
        try
        {
            while (true)
            {
                Thread.sleep(1250);
                Point p = frame.getLocation();
                frame.setLocation(-frame.getWidth(),-frame.getHeight());
                updateBackground( );
                frame.setLocation(p);
                repaint( );
            }
        } catch (Exception ex) {
            ex.printStackTrace( );
        }

    }
}

