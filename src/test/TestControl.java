package test;

import com.dongyang.control.TControl;
import com.dongyang.ui.event.TActionListener;
import com.dongyang.ui.TTable;
import com.dongyang.ui.base.JTableBase;
import javax.swing.table.TableCellEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JTextField;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import com.dongyang.ui.base.TTableCellEditor;
import com.dongyang.ui.TComboBox;
import com.dongyang.data.TParm;

public class TestControl extends TControl{
    /**
     * ³õÊ¼»¯
     */
    public void onInit() {
        addEventListener("AAA->" + TActionListener.ACTION_PERFORMED, this,"onAAA");
        CellEditorListener c;
        //addEventListener("Close->" + TActionListener.ACTION_PERFORMED, this,"onClose");

        TTable table = (TTable)this.callFunction("UI|findObject","table");
        table.setRowSelectionInterval(0,1);
        /*final JTableBase jtable = table.getTable();
        JTextField text = new JTextField();
        TTableCellEditor editor = new TTableCellEditor(text);
        jtable.getColumnModel().getColumn(0).setCellEditor(editor);
        //jtable.setCellEditor(editor);


        editor.addCellEditorListener(new CellEditorListener(){
            public void editingStopped(ChangeEvent e)
            {
                System.out.println("editingStopped ");
                System.out.println(jtable.getValueAt(jtable.getSelectedRow(),jtable.getSelectedColumn()));

            }

            public void editingCanceled(ChangeEvent e)
            {
                System.out.println("editingCanceled " + e);
            }

        });
        TableCellEditor editort = jtable.getCellEditor();
        System.out.println("jtable " + editort);
        for(int i = 0;i < jtable.getColumnCount();i++)
        {
            TableCellEditor editor1 = jtable.getColumnModel().getColumn(i).getCellEditor();
            System.out.println(i + " " + editor1);
        }

        System.out.println("table=====================" + table);*/
    }
    /*public Object callMessage(String message,Object parm)
    {
        Object value = super.callMessage(message,parm);
        System.out.println("Control message=" + message);
        return value;
    }*/
    public void onAAA()
    {
        //TTable table = (TTable)this.callFunction("UI|findObject","table");
        //table.setSelectedColumn(1);
        TComboBox comboBox = (TComboBox)this.callFunction("UI|findObject","ComboBox");
        comboBox.setSelectedID("02");
        /*TParm parm = new TParm();
        parm.addData("CODE","001");
        parm.addData("DESC","desc1");
        parm.addData("CODE","002");
        parm.addData("DESC","desc2");
        parm.addData("CODE","003");
        parm.addData("DESC","desc3");
        comboBox.setTParmData(parm);
        System.out.println("onAAA");*/
        //callMessage("UI|AAA|setEnabled|N");
        //System.out.println(callMessage("UI|AAA|getText"));
        //System.out.println(callMessage("UI|Button|setSize",new Object[]{100,200}));
    }
    public void onButton()
    {
        System.out.println("onButton");
        Object obj = callMessage("UI|Number|getValue");
        System.out.println(callMessage("UI|Number|getText"));
        //System.out.println(obj + " " + obj.getClass());
        callFunction("UI|Number|setValue","68.9");

    }
}
