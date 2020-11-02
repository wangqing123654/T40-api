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
 * <p>Title: ͼ�����ģ��</p>
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
     * ͼ�����
     */
    private EPic pic;
    /**
     * �﷨
     */
    private Map syntaxs;
    /**
     * ������
     */
    public EPicModel()
    {
        syntaxs = new HashMap();
    }
    /**
     * ������
     * @param pic EPic
     */
    public EPicModel(EPic pic)
    {
        this();
        setPic(pic);
    }
    /**
     * ����ͼ�����
     * @param pic EPic
     */
    public void setPic(EPic pic)
    {
        this.pic = pic;
    }
    /**
     * �õ�ͼ�����
     * @return EPic
     */
    public EPic getPic()
    {
        return pic;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getPic().getPM();
    }
    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI()
    {
        return getPM().getUI();
    }

    /**
     * �õ��﷨������
     * @return MSyntax
     */
    public MSyntax getSyntaxManager()
    {
        return getPM().getSyntaxManager();
    }
    /**
     * �����﷨
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
     * �����﷨
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
     * �õ��﷨
     * @param name String
     * @return ESyntaxItem
     */
    public ESyntaxItem getSyntax(String name)
    {
        return (ESyntaxItem)syntaxs.get(name);
    }
    /**
     * �õ��﷨����
     * @return int
     */
    public int getSyntaxsSize()
    {
        return syntaxs.size();
    }
    /**
     * �õ��﷨������
     * @return Iterator
     */
    public Iterator getSyntaxIterator()
    {
        return syntaxs.keySet().iterator();
    }
    /**
     * �����﷨
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
     * ��ʼ���﷨
     */
    public void initSyntax()
    {
        //��������
        ESyntaxItem syntax = createSyntax("paintBackground");
        syntax.setInValue("Graphics g,int width,int height,EPic This");
        syntax.setSyntax("//g.fillRect(10,10,30,30);\n");
        //����ǰ��
        syntax = createSyntax("paintForeground");
        syntax.setInValue("Graphics g,int width,int height,EPic This");
        syntax.setSyntax("//g.setColor(new Color(0,255,0));\n//g.fillRect(15,15,30,30);\n");
        //�������
        if(!syntax.getManager().make())
            getUI().messageBox(syntax.getManager().getMakeErrText());
    }
    /**
     * �����﷨
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
     * ���Ʊ���
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
     * ����ǰ��
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
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\PicEdit.x",getPic());
        if(value == null || !value.equals("OK"))
            return true;
        //�������
        if(!getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return true;
        }
        getPM().getFocusManager().update();
        return true;
    }
    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
    }
    /**
     * ����������
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
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        //д��������
        writeObjectAttribute(s);
        s.writeShort( -1);
        //����ҳ
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
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s)
      throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            //����������
            readObjectAttribute(id, s);
            id = s.readShort();
        }
        //��ȡ�﷨
        int count = s.readInt();
        for(int i = 0;i < count;i++)
            setSyntax(s.readInt());
    }
}
