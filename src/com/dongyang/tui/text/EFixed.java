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
 * <p>Title: 固定文本</p>
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
     * 组名称
     */
    private String groupName;
    /**
     * 宏名称
     */
    String microName = "";
    /**
     * 是否允许为空
     */
    private boolean allowNull = true;
    /**
     * 是否允许为空
     */
    private boolean isDataElements = false;
    /**
     * 名称
     */
    private String name = "";
    /**
     * 下一个连接组件
     */
    private EFixed nextFixed;
    /**
     * 上一个连接组件
     */
    private EFixed previousFixed;
    /**
     * 第一次选中
     */
    private boolean isSelectOne;
    /**
     * 动作类型
     */
    private String actionType = "";
    /**
     * 抓取文件名
     */
    private String tryFileName = "";
    /**
     * 抓取字段
     */
    private String tryName = "";
    /**
     * 是元件内容
     */
    private boolean isElementContent = false;

    /**
     *
     */
    private boolean locked = false;

    /**
     * 是时戳插入点
     */
    private boolean insert=false;
    /**
     *文字标记
     *普通 0
     *上标 1
     *下标 2
     */
    //private int script=0;

    /**
     * 是否为表达式
     */
    private boolean isExpression = false;

    /**
     * 表达式内容
     */
    private String expressionDesc = null;

    /**
     * 上标记
     */
    private String scriptUp = null;

    /**
     * 下标记
     */
    private String scriptDown = null;
    
    /**
     * 
     */
    private boolean likeQuery = false;
    
    /**
     * 是否保存数据库
     */
    private boolean isSaveDB= false;
    
    /**
     * 必填项提示消息
     */
    private String  tip= "";
    
    /**
     * 宏名称
     */
    String microMethod = "";

    /**
     * 构造器
     * @param panel EPanel
     */
    public EFixed(EPanel panel) {
        super(panel);
    }

    /**
     * 设置名称
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
     * 得到名称
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * 设置文本
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
     * 得到文本
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
     * 设置下一个连接组件
     * @param nextFixed EFixed
     */
    public void setNextFixed(EFixed nextFixed) {
        this.nextFixed = nextFixed;
    }

    /**
     * 得到下一个连接组件
     * @return EFixed
     */
    public EFixed getNextFixed() {
        return nextFixed;
    }

    /**
     * 是否存在下一个固定文本
     * @return boolean
     */
    public boolean hasNextFixed() {
        return getNextFixed() != null;
    }

    /**
     * 设置上一个连接组件
     * @param previousFixed EFixed
     */
    public void setPreviousFixed(EFixed previousFixed) {
        this.previousFixed = previousFixed;
    }

    /**
     * 得到上一个连接组件
     * @return EFixed
     */
    public EFixed getPreviousFixed() {
        return previousFixed;
    }

    /**
     * 是否存在上一个固定文本
     * @return boolean
     */
    public boolean hasPreviousFixed() {
        return getPreviousFixed() != null;
    }

    /**
     * 绘制
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paint(Graphics g, int x, int y, int width, int height) {
        //int position=0;
        //是下标
        /** if(getScript()==2){
             position-=4;
             y+=4;
         //是上标
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
     * 绘制公式的焦点背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    private void paintExpressionBack(Graphics g,int x,int y,int width,int height){

    	if( isExpression ){

        	//非整洁显示
        	if( !isPreview() ){
        		paintSuffix(g,x + width - 2,y + height);
        	}
    	}

    }

    /**
     * 绘制蓝色下标
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
	 * 绘制上下标
	 * 
	 * @param g
	 * @param x
	 * @param y
	 */
	private void paintScript(Graphics g, int x, int y) {
		// System.out.println("-------调用paintScript方法，绘制上下标-------");
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
     * 打印  测试
     * @param g Graphics
     * @param x int
     * @param y int
     */
	public void print(Graphics g, int x, int y) {
		// System.out.println("----------EFixed 重写打印方法------------------");
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

		// 绘制其他
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
     * 绘制焦点背景
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
     * 绘制固定文本焦点背景
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
     * 合并全部同类项
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
     * 合并
     * @param block IBlock
     * @return boolean true 成功 false 失败
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
     * 粘贴字符
     * @param c char
     * @return boolean
     */
    public boolean pasteChar(char c) {
        int index = getFocusIndex();
        //光标在首位
        if (index == 0 && getPreviousFixed() == null) {
            //如果前一个不是Text创建一个
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
        //光标在最末尾
        if (index == getLength() && getNextFixed() == null) {
            //如果最后没有Text创建一个
            EText nextText = getNextText();
            if (nextText == null || nextText instanceof EFixed) {
                nextText = getPanel().newText(findIndex() + 1);
                nextText.setBlock(getEnd(), getEnd());
            }
            setFocus(nextText, 0);
            return nextText.pasteChar(c);
        }
        //固定文本区域能否编辑
        if (!canEdit(index, 1)) {
            return false;
        }
        //测试该字符能否接受
        if (!canInputChar(index, c)) {
            return false;
        }
        return super.pasteChar(c);
    }

    /**
     * 固定文本区域能否编辑
     * @param index int
     * @param type int
     *  1 输入字符
     *  2 向前删除
     *  3 向后删除
     * @return boolean true 能编辑 false 不能编辑
     */
    public boolean canEdit(int index, int type) {
        return false;
    }

    /**
     * 能否录入该字符
     * @param index int
     * @param c char
     * @return boolean
     */
    public boolean canInputChar(int index, char c) {
        return true;
    }

    /**
     * 向前删除
     * @return boolean
     */
    public boolean backspaceChar() {
        int index = getFocusIndex();
        if (index != 0) {
            //固定文本区域能否向前删除
            if (canEdit(index, 2)) {
                return super.backspaceChar();
            }
            setFocusIndex(getFocusIndex() - 1);
            return true;
        }
        return super.backspaceChar();
    }

    /**
     * 向后删除
     * @return boolean
     */
    public boolean deleteChar() {
        int index = getFocusIndex();
        if (index != getLength()) {
            //固定文本区域能否向后删除
            if (canEdit(index, 3)) {
                return super.deleteChar();
            }
            setFocusIndex(getFocusIndex() + 1);
            return true;
        }
        return super.deleteChar();
    }

    /**
     * 回车事件
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
     * 得到分割新组件
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
     * 得到新对象
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index) {
        return getPanel().newFixed(index);
    }

    /**
     * 当前焦点
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
     * 得到坐标位置
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
     * 写对象属性
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
        //加入模糊查询
        s.writeBoolean(66, this.isLikeQuery(),false);
        s.writeString(67, this.getMicroMethod(), "");
        //加入是否保存数据库标志
        s.writeBoolean(68, this.isSaveDB(),false);
        //
        s.writeString(69, this.getTip(),"");
    }

    /**
     * 读对象属性
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
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX, int mouseY) {
        return false;
    }

    /**
     * 属性对话框
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
     * 删除自己
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
        //合并相同的EText
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
     * 连接上一个Text
     */
    public void linkText() {
    }

    /**
     * 固定文本阻止扩选删除动作
     * @param start int 开始位置
     * @param end int 结束位置
     * @return boolean true 阻止后续动作 false 处理标准动作
     */
    public boolean onDeleteActionIO(int start, int end) {
        return true;
    }

    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType() {
        return FIXED_TYPE;
    }

    /**
     * 查找对象
     * @param name String 名称
     * @param type int 类型
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
     * 过滤对象
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
     * 查询组
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
     * 得到最后一个固定文本
     * @return EFixed
     */
    public EFixed getEndFixed() {
        if (hasNextFixed()) {
            return getNextFixed().getEndFixed();
        }
        return this;
    }

    /**
     * 得到第一个固定文本
     * @return EFixed
     */
    public EFixed getHeadFixed() {
        if (hasPreviousFixed()) {
            return getPreviousFixed().getHeadFixed();
        }
        return this;
    }

    /**
     * 得到块值
     * @return String
     */
    public String getBlockValue() {
        return getText();
    }

    /**
     * 得到上一个真块
     * @return IBlock
     */
    public IBlock getPreviousTrueBlock() {
        EFixed fixed = getHeadFixed();
        return fixed.getPreviousBlock();
    }

    /**
     * 得到下一个真块
     * @return IBlock
     */
    public IBlock getNextTrueBlock() {
        EFixed fixed = getEndFixed();
        return fixed.getNextBlock();
    }

    /**
     * 粘贴字符串
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
     * 是否是Tab焦点
     * @return boolean
     */
    public boolean isTabFocus() {
        return false;
    }

    /**
     * 设置Tab焦点
     */
    public void setTabFocus() {
    }

    /**
     * 按下Tab
     * @param flg boolean true 向下 false 向上
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
     * 得到下一个Tab跳转控件
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
            //是否是结束符号
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
     * 是否是Tab焦点组件
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
     * 得到上一个Tab跳转控件
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
            //是否是结束符号
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
     * 克隆
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
     * 设置第一次选中
     * @param isSelectOne boolean
     */
    public void setSelectOne(boolean isSelectOne) {
        this.isSelectOne = isSelectOne;
    }

    /**
     * 是否第一次选中
     * @return boolean
     */
    public boolean isSelectOne() {
        return isSelectOne;
    }

    /**
     * 设置全部第一次选中
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
     * 设置动作类型
     * @param actionType String
     */
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    /**
     * 得到动作类型
     * @return String
     */
    public String getActionType() {
        return actionType;
    }

    /**
     * 设置抓取文件
     * @param tryFileName String
     */
    public void setTryFileName(String tryFileName) {
        this.tryFileName = tryFileName;
    }

    /**
     * 得到抓取文件
     * @return String
     */
    public String getTryFileName() {
        return tryFileName;
    }

    /**
     * 设置抓取字段
     * @param tryName String
     */
    public void setTryName(String tryName) {
        this.tryName = tryName;
    }

    /**
     * 得到抓取字段
     * @return String
     */
    public String getTryName() {
        return tryName;
    }

    /**
     * 刷新数据
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
            //后缀序号
            fSeq = f.substring(f.lastIndexOf("_") + 1, f.lastIndexOf("."));
            sSeq = s[i].substring(s[i].lastIndexOf("_") + 1,
                                  s[i].lastIndexOf("."));
            //后缀序号是非数字情况跳过
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

            //这个比较按字符比较有问题，
            /**if (f.compareTo(s[i]) < 0) {
                f = s[i];
                         }**/
        }

        return f;
    }
    
    
    /**
     * 模糊查询引用对应的文件
     * @return
     */
    private String likeFindFileName(String s[], String name, String caseNo){
        String f = "";
        String fSeq = "";
        String sSeq = "";
        for (int i = 0; i < s.length; i++) {
        	//System.out.println("--s[i]文件===["+i+"]==="+s[i]);
        	//生成的无效文件跳过
        	if(s[i].split("_").length>3){
        		continue;
        	}
        	//不是同caseno则跳过
        	//System.out.println("----------"+caseNo+","+s[i].split("_")[0]);
        	if (!s[i].split("_")[0].equals(caseNo)) {
        		//System.out.println("--不是同caseno则跳过--");
                continue;
            }
        	//不存在，则跳过
            if (s[i].indexOf(name)==-1) {
            	//System.out.println("--不存在名字，跳过--");
                continue;
            }else{
            	name=s[i].split("_")[1];
            	//System.out.println("--111name1111--"+name);
            }
            //caseNO_
            //System.out.println("--caseNo文件名--"+caseNo + "_" + name + "_");
            if (!s[i].startsWith(caseNo + "_" + name + "_")) {
                continue;
            }
            
            //存在 模糊的病历模版文件
            if (f.length() == 0) {
                f = s[i];
                continue;
            }
            //
            //后缀序号
            fSeq = f.substring(f.lastIndexOf("_") + 1, f.lastIndexOf("."));
            sSeq = s[i].substring(s[i].lastIndexOf("_") + 1,
                                  s[i].lastIndexOf("."));
            //后缀序号是非数字情况跳过
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
        System.out.println("========抓取文件路径========" +
                           getPM().getFileManager().getEmrDataDir() + "JHW\\" +
                           path);
        System.out.println("========抓取文件========" + fileName);
        System.out.println("=====抓取框名称=====" + getTryName());
        //
        ECapture capture = (ECapture) pm.getPageManager().findObject(getTryName(),
                EComponent.CAPTURE_TYPE);
        if (capture == null) {
            return false;
        }
        //System.out.println("是元件抓取==="+this.isIsElementContent());
        //取元件内容;
        if (this.isIsElementContent()) {
            List elms = capture.getElements();
            //System.out.println("==elms=="+elms.size());            
            //
            setText(" ");
            this.onFocusToRight();
            //
            //System.out.println("字体==="+this.getStyle().getFont().getFamily()+"字号===="+this.getStyle().getFont().getSize());
            //取得当前焦点       以设定的
            this.getFocusManager().insertComponents(elms,this.getStyle());
            setText("");
			// 特殊处理主诉 和 现病史 ,没有内容输出-
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
            //抓取的值中包括(和抓取框的替换成"";
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
     * 设置宏名称
     * @param code String
     */
    public void setMicroName(String microName) {
        this.microName = microName;
    }



	/**
     * 得到宏名称
     * @return String
     */
    public String getMicroName() {
        return microName;
    }

    /**
     * 设置组名称
     * @param code String
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     * 设置组名称
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
     * 得到显示字串
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
                strTemp.equals("（") || strTemp.equals("）")) {
                return "";
            }
        }
        return strTemp;
    }

    /**
     * 处理特殊字符
     * @param s String
     * @return String
     */
    private String procString(String s) {
        String str = s.replaceAll("\\(|\\)|（|）|【|】", "");
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
     * 上下标属性对话框
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
