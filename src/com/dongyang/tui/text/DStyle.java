package com.dongyang.tui.text;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.FontMetrics;
import com.dongyang.tui.DFont;
import com.dongyang.ui.TWord;
import com.dongyang.util.StringTool;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.util.TList;

//import com.dongyang.ui.TPositionCombo;

/**
 *
 * <p>Title: 风格</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @author whao 2014
 * @version 1.0
 */
public class DStyle {
    /**
     * 管理器
     */
    private MStyle manager;
    /**
     * 名称
     */
    private String name;
    /**
     * 字体
     */
    private Font font;
    /**
     * 字体样式
     */
    private FontMetrics fontMetrics;
    /**
     * 背景颜色
     */
    private Color bkColor;
    /**
     * 前景颜色
     */
    private Color color;
    /**
     * 下划线
     */
    private boolean isLine;
    /**
     * 删除线
     */
    private boolean isDeleteLine;
    /**
     * 删除线类型
     */
    private int deleteLineType;
    /**
     * 线数
     */
    private int lineCount;
    /**
     * GC标记
     */
    private boolean gc;

    /**
     * 字体位置
     */
    //private String position;
    /**
     * 原字体位置;
     */
    //private String oldPosition;
    /**
     * 构造器
     * @param manager MStyle
     */
    public DStyle(MStyle manager) {
        setManager(manager);
    }

    /**
     * 设置管理器
     * @param manager MStyle
     */
    public void setManager(MStyle manager) {
        this.manager = manager;
    }

    /**
     * 得到管理器
     * @return MStyle
     */
    public MStyle getManager() {
        return manager;
    }

    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 设置名称
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * 设置字体
     * @param font Font
     */
    public void setFont(Font font) {
        this.font = font;
        if (font == null) {
            fontMetrics = null;
            return;
        }
        fontMetrics = DFont.getFontMetrics(getFont());
    }

    /**
     * 得到字体
     * @return Font
     */
    public Font getFont() {
        return font;
    }

    /**
     * 设置背景颜色
     * @param bkColor Color
     */
    public void setBKColor(Color bkColor) {
        this.bkColor = bkColor;
    }

    /**
     * 得到背景颜色
     * @return Color
     */
    public Color getBKColor() {
        return bkColor;
    }

    /**
     * 设置前景颜色
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * 得到前景颜色
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * 设置下划线
     * @param isLine boolean
     */
    public void setIsLine(boolean isLine) {

        if( TWord.IsMark ){

        	this.isLine = isLine;
        }else{

            //泰心专用
            this.isLine = false;
        }
    }

    /**
     * 是否是下划线
     * @return boolean
     */
    public boolean isLine() {
        return isLine;

    }

    /**
     * 设置是否是删除线
     * @param isDeleteLine boolean
     */
    public void setIsDeleteLine(boolean isDeleteLine) {

        if( TWord.IsMark ){

        	this.isDeleteLine = isDeleteLine;
        }else{

            //泰心专用
            this.isDeleteLine = false;
        }
    }

    /**
     * 是否是删除线
     * @return boolean
     */
    public boolean isDeleteLine() {
        return isDeleteLine;
    }

    /**
     * 设置删除线类型
     * @param deleteLineType int
     */
    public void setDeleteLineType(int deleteLineType) {
        this.deleteLineType = deleteLineType;
    }

    /**
     * 得到删除线类型
     * @return int
     */
    public int getDeleteLineType() {
        return deleteLineType;
    }

    /**
     * 得到字体样式
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics() {
        return fontMetrics;
    }

    /**
     * 调整尺寸
     * @param s String
     * @param com EComponent
     */
    public void resetSize(String s, EComponent com) {
        if (getFontMetrics() == null) {
            return;
        }
        if (s == null || com == null) {
            return;
        }
        //设置宽度
        com.setWidth(stringWidth(s));
        //设置高度
        com.setHeight(getFontMetrics().getHeight());
    }

    /**
     * 得到字符串宽度
     * @param s String
     * @return int
     */
    public int stringWidth(String s) {
        if (getFontMetrics() == null) {
            return 0;
        }
        return getFontMetrics().stringWidth(s);
    }

