package com.dongyang.ui.event;

import java.util.Map;
import java.util.HashMap;
import java.util.Vector;
import java.lang.reflect.Method;
import com.dongyang.util.StringTool;

public class BaseEvent {
    private Map events;
    public BaseEvent() {
        events = new HashMap();
    }

    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void add(String eventName, Object object, String methodName) {
        if(exists(eventName,object,methodName))
            return;
        Vector data = (Vector) events.get(eventName);
        if (data == null) {
            data = new Vector();
            events.put(eventName, data);
        }
        data.add(new Node(object, methodName));
    }
    /**
     * �Ƿ���ڼ���
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     * @return boolean
     */
    public boolean exists(String eventName, Object object, String methodName)
    {
        Vector data = (Vector) events.get(eventName);
        if (data == null)
            return false;
        for (int i = 0; i < data.size(); i++)
            if (((Node) data.get(i)).equals(object, methodName))
                return true;
        return false;
    }
    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void remove(String eventName, Object object, String methodName) {
        if(events == null)
            return;
        Vector data = (Vector) events.get(eventName);
        if (data == null)
            return;
        for (int i = 0; i < data.size(); i++)
            if (((Node) data.get(i)).equals(object, methodName)) {
                data.remove(i);
                return;
            }
    }
    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        //System.out.println("====eventName===="+eventName);
        Vector data = (Vector) events.get(eventName);

        if (data == null)
            return null;

        Object value = null;
        for (int i = 0; i < data.size(); i++) {
            Node node = (Node) data.get(i);
            //test
            //System.out.println("data object===="+node.object);
            //System.out.println("data methodName===="+node.methodName);
            //end test
            try {
                Object returnValue = invokeMethod(node.object, node.methodName,
                                                  parms, parmType);
                if(returnValue == null)
                    continue;
                if(returnValue instanceof Boolean)
                {
                    if(!(boolean)(Boolean)returnValue)
                        return false;
                    value = true;
                }else
                    value = returnValue;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    /**
     * ���з���
     * @param object Object ����
     * @param methodName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @throws Exception
     * @return Object
     */
    private Object invokeMethod(Object object, String methodName,
                                Object[] parms,
                                String[] parmType) throws Exception {
        Method[] methods = object.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            if (!methods[i].getName().equals(methodName))
                continue;
            if (methods[i].getParameterTypes().length != parms.length)
                continue;
            boolean equalsParmType = true;
            for (int j = 0; j < parms.length; j++) {
                if (!StringTool.equalsObjectType(methods[i].getParameterTypes()[j].
                                                 getName(), parmType[j])) {
                    equalsParmType = false;
                    break;
                }
            }
            if (!equalsParmType)
                continue;
            return methods[i].invoke(object, parms);
        }
        /*String s="";
               for(int i = 0;i < parmType.length;i++)
               {
          s += parmType[i];
          if(i < parmType.length - 1 && s.length() > 0)
            s += ",";
               }
               throw new Exception("û���ҵ����� " + object.getClass().getName() + "." + methodName + "(" + s + ")");
         */
        return null;
    }

    private class Node {
        public Object object;
        public String methodName;
        public Node(Object object, String methodName) {
            this.object = object;
            this.methodName = methodName;
        }

        public boolean equals(Object object, String methodName) {
            return this.object == object &&
                    this.methodName.equals(methodName);
        }
    }
    /**
     * �ͷ�
     */
    public void release()
    {
        events = null;
    }
}
