package com.dongyang.ui.base;

import javax.swing.JComboBox;
import com.dongyang.ui.TComponent;
import java.awt.event.ActionEvent;
import com.dongyang.ui.event.TMouseListener;
import com.dongyang.control.TControl;
import com.dongyang.config.TConfigParm;
import java.awt.Container;
import com.dongyang.ui.event.BaseEvent;
import java.awt.event.MouseEvent;
import com.dongyang.util.RunClass;
import com.dongyang.ui.event.TActionListener;
import java.util.ArrayList;
import com.dongyang.ui.event.TMouseMotionListener;
import com.dongyang.util.StringTool;
import java.lang.reflect.Method;
import javax.swing.ComboBoxModel;
import java.util.Vector;
import com.dongyang.ui.TComboNode;
import com.dongyang.data.TParm;
import com.dongyang.ui.event.TComboBoxEvent;
import com.dongyang.ui.event.TKeyListener;
import java.awt.event.KeyEvent;
import javax.swing.KeyStroke;
import java.awt.Component;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import com.dongyang.jdo.TJDOTool;
import com.dongyang.manager.TCM_Transform;
import com.dongyang.ui.event.TFocusListener;
import java.awt.event.FocusEvent;
import java.awt.Dimension;
import com.dongyang.ui.TUIStyle;
import javax.swing.UIManager;
import com.dongyang.manager.TIOM_Database;
import com.dongyang.jdo.TDataStore;
import javax.swing.plaf.ComboBoxUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.basic.BasicComboPopup;
import java.awt.Toolkit;
import java.awt.Point;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.GraphicsConfiguration;
import javax.swing.MenuSelectionManager;
import javax.swing.MenuElement;
import javax.swing.JMenu;
import java.awt.Font;
import java.awt.Color;
import java.util.Date;
import com.dongyang.jdo.MaxLoad;
import com.dongyang.ui.TContainer;
import com.dongyang.jdo.MaxLoadInf;
import com.dongyang.util.TSystem;
import com.dongyang.ui.event.TComponentListener;
import com.dongyang.db.TConnection;
import com.dongyang.db.TDBPoolManager;
import com.dongyang.jdo.TJDODBTool;

public class TComboBoxBase extends JComboBox implements TComponent {
    /**
     * �����¼�
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * ��ǩ
     */
    private String tag = "";
    /**
     * ���ر�ǩ
     */
    private String loadtag;
    /**
     * ��������
     */
    private String controlClassName;
    /**
     * ������
     */
    private TControl control;
    /**
     * ������Ϣ
     */
    private String actionMessage;
    /**
     * ѡ�ж�����Ϣ
     */
    private String selectedAction;
    /**
     * ���ò�������
     */
    private TConfigParm configParm;
    /**
     * ��ʾID
     */
    private boolean showID = true;
    /**
     * ��ʾ����
     */
    private boolean showName = false;
    /**
     * ��ʾ�ı�
     */
    private boolean showText = true;
    /**
     * ��ʾֵ
     */
    private boolean showValue = false;
    /**
     * ��ʾƴ��1
     */
    private boolean showPy1 = false;
    /**
     * ��ʾƴ��2
     */
    private boolean showPy2 = false;
    /**
     * �������ձ�
     */
    private String parmMap;
    /**
     * Table �齨��ʾ�б�
     */
    private String tableShowList;
    /**
     * ����¼���������
     */
    private TMouseListener mouseListenerObject;
    /**
     * ����ƶ���������
     */
    private TMouseMotionListener mouseMotionListenerObject;
    /**
     * ������������
     */
    private TActionListener actionListenerObject;
    /**
     * ���̼�������
     */
    private TKeyListener keyListenerObject;
    /**
     * �Ƿ��ʼ���¼�����
     */
    private boolean isInitListener;
    /**
     * �ϴ�ѡ�б�ǩ
     */
    private TComboNode oldSelectedNode;
    /**
     * �ַ�����
     */
    private String stringData;
    /**
     * Module�ļ���
     */
    private String moduleName;
    /**
     * Module������
     */
    private String moduleMethodName;
    /**
     * Module����
     */
    private TParm moduleParm;
    /**
     * Module��̬����
     */
    private String moduleParmString;
    /**
     * Module��̬����
     */
    private String moduleParmTag;
    /**
     * �����¼���������
     */
    private TFocusListener focusListenerObject;
    /**
     * ʧȥ���㶯��
     */
    private String focusLostAction;
    /**
     * ���ݴ洢
     */
    private TDataStore dataStore;
    /**
     * SQL���
     */
    private String sql;
    /**
     * չ�����
     */
    private int expandWidth;
    /**
     * �������
     */
    private TComponent parentComponent;
    /**
     * ���ڼ�������
     */
    private boolean initNow;
    /**
     * ���ڲ�ѯ
     */
    private boolean queryNow;
    /**
     * �첽��ѯ
     */
    private boolean postQuery;
    /**
     * �ܷ�༭
     */
    private boolean canEdit;
    /**
     * ����
     */
    private String language;
    /**
     * ����Tip
     */
    private String zhTip;
    /**
     * Ӣ��Tip
     */
    private String enTip;
    /**
     * ����
     */
    private Font tfont;
    /**
     * ����
     */
    private boolean isAutoCenter;
    /**
     * �Զ��ߴ�
     */
    private int autoSize = 5;
    /**
     * �Զ�X
     */
    private boolean autoX;
    /**
     * �Զ�Y
     */
    private boolean autoY;
    /**
     * �Զ����
     */
    private boolean autoWidth;
    /**
     * �Զ��߶�
     */
    private boolean autoHeight;
    /**
     * �Զ�H
     */
    private boolean autoH;
    /**
     * �Զ�W
     */
    private boolean autoW;
    /**
     * �Զ�W�ߴ�
     */
    private int autoWidthSize;
    /**
     * �Զ�H�ߴ�
     */
    private int autoHeightSize;
    /**
     * ���ݳ���;
     */
    private String dbPoolName;
    
    /**
     * ������
     */
    private boolean required;

