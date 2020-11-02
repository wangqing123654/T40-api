package com.dongyang.tui.text;

import com.dongyang.tui.DText;


/**
 *
 * <p>Title: 管理者</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.3.11
 * @version 1.0
 */
public class PM {
    /**
     * UI
     */
    private DText UI;
    /**
     * 父类UI
     */
    private DText superUI;
    /**
     * 页面管理器
     */
    private MPage pageManager;
    /**
     * 字符数据管理器
     */
    private MString stringManager;
    /**
     * 显示管理器
     */
    private MView viewManager;
    /**
     * 文件管理器
     */
    private MFile fileManager;
    /**
     * 事件管理器
     */
    private MEvent eventManager;
    /**
     * 风格管理器
     */
    private MStyle styleManager;
    /**
     * 报表风格管理器
     */
    //private MStyle reportStyleManager;
    /**
     * 焦点控制器
     */
    private MFocus focusManager;
    /**
     * Table控制器管理器
     */
    private MCTable cTableManager;
    /**
     * 宏控制器
     */
    private MMacroroutine macroroutineManager;
    /**
     * 语法管理器
     */
    private MSyntax syntaxManager;
    /**
     * 插入宏管理器
     */
    private MMicroField microFieldManager;
    /**
     * 修改记录管理器
     */
    private MModifyNode modifyNodeManager;
    /**
     * 图片管理器
     */
    private MImage imageManager;
    /**
     * 性别显示
     */
    private int sexControl = 0;
    /**
     * 病案号
     */
    private String mrNo;
    /**
     * 问诊号
     */
    private String caseNo;
    /**
     * 脚本管理器
     */
    private MSrcipt srciptManager;

    /**
     * 当前用户ID
     */
    private String userId;
    /**
     * 当前用户名
     */
    private String userName;

    public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUser(String userId,String userName){

		this.userId = userId;
		this.userName = userName;
	}

	/**
     * 构造器
     */
    public PM() {
        setPageManager(new MPage());
        setStringManager(new MString());
        setViewManager(new MView());
        setFileManager(new MFile());
        setEventManager(new MEvent());
        setStyleManager(new MStyle(false));
        setFocusManager(new MFocus());
        setCTableManager(new MCTable());
        setMacroroutineManager(new MMacroroutine());
        setSyntaxManager(new MSyntax());
        setMicroFieldManager(new MMicroField());
        setModifyNodeManager(new MModifyNode());
        setSrciptManager(new MSrcipt());
        setImageManager(new MImage());

    }

    /**
     * 报表构造方法
     * @param isReport boolean
     */
    public PM(boolean isReport) {

        setPageManager(new MPage());
        setStringManager(new MString());
        setViewManager(new MView());
        setFileManager(new MFile());
        setEventManager(new MEvent());
        setStyleManager(new MStyle(true));
        setFocusManager(new MFocus());
        setCTableManager(new MCTable());
        setMacroroutineManager(new MMacroroutine());
        setSyntaxManager(new MSyntax());
        setMicroFieldManager(new MMicroField());
        setModifyNodeManager(new MModifyNode());
        setSrciptManager(new MSrcipt());
        setImageManager(new MImage());

    }

    /**
     * 构造器
     * @param UI DText
     */
    public PM(DText UI) {
        this();
        setUI(UI);
        //System.out.println("===isPreview==="+UI.isPreview());

    }

    public PM(DText UI, boolean isReport) {
        this(isReport);
        setUI(UI);
        //System.out.println("===isPreview==="+UI.isPreview());

    }


    /**
     * 设置UI
     * @param UI DText
     */
    public void setUI(DText UI) {
        this.UI = UI;
    }

    /**
     * 得到UI
     * @return DText
     */
    public DText getUI() {
        return UI;
    }

    /**
     * 设置父类UI
     * @param superUI DText
     */
    public void setSuperUI(DText superUI) {
        this.superUI = superUI;
    }

    /**
     * 得打父类UI
     * @return DText
     */
    public DText getSuperUI() {
        return superUI;
    }

    /**
     * 设置页面管理器
     * @param pageManager MPage
     */
    public void setPageManager(MPage pageManager) {
        this.pageManager = pageManager;
        if (pageManager != null) {
            pageManager.setPM(this);
        }
    }

    /**
     * 得到页面管理器
     * @return MPage
     */
    public MPage getPageManager() {
        return pageManager;
    }

    /**
     * 设置字符数据管理器
     * @param stringManager MString
     */
    public void setStringManager(MString stringManager) {
        this.stringManager = stringManager;
        if (stringManager != null) {
            stringManager.setPM(this);
        }
    }

    /**
     * 得到字符数据管理器
     * @return MString
     */
    public MString getStringManager() {
        return stringManager;
    }

    /**
     * 设置显示管理器
     * @param viewManager MView
     */
    public void setViewManager(MView viewManager) {
        this.viewManager = viewManager;
        if (viewManager != null) {
            viewManager.setPM(this);
        }
    }

    /**
     * 得到显示管理器
     * @return MView
     */
    public MView getViewManager() {
        return viewManager;
    }

    /**
     * 设置文件管理器
     * @param fileManager MFile
     */
    public void setFileManager(MFile fileManager) {
        this.fileManager = fileManager;
        if (fileManager != null) {
            fileManager.setPM(this);
        }
    }

    /**
     * 得到文件管理器
     * @return MFile
     */
    public MFile getFileManager() {
        return fileManager;
    }

    /**
     * 设置事件管理器
     * @param eventManager MEvent
     */
    public void setEventManager(MEvent eventManager) {
        this.eventManager = eventManager;
        if (eventManager != null) {
            eventManager.setPM(this);
        }
    }

