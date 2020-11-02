package com.dongyang.config;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.io.StringReader;
import com.dongyang.data.TSocket;
import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.StringTool;
import com.dongyang.Service.Server;
import com.dongyang.util.FileTool;

public class TConfig {
    /**
     * �ļ���
     */
    String fileName;
    /**
     * �����б�
     */
    Map map;
    /**
     * �Ƿ�ӷ������϶��ļ� true �ӷ������� false �ӱ��ض�
     */
    boolean isReadServer;
    /**
     * ���Ӷ���
     */
    TSocket socket;
    /**
     * ����
     */
    private String type;
    /**
     * �Ƿ��ʼ��
     */
    private boolean isInit;
    /**
     * �õ������ļ�
     * @param filename String
     * @return TConfig
     */
    public static TConfig getConfig(String filename)
    {
        if(TIOM_AppServer.SOCKET != null)
            return new TConfig("%ROOT%\\" + filename,TIOM_AppServer.SOCKET);
        String dir = Server.getConfigDir();
        if(dir == null)
            return null;
        dir = dir.substring(0,dir.length() - 15);
        return new TConfig(dir + filename);
    }
    /**
     * �õ��ļ��������ĸ�Ŀ¼
     * @param name String
     * @return String
     */
    public static String getSystemValue(String name)
    {
        TConfig config = TConfig.getConfig("WEB-INF\\config\\system\\TConfig.x");
        return config.getString("",name);
    }
    /**
     * ������
     * @param fileName String INI�ļ���
     * @param socket TSocket ���Ӷ���
     */
    public TConfig(String fileName,TSocket socket) {
        this.fileName = fileName;
        if(socket != null)
            isReadServer = true;
        setSocket(socket);
        reset();
    }


    /**
     * �������Ӷ���
     * @param socket TSocket
     */
    public void setSocket(TSocket socket) {
        this.socket = socket;
    }

    /**
     * �õ����Ӷ���
     * @return TSocket
     */
    public TSocket getSocket() {
        return socket;
    }

    /**
     * ������(Ĭ�ϴӱ��ض�ȡ)
     * @param fileName String
     */
    public TConfig(String fileName) {
        this(fileName, null);
    }
    /**
     * �õ��ļ���
     * @return String
     */
    public String getFileName()
    {
        return fileName;
    }
    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    /**
     * ��������
     * @param type String
     */
    public void setType(String type)
    {
        this.type = type;
    }
    /**
     * �����Ƿ��ʼ��
     * @param isInit boolean true ��ʼ�� false û�г�ʼ��
     */
    public void setIsInit(boolean isInit)
    {
        this.isInit = isInit;
    }
    /**
     * �Ƿ��ʼ��
     * @return boolean true ��ʼ�� false û�г�ʼ��
     */
    public boolean isInit()
    {
        return isInit;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return type;
    }
    /**
     * ���¶�ȡ�ļ�
     * @return boolean
     */
    public boolean reset()
    {
        map = new HashMap();
        return reset(fileName,map);
    }
    /**
     * ���¶�ȡ�ļ�
     * @param fileName String
     * @param map Map
     * @return boolean
     */
    public boolean reset(String fileName,Map map) {
        if (isReadServer)
            return loadServer(fileName,map);
        return loadLocal(fileName,map);
    }

