package com.dongyang.tui.text;

import com.dongyang.tui.DText;
import com.dongyang.util.TList;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.div.MVList;
import com.dongyang.util.TypeTool;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

/**
 *
 * <p>Title: ��ģ��</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.11
 * @version 1.0
 */
public class EMacroroutineModel
{
    /**
     * ������
     */
    private MMacroroutine manager;
    /**
     * ��
     */
    private EMacroroutine macroroutine;
    /**
     * ��ǰ��
     */
    private EMacroroutine macroroutineNow;
    /**
     * �﷨
     */
    private ESyntaxItem syntax;
    /**
     * ���Ϻ��б�
     */
    private TList macroroutines;
    /**
     * ����λ��
     * 0 left
     * 1 center
     * 2 right
     */
    private int alignment = 0;
    /**
     * ͼ�������
     */
    private MVList mvList;
    /**
     * �����ߴ�
     */
    private boolean lockSize = false;
    /**
     * ���
     */
    private int width = 100;
    /**
     * �߶�
     */
    private int height = 100;
    /**
     * ����
     */
    private Font font;
    /**
     * ǰ����ɫ
     */
    private Color color;
    /**
     * CDA ����;
     */
    private String groupName;
    /**
     * CDA ��;
     */
    private String cdaName;

    /**
     * ��ʾ������
     */
    private String[] showValueList = new String[0];

