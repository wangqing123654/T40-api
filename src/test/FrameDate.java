package test;

import java.awt.*;

import javax.swing.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class FrameDate extends JFrame
{
    String days[] = {"星期日", "星期一", "星期二", "星期三","星期四", "星期五", "星期六"};
    static final int TOP = 70;  //顶端距离
    static final int CELLWIDTH=50,CELLHEIGHT = 30;  //单元格尺寸
    static final int FEBRUARY = 1;
    int searchMonth = 2009,searchYear = 2;
    GregorianCalendar calendar=new GregorianCalendar();
    int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final int MARGIN = 3;  //边界距离
    public FrameDate()
    {
        this.setSize(200,200);
        this.removeAll();
        searchMonth = calendar.get(Calendar.MONTH);
        searchYear = calendar.get(Calendar.YEAR);
        System.out.println(searchMonth + " " + searchYear);
    }
    public void paint(Graphics g)
    {
        FontMetrics fontMetric = g.getFontMetrics();
        int fontAscent = fontMetric.getAscent();
        int dayPos = TOP + fontAscent / 2;
        for (int i = 0; i < 7; i++) {
            g.drawString(days[i], (CELLWIDTH-fontMetric.stringWidth(days[i]))/2 + i*CELLWIDTH,dayPos-20);  //绘制表格标题栏
        }
        //计算需要的行的数量
        int numRows = getNumberRows(searchYear, searchMonth);
        //得到总的表格高度
        int totalHeight = numRows * CELLHEIGHT;
        int totalWidth = 7 * CELLWIDTH;
        for (int i = 0; i <= totalWidth; i += CELLWIDTH) {
            g.drawLine(i, TOP , i, TOP+ totalHeight); //绘制表格线
        }
        for (int i = 0, j = TOP ; i <= numRows; i++, j += CELLHEIGHT) {
            g.drawLine(0, j, totalWidth, j); //绘制表格线
        }
        int xNum = (getFirstDayOfMonth(searchYear, searchMonth) + 1) * CELLWIDTH - MARGIN;
        int yNum = TOP +  MARGIN + fontAscent;
        int numDays = daysInMonth[searchMonth] + ((calendar.isLeapYear(searchYear) && (searchMonth == FEBRUARY)) ? 1 : 0);
        for (int day = 1; day <= numDays; day++) {
            String dayStr = Integer.toString(day);
             g.drawString(dayStr, xNum - fontMetric.stringWidth(dayStr), yNum);     //绘制字符串
             xNum += CELLWIDTH;
             if (xNum > totalWidth) {
                 xNum = CELLWIDTH - MARGIN;
                 yNum += CELLHEIGHT;
             }
         }
    }
    private int getNumberRows(int year, int month)
    {
        int firstDay;
        int numCells;
        if (year < 1582) { //年份小于1582年，则返回-1
            return (-1);
        }
        if ((month < 0) || (month > 11)) {
            return (-1);
        }
        firstDay = getFirstDayOfMonth(year, month); //计算月份的第一天

        if ((month == FEBRUARY) && (firstDay == 0) && !calendar.isLeapYear(year)) {
            return 4;
        }
        numCells = firstDay + daysInMonth[month];
        if ((month == FEBRUARY) && (calendar.isLeapYear(year))) {
            numCells++;
        }
        return ((numCells <= 35) ? 5 : 6);     //返回行数
    }
    /**
     * 得到每月的第一天
     * @param year int
     * @param month int
     * @return int
     */
    private int  getFirstDayOfMonth(int year, int month) {
        int firstDay;
        int i;
        if (year < 1582) { //年份小于1582年,返回-1
            return (-1);
        }
        if ((month < 0) || (month > 11)) { //月份数错误,返回-1
            return (-1);
        }
        firstDay = getFirstDayOfYear(year);    //得到每年的第一天
        for (i = 0; i < month; i++) {
            firstDay += daysInMonth[i]; //计算每月的第一天
        }
        if ((month > FEBRUARY) && calendar.isLeapYear(year)) {
            firstDay++;
        }
        return (firstDay % 7);
    }
    /**
     * 计算每年的第一天
     * @param year int
     * @return int
     */
    private int getFirstDayOfYear(int year){
        int leapYears;
        int hundreds;
        int fourHundreds;
        int first;
        if (year < 1582) { //如果年份小于1582年
            return (-1); //返回-1
        }
        leapYears = (year - 1581) / 4;
        hundreds = (year - 1501) / 100;
        leapYears -= hundreds;
        fourHundreds = (year - 1201) / 400;
        leapYears += fourHundreds;
        first=5 + (year - 1582) + leapYears % 7; //得到每年第一天
        return first;
    }
    public static void main(String args[])
    {
        new FrameDate().setVisible(true);
    }
}