    /**
     * �ӷ���������Ini�ļ�
     * @param fileName String �ļ��� ��Ŀ¼Ϊ HIS\config
     * @param map Map
     * @return boolean
     */
    public boolean loadServer(String fileName,Map map) {
        long fileLastModified = TConfigStrike.getInstance().getFileLastModified(fileName);
        long localFileLastModified = getFileLastModified(fileName);
        if(localFileLastModified == fileLastModified)
            return loadLocal("c:\\JavaHis\\Application Data" + fileName.substring(6,fileName.length()),map);

        byte[] data = TIOM_AppServer.readFile(getSocket(), fileName);
        if (data == null)
            return false;
        saveLocalFile(fileName,data,fileLastModified);
        try {
            return load(new BufferedReader(new StringReader(new String(data))),map);
        } catch (Exception e) {
        }
        return false;
    }
    public long getFileLastModified(String fileName)
    {
        if(fileName == null || fileName.length() == 0)
            return -1;
        fileName = "c:\\JavaHis\\Application Data" + fileName.substring(6,fileName.length());
        File f = new File(fileName);
        if(!f.exists())
            return -1;
        return f.lastModified();
    }
    public void saveLocalFile(String fileName,byte[] data,long fileLastModified)
    {
        if(fileName == null || fileName.length() == 0)
            return;
        String f = "c:\\JavaHis\\Application Data" + fileName.substring(6,fileName.length());
        int index = f.lastIndexOf("\\");
        String dir = f.substring(0,index);
        File file = new File(dir);
        if(!file.exists())
            file.mkdirs();
        try{
            FileTool.setByte(f, data);
        }catch(Exception e)
        {
        }
        file = new File(f);
        file.setLastModified(fileLastModified);
    }
    /**
     * �����ļ�
     * @param fileName String �����ļ���
     * @param map Map
     * @return boolean
     */
    public boolean loadLocal(String fileName,Map map) {
        fileName = StringTool.replaceAll(fileName,"\\","/");
        File f = new File(fileName);
        if (!f.exists())
            return false;
        try {
            return load(new BufferedReader(new FileReader(f)),map);
        } catch (Exception e) {
        }
        return false;
    }
    /**
     * ��������
     * @param data String ���ݰ�
     * @param map Map
     * @return boolean
     */
    public boolean loadData(String data,Map map)
    {
        try {
            return load(new BufferedReader(new StringReader(data)),map);
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * �����ļ�
     * @param br BufferedReader
     * @param map Map
     * @return boolean
     */
    public boolean load(BufferedReader br,Map map) {
        try {
            String s = "";
            String olds = "";
            Map valueMap = null;
            while ((s = br.readLine()) != null) {
                s = s.trim();
                if (s.length() == 0)
                    continue;
                if (s.startsWith("#"))
                    continue;
                if (s.startsWith("//"))
                    continue;
                if (olds.length() > 0) {
                    s = olds + s;
                    olds = "";
                }
                if (s.endsWith("&")) {
                    olds = s.substring(0, s.length() - 1);
                    continue;
                }
                String group = s.toUpperCase();
                if (group.startsWith("<") && group.endsWith(">")) {
                    s = s.trim();
                    s = s.substring(1, s.length() - 1).trim();
                    int index = s.indexOf("=");
                    if (index < 1)
                        continue;
                    String name = s.substring(0, index).toUpperCase().trim();
                    String value = s.substring(index + 1, s.length()).trim();
                    if("EXTENDS".equals(name))
                    {
                        if(!reset(value,map))
                            return false;
                        continue;
                    }
                    if("TYPE".equals(name))
                    {
                        setType(value);
                        continue;
                    }
                    continue;
                }
                if (group.startsWith("[") && group.endsWith("]")) {
                    group = group.substring(1, group.length() - 1).trim();
                    valueMap = (Map)map.get(group);
                    if(valueMap == null)
                    {
                        valueMap = new HashMap();
                        map.put(group, valueMap);
                    }
                    continue;
                }
                int index = group.indexOf("=");
                if (index < 1)
                    continue;
                group = group.substring(0, index).trim();
                String value = s.substring(index + 1, s.length()).trim();
                if (valueMap == null) {
                    map.put(group, value);
                    continue;
                }
                valueMap.put(group, value);
            }
            br.close();
        } catch (Exception e) {
        }
        //���ó�ʼ���ɹ�
        setIsInit(true);
        return true;
    }

    /**
     * ��ȡ�ַ���Ϣ
     * @param group String
     * @param name String
     * @param defaultValue String
     * @return String
     */
    public String getString(String group, String name, String defaultValue) {
        Object obj = map.get(group.trim().toUpperCase());
        if (obj == null || !(obj instanceof Map)) {
            String value = (String) map.get(name.toUpperCase());
            if (value == null)
                return defaultValue;
            return value;
        }
        Map mapValue = (Map) obj;
        String s = (String) mapValue.get(name.toUpperCase());
        if (s == null)
            s = (String) map.get(name.toUpperCase());
        if (s == null)
            return defaultValue;
        return s;
    }

    /**
     * ��ȡ��ֵ
     * @param group String
     * @param name String
     * @param defaultValue String
     * @return String[]
     */
    public String[] getStringList(String group, String name,
                                  String defaultValue) {
        return parseLine(getString(group, name, defaultValue), ";");
    }

    /**
     * ��ȡ��ֵ
     * @param group String
     * @param name String
     * @return String[]
     */
    public String[] getStringList(String group, String name) {
        return getStringList(group, name, "");
    }

    /**
     * ��ȡ�ַ���
     * @param group String
     * @param name String
     * @return String
     */
    public String getString(String group, String name) {
        return getString(group, name, "");
    }
    /**
     * ��ȡ�ַ���
     * @param name String
     * @return String
     */
    public String getString(String name) {
        return getString("", name, "");
    }

    /**
     * ��ȡ������
     * @param group String
     * @param name String
     * @param defaultValue int
     * @return int
     */
    public int getInt(String group, String name, int defaultValue) {
        String s = getString(group, name, "" + defaultValue);
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * ��ȡ������
     * @param group String
     * @param name String
     * @return int
     */
    public int getInt(String group, String name) {
        return getInt(group, name, 0);
    }
    /**
     * �õ�tag Map
     * @param group String
     * @param tag String
     * @return Map
     */
    public Map getTagMap(String group, String tag) {
        if (!tag.endsWith("."))
            tag += ".";
        Map mapValue = new HashMap();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            Object obj = map.get(name);
            if (obj instanceof Map) {
                if (!name.equals(group))
                    continue;
                Map mapv = (Map) obj;
                Iterator iterator1 = mapv.keySet().iterator();
                while (iterator1.hasNext()) {
                    String cname = (String) iterator1.next();
                    if (cname.startsWith(tag)) {
                        mapValue.put(cname.substring(tag.length(), cname.length()),
                                     mapv.get(cname));
                    }
                }
            }
            if (obj instanceof String) {
                if (!name.startsWith(tag))
                    continue;
                if (mapValue.get(name) == null)
                    mapValue.put(name.substring(tag.length(), name.length()),
                                 obj);
            }
        }
        return mapValue;
    }
    /**
     * �õ�tag List
     * @param group String
     * @param tag String
     * @return String[]
     */
    public String[] getTagList(String group, String tag) {
        return getTagList(group,tag,null);
    }
    /**
     * �õ�tag List
     * @param group String
     * @param tag String
     * @param downList String ˳���ö��ŷָ�
     * @return String[]
     */
    public String[] getTagList(String group, String tag,String downList) {
        group = group.toUpperCase();
        tag = tag.toUpperCase();
        Map mapValue = getTagMap(group,tag);
        String s[] = new String[mapValue.size()];
        int index = 0;
        if(downList != null && downList.length() > 0)
        {
            String downLists[] = StringTool.parseLine(downList.toUpperCase(),",");
            for(int i = 0;i < downLists.length;i++)
            {
                String name = downLists[i].trim();
                if(name.length() == 0)
                    continue;
                Object value = mapValue.remove(name);
                if(value == null)
                    continue;
                s[index] = name + "=" + value;
                index ++;
            }
        }
        Iterator iterator = mapValue.keySet().iterator();
        while (iterator.hasNext()) {
            String name = (String) iterator.next();
            s[index] = name + "=" + mapValue.get(name);
            index++;
        }
        return s;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        Iterator iterator = map.keySet().iterator();
        while (iterator.hasNext()) {
            String group = (String) iterator.next();
            Object obj = map.get(group);
            if (obj instanceof Map) {
                sb.append("[" + group + "]");
                sb.append("\r\n");
                Map mapVale = (Map) obj;
                Iterator iterator1 = mapVale.keySet().iterator();
                while (iterator1.hasNext()) {
                    String name = (String) iterator1.next();
                    String value = (String) mapVale.get(name);
                    sb.append(name + "=" + value);
                    sb.append("\r\n");
                }
                sb.append("\r\n");
            }
            if (obj instanceof String) {
                sb.append(group + "=" + obj);
                sb.append("\r\n");
            }
        }
        return sb.toString();
    }

    /**
     * �ָ�����
     * @param s String
     * @param separator String
     * @return String[]
     */
    private static String[] parseLine(String s, String separator) {
        ArrayList list = new ArrayList();
        int index = s.indexOf(separator);
        while (index >= 0) {
            list.add(s.substring(0, index));
            s = s.substring(index + separator.length(), s.length());
            index = s.indexOf(separator);
        }
        if (s.length() > 0)
            list.add(s);
        return (String[]) list.toArray(new String[] {});
    }
    
	public Map getMap() {
		return map;
	}
	public void setMap(Map map) {
		this.map = map;
	}
    
    
}
