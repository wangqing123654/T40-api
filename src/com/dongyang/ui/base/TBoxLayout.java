package com.dongyang.ui.base;

import java.awt.LayoutManager2;
import java.awt.AWTError;
import javax.swing.SizeRequirements;
import java.io.PrintStream;
import java.awt.ComponentOrientation;
import java.io.Serializable;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Component;
import java.awt.Dimension;
import com.dongyang.ui.TToolButton;

/**
 *
 * <p>Title: ���ֹ�����</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.10.27
 * @version 1.0
 */
public class TBoxLayout implements LayoutManager2, Serializable {

    /**
     * Specifies that components should be laid out left to right.
     */
    public static final int X_AXIS = 0;

    /**
     * Specifies that components should be laid out top to bottom.
     */
    public static final int Y_AXIS = 1;

    /**
     * Specifies that components should be laid out in the direction of
     * a line of text as determined by the target container's
     * <code>ComponentOrientation</code> property.
     */
    public static final int LINE_AXIS = 2;

    /**
     * Specifies that components should be laid out in the direction that
     * lines flow across a page as determined by the target container's
     * <code>ComponentOrientation</code> property.
     */
    public static final int PAGE_AXIS = 3;

    /**
     * Creates a layout manager that will lay out components along the
     * given axis.
     *
     * @param target  the container that needs to be laid out
     * @param axis  the axis to lay out components along. Can be one of:
     *              <code>BoxLayout.X_AXIS</code>,
     *              <code>BoxLayout.Y_AXIS</code>,
     *              <code>BoxLayout.LINE_AXIS</code> or
     *              <code>BoxLayout.PAGE_AXIS</code>
     *
     * @exception AWTError  if the value of <code>axis</code> is invalid
     */
    public TBoxLayout(Container target, int axis)
    {
        if (axis != X_AXIS && axis != Y_AXIS &&
            axis != LINE_AXIS && axis != PAGE_AXIS)
        {
            throw new AWTError("Invalid axis");
        }
        this.axis = axis;
        this.target = target;
    }

    /**
     * Constructs a BoxLayout that
     * produces debugging messages.
     *
     * @param target  the container that needs to be laid out
     * @param axis  the axis to lay out components along. Can be one of:
     *              <code>BoxLayout.X_AXIS</code>,
     *              <code>BoxLayout.Y_AXIS</code>,
     *              <code>BoxLayout.LINE_AXIS</code> or
     *              <code>BoxLayout.PAGE_AXIS</code>
     *
     * @param dbg  the stream to which debugging messages should be sent,
     *   null if none
     */
    TBoxLayout(Container target, int axis, PrintStream dbg)
    {
        this(target, axis);
        this.dbg = dbg;
    }

    /**
     * Indicates that a child has changed its layout related information,
     * and thus any cached calculations should be flushed.
     * <p>
     * This method is called by AWT when the invalidate method is called
     * on the Container.  Since the invalidate method may be called
     * asynchronously to the event thread, this method may be called
     * asynchronously.
     *
     * @param target  the affected container
     *
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public synchronized void invalidateLayout(Container target)
    {
        checkContainer(target);
        xChildren = null;
        yChildren = null;
        xTotal = null;
        yTotal = null;
    }

    /**
     * Not used by this class.
     *
     * @param name the name of the component
     * @param comp the component
     */
    public void addLayoutComponent(String name, Component comp)
    {
    }

    /**
     * Not used by this class.
     *
     * @param comp the component
     */
    public void removeLayoutComponent(Component comp)
    {
    }

    /**
     * Not used by this class.
     *
     * @param comp the component
     * @param constraints constraints
     */
    public void addLayoutComponent(Component comp, Object constraints)
    {
    }

    /**
     * Returns the preferred dimensions for this layout, given the components
     * in the specified target container.
     *
     * @param target  the container that needs to be laid out
     * @return the dimensions >= 0 && <= Integer.MAX_VALUE
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     * @see Container
     * @see #minimumLayoutSize
     * @see #maximumLayoutSize
     */
    public Dimension preferredLayoutSize(Container target)
    {
        Dimension size;
        synchronized (this)
        {
            checkContainer(target);
            checkRequests();
            size = new Dimension(xTotal.preferred, yTotal.preferred);
        }

        Insets insets = target.getInsets();
        size.width = (int) Math.min((long) size.width + (long) insets.left +
                                    (long) insets.right, Integer.MAX_VALUE);
        size.height = (int) Math.min((long) size.height + (long) insets.top +
                                     (long) insets.bottom, Integer.MAX_VALUE);
        return size;
    }