    /**
     * ������
     */
    public EMacroroutineModel()
    {
        macroroutines = new TList();
        createMVList();
    }
    /**
     * ����ͼ�������
     */
    public void createMVList()
    {
        mvList = new MVList(this);
    }
    /**
     * ������
     * @param macroroutine EMacroroutine
     */
    public EMacroroutineModel(EMacroroutine macroroutine)
    {
        this();
        setMacroroutine(macroroutine);
    }
    /**
     * ������
     * @param manager MMacroroutine
     */
    public EMacroroutineModel(MMacroroutine manager)
    {
        this();
        setManager(manager);
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return manager.getPM();
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
     * ���ÿ�����
     * @param manager MMacroroutine
     */
    public void setManager(MMacroroutine manager)
    {
        this.manager = manager;
    }
    /**
     * �õ�������
     * @return MMacroroutine
     */
    public MMacroroutine getManager()
    {
        return manager;
    }
    /**
     * ���ú�
     * @param macroroutine EMacroroutine
     */
    public void setMacroroutine(EMacroroutine macroroutine)
    {
        this.macroroutine = macroroutine;
    }
    /**
     * �õ���
     * @return EMacroroutine
     */
    public EMacroroutine getMacroroutine()
    {
        return macroroutine;
    }
    /**
     * �����﷨
     * @param syntax ESyntaxItem
     */
    public void setSyntax(ESyntaxItem syntax)
    {
        this.syntax = syntax;
    }
    /**
     * �����﷨
     * @param index int
     */
    public void setSyntax(int index)
    {
        if(index == -1)
            return;
        setSyntax(getSyntaxManager().get(index));
    }
    /**
     * �õ��﷨
     * @return ESyntaxItem
     */
    public ESyntaxItem getSyntax()
    {
        return syntax;
    }
    /**
     * �������λ��
     * @param model EMacroroutineModel
     * @return int
     */
    public int findIndex(EMacroroutineModel model)
    {
        MMacroroutine manager = getManager();
        if(manager == null)
            return -1;
        return manager.indexOf(model);
    }
    /**
     * �����Լ���λ��
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * ���Ӹ��Ϻ�
     * @param macroroutine EMacroroutine
     */
    public void addMacroroutine(EMacroroutine macroroutine)
    {

        macroroutines.add(macroroutine);
    }
    /**
     * ���Ӹ��Ϻ�
     * @param index int
     * @param macroroutine EMacroroutine
     */
    public void addMacroroutine(int index,EMacroroutine macroroutine)
    {
        macroroutines.add(index,macroroutine);
    }
    /**
     * ɾ�����Ϻ�
     * @param index int
     */
    public void removeMacroroutine(int index)
    {
        macroroutines.remove(index);
    }
    /**
     * ɾ�����Ϻ�
     * @param macroroutine EMacroroutine
     */
    public void removeMacroroutine(EMacroroutine macroroutine)
    {
        macroroutines.remove(macroroutine);
    }
    /**
     * ���Ϻ����
     * @return int
     */
    public int sizeMacroroutine()
    {
        return macroroutines.size();
    }
    /**
     * �õ����Ϻ�
     * @param index int
     * @return EMacroroutine
     */
    public EMacroroutine getEMacroroutine(int index)
    {
        return (EMacroroutine)macroroutines.get(index);
    }
    /**
     * �ͷ��Լ�
     */
    public void free()
    {
        getManager().remove(this);
        if(getSyntax() != null)
        {
            getSyntaxManager().remove(getSyntax());
            setSyntax(null);
        }
    }
    /**
     * �����﷨
     * @return ESyntaxItem
     */
    public ESyntaxItem createSyntax()
    {
        ESyntaxItem syntax = new ESyntaxItem();
        syntax.setClassify(1);
        getSyntaxManager().add(syntax);
        setSyntax(syntax);
        return syntax;
    }
    /**
     * �����Լ��﷨��λ��
     * @return int
     */
    public int findSyntaxIndex()
    {
        if(getSyntax() == null)
            return -1;
        return getSyntax().findIndex();
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        if(getSyntax() == null)
            return "";
        return getSyntax().getName();
    }
    /**
     * �޸�����
     * @param name String
     */
    public void setName(String name)
    {
        if (getSyntax() == null)
            return;
        if (getSyntax().getName().equals(name))
            return;
        getSyntax().setName(name);
        //�������
        if(!getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return;
        }
        show();
        getManager().getFocusManager().update();
    }
    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        if(getSyntax() == null)
            return true;
        Object value[] = (Object[])getManager().getUI().openDialog("%ROOT%\\config\\database\\MacroroutineEditDialog.x",
                new Object[]{getSyntax().getName(),
                getSyntax().getSyntax(),
                getSyntax().getType(),
                isLockSize(),
                getWidth(),
                getHeight(),getGroupName(),getCdaName()});
        if(value == null)
            return true;
        boolean ismake = false;
        if(!getSyntax().getName().equals((String)value[0]))
        {
            getSyntax().setName((String) value[0]);
            ismake = true;
        }
        if(!getSyntax().getSyntax().equals((String)value[1]))
        {
            getSyntax().setSyntax((String)value[1]);
            ismake = true;
        }
        if(getSyntax().getType() != (Integer)value[2])
        {
            getSyntax().setType((Integer) value[2]);
            ismake = true;
        }
        setLockSize(TypeTool.getBoolean(value[3]));
        setWidth(TypeTool.getInt(value[4]));
        setHeight(TypeTool.getInt(value[5]));
        setGroupName(TypeTool.getString(value[6]));
        setCdaName(TypeTool.getString(value[7]));

        //�������
        if(ismake && !getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return true;
        }
        show();
        getManager().getFocusManager().update();
        return true;
    }
    /**
     * ��ʾ
     */
    public void show()
    {
        if(getManager().isRun())
            run();
        else
            edit();
    }
    /**
     * ���к�
     */
    public void run()
    {
        if(getSyntaxManager().getControl() == null){
            runIndependent();
            return;
        }
        
        //��ֵ��
        if(getMacroroutine() != null)
        {
            String value = (String) getSyntaxManager().getControl().runM(
                    getSyntax().getMethodName(), getMacroroutine());
            getMacroroutine().uniteAll();
            if(value != null &&
               value.length() != 0 &&
               !value.equalsIgnoreCase("null")&&
               !value.startsWith("err")){
                getMacroroutine().setString(value);
                getMacroroutine().setShowValue(value);
                return;
            }
            if(getMacroroutine().getShowValue() != null &&
               getMacroroutine().getShowValue().length() != 0 &&
               !getMacroroutine().getShowValue().equalsIgnoreCase("null")){
                getMacroroutine().setString(getMacroroutine().getShowValue());
                return;
            }
            getMacroroutine().setString("");
            return;
        }
        //���Ϻ�
        String[] showValueListTemp = new String[sizeMacroroutine()];
        for(int i = 0;i < sizeMacroroutine();i++)
        {
            EMacroroutine macroroutine = getEMacroroutine(i);
                       
            String value = (String) getSyntaxManager().getControl().runM(
                    getSyntax().getMethodName(), macroroutine);        
            macroroutine.uniteAll();
            
            if(value != null &&
               value.length() != 0 &&
               !value.equalsIgnoreCase("null")&&
               !value.startsWith("err")){
                macroroutine.setString(value);
                showValueListTemp[i] = value;
                continue;
            }
            if(i >= showValueList.length){
                macroroutine.setString("");
                showValueListTemp[i] = "";
                continue;
            }
            if(showValueList[i] != null &&
               showValueList[i].length() != 0 &&
               !showValueList[i].equalsIgnoreCase("null")){
                macroroutine.setString(showValueList[i]);
                showValueListTemp[i] = showValueList[i];
                continue;
            }
            macroroutine.setString("");
            showValueListTemp[i] = "";
        }
        if(sizeMacroroutine() > 0)
            showValueList = showValueListTemp;
    }

