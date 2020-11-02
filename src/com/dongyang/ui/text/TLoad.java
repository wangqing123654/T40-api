package com.dongyang.ui.text;

import com.dongyang.config.INode;

public class TLoad {
    public static IElement read(INode node)
    {
        String name = node.getName();
        IElement element = null;
        if("Text".equalsIgnoreCase(name))
            element = new TEText();
        else if("Table".equalsIgnoreCase(name))
            element = new TETable();
        else if("Caption".equalsIgnoreCase(name))
            element = new TECaption();
        else if("TR".equalsIgnoreCase(name))
            element = new TETR();
        else if("TD".equalsIgnoreCase(name))
            element = new TETD();
        else
            return null;
        element.read(node);
        return element;
    }
}
