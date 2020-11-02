package com.dongyang.tui.text;

import com.dongyang.tui.DText;
import com.dongyang.config.TConfig;
import java.io.File;
import com.dongyang.tui.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import com.dongyang.tui.io.DataInputStream;
import java.io.InputStream;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.dongyang.manager.TIOM_AppServer;
import com.dongyang.util.TypeTool;
import com.dongyang.manager.TIOM_FileServer;
import com.dongyang.data.TParm;
import com.dongyang.data.TSocket;


/**
 *
 * <p>Title: �ļ�������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @author whao 2013~
 * @version 1.0
 */
public class MFile {
    /**
     * ������
     */
    private PM pm;
    /**
     * ���ļ���λ��
     * -1 ��ȷ��
     * 0 ����
     * 1 AppServer
     * 2 FileServer Emrģ���ļ�
     * 3 FileServer Emr�����ļ�
     */
    private int openServerType;
    /**
     * Ŀ¼
     */
    private String path;
    /**
     * �ļ���
     */
    private String fileName;
    /**
     * ����
     */
    private String author = "";
    /**
     * ��˾
     */
    private String co = "";
    /**
     * ��������
     */
    private String createDate = "";
    /**
     * ��ע
     */
    private String remark = "";
    /**
     * �༭��
     */
    private boolean editLock;
    /**
     * �����û�
     */
    private String lockUser;
    /**
     * ��������
     */
    private String lockDate;
    /**
     * ��������
     */
    private String lockDept;
    /**
     * ����IP
     */
    private String lockIP;
    /**
     * ����޸���
     */
    private String lastEditUser;
    /**
     * ����޸�����
     */
    private String lastEditDate;
    /**
     * ����޸�IP
     */
    private String lastEditIP;
    /**
     * �Ƿ��޸�
     */
    private boolean isModify;

    /**
     * �����ļ�
     */
    private TConfig config;
    /**
     * ���������
     */
    private DataOutputStream dataOutputStream;
    /**
     * ����������
     */
    private DataInputStream dataInputStream;
    /**
     * ��ʱ�ļ���
     */
    private String tempFileName;
    /**
     * �汾��
     */
    private String version;
    /**
     * ��������
     */
    private MPanel panelManager;
    /**
     * TD������
     */
    private MTDSave TDSaveManager;
    /**
     * TD�ϲ���Ԫ�������
     */
    private MTDSave TDSaveUniteManager;
    /**
     * TR������
     */
    private MTRSave TRSaveManager;
    /**
     * Text������
     */
    private MTextSave textSaveManager;
    /**
     * Table������
     */
    private MTable tableManager;
    /**
     * ����
     */
    private String title;
    /**
     * Ӣ�ı���
     */
    private String enTitle;
    /**
     * ��������X
     */
    private int previewWindowX;
    /**
     * ��������Y
     */
    private int previewWindowY;
    /**
     * �������ڿ��
     */
    private int previewWindowWidth = 500;
    /**
     * �������ڸ߶�
     */
    private int previewWindowHeight = 500;
    /**
     * �������ھ���
     */
    private boolean previewWindowCenter = true;
    /**
     * ����
     */
    private Object parameter;
    /**
     * ���Լ���
     */
    private boolean loadDebug = false;
    /**
     * �Ի��򿪹�
     */
    private boolean messageBoxSwitch = true;

    /**
     * �����ϴ�ͼƬ
     */
    private String containPic;


    private SimpleDateFormat fmt = new SimpleDateFormat("HHmmss");

    //private String  reportFlg;


    /**
     * ������
     */
    public MFile() {
        setConfig(TConfig.getConfig("WEB-INF\\config\\system\\TConfig.x"));
        //����Ĭ�ϰ汾��
        setVersion(getDefaultVersion());
    }

    /**
     * ���ù�����
     * @param pm PM
     */
    public void setPM(PM pm) {
        this.pm = pm;
    }

    /**
     * �õ�������
     * @return PM
     */
    public PM getPM() {
        return pm;
    }

    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager() {
        return getPM().getPageManager();
    }

    /**
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
    }

    /**
     * �õ�Table������������
     * @return MCTable
     */
    public MCTable getCTableManager() {
        return getPM().getCTableManager();
    }

    /**
     * �õ���������
     * @return MStyle
     */
    public MStyle getStyleManager() {
        return getPM().getStyleManager();
    }

    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager() {
        return getPM().getFocusManager();
    }

    /**
     * �õ��﷨������
     * @return MSyntax
     */
    public MSyntax getSyntaxManager() {
        return getPM().getSyntaxManager();
    }

    /**
     * �õ��������
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager() {
        return getPM().getMacroroutineManager();
    }

    /**
     * �õ��ַ����ݹ�����
     * @return MString
     */
    public MString getStringManager() {
        return getPM().getStringManager();
    }

    /**
     * �õ�ͼƬ������
     * @return MImage
     */
    public MImage getImageManager() {
        return getPM().getImageManager();
    }

    /**
     * ������������
     * @param panelManager MPanel
     */
    public void setPanelManager(MPanel panelManager) {
        this.panelManager = panelManager;
    }

    /**
     * �õ���������
     * @return MPanel
     */
    public MPanel getPanelManager() {
        return panelManager;
    }

    /**
     * ����TD������
     * @param TDSaveManager MTDSave
     */
    public void setTDSaveManager(MTDSave TDSaveManager) {
        this.TDSaveManager = TDSaveManager;
    }

