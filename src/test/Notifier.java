package test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Notifier extends JFrame
{
    public JList list;
    public DefaultListModel listModel;
    public JScrollPane jsp;
    public GridBagConstraints gbc;
    public GridBagLayout gbl;
    public Container c;
    public MyWindow myWindow;
    public JFrame frame;
    public Notifier()
    {
        listModel = new DefaultListModel();
        list = new JList(listModel);
        GraphicsEnvironment ge = GraphicsEnvironment.
                                 getLocalGraphicsEnvironment();
        GraphicsDevice[] gs = ge.getScreenDevices();
        //for (int j = 0; j < gs.length; j++)
        {
            //System.out.println("j=" + j);
            //GraphicsDevice gd = gs[j];
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            System.out.println(gd.getClass().getName());
            //if (gd.isFullScreenSupported())
            {
                //if (gd.getType() == gd.TYPE_RASTER_SCREEN)
                {
                    frame = new JFrame();
                    frame.setUndecorated(true);
                    frame.setDefaultLookAndFeelDecorated(false);
                    frame.setVisible(false);

                    gd.setFullScreenWindow(frame);
                    //break;
                }
            }
        }
        //if (frame != null)
        //    frame.setVisible(false);
    }

    public void makeWindow()
    {
        if (frame == null)
            return;
        try
        {
            list.removeMouseListener(myWindow);
            list.removeMouseMotionListener(myWindow);
        } catch (Exception e)
        {}
        myWindow = new MyWindow(frame);
        frame.setVisible(false);
        myWindow.setup();
    }

    public static void main(String[] args)
    {
        Notifier nf = new Notifier();
        nf.makeWindow();

    }

    public class MyWindow extends JWindow implements MouseListener,
            MouseMotionListener
    {
        public JButton closeButton;
        public Point offset;
        public MyWindow(JFrame frame)
        {
            super(frame);
        }

        public void setup()
        {
            setVisible(true);
            setLocation(300, 100);
            setSize(300, 300);
            /*gbc = new GridBagConstraints();
            gbl = new GridBagLayout();
            c = this.getContentPane();
            c.setLayout(new BorderLayout());
            this.setVisible(true);
            JPanel p = new JPanel();
            p.setLayout(new BorderLayout());
            p.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 2));
            p.addMouseListener(this);
            p.addMouseMotionListener(this);
            list.addMouseListener(this);
            list.addMouseMotionListener(this);
            jsp = new JScrollPane(list);
            p.add(jsp, BorderLayout.CENTER);
            c.add(p, BorderLayout.CENTER);
            p.validate();
            c.validate();
            repaint();*/
        }

        public void mouseClicked(java.awt.event.MouseEvent mouseEvent)
        {}

        public void mouseEntered(java.awt.event.MouseEvent mouseEvent)
        {}

        public void mouseExited(java.awt.event.MouseEvent mouseEvent)
        {}

        public void mousePressed(java.awt.event.MouseEvent mouseEvent)
        {
            if (SwingUtilities.isLeftMouseButton(mouseEvent))
            {
                int count = mouseEvent.getClickCount();
                if (count == 1)
                {
                    offset = new Point(mouseEvent.getX(), mouseEvent.getY());
                } else if (count == 2)
                {
                    int i = list.locationToIndex(new Point(mouseEvent.getX(),
                            mouseEvent.getY()));
                    if (i >= 0 && i < listModel.size())
                    {
                        String s = (String) listModel.elementAt(i);
                    } else
                        return;
                    if (c != null)
                        ;
                }
            } else if (SwingUtilities.isRightMouseButton(mouseEvent))
            {
                Vector v = new Vector();
                v.add("Close");
                ClickMenu menu = new ClickMenu(v, mouseEvent);
                menu.show(mouseEvent.getComponent(), mouseEvent.getX(),
                          mouseEvent.getY());
            }
        }

        public void mouseReleased(java.awt.event.MouseEvent mouseEvent)
        {
            offset = null;
        }

        public void mouseDragged(java.awt.event.MouseEvent mouseEvent)
        {
            if (SwingUtilities.isLeftMouseButton(mouseEvent))
            {
                myWindow.setLocation((int) (myWindow.getX() + mouseEvent.getX() -
                                            offset.getX()),
                                     (int) (myWindow.getY() + mouseEvent.getY() -
                                            offset.getY()));
                c.repaint();
            }
        }

        public void mouseMoved(java.awt.event.MouseEvent mouseEvent)
        {}
    }


    public void addToList(String s)
    {
        if (listModel.contains(s))
            return;
        listModel.add(0, s);
        list.revalidate();
        list.repaint();
    }

    public void removeFromList(String s)
    {
        listModel.removeElement(s);
        list.revalidate();
        list.repaint();
    }

    public String getFirst()
    {
        if (listModel.size() > 0)
            ;
        return (String) listModel.get(0);
    }

    public class ClickMenu extends JPopupMenu implements ActionListener
    {
        MouseEvent e;
        public ClickMenu(Vector menu, MouseEvent e)
        {
            this.e = e;
            JMenuItem tmpItem;
            for (int x = 0; x < menu.size(); x++)
            {
                add(tmpItem = new JMenuItem((String) menu.elementAt(x)));
                tmpItem.addActionListener(this);
            }
        }

        public void actionPerformed(java.awt.event.ActionEvent actionEvent)
        {
            String command = actionEvent.getActionCommand();
            if (command.equals("Close"))
            {
                myWindow.setVisible(false);
                System.exit(0);
            }

        }
    }
}
