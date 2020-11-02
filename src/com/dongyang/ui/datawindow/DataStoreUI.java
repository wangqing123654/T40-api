package com.dongyang.ui.datawindow;

import com.dongyang.config.INode;
import com.dongyang.data.TParm;
import java.util.Vector;
import com.dongyang.util.StringTool;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.config.TConfigParm;
import java.awt.Graphics;

public class DataStoreUI
{
    /**
     * 配置文件对象
     */
    private TConfigParm config;
    /**
     * 表格头对象
     */
    private Header header;
    /**
     * 数据总宽度
     */
    private double dataWidth;
    /**
     * 数据总高度
     */
    private double dataHeight;
    /**
     * xml数据
     */
    private INode node;
    /**
     * 数据包
     */
    private TParm parm;
    /**
     * 构造器
     */
    public DataStoreUI()
    {
        header = new Header();
    }
    /**
     * 设置配置文件对象
     * @param config TConfigParm
     */
    public void setConfigParm(TConfigParm config)
    {
        this.config = config;
        getHeader().setConfigParm(config);
    }
    /**
     * 得到配置文件对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return config;
    }
    /**
     * 得到头对象
     * @return Header
     */
    public Header getHeader()
    {
        return header;
    }

    /**
     * 设置数据总宽度
     * @param dataWidth double
     */
    public void setDataWidth(double dataWidth)
    {
        this.dataWidth = dataWidth;
    }

    /**
     * 得到数据总宽度
     * @return double
     */
    public double getDataWidth()
    {
        return dataWidth;
    }

    /**
     * 设置数据总高度
     * @param dataHeight double
     */
    public void setDataHeight(double dataHeight)
    {
        this.dataHeight = dataHeight;
    }

    /**
     * 得到数据总高度
     * @return double
     */
    public double getDataHeight()
    {
        return dataHeight;
    }

    /**
     * 设置xml数据
     * @param node INode
     */
    public void setNode(INode node)
    {
        this.node = node;
    }

    /**
     * 得到xml数据
     * @return INode
     */
    public INode getNode()
    {
        return node;
    }

    /**
     * 设置数据包
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }

    /**
     * 得到数据包
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }

    /**
     * 初始化
     */
    public void onInit()
    {
        if (getNode() == null)
            return;
        getHeader().initNode(getNode().getChildElement("Header"));
    }
    public void paintHead(Graphics g)
    {
        getHeader().paint(g);
    }
    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("DataStoreUI{\n");
        sb.append(getHeader());
        sb.append("\n}");
        return sb.toString();
    }
}
