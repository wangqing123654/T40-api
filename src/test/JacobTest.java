package test;
import com.jacob.com.*;
import com.jacob.activeX.*;

public class JacobTest
{
    public int aaa()
    {
        return 100;
    }
    public static void main(String args[])
    {
        ActiveXComponent sC = new ActiveXComponent("ScriptControl");
        Dispatch sControl = sC.getObject();
        //Dispatch.put(sControl, "Language", "VBScript");
        //Variant v = Dispatch.call(sControl, "Eval", "1+2*3");
        Dispatch.put(sControl, "Language", "JScript");
        Dispatch.call(sControl, "addCode", "function factorial(num){ return num + 100;}");
        Variant t1 = new Variant();
        //t1.putObject(new JacobTest());

        Dispatch.call(sControl, "addObject","aaa",new JacobTest());
        Variant t = new Variant();
        t.putInt(20);
        Variant v = Dispatch.call(sControl, "Run", "factorial",t);

        /*script.addCode('function factorial(num){'+
                     'result=1;for(ix=1;ix<=num;ix++){'+
                     'result = result*ix;};return result;};');
      s:=inputbox('demo','请输入要计算阶乘的数','');

      ShowMessage(script.Run('factorial',s));*/


        System.out.println(v.toString());

    }
    public static void main1(String args[])
    {
        ActiveXComponent MsWordApp = new ActiveXComponent("Word.Application");
        Dispatch.put(MsWordApp, "Visible", new Variant(true));
        Dispatch documents = Dispatch.get(MsWordApp, "Documents").toDispatch();
        Dispatch document = Dispatch.invoke(documents, "Open",Dispatch.Method,
                                   new Object[] { "D:\\Project\\client\\Word.doc", new Variant(false),
                                   new Variant(true) }, new int[1]).toDispatch();
        //Dispatch documents = Dispatch.get(MsWordApp, "Documents").toDispatch();
        //document = Dispatch.call(documents, "Add").toDispatch();
        Dispatch selection = Dispatch.get(MsWordApp, "Selection").toDispatch();
        Dispatch.put(selection, "Text", "aaaaaaaabbbbbbbccccc");

        Dispatch.invoke(document, "SaveAs", Dispatch.Method, new Object[] {
                        "saveFile", new Variant(0) }, new int[1]);
        Variant variant = new Variant(false);
        Dispatch.call(document, "Close", variant);

        Dispatch.call(document, "PrintOut");

        // 0 = wdDoNotSaveChanges
        // -1 = wdSaveChanges
        // -2 = wdPromptToSaveChanges
        Dispatch.call(document, "Close", new Variant(0));


        //Dispatch.call(MsWordApp, "Quit");
    }
}
