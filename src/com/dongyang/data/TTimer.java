package com.dongyang.data;

abstract public class TTimer implements Runnable{
    public boolean isStart = true;
    public int timerCount = 100;
    public void run()
    {
        if(!init())
            return;
        while(true)
        {
            if(!isStart)
                return;
            work();
            try{
                Thread.sleep(timerCount);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }
    abstract public void work();
    public boolean init()
    {
        return true;
    }
    public void start()
    {
        new Thread(this).start();
    }
}
