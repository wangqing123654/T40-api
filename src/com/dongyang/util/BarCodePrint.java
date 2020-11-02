package com.dongyang.util;
//import com.tiis.ui.Print_Btn;
import java.util.Vector;

public class BarCodePrint
{
  int bH = 5;

  double bR = 3.0D;

  int bW = 2;

  String codeType = "CODE39";

  StringBuffer printText = new StringBuffer();
  int sX = 0;
  int sY = 0;

  public BarCodePrint()
  {
  }

  public BarCodePrint(String codeType)
  {
    setCodeType(codeType);
  }

  public void addBarcode(int x, int y, String str)
  {
    this.printText.append("^FO" + x + "," + y + getBarCodeType() + "^FD" + str + "^FS");
  }

  public void addText(int x, int y, int fontSize, String str)
  {
    this.printText.append(getCode(x, y, fontSize, str));
  }

  public void addText(int x, int y, String str)
  {
    addText(x, y, 24, str);
  }

  private String getBarCodeType()
  {
    if ("CODE39".equals(getCodeType()))
      return "^B3";
    if ("CODE128".equals(getCodeType()))
      return "^BCN";
    return "^B3";
  }

  public static String getCode(int x, int y, int fontSize, String str)
  {
    StringBuffer temp = new StringBuffer();
    try {
      for (int i = 0; i < str.length(); i++) {
        String s = str.substring(i, i + 1);
        byte[] ba = s.getBytes("GBK");
        if (ba.length == 1) {
          temp.append("^CI0^FO" + x + "," + (y + 4) + "^A0N," + fontSize + "," + fontSize + "^FD" + s + "^FS");

          x += fontSize / 2;
        } else if (ba.length == 2) {
          StringBuffer inTmp = new StringBuffer();
          for (int j = 0; j < 2; j++) {
            String hexStr = Integer.toHexString(ba[j] - 128);
            hexStr = hexStr.substring(hexStr.length() - 2);

            inTmp.append(hexStr + ")");
          }
          temp.append("^CI14^FO" + x + "," + y + "^AJN," + fontSize + "," + fontSize + "^FH)^FD)" + inTmp + "^FS");

          x += fontSize;
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return temp.toString();
  }

  private String getCodeType()
  {
    return this.codeType;
  }

  public static void main(String[] args)
  {
    BarCodePrint bcp = new BarCodePrint();
    bcp.setStart(32, 5);
    bcp.setBarHeight(50);
    bcp.addBarcode(35, 5, "123456789");
    bcp.addText(32, 120, "姓名: 王晓明");
    bcp.addText(200, 120, "性别: 男");
    bcp.addText(32, 145, "送验组: XXX组");
    bcp.addText(200, 145, "科室: 心脏外科");
    bcp.print("LPT1");
  }

  public void print(String print)
  {
    this.printText.insert(0, "^XA^LH" + this.sX + "," + this.sY + ",^BY" + this.bW + "," + this.bR + "," + this.bH + ",");

    this.printText.append("^XZ");
    Vector send = new Vector();
    
    System.out.println("-------111111------"+this.printText.toString());
    send.add(this.printText.toString());
    /*Print_Btn print_Btn1 = new Print_Btn();
    print_Btn1.sendDataTOPrint(send, print, false);*/
    
    //同步  送  打印机
    
    
  }

  public void setBarHeight(int h)
  {
    this.bH = h;
  }

  public void setBarRatio(double r)
  {
    this.bR = r;
  }

  public void setBarWedith(int w)
  {
    this.bW = w;
  }

  public void setBarcode(int w, double r, int h)
  {
    this.bW = w;
    this.bR = r;
    this.bH = h;
  }

  private void setCodeType(String codeType)
  {
    this.codeType = codeType;
  }

  public void setStart(int x, int y)
  {
    this.sX = x;
    this.sY = y;
  }
}