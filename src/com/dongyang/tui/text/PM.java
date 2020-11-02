package com.dongyang.tui.text;

import com.dongyang.tui.DText;


/**
 *
 * <p>Title: ������</p>
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
     * ����UI
     */
    private DText superUI;
    /**
     * ҳ�������
     */
    private MPage pageManager;
    /**
     * �ַ����ݹ�����
     */
    private MString stringManager;
    /**
     * ��ʾ������
     */
    private MView viewManager;
    /**
     * �ļ�������
     */
    private MFile fileManager;
    /**
     * �¼�������
     */
    private MEvent eventManager;
    /**
     * ��������
     */
    private MStyle styleManager;
    /**
     * �����������
     */
    //private MStyle reportStyleManager;
    /**
     * ���������
     */
    private MFocus focusManager;
    /**
     * Table������������
     */
    private MCTable cTableManager;
    /**
     * �������
     */
    private MMacroroutine macroroutineManager;
    /**
     * �﷨������
     */
    private MSyntax syntaxManager;
    /**
     * ����������
     */
    private MMicroField microFieldManager;
    /**
     * �޸ļ�¼������
     */
    private MModifyNode modifyNodeManager;
    /**
     * ͼƬ������
     */
    private MImage imageManager;
    /**
     * �Ա���ʾ
     */
    private int sexControl = 0;
    /**
     * ������
     */
    private String mrNo;
    /**
     * �����
     */
    private String caseNo;
    /**
     * �ű�������
     */
    private MSrcipt srciptManager;

    /**
     * ��ǰ�û�ID
     */
    private String userId;
    /**
     * ��ǰ�û���
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
     * ������
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
     * �����췽��
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
     * ������
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
     * ����UI
     * @param UI DText
     */
    public void setUI(DText UI) {
        this.UI = UI;
    }

    /**
     * �õ�UI
     * @return DText
     */
    public DText getUI() {
        return UI;
    }

    /**
     * ���ø���UI
     * @param superUI DText
     */
    public void setSuperUI(DText superUI) {
        this.superUI = superUI;
    }

    /**
     * �ô���UI
     * @return DText
     */
    public DText getSuperUI() {
        return superUI;
    }

    /**
     * ����ҳ�������
     * @param pageManager MPage
     */
    public void setPageManager(MPage pageManager) {
        this.pageManager = pageManager;
        if (pageManager != null) {
            pageManager.setPM(this);
        }
    }

    /**
     * �õ�ҳ�������
     * @return MPage
     */
    public MPage getPageManager() {
        return pageManager;
    }

    /**
     * �����ַ����ݹ�����
     * @param stringManager MString
     */
    public void setStringManager(MString stringManager) {
        this.stringManager = stringManager;
        if (stringManager != null) {
            stringManager.setPM(this);
        }
    }

    /**
     * �õ��ַ����ݹ�����
     * @return MString
     */
    public MString getStringManager() {
        return stringManager;
    }

    /**
     * ������ʾ������
     * @param viewManager MView
     */
    public void setViewManager(MView viewManager) {
        this.viewManager = viewManager;
        if (viewManager != null) {
            viewManager.setPM(this);
        }
    }

    /**
     * �õ���ʾ������
     * @return MView
     */
    public MView getViewManager() {
        return viewManager;
    }

    /**
     * �����ļ�������
     * @param fileManager MFile
     */
    public void setFileManager(MFile fileManager) {
        this.fileManager = fileManager;
        if (fileManager != null) {
            fileManager.setPM(this);
        }
    }

    /**
     * �õ��ļ�������
     * @return MFile
     */
    public MFile getFileManager() {
        return fileManager;
    }

    /**
     * �����¼�������
     * @param eventManager MEvent
     */
    public void setEventManager(MEvent eventManager) {
        this.eventManager = eventManager;
        if (eventManager != null) {
            eventManager.setPM(this);
        }
    }

    /**
     * �õ��¼�������
     * @return MEvent
     */
    public MEvent getEventManager() {
        return eventManager;
    }

    /**
     * ���÷�������
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
     * �õ���������
     * @return MStyle
     */
    public MStyle getStyleManager() {
        return styleManager;
    }

    /**
     * ���ý��������
     * @param focusManager MFocus
     */
    public void setFocusManager(MFocus focusManager) {
        this.focusManager = focusManager;
        if (focusManager != null) {
            focusManager.setPM(this);
        }
    }

    /**
     * �õ����������
     * @return MFocus
     */
    public MFocus getFocusManager() {
        return focusManager;
    }

    /**
     * ����Table������������
     * @param cTableManager MCTable
     */
    public void setCTableManager(MCTable cTableManager) {
        this.cTableManager = cTableManager;
        if (cTableManager != null) {
            cTableManager.setPM(this);
        }
    }

    /**
     * �õ�Table������������
     * @return MCTable
     */
    public MCTable getCTableManager() {
        return cTableManager;
    }

    /**
     * ���ú������
     * @param macroroutineManager MMacroroutine
     */
    public void setMacroroutineManager(MMacroroutine macroroutineManager) {
        this.macroroutineManager = macroroutineManager;
        if (macroroutineManager != null) {
            macroroutineManager.setPM(this);
        }
    }

    /**
     * �õ��������
     * @return MMacroroutine
     */
    public MMacroroutine getMacroroutineManager() {
        return macroroutineManager;
    }

    /**
     * �����﷨������
     * @param syntaxManager MSyntax
     */
    public void setSyntaxManager(MSyntax syntaxManager) {
        this.syntaxManager = syntaxManager;
        if (syntaxManager != null) {
            syntaxManager.setPM(this);
        }
    }

    /**
     * �õ��﷨������
     * @return MSyntax
     */
    public MSyntax getSyntaxManager() {
        return syntaxManager;
    }

    /**
     * ���ò����
     * @param microFieldManager MMicroField
     */
    public void setMicroFieldManager(MMicroField microFieldManager) {
        this.microFieldManager = microFieldManager;
        if (microFieldManager != null) {
            microFieldManager.setPM(this);
        }
    }

    /**
     * �õ�����������
     * @return MMicroField
     */
    public MMicroField getMicroFieldManager() {
        return microFieldManager;
    }

    /**
     * �����޸ļ�¼������
     * @param modifyNodeManager MModifyNode
     */
    public void setModifyNodeManager(MModifyNode modifyNodeManager) {
        this.modifyNodeManager = modifyNodeManager;
        if (modifyNodeManager != null) {
            modifyNodeManager.setPM(this);
        }
    }

    /**
     * �õ��޸ļ�¼������
     * @return MModifyNode
     */
    public MModifyNode getModifyNodeManager() {
        return modifyNodeManager;
    }

    /**
     * ���ýű�������
     * @param srciptManager MSrcipt
     */
    public void setSrciptManager(MSrcipt srciptManager) {
        this.srciptManager = srciptManager;
        if (srciptManager != null) {
            srciptManager.setPM(this);
        }
    }

    /**
     * �õ��ű�������
     * @return MSrcipt
     */
    public MSrcipt getSrciptManager() {
        return srciptManager;
    }

    /**
     * ����ͼƬ������
     * @param imageManager MImage
     */
    public void setImageManager(MImage imageManager) {
        this.imageManager = imageManager;
        if (imageManager != null) {
            imageManager.setPM(this);
        }
    }

    /**
     * �õ�ͼƬ������
     * @return MImage
     */
    public MImage getImageManager() {
        return imageManager;
    }

    /**
     * �����Ա�����
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
     * �õ��Ա�����
     * @return int
     */
    public int getSexControl() {
        return sexControl;
    }

    /**
     * ���ò�����
     * @param mrNo String
     */
    public void setMrNo(String mrNo) {
        this.mrNo = mrNo;
    }

    /**
     * �õ�������
     * @return String
     */
    public String getMrNo() {
        return mrNo;
    }

    /**
     * ���������
     * @param caseNo String
     */
    public void setCaseNo(String caseNo) {
        this.caseNo = caseNo;
    }

    /**
     * �õ������
     * @return String
     */
    public String getCaseNo() {
        return caseNo;
    }

    /**
     * �̶��ı�����ץȡ����
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
