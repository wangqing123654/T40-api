package com.dongyang.tui.text.ui;

import com.dongyang.tui.DComponent;
import com.dongyang.tui.text.ETable;
import com.dongyang.tui.text.MCTable;
import com.dongyang.tui.DButton;
import com.dongyang.jdo.TJDODBTool;
import com.dongyang.data.TParm;
import com.dongyang.tui.DText;
import com.dongyang.tui.text.PM;
import com.dongyang.tui.text.MFocus;
import com.dongyang.util.TypeTool;
import com.dongyang.tui.text.EColumnModel;
import com.dongyang.tui.text.ESyntaxItem;
import com.dongyang.tui.text.ETD;
import com.dongyang.tui.text.ETR;
import com.dongyang.tui.text.ETRModel;
import com.dongyang.tui.text.EMacroroutineModel;
import com.dongyang.tui.text.EPanel;
import com.dongyang.tui.text.EMacroroutine;
import com.dongyang.tui.text.MSyntax;
import com.dongyang.tui.text.MMacroroutine;
import com.dongyang.tui.text.EGroupModelList;
import com.dongyang.tui.text.EGroupModel;
import com.dongyang.util.TList;
import com.dongyang.util.StringTool;
import java.io.IOException;
import com.dongyang.tui.io.DataOutputStream;
import com.dongyang.tui.io.DataInputStream;
import com.dongyang.tui.text.SyntaxControl;
import java.awt.Font;

/**
 *
 * <p>Title: Table������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.16
 * @version 1.0
 */
