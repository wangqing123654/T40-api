package com.dongyang.tui;

/**
 *
 * <p>Title: ¡¨Ω”œﬂ</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.7
 * @version 1.0
 */
public class DLinkLine
{
    int toComponentIndex;
    DComponent toComponent;
    DComponent fromComponent;
    int toType;
    int fromType;
    int size;
    int type;
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(fromComponent != null?fromComponent.getTag():"null");
        sb.append(":");
        sb.append(fromType);
        sb.append("->");
        sb.append(toComponent != null?toComponent.getTag():"null");
        sb.append(":");
        sb.append(toType);
        sb.append(" ");
        sb.append(type);
        return sb.toString();
    }
}
