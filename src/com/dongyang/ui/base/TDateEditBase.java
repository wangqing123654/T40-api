package com.dongyang.ui.base;

import com.dongyang.ui.TComboBox;
import com.dongyang.util.StringTool;
import com.dongyang.ui.TDateList;
import com.dongyang.ui.TNumberTextField;
import com.dongyang.ui.TSpinner;
import com.dongyang.manager.TCM_Transform;
import java.util.Date;
import com.dongyang.ui.TButton;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Insets;
import javax.swing.BorderFactory;

/**
 *
 * <p>Title: �������</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.2.6
 * @version 1.0
 */
public class TDateEditBase extends TComponentBase
{
    private static final String MONTH_S =
            "[[id,name,enname],[1,һ��,Jan],[2,����,Feb],[3,����,Mar],[4,����,Apr],[5,����,May],[6,����,Jun],"+
            "[7,����,Jul],[8,����,Aug],[9,����,Sep],[10,ʮ��,Oct],[11,ʮһ��,Nov],[12,ʮ����,Dec]]";
    private TComboBox monthCombo = new TComboBox();
    private TDateList dayList = new TDateList();
    private TNumberTextField yearEdit = new TNumberTextField();
    private TSpinner yearEditT = new TSpinner();
    private TempButton now = new TempButton();
    /**
     * ��
     */
    private int year = 2009;
    /**
     * ��
     */
    private int month = 2;
    /**
     * ����
     */
    private int day = 1;
    /**
     * ����
     */
    private String language;
    /**
     * ������
     */
    public TDateEditBase()
    {
        uiInit();
        setDate(new Date());
    }
    /**
     * ��ʼ�����
     */
    protected void uiInit() {
        setBackground(new Color(255,255,255));
        monthCombo.setEditable(false);
        monthCombo.setBounds(10,10,68,22);
        monthCombo.setVectorData(StringTool.getVector(MONTH_S));
        monthCombo.setShowName(true);
        monthCombo.setShowID(false);
        monthCombo.updateUI();
        monthCombo.onInit();
        monthCombo.setParentComponent(this);
        monthCombo.setSelectedAction("monthComboAction");
        add(monthCombo);
        dayList.setBounds(10,40,166,118);
        dayList.setBorder(BorderFactory.createEtchedBorder());
        dayList.onInit();
        dayList.setParentComponent(this);
        dayList.setChangedAction("dayAction");
        dayList.setActionMessage("dateAction");
        add(dayList);
        yearEdit.setBounds(80,10,50,22);
        yearEdit.setFormat("#########0");
        yearEdit.onInit();
        yearEdit.setParentComponent(this);
        yearEdit.setFocusLostAction("yearFocusLostAction");
        add(yearEdit);
        yearEditT.setBounds(132,10,18,22);
        yearEditT.onInit();
        yearEditT.setParentComponent(this);
        yearEditT.setChangedAction("yearTAction");
        yearEditT.setValue(0);
        add(yearEditT);
        now.setBounds(152,10,22,22);
        now.onInit();
        now.setParentComponent(this);
        now.setActionMessage("nowAction");
        add(now);
        setSize(187,168);
    }
    /**
     * ��ǰ���ڶ���
     */
    public void nowAction()
    {
        setDate(new Date());
        exeAction(getDayAction());
    }
    /**
     * �����ձ仯����
     * @param action String
     */
    public void setDayAction(String action) {
        setActionMessageMap("DayAction",action);
    }
    /**
     * �õ��ձ仯����
     * @return String
     */
    public String getDayAction() {
        return getActionMessageMap("DayAction");
    }
    /**
     * ������仯����
     * @param action String
     */
    public void setYearAction(String action) {
        setActionMessageMap("YearAction",action);
    }
    /**
     * �õ��±仯����
     * @return String
     */
    public String getMonthAction() {
        return getActionMessageMap("MonthAction");
    }
    /**
     * �����±仯����
     * @param action String
     */
    public void setMonthAction(String action) {
        setActionMessageMap("MonthAction",action);
    }

