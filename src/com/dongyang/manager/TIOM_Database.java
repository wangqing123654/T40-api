package com.dongyang.manager;

import com.dongyang.jdo.TDataStore;
import com.dongyang.jdo.TJDODBTool;
import com.dongyang.util.TDebug;
import com.dongyang.data.TParm;
import com.dongyang.data.TSocket;
import com.dongyang.util.StringTool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
import com.dongyang.config.TConfig;
import com.dongyang.config.TRegistry;

/**
 *
 * <p>Title: 数据库对象</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2008.12.18
 * @version 1.0
 */
public class TIOM_Database extends TIOM_Expend{
    /**
     * 备份表修改记录
     * @param tablename String 表明
     * @return boolean true 成功 false 失败
     */
    public synchronized static boolean logTableAction(String tablename)
    {
        System.out.println("logTableAction()" + tablename);
        
        tablename = tablename.toUpperCase();
        TDataStore dataStore = new TDataStore();
        dataStore.setSQL("select * from " + tablename + "_LOG ORDER BY ID");
        if(dataStore.retrieve() < 0)
        {
            System.out.println("TIOM_Database.logTableAction() ERR0:" + dataStore.getErrText());
            return false;
        }
        //System.out.println("dataStore.rowCount()=" + dataStore.rowCount());
        if(dataStore.rowCount() <= 0)
            return true;
        byte[] data = dataStore.outData(dataStore.PRIMARY);
        if(data == null)
        {
            System.out.println("TIOM_Database.logTableAction() ERR1: data is null");
            return false;
        }
        TSocket socket = TIOM_FileServer.getSocket();
        if(socket == null)
        {
            System.out.println("TIOM_Database.logTableAction() ERR2: TSocket is null");
            return false;
        }
        //zhangp 秒2毫秒
//        String name = tablename + "/" + StringTool.getString(dataStore.getDBTime(),"yyyyMMddHHmmss") + ".log";
        String name = tablename + "/" + StringTool.getString(TJDODBTool.getInstance().getDBExactTime(),"yyyyMMddHHmmssSSS") + ".log";
        String path = TIOM_FileServer.getPath("DBTableModifyLog.Root");
        
        System.out.println("fileName=" + TIOM_FileServer.getRoot() + path + name);
        
        if(!TIOM_FileServer.writeFile(socket,TIOM_FileServer.getRoot() + path + name,data))
        {
            System.out.println("TIOM_Database.logTableAction() ERR3: writeFile");
            return false;
        }
        if(!dataStore.deleteRowAll())
        {
            System.out.println("TIOM_Database.logTableAction() ERR4: deleteRowAll");
            return false;
        }
        //dataStore.showDebug();
        if(!dataStore.update())
        {
            System.out.println("TIOM_Database.logTableAction() ERR5: " + dataStore.getErrText());
            System.out.println(StringTool.getString(dataStore.getUpdateSQL()));
            return false;
        }
        return true;
    }
    /**
     * 清除本地文件
     * @param tablename String
     * @param removeFile boolean
     * @return boolean
     */
    public static boolean removeLocalTable(String tablename,boolean removeFile)
    {
        if(removeFile)
        {
            String filename = TIOM_FileServer.getPath("DBTableLocalLog.Root");
            filename += tablename + ".log";
            TIOM_FileServer.deleteFile(filename);
        }
        getLocalTable(tablename,true);
        return true;
    }
    private static Map dataStoreList = new HashMap();
    /**
     * 得到本地Table数据
     * @param tablename String
     * @return TDataStore
     */
    public static TDataStore getLocalTable(String tablename)
    {
        return getLocalTable(tablename,false);
    }
    /**
     * 得到本地下载的列
     * @param tableName String
     * @return String
     */
    public static String getLoadColumns(String tableName)
    {
        return TConfig.getSystemValue(tableName);
    }
    /**
     * 得到本地Table数据
     * @param tablename String
     * @param isLoad boolean
     * @return TDataStore
     */
    public synchronized static TDataStore getLocalTable(String tablename,boolean isLoad)
    {
        if(tablename == null)
            return null;
        String version = TRegistry.get("HKEY_CURRENT_USER\\Software\\JavaHis\\Locale\\" + tablename + "_Version");
        if(version == null)
            version = "";
        String appVersion = getLoadColumns("Locale.Version");
        String p[] = StringTool.parseLine(tablename,":");
        tablename = p[0];
        String columns = "*";
        String id = "";
        if(p.length > 1)
            columns = p[1];
        if(p.length > 2)
            id = p[2];
        TDataStore dataStore = (TDataStore)dataStoreList.get(tablename);
        if(!isLoad && dataStore != null)
            return dataStore;
        if(dataStore == null)
            dataStore = new TDataStore();
        dataStoreList.put(tablename,dataStore);
        tablename = tablename.toUpperCase();
        String filename = TIOM_FileServer.getPath("DBTableLocalLog.Root");
        TIOM_FileServer.mkdir(filename);
        filename += tablename + id + ".log";
        if(!version.equals(appVersion) || !dataStore.loadFile(filename) || dataStore.getLogDateCount() > 10)
        {
            //System.out.println(tablename + " load db start");
            //com.dongyang.util.DebugUsingTime.add("start");
            String s = getLoadColumns("Locale." + tablename + ".Columns");
            if(s != null && s.length() > 0)
                columns = s;
            //System.out.println("columns:" + columns);
            dataStore.setSQL("select " + columns + " from " + tablename);
            com.dongyang.util.DebugUsingTime.add("select");
            dataStore.retrieve();
            com.dongyang.util.DebugUsingTime.add("retrieve");
            //zhangp 秒2毫秒
//            dataStore.setRetrieveTime(StringTool.getString(dataStore.getDBTime(),"yyyyMMddHHmmss"));
            dataStore.setRetrieveTime(StringTool.getString(TJDODBTool.getInstance().getDBExactTime(),"yyyyMMddHHmmssSSS"));
            if(!dataStore.saveFile(filename))
                return null;
            com.dongyang.util.DebugUsingTime.add("save");
            TRegistry.set("HKEY_CURRENT_USER\\Software\\JavaHis\\Locale\\" + tablename + "_Version",appVersion);
        }
        TSocket socket = TIOM_FileServer.getSocket();
        if(socket == null)
            return dataStore;
        String path = TIOM_FileServer.getRoot() + TIOM_FileServer.getPath("DBTableModifyLog.Root") + tablename + "/";
        if(path == null)
            return dataStore;
        String dirs[] = TIOM_FileServer.listFile(socket,path);
        if(dirs == null)
            return dataStore;
        String time = "";
        StringTool.orderStringArray(dirs);
        //zhangp 秒2毫秒
//        String logTime=StringTool.getString(dataStore.getDBTime(),"yyyyMMddHHmmss");
        String logTime=StringTool.getString(TJDODBTool.getInstance().getDBExactTime(),"yyyyMMddHHmmssSSS");
        StringBuilder sb = new StringBuilder();
        sb.append("系统当前时间 = "+logTime+"\r\n");
        sb.append("  本机SYS_FEE.log时间 = "+dataStore.getRetrieveTime()+"\r\n");
        for(int i = 0;i < dirs.length;i++)
        {
            if(StringTool.compareTo(dataStore.getRetrieveTime() + ".log",dirs[i]) >= 0)
                continue;
            sb.append("  下载的文件 = "+dirs[i]+"\r\n");
            byte data[] = TIOM_FileServer.readFile(socket,path + dirs[i]);
            if(data == null)
                continue;
            TParm logParm = new TParm();
            logParm.inData(data);
            sb.append("  下载的内容 = "+logParm+"\r\n");
            if(!dataStore.inDataChoose(data))
                continue;
            time = dirs[i].substring(0,dirs[i].length() - 4);
        }
        if(time.length() > 0)
        {
            dataStore.setRetrieveTime(time);
            dataStore.resetModify();
            dataStore.saveFile(filename);
        }
        //add by lx 2014/12/14 测试日志
        File logPath = new File("C:\\JavaHisSysFee\\logs\\");
		// 判断日志文件夹是否存在
		if (!logPath.exists()) {
			//System.out.println("创建日志文件夹...");
			logPath.mkdirs();
		}
        
        //输出本地表名
        String log=logTime+" 本地表名："+dataStore.getTableName()+"\r\n";
        //最后更新时间
        log+=logTime+" 本地最后更新时间："+dataStore.getRetrieveTime()+"\r\n";
        log+=logTime+" 本地更新文件名："+filename+"\r\n";
        writerLog(log);
        writerLog2(sb.toString());
        //
        return dataStore;
    }
    public static void main(String args[])
    {

        TDebug.initClient();
        TIOM_AppServer.resetAction();
        com.dongyang.util.DebugUsingTime.start();
        //TDataStore ds = getLocalTable("SYS_FEE:ORDER_CODE,ORDER_DESC,PY1,PY2:01");
        //System.out.println(ds.rowCount());
        logTableAction("SYS_FEE");
        com.dongyang.util.DebugUsingTime.add("end");
    }

