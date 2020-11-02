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
     * �����ļ�����
     */
    private TConfigParm config;
    /**
     * ���ͷ����
     */
    private Header header;
    /**
     * �����ܿ��
     */
    private double dataWidth;
    /**
     * �����ܸ߶�
     */
    private double dataHeight;
    /**
     * xml����
     */
    private INode node;
    /**
     * ���ݰ�
     */
    private TParm parm;
    /**
     * ������
     */
    public DataStoreUI()
    {
        header = new Header();
    }
    /**
     * ���������ļ�����
     * @param config TConfigParm
     */
    public void setConfigParm(TConfigParm config)
    {
        this.config = config;
        getHeader().setConfigParm(config);
    }
    /**
     * �õ������ļ�����
     * @return TConfigParm
     */
    public TConfigParm getConfigParm()
    {
        return config;
    }
    /**
     * �õ�ͷ����
     * @return Header
     */
    public Header getHeader()
    {
        return header;
    }

    /**
     * ���������ܿ��
     * @param dataWidth double
     */
    public void setDataWidth(double dataWidth)
    {
        this.dataWidth = dataWidth;
    }

    /**
     * �õ������ܿ��
     * @return double
     */
    public double getDataWidth()
    {
        return dataWidth;
    }

    /**
     * ���������ܸ߶�
     * @param dataHeight double
     */
    public void setDataHeight(double dataHeight)
    {
        this.dataHeight = dataHeight;
    }

    /**
     * �õ������ܸ߶�
     * @return double
     */
    public double getDataHeight()
    {
        return dataHeight;
    }

    /**
     * ����xml����
     * @param node INode
     */
    public void setNode(INode node)
    {
        this.node = node;
    }

    /**
     * �õ�xml����
     * @return INode
     */
    public INode getNode()
    {
        return node;
    }

    /**
     * �������ݰ�
     * @param parm TParm
     */
    public void setParm(TParm parm)
    {
        this.parm = parm;
    }

    /**
     * �õ����ݰ�
     * @return TParm
     */
    public TParm getParm()
    {
        return parm;
    }

    /**
     * ��ʼ��
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
