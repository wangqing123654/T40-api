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
     * 图片名称
     */
    private String pictureName;
    /**
     * 图片对象
     */
    private ImageIcon pic;
    private static JLabel drawLabel;
    /**
     * 置右面
     */
    private boolean toRight;
    /**
     * 构造器
     */
    public TToolButton()
    {
    }
    /**
     * 设置按钮图片
     * @param name 图片名称
     */
    public void setPictureName(String name)
    {
      pictureName = name;
      if(name != null)
          pic = createImageIcon(name);
      this.setIcon(pic);
    }
    /**
     * 得到按钮图片
     * @return 图片名称
     */
    public String getPictureName()
    {
      return pictureName;
    }
    /**
     * 设置有效 (增加同步工具条方法)
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
     * 设置显示 (增加同步工具条方法)
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
     * 得到菜单对象
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
     * 得到菜单条
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
     * 加载图片
     * @param filename String
     * @return ImageIcon
     */
    private ImageIcon createImageIcon(String filename)
    {
    	//$$ modified by lx 2014/02/11   修改工具栏图标样式
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
            err("没有找到图标" + path);
        }
        
        return icon;
    }
    /**
     * 过滤初始化信息
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
     * 重新命名属性名称
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
        super.baseFieldNameChange(value);
        if ("pic".equalsIgnoreCase(value[0]))
            value[0] = "pictureName";
    }
    /**
     * 画图
     * @param g 图形对象
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
     * 绘制渐进
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
     * 设置置右面
     * @param toRight boolean
     */
    public void setToRight(boolean toRight)
    {
        this.toRight = toRight;
    }
    /**
     * 是否置右面
     * @return boolean
     */
    public boolean isToRight()
    {
        return toRight;
    }
    /**
     * 设置语种
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
