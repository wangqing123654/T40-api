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
 * <p>Title: 宏模型</p>
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
     * 控制器
     */
    private MMacroroutine manager;
    /**
     * 宏
     */
    private EMacroroutine macroroutine;
    /**
     * 当前宏
     */
    private EMacroroutine macroroutineNow;
    /**
     * 语法
     */
    private ESyntaxItem syntax;
    /**
     * 复合宏列表
     */
    private TList macroroutines;
    /**
     * 对其位置
     * 0 left
     * 1 center
     * 2 right
     */
    private int alignment = 0;
    /**
     * 图层管理器
     */
    private MVList mvList;
    /**
     * 锁定尺寸
     */
    private boolean lockSize = false;
    /**
     * 宽度
     */
    private int width = 100;
    /**
     * 高度
     */
    private int height = 100;
    /**
     * 字体
     */
    private Font font;
    /**
     * 前景颜色
     */
    private Color color;
    /**
     * CDA 组名;
     */
    private String groupName;
    /**
     * CDA 名;
     */
    private String cdaName;

    /**
     * 显示数据列
     */
    private String[] showValueList = new String[0];

    /**
     * 构造器
     */
    public EMacroroutineModel()
    {
        macroroutines = new TList();
        createMVList();
    }
    /**
     * 创建图层管理器
     */
    public void createMVList()
    {
        mvList = new MVList(this);
    }
    /**
     * 构造器
     * @param macroroutine EMacroroutine
     */
    public EMacroroutineModel(EMacroroutine macroroutine)
    {
        this();
        setMacroroutine(macroroutine);
    }
    /**
     * 构造器
     * @param manager MMacroroutine
     */
    public EMacroroutineModel(MMacroroutine manager)
    {
        this();
        setManager(manager);
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return manager.getPM();
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
     * 设置控制器
     * @param manager MMacroroutine
     */
    public void setManager(MMacroroutine manager)
    {
        this.manager = manager;
    }
    /**
     * 得到控制器
     * @return MMacroroutine
     */
    public MMacroroutine getManager()
    {
        return manager;
    }
    /**
     * 设置宏
     * @param macroroutine EMacroroutine
     */
    public void setMacroroutine(EMacroroutine macroroutine)
    {
        this.macroroutine = macroroutine;
    }
    /**
     * 得到宏
     * @return EMacroroutine
     */
    public EMacroroutine getMacroroutine()
    {
        return macroroutine;
    }
    /**
     * 设置语法
     * @param syntax ESyntaxItem
     */
    public void setSyntax(ESyntaxItem syntax)
    {
        this.syntax = syntax;
    }
    /**
     * 设置语法
     * @param index int
     */
    public void setSyntax(int index)
    {
        if(index == -1)
            return;
        setSyntax(getSyntaxManager().get(index));
    }
    /**
     * 得到语法
     * @return ESyntaxItem
     */
    public ESyntaxItem getSyntax()
    {
        return syntax;
    }
    /**
     * 查找组件位置
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
     * 查找自己的位置
     * @return int
     */
    public int findIndex()
    {
        return findIndex(this);
    }
    /**
     * 增加复合宏
     * @param macroroutine EMacroroutine
     */
    public void addMacroroutine(EMacroroutine macroroutine)
    {

        macroroutines.add(macroroutine);
    }
    /**
     * 增加复合宏
     * @param index int
     * @param macroroutine EMacroroutine
     */
    public void addMacroroutine(int index,EMacroroutine macroroutine)
    {
        macroroutines.add(index,macroroutine);
    }
    /**
     * 删除复合宏
     * @param index int
     */
    public void removeMacroroutine(int index)
    {
        macroroutines.remove(index);
    }
    /**
     * 删除复合宏
     * @param macroroutine EMacroroutine
     */
    public void removeMacroroutine(EMacroroutine macroroutine)
    {
        macroroutines.remove(macroroutine);
    }
    /**
     * 复合宏个数
     * @return int
     */
    public int sizeMacroroutine()
    {
        return macroroutines.size();
    }
    /**
     * 得到复合宏
     * @param index int
     * @return EMacroroutine
     */
    public EMacroroutine getEMacroroutine(int index)
    {
        return (EMacroroutine)macroroutines.get(index);
    }
    /**
     * 释放自己
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
     * 创建语法
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
     * 查找自己语法的位置
     * @return int
     */
    public int findSyntaxIndex()
    {
        if(getSyntax() == null)
            return -1;
        return getSyntax().findIndex();
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        if(getSyntax() == null)
            return "";
        return getSyntax().getName();
    }
    /**
     * 修改名称
     * @param name String
     */
    public void setName(String name)
    {
        if (getSyntax() == null)
            return;
        if (getSyntax().getName().equals(name))
            return;
        getSyntax().setName(name);
        //编译程序
        if(!getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return;
        }
        show();
        getManager().getFocusManager().update();
    }
    /**
     * 双击
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

        //编译程序
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
     * 显示
     */
    public void show()
    {
        if(getManager().isRun())
            run();
        else
            edit();
    }
    /**
     * 运行宏
     */
    public void run()
    {
        if(getSyntaxManager().getControl() == null){
            runIndependent();
            return;
        }
        
        //单值宏
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
        //复合宏
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
     * 独立运行宏
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
        //复合宏
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
     * 编辑宏
     */
    public void edit()
    {
        if(getMacroroutine() != null)
        {
            getMacroroutine().uniteAll();
            getMacroroutine().setString(getName());
            return;
        }
        //复合宏
        for(int i = 0;i < sizeMacroroutine();i++)
        {
            EMacroroutine macroroutine = getEMacroroutine(i);
            macroroutine.uniteAll();
            macroroutine.setString(getName());
        }
    }
    /**
     * 写对象
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
     * 写对象
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
     * 读对象
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
     * 删除自己
     */
    public void removeThis()
    {
        free();
    }
    /**
     * 设置对其位置(当前位置)
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
     * 得到对其位置
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
     * 锁定尺寸
     * @param lockSize boolean
     */
    public void setLockSize(boolean lockSize)
    {
        this.lockSize = lockSize;
    }
    /**
     * 是否锁定尺寸
     * @return boolean
     */
    public boolean isLockSize()
    {
        return lockSize;
    }
    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width)
    {
        this.width = width;
    }
    /**
     * 得到宽度
     * @return int
     */
    public int getWidth()
    {
        return width;
    }
    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height)
    {
        this.height = height;
    }
    /**
     * 得到高度
     * @return int
     */
    public int getHeight()
    {
        return height;
    }
    /**
     * 得到图层管理器
     * @return MV
     */
    public MVList getMVList()
    {
        return mvList;
    }
    /**
     * 绘制背景
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
     * 绘制前景
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
     * 绘制背景(打印)
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
     * 绘制前景(打印)
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
     * 设置当前宏
     * @param macroroutineNow EMacroroutine
     */
    public void setMacroroutineNow(EMacroroutine macroroutineNow)
    {
        this.macroroutineNow = macroroutineNow;
    }
    /**
     * 得到当前宏
     * @return EMacroroutine
     */
    public EMacroroutine getMacroroutineNow()
    {
        return macroroutineNow;
    }
    /**
     * 得到数据
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
     * 设置字体
     * @param font Font
     */
    public void setFont(Font font)
    {
        this.font = font;
    }
    /**
     * 得到
     * @return Font
     */
    public Font getFont()
    {
        return font;
    }
    /**
     * 设置字体名称
     * @param name String
     */
    public void setFontName(String name)
    {
        setFont(new Font(name,getFontStyle(),getFontSize()));
    }
    /**
     * 得到字体名称
     * @return String
     */
    public String getFontName()
    {
        if(getFont()== null)
            return null;
        return getFont().getFontName();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        setFont(new Font(getFontName(),getFontStyle(),size));
    }
    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize()
    {
        if(getFont()== null)
            return 0;
        return getFont().getSize();
    }
    /**
     * 设置字体风格
     * @param style int
     */
    public void setFontStyle(int style)
    {
        setFont(new Font(getFontName(),style,getFontSize()));
    }
    /**
     * 得到字体风格
     * @return int
     */
    public int getFontStyle()
    {
        if(getFont()== null)
            return 0;
        return getFont().getStyle();
    }
    /**
     * 设置粗体
     * @param b boolean
     */
    public void setFontBold(boolean b)
    {
        setFontStyle((b?Font.BOLD:0)|((isFontItalic())?Font.ITALIC:0));
    }
    /**
     * 是否是粗体
     * @return boolean
     */
    public boolean isFontBold()
    {
        return (getFontStyle() & Font.BOLD) == Font.BOLD;
    }
    /**
     * 设置是否是斜体
     * @param b boolean
     */
    public void setFontItalic(boolean b)
    {
        setFontStyle((isFontBold()?Font.BOLD:0)|(b?Font.ITALIC:0));
    }
    /**
     * 是否是斜体
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
