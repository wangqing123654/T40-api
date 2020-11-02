package test;

import java.awt.*;

import javax.swing.*;
import java.util.GregorianCalendar;
import java.util.Calendar;

public class FrameDate extends JFrame
{
    String days[] = {"������", "����һ", "���ڶ�", "������","������", "������", "������"};
    static final int TOP = 70;  //���˾���
    static final int CELLWIDTH=50,CELLHEIGHT = 30;  //��Ԫ��ߴ�
    static final int FEBRUARY = 1;
    int searchMonth = 2009,searchYear = 2;
    GregorianCalendar calendar=new GregorianCalendar();
    int daysInMonth[] = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    static final int MARGIN = 3;  //�߽����
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
            g.drawString(days[i], (CELLWIDTH-fontMetric.stringWidth(days[i]))/2 + i*CELLWIDTH,dayPos-20);  //���Ʊ�������
        }
        //������Ҫ���е�����
        int numRows = getNumberRows(searchYear, searchMonth);
        //�õ��ܵı��߶�
        int totalHeight = numRows * CELLHEIGHT;
        int totalWidth = 7 * CELLWIDTH;
        for (int i = 0; i <= totalWidth; i += CELLWIDTH) {
            g.drawLine(i, TOP , i, TOP+ totalHeight); //���Ʊ����
        }
        for (int i = 0, j = TOP ; i <= numRows; i++, j += CELLHEIGHT) {
            g.drawLine(0, j, totalWidth, j); //���Ʊ����
        }
        int xNum = (getFirstDayOfMonth(searchYear, searchMonth) + 1) * CELLWIDTH - MARGIN;
        int yNum = TOP +  MARGIN + fontAscent;
        int numDays = daysInMonth[searchMonth] + ((calendar.isLeapYear(searchYear) && (searchMonth == FEBRUARY)) ? 1 : 0);
        for (int day = 1; day <= numDays; day++) {
            String dayStr = Integer.toString(day);
             g.drawString(dayStr, xNum - fontMetric.stringWidth(dayStr), yNum);     //�����ַ���
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
        if (year < 1582) { //���С��1582�꣬�򷵻�-1
            return (-1);
        }
        if ((month < 0) || (month > 11)) {
            return (-1);
        }
        firstDay = getFirstDayOfMonth(year, month); //�����·ݵĵ�һ��

        if ((month == FEBRUARY) && (firstDay == 0) && !calendar.isLeapYear(year)) {
            return 4;
        }
        numCells = firstDay + daysInMonth[month];
        if ((month == FEBRUARY) && (calendar.isLeapYear(year))) {
            numCells++;
        }
        return ((numCells <= 35) ? 5 : 6);     //��������
    }
    /**
     * �õ�ÿ�µĵ�һ��
     * @param year int
     * @param month int
     * @return int
     */
    private int  getFirstDayOfMonth(int year, int month) {
        int firstDay;
        int i;
        if (year < 1582) { //���С��1582��,����-1
            return (-1);
        }
        if ((month < 0) || (month > 11)) { //�·�������,����-1
            return (-1);
        }
        firstDay = getFirstDayOfYear(year);    //�õ�ÿ��ĵ�һ��
        for (i = 0; i < month; i++) {
            firstDay += daysInMonth[i]; //����ÿ�µĵ�һ��
        }
        if ((month > FEBRUARY) && calendar.isLeapYear(year)) {
            firstDay++;
        }
        return (firstDay % 7);
    }
    /**
     * ����ÿ��ĵ�һ��
     * @param year int
     * @return int
     */
    private int getFirstDayOfYear(int year){
        int leapYears;
        int hundreds;
        int fourHundreds;
        int first;
        if (year < 1582) { //������С��1582��
            return (-1); //����-1
        }
        leapYears = (year - 1581) / 4;
        hundreds = (year - 1501) / 100;
        leapYears -= hundreds;
        fourHundreds = (year - 1201) / 400;
        leapYears += fourHundreds;
        first=5 + (year - 1582) + leapYears % 7; //�õ�ÿ���һ��
        return first;
    }
    public static void main(String args[])
    {
        new FrameDate().setVisible(true);
    }
}