    /**
     * 得到事件管理器
     * @return MEvent
     */
    public MEvent getEventManager() {
        return eventManager;
    }

    /**
     * 设置风格管理器
     * @param styleManager MStyle
     */
    public void setStyleManager(MStyle styleManager) {
        this.styleManager = styleManager;
        if (styleManager != null) {
            styleManager.setPM(this);
        }
    }

    /**
     *
     * @param reportStyleManager MStyle
     */
    /**public void setReportStyleManager(MStyle reportStyleManager){
        this.reportStyleManager=reportStyleManager;
        if(reportStyleManager!=null)
            reportStyleManager.setPM(this);

         }**/
    /**
     *
     * @return MStyle
     */
    /**public MStyle getReportStyleManager()
        {
         return reportStyleManager;
        }**/


    /**
     * 得到风格管理器
     * @return MStyle
     */
    public MStyle getStyleManager() {
        return styleManager;
    }

    /**
     * 设置焦点控制器
     * @param focusManager MFocus
     */
    public void setFocusManager(MFocus focusManager) {
        this.focusManager = focusManager;
        if (focusManager != null) {
            focusManager.setPM(this);
        }
    }

    /**
     * 得到焦点控制器
     * @return MFocus
     */
    public MFocus getFocusManager() {
        return focusManager;
    }

    /**
     * 设置Table控制器管理器
     * @param cTableManager MCTable
     */
    public void setCTableManager(MCTable cTableManager) {
        this.cTableManager = cTableManager;
        if (cTableManager != null) {
            cTableManager.setPM(this);
        }
    }

    /**
     * 得到Table控制器管理器
     * @return MCTable
     */
    public MCTable getCTableManager() {
        return cTableManager;
    }

    /**
     * 设置宏控制器
     * @param macroroutineManager MMacroroutine
     */
    public void setMacroroutineManager(MMacroroutine macroroutineManager) {
        this.macroroutineManager = macroroutineManager;
        if (macroroutineManager != null) {
            macroroutineManager.setPM(this);
        }
    }

    /**
     * 得到宏控制器
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager() {
        return macroroutineManager;
    }

    /**
     * 设置语法管理器
     * @param syntaxManager MSyntax
     */
    public void setSyntaxManager(MSyntax syntaxManager) {
        this.syntaxManager = syntaxManager;
        if (syntaxManager != null) {
            syntaxManager.setPM(this);
        }
    }

    /**
     * 得到语法管理器
     * @return MSyntax
     */
    public MSyntax getSyntaxManager() {
        return syntaxManager;
    }

    /**
     * 设置插入宏
     * @param microFieldManager MMicroField
     */
    public void setMicroFieldManager(MMicroField microFieldManager) {
        this.microFieldManager = microFieldManager;
        if (microFieldManager != null) {
            microFieldManager.setPM(this);
        }
    }

    /**
     * 得到插入宏管理器
     * @return MMicroField
     */
    public MMicroField getMicroFieldManager() {
        return microFieldManager;
    }

    /**
     * 设置修改记录管理器
     * @param modifyNodeManager MModifyNode
     */
    public void setModifyNodeManager(MModifyNode modifyNodeManager) {
        this.modifyNodeManager = modifyNodeManager;
        if (modifyNodeManager != null) {
            modifyNodeManager.setPM(this);
        }
    }

    /**
     * 得到修改记录管理器
     * @return MModifyNode
     */
    public MModifyNode getModifyNodeManager() {
        return modifyNodeManager;
    }

    /**
     * 设置脚本管理器
     * @param srciptManager MSrcipt
     */
    public void setSrciptManager(MSrcipt srciptManager) {
        this.srciptManager = srciptManager;
        if (srciptManager != null) {
            srciptManager.setPM(this);
        }
    }

    /**
     * 得到脚本管理器
     * @return MSrcipt
     */
    public MSrcipt getSrciptManager() {
        return srciptManager;
    }

    /**
     * 设置图片管理器
     * @param imageManager MImage
     */
    public void setImageManager(MImage imageManager) {
        this.imageManager = imageManager;
        if (imageManager != null) {
            imageManager.setPM(this);
        }
    }

    /**
     * 得到图片管理器
     * @return MImage
     */
    public MImage getImageManager() {
        return imageManager;
    }

    /**
     * 设置性别限制
     * @param sexControl int
     */
    public void setSexControl(int sexControl) {
        if (this.sexControl == sexControl) {
            return;
        }
        getPageManager().resetData(new EAction(EAction.SET_SEX_CONTROL));
        this.sexControl = sexControl;
    }

    /**
     * 得到性别限制
     * @return int
     */
    public int getSexControl() {
        return sexControl;
    }

    /**
     * 设置病案号
     * @param mrNo String
     */
    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    /**
     * 得到病案号
     * @return String
     */
    public String getMrNo() {
        return mrNo;
    }

    /**
     * 设置问诊号
     * @param caseNo String
     */
    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    /**
     * 得到问诊号
     * @return String
     */
    public String getCaseNo() {
        return caseNo;
    }

    /**
     * 固定文本重新抓取数据
     * @param mrNo String
     * @param caseNo String
     */
    public void fixedTryReset(String mrNo, String caseNo) {
        setMrNo(mrNo);
        setCaseNo(caseNo);
        if (getMrNo() == null || getMrNo().length() == 0) {
            return;
        }
        if (getCaseNo() == null || getCaseNo().length() == 0) {
            return;
        }
        getPageManager().resetData(new EAction(EAction.FIXED_TRY_RESET));
        getFocusManager().update();
    }
}
