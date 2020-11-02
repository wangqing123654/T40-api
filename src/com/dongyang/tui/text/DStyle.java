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
 * <p>Title: ���</p>
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
     * ������
     */
    private MStyle manager;
    /**
     * ����
     */
    private String name;
    /**
     * ����
     */
    private Font font;
    /**
     * ������ʽ
     */
    private FontMetrics fontMetrics;
    /**
     * ������ɫ
     */
    private Color bkColor;
    /**
     * ǰ����ɫ
     */
    private Color color;
    /**
     * �»���
     */
    private boolean isLine;
    /**
     * ɾ����
     */
    private boolean isDeleteLine;
    /**
     * ɾ��������
     */
    private int deleteLineType;
    /**
     * ����
     */
    private int lineCount;
    /**
     * GC���
     */
    private boolean gc;

    /**
     * ����λ��
     */
    //private String position;
    /**
     * ԭ����λ��;
     */
    //private String oldPosition;
    /**
     * ������
     * @param manager MStyle
     */
    public DStyle(MStyle manager) {
        setManager(manager);
    }

    /**
     * ���ù�����
     * @param manager MStyle
     */
    public void setManager(MStyle manager) {
        this.manager = manager;
    }

    /**
     * �õ�������
     * @return MStyle
     */
    public MStyle getManager() {
        return manager;
    }

    /**
     * ��������
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * ��������
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * ��������
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
     * �õ�����
     * @return Font
     */
    public Font getFont() {
        return font;
    }

    /**
     * ���ñ�����ɫ
     * @param bkColor Color
     */
    public void setBKColor(Color bkColor) {
        this.bkColor = bkColor;
    }

    /**
     * �õ�������ɫ
     * @return Color
     */
    public Color getBKColor() {
        return bkColor;
    }

    /**
     * ����ǰ����ɫ
     * @param color Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * �õ�ǰ����ɫ
     * @return Color
     */
    public Color getColor() {
        return color;
    }

    /**
     * �����»���
     * @param isLine boolean
     */
    public void setIsLine(boolean isLine) {

        if( TWord.IsMark ){

        	this.isLine = isLine;
        }else{

            //̩��ר��
            this.isLine = false;
        }
    }

    /**
     * �Ƿ����»���
     * @return boolean
     */
    public boolean isLine() {
        return isLine;

    }

    /**
     * �����Ƿ���ɾ����
     * @param isDeleteLine boolean
     */
    public void setIsDeleteLine(boolean isDeleteLine) {

        if( TWord.IsMark ){

        	this.isDeleteLine = isDeleteLine;
        }else{

            //̩��ר��
            this.isDeleteLine = false;
        }
    }

    /**
     * �Ƿ���ɾ����
     * @return boolean
     */
    public boolean isDeleteLine() {
        return isDeleteLine;
    }

    /**
     * ����ɾ��������
     * @param deleteLineType int
     */
    public void setDeleteLineType(int deleteLineType) {
        this.deleteLineType = deleteLineType;
    }

    /**
     * �õ�ɾ��������
     * @return int
     */
    public int getDeleteLineType() {
        return deleteLineType;
    }

    /**
     * �õ�������ʽ
     * @return FontMetrics
     */
    public FontMetrics getFontMetrics() {
        return fontMetrics;
    }

    /**
     * �����ߴ�
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
        //���ÿ��
        com.setWidth(stringWidth(s));
        //���ø߶�
        com.setHeight(getFontMetrics().getHeight());
    }

    /**
     * �õ��ַ������
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
     * �õ��ַ����
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
     * ����ָ�λ��
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
                if (c1 == ',' || c1 == '��' || c1 == '.' || c1 == '��' ||
                    c1 == '��' || c1 == '��') {
                    return i > 0 ? (i - 1) : -1;
                }
                return i > 0 ? i : -1;
            }
        }
        return -1;
    }

    /**
     * �õ��ַ�λ��
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
     * �õ����Ƹ߶�
     * @return int
     */
    public int getAscent() {
        if (getFontMetrics() == null) {
            return 0;
        }
        return getFontMetrics().getAscent();
    }

    /**
     * �õ�����߶�
     * @return int
     */
    public int getFontHeight() {
        if (getFontMetrics() == null) {
            return 0;
        }
        return getFontMetrics().getHeight();
    }

    /**
     * ��������
     * @param lineCount int
     */
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }

    /**
     * �õ�����
     * @return int
     */
    public int getLineCount() {
        return lineCount;
    }

    /**
     * ����
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
            //���Ʊ���
            paintBackground(g, s, x, y, width, height, text);
        }
        //��������
        paintText(g, s, x, y, width, height, text);
        //����ǰ��
        drawFront(g, x, y, width, height, text);
    }

    /**
     * ֧�����±���Ʒ���  add by lx
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
            //���Ʊ���
            paintBackground(g, s, x, y, width, height, text);
        }
        //��������
        paintText(g, s, x, y, width, height, text, position);
        //����ǰ��
        drawFront(g, x, y, width, height, text);
    }

    /**
     * ����ǰ��
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
     * ��ӡ
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
     * ���Ʊ���
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
            //$$============modified by lx 2011-12-30 ֧�ֲ���������� start============$$//
            //���
            if (text.isPreview()) {
                end = (int) (stringWidth(s) *zoom + 0.5);
            }else{
                end = (int) (stringWidth(s.substring(startPoint, endPoint)) *zoom + 0.5);
            }
            //$$============modified by lx 2011-12-30 ֧�ֲ���������� end============$$//

            g.fillRect(x + start, y, end, height);
            //������
            g.setColor(new Color(255, 0, 0));
            g.drawLine(x + start, y, x + start, y + height);
            g.drawLine(x + start + end - 1, y, x + start + end - 1,
                       y + height);
            g.setColor(new Color(0, 0, 0));

        }
    }

    /**
     * ��������
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
     * ��������
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
     * ���±��������֧��
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
     * ����һ����ʽ
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
     * ����GC���
     * @param gc boolean
     */
    public void setGC(boolean gc) {
        this.gc = gc;
    }

    /**
     * �õ�GC���
     * @return boolean
     */
    public boolean getGC() {
        return gc;
    }

    /**
     * �޸���ɫ
     * @param c Color
     * @return DStyle
     */
    public DStyle modifyColor(Color c) {
        DStyle style = copy();
        style.setColor(c);
        return getManager().getStyle(style);
    }

    /**
     * �޸�����
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
     * �޸�����ߴ�
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
     * �޸�����
     * @param font Font
     * @return DStyle
     */
    public DStyle modifyFont(Font font) {
        DStyle style = copy();
        style.setFont(font);
        return getManager().getStyle(style);
    }

    /**
     * �޸��������
     * @param bold boolean true ���� false �Ǵ���
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
     * �޸�����б��
     * @param italic boolean true б�� false ��б��
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
     * �޸��»���
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
     * �޸�ɾ����
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
     * �����Լ���λ��
     * @return int
     */
    public int findIndex() {
        return findIndex(this);
    }

    /**
     * �������λ��
     * @param com EComponent
     * @return int
     */
    public int findIndex(DStyle com) {
        return getManager().indexOf(com);
    }

    /**
     * д����
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
        //��λ��
        //s.writeString(9, getPosition(), "");
        s.writeShort( -1);
    }

    /**
     * ������
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
                //������λ��
                /**case 9:
                    //System.out.println("��s==="+s.readString());
                    setPosition(s.readString());
                    break;**/

            }
            id = s.readShort();
        }
    }

    /**
     * �޸�λ��
     * @param s String
     * @return DStyle
     */
    public DStyle modifyPosition(String s) {
        DStyle style = copy();
        Font f = getFont();
        //ԭ����λ��
        /** setOldPosition(style.getPosition());
         //׼���ı��λ��
         setPosition(s);
         //System.out.println(" getOldPosition "+getOldPosition());
         //System.out.println(" getPosition "+getPosition());

         //ԭλ������λ��һ����

         //�ָĳ���ͨ
         if(getPosition().equals(TPositionCombo.NORMAL_TEXT)){
             //System.out.println("getPosition() is normal");
             style.setFont(new Font(f.getName(), f.getStyle(), f.getSize()+4));
         //�ָĳ��ϱ�
         }else if(getPosition().equals(TPositionCombo.SUP_TEXT)){
              //System.out.println("getPosition() is sup");
         style.setFont(new Font(f.getName(), f.getStyle(), f.getSize()-4));
         //�ָĳ��±�
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
