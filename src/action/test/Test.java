package action.test;

import com.dongyang.util.StringTool;
import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        String s[] = new String[]{"1","2"};
        System.out.println(StringTool.getString(s));
        System.out.println(StringTool.getString(addArray(s,"d")));
    }
    public static String[] addArray(String oldData[],String s)
    {
        String data[] = new String[oldData.length + 1];
        System.arraycopy(oldData, 0, data, 0, oldData.length);
        data[oldData.length] = s;
        return data;
    }
}
