package com.dongyang.ui.base;

import com.dongyang.ui.TScrollPane;
import com.dongyang.ui.TPopupMenu;
import com.dongyang.ui.TComponent;
import com.dongyang.ui.event.TMouseListener;
import javax.swing.tree.TreePath;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.util.Hashtable;
import java.awt.Rectangle;
import javax.swing.tree.TreeModel;
import com.dongyang.ui.event.TTreeEvent;
import com.dongyang.util.StringTool;
import javax.swing.tree.TreeNode;
import java.util.Vector;
import com.dongyang.ui.TTreeNode;
import com.dongyang.ui.TTree;

public class TTreeBase extends TScrollPane
{
    /**
     * 内部树对象
     */
    private JTreeBase tree;
    /**
     * 界外弹出菜单标签
     */
    private String outdropPopupMenuTag;
    /**
     * 类型弹出菜单标签列表(分号间隔)
     */
    private String typePopupMenuTags;
    /**
     * 树根弹出菜单标签
     */
    private String rootPopupMenuTags;
    /**
     * 项目弹出菜单标签
     */
    private String itemPopupMenuTags;
    /**
     * 一种
     */
    private String language;
    /**
     * 当前标签
     */
    private TTreeNode nowNode;
    /**
     * Returns a <code>TTree</code> with a sample model.
     * The default model used by the tree defines a leaf node as any node
     * without children.
     *
     */
    public TTreeBase() {
        setTree(new JTreeBase(getDefaultTreeModel(),(TTree)this));
    }

