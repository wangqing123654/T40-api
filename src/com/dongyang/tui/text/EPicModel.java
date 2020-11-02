package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.tui.DText;
import java.util.Map;
import java.util.HashMap;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import java.util.Iterator;

/**
 *
 * <p>Title: 图形组件模型</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class EPicModel
{
    /**
     * 图形组件
     */
    private EPic pic;
    /**
     * 语法
     */
    private Map syntaxs;
    /**
     * 构造器
     */
    public EPicModel()
    {
        syntaxs = new HashMap();
    }
    /**
     * 构造器
     * @param pic EPic
     */
    public EPicModel(EPic pic)
    {
        this();
        setPic(pic);
    }
    /**
     * 设置图形组件
     * @param pic EPic
     */
    public void setPic(EPic pic)
    {
        this.pic = pic;
    }
    /**
     * 得到图形组件
     * @return EPic
     */
    public EPic getPic()
    {
        return pic;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getPic().getPM();
    }
    /**
     * 得到UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }

    /**
     * 得到语法管理器
     * @return MSyntax
     */
    public MSyntax getSyntaxManager()
    {
        return getPM().getSyntaxManager();
    }
    /**
     * 设置语法
     * @param name String
     * @param syntax ESyntaxItem
     */
    public void setSyntax(String name,ESyntaxItem syntax)
    {
        if(syntax == null)
        {
            ESyntaxItem item = getSyntax(name);
            if(item != null)
                getSyntaxManager().remove(item);
            syntaxs.remove(name);
            return;
        }
        ESyntaxItem item = getSyntax(name);
        if(item != syntax)
            getSyntaxManager().remove(item);
        syntaxs.put(name,syntax);
    }
    /**
     * 设置语法
     * @param index int
     */
    public void setSyntax(int index)
    {
        if(index == -1)
            return;
        ESyntaxItem syntax = getSyntaxManager().get(index);
        setSyntax(syntax.getName(),syntax);
    }
    /**
     * 得到语法
     * @param name String
     * @return ESyntaxItem
     */
    public ESyntaxItem getSyntax(String name)
    {
        return (ESyntaxItem)syntaxs.get(name);
    }
    /**
     * 得到语法个数
     * @return int
     */
    public int getSyntaxsSize()
    {
        return syntaxs.size();
    }
    /**
     * 得到语法迭代器
     * @return Iterator
     */
    public Iterator getSyntaxIterator()
    {
        return syntaxs.keySet().iterator();
    }
    /**
     * 创建语法
     * @param name String
     * @return ESyntaxItem
     */
    public ESyntaxItem createSyntax(String name)
    {
        ESyntaxItem syntax = new ESyntaxItem();
        syntax.setName(name);
        syntax.setFunctionNameValue(name);
        syntax.setType(1);
        syntax.setClassify(2);
        getSyntaxManager().add(syntax);
        setSyntax(name,syntax);
        return syntax;
    }

    /**
     * 初始化语法
     */
    public void initSyntax()
    {
        //创建背景
        ESyntaxItem syntax = createSyntax("paintBackground");
        syntax.setInValue("Graphics g,int width,int height,EPic This");
        syntax.setSyntax("//g.fillRect(10,10,30,30);\n");
        //创建前景
        syntax = createSyntax("paintForeground");
        syntax.setInValue("Graphics g,int width,int height,EPic This");
        syntax.setSyntax("//g.setColor(new Color(0,255,0));\n//g.fillRect(15,15,30,30);\n");
        //编译程序
        if(!syntax.getManager().make())
            getUI().messageBox(syntax.getManager().getMakeErrText());
    }
    /**
     * 销毁语法
     */
    public void destroy()
    {
        ESyntaxItem item = getSyntax("paintBackground");
        if(item != null)
            getSyntaxManager().remove(item);
        item = getSyntax("paintForeground");
        if(item != null)
            getSyntaxManager().remove(item);
    }
    /**
     * 绘制背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintBackground(Graphics g,int x,int y,int width,int height)
    {
        if(getSyntaxManager().getControl() == null)
            return;
        getSyntaxManager().getControl().runM(
                getSyntax("paintBackground").getMethodName() + "paintBackground",g,width,height,getPic());
    }
    /**
     * 绘制前景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintForeground(Graphics g,int x,int y,int width,int height)
    {
        if(getSyntaxManager().getControl() == null)
            return;
        getSyntaxManager().getControl().runM(
                getSyntax("paintForeground").getMethodName() + "paintForeground",g,width,height,getPic());
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\PicEdit.x",getPic());
        if(value == null || !value.equals("OK"))
            return true;
        //编译程序
        if(!getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return true;
        }
        getPM().getFocusManager().update();
        return true;
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
    }
    /**
     * 读对象属性
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id,DataInputStream s)
            throws IOException
    {
        return false;
    }
    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //写对象属性
        writeObjectAttribute(s);
        s.writeShort( -1);
        //保存页
        s.writeInt(getSyntaxsSize());
        Iterator iterator = getSyntaxIterator();
        while(iterator.hasNext())
        {
            String name = (String)iterator.next();
            ESyntaxItem syntax = getSyntax(name);
            s.writeInt(syntax.findIndex());
        }
    }
    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //读对象属性
            readObjectAttribute(id, s);
            id = s.readShort();
        }
        //读取语法
        int count = s.readInt();
        for(int i = 0;i < count;i++)
            setSyntax(s.readInt());
    }
}