    /**
     * Returns the minimum dimensions needed to lay out the components
     * contained in the specified target container.
     *
     * @param target  the container that needs to be laid out
     * @return the dimensions >= 0 && <= Integer.MAX_VALUE
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     * @see #preferredLayoutSize
     * @see #maximumLayoutSize
     */
    public Dimension minimumLayoutSize(Container target)
    {
        Dimension size;
        synchronized (this)
        {
            checkContainer(target);
            checkRequests();
            size = new Dimension(xTotal.minimum, yTotal.minimum);
        }

        Insets insets = target.getInsets();
        size.width = (int) Math.min((long) size.width + (long) insets.left +
                                    (long) insets.right, Integer.MAX_VALUE);
        size.height = (int) Math.min((long) size.height + (long) insets.top +
                                     (long) insets.bottom, Integer.MAX_VALUE);
        return size;
    }

    /**
     * Returns the maximum dimensions the target container can use
     * to lay out the components it contains.
     *
     * @param target  the container that needs to be laid out
     * @return the dimenions >= 0 && <= Integer.MAX_VALUE
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     * @see #preferredLayoutSize
     * @see #minimumLayoutSize
     */
    public Dimension maximumLayoutSize(Container target)
    {
        Dimension size;
        synchronized (this)
        {
            checkContainer(target);
            checkRequests();
            size = new Dimension(xTotal.maximum, yTotal.maximum);
        }

        Insets insets = target.getInsets();
        size.width = (int) Math.min((long) size.width + (long) insets.left +
                                    (long) insets.right, Integer.MAX_VALUE);
        size.height = (int) Math.min((long) size.height + (long) insets.top +
                                     (long) insets.bottom, Integer.MAX_VALUE);
        return size;
    }

    /**
     * Returns the alignment along the X axis for the container.
     * If the box is horizontal, the default
     * alignment will be returned. Otherwise, the alignment needed
     * to place the children along the X axis will be returned.
     *
     * @param target  the container
     * @return the alignment >= 0.0f && <= 1.0f
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public synchronized float getLayoutAlignmentX(Container target)
    {
        checkContainer(target);
        checkRequests();
        return xTotal.alignment;
    }

    /**
     * Returns the alignment along the Y axis for the container.
     * If the box is vertical, the default
     * alignment will be returned. Otherwise, the alignment needed
     * to place the children along the Y axis will be returned.
     *
     * @param target  the container
     * @return the alignment >= 0.0f && <= 1.0f
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public synchronized float getLayoutAlignmentY(Container target)
    {
        checkContainer(target);
        checkRequests();
        return yTotal.alignment;
    }

    /**
     * Called by the AWT <!-- XXX CHECK! --> when the specified container
     * needs to be laid out.
     *
     * @param target  the container to lay out
     *
     * @exception AWTError  if the target isn't the container specified to the
     *                      BoxLayout constructor
     */
    public void layoutContainer(Container target)
    {
        checkContainer(target);
        int nChildren = target.getComponentCount();
        int[] xOffsets = new int[nChildren];
        int[] xSpans = new int[nChildren];
        int[] yOffsets = new int[nChildren];
        int[] ySpans = new int[nChildren];

        Dimension alloc = target.getSize();
        Insets in = target.getInsets();
        alloc.width -= in.left + in.right;
        alloc.height -= in.top + in.bottom;

        // Resolve axis to an absolute value (either X_AXIS or Y_AXIS)
        ComponentOrientation o = target.getComponentOrientation();
        int absoluteAxis = resolveAxis(axis, o);
        boolean ltr = (absoluteAxis != axis) ? o.isLeftToRight() : true;

        // determine the child placements
        synchronized (this)
        {
            checkRequests();

            if (absoluteAxis == X_AXIS)
            {
                SizeRequirements.calculateTiledPositions(alloc.width, xTotal,
                        xChildren, xOffsets,
                        xSpans, ltr);
                SizeRequirements.calculateAlignedPositions(alloc.height, yTotal,
                        yChildren, yOffsets,
                        ySpans);
            } else
            {
                SizeRequirements.calculateAlignedPositions(alloc.width, xTotal,
                        xChildren, xOffsets,
                        xSpans, ltr);
                SizeRequirements.calculateTiledPositions(alloc.height, yTotal,
                        yChildren, yOffsets,
                        ySpans);
            }
        }
        int x0 = target.getWidth() - in.right - 10;
        // flush changes to the container
        for (int i = 0; i < nChildren; i++)
        {
            Component c = target.getComponent(i);
            if(c instanceof TToolButton)
            {
                TToolButton button = (TToolButton)c;
                if(button.isToRight())
                {
                    Dimension typ = c.getPreferredSize();
                    x0 -= typ.width;
                    c.setBounds(x0, in.top, typ.width,typ.height);
                    continue;
                }


            }
            c.setBounds((int) Math.min((long) in.left + (long) xOffsets[i],
                                       Integer.MAX_VALUE),
                        (int) Math.min((long) in.top + (long) yOffsets[i],
                                       Integer.MAX_VALUE),
                        xSpans[i], ySpans[i]);

        }
    }