    /**
     * ������
     */
    public TComboBoxBase() {
        super(new TComboBoxModel());
        uiInit();
        //BasicComboBoxUI
        //com.sun.java.swing.plaf.windows.WindowsComboBoxUI a;
    }
    public class TComboPopup extends BasicComboPopup
    {
        public TComboPopup( JComboBox combo ) {
            super(combo);
        }
        protected Rectangle computePopupBounds(int px,int py,int pw,int ph) {
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Rectangle screenBounds;

            // Calculate the desktop dimensions relative to the combo box.
            GraphicsConfiguration gc = comboBox.getGraphicsConfiguration();
            Point p = new Point();
            SwingUtilities.convertPointFromScreen(p, comboBox);
            if (gc != null) {
                Insets screenInsets = toolkit.getScreenInsets(gc);
                screenBounds = gc.getBounds();
                screenBounds.width -= (screenInsets.left + screenInsets.right);
                screenBounds.height -= (screenInsets.top + screenInsets.bottom);
                screenBounds.x += (p.x + screenInsets.left);
                screenBounds.y += (p.y + screenInsets.top);
            }
            else {
                screenBounds = new Rectangle(p, toolkit.getScreenSize());
            }

            Rectangle rect = new Rectangle(px,py,pw,ph);
            if (py+ph > screenBounds.y+screenBounds.height
                && ph < screenBounds.height) {
                rect.y = -rect.height;
            }
            if(comboBox instanceof TComboBoxBase)
                rect.width += ((TComboBoxBase)comboBox).getExpandWidth();

            return rect;
        }
    }
    public class TComboBoxUI extends com.sun.java.swing.plaf.windows.WindowsComboBoxUI
    {
        protected ComboPopup createPopup() {
            return new TComboPopup( comboBox );
        }
    }
    public void updateUI() {
        setUI(new TComboBoxUI());
    }

    /**
     * �ڲ���ʼ��UI
     */
    protected void uiInit() {
        getModel().setComboBox(this);
        setRenderer(new TComboBoxRenderer(this));
        setEditor(new TComboBoxEditor(this));
        setEditable(true);
        setTFont(TUIStyle.getComboBoxDefaultFont());
        UIManager.put("TextField.inactiveForeground", TUIStyle.getInactiveForeground());
        updateUI();
    }
    /**
     * ����ѡ��ID
     * @param id String
     */
    public void setSelectedID(String id)
    {
        TComboNode node = getNodeForID(id);
        if(id == null ||node == null)
        {
            if(!canEdit())
                return;
            getComboEditor().setText(id);
            return;
        }
        oldSelectedNode = node;
        setSelectedNode(node);
    }
    /**
     * ����ѡ��Node
     * @param node TComboNode
     */
    public void setSelectedNode(TComboNode node)
    {
        setSelectedItem(node);
    }
    /**
     * ����ID����Node
     * @param id String
     * @return TComboNode
     */
    public TComboNode getNodeForID(String id)
    {
        if(id == null)
            return null;
        TComboBoxModel model = getModel();
        if(model == null)
            return null;;
        int count = model.getSize();
        for(int i = 0;i < count;i++)
        {
            TComboNode node = (TComboNode)model.getElementAt(i);
            if(node == null)
                continue;
            if(id.equalsIgnoreCase(node.getID()))
                return node;
        }
        return null;
    }
    /**
     * �õ�ѡ�е�ID
     * @return String
     */
    public String getSelectedID()
    {
        TComboNode node = getSelectedNode();
        if(node == null || node.getID() == null || node.getID().length() == 0)
        {
            if(canEdit())
                return getComboEditor().getText();
            return "";
        }
        return node.getID();
    }
    /**
     * �����ı�
     * @param text String
     */
    public void setText(String text)
    {
        setSelectedID(text);
    }
    /**
     * �õ��ı�
     * @return String
     */
    public String getText()
    {
        return getSelectedID();
    }
    /**
     * �õ�ѡ�е�����
     * @return String
     */
    public String getSelectedName()
    {
        TComboNode node = getSelectedNode();
        if(node == null)
            return "";
        return node.getName();
    }
    /**
     * �õ�ѡ�е��ı�
     * @return String
     */
    public String getSelectedText()
    {
        TComboNode node = getSelectedNode();
        if(node == null)
            return "";
        return node.getText();
    }
    /**
     * �õ�ѡ�е�ֵ
     * @return String
     */
    public String getSelectedValue()
    {
        TComboNode node = getSelectedNode();
        if(node == null)
            return "";
        return node.getValue();
    }
    /**
     * �õ�ѡ�е�����
     * @return String
     */
    public String getSelectedType()
    {
        TComboNode node = getSelectedNode();
        if(node == null)
            return "";
        return node.getType();
    }
    /**
     * �õ�ѡ�е�����
     * @return Object
     */
    public Object getSelectedData()
    {
        TComboNode node = getSelectedNode();
        if(node == null)
            return "";
        return node.getData();
    }

    /**
     * �õ�ѡ�еĽڵ����
     * @return TComboNode
     */
    public TComboNode getSelectedNode()
    {
        return (TComboNode)getSelectedItem();
    }
    /**
     * �õ�Model
     * @return TComboBoxModel
     */
    public TComboBoxModel getModel() {
        return (TComboBoxModel)dataModel;
    }
    /**
     * �õ�Renderer
     * @return TComboBoxRenderer
     */
    public TComboBoxRenderer getTRenderer() {
        return (TComboBoxRenderer)getRenderer();
    }

