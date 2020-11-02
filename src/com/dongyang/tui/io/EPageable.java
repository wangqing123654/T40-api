package com.dongyang.tui.io;

import java.awt.print.Pageable;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

/**
 *
 * <p>Title: ��ӡ����</p>
 *
 * <p>Description: �޶�JDK 1.4֮��Ĵ���</p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.4.28
 * @version 1.0
 */
public class EPageable implements Pageable
{
    private PageFormat mFormat;
    private Printable mPainter;

    public EPageable(PageFormat pageformat, Printable printable)
    {
        mFormat = pageformat;
        mPainter = printable;
    }

    public int getNumberOfPages()
    {
        return -1;
    }

    public PageFormat getPageFormat(int i)
    {
        return mFormat;
    }

    public Printable getPrintable(int i) throws IndexOutOfBoundsException
    {
        return mPainter;
    }
}
