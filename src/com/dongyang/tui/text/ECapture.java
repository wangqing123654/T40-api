package com.dongyang.tui.text;

import java.awt.Color;
import java.awt.Graphics;
import com.dongyang.tui.io.DataOutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: 抓取组件</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.24
 * @author whao 2013~
 * @version 1.0
 */
public class ECapture extends EFixed
{
    /**
     * 类型
     * 0 开始
     * 1 结束
     */
    private int captureType;
    /**
     * 是否锁定;
     */
    private boolean locked;
    //add by lx 2012/08/16 加入引用文件属性
    /**
     * 引用文件名
     */
    private String refFileName="";

    /**
     * 依赖文件名
     */
    private String dependFileName="";

    /**
     * 构造器
     * @param panel EPanel
     */
    public ECapture(EPanel panel)
    {
        super(panel);

        // 此处与合并版不同
        this.initColor();
    }
    /**
     * 设置抓取类型
     * @param captureType int
     */
    public void setCaptureType(int captureType)
    {
        this.captureType = captureType;
        switch(captureType)
        {
        case 0://开始
            setText("【");
            break;
        case 1://结束
            setText("】");
            break;
        }
    }
    /**
     * 得到抓取类型
     * @return int
     */
    public int getCaptureType()
    {
        return captureType;
    }
    /**
     * 得到新对象
     * @param index int
     * @return EFixed
     */
    public EFixed getNewObject(int index)
    {
        return getPanel().newCapture(index);
    }
    /**
     * 得到坐标位置
     * @return String
     */
    public String getIndexString()
    {
        EPanel panel = getPanel();
        if(panel == null)
            return "Parent:Null";
        return panel.getIndexString() + ",ECapture:" + findIndex();
    }
    public String toString()
    {
        return "<ECapture name=" + getName() + ",start=" + getStart() + ",end=" + getEnd() + ",s=" + getString() + ",position=" + getPosition() + ",index=" + getIndexString() +
                ",p=" + (getPreviousFixed() != null?"[" + getPreviousFixed().getIndexString() + "]":"null") +
                ",n=" + (getNextFixed() != null?"[" + getNextFixed().getIndexString() + "]":"null") + ">";
    }
    /**
     * 绘制固定文本焦点背景
     * @param g Graphics
     * @param x int
     * @param y int
     * @param width int
     * @param height int
     */
    public void paintFixedFocusBack(Graphics g,int x,int y,int width,int height)
    {
        super.paintFixedFocusBack(g,x,y,width,height);
    }
    /**
     * 双击
     * @param mouseX int
     * @param mouseY int
     * @return boolean
     */
    public boolean onDoubleClickedS(int mouseX,int mouseY)
    {
        return false;
    }
    /**
     * 属性对话框
     */
    public void openPropertyDialog()
    {
        EFixed fixed = getHeadFixed();
        String value = (String)getPM().getUI().openDialog("%ROOT%\\config\\database\\CaptureEdit.x",fixed);
        if(value == null || !"OK".equals(value))
            return;
        fixed.getPM().getFileManager().update();
    }
    /**
     * 写对象属性
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObjectAttribute(DataOutputStream s)
            throws IOException
    {
        super.writeObjectAttribute(s);
        s.writeInt(100,getCaptureType(),0);
        s.writeBoolean(101,this.isLocked(),false);
        s.writeString(102,this.getRefFileName(),"");
        s.writeString(103,this.getDependFileName(),"");
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

    	// 此处与合并版不同
        this.initColor();

        //
        if (super.readObjectAttribute(id, s))
            return true;
        switch (id)
        {
        case 100:
            setCaptureType(s.readInt());
            return true;
        case 101:
            this.setLocked(s.readBoolean());
            return true;
        case 102:
        	this.setRefFileName(s.readString());
        	return true;
        case 103:
        	this.setDependFileName(s.readString());
        	return true;

        }
        return false;
    }

    /**
     *
     */
    private void initColor(){
        DStyle ds = this.getStyle().copy();
        ds.setColor(Color.lightGray);
        this.setStyle(ds);
    }

    /**
     * 得到对象类型
     * @return int
     */
    public int getObjectType()
    {
        return CAPTURE_TYPE;
    }

    /**
     * 得到值<默认查出带删除线的>
     * @param
     * @return
     */
	public String getValue() {

		return this.getValue(false);
	}

