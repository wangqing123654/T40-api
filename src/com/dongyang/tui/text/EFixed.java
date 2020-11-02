package com.dongyang.tui.text;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.ui.base.TWordBase;
import com.dongyang.wcomponent.util.TiString;

import java.util.List;
import com.dongyang.manager.TIOM_FileServer;

/**
 *
 * <p>Title: �̶��ı�</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.8.20
 * @author whao 2013~
 * @version 1.0
 */
public class EFixed extends EText {

    /**
     * ������
     */
    private String groupName;
    /**
     * ������
     */
    String microName = "";
    /**
     * �Ƿ�����Ϊ��
     */
    private boolean allowNull = true;
    /**
     * �Ƿ�����Ϊ��
     */
    private boolean isDataElements = false;
    /**
     * ����
     */
    private String name = "";
    /**
     * ��һ���������
     */
    private EFixed nextFixed;
    /**
     * ��һ���������
     */
    private EFixed previousFixed;
    /**
     * ��һ��ѡ��
     */
    private boolean isSelectOne;
    /**
     * ��������
     */
    private String actionType = "";
    /**
     * ץȡ�ļ���
     */
    private String tryFileName = "";
    /**
     * ץȡ�ֶ�
     */
    private String tryName = "";
    /**
     * ��Ԫ������
     */
    private boolean isElementContent = false;

    /**
     *
     */
    private boolean locked = false;

    /**
     * ��ʱ�������
     */
    private boolean insert=false;
    /**
     *���ֱ��
     *��ͨ 0
     *�ϱ� 1
     *�±� 2
     */
    //private int script=0;

    /**
     * �Ƿ�Ϊ���ʽ
     */
    private boolean isExpression = false;

    /**
     * ���ʽ����
     */
    private String expressionDesc = null;

    /**
     * �ϱ��
     */
    private String scriptUp = null;

    /**
     * �±��
     */
    private String scriptDown = null;
    
    /**
     * 
     */
    private boolean likeQuery = false;
    
    /**
     * �Ƿ񱣴����ݿ�
     */
    private boolean isSaveDB= false;
    
    /**
     * ��������ʾ��Ϣ
     */
    private String  tip= "";
    
    /**
     * ������
     */
    String microMethod = "";

    /**
     * ������
     * @param panel EPanel
     */
    public EFixed(EPanel panel) {
        super(panel);
    }

    /**
     * ��������
     * @param name String
     */
    public void setName(String name) {
        if (name == null) {
            name = "";
        }
        this.name = name;
        EFixed fixed = getPreviousFixed();
        while (fixed != null) {
            fixed.name = name;
            fixed = fixed.getPreviousFixed();
        }
        fixed = getNextFixed();
        while (fixed != null) {
            fixed.name = name;
            fixed = fixed.getNextFixed();
        }
    }

    /**
     * �õ�����
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text) {
        EFixed fixed = uniteAll();
        if (fixed == null) {
            return;
        }
        if (text == null) {
            text = "";
        }
        fixed.setString(text);
        fixed.setFocus(text.length() - 1);
    }

    /**
     * �õ��ı�
     * @return String
     */
    public String getText() {
        StringBuffer sb = new StringBuffer();
        EFixed fixed = getHeadFixed();
        while (fixed != null) {
            sb.append(fixed.getString());
            fixed = fixed.getNextFixed();
        }
        return sb.toString();
    }

    /**
     * ������һ���������
     * @param nextFixed EFixed
     */
    public void setNextFixed(EFixed nextFixed) {
        this.nextFixed = nextFixed;
    }

    /**
     * �õ���һ���������
     * @return EFixed
     */
    public EFixed getNextFixed() {
        return nextFixed;
    }

    /**
     * �Ƿ������һ���̶��ı�
     * @return boolean
     */
    public boolean hasNextFixed() {
        return getNextFixed() != null;
    }

    /**
     * ������һ���������
     * @param previousFixed EFixed
     */
    public void setPreviousFixed(EFixed previousFixed) {
        this.previousFixed = previousFixed;
    }

    /**
     * �õ���һ���������
     * @return EFixed
     */
    public EFixed getPreviousFixed() {
        return previousFixed;
    }

    /**
     * �Ƿ������һ���̶��ı�
     * @return boolean
     */
    public boolean hasPreviousFixed() {
        return getPreviousFixed() != null;
    }

    /**
     * ����
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g, int x, int y, int width, int height) {
        //int position=0;
        //���±�
        /** if(getScript()==2){
             position-=4;
             y+=4;
         //���ϱ�
         }
         else if(getScript()==1){
            position-=4;
            y-=4;
         }**/
        //super.paint(g, x, y, width, height,position);
        super.paint(g, x, y, width, height);

