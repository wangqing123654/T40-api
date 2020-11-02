package com.dongyang.tui.text;

import com.dongyang.util.TList;
import com.dongyang.util.TypeTool;
import java.util.ArrayList;

/**
 *
 * <p>Title: ѡ�п���</p>
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
     * ���������
     */
    private MFocus focusManager;
    /**
     * ѡ����ʼ���
     */
    private EComponent startComponent;
    /**
     * �������
     */
    private EComponent focus;
    /**
     * ��������
     */
    private int focusType;
    /**
     * ����λ��
     */
    private int focusIndex;
    /**
     * ѡ����ʼλ��
     */
    private int startIndex;
    /**
     * ѡ����ʼ�к�
     */
    private int startRow;
    /**
     * ѡ����ʼ�к�
     */
    private int startColumn;
    /**
     * ��ʼ����
     */
    private int startType;
    /**
     * ѡ�н������
     */
    private EComponent endComponent;
    /**
     * ѡ�н���λ��
     */
    private int endIndex;
    /**
     * ѡ�н����к�
     */
    private int endRow;
    /**
     * ѡ�н����к�
     */
    private int endColumn;
    /**
     * ��������
     */
    private int endType;
    /**
     * ����
     * 1 ����
     * 2 ����
     */
    private int direction;
    /**
     * ѡ�г�
     */
    private TList selectedPool;
    /**
     * ѡ���б�
     */
    private TList selectedList;
    /**
     * ������
     * @param focusManager MFocus
     */
    public CSelectedModel(MFocus focusManager)
    {
        //��ʼ��ѡ�г�
        setSelectPool(new TList());
        setFocusManager(focusManager);
    }
    /**
     * ���ý��������
     * @param focusManager MFocus
     */
    public void setFocusManager(MFocus focusManager)
    {
        this.focusManager = focusManager;
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return focusManager;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getFocusManager().getPM();
    }
    /**
     * ����ѡ����ʼ���
     * @param startComponent EComponent
     */
    public void setStartComponent(EComponent startComponent)
    {
        this.startComponent = startComponent;
    }
    /**
     * �õ�ѡ����ʼ���
     * @return EComponent
     */
    public EComponent getStartComponent()
    {
        return startComponent;
    }
    /**
     * �õ���ʼ·��
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
     * ����ѡ����ʼλ��
     * @param startIndex int
     */
    public void setStartIndex(int startIndex)
    {
        this.startIndex = startIndex;
    }
    /**
     * �õ�ѡ����ʼλ��
     * @return int
     */
    public int getStartIndex()
    {
        return startIndex;
    }
    /**
     * ����ѡ����ʼ�к�
     * @param startRow int
     */
    public void setStartRow(int startRow)
    {
        this.startRow = startRow;
    }
    /**
     * �õ�ѡ����ʼ�к�
     * @return int
     */
    public int getStartRow()
    {
        return startRow;
    }
    /**
     * ����ѡ����ʼ�к�
     * @param startColumn int
     */
    public void setStartColumn(int startColumn)
    {
        this.startColumn = startColumn;
    }
    /**
     * �õ�ѡ����ʼ�к�
     * @return int
     */
    public int getStartColumn()
    {
        return startColumn;
    }
    /**
     * ������ʼ����
     * @param startType int
     */
    public void setStartType(int startType)
    {
        this.startType = startType;
    }
    /**
     * �õ���ʾ
     * @return int
     */
    public int getStartType()
    {
        return startType;
    }
    /**
     * ����ѡ�н������
     * @param endComponent EComponent
     */
    public void setEndComponent(EComponent endComponent)
    {
        this.endComponent = endComponent;
    }
    /**
     * �õ�ѡ�н������
     * @return EComponent
     */
    public EComponent getEndComponent()
    {
        return endComponent;
    }
    /**
     * �õ�����·��
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
     * ����ѡ�н���λ��
     * @param endIndex int
     */
    public void setEndIndex(int endIndex)
    {
        this.endIndex = endIndex;
    }
    /**
     * �õ�ѡ�н���λ��
     * @return int
     */
    public int getEndIndex()
    {
        return endIndex;
    }
    /**
     * ����ѡ�н����к�
     * @param endRow int
     */
    public void setEndRow(int endRow)
    {
        this.endRow = endRow;
    }
    /**
     * �õ�ѡ�н����к�
     * @return int
     */
    public int getEndRow()
    {
        return endRow;
    }
    /**
     * ����ѡ�н����к�
     * @param endColumn int
     */
    public void setEndColumn(int endColumn)
    {
        this.endColumn = endColumn;
    }
    /**
     * �õ�ѡ�н����к�
     * @return int
     */
    public int getEndColumn()
    {
        return endColumn;
    }
    /**
     * ���ý�������
     * @param endType int
     */
    public void setEndType(int endType)
    {
        this.endType = endType;
    }
    /**
     * �õ���������
     * @return int
     */
    public int getEndType()
    {
        return endType;
    }
    /**
     * ���÷���
     * @param direction int
     */
    public void setDirection(int direction)
    {
        this.direction = direction;
    }
    /**
     * �õ�����
     * @return int
     */
    public int getDirection()
    {
        return direction;
    }
    /**
     * �Ƿ�ѡ��
     * @return boolean
     */
    public boolean isSelected()
    {
        if(getSelectedPool().size() > 0)
            return true;
        return getSelectedList() != null;
    }
    /**
     * ���ѡ��
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
        //���ѡ�г�
        clearSelectedPool();
        //��յ�ǰѡ�ж����б�
        clearSelectedList(getSelectedList());
        setSelectedList(null);
    }
    /**
     * ѹ��ѡ���б�
     */
    public void putSelected()
    {
        if(getSelectedList() == null || getSelectedList().size() == 0)
            return;
        getSelectedPool().add(getSelectedList());
        setSelectedList(null);
    }
    /**
     * ���ѡ�г�
     */
    public void clearSelectedPool()
    {
        TList list = getSelectedPool();
        for(int i = 0;i < list.size();i++)
            clearSelectedList((TList)list.get(i));
        list.removeAll();
    }
    /**
     * ɾ��һ��ѡ���б�
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
     * ���ѡ���б�
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
     * ���ÿ�ʼѡ��
     */
    public void setStartSelected()
    {
        setStartType(getFocusType());
        setStartComponent(getFocus());
        setStartIndex(getFocusIndex());
    }
    /**
     * ���ý���ѡ��
     */
    public void setEndSelected()
    {
        //�����ǰ��ѡ������
        clearSelectedList(getSelectedList());
        //���ý�����
        setEndType(getFocusType());
        setEndComponent(getFocus());
        setEndIndex(getFocusIndex());
        selectCheck();
        if(getSelectedList() != null && getSelectedList().size() == 0)
            setSelectedList(null);
        getFocusManager().showCursor();
    }
    /**
     * ����ѡ���б�
     */
    public void createSelectList()
    {
        if(selectedList != null)
            return;
        selectedList = new TList();
    }
    /**
     * ����ѡ���б�
     * @param selectedList TList
     */
    public void setSelectedList(TList selectedList)
    {
        this.selectedList = selectedList;
    }
    /**
     * �õ�ѡ���б�
     * @return TList
     */
    public TList getSelectedList()
    {
        return selectedList;
    }
    /**
     * ѡ����
     */
    public void selectCheck()
    {
        //û�ж�ѡ
        if(notSelect())
            return;
        //ѡ��һ����
        if(selectOneTR())
            return;
        setDirection(checkDirection());
        //ѡ��ͬһ��Table���TR
        if(selectMoreTR())
            return;
        //ѡ��һ��������
        if(selectedOne())
            return;
        //ѡ��ͬһ������в�ͬ���ı�
        if(selectedEqualsPanelText())
            return;
        //ѡ��ͬ�����
        if(selectClassPanelText())
            return;
        //ѡ��ͬ��Text ��Table��
        if(selectTextToTR())
            return;
        //ѡ��ͬ��Table�е�Text
        if(selectTRToText())
            return;
        //ѡ��Text������е�Text
        if(selectTextToTRText())
            return;
        //ѡ��TD��TD
        if(selectTDToTD())
            return;
        //ѡ��TD��TR
        if(selectTDToTR())
            return;
        //ѡ��TR��TD
        if(selectTRToTD())
            return;
        //ѡ��TR��TR
        if(selectTRToTR())
            return;
    }
    /**
     * ѡ��TR��TR
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
        //����ͬ��
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        //����ѡ���б�
        createSelectList();
        startTR = startTR.getHeadTR();
        endTR = endTR.getHeadTR();
        //װ�ص�һ��TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //װ�ص�һ��Table
        initTableDownTR(startTR);
        //װ���м�Panel
        initPanel(startTR.getPanel(),endTR.getPanel());
        //װ�����һ��Table
        initTableUpTR(endTR);
        //װ�����һ��TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * ѡ��TR��TD
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
        //����ͬһ�������
        if(startTable == null || endTable == null)
            return false;
        if(startTable.tableIsNextListTable(endTable))
        {
            //��ͬһ������
            if (((getDirection() == 1 && getStartType() == 2) ||
                 (getDirection() == -1 && getEndType() == 2)) &&
                endText.getTD().getHeadTD() == tr.getHeadTR().get(0))
            {
                ETD td = endText.getTD();
                //����ѡ���б�
                createSelectList();
                getSelectedList().add(td.createSelectedModel());
                td.setSelectedAll(true);
                return true;
            }
            //�õ�ѡ����������
            setStartRow(tr.getRow());
            if ((getDirection() == 1 && getStartType() == 2) ||
                (getDirection() == -1 && getEndType() == 2))
                setStartColumn(0);
            else
                setStartColumn(tr.size() - 1);
            setEndRow(endText.getTableRow());
            setEndColumn(endText.getTableColumn());
            //����ѡ���б�
            createSelectList();
            //ѡ��TD
            selectTD(startTable);
            return true;
        }
        EPanel startPanel = startTable.getPanel();
        EPanel endPanel = endTable.getPanel();
        //����ͬ��
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        ETR startTR = tr.getHeadTR();
        ETR endTR = endText.getTR().getHeadTR();
        //����ѡ���б�
        createSelectList();
        //װ�ص�һ��TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //װ�ص�һ��Table
        initTableDownTR(startTR);
        //װ���м�Panel
        initPanel(startPanel,endPanel);
        //װ�����һ��Table
        initTableUpTR(endTR);
        //װ�����һ��TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * ѡ��TD��TR
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
        //��ͬһ�������
        if(startTable.tableIsNextListTable(endTable))
        {
            //�õ�ѡ����������
            setStartRow(startText.getTableRow());
            setStartColumn(startText.getTableColumn());
            setEndRow(tr.getRow());
            if ((getDirection() == 1 && getEndType() == 2) ||
                (getDirection() == -1 && getStartType() == 2))
                setEndColumn(0);
            else
                setEndColumn(tr.size() - 1);
            //����ѡ���б�
            createSelectList();
            //ѡ��TD
            selectTD(startTable);
            return true;
        }
        EPanel startPanel = startTable.getPanel();
        EPanel endPanel = endTable.getPanel();
        //����ͬ��
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        ETR startTR = startText.getTR().getHeadTR();
        ETR endTR = tr.getHeadTR();
        //����ѡ���б�
        createSelectList();
        //װ�ص�һ��TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //װ�ص�һ��Table
        initTableDownTR(startTR);
        //װ���м�Panel
        initPanel(startPanel,endPanel);
        //װ�����һ��Table
        initTableUpTR(endTR);
        //װ�����һ��TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * �ܷ�ϲ�ͬ��Ԫ��
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
     * ѡ��TD��TD
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
        //��ͬһ�������
        if(startTable.tableIsNextListTable(endTable))
        {
            //�õ�ѡ����������
            setStartRow(startText.getTableRow());
            setStartColumn(startText.getTableColumn());
            setEndRow(endText.getTableRow());
            setEndColumn(endText.getTableColumn());
            //����ѡ���б�
            createSelectList();
            //ѡ��TD
            selectTD(startTable);
            return true;
        }
        EPanel startPanel = startTable.getPanel();
        EPanel endPanel = endTable.getPanel();
        //����ͬ��
        if(!startPanel.panelIsClassPanel(endPanel))
            return false;
        ETR startTR = startText.getTR().getHeadTR();
        ETR endTR = endText.getTR().getHeadTR();
        //����ѡ���б�
        createSelectList();
        //װ�ص�һ��TR
        getSelectedList().add(startTR.createSelectedModel());
        startTR.setSelectedAll(true);
        //װ�ص�һ��Table
        initTableDownTR(startTR);
        //װ���м�Panel
        initPanel(startPanel,endPanel);
        //װ�����һ��Table
        initTableUpTR(endTR);
        //װ�����һ��TR
        getSelectedList().add(endTR.createSelectedModel());
        endTR.setSelectedAll(true);
        return true;
    }
    /**
     * ѡ��TD
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
     * ѡ��Text������е�Text
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
        //��Text��TD
        if(endTablePanel != null && startText.getPanel().panelIsClassPanel(endTablePanel))
        {
            //����ѡ���б�
            createSelectList();
            //ѡ�д�Text ��TR
            selectTextToTR(startText,endText.getTR());
            return true;
        }
        //��TD��Text
        if(startTablePanel != null && startTablePanel.panelIsClassPanel(endText.getPanel()))
        {
            //����ѡ���б�
            createSelectList();
            //ѡ��TR��Text
            selectTRToText(startText.getTR(),endText);
            return true;
        }
        return false;
    }
    /**
     * ѡ��ͬһ��Table���TR
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
        //�����Ƿ���ͬһ��Table��������Table
        if(!startTR.getTable().tableIsNextListTable(endTR.getTable()))
            return false;
        //����ѡ���б�
        createSelectList();
        //װ��������֮����
        initTableTR(startTR,endTR);
        return true;
    }
    /**
     * װ��������֮����
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
     * ѡ��һ����
     * @return boolean
     */
    public boolean selectOneTR()
    {
        if(getStartComponent() != getEndComponent())
            return false;
        if(getStartType() != 2 && getStartType() != 3)
            return false;
        ETR startTR = getStartTR();
        //����ѡ���б�
        createSelectList();
        //װ�ص�һ��ѡ����
        getSelectedList().add(startTR.getHeadTR().createSelectedModel());
        startTR.setSelectedAll(true);
        return true;
    }
    /**
     * ѡ��ͬ��Table�е�Text
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
        //����ͬһ����������������
        if(!startTR.getPanel().panelIsClassPanel(endText.getPanel()))
            return false;
        //����ѡ���б�
        createSelectList();
        //ѡ��TR��Text
        selectTRToText(startTR,endText);
        return true;
    }
    /**
     * ѡ��TR��Text
     * @param startTR ETR
     * @param endText EText
     */
    private void selectTRToText(ETR startTR,EText endText)
    {
        //װ�ص�һ��ѡ����
        getSelectedList().add(startTR.getHeadTR().createSelectedModel());
        startTR.setSelectedAll(true);
        //װ��ָ����֮���ȫ����
        initTableDownTR(startTR);
        //װ���м�Panel
        initPanel(startTR.getPanel(),endText.getPanel());
        //װ��������
        initPanelUpText(endText);
        //װ�����һ��
        initLastText(endText);
    }
    /**
     * ѡ��ͬ��Text ��Table��
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
        //����ͬһ����������������
        if(!startText.getPanel().panelIsClassPanel(endTR.getPanel()))
            return false;
        //����ѡ���б�
        createSelectList();
        //ѡ�д�Text ��TR
        selectTextToTR(startText,endTR);
        return true;
    }
    /**
     * ѡ�д�Text ��TR
     * @param startText EText
     * @param endTR ETR
     */
    private void selectTextToTR(EText startText,ETR endTR)
    {
        //װ�ص�һ�����
        initFirstText(startText);
        //װ�ص�һ�����
        initPanelDownText(startText);
        //װ���м�Panel
        initPanel(startText.getPanel(),endTR.getPanel());
        //װ��ָ����֮ǰ��ȫ����
        initTableUpTR(endTR);
        //װ�����ѡ����
        getSelectedList().add(endTR.getHeadTR().createSelectedModel());
        endTR.setSelectedAll(true);
    }
    /**
     * װ��ָ����֮���ȫ����
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
     * װ��ָ����֮ǰ��ȫ����
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
     * ѡ��ͬ�����
     * @return boolean
     */
    private boolean selectClassPanelText()
    {
        if(getDirection() == 0 || getStartType() != 1 || getEndType() != 1)
            return false;
        EText startText = getStartText();
        EText endText = getEndText();
        //����ͬһ����������������
        if(!startText.getPanel().panelIsClassPanel(endText.getPanel()))
            return false;
        //����ѡ���б�
        createSelectList();
        //װ�ص�һ�����
        initFirstText(startText);
        //װ�ص�һ�����
        initPanelDownText(startText);
        //װ���м�Panel
        initPanel(startText.getPanel(),endText.getPanel());
        //װ��������
        initPanelUpText(endText);
        //װ�����һ��
        initLastText(endText);
        return true;
    }
    /**
     * װ��Panel
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
     * ѡ��ͬһ������в�ͬ���ı�
     * @return boolean
     */
    private boolean selectedEqualsPanelText()
    {
        if(getDirection() == 0 || getStartType() != 1 || getEndType() != 1)
            return false;
        EText startText = getStartText();
        EText endText = getEndText();
        //����ͬһ����������������
        if(!startText.getPanel().panelIsNextListPanel(endText.getPanel()))
            return false;
        //����ѡ���б�
        createSelectList();
        //װ�ص�һ�����
        initFirstText(startText);
        //װ��ͬһ����м��Text
        initEqualsPanelText(startText,endText);
        //װ�����һ��
        initLastText(endText);
        return true;
    }
    /**
     * װ��ͬһ�������ָ��Text���µ�ȫ�����
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
     * װ��ͬһ�������ָ��Text���ϵ�ȫ�����
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
     * װ��ͬһ����м��Text
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
     * װ�ص�һ��Text
     * @param startText EText
     */
    public void initFirstText(EText startText)
    {
        //����ı�ѡ������
        CSelectTextBlock selectText = startText.selectText(getDirection() == 1?getStartIndex():getEndIndex(),startText.getLength());
        if(selectText == null)
            return;
        //������ʼ��
        selectText.setStartPoint(true);
        getSelectedList().add(selectText);
    }
    /**
     * װ�ص����һ��Text
     * @param endText EText
     */
    public void initLastText(EText endText)
    {
        //����ı�ѡ������
        CSelectTextBlock selectText = endText.selectText(0,getDirection() == 1?getEndIndex():getStartIndex());
        if(selectText == null)
            return;
        //���ý�����
        selectText.setStopPoint(true);
        getSelectedList().add(selectText);
    }
    /**
     * �õ���ʼText
     * @return EText
     */
    public EText getStartText()
    {
        return (EText)(getDirection() == 1?getStartComponent():getEndComponent());
    }
    /**
     * �õ������ı�
     * @return EText
     */
    public EText getEndText()
    {
        return (EText)(getDirection() == 1?getEndComponent():getStartComponent());
    }
    /**
     * �õ���ʼ��
     * @return ETR
     */
    public ETR getStartTR()
    {
        return (ETR)getStartComponent();
    }
    /**
     * �õ�������
     * @return ETR
     */
    public ETR getEndTR()
    {
        return (ETR)getEndComponent();
    }
    /**
     * ѡ��һ��������
     * @return boolean
     */
    private boolean selectedOne()
    {
        if(getDirection() != 0)
            return false;
        //�����ı�EText
        if(getStartType() != 1)
            return true;
        EText text = (EText)getStartComponent();
        if(getStartIndex() == getEndIndex())
            return true;
        //����ѡ���б�
        createSelectList();
        //����ı�ѡ������
        CSelectTextBlock selectText = text.selectText(getStartIndex(),getEndIndex());
        if(selectText == null)
            return false;
        //������ʼ��
        selectText.setStartPoint(true);
        //���ý�����
        selectText.setStopPoint(true);
        getSelectedList().add(selectText);
        return true;
    }
    /**
     * �Ƿ�û��ѡ��
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
        //ѡ��EText
        if(getStartType() == 1 && getStartIndex() != getEndIndex())
            return false;
        return true;
    }
    /**
     * �õ�ѡ�з���
     * @return int 1 ���� 2 ����
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
     * ���ý������
     * @param focus EComponent
     */
    public void setFocus(EComponent focus)
    {
        this.focus = focus;
    }
    /**
     * �õ��������
     * @return EComponent
     */
    public EComponent getFocus()
    {
        return focus;
    }
    /**
     * ���ý�������
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
     * �õ���������
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
     * ���ý���λ��
     * @param focusIndex int
     */
    public void setFocusIndex(int focusIndex)
    {
        this.focusIndex = focusIndex;
    }
    /**
     * �õ�����λ��
     * @return int
     */
    public int getFocusIndex()
    {
        return focusIndex;
    }
    /**
     * ����ѡ�г�
     * @param selectedPool TList
     */
    public void setSelectPool(TList selectedPool)
    {
        this.selectedPool = selectedPool;
    }
    /**
     * �õ�ѡ�г�
     * @return TList
     */
    public TList getSelectedPool()
    {
        return selectedPool;
    }
    /**
     * �õ��������
     * @return TList
     */
    public TList getFocusPanels()
    {
        TList list = new TList();
        //û��ѡ��
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
     * ִ�ж���
     * @param action EAction
     */
    public void exeAction(EAction action)
    {
        //û��ѡ��
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
     * ִ�ж���
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
            //�����ɾ��������������ϲ�
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
     * �õ������ı�
     * @return EText
     */
    public EText getFocusText()
    {
        return (EText)getFocus();
    }
    /**
     * �õ�����TR
     * @return ETR
     */
    public ETR getFocusTR()
    {
        return (ETR)getFocus();
    }
    /**
     * �õ�����TD
     * @return ETD
     */
    public ETD getFocusTD()
    {
        return (ETD)getFocus();
    }
    /**
     * �õ�����Pic
     * @return EPic
     */
    public EPic getFocusPic()
    {
        return (EPic)getFocus();
    }
    /**
     * �õ����������
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
     * ����
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