    /**
     * �õ�TD������
     * @return MTDSave
     */
    public MTDSave getTDSaveManager() {
        return TDSaveManager;
    }

    /**
     * ����TD�ϲ���Ԫ�������
     * @param TDSaveUniteManager MTDSave
     */
    public void setTDSaveUniteManager(MTDSave TDSaveUniteManager) {
        this.TDSaveUniteManager = TDSaveUniteManager;
    }

    /**
     * �õ�TD�ϲ���Ԫ�������
     * @return MTDSave
     */
    public MTDSave getTDSaveUniteManager() {
        return TDSaveUniteManager;
    }

    /**
     * ����TR������
     * @param TRSaveManager MTRSave
     */
    public void setTRSaveManager(MTRSave TRSaveManager) {
        this.TRSaveManager = TRSaveManager;
    }

    /**
     * �õ�TR������
     * @return MTRSave
     */
    public MTRSave getTRSaveManager() {
        return TRSaveManager;
    }

    /**
     * ����Text������
     * @param textSaveManager MTextSave
     */
    public void setTextSaveManager(MTextSave textSaveManager) {
        this.textSaveManager = textSaveManager;
    }

    /**
     * �õ�Text������
     * @return MTextSave
     */
    public MTextSave getTextSaveManager() {
        return textSaveManager;
    }

    /**
     * ����Table������
     * @param tableManager MTable
     */
    public void setTableManager(MTable tableManager) {
        this.tableManager = tableManager;
    }

    /**
     * �õ�Table������
     * @return MTable
     */
    public MTable getTableManager() {
        return tableManager;
    }

    /**
     * ����
     */
    public void update() {

        getFocusManager().update();
    }

    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI() {
        return getPM().getUI();
    }

    /**
     * �ô���UI
     * @return DText
     */
    public DText getSuperUI() {
        return getPM().getSuperUI();
    }

    /**
     * ����·��
     * @param path String
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * �õ�·��
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * ���ô��ļ���λ��
     * @param openServerType int
     * -1 ��ȷ��
     * 0 ����
     * 1 AppServer
     * 2 FileServer Emrģ���ļ�
     * 3 FileServer Emr�����ļ�
     */
    public void setOpenServerType(int openServerType) {
        this.openServerType = openServerType;
    }

    /**
     * �õ����ļ���λ��
     * @return int
     * -1 ��ȷ��
     * 0 ����
     * 1 AppServer
     * 2 FileServer Emrģ���ļ�
     * 3 FileServer Emr�����ļ�
     */
    public int getOpenServerType() {
        return openServerType;
    }

    /**
     * �����ļ���
     * @param fileName String
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * �õ��ļ���
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * �õ������������ļ���
     * @return String
     */
    public String getServerFileName() {
        return getServerFileName(getPath(), getFileName());
    }

    /**
     * �õ������������ļ���
     * @param path String
     * @param fileName String
     * @return String
     */
    public String getServerFileName(String path, String fileName) {
        if (path == null || path.length() == 0) {
            return "";
        }
        if (fileName == null || fileName.length() == 0) {
            return "";
        }
        if (!path.endsWith("\\")) {
            path += "\\";
        }
        fileName = path + fileName;
        if (!fileName.endsWith(".jhw")) {
            fileName += ".jhw";
        }
        return fileName;
    }

    /**
     * ��������
     * @param author String
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * �õ�����
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    /**
     * ���ù�˾
     * @param co String
     */
    public void setCo(String co) {
        this.co = co;
    }

    /**
     * �õ���˾
     * @return String
     */
    public String getCo() {
        return co;
    }

    /**
     * ���ô�������
     * @param createDate String
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * �õ���������
     * @return String
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * ���ñ�ע
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * �õ���ע
     * @return String
     */
    public String getRemark() {
        return remark;
    }

    /**
     * �����޸�
     * @param isModify boolean
     */
    public void setModify(boolean isModify) {
        this.isModify = isModify;
    }

    /**
     * �Ƿ��޸�
     * @return boolean
     */
    public boolean isModify() {
        return isModify;
    }

    /**
     * ���������ļ�
     * @param config TConfig
     */
    public void setConfig(TConfig config) {
        this.config = config;
    }

    /**
     * �õ������ļ�
     * @return TConfig
     */
    public TConfig getConfig() {
        return config;
    }

    /**
     * �õ�������Ϣ
     * @param name String
     * @return String
     */
    public String getConfigString(String name) {
        return getConfig().getString("", "JavaHisWord." + name);
    }

    /**
     * �õ�������ʱĿ¼
     * @return String
     */
    public String getLocalTempPath() {
        return getConfigString("localTempPath");
    }

    /**
     * �õ�������ʱ�ļ���
     * @param extend String ��չ��
     * @return String
     */
    public String getLocalTempFileName(String extend) {
        //�õ�������ʱĿ¼
        String localTempPath = getLocalTempPath();
        //����Ŀ¼
        checkPath(localTempPath);
        if (!localTempPath.endsWith("\\")) {
            localTempPath += "\\";
        }

        String fileName = localTempPath + hashCode() + "." + extend;
        int i = 0;
        while (existFile(fileName)) {
            fileName = localTempPath + hashCode() + i + "." + extend;
            i++;
        }
        return fileName;
    }

    /**
     * ������ʱ�ļ�
     * @param extend String
     * @return boolean
     */
    public boolean createLocalTempFile(String extend) {
        setTempFileName(getLocalTempFileName(extend));
        //����
        return openOutStream();
    }

