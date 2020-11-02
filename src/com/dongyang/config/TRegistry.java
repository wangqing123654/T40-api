package com.dongyang.config;
import com.dongyang.util.Log;
import java.util.prefs.Preferences;
import java.lang.reflect.Method;
import java.io.ByteArrayOutputStream;

public class TRegistry
{
    private static final int DELETE = 0x10000;
    private static final int KEY_QUERY_VALUE = 1;
    private static final int KEY_SET_VALUE = 2;
    private static final int KEY_CREATE_SUB_KEY = 4;
    private static final int KEY_ENUMERATE_SUB_KEYS = 8;
    private static final int KEY_READ = 0x20019;
    private static final int KEY_WRITE = 0x20006;
    private static final int KEY_ALL_ACCESS = 0xf003f;
    private static final int HKEY_CURRENT_USER = 0x80000001;
    private static final int HKEY_LOCAL_MACHINE = 0x80000002;

    private Preferences userRoot;
    private Class clz;
    private Method openKey;
    private Method closeKey;
    private Method WindowsRegQueryValueEx;
    private Method WindowsRegSetValueEx;
    private Method WindowsRegEnumValue1;
    private Method WindowsRegQueryInfoKey1;
    private Method WindowsRegDeleteKey;
    private Method WindowsRegDeleteValue;
    /**
     * 日志系统
     */
    private Log log;
    public TRegistry()
    {
        this(HKEY_CURRENT_USER);
    }
    public TRegistry(int rootNativeHandle)
    {
        log = new Log();
        onInit(rootNativeHandle);
    }
    public void onInit(int rootNativeHandle)
    {
        if(rootNativeHandle == HKEY_CURRENT_USER)
            userRoot = Preferences.userRoot();
        else
            userRoot = Preferences.systemRoot();
        clz = userRoot.getClass();
        try{
            openKey = clz.getDeclaredMethod("openKey", byte[].class, int.class, int.class);
            openKey.setAccessible(true);
            closeKey = clz.getDeclaredMethod("closeKey",int.class);
            closeKey.setAccessible(true);
            WindowsRegQueryValueEx = clz.getDeclaredMethod("WindowsRegQueryValueEx", int.class, byte[].class);
            WindowsRegQueryValueEx.setAccessible(true);
            WindowsRegSetValueEx = clz.getDeclaredMethod("WindowsRegSetValueEx", int.class, byte[].class, byte[].class);
            WindowsRegSetValueEx.setAccessible(true);
            WindowsRegEnumValue1 = clz.getDeclaredMethod(
                    "WindowsRegEnumValue1", int.class, int.class, int.class);
            WindowsRegEnumValue1.setAccessible(true);
            WindowsRegQueryInfoKey1 = clz.getDeclaredMethod(
                    "WindowsRegQueryInfoKey1", int.class);
            WindowsRegQueryInfoKey1.setAccessible(true);
            WindowsRegDeleteKey = clz.getDeclaredMethod("WindowsRegDeleteKey", int.class, byte[].class);
            WindowsRegDeleteKey.setAccessible(true);
            WindowsRegDeleteValue = clz.getDeclaredMethod("WindowsRegDeleteValue", int.class, byte[].class);
            WindowsRegDeleteValue.setAccessible(true);

        }catch(Exception e)
        {
            err(e.getMessage());
        }
    }
    private int openKey(byte[] windowsAbsolutePath, int mask1, int mask2) {
        try{
            return (Integer) openKey.invoke(userRoot, windowsAbsolutePath,
                                            mask1, mask2);
        }catch(Exception e)
        {
            err(e.getMessage());
            return -1;
        }
    }
    public int openKey(String path)
    {
        return openKey(toByteArray(path), KEY_ALL_ACCESS, KEY_ALL_ACCESS);
    }
    public void closeKey(int nativeHandle) {
        try{
            closeKey.invoke(userRoot, nativeHandle);
        }catch(Exception e)
        {
            err(e.getMessage());
        }
    }
    private byte[] WindowsRegQueryValueEx(int hKey,byte[] valueName){
        try{
            return (byte[]) WindowsRegQueryValueEx.invoke(userRoot, hKey, valueName);
        }catch(Exception e)
        {
            err(e.getMessage());
            return null;
        }
    }
    public String read(int hKey,String name)
    {
        byte[] b = WindowsRegQueryValueEx(hKey, toByteArray(name));
        return b != null ? new String(b).trim() : null;
    }
    public static String get(String name)
    {
        TRegistry registry;
        if(name.toUpperCase().startsWith("HKEY_CURRENT_USER"))
        {
            registry = new TRegistry(HKEY_CURRENT_USER);
            name = name.substring("HKEY_CURRENT_USER".length(),name.length());
        }else if(name.toUpperCase().startsWith("HKEY_LOCAL_MACHINE"))
        {
            registry = new TRegistry(HKEY_LOCAL_MACHINE);
            name = name.substring("HKEY_LOCAL_MACHINE".length(),name.length());
        }else
            return null;
        if(name.startsWith("\\"))
            name = name.substring(1);
        int index = name.lastIndexOf("\\");
        String path = "";
        if(index > 0)
        {
            path = name.substring(0, index);
            name = name.substring(index + 1,name.length());
        }
        int hSettings = registry.openKey(path);
        String value = registry.read(hSettings,name);
        registry.closeKey(hSettings);
        return value;
    }
    public static boolean set(String name,String value)
    {
        TRegistry registry;
        if(name.toUpperCase().startsWith("HKEY_CURRENT_USER"))
        {
            registry = new TRegistry(HKEY_CURRENT_USER);
            name = name.substring("HKEY_CURRENT_USER".length(),name.length());
        }else if(name.toUpperCase().startsWith("HKEY_LOCAL_MACHINE"))
        {
            registry = new TRegistry(HKEY_LOCAL_MACHINE);
            name = name.substring("HKEY_LOCAL_MACHINE".length(),name.length());
        }else
            return false;
        if(name.startsWith("\\"))
            name = name.substring(1);
        int index = name.lastIndexOf("\\");
        String path = "";
        if(index > 0)
        {
            path = name.substring(0, index);
            name = name.substring(index + 1,name.length());
        }
        int hSettings = registry.openKey(path);
        boolean result = registry.write(hSettings,name,value);
        registry.closeKey(hSettings);
        return result;
    }
    public static boolean removeKey(String name)
    {
        TRegistry registry;
        if(name.toUpperCase().startsWith("HKEY_CURRENT_USER"))
        {
            registry = new TRegistry(HKEY_CURRENT_USER);
            name = name.substring("HKEY_CURRENT_USER".length(),name.length());
        }else if(name.toUpperCase().startsWith("HKEY_LOCAL_MACHINE"))
        {
            registry = new TRegistry(HKEY_LOCAL_MACHINE);
            name = name.substring("HKEY_LOCAL_MACHINE".length(),name.length());
        }else
            return false;
        if(name.startsWith("\\"))
            name = name.substring(1);
        int index = name.lastIndexOf("\\");
        String path = "";
        if(index > 0)
        {
            path = name.substring(0, index);
            name = name.substring(index + 1,name.length());
        }
        int hSettings = registry.openKey(path);
        boolean result = registry.WindowsRegDeleteKey(hSettings,name);
        registry.closeKey(hSettings);
        return result;
    }
    public static boolean removeValue(String name)
    {
        TRegistry registry;
        if(name.toUpperCase().startsWith("HKEY_CURRENT_USER"))
        {
            registry = new TRegistry(HKEY_CURRENT_USER);
            name = name.substring("HKEY_CURRENT_USER".length(),name.length());
        }else if(name.toUpperCase().startsWith("HKEY_LOCAL_MACHINE"))
        {
            registry = new TRegistry(HKEY_LOCAL_MACHINE);
            name = name.substring("HKEY_LOCAL_MACHINE".length(),name.length());
        }else
            return false;
        if(name.startsWith("\\"))
            name = name.substring(1);
        int index = name.lastIndexOf("\\");
        String path = "";
        if(index > 0)
        {
            path = name.substring(0, index);
            name = name.substring(index + 1,name.length());
        }
        int hSettings = registry.openKey(path);
        boolean result = registry.WindowsRegDeleteValue(hSettings,name);
        registry.closeKey(hSettings);
        return result;
    }
    private int WindowsRegSetValueEx(int hKey, byte[] valueName, byte[] value){
        try{
            return (Integer) WindowsRegSetValueEx.invoke(userRoot, hKey, valueName, value);
        }catch(Exception e)
        {
            err(e.getMessage());
            return -1;
        }
    }
    public boolean write(int hKey,String name,String value)
    {
        return WindowsRegSetValueEx(hKey,toByteArray(name),toByteArray(value)) == 0;
    }
    public byte[] WindowsRegEnumValue1(int hKey, int valueIndex,
                                      int maxValueNameLength) {
        try{
            return (byte[]) WindowsRegEnumValue1.invoke(userRoot, hKey,valueIndex,maxValueNameLength);
        }catch(Exception e)
        {
            err(e.getMessage());
            return null;
        }
    }
    public int[] WindowsRegQueryInfoKey1(int hKey) {
        try{
            return (int[]) WindowsRegQueryInfoKey1.invoke(userRoot, hKey);
        }catch(Exception e)
        {
            err(e.getMessage());
            return null;
        }
    }
    private int WindowsRegDeleteKey(int hKey, byte[] subKey)
    {
        try{
            return (Integer) WindowsRegDeleteKey.invoke(userRoot, hKey,subKey);
        }catch(Exception e)
        {
            err(e.getMessage());
            return -1;
        }
    }
    public boolean WindowsRegDeleteKey(int hKey,String subKey)
    {
        return WindowsRegDeleteKey(hKey,toByteArray(subKey)) == 0;
    }
    private int WindowsRegDeleteValue(int hKey, byte[] valueName)
    {
        try{
            return (Integer) WindowsRegDeleteValue.invoke(userRoot, hKey,valueName);
        }catch(Exception e)
        {
            err(e.getMessage());
            return -1;
        }
    }
    public boolean WindowsRegDeleteValue(int hKey,String valueName)
    {
        return WindowsRegDeleteValue(hKey,toByteArray(valueName)) == 0;
    }
    private static byte[] toByteArray(String str) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(str.length() * 2 + 1);
        byte[] data = str.getBytes();
        byte[] d = new byte[]{0};
        out.write(data, 0, data.length);
        out.write(d,0,1);
        try{
            out.close();
        }catch(Exception e){};
        return out.toByteArray();
    }
    /**
     * 日志输出
     * @param text String 日志内容
     */
    public void out(String text) {
        //log.out(text);
    }
    /**
     * 日志输出
     * @param text String 日志内容
     * @param debug boolean true 强行输出 false 不强行输出
     */
    public void out(String text,boolean debug)
    {
        //log.out(text,debug);
    }
    /**
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        //log.err(text);
    }
    public static void main(String args[]) {
        //System.out.println(TRegistry.set("HKEY_CURRENT_USER\\Software\\JavaHis\\aaa\\a1\\ccc","1"));
        System.out.println(TRegistry.get("HKEY_CURRENT_USER\\Software\\JavaHis\\aaa\\a1\\ccc"));
        //System.out.println(TRegistry.removeKey("HKEY_CURRENT_USER\\Software\\JavaHis\\aaa\\a1"));
        //System.out.println(TRegistry.removeValue("HKEY_CURRENT_USER\\Software\\JavaHis\\aaa\\bbb"));
        /*TRegistry registry = new TRegistry();
        final String subKey = "Software\\JavaHis";
        int hSettings = registry.openKey(subKey);
        System.out.println(registry.write(hSettings,"aaa","测试1"));
        System.out.println(registry.read(hSettings,"aaa"));
        registry.closeKey(hSettings);*/
    }

}