    /**
     * �������к�
     */
    public void runIndependent(){
        if(getMacroroutine() != null)
        {
            if(getMacroroutine().getShowValue() == null ||
               getMacroroutine().getShowValue().equalsIgnoreCase("null")){
                getMacroroutine().uniteAll();
                getMacroroutine().setString("");
                return;
            }
            getMacroroutine().uniteAll();
            getMacroroutine().setString(getMacroroutine().getShowValue());
            return;
        }
        //���Ϻ�
        for(int i = 0;i < sizeMacroroutine();i++)
        {
            EMacroroutine macroroutine = getEMacroroutine(i);
            macroroutine.uniteAll();
            if(i >= showValueList.length){
                macroroutine.setString("");
                continue;
            }
            if(showValueList[i] == null ||
               showValueList[i].equalsIgnoreCase("null")){
                macroroutine.setString("");
                continue;
            }
            macroroutine.setString(showValueList[i]);
        }
    }

    /**
     * �༭��
     */
    public void edit()
    {
        if(getMacroroutine() != null)
        {
            getMacroroutine().uniteAll();
            getMacroroutine().setString(getName());
            return;
        }
        //���Ϻ�
        for(int i = 0;i < sizeMacroroutine();i++)
        {
            EMacroroutine macroroutine = getEMacroroutine(i);
            macroroutine.uniteAll();
            macroroutine.setString(getName());
        }
    }
    /**
     * д����
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s,int c)
      throws IOException
    {
        s.WO("<MMacroroutineModel>",c);
        s.WO(1,"SyntaxIndex",findSyntaxIndex(),"",c + 1);
        s.WO("</MMacroroutineModel>",c);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeInt(1, findSyntaxIndex(), -1);
        s.writeInt(2, getAlignment(),0);
        s.writeBoolean(3,isLockSize(),false);
        s.writeInt(4,getWidth(),0);
        s.writeInt(5,getHeight(),0);
        s.writeShort(6);
        getMVList().writeObject(s);
        s.writeShort(7);
        s.writeString(getFontName());
        s.writeInt(getFontStyle());
        s.writeInt(getFontSize());
        s.writeShort(8);
        s.writeStrings(getShowValueList());
        s.writeShort(9);
        s.writeString(getGroupName());
        s.writeShort(10);
        s.writeString(getCdaName());
        s.writeShort( -1);

    }
    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s) throws IOException
    {
        int id = s.readShort();
        while (id > 0)
        {
            switch (id)
            {
            case 1:
                setSyntax(s.readInt());
                break;
            case 2:
                setAlignment(s.readInt());
                break;
            case 3:
                setLockSize(s.readBoolean());
                break;
            case 4:
                setWidth(s.readInt());
                break;
            case 5:
                setHeight(s.readInt());
                break;
            case 6:
                getMVList().readObject(s);
                break;
            case 7:
                String f = s.readString();
                int s1 = s.readInt();
                int s2 = s.readInt();
                if(f != null)
                    setFont(new Font(f,s1,s2));
                break;
            case 8:
                setShowValueList(s.readStrings());
                break;
            case 9:
                setGroupName(s.readString());
                 break;
            case 10:
                setCdaName(s.readString());
                 break;
            }
            id = s.readShort();
        }
    }
    /**
     * ɾ���Լ�
     */
    public void removeThis()
    {
        free();
    }
    /**
     * ���ö���λ��(��ǰλ��)
     * @param alignment int
     * 0 left
     * 1 center
     * 2 right
     */
    public void setAlignment(int alignment)
    {
        this.alignment = alignment;
    }
    /**
     * �õ�����λ��
     * @return int
     * 0 left
     * 1 center
     * 2 right
     */
    public int getAlignment()
    {
        return alignment;
    }
    /**
     * �����ߴ�
     * @param lockSize boolean
     */
    public void setLockSize(boolean lockSize)
    {
        this.lockSize = lockSize;
    }
    /**
     * �Ƿ������ߴ�
     * @return boolean
     */
    public boolean isLockSize()
    {
        return lockSize;
    }
    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * �õ����
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * �õ��߶�
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * �õ�ͼ�������
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
    }
    /**
     * ���Ʊ���
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintBackground(Graphics g,int x,int y,int pageIndex)
    {
        getMVList().paintBackground(g,x,y,pageIndex);
    }
    /**
     * ����ǰ��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void paintForeground(Graphics g,int x,int y,int pageIndex)
    {
        getMVList().paintForeground(g,x,y,pageIndex);
    }
    /**
     * ���Ʊ���(��ӡ)
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printBackground(Graphics g,int x,int y,int pageIndex)
    {
        getMVList().printBackground(g,x,y,pageIndex);
    }
    /**
     * ����ǰ��(��ӡ)
     * @param g Graphics
     * @param x int
     * @param y int
     * @param pageIndex int
     */
    public void printForeground(Graphics g,int x,int y,int pageIndex)
    {
        getMVList().printForeground(g,x,y,pageIndex);
    }
    /**
     * ���õ�ǰ��
     * @param macroroutineNow EMacroroutine
     */
    public void setMacroroutineNow(EMacroroutine macroroutineNow)
    {
        this.macroroutineNow = macroroutineNow;
    }
    /**
     * �õ���ǰ��
     * @return EMacroroutine
     */
    public EMacroroutine getMacroroutineNow()
    {
        return macroroutineNow;
    }
    /**
     * �õ�����
     * @param column String
     * @return Object
     */
    public Object getData(String column)
    {
        if(getMacroroutineNow() == null)
            return null;
        
        return getMacroroutineNow().getData(column);
    }
    /**
     * ��������
     * @param font Font
     */
    public void setFont(Font font)
    {
        this.font = font;
    }
    /**
     * �õ�
     * @return Font
     */
    public Font getFont()
    {
        return font;
    }
    /**
     * ������������
     * @param name String
     */
    public void setFontName(String name)
    {
        setFont(new Font(name,getFontStyle(),getFontSize()));
    }
    /**
     * �õ���������
     * @return String
     */
    public String getFontName()
    {
        if(getFont()== null)
            return null;
        return getFont().getFontName();
    }
    /**
     * ��������ߴ�
     * @param size int
     */
    public void setFontSize(int size)
    {
        setFont(new Font(getFontName(),getFontStyle(),size));
    }
    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize()
    {
        if(getFont()== null)
            return 0;
        return getFont().getSize();
    }
    /**
     * ����������
     * @param style int
     */
    public void setFontStyle(int style)
    {
        setFont(new Font(getFontName(),style,getFontSize()));
    }
    /**
     * �õ�������
     * @return int
     */
    public int getFontStyle()
    {
        if(getFont()== null)
            return 0;
        return getFont().getStyle();
    }
    /**
     * ���ô���
     * @param b boolean
     */
    public void setFontBold(boolean b)
    {
        setFontStyle((b?Font.BOLD:0)|((isFontItalic())?Font.ITALIC:0));
    }
    /**
     * �Ƿ��Ǵ���
     * @return boolean
     */
    public boolean isFontBold()
    {
        return (getFontStyle() & Font.BOLD) == Font.BOLD;
    }
    /**
     * �����Ƿ���б��
     * @param b boolean
     */
    public void setFontItalic(boolean b)
    {
        setFontStyle((isFontBold()?Font.BOLD:0)|(b?Font.ITALIC:0));
    }
    /**
     * �Ƿ���б��
     * @return boolean
     */
    public boolean isFontItalic()
    {
        return (getFontStyle() & Font.ITALIC) == Font.ITALIC;
    }

    public String[] getShowValueList(){
        return showValueList;
    }
    public void setShowValueList(String[] showValueList){
        this.showValueList = showValueList;
    }
    /**
     *
     * @return String
     */
    public String getGroupName(){
        return groupName;
    }
    /**
     *
     * @param groupName String
     */
    public void setGroupName(String groupName){
        this.groupName=groupName;
    }
    /**
     *
     * @return String
     */
    public String getCdaName(){
        return cdaName;
    }

    /**
     *
     * @param cdaName String
     */
    public void setCdaName(String cdaName){
        this.cdaName=cdaName;
    }

}