    /**
     * �����Ƿ���ʾID
     * @param showID boolean true ��ʾID false ����ʾID
     */
    public void setShowID(boolean showID)
    {
        this.showID = showID;
    }
    /**
     * �Ƿ���ʾID
     * @return boolean true ��ʾID false ����ʾID
     */
    public boolean isShowID()
    {
        return showID;
    }
    /**
     * �����Ƿ���ʾ����
     * @param showName boolean true ��ʾ���� false ����ʾ����
     */
    public void setShowName(boolean showName)
    {
        this.showName = showName;
    }
    /**
     * �Ƿ���ʾ����
     * @return boolean true ��ʾ���� false ����ʾ����
     */
    public boolean isShowName()
    {
        return showName;
    }
    /**
     * �����Ƿ���ʾ����
     * @param showText boolean true ��ʾ���� false ����ʾ����
     */
    public void setShowText(boolean showText)
    {
        this.showText = showText;
    }
    /**
     * �Ƿ���ʾ����
     * @return boolean true ��ʾ���� false ����ʾ����
     */
    public boolean isShowText()
    {
        return showText;
    }
    /**
     * �����Ƿ���ʾֵ
     * @param showValue boolean true ��ʾ���� false ����ʾ����
     */
    public void setShowValue(boolean showValue)
    {
        this.showValue = showValue;
    }
    /**
     * �Ƿ���ʾֵ
     * @return boolean true ��ʾ���� false ����ʾ����
     */
    public boolean isShowValue()
    {
        return showValue;
    }
    /**
     * �����Ƿ���ʾƴ��1
     * @param showPy1 boolean true ��ʾ���� false ����ʾ����
     */
    public void setShowPy1(boolean showPy1)
    {
        this.showPy1 = showPy1;
    }
    /**
     * �Ƿ���ʾƴ��1
     * @return boolean true ��ʾ���� false ����ʾ����
     */
    public boolean isShowPy1()
    {
        return showPy1;
    }
    /**
     * �����Ƿ���ʾƴ��1
     * @param showPy2 boolean true ��ʾ���� false ����ʾ����
     */
    public void setShowPy2(boolean showPy2)
    {
        this.showPy2 = showPy2;
    }
    /**
     * �Ƿ���ʾƴ��2
     * @return boolean true ��ʾ���� false ����ʾ����
     */
    public boolean isShowPy2()
    {
        return showPy2;
    }
    /**
     * ����ͼ���б�
     * @param pics String
     */
    public void setPics(String pics)
    {
        TComboBoxRenderer renderer = getTRenderer();
        if(renderer == null)
            return;
        renderer.setPics(pics);
    }
    /**
     * �õ�ͼ���б�
     * @return String
     */
    public String getPics()
    {
        TComboBoxRenderer renderer = getTRenderer();
        if(renderer == null)
            return null;
        return renderer.getPics();
    }
    /**
     * ���ò������ձ�
     * @param parmMap String
     */
    public void setParmMap(String parmMap)
    {
        this.parmMap = parmMap;
    }
    /**
     * �õ��������ձ�
     * @return String
     */
    public String getParmMap()
    {
        return parmMap;
    }
    /**
     * ����Table�����ʾ�б�
     * @param tableShowList String
     */
    public void setTableShowList(String tableShowList)
    {
        this.tableShowList = tableShowList;
    }
    /**
     * �õ�Table�����ʾ�ı�
     * @param value String
     * @return String
     */
    public String getTableShowValue(String value)
    {
        TComboNode node = getNodeForID(value);
        if(node == null)
            return value;
        String showList = getTableShowList();
        return node.getShowValue(showList,language);
    }
    /**
     * �õ�Table�����ʾ�ı�
     * @param value String
     * @param language String
     * @return String
     */
    public String getTableShowValue(String value,String language)
    {
        TComboNode node = getNodeForID(value);
        if(node == null)
            return value;
        String showList = getTableShowList();
        return node.getShowValue(showList,language);
    }
    /**
     * �õ�Table�����ʾ�б�
     * @return String
     */
    public String getTableShowList()
    {
        return tableShowList;
    }
    /**
     * �����ַ�����
     * @param stringData String
     */
    public void setStringData(String stringData)
    {
        this.stringData = stringData;
        setVectorData(StringTool.getVector(stringData));
        updateUI();
    }
    /**
     * �õ��ַ�����
     * @return String
     */
    public String stringData()
    {
        return stringData;
    }
    /**
     * ������Ŀֵ
     * @param data Vector
     */
    public void setVectorData(Vector data)
    {
        TComboBoxModel model = getModel();
        if(model == null)
            return;
        model.setVectorData(data);
    }
    /**
     * ������Ŀֵ
     * @param data String[]
     */
    public void setVectorData(String[] data)
    {
        Vector v = new Vector();
        Vector t = new Vector();
        t.add("id");
        t.add("text");
        v.add(t);
        t = new Vector();
        t.add("");
        t.add("");
        v.add(t);
        for(int i = 0;i < data.length;i++)
        {
            t = new Vector();
            t.add(data[i]);
            t.add(data[i]);
            v.add(t);
        }
        setVectorData(v);
        updateUI();
    }
    /**
     * ������Ŀֵ
     * @param parm TParm
     */
    public void setParmValue(TParm parm)
    {
        TComboBoxModel model = getModel();
        if (model == null)
            return;
        model.setTParmData(parm);
        updateUI();
    }
    /**
     * ��������
     * @param data String[]
     */
    public void setData(String data[])
    {
        setData(data,"");
    }
    /**
     * ��������
     * @param data String[]
     * @param fg String �ָ��
     */
    public void setData(String data[],String fg)
    {
        TComboBoxModel model = getModel();
        if (model == null)
            return;
        model.setData(data,fg);
        updateUI();
    }
    /**
     * ��������
     * @param data String
     * @param fg String
     */
    public void addData(String data,String fg)
    {
        TComboBoxModel model = getModel();
        if (model == null)
            return;
        model.addData(data,fg);
        updateUI();
    }
    public TComboBoxEditor getComboEditor()
    {
        return (TComboBoxEditor)getEditor();
    }
    public JTextField getEditorComponent()
    {
        return (JTextField)getComboEditor().getEditorComponent();
    }
    /**
     * �Ƿ�ı�¼���ı�
     * @return boolean
     */
    public boolean isModifiedText()
    {
        if(!isEditable())
            return false;
        return ((TComboBoxEditor)getEditor()).isModifiedText();
    }
    /**
     * ȫ��ѡ��
     */
    public void selectAll()
    {
        if(!isEditable())
            return;
        ((TComboBoxEditor)getEditor()).selectAll();
    }
    /**
     * �õ���ǩ
     * @return String
     */
    public String getTag() {
        return tag;
    }

    /**
     * ���ñ�ǩ
     * @param tag String
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * ���ü��ر�ǩ
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * �õ����ر�ǩ
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * ����X����
     * @param x int
     */
    public void setX(int x) {
        this.setLocation(x, getLocation().y);
    }

    /**
     * ����Y����
     * @param y int
     */
    public void setY(int y) {
        this.setLocation(getLocation().x, y);
    }

    /**
     * ���ÿ��
     * @param width int
     */
    public void setWidth(int width) {
        this.setSize(width, getSize().height);
    }

    /**
     * ���ø߶�
     * @param height int
     */
    public void setHeight(int height) {
        this.setSize(getSize().width, height);
    }
    /**
     * X����ƫ��
     * @param d int
     */
    public void setX$(int d)
    {
        if(d == 0)
            return;
        setX(getX() + d);
    }
    /**
     * Y����ƫ��
     * @param d int
     */
    public void setY$(int d)
    {
        if(d == 0)
            return;
        setY(getY() + d);
    }
    /**
     * ��������
     * @param d int
     */
    public void setX_$(int d)
    {
        setX$(d);
        setWidth$(-d);
    }
    /**
     * ��������
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$(-d);
    }
    /**
     * �������ƫ��
     * @param d int
     */
    public void setWidth$(int d)
    {
        if(d == 0)
            return;
        setWidth(getWidth() + d);
    }
    /**
     * �߶�����ƫ��
     * @param d int
     */
    public void setHeight$(int d)
    {
        if(d == 0)
            return;
        setHeight(getHeight() + d);
    }

