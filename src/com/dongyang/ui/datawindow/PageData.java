package com.dongyang.ui.datawindow;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.ui.base.TDataWindowBase;

public class PageData
{
  List pageList = new ArrayList();
  Data data;
  double zoom;
  int width;
  int height;
  class StartPosition
  {
    private int x;
    private int width;
    public StartPosition(int x,int width)
    {
      this.x = x;
      this.width = width;
    }
  }
  class Data
  {
    List header = new ArrayList();
    List footer = new ArrayList();
    List drawer = new ArrayList();
    List startPosition = new ArrayList();
    int userHeight;
    int startX;
    int startWidth;
    public void addPosition(int x,int width)
    {
      if(getStartWidth() < x + width)
        width = getStartWidth() - x;
      x += getStartX();
      addStartPosition(x,width);
    }
    public void addStartPosition(int x,int width)
    {
      setStartX(x);
      setStartWidth(width);
      startPosition.add(new StartPosition(x,width));
    }
    public int getStartPositionSize()
    {
      return startPosition.size();
    }
    public StartPosition getStartPosition(int index)
    {
      return (StartPosition)startPosition.get(index);
    }
    public void popStartPosition()
    {
      if(getStartPositionSize() == 0)
        return;
      startPosition.remove(getStartPositionSize() - 1);
      if(getStartPositionSize() == 0)
      {
        setStartX(0);
        setStartWidth(getWidth());
        return;
      }
      StartPosition position = getStartPosition(getStartPositionSize() - 1);
      setStartX(position.x);
      setStartWidth(position.width);
    }
    public void addHeader(TDataWindowBase datawindow,int height)
    {
      addHeader(datawindow,getStartX(),getUserHeight(),getStartWidth(),height);
    }
    public void addHeader(TDataWindowBase datawindow,int x,int y,int width,int height)
    {
      int meedHeight = getUserHeight() + height + getFooterHeight();

      if(meedHeight < getHeight())
      {
        DrawModule module = new DrawModule(datawindow,"Header",0,x,y,width,height);
        header.add(module);
        addDrawer(module);
        setUserHeight(getUserHeight() + height);
        return;
      }
      Data data = newPage();
      data.addHeader(datawindow,x,data.getUserHeight(),width,height);
    }
    public int getHeaderSize()
    {
      return header.size();
    }
    public DrawModule getHeader(int index)
    {
      return (DrawModule)header.get(index);
    }
    public void addFooter(TDataWindowBase datawindow,int height)
    {
      addFooter(datawindow,getStartX(),getUserHeight(),getStartWidth(),height);
    }
    public void addFooter(TDataWindowBase datawindow,int x,int y,int width,int height)
    {
      DrawModule module = new DrawModule(datawindow,"Detail",0,x,y,width,height);
      footer.add(module);
    }
    public int getFooterSize()
    {
      return footer.size();
    }
    public DrawModule getFooter(int index)
    {
      return (DrawModule)footer.get(index);
    }
    public void pop()
    {
      if(getHeaderSize() > 0)
        header.remove(getHeaderSize() - 1);
      if(getFooterSize() > 0)
      {
        DrawModule footerObject = (DrawModule)getFooter(getFooterSize() - 1);
        footer.remove(footerObject);
        addDrawer(footerObject.datawindow, "Footer", 0, getStartX(), getUserHeight(),
                  getStartWidth(), footerObject.height);
        setUserHeight(getUserHeight() + footerObject.height);
      }
      popStartPosition();
    }
    public void addDrawer(TDataWindowBase datawindow,String type,int row,int x,int y,int width,int height)
    {
      addDrawer(new DrawModule(datawindow,type,row,x,y,width,height));
    }
    public void addDrawer(DrawModule module)
    {
      drawer.add(module);
    }
    public int getDrawerSize()
    {
      return drawer.size();
    }
    public DrawModule getDrawer(int index)
    {
      return (DrawModule)drawer.get(index);
    }
    public void setUserHeight(int height)
    {
      userHeight = height;
    }
    public int getUserHeight()
    {
      return userHeight;
    }
    public void setStartX(int x)
    {
      startX = x;
    }
    public int getStartX()
    {
      return startX;
    }
    public void setStartWidth(int width)
    {
      startWidth = width;
    }
    public int getStartWidth()
    {
      return startWidth;
    }
    public boolean userHeight(TDataWindowBase datawindow,String type,int row,int height,boolean jump)
    {
      int meedHeight = getUserHeight() + height + getFooterHeight();
      if(meedHeight > getHeight())
        return false;
      addDrawer(datawindow,type,row,getStartX(),getUserHeight(),getStartWidth(),height);
      if(jump)
        setUserHeight(height + getUserHeight());
      return true;
    }
    public boolean moveHeight(int height)
    {
      if(height <= 0)
        return true;
      int meedHeight = getUserHeight() + height + getFooterHeight();
      if(meedHeight <= getHeight())
      {
        setUserHeight(height + getUserHeight());
        return true;
      }
      Data data = newPage();
      return data.moveHeight(height - getHeight() + getUserHeight() + getFooterHeight());
    }

