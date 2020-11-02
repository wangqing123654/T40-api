package com.dongyang.action;

import com.dongyang.data.TParm;
import java.io.File;
import java.io.FileInputStream;
import com.dongyang.util.FileTool;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TFileAction extends TAction{
    /**
     * ���ļ�
     * @param parm TParm FILE_NAME
     * @return TParm BYTES
     */
    public TParm readFile(TParm parm) {
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        //System.out.println("fileName=" + fileName);
        File f = new File(fileName);
        if (!f.exists())
            return err(-1,fileName + " �ļ�������!");
        TParm result = new TParm();
        try {
            //���ļ�����
            FileInputStream stream = new FileInputStream(f);
            byte data[] = FileTool.getByte(stream);
            stream.close();
            result.setData("BYTES", data);
        } catch (Exception e) {
            return err(-1,e.getMessage());
        }
        return result;
    }
    /**
     * ��Jar�е��ļ��ļ�
     * @param parm TParm DIR,FILE_NAME
     * @return TParm BYTES
     */
    public TParm readJarFile(TParm parm) {
        String dir = parm.getValue("DIR");
        String fileName = parm.getValue("FILE_NAME");
        if (dir.trim().length() == 0)
            return err(-1,"DIR is null","DIR");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        dir = addRoot(dir,parm.getValue("SYSTEM","REALPATH"));
        File f = new File(dir);
        if (!f.exists())
            return err(-1,dir + " �ļ�������!");
        TParm result = new TParm();
        byte data[] = FileTool.getJarByte(dir,fileName);
        result.setData("BYTES", data);
        return result;
    }
    /**
     * ���ļ�ͷ
     * @param parm TParm FILE_NAME
     * @return TParm DATA
     */
    public TParm readFileHead(TParm parm) {
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        File f = new File(fileName);
        if (!f.exists())
            return err(-1,fileName + " �ļ�������!");
        TParm result = new TParm();
        try {
            BufferedReader dr = new BufferedReader(new InputStreamReader(new
                    FileInputStream(f)));
            String data = dr.readLine();
            dr.close();
            result.setData("DATA", data);
        } catch (Exception e) {
            return err(-1,e.getMessage());
        }
        return result;
    }
    /**
     * д�ļ�
     * @param parm TParm FILE_NAME,BYTES
     * @return TParm
     */
    public TParm writeFile(TParm parm) {
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        if (parm.getData("BYTES") == null || !(parm.getData("BYTES") instanceof byte[]))
            return err(-1,"BYTES ��������!","BYTES");
        byte data[] = (byte[]) parm.getData("BYTES");
        String dir = FileTool.getDir(fileName);
        if (dir.length() == 0)
            return err(-1,"FILE_NAME " + fileName + "Ŀ¼����!","FILE_NAME");
        //Ŀ¼���
        File f = new File(dir);
        if (!f.exists())
            f.mkdirs();
        f = new File(fileName);
        TParm result = new TParm();
        try {
            //���ļ�����
            FileOutputStream stream = new FileOutputStream(fileName);
            stream.write(data);
            stream.close();
        } catch (Exception e) {
            return err(-1,e.getMessage());
        }
        return result;
    }
    /**
     * �б��ļ�
     * @param parm TParm FILE_NAME,LIST_TYPE,FILE_FILTER
     * @return TParm FILE_LIST
     */
    public TParm listFile(TParm parm)
    {
        String fileName = parm.getValue("FILE_NAME");
        String listType = parm.getValue("LIST_TYPE");
        String fileFilter = parm.getValue("FILE_FILTER").toUpperCase();
        if (fileName == null || fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        if(!fileName.endsWith("/"))
            fileName += "/";
        File f = new File(fileName);
        if (!f.exists())
          return err(-1,fileName + " �ļ�������!");
        File files[] = f.listFiles();
        ArrayList list = new ArrayList();
        for(int i = 0;i < files.length;i++)
        {
            String cFileName = files[i].getName();
            if (listType.length() == 0)
            {
                list.add(cFileName);
                continue;
            }
            if(listType.equalsIgnoreCase("F"))
            {
                if(!files[i].isFile())
                    continue;
                if(fileFilter.length() == 0)
                    list.add(cFileName);
                else if(cFileName.toUpperCase().endsWith(fileFilter))
                    list.add(cFileName);
                continue;
            }
            if(listType.equalsIgnoreCase("D") && files[i].isDirectory())
                list.add(cFileName);
        }
        TParm result = new TParm();
        result.setData("FILE_LIST",(String[])list.toArray(new String[]{}));
        return result;
    }
    /**
     * ɾ���ļ�
     * @param parm TParm FILE_NAME
     * @return TParm
     */
    public TParm deleteFile(TParm parm) {
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        File f = new File(fileName);
        if (!f.exists())
            return err(-1,fileName + " �ļ�������!");
        if(!f.delete())
            return err(-1,fileName + " ɾ���ļ�ʧ��!");
        return new TParm();
    }
    /**
     * ����Ŀ¼
     * @param parm TParm FILE_NAME
     * @return TParm
     */
    public TParm mkdir(TParm parm) {
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        //Ŀ¼���
        File f = new File(fileName);
        if (!f.exists())
            f.mkdirs();
        return new TParm();
    }
    /**
     * ��ȡ�ļ���Ϣ
     * @param parm TParm FILE_NAME
     * @return TParm
     */
    public TParm fileInf(TParm parm) {
        String fileName = parm.getValue("FILE_NAME");
        if (fileName.trim().length() == 0)
            return err(-1,"FILE_NAME is null","FILE_NAME");
        fileName = addRoot(fileName,parm.getValue("SYSTEM","REALPATH"));
        //Ŀ¼���
        File f = new File(fileName);
        if (!f.exists())
            return err(-1,fileName + " �ļ�������!");
        TParm result = new TParm();
        result.setData("CAN_READ",f.canRead());
        result.setData("CAN_WRITE",f.canWrite());
        result.setData("GET_ABSOLUTE_PATH",f.getAbsolutePath());
        result.setData("GET_PARENT",f.getParent());
        result.setData("GET_PATH",f.getPath());
        result.setData("IS_ABSOLUTE",f.isAbsolute());
        result.setData("IS_DIRECTORY",f.isDirectory());
        result.setData("IS_FILE",f.isFile());
        result.setData("IS_HIDDEN",f.isHidden());
        result.setData("LAST_MODIFIED",f.lastModified());
        result.setData("LAST_LENGTH",f.length());
        return result;
    }
    private String addRoot(String name,String root)
    {
        name.replace('\\','/');
        if(root.endsWith("/"))
            root = root.substring(0, root.length() - 1);
        if (name.toUpperCase().startsWith("%ROOT%"))
            name = root + name.substring(6, name.length());
        return name;
    }
}
