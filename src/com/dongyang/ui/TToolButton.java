package com.dongyang.ui;

import com.dongyang.util.StringTool;
import javax.swing.ImageIcon;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.FileTool;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class TToolButton extends TButton{
    /**
     * ͼƬ����
     */
    private String pictureName;
    /**
     * ͼƬ����
     */
    private ImageIcon pic;
    private static JLabel drawLabel;
    /**
     * ������
     */
    private boolean toRight;
    /**
     * ������
     */
    public TToolButton()
    {
    }
    /**
     * ���ð�ťͼƬ
     * @param name ͼƬ����
     */
    public void setPictureName(String name)
    {
      pictureName = name;
      if(name != null)
          pic = createImageIcon(name);
      this.setIcon(pic);
    }
    /**
     * �õ���ťͼƬ
     * @return ͼƬ����
     */
    public String getPictureName()
    {
      return pictureName;
    }
    /**
     * ������Ч (����ͬ������������)
     * @param b boolean
     */
    public void setEnabled(boolean b)
    {
        if(isEnabled() == b)
            return;
        super.setEnabled(b);
        TComponent component = getMenuItem(getTag());
        if(component == null)
            return;
        if(b == (Boolean)component.callMessage("isEnabled"))
            return;
        component.callMessage("setEnabled|" + b);
    }
    /**
     * ������ʾ (����ͬ������������)
     * @param b boolean
     */
    public void setVisible(boolean b)
    {
        if(isVisible() == b)
            return;
        super.setVisible(b);
        TComponent component = getMenuItem(getTag());
        if(component == null)
            return;
        if(b == (Boolean)component.callMessage("isVisible"))
            return;
        component.callMessage("setVisible|" + b);
    }
    /**
     * �õ��˵�����
     * @param tag String
     * @return TComponent
     */
    public TComponent getMenuItem(String tag)
    {
        TMenuBar menuBar = getMenuBar();
        if(menuBar == null)
            return null;
        TComponent component = menuBar.findObject(tag);
        if(component == null)
            return null;
        return component;
    }
    /**
     * �õ��˵���
     * @return TMenuBar
     */
    public TMenuBar getMenuBar()
    {
        if(getParentComponent() == null)
            return null;
        if(!(getParentComponent() instanceof TMenuBar))
            return null;
        return (TMenuBar)getParentComponent();
    }
    /**
     * ����ͼƬ
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
    	//$$ modified by lx 2014/02/11   �޸Ĺ�����ͼ����ʽ
    	ImageIcon icon =null;    	
        if(TIOM_AppServer.SOCKET != null){
        	//System.out.println("--------filename----------"+filename);
        	icon=TIOM_AppServer.getImage("%ROOT%\\image\\ImageIcon\\" + filename);
        	icon.setImage(icon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
            return icon;
            
        }
        icon = FileTool.getImage("image/ImageIcon/" + filename);
        icon.setImage(icon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        
        if(icon != null)
            return icon;
        String path = "/image/ImageIcon/" + filename;
        try{
            icon = new ImageIcon(getClass().getResource(path));
            icon.setImage(icon.getImage().getScaledInstance(20,20,Image.SCALE_DEFAULT));
        }catch(NullPointerException e)
        {
            err("û���ҵ�ͼ��" + path);
        }
        
        return icon;
    }
    /**
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message)
    {
        if(!super.filterInit(message))
            return false;
        String value[] = StringTool.getHead(message,"=");
        /*if("text".equalsIgnoreCase(value[0]))
            return false;*/
        if("Key".equalsIgnoreCase(value[0])||
           "globalkey".equalsIgnoreCase(value[0]))
            return false;
        return true;
    }
    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
        super.baseFieldNameChange(value);
        if ("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
    }
    /**
     * ��ͼ
     * @param g ͼ�ζ���
     */
    public void paint(Graphics g)
    {
        int w = getWidth();
        int h = getHeight();

        Color c1 = TUIStyle.getToolButtonBackColor1();
        Color c2 = TUIStyle.getToolButtonBackColor2();
        fillTransition(0, 0, w, h, c1, c2, g);

        if(getModel().isRollover()||isSelected())
        {
            g.setColor(new Color(0, 0, 128));
            g.drawRect(0,0,w - 1,h - 2);
            fillTransition(1, 1, w - 2, h - 3, new Color(255,244,204), new Color(255,208,145), g);
        }
        if(getModel().isPressed())
        {
            g.setColor(new Color(0, 0, 128));
            g.drawRect(0,0,w - 1,h - 2);
            fillTransition(1, 1, w - 2, h - 3, new Color(254,145,78), new Color(255,211,142), g);
        }
        if(drawLabel == null)
            drawLabel = new JLabel();
        drawLabel.setText(this.getText());
        drawLabel.setSize(getSize());
        drawLabel.setFont(getFont());
        drawLabel.setIcon(getIcon());
        drawLabel.setEnabled(isEnabled());
        drawLabel.setHorizontalAlignment(SwingConstants.CENTER);
        drawLabel.paint(g);
    }
    /**
     * ���ƽ���
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param c1 Color
     * @param c2 Color
     * @param g Graphics
     */
    public void fillTransition(int x, int y, int width, int height, Color c1,
                               Color c2, Graphics g)
    {
      for (int i = 0; i < height; i++)
      {
        double d = (double) i / (double) height;
        int R = (int) (c1.getRed() - (c1.getRed() - c2.getRed()) * d);
        int G = (int) (c1.getGreen() - (c1.getGreen() - c2.getGreen()) * d);
        int B = (int) (c1.getBlue() - (c1.getBlue() - c2.getBlue()) * d);
        g.setColor(new Color(R, G, B));
        g.fillRect(x, i + y, width, 1);
      }
    }
    /**
     * ����������
     * @param toRight boolean
     */
    public void setToRight(boolean toRight)
    {
        this.toRight = toRight;
    }
    /**
     * �Ƿ�������
     * @return boolean
     */
    public boolean isToRight()
    {
        return toRight;
    }
    /**
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        if("en".equals(language) && getEnTip() != null && getEnTip().length() > 0){
            setToolTipText(getEnTip());
            this.setText(this.getEnText());
            
        }
        else if(getZhTip() != null && getZhTip().length() > 0){
            setToolTipText(getZhTip());
            this.setText(this.getText());
        }
    }
}
