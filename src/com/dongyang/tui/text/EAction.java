package com.dongyang.tui.text;

/**
 *
 * <p>Title: 动作类</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.11.12
 * @version 1.0
 */
public class EAction
{
    public static final int FONT_NAME = 0x01;
    public static final int FONT_SIZE = 0x02;
    public static final int FONT_BOLD = 0x03;
    public static final int FONT_ITALIC = 0x04;
    public static final int DELETE = 0x05;
    public static final int COPY = 0x06;
    public static final int PASTE = 0x07;
    public static final int CUT = 0x08;


    /**
     * 阅览状态动作
     */
    public static final int PREVIEW_STATE = 0x100;
    /**
     * 编辑状态动作
     */
    public static final int EDIT_STATE = 0x101;
    /**
     * 设置宏值
     */
    public static final int SET_MICRO_FIELD = 0x102;
    /**
     * 设置性别限制
     */
    public static final int SET_SEX_CONTROL = 0x103;
    /**
     * 固定文本重新抓取数据
     */
    public static final int FIXED_TRY_RESET = 0x104;

    /**
     * 动作类型
     */
    private int type;
    /**
     * 参数
     */
    private Object[] value;
    /**
     * 构造器
     * @param type int
     * @param value Object[]
     */
    public EAction(int type,Object ...value)
    {
        setType(type);
        setValue(value);
    }
    /**
     * 设置动作类型
     * @param type int
     */
    public void setType(int type)
    {
        this.type = type;
    }
    /**
     * 得到动作类型
     * @return int
     */
    public int getType()
    {
        return type;
    }
    /**
     * 设置参数
     * @param value Object[]
     */
    public void setValue(Object[] value)
    {
        this.value = value;
    }
    /**
     * 得到参数
     * @return Object[]
     */
    public Object[] getValue()
    {
        return value;
    }
    /**
     * 得到整形
     * @param i int
     * @return int
     */
    public int getInt(int i)
    {
        return (Integer)value[i];
    }
    /**
     * 得到字符串
     * @param i int
     * @return String
     */
    public String getString(int i)
    {
        return (String)value[i];
    }
    /**
     * 得到布尔
     * @param i int
     * @return boolean
     */
    public boolean getBoolean(int i)
    {
        return (Boolean)value[i];
    }
    /**
     * 得到拷贝对象
     * @param i int
     * @return CopyOperator
     */
    public CopyOperator getCopyOperator(int i)
    {
        if(value[i] == null || !(value[i] instanceof CopyOperator))
            return null;

        return (CopyOperator)value[i];
    }
    /**
     * 增加行号
     */
    public void addRow()
    {
        CopyOperator copyOperator = getCopyOperator(0);
        if(copyOperator == null)
            return;
        copyOperator.addRow();
    }
    public void insertBlock()
    {
        CopyOperator copyOperator = getCopyOperator(0);
        if(copyOperator == null)
            return;
        copyOperator.insertBlock();
    }
    public void addBlock()
    {
        CopyOperator copyOperator = getCopyOperator(0);
        if(copyOperator == null)
            return;
        copyOperator.addBlock();
    }
}
