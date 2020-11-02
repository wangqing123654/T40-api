package com.dongyang.util;

public class Parameter
{
  public String name;
  public Object values[];
  public String toString()
  {
      StringBuffer sb = new StringBuffer();
      sb.append("Parameter{name=");
      sb.append(name);
      sb.append(" values[");
      for(int i = 0;i < values.length;i++)
      {
          sb.append(values[i]);
          sb.append(" ");
      }
      sb.append("]}");
      return sb.toString();
  }
}