        //
        this.paintExpressionBack(g, x, y, width, height);

        //
        this.paintScript(g, x, y);
    }

    /**
     * ���ƹ�ʽ�Ľ��㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    private void paintExpressionBack(Graphics g,int x,int y,int width,int height){

    	if( isExpression ){

        	//��������ʾ
        	if( !isPreview() ){
        		paintSuffix(g,x + width - 2,y + height);
        	}
    	}

    }

    /**
     * ������ɫ�±�
     * @param g Graphics
     * @param x int
     * @param y int
     */
    private void paintSuffix(Graphics g,int x,int y){

        g.setColor(new Color(0,0,255));
        g.drawLine(x - 2,y,x + 2,y);
        g.drawLine(x - 1,y + 1,x + 1,y + 1);
        g.drawLine(x - 1,y + 2,x + 1,y + 2);
        g.drawLine(x,y + 3,x,y + 3);

       /*
         g.setFont(new Font(null,0,10));
         g.drawString("2", x+2, y-14);
         g.drawString("2", x+2, y+2);
       */
    }

	/**
	 * �������±�
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	private void paintScript(Graphics g, int x, int y) {
		// System.out.println("-------����paintScript�������������±�-------");
		//
		/*
		 * Font ef = this.getStyle().copy().getFont(); ef.deriveFont(
		 * ef.getSize()/4 ); g.setFont(ef);
		 */
		//System.out.println("==style-----==" + this.getStyle());
		//System.out.println("==Graphics-----==" + g.toString());
		DStyle ds = this.getStyle();
		if (this.getStyle() != null) {
			Font ef = ds.copy().getFont();
			Font f = new Font(ef.getFamily(), ef.getStyle(), (int) (ef
					.getSize() * 0.7));
			g.setFont(f);

			//
			String up = this.getScriptUp();
			String down = this.getScriptDown();
			int sx = x + this.getWidth() + 3;
			int sh = y + this.getHeight();
			if (TiString.isNotEmpty(up)) {
				// 14
				this.paintScriptStr(g, sx, sh - 7, up);
			}
			if (TiString.isNotEmpty(down)) {
				// 10 5
				this.paintScriptStr(g, sx, sh + 1, down);
			}

		}
	}
    
    /**
     * ��ӡ  ����
     * @param g Graphics
     * @param x int
     * @param y int
     */
	public void print(Graphics g, int x, int y) {
		// System.out.println("----------EFixed ��д��ӡ����------------------");
		DStyle style = getStyle();
		if (style != null) {
			// System.out.println("-------getShowString()---------"+getShowString());

			int textX = x + getShowLeftW();
			style.print(g, getShowString(), textX, y, getWidth(), getHeight(),
					this);
		}
		// 
		DStyle ds = this.getStyle();
		if (ds != null) {
			Font ef = ds.copy().getFont();

			Font f = new Font(ef.getFamily(), ef.getStyle(), (int) (ef
					.getSize() * 0.7));
			g.setFont(f);
			//
			String up = this.getScriptUp();
			String down = this.getScriptDown();
			int sx = x + this.getWidth() + 1;
			int sh = y + this.getHeight();
			//
			if (TiString.isNotEmpty(up)) {
				this.paintScriptStr(g, sx, sh - 7, up);
			}
			//
			if (TiString.isNotEmpty(down)) {
				// 10
				this.paintScriptStr(g, sx, sh + 1, down);
			}
		}

		// ��������
		paintOther(g, x, y, getWidth(), getHeight());

	}
    
    /**
     *
     * @param g
     * @param x
     * @param y
     * @param str
     */
    private void paintScriptStr(Graphics g,int x,int y,String str){
    	g.drawString(str, x, y);

    }

    /**
     * ���ƽ��㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFocusBack(Graphics g, int x, int y, int width, int height) {
        if (getViewManager().isFixedDebug()) {
            g.setColor(new Color(255, 0, 0));
            g.drawRect(x, y, width, height);
        }
        if (!isPreview() && isFocus()) {
            paintFixedFocusBack(g, x, y, width, height);
        }
    }

    /**
     * ���ƹ̶��ı����㱳��
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFixedFocusBack(Graphics g, int x, int y, int width,
                                    int height) {
        if (isSelectOne()) {
            g.setColor(new Color(168, 168, 168));
        } else {
            g.setColor(new Color(240, 240, 240));
        }
        g.fillRect(x, y, width, height);
    }

    /**
     * �ϲ�ȫ��ͬ����
     * @return EFixed
     */
    public EFixed uniteAll() {
        EFixed fixed = getHeadFixed();
        while (fixed.getNextFixed() != null) {
            fixed.unite(fixed.getNextFixed());
        }
        return fixed;
    }

    /**
     * �ϲ�
     * @param block IBlock
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean unite(IBlock block) {
        if (block == null) {
            return false;
        }
        if (!(block instanceof EFixed)) {
            return false;
        }
        EFixed fixed = (EFixed) block;
        if (getPreviousFixed() != null && getPreviousFixed() == fixed) {
            fixed.setEnd(getEnd());
            if (getNextFixed() != null) {
                fixed.setNextFixed(getNextFixed());
                getNextFixed().setPreviousFixed(fixed);
            } else {
                fixed.setNextFixed(null);
            }
            removeThis();
            return true;
        }
        if (getNextFixed() != null && getNextFixed() == fixed) {
            setEnd(fixed.getEnd());
            if (fixed.getNextFixed() != null) {
                setNextFixed(fixed.getNextFixed());
                fixed.getNextFixed().setPreviousFixed(this);
            } else {
                setNextFixed(null);
            }
            fixed.removeThis();
            return true;
        }
        return false;
    }

    /**
     * ճ���ַ�
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c) {
        int index = getFocusIndex();
        //�������λ
        if (index == 0 && getPreviousFixed() == null) {
            //���ǰһ������Text����һ��
            EText previousText = getPreviousText();
            if (previousText == null || previousText instanceof EFixed) {
                previousText = getPanel().newText(findIndex());
                previousText.setBlock(getStart(), getStart());
            }
            setFocus(previousText, previousText.getLength());
            previousText.pasteChar(c);
            setFocus(this, 0);
            return true;
        }
        //�������ĩβ
        if (index == getLength() && getNextFixed() == null) {
            //������û��Text����һ��
            EText nextText = getNextText();
            if (nextText == null || nextText instanceof EFixed) {
                nextText = getPanel().newText(findIndex() + 1);
                nextText.setBlock(getEnd(), getEnd());
            }
            setFocus(nextText, 0);
            return nextText.pasteChar(c);
        }
        //�̶��ı������ܷ�༭
        if (!canEdit(index, 1)) {
            return false;
        }
        //���Ը��ַ��ܷ����
        if (!canInputChar(index, c)) {
            return false;
        }
        return super.pasteChar(c);
    }

    /**
     * �̶��ı������ܷ�༭
     * @param index int
     * @param type int
     *  1 �����ַ�
     *  2 ��ǰɾ��
     *  3 ���ɾ��
     * @return boolean true �ܱ༭ false ���ܱ༭
     */
    public boolean canEdit(int index, int type) {
        return false;
    }

    /**
     * �ܷ�¼����ַ�
     * @param index int
     * @param c char
     * @return boolean
     */
    public boolean canInputChar(int index, char c) {
        return true;
    }

    /**
     * ��ǰɾ��
     * @return boolean
     */
    public boolean backspaceChar() {
        int index = getFocusIndex();
        if (index != 0) {
            //�̶��ı������ܷ���ǰɾ��
            if (canEdit(index, 2)) {
                return super.backspaceChar();
            }
            setFocusIndex(getFocusIndex() - 1);
            return true;
        }
        return super.backspaceChar();
    }

    /**
     * ���ɾ��
     * @return boolean
     */
    public boolean deleteChar() {
        int index = getFocusIndex();
        if (index != getLength()) {
            //�̶��ı������ܷ����ɾ��
            if (canEdit(index, 3)) {
                return super.deleteChar();
            }
            setFocusIndex(getFocusIndex() + 1);
            return true;
        }
        return super.deleteChar();
    }

    /**
     * �س��¼�
     * @return boolean
     */
    public boolean onEnter() {
        int index = getFocusIndex();
        if (index == 0 || index == getLength()) {
            return super.onEnter();
        }
        return true;
    }

    /**
     * �õ��ָ������
     * @param index int
     * @return EText
     */
    public EText getSeparateNewText(int index) {
        EFixed fixed = getNewObject(index);
        if (getNextFixed() != null) {
            fixed.setNextFixed(getNextFixed());
            getNextFixed().setPreviousFixed(fixed);
        }
        fixed.name = getName();
        fixed.setPreviousFixed(this);
        setNextFixed(fixed);
        return fixed;
    }

    /**
     * �õ��¶���
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index) {
        return getPanel().newFixed(index);
    }

    /**
     * ��ǰ����
     * @return boolean
     */
    public boolean isFocus() {
        EComponent com = getFocus();
        if (!(com instanceof EFixed)) {
            return false;
        }
        EFixed fixed = (EFixed) com;
        if (fixed == this) {
            return true;
        }
        EFixed n = fixed.getPreviousFixed();
        while (n != null) {
            if (n == this) {
                return true;
            }
            n = n.getPreviousFixed();
        }
        n = fixed.getNextFixed();
        while (n != null) {
            if (n == this) {
                return true;
            }
            n = n.getNextFixed();
        }
        return false;
    }

    /**
     * �õ�����λ��
     * @return String
     */
    public String getIndexString() {
        EPanel panel = getPanel();
        if (panel == null) {
            return "Parent:Null";
        }
        return panel.getIndexString() + ",EFixed:" + findIndex();
    }

    public String toString() {
        return "<EFixed name=" + getName() + ",start=" + getStart() + ",end=" +
                getEnd() + ",s=" + getString() + ",position=" + getPosition() +
                ",index=" + getIndexString() +
                ",row=" + getRowID()+
                ",p=" +
                (getPreviousFixed() != null ?
                 "[" + getPreviousFixed().getIndexString() + "]" : "null") +
                ",n=" +
                (getNextFixed() != null ?
                 "[" + getNextFixed().getIndexString() + "]" : "null") + ">";
    }

    /**
     * д��������
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s) throws IOException {
        super.writeObjectAttribute(s);
        s.writeString(50, getName(), "");
        s.writeBoolean(51, getPreviousFixed() != null, false);
        s.writeString(52, getActionType(), "");
        s.writeString(53, getTryFileName(), "");
        s.writeString(54, getTryName(), "");
        s.writeBoolean(55, isAllowNull(), !isAllowNull());
        s.writeBoolean(56, this.isIsDataElements(), !isIsDataElements());
        s.writeString(57, getMicroName(), "");
        s.writeString(58, getGroupName(), "");
        s.writeBoolean(59, isIsElementContent(), false);
        s.writeBoolean(60, isLocked(), false);
        s.writeBoolean(61, this.isInsert(), false);

        s.writeBoolean(62, this.isExpression(), false);
        s.writeString(63, this.getExpressionDesc(),"");

        s.writeString(64, this.getScriptUp(),"");
        s.writeString(65, this.getScriptDown(),"");
        //����ģ����ѯ
        s.writeBoolean(66, this.isLikeQuery(),false);
        s.writeString(67, this.getMicroMethod(), "");
        //�����Ƿ񱣴����ݿ��־
        s.writeBoolean(68, this.isSaveDB(),false);
        //
        s.writeString(69, this.getTip(),"");
    }

    /**
     * ����������
     * @param id int
     * @param s DataInputStream
     * @return boolean
     * @throws IOException
     */
    public boolean readObjectAttribute(int id, DataInputStream s) throws
            IOException {
        if (super.readObjectAttribute(id, s)) {
            return true;
        }
        switch (id) {
        case 50:
            setName(s.readString());
            return true;
        case 51:
            if (s.readBoolean()) {
                EComponent com = getPreviousComponent();
                if (com != null && com instanceof EFixed) {
                    EFixed fixed = (EFixed) com;
                    setPreviousFixed(fixed);
                    fixed.setNextFixed(this);
                }
            }
            return true;
        case 52:
            setActionType(s.readString());
            return true;
        case 53:
            setTryFileName(s.readString());
            return true;
        case 54:
            setTryName(s.readString());
            return true;
        case 55:
            setAllowNull(s.readBoolean());
            return true;
        case 56:
            setDataElements(s.readBoolean());
            return true;
        case 57:
            setMicroName(s.readString());
            return true;
        case 58:
            setGroupName(s.readString());
            return true;
        case 59:
            setElementContent(s.readBoolean());
            return true;
        case 60:
            this.setLocked(s.readBoolean());
            return true;
        case 61:
            this.setInsert(s.readBoolean());
            return true;
        case 62:
            this.setExpression(s.readBoolean());

            this.doAddExpression();

            return true;

        case 63:
            this.setExpressionDesc(s.readString());
            return true;

        case 64:
            this.setScriptUp(s.readString());
            return true;

        case 65:
            this.setScriptDown(s.readString());
            return true;
            
        case 66:
            this.setLikeQuery(s.readBoolean());
            return true;
            
        case 67:
            this.setMicroMethod(s.readString());
            return true;
        case 68:
            this.setSaveDB(s.readBoolean());
            return true;
        case 69:
            this.setTip(s.readString());
            return true;          
        }



        return false;
    }

    /**
     *
     */
    private void doAddExpression(){
        if( this.isExpression() ){
       	 TWordBase twb = (TWordBase) this.getPageManager().getUI().getParentTCBase();
         twb.getExpressionList().add(this);
       }
    }

    /**
     * ˫��
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX, int mouseY) {
        return false;
    }

    /**
     * ���ԶԻ���
     */
    public void openPropertyDialog() {
        EFixed fixed = getHeadFixed();
        String value = (String) getPM().getUI().openDialog(
                "%ROOT%\\config\\database\\FixedEdit.x", fixed);
        if (value == null || !"OK".equals(value)) {
            return;
        }
        fixed.getPM().getFileManager().update();
    }

    /**
     * ɾ���Լ�
     */
    public void deleteFixed() {
        EFixed fixed = uniteAll();
        if (fixed == null) {
            return;
        }
        fixed.setString("");
        int i = findIndex(fixed);
        super.removeThis();
        if (getPanel().size() == 0) {
            EText text = getPanel().newText();
            setFocus(text, 0);
            return;
        }
        //�ϲ���ͬ��EText
        if (i < getPanel().size()) {
            IBlock b = getPanel().get(i);
            if (b instanceof EText) {
                EText text = (EText) b;
                text.setFocus();
                text.setFocusIndex(0);
                text.linkText();
            }
        }

    }

    /**
     * ������һ��Text
     */
    public void linkText() {
    }

    /**
     * �̶��ı���ֹ��ѡɾ������
     * @param start int ��ʼλ��
     * @param end int ����λ��
     * @return boolean true ��ֹ�������� false �����׼����
     */
    public boolean onDeleteActionIO(int start, int end) {
        return true;
    }

    /**
     * �õ���������
     * @return int
     */
    public int getObjectType() {
        return FIXED_TYPE;
    }

    /**
     * ���Ҷ���
     * @param name String ����
     * @param type int ����
     * @return EComponent
     */
    public EComponent findObject(String name, int type) {
        if (name == null || name.length() == 0) {
            return super.findObject(name, type);
        }
        if (name.equals(getName()) && getObjectType() == type) {
            return this;
        }
        return null;
    }

    /**
     * ���˶���
     * @param list List
     * @param name String
     * @param type int
     */
    public void filterObject(List list, String name, int type) {
        if (name == null || list == null) {
            return;
        }
        if (type == FIXED_ACTION_TYPE) {
            if (name.equals(getActionType())) {
                if (list.indexOf(this) == -1) {
                    list.add(this);
                }
            }
        }
    }

    /**
     * ��ѯ��
     * @param groupName String
     * @return EComponent
     */
    public EComponent findGroup(String groupName) {
        if (groupName == null || groupName.length() == 0) {
            return null;
        }
        return null;
    }

    /**
     * �õ����һ���̶��ı�
     * @return EFixed
     */
    public EFixed getEndFixed() {
        if (hasNextFixed()) {
            return getNextFixed().getEndFixed();
        }
        return this;
    }

    /**
     * �õ���һ���̶��ı�
     * @return EFixed
     */
    public EFixed getHeadFixed() {
        if (hasPreviousFixed()) {
            return getPreviousFixed().getHeadFixed();
        }
        return this;
    }

    /**
     * �õ���ֵ
     * @return String
     */
    public String getBlockValue() {
        return getText();
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock() {
        EFixed fixed = getHeadFixed();
        return fixed.getPreviousBlock();
    }

    /**
     * �õ���һ�����
     * @return IBlock
     */
    public IBlock getNextTrueBlock() {
        EFixed fixed = getEndFixed();
        return fixed.getNextBlock();
    }

    /**
     * ճ���ַ���
     * @param index int
     * @param text String
     * @return boolean
     */
    public boolean pasteString(int index, String text,DStyle defineStyle) {
        if (index == 0 && getHeadFixed() == this) {
            if (findIndex() == 0) {
                return getPanel().newText(0).pasteString(index, text,defineStyle);
            }
            IBlock block = getPreviousBlock();
            if (block instanceof EText && (!(block instanceof EFixed))) {
                EText t = (EText) block;
                return t.pasteString(t.getLength(), text,defineStyle);
            }
            EText t = getPanel().newText(findIndex());
            t.setStart(getStart());
            t.setEnd(getStart());
            return t.pasteString(index, text,defineStyle);
        }
        if (index == getLength() && getNextFixed() == null) {
            IBlock block = getNextBlock();
            if (block instanceof EText && (!(block instanceof EFixed))) {
                EText t = (EText) block;
                return t.pasteString(0, text,defineStyle);
            }
            EText t = getPanel().newText(findIndex() + 1);
            t.setStart(getEnd());
            t.setEnd(getEnd());
            return t.pasteString(0, text,defineStyle);
        }
        return false;
    }

    /**
     * �Ƿ���Tab����
     * @return boolean
     */
    public boolean isTabFocus() {
        return false;
    }

    /**
     * ����Tab����
     */
    public void setTabFocus() {
    }

    /**
     * ����Tab
     * @param flg boolean true ���� false ����
     * @return boolean
     */
    public boolean onFocusToTab(boolean flg) {
        int focusIndex = getFocusIndex();
        if (getPreviousFixed() == null && focusIndex == 0) {
            return false;
        }
        if (getNextFixed() == null && focusIndex > getLength() - 1) {
            return false;
        }
        EFixed fixed = flg ? getNextTabCom() : getPreviousTabCom();
        if (fixed == null) {
            return false;
        }
        fixed.setTabFocus();
        return true;
    }

    /**
     * �õ���һ��Tab��ת�ؼ�
     * @return EFixed
     */
    public EFixed getNextTabCom() {
        IBlock block = getNextTrueBlock();
        if (block == null) {
            EPanel panel = getPanel().getNextTruePanel();
            if (panel == null || panel.size() == 0) {
                return null;
            }
            block = panel.get(0);
        } while (block != null) {
            //�Ƿ��ǽ�������
            if (isTabFocus(block)) {
                return (EFixed) block;
            }
            IBlock nextBlock = block.getNextTrueBlock();
            if (nextBlock != null) {
                block = nextBlock;
                continue;
            }
            EPanel panel = block.getPanel().getNextTruePanel();
            if (panel == null || panel.size() == 0) {
                return null;
            }
            block = panel.get(0);
        }
        return null;
    }

    /**
     * �Ƿ���Tab�������
     * @param block IBlock
     * @return boolean
     */
    public boolean isTabFocus(IBlock block) {
        if (!(block instanceof EFixed)) {
            return false;
        }
        EFixed fixed = (EFixed) block;
        return fixed.isTabFocus();
    }

    /**
     * �õ���һ��Tab��ת�ؼ�
     * @return EFixed
     */
    public EFixed getPreviousTabCom() {
        IBlock block = getPreviousTrueBlock();
        if (block == null) {
            EPanel panel = getPanel().getPreviousTruePanel();
            if (panel == null || panel.size() == 0) {
                return null;
            }
            block = panel.get(panel.size() - 1);
        } while (block != null) {
            //�Ƿ��ǽ�������
            if (isTabFocus(block)) {
                return (EFixed) block;
            }
            IBlock nextBlock = block.getPreviousTrueBlock();
            if (nextBlock != null) {
                block = nextBlock;
                continue;
            }
            EPanel panel = block.getPanel().getPreviousTruePanel();
            if (panel == null || panel.size() == 0) {
                return null;
            }
            block = panel.get(panel.size() - 1);
        }
        return null;
    }

    /**
     * ��¡
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel) {
        EFixed fixed = new EFixed(panel);
        fixed.setStart(panel.getDString().size());
        fixed.setEnd(getStart());
        fixed.setString(getString());
        fixed.setKeyIndex(getKeyIndex());
        fixed.setDeleteFlg(isDeleteFlg());
        fixed.setStyleOther(getStyle());
        fixed.setGroupName(getGroupName());
        fixed.setMicroName(getMicroName());
        fixed.setDataElements(isIsDataElements());
        fixed.setAllowNull(isAllowNull());
        fixed.setElementContent(isIsElementContent());
        fixed.setLocked(this.isLocked());
        fixed.setInsert(this.isInsert());
        fixed.setScriptDown(this.getScriptDown());
        fixed.setScriptUp(this.getScriptUp());
        fixed.name = name;
        fixed.setTip(this.getTip());
        return fixed;
    }

    /**
     * ���õ�һ��ѡ��
     * @param isSelectOne boolean
     */
    public void setSelectOne(boolean isSelectOne) {
        this.isSelectOne = isSelectOne;
    }

    /**
     * �Ƿ��һ��ѡ��
     * @return boolean
     */
    public boolean isSelectOne() {
        return isSelectOne;
    }

    /**
     * ����ȫ����һ��ѡ��
     * @param flg boolean
     */
    public void setSelectOneAll(boolean flg) {
        ENumberChoose c = (ENumberChoose) getHeadFixed();
        while (c != null) {
            c.setSelectOne(flg);
            c = (ENumberChoose) c.getNextFixed();
        }
    }

    /**
     * ���ö�������
     * @param actionType String
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * �õ���������
     * @return String
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * ����ץȡ�ļ�
     * @param tryFileName String
     */
    public void setTryFileName(String tryFileName) {
        this.tryFileName = tryFileName;
    }

    /**
     * �õ�ץȡ�ļ�
     * @return String
     */
    public String getTryFileName() {
        return tryFileName;
    }

    /**
     * ����ץȡ�ֶ�
     * @param tryName String
     */
    public void setTryName(String tryName) {
        this.tryName = tryName;
    }

    /**
     * �õ�ץȡ�ֶ�
     * @return String
     */
    public String getTryName() {
        return tryName;
    }

    /**
     * ˢ������
     * @param action EAction
     */
    public void resetData(EAction action) {
        if (action.getType() == EAction.FIXED_TRY_RESET) {
            if (tryReset()) {
                setModify(true);
            }
        }
    }

    private String findFileName(String s[], String name, String caseNo) {
        String f = "";
        String fSeq = "";
        String sSeq = "";

        for (int i = 0; i < s.length; i++) {
            if (!s[i].startsWith(caseNo + "_" + name + "_")) {
                continue;
            }
            if (f.length() == 0) {
                f = s[i];
                continue;
            }
            //��׺���
            fSeq = f.substring(f.lastIndexOf("_") + 1, f.lastIndexOf("."));
            sSeq = s[i].substring(s[i].lastIndexOf("_") + 1,
                                  s[i].lastIndexOf("."));
            //��׺����Ƿ������������
            try {
                Integer.parseInt(fSeq);
            } catch (java.lang.NumberFormatException e) {
                f = s[i];
                continue;
            }
            try {
                Integer.parseInt(sSeq);
            } catch (java.lang.NumberFormatException e) {
                continue;
            }
            //

            if (Integer.parseInt(fSeq) < Integer.parseInt(sSeq)) {
                f = s[i];
            }

            //����Ƚϰ��ַ��Ƚ������⣬
            /**if (f.compareTo(s[i]) < 0) {
                f = s[i];
                         }**/
        }

        return f;
    }
    
    
    /**
     * ģ����ѯ���ö�Ӧ���ļ�
     * @return
     */
    private String likeFindFileName(String s[], String name, String caseNo){
        String f = "";
        String fSeq = "";
        String sSeq = "";
        for (int i = 0; i < s.length; i++) {
        	//System.out.println("--s[i]�ļ�===["+i+"]==="+s[i]);
        	//���ɵ���Ч�ļ�����
        	if(s[i].split("_").length>3){
        		continue;
        	}
        	//����ͬcaseno������
        	//System.out.println("----------"+caseNo+","+s[i].split("_")[0]);
        	if (!s[i].split("_")[0].equals(caseNo)) {
        		//System.out.println("--����ͬcaseno������--");
                continue;
            }
        	//�����ڣ�������
            if (s[i].indexOf(name)==-1) {
            	//System.out.println("--���������֣�����--");
                continue;
            }else{
            	name=s[i].split("_")[1];
            	//System.out.println("--111name1111--"+name);
            }
            //caseNO_
            //System.out.println("--caseNo�ļ���--"+caseNo + "_" + name + "_");
            if (!s[i].startsWith(caseNo + "_" + name + "_")) {
                continue;
            }
            
            //���� ģ���Ĳ���ģ���ļ�
            if (f.length() == 0) {
                f = s[i];
                continue;
            }
            //
            //��׺���
            fSeq = f.substring(f.lastIndexOf("_") + 1, f.lastIndexOf("."));
            sSeq = s[i].substring(s[i].lastIndexOf("_") + 1,
                                  s[i].lastIndexOf("."));
            //��׺����Ƿ������������
            try {
                Integer.parseInt(fSeq);
            } catch (java.lang.NumberFormatException e) {
                f = s[i];
                continue;
            }
            //
            try {
                Integer.parseInt(sSeq);
            } catch (java.lang.NumberFormatException e) {
                continue;
            }
            //

            if (Integer.parseInt(fSeq) < Integer.parseInt(sSeq)) {
                f = s[i];
            }
        }

        return f;
    }

    public boolean tryReset() {
        if (getTryFileName() == null || getTryFileName().length() == 0) {
            return false;
        }
        if (getTryName() == null || getTryName().length() == 0) {
            return false;
        }
        String caseNo = getPM().getCaseNo();
        String mrNo = getPM().getMrNo();
        String path = caseNo.substring(0, 2) + "\\" + caseNo.substring(2, 4) +
                      "\\" + mrNo;
        //
        /*System.out.println("========Efixed fileName1111========" +
                           getPM().getFileManager().getEmrDataDir() + "JHW\\" +
                           path);*/
        //
        String fileList[] = TIOM_FileServer.listFile(TIOM_FileServer.getSocket(),
                getPM().getFileManager().getEmrDataDir() + "JHW\\" + path);
        if (fileList == null) {
            return false;
        }
        String fileName="";
        //
        //
        if(this.isLikeQuery()){
        	fileName = likeFindFileName(fileList, getTryFileName(),caseNo);
        }else{
        	fileName = findFileName(fileList, getTryFileName(), caseNo);
        }
        //
        
        PM pm = new PM();
        pm.setUI(getPM().getUI());
        if (!pm.getFileManager().onOpen("JHW\\" + path, fileName, 3, false)) {
            return false;
        }
        //
        System.out.println("========ץȡ�ļ�·��========" +
                           getPM().getFileManager().getEmrDataDir() + "JHW\\" +
                           path);
        System.out.println("========ץȡ�ļ�========" + fileName);
        System.out.println("=====ץȡ������=====" + getTryName());
        //
        ECapture capture = (ECapture) pm.getPageManager().findObject(getTryName(),
                EComponent.CAPTURE_TYPE);
        if (capture == null) {
            return false;
        }
        //System.out.println("��Ԫ��ץȡ==="+this.isIsElementContent());
        //ȡԪ������;
        if (this.isIsElementContent()) {
            List elms = capture.getElements();
            //System.out.println("==elms=="+elms.size());            
            //
            setText(" ");
            this.onFocusToRight();
            //
            //System.out.println("����==="+this.getStyle().getFont().getFamily()+"�ֺ�===="+this.getStyle().getFont().getSize());
            //ȡ�õ�ǰ����       ���趨��
            this.getFocusManager().insertComponents(elms,this.getStyle());
            setText("");
			// ���⴦������ �� �ֲ�ʷ ,û���������-
			if (capture.getName().equals("SUB")
					|| capture.getName().equals("OBJ")
					|| capture.getName().equals("EXA_RESULT")
					|| capture.getName().equals("PROPOSAL")) {
				String value = capture.getValue();
				if (value.trim().equals("")) {
					setText("-");
				}
			}
            //
            //elms.setFocus(this.getEnd());
        } else {
            String value = capture.getValue();
            //ץȡ��ֵ�а���(��ץȡ����滻��"";
            setText(this.procString(value));
        }

        return true;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setDataElements(boolean isDataElements) {
        this.isDataElements = isDataElements;
    }

    public boolean isIsDataElements() {
        return isDataElements;
    }


    public void setElementContent(boolean isElementContent) {
        this.isElementContent = isElementContent;
    }

    public boolean isIsElementContent() {
        return isElementContent;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isLocked() {
        return locked;
    }




    /**
     *
     * @param script int
     */
    /**public void setScript(int script) {
        this.script = script;
         }

         public int getScript() {
        return script;
         }**/


    public boolean isInsert() {
		return insert;
	}

	public void setInsert(boolean insert) {
		this.insert = insert;
	}
	/**
     * ���ú�����
     * @param code String
     */
    public void setMicroName(String microName) {
        this.microName = microName;
    }



	/**
     * �õ�������
     * @return String
     */
    public String getMicroName() {
        return microName;
    }

    /**
     * ����������
     * @param code String
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * ����������
     * @return String
     */
    public String getGroupName() {
        return groupName;
    }
    /**
     * 
     * @return
     */
    public String getMicroMethod() {
		return microMethod;
	}

	public void setMicroMethod(String microMethod) {
		this.microMethod = microMethod;
	}
	
	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	/**
     * �õ���ʾ�ִ�
     * @return String
     */
    public String getShowString() {
        //System.out.println("=========come in EFixed getShowString==================");
        String strTemp = super.getShowString();
        //System.out.println("=========strTemp=================="+strTemp);

        if (!isPreview()) {
            return strTemp;
        } else {
            if (strTemp.equals("(") || strTemp.equals("(") ||
                strTemp.equals("��") || strTemp.equals("��")) {
                return "";
            }
        }
        return strTemp;
    }

    /**
     * ���������ַ�
     * @param s String
     * @return String
     */
    private String procString(String s) {
        String str = s.replaceAll("\\(|\\)|��|��|��|��", "");
        return str;
    }

    //
	public boolean isExpression() {
		return isExpression;
	}

	public void setExpression(boolean isExpression) {
		this.isExpression = isExpression;
	}

	public String getExpressionDesc() {
		return expressionDesc;
	}

	public void setExpressionDesc(String expressionDesc) {
		this.expressionDesc = expressionDesc;
	}

	public String getScriptUp() {
		return scriptUp;
	}

	public void setScriptUp(String scriptUp) {
		this.scriptUp = scriptUp;
	}

	public String getScriptDown() {
		return scriptDown;
	}

	public void setScriptDown(String scriptDown) {
		this.scriptDown = scriptDown;
	}
		
    public boolean isLikeQuery() {
		return likeQuery;
	}

	public void setLikeQuery(boolean likeQuery) {
		this.likeQuery = likeQuery;
	}

	public boolean isSaveDB() {
		return isSaveDB;
	}

	public void setSaveDB(boolean isSaveDB) {
		this.isSaveDB = isSaveDB;
	}

	/**
     * ���±����ԶԻ���
     */
    public void openMarkDialog() {
        EFixed fixed = getHeadFixed();
        String value = (String) getPM().getUI().openDialog(
                "%ROOT%\\config\\database\\MarkEdit.x", fixed);
        if (value == null || !"OK".equals(value)) {
            return;
        }
        fixed.getPM().getFileManager().update();
    }


}