    /**
     * ���ÿ��������
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null) {
            control.setComponent(this);
        }
    }

    /**
     * �õ����������
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * ���ö�����Ϣ
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * �õ�������Ϣ
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }
    /**
     * ����ѡ�ж�����Ϣ
     * @param selectedAction String
     */
    public void setSelectedAction(String selectedAction)
    {
        this.selectedAction = selectedAction;
    }
    /**
     * �õ�ѡ�ж�����Ϣ
     * @return String
     */
    public String getSelectedAction()
    {
        return selectedAction;
    }
    /**
     * �������ò�������
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * �õ����ò�������
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
        return configParm;
    }

    /**
     * �õ������¼�����
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param methodName String ������
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * ���Ӽ�������
     * @param eventName String �¼�����
     * @param object Object �������
     * @param methodName String ������
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * ɾ����������
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parms Object[] ����
     * @param parmType String[] ��������
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * ���з���
     * @param eventName String ������
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * ���з���
     * @param eventName String ������
     * @param parm Object ����
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},new String[] {"java.lang.Object"});
    }
    /**
     * �ͷż����¼�
     */
    public void releaseListeners()
    {
        removeMouseListener(mouseListenerObject);
        removeMouseMotionListener(mouseMotionListenerObject);
        removeActionListener(actionListenerObject);
        removeFocusListener(focusListenerObject);
        getBaseEventObject().release();
    }
    /**
     * ��ʼ�������¼�
     */
    public void initListeners()
    {
        //��������¼�
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //��������ƶ��¼�
        if(mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        //���������¼�
        /*if(keyListenerObject == null)
        {
            keyListenerObject = new TKeyListener(this);
            addKeyListener(keyListenerObject);
        }*/
        //���������¼�
        if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        //���������¼�
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        if(!isInitListener)
        {
            //����Mouse�¼�
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_PRESSED,"onMousePressed");
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_RELEASED,"onMouseReleased");
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_ENTERED,"onMouseEntered");
            addEventListener(getTag() + "->" + TMouseListener.MOUSE_EXITED,"onMouseExited");
            addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_DRAGGED,"onMouseDragged");
            addEventListener(getTag() + "->" + TMouseMotionListener.MOUSE_MOVED,"onMouseMoved");
            addEventListener(getTag() + "->" + TKeyListener.KEY_PRESSED,"onKeyPressed");
            addEventListener(getTag() + "->" + TKeyListener.KEY_RELEASED,"onKeyReleased");
            addEventListener(getTag() + "->" + TKeyListener.KEY_TYPE,"onKeyType");
            addEventListener(getTag() + "->" + TActionListener.ACTION_PERFORMED,"onActionPerformed");
            addEventListener(getTag() + "->" + TFocusListener.FOCUS_LOST,"onFocusLostAction");
            isInitListener = true;
        }
        TComponent component = getParentTComponent();
        if(component != null)
            component.callFunction("addEventListener",
                                   component.getTag() + "->" + TComponentListener.RESIZED,
                                   this,"onParentResize");
    }
    /**
     * ��������
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
    }
    /**
     * ����̧��
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }
    /**
     * ������
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }
    /**
     * ����뿪
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }
    /**
     * ����϶�
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
    }
    /**
     * ����ƶ�
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }
    /**
     * �����¼�
     * @param e ActionEvent
     */
    public void onActionPerformed(ActionEvent e)
    {
        final TComboNode node = getSelectedNode();
        if(oldSelectedNode == node && !canEdit())
            return;
        oldSelectedNode = node;
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                callEvent(TComboBoxEvent.SELECTED,new Object[]{},new String[]{});
                callMessage(getTag() + "->" + TComboBoxEvent.SELECTED,node);
            }
        });
    }
    /**
     * ���̰����¼�
     * @param e KeyEvent
     */
    public void onKeyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ENTER)
        {
            onKeyEnter(e);
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            onKeyESC(e);
            return;
        }
        if(e.getKeyCode() == KeyEvent.VK_TAB)
        {
            onKeyTab(e);
            return;
        }
    }
    /**
     * ʧȥ�����¼�
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        exeAction(getFocusLostAction());
    }
    /**
     * �õ��༭��Ŀ
     * @return TComboNode
     */
    public TComboNode getEditorItem()
    {
        return (TComboNode)((TComboBoxEditor)getEditor()).getItem();
    }
    /**
     * ���༭�����Ƿ�Ϸ�
     * @return boolean��true �Ϸ���false ���Ϸ�
     */
    public boolean checkEditEnd()
    {
        final String text = ((TComboBoxEditor)getEditor()).getText();
        TComboNode node = getEditorItem();
        if(node == null)
        {
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                    ((TComboBoxEditor)getEditor()).setText(text);
                    selectAll();
                }
            });
            return false;
        }
        setSelectedItem(node);
        return true;
    }
    public JTableBase tableBase;
    public JTableSortBase tableSortBase;
    /**
     * ���̻س�������
     * @param e KeyEvent
     */
    public void onKeyEnter(KeyEvent e)
    {
        if(!checkEditEnd())
            return;
        callEvent(getTag() + "->" + TComboBoxEvent.EDIT_ENTER,
                  new Object[]{},new String[]{});
        callMessage(getTag() + "->" + TComboBoxEvent.EDIT_ENTER,null);
        if(tableBase != null)
            tableBase.grabFocus();
        if(tableSortBase!=null)
        	tableSortBase.grabFocus();
    }
    /**
     * ����ESC����
     * @param e KeyEvent
     */
    public void onKeyESC(KeyEvent e)
    {
        callEvent(getTag() + "->" + TComboBoxEvent.EDIT_ESC,
                  new Object[]{},new String[]{});
        if(tableBase != null)
        {
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                    tableBase.grabFocus();
                }
            });

        }
        //
        if(tableSortBase != null)
        {
            SwingUtilities.invokeLater(new Runnable(){
                public void run()
                {
                	tableSortBase.grabFocus();
                }
            });

        }
        
    }
    /**
     * ����TAB����
     * @param e KeyEvent
     */
    public void onKeyTab(KeyEvent e)
    {
        if(!checkEditEnd())
            return;
        if(e.isShiftDown())
            callEvent(getTag() + "->" + TComboBoxEvent.EDIT_SHIFT_TAB,
                      new Object[]{},new String[]{});
        else
            callEvent(getTag() + "->" + TComboBoxEvent.EDIT_TAB,
                      new Object[]{},new String[]{});
        if(tableBase != null)
            tableBase.grabFocus();
        if(tableSortBase != null)
        	tableSortBase.grabFocus();
        
    }
    /**
     * ����̧���¼�
     * @param e KeyEvent
     */
    public void onKeyReleased(KeyEvent e)
    {
    }
    /**
     * ����¼���¼�
     * @param e KeyEvent
     */
    public void onKeyType(KeyEvent e)
    {

    }
    /**
     * ��ʼ��
     */
    public void onInit() {
        //��ʼ�������¼�
        initListeners();
        //��ʼ������׼��
        if (getControl() != null)
            getControl().onInitParameter();
        //ִ��Module
        onQuery();
        retrieve();
        //��ʼ��������
        if (getControl() != null)
            getControl().onInit();
    }
    /**
     * initialize
     * @param configParm TConfigParm
     */
    public void init(TConfigParm configParm) {
        if (configParm == null)
            return;
        setConfigParm(configParm);
        //new Thread(){
        //    public void run()
        //    {
                dynamicInit();
        //    }
        //}.start();
    }
    /**
     * ��̬����
     */
    private synchronized void dynamicInit()
    {
        initNow = true;
        //����ȫ������
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //���ؿ�����
        if (getControl() != null)
            getControl().init();
        initNow = false;
    }
    /**
     * ���˳�ʼ����Ϣ
     * @param message String
     * @return boolean
     */
    public boolean filterInit(String message) {
        String value[] = StringTool.getHead(message,"=");
        if("TYPE".equalsIgnoreCase(value[0]))
            return false;
        return true;
    }

    /**
     * ����˳��
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig,ParmMap,VectorData,TParmData,StringData,SelectedID,Text";
    }
    /**
     * ���з���
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        return callMessage(message,parameters);
    }

    /**
     * ��Ϣ����
     * @param message String ��Ϣ����
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * ������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //���������Ϣ
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //������������Ϣ
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //��Ϣ�ϴ�
        TComponent parentTComponent = getParentComponent();
        if(parentTComponent == null)
            parentTComponent = getParentTComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        //����Ĭ�϶���
        value = onDefaultActionMessage(message, parm);
        if (value != null)
            return value;
        return null;
    }

    /**
     * ִ��Ĭ�϶���
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object onDefaultActionMessage(String message, Object parm) {
        String value[] = StringTool.getHead(message, "->");
        if (value[0].equalsIgnoreCase(getTag()) &&
            value[1].equalsIgnoreCase(TComboBoxEvent.EDIT_ENTER)) {
            Object result = sendActionMessage(getActionMessage());
            if (result != null)
                return result;
            return callMessage("afterFocus|" + getTag());
        }
        if (value[0].equalsIgnoreCase(getTag()) &&
            value[1].equalsIgnoreCase(TComboBoxEvent.SELECTED)) {
            return sendActionMessage(getSelectedAction());
        }
        return null;
    }
    /**
     * ���Ͷ�����Ϣ
     * @param message String ��Ϣ
     * @return Object
     */
    public Object sendActionMessage(String message) {
        if (message == null || message.length() == 0)
            return null;
        String value[] = StringTool.parseLine(message, ';',
                                              "[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            callMessage(value[i]);
        }
        return "OK";
    }
    /**
     * �õ�����
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * �õ�����(�ݹ���)
     * @param container Container
     * @return TComponent
     */
    public TComponent getParentTComponent(Container container) {
        if (container == null)
            return null;
        if (container instanceof TComponent)
            return (TComponent) container;
        return getParentTComponent(container.getParent());
    }

    /**
     * �õ�ͬ���ķ���
     * @param name String
     * @return Method[]
     */
    public Method[] searchMethods(String name) {
        Method[] methods = getClass().getMethods();
        ArrayList list = new ArrayList();
        for (int i = 0; i < methods.length; i++) {
            Method m = methods[i];
            if (m.getName().equalsIgnoreCase(name))
                list.add(m);
        }
        return (Method[]) list.toArray(new Method[] {});
    }

    /**
     * ��������Ϣ
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //������
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //��������
        value = StringTool.getHead(message, "=");
        //����������������
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }

    /**
     * ����������������
     * @param value String[]
     */
    protected void baseFieldNameChange(String value[]) {
        if ("action".equalsIgnoreCase(value[0]))
            value[0] = "actionMessage";
        else if ("Key".equalsIgnoreCase(value[0]))
            value[0] = "globalkey";
        else if ("Tip".equalsIgnoreCase(value[0]))
            value[0] = "toolTipText";
    }

    /**
     * �����������
     * @param value String
     */
    public void setControlConfig(String value) {
        if (getControl() == null) {
            err("control is null");
            return;
        }
        getControl().setConfigParm(getConfigParm().newConfig(value));
    }

    /**
     * ���������������
     * @param controlClassName String
     */
    public void setControlClassName(String controlClassName) {
        this.controlClassName = controlClassName;
        if(controlClassName == null || controlClassName.length() == 0)
            return;
        if(controlClassName == null || controlClassName.length() == 0)
            return;
        Object obj = getConfigParm().loadObject(controlClassName);
        if (obj == null) {
            err("Class loadObject err className=" + controlClassName);
            return;
        }
        if (!(obj instanceof TControl)) {
            err("Class loadObject type err className=" + controlClassName +
                " is not TControl");
            return;
        }
        TControl control = (TControl) obj;
        control.setConfigParm(getConfigParm());
        setControl(control);
    }
    /**
     * �õ������������
     * @return String
     */
    public String getControlClassName()
    {
        return controlClassName;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getType()
    {
        return "TComboBoxBase";
    }
    /**
     * �õ��Լ�����
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * �������
     * @param tag String ��ǩ
     * @return TComponent
     */
    public TComponent findTComponent(String tag)
    {
        return (TComponent)callMessage(tag + "|getThis");
    }
    /**
     * ����ֵ
     * @param value Object
     */
    public void setValue(Object value)
    {
        setSelectedID(TCM_Transform.getString(value));
    }
    /**
     * �õ�ֵ
     * @return String
     */
    public String getValue()
    {
        return getSelectedID();
    }
    /**
     * ����Module�ļ���
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * �õ�Module�ļ���
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
    }
    /**
     * ����Module������
     * @param moduleMethodName String
     */
    public void setModuleMethodName(String moduleMethodName)
    {
        this.moduleMethodName = moduleMethodName;
    }
    /**
     * �õ�Module������
     * @return String
     */
    public String getModuleMethodName()
    {
        return moduleMethodName;
    }
    /**
     * ����Module����
     * @param moduleParm TParm
     */
    public void setModuleParm(TParm moduleParm)
    {
        this.moduleParm = moduleParm;
    }
    /**
     * �õ�Module����
     * @return TParm
     */
    public TParm getModuleParm()
    {
        return moduleParm;
    }
    /**
     * ����Module��̬����
     * @param moduleParmString String ����"ID:01;AA:<I>12"
     */
    public void setModuleParmString(String moduleParmString)
    {
        this.moduleParmString = moduleParmString;
    }
    /**
     * �õ�Module��̬����
     * @return String
     */
    public String getModuleParmString()
    {
        return moduleParmString;
    }
    /**
     * ����Module��̬����
     * @param moduleParmTag String "ID:String:T_ID:L;"
     */
    public void setModuleParmTag(String moduleParmTag)
    {
        this.moduleParmTag = moduleParmTag;
    }
    /**
     * �õ�Module��̬����
     * @return String
     */
    public String getModuleParmTag()
    {
        return moduleParmTag;
    }
    /**
     * ���Tag��̬����
     * @param parm TParm
     */
    private void checkModuleParmTag(TParm parm)
    {
        if(getModuleParmTag() == null || getModuleParmTag().length() == 0)
            return;
        String names[] = StringTool.parseLine(getModuleParmTag(),";");
        for(int i = 0; i < names.length;i++)
        {
            String v[] = StringTool.parseLine(names[i],":");
            if(v[0].length() == 0)
                continue;
            String name = v[0];
            String type = "String";
            String tag = name;
            String LRC = "";
            if(v.length > 1)
                type = v[1];
            if(v.length > 2)
                tag = v[2];
            if(v.length > 3)
                LRC = v[3];
            Object value = this.callMessage(tag + "|getValue");
            if("string".equalsIgnoreCase(type))
            {
                String sValue = TCM_Transform.getString(value);
                if(LRC.equalsIgnoreCase("L"))
                    sValue += "%";
                if(LRC.equalsIgnoreCase("R"))
                    sValue = "%" + sValue;
                if(LRC.equalsIgnoreCase("C"))
                    sValue = "%" + sValue + "%";
                parm.setData(name,sValue);
            }
            else if("int".equalsIgnoreCase(type))
                parm.setData(name,TCM_Transform.getInt(value));
            else if("double".equalsIgnoreCase(type))
                parm.setData(name, TCM_Transform.getDouble(value));
        }
    }
    /**
     * ִ��Module����
     */
    public void onQuery()
    {
        //if(!postQuery)
            //com.dongyang.util.DebugUsingTime.add("start");
            dynamicQuery();
        /*else
            new Thread(){
                public void run()
                {
                    queryNow = true;
                    dynamicQuery();
                    queryNow = false;
                }
            }.start();
         */
    }
    /**
     * �첽��ѯ
     */
    private synchronized void dynamicQuery()
    {
        if(getModuleName() == null || getModuleName().length() == 0)
            return;
        if(getModuleMethodName() == null || getModuleMethodName().length() == 0)
            return;
        TParm parm = getModuleParm();
        if(parm == null)
        {
            parm = StringTool.getParm(getModuleParmString());
            checkModuleParmTag(parm);
        }
        MaxLoad maxLoad = getMaxLoad();
        if(maxLoad != null)
        {
            MaxLoadInf inf = new MaxLoadInf();
            inf.setType(MaxLoadInf.COMBO);
            inf.setComponent(this);
            inf.setModuleName(getModuleName());
            inf.setMethodName(getModuleMethodName());
            inf.setInParm(parm);
            maxLoad.addInf(inf);
            return;
        }
        TJDOTool tool = new TJDOTool();
        tool.setModuleName(getModuleName());
        //$$==========modified by lx 2011-08-25 ֧�ֶ�����Դ START====================$$//
        TParm result =null;
        if(this.getDbPoolName()!=null&&!this.getDbPoolName().equals("")){
            result = tool.query( getModuleMethodName(), parm, this.getDbPoolName());
        }else{
            result = tool.query(getModuleMethodName(),parm);
        }
        //$$==========modified by lx 2011-08-25 ֧�ֶ�����Դ END====================$$//
        if(result == null)
        {
            err("result is null");
            return;
        }
        if(result.getErrCode() < 0)
        {
            err(result.getErrCode() + " " + result.getErrText() +
                getClass().getName() + " ModuleName=" + getModuleName() +
                " ModuleMethodName=" + getModuleMethodName());
            return;
        }
        setParmValue(result);
    }
    /**
     * �õ���ǩֵ
     * @param tag String
     * @return Object
     */
    public Object getTagValue(String tag)
    {
        if(!StringTool.isTag(tag))
            return tag;
        tag = StringTool.getTag(tag);
        Object value = callMessage(tag + "|getValue");
        if(value == null)
            return "";
        return value;
    }
    /**
     * ����ʧȥ���㶯��
     * @param focusLostAction String
     */
    public void setFocusLostAction(String focusLostAction)
    {
        this.focusLostAction = focusLostAction;
    }
    /**
     * �õ�ʧȥ���㶯��
     * @return String
     */
    public String getFocusLostAction()
    {
        return focusLostAction;
    }
    /**
     * ִ�ж���
     * @param message String ��Ϣ
     * @return Object
     */
    public Object exeAction(String message)
    {
        if (message == null || message.length() == 0)
            return null;
        String value[] = StringTool.parseLine(message, ';',"[]{}()''\"\"");
        for (int i = 0; i < value.length; i++) {
            String actionMessage = value[i];
            if ("call".equalsIgnoreCase(actionMessage))
                actionMessage = getText();
            callMessage(actionMessage);
        }
        return "OK";
    }
    /**
     * ������ѿ��
     * @param width int
     */
    public void setPreferredWidth(int width)
    {
        Dimension d = getPreferredSize();
        d.setSize(width,d.getHeight());
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }
    /**
     * ������Ѹ߶�
     * @param height int
     */
    public void setPreferredHeight(int height)
    {
        Dimension d = getPreferredSize();
        d.setSize(d.getWidth(),height);
        setPreferredSize(d);
        setMaximumSize(d);
        setMinimumSize(d);
    }
    /**
     * �������ݴ洢
     * @param dataStore TDataStore
     */
    public void setDataStore(TDataStore dataStore)
    {
        this.dataStore = dataStore;
    }
    /**
     * �õ����ݴ洢
     * @return TDataStore
     */
    public TDataStore getDataStore()
    {
        return dataStore;
    }
    /**
     * ����SQL
     * @param sql String
     */
    public void setSQL(String sql)
    {
        this.sql = sql;
    }
    /**
     * �õ�SQL
     * @return String
     */
    public String getSQL()
    {
        return sql;
    }
    /**
     * ��ȡ����
     * @return boolean
     */
    public boolean retrieve()
    {
        if (sql == null || sql.length() == 0)
            return false;
        if(getDataStore() == null)
            setDataStore(new TDataStore());
        if (!getDataStore().setSQL(sql))
            return false;
        if (getDataStore().retrieve() == -1)
            return false;
        if(getParmMap() == null || getParmMap().length() == 0)
        {
            if(getDataStore().getColumns().length >= 2)
                setParmMap("ID:" + getDataStore().getColumns()[0] + ";" +
                           "TEXT:" + getDataStore().getColumns()[1]);
            else
                setParmMap("ID:" + getDataStore().getColumns()[0] + ";" +
                           "TEXT:" + getDataStore().getColumns()[0]);

        }
        if(getDataStore().rowCount() > 0)
            setParmValue(getDataStore().getBuffer(TDataStore.PRIMARY));
        return true;
    }
    /**
     * �õ�������
     * @return int
     */
    public int rowCount()
    {
        return getModel().getSize();
    }
    /**
     * �õ���Ŀ
     * @param index int
     * @return TComboNode
     */
    public TComboNode getItem(int index)
    {
        Object obj = getModel().getElementAt(index);
        if(obj instanceof TComboNode)
            return (TComboNode)obj;
        return null;
    }
    /**
     * ������Ŀ
     * @param node TComboNode
     */
    public void addItem(TComboNode node)
    {
        getModel().addElement(node);
        updateUI();
    }
    /**
     * ����һ����Ŀ
     * @param node TComboNode
     * @param index int
     */
    public void insertItem(TComboNode node,int index)
    {
        getModel().insertElementAt(node,index);
        updateUI();
    }
    /**
     * ����չ�����
     * @param expandWidth int
     */
    public void setExpandWidth(int expandWidth)
    {
        this.expandWidth = expandWidth;
    }
    /**
     * �õ�չ�����
     * @return int
     */
    public int getExpandWidth()
    {
        return expandWidth;
    }
    /**
     * ���ø������
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent)
    {
        this.parentComponent = parentComponent;
    }
    /**
     * �õ��������
     * @return TComponent
     */
    public TComponent getParentComponent()
    {
        return parentComponent;
    }
    /**
     * �Ƿ����ڼ���
     * @return boolean
     */
    public boolean isInitNow()
    {
        return initNow;
    }
    /**
     * �Ƿ����ڲ�ѯ
     * @return boolean
     */
    public boolean isQueryNow()
    {
        return queryNow;
    }
    /**
     * �����첽��ѯ
     * @param postQuery boolean
     */
    public void setPostQuery(boolean postQuery)
    {
        this.postQuery = postQuery;
    }
    /**
     * �Ƿ��첽��ѯ
     * @return boolean
     */
    public boolean isPostQuery()
    {
        return postQuery;
    }
    /**
     * �����ܷ�༭
     * @param canEdit boolean
     */
    public void setCanEdit(boolean canEdit)
    {
        this.canEdit = canEdit;
    }
    /**
     * �Ƿ���Ա༭
     * @return boolean
     */
    public boolean canEdit()
    {
        return canEdit;
    }
    /**
     * �ͷ�
     */
    public void release()
    {
        //�ͷż���
        releaseListeners();
        //�ͷſ�����
        if (control != null)
        {
            control.release();
            control = null;
        }
        baseEvent = null;
        tag = null;
        loadtag = null;
        controlClassName = null;
        control = null;
        actionMessage = null;
        selectedAction = null;
        configParm = null;
        parmMap = null;
        tableShowList = null;
        mouseListenerObject = null;
        mouseMotionListenerObject = null;
        actionListenerObject = null;
        keyListenerObject = null;
        oldSelectedNode = null;
        stringData = null;
        moduleName = null;
        moduleMethodName = null;
        moduleParm = null;
        moduleParmString = null;
        moduleParmTag = null;
        focusListenerObject = null;
        focusLostAction = null;
        dataStore = null;
        sql = null;
        parentComponent = null;

        //�ͷ�����
        RunClass.release(this);
    }
    /**
     * ��������
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getTFont();
        setTFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * �õ�����
     * @return String
     */
    public String getFontName()
    {
        return getTFont().getFontName();
    }
    /**
     * ��������ߴ�
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * �õ�����ߴ�
     * @return int
     */
    public int getFontSize()
    {
        return getTFont().getSize();
    }
    /**
     * ������������
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * �õ���������
     * @return int
     */
    public int getFontStyle()
    {
        return getTFont().getStyle();
    }
    /**
     * ��������
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * �õ�����
     * @return Font
     */
    public Font getFont() {
        Font f = getTFont();
        if(f == null)
            return super.getFont();
        String language = (String)TSystem.getObject("Language");
        if(language == null)
            return f;
        if("zh".equals(language))
        {
            Object obj = TSystem.getObject("ZhFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        if("en".equals(language))
        {
            Object obj = TSystem.getObject("EnFontSizeProportion");
            if(obj == null)
                return f;
            double d = (Double)obj;
            if(d == 1)
                return f;
            int size = (int)((double)f.getSize() * d + 0.5);
            return new Font(f.getFontName(),f.getStyle(),size);
        }
        return f;
    }
    /**
     * ���ñ�����ɫ
     * @param color String
     */
    public void setBKColor(String color) {
        if(color == null || color.length() == 0)
            return;
        Color color1 = StringTool.getColor(color, getConfigParm());
        setBackground(color1);
        getEditor().getEditorComponent().setBackground(color1);
    }
    /**
     * ����������ɫ
     * @param color String
     */
    public void setColor(String color)
    {
        if(color == null || color.length() == 0)
            return;
        Color color1 = StringTool.getColor(color, getConfigParm());
        setForeground(color1);
        getEditor().getEditorComponent().setForeground(color1);
    }
    /**
     * ���ñ߿�
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }
    /**
     * ��������Tip
     * @param zhTip String
     */
    public void setZhTip(String zhTip)
    {
        this.zhTip = zhTip;
    }
    /**
     * �õ�����Tip
     * @return String
     */
    public String getZhTip()
    {
        return zhTip;
    }
    /**
     * ����Ӣ��Tip
     * @param enTip String
     */
    public void setEnTip(String enTip)
    {
        this.enTip = enTip;
    }
    /**
     * �õ�Ӣ��Tip
     * @return String
     */
    public String getEnTip()
    {
        return enTip;
    }
    /**
     * �õ�����
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if(language == null)
            return;
        if(language.equals(this.language))
            return;
        this.language = language;

        if("en".equals(language) && getEnTip() != null && getEnTip().length() > 0)
            setToolTipText(getEnTip());
        else if(getZhTip() != null && getZhTip().length() > 0)
            setToolTipText(getZhTip());

        if(oldSelectedNode == null)
            return;
        getComboEditor().setLanguageText(oldSelectedNode);
    }
    /**
     * ������־���
     * @param text String ��־����
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * �õ��̳м�����
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
    }
    /**
     * ���þ���
     * @param isAutoCenter boolean
     */
    public void setAutoCenter(boolean isAutoCenter)
    {
        this.isAutoCenter = isAutoCenter;
    }
    /**
     * �Ƿ����
     * @return boolean
     */
    public boolean isAutoCenter()
    {
        return isAutoCenter;
    }
    /**
     * �����Զ�X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
    }
    /**
     * �Ƿ����Զ�X
     * @return boolean
     */
    public boolean isAutoX()
    {
        return autoX;
    }
    /**
     * �����Զ�Y
     * @param autoY boolean
     */
    public void setAutoY(boolean autoY)
    {
        this.autoY = autoY;
    }
    /**
     * �Ƿ����Զ�Y
     * @return boolean
     */
    public boolean isAutoY()
    {
        return autoY;
    }
    /**
     * �����Զ����
     * @param autoWidth boolean
     */
    public void setAutoWidth(boolean autoWidth)
    {
        this.autoWidth = autoWidth;
    }
    /**
     * �Ƿ����Զ����
     * @return boolean
     */
    public boolean isAutoWidth()
    {
        return autoWidth;
    }
    /**
     * �����Զ��߶�
     * @param autoHeight boolean
     */
    public void setAutoHeight(boolean autoHeight)
    {
        this.autoHeight = autoHeight;
    }
    /**
     * �Ƿ����Զ��߶�
     * @return boolean
     */
    public boolean isAutoHeight()
    {
        return autoHeight;
    }
    /**
     * �����Զ�H
     * @param autoH boolean
     */
    public void setAutoH(boolean autoH)
    {
        this.autoH = autoH;
    }
    /**
     * �Ƿ����Զ�H
     * @return boolean
     */
    public boolean isAutoH()
    {
        return autoH;
    }
    /**
     * �����Զ�H
     * @param autoW boolean
     */
    public void setAutoW(boolean autoW)
    {
        this.autoW = autoW;
    }
    /**
     * �Ƿ����Զ�W
     * @return boolean
     */
    public boolean isAutoW()
    {
        return autoW;
    }
    /**
     * �����Զ��ߴ�
     * @param autoSize int
     */
    public void setAutoSize(int autoSize)
    {
        this.autoSize = autoSize;
    }
    /**
     * �õ��Զ��ߴ�
     * @return int
     */
    public int getAutoSize()
    {
        return autoSize;
    }
    /**
     * �����Զ���ȳߴ�
     * @param autoWidthSize int
     */
    public void setAutoWidthSize(int autoWidthSize)
    {
        this.autoWidthSize = autoWidthSize;
    }
    /**
     * �õ��Զ���ȳߴ�
     * @return int
     */
    public int getAutoWidthSize()
    {
        return autoWidthSize;
    }
    /**
     * �����Զ��߶ȳߴ�
     * @param autoHeightSize int
     */
    public void setAutoHeightSize(int autoHeightSize)
    {
        this.autoHeightSize = autoHeightSize;
    }
    /**
     * �õ��Զ��߶ȳߴ�
     * @return int
     */
    public int getAutoHeightSize()
    {
        return autoHeightSize;
    }

    /**
     * �������ݳ���
     * @param dbPoolName String
     */
    public void setDbPoolName(String dbPoolName)
    {
        this.dbPoolName = dbPoolName;
    }

    /**
     * �õ����ݳ���
     * @return String
     */
    public String getDbPoolName() {
        return dbPoolName;
    }
    /**
     * 
     * @return
     */
    public boolean isRequired() {
		return required;
	}
    
    /**
     * 
     * @param required
     */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
     * �������ߴ�ı�
     * @param width int
     * @param height int
     */
    public void onParentResize(int width,int height)
    {
        Container c = getParent();
        if(c == null)
            return;
        Insets insets = c.getInsets();
        if(isAutoCenter())
        {
            int w = c.getWidth() - insets.left - insets.right;
            int h = c.getHeight() - insets.top - insets.bottom;
            int x = (w - getWidth()) / 2;
            int y = (h - getHeight()) / 2;
            this.setLocation(x,y);
            return;
        }
        if(isAutoW())
        {
            int w = c.getWidth();
            setX((w == 0?height:w) - insets.right - getAutoSize() - getWidth() - getAutoWidthSize());
        }
        if(isAutoH())
        {
            int h = c.getHeight();
            setY((h == 0?height:h) - insets.bottom - getAutoSize() - getHeight() - getAutoHeightSize());
        }
        if(!isAutoW() && isAutoX())
            setX(insets.left + getAutoSize());
        if(!isAutoH() && isAutoY())
            setY(insets.top + getAutoSize());
        if(!isAutoW() && isAutoWidth())
        {
            int w = c.getWidth();
            setWidth((w == 0?width:w) - insets.right - getX() - getAutoSize() - getAutoWidthSize());
        }
        if(!isAutoH() && isAutoHeight())
        {
            int h = c.getHeight();
            setHeight((h == 0?height:h) - insets.bottom - getY() - getAutoSize() - getAutoHeightSize());
        }
    }
}
