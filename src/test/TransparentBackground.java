package test;

//根据上一篇的思想，修改的如下代码

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.net.URL;
import java.sql.Date;

import javax.swing.*;

public class TransparentBackground extends JComponent implements ComponentListener, WindowFocusListener,Runnable {
     private JFrame frame;
     private static Image background;
     private long lastupdate = 0;
     private long now = 0;
     public boolean refreshRequested = true;
     public TransparentBackground(JFrame frame) {
         this.frame = frame;
         updateBackground( );
         frame.addComponentListener(this);
         frame.addWindowFocusListener(this);
         new Thread(this).start( );
     }
     public void componentShown(ComponentEvent evt) { repaint( ); }
     public void componentResized(ComponentEvent evt) { repaint( ); }
     public void componentMoved(ComponentEvent evt) { repaint( ); }
     public void componentHidden(ComponentEvent evt) { }

     public void windowGainedFocus(WindowEvent evt) { refresh( ); }
     public void windowLostFocus(WindowEvent evt) { refresh( ); }
     public void refresh( ) {
         if(frame.isVisible( )) {
             repaint( );
             refreshRequested = true;
             lastupdate = new Date(lastupdate).getTime( );
         }
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

    public void run( ) {
         try {
             while(true) {
                 Thread.sleep(250);
                 now = new Date(now).getTime( );
                 if(refreshRequested &&((now - lastupdate) > 1000)) {
                     if(frame.isVisible( )) {
                         Point location = frame.getLocation( );
                         frame.hide( );
                         updateBackground( );
                         frame.show( );
                         frame.setLocation(location);
                         refresh( );
                     }
                     lastupdate = now;
                     refreshRequested = false;
                     }
                 }
             } catch (Exception ex) {
                 ex.printStackTrace( );
             }
         }
    public void paintComponent(Graphics g) {
    Point pos = this.getLocationOnScreen();
    Point offset = new Point(-pos.x, -pos.y);
    g.drawImage(background, offset.x, offset.y, null);
    }

    public static void main(String[] args) {

    JFrame frame = new JFrame("Transparent Window");
    Container con = frame.getContentPane();

        TransparentBackground bg = new TransparentBackground(frame);

        //可以再bg上添加其他组件，但要让他们透明。

        frame.getContentPane( ).add("Center",bg);
        frame.pack( );
        frame.setSize(200,200);
        frame.setLocation(200,300);
        frame.setVisible(true);
   }


}
