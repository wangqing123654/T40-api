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
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
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
     * 是否存在监听
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
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
     * 删除监听方法
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
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName,new Object[]{},new String[]{});
    }
    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
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
     * 运行方法
     * @param object Object 对象
     * @param methodName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
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
               throw new Exception("没有找到方法 " + object.getClass().getName() + "." + methodName + "(" + s + ")");
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
     * 释放
     */
    public void release()
    {
        events = null;
    }
}
