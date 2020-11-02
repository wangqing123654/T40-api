package com.dongyang.jdo;

import java.util.List;
import java.util.ArrayList;
import com.dongyang.module.TModule;
import com.dongyang.data.TParm;
import java.util.Map;
import com.dongyang.util.TypeTool;

public class MaxLoadRun extends TStrike
{
    public List run(List in)
    {
        if(isClientlink())
            return (List)callServerMethod(in);
        List list = new ArrayList(in.size());
        for(int i = 0;i < in.size();i++)
        {
            List data = (List)in.get(i);
            if(TypeTool.getInt(data.get(0)) == 1)
            {
                TModule module = TModule.getModule((String)data.get(1));
                TParm SQLParm = new TParm();
                SQLParm.setData((Map)data.get(3));
                TParm result = module.query((String)data.get(2), SQLParm);
                list.add(result.getData());
            }
        }
        return list;
    }
}