    /**
     * ����ʱ�ļ�
     * @param fileName String
     * @return boolean
     */
    public boolean openLocalTempFile(String fileName) {
        setTempFileName(fileName);
        return openInStream();
    }

    /**
     * �ļ��Ƿ����
     * @param fileName String
     * @return boolean
     */
    public boolean existFile(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    /**
     * ����Ŀ¼
     * @param path String
     */
    public void checkPath(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * ������ʱ�ļ���
     * @param tempFileName String
     */
    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    /**
     * �õ���ʱ�ļ���
     * @return String
     */
    public String getTempFileName() {
        return tempFileName;
    }

    /**
     * �õ������
     * @return DataOutputStream
     */
    public DataOutputStream getOutStream() {
        return dataOutputStream;
    }

    /**
     * ���������
     * @return boolean
     */
    public boolean openOutStream() {
        if (dataOutputStream != null) {
            closeOutStream();
        }
        if (getTempFileName() == null) {
            return false;
        }
        try {
            OutputStream outputStream = new FileOutputStream(getTempFileName());
            dataOutputStream = new DataOutputStream(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * �ر������
     * @return boolean
     */
    public boolean closeOutStream() {
        if (dataOutputStream == null) {
            return true;
        }
        dataOutputStream.close();
        return true;
    }

    /**
     * �õ�������
     * @return DataInputStream
     */
    public DataInputStream getInStream() {
        return dataInputStream;
    }

    /**
     * ����������
     * @return boolean
     */
    public boolean openInStream() {
        if (dataInputStream != null) {
            closeInStream();
        }
        if (getTempFileName() == null) {
            return false;
        }
        try {
            InputStream inputStream = new FileInputStream(getTempFileName());
            dataInputStream = new DataInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * �ر�������
     * @return boolean
     */
    public boolean closeInStream() {
        if (dataInputStream == null) {
            return true;
        }
        dataInputStream.close();
        return true;
    }

    /**
     * �����Ի�����ʾ��Ϣ
     * @param message Object
     */
    public void messageBox(Object message) {
        getUI().messageBox(message);
    }

    /**
     * ���ð汾��
     * @param version String
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * �õ��汾��
     * @return String
     */
    public String getVersion() {
        return version;
    }

    /**
     * Ĭ�ϰ汾��
     * @return String
     */
    public String getDefaultVersion() {
        return "JHW1.0";
    }

    /**
     * д����
     * @param s DataOutputStream
     * @param c int
     * @throws IOException
     */
    public void writeObjectDebug(DataOutputStream s, int c) throws IOException {
        s.WO("<MFile>", c);
        s.WO(1, "Version", getVersion(), "", c + 1);
        s.WO(2, "Author", getAuthor(), "", c + 1);
        s.WO(3, "Co", getCo(), "", c + 1);
        s.WO(4, "CreateDate", getCreateDate(), "", c + 1);
        s.WO(5, "getRemark", getRemark(), "", c + 1);
        s.WO("</MFile>", c);
    }

    /**
     * д��������Ϣ
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeInfo(DataOutputStream s) throws IOException {
        s.writeBoolean(1, isEditLock(), false);
        s.writeString(2, getLockUser(), null);
        s.writeString(3, getLockDate(), null);
        s.writeString(4, getLockDept(), null);
        s.writeString(5, getLockIP(), null);
        s.writeString(6, getLastEditUser(), null);
        s.writeString(7, getLastEditDate(), null);
        s.writeString(8, getLastEditIP(), null);
        s.writeInt(9, getPM().getModifyNodeManager().getIndex(), -1);
        s.writeString(10, getEnTitle(), null);
        s.writeShort(11);
        getPM().getModifyNodeManager().writeObject(s);
        s.writeString(12, getContainPic(), null);
        s.writeShort( -1);
    }

    /**
     * ��ȡ������Ϣ
     * @param s DataInputStream
     * @throws IOException
     */
    public void readInfo(DataInputStream s) throws IOException {
        int id = s.readShort();
        while (id > 0) {
            switch (id) {
            case 1:
                setEditLock(s.readBoolean());
                break;
            case 2:
                setLockUser(s.readString());
                break;
            case 3:
                setLockDate(s.readString());
                break;
            case 4:
                setLockDept(s.readString());
                break;
            case 5:
                setLockIP(s.readString());
                break;
            case 6:
                setLastEditUser(s.readString());
                break;
            case 7:
                setLastEditDate(s.readString());
                break;
            case 8:
                setLastEditIP(s.readString());
                break;
            case 9:
                getPM().getModifyNodeManager().setIndex(s.readInt());
                break;
            case 10:
                setEnTitle(s.readString());
                break;
            case 11:
                getPM().getModifyNodeManager().readObject(s);
                break;
            case 12:
               setContainPic(s.readString());
               break;
            }
            id = s.readShort();
        }
    }

    /**
     * д����
     * @param s DataOutputStream
     * @throws IOException
     */
    public void writeObject(DataOutputStream s) throws IOException {
        s.writeString(1, getVersion(), "");
        s.writeString(2, getAuthor(), "");
        s.writeString(3, getCo(), "");
        s.writeString(4, getCreateDate(), "");
        s.writeString(5, getRemark(), "");
        s.writeShort(6);
        s.writeString(getTitle());
        s.writeInt(getPreviewWindowX());
        s.writeInt(getPreviewWindowY());
        s.writeInt(getPreviewWindowWidth());
        s.writeInt(getPreviewWindowHeight());
        s.writeBoolean(isPreviewWindowCenter());
        //д��������Ϣ
        s.writeShort(7);
        writeInfo(s);
        //�����﷨
        s.writeShort(10);
        getSyntaxManager().writeObject(getOutStream());
        //����ͼƬ
        s.writeShort(15);
        getImageManager().writeObject(getOutStream());
        //�����ģ��
        s.writeShort(20);
        getMacroroutineManager().writeObject(getOutStream());
        //����Table������������
        s.writeShort(30);
        getCTableManager().writeObject(getOutStream());
        //������ʽ
        s.writeShort(40);

        getStyleManager().writeObject(getOutStream());

        //����ҳ��Ϣ
        s.writeShort(100);
        getPageManager().writeObject(getOutStream());
        s.writeShort( -1);
    }

    /**
     * ������
     * @param s DataInputStream
     * @throws IOException
     */
    public void readObject(DataInputStream s) throws IOException {
        int id = s.readShort();
        while (id > 0) {
            switch (id) {
            case 1:
                setVersion(s.readString());
                break;
            case 2:
                setAuthor(s.readString());
                break;
            case 3:
                setCo(s.readString());
                break;
            case 4:
                setCreateDate(s.readString());
                break;
            case 5:
                setRemark(s.readString());
                break;
            case 6:
                setTitle(s.readString());
                setPreviewWindowX(s.readInt());
                setPreviewWindowY(s.readInt());
                setPreviewWindowWidth(s.readInt());
                setPreviewWindowHeight(s.readInt());
                setPreviewWindowCenter(s.readBoolean());
                break;
            case 7: //��ȡ������Ϣ
                readInfo(s);
                break;
            case 10:

                //��ȡ�﷨
                getSyntaxManager().readObject(getInStream());
                break;
            case 15:

                //��ȡͼƬ
                getImageManager().readObject(getInStream());
                break;
            case 20:

                //��ȡ��ģ��
                getMacroroutineManager().readObject(getInStream());
                break;
            case 30:

                //��ȡTable������������
                getCTableManager().readObject(getInStream());
                break;
            case 40:
                //��ȡ��ʽ
                  getStyleManager().readObject(getInStream());
                break;
            case 100:

                //��ȡҳ��Ϣ
                getPageManager().readObject(getInStream());
                break;
            }
            id = s.readShort();
        }
    }

    /**
     * �����������
     * @return boolean
     */
    private boolean saveDataDebug() {
        try {
            //��ʼ����������
            setPanelManager(new MPanel());
            //��ʼ��TD������
            setTDSaveManager(new MTDSave());
            //��ʼ��TD�ϲ���Ԫ�������
            setTDSaveUniteManager(new MTDSave());
            //��ʼ��TR������
            setTRSaveManager(new MTRSave());
            //��ʼ��Text������
            setTextSaveManager(new MTextSave());
            //��ʼ��Table������
            setTableManager(new MTable());
            //�����ļ���Ϣ
            writeObjectDebug(getOutStream(), 0);
            //�����﷨
            getSyntaxManager().writeObjectDebug(getOutStream(), 0);
            //�����ģ��
            getMacroroutineManager().writeObjectDebug(getOutStream(), 0);
            //����Table������������
            getCTableManager().writeObjectDebug(getOutStream(), 0);
            //����ҳ��Ϣ
            getPageManager().writeObjectDebug(getOutStream(), 0);
            //�����������
            setPanelManager(null);
            //���TD������
            setTDSaveManager(null);
            //���TD�ϲ���Ԫ�������
            setTDSaveUniteManager(null);
            //���TR������
            setTRSaveManager(null);
            //��ջ�Text������
            setTextSaveManager(null);
            //���Table������
            setTableManager(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ��������
     * @return boolean
     */
    private boolean saveData() {
        try {
            //��ʼ����������
            setPanelManager(new MPanel());
            //��ʼ��TD������
            setTDSaveManager(new MTDSave());
            //��ʼ��TR������
            setTRSaveManager(new MTRSave());
            //��ʼ��TD�ϲ���Ԫ�������
            setTDSaveUniteManager(new MTDSave());
            //��ʼ��Text������
            setTextSaveManager(new MTextSave());
            //��ʼ��Table������
            setTableManager(new MTable());
            //�ͷŶ��ڵ���ʽ
            getStyleManager().gcStyle();
            //�����ļ���Ϣ
            writeObject(getOutStream());
            //�����������
            setPanelManager(null);
            //���TD������
            setTDSaveManager(null);
            //��ջ�TD�ϲ���Ԫ�������
            setTDSaveUniteManager(null);
            //���TR������
            setTRSaveManager(null);
            //���Text������
            setTextSaveManager(null);
            //���Table������
            setTableManager(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ��ȡ����
     * @return boolean
     */
    private boolean readData() {
        try {
            //��ʼ����������
            setPanelManager(new MPanel());
            //��ʼ��TD������
            setTDSaveManager(new MTDSave());
            //��ʼ��TD�ϲ���Ԫ�������
            setTDSaveUniteManager(new MTDSave());
            //��ʼ��TR������
            setTRSaveManager(new MTRSave());
            //��ʼ��Text������
            setTextSaveManager(new MTextSave());
            //��ʼ��Table������
            setTableManager(new MTable());
            //���ͼƬ������
            getImageManager().removeAll();
            //��ȡ�ļ���Ϣ
            readObject(getInStream());
            //�����������
            setPanelManager(null);
            //���TD������
            setTDSaveManager(null);
            //���TD�ϲ���Ԫ�������
            setTDSaveUniteManager(null);
            //���TR������
            setTRSaveManager(null);
            //���Text������
            setTextSaveManager(null);
            //���Table������
            setTableManager(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * ���
     */
    public void clear() {
        getStyleManager().clear();
        setOpenServerType( -1);
        setPath(null);
        setFileName(null);
        setTitle("");
        setPreviewWindowX(0);
        setPreviewWindowY(0);
        setPreviewWindowWidth(500);
        setPreviewWindowHeight(500);
    }

    /**
     * ���ļ�
     */
    public void onNewFile() {
        clear();
        MPage pageManager = getPageManager();
        if (pageManager == null) {
            return;
        }
        //���ȫ������
        pageManager.removeAll();
        //��ҳ
        EPage page = pageManager.newPage();
        EPanel panel = page.newPanel();
        panel.newText();
        pageManager.reset();
        //���ý���
        MFocus focusManager = getFocusManager();
        if (focusManager != null) {
            focusManager.reset();
        }
        getSyntaxManager().clear();
    }

    /**
     * ����
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onSave() {
        if (!isOpen()) {
            return onSaveAsDialog();
        }
        return onSaveAs(getPath(), getFileName());
    }

    /**
     * ���Ϊ�Ի���
     * @return boolean
     */
    public boolean onSaveAsDialog() {
        Object[] obj = (Object[]) getUI().openDialog(
                "%ROOT%\\config\\database\\SaveAsDialog.x");
        if (obj == null) {
            return false;
        }
        String dir = TypeTool.getString(obj[0]);
        String filename = TypeTool.getString(obj[1]);
        //��������
        setAuthor(TypeTool.getString(obj[2]));
        //���ù�˾
        setCo(TypeTool.getString(obj[3]));
        //���ô���ʱ��
        setCreateDate(TypeTool.getString(obj[4]));
        //���ñ�ע
        setRemark(TypeTool.getString(obj[5]));
        boolean debug = TypeTool.getBoolean(obj[6]);
        //����
        return onSaveAs(dir, filename, getOpenServerType(), debug);
    }

    /**
     * ���Ϊ
     * @param path String
     * @param fileName String
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onSaveAs(String path, String fileName) {
        return onSaveAs(path, fileName, getOpenServerType());
    }

    /**
     * ���Ϊ
     * @param path String
     * @param fileName String
     * @param type int
     * @return boolean
     */
    public boolean onSaveAs(String path, String fileName, int type) {
        return onSaveAs(path, fileName, type, false);
    }

    /**
     * ���Ϊ
     * @param path String
     * @param fileName String
     * @param type int
     * @param debug boolean true ���� false ����
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onSaveAs(String path, String fileName, int type,
                            boolean debug) {
        String serverFileName = getServerFileName(path, fileName);
        if (serverFileName == null || serverFileName.length() == 0) {
            return false;
        }
        getFocusManager().onEditWord();
        update();
        //���Table��ʾ��ȫ������
        getCTableManager().clearShowData();
        //���ú�Ϊ�༭״̬
        getMacroroutineManager().edit();
        //����
        //if(debug)
        //    onSaveAsDebug();
        boolean result = false;
        if (type == 0) {
            result = openOutStream();
        } else {
            //������ʱĿ¼
            result = createLocalTempFile("jhw");
        }
        if (!result) {
            getFocusManager().onEditWord();
            update();
            if (getMessageBoxSwitch()) {
                messageBox("�޷����������ļ�!");
            }
            return false;
        }
        //��������
        boolean flg = saveData();
        //�ر���
        closeOutStream();
        if (!flg) {
            getFocusManager().onEditWord();
            update();
            if (getMessageBoxSwitch()) {
                messageBox("����ʧ��!");
            }
            return false;
        }
        switch (type) {
        case -1:
        case 1: //AppServer��

            //�������ϴ�
            if (!TIOM_AppServer.writeFile(serverFileName, getTempFileName())) {
                getFocusManager().onEditWord();
                update();
                if (getMessageBoxSwitch()) {
                    messageBox("�������ϴ�ʧ��!");
                }
                return false;
            }
            break;
        case 2: //�ļ��������ϴ�EMRģ���ļ�
            serverFileName = getEmrTempletDir() + serverFileName;

            //�������ϴ�
            if (!TIOM_FileServer.writeFile(TIOM_FileServer.getSocket(),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("�������ϴ�ʧ��!");
                }
                return false;
            }
            break;
        case 3: //�ļ��������ϴ�EMR�����ļ�
            serverFileName = getEmrDataDir(fileName) + serverFileName;

            //�������ϴ�
            if (!TIOM_FileServer.writeFile(getFileServerAddress(fileName),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("�������ϴ�ʧ��!");
                }
                return false;
            }
            break;
        }
        //���ô��ļ���λ��
        setOpenServerType(type);
        //����Ŀ¼
        setPath(path);
        //�����ļ���
        setFileName(fileName);

        getFocusManager().onEditWord();
        update();
        if (getMessageBoxSwitch()) {
            messageBox("����ɹ�!");
        }

        setModify(false);
        runListener("onSave");
        return true;
    }

    /**
     * ������
     * @param path String
     * @param fileName String
     * @param type int
     * @return boolean
     */
    public boolean onSaveAsReport(String path, String fileName, int type) {
        String serverFileName = getServerFileName(path, fileName);
        if (serverFileName == null || serverFileName.length() == 0) {
            return false;
        }
        boolean result = false;
        if (type == 0) {
            result = openOutStream();
        } else {
            //������ʱĿ¼
            result = createLocalTempFile("jhw");
        }
        if (!result) {
            if (getMessageBoxSwitch()) {
                messageBox("�޷����������ļ�!");
            }
            return false;
        }
        //��������
        boolean flg = saveData();
        //�ر���
        closeOutStream();
        if (!flg) {
            if (getMessageBoxSwitch()) {
                messageBox("����ʧ��!");
            }
            return false;
        }
        switch (type) {
        case -1:
        case 1: //AppServer��

            //�������ϴ�
            if (!TIOM_AppServer.writeFile(serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("�������ϴ�ʧ��!");
                }
                return false;
            }
            break;
        case 2: //�ļ��������ϴ�EMRģ���ļ�
            serverFileName = getEmrTempletDir() + serverFileName;

            //�������ϴ�
            if (!TIOM_FileServer.writeFile(TIOM_FileServer.getSocket(),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("�������ϴ�ʧ��!");
                }
                return false;
            }
            break;
        case 3: //�ļ��������ϴ�EMR�����ļ�
            serverFileName = getEmrDataDir() + serverFileName;

            //�������ϴ�
            if (!TIOM_FileServer.writeFile(TIOM_FileServer.getSocket(),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("�������ϴ�ʧ��!");
                }
                return false;
            }
            break;
        }
        if (getMessageBoxSwitch()) {
            messageBox("����ɹ�!");
        }
        return true;
    }


    /**
     * ִ���¼�
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListener(String action, Object ...parameters) {
        return runListenerArray(action, parameters);
    }

    /**
     * ִ���¼�
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListenerArray(String action, Object[] parameters) {
        if (getParameter() == null || !(getParameter() instanceof TParm)) {
            return null;
        }
        TParm parm = (TParm) getParameter();
        return parm.runListenerArray(action, parameters);
    }

    /**
     * ��������ļ�
     */
    public void onSaveAsDebug() {
        //������ʱĿ¼
        if (!createLocalTempFile("jhd")) {
            messageBox("�޷�����������ʱ�ļ�!");
            return;
        }
        //��������
        boolean flg = saveDataDebug();
        //�ر���
        closeOutStream();
        if (!flg) {
            messageBox("����ʧ��!");
            return;
        }
    }

    /**
     * ���ļ��Ի���
     * @return boolean
     */
    public boolean onOpenDialog() {
        Object[] obj = (Object[]) openDialog(
                "%ROOT%\\config\\database\\OpenDialog.x");
        if (obj == null) {
            return false;
        }
        String dir = TypeTool.getString(obj[0]);
        String filename = TypeTool.getString(obj[1]);
        boolean edit = !TypeTool.getBoolean(obj[2]);
        //��
        onOpen(dir, filename, 1, edit);
        return true;
    }

    /**
     * �򿪶Ի���
     * @param fileName String
     * @return Object
     */
    public Object openDialog(String fileName) {
        if (fileName == null || fileName.length() == 0) {
            return null;
        }
        DText text = getUI();
        if (text == null) {
            text = getSuperUI();
        }
        if (text == null) {
            return null;
        }
        return text.openDialog(fileName);
    }

    /**
     * ���ļ�
     * @param fileName String
     * @return boolean
     */
    public boolean onOpen(String fileName) {
        return onOpen(fileName, true);
    }

    /**
     * ���ļ�
     * @param fileName String
     * @param state boolean true ���� false �༭
     * @return boolean
     */
    public boolean onOpen(String fileName, boolean state) {
        if (fileName == null || fileName.length() == 0) {
            return false;
        }
        int index = fileName.lastIndexOf("\\");
        String dir = "";
        if (index == -1) {
            dir = "%ROOT%\\config\\prt";
        } else {
            dir = fileName.substring(0, index);
            fileName = fileName.substring(index + 1);
        }
        if (fileName.toUpperCase().endsWith(".JHW")) {
            fileName = fileName.substring(0, fileName.length() - 4);
        }
        return onOpen(dir, fileName, 1, state);
    }



    /**
     * ���ļ�
     * @param path String
     * @param fileName String
     * @param type int
     * 0 ����
     * 1 AppServer
     * 2 FileServer Emrģ���ļ�
     * 3 FileServer Emr�����ļ�
     * @param state boolean true ���� false �༭
     * @return boolean true �ɹ� false ʧ��
     */
    public boolean onOpen(String path, String fileName, int type, boolean state) {
        clear();
        String serverFileName = getServerFileName(path, fileName);
        //
        System.out.println("=======1111fileName11111========"+fileName);
        //System.out.println("=======1111serverFileName11111========"+serverFileName);
        //
        if (serverFileName == null || serverFileName.length() == 0) {
            return false;
        }
        setTempFileName(getLocalTempFileName("jhw"));
        switch (type) {
        case 0: //���ش�
            setTempFileName(serverFileName);
            break;
        case 1: //AppServer��

            //����������
            if (!TIOM_AppServer.readFileToLocal(serverFileName, getTempFileName())) {
                messageBox("����������ʧ��!");
                return false;
            }
            break;
        case 2: //�ļ��������ϴ�EMRģ���ļ�
            serverFileName = getEmrTempletDir() + serverFileName;

            //����������
            if (!TIOM_FileServer.readFileToLocal(TIOM_FileServer.getSocket(),
                                                 serverFileName,
                                                 getTempFileName())) {
                messageBox("����������ʧ��!");
                return false;
            }
            break;
        case 3: //�ļ��������ϴ�EMR�����ļ�
        	//add by lx 2016/03/30
            serverFileName = getEmrDataDir(fileName) + serverFileName;
            //����������
            if (!TIOM_FileServer.readFileToLocal(getFileServerAddress(fileName),
                                                 serverFileName,
                                                 getTempFileName())) {
                messageBox("����������ʧ��!");
                return false;
            }
            break;
        case 4:
        	//add by lx 2016/03/30
            serverFileName = getRefEmrDataDir(fileName) + serverFileName;
			if (!TIOM_FileServer.readFileToLocal(getFileServerAddress(fileName),
					serverFileName, getTempFileName())) {
				messageBox("����������ʧ��!");
				return false;
			}
            break;     
        }
        //���ô��ļ���λ��
        setOpenServerType(type);
        //����Ŀ¼
        setPath(path);
        //�����ļ���
        setFileName(fileName);
        //���ر����ļ�
        return openTempFile(state);
    }

    /**
     * ���ر����ļ�
     * @param state boolean true ���� false �༭
     * @return boolean true �ɹ� false ʧ��
     */
    private boolean openTempFile(boolean state) {
        //�����������
        getStringManager().removeAll();
        //���ȫ������
        getPageManager().removeAll();
        //���Table������
        getCTableManager().removeAll();
        //����������
        getMacroroutineManager().removeAll();
        //�����﷨
        getSyntaxManager().clear();

        openLocalTempFile(getTempFileName());
        readData();
        closeInStream();
        if (state) {
            getFocusManager().onPreviewWord();
        } else {
            getFocusManager().onEditWord();
        }
        //�������﷨
        getSyntaxManager().onOpen();
        //����
        update();
        return true;
    }

    /**
     * �õ���ӡ��ҵ����
     * @return String
     */
    public String getJobName() {
    	//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//String name = "LABEL_LOG";
	    //name +=format.format(new Date());
    	//HH MM SS
		String time1 =fmt.format(new Date());
		//System.out.println("------�ļ���----"+this.getFileName()+"_"+time1);
    	return this.getFileName()+"_"+time1;
        //return "JavaHisWord Print Job";
    }

    /**
     * �Ƿ��
     * @return boolean
     */
    public boolean isOpen() {
        return fileName != null && fileName.length() > 0;
    }

    /**
     * ���ñ���
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * �õ�����
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * ����Ӣ�ı���
     * @param enTitle String
     */
    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    /**
     * �õ�Ӣ�ı���
     * @return String
     */
    public String getEnTitle() {
        return enTitle;
    }

    /**
     * ������������X
     * @param previewWindowX int
     */
    public void setPreviewWindowX(int previewWindowX) {
        this.previewWindowX = previewWindowX;
    }

    /**
     * �õ���������X
     * @return int
     */
    public int getPreviewWindowX() {
        return previewWindowX;
    }

    /**
     * ������������Y
     * @param previewWindowY int
     */
    public void setPreviewWindowY(int previewWindowY) {
        this.previewWindowY = previewWindowY;
    }

    /**
     * �õ���������Y
     * @return int
     */
    public int getPreviewWindowY() {
        return previewWindowY;
    }

    /**
     * �����������ڿ��
     * @param previewWindowWidth int
     */
    public void setPreviewWindowWidth(int previewWindowWidth) {
        this.previewWindowWidth = previewWindowWidth;
    }

    /**
     * �õ��������ڿ��
     * @return int
     */
    public int getPreviewWindowWidth() {
        return previewWindowWidth;
    }

    /**
     * �����������ڸ߶�
     * @param previewWindowHeight int
     */
    public void setPreviewWindowHeight(int previewWindowHeight) {
        this.previewWindowHeight = previewWindowHeight;
    }

    /**
     * �õ��������ڸ߶�
     * @return int
     */
    public int getPreviewWindowHeight() {
        return previewWindowHeight;
    }

    /**
     * �����������ھ���
     * @param previewWindowCenter boolean
     */
    public void setPreviewWindowCenter(boolean previewWindowCenter) {
        this.previewWindowCenter = previewWindowCenter;
    }

    /**
     * �õ��������ھ���
     * @return boolean
     */
    public boolean isPreviewWindowCenter() {
        return previewWindowCenter;
    }

    /**
     * ���ò���
     * @param parameter Object
     */
    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    /**
     * �õ�����
     * @return Object
     */
    public Object getParameter() {
        if (parameter == null) {
            return "DEFAULT";
        }
        return parameter;
    }

    /**
     * �Ƿ���Լ���
     * @return boolean
     */
    public boolean isLoadDebug() {
        return loadDebug;
    }

    /**
     * �õ��ļ��������洢��Ŀ¼
     * @return String
     */
    public String getFileServerRoot() {
        return TIOM_FileServer.getRoot();
    }

    /**
     * �õ��ṹ������ģ���ŵĸ�Ŀ¼
     * @return String
     */
    public String getEmrTempletDir() {
        return getFileServerRoot() + TIOM_FileServer.getPath("EmrTemplet");
    }

    /**
     * �õ��ṹ���������ݴ�ŵĸ�Ŀ¼
     * @return String
     */
    public String getEmrDataDir() {
        return getFileServerRoot() + TIOM_FileServer.getPath("EmrData");
    }
        
    /**
     * 
     * @return
     */
    public String getRefEmrDataDir()
    {
      return getFileServerRoot() + TIOM_FileServer.getPath("RefEmrData");
    }

    /**
     * ���öԻ��򿪹�
     * @param messageBoxSwitch boolean
     */
    public void setMessageBoxSwitch(boolean messageBoxSwitch) {
        this.messageBoxSwitch = messageBoxSwitch;
    }

    /**
     * �õ��Ի��򿪹�
     * @return boolean
     */
    public boolean getMessageBoxSwitch() {
        return messageBoxSwitch;
    }

    /**
     * ���ñ༭��
     * @param editLock boolean
     */
    public void setEditLock(boolean editLock) {
        this.editLock = editLock;
    }

    /**
     * �Ƿ��б༭��
     * @return boolean
     */
    public boolean isEditLock() {
        return editLock;
    }

    /**
     * ���������û�
     * @param lockUser String
     */
    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    /**
     * �õ������û�
     * @return String
     */
    public String getLockUser() {
        return lockUser;
    }

    /**
     * ������������
     * @param lockDate String
     */
    public void setLockDate(String lockDate) {
        this.lockDate = lockDate;
    }

    /**
     * �õ���������
     * @return String
     */
    public String getLockDate() {
        return lockDate;
    }

    /**
     * ������������
     * @param lockDept String
     */
    public void setLockDept(String lockDept) {
        this.lockDept = lockDept;
    }

    /**
     * �õ���������
     * @return String
     */
    public String getLockDept() {
        return lockDept;
    }

    /**
     * �趨����IP
     * @param lockIP String
     */
    public void setLockIP(String lockIP) {
        this.lockIP = lockIP;
    }

    /**
     * �õ�����IP
     * @return String
     */
    public String getLockIP() {
        return lockIP;
    }

    /**
     * ��������޸���
     * @param lastEditUser String
     */
    public void setLastEditUser(String lastEditUser) {
        this.lastEditUser = lastEditUser;
    }

    /**
     * �õ�����޸���
     * @return String
     */
    public String getLastEditUser() {
        return lastEditUser;
    }

    /**
     * ��������޸�����
     * @param lastEditDate String
     */
    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    /**
     * �õ�����޸�����
     * @return String
     */
    public String getLastEditDate() {
        return lastEditDate;
    }

    /**
     * ��������޸�IP
     * @param lastEditIP String
     */
    public void setLastEditIP(String lastEditIP) {
        this.lastEditIP = lastEditIP;
    }

    /**
     * �õ�����޸�IP
     * @return String
     */
    public String getLastEditIP() {
        return lastEditIP;
    }

    /**
     * ��Ҫ�ϴ�ͼƬ
     * @param lastEditUser String
     */
    public void setContainPic(String containPic) {
        this.containPic = containPic;
    }

    /**
     * ��Ҫ�ϴ�ͼƬ
     * @return String
     */
    public String getContainPic() {

        return containPic;
    }
    
	/**
	 * ͨ���ļ�����ȷ���ļ��洢λ��
	 * 
	 * @param fileName
	 * @return
	 */
	public TSocket getFileServerAddress(String fileName) {
		//Ĭ���ļ�����
		TSocket tsocket = TIOM_FileServer.getSocket("Main");
		// 1.�ļ������ǿմ������
		if (fileName != null && !fileName.equals("")) {
			// 2.����-�����
			if (fileName.indexOf("_") != -1) {
				// 3.ȡ��һ��caseNo�µ����ǰ2λ�����
				String sYear = fileName.substring(0, 2);
				//System.out.println("---sYear��---" + sYear);
				TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
		        String ip = config.getString("","FileServer." + sYear + ".IP");
		        //System.out.println("====IP��======"+ip);
		        if (ip != null && !ip.equals("")) {
				// 4.��ָ���������ļ����������
		        	tsocket = TIOM_FileServer.getSocket(sYear);
		        }
				
			}
		}
		//   	
		return tsocket;
	}
	
    /**
     * 
     * @param fileName
     * @return
     */
    public String getEmrDataDir(String fileName){
    	//
    	String strEmrDataDir=getFileServerRoot() + TIOM_FileServer.getPath("EmrData");
    	// 1.�ļ������ǿմ������
		if (fileName != null && !fileName.equals("")) {
			// 2.����-�����
			if (fileName.indexOf("_") != -1) {
				// 3.ȡ��һ��caseNo�µ����ǰ2λ�����
				String sYear = fileName.substring(0, 2);
				//System.out.println("---sYear��---" + sYear);
				TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
		        String root = config.getString("","FileServer." + sYear + ".Root");
		        if (root != null && !root.equals("")) {
				// 4.��ָ���������ļ����������
		        	strEmrDataDir = TIOM_FileServer.getRoot(sYear)+ TIOM_FileServer.getPath("EmrData");
		        }
				
			}
		}
    	
    	return strEmrDataDir;
    }
    //
    /**
     * 
     * @return
     */
    public String getRefEmrDataDir(String fileName)
    {
    	String strEmrDataDir=getFileServerRoot() + TIOM_FileServer.getPath("RefEmrData");
    	// 1.�ļ������ǿմ������
		if (fileName != null && !fileName.equals("")) {
			// 2.����-�����
			if (fileName.indexOf("_") != -1) {
				// 3.ȡ��һ��caseNo�µ����ǰ2λ�����
				String sYear = fileName.substring(0, 2);
				//System.out.println("---sYear��---" + sYear);
				TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
		        String root = config.getString("","FileServer." + sYear + ".Root");
		        //System.out.println("====IP��======"+ip);
		        if (root != null && !root.equals("")) {
				// 4.��ָ���������ļ����������
		        	strEmrDataDir = TIOM_FileServer.getRoot(sYear)+ TIOM_FileServer.getPath("RefEmrData");
		        }				
			}
		}
    	
    	return strEmrDataDir;
    }


}
