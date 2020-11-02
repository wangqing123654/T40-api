package com.dongyang.ui.base;

import javax.swing.JTree;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.tree.TreePath;
import com.dongyang.ui.TTreeNode;
import javax.swing.plaf.TreeUI;
import com.dongyang.ui.TUIStyle;
import com.dongyang.ui.TTree;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeExpansionEvent;

public class JTreeBase extends JTree{
    TTree tree;
    /**
     * 构造器
     * @param newModel TreeModel
     * @param tree TTree
     */
    public JTreeBase(TreeModel newModel,TTree tree) {
        super(newModel);
        uiInit();
        this.tree = tree;
        this.addTreeExpansionListener(new TreeExpansionListener(){
            public void treeExpanded(TreeExpansionEvent event)
            {
                TreePath treepath = event.getPath();
                TTreeNode node = (TTreeNode)treepath.getLastPathComponent();
                getTree().treeExpanded(node);
            }
            public void treeCollapsed(TreeExpansionEvent event)
            {
                TreePath treepath = event.getPath();
                TTreeNode node = (TTreeNode)treepath.getLastPathComponent();
                getTree().treeCollapsed(node);
            }
        });
    }
    /**
     * 内部初始化UI
     */
    protected void uiInit() {
        setFont(TUIStyle.getTreeDefaultFont());
    }
    /**
     * 更新UI
     */
    public void updateUI() {
        setUI(new TTreeUI());
        invalidate();
    }
    /**
     * 得到TTree
     * @return TTree
     */
    public TTree getTree()
    {
        return tree;
    }
    /**
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        return getTree().getLanguage();
    }
    /**
     * 定位节点
     * @param x int
     * @param y int
     * @return TTreeNode
     */
    public TTreeNode getPathForLocationNode(int x,int y)
    {
        TreePath tp = getPathForLocation(x,y);
        if(tp == null)
            return null;
        if(tp.getPathCount() == 0)
            return null;
        return (TTreeNode)tp.getLastPathComponent();
    }
    /**
     * 根据行号得到Node
     * @param row int 行号
     * @return TTreeNode node
     */
    public TTreeNode getNodeForRow(int row)
    {
        TreePath path = getPathForRow(row);
        if(path == null)
            return null;
        return (TTreeNode)path.getLastPathComponent();
    }
    /**
     * 根据node 查找行号
     * @param node TTreeNode
     * @return int 行号
     */
    public int getRowForNode(TTreeNode node)
    {
        for(int i = 0;i < getRowCount();i++)
        {
            if(getNodeForRow(i) == node)
                return i;
        }
        return -1;
    }
    /**
     * 选中标签
     * @param node TTreeNode[]
     */
    public void setSelectionNode(TTreeNode node[])
    {
        for(int i = 0;i < node.length;i++)
        {
            TreeNode tn[] = node[i].getPath();
            for(int j = 0;j < tn.length;j++)
                for(int k = 0;k < getRowCount();k++)
                    if (getNodeForRow(k) == tn[j])
                        expandRow(k);
        }
        int count = getRowCount();
        int rows[] = new int[node.length];
        for(int j = 0;j < node.length;j++)
            for(int i = 0;i < count;i++)
                if(getNodeForRow(i) == node[j])
                    rows[j] = i;
        setSelectionRows(rows);
    }
    public class TTreeUI extends com.sun.java.swing.plaf.windows.WindowsTreeUI
    {

    }

}
