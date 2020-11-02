package com.dongyang.jdo;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.data.TParm;
import java.util.Map;

public class MaxLoad
{
    private List list = new ArrayList();
    /**
     * 增加加载信息
     * @param inf MaxLoadInf
     */
    public void addInf(MaxLoadInf inf)
    {
        list.add(inf);
    }
    /**
     * 得到个数
     * @return int
     */
    public int size()
    {
        return list.size();
    }
    /**
     * 得到加载信息
     * @param index int
     * @return MaxLoadInf
     */
    public MaxLoadInf getInf(int index)
    {
        return (MaxLoadInf)list.get(index);
    }
    /**
     * 输出调试
     */
    public void out()
    {
        for(int i = 0;i < size();i++)
            System.out.println(i + " " + getInf(i));
    }
    /**
     * 得到传输数据
     * @return List
     */
    public List getData()
    {
        List list = new ArrayList(size());
        for(int i = 0;i < size();i++)
        {
            List inf = new ArrayList();
            MaxLoadInf d = getInf(i);
            inf.add(d.getType());
            inf.add(d.getModuleName());
            inf.add(d.getMethodName());
            inf.add(d.getInParm().getData());
            list.add(inf);
        }
        return list;
    }
    public void run()
    {
        MaxLoadRun run = new MaxLoadRun();
        List list = run.run(getData());
        for(int i = 0;i < size();i++)
        {
            MaxLoadInf inf = getInf(i);
            if(inf.getType() == 1)
            {
                TParm result = new TParm();
                result.setData((Map)list.get(i));
                inf.getCombo().setParmValue(result);
            }
        }
    }
}
