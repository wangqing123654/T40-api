package com.dongyang.ui.base;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import com.dongyang.ui.TTreeNode;

public class TTreeModel extends DefaultTreeModel{
    /**
      * Creates a tree in which any node can have children.
      *
      * @param root a TreeNode object that is the root of the tree
      * @see #TTreeModel(TreeNode, boolean)
      */
     public TTreeModel(TreeNode root) {
        super(root);
    }

    /**
      * Creates a tree specifying whether any node can have children,
      * or whether only certain nodes can have children.
      *
      * @param root a TreeNode object that is the root of the tree
      * @param asksAllowsChildren a boolean, false if any node can
      *        have children, true if each node is asked to see if
      *        it can have children
      * @see #asksAllowsChildren
      */
    public TTreeModel(TreeNode root, boolean asksAllowsChildren) {
        super(root,asksAllowsChildren);
    }
    public void valueForPathChanged(TreePath path, Object newValue) {
        MutableTreeNode   aNode = (MutableTreeNode)path.getLastPathComponent();
        if(!(aNode instanceof TTreeNode))
        {
            super.valueForPathChanged(path, newValue);
            return;
        }
        TTreeNode node = (TTreeNode)aNode;
        node.setValue("" + newValue);
    }
}