    void checkContainer(Container target)
    {
        if (this.target != target)
        {
            throw new AWTError("BoxLayout can't be shared");
        }
    }

    void checkRequests()
    {
        if (xChildren == null || yChildren == null)
        {
            // The requests have been invalidated... recalculate
            // the request information.
            int n = target.getComponentCount();
            xChildren = new SizeRequirements[n];
            yChildren = new SizeRequirements[n];
            for (int i = 0; i < n; i++)
            {
                Component c = target.getComponent(i);
                if (!c.isVisible())
                {
                    xChildren[i] = new SizeRequirements(0, 0, 0,
                            c.getAlignmentX());
                    yChildren[i] = new SizeRequirements(0, 0, 0,
                            c.getAlignmentY());
                    continue;
                }
                if(c instanceof TToolButton)
                {
                    TToolButton button = (TToolButton)c;
                    if(button.isToRight())
                    {
                        xChildren[i] = new SizeRequirements(0, 0, 0,
                                c.getAlignmentX());
                        yChildren[i] = new SizeRequirements(0, 0, 0,
                                c.getAlignmentY());
                        continue;
                    }
                }

                Dimension min = c.getMinimumSize();
                Dimension typ = c.getPreferredSize();
                Dimension max = c.getMaximumSize();
                xChildren[i] = new SizeRequirements(min.width, typ.width,
                        max.width,
                        c.getAlignmentX());
                yChildren[i] = new SizeRequirements(min.height, typ.height,
                        max.height,
                        c.getAlignmentY());
            }

            // Resolve axis to an absolute value (either X_AXIS or Y_AXIS)
            int absoluteAxis = resolveAxis(axis, target.getComponentOrientation());

            if (absoluteAxis == X_AXIS)
            {
                xTotal = SizeRequirements.getTiledSizeRequirements(xChildren);
                yTotal = SizeRequirements.getAlignedSizeRequirements(yChildren);
            } else
            {
                xTotal = SizeRequirements.getAlignedSizeRequirements(xChildren);
                yTotal = SizeRequirements.getTiledSizeRequirements(yChildren);
            }
        }
    }

    /**
     * Given one of the 4 axis values, resolve it to an absolute axis.
     * The relative axis values, PAGE_AXIS and LINE_AXIS are converted
     * to their absolute couterpart given the target's ComponentOrientation
     * value.  The absolute axes, X_AXIS and Y_AXIS are returned unmodified.
     *
     * @param axis the axis to resolve
     * @param o the ComponentOrientation to resolve against
     * @return the resolved axis
     */
    private int resolveAxis(int axis, ComponentOrientation o)
    {
        int absoluteAxis;
        if (axis == LINE_AXIS)
        {
            absoluteAxis = o.isHorizontal() ? X_AXIS : Y_AXIS;
        } else if (axis == PAGE_AXIS)
        {
            absoluteAxis = o.isHorizontal() ? Y_AXIS : X_AXIS;
        } else
        {
            absoluteAxis = axis;
        }
        return absoluteAxis;
    }


    private int axis;
    private Container target;

    private transient SizeRequirements[] xChildren;
    private transient SizeRequirements[] yChildren;
    private transient SizeRequirements xTotal;
    private transient SizeRequirements yTotal;

    private transient PrintStream dbg;
}
