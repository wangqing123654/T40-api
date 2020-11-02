package com.dongyang.ui.datawindow;

import com.dongyang.ui.base.TDataWindowBase;
import java.awt.Graphics;

public class DataWindowUI
{
    /**
     * 受控DataWindow对象
     */
    private TDataWindowBase datawindow;
    /**
     * 绘制文本组件的设备
     */
    private TextLabel textLabel = new TextLabel();
    /**
     * 数据更新标记
     */
    private boolean dataUpdate;
    /**
     * 数据存贮
     */
    private DataStoreUI dataStore;
    /**
     * 构造器
     * @param datawindow TDataWindowBase
     */
    public DataWindowUI(TDataWindowBase datawindow)
    {
      this.datawindow = datawindow;
    }
    /**
     * 设置数据是否更新
     * @param dataUpdate boolean
     */
    public void setDataUpdate(boolean dataUpdate)
    {
        this.dataUpdate = dataUpdate;
    }
    /**
     * 数据是否更新
     * @return boolean
     */
    public boolean isDataUpdate()
    {
        return dataUpdate;
    }
    /**
     * 初始化数据包
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
     * 得到绘制文本组件的设备
     * @return TextLabel
     */
    public TextLabel getLabel()
    {
      return textLabel;
    }
    /**
     * 图形绘制入口
     * @param g 图形设备
     * @param width 宽度
     * @param height 高度
     */
    public void paint(Graphics g, int width, int height)
    {
        onInitData();
        dataStore.paintHead(g);
    }
}