    /**
     * 得到值
     * @param withnotDelText 是否需要带删除线的文本
     * @return
     */
	public String getValue(boolean withnotDelText) {
		if (getCaptureType() == 1)
			return "";
		StringBuffer sb = new StringBuffer();
		IBlock block = getNextBlock();

		while (block != null) {
			// 是否是结束符号
			if (isEndCapture(block))
				return sb.toString();

            //
            this.doProcessValue(withnotDelText, block, sb);

			//
			IBlock nextBlock = block.getNextTrueBlock();
			if (nextBlock != null) {
				block = nextBlock;
				continue;
			}
			EPanel panel = block.getPanel().getNextTruePanel();
			if (panel == null || panel.size() == 0) {
				System.out.println("err:[" + getName() + "]没有找到结束符!");
				return "";
			}
			block = panel.get(0);
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 *
	 * @param withnotDelText
	 * @param block
	 * @param sb
	 */
	private void doProcessValue(boolean withnotDelText, IBlock block,
			StringBuffer sb) {

		if ( withnotDelText ) {

			if ( block instanceof EText ) {

				 EText tt = (EText) block;

				 // System.out.println( block );

				 // System.out.println( tt.getStyle().isDeleteLine() +" --"+block.getBlockValue());

				 if ( !tt.getStyle().isDeleteLine() ) {

					sb.append( block.getBlockValue() );
				 }
			}

		} else {

			sb.append( block.getBlockValue( ));
		}
	}

    /**
	 * 焦点在抓取框内
	 *
	 * @return boolean
	 */
    public boolean focusInCaptue()
    {

    	// System.out.println("======come in======"+getCaptureType());
        if(getCaptureType() == 1)
            return true;
        IBlock block = getNextBlock();
        if(block == null)
        {
            EPanel panel = getPanel().getNextTruePanel();
            if(panel == null || panel.size() == 0)
                return false;
            block = panel.get(0);
        }
        while(block != null)
        {
            //是否是结束符号
            if(isEndCapture(block))
            {
                if(getFocus() == block && getFocusIndex() == 0){
                    return true;
                }
                return false;
            }
            if(getFocus() == block){
                return true;
            }
            IBlock nextBlock = block.getNextTrueBlock();
            if(nextBlock != null)
            {
                block = nextBlock;
                continue;
            }
            EPanel panel = block.getPanel().getNextTruePanel();
            if(panel == null || panel.size() == 0)
                return false;
            block = panel.get(0);
        }
        return false;
    }
    /**
     * 得到结束抓取对象
     * @return ECapture
     */
    public ECapture getEndCapture()
    {
        if(getCaptureType() == 1)
            return null;
        IBlock block = getNextBlock();
        if(block == null)
        {
            EPanel panel = getPanel().getNextTruePanel();
            if(panel == null || panel.size() == 0)
                return null;
            block = panel.get(0);
        }
        while(block != null)
        {
            //是否是结束符号
            if(isEndCapture(block))
                return (ECapture)block;
            IBlock nextBlock = block.getNextTrueBlock();
            if(nextBlock != null)
            {
                block = nextBlock;
                continue;
            }
            EPanel panel = block.getPanel().getNextTruePanel();
            if(panel == null || panel.size() == 0)
                return null;
            block = panel.get(0);
        }
        return null;
    }
    /**
     * 是否是结束符
     * @param block IBlock
     * @return boolean
     */
    private boolean isEndCapture(IBlock block)
    {
        if(block == null)
            return false;
        if(block.getObjectType() != EComponent.CAPTURE_TYPE)
            return false;
        ECapture capture = (ECapture)block;
        if(capture.getCaptureType() != 1)
            return false;
        if(capture.getName() == null || capture.getName().length() == 0)
            return false;
        if(capture.getName().equals(getName()))
            return true;
        return false;
    }
    /**
     * 刷新数据
     * @param action EAction
     */
    public void resetData(EAction action)
    {
        if(action.getType() == EAction.PREVIEW_STATE ||
           action.getType() == EAction.EDIT_STATE)
        {
            setModify(true);
            return;
        }
    }
    /**
     * 得到显示字串
     * @return String
     */
    public String getShowString()
    {
        if(!isPreview()){
            return super.getShowString();
        }
        return "";
    }
    /**
     * 清除抓取对象之间的全部对象
     */
    public void clear()
    {
        ECapture capture = getEndCapture();
        //System.out.println("======clear capturename======="+capture.getName());
        //System.out.println("======clear capture type======="+capture.getCaptureType());
        if(capture == null)
            return;
        setFocusLast();
        getFocusManager().setStartSelected();
        capture.setFocus();
        getFocusManager().setEndSelected();
        getFocusManager().delete(0);
        setFocusLast();
    }
    /**
     * 克隆
     * @param panel EPanel
     * @return IBlock
     */
    public IBlock clone(EPanel panel)
    {
        ECapture capture = new ECapture(panel);
        capture.setStart(panel.getDString().size());
        capture.setEnd(getStart());
        capture.setString(getString());
        capture.setKeyIndex(getKeyIndex());
        capture.setDeleteFlg(isDeleteFlg());
        capture.setStyleOther(getStyle());
        capture.setName(getName());
        capture.setCaptureType(getCaptureType());
        capture.setGroupName(getGroupName());
        capture.setMicroName(getMicroName());
        capture.setDataElements(isIsDataElements());
        capture.setAllowNull(isAllowNull());
        capture.setLocked(isLocked());
        capture.setRefFileName(getRefFileName());
        capture.setDependFileName(getDependFileName());
        return capture;
    }
    /**
     * 获得抓取框内的所有元素;
     * @return List
     */
    public List<Object> getElements(){
        List<Object> coms=new ArrayList<Object>();
        if(getCaptureType() == 1)
            return null;

        IBlock block = getNextBlock();
        while(block != null)
        {

            //是否是结束符号
            if(isEndCapture(block))
             return coms;
             //20141209 modify by zhangp 体格检查空行换行显示为换行
            if(("PHY".equals(getName())
            		|| "EXA_RESULT".equals(getName()))
            		&& block.getBlockValue().length() == 0){
            	coms.add(CSelectPanelEnterModel.NEW_LINE);
            }
            coms.add(block);
            IBlock nextBlock = block.getNextTrueBlock();
            if(nextBlock != null)
            {
                block = nextBlock;
                continue;
            }

            EPanel panel = block.getPanel().getNextTruePanel();
            if(panel == null || panel.size() == 0)
            {
                System.out.println("err:[" + getName() + "]没有找到结束符!");
                return null;
            }
            block = panel.get(0);


        }

        return coms;
    }
    /**
     *
     * @param isLocked boolean
     */
    public void setLocked(boolean locked){
        this.locked=locked;
    }
    /**
     *
     * @return boolean
     */
    public boolean isLocked(){
        return this.locked;
    }

	public String getRefFileName() {
		return refFileName;
	}
	public void setRefFileName(String refFileName) {
		this.refFileName = refFileName;
	}
	//$$==============add by lx 2012/08/20  加入引用内容start========================$$//

	/**
	 * 引入单独文件内容
	 */
	public boolean doRefFile(){
		//
		 if (getRefFileName() == null || getRefFileName().length() == 0) {
	            return false;
	        }
		    //取引用文件内容
		    String caseNo = getPM().getCaseNo();
		    //System.out.println("=====caseNo1111====="+caseNo);
		    //
	        String mrNo = getPM().getMrNo();
	        //System.out.println("=====mrNo1111====="+mrNo);

	        String path = caseNo.substring(0, 2) + "\\" + caseNo.substring(2, 4) +
	                      "\\" + mrNo;
	        PM pm = new PM();
	        pm.setUI(getPM().getUI());
	       /* if (!pm.getFileManager().onOpen("JHW\\" + path, getRefFileName(), 3, false)) {
	            return false;
	        }*/

	        //将文件内容插入抓取框内
	        ECapture capture = (ECapture) this.getPageManager().findObject(this.getName(),
	                EComponent.CAPTURE_TYPE);
	        //System.out.println("===capture name==="+capture.getName());
	        //System.out.println("===capture file name==="+capture.getRefFileName());
	        //
	        if (capture.getCaptureType() == 0) {
				capture.setFocusLast();
				capture.clear();
				//刷新
			    //this.getPM().getFileManager().update();
			    //插入文件内容
		        this.getFocusManager().onInsertFile("JHW\\" + path, getRefFileName(),
	                    3, false);
		        //没起作用?????
		        //capture.setFocus();
		        //capture.deleteChar();
	        }

		 return true;
	}
	//$$==============add by lx 2012/08/20  加入引用内容方法========================$$//


	public String getDependFileName() {
		return dependFileName;
	}
	public void setDependFileName(String dependFileName) {
		this.dependFileName = dependFileName;
	}



}
