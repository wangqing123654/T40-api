package com.dongyang.ui;

public class TListNode
{
    /**
     * 编号
     */
    private String id;
    /**
     * 名称
     */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 路径
     */
    private String path;
    /**
     * 状态
     */
    private String state;
    /**
     * 横坐标
     */
    private int x;
    /**
     * 纵坐标
     */
    private int y;
    /**
     * 设置编号
     * @param id String
     */
    public void setID(String id)
    {
        this.id = id;
    }
    /**
     * 得到编号
     * @return String
     */
    public String getID()
    {
        return id;
    }
    /**
     * 设置名称
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * 得到名称
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * 设置类型
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * 设置横坐标
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * 得到横坐标
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * 设置纵坐标
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * 得到纵坐标
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * 设置路径
     * @param path String
     */
    public void setPath(String path)
    {
        this.path = path;
    }
    /**
     * 得到路径
     * @return String
     */
    public String getPath()
    {
        return path;
    }
    /**
     * 设置状态
     * @param state String
     */
    public void setState(String state)
    {
        this.state = state;
    }
    /**
     * 得到状态
     * @return String
     */
    public String getState()
    {
        return state;
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("TListNode{id=");
        sb.append(id);
        sb.append(",name=");
        sb.append(name);
        sb.append(",type=");
        sb.append(type);
        sb.append("}");
        return sb.toString();
    }
}