    public int getHeaderHeight()
    {
      int height = 0;
      for(int i = 0;i < header.size();i++)
        height += ((DrawModule)header.get(i)).height;
      return height;
    }
    public int getFooterHeight()
    {
      int height = 0;
      for(int i = 0;i < footer.size();i++)
        height += ((DrawModule)footer.get(i)).height;
      return height;
    }
  }
  class DrawModule
  {
    int x;
    int y;
    int width;
    int height;
    int row;
    String type;
    TDataWindowBase datawindow;
    public DrawModule(TDataWindowBase datawindow,String type,int row,int x,int y,int width,int height)
    {
      this.x = x;
      this.y = y;
      this.width = width;
      this.height = height;
      this.row = row;
      this.type = type;
      this.datawindow = datawindow;
    }
    public String toString()
    {
      return "type=" + type + ",row=" + row + ",x=" + x + ",y=" + y + ",width=" + width + ",height=" + height;
    }
  }
  public void setZoom(double zoom)
  {
    this.zoom = zoom;
  }
  public double getZoom()
  {
    return zoom;
  }
  public void setWidth(int width)
  {
    this.width = width;
  }
  public int getWidth()
  {
    return width;
  }
  public void setHeight(int height)
  {
    this.height = height;
  }
  public int getHeight()
  {
    return height;
  }
  private Data getData()
  {
    return data;
  }
  public void setData(int page)
  {
    data = (Data)pageList.get(page);
  }
  public void addHeader(TDataWindowBase datawindow,int height)
  {
    getData().addHeader(datawindow,height);
  }
  public void addFooter(TDataWindowBase datawindow,int height)
  {
    getData().addFooter(datawindow,height);
  }
  public boolean userHeight(TDataWindowBase datawindow,String type,int row,int height)
  {
    return userHeight(datawindow,type,row,height,true);
  }
  public boolean userHeight(TDataWindowBase datawindow,String type,int row,int height,boolean jump)
  {
    return getData().userHeight(datawindow,type,row,height,jump);
  }
  public boolean moveHeight(int height)
  {
    return getData().moveHeight(height);
  }
  public void addPosition(int x,int width)
  {
    getData().addPosition(x,width);
  }
  public Data getEndPage()
  {
    if(pageList.size() == 0)
      return null;
    return (Data)pageList.get(pageList.size() - 1);
  }
  public int getPageCount()
  {
    return pageList.size();
  }
  public Data newPage()
  {
    Data forwardPage = getEndPage();
    data = new Data();
    pageList.add(data);
    data.setStartX(0);
    data.setStartWidth(getWidth());
    if(forwardPage == null)
      return data;
    data.setStartX(forwardPage.getStartX());
    data.setStartWidth(forwardPage.getStartWidth());
    for(int i = 0;i < forwardPage.getHeaderSize();i++)
    {
      DrawModule header = forwardPage.getHeader(i);
      data.addHeader(header.datawindow,header.x,data.getUserHeight(),header.width,header.height);
    }
    int height = getHeight();
    for(int i = 0;i < forwardPage.getFooterSize();i++)
    {
      DrawModule footer = forwardPage.getFooter(i);
      height -= footer.height;
      forwardPage.addDrawer(footer.datawindow, "Footer", 0, footer.x, height,
                footer.width, footer.height);
      data.addFooter(footer.datawindow,footer.x,data.getUserHeight(),footer.width,footer.height);
    }
    for(int i = 0;i < forwardPage.getStartPositionSize();i++)
    {
      StartPosition position = forwardPage.getStartPosition(i);
      data.addStartPosition(position.x,position.width);
    }
    return data;
  }
  public void closePage()
  {
    Data forwardPage = getEndPage();
    int height = getHeight();
    for(int i = 0;i < forwardPage.getFooterSize();i++)
    {
      DrawModule footer = forwardPage.getFooter(i);
      height -= footer.height;
      forwardPage.addDrawer(footer.datawindow, "Footer", 0, footer.x, height,
                footer.width, footer.height);
    }
  }
  public void pop()
  {
    getData().pop();
  }
  public int getDrawerSize()
  {
    return getData().getDrawerSize();
  }
  public int getDrawerX(int index)
  {
    return getData().getDrawer(index).x;
  }
  public int getDrawerY(int index)
  {
    return getData().getDrawer(index).y;
  }
  public int getDrawerWidth(int index)
  {
    return getData().getDrawer(index).width;
  }
  public int getDrawerHeight(int index)
  {
    return getData().getDrawer(index).height;
  }
  public int getDrawerRow(int index)
  {
    return getData().getDrawer(index).row;
  }
  public String getDrawerType(int index)
  {
    return getData().getDrawer(index).type;
  }
  public TDataWindowBase getDrawerDataWindow(int index)
  {
    return getData().getDrawer(index).datawindow;
  }
  public int getFooterSize()
  {
    return getData().getFooterSize();
  }
}
