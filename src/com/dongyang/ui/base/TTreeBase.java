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
     * �ڲ�������
     */
    private JTreeBase tree;
    /**
     * ���ⵯ���˵���ǩ
     */
    private String outdropPopupMenuTag;
    /**
     * ���͵����˵���ǩ�б�(�ֺż��)
     */
    private String typePopupMenuTags;
    /**
     * ���������˵���ǩ
     */
    private String rootPopupMenuTags;
    /**
     * ��Ŀ�����˵���ǩ
     */
    private String itemPopupMenuTags;
    /**
     * һ��
     */
    private String language;
    /**
     * ��ǰ��ǩ
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
     * ��ʼ��
     */
    public void onInit()
    {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        //��ʼ��������
        if (getControl() != null)
            getControl().onInit();
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        super.initListeners();
        //����Mouse�¼�
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_LEFT_CLICKED,this,"mouseLeftPressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_RIGHT_CLICKED,this,"mouseRightPressed");
        addEventListener(getTag() + "->" + TMouseListener.MOUSE_DOUBLE_CLICKED,this,"mouseDoubleClicked");
    }
    /**
     * ����ԭʼ������
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
     * ����ԭʼ������
     * @return JTreeBase
     */
    public JTreeBase getTree()
    {
        return tree;
    }
    /**
     * ����ͼ���б�
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
     * �õ�ͼ���б�
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
     * ������ʾID
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
     * �Ƿ���ʾID
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
     * ������ʾ����
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
     * �Ƿ���ʾ����
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
     * ������ʾֵ
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
     * �Ƿ���ʾֵ
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
     * ������ʾ������
     * @param cellRenderer TTreeCellRenderer
     */
    public void setCellRenderer(TTreeCellRenderer cellRenderer)
    {
        if(getTree() == null)
            return;
        getTree().setCellRenderer(cellRenderer);
    }
    /**
     * �õ���ʾ������
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
     * ���ý��ⵯ���˵���ǩ
     * @param outdropPopupMenuTag String
     */
    public void setOutdropPopupMenuTag(String outdropPopupMenuTag)
    {
        this.outdropPopupMenuTag = outdropPopupMenuTag;
    }
    /**
     * �õ����ⵯ���˵���ǩ
     * @return String
     */
    public String getOutdropPopupMenuTag()
    {
        return outdropPopupMenuTag;
    }
    /**
     * �������͵����˵���ǩ�б�(�ֺż��)
     * @param typePopupMenuTags String
     */
    public void setTypePopupMenuTags(String typePopupMenuTags)
    {
        this.typePopupMenuTags = typePopupMenuTags;
    }
    /**
     * �õ����͵����˵���ǩ�б�(�ֺż��)
     * @return String
     */
    public String getTypePopupMenuTags()
    {
        return typePopupMenuTags;
    }
    /**
     * �õ����Ͷ�Ӧ�ĵ����˵���ǩ
     * @param type String ����
     * @return String �����˵���ǩ
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
     * �������������˵���ǩ
     * @param rootPopupMenuTags String
     */
    public void setRootPopupMenuTags(String rootPopupMenuTags)
    {
        this.rootPopupMenuTags = rootPopupMenuTags;
    }
    /**
     * �õ����������˵���ǩ
     * @return String
     */
    public String getRootPopupMenuTags()
    {
        return rootPopupMenuTags;
    }
    /**
     * ������Ŀ�����˵���ǩ
     * @param itemPopupMenuTags String
     */
    public void setItemPopupMenuTags(String itemPopupMenuTags)
    {
        this.itemPopupMenuTags = itemPopupMenuTags;
    }
    /**
     * �õ���Ŀ�����˵���ǩ
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

        parent = new TTreeNode("����","Path");
        root.add(parent);
        TTreeNode c1 = new TTreeNode("����12");
        c1.setFont(new Font("����",1,12));
        c1.setColor(new Color(255,0,0));
        parent.add(c1);
        c1 = new TTreeNode("����12");
        c1.setFont(new Font("����",2,12));
        c1.setColor(new Color(0,255,0));
        parent.add(c1);
        c1 = new TTreeNode("����12");
        c1.setFont(new Font("����",3,12));
        c1.setColor(new Color(0,0,255));
        parent.add(c1);

        return new TTreeModel(root);
    }
    /**
     * ����Tag����
     * @param tag String
     * @return TComponent
     */
    public TComponent findObject(String tag)
    {
        return null;
    }
    /**
     * ������
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
     * �����Box
     * @param e MouseEvent
     */
    private void checkButtonClick(MouseEvent e)
    {
        //��������CheckBox
        if(checkBoxMouseClick(e))
            return;
        //��������RadioButton
        if(RadioButtonMouseClick(e))
            return;
    }
    /**
     * �Ҽ����
     * @param e MouseEvent
     */
    public void mouseRightPressed(MouseEvent e)
    {
        //�����˵�
        popupMenu(e);
    }
    /**
     * ���˫��
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
     * �����˵�
     * @param e MouseEvent ����¼�
     */
    public void popupMenu(MouseEvent e)
    {
        //�õ������˵���ǩ
        String popupMenuTag = getPopupMenuTag(e);
        if(popupMenuTag == null)
            return;
        //�õ������˵�
        TPopupMenu popup = getPopupMenu(popupMenuTag);
        if(popup == null)
            return;
        //�õ�ѡ�е���Ŀ �����ѡ��Ŀ���潫����null
        TTreeNode node = null;
        if(popupMenuTag != getOutdropPopupMenuTag())
            node = getSelectNode();
        setNowNode(node);
        popup.changeLanguage(getLanguage());
        callEvent("popupMenu",new Object[]{popup,node},new String[]{"com.dongyang.ui.TPopupMenu","com.dongyang.ui.TTreeNode"});
        //�����˵�
        popup.show(getTree(),e.getX(),e.getY());
    }
    /**
     * �õ�ѡ�е���Ŀ
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
     * �õ������˵�
     * @param tag String �����˵���ǩ
     * @return TPopupMenu �����˵�����
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
     * �������ʱ������ǩ
     * @param e MouseEvent
     * @return TTreeNode
     */
    public TTreeNode getNodeForLocation(MouseEvent e)
    {
        return getNodeForLocation(e.getX(),e.getY());
    }
    /**
     * ������������ǩ
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
     * �õ������˵���ǩ
     * @param e MouseEvent ����¼�
     * @return String �����˵���ǩ
     */
    public String getPopupMenuTag(MouseEvent e)
    {
        if(getTree() == null)
            return null;
        TreePath path=getTree().getPathForLocation(e.getX(),e.getY());
        //��ѡ��Ŀ�ⲿ���ؽ���˵���ǩ
        if(path == null)
            return getOutdropPopupMenuTag();
        //ѡ����ѡ��Ŀ
        getTree().setSelectionPath(path);
        //�õ���Ŀ�ڲ������˵���ǩ
        TTreeNode node = (TTreeNode)path.getLastPathComponent();
        if(node.getPopupMenuTag() != null && node.getPopupMenuTag().trim().length() > 0)
            return node.getPopupMenuTag();
        //�õ����Ͷ�Ӧ�ĵ����˵���ǩ
        String tag = getTypePopupMenuTag(node.getType());
        if(tag != null)
            return tag;
        //�õ����������˵���ǩ
        if(node.isRoot())
            return getRootPopupMenuTags();
        //�õ���Ŀ�����˵���ǩ
        return getItemPopupMenuTags();
    }
    /**
     * ��������CheckBox
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
     * ��������RadioButton
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
     * �õ�Model
     * @return TTreeModel
     */
    public TTreeModel getModel()
    {
        return (TTreeModel)getTree().getModel();
    }
    /**
     * �õ���
     * @return TTreeNode
     */
    public TTreeNode getRoot()
    {
        return (TTreeNode)getModel().getRoot();
    }
    /**
     * �õ�ѡ�б�ǩ
     * @return TTreeNode
     */
    public TTreeNode getSelectionNode()
    {
        return (TTreeNode)getTree().getLastSelectedPathComponent();
    }
    /**
     * ����
     */
    public void update()
    {
        getTree().updateUI();
    }
    /**
     * ѡ�б�ǩ
     * @param node TTreeNode[]
     */
    public void setSelectionNode(TTreeNode node[])
    {
        getTree().setSelectionNode(node);
    }
    /**
     * ѡ�б�ǩ
     * @param node TTreeNode
     */
    public void setSelectNode(TTreeNode node)
    {
        setSelectionNode(new TTreeNode[]{node});
    }
    /**
     * �õ��Լ�����
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * չ����
     * @param layer int
     */
    public void expandLayer(int layer)
    {
        TTreeNode node = getRoot();
        expandLayer(node,layer);
    }
    /**
     * չ����
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
     * չ����ǩ
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
     * ������
     * @param layer int
     */
    public void collapseLayer(int layer)
    {
        TTreeNode node = getRoot();
        collapseLayer(node,layer);
    }
    /**
     * ������
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
     * ������ǩ
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
     * ����˫����Ŀ��Ϣ
     * @param action String
     */
    public void setDoubleClickedItemAction(String action) {
        actionList.setAction("doubleClickedItemAction",action);
    }

    /**
     * �õ�˫����Ŀ��Ϣ
     * @return String
     */
    public String getDoubleClickedItemAction() {
        return actionList.getAction("doubleClickedItemAction");
    }
    /**
     * ���õ����Ŀ����
     * @param action String
     */
    public void setClickedItemAction(String action)
    {
        actionList.setAction("clickedItemAction",action);
    }
    /**
     * �õ������Ŀ����
     * @return String
     */
    public String getClickedItemAction()
    {
        return actionList.getAction("clickedItemAction");
    }
    /**
     * ����չ������
     * @param action String
     */
    public void setTreeExpandedAction(String action)
    {
        actionList.setAction("treeExpandedAction",action);
    }
    /**
     * �õ�չ������
     * @return String
     */
    public String getTreeExpandedAction()
    {
        return actionList.getAction("treeExpandedAction");
    }
    /**
     * ���úϲ�����
     * @param action String
     */
    public void setTreeCollapsedAction(String action)
    {
        actionList.setAction("treeCollapsedAction",action);
    }
    /**
     * �õ��ϲ�����
     * @return String
     */
    public String getTreeCollapsedAction()
    {
        return actionList.getAction("treeCollapsedAction");
    }
    /**
     * �����и߶�
     * @param rowHeight int
     */
    public void setRowHeight(int rowHeight)
    {
        getTree().setRowHeight(rowHeight);
    }
    /**
     * �õ��и߶�
     * @return int
     */
    public int getRowHeight()
    {
        return getTree().getRowHeight();
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * ��������
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