    //zhangyong20110307
    /**
     * 清除本地文件
     * @param tablename String
     * @param removeFile boolean
     * @param sort String
     * @return boolean
     */
    public static boolean removeLocalTable(String tablename,boolean removeFile, String sort)
    {
        if(removeFile)
        {
            String filename = TIOM_FileServer.getPath("DBTableLocalLog.Root");
            filename += tablename + ".log";
            TIOM_FileServer.deleteFile(filename);
        }
        getLocalTable(tablename,true, sort);
        return true;
    }

    //zhangyong20110307
    /**
     * 得到本地Table数据
     * @param tablename String
     * @param isLoad boolean
     * @param sort String
     * @return TDataStore
     */
    public synchronized static TDataStore getLocalTable(String tablename,boolean isLoad, String sort)
    {
    	//System.out.println("===TIOM_Database getLocalTable===");
        if(tablename == null)
            return null;
        String version = TRegistry.get("HKEY_CURRENT_USER\\Software\\JavaHis\\Locale\\" + tablename + "_Version");
        if(version == null)
            version = "";
        String appVersion = getLoadColumns("Locale.Version");
        //System.out.println("===appVersion==="+appVersion);
        
        String p[] = StringTool.parseLine(tablename,":");
        tablename = p[0];
        String columns = "*";
        String id = "";
        if(p.length > 1)
            columns = p[1];
        if(p.length > 2)
            id = p[2];
        TDataStore dataStore = (TDataStore)dataStoreList.get(tablename);
        if(!isLoad && dataStore != null)
            return dataStore;
        if(dataStore == null)
            dataStore = new TDataStore();
        dataStoreList.put(tablename,dataStore);
        tablename = tablename.toUpperCase();
        String filename = TIOM_FileServer.getPath("DBTableLocalLog.Root");
        TIOM_FileServer.mkdir(filename);
        filename += tablename + id + ".log";
        if(!version.equals(appVersion) || !dataStore.loadFile(filename) || dataStore.getLogDateCount() > 10)
        {
            //System.out.println(tablename + " load db start");
            //com.dongyang.util.DebugUsingTime.add("start");
            String s = getLoadColumns("Locale." + tablename + ".Columns");
            if(s != null && s.length() > 0)
                columns = s;
            //System.out.println("columns:" + columns);
            dataStore.setSQL("select " + columns + " from " + tablename + sort);
            com.dongyang.util.DebugUsingTime.add("select");
            dataStore.retrieve();
            com.dongyang.util.DebugUsingTime.add("retrieve");
            dataStore.setRetrieveTime(StringTool.getString(dataStore.getDBTime(),"yyyyMMddHHmmss"));
            if(!dataStore.saveFile(filename))
                return null;
            com.dongyang.util.DebugUsingTime.add("save");
            TRegistry.set("HKEY_CURRENT_USER\\Software\\JavaHis\\Locale\\" + tablename + "_Version",appVersion);
        }
        TSocket socket = TIOM_FileServer.getSocket();
        if(socket == null)
            return dataStore;
        String path = TIOM_FileServer.getRoot() + TIOM_FileServer.getPath("DBTableModifyLog.Root") + tablename + "/";
        if(path == null)
            return dataStore;
        String dirs[] = TIOM_FileServer.listFile(socket,path);
        if(dirs == null)
            return dataStore;
        String time = "";
        StringTool.orderStringArray(dirs);
        for(int i = 0;i < dirs.length;i++)
        {
            if(StringTool.compareTo(dataStore.getRetrieveTime() + ".log",dirs[i]) >= 0)
                continue;
            byte data[] = TIOM_FileServer.readFile(socket,path + dirs[i]);
            if(data == null)
                continue;
            if(!dataStore.inDataChoose(data))
                continue;
            time = dirs[i].substring(0,dirs[i].length() - 4);
        }
        if(time.length() > 0)
        {
            dataStore.setRetrieveTime(time);
            dataStore.resetModify();
            dataStore.saveFile(filename);
        }
        return dataStore;
    }
    
    /**
     * lx测试用的日志文件
     * @param msg
     */
	public static void writerLog(String msg) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String name = "SysFee_LOG";
	    name +=format.format(new Date());
		File f = new File("C:\\JavaHisSysFee\\logs\\"+name+".log");
		BufferedWriter out = null;
		try {
			if (!f.exists()) {
				f.createNewFile();// 如果SPC.log不存在，则创建一个新文件
			}
			out = new BufferedWriter(new FileWriter(f, true));// 参数true表示将输出追加到文件内容的末尾而不覆盖原来的内容
			out.write(msg);
			out.newLine(); // 换行
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 测试用的日志文件-2
	 * @param msg
	 * @author zhangp
	 */
	public static void writerLog2(String msg) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String name = "sys_fee_download_log";
		name +=format.format(new Date());
		File f = new File("C:\\JavaHisSysFee\\logs\\"+name+".log");
		BufferedWriter out = null;
		try {
			if (!f.exists()) {
				f.createNewFile();// 如果SPC.log不存在，则创建一个新文件
			}
			out = new BufferedWriter(new FileWriter(f, true));// 参数true表示将输出追加到文件内容的末尾而不覆盖原来的内容
			out.write(msg);
			out.newLine(); // 换行
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
