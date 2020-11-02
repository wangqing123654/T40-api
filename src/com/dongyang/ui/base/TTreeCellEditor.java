package com.dongyang.ui.base;

import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.JTree;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.Font;
import java.awt.Component;
import javax.swing.tree.TreePath;
import com.dongyang.ui.TTreeNode;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.Icon;

public class TTreeCellEditor extends DefaultTreeCellEditor{
    private String tText = "";
    /**
     * Constructs a <code>TTreeCellEditor</code>
     * object for a JTree using the specified renderer and
     * a default editor. (Use this constructor for normal editing.)
     *
     * @param tree      a <code>JTree</code> object
     * @param renderer  a <code>DefaultTreeCellRenderer</code> object
     */
    public TTreeCellEditor(JTree tree,
                                 DefaultTreeCellRenderer renderer) {
        super(tree, renderer);
    }

    /**
     * Constructs a <code>DefaultTreeCellEditor</code>
     * object for a <code>JTree</code> using the
     * specified renderer and the specified editor. (Use this constructor
     * for specialized editing.)
     *
     * @param tree      a <code>JTree</code> object
     * @param renderer  a <code>DefaultTreeCellRenderer</code> object
     * @param editor    a <code>TreeCellEditor</code> object
     */
    public TTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer,
                                 TreeCellEditor editor) {
        super(tree,renderer,editor);
    }
    /**
     * Configures the editor.  Passed onto the <code>realEditor</code>.
     */
    public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                boolean isSelected,
                                                boolean expanded,
                                                boolean leaf, int row) {
        if(!(value instanceof TTreeNode))
            return super.getTreeCellEditorComponent(tree,value,isSelected,expanded,
                    leaf,row);
        TTreeNode node = (TTreeNode)value;
        tText = node.getText();
        String valueString = node.getValue();
        setTree(tree);
        lastRow = row;
        determineOffset(tree, value, isSelected, expanded, leaf, row);

        if (editingComponent != null) {
            editingContainer.remove(editingComponent);
        }
        editingComponent = realEditor.getTreeCellEditorComponent(tree, valueString,
                                        isSelected, expanded,leaf, row);


        // this is kept for backwards compatability but isn't really needed
        // with the current BasicTreeUI implementation.
        TreePath        newPath = tree.getPathForRow(row);

        canEdit = (lastPath != null && newPath != null &&
                   lastPath.equals(newPath));

        Font            font = getFont();

        if(font == null) {
            if(renderer != null)
                font = renderer.getFont();
            if(font == null)
                font = tree.getFont();
        }
        editingContainer.setFont(font);
        prepareForEditing();
        return editingContainer;
    }
    protected void determineOffset(JTree tree, Object value,
                                   boolean isSelected, boolean expanded,
                                   boolean leaf, int row) {
        if(renderer == null||!(renderer instanceof TTreeCellRenderer)) {
            super.determineOffset(tree,value,isSelected,expanded,leaf,row);
            return;
        }
        if(!(value instanceof TTreeNode))
        {
            super.determineOffset(tree,value,isSelected,expanded,leaf,row);
            return;
        }
        TTreeCellRenderer tReaderer = (TTreeCellRenderer)renderer;
        TTreeNode node = (TTreeNode)value;
        editingIcon = tReaderer.getPic(node.getType(),expanded);
        if(editingIcon != null)
            offset = renderer.getIconTextGap() +
                     editingIcon.getIconWidth() + 4;
        else
            offset = renderer.getIconTextGap();
        offset += tree.getFontMetrics(tree.getFont()).stringWidth(node.getText());
    }
    /**
     * Creates the container to manage placement of
     * <code>editingComponent</code>.
     */
    protected Container createContainer() {
        return new TEditorContainer();
    }
    public class TEditorContainer extends Container {
        /**
         * Constructs an <code>EditorContainer</code> object.
         */
        public TEditorContainer() {
            setLayout(null);
        }

        // This should not be used. It will be removed when new API is
        // allowed.
        public void EditorContainer() {
            setLayout(null);
        }

        /**
         * Overrides <code>Container.paint</code> to paint the node's
         * icon and use the selection color for the background.
         */
        public void paint(Graphics g) {
            Dimension        size = getSize();
            // Then the icon.
            int x = 0;
            if(editingIcon != null) {
                int       yLoc = Math.max(0, (getSize().height -
                                          editingIcon.getIconHeight()) / 2);

                editingIcon.paintIcon(this, g, 0, yLoc);
                x += editingIcon.getIconWidth() + 4;
            }
            int y = getFontMetrics(getFont()).getAscent() + 1;
            g.drawString(tText,x,y);
            // Border selection color
            Color       background = getBorderSelectionColor();
            if(background != null) {
                g.setColor(background);
                g.drawRect(0, 0, size.width - 1, size.height - 1);
            }
            super.paint(g);
        }

        /**
         * Lays out this <code>Container</code>.  If editing,
         * the editor will be placed at
         * <code>offset</code> in the x direction and 0 for y.
         */
        public void doLayout() {
            if(editingComponent != null) {
                Dimension             cSize = getSize();

                editingComponent.getPreferredSize();
                editingComponent.setLocation(offset, 0);
                editingComponent.setBounds(offset, 0,
                                           cSize.width - offset,
                                           cSize.height);
            }
        }

        /**
         * Returns the preferred size for the <code>Container</code>.
         * This will be at least preferred size of the editor plus
         * <code>offset</code>.
         * @return a <code>Dimension</code> containing the preferred
         *   size for the <code>Container</code>; if
         *   <code>editingComponent</code> is <code>null</code> the
         *   <code>Dimension</code> returned is 0, 0
         */
        public Dimension getPreferredSize() {
            if(editingComponent != null) {
                Dimension         pSize = editingComponent.getPreferredSize();

                pSize.width += offset + 5;

                Dimension         rSize = (renderer != null) ?
                                          renderer.getPreferredSize() : null;

                if(rSize != null)
                    pSize.height = Math.max(pSize.height, rSize.height);
                if(editingIcon != null)
                    pSize.height = Math.max(pSize.height,
                                            editingIcon.getIconHeight());

                // Make sure width is at least 100.
                pSize.width = Math.max(pSize.width, 100);
                return pSize;
            }
            return new Dimension(0, 0);
        }
    }
}
