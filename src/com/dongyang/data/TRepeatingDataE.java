package com.dongyang.data;

import java.util.Vector;

public class TRepeatingDataE extends TRepeatingData{
    private boolean initFlg = true;
    private Vector valueM = new Vector();
    private Vector valueD = new Vector();
    private Vector valueN = new Vector();
    private Vector valueF = new Vector();
    public void setValueM(Vector value) {
        this.valueM = value;
    }

    public Vector getValueM() {
        return valueM;
    }

    public void setValueD(Vector value) {
        this.valueD = value;
    }

    public Vector getValueD() {
        return valueD;
    }

    public void setValueN(Vector value) {
        this.valueN = value;
    }

    public Vector getValueN() {
        return valueN;
    }

    public void setValueF(Vector value) {
        this.valueF = value;
    }

    public Vector getValueF() {
        return valueF;
    }

    public int getModifiedCount() {
        return valueM.size();
    }

    public int getDeletedCount() {
        return valueD.size();
    }

    public int getNewCount() {
        return valueN.size();
    }

    public void addValueM(Object value) {
        getValueM().add(value);
    }

    public void insertValueM(Object value, int index) {
        getValueM().insertElementAt(value, index);
    }

    public void removeValueM(int index) {
        getValueM().removeElementAt(index);
    }

    public Object getM(int index) {
        return getValueM().get(index);
    }

    public void setM(int index, Object value) {
        getValueM().set(index, value);
    }

    public int indexM(Object value) {
        int index = getValueM().indexOf(value);
        if (index < 0)
            return index;
        return index;
    }

    public void addValueD(Object value) {
        getValueD().add(value);
    }

    public void insertValueD(Object value, int index) {
        getValueD().insertElementAt(value, index);
    }

    public void removeValueD(int index) {
        getValueD().removeElementAt(index);
    }

    public Object getD(int index) {
        return getValueD().get(index);
    }

    public void setD(int index, Object value) {
        getValueD().set(index, value);
    }
    public int indexD(Object value) {
        int index = getValueD().indexOf(value);
        if (index < 0)
            return index;
        return index;
    }
    public void addValueN(Object value) {
        getValueN().add(value);
    }

    public void insertValueN(Object value, int index) {
        getValueN().insertElementAt(value, index);
    }

    public void removeValueN(int index) {
        getValueN().removeElementAt(index);
    }

    public Object getN(int index) {
        return getValueN().get(index);
    }

    public void setN(int index, Object value) {
        getValueN().set(index, value);
    }

    public int indexN(Object value) {
        int index = getValueN().indexOf(value);
        if (index < 0)
            return index;
        return index;
    }

    public void addValueF(Object value) {
         getValueF().add(value);
    }

    public void insertValueF(Object value, int index) {
        getValueF().insertElementAt(value, index);
    }

    public void removeValueF(int index) {
        getValueF().removeElementAt(index);
    }

    public Object getF(int index) {
        return getValueF().get(index);
    }

    public void setF(int index, Object value) {
        getValueF().set(index, value);
    }

    public int indexF(Object value) {
        int index = getValueF().indexOf(value);
        if (index < 0)
            return index;
        return index;
    }
    public void clear()
    {
        super.clear();
        getValueM().clear();
        getValueD().clear();
        getValueN().clear();
        getValueF().clear();
    }
    public void setInit(boolean initFlg)
    {
        this.initFlg = initFlg;
    }
    public boolean isInit()
    {
        return initFlg;
    }
    public void addValue(Object value)
    {
        super.addValue(value);
        if(isInit())
            return;
        getValueN().add(value);
    }
    public void insertValue(Object value,int index)
    {
        super.insertValue(value,index);
        if(isInit())
            return;
        getValueN().add(value);
    }
    public void removeValue(int index)
    {
        Object oldValue = get(index);
        super.removeValue(index);
        if(isInit())
            return;
        getValueD().add(oldValue);
    }
    public static void main(String args[])
    {
        TRepeatingDataE t = new TRepeatingDataE();
        t.addValue("a");
        t.addValue("b");
        t.addValue("c");
        t.setInit(false);
        t.removeValue(2);
        t.addValue("d");
        t.insertValue("e",1);
        t.set(0,'1');
        System.out.println("P " + t.getValue());
        System.out.println("N " + t.getValueN());
        System.out.println("M " + t.getValueM());
        System.out.println("D " + t.getValueD());
    }
}

