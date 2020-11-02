package com.dongyang.ui.event;

import java.awt.EventQueue;
import java.awt.AWTEvent;
import java.awt.Toolkit;

public class TQueueEvent extends EventQueue
{
    private static TQueueEvent instance;
    public static boolean INIT;
    public static TQueueEvent getInstance()
    {
        if(instance == null)
        {
            instance = new TQueueEvent();
            Toolkit.getDefaultToolkit().getSystemEventQueue().push(instance);
            INIT = true;
        }
        return instance;
    }
    public TQueueEvent()
    {
    }
    public void dispatchEvent(AWTEvent ae)
    {
      try{
          super.dispatchEvent(ae);
      }catch(Exception e)
      {
          e.printStackTrace();
      }
      //System.out.println("end " + ae);
    }
}
