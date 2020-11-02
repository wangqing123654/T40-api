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
 * <p>Title: Table控制器</p>
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
     * Table对象
     */
    private ETable table;
    /**
     * Table控制器管理器
     */
    private MCTable cTableManager;
    /**
     * y间距
     */
    private int spacingY;
    /**
     * SQL语句
     */
    private String sql;
    /**
     * 数据包
     */
    private TParm data;
    /**
     * 列模型
     */
    private EColumnModel columnModel;
    /**
     * 是否存在总计
     */
    private boolean hasSum;
    /**
     * 插入数据行号
     */
    private int insertDataRow;
    /**
     * 分组
     */
    private TParm group;
    /**
     * 分组显示基础数据
     */
    private boolean groupShowData;
    /**
     * 组模型列表
     */
    private EGroupModelList groupModelList;
    /**
     * Table编号
     */
    private String tableID = "";
    /**
     * 传入数据
     */
    private boolean inputData;
    /**
     * 构造器
     */
    public CTable()
    {
        setBorder("线");
        //setText("查询");
        setBounds(10,10,100,100);
        setMouseMoveType(1);
        setMouseBorderDraggedType(255);
        DButton b = new DButton();
        b.setBounds(0,0,50,20);
        b.setText("查询");
        b.setActionMap("onClicked","onQuery");
        addPublicMethod("onQuery",this);
        addDComponent(b);
        //初始化列模型
        setColumnModel(new EColumnModel(this));
        //初始化组模型列表
        setGroupModelList(new EGroupModelList(this));

    }
    /**
     * 设置Table控制器管理器
     * @param cTableManager MCTable
     */
    public void setCTableManager(MCTable cTableManager)
    {
        this.cTableManager = cTableManager;
    }
    /**
     * 得到Table控制器管理器
     * @return MCTable
     */
    public MCTable getCTableManager()
    {
        return cTableManager;
    }
    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM()
    {
        return getCTableManager().getPM();
    }
    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager()
    {
        return getPM().getFocusManager();
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
     * 得到宏控制器
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager()
    {
        return getPM().getMacroroutineManager();
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
     * 构造器
     * @param table ETable
     */
    public CTable(ETable table)
    {
        this();
        setTable(table);
    }
    /**
     * 设置Table对象
     * @param table ETable
     */
    public void setTable(ETable table)
    {
        this.table = table;
    }
    /**
     * 得到Table对象
     * @return ETable
     */
    public ETable getTable()
    {
        return table;
    }
    /**
     * 得到纵向滚动条尺寸
     * @return int
     */
    public int getVScrollBarValue()
    {
        return 0;
    }
    /**
     * 纵向滚动条值改变
     * @param value int
     */
    public void onVScrollBarChangeValue(int value)
    {
        if(getTable() == null)
            return;
        setY(getTable().getScreenY() - spacingY - value);
    }
    /**
     * 设置SQL语句
     * @param sql String
     */
    public void setSQL(String sql)
    {
        this.sql = sql;
    }
    /**
     * 得到SQL语句
     * @return String
     */
    public String getSQL()
    {
        return sql;
    }
    /**
     * 设置数据包
     * @param data TParm
     */
    public void setData(TParm data)
    {
        this.data = data;
    }
    /**
     * 得到数据包
     * @return TParm
     */
    public TParm getData()
    {
        return data;
    }
    /**
     * 设置列模型
     * @param columnModel EColumnModel
     */
    public void setColumnModel(EColumnModel columnModel)
    {
        this.columnModel = columnModel;
    }
    /**
     * 得到列模型
     * @return EColumnModel
     */
    public EColumnModel getColumnModel()
    {
        return columnModel;
    }
    /**
     * 设置插入数据行
     * @param insertDataRow int
     */
    public void setInsertDataRow(int insertDataRow)
    {
        this.insertDataRow = insertDataRow;
    }
    /**
     * 得到插入数据行
     * @return int
     */
    public int getInsertDataRow()
    {
        return insertDataRow;
    }
    /**
     * 设置存在总计
     * @param hasSum boolean
     */
    public void setHasSum(boolean hasSum)
    {
        this.hasSum = hasSum;
    }
    /**
     * 是否存在总计
     * @return boolean
     */
    public boolean hasSum()
    {
        return hasSum;
    }
    /**
     * 设置分组
     * @param group TParm
     */
    public void setGroup(TParm group)
    {
        this.group = group;
    }
    /**
     * 得到分组
     * @return TParm
     */
    public TParm getGroup()
    {
        return group;
    }
    /**
     * 存在分组
     * @return boolean
     */
    public boolean hasGroup()
    {
        return getGroup() != null && getGroup().getCount() > 0;
    }
    /**
     * 设置组模型
     * @param groupModelList EGroupModelList
     */
    public void setGroupModelList(EGroupModelList groupModelList)
    {
        this.groupModelList = groupModelList;
    }
    /**
     * 得到组模型
     * @return EGroupModelList
     */
    public EGroupModelList getGroupModelList()
    {
        return groupModelList;
    }
    /**
     * 设置分组显示基础数据
     * @param groupShowData boolean
     */
    public void setGroupShowData(boolean groupShowData)
    {
        this.groupShowData = groupShowData;
    }
    /**
     * 分组是否显示基础数据
     * @return boolean
     */
    public boolean isGroupShowData()
    {
        return groupShowData;
    }
    /**
     * 查找组件位置
     * @param item CTable
     * @return int
     */
    public int findIndex(CTable item)
    {
        return getCTableManager().indexOf(item);
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
     * 初始化
     */
    public void init()
    {
        int columnCount = table.getColumnCount();
        //列初始化
        for(int i = 0;i < columnCount;i++)
        {
            EMacroroutineModel model = new EMacroroutineModel();
            getMacroroutineManager().add(model);
            //创建语法
            ESyntaxItem syntax = model.createSyntax();
            syntax.setName("#" + i);
            syntax.setSyntax("#" + i);
            getColumnModel().add(model);
        }
        //分组初始化
        if(hasGroup())
        {
            for(int group = 0;group < getGroup().getCount("ID");group ++)
            {
                //新建组模型
                EGroupModel groupModel = getGroupModelList().newGroupModel();
                //设置组编号
                groupModel.setID(TypeTool.getInt(getGroup().getData("ID",group)));
                //设置组名称
                groupModel.setName(TypeTool.getString(getGroup().getData("NAME",group)));
                //设置是否显示数据
                groupModel.setShowData(TypeTool.getBoolean(getGroup().getData("DATA",group)));
                //显示组标题
                boolean showHead = TypeTool.getBoolean(getGroup().getData("TITLE",group));
                //显示组合计
                boolean showSum = TypeTool.getBoolean(getGroup().getData("SUM",group));
                if(showHead)
                {
                    EColumnModel columnModel = new EColumnModel(this);
                    groupModel.setHead(columnModel);
                    for(int i = 0;i < columnCount;i++)
                    {
                        EMacroroutineModel model = new EMacroroutineModel();
                        getMacroroutineManager().add(model);
                        //创建语法
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
                        //创建语法
                        ESyntaxItem syntax = model.createSyntax();
                        syntax.setName("sum(" + groupModel.getName() + ")");
                        syntax.setSyntax("sum(#" + i + ")");
                        columnModel.add(model);
                    }
                }
            }
        }
        //显示列编辑行
        showColumnEditTR();
        int row = getInsertDataRow();
        //分组编辑行
        if(hasGroup())
            row = showColumnGroupEditTR();
        //总计编辑行
        if(hasSum())
            showMaxColumnTR(row + 1);
        //编译程序
        if(!getSyntaxManager().make())
        {
            getUI().messageBox(getSyntaxManager().getMakeErrText());
            return;
        }
    }
    /**
     * 显示列编辑行
     */
    public void showColumnEditTR()
    {
        //清除显示数据
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
     * 显示分组
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
     * 显示总计
     * @param index int
     */
    public void showMaxColumnTR(int index)
    {
        //新增行
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
            //创建语法
            ESyntaxItem syntax = model.createSyntax();
            syntax.setName("sum(#" + i + ")");
            syntax.setSyntax("sum(#" + i + ")");
            model.show();
        }
        ETR tr = table.get(row);
        //创建model
        ETRModel trModel = tr.createModel();
        //设置类型
        trModel.setType(ETRModel.COLUMN_MAX);
    }
    /**
     * 查询数据
     * @return int 行数
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
            System.out.println("CTable.retrieve() sql语句为空");
            return -1;
        }
        //解析SQL
        sql = parseSQL(sql);
        if(getSyntaxManager().getControl() == null)
            return -1;
        TJDODBTool tool = TJDODBTool.getInstance();
        TParm parm = new TParm(tool.select(sql));
        if(parm.getErrCode() < 0)
        {
            System.out.println("CTable.retrieve()错误 " + parm.getErrText());
            System.out.println("SQL=" + sql);
            return -1;
        }
        setData(parm);
        return parm.getCount();
    }
    /**
     * 解析SQL
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
     * 得到SQL参数
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
     * 查询
     */
    public void onQuery()
    {
        if(table == null)
            return;
        if(retrieve() < 0)
            return;
        //显示数据
        showData();
    }
    /**
     * 显示编辑
     */
    public void showEdit()
    {
        if(table == null)
            return;
        //显示列编辑行
        showColumnEditTR();
        //分组编辑行
        if(hasGroup())
            showColumnGroupEditTR();
    }
    /**
     * 清除显示数据
     */
    public void clearShowData()
    {
        if(table != null)
            //删除列数据
            table.getModel().removeDataRow();
        //清除列上关联宏
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
     * 独立运行显示数据
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
            //创建model
            ETRModel trModel = tr.createModel();
            //设置类型
            trModel.setType(ETRModel.COLUMN_TYPE);
            //设置行号
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
     * 显示数据
     */
    public void showData()
    {
        if(table == null)
            return;
        //清除显示数据
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
            //显示分组数据
            showGroupData();
            return;
        }
        int count = getData().getCount();

        int columnCount = table.getColumnCount();
        
        int row = getInsertDataRow();
        
        if(count <= 0)
            showDataIndependent();
        //插入新行
        for(int i = 0;i < count;i++)
        {

            String v = "";
            if(getData().existData("TABLE_VALUE"))
                v = getData().getValue("TABLE_VALUE",i);
               
            //新增行
            row = table.insertRow(row,false);
            ETR tr = table.get(row);
            //创建model
            ETRModel trModel = tr.createModel();
            //设置类型
            trModel.setType(ETRModel.COLUMN_TYPE);
            //设置行号
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
     * 显示分组数据
     */
    public void showGroupData()
    {
    	
    	
        int count = getData().getCount();
        //System.out.println("========CTable count=========="+count);
        if(count == 0)
            return;
        int columnCount = table.getColumnCount();
        int row = getInsertDataRow();
        //得到组列表对象
        EGroupModelList groupModelList = getGroupModelList();
        //清除列上关联宏
        groupModelList.clearColumnMacroroutine();
        //组校验值
        TList valueList = groupModelList.createValueList();
        //行索引
        TList rowIndexList = new TList();
        for(int i = 0;i < count;i++)
        {
            //分组合计开关
            boolean maxSwitch = true;
            //分组循环
            for(int group = 0;group < groupModelList.size();group++)
            {
                //得到组模型
                EGroupModel groupModel = groupModelList.get(group);
                //得到列序号
                int column = groupModel.getID();
                //得到值
                Object value = getData().getData(i,column);
                //是本组行跳过
                if(StringTool.equals(value,valueList.get(group)))
                    continue;
                //插入组合计
                if(i > 0 && maxSwitch)
                {
                    maxSwitch = false;
                    for(int max = groupModelList.size() - 1;max >= group;max --)
                    {
                        EGroupModel maxGroupModel = groupModelList.get(max);
                        Object obj[] = (Object[])rowIndexList.get(rowIndexList.size() - 1);
                        rowIndexList.remove(rowIndexList.size() - 1);
                        //起始行
                        int startRow = (Integer)obj[0];
                        ETRModel trModel = (ETRModel)obj[1];
                        if(trModel != null)
                            trModel.setEndRow(i);
                        if(!maxGroupModel.hasSum())
                            continue;
                        //新增行
                        row = table.insertRow(row, false);
                        //得到行
                        ETR tr = table.get(row);
                        //创建行模型
                        trModel = tr.createModel();
                        //设置类型
                        trModel.setType(ETRModel.GROUP_SUM);
                        //设置行号
                        trModel.setRow(startRow);
                        //设置结束行
                        trModel.setEndRow(i);
                        //设置组编号
                        trModel.setGroupID(maxGroupModel.getID());
                        //设置组名
                        trModel.setGroupName(maxGroupModel.getName());
                        for (int j = 0; j < columnCount; j++)
                        {
                            //得到组合计宏模型
                            EMacroroutineModel model = maxGroupModel.getSum().get(
                                    j);
                            //创建单元格
                            ETD td = table.getTDAt(row, j);
                            //清空单元格
                            td.removeAll();
                            //创建面板
                            EPanel panel = td.newPanel();
                            //创建宏
                            EMacroroutine macroroutine = panel.newMacroroutine();
                            macroroutine.setFreeModel(false);
                            //设置模型
                            macroroutine.setModel(model);
                            if(model.getFont() != null)
                                macroroutine.setFont(model.getFont());
                            //模型加载
                            model.addMacroroutine(macroroutine);
                            model.show();
                        }
                        row++;
                    }
                }
                //插入组标题
                valueList.set(group,value);
                //后面组校验值归零
                for(int j = group + 1;j < groupModelList.size();j++)
                    valueList.set(j,new Object());

                if(groupModel.hasHead())
                {
                    //新增行
                    row = table.insertRow(row,false);
                    //得到行
                    ETR tr = table.get(row);
                    //创建行模型
                    ETRModel trModel = tr.createModel();
                    //设置类型
                    trModel.setType(ETRModel.GROUP_HEAD);
                    //设置行号
                    trModel.setRow(i);
                    //设置组编号
                    trModel.setGroupID(groupModel.getID());
                    //设置组名
                    trModel.setGroupName(groupModel.getName());
                    //起始行
                    rowIndexList.add(new Object[]{i,trModel});
                    for(int j = 0;j < columnCount;j++)
                    {
                        //得到组标题宏模型
                        EMacroroutineModel model = groupModel.getHead().get(j);
                        //创建单元格
                        ETD td = table.getTDAt(row,j);
                        //清空单元格
                        td.removeAll();
                        //创建面板
                        EPanel panel = td.newPanel();
                        //创建宏
                        EMacroroutine macroroutine = panel.newMacroroutine();
                        macroroutine.setFreeModel(false);
                        //设置模型
                        macroroutine.setModel(model);
                        if(model.getFont() != null)
                            macroroutine.setFont(model.getFont());
                        //模型加载
                        model.addMacroroutine(macroroutine);
                        model.show();
                    }
                    row++;
                }
                else
                    //起始行
                    rowIndexList.add(new Object[]{i,null});
            }
            if(!isGroupShowData())
                continue;
            //显示基础数据
            row = table.insertRow(row,false);
            ETR tr = table.get(row);
            //创建行模型
            ETRModel trModel = tr.createModel();
            //设置类型
            trModel.setType(ETRModel.COLUMN_TYPE);
            //设置行号
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
        //最后的合计
        for(int max = groupModelList.size() - 1;max >= 0;max --)
        {
            EGroupModel maxGroupModel = groupModelList.get(max);
            Object obj[] = (Object[])rowIndexList.get(rowIndexList.size() - 1);
            rowIndexList.remove(rowIndexList.size() - 1);
            //起始行
            int startRow = (Integer)obj[0];
            ETRModel trModel = (ETRModel)obj[1];
            if(trModel != null)
                trModel.setEndRow(count);
            if(!maxGroupModel.hasSum())
                continue;
            //新增行
            row = table.insertRow(row, false);
            //得到行
            ETR tr = table.get(row);
            //创建行模型
            trModel = tr.createModel();
            //设置类型
            trModel.setType(ETRModel.GROUP_SUM);
            //设置行号
            trModel.setRow(startRow);
            //设置结束行
            trModel.setEndRow(count);
            //设置组编号
            trModel.setGroupID(maxGroupModel.getID());
            //设置组名
            trModel.setGroupName(maxGroupModel.getName());
            for (int j = 0; j < columnCount; j++)
            {
                //得到组合计宏模型
                EMacroroutineModel model = maxGroupModel.getSum().get(
                        j);
                //创建单元格
                ETD td = table.getTDAt(row, j);
                //清空单元格
                td.removeAll();
                //创建面板
                EPanel panel = td.newPanel();
                //创建宏
                EMacroroutine macroroutine = panel.newMacroroutine();
                macroroutine.setFreeModel(false);
                //设置模型
                macroroutine.setModel(model);
                if(model.getFont() != null)
                    macroroutine.setFont(model.getFont());
                //模型加载
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
     * 写对象
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
     * 写对象
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
     * 读对象
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
     * 删除自己
     */
    public void removeThis()
    {
        //getParent().removeDComponent(this);
        getColumnModel().removeThis();
        getGroupModelList().removeThis();
    }
    /**
     * 设置Table编号
     * @param tableID String
     */
    public void setTableID(String tableID)
    {
        this.tableID = tableID;
    }
    /**
     * 得到Table编号
     * @return String
     */
    public String getTableID()
    {
        return tableID;
    }
    /**
     * 设置传入数据
     * @param inputData boolean
     */
    public void setInputData(boolean inputData)
    {
        this.inputData = inputData;
    }
    /**
     * 是否传入数据
     * @return boolean
     */
    public boolean isInputData()
    {
        return inputData;
    }
}

