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
 * <p>Title: ���߱������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: JavaHis</p>
 * @author lzk 2006.07.10
 * @version 1.0
 */
public class BackPanel
    extends WorkPanel
{
  //����״̬
  private String state = "";
  //ѡ�п����豸
  private SelectControl selectControl = new SelectControl(this);
  //���������
  private BoundPanel boundHeader = new BoundPanel();
  private BoundPanel boundDetail = new BoundPanel();
  private BoundPanel boundSummary = new BoundPanel();
  private BoundPanel boundFooter = new BoundPanel();
  //�����
  private HeaderPanel headerPanel = new HeaderPanel();
  private DetailPanel detailPanel = new DetailPanel();
  private SummaryPanel summaryPanel = new SummaryPanel();
  private FooterPanel footerPanel = new FooterPanel();
  //�����¼��˻���
  private JTextArea keyListener = new JTextArea();
  //xml����
  private INode xmlElement;
  //xml������
  private ConfigDataWindowFile configXML;
  //��־�ع�������
  private WorkLog workLog = new WorkLog(this);
  private TDWToolBase tool;
  /**
   * ������
   */
  public BackPanel(TDWToolBase tool)
  {
      this.tool = tool;
      configXML = new ConfigDataWindowFile();
      jbInit();
      load();
  }

  /**
   * ��ʼ��
   * @throws java.lang.Exception
   */
  public void jbInit()
  {
    this.setLayout(null);
    boundHeader.setText("ҳͷ��");
    boundHeader.setTag("Header");
    boundDetail.setText("������");
    boundDetail.setTag("Detail");
    boundSummary.setText("�ϼ���");
    boundSummary.setTag("Summary");
    boundFooter.setText("ҳ����");
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
   * �õ�ѡ�п���
   * @return ���ƶ���
   */
  public SelectControl getSelectControl()
  {
    return selectControl;
  }

  /**
   * �õ���ǰ״̬
   * @return String ״̬
   */
  public String getState()
  {
    return state;
  }

  /**
   * ���õ�ǰ״̬
   * @param state ״̬
   */
  public void setState(String state)
  {
    this.state = state;
  }

  /**
   * �õ��������
   * @param tag ����������
   * @return �������
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
   * ����ߴ�ı�
   * @param y �߶�
   * @param tag ����������
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
   * �����ߴ��¼�
   * @param x
   * @param y
   * @param obj �ı�Ķ���
   * @param tag ������
   */
  public void resizeEvent(int x, int y, Object obj, String tag)
  {
    WorkPanel object = null;
    if (obj instanceof WorkPanel)
      object = (WorkPanel) obj;
    object.setLocation(x, y);
    if (tag.equals("Header"))
    {
      //ҳͷ���ߴ�ı�
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
      //�������ߴ�ı�
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
      //�������ߴ�ı�
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
      //ҳ�����ߴ�ı�
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
   * ������Ǹ�����
   * @param y ������
   * @param height �߶�
   * @return ����������
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
   * ����Ƿ����Խ��
   * @param width ���
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
   * �����ѡ
   * @param x1 ���� x
   * @param y1 ���� y
   * @param x2 ���� x
   * @param y2 ���� y
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
   * �õ�����
   */
  public void grabFocus()
  {
    keyListener.grabFocus();
  }

  /**
   * �����������
   * @param area ��������
   * @return ����������
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
   * ȫ��ѡ��
   */
  public void selectAll()
  {
    headerPanel.selectAll();
    detailPanel.selectAll();
    summaryPanel.selectAll();
    footerPanel.selectAll();
  }

  /**
   * ���� DataWindow XML�ļ�
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
   * ���ؿչ���
   * @return
   */
  public boolean load()
  {
    return load( (String)null);
  }

  /**
   * ����SQL����������
   * @param sql
   * @return
   */
  public boolean load(String sql)
  {
    return load(getConfigManager().create(sql));
  }
  /**
   * ����SQL
   * @param sql String
   */
  public void setSql(String sql)
  {
      getConfig().getChildElement("tables").getChildElement("sql").setValue(sql);
  }
  /**
   * �õ�SQL
   * @return String
   */
  public String getSql()
  {
      return getConfig().getChildElement("tables").getChildElement("sql").getValue();
  }
  /**
   * �����ļ�����������
   * @param filename
   * @return
   */
  public boolean loadFile(String filename)
  {
    return load(getConfigManager().read(filename));
  }

  /**
   * �����ļ�����������
   * @param filename
   */
  public void saveFile(String filename)
  {
    getConfigManager().saveXML(filename, getConfig());
  }

  /**
   * �õ�xml���ݶ���
   * @return xml���ݶ���
   */
  public INode getConfig()
  {
    return xmlElement;
  }

  /**
   * �õ�xml������
   * @param name ����������
   * @return xml���ݶ���
   */
  public INode getConfigElement(String name)
  {
    return xmlElement.getChildElement(name);
  }

  /**
   * �õ�xml����������
   * @return xml����������
   */
  public ConfigDataWindowFile getConfigManager()
  {
    return configXML;
  }

  /**
   * ȫ�����
   */
  public void reset()
  {
    selectControl.removeSelects();
    selectAll();
    selectControl.delete();
    getWorkLog().reset();
  }

  /**
   * ��־����
   * @param name ����
   * @param type ����
   */
  public void saveLog(String name, String type)
  {
    workLog.saveLog(name, type);
  }

  /**
   * �õ���־�ع����ƶ���
   * @return
   */
  public WorkLog getWorkLog()
  {
    return workLog;
  }

  /**
   * �õ��������,�����������в���
   * @param id ������
   * @param type �������
   * @return �������
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
   * �õ��������,�����������в���
   * @param name "�������:������"
   * @return �������
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
   * �����
   * @param e
   */
  public void mousePressed(MouseEvent e)
  {
    grabFocus();
  }

  /**
   * �õ���ӡҳ��ߴ�
   * @return ҳ�����
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
      case PageFormat.PORTRAIT: //����
        paper.setImageableArea(x, y, width, height);
        paper.setSize(w, h);
        break;
      case PageFormat.REVERSE_LANDSCAPE: //����
        paper.setImageableArea(y, x, height, width);
        paper.setSize(h, w);
        break;
    }
    return paper;
  }

  /**
   * �õ���ӡ��ʽ����
   * @return ��ӡ��ʽ����
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
   * ���ô�ӡ��ʽ
   * @param pageFormat ��ӡ��ʽ����
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
   * ������ӡ���ô���
   */
  public void printSetup()
  {
    setPageFormat(PrinterJob.getPrinterJob().pageDialog(getPageFormat()));
  }

  /**
   * �����ⲿ���ܵ�������
   * @param action ��������
   * @param parm ����
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
   * ѡ�ж����¼�
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
   * �༭����
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
   * �༭����
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
   * �༭�ֺ�
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
   * �༭�߿�
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
   * �޸Ķ���
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
   * �޸�������ɫ
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
   * �޸ı�����ɫ
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
   * ����
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
   * б��
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
   * �õ�����
   * @return TDWToolBase
   */
  public TDWToolBase getTool()
  {
      return tool;
  }
  /**
   * ����
   */
  public void onUndo()
  {
    getWorkLog().undo();
  }

  /**
   * �ָ�
   */
  public void onRedo()
  {
    getWorkLog().redo();
  }

  /**
   * ����
   */
  public void onCut()
  {
    getSelectControl().cut();
  }

  /**
   * ����
   */
  public void onCopy()
  {
    getSelectControl().copy();
  }

  /**
   * ճ��
   */
  public void onPaste()
  {
    getSelectControl().paste();
  }

  /**
   * ɾ��
   */
  public void onDelete()
  {
    getSelectControl().delete(true);
  }

  /**
   * ȫѡ
   */
  public void onSelectAll()
  {
    getSelectControl().selectAll();
  }

  /**
   * �ܷ񿽱�
   * @return true �� false ��
   */
  public boolean canCopy()
  {
    return getSelectControl().canCopy();
  }
}
