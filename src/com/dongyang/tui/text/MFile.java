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
 * <p>Title: 文件管理器</p>
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
     * 管理器
     */
    private PM pm;
    /**
     * 打开文件的位置
     * -1 不确定
     * 0 本地
     * 1 AppServer
     * 2 FileServer Emr模板文件
     * 3 FileServer Emr数据文件
     */
    private int openServerType;
    /**
     * 目录
     */
    private String path;
    /**
     * 文件名
     */
    private String fileName;
    /**
     * 作者
     */
    private String author = "";
    /**
     * 公司
     */
    private String co = "";
    /**
     * 创建日期
     */
    private String createDate = "";
    /**
     * 备注
     */
    private String remark = "";
    /**
     * 编辑锁
     */
    private boolean editLock;
    /**
     * 锁定用户
     */
    private String lockUser;
    /**
     * 锁定日期
     */
    private String lockDate;
    /**
     * 锁定部门
     */
    private String lockDept;
    /**
     * 锁定IP
     */
    private String lockIP;
    /**
     * 最后修改人
     */
    private String lastEditUser;
    /**
     * 最后修改日期
     */
    private String lastEditDate;
    /**
     * 最后修改IP
     */
    private String lastEditIP;
    /**
     * 是否修改
     */
    private boolean isModify;

    /**
     * 配置文件
     */
    private TConfig config;
    /**
     * 数据输出流
     */
    private DataOutputStream dataOutputStream;
    /**
     * 数据输入流
     */
    private DataInputStream dataInputStream;
    /**
     * 临时文件名
     */
    private String tempFileName;
    /**
     * 版本号
     */
    private String version;
    /**
     * 面板控制类
     */
    private MPanel panelManager;
    /**
     * TD控制类
     */
    private MTDSave TDSaveManager;
    /**
     * TD合并单元格控制类
     */
    private MTDSave TDSaveUniteManager;
    /**
     * TR控制类
     */
    private MTRSave TRSaveManager;
    /**
     * Text控制类
     */
    private MTextSave textSaveManager;
    /**
     * Table控制器
     */
    private MTable tableManager;
    /**
     * 标题
     */
    private String title;
    /**
     * 英文标题
     */
    private String enTitle;
    /**
     * 阅览窗口X
     */
    private int previewWindowX;
    /**
     * 阅览窗口Y
     */
    private int previewWindowY;
    /**
     * 阅览窗口宽度
     */
    private int previewWindowWidth = 500;
    /**
     * 阅览窗口高度
     */
    private int previewWindowHeight = 500;
    /**
     * 阅览窗口居中
     */
    private boolean previewWindowCenter = true;
    /**
     * 参数
     */
    private Object parameter;
    /**
     * 调试加载
     */
    private boolean loadDebug = false;
    /**
     * 对话框开关
     */
    private boolean messageBoxSwitch = true;

    /**
     * 包含上传图片
     */
    private String containPic;


    private SimpleDateFormat fmt = new SimpleDateFormat("HHmmss");

    //private String  reportFlg;


    /**
     * 构造器
     */
    public MFile() {
        setConfig(TConfig.getConfig("WEB-INF\\config\\system\\TConfig.x"));
        //设置默认版本号
        setVersion(getDefaultVersion());
    }

    /**
     * 设置管理器
     * @param pm PM
     */
    public void setPM(PM pm) {
        this.pm = pm;
    }

    /**
     * 得到管理器
     * @return PM
     */
    public PM getPM() {
        return pm;
    }

    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager() {
        return getPM().getPageManager();
    }

    /**
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager() {
        return getPM().getViewManager();
    }

    /**
     * 得到Table控制器管理器
     * @return MCTable
     */
    public MCTable getCTableManager() {
        return getPM().getCTableManager();
    }

    /**
     * 得到风格管理器
     * @return MStyle
     */
    public MStyle getStyleManager() {
        return getPM().getStyleManager();
    }

    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager() {
        return getPM().getFocusManager();
    }

    /**
     * 得到语法管理器
     * @return MSyntax
     */
    public MSyntax getSyntaxManager() {
        return getPM().getSyntaxManager();
    }

    /**
     * 得到宏控制器
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager() {
        return getPM().getMacroroutineManager();
    }

    /**
     * 得到字符数据管理器
     * @return MString
     */
    public MString getStringManager() {
        return getPM().getStringManager();
    }

    /**
     * 得到图片管理器
     * @return MImage
     */
    public MImage getImageManager() {
        return getPM().getImageManager();
    }

    /**
     * 设置面板控制类
     * @param panelManager MPanel
     */
    public void setPanelManager(MPanel panelManager) {
        this.panelManager = panelManager;
    }

    /**
     * 得到面板控制类
     * @return MPanel
     */
    public MPanel getPanelManager() {
        return panelManager;
    }

    /**
     * 设置TD控制类
     * @param TDSaveManager MTDSave
     */
    public void setTDSaveManager(MTDSave TDSaveManager) {
        this.TDSaveManager = TDSaveManager;
    }

    /**
     * 得到TD控制类
     * @return MTDSave
     */
    public MTDSave getTDSaveManager() {
        return TDSaveManager;
    }

    /**
     * 设置TD合并单元格控制类
     * @param TDSaveUniteManager MTDSave
     */
    public void setTDSaveUniteManager(MTDSave TDSaveUniteManager) {
        this.TDSaveUniteManager = TDSaveUniteManager;
    }

    /**
     * 得到TD合并单元格控制类
     * @return MTDSave
     */
    public MTDSave getTDSaveUniteManager() {
        return TDSaveUniteManager;
    }

    /**
     * 设置TR控制类
     * @param TRSaveManager MTRSave
     */
    public void setTRSaveManager(MTRSave TRSaveManager) {
        this.TRSaveManager = TRSaveManager;
    }

    /**
     * 得到TR控制类
     * @return MTRSave
     */
    public MTRSave getTRSaveManager() {
        return TRSaveManager;
    }

    /**
     * 设置Text控制类
     * @param textSaveManager MTextSave
     */
    public void setTextSaveManager(MTextSave textSaveManager) {
        this.textSaveManager = textSaveManager;
    }

    /**
     * 得到Text控制类
     * @return MTextSave
     */
    public MTextSave getTextSaveManager() {
        return textSaveManager;
    }

    /**
     * 设置Table控制类
     * @param tableManager MTable
     */
    public void setTableManager(MTable tableManager) {
        this.tableManager = tableManager;
    }

    /**
     * 得到Table控制类
     * @return MTable
     */
    public MTable getTableManager() {
        return tableManager;
    }

    /**
     * 更新
     */
    public void update() {

        getFocusManager().update();
    }

    /**
     * 得到UI
     * @return DText
     */
    public DText getUI() {
        return getPM().getUI();
    }

    /**
     * 得打父类UI
     * @return DText
     */
    public DText getSuperUI() {
        return getPM().getSuperUI();
    }

    /**
     * 设置路径
     * @param path String
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 得到路径
     * @return String
     */
    public String getPath() {
        return path;
    }

    /**
     * 设置打开文件的位置
     * @param openServerType int
     * -1 不确定
     * 0 本地
     * 1 AppServer
     * 2 FileServer Emr模板文件
     * 3 FileServer Emr数据文件
     */
    public void setOpenServerType(int openServerType) {
        this.openServerType = openServerType;
    }

    /**
     * 得到打开文件的位置
     * @return int
     * -1 不确定
     * 0 本地
     * 1 AppServer
     * 2 FileServer Emr模板文件
     * 3 FileServer Emr数据文件
     */
    public int getOpenServerType() {
        return openServerType;
    }

    /**
     * 设置文件名
     * @param fileName String
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 得到文件名
     * @return String
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 得到服务器保存文件名
     * @return String
     */
    public String getServerFileName() {
        return getServerFileName(getPath(), getFileName());
    }

    /**
     * 得到服务器保存文件名
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
     * 设置作者
     * @param author String
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 得到作者
     * @return String
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置公司
     * @param co String
     */
    public void setCo(String co) {
        this.co = co;
    }

    /**
     * 得到公司
     * @return String
     */
    public String getCo() {
        return co;
    }

    /**
     * 设置创建日期
     * @param createDate String
     */
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    /**
     * 得到创建日期
     * @return String
     */
    public String getCreateDate() {
        return createDate;
    }

    /**
     * 设置备注
     * @param remark String
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 得到备注
     * @return String
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置修改
     * @param isModify boolean
     */
    public void setModify(boolean isModify) {
        this.isModify = isModify;
    }

    /**
     * 是否修改
     * @return boolean
     */
    public boolean isModify() {
        return isModify;
    }

    /**
     * 设置配置文件
     * @param config TConfig
     */
    public void setConfig(TConfig config) {
        this.config = config;
    }

    /**
     * 得到配置文件
     * @return TConfig
     */
    public TConfig getConfig() {
        return config;
    }

    /**
     * 得到配置信息
     * @param name String
     * @return String
     */
    public String getConfigString(String name) {
        return getConfig().getString("", "JavaHisWord." + name);
    }

    /**
     * 得到本地临时目录
     * @return String
     */
    public String getLocalTempPath() {
        return getConfigString("localTempPath");
    }

    /**
     * 得到本地临时文件名
     * @param extend String 扩展名
     * @return String
     */
    public String getLocalTempFileName(String extend) {
        //得到本地临时目录
        String localTempPath = getLocalTempPath();
        //测试目录
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
     * 创建临时文件
     * @param extend String
     * @return boolean
     */
    public boolean createLocalTempFile(String extend) {
        setTempFileName(getLocalTempFileName(extend));
        //打开流
        return openOutStream();
    }

    /**
     * 打开临时文件
     * @param fileName String
     * @return boolean
     */
    public boolean openLocalTempFile(String fileName) {
        setTempFileName(fileName);
        return openInStream();
    }

    /**
     * 文件是否存在
     * @param fileName String
     * @return boolean
     */
    public boolean existFile(String fileName) {
        File f = new File(fileName);
        return f.exists();
    }

    /**
     * 测试目录
     * @param path String
     */
    public void checkPath(String path) {
        File f = new File(path);
        if (!f.exists()) {
            f.mkdirs();
        }
    }

    /**
     * 设置临时文件名
     * @param tempFileName String
     */
    public void setTempFileName(String tempFileName) {
        this.tempFileName = tempFileName;
    }

    /**
     * 得到临时文件名
     * @return String
     */
    public String getTempFileName() {
        return tempFileName;
    }

    /**
     * 得到输出流
     * @return DataOutputStream
     */
    public DataOutputStream getOutStream() {
        return dataOutputStream;
    }

    /**
     * 打来输出流
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
     * 关闭输出流
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
     * 得到输入流
     * @return DataInputStream
     */
    public DataInputStream getInStream() {
        return dataInputStream;
    }

    /**
     * 打来输入流
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
     * 关闭输入流
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
     * 弹出对话框提示消息
     * @param message Object
     */
    public void messageBox(Object message) {
        getUI().messageBox(message);
    }

    /**
     * 设置版本号
     * @param version String
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 得到版本号
     * @return String
     */
    public String getVersion() {
        return version;
    }

    /**
     * 默认版本号
     * @return String
     */
    public String getDefaultVersion() {
        return "JHW1.0";
    }

    /**
     * 写对象
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
     * 写入其他信息
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
     * 读取其他信息
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
     * 写对象
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
        //写入其他信息
        s.writeShort(7);
        writeInfo(s);
        //保存语法
        s.writeShort(10);
        getSyntaxManager().writeObject(getOutStream());
        //保存图片
        s.writeShort(15);
        getImageManager().writeObject(getOutStream());
        //保存宏模型
        s.writeShort(20);
        getMacroroutineManager().writeObject(getOutStream());
        //保存Table控制器管理器
        s.writeShort(30);
        getCTableManager().writeObject(getOutStream());
        //保存样式
        s.writeShort(40);

        getStyleManager().writeObject(getOutStream());

        //保存页信息
        s.writeShort(100);
        getPageManager().writeObject(getOutStream());
        s.writeShort( -1);
    }

    /**
     * 读对象
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
            case 7: //读取其他信息
                readInfo(s);
                break;
            case 10:

                //读取语法
                getSyntaxManager().readObject(getInStream());
                break;
            case 15:

                //读取图片
                getImageManager().readObject(getInStream());
                break;
            case 20:

                //读取宏模型
                getMacroroutineManager().readObject(getInStream());
                break;
            case 30:

                //读取Table控制器管理器
                getCTableManager().readObject(getInStream());
                break;
            case 40:
                //读取样式
                  getStyleManager().readObject(getInStream());
                break;
            case 100:

                //读取页信息
                getPageManager().readObject(getInStream());
                break;
            }
            id = s.readShort();
        }
    }

    /**
     * 保存调试数据
     * @return boolean
     */
    private boolean saveDataDebug() {
        try {
            //初始化面板控制类
            setPanelManager(new MPanel());
            //初始化TD控制类
            setTDSaveManager(new MTDSave());
            //初始化TD合并单元格控制类
            setTDSaveUniteManager(new MTDSave());
            //初始化TR控制类
            setTRSaveManager(new MTRSave());
            //初始化Text控制类
            setTextSaveManager(new MTextSave());
            //初始化Table控制器
            setTableManager(new MTable());
            //保存文件信息
            writeObjectDebug(getOutStream(), 0);
            //保存语法
            getSyntaxManager().writeObjectDebug(getOutStream(), 0);
            //保存宏模型
            getMacroroutineManager().writeObjectDebug(getOutStream(), 0);
            //保存Table控制器管理器
            getCTableManager().writeObjectDebug(getOutStream(), 0);
            //保存页信息
            getPageManager().writeObjectDebug(getOutStream(), 0);
            //清空面板控制类
            setPanelManager(null);
            //清空TD控制类
            setTDSaveManager(null);
            //清空TD合并单元格控制类
            setTDSaveUniteManager(null);
            //清空TR控制类
            setTRSaveManager(null);
            //清空化Text控制类
            setTextSaveManager(null);
            //清空Table控制类
            setTableManager(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 保存数据
     * @return boolean
     */
    private boolean saveData() {
        try {
            //初始化面板控制类
            setPanelManager(new MPanel());
            //初始化TD控制类
            setTDSaveManager(new MTDSave());
            //初始化TR控制类
            setTRSaveManager(new MTRSave());
            //初始化TD合并单元格控制类
            setTDSaveUniteManager(new MTDSave());
            //初始化Text控制类
            setTextSaveManager(new MTextSave());
            //初始化Table控制器
            setTableManager(new MTable());
            //释放对于得样式
            getStyleManager().gcStyle();
            //保存文件信息
            writeObject(getOutStream());
            //清空面板控制类
            setPanelManager(null);
            //清空TD控制类
            setTDSaveManager(null);
            //清空化TD合并单元格控制类
            setTDSaveUniteManager(null);
            //清空TR控制类
            setTRSaveManager(null);
            //清空Text控制类
            setTextSaveManager(null);
            //清空Table控制类
            setTableManager(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 读取对象
     * @return boolean
     */
    private boolean readData() {
        try {
            //初始化面板控制类
            setPanelManager(new MPanel());
            //初始化TD控制类
            setTDSaveManager(new MTDSave());
            //初始化TD合并单元格控制类
            setTDSaveUniteManager(new MTDSave());
            //初始化TR控制类
            setTRSaveManager(new MTRSave());
            //初始化Text控制类
            setTextSaveManager(new MTextSave());
            //初始化Table控制器
            setTableManager(new MTable());
            //清空图片管理器
            getImageManager().removeAll();
            //读取文件信息
            readObject(getInStream());
            //清空面板控制类
            setPanelManager(null);
            //清空TD控制类
            setTDSaveManager(null);
            //清空TD合并单元格控制类
            setTDSaveUniteManager(null);
            //清空TR控制类
            setTRSaveManager(null);
            //清空Text控制类
            setTextSaveManager(null);
            //清空Table控制类
            setTableManager(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 清空
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
     * 新文件
     */
    public void onNewFile() {
        clear();
        MPage pageManager = getPageManager();
        if (pageManager == null) {
            return;
        }
        //清除全部内容
        pageManager.removeAll();
        //新页
        EPage page = pageManager.newPage();
        EPanel panel = page.newPanel();
        panel.newText();
        pageManager.reset();
        //设置焦点
        MFocus focusManager = getFocusManager();
        if (focusManager != null) {
            focusManager.reset();
        }
        getSyntaxManager().clear();
    }

    /**
     * 保存
     * @return boolean true 成功 false 失败
     */
    public boolean onSave() {
        if (!isOpen()) {
            return onSaveAsDialog();
        }
        return onSaveAs(getPath(), getFileName());
    }

    /**
     * 另存为对话框
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
        //设置作者
        setAuthor(TypeTool.getString(obj[2]));
        //设置公司
        setCo(TypeTool.getString(obj[3]));
        //设置创建时间
        setCreateDate(TypeTool.getString(obj[4]));
        //设置备注
        setRemark(TypeTool.getString(obj[5]));
        boolean debug = TypeTool.getBoolean(obj[6]);
        //保存
        return onSaveAs(dir, filename, getOpenServerType(), debug);
    }

    /**
     * 另存为
     * @param path String
     * @param fileName String
     * @return boolean true 成功 false 失败
     */
    public boolean onSaveAs(String path, String fileName) {
        return onSaveAs(path, fileName, getOpenServerType());
    }

    /**
     * 另存为
     * @param path String
     * @param fileName String
     * @param type int
     * @return boolean
     */
    public boolean onSaveAs(String path, String fileName, int type) {
        return onSaveAs(path, fileName, type, false);
    }

    /**
     * 另存为
     * @param path String
     * @param fileName String
     * @param type int
     * @param debug boolean true 调试 false 正常
     * @return boolean true 成功 false 失败
     */
    public boolean onSaveAs(String path, String fileName, int type,
                            boolean debug) {
        String serverFileName = getServerFileName(path, fileName);
        if (serverFileName == null || serverFileName.length() == 0) {
            return false;
        }
        getFocusManager().onEditWord();
        update();
        //清除Table显示的全部数据
        getCTableManager().clearShowData();
        //设置宏为编辑状态
        getMacroroutineManager().edit();
        //调试
        //if(debug)
        //    onSaveAsDebug();
        boolean result = false;
        if (type == 0) {
            result = openOutStream();
        } else {
            //创建临时目录
            result = createLocalTempFile("jhw");
        }
        if (!result) {
            getFocusManager().onEditWord();
            update();
            if (getMessageBoxSwitch()) {
                messageBox("无法创建本地文件!");
            }
            return false;
        }
        //保存数据
        boolean flg = saveData();
        //关闭流
        closeOutStream();
        if (!flg) {
            getFocusManager().onEditWord();
            update();
            if (getMessageBoxSwitch()) {
                messageBox("保存失败!");
            }
            return false;
        }
        switch (type) {
        case -1:
        case 1: //AppServer打开

            //服务器上传
            if (!TIOM_AppServer.writeFile(serverFileName, getTempFileName())) {
                getFocusManager().onEditWord();
                update();
                if (getMessageBoxSwitch()) {
                    messageBox("服务器上传失败!");
                }
                return false;
            }
            break;
        case 2: //文件服务器上打开EMR模板文件
            serverFileName = getEmrTempletDir() + serverFileName;

            //服务器上传
            if (!TIOM_FileServer.writeFile(TIOM_FileServer.getSocket(),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("服务器上传失败!");
                }
                return false;
            }
            break;
        case 3: //文件服务器上打开EMR数据文件
            serverFileName = getEmrDataDir(fileName) + serverFileName;

            //服务器上传
            if (!TIOM_FileServer.writeFile(getFileServerAddress(fileName),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("服务器上传失败!");
                }
                return false;
            }
            break;
        }
        //设置打开文件的位置
        setOpenServerType(type);
        //设置目录
        setPath(path);
        //设置文件名
        setFileName(fileName);

        getFocusManager().onEditWord();
        update();
        if (getMessageBoxSwitch()) {
            messageBox("保存成功!");
        }

        setModify(false);
        runListener("onSave");
        return true;
    }

    /**
     * 报表保存
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
            //创建临时目录
            result = createLocalTempFile("jhw");
        }
        if (!result) {
            if (getMessageBoxSwitch()) {
                messageBox("无法创建本地文件!");
            }
            return false;
        }
        //保存数据
        boolean flg = saveData();
        //关闭流
        closeOutStream();
        if (!flg) {
            if (getMessageBoxSwitch()) {
                messageBox("保存失败!");
            }
            return false;
        }
        switch (type) {
        case -1:
        case 1: //AppServer打开

            //服务器上传
            if (!TIOM_AppServer.writeFile(serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("服务器上传失败!");
                }
                return false;
            }
            break;
        case 2: //文件服务器上打开EMR模板文件
            serverFileName = getEmrTempletDir() + serverFileName;

            //服务器上传
            if (!TIOM_FileServer.writeFile(TIOM_FileServer.getSocket(),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("服务器上传失败!");
                }
                return false;
            }
            break;
        case 3: //文件服务器上打开EMR数据文件
            serverFileName = getEmrDataDir() + serverFileName;

            //服务器上传
            if (!TIOM_FileServer.writeFile(TIOM_FileServer.getSocket(),
                                           serverFileName, getTempFileName())) {
                if (getMessageBoxSwitch()) {
                    messageBox("服务器上传失败!");
                }
                return false;
            }
            break;
        }
        if (getMessageBoxSwitch()) {
            messageBox("保存成功!");
        }
        return true;
    }


    /**
     * 执行事件
     * @param action String
     * @param parameters Object[]
     * @return Object
     */
    public Object runListener(String action, Object ...parameters) {
        return runListenerArray(action, parameters);
    }

    /**
     * 执行事件
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
     * 保存调试文件
     */
    public void onSaveAsDebug() {
        //创建临时目录
        if (!createLocalTempFile("jhd")) {
            messageBox("无法创建本地临时文件!");
            return;
        }
        //保存数据
        boolean flg = saveDataDebug();
        //关闭流
        closeOutStream();
        if (!flg) {
            messageBox("保存失败!");
            return;
        }
    }

    /**
     * 打开文件对话框
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
        //打开
        onOpen(dir, filename, 1, edit);
        return true;
    }

    /**
     * 打开对话框
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
     * 打开文件
     * @param fileName String
     * @return boolean
     */
    public boolean onOpen(String fileName) {
        return onOpen(fileName, true);
    }

    /**
     * 打开文件
     * @param fileName String
     * @param state boolean true 阅览 false 编辑
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
     * 打开文件
     * @param path String
     * @param fileName String
     * @param type int
     * 0 本地
     * 1 AppServer
     * 2 FileServer Emr模板文件
     * 3 FileServer Emr数据文件
     * @param state boolean true 阅览 false 编辑
     * @return boolean true 成功 false 失败
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
        case 0: //本地打开
            setTempFileName(serverFileName);
            break;
        case 1: //AppServer打开

            //服务器下载
            if (!TIOM_AppServer.readFileToLocal(serverFileName, getTempFileName())) {
                messageBox("服务器下载失败!");
                return false;
            }
            break;
        case 2: //文件服务器上打开EMR模板文件
            serverFileName = getEmrTempletDir() + serverFileName;

            //服务器下载
            if (!TIOM_FileServer.readFileToLocal(TIOM_FileServer.getSocket(),
                                                 serverFileName,
                                                 getTempFileName())) {
                messageBox("服务器下载失败!");
                return false;
            }
            break;
        case 3: //文件服务器上打开EMR数据文件
        	//add by lx 2016/03/30
            serverFileName = getEmrDataDir(fileName) + serverFileName;
            //服务器下载
            if (!TIOM_FileServer.readFileToLocal(getFileServerAddress(fileName),
                                                 serverFileName,
                                                 getTempFileName())) {
                messageBox("服务器下载失败!");
                return false;
            }
            break;
        case 4:
        	//add by lx 2016/03/30
            serverFileName = getRefEmrDataDir(fileName) + serverFileName;
			if (!TIOM_FileServer.readFileToLocal(getFileServerAddress(fileName),
					serverFileName, getTempFileName())) {
				messageBox("服务器下载失败!");
				return false;
			}
            break;     
        }
        //设置打开文件的位置
        setOpenServerType(type);
        //设置目录
        setPath(path);
        //设置文件名
        setFileName(fileName);
        //加载本地文件
        return openTempFile(state);
    }

    /**
     * 加载本地文件
     * @param state boolean true 阅览 false 编辑
     * @return boolean true 成功 false 失败
     */
    private boolean openTempFile(boolean state) {
        //清除文字数据
        getStringManager().removeAll();
        //清除全部内容
        getPageManager().removeAll();
        //清除Table控制类
        getCTableManager().removeAll();
        //清除宏控制类
        getMacroroutineManager().removeAll();
        //清理语法
        getSyntaxManager().clear();

        openLocalTempFile(getTempFileName());
        readData();
        closeInStream();
        if (state) {
            getFocusManager().onPreviewWord();
        } else {
            getFocusManager().onEditWord();
        }
        //启动打开语法
        getSyntaxManager().onOpen();
        //更新
        update();
        return true;
    }

    /**
     * 得到打印作业名称
     * @return String
     */
    public String getJobName() {
    	//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//String name = "LABEL_LOG";
	    //name +=format.format(new Date());
    	//HH MM SS
		String time1 =fmt.format(new Date());
		//System.out.println("------文件名----"+this.getFileName()+"_"+time1);
    	return this.getFileName()+"_"+time1;
        //return "JavaHisWord Print Job";
    }

    /**
     * 是否打开
     * @return boolean
     */
    public boolean isOpen() {
        return fileName != null && fileName.length() > 0;
    }

    /**
     * 设置标题
     * @param title String
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 得到标题
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置英文标题
     * @param enTitle String
     */
    public void setEnTitle(String enTitle) {
        this.enTitle = enTitle;
    }

    /**
     * 得到英文标题
     * @return String
     */
    public String getEnTitle() {
        return enTitle;
    }

    /**
     * 设置阅览窗口X
     * @param previewWindowX int
     */
    public void setPreviewWindowX(int previewWindowX) {
        this.previewWindowX = previewWindowX;
    }

    /**
     * 得到阅览窗口X
     * @return int
     */
    public int getPreviewWindowX() {
        return previewWindowX;
    }

    /**
     * 设置阅览窗口Y
     * @param previewWindowY int
     */
    public void setPreviewWindowY(int previewWindowY) {
        this.previewWindowY = previewWindowY;
    }

    /**
     * 得到阅览窗口Y
     * @return int
     */
    public int getPreviewWindowY() {
        return previewWindowY;
    }

    /**
     * 设置阅览窗口宽度
     * @param previewWindowWidth int
     */
    public void setPreviewWindowWidth(int previewWindowWidth) {
        this.previewWindowWidth = previewWindowWidth;
    }

    /**
     * 得到阅览窗口宽度
     * @return int
     */
    public int getPreviewWindowWidth() {
        return previewWindowWidth;
    }

    /**
     * 设置阅览窗口高度
     * @param previewWindowHeight int
     */
    public void setPreviewWindowHeight(int previewWindowHeight) {
        this.previewWindowHeight = previewWindowHeight;
    }

    /**
     * 得到阅览窗口高度
     * @return int
     */
    public int getPreviewWindowHeight() {
        return previewWindowHeight;
    }

    /**
     * 设置阅览窗口居中
     * @param previewWindowCenter boolean
     */
    public void setPreviewWindowCenter(boolean previewWindowCenter) {
        this.previewWindowCenter = previewWindowCenter;
    }

    /**
     * 得到阅览窗口居中
     * @return boolean
     */
    public boolean isPreviewWindowCenter() {
        return previewWindowCenter;
    }

    /**
     * 设置参数
     * @param parameter Object
     */
    public void setParameter(Object parameter) {
        this.parameter = parameter;
    }

    /**
     * 得到参数
     * @return Object
     */
    public Object getParameter() {
        if (parameter == null) {
            return "DEFAULT";
        }
        return parameter;
    }

    /**
     * 是否调试加载
     * @return boolean
     */
    public boolean isLoadDebug() {
        return loadDebug;
    }

    /**
     * 得到文件服务器存储根目录
     * @return String
     */
    public String getFileServerRoot() {
        return TIOM_FileServer.getRoot();
    }

    /**
     * 得到结构化病历模板存放的根目录
     * @return String
     */
    public String getEmrTempletDir() {
        return getFileServerRoot() + TIOM_FileServer.getPath("EmrTemplet");
    }

    /**
     * 得到结构化病历数据存放的根目录
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
     * 设置对话框开关
     * @param messageBoxSwitch boolean
     */
    public void setMessageBoxSwitch(boolean messageBoxSwitch) {
        this.messageBoxSwitch = messageBoxSwitch;
    }

    /**
     * 得到对话框开关
     * @return boolean
     */
    public boolean getMessageBoxSwitch() {
        return messageBoxSwitch;
    }

    /**
     * 设置编辑锁
     * @param editLock boolean
     */
    public void setEditLock(boolean editLock) {
        this.editLock = editLock;
    }

    /**
     * 是否有编辑锁
     * @return boolean
     */
    public boolean isEditLock() {
        return editLock;
    }

    /**
     * 设置锁定用户
     * @param lockUser String
     */
    public void setLockUser(String lockUser) {
        this.lockUser = lockUser;
    }

    /**
     * 得到锁定用户
     * @return String
     */
    public String getLockUser() {
        return lockUser;
    }

    /**
     * 设置锁定日期
     * @param lockDate String
     */
    public void setLockDate(String lockDate) {
        this.lockDate = lockDate;
    }

    /**
     * 得到锁定日期
     * @return String
     */
    public String getLockDate() {
        return lockDate;
    }

    /**
     * 设置锁定部门
     * @param lockDept String
     */
    public void setLockDept(String lockDept) {
        this.lockDept = lockDept;
    }

    /**
     * 得到锁定部门
     * @return String
     */
    public String getLockDept() {
        return lockDept;
    }

    /**
     * 设定锁定IP
     * @param lockIP String
     */
    public void setLockIP(String lockIP) {
        this.lockIP = lockIP;
    }

    /**
     * 得到锁定IP
     * @return String
     */
    public String getLockIP() {
        return lockIP;
    }

    /**
     * 设置最后修改人
     * @param lastEditUser String
     */
    public void setLastEditUser(String lastEditUser) {
        this.lastEditUser = lastEditUser;
    }

    /**
     * 得到最后修改人
     * @return String
     */
    public String getLastEditUser() {
        return lastEditUser;
    }

    /**
     * 设置最后修改日期
     * @param lastEditDate String
     */
    public void setLastEditDate(String lastEditDate) {
        this.lastEditDate = lastEditDate;
    }

    /**
     * 得到最后修改日期
     * @return String
     */
    public String getLastEditDate() {
        return lastEditDate;
    }

    /**
     * 设置最后修改IP
     * @param lastEditIP String
     */
    public void setLastEditIP(String lastEditIP) {
        this.lastEditIP = lastEditIP;
    }

    /**
     * 得到最后修改IP
     * @return String
     */
    public String getLastEditIP() {
        return lastEditIP;
    }

    /**
     * 需要上传图片
     * @param lastEditUser String
     */
    public void setContainPic(String containPic) {
        this.containPic = containPic;
    }

    /**
     * 需要上传图片
     * @return String
     */
    public String getContainPic() {

        return containPic;
    }
    
	/**
	 * 通过文件名，确认文件存储位置
	 * 
	 * @param fileName
	 * @return
	 */
	public TSocket getFileServerAddress(String fileName) {
		//默认文件服器
		TSocket tsocket = TIOM_FileServer.getSocket("Main");
		// 1.文件名不是空串情况下
		if (fileName != null && !fileName.equals("")) {
			// 2.包含-的情况
			if (fileName.indexOf("_") != -1) {
				// 3.取第一个caseNo下的情况前2位的情况
				String sYear = fileName.substring(0, 2);
				//System.out.println("---sYear：---" + sYear);
				TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
		        String ip = config.getString("","FileServer." + sYear + ".IP");
		        //System.out.println("====IP：======"+ip);
		        if (ip != null && !ip.equals("")) {
				// 4.找指定的配置文件，如果有则：
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
    	// 1.文件名不是空串情况下
		if (fileName != null && !fileName.equals("")) {
			// 2.包含-的情况
			if (fileName.indexOf("_") != -1) {
				// 3.取第一个caseNo下的情况前2位的情况
				String sYear = fileName.substring(0, 2);
				//System.out.println("---sYear：---" + sYear);
				TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
		        String root = config.getString("","FileServer." + sYear + ".Root");
		        if (root != null && !root.equals("")) {
				// 4.找指定的配置文件，如果有则：
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
    	// 1.文件名不是空串情况下
		if (fileName != null && !fileName.equals("")) {
			// 2.包含-的情况
			if (fileName.indexOf("_") != -1) {
				// 3.取第一个caseNo下的情况前2位的情况
				String sYear = fileName.substring(0, 2);
				//System.out.println("---sYear：---" + sYear);
				TConfig config = TConfig.getConfig("WEB-INF/config/system/TConfig.x");
		        String root = config.getString("","FileServer." + sYear + ".Root");
		        //System.out.println("====IP：======"+ip);
		        if (root != null && !root.equals("")) {
				// 4.找指定的配置文件，如果有则：
		        	strEmrDataDir = TIOM_FileServer.getRoot(sYear)+ TIOM_FileServer.getPath("RefEmrData");
		        }				
			}
		}
    	
    	return strEmrDataDir;
    }


}
