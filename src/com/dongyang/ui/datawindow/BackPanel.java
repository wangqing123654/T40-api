package com.dongyang.ui.datawindow;

import javax.swing.*;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Color;
import java.awt.event.*;
import java.awt.Dimension;
import java.awt.print.*;
import java.util.*;
import com.dongyang.config.INode;
import com.dongyang.ui.base.TDWToolBase;
import com.dongyang.ui.event.TDWToolEvent;

/**
 *
 * <p>Title: 工具背景面板</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class BackPanel
    extends WorkPanel
{
  //动作状态
  private String state = "";
  //选中控制设备
  private SelectControl selectControl = new SelectControl(this);
  //区域调节条
  private BoundPanel boundHeader = new BoundPanel();
  private BoundPanel boundDetail = new BoundPanel();
  private BoundPanel boundSummary = new BoundPanel();
  private BoundPanel boundFooter = new BoundPanel();
  //区域框
  private HeaderPanel headerPanel = new HeaderPanel();
  private DetailPanel detailPanel = new DetailPanel();
  private SummaryPanel summaryPanel = new SummaryPanel();
  private FooterPanel footerPanel = new FooterPanel();
  //键盘事件扑获器
  private JTextArea keyListener = new JTextArea();
  //xml数据
  private INode xmlElement;
  //xml控制器
  private ConfigDataWindowFile configXML;
  //日志回滚控制器
  private WorkLog workLog = new WorkLog(this);
  private TDWToolBase tool;
  /**
   * 构造器
   */
  public BackPanel(TDWToolBase tool)
  {
      this.tool = tool;
      configXML = new ConfigDataWindowFile();
      jbInit();
      load();
  }

  /**
   * 初始化
   * @throws java.lang.Exception
   */
  public void jbInit()
  {
    this.setLayout(null);
    boundHeader.setText("页头区");
    boundHeader.setTag("Header");
    boundDetail.setText("数据区");
    boundDetail.setTag("Detail");
    boundSummary.setText("合计区");
    boundSummary.setTag("Summary");
    boundFooter.setText("页脚区");
    boundFooter.setTag("Footer");
    headerPanel.setArea("Header");
    detailPanel.setArea("Detail");
    summaryPanel.setArea("Summary");
    footerPanel.setArea("Footer");
    headerPanel.setBounds(new Rectangle(0, 0, 1024, 30));
    boundHeader.setBounds(new Rectangle(0, 30, 1024, 13));
    detailPanel.setBounds(new Rectangle(0, 43, 1024, 30));
    boundDetail.setBounds(new Rectangle(0, 73, 1024, 13));
    summaryPanel.setBounds(new Rectangle(0, 86, 1024, 30));
    boundSummary.setBounds(new Rectangle(0, 116, 1024, 13));
    footerPanel.setBounds(new Rectangle(0, 129, 1024, 30));
    boundFooter.setBounds(new Rectangle(0, 159, 1024, 13));
    this.setBackground(new Color(113, 111, 100));
    this.setPreferredSize(new Dimension(1024, 768));
    this.setSize(new Dimension(1024, 768));
    keyListener.setBackground(new Color(113, 111, 100));
    keyListener.setBorder(BorderFactory.createLineBorder(Color.black));
    keyListener.setVerifyInputWhenFocusTarget(true);
    keyListener.setEditable(false);
    keyListener.setText("DYDataWindow Tool");
    keyListener.setBounds(new Rectangle(0, 0, 1, 1));
    this.add(headerPanel, null);
    this.add(boundHeader, null);
    this.add(detailPanel, null);
    this.add(boundDetail, null);
    this.add(summaryPanel, null);
    this.add(boundSummary, null);
    this.add(footerPanel, null);
    this.add(boundFooter, null);
    this.add(keyListener, null);
    keyListener.addKeyListener(selectControl);
  }

  /**
   * 得到选中控制
   * @return 控制对象
   */
  public SelectControl getSelectControl()
  {
    return selectControl;
  }

  /**
   * 得到当前状态
   * @return String 状态
   */
  public String getState()
  {
    return state;
  }

  /**
   * 设置当前状态
   * @param state 状态
   */
  public void setState(String state)
  {
    this.state = state;
  }

  /**
   * 得到区域对象
   * @param tag 区域标记名称
   * @return 区域对象
   */
  public BoundPanel getBound(String tag)
  {
    if (tag.equals("Header"))
      return boundHeader;
    if (tag.equals("Detail"))
      return boundDetail;
    if (tag.equals("Summary"))
      return boundSummary;
    if (tag.equals("Footer"))
      return boundFooter;
    return null;
  }

  /**
   * 区域尺寸改变
   * @param y 高度
   * @param tag 区域标记名称
   */
  public void resizeArea(int y, String tag)
  {
    if (tag.equals("Detail"))
      y += boundHeader.getY() + boundHeader.getHeight();
    if (tag.equals("Summary"))
      y += boundDetail.getY() + boundDetail.getHeight();
    if (tag.equals("Footer"))
      y += boundSummary.getY() + boundSummary.getHeight();
    resizeEvent(0, y, getBound(tag), tag);
  }

  /**
   * 调整尺寸事件
   * @param x
   * @param y
   * @param obj 改变的对象
   * @param tag 对象标记
   */
  public void resizeEvent(int x, int y, Object obj, String tag)
  {
    WorkPanel object = null;
    if (obj instanceof WorkPanel)
      object = (WorkPanel) obj;
    object.setLocation(x, y);
    if (tag.equals("Header"))
    {
      //页头区尺寸改变
      if (y < 0)
      {
        y = 0;
        object.setY(y);
      }
      headerPanel.setHeight(y);
      detailPanel.setY(y + object.getHeight());
      boundDetail.setY(detailPanel.getY() + detailPanel.getHeight());
      summaryPanel.setY(boundDetail.getY() + boundDetail.getHeight());
      boundSummary.setY(summaryPanel.getY() + summaryPanel.getHeight());
      footerPanel.setY(boundSummary.getY() + boundSummary.getHeight());
      boundFooter.setY(footerPanel.getY() + footerPanel.getHeight());
    }
    else if (tag.equals("Detail"))
    {
      //数据区尺寸改变
      if (y < boundHeader.getY() + boundHeader.getHeight())
      {
        y = boundHeader.getY() + boundHeader.getHeight();
        object.setY(y);
      }
      detailPanel.setHeight(y - detailPanel.getY());
      boundDetail.setY(detailPanel.getY() + detailPanel.getHeight());
      summaryPanel.setY(boundDetail.getY() + boundDetail.getHeight());
      boundSummary.setY(summaryPanel.getY() + summaryPanel.getHeight());
      footerPanel.setY(boundSummary.getY() + boundSummary.getHeight());
      boundFooter.setY(footerPanel.getY() + footerPanel.getHeight());
    }
    else if (tag.equals("Summary"))
    {
      //计算区尺寸改变
      if (y < boundDetail.getY() + boundDetail.getHeight())
      {
        y = boundDetail.getY() + boundDetail.getHeight();
        object.setY(y);
      }
      summaryPanel.setHeight(y - summaryPanel.getY());
      boundSummary.setY(summaryPanel.getY() + summaryPanel.getHeight());
      footerPanel.setY(boundSummary.getY() + boundSummary.getHeight());
      boundFooter.setY(footerPanel.getY() + footerPanel.getHeight());
    }
    else if (tag.equals("Footer"))
    {
      //页脚区尺寸改变
      if (y < boundSummary.getY() + boundSummary.getHeight())
      {
        y = boundSummary.getY() + boundSummary.getHeight();
        object.setY(y);
      }
      footerPanel.setHeight(y - footerPanel.getY());
    }
    this.setPreferredHeight(boundFooter.getY() + boundFooter.getHeight());
    selectControl.mouseDragged(0, 0);
  }

  /**
   * 检测在那个区中
   * @param y 纵坐标
   * @param height 高度
   * @return 操作区对象
   */
  public AreaPanel checkPanel(int y, int height)
  {
    if (footerPanel.checkPanel(y, height))
      return footerPanel;
    if (summaryPanel.checkPanel(y, height))
      return summaryPanel;
    if (detailPanel.checkPanel(y, height))
      return detailPanel;
    if (headerPanel.checkPanel(y, height))
      return headerPanel;
    return null;
  }

  /**
   * 检测是否横向越界
   * @param width 宽度
   */
  public void checkPreferredWidth(int width)
  {
    if (getWidth() < width)
    {
      boundHeader.setWidth(width);
      boundDetail.setWidth(width);
      boundSummary.setWidth(width);
      boundFooter.setWidth(width);

      headerPanel.setWidth(width);
      detailPanel.setWidth(width);
      summaryPanel.setWidth(width);
      footerPanel.setWidth(width);

      this.setPreferredWidth(width);
    }
  }

  /**
   * 检测扩选
   * @param x1 左上 x
   * @param y1 左上 y
   * @param x2 右下 x
   * @param y2 右下 y
   */
  public void checkInclude(int x1, int y1, int x2, int y2)
  {
    headerPanel.checkInclude(x1 - headerPanel.getX(), y1 - headerPanel.getY(),
                             x2 - headerPanel.getX(), y2 - headerPanel.getY());
    detailPanel.checkInclude(x1 - detailPanel.getX(), y1 - detailPanel.getY(),
                             x2 - detailPanel.getX(), y2 - detailPanel.getY());
    summaryPanel.checkInclude(x1 - summaryPanel.getX(), y1 - summaryPanel.getY(),
                              x2 - summaryPanel.getX(), y2 - summaryPanel.getY());
    footerPanel.checkInclude(x1 - footerPanel.getX(), y1 - footerPanel.getY(),
                             x2 - footerPanel.getX(), y2 - footerPanel.getY());
  }

  /**
   * 得到焦点
   */
  public void grabFocus()
  {
    keyListener.grabFocus();
  }

  /**
   * 检索区域面板
   * @param area 区域名称
   * @return 区域面板对象
   */
  public AreaPanel getArea(String area)
  {
    if (headerPanel.getArea().equals(area))
      return headerPanel;
    if (detailPanel.getArea().equals(area))
      return detailPanel;
    if (summaryPanel.getArea().equals(area))
      return summaryPanel;
    if (footerPanel.getArea().equals(area))
      return footerPanel;
    return null;
  }

  /**
   * 全部选中
   */
  public void selectAll()
  {
    headerPanel.selectAll();
    detailPanel.selectAll();
    summaryPanel.selectAll();
    footerPanel.selectAll();
  }

  /**
   * 加载 DataWindow XML文件
   * @param element
   * @return
   */
  public boolean load(INode element)
  {
    reset();
    xmlElement = element;
    headerPanel.load();
    detailPanel.load();
    summaryPanel.load();
    footerPanel.load();
    return true;
  }

  /**
   * 加载空工具
   * @return
   */
  public boolean load()
  {
    return load( (String)null);
  }

  /**
   * 根据SQL语句加载数据
   * @param sql
   * @return
   */
  public boolean load(String sql)
  {
    return load(getConfigManager().create(sql));
  }
  /**
   * 设置SQL
   * @param sql String
   */
  public void setSql(String sql)
  {
      getConfig().getChildElement("tables").getChildElement("sql").setValue(sql);
  }
  /**
   * 得到SQL
   * @return String
   */
  public String getSql()
  {
      return getConfig().getChildElement("tables").getChildElement("sql").getValue();
  }
  /**
   * 根据文件名加载数据
   * @param filename
   * @return
   */
  public boolean loadFile(String filename)
  {
    return load(getConfigManager().read(filename));
  }

  /**
   * 根据文件名保存数据
   * @param filename
   */
  public void saveFile(String filename)
  {
    getConfigManager().saveXML(filename, getConfig());
  }

  /**
   * 得到xml数据对象
   * @return xml数据对象
   */
  public INode getConfig()
  {
    return xmlElement;
  }

  /**
   * 得到xml数据项
   * @param name 数据项名称
   * @return xml数据对象
   */
  public INode getConfigElement(String name)
  {
    return xmlElement.getChildElement(name);
  }

  /**
   * 得到xml控制器对象
   * @return xml控制器对象
   */
  public ConfigDataWindowFile getConfigManager()
  {
    return configXML;
  }

  /**
   * 全部清除
   */
  public void reset()
  {
    selectControl.removeSelects();
    selectAll();
    selectControl.delete();
    getWorkLog().reset();
  }

  /**
   * 日志保存
   * @param name 名称
   * @param type 类型
   */
  public void saveLog(String name, String type)
  {
    workLog.saveLog(name, type);
  }

  /**
   * 得到日志回滚控制对象
   * @return
   */
  public WorkLog getWorkLog()
  {
    return workLog;
  }

  /**
   * 得到组件对象,从所有区域中查找
   * @param id 组件编号
   * @param type 组件类型
   * @return 组件对象
   */
  public DComponent getObjectForID(int id, String type)
  {
    DComponent component = headerPanel.getObjectForID(id, type);
    if (component == null)
      component = detailPanel.getObjectForID(id, type);
    if (component == null)
      component = summaryPanel.getObjectForID(id, type);
    if (component == null)
      component = footerPanel.getObjectForID(id, type);
    return component;
  }

  /**
   * 得到组件对象,从所有区域中查找
   * @param name "组件类型:组件编号"
   * @return 组件对象
   */
  public DComponent getObjectForID(String name)
  {
    int id = 0;
    String type = "";
    int index = name.indexOf(":");
    if (index > 0)
    {
      id = Integer.parseInt(name.substring(index + 1, name.length()));
      type = name.substring(0, index);
    }
    return getObjectForID(id, type);
  }

  /**
   * 鼠标点击
   * @param e
   */
  public void mousePressed(MouseEvent e)
  {
    grabFocus();
  }

  /**
   * 得到打印页面尺寸
   * @return 页面对象
   */
  private Paper getPaper()
  {
    Paper paper = new Paper();
    INode print = getConfigElement("Print");
    double x = print.getAttributeValueAsDouble("ImageableX").doubleValue();
    double y = print.getAttributeValueAsDouble("ImageableY").doubleValue();
    double width = print.getAttributeValueAsDouble("ImageableWidth").
        doubleValue();
    double height = print.getAttributeValueAsDouble("ImageableHeight").
        doubleValue();
    double w = print.getAttributeValueAsDouble("Width").doubleValue();
    double h = print.getAttributeValueAsDouble("Height").doubleValue();
    switch (print.getAttributeValueAsDouble("Orientation").intValue())
    {
      case PageFormat.PORTRAIT: //纵向
        paper.setImageableArea(x, y, width, height);
        paper.setSize(w, h);
        break;
      case PageFormat.REVERSE_LANDSCAPE: //横向
        paper.setImageableArea(y, x, height, width);
        paper.setSize(h, w);
        break;
    }
    return paper;
  }

  /**
   * 得到打印格式对象
   * @return 打印格式对象
   */
  public PageFormat getPageFormat()
  {
    INode print = getConfigElement("Print");
    PageFormat pageFormat = new PageFormat();
    pageFormat.setPaper(getPaper());
    pageFormat.setOrientation(print.getAttributeValueAsDouble("Orientation").
                              intValue());
    return pageFormat;
  }

  /**
   * 设置打印格式
   * @param pageFormat 打印格式对象
   */
  public void setPageFormat(PageFormat pageFormat)
  {
    INode print = getConfigElement("Print");
    print.setAttributeValue("ImageableX", pageFormat.getImageableX());
    print.setAttributeValue("ImageableY", pageFormat.getImageableY());
    print.setAttributeValue("ImageableWidth", pageFormat.getImageableWidth());
    print.setAttributeValue("ImageableHeight", pageFormat.getImageableHeight());
    print.setAttributeValue("Width", pageFormat.getWidth());
    print.setAttributeValue("Height", pageFormat.getHeight());
    print.setAttributeValue("Orientation", pageFormat.getOrientation());
  }

  /**
   * 弹出打印设置窗口
   */
  public void printSetup()
  {
    setPageFormat(PrinterJob.getPrinterJob().pageDialog(getPageFormat()));
  }

  /**
   * 处理外部功能调用请求
   * @param action 功能名称
   * @param parm 参数
   */
  public void callEventListener(String action, Object parm)
  {
    if (action.equals("EDIT_TEXT"))
      editText( (String) parm);
    else if (action.equals("EDIT_FONT"))
      editFont( (String) parm);
    else if (action.equals("EDIT_FONT_SIZE"))
      editFontSize(Integer.parseInt( (String) parm));
    else if (action.equals("EDIT_BORDER"))
      editBorder( (String) parm);
    else if (action.equals("SELECT_OBJECT"))
      onSelectObject(parm);
    else if (action.equals("EDIT_ALIGNMENT"))
      editAlignment( ( (Integer) parm).intValue());
    else if (action.equals("EDIT_TEXT_COLOR"))
      editTextColor( (Color) parm);
    else if (action.equals("EDIT_BACK_COLOR"))
      editBackColor( (Color) parm);
    else if (action.equals("EDIT_B"))
      editB( ( (Boolean) parm).booleanValue());
    else if (action.equals("EDIT_I"))
      editI( ( (Boolean) parm).booleanValue());
  }

  /**
   * 选中对象事件
   * @param parm
   */
  private void onSelectObject(Object parm)
  {
    getTool().callEvent(TDWToolEvent.TEXT_EDIT_END,new Object[]{},new String[]{});
    if (getSelectControl().getSelectCount() == 1)
    {
      if (parm instanceof DText)
      {
        DText text = (DText) parm;
        if (text.getType().equals("text"))
            getTool().callEvent(TDWToolEvent.TEXT_EDIT_START,new Object[]{text.getText()},new String[]{"java.lang.String"});
        getTool().callEvent(TDWToolEvent.SELECT_ONE_TEXT,new Object[]{text},new String[]{"com.dongyang.ui.datawindow.DText"});
      }
    }
    if (getSelectControl().getSelectCount() > 0)
      callEventListener("SELECT_START", parm);
    else
      callEventListener("SELECT_END", parm);
  }

  /**
   * 编辑文字
   * @param label
   */
  public void editText(String label)
  {
    List selectobjlist = getSelectControl().getSelectObjects();
    if (selectobjlist.size() == 1)
      if (selectobjlist.get(0)instanceof DText)
      {
        DText text = (DText) selectobjlist.get(0);
        if (text.getType().equals("text"))
        {
          text.setText(label);
          getWorkLog().saveLog("text:" + text.getID(), "Edit Text");
        }
      }

  }

  /**
   * 编辑字体
   * @param font
   */
  public void editFont(String font)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      if (!text.getFont().getFontName().equals(font))
      {
        text.setFont(new Font(font, text.getFont().getStyle(),
                              text.getFont().getSize()));
        getWorkLog().saveLog(text.getType() + ":" + text.getID(), "Edit Font");
      }
    }
  }

  /**
   * 编辑字号
   * @param size
   */
  public void editFontSize(int size)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      if (text.getFont().getSize() != size)
      {
        text.setFont(new Font(text.getFont().getFontName(),
                              text.getFont().getStyle(), size));
        getWorkLog().saveLog(text.getType() + ":" + text.getID(),
                             "Edit Font Size");
      }
    }
  }

  /**
   * 编辑边框
   * @param border
   */
  private void editBorder(String border)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      if (!text.getBorderTag().equals(border))
      {
        text.setBorderTag(border);
        getWorkLog().saveLog(text.getType() + ":" + text.getID(), "Edit Border");
      }
    }
  }

  /**
   * 修改队列
   * @param alignment
   */
  public void editAlignment(int alignment)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      if (text.getHorizontalAlignment() != alignment)
      {
        text.setHorizontalAlignment(alignment);
        getWorkLog().saveLog(text.getType() + ":" + text.getID(),
                             "Edit Alignment");
      }
    }
  }

  /**
   * 修改字体颜色
   * @param color
   */
  private void editTextColor(Color color)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      System.out.println(color);
      if (!text.getForeground().equals(color))
      {
        text.setForeground(color);
        getWorkLog().saveLog(text.getType() + ":" + text.getID(),
                             "Edit Text Color");
      }
    }
  }

  /**
   * 修改背景颜色
   * @param color
   */
  private void editBackColor(Color color)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      System.out.println(color);
      if (!text.getBackground().equals(color))
      {
        text.setTransparence(color == null);
        text.setBackground(color);
        getWorkLog().saveLog(text.getType() + ":" + text.getID(),
                             "Edit Back Color");
      }
    }
  }

  /**
   * 粗体
   * @param b
   */
  public void editB(boolean b)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      int style = text.getFont().getStyle();
      if ((b && (style == 0 || style == 2)) || (!b && ( style == 1 || style == 3)))
      {
        style = b?style == 2?3:1:style == 3?2:0;
        text.setFont(new Font(text.getFont().getFontName(),style,
                              text.getFont().getSize()));
        getWorkLog().saveLog(text.getType() + ":" + text.getID(),
                             "Edit Font Style");
      }
    }
  }

  /**
   * 斜体
   * @param b
   */
  public void editI(boolean b)
  {
    if (getSelectControl().getSelectCount() == 0)
      return;
    List selectobjlist = getSelectControl().getSelectObjects();
    for (int i = 0; i < selectobjlist.size(); i++)
    {
      if (! (selectobjlist.get(i)instanceof DText))
        continue;
      DText text = (DText) selectobjlist.get(i);
      int style = text.getFont().getStyle();
      if ((b && (style == 0 || style == 1)) || (!b && ( style == 2 || style == 3)))
      {
        style = b?style == 1?3:2:style == 3?1:0;
        text.setFont(new Font(text.getFont().getFontName(),style,
                              text.getFont().getSize()));
        getWorkLog().saveLog(text.getType() + ":" + text.getID(),
                             "Edit Font Style");
      }
    }
  }
  /**
   * 得到工具
   * @return TDWToolBase
   */
  public TDWToolBase getTool()
  {
      return tool;
  }
  /**
   * 撤销
   */
  public void onUndo()
  {
    getWorkLog().undo();
  }

  /**
   * 恢复
   */
  public void onRedo()
  {
    getWorkLog().redo();
  }

  /**
   * 剪切
   */
  public void onCut()
  {
    getSelectControl().cut();
  }

  /**
   * 拷贝
   */
  public void onCopy()
  {
    getSelectControl().copy();
  }

  /**
   * 粘贴
   */
  public void onPaste()
  {
    getSelectControl().paste();
  }

  /**
   * 删除
   */
  public void onDelete()
  {
    getSelectControl().delete(true);
  }

  /**
   * 全选
   */
  public void onSelectAll()
  {
    getSelectControl().selectAll();
  }

  /**
   * 能否拷贝
   * @return true 能 false 否
   */
  public boolean canCopy()
  {
    return getSelectControl().canCopy();
  }
}
