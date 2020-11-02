package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.util.TypeTool;
import java.util.ArrayList;

/**
 *
 * <p>Title: 选中控制</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 009.11.3
 * @version 1.0
 */
public class CSelectedModel
{
    /**
     * 焦点管理器
     */
    private MFocus focusManager;
    /**
     * 选中起始组件
     */
    private EComponent startComponent;
    /**
     * 焦点对象
     */
    private EComponent focus;
    /**
     * 焦点类型
     */
    private int focusType;
    /**
     * 焦点位置
     */
    private int focusIndex;
    /**
     * 选中起始位置
     */
    private int startIndex;
    /**
     * 选中起始行号
     */
    private int startRow;
    /**
     * 选中起始列号
     */
    private int startColumn;
    /**
     * 起始类型
     */
    private int startType;
    /**
     * 选中结束组件
     */
    private EComponent endComponent;
    /**
     * 选中结束位置
     */
    private int endIndex;
    /**
     * 选中结束行号
     */
    private int endRow;
    /**
     * 选中结束列号
     */
    private int endColumn;
    /**
     * 结束类型
     */
    private int endType;
    /**
     * 方向
     * 1 正向
     * 2 负向
     */
    private int direction;
    /**
     * 选中池
     */
    private TList selectedPool;
    /**
     * 选中列表
     */
    private TList selectedList;
    /**
     * 构造器
     * @param focusManager MFocus
     */
    public CSelectedModel(MFocus focusManager)
    {
        //初始化选中池
        setSelectPool(new TList());
        setFocusManager(focusManager);
    }
    /**
     * 设置焦点管理器
     * @param focusManager MFocus
     */
    public void setFocusManager(MFocus focusManager)
    {
        this.focusManager = focusManager;
    }
    /**
     * 得到焦点管理器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return focusManager;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getFocusManager().getPM();
    }
    /**
     * 设置选中起始组件
     * @param startComponent EComponent
     */
    public void setStartComponent(EComponent startComponent)
    {
        this.startComponent = startComponent;
    }
    /**
     * 得到选中起始组件
     * @return EComponent
     */
    public EComponent getStartComponent()
    {
        return startComponent;
    }
    /**
     * 得到起始路径
     * @return TList
     */
    public TList getStartPathIndex()
    {
        if(startComponent == null)
            return new TList();
        if(startComponent instanceof EText)
            return ((EText)startComponent).getPathIndex();
        if(startComponent instanceof ETR)
            return ((ETR)startComponent).getPathIndex();
        if(startComponent instanceof EImage)
            return ((EImage)startComponent).getPathIndex();
        return null;
    }
    /**
     * 设置选中起始位置
     * @param startIndex int
     */
    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }
    /**
     * 得到选中起始位置
     * @return int
     */
    public int getStartIndex()
    {
        return startIndex;
    }
    /**
     * 设置选中起始行号
     * @param startRow int
     */
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }
    /**
     * 得到选中起始行号
     * @return int
     */
    public int getStartRow()
    {
        return startRow;
    }
    /**
     * 设置选中起始列号
     * @param startColumn int
     */
    public void setStartColumn(int startColumn)
    {
        this.startColumn = startColumn;
    }
    /**
     * 得到选中起始列号
     * @return int
     */
    public int getStartColumn()
    {
        return startColumn;
    }
    /**
     * 设置起始类型
     * @param startType int
     */
    public void setStartType(int startType)
    {
        this.startType = startType;
    }
    /**
     * 得到启示
     * @return int
     */
    public int getStartType()
    {
        return startType;
    }
    /**
     * 设置选中结束组件
     * @param endComponent EComponent
     */
    public void setEndComponent(EComponent endComponent)
    {
        this.endComponent = endComponent;
    }
    /**
     * 得到选中结束组件
     * @return EComponent
     */
    public EComponent getEndComponent()
    {
        return endComponent;
    }
    /**
     * 得到结束路径
     * @return TList
     */
    public TList getEndPathIndex()
    {
        if(endComponent == null)
            return new TList();
        if(endComponent instanceof EText)
            return ((EText)endComponent).getPathIndex();
        if(endComponent instanceof ETR)
            return ((ETR)endComponent).getPathIndex();
        if(endComponent instanceof EImage)
            return ((EImage)endComponent).getPathIndex();
        return null;
    }
    /**
     * 设置选中结束位置
     * @param endIndex int
     */
    public void setEndIndex(int endIndex)
    {
        this.endIndex = endIndex;
    }
    /**
     * 得到选中结束位置
     * @return int
     */
    public int getEndIndex()
    {
        return endIndex;
    }
    /**
     * 设置选中结束行号
     * @param endRow int
     */
    public void setEndRow(int endRow)
    {
        this.endRow = endRow;
    }
    /**
     * 得到选中结束行号
     * @return int
     */
    public int getEndRow()
    {
        return endRow;
    }
    /**
     * 设置选中结束列号
     * @param endColumn int
     */
    public void setEndColumn(int endColumn)
    {
        this.endColumn = endColumn;
    }
    /**
     * 得到选中结束列号
     * @return int
     */
    public int getEndColumn()
    {
        return endColumn;
    }
    /**
     * 设置结束类型
     * @param endType int
     */
    public void setEndType(int endType)
    {
        this.endType = endType;
    }
    /**
     * 得到结束类型
     * @return int
     */
    public int getEndType()
    {
        return endType;
    }
    /**
     * 设置方向
     * @param direction int
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
    }
    /**
     * 得到方向
     * @return int
     */
    public int getDirection()
    {
        return direction;
    }
    /**
     * 是否选中
     * @return boolean
     */
    public boolean isSelected()
    {
        if(getSelectedPool().size() > 0)
            return true;
        return getSelectedList() != null;
    }
    /**
     * 清空选蓝
     */
    public void clearSelected()
    {
        setStartComponent(null);
        setStartIndex(-1);
        setStartRow(-1);
        setStartColumn(-1);
        setStartType(-1);
        setEndComponent(null);
        setEndIndex(-1);
        setEndRow(-1);
        setEndColumn(-1);
        setEndType(-1);
        //清除选中池
        clearSelectedPool();
        //清空当前选中对象列表
        clearSelectedList(getSelectedList());
        setSelectedList(null);
    }
    /**
     * 压制选蓝列表
     */
    public void putSelected()
    {
        if(getSelectedList() == null || getSelectedList().size() == 0)
            return;
        getSelectedPool().add(getSelectedList());
        setSelectedList(null);
    }
    /**
     * 清除选中池
     */
    public void clearSelectedPool()
    {
        TList list = getSelectedPool();
        for(int i = 0;i < list.size();i++)
            clearSelectedList((TList)list.get(i));
        list.removeAll();
    }
    /**
     * 删除一个选中列表
     * @param list TList
     */
    public void removeSelectList(TList list)
    {
        if(list == null)
            return;
        if(getSelectedPool().size() == 0)
            return;
        clearSelectedList(list);
        getSelectedPool().remove(list);
    }
    /**
     * 清除选中列表
     * @param list TList
     */
    public void clearSelectedList(TList list)
    {
        if(list == null)
            return;
        for(int i = list.size() - 1;i >= 0 ;i--)
        {
            Object com = list.get(i);
            if(com instanceof CSelectTextBlock)
                ((CSelectTextBlock)com).removeThis();
            else if(com instanceof CSelectPanelEnterModel)
                ((CSelectPanelEnterModel)com).removeThis();
            else if(com instanceof CSelectPanelModel)
                ((CSelectPanelModel)com).removeThis();
            else if(com instanceof CSelectTRModel)
                ((CSelectTRModel)com).removeThis();
            else if(com instanceof CSelectTDModel)
                ((CSelectTDModel)com).removeThis();
            else if(com instanceof EText)
                ((EText)com).clearSelected();
            else if(com instanceof EPanel)
                ((EPanel)com).setSelectedAll(false);
            else if(com instanceof ETR)
                ((ETR)com).setSelectedAll(false);
            else if(com instanceof ETD)
                ((ETD)com).setSelectedAll(false);
        }
        list.removeAll();
    }
    /**
     * 设置开始选中
     */
    public void setStartSelected()
    {
        setStartType(getFocusType());
        setStartComponent(getFocus());
        setStartIndex(getFocusIndex());
    }
    /**
     * 设置结束选中
     */
    public void setEndSelected()
    {
        //清除当前的选蓝对象
        clearSelectedList(getSelectedList());
        //设置结束点
        setEndType(getFocusType());
        setEndComponent(getFocus());
        setEndIndex(getFocusIndex());
        selectCheck();
        if(getSelectedList() != null && getSelectedList().size() == 0)
            setSelectedList(null);
        getFocusManager().showCursor();
    }
    /**
     * 创建选中列表
     */
    public void createSelectList()
    {
        if(selectedList != null)
            return;
        selectedList = new TList();
    }
    /**
     * 设置选中列表
     * @param selectedList TList
     */
    public void setSelectedList(TList selectedList)
    {
        this.selectedList = selectedList;
    }
    /**
     * 得到选中列表
     * @return TList
     */
    public TList getSelectedList()
    {
        return selectedList;
    }
    /**
     * 选择检测
     */
    public void selectCheck()
    {
        //没有多选
        if(notSelect())
            return;
        //选中一个行
        if(selectOneTR())
            return;
        setDirection(checkDirection());
        //选择同一个Table多个TR
        if(selectMoreTR())
            return;
        //选中一个对象处理
        if(selectedOne())
            return;
        //选中同一个面板中不同的文本
        if(selectedEqualsPanelText())
            return;
        //选中同层面板
        if(selectClassPanelText())
            return;
        //选中同层Text 到Table行
        if(selectTextToTR())
            return;
        //选中同层Table行到Text
        if(selectTRToText())
            return;
        //选中Text到表格中的Text
        if(selectTextToTRText())
            return;
        //选中TD到TD
        if(selectTDToTD())
            return;
        //选择TD到TR
        if(selectTDToTR())
            return;
        //选中TR到TD
        if(selectTRToTD())
            return;
        //选择TR到TR
        if(selectTRToTR())
            return;
    }
    /**
     * 选择TR到TR
     * @return boolean
     */
    public boolean selectTRToTR()
    {
        if(getDirection() == 0 || (getStartType() != 2 && getStartType() != 3) ||
           (getEndType() != 2 && getEndType() != 3))
            return false;
        ETR startTR = getDirection() == 1?getStartTR():getEndTR();
        ETR endTR = getDirection() == 1?getEndTR():getStartTR();
        EPanel startPanel = startTR.getPanel();
        EPanel endPanel = endTR.getPanel();
        //不再同层
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        //创建选择列表
        createSelectList();
        startTR = startTR.getHeadTR();
        endTR = endTR.getHeadTR();
        //装载第一个TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //装载第一个Table
        initTableDownTR(startTR);
        //装载中间Panel
        initPanel(startTR.getPanel(),endTR.getPanel());
        //装载最后一个Table
        initTableUpTR(endTR);
        //装载最后一个TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * 选中TR到TD
     * @return boolean
     */
    public boolean selectTRToTD()
    {
        if(getDirection() == 0 ||
          (getDirection() == 1 && ((getStartType() != 2 && getStartType() != 3) || getEndType() != 1)) ||
          (getDirection() == -1 && (getStartType() != 1 || (getEndType() != 2 && getEndType() != 3))))
            return false;
        ETR tr = getDirection() == 1?getStartTR():getEndTR();
        EText endText = getEndText();
        ETable startTable = tr.getTable();
        ETable endTable = endText.getTable();
        //不再同一个表格中
        if(startTable == null || endTable == null)
            return false;
        if(startTable.tableIsNextListTable(endTable))
        {
            //在同一个格中
            if (((getDirection() == 1 && getStartType() == 2) ||
                 (getDirection() == -1 && getEndType() == 2)) &&
                endText.getTD().getHeadTD() == tr.getHeadTR().get(0))
            {
                ETD td = endText.getTD();
                //创建选择列表
                createSelectList();
                getSelectedList().add(td.createSelectedModel());
                td.setSelectedAll(true);
                return true;
            }
            //得到选中行列坐标
            setStartRow(tr.getRow());
            if ((getDirection() == 1 && getStartType() == 2) ||
                (getDirection() == -1 && getEndType() == 2))
                setStartColumn(0);
            else
                setStartColumn(tr.size() - 1);
            setEndRow(endText.getTableRow());
            setEndColumn(endText.getTableColumn());
            //创建选择列表
            createSelectList();
            //选中TD
            selectTD(startTable);
            return true;
        }
        EPanel startPanel = startTable.getPanel();
        EPanel endPanel = endTable.getPanel();
        //不再同层
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        ETR startTR = tr.getHeadTR();
        ETR endTR = endText.getTR().getHeadTR();
        //创建选择列表
        createSelectList();
        //装载第一个TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //装载第一个Table
        initTableDownTR(startTR);
        //装载中间Panel
        initPanel(startPanel,endPanel);
        //装载最后一个Table
        initTableUpTR(endTR);
        //装载最后一个TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * 选择TD到TR
     * @return boolean
     */
    public boolean selectTDToTR()
    {
        if(getDirection() == 0 ||
          (getDirection() == 1 && (getStartType() != 1 || (getEndType() != 2 && getEndType() != 3))) ||
          (getDirection() == -1 && ((getStartType() != 2 && getStartType() != 3) || getEndType() != 1)))
            return false;
        EText startText = getStartText();
        ETR tr = getDirection() == 1?getEndTR():getStartTR();
        ETable startTable = startText.getTable();
        ETable endTable = tr.getTable();
        if(startTable == null || endTable == null)
            return false;
        //在同一个表格中
        if(startTable.tableIsNextListTable(endTable))
        {
            //得到选中行列坐标
            setStartRow(startText.getTableRow());
            setStartColumn(startText.getTableColumn());
            setEndRow(tr.getRow());
            if ((getDirection() == 1 && getEndType() == 2) ||
                (getDirection() == -1 && getStartType() == 2))
                setEndColumn(0);
            else
                setEndColumn(tr.size() - 1);
            //创建选择列表
            createSelectList();
            //选中TD
            selectTD(startTable);
            return true;
        }
        EPanel startPanel = startTable.getPanel();
        EPanel endPanel = endTable.getPanel();
        //不再同层
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        ETR startTR = startText.getTR().getHeadTR();
        ETR endTR = tr.getHeadTR();
        //创建选择列表
        createSelectList();
        //装载第一个TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //装载第一个Table
        initTableDownTR(startTR);
        //装载中间Panel
        initPanel(startPanel,endPanel);
        //装载最后一个Table
        initTableUpTR(endTR);
        //装载最后一个TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * 能否合并同单元格
     * @return boolean
     */
    public boolean canUniteTD()
    {
        if(getSelectedPool().size() > 0)
            return false;
        if(getSelectedList() == null)
        {
            if (getFocusType() == 2)
                return true;
            return false;
        }
        ETable table = null;
        for(int i = 0;i < getSelectedList().size();i++)
        {
            Object obj = getSelectedList().get(i);
            if(obj instanceof CSelectTDModel)
            {
                CSelectTDModel model = (CSelectTDModel)obj;
                if(table == null)
                {
                    table = model.getTD().getTable().getHeadTable();
                    continue;
                }
                if(table != model.getTD().getTable().getHeadTable())
                    return false;
            }else if(obj instanceof CSelectTRModel)
            {
                CSelectTRModel model = (CSelectTRModel)obj;
                if(table == null)
                {
                    table = model.getTR().getTable().getHeadTable();
                    continue;
                }
                if(table != model.getTR().getTable().getHeadTable())
                    return false;
            }else
                return false;
        }
        return true;
    }
    /**
     * 选中TD到TD
     * @return boolean
     */
    public boolean selectTDToTD()
    {
        if(getDirection() == 0 || getStartType() != 1 || getEndType() != 1)
            return false;
        EText startText = getStartText();
        EText endText = getEndText();
        ETable startTable = startText.getTable();
        ETable endTable = endText.getTable();
        if(startTable == null || endTable == null)
            return false;
        //在同一个表格中
        if(startTable.tableIsNextListTable(endTable))
        {
            //得到选中行列坐标
            setStartRow(startText.getTableRow());
            setStartColumn(startText.getTableColumn());
            setEndRow(endText.getTableRow());
            setEndColumn(endText.getTableColumn());
            //创建选择列表
            createSelectList();
            //选中TD
            selectTD(startTable);
            return true;
        }
        EPanel startPanel = startTable.getPanel();
        EPanel endPanel = endTable.getPanel();
        //不再同层
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        ETR startTR = startText.getTR().getHeadTR();
        ETR endTR = endText.getTR().getHeadTR();
        //创建选择列表
        createSelectList();
        //装载第一个TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //装载第一个Table
        initTableDownTR(startTR);
        //装载中间Panel
        initPanel(startPanel,endPanel);
        //装载最后一个Table
        initTableUpTR(endTR);
        //装载最后一个TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * 选中TD
     * @param table ETable
     */
    private void selectTD(ETable table)
    {
        table = table.getHeadTable();
        int row1 = Math.min(getStartRow(),getEndRow());
        int row2 = Math.max(getStartRow(),getEndRow());
        int column1 = Math.min(getStartColumn(),getEndColumn());
        int column2 = Math.max(getStartColumn(),getEndColumn());
        for(int row = row1;row <= row2;row++)
        {
            ETR tr = table.getTR(row);
            for (int column = column1; column <= column2; column++)
            {
                ETD td = tr.get(column);
                getSelectedList().add(td.createSelectedModel());
                td.setSelectedAll(true);
            }
        }
    }
    /**
     * 选中Text到表格中的Text
     * @return boolean
     */
    public boolean selectTextToTRText()
    {
        if(getDirection() == 0 || getStartType() != 1 || getEndType() != 1)
            return false;
        EText startText = getStartText();
        EText endText = getEndText();
        EPanel startTablePanel = startText.getTablePanel();
        EPanel endTablePanel = endText.getTablePanel();
        //从Text到TD
        if(endTablePanel != null && startText.getPanel().panelIsClassPanel(endTablePanel))
        {
            //创建选择列表
            createSelectList();
            //选中从Text 到TR
            selectTextToTR(startText,endText.getTR());
            return true;
        }
        //从TD到Text
        if(startTablePanel != null && startTablePanel.panelIsClassPanel(endText.getPanel()))
        {
            //创建选择列表
            createSelectList();
            //选中TR到Text
            selectTRToText(startText.getTR(),endText);
            return true;
        }
        return false;
    }
    /**
     * 选择同一个Table多个TR
     * @return boolean
     */
    public boolean selectMoreTR()
    {
        if(getDirection() == 0 ||
           (getStartType() != 2 && getStartType() != 3) ||
           (getEndType() != 2 && getEndType() != 3))
            return false;
        ETR startTR = getDirection() == 1?getStartTR():getEndTR();
        ETR endTR = getDirection() == 1?getEndTR():getStartTR();
        //测试是否是同一个Table包括连接Table
        if(!startTR.getTable().tableIsNextListTable(endTR.getTable()))
            return false;
        //创建选择列表
        createSelectList();
        //装载连个行之间行
        initTableTR(startTR,endTR);
        return true;
    }
    /**
     * 装载连个行之间行
     * @param startTR ETR
     * @param endTR ETR
     */
    private void initTableTR(ETR startTR,ETR endTR)
    {
        ETR tr = startTR.getHeadTR();
        endTR = endTR.getHeadTR();
        if(tr == endTR)
        {
            getSelectedList().add(tr.createSelectedModel());
            tr.setSelectedAll(true);
            return;
        }
        while(tr != null && tr != endTR)
        {
            getSelectedList().add(tr.createSelectedModel());
            tr.setSelectedAll(true);
            tr = tr.getNextTrueTR();
        }
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
    }
    /**
     * 选中一个行
     * @return boolean
     */
    public boolean selectOneTR()
    {
        if(getStartComponent() != getEndComponent())
            return false;
        if(getStartType() != 2 && getStartType() != 3)
            return false;
        ETR startTR = getStartTR();
        //创建选择列表
        createSelectList();
        //装载第一个选中行
        getSelectedList().add(startTR.getHeadTR().createSelectedModel());
        startTR.setSelectedAll(true);
        return true;
    }
    /**
     * 选中同层Table行到Text
     * @return boolean
     */
    private boolean selectTRToText()
    {
        if(getDirection() == 0 ||
           (getDirection() == 1 && ((getStartType() != 2 && getStartType() != 3) || getEndType() != 1)) ||
           (getDirection() == -1 && ((getEndType() != 2 && getEndType() != 3) || getStartType() != 1)))
           return false;
        ETR startTR = getDirection() == 1?getStartTR():getEndTR();
        EText endText = getEndText();
        //不是同一个面板或后续连接面板
        if(!startTR.getPanel().panelIsClassPanel(endText.getPanel()))
            return false;
        //创建选择列表
        createSelectList();
        //选中TR到Text
        selectTRToText(startTR,endText);
        return true;
    }
    /**
     * 选中TR到Text
     * @param startTR ETR
     * @param endText EText
     */
    private void selectTRToText(ETR startTR,EText endText)
    {
        //装载第一个选中行
        getSelectedList().add(startTR.getHeadTR().createSelectedModel());
        startTR.setSelectedAll(true);
        //装载指定行之后的全部行
        initTableDownTR(startTR);
        //装载中间Panel
        initPanel(startTR.getPanel(),endText.getPanel());
        //装载最后面板
        initPanelUpText(endText);
        //装载最后一个
        initLastText(endText);
    }
    /**
     * 选中同层Text 到Table行
     * @return boolean
     */
    private boolean selectTextToTR()
    {
        if(getDirection() == 0 ||
           (getDirection() == 1 && (getStartType() != 1 || (getEndType() != 2) && getEndType() != 3)) ||
           (getDirection() == -1 && (getEndType() != 1 || (getStartType() != 2 && getStartType() !=3))))
            return false;
        EText startText = getStartText();
        ETR endTR = getDirection() == 1?getEndTR():getStartTR();
        //不是同一个面板或后续连接面板
        if(!startText.getPanel().panelIsClassPanel(endTR.getPanel()))
            return false;
        //创建选择列表
        createSelectList();
        //选中从Text 到TR
        selectTextToTR(startText,endTR);
        return true;
    }
    /**
     * 选中从Text 到TR
     * @param startText EText
     * @param endTR ETR
     */
    private void selectTextToTR(EText startText,ETR endTR)
    {
        //装载第一个组件
        initFirstText(startText);
        //装载第一个面板
        initPanelDownText(startText);
        //装载中间Panel
        initPanel(startText.getPanel(),endTR.getPanel());
        //装载指定行之前的全部行
        initTableUpTR(endTR);
        //装载最后选中行
        getSelectedList().add(endTR.getHeadTR().createSelectedModel());
        endTR.setSelectedAll(true);
    }
    /**
     * 装载指定行之后的全部行
     * @param trStart ETR
     */
    public void initTableDownTR(ETR trStart)
    {
        ETR tr = trStart.getNextTrueTR();
        while(tr != null)
        {
            getSelectedList().add(tr.createSelectedModel());
            tr.setSelectedAll(true);
            tr = tr.getNextTrueTR();
        }
    }
    /**
     * 装载指定行之前的全部行
     * @param trEnd ETR
     */
    private void initTableUpTR(ETR trEnd)
    {
        trEnd = trEnd.getHeadTR();
        ETR tr = trEnd.getTable().getHeadTable().get(0);
        while(tr != null && tr != trEnd)
        {
            getSelectedList().add(tr.createSelectedModel());
            tr.setSelectedAll(true);
            tr = tr.getNextTrueTR();
        }
    }
    /**
     * 选中同层面板
     * @return boolean
     */
    private boolean selectClassPanelText()
    {
        if(getDirection() == 0 || getStartType() != 1 || getEndType() != 1)
            return false;
        EText startText = getStartText();
        EText endText = getEndText();
        //不是同一个面板或后续连接面板
        if(!startText.getPanel().panelIsClassPanel(endText.getPanel()))
            return false;
        //创建选择列表
        createSelectList();
        //装载第一个组件
        initFirstText(startText);
        //装载第一个面板
        initPanelDownText(startText);
        //装载中间Panel
        initPanel(startText.getPanel(),endText.getPanel());
        //装载最后面板
        initPanelUpText(endText);
        //装载最后一个
        initLastText(endText);
        return true;
    }
    /**
     * 装载Panel
     * @param startPanel EPanel
     * @param endPanel EPanel
     */
    public void initPanel(EPanel startPanel,EPanel endPanel)
    {
        endPanel = endPanel.getHeadPanel();
        EPanel panel = startPanel.getNextTruePanel();
        while(panel != null && panel != endPanel)
        {
            panel.setSelectedAll(true);
            getSelectedList().add(panel.getHeadPanel().createSelectedModel());
            getSelectedList().add(panel.getEndPanel().createSelectedEnterModel());
            panel = panel.getNextTruePanel();
        }
    }
    /**
     * 选中同一个面板中不同的文本
     * @return boolean
     */
    private boolean selectedEqualsPanelText()
    {
        if(getDirection() == 0 || getStartType() != 1 || getEndType() != 1)
            return false;
        EText startText = getStartText();
        EText endText = getEndText();
        //不是同一个面板或后续连接面板
        if(!startText.getPanel().panelIsNextListPanel(endText.getPanel()))
            return false;
        //创建选择列表
        createSelectList();
        //装载第一个组件
        initFirstText(startText);
        //装载同一面板中间的Text
        initEqualsPanelText(startText,endText);
        //装载最后一个
        initLastText(endText);
        return true;
    }
    /**
     * 装载同一个面板中指定Text向下的全部组件
     * @param startText EText
     */
    public void initPanelDownText(EText startText)
    {
        EText text = startText.getNextTrueText();
        while(text != null)
        {
            CSelectTextBlock selectText = text.selectAll();
            if(selectText != null)
                getSelectedList().add(selectText);
            text = text.getNextTrueText();
        }
        EPanel panel = startText.getPanel().getEndPanel();
        getSelectedList().add(panel.createSelectedEnterModel());
    }
    /**
     * 装载同一个面板中指定Text向上的全部组件
     * @param endText EText
     */
    public void initPanelUpText(EText endText)
    {
        EText text = (EText)endText.getPanel().getHeadPanel().get(0);
        while(text != null && text != endText)
        {
            CSelectTextBlock selectText = text.selectAll();
            if(selectText != null)
                getSelectedList().add(selectText);
            text = text.getNextTrueText();
        }
    }
    /**
     * 装载同一面板中间的Text
     * @param startText EText
     * @param endText EText
     */
    public void initEqualsPanelText(EText startText,EText endText)
    {
        EText text = startText.getNextTrueText();
        while(text != null && text != endText)
        {
            CSelectTextBlock selectText = text.selectAll();
            if(selectText != null)
                getSelectedList().add(selectText);
            text = text.getNextTrueText();
        }
    }
    /**
     * 装载第一个Text
     * @param startText EText
     */
    public void initFirstText(EText startText)
    {
        //添加文本选蓝对象
        CSelectTextBlock selectText = startText.selectText(getDirection() == 1?getStartIndex():getEndIndex(),startText.getLength());
        if(selectText == null)
            return;
        //设置起始点
        selectText.setStartPoint(true);
        getSelectedList().add(selectText);
    }
    /**
     * 装载第最后一个Text
     * @param endText EText
     */
    public void initLastText(EText endText)
    {
        //添加文本选蓝对象
        CSelectTextBlock selectText = endText.selectText(0,getDirection() == 1?getEndIndex():getStartIndex());
        if(selectText == null)
            return;
        //设置结束点
        selectText.setStopPoint(true);
        getSelectedList().add(selectText);
    }
    /**
     * 得到开始Text
     * @return EText
     */
    public EText getStartText()
    {
        return (EText)(getDirection() == 1?getStartComponent():getEndComponent());
    }
    /**
     * 得到结束文本
     * @return EText
     */
    public EText getEndText()
    {
        return (EText)(getDirection() == 1?getEndComponent():getStartComponent());
    }
    /**
     * 得到开始行
     * @return ETR
     */
    public ETR getStartTR()
    {
        return (ETR)getStartComponent();
    }
    /**
     * 得到结束行
     * @return ETR
     */
    public ETR getEndTR()
    {
        return (ETR)getEndComponent();
    }
    /**
     * 选中一个对象处理
     * @return boolean
     */
    private boolean selectedOne()
    {
        if(getDirection() != 0)
            return false;
        //不是文本EText
        if(getStartType() != 1)
            return true;
        EText text = (EText)getStartComponent();
        if(getStartIndex() == getEndIndex())
            return true;
        //创建选择列表
        createSelectList();
        //添加文本选蓝对象
        CSelectTextBlock selectText = text.selectText(getStartIndex(),getEndIndex());
        if(selectText == null)
            return false;
        //设置起始点
        selectText.setStartPoint(true);
        //设置结束点
        selectText.setStopPoint(true);
        getSelectedList().add(selectText);
        return true;
    }
    /**
     * 是否没有选择
     * @return boolean
     */
    private boolean notSelect()
    {
        if(getStartType() != getEndType())
            return false;
        if(getStartComponent() != getEndComponent())
            return false;
        if(getStartType() == 2)
            return false;
        //选中EText
        if(getStartType() == 1 && getStartIndex() != getEndIndex())
            return false;
        return true;
    }
    /**
     * 得到选中方向
     * @return int 1 正向 2 逆向
     */
    private int checkDirection()
    {
        TList start = getStartPathIndex();
        TList end = getEndPathIndex();
        int count = Math.min(start.size(),end.size());
        for(int i = 0;i < count;i++)
        {
            int startIndex = TypeTool.getInt(start.get(i));
            int endIndex = TypeTool.getInt(end.get(i));
            if(startIndex < endIndex)
                return 1;
            if(startIndex > endIndex)
                return -1;
        }
        if(start.size() == end.size())
            return 0;
        if(start.size() < end.size())
            return 1;
        return -1;
    }
    /**
     * 设置焦点对象
     * @param focus EComponent
     */
    public void setFocus(EComponent focus)
    {
        this.focus = focus;
    }
    /**
     * 得到焦点对象
     * @return EComponent
     */
    public EComponent getFocus()
    {
        return focus;
    }
    /**
     * 设置焦点类型
     * @param focusType int
     * 1 EText
     * 2 ETD
     * 3 ETR Enter
     * 4 EPic
     * 5 EImage
     */
    public void setFocusType(int focusType)
    {
        this.focusType = focusType;
    }
    /**
     * 得到焦点类型
     * @return int
     * 1 EText
     * 2 ETR
     * 3 ETR Enter
     * 4 EPic
     * 5 EImage
     */
    public int getFocusType()
    {
        return focusType;
    }
    /**
     * 设置焦点位置
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        this.focusIndex = focusIndex;
    }
    /**
     * 得到焦点位置
     * @return int
     */
    public int getFocusIndex()
    {
        return focusIndex;
    }
    /**
     * 设置选中池
     * @param selectedPool TList
     */
    public void setSelectPool(TList selectedPool)
    {
        this.selectedPool = selectedPool;
    }
    /**
     * 得到选中池
     * @return TList
     */
    public TList getSelectedPool()
    {
        return selectedPool;
    }
    /**
     * 得到焦点面板
     * @return TList
     */
    public TList getFocusPanels()
    {
        TList list = new TList();
        //没有选蓝
        if(!isSelected())
        {
            switch(getFocusType())
            {
            case 1://EText
                list.add(((EText)getFocus()).getPanel().getHeadPanel());
                return list;
            case 2://ETR
                ((ETR)getFocus()).getPanelAll(list,false);
                return list;
            case 3://ETR Enter
                return null;
            }
            return null;
        }
        getFocusPanels(list,getSelectedList());
        for(int i = 0;i < getSelectedPool().size();i++)
        {
            TList selecteList = (TList)getSelectedPool().get(i);
            getFocusPanels(list,selecteList);
        }
        if(getFocusType() == 2)
            ((ETR)getFocus()).getPanelAll(list,false);
        return list;
    }
    private void getFocusPanels(TList list,TList selectList)
    {
        if(selectList == null || selectList.size() == 0)
            return;
        for(int i = 0;i < selectList.size();i++)
        {
            Object com = selectList.get(i);
            if(com instanceof CSelectTextBlock)
            {
                EPanel panel = ((CSelectTextBlock)com).getText().getPanel();
                if(list.indexOf(panel) == -1)
                    list.add(panel);
                continue;
            }
            if(com instanceof CSelectPanelEnterModel)
            {
                EPanel panel = ((CSelectPanelEnterModel)com).getPanel();
                if(list.indexOf(panel) == -1)
                    list.add(panel);
                continue;
            }
            if(com instanceof CSelectPanelModel)
            {
                ((CSelectPanelModel)com).getPanel().getPanelAll(list,false);
                continue;
            }
            if(com instanceof CSelectTRModel)
            {
                ((CSelectTRModel)com).getTR().getPanelAll(list,false);
                continue;
            }
            if(com instanceof CSelectTDModel)
            {
                ((CSelectTDModel)com).getTD().getPanelAll(list,false);
                continue;
            }
            if(com instanceof EText)
            {
                EPanel panel = ((EText) com).getPanel();
                if(list.indexOf(panel) == -1)
                    list.add(panel);
                continue;
            }
            if(com instanceof EPanel)
            {
                ((EPanel) com).getPanelAll(list,false);
                continue;
            }
            if(com instanceof ETR)
            {
                ((ETR) com).getPanelAll(list,false);
                continue;
            }
            if(com instanceof ETD)
            {
                ((ETD) com).getPanelAll(list,false);
                continue;
            }
        }
    }
    /**
     * 执行动作
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        //没有选蓝
        if(!isSelected())
        {
            if(getFocusType() == 2)
                ((ETR)getFocus()).exeAction(action);
            return;
        }
        action.addBlock();
        exeAction(getSelectedList(),action);
        if(getSelectedList().size() == 0)
            setSelectedList(null);
        for(int i = getSelectedPool().size() - 1;i >=0 ;i--)
        {
            TList selecteList = (TList)getSelectedPool().get(i);
            action.insertBlock();
            exeAction(selecteList,action);
            if(selecteList.size() == 0)
                getSelectedPool().remove(i);
        }
    }
    /**
     * 执行动作
     * @param selectList TList
     * @param action EAction
     */
    public void exeAction(TList selectList,EAction action)
    {
        if(selectList == null || selectList.size() == 0)
            return;

        if(action.getType() == EAction.DELETE)
        {
            for (int i = selectList.size() - 1; i >= 0; i--)
            {
                Object com = selectList.get(i);

                if (!(com instanceof IExeAction))
                    continue;
                if (!(com instanceof CSelectPanelEnterModel))
                    ((IExeAction) com).exeAction(action);
            }
            //如果是删除动作最后处理面板合并
            for (int i = selectList.size() - 1; i >= 0; i--)
            {
                Object com = selectList.get(i);
                if (!(com instanceof IExeAction))
                    continue;
                if (com instanceof CSelectPanelEnterModel)
                    ((IExeAction) com).exeAction(action);
            }
        }else
        {
            for (int i = 0; i < selectList.size(); i++)
            {
                Object com = selectList.get(i);
                if (!(com instanceof IExeAction))
                    continue;
                ((IExeAction) com).exeAction(action);
            }
        }
    }
    /**
     * 得到焦点文本
     * @return EText
     */
    public EText getFocusText()
    {
        return (EText)getFocus();
    }
    /**
     * 得到焦点TR
     * @return ETR
     */
    public ETR getFocusTR()
    {
        return (ETR)getFocus();
    }
    /**
     * 得到焦点TD
     * @return ETD
     */
    public ETD getFocusTD()
    {
        return (ETD)getFocus();
    }
    /**
     * 得到焦点Pic
     * @return EPic
     */
    public EPic getFocusPic()
    {
        return (EPic)getFocus();
    }
    /**
     * 得到焦点层索引
     * @return String
     */
    public String getFocusLayerString()
    {
        switch(getFocusType())
        {
        case 1://EText
            return getFocusText().getIndexString();
        case 2://ETR
            return getFocusTR().getIndexString();
        case 3://ETR Enter
            return getFocusTR().getIndexString();
        case 4://EPic
            return getFocusPic().getIndexString();
        }
        return "";
    }
    /**
     * 调试
     */
    public void debugShow()
    {
        
        if(!isSelected())
            return;
        //System.out.println("Selected Pool Size=" + getSelectedPool().size());
        for(int i = 0;i < getSelectedPool().size();i++)
        {
            TList list = (TList)getSelectedPool().get(i);
            //System.out.println("Pool " + i + " ----------");
            for(int j = 0;j < list.size();j++)
                System.out.println(j + " " + list.get(j));
        }
        for(int i = 0;i < getSelectedList().size();i++)
            System.out.println(i + " " + getSelectedList().get(i));
    }
}
