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
     * 基础事件
     */
    private BaseEvent baseEvent = new BaseEvent();
    /**
     * 标签
     */
    private String tag = "";
    /**
     * 加载标签
     */
    private String loadtag;
    /**
     * 控制类名
     */
    private String controlClassName;
    /**
     * 控制类
     */
    private TControl control;
    /**
     * 动作消息
     */
    private String actionMessage;
    /**
     * 选中动作消息
     */
    private String selectedAction;
    /**
     * 配置参数对象
     */
    private TConfigParm configParm;
    /**
     * 显示ID
     */
    private boolean showID = true;
    /**
     * 显示名称
     */
    private boolean showName = false;
    /**
     * 显示文本
     */
    private boolean showText = true;
    /**
     * 显示值
     */
    private boolean showValue = false;
    /**
     * 显示拼音1
     */
    private boolean showPy1 = false;
    /**
     * 显示拼音2
     */
    private boolean showPy2 = false;
    /**
     * 参数对照表
     */
    private String parmMap;
    /**
     * Table 组建显示列表
     */
    private String tableShowList;
    /**
     * 鼠标事件监听对象
     */
    private TMouseListener mouseListenerObject;
    /**
     * 鼠标移动监听对象
     */
    private TMouseMotionListener mouseMotionListenerObject;
    /**
     * 动作监听对象
     */
    private TActionListener actionListenerObject;
    /**
     * 键盘监听对象
     */
    private TKeyListener keyListenerObject;
    /**
     * 是否初始化事件监听
     */
    private boolean isInitListener;
    /**
     * 上次选中标签
     */
    private TComboNode oldSelectedNode;
    /**
     * 字符数据
     */
    private String stringData;
    /**
     * Module文件名
     */
    private String moduleName;
    /**
     * Module方法名
     */
    private String moduleMethodName;
    /**
     * Module参数
     */
    private TParm moduleParm;
    /**
     * Module静态参数
     */
    private String moduleParmString;
    /**
     * Module动态参数
     */
    private String moduleParmTag;
    /**
     * 焦点事件监听对象
     */
    private TFocusListener focusListenerObject;
    /**
     * 失去焦点动作
     */
    private String focusLostAction;
    /**
     * 数据存储
     */
    private TDataStore dataStore;
    /**
     * SQL语句
     */
    private String sql;
    /**
     * 展开宽度
     */
    private int expandWidth;
    /**
     * 父类组件
     */
    private TComponent parentComponent;
    /**
     * 正在加载数据
     */
    private boolean initNow;
    /**
     * 正在查询
     */
    private boolean queryNow;
    /**
     * 异步查询
     */
    private boolean postQuery;
    /**
     * 能否编辑
     */
    private boolean canEdit;
    /**
     * 语种
     */
    private String language;
    /**
     * 中文Tip
     */
    private String zhTip;
    /**
     * 英文Tip
     */
    private String enTip;
    /**
     * 字体
     */
    private Font tfont;
    /**
     * 居中
     */
    private boolean isAutoCenter;
    /**
     * 自动尺寸
     */
    private int autoSize = 5;
    /**
     * 自动X
     */
    private boolean autoX;
    /**
     * 自动Y
     */
    private boolean autoY;
    /**
     * 自动宽度
     */
    private boolean autoWidth;
    /**
     * 自动高度
     */
    private boolean autoHeight;
    /**
     * 自动H
     */
    private boolean autoH;
    /**
     * 自动W
     */
    private boolean autoW;
    /**
     * 自动W尺寸
     */
    private int autoWidthSize;
    /**
     * 自动H尺寸
     */
    private int autoHeightSize;
    /**
     * 数据池名;
     */
    private String dbPoolName;
    
    /**
     * 必填项
     */
    private boolean required;

    /**
     * 构造器
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
     * 内部初始化UI
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
     * 设置选中ID
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
     * 设置选中Node
     * @param node TComboNode
     */
    public void setSelectedNode(TComboNode node)
    {
        setSelectedItem(node);
    }
    /**
     * 根据ID查找Node
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
     * 得到选中的ID
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
     * 设置文本
     * @param text String
     */
    public void setText(String text)
    {
        setSelectedID(text);
    }
    /**
     * 得到文本
     * @return String
     */
    public String getText()
    {
        return getSelectedID();
    }
    /**
     * 得到选中的名称
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
     * 得到选中的文本
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
     * 得到选中的值
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
     * 得到选中的类型
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
     * 得到选中的数据
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
     * 得到选中的节点对象
     * @return TComboNode
     */
    public TComboNode getSelectedNode()
    {
        return (TComboNode)getSelectedItem();
    }
    /**
     * 得到Model
     * @return TComboBoxModel
     */
    public TComboBoxModel getModel() {
        return (TComboBoxModel)dataModel;
    }
    /**
     * 得到Renderer
     * @return TComboBoxRenderer
     */
    public TComboBoxRenderer getTRenderer() {
        return (TComboBoxRenderer)getRenderer();
    }

    /**
     * 设置是否显示ID
     * @param showID boolean true 显示ID false 不显示ID
     */
    public void setShowID(boolean showID)
    {
        this.showID = showID;
    }
    /**
     * 是否显示ID
     * @return boolean true 显示ID false 不显示ID
     */
    public boolean isShowID()
    {
        return showID;
    }
    /**
     * 设置是否显示名称
     * @param showName boolean true 显示名称 false 不显示名称
     */
    public void setShowName(boolean showName)
    {
        this.showName = showName;
    }
    /**
     * 是否显示名称
     * @return boolean true 显示名称 false 不显示名称
     */
    public boolean isShowName()
    {
        return showName;
    }
    /**
     * 设置是否显示文字
     * @param showText boolean true 显示名称 false 不显示名称
     */
    public void setShowText(boolean showText)
    {
        this.showText = showText;
    }
    /**
     * 是否显示文字
     * @return boolean true 显示名称 false 不显示名称
     */
    public boolean isShowText()
    {
        return showText;
    }
    /**
     * 设置是否显示值
     * @param showValue boolean true 显示名称 false 不显示名称
     */
    public void setShowValue(boolean showValue)
    {
        this.showValue = showValue;
    }
    /**
     * 是否显示值
     * @return boolean true 显示名称 false 不显示名称
     */
    public boolean isShowValue()
    {
        return showValue;
    }
    /**
     * 设置是否显示拼音1
     * @param showPy1 boolean true 显示名称 false 不显示名称
     */
    public void setShowPy1(boolean showPy1)
    {
        this.showPy1 = showPy1;
    }
    /**
     * 是否显示拼音1
     * @return boolean true 显示名称 false 不显示名称
     */
    public boolean isShowPy1()
    {
        return showPy1;
    }
    /**
     * 设置是否显示拼音1
     * @param showPy2 boolean true 显示名称 false 不显示名称
     */
    public void setShowPy2(boolean showPy2)
    {
        this.showPy2 = showPy2;
    }
    /**
     * 是否显示拼音2
     * @return boolean true 显示名称 false 不显示名称
     */
    public boolean isShowPy2()
    {
        return showPy2;
    }
    /**
     * 设置图标列表
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
     * 得到图标列表
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
     * 设置参数对照表
     * @param parmMap String
     */
    public void setParmMap(String parmMap)
    {
        this.parmMap = parmMap;
    }
    /**
     * 得到参数对照表
     * @return String
     */
    public String getParmMap()
    {
        return parmMap;
    }
    /**
     * 设置Table组件显示列表
     * @param tableShowList String
     */
    public void setTableShowList(String tableShowList)
    {
        this.tableShowList = tableShowList;
    }
    /**
     * 得到Table组件显示文本
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
     * 得到Table组件显示文本
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
     * 得到Table组件显示列表
     * @return String
     */
    public String getTableShowList()
    {
        return tableShowList;
    }
    /**
     * 设置字符数据
     * @param stringData String
     */
    public void setStringData(String stringData)
    {
        this.stringData = stringData;
        setVectorData(StringTool.getVector(stringData));
        updateUI();
    }
    /**
     * 得到字符数据
     * @return String
     */
    public String stringData()
    {
        return stringData;
    }
    /**
     * 设置项目值
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
     * 设置项目值
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
     * 设置项目值
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
     * 设置数据
     * @param data String[]
     */
    public void setData(String data[])
    {
        setData(data,"");
    }
    /**
     * 设置数据
     * @param data String[]
     * @param fg String 分割符
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
     * 增加数据
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
     * 是否改变录入文本
     * @return boolean
     */
    public boolean isModifiedText()
    {
        if(!isEditable())
            return false;
        return ((TComboBoxEditor)getEditor()).isModifiedText();
    }
    /**
     * 全部选蓝
     */
    public void selectAll()
    {
        if(!isEditable())
            return;
        ((TComboBoxEditor)getEditor()).selectAll();
    }
    /**
     * 得到标签
     * @return String
     */
    public String getTag() {
        return tag;
    }

    /**
     * 设置标签
     * @param tag String
     */
    public void setTag(String tag) {
        this.tag = tag;
    }

    /**
     * 设置加载标签
     * @param loadtag String
     */
    public void setLoadTag(String loadtag) {
        this.loadtag = loadtag;
    }

    /**
     * 得到加载标签
     * @return String
     */
    public String getLoadTag() {
        if (loadtag != null)
            return loadtag;
        return getTag();
    }

    /**
     * 设置X坐标
     * @param x int
     */
    public void setX(int x) {
        this.setLocation(x, getLocation().y);
    }

    /**
     * 设置Y坐标
     * @param y int
     */
    public void setY(int y) {
        this.setLocation(getLocation().x, y);
    }

    /**
     * 设置宽度
     * @param width int
     */
    public void setWidth(int width) {
        this.setSize(width, getSize().height);
    }

    /**
     * 设置高度
     * @param height int
     */
    public void setHeight(int height) {
        this.setSize(getSize().width, height);
    }
    /**
     * X坐标偏移
     * @param d int
     */
    public void setX$(int d)
    {
        if(d == 0)
            return;
        setX(getX() + d);
    }
    /**
     * Y坐标偏移
     * @param d int
     */
    public void setY$(int d)
    {
        if(d == 0)
            return;
        setY(getY() + d);
    }
    /**
     * 向左生长
     * @param d int
     */
    public void setX_$(int d)
    {
        setX$(d);
        setWidth$(-d);
    }
    /**
     * 向上升上
     * @param d int
     */
    public void setY_$(int d)
    {
        setY$(d);
        setHeight$(-d);
    }
    /**
     * 宽度坐标偏移
     * @param d int
     */
    public void setWidth$(int d)
    {
        if(d == 0)
            return;
        setWidth(getWidth() + d);
    }
    /**
     * 高度坐标偏移
     * @param d int
     */
    public void setHeight$(int d)
    {
        if(d == 0)
            return;
        setHeight(getHeight() + d);
    }

    /**
     * 设置控制类对象
     * @param control TControl
     */
    public void setControl(TControl control) {
        this.control = control;
        if (control != null) {
            control.setComponent(this);
        }
    }

    /**
     * 得到控制类对象
     * @return TControl
     */
    public TControl getControl() {
        return control;
    }

    /**
     * 设置动作消息
     * @param actionMessage String
     */
    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    /**
     * 得到动作消息
     * @return String
     */
    public String getActionMessage() {
        return actionMessage;
    }
    /**
     * 设置选中动作消息
     * @param selectedAction String
     */
    public void setSelectedAction(String selectedAction)
    {
        this.selectedAction = selectedAction;
    }
    /**
     * 得到选中动作消息
     * @return String
     */
    public String getSelectedAction()
    {
        return selectedAction;
    }
    /**
     * 设置配置参数对象
     * @param configParm TConfigParm
     */
    public void setConfigParm(TConfigParm configParm) {
        this.configParm = configParm;
    }

    /**
     * 得到配置参数对象
     * @return TConfigParm
     */
    public TConfigParm getConfigParm() {
        return configParm;
    }

    /**
     * 得到基础事件对象
     * @return BaseEvent
     */
    public BaseEvent getBaseEventObject() {
        return baseEvent;
    }
    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName,String methodName)
    {
        addEventListener(eventName,this,methodName);
    }
    /**
     * 增加监听方法
     * @param eventName String 事件名称
     * @param object Object 处理对象
     * @param methodName String 处理方法
     */
    public void addEventListener(String eventName, Object object,
                                 String methodName) {
        getBaseEventObject().add(eventName, object, methodName);
    }

    /**
     * 删除监听方法
     * @param eventName String
     * @param object Object
     * @param methodName String
     */
    public void removeEventListener(String eventName, Object object,
                                    String methodName) {
        getBaseEventObject().remove(eventName, object, methodName);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parms Object[] 参数
     * @param parmType String[] 参数类型
     * @return Object
     */
    public Object callEvent(String eventName, Object[] parms, String[] parmType) {
        return getBaseEventObject().callEvent(eventName, parms, parmType);
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @return Object
     */
    public Object callEvent(String eventName) {
        return callEvent(eventName, new Object[] {}, new String[] {});
    }

    /**
     * 呼叫方法
     * @param eventName String 方法名
     * @param parm Object 参数
     * @return Object
     */
    public Object callEvent(String eventName, Object parm) {
        return callEvent(eventName, new Object[] {parm},new String[] {"java.lang.Object"});
    }
    /**
     * 释放监听事件
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
     * 初始化监听事件
     */
    public void initListeners()
    {
        //监听鼠标事件
        if(mouseListenerObject == null)
        {
            mouseListenerObject = new TMouseListener(this);
            addMouseListener(mouseListenerObject);
        }
        //监听鼠标移动事件
        if(mouseMotionListenerObject == null)
        {
            mouseMotionListenerObject = new TMouseMotionListener(this);
            addMouseMotionListener(mouseMotionListenerObject);
        }
        //监听键盘事件
        /*if(keyListenerObject == null)
        {
            keyListenerObject = new TKeyListener(this);
            addKeyListener(keyListenerObject);
        }*/
        //监听动作事件
        if(actionListenerObject == null)
        {
            actionListenerObject = new TActionListener(this);
            addActionListener(actionListenerObject);
        }
        //监听焦点事件
        if(focusListenerObject == null)
        {
            focusListenerObject = new TFocusListener(this);
            addFocusListener(focusListenerObject);
        }
        if(!isInitListener)
        {
            //监听Mouse事件
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
     * 鼠标键按下
     * @param e MouseEvent
     */
    public void onMousePressed(MouseEvent e)
    {
    }
    /**
     * 鼠标键抬起
     * @param e MouseEvent
     */
    public void onMouseReleased(MouseEvent e)
    {
    }
    /**
     * 鼠标进入
     * @param e MouseEvent
     */
    public void onMouseEntered(MouseEvent e)
    {
    }
    /**
     * 鼠标离开
     * @param e MouseEvent
     */
    public void onMouseExited(MouseEvent e)
    {
    }
    /**
     * 鼠标拖动
     * @param e MouseEvent
     */
    public void onMouseDragged(MouseEvent e)
    {
    }
    /**
     * 鼠标移动
     * @param e MouseEvent
     */
    public void onMouseMoved(MouseEvent e)
    {
    }
    /**
     * 动作事件
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
     * 键盘按键事件
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
     * 失去焦点事件
     * @param e FocusEvent
     */
    public void onFocusLostAction(FocusEvent e)
    {
        exeAction(getFocusLostAction());
    }
    /**
     * 得到编辑项目
     * @return TComboNode
     */
    public TComboNode getEditorItem()
    {
        return (TComboNode)((TComboBoxEditor)getEditor()).getItem();
    }
    /**
     * 检测编辑内容是否合法
     * @return boolean　true 合法　false 不合法
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
     * 键盘回车键按下
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
     * 键盘ESC按下
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
     * 键盘TAB按下
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
     * 键盘抬键事件
     * @param e KeyEvent
     */
    public void onKeyReleased(KeyEvent e)
    {
    }
    /**
     * 键盘录入事件
     * @param e KeyEvent
     */
    public void onKeyType(KeyEvent e)
    {

    }
    /**
     * 初始化
     */
    public void onInit() {
        //初始化监听事件
        initListeners();
        //初始化参数准备
        if (getControl() != null)
            getControl().onInitParameter();
        //执行Module
        onQuery();
        retrieve();
        //初始化控制类
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
     * 动态加载
     */
    private synchronized void dynamicInit()
    {
        initNow = true;
        //加载全部属性
        String value[] = configParm.getConfig().getTagList(configParm.
                getSystemGroup(), getLoadTag(), getDownLoadIndex());
        for (int index = 0; index < value.length; index++)
            if (filterInit(value[index]))
                callMessage(value[index], configParm);
        //加载控制类
        if (getControl() != null)
            getControl().init();
        initNow = false;
    }
    /**
     * 过滤初始化信息
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
     * 加载顺序
     * @return String
     */
    protected String getDownLoadIndex() {
        return "ControlClassName,ControlConfig,ParmMap,VectorData,TParmData,StringData,SelectedID,Text";
    }
    /**
     * 呼叫方法
     * @param message String
     * @param parameters Object[]
     * @return Object
     */
    public Object callFunction(String message,Object ... parameters)
    {
        return callMessage(message,parameters);
    }

    /**
     * 消息处理
     * @param message String 消息处理
     * @return Object
     */
    public Object callMessage(String message) {
        return callMessage(message, null);
    }

    /**
     * 处理消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    public Object callMessage(String message, Object parm) {
        if (message == null || message.length() == 0)
            return null;
        //处理基本消息
        Object value = onBaseMessage(message, parm);
        if (value != null)
            return value;
        //处理控制类的消息
        if (getControl() != null) {
            value = getControl().callMessage(message, parm);
            if (value != null)
                return value;
        }
        //消息上传
        TComponent parentTComponent = getParentComponent();
        if(parentTComponent == null)
            parentTComponent = getParentTComponent();
        if (parentTComponent != null) {
            value = parentTComponent.callMessage(message, parm);
            if (value != null)
                return value;
        }
        //启动默认动作
        value = onDefaultActionMessage(message, parm);
        if (value != null)
            return value;
        return null;
    }

    /**
     * 执行默认动作
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
     * 发送动作消息
     * @param message String 消息
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
     * 得到父类
     * @return TComponent
     */
    public TComponent getParentTComponent() {
        return getParentTComponent(getParent());
    }

    /**
     * 得到父类(递归用)
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
     * 得到同名的方法
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
     * 基础类消息
     * @param message String
     * @param parm Object
     * @return Object
     */
    protected Object onBaseMessage(String message, Object parm) {
        if (message == null)
            return null;
        //处理方法
        String value[] = StringTool.getHead(message, "|");
        Object result = null;
        if ((result = RunClass.invokeMethodT(this, value, parm)) != null)
            return result;
        //处理属性
        value = StringTool.getHead(message, "=");
        //重新命名属性名称
        baseFieldNameChange(value);
        if ((result = RunClass.invokeFieldT(this, value, parm)) != null)
            return result;
        return null;
    }

    /**
     * 重新命名属性名称
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
     * 设置组件配置
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
     * 设置组件控制类名
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
     * 得到组件控制类名
     * @return String
     */
    public String getControlClassName()
    {
        return controlClassName;
    }
    /**
     * 得到类型
     * @return String
     */
    public String getType()
    {
        return "TComboBoxBase";
    }
    /**
     * 得到自己对象
     * @return TComponent
     */
    public TComponent getThis()
    {
        return this;
    }
    /**
     * 查找组件
     * @param tag String 标签
     * @return TComponent
     */
    public TComponent findTComponent(String tag)
    {
        return (TComponent)callMessage(tag + "|getThis");
    }
    /**
     * 设置值
     * @param value Object
     */
    public void setValue(Object value)
    {
        setSelectedID(TCM_Transform.getString(value));
    }
    /**
     * 得到值
     * @return String
     */
    public String getValue()
    {
        return getSelectedID();
    }
    /**
     * 设置Module文件名
     * @param moduleName String
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }
    /**
     * 得到Module文件名
     * @return String
     */
    public String getModuleName()
    {
        return moduleName;
    }
    /**
     * 设置Module方法名
     * @param moduleMethodName String
     */
    public void setModuleMethodName(String moduleMethodName)
    {
        this.moduleMethodName = moduleMethodName;
    }
    /**
     * 得到Module方法名
     * @return String
     */
    public String getModuleMethodName()
    {
        return moduleMethodName;
    }
    /**
     * 设置Module参数
     * @param moduleParm TParm
     */
    public void setModuleParm(TParm moduleParm)
    {
        this.moduleParm = moduleParm;
    }
    /**
     * 得到Module参数
     * @return TParm
     */
    public TParm getModuleParm()
    {
        return moduleParm;
    }
    /**
     * 设置Module静态参数
     * @param moduleParmString String 参数"ID:01;AA:<I>12"
     */
    public void setModuleParmString(String moduleParmString)
    {
        this.moduleParmString = moduleParmString;
    }
    /**
     * 得到Module静态参数
     * @return String
     */
    public String getModuleParmString()
    {
        return moduleParmString;
    }
    /**
     * 设置Module动态参数
     * @param moduleParmTag String "ID:String:T_ID:L;"
     */
    public void setModuleParmTag(String moduleParmTag)
    {
        this.moduleParmTag = moduleParmTag;
    }
    /**
     * 得到Module动态参数
     * @return String
     */
    public String getModuleParmTag()
    {
        return moduleParmTag;
    }
    /**
     * 检测Tag动态参数
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
     * 执行Module动作
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
     * 异步查询
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
        //$$==========modified by lx 2011-08-25 支持多数据源 START====================$$//
        TParm result =null;
        if(this.getDbPoolName()!=null&&!this.getDbPoolName().equals("")){
            result = tool.query( getModuleMethodName(), parm, this.getDbPoolName());
        }else{
            result = tool.query(getModuleMethodName(),parm);
        }
        //$$==========modified by lx 2011-08-25 支持多数据源 END====================$$//
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
     * 得到标签值
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
     * 设置失去焦点动作
     * @param focusLostAction String
     */
    public void setFocusLostAction(String focusLostAction)
    {
        this.focusLostAction = focusLostAction;
    }
    /**
     * 得到失去焦点动作
     * @return String
     */
    public String getFocusLostAction()
    {
        return focusLostAction;
    }
    /**
     * 执行动作
     * @param message String 消息
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
     * 设置最佳宽度
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
     * 设置最佳高度
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
     * 设置数据存储
     * @param dataStore TDataStore
     */
    public void setDataStore(TDataStore dataStore)
    {
        this.dataStore = dataStore;
    }
    /**
     * 得到数据存储
     * @return TDataStore
     */
    public TDataStore getDataStore()
    {
        return dataStore;
    }
    /**
     * 设置SQL
     * @param sql String
     */
    public void setSQL(String sql)
    {
        this.sql = sql;
    }
    /**
     * 得到SQL
     * @return String
     */
    public String getSQL()
    {
        return sql;
    }
    /**
     * 读取数据
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
     * 得到总行数
     * @return int
     */
    public int rowCount()
    {
        return getModel().getSize();
    }
    /**
     * 得到项目
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
     * 新增项目
     * @param node TComboNode
     */
    public void addItem(TComboNode node)
    {
        getModel().addElement(node);
        updateUI();
    }
    /**
     * 插入一个项目
     * @param node TComboNode
     * @param index int
     */
    public void insertItem(TComboNode node,int index)
    {
        getModel().insertElementAt(node,index);
        updateUI();
    }
    /**
     * 设置展开宽度
     * @param expandWidth int
     */
    public void setExpandWidth(int expandWidth)
    {
        this.expandWidth = expandWidth;
    }
    /**
     * 得到展开宽度
     * @return int
     */
    public int getExpandWidth()
    {
        return expandWidth;
    }
    /**
     * 设置父类组件
     * @param parentComponent TComponent
     */
    public void setParentComponent(TComponent parentComponent)
    {
        this.parentComponent = parentComponent;
    }
    /**
     * 得到父类组件
     * @return TComponent
     */
    public TComponent getParentComponent()
    {
        return parentComponent;
    }
    /**
     * 是否正在加载
     * @return boolean
     */
    public boolean isInitNow()
    {
        return initNow;
    }
    /**
     * 是否正在查询
     * @return boolean
     */
    public boolean isQueryNow()
    {
        return queryNow;
    }
    /**
     * 设置异步查询
     * @param postQuery boolean
     */
    public void setPostQuery(boolean postQuery)
    {
        this.postQuery = postQuery;
    }
    /**
     * 是否异步查询
     * @return boolean
     */
    public boolean isPostQuery()
    {
        return postQuery;
    }
    /**
     * 设置能否编辑
     * @param canEdit boolean
     */
    public void setCanEdit(boolean canEdit)
    {
        this.canEdit = canEdit;
    }
    /**
     * 是否可以编辑
     * @return boolean
     */
    public boolean canEdit()
    {
        return canEdit;
    }
    /**
     * 释放
     */
    public void release()
    {
        //释放监听
        releaseListeners();
        //释放控制类
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

        //释放属性
        RunClass.release(this);
    }
    /**
     * 设置字体
     * @param name String
     */
    public void setFontName(String name)
    {
        Font f = getTFont();
        setTFont(new Font(name,f.getStyle(),f.getSize()));
    }
    /**
     * 得到字体
     * @return String
     */
    public String getFontName()
    {
        return getTFont().getFontName();
    }
    /**
     * 设置字体尺寸
     * @param size int
     */
    public void setFontSize(int size)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),f.getStyle(),size));
    }
    /**
     * 得到字体尺寸
     * @return int
     */
    public int getFontSize()
    {
        return getTFont().getSize();
    }
    /**
     * 设置字体类型
     * @param style int
     */
    public void setFontStyle(int style)
    {
        Font f = getTFont();
        setTFont(new Font(f.getFontName(),style,f.getSize()));
    }
    /**
     * 得到字体类型
     * @return int
     */
    public int getFontStyle()
    {
        return getTFont().getStyle();
    }
    /**
     * 设置字体
     * @param font Font
     */
    public void setTFont(Font font)
    {
        this.tfont = font;
    }
    /**
     * 得到字体
     * @return Font
     */
    public Font getTFont()
    {
        return tfont;
    }
    /**
     * 得到字体
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
     * 设置背景颜色
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
     * 设置字体颜色
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
     * 设置边框
     * @param border String
     */
    public void setBorder(String border) {
        setBorder(StringTool.getBorder(border, getConfigParm()));
    }
    /**
     * 设置中文Tip
     * @param zhTip String
     */
    public void setZhTip(String zhTip)
    {
        this.zhTip = zhTip;
    }
    /**
     * 得到中文Tip
     * @return String
     */
    public String getZhTip()
    {
        return zhTip;
    }
    /**
     * 设置英文Tip
     * @param enTip String
     */
    public void setEnTip(String enTip)
    {
        this.enTip = enTip;
    }
    /**
     * 得到英文Tip
     * @return String
     */
    public String getEnTip()
    {
        return enTip;
    }
    /**
     * 得到语种
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }
    /**
     * 设置语种
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
     * 错误日志输出
     * @param text String 日志内容
     */
    public void err(String text) {
        System.out.println(text);
    }
    /**
     * 得到继承加载器
     * @return MaxLoad
     */
    public MaxLoad getMaxLoad()
    {
        if(getParentComponent() instanceof TContainer)
            return ((TContainer)getParentComponent()).getMaxLoad();
        return null;
    }
    /**
     * 设置居中
     * @param isAutoCenter boolean
     */
    public void setAutoCenter(boolean isAutoCenter)
    {
        this.isAutoCenter = isAutoCenter;
    }
    /**
     * 是否居中
     * @return boolean
     */
    public boolean isAutoCenter()
    {
        return isAutoCenter;
    }
    /**
     * 设置自动X
     * @param autoX boolean
     */
    public void setAutoX(boolean autoX)
    {
        this.autoX = autoX;
    }
    /**
     * 是否是自动X
     * @return boolean
     */
    public boolean isAutoX()
    {
        return autoX;
    }
    /**
     * 设置自动Y
     * @param autoY boolean
     */
    public void setAutoY(boolean autoY)
    {
        this.autoY = autoY;
    }
    /**
     * 是否是自动Y
     * @return boolean
     */
    public boolean isAutoY()
    {
        return autoY;
    }
    /**
     * 设置自动宽度
     * @param autoWidth boolean
     */
    public void setAutoWidth(boolean autoWidth)
    {
        this.autoWidth = autoWidth;
    }
    /**
     * 是否是自动宽度
     * @return boolean
     */
    public boolean isAutoWidth()
    {
        return autoWidth;
    }
    /**
     * 设置自动高度
     * @param autoHeight boolean
     */
    public void setAutoHeight(boolean autoHeight)
    {
        this.autoHeight = autoHeight;
    }
    /**
     * 是否是自动高度
     * @return boolean
     */
    public boolean isAutoHeight()
    {
        return autoHeight;
    }
    /**
     * 设置自动H
     * @param autoH boolean
     */
    public void setAutoH(boolean autoH)
    {
        this.autoH = autoH;
    }
    /**
     * 是否是自动H
     * @return boolean
     */
    public boolean isAutoH()
    {
        return autoH;
    }
    /**
     * 设置自动H
     * @param autoW boolean
     */
    public void setAutoW(boolean autoW)
    {
        this.autoW = autoW;
    }
    /**
     * 是否是自动W
     * @return boolean
     */
    public boolean isAutoW()
    {
        return autoW;
    }
    /**
     * 设置自动尺寸
     * @param autoSize int
     */
    public void setAutoSize(int autoSize)
    {
        this.autoSize = autoSize;
    }
    /**
     * 得到自动尺寸
     * @return int
     */
    public int getAutoSize()
    {
        return autoSize;
    }
    /**
     * 设置自动宽度尺寸
     * @param autoWidthSize int
     */
    public void setAutoWidthSize(int autoWidthSize)
    {
        this.autoWidthSize = autoWidthSize;
    }
    /**
     * 得到自动宽度尺寸
     * @return int
     */
    public int getAutoWidthSize()
    {
        return autoWidthSize;
    }
    /**
     * 设置自定高度尺寸
     * @param autoHeightSize int
     */
    public void setAutoHeightSize(int autoHeightSize)
    {
        this.autoHeightSize = autoHeightSize;
    }
    /**
     * 得到自动高度尺寸
     * @return int
     */
    public int getAutoHeightSize()
    {
        return autoHeightSize;
    }

    /**
     * 设置数据池名
     * @param dbPoolName String
     */
    public void setDbPoolName(String dbPoolName)
    {
        this.dbPoolName = dbPoolName;
    }

    /**
     * 得到数据池名
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
     * 父容器尺寸改变
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
