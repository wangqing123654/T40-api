package com.dongyang.util;

import java.util.Vector;


public class TFix
{
    private Point start;
    private Point end;
    private int count;
    public class Point
    {
        int id;
        String name;
        Object data;
        Point up;
        Point down;
        Point left;
        Point right;
        Point head;
        Point foot;
        boolean using;
    }
}
