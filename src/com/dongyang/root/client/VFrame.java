package com.dongyang.root.client;

import java.awt.Frame;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import com.dongyang.util.ImageTool;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.root.util.VideoData;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;


public class VFrame extends Frame
        implements WindowListener,MouseListener,MouseMotionListener,KeyListener
{
    private String id;
    private static Map fromList = new HashMap();
    private Image image;
    private SocketLink link;
    public VFrame()
    {
        setSize(new Dimension(310, 268));
        addWindowListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setVisible(true);
    }
    public void setLink(SocketLink link)
    {
        this.link = link;
    }
    public void setID(String id)
    {
        this.id = id;
    }
    public String getID()
    {
        return id;
    }
    public static VFrame getFrame(String id)
    {
        VFrame from =(VFrame)fromList.get(id);
        if(from == null)
        {
            from = new VFrame();
            from.setID(id);
            from.setTitle(id + " - [×ÀÃæ]");
            fromList.put(id,from);
        }
        return from;
    }
    public void paint(Graphics g)
    {
        if(image != null)
            g.drawImage(image, 0, 30,this);
    }
    public void update(Graphics g) {
        paint(g);
    }
    public void setData(byte[] data)
    {
        image= ImageTool.getImage(data);
        repaint();
    }
    public void setVideoData(VideoData data)
    {
        image = ImageTool.getImage(data);
    }
    public void windowOpened(WindowEvent windowevent)
    {
    }

    public void windowClosing(WindowEvent event)
    {
      dispose();
      fromList.remove(id);
    }

    public void windowClosed(WindowEvent event)
    {
    }

    public void windowIconified(WindowEvent windowevent)
    {
    }

    public void windowDeiconified(WindowEvent windowevent)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
    }
    public void mouseClicked(MouseEvent e)
    {
    }
    public void mouseEntered(MouseEvent e)
    {
    }
    public void mouseExited(MouseEvent e)
    {
    }
    public void mouseMoved(MouseEvent e)
    {
        if(link == null || link.isClose())
            return;
        link.sendCommand(id,link.MOUSE_MODE,new int[]{e.getX(),e.getY()-30});
    }
    public void mouseDragged(MouseEvent e)
    {
        if(link == null || link.isClose())
            return;
        link.sendCommand(id,link.MOUSE_DRAGGED,new int[]{e.getX(),e.getY()-30});
    }
    public void mousePressed(MouseEvent e)
    {
        if(link == null || link.isClose())
            return;
        link.sendCommand(id,link.MOUSE_PRESSED,e.getButton());
    }
    public void mouseReleased(MouseEvent e)
    {
        if(link == null || link.isClose())
            return;
        link.sendCommand(id,link.MOUSE_RELEASED,e.getButton());
    }
    public void keyPressed(KeyEvent e)
    {
        if(link == null || link.isClose())
            return;
        link.sendCommand(id,link.KEY_PRESSED,e.getKeyCode());
    }
    public void keyReleased(KeyEvent e)
    {
        if(link == null || link.isClose())
            return;
        link.sendCommand(id,link.KEY_RELEASED,e.getKeyCode());
    }
    public void keyTyped(KeyEvent e)
    {
    }
    public static void main(String args[])
    {
        getFrame("0000");
    }
}