    /**
     * �õ���仯����
     * @return String
     */
    public String getYearAction() {
        return getActionMessageMap("YearAction");
    }
    /**
     * �ձ仯
     */
    public void dayAction()
    {
        int oldYear = this.year;
        int oldMonth = this.month;
        int oldDay = this.day;
        int y = dayList.getYear();
        int m = dayList.getMonth();
        int d = dayList.getDay();
        setYear(y);
        setMonth(m);
        setDay(d);
        if(oldYear != y)
            exeAction(getYearAction());
        if(oldMonth != m)
            exeAction(getMonthAction());
        if(oldDay != d)
            exeAction(getDayAction());
    }
    /**
     * ����ȷ��
     */
    public void dateAction()
    {
        setYear(dayList.getYear());
        setMonth(dayList.getMonth());
        setDay(dayList.getDay());
        exeAction(getActionMessage());
    }
    /**
     * ��ؼ�ʧȥ����
     */
    public void yearFocusLostAction()
    {
        int oldYear = year;
        int x = TCM_Transform.getInt(yearEdit.getValue());
        setYear(x);
        if(oldYear != x)
            exeAction(getYearAction());
    }
    /**
     * ѡ���·ݶ���
     */
    public void monthComboAction()
    {
        int oldMonth = month;
        int x = StringTool.getInt(monthCombo.getSelectedID());
        setMonth(x);
        if(oldMonth != x)
            exeAction(getMonthAction());
    }
    /**
     * �����
     */
    public void yearTAction()
    {
        int oldYear = year;
        int x = TCM_Transform.getInt(yearEdit.getValue()) + (Integer)yearEditT.getValue();
        yearEdit.setValue(x);
        yearEditT.setValue(0);
        setYear(x);
        if(oldYear != x)
            exeAction(getYearAction());
    }
    /**
     * ������
     * @param year int
     */
    public void setYear(int year)
    {
        this.year = year;
        if(dayList.getYear() != year)
            dayList.setYear(year);
        if(TCM_Transform.getInt(yearEdit.getValue()) != year)
            yearEdit.setValue(year);
    }
    /**
     * �õ���
     * @return int
     */
    public int getYear()
    {
        return year;
    }
    /**
     * ������
     * @param month int
     */
    public void setMonth(int month)
    {
        this.month = month;
        if(dayList.getMonth() != month)
            dayList.setMonth(month);
        if(StringTool.getInt(monthCombo.getSelectedID()) != month)
            monthCombo.setSelectedID("" + month);
    }
    /**
     * �õ���
     * @return int
     */
    public int getMonth()
    {
        return month;
    }
    /**
     * ��������
     * @param day int
     */
    public void setDay(int day)
    {
        this.day = day;
        if(dayList.getDay() != day)
            dayList.setDay(day);
    }

    /**
     * �õ�����
     * @return int
     */
    public int getDay()
    {
        return day;
    }
    /**
     * ��������
     * @param date Date
     */
    public void setDate(Date date)
    {
        setYear(StringTool.getYear(date));
        setMonth(StringTool.getMonth(date));
        setDay(StringTool.getDay(date));
    }
    /**
     * ��������
     * @param year int
     * @param month int
     * @param day int
     */
    public void setDate(int year,int month,int day)
    {
        setYear(year);
        setMonth(month);
        setDay(day);
    }
    /**
     * �õ�����
     * @return Date
     */
    public Date getDate()
    {
        return StringTool.getDate(getYear(),getMonth(),getDay());
    }
    /**
     * �ػ�
     * @param g Graphics
     */
    public void paint(Graphics g)
    {
        Insets insets = getInsets();
        g.setColor(this.getBackground());
        g.fillRect(insets.left,insets.top,
                   getWidth() - insets.left - insets.right,
                   getHeight() - insets.top - insets.bottom);
        super.paint(g);
    }
    /**
     * ��������
     * @param language String
     */
    public void changeLanguage(String language)
    {
        if (language == null)
            return;
        if (language.equals(this.language))
            return;
        this.language = language;
        repaint();
        if(dayList != null)
            dayList.changeLanguage(language);
        if(monthCombo !=null)
            monthCombo.changeLanguage(language);
    }
    class TempButton extends TButton
    {
        /**
         * �ػ�
         * @param g Graphics
         */
        public void paint(Graphics g)
        {
            super.paint(g);
            g.setColor(new Color(192,210,251));
            g.fillRect(5,5,12,12);
            g.setColor(new Color(255,255,255));
            int x = 0;
            g.drawArc(6 + x,6 + x,9,9,0,290);
            g.drawLine(12 + x,10 + x,14 + x,12 + x);
            g.drawLine(16 + x,10 + x,14 + x,12 + x);

            g.setColor(new Color(77,97,133));
            x = -1;
            g.drawArc(6 + x,6 + x,9,9,0,290);
            g.drawLine(12 + x,10 + x,14 + x,12 + x);
            g.drawLine(16 + x,10 + x,14 + x,12 + x);
        }
    }
}