    /**
     * 初始化
     */
    public void onInit()
    {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        //初始化控制类
        if (getControl() != null)
            getControl().onInit();
    }
    /**
     * 初始化监听事件
     */
    public void initListeners()
    {
        super.initListeners();
        //监听Mouse事件
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,this,"mouseLeftPressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,this,"mouseRightPressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,this,"mouseDoubleClicked");
    }
    /**
     * 设置原始树对象
     * @param tree JTreeBase
     */
    public void setTree(JTreeBase tree)
    {
        if(this.tree == tree)
            return;
        if(this.tree != null)
            getViewport().remove(this.tree);
        this.tree = tree;
        getViewport().add(tree);
        tree.setCellRenderer(new TTreeCellRenderer());
        tree.setCellEditor(new TTreeCellEditor(getTree(),getCellRenderer()));
        tree.addMouseListener(new TMouseListener(this));
        tree.setEditable(false);
    }
    /**
     * 设置原始树对象
     * @return JTreeBase
     */
    public JTreeBase getTree()
    {
        return tree;
    }
    /**
     * 设置图标列表
     * @param pics String
     */
    public void setPics(String pics)
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setPics(pics);
    }
    /**
     * 得到图标列表
     * @return String
     */
    public String getPics()
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return null;
        return cellRenderer.getPics();
    }
    /**
     * 设置显示ID
     * @param showID boolean
     */
    public void setShowID(boolean showID)
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setShowID(showID);
    }
    /**
     * 是否显示ID
     * @return boolean
     */
    public boolean isShowID()
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return false;
        return cellRenderer.isShowID();
    }
    /**
     * 设置显示名称
     * @param showName boolean
     */
    public void setShowName(boolean showName)
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setShowName(showName);
    }
    /**
     * 是否显示名称
     * @return boolean
     */
    public boolean isShowName()
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return false;
        return cellRenderer.isShowName();
    }
    /**
     * 设置显示值
     * @param showValue boolean
     */
    public void setShowValue(boolean showValue)
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return;
        cellRenderer.setShowValue(showValue);
    }
    /**
     * 是否显示值
     * @return boolean
     */
    public boolean isShowValue()
    {
        TTreeCellRenderer cellRenderer = getCellRenderer();
        if(cellRenderer == null)
            return false;
        return cellRenderer.isShowValue();
    }
    /**
     * 设置显示控制类
     * @param cellRenderer TTreeCellRenderer
     */
    public void setCellRenderer(TTreeCellRenderer cellRenderer)
    {
        if(getTree() == null)
            return;
        getTree().setCellRenderer(cellRenderer);
    }
    /**
     * 得到显示控制类
     * @return TTreeCellRenderer
     */
    public TTreeCellRenderer getCellRenderer()
    {
        if(getTree() == null)
            return null;
        if(!(getTree().getCellRenderer() instanceof TTreeCellRenderer))
            return null;
        return (TTreeCellRenderer)getTree().getCellRenderer();
    }
    /**
     * 设置界外弹出菜单标签
     * @param outdropPopupMenuTag String
     */
    public void setOutdropPopupMenuTag(String outdropPopupMenuTag)
    {
        this.outdropPopupMenuTag = outdropPopupMenuTag;
    }
    /**
     * 得到界外弹出菜单标签
     * @return String
     */
    public String getOutdropPopupMenuTag()
    {
        return outdropPopupMenuTag;
    }
    /**
     * 设置类型弹出菜单标签列表(分号间隔)
     * @param typePopupMenuTags String
     */
    public void setTypePopupMenuTags(String typePopupMenuTags)
    {
        this.typePopupMenuTags = typePopupMenuTags;
    }
    /**
     * 得到类型弹出菜单标签列表(分号间隔)
     * @return String
     */
    public String getTypePopupMenuTags()
    {
        return typePopupMenuTags;
    }
    /**
     * 得到类型对应的弹出菜单标签
     * @param type String 类型
     * @return String 弹出菜单标签
     */
    public String getTypePopupMenuTag(String type)
    {
        if(type == null||type.trim().length() == 0)
            return null;
        if(getTypePopupMenuTags() == null || getTypePopupMenuTags().trim().length() == 0)
            return null;
        String data[] = StringTool.parseLine(getTypePopupMenuTags(),';',"''\"\"");
        for(int i = 0;i < data.length;i++)
        {
            String parm[] = StringTool.getHead(data[i],":");
            if(parm.length < 2)
                continue;
            if(type.equalsIgnoreCase(parm[0]))
                return parm[1];
        }
        return null;
    }
    /**
     * 设置树根弹出菜单标签
     * @param rootPopupMenuTags String
     */
    public void setRootPopupMenuTags(String rootPopupMenuTags)
    {
        this.rootPopupMenuTags = rootPopupMenuTags;
    }
    /**
     * 得到树根弹出菜单标签
     * @return String
     */
    public String getRootPopupMenuTags()
    {
        return rootPopupMenuTags;
    }
    /**
     * 设置项目弹出菜单标签
     * @param itemPopupMenuTags String
     */
    public void setItemPopupMenuTags(String itemPopupMenuTags)
    {
        this.itemPopupMenuTags = itemPopupMenuTags;
    }
    /**
     * 得到项目弹出菜单标签
     * @return String
     */
    public String getItemPopupMenuTags()
    {
        return itemPopupMenuTags;
    }

    protected static TreeModel getDefaultTreeModel() {
        TTreeNode      root = new TTreeNode("JTree","");
        TTreeNode      parent;

        parent = new TTreeNode("colors","Path");
        root.add(parent);
        parent.add(new TTreeNode("01","A1","T","blue","File","checkbox"));
        parent.add(new TTreeNode("02","A2","N","violet","File","checkbox"));
        parent.add(new TTreeNode("03","A3","1","red","File","checkbox"));
        parent.add(new TTreeNode("04","A4","0","yellow","File","checkbox"));

        parent = new TTreeNode("sports","Path");
        root.add(parent);
        parent.add(new TTreeNode("0","basketball","File","RadioButton"));
        parent.add(new TTreeNode("0","soccer","File","RadioButton"));
        parent.add(new TTreeNode("1","football","File","RadioButton"));
        parent.add(new TTreeNode("0","hockey","File","RadioButton"));

        parent = new TTreeNode("food","Path");
        root.add(parent);
        parent.add(new TTreeNode("hot dogs","File"));
        parent.add(new TTreeNode("pizza"));
        parent.add(new TTreeNode("ravioli"));
        parent.add(new TTreeNode("bananas"));

        parent = new TTreeNode("字体","Path");
        root.add(parent);
        TTreeNode c1 = new TTreeNode("宋体12");
        c1.setFont(new Font("宋体",1,12));
        c1.setColor(new Color(255,0,0));
        parent.add(c1);
        c1 = new TTreeNode("宋体12");
        c1.setFont(new Font("宋体",2,12));
        c1.setColor(new Color(0,255,0));
        parent.add(c1);
        c1 = new TTreeNode("黑体12");
        c1.setFont(new Font("黑体",3,12));
        c1.setColor(new Color(0,0,255));
        parent.add(c1);

        return new TTreeModel(root);
    }
    /**
     * 查找Tag对象
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        return null;
    }
    /**
     * 左键点击
     * @param e MouseEvent
     */
    public void mouseLeftPressed(MouseEvent e)
    {
        checkButtonClick(e);
        TTreeNode node = getNodeForLocation(e);
        callMessage(getTag() + "->" + TTreeEvent.CLICKED,node);
        if(node == null)
            return;
        setNowNode(node);
        exeAction(getClickedItemAction());
    }
    /**
     * 检测点击Box
     * @param e MouseEvent
     */
    private void checkButtonClick(MouseEvent e)
    {
        //检测鼠标点击CheckBox
        if(checkBoxMouseClick(e))
            return;
        //检测鼠标点击RadioButton
        if(RadioButtonMouseClick(e))
            return;
    }
    /**
     * 右键点击
     * @param e MouseEvent
     */
    public void mouseRightPressed(MouseEvent e)
    {
        //弹出菜单
        popupMenu(e);
    }
    /**
     * 鼠标双击
     * @param e MouseEvent
     */
    public void mouseDoubleClicked(MouseEvent e)
    {
        TTreeNode node = getNodeForLocation(e);
        callMessage(getTag() + "->" + TTreeEvent.DOUBLE_CLICKED,node);
        exeAction(getDoubleClickedItemAction());
    }
    public void treeExpanded(TTreeNode node)
    {
        setNowNode(node);
        exeAction(getTreeExpandedAction());
    }
    public void treeCollapsed(TTreeNode node)
    {
        setNowNode(node);
        exeAction(getTreeCollapsedAction());
    }
    public void setNowNode(TTreeNode node)
    {
        this.nowNode = node;
    }
    public TTreeNode getNowNode()
    {
        return nowNode;
    }
    /**
     * 弹出菜单
     * @param e MouseEvent 鼠标事件
     */
    public void popupMenu(MouseEvent e)
    {
        //得到弹出菜单标签
        String popupMenuTag = getPopupMenuTag(e);
        if(popupMenuTag == null)
            return;
        //得到弹出菜单
        TPopupMenu popup = getPopupMenu(popupMenuTag);
        if(popup == null)
            return;
        //得到选中的项目 如果点选项目外面将设置null
        TTreeNode node = null;
        if(popupMenuTag != getOutdropPopupMenuTag())
            node = getSelectNode();
        setNowNode(node);
        popup.changeLanguage(getLanguage());
        callEvent("popupMenu",new Object[]{popup,node},new String[]{"com.dongyang.ui.TPopupMenu","com.dongyang.ui.TTreeNode"});
        //弹出菜单
        popup.show(getTree(),e.getX(),e.getY());
    }
    /**
     * 得到选中的项目
     * @return TTreeNode
     */
    public TTreeNode getSelectNode()
    {
        if(getTree() == null)
            return null;
        Object obj = getTree().getLastSelectedPathComponent();
        if(!(obj instanceof TTreeNode))
            return null;
        return (TTreeNode)obj;
    }
    /**
     * 得到弹出菜单
     * @param tag String 弹出菜单标签
     * @return TPopupMenu 弹出菜单对象
     */
    public TPopupMenu getPopupMenu(String tag)
    {
        TPopupMenu popup = new TPopupMenu();
        popup.setTag(tag);
        popup.setParentComponent(this);
        String config = getConfigParm().getConfig().getString(getConfigParm().getSystemGroup(),tag + ".MenuConfig");
        if(config.length() > 0)
        {
            popup.setLoadTag("UI");
            popup.init(getConfigParm().newConfig(config,false));
        }else
        {
            popup.setLoadTag(tag);
            popup.init(getConfigParm());
        }
        popup.onInit();
        return popup;
    }
    /**
     * 根据鼠标时间计算标签
     * @param e MouseEvent
     * @return TTreeNode
     */
    public TTreeNode getNodeForLocation(MouseEvent e)
    {
        return getNodeForLocation(e.getX(),e.getY());
    }
    /**
     * 根据坐标计算标签
     * @param x int
     * @param y int
     * @return TTreeNode
     */
    public TTreeNode getNodeForLocation(int x,int y)
    {
        if(getTree() == null)
            return null;
        return getTree().getPathForLocationNode(x,y);
    }
    /**
     * 得到弹出菜单标签
     * @param e MouseEvent 鼠标事件
     * @return String 弹出菜单标签
     */
    public String getPopupMenuTag(MouseEvent e)
    {
        if(getTree() == null)
            return null;
        TreePath path=getTree().getPathForLocation(e.getX(),e.getY());
        //点选项目外部返回界外菜单标签
        if(path == null)
            return getOutdropPopupMenuTag();
        //选蓝点选项目
        getTree().setSelectionPath(path);
        //得到项目内部弹出菜单标签
        TTreeNode node = (TTreeNode)path.getLastPathComponent();
        if(node.getPopupMenuTag() != null && node.getPopupMenuTag().trim().length() > 0)
            return node.getPopupMenuTag();
        //得到类型对应的弹出菜单标签
        String tag = getTypePopupMenuTag(node.getType());
        if(tag != null)
            return tag;
        //得到树根弹出菜单标签
        if(node.isRoot())
            return getRootPopupMenuTags();
        //得到项目弹出菜单标签
        return getItemPopupMenuTags();
    }
    /**
     * 检测鼠标点击CheckBox
     * @param e MouseEvent
     * @return boolean
     */
    private boolean checkBoxMouseClick(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        TreePath closestPath = getTree().getClosestPathForLocation(x, y);
        if(closestPath == null)
            return false;
        TTreeNode node = (TTreeNode)closestPath.getLastPathComponent();
        if(!"CheckBox".equalsIgnoreCase(node.getShowType()))
            return false;
        Rectangle pathBounds = getTree().getPathBounds(closestPath);
        if(pathBounds != null &&
           x >= pathBounds.x + 4 && x < pathBounds.x + 20 &&
           y >= pathBounds.y && y < (pathBounds.y + pathBounds.height))
        {
            node.setValue(StringTool.getBoolean(node.getValue()) ? "N" :
                          "Y");
            getTree().repaint();
            return true;
        }
        return false;
    }
    /**
     * 检测鼠标点击RadioButton
     * @param e MouseEvent
     * @return boolean
     */
    private boolean RadioButtonMouseClick(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        TreePath closestPath = getTree().getClosestPathForLocation(x, y);
        if(closestPath == null)
            return false;
        TTreeNode node = (TTreeNode)closestPath.getLastPathComponent();
        if(!"RadioButton".equalsIgnoreCase(node.getShowType()))
            return false;
        Rectangle pathBounds = getTree().getPathBounds(closestPath);
        if(pathBounds != null &&
               x >= pathBounds.x + 4 && x < pathBounds.x + 20 &&
               y >= pathBounds.y && y < (pathBounds.y + pathBounds.height))
        {
            if(node.getParent() == null)
                return true;
            TTreeNode nodeParent = (TTreeNode)node.getParent();
            int count = nodeParent.getChildCount();
            for(int i = 0;i < count;i++)
                ((TTreeNode)nodeParent.getChildAt(i)).setValue("N");
            node.setValue(StringTool.getBoolean(node.getValue()) ? "N" :
                          "Y");
            getTree().repaint();
            return true;
        }
        return false;
    }
    /**
     * 得到Model
     * @return TTreeModel
     */
    public TTreeModel getModel()
    {
        return (TTreeModel)getTree().getModel();
    }
    /**
     * 得到根
     * @return TTreeNode
     */
    public TTreeNode getRoot()
    {
        return (TTreeNode)getModel().getRoot();
    }
    /**
     * 得到选中标签
     * @return TTreeNode
     */
    public TTreeNode getSelectionNode()
    {
        return (TTreeNode)getTree().getLastSelectedPathComponent();
    }
    /**
     * 更新
     */
    public void update()
    {
        getTree().updateUI();
    }
    /**
     * 选中标签
     * @param node TTreeNode[]
     */
    public void setSelectionNode(TTreeNode node[])
    {
        getTree().setSelectionNode(node);
    }
    /**
     * 选中标签
     * @param node TTreeNode
     */
    public void setSelectNode(TTreeNode node)
    {
        setSelectionNode(new TTreeNode[]{node});
    }
    /**
     * 得到自己对象
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * 展开层
     * @param layer int
     */
    public void expandLayer(int layer)
    {
        TTreeNode node = getRoot();
        expandLayer(node,layer);
    }
    /**
     * 展开层
     * @param node TTreeNode
     * @param layer int
     */
    public void expandLayer(TTreeNode node,int layer)
    {
        expandRow(node);
        if(layer == 0)
            return;
        int count = node.getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = node.getChildAt(i);
            if (tnode == null)
                continue;
            if (!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node1 = (TTreeNode)tnode;
            expandLayer(node1,layer - 1);
        }
    }
    /**
     * 展开标签
     * @param node TTreeNode
     */
    public void expandRow(TTreeNode node)
    {
        TreeNode tn[] = node.getPath();
        for(int j = 0;j < tn.length;j++)
            for(int k = 0;k < getTree().getRowCount();k++)
                if (getTree().getNodeForRow(k) == tn[j])
                    getTree().expandRow(k);
    }
    /**
     * 回缩层
     * @param layer int
     */
    public void collapseLayer(int layer)
    {
        TTreeNode node = getRoot();
        collapseLayer(node,layer);
    }
    /**
     * 回缩层
     * @param node TTreeNode
     * @param layer int
     */
    public void collapseLayer(TTreeNode node,int layer)
    {
        if(layer == 0)
        {
            collapseRow(node);
            return;
        }
        int count = node.getChildCount();
        for(int i = 0;i < count;i++)
        {
            TreeNode tnode = node.getChildAt(i);
            if (tnode == null)
                continue;
            if (!(tnode instanceof TTreeNode))
                continue;
            TTreeNode node1 = (TTreeNode)tnode;
            collapseLayer(node1,layer - 1);
        }
    }
    /**
     * 回缩标签
     * @param node TTreeNode
     */
    public void collapseRow(TTreeNode node)
    {
        TreeNode tn[] = node.getPath();
        for(int k = 0;k < getTree().getRowCount();k++)
            if (getTree().getNodeForRow(k) == tn[tn.length - 1])
                getTree().collapseRow(k);
    }
    /**
     * 设置双击项目消息
     * @param action String
     */
    public void setDoubleClickedItemAction(String action) {
        actionList.setAction("doubleClickedItemAction",action);
    }

    /**
     * 得到双击项目消息
     * @return String
     */
    public String getDoubleClickedItemAction() {
        return actionList.getAction("doubleClickedItemAction");
    }
    /**
     * 设置点击项目动作
     * @param action String
     */
    public void setClickedItemAction(String action)
    {
        actionList.setAction("clickedItemAction",action);
    }
    /**
     * 得到点击项目动作
     * @return String
     */
    public String getClickedItemAction()
    {
        return actionList.getAction("clickedItemAction");
    }
    /**
     * 设置展开动作
     * @param action String
     */
    public void setTreeExpandedAction(String action)
    {
        actionList.setAction("treeExpandedAction",action);
    }
    /**
     * 得到展开动作
     * @return String
     */
    public String getTreeExpandedAction()
    {
        return actionList.getAction("treeExpandedAction");
    }
    /**
     * 设置合并动作
     * @param action String
     */
    public void setTreeCollapsedAction(String action)
    {
        actionList.setAction("treeCollapsedAction",action);
    }
    /**
     * 得到合并动作
     * @return String
     */
    public String getTreeCollapsedAction()
    {
        return actionList.getAction("treeCollapsedAction");
    }
    /**
     * 设置行高度
     * @param rowHeight int
     */
    public void setRowHeight(int rowHeight)
    {
        getTree().setRowHeight(rowHeight);
    }
    /**
     * 得到行高度
     * @return int
     */
    public int getRowHeight()
    {
        return getTree().getRowHeight();
    }
    /**
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * 设置语种
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if (language == null)
            return;
        if(language.equals(this.language))
            return;
        this.language = language;
        update();
    }
}