public class CTable extends DComponent
{
    /**
     * Table����
     */
    private ETable table;
    /**
     * Table������������
     */
    private MCTable cTableManager;
    /**
     * y���
     */
    private int spacingY;
    /**
     * SQL���
     */
    private String sql;
    /**
     * ���ݰ�
     */
    private TParm data;
    /**
     * ��ģ��
     */
    private EColumnModel columnModel;
    /**
     * �Ƿ�����ܼ�
     */
    private boolean hasSum;
    /**
     * ���������к�
     */
    private int insertDataRow;
    /**
     * ����
     */
    private TParm group;
    /**
     * ������ʾ��������
     */
    private boolean groupShowData;
    /**
     * ��ģ���б�
     */
    private EGroupModelList groupModelList;
    /**
     * Table���
     */
    private String tableID = "";
    /**
     * ��������
     */
    private boolean inputData;
    /**
     * ������
     */
    public CTable()
    {
        setBorder("��");
        //setText("��ѯ");
        setBounds(10,10,100,100);
        setMouseMoveType(1);
        setMouseBorderDraggedType(255);
        DButton b = new DButton();
        b.setBounds(0,0,50,20);
        b.setText("��ѯ");
        b.setActionMap("onClicked","onQuery");
        addPublicMethod("onQuery",this);
        addDComponent(b);
        //��ʼ����ģ��
        setColumnModel(new EColumnModel(this));
        //��ʼ����ģ���б�
        setGroupModelList(new EGroupModelList(this));

    }
    /**
     * ����Table������������
     * @param cTableManager MCTable
     */
    public void setCTableManager(MCTable cTableManager)
    {
        this.cTableManager = cTableManager;
    }
    /**
     * �õ�Table������������
     * @return MCTable
     */
    public MCTable getCTableManager()
    {
        return cTableManager;
    }
    /**
     * �õ�������
     * @return PM
     */
    public PM getPM()
    {
        return getCTableManager().getPM();
    }
    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
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
     * �õ��������
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager()
    {
        return getPM().getMacroroutineManager();
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
     * ������
     * @param table ETable
     */
    public CTable(ETable table)
    {
        this();
        setTable(table);
    }
    /**
     * ����Table����
     * @param table ETable
     */
    public void setTable(ETable table)
    {
        this.table = table;
    }
    /**
     * �õ�Table����
     * @return ETable
     */
    public ETable getTable()
    {
        return table;
    }
    /**
     * �õ�����������ߴ�
     * @return int
     */
    public int getVScrollBarValue()
    {
        return 0;
    }
    /**
     * ���������ֵ�ı�
     * @param value int
     */
    public void onVScrollBarChangeValue(int value)
    {
        if(getTable() == null)
            return;
        setY(getTable().getScreenY() - spacingY - value);
    }
    /**
     * ����SQL���
     * @param sql String
     */
    public void setSQL(String sql)
    {
        this.sql = sql;
    }
    /**
     * �õ�SQL���
     * @return String
     */
    public String getSQL()
    {
        return sql;
    }
    /**
     * �������ݰ�
     * @param data TParm
     */
    public void setData(TParm data)
    {
        this.data = data;
    }
    /**
     * �õ����ݰ�
     * @return TParm
     */
    public TParm getData()
    {
        return data;
    }
    /**
     * ������ģ��
     * @param columnModel EColumnModel
     */
    public void setColumnModel(EColumnModel columnModel)
    {
        this.columnModel = columnModel;
    }
    /**
     * �õ���ģ��
     * @return EColumnModel
     */
    public EColumnModel getColumnModel()
    {
        return columnModel;
    }
    /**
     * ���ò���������
     * @param insertDataRow int
     */
    public void setInsertDataRow(int insertDataRow)
    {
        this.insertDataRow = insertDataRow;
    }
    /**
     * �õ�����������
     * @return int
     */
    public int getInsertDataRow()
    {
        return insertDataRow;
    }
    /**
     * ���ô����ܼ�
     * @param hasSum boolean
     */
    public void setHasSum(boolean hasSum)
    {
        this.hasSum = hasSum;
    }
    /**
     * �Ƿ�����ܼ�
     * @return boolean
     */
    public boolean hasSum()
    {
        return hasSum;
    }
    /**
     * ���÷���
     * @param group TParm
     */
    public void setGroup(TParm group)
    {
        this.group = group;
    }
    /**
     * �õ�����
     * @return TParm
     */
    public TParm getGroup()
    {
        return group;
    }
    /**
     * ���ڷ���
     * @return boolean
     */
    public boolean hasGroup()
    {
        return getGroup() != null && getGroup().getCount() > 0;
    }
    /**
     * ������ģ��
     * @param groupModelList EGroupModelList
     */
    public void setGroupModelList(EGroupModelList groupModelList)
    {
        this.groupModelList = groupModelList;
    }
    /**
     * �õ���ģ��
     * @return EGroupModelList
     */
    public EGroupModelList getGroupModelList()
    {
        return groupModelList;
    }
    /**
     * ���÷�����ʾ��������
     * @param groupShowData boolean
     */
    public void setGroupShowData(boolean groupShowData)
    {
        this.groupShowData = groupShowData;
    }
    /**
     * �����Ƿ���ʾ��������
     * @return boolean
     */
    public boolean isGroupShowData()
    {
        return groupShowData;
    }
    /**
     * �������λ��
     * @param item CTable
     * @return int
     */
    public int findIndex(CTable item)
    {
        return getCTableManager().indexOf(item);
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
     * ��ʼ��
     */
    public void init()
    {
        int columnCount = table.getColumnCount();
        //�г�ʼ��
        for(int i = 0;i < columnCount;i++)
        {
            EMacroroutineModel model = new EMacroroutineModel();
            getMacroroutineManager().add(model);
            //�����﷨
            ESyntaxItem syntax = model.createSyntax();
            syntax.setName("#" + i);
            syntax.setSyntax("#" + i);
            getColumnModel().add(model);
        }
        //�����ʼ��
        if(hasGroup())
        {
            for(int group = 0;group < getGroup().getCount("ID");group ++)
            {
                //�½���ģ��
                EGroupModel groupModel = getGroupModelList().newGroupModel();
                //��������
                groupModel.setID(TypeTool.getInt(getGroup().getData("ID",group)));
                //����������
                groupModel.setName(TypeTool.getString(getGroup().getData("NAME",group)));
                //�����Ƿ���ʾ����
                groupModel.setShowData(TypeTool.getBoolean(getGroup().getData("DATA",group)));
                //��ʾ�����
                boolean showHead = TypeTool.getBoolean(getGroup().getData("TITLE",group));
                //��ʾ��ϼ�
                boolean showSum = TypeTool.getBoolean(getGroup().getData("SUM",group));
                if(showHead)
                {
                    EColumnModel columnModel = new EColumnModel(this);
                    groupModel.setHead(columnModel);
                    for(int i = 0;i < columnCount;i++)
                    {
                        EMacroroutineModel model = new EMacroroutineModel();
                        getMacroroutineManager().add(model);
                        //�����﷨
                        ESyntaxItem syntax = model.createSyntax();
                        syntax.setName(groupModel.getName());
                        syntax.setSyntax("\"" + groupModel.getName() + "\"");
                        columnModel.add(model);
                    }
                }
                if(showSum)
                {
                    EColumnModel columnModel = new EColumnModel(this);
                    groupModel.setSum(columnModel);
                    for(int i = 0;i < columnCount;i++)
                    {
                        EMacroroutineModel model = new EMacroroutineModel();
                        getMacroroutineManager().add(model);
                        //�����﷨
                        ESyntaxItem syntax = model.createSyntax();
                        syntax.setName("sum(" + groupModel.getName() + ")");
                        syntax.setSyntax("sum(#" + i + ")");
                        columnModel.add(model);
                    }
                }
            }
        }
        //��ʾ�б༭��
        showColumnEditTR();
        int row = getInsertDataRow();
        //����༭��
        if(hasGroup())
            row = showColumnGroupEditTR();
        //�ܼƱ༭��
        if(hasSum())
            showMaxColumnTR(row + 1);
        //�������
        if(!getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return;
        }
    }
    /**
     * ��ʾ�б༭��
     */
    public void showColumnEditTR()
    {
        //�����ʾ����
        clearShowData();
        int row = table.insertRow(getInsertDataRow(),false);
        int columnCount = table.getColumnCount();
        for(int i = 0;i < columnCount;i++)
        {
            EMacroroutineModel model = getColumnModel().get(i);
            ETD td = table.getTDAt(row,i);
            if(td == null)
                continue;
            td.removeAll();
            td.setColumnIndex(i);
            EPanel panel = td.newPanel();
            panel.setAlignment(model.getAlignment());
            EMacroroutine macroroutine = panel.newMacroroutine();
            macroroutine.setModel(model);
            if(model.getFont() != null)
                macroroutine.setFont(model.getFont());
            model.setMacroroutine(macroroutine);
            model.show();
        }
        ETR tr = table.get(row);
        ETRModel trModel = tr.createModel();
        trModel.setType(ETRModel.COLUMN_EDIT);
    }
    /**
     * ��ʾ����
     * @return int
     */
    public int showColumnGroupEditTR()
    {
        int index = getInsertDataRow() + 1;
        for(int i = 0;i < getGroupModelList().size();i++)
        {
            EGroupModel group = getGroupModelList().get(i);
            EColumnModel head = group.getHead();
            if(head != null)
            {
                int row = table.insertRow(index,false);
                index ++;
                for (int j = 0; j < head.size(); j++)
                {
                    EMacroroutineModel model = head.get(j);
                    ETD td = table.getTDAt(row,j);
                    if(td == null)
                        continue;
                    td.removeAll();
                    td.setColumnIndex(i);
                    EPanel panel = td.newPanel();
                    panel.setAlignment(model.getAlignment());
                    EMacroroutine macroroutine = panel.newMacroroutine();
                    macroroutine.setModel(model);
                    if(model.getFont() != null)
                        macroroutine.setFont(model.getFont());
                    model.setMacroroutine(macroroutine);
                    model.show();
                }
                ETR tr = table.get(row);
                ETRModel trModel = tr.createModel();
                trModel.setType(ETRModel.GROUP_HEAD_EDIT);
            }
            EColumnModel sum = group.getSum();
            if(sum != null)
            {
                int row = table.insertRow(index,false);
                index ++;
                for (int j = 0; j < sum.size(); j++)
                {
                    EMacroroutineModel model = sum.get(j);
                    ETD td = table.getTDAt(row,j);
                    if(td == null)
                        continue;
                    td.removeAll();
                    EPanel panel = td.newPanel();
                    EMacroroutine macroroutine = panel.newMacroroutine();
                    macroroutine.setModel(model);
                    if(model.getFont() != null)
                        macroroutine.setFont(model.getFont());
                    model.setMacroroutine(macroroutine);
                    model.show();
                }
                ETR tr = table.get(row);
                ETRModel trModel = tr.createModel();
                trModel.setType(ETRModel.GROUP_SUM_EDIT);
            }
        }
        return index - 1;
    }
    /**
     * ��ʾ�ܼ�
     * @param index int
     */
    public void showMaxColumnTR(int index)
    {
        //������
        int row = table.insertRow(index, false);
        int columnCount = table.getColumnCount();
        for (int i = 0; i < columnCount; i++)
        {
            ETD td = table.getTDAt(row, i);
            if (td == null)
                continue;
            td.removeAll();
            EPanel panel = td.newPanel();
            EMacroroutine macroroutine = panel.newMacroroutine();
            EMacroroutineModel model = macroroutine.createModel();
            //�����﷨
            ESyntaxItem syntax = model.createSyntax();
            syntax.setName("sum(#" + i + ")");
            syntax.setSyntax("sum(#" + i + ")");
            model.show();
        }
        ETR tr = table.get(row);
        //����model
        ETRModel trModel = tr.createModel();
        //��������
        trModel.setType(ETRModel.COLUMN_MAX);
    }
    /**
     * ��ѯ����
     * @return int ����
     */
    public int retrieve()
    {
        if(isInputData())
        {
            Object obj = getPM().getFileManager().getParameter();
            if(obj == null || !(obj instanceof TParm))
                return 0;
            setData(((TParm)obj).getParm(getTableID()));
            return getData().getCount();
        }
        String sql = getSQL();
        if(sql == null || sql.length() == 0)
        {
            System.out.println("CTable.retrieve() sql���Ϊ��");
            return -1;
        }
        //����SQL
        sql = parseSQL(sql);
        if(getSyntaxManager().getControl() == null)
            return -1;
        TJDODBTool tool = TJDODBTool.getInstance();
        TParm parm = new TParm(tool.select(sql));
        if(parm.getErrCode() < 0)
        {
            System.out.println("CTable.retrieve()���� " + parm.getErrText());
            System.out.println("SQL=" + sql);
            return -1;
        }
        setData(parm);
        return parm.getCount();
    }
    /**
     * ����SQL
     * @param sql String
     * @return String
     */
    public String parseSQL(String sql)
    {
        int index = 0;
        index = sql.indexOf("<",index);
        while(index != -1)
        {
            int startIndex = sql.indexOf("<", index + 1);
            int endIndex = sql.indexOf(">", index + 1);
            if (endIndex == -1)
                return sql;
            if (startIndex != -1 && startIndex < endIndex)
                index = startIndex;
            String s = sql.substring(index + 1, endIndex);
            String value = getSQLParm(s);
            if (value != null)
            {
                sql = sql.substring(0, index) + value +
                    sql.substring(endIndex + 1);
                index += value.length();
            } else
                index = endIndex + 1;
            index = sql.indexOf("<",index);
        }
        return sql;
    }
    /**
     * �õ�SQL����
     * @param name String
     * @return String
     */
    public String getSQLParm(String name)
    {
        SyntaxControl control = getSyntaxManager().getControl();
        if(control == null)
            return null;
        return control.getTableParm(getTableID(),name);
    }
    /**
     * ��ѯ
     */
    public void onQuery()
    {
        if(table == null)
            return;
        if(retrieve() < 0)
            return;
        //��ʾ����
        showData();
    }
    /**
     * ��ʾ�༭
     */
    public void showEdit()
    {
        if(table == null)
            return;
        //��ʾ�б༭��
        showColumnEditTR();
        //����༭��
        if(hasGroup())
            showColumnGroupEditTR();
    }
    /**
     * �����ʾ����
     */
    public void clearShowData()
    {
        if(table != null)
            //ɾ��������
            table.getModel().removeDataRow();
        //������Ϲ�����
        getColumnModel().clearColumnMacroroutine();
    }
    public String getValue(String s,String name)
    {
        String ss[] = StringTool.parseLine(s,";");
        for(int i = 0;i < ss.length;i++)
        {
            int index = ss[i].indexOf("=");
            String s1 = ss[i].substring(0,index);
            String s2 = ss[i].substring(index + 1).trim();
            if(!s1.toUpperCase().trim().equals(name.toUpperCase().trim()))
                continue;
            return s2;
        }
        return "";
    }

    /**
     * ����������ʾ����
     */
    public void showDataIndependent(){
        int columnCount = table.getColumnCount();
        if(columnCount <= 0)
            return;
        EMacroroutineModel modelFirst = getColumnModel().get(0);
        int rowCount = modelFirst.getShowValueList().length;
        int row = getInsertDataRow();
        for (int i = 0;i < rowCount;i++) {
            row = table.insertRow(row,false);
            ETR tr = table.get(row);
            //����model
            ETRModel trModel = tr.createModel();
            //��������
            trModel.setType(ETRModel.COLUMN_TYPE);
            //�����к�
            trModel.setRow(i);
            for(int j = 0; j < columnCount; j++){
                EMacroroutineModel model = getColumnModel().get(j);
                ETD td = table.getTDAt(row, j);
                if (td == null)
                    continue;
                td.removeAll();
                td.setColumnIndex(j);
                EPanel panel = td.newPanel();
                panel.setAlignment(model.getAlignment());
                if(model.getEMacroroutine(i) != null)
                    panel.add(model.getEMacroroutine(i));
                else{
                    EMacroroutine macroroutine = panel.newMacroroutine();
                    macroroutine.setFreeModel(false);
                    macroroutine.setModel(model);
                    if(model.getFont() != null)
                        macroroutine.setFont(model.getFont());
                    model.addMacroroutine(macroroutine);
                }
            }
            row++;
        }
    }

    /**
     * ��ʾ����
     */
    public void showData()
    {
        if(table == null)
            return;
        //�����ʾ����
        clearShowData();
   
        if(getData() == null){
            if(getPM().getSyntaxManager().getControl() != null)
                return;
            showDataIndependent();
            return;
        }
        if(getData().existData("Visible"))
        {
            if(!getData().getBoolean("Visible"))
                return;
        }
        if(hasGroup())
        {
            //��ʾ��������
            showGroupData();
            return;
        }
        int count = getData().getCount();

        int columnCount = table.getColumnCount();
        
        int row = getInsertDataRow();
        
        if(count <= 0)
            showDataIndependent();
        //��������
        for(int i = 0;i < count;i++)
        {

            String v = "";
            if(getData().existData("TABLE_VALUE"))
                v = getData().getValue("TABLE_VALUE",i);
               
            //������
            row = table.insertRow(row,false);
            ETR tr = table.get(row);
            //����model
            ETRModel trModel = tr.createModel();
            //��������
            trModel.setType(ETRModel.COLUMN_TYPE);
            //�����к�
            trModel.setRow(i);
            for(int j = 0;j < columnCount;j++)
            {
            	
                EMacroroutineModel model = getColumnModel().get(j);
                ETD td = table.getTDAt(row,j);
                if(td == null)
                    continue;
                td.removeAll();
                td.setColumnIndex(j);
                EPanel panel = td.newPanel();
                panel.setAlignment(model.getAlignment());
                EMacroroutine macroroutine = panel.newMacroroutine();
                macroroutine.setFreeModel(false);
                macroroutine.setModel(model);
                if(model.getFont() != null)
                    macroroutine.setFont(model.getFont());
                String s = getValue(v,"#" + j + ".Bold");
                if(s != null && s.length() > 0)
                    macroroutine.modifyBold(StringTool.getBoolean(s));
                s = getValue(v,"#" + j + ".FontSize");
                if(s != null && s.length() > 0)
                    macroroutine.modifyFontSize(StringTool.getInt(s));
                
                //System.out.println("======macroroutine data======="+macroroutine.getString());
                model.addMacroroutine(macroroutine);
            }
            row ++;
        }
        for(int j = 0;j < columnCount;j++)
        {
            EMacroroutineModel model = getColumnModel().get(j);
            model.show();
        }
        
        //System.out.println("===table==="+table.getTableRowCount());

    }
    /**
     * ��ʾ��������
     */
    public void showGroupData()
    {
    	
    	
        int count = getData().getCount();
        //System.out.println("========CTable count=========="+count);
        if(count == 0)
            return;
        int columnCount = table.getColumnCount();
        int row = getInsertDataRow();
        //�õ����б����
        EGroupModelList groupModelList = getGroupModelList();
        //������Ϲ�����
        groupModelList.clearColumnMacroroutine();
        //��У��ֵ
        TList valueList = groupModelList.createValueList();
        //������
        TList rowIndexList = new TList();
        for(int i = 0;i < count;i++)
        {
            //����ϼƿ���
            boolean maxSwitch = true;
            //����ѭ��
            for(int group = 0;group < groupModelList.size();group++)
            {
                //�õ���ģ��
                EGroupModel groupModel = groupModelList.get(group);
                //�õ������
                int column = groupModel.getID();
                //�õ�ֵ
                Object value = getData().getData(i,column);
                //�Ǳ���������
                if(StringTool.equals(value,valueList.get(group)))
                    continue;
                //������ϼ�
                if(i > 0 && maxSwitch)
                {
                    maxSwitch = false;
                    for(int max = groupModelList.size() - 1;max >= group;max --)
                    {
                        EGroupModel maxGroupModel = groupModelList.get(max);
                        Object obj[] = (Object[])rowIndexList.get(rowIndexList.size() - 1);
                        rowIndexList.remove(rowIndexList.size() - 1);
                        //��ʼ��
                        int startRow = (Integer)obj[0];
                        ETRModel trModel = (ETRModel)obj[1];
                        if(trModel != null)
                            trModel.setEndRow(i);
                        if(!maxGroupModel.hasSum())
                            continue;
                        //������
                        row = table.insertRow(row, false);
                        //�õ���
                        ETR tr = table.get(row);
                        //������ģ��
                        trModel = tr.createModel();
                        //��������
                        trModel.setType(ETRModel.GROUP_SUM);
                        //�����к�
                        trModel.setRow(startRow);
                        //���ý�����
                        trModel.setEndRow(i);
                        //��������
                        trModel.setGroupID(maxGroupModel.getID());
                        //��������
                        trModel.setGroupName(maxGroupModel.getName());
                        for (int j = 0; j < columnCount; j++)
                        {
                            //�õ���ϼƺ�ģ��
                            EMacroroutineModel model = maxGroupModel.getSum().get(
                                    j);
                            //������Ԫ��
                            ETD td = table.getTDAt(row, j);
                            //��յ�Ԫ��
                            td.removeAll();
                            //�������
                            EPanel panel = td.newPanel();
                            //������
                            EMacroroutine macroroutine = panel.newMacroroutine();
                            macroroutine.setFreeModel(false);
                            //����ģ��
                            macroroutine.setModel(model);
                            if(model.getFont() != null)
                                macroroutine.setFont(model.getFont());
                            //ģ�ͼ���
                            model.addMacroroutine(macroroutine);
                            model.show();
                        }
                        row++;
                    }
                }
                //���������
                valueList.set(group,value);
                //������У��ֵ����
                for(int j = group + 1;j < groupModelList.size();j++)
                    valueList.set(j,new Object());

                if(groupModel.hasHead())
                {
                    //������
                    row = table.insertRow(row,false);
                    //�õ���
                    ETR tr = table.get(row);
                    //������ģ��
                    ETRModel trModel = tr.createModel();
                    //��������
                    trModel.setType(ETRModel.GROUP_HEAD);
                    //�����к�
                    trModel.setRow(i);
                    //��������
                    trModel.setGroupID(groupModel.getID());
                    //��������
                    trModel.setGroupName(groupModel.getName());
                    //��ʼ��
                    rowIndexList.add(new Object[]{i,trModel});
                    for(int j = 0;j < columnCount;j++)
                    {
                        //�õ�������ģ��
                        EMacroroutineModel model = groupModel.getHead().get(j);
                        //������Ԫ��
                        ETD td = table.getTDAt(row,j);
                        //��յ�Ԫ��
                        td.removeAll();
                        //�������
                        EPanel panel = td.newPanel();
                        //������
                        EMacroroutine macroroutine = panel.newMacroroutine();
                        macroroutine.setFreeModel(false);
                        //����ģ��
                        macroroutine.setModel(model);
                        if(model.getFont() != null)
                            macroroutine.setFont(model.getFont());
                        //ģ�ͼ���
                        model.addMacroroutine(macroroutine);
                        model.show();
                    }
                    row++;
                }
                else
                    //��ʼ��
                    rowIndexList.add(new Object[]{i,null});
            }
            if(!isGroupShowData())
                continue;
            //��ʾ��������
            row = table.insertRow(row,false);
            ETR tr = table.get(row);
            //������ģ��
            ETRModel trModel = tr.createModel();
            //��������
            trModel.setType(ETRModel.COLUMN_TYPE);
            //�����к�
            trModel.setRow(i);
            for(int j = 0;j < columnCount;j++)
            {
                EMacroroutineModel model = getColumnModel().get(j);
                ETD td = table.getTDAt(row,j);
                if(td == null)
                    continue;
                td.removeAll();
                EPanel panel = td.newPanel();
                EMacroroutine macroroutine = panel.newMacroroutine();
                macroroutine.setFreeModel(false);
                macroroutine.setModel(model);
                if(model.getFont() != null)
                    macroroutine.setFont(model.getFont());
                model.addMacroroutine(macroroutine);
            }
            row ++;
        }
        //���ĺϼ�
        for(int max = groupModelList.size() - 1;max >= 0;max --)
        {
            EGroupModel maxGroupModel = groupModelList.get(max);
            Object obj[] = (Object[])rowIndexList.get(rowIndexList.size() - 1);
            rowIndexList.remove(rowIndexList.size() - 1);
            //��ʼ��
            int startRow = (Integer)obj[0];
            ETRModel trModel = (ETRModel)obj[1];
            if(trModel != null)
                trModel.setEndRow(count);
            if(!maxGroupModel.hasSum())
                continue;
            //������
            row = table.insertRow(row, false);
            //�õ���
            ETR tr = table.get(row);
            //������ģ��
            trModel = tr.createModel();
            //��������
            trModel.setType(ETRModel.GROUP_SUM);
            //�����к�
            trModel.setRow(startRow);
            //���ý�����
            trModel.setEndRow(count);
            //��������
            trModel.setGroupID(maxGroupModel.getID());
            //��������
            trModel.setGroupName(maxGroupModel.getName());
            for (int j = 0; j < columnCount; j++)
            {
                //�õ���ϼƺ�ģ��
                EMacroroutineModel model = maxGroupModel.getSum().get(
                        j);
                //������Ԫ��
                ETD td = table.getTDAt(row, j);
                //��յ�Ԫ��
                td.removeAll();
                //�������
                EPanel panel = td.newPanel();
                //������
                EMacroroutine macroroutine = panel.newMacroroutine();
                macroroutine.setFreeModel(false);
                //����ģ��
                macroroutine.setModel(model);
                if(model.getFont() != null)
                    macroroutine.setFont(model.getFont());
                //ģ�ͼ���
                model.addMacroroutine(macroroutine);
                model.show();
            }
            row++;
        }
        for(int j = 0;j < columnCount;j++)
        {
            EMacroroutineModel model = getColumnModel().get(j);
            model.show();
        }
        getFocusManager().update();
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
        s.WO("<CTable InsertDataRow=" + getInsertDataRow() + " >",c);
        s.WO("<SQL>" + getSQL() + "</SQL>",c + 1);
        if(getGroup() != null)
        {
            s.WO("<Groups>", c + 1);
            for(int i = 0;i < getGroup().getCount();i++)
            {
                s.WO("<Group ID=" + getGroup().getValue("ID",i) +
                     " Name=" + getGroup().getValue("NAME",i) +
                     " Title=" + getGroup().getBoolean("TITLE",i) +
                     " Sum=" + getGroup().getBoolean("SUM",i) + " />", c + 1);
            }
            s.WO("</Groups>", c + 1);
        }
        getColumnModel().writeObjectDebug(s,c + 1);
        getGroupModelList().writeObjectDebug(s,c + 1);
        s.WO("</CTable>",c);
    }
    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s)
      throws IOException
    {
        s.writeString(1,getSQL(),"");
        s.writeInt(2,getInsertDataRow(),0);
        if(getGroup() != null)
        {
            s.writeShort(3);
            getGroup().writeObject(s);
        }
        s.writeShort(4);
        getColumnModel().writeObject(s);
        s.writeShort(5);
        getGroupModelList().writeObject(s);
        s.writeBoolean(6,isGroupShowData(),false);
        s.writeBoolean(7,hasSum(),false);
        s.writeString(8,getTableID(),"");
        s.writeBoolean(9,isInputData(),false);
        s.writeShort( -1);
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
            switch (id)
            {
            case 1:
                setSQL(s.readString());
                break;
            case 2:
                setInsertDataRow(s.readInt());
                break;
            case 3:
                setGroup(new TParm());
                getGroup().readObject(s);
                break;
            case 4:
                getColumnModel().readObject(s);
                break;
            case 5:
                getGroupModelList().readObject(s);
                break;
            case 6:
                setGroupShowData(s.readBoolean());
                break;
            case 7:
                setHasSum(s.readBoolean());
                break;
            case 8:
                setTableID(s.readString());
                break;
            case 9:
                setInputData(s.readBoolean());
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
        //getParent().removeDComponent(this);
        getColumnModel().removeThis();
        getGroupModelList().removeThis();
    }
    /**
     * ����Table���
     * @param tableID String
     */
    public void setTableID(String tableID)
    {
        this.tableID = tableID;
    }
    /**
     * �õ�Table���
     * @return String
     */
    public String getTableID()
    {
        return tableID;
    }
    /**
     * ���ô�������
     * @param inputData boolean
     */
    public void setInputData(boolean inputData)
    {
        this.inputData = inputData;
    }
    /**
     * �Ƿ�������
     * @return boolean
     */
    public boolean isInputData()
    {
        return inputData;
    }
}

