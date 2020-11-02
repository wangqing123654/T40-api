package com.dongyang.ui;

public class TListNode
{
    /**
     * ���
     */
    private String id;
    /**
     * ����
     */
    private String name;
    /**
     * ����
     */
    private String type;
    /**
     * ·��
     */
    private String path;
    /**
     * ״̬
     */
    private String state;
    /**
     * ������
     */
    private int x;
    /**
     * ������
     */
    private int y;
    /**
     * ���ñ��
     * @param id String
     */
    public void setID(String id)
    {
        this.id = id;
    }
    /**
     * �õ����
     * @return String
     */
    public String getID()
    {
        return id;
    }
    /**
     * ��������
     * @param name String
     */
    public void setName(String name)
    {
        this.name = name;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getName()
    {
        return name;
    }
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * ���ú�����
     * @param x int
     */
    public void setX(int x)
    {
        this.x = x;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getX()
    {
        return x;
    }
    /**
     * ����������
     * @param y int
     */
    public void setY(int y)
    {
        this.y = y;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getY()
    {
        return y;
    }
    /**
     * ����·��
     * @param path String
     */
    public void setPath(String path)
    {
        this.path = path;
    }
    /**
     * �õ�·��
     * @return String
     */
    public String getPath()
    {
        return path;
    }
    /**
     * ����״̬
     * @param state String
     */
    public void setState(String state)
    {
        this.state = state;
    }
    /**
     * �õ�״̬
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