    /**
     * 得到字符宽度
     * @param c char
     * @return int
     */
    public int charwidth(char c) {
        if (getFontMetrics() == null) {
            return 0;
        }
        return getFontMetrics().charWidth(c);
    }

    /**
     * 计算分割位置
     * @param s String
     * @param width int
     * @return int
     */
    public int separateIndex(String s, int width) {
        if (s == null || s.length() == 0) {
            return -1;
        }
        if (getFontMetrics() == null) {
            return -1;
        }
        char c[] = s.toCharArray();
        int w = 0;
        for (int i = 0; i < c.length; i++) {
            w += getFontMetrics().charWidth(c[i]);
            if (w >= width) {
                char c1 = c[i];
                if (c1 == ',' || c1 == '，' || c1 == '.' || c1 == '。' ||
                    c1 == '、' || c1 == '”') {
                    return i > 0 ? (i - 1) : -1;
                }
                return i > 0 ? i : -1;
            }
        }
        return -1;
    }

    /**
     * 得到字符位置
     * @param s String
     * @param x int
     * @return int
     */
    public int getIndex(String s, int x) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        if (getFontMetrics() == null) {
            return -1;
        }
        char c[] = s.toCharArray();
        int width = 0;
        for (int i = 0; i < c.length; i++) {
            int w = getFontMetrics().charWidth(c[i]);
            if (x < width + w / 2) {
                return i;
            }
            width += w;
        }
        return c.length;
    }

    /**
     * 得到绘制高度
     * @return int
     */
    public int getAscent() {
        if (getFontMetrics() == null) {
            return 0;
        }
        return getFontMetrics().getAscent();
    }

    /**
     * 得到字体高度
     * @return int
     */
    public int getFontHeight() {
        if (getFontMetrics() == null) {
            return 0;
        }
        return getFontMetrics().getHeight();
    }

    /**
     * 设置线数
     * @param lineCount int
     */
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    /**
     * 得到线数
     * @return int
     */
    public int getLineCount() {
        return lineCount;
    }

    /**
     * 绘制
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     */
    public void paint(Graphics g, String s, int x, int y, int width, int height,
                      EText text) {
        if (!text.isSelectedDraw()) {
            //绘制背景
            paintBackground(g, s, x, y, width, height, text);
        }
        //绘制文字
        paintText(g, s, x, y, width, height, text);
        //绘制前景
        drawFront(g, x, y, width, height, text);
    }

    /**
     * 支持上下标绘制方法  add by lx
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     * @param position int
     */
    public void paint(Graphics g, String s, int x, int y, int width, int height,
                      EText text, int position) {
        if (!text.isSelectedDraw()) {
            //绘制背景
            paintBackground(g, s, x, y, width, height, text);
        }
        //绘制文字
        paintText(g, s, x, y, width, height, text, position);
        //绘制前景
        drawFront(g, x, y, width, height, text);
    }

    /**
     * 绘制前景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     */
    public void drawFront(Graphics g, int x, int y, int width, int height,
                          EText text) {
        if (isLine() && !text.isPreview()) {
            g.setColor(new Color(255, 0, 0));
            for (int i = 0; i < getLineCount(); i++) {
                g.drawLine(x, y + height - 2 + i * 2, x + width,
                           y + height - 2 + i * 2);
            }
        }
        if (isDeleteLine()) {
            g.setColor(new Color(255, 0, 0));
            int y0 = y + (height - getLineCount() * 2) / 2;
            for (int i = 0; i < getLineCount(); i++) {
                g.drawLine(x, y0 + i * 2, x + width, y0 + i * 2);
            }
        }
    }

    /**
     * 打印
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     */
    public void print(Graphics g, String s, int x, int y, int width, int height,
                      EText text) {
        if (getBKColor() != null) {
            g.setColor(getBKColor());
            g.fillRect(x, y, width, height);
        }
        if (getFont() != null) {
            g.setFont(getFont());
        }
        if (getColor() != null) {
            g.setColor(getColor());
        }
        g.drawString(s, x + 1, y + getAscent());
    }

    /**
     * 绘制背景
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     */
    public void paintBackground(Graphics g, String s, int x, int y, int width,
                                int height, EText text) {

        if (text.isSelectedDraw() || !text.isSelected()) {
            if (getBKColor() != null) {
                g.setColor(getBKColor());
                g.fillRect(x, y, width, height);
            }
            return;
        }
        double zoom = text.getZoom() / 75.0;
        if (s == null || s.length() == 0) {
            return;
        }
        TList list = text.getSelectedModel().getSelectList();
        g.setColor(new Color(0, 0, 0));
        for (int i = 0; i < list.size(); i++) {
            CSelectTextBlock block = (CSelectTextBlock) list.get(i);
            if (block.getStart() == block.getEnd()) {
                continue;
            }
            int startPoint = block.getStart();
            int endPoint = block.getEnd();

            int start = (int) (stringWidth(s.substring(0, startPoint)) *
                               zoom +0.5);

            int end =0;
            //$$============modified by lx 2011-12-30 支持病历浏览复制 start============$$//
            //浏览
            if (text.isPreview()) {
                end = (int) (stringWidth(s) *zoom + 0.5);
            }else{
                end = (int) (stringWidth(s.substring(startPoint, endPoint)) *zoom + 0.5);
            }
            //$$============modified by lx 2011-12-30 支持病历浏览复制 end============$$//

            g.fillRect(x + start, y, end, height);
            //测试用
            g.setColor(new Color(255, 0, 0));
            g.drawLine(x + start, y, x + start, y + height);
            g.drawLine(x + start + end - 1, y, x + start + end - 1,
                       y + height);
            g.setColor(new Color(0, 0, 0));

        }
    }

    /**
     * 绘制文字
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param ascent int
     * @param zoom double
     */
    public void drawString(Graphics g, String s, int x, int y, int ascent,
                           double zoom) {
        int cw = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            g.drawString("" + c, (int) (x + cw * zoom + 0.5), y + ascent);
            cw += charwidth(c);
        }
    }

    /**
     * 绘制文字
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     */
    public void paintText(Graphics g, String s, int x, int y, int width,
                          int height, EText text) {
        double zoom = text.getZoom() / 75.0;
        Font font = new Font(getFont().getName(), getFont().getStyle(),
                             (int) (getFont().getSize2D() * zoom + 0.5));
        FontMetrics metrics = DFont.getFontMetrics(font);
        int ascent = metrics.getAscent();
        g.setFont(font);
        if (s == null || s.length() == 0) {
            return;
        }
        Color color = new Color(0, 0, 0);
        if (getColor() != null) {
            color = getColor();
        }
        Color colorb = new Color(255, 255, 255);
        boolean isBack = text.isSelectedDraw();
        boolean isSelected = isBack;
        if (isSelected) {
            g.setColor(colorb);
        } else {
            g.setColor(color);
        }

        int cw = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isBack && text.isSelectedChar(i) != isSelected) {
                isSelected = !isSelected;
                if (isSelected) {
                    g.setColor(colorb);
                } else {
                    g.setColor(color);
                }
            }

            g.drawString("" + c, (int) (x + cw * zoom + 0.5), y + ascent);
            cw += charwidth(c);
        }
    }


    /**
     * 上下标绘制文字支持
     * @param g Graphics
     * @param s String
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     * @param text EText
     * @param position int
     */
    public void paintText(Graphics g, String s, int x, int y, int width,
                          int height, EText text, int position) {
        double zoom = text.getZoom() / 75.0;
        Font font = new Font(getFont().getName(), getFont().getStyle(),
                             (int) (getFont().getSize2D() * zoom + 0.5 +
                                    position));
        FontMetrics metrics = DFont.getFontMetrics(font);
        int ascent = metrics.getAscent();
        g.setFont(font);
        if (s == null || s.length() == 0) {
            return;
        }
        Color color = new Color(0, 0, 0);
        if (getColor() != null) {
            color = getColor();
        }
        Color colorb = new Color(255, 255, 255);
        boolean isBack = text.isSelectedDraw();
        boolean isSelected = isBack;
        if (isSelected) {
            g.setColor(colorb);
        } else {
            g.setColor(color);
        }

        int cw = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (!isBack && text.isSelectedChar(i) != isSelected) {
                isSelected = !isSelected;
                if (isSelected) {
                    g.setColor(colorb);
                } else {
                    g.setColor(color);
                }
            }

            g.drawString("" + c, (int) (x + cw * zoom + 0.5), y + ascent);
            cw += charwidth(c);
        }
    }


    /**
     * 复制一个样式
     * @return DStyle
     */
    public DStyle copy() {
        DStyle style = new DStyle(getManager());
        style.setFont(getFont());
        style.setBKColor(getBKColor());
        style.setColor(getColor());
        style.setIsLine(isLine());
        style.setIsDeleteLine(isDeleteLine());
        style.setLineCount(getLineCount());
        return style;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof DStyle)) {
            return false;
        }
        DStyle s = (DStyle) obj;
        if (!StringTool.equals(getFont(), s.getFont())) {
            return false;
        }
        if (!StringTool.equals(getBKColor(), s.getBKColor())) {
            return false;
        }
        if (!StringTool.equals(getColor(), s.getColor())) {
            return false;
        }
        if (isLine() != s.isLine()) {
            return false;
        }
        if (isDeleteLine() != s.isDeleteLine()) {
            return false;
        }
        if (getLineCount() != s.getLineCount()) {
            return false;
        }
        /**if (getPosition() != s.getPosition()) {
            return false;
                 }**/
        return true;
    }

    /**
     * 设置GC标记
     * @param gc boolean
     */
    public void setGC(boolean gc) {
        this.gc = gc;
    }

    /**
     * 得到GC标记
     * @return boolean
     */
    public boolean getGC() {
        return gc;
    }

    /**
     * 修改颜色
     * @param c Color
     * @return DStyle
     */
    public DStyle modifyColor(Color c) {
        DStyle style = copy();
        style.setColor(c);
        return getManager().getStyle(style);
    }

    /**
     * 修改字体
     * @param s String
     * @return DStyle
     */
    public DStyle modifyFont(String s) {
        DStyle style = copy();
        Font f = getFont();

        style.setFont(new Font(s, f.getStyle(), f.getSize()));
        return getManager().getStyle(style);
    }

    /**
     * 修改字体尺寸
     * @param size int
     * @return DStyle
     */
    public DStyle modifyFontSize(int size) {
        DStyle style = copy();
        Font f = getFont();
        style.setFont(new Font(f.getFontName(), f.getStyle(), size));
        return getManager().getStyle(style);
    }

    /**
     * 修改字体
     * @param font Font
     * @return DStyle
     */
    public DStyle modifyFont(Font font) {
        DStyle style = copy();
        style.setFont(font);
        return getManager().getStyle(style);
    }

    /**
     * 修改字体粗体
     * @param bold boolean true 粗体 false 非粗体
     * @return DStyle
     */
    public DStyle modifyBold(boolean bold) {
        DStyle style = copy();
        Font f = getFont();
        style.setFont(new Font(f.getFontName(),
                               (f.isItalic() ? 2 : 0) + (bold ? 1 : 0),
                               f.getSize()));
        return getManager().getStyle(style);
    }

    /**
     * 修改字体斜体
     * @param italic boolean true 斜体 false 非斜体
     * @return DStyle
     */
    public DStyle modifyItalic(boolean italic) {
        DStyle style = copy();
        Font f = getFont();

        style.setFont(new Font(f.getFontName(),
                               (italic ? 2 : 0) + (f.isBold() ? 1 : 0),
                               f.getSize()));
        return getManager().getStyle(style);
    }

    /**
     * 修改下划线
     * @param isLine boolean
     * @param lineCount int
     * @return DStyle
     */
    public DStyle modifyIsLine(boolean isLine, int lineCount) {
        DStyle style = copy();
        style.setIsLine(isLine);
        style.setIsDeleteLine(false);
        style.setLineCount(lineCount);
        return getManager().getStyle(style);
    }

    /**
     * 修改删除线
     * @param isDeleteLine boolean
     * @param lineCount int
     * @return DStyle
     */
    public DStyle modifyIsDeleteLine(boolean isDeleteLine, int lineCount) {
        DStyle style = copy();
        style.setIsDeleteLine(isDeleteLine);
        style.setLineCount(lineCount);
        return getManager().getStyle(style);
    }

    /**
     * 查找自己的位置
     * @return int
     */
    public int findIndex() {
        return findIndex(this);
    }

    /**
     * 查找组件位置
     * @param com EComponent
     * @return int
     */
    public int findIndex(DStyle com) {
        return getManager().indexOf(com);
    }

    /**
     * 写对象
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s) throws IOException {
        s.writeString(1, getName(), "");
        if (getFont() != null) {
            s.writeShort(2);
            s.writeString(getFont().getFontName());
            s.writeInt(getFont().getStyle());
            s.writeInt(getFont().getSize());
        }
        if (getBKColor() != null) {
            s.writeShort(3);
            s.writeInt(getBKColor().getRGB());
        }
        if (getColor() != null) {
            s.writeShort(4);
            s.writeInt(getColor().getRGB());
        }
        s.writeBoolean(5, isLine(), false);
        s.writeBoolean(6, isDeleteLine(), false);
        s.writeInt(7, getDeleteLineType(), 0);
        s.writeInt(8, getLineCount(), 0);
        //字位置
        //s.writeString(9, getPosition(), "");
        s.writeShort( -1);
    }

    /**
     * 读对象
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s) throws IOException {
        int id = s.readShort();
        while (id > 0) {
            switch (id) {
            case 1:
                setName(s.readString());
                break;
            case 2:
                setFont(new Font(s.readString(), s.readInt(), s.readInt()));
                break;
            case 3:
                setBKColor(new Color(s.readInt()));
                break;
            case 4:
                setColor(new Color(s.readInt()));
                break;
            case 5:
                setIsLine(s.readBoolean());
                break;
            case 6:
                setIsDeleteLine(s.readBoolean());
                break;
            case 7:
                setDeleteLineType(s.readInt());
                break;
            case 8:
                setLineCount(s.readInt());
                break;
                //加入字位置
                /**case 9:
                    //System.out.println("读s==="+s.readString());
                    setPosition(s.readString());
                    break;**/

            }
            id = s.readShort();
        }
    }

    /**
     * 修改位置
     * @param s String
     * @return DStyle
     */
    public DStyle modifyPosition(String s) {
        DStyle style = copy();
        Font f = getFont();
        //原字体位置
        /** setOldPosition(style.getPosition());
         //准备改变的位置
         setPosition(s);
         //System.out.println(" getOldPosition "+getOldPosition());
         //System.out.println(" getPosition "+getPosition());

         //原位置与现位置一样；

         //现改成普通
         if(getPosition().equals(TPositionCombo.NORMAL_TEXT)){
             //System.out.println("getPosition() is normal");
             style.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+4));
         //现改成上标
         }else if(getPosition().equals(TPositionCombo.SUP_TEXT)){
              //System.out.println("getPosition() is sup");
         style.setFont(new Font(f.getName(), f.getStyle(), f.getSize()-4));
         //现改成下标
         }else if(getPosition().equals(TPositionCombo.SUB_TEXT)){
             style.setFont(new Font(f.getName(), f.getStyle(), f.getSize()-4));
         }**/

        return getManager().getStyle(style);
    }

    /**
     *
     * @param position String
     */
    /** public void setPosition(String position) {
         this.position = position;
     }

     public String getPosition() {
         return position;
     }**/


    public String toString() {
        return getFont().toString();
    }

    /**
        public String getOldPosition() {
            return oldPosition;
        }


        public void setOldPosition(String oldPosition) {
            if(oldPosition==null){
                oldPosition=TPositionCombo.NORMAL_TEXT;
            }
            this.oldPosition = oldPosition;
        }**/


}
