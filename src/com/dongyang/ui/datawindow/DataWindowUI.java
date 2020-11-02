package com.dongyang.ui.datawindow;

import com.dongyang.ui.base.TDataWindowBase;
import java.awt.Graphics;

public class DataWindowUI
{
    /**
     * �ܿ�DataWindow����
     */
    private TDataWindowBase datawindow;
    /**
     * �����ı�������豸
     */
    private TextLabel textLabel = new TextLabel();
    /**
     * ���ݸ��±��
     */
    private boolean dataUpdate;
    /**
     * ���ݴ���
     */
    private DataStoreUI dataStore;
    /**
     * ������
     * @param datawindow TDataWindowBase
     */
    public DataWindowUI(TDataWindowBase datawindow)
    {
      this.datawindow = datawindow;
    }
    /**
     * ���������Ƿ����
     * @param dataUpdate boolean
     */
    public void setDataUpdate(boolean dataUpdate)
    {
        this.dataUpdate = dataUpdate;
    }
    /**
     * �����Ƿ����
     * @return boolean
     */
    public boolean isDataUpdate()
    {
        return dataUpdate;
    }
    /**
     * ��ʼ�����ݰ�
     */
    public void onInitData()
    {
        if(!isDataUpdate())
        {
            dataStore = new DataStoreUI();
            dataStore.setConfigParm(datawindow.getConfigParm());
            dataStore.setNode(datawindow.getNodeData());
            dataStore.setParm(datawindow.getParmValue());
            dataStore.onInit();
            System.out.println(dataStore);
            setDataUpdate(true);
        }
    }
    /**
     * �õ������ı�������豸
     * @return TextLabel
     */
    public TextLabel getLabel()
    {
      return textLabel;
    }
    /**
     * ͼ�λ������
     * @param g ͼ���豸
     * @param width ���
     * @param height �߶�
     */
    public void paint(Graphics g, int width, int height)
    {
        onInitData();
        dataStore.paintHead(g);
    }
}
