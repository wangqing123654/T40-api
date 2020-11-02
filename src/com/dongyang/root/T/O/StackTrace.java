package com.dongyang.root.T.O;

import com.dongyang.util.StringTool;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * <p>Title: ���л���</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: JavaHis</p>
 *
 * @author lzk 2009.1.7
 * @version 1.0
 */
public class StackTrace
{
    public final static String KEY[] = new String[]{
                                       "if","for","return","break","continue","while","do",
                                       "static","final","true","false","null","new"};
    public final static String BASE_TYPE[] = new String[]{
                                             "int","float","long","boolean","char","short","byte","double",};
    /**
     * ���ż���1
     */
    public final static int H1=10;
    /**
     * ����
     */
    public final static int E1=1;
    /**
     * �߼� || &&
     */
    public final static int L1=3;
    /**
     * �߼� > < >= <=
     */
    public final static int L2=3;
    /**
     * �ؼ���
     */
    public final static int K1=4;
    /**
     * ����� | &
     */
    public final static int F1=5;
    /**
     * ����� + -
     */
    public final static int F2=6;
    /**
     * ����� * /
     */
    public final static int F3=7;
    /**
     * ����� ++ --
     */
    public final static int W1=8;


    /**
     * ���ż���2
     */
    public final static int H2=7;
    /**
     * �����
     */
    private VM vm;
    /**
     * ����
     */
    private TM tm;
    /**
     * ������ʼ��
     */
    private int tmStartIndex = 0;
    /**
     * ����������
     */
    private int tmEndIndex = 0;
    /**
     * ��������
     */
    private S s;
    /**
     * ����
     */
    private StackTrace parent;
    /**
     * ����
     */
    private TObject tObject;
    /**
     * �﷨
     */
    private Script script;
    /**
     * ����ָ��
     */
    private int runIndex = 0;
    /**
     * ����ֵ
     */
    private Object returnValue;
    /**
     * �Ƿ񷵻�
     */
    private boolean isReturn;
    /**
     * ����
     */
    private boolean isBreak;
    /**
     * ����
     */
    private boolean isContinue;
    /**
     * ������
     */
    public StackTrace()
    {
    }
    /**
     * ���������
     * @param vm VM
     */
    public void setVM(VM vm)
    {
        this.vm = vm;
    }
    /**
     * �õ������
     * @return VM
     */
    public VM getVM()
    {
        return vm;
    }
    /**
     * ���ÿ�ʼ��
     * @param tmStartIndex int
     */
    public void setTMStartIndex(int tmStartIndex)
    {
        this.tmStartIndex = tmStartIndex;
        runIndex = tmStartIndex;
    }
    /**
     * �õ���ʼ��
     * @return int
     */
    public int getTMStartIndex()
    {
        return tmStartIndex;
    }
    /**
     * ���ý�����
     * @param tmEndIndex int
     */
    public void setTMEndIndex(int tmEndIndex)
    {
        this.tmEndIndex = tmEndIndex;
    }
    /**
     * �õ�������
     * @return int
     */
    public int getTMEndIndex()
    {
        return tmEndIndex;
    }
    /**
     * ���ø���
     * @param parent StackTrace
     */
    public void setParent(StackTrace parent)
    {
        this.parent = parent;
    }
    /**
     * �õ�����
     * @return StackTrace
     */
    public StackTrace getParent()
    {
        return parent;
    }
    /**
     * ���÷���
     * @param tm TM
     */
    public void setTM(TM tm)
    {
        if(tm == null)
            return;
        this.tm = tm;
        setTMStartIndex(0);
        setTMEndIndex(tm.size());
    }
    /**
     * �õ�����
     * @return TM
     */
    public TM getTM()
    {
        return tm;
    }
    /**
     * ��������
     * @param s S
     */
    public void setS(S s)
    {
        this.s = s;
    }
    /**
     * �õ�����
     * @return S
     */
    public S getS()
    {
        return s;
    }
    /**
     * ���ö���
     * @param tObject TObject
     */
    public void setTObject(TObject tObject)
    {
        this.tObject = tObject;
    }
    /**
     * �õ���������
     * @return TObject
     */
    public TObject getTObject()
    {
        return tObject;
    }
    /**
     * �Ƿ񷵻�
     * @return boolean
     */
    public boolean isReturn()
    {
        return isReturn;
    }
    /**
     * �õ����ؽ��
     * @return Object
     */
    public Object getReturnValue()
    {
        return returnValue;
    }
    /**
     * �Ƿ�����
     * @return boolean
     */
    public boolean isBreak()
    {
        return isBreak;
    }
    /**
     * �Ƿ����
     * @return boolean
     */
    public boolean isContinue()
    {
        return isContinue;
    }
    /**
     * ����While
     * @param list List
     * @return boolean
     */
    public boolean runWhile(List list)
    {
        getForWhile(list);
        while(forTrue(list))
        {
            runIndex = this.getTMStartIndex();
            run();
            if(isReturn)
                return false;
            if(isBreak)
                return false;
        }
        return true;
    }
    /**
     * ����For
     * @param list List
     * @return boolean
     */
    public boolean runFor(List list)
    {
        List[] l = getForList(list);
        //ִ�г�ʼ��
        if(l[0].size() > 0)
            runRow(l[0]);
        while(forTrue(l[1]))
        {
            runIndex = this.getTMStartIndex();
            run();
            if(isReturn)
                return false;
            if(isBreak)
                return false;
            forAdd(l[2]);
        }
        return true;
    }
    /**
     * ����For �е��ۼ�
     * @param list List
     */
    public void forAdd(List list)
    {
        if(list.size() == 0)
            return;
        List p = listCopyFor(list);
        runRow(p);
    }
    /**
     * ����For �е��ж�
     * @param list List
     * @return boolean
     */
    public boolean forTrue(List list)
    {
        if(list.size() == 0)
            return true;
        List p = listCopyFor(list);
        runRow(p);
        JB jb = (JB)p.get(0);
        if(jb.data instanceof Boolean)
            return (Boolean)jb.data;
        return true;
    }
    /**
     * ����For����
     * @param l List
     * @return List
     */
    public List listCopyFor(List l)
    {
        List list = new ArrayList();
        for(int i = 0; i < l.size();i++)
            list.add(((JB)l.get(i)).clone());
        return list;
    }
    /**
     * �õ�While�����б�
     * @param list List
     */
    public void getForWhile(List list)
    {
        list.remove(0);
        list.remove(0);
        list.remove(list.size() - 1);
    }
    /**
     * �õ�For�����б�
     * @param list List
     * @return List[]
     */
    public List[] getForList(List list)
    {
        List[] l = new List[]{new ArrayList(),new ArrayList(),new ArrayList()};
        int t = 0;
        for(int i = 2;i < list.size() - 1;i++)
        {
            JB jb = (JB)list.get(i);
            if("T".equals(jb.type) && ";".equals(jb.s))
            {
                t ++;
                if(t > 2)
                    break;
                continue;
            }
            l[t].add(jb);
        }
        return l;
    }
    /**
     * ����
     * @return Object
     */
    public Object run()
    {
        while(runIndex < getTMEndIndex())
        {
            String s = tm.get(runIndex).trim();
            if(s.length() == 0)
            {
                runIndex ++;
                continue;
            }
            List list = fl(s);
            System.out.println(s);
            System.out.println(this.s);
            if(list.size() == 0)
            {
                runIndex ++;
                continue;
            }
            if(((JB)list.get(0)).s.equals("for"))
            {
                script = new Script();
                script.name = "for";
                script.value1 = list;
                script.index = runIndex;
                if(!runFor())
                    return getReturnValue();
                runIndex ++;
                continue;
            }
            if(((JB)list.get(0)).s.equals("while"))
            {
                script = new Script();
                script.name = "while";
                script.value1 = list;
                script.index = runIndex;
                if(!runWhile())
                    return getReturnValue();
                runIndex ++;
                continue;
            }
            if(((JB)list.get(0)).s.equals("do"))
            {
                script = new Script();
                script.name = "do";
                script.value1 = list;
                script.index = runIndex;
                if(!runDoWhile())
                    return getReturnValue();
                runIndex ++;
                continue;
            }
            if(!runRow(list))
                return returnValue;
            if(script != null)
            {
                script.index = runIndex;
                if(script.name.equals("if"))
                    if(!runIf())
                        return getReturnValue();
                runIndex ++;
                continue;
            }
            runIndex ++;
        }
        return null;
    }
    /**
     * ����DoWhile
     * @return boolean
     */
    public boolean runDoWhile()
    {
        Block block = getBlock(script.index + 1);
        System.out.println("block=" + block);
        int end = block.type == 1?block.end == -1?block.start:block.end:block.start;
        end += 1;
        String s = tm.get(end).trim();
        List list = fl(s);

        StackTrace st = new StackTrace();
        st.setVM(getVM());
        st.setTM(tm);
        st.setParent(this);
        st.setTMStartIndex(block.start);
        st.setTMEndIndex(block.type==0?block.start+1:block.end);
        st.setS(new S());
        if(!st.runWhile(list))
        {
            if(st.isReturn())
            {
                returnValue = st.getReturnValue();
                isReturn = true;
                return false;
            }
        }
        runIndex = end;
        script = null;
        return true;
    }
    /**
     * ����While
     * @return boolean
     */
    public boolean runWhile()
    {
        Block block = getBlock(script.index + 1);
        System.out.println("block=" + block);
        StackTrace st = new StackTrace();
        st.setVM(getVM());
        st.setTM(tm);
        st.setParent(this);
        st.setTMStartIndex(block.start);
        st.setTMEndIndex(block.type==0?block.start+1:block.end);
        st.setS(new S());
        if(!st.runWhile((List)script.value1))
        {
            if(st.isReturn())
            {
                returnValue = st.getReturnValue();
                isReturn = true;
                return false;
            }
        }
        runIndex = block.type == 1?block.end == -1?block.start:block.end:block.start;
        script = null;
        return true;
    }
    /**
     * ����For
     * @return boolean
     */
    public boolean runFor()
    {
        Block block = getBlock(script.index + 1);
        System.out.println("block=" + block);
        StackTrace st = new StackTrace();
        st.setVM(getVM());
        st.setTM(tm);
        st.setParent(this);
        st.setTMStartIndex(block.start);
        st.setTMEndIndex(block.type==0?block.start+1:block.end);
        st.setS(new S());
        if(!st.runFor((List)script.value1))
        {
            if(st.isReturn())
            {
                returnValue = st.getReturnValue();
                isReturn = true;
                return false;
            }
        }
        runIndex = block.type == 1?block.end == -1?block.start:block.end:block.start;
        script = null;
        return true;
    }
    /**
     * ����IF
     * @return boolean
     */
    public boolean runIf()
    {
        Block b[] = getIfBlock(script.index + 1);
        Script scriptNode = script;
        System.out.println("b0=" + b[0]);
        System.out.println("b1=" + b[1]);

        if((Boolean)scriptNode.value1)
        {
            StackTrace st = new StackTrace();
            st.setVM(getVM());
            st.setTM(tm);
            st.setParent(this);
            st.setTMStartIndex(b[0].start);
            st.setTMEndIndex(b[0].type==0?b[0].start+1:b[0].end);
            st.setS(new S());
            st.run();
            if(st.isReturn()||st.isBreak()||st.isContinue)
            {
                returnValue = st.getReturnValue();
                isReturn = st.isReturn();
                isBreak = st.isBreak();
                isContinue = st.isContinue();
                return false;
            }
        }
        else if(b[1] != null)
        {
            StackTrace st = new StackTrace();
            st.setVM(getVM());
            st.setTM(tm);
            st.setParent(this);
            st.setTMStartIndex(b[1].start);
            st.setTMEndIndex(b[1].type==0?b[1].start+1:b[1].end);
            st.setS(new S());
            st.run();
            if(st.isReturn()||st.isBreak()||st.isContinue)
            {
                returnValue = st.getReturnValue();
                isReturn = st.isReturn();
                isBreak = st.isBreak();
                isContinue = st.isContinue();
                return false;
            }
        }
        runIndex = b[1] == null?b[0].type == 1?b[0].end == -1?b[0].start:b[0].end:b[0].start:
                   b[1].type == 1?b[1].end == -1?b[1].start:b[1].end:b[1].start;

        script = null;
        return true;
    }
    /**
     * �õ� IF ��
     * @param index int
     * @return Block[]
     */
    public Block[] getIfBlock(int index)
    {
        Block[] b = new Block[2];
        int start = getNextIndex(index);
        b[0] = getBlock(index);
        int end = b[0].type == 1?b[0].end == -1?b[0].start + 1:b[0].end + 1:start + 1;
        System.out.println("end=" + end);
        if(end >= getTMEndIndex())
            return b;
        if(!tm.get(end).trim().equals("else"))
            return b;
        b[1] = getBlock(end + 1);
        return b;
    }
    /**
     * �õ���
     * @param index int
     * @return Block
     */
    public Block getBlock(int index)
    {
        int start = getNextIndex(index);
        Block block = new Block();
        block.type =tm.get(start).trim().equals("{")?1:0;
        block.start = block.type == 1?start + 1:start;
        block.end = block.type == 1?getNextEndIndex(block.start + 1):0;
        return block;
    }
    /**
     * �õ���Ч��ֹ��
     * @param index int
     * @return int
     */
    public int getNextEndIndex(int index)
    {
        int i = 0;
        while(index < getTMEndIndex())
        {
            String s = tm.get(index).trim();
            if(s.length() == 0)
            {
                index ++;
                continue;
            }
            if(s.equals("{"))
                i++;
            if(s.equals("}"))
                if(i == 0)
                    return index;
                else
                    i--;
            index++;
        }
        return -1;
    }
    /**
     * �õ���һ����Ч��
     * @param index int
     * @return int
     */
    public int getNextIndex(int index)
    {
        while(index < getTMEndIndex())
        {
            String s = tm.get(index).trim();
            if(s.length() == 0)
            {
                index ++;
                continue;
            }
            return index;
        }
        return -1;
    }
    /**
     * ���е���
     * @param list List
     * @return boolean
     */
    public boolean runRow(List list)
    {
        while(list.size() > 0)
        {
            System.out.println("");
            for(int t = 0;t < list.size();t++)
                System.out.println("   " + t + " " + list.get(t));
            int index = getMax(list);

            JB jb = (JB) list.get(index);
            if(list.size() == 1 && !jb.type.equals("KEY"))
                break;
            if (jb.type.equals("I"))
            {
                if (index > 0)
                {
                    JB jb1 = (JB) list.get(index - 1);
                    if(index - 2 >= 0)
                    {
                         JB jbn = (JB)list.get(index - 2);
                         if(jbn.type.equals("KEY") && jbn.s.equals("new"))
                         {
                             //��������
                             int indexEnd = getIEnd(list, index);
                             String fName = jb1.s;
                             Object parm[] = getFParm(list,index,indexEnd);
                             Object value = newFunction(fName,parm);
                             rem(list, index - 2, indexEnd,value);
                             continue;
                         }
                    }
                    if (jb1.type.equals("NO"))
                    {
                        //ִ�з���
                        int indexEnd = getIEnd(list, index);
                        String fName = jb1.s;
                        Object parm[] = getFParm(list,index,indexEnd);
                        Object value = runFunction(fName,parm);
                        rem(list, index - 1, indexEnd,value);
                        continue;
                    }
                    if(jb1.type.equals("F") || jb1.type.equals("=") || jb.type.equals("I"))
                    {
                        int indexEnd = getIEnd(list, index);
                        JB jb2 = (JB) list.get(index + 1);
                        if(indexEnd - index == 2)
                        {
                            rem(list, index, indexEnd, getJBValue(jb2));
                            continue;
                        }
                    }
                }
                continue;
            }
            //������
            if (jb.type.equals("F"))
            {
                JB jb1 = (JB) list.get(index - 1);
                JB jb2 = (JB) list.get(index + 1);
                Object o1 = getJBValue(jb1);
                Object o2 = getJBValue(jb2);
                Object value = Action.operation(jb.s,o1,o2);
                rem(list, index - 1, index + 1, value);
                continue;
            }
            // �߼���
            if (jb.type.equals("L"))
            {
                if(jb.s.equals("!"))
                {
                    JB jb1 = (JB) list.get(index + 1);
                    rem(list, index, index + 1, !getJBValueBoolean(jb1));
                    continue;
                }
                if(jb.s.equals("||"))
                {
                    JB jb1 = (JB) list.get(index - 1);
                    if(getJBValueBoolean(jb1))
                    {
                        rem(list, index - 1, getLJEnd(list,index), true);
                    }else
                    {
                        JB jb2 = (JB) list.get(index + 1);
                        if(jb2.type.equals("V"))
                            rem(list, index - 1, index + 1, getJBValueBoolean(jb1) ||
                                getJBValueBoolean(jb2));
                        else
                            jb.c --;
                    }
                    continue;
                }
                if(jb.s.equals("&&"))
                {
                    JB jb1 = (JB) list.get(index - 1);
                    if(!getJBValueBoolean(jb1))
                    {
                        rem(list, index - 1, getLJEnd(list,index), false);
                    }else
                    {
                        JB jb2 = (JB) list.get(index + 1);
                        if(jb2.type.equals("V"))
                            rem(list, index - 1, index + 1, getJBValueBoolean(jb1) &&
                                getJBValueBoolean(jb2));
                        else
                            jb.c --;
                    }
                    continue;
                }
                JB jb1 = (JB) list.get(index - 1);
                JB jb2 = (JB) list.get(index + 1);
                Object o1 = getJBValue(jb1);
                Object o2 = getJBValue(jb2);
                Object value = Action.operation(jb.s,o1,o2);
                rem(list, index - 1, index + 1, value);
                continue;
            }
            //�ؼ���
            if(jb.type.equals("KEY"))
            {
                if(jb.s.equals("break"))
                {
                    isBreak = true;
                    return false;
                }
                if(jb.s.equals("continue"))
                {
                    isContinue = true;
                    return false;
                }
                if(jb.s.equals("return"))
                {
                    if(index < list.size() - 1)
                        returnValue = getJBValue((JB) list.get(index + 1));
                    isReturn = true;
                    return false;
                }
                JB jb1 = (JB) list.get(index + 1);
                if(jb.s.equals("if"))
                {
                    script = new Script();
                    script.name = "if";
                    script.value1 = getJBValue(jb1);
                    return true;
                }
                continue;
            }
            if(jb.type.equals("NO"))
            {
                JB jb1 = (JB) list.get(index + 1);
                if(createVariable(jb1.s,jb.s))
                {
                    list.remove(index);
                }
                continue;
            }
            if(jb.type.equals("="))
            {
                JB jb1 = (JB) list.get(index - 1);
                JB jb2 = (JB) list.get(index + 1);
                Object value = getJBValue(jb2);
                if(!jb.s.equals("="))
                {
                    Object ov = getS(jb1.s);
                    value = Action.operation(jb.s,ov,value);
                }
                setS(jb1.s,value);
                rem(list, index - 1, index + 1, value);
                continue;
            }
            if(jb.type.equals("++--"))
            {
                //++i
                if(leftOperation(list,index))
                {
                    JB jb1 = (JB) list.get(index + 1);
                    Object ov = getS(jb1.s);
                    Object value = Action.operation(jb.s.equals("++")?"+":"-",ov,1);
                    setS(jb1.s,value);
                    rem(list, index, index + 1, value);
                }
                //i++
                else
                {
                    JB jb1 = (JB) list.get(index - 1);
                    Object ov = getS(jb1.s);
                    Object value = Action.operation(jb.s.equals("++")?"+":"-",ov,1);
                    setS(jb1.s,value);
                    rem(list, index - 1, index, ov);
                }
                continue;
            }
        }
        //for(int t = 0;t < list.size();t++)
        //    System.out.println(t + " " + list.get(t));
        //System.out.println(s);
        return true;
    }
    /**
     * �ҵ��߼����ʽ�����һ��λ��
     * @param list List
     * @param index int
     * @return int
     */
    public int getLJEnd(List list,int index)
    {
        int count = list.size();
        int x = 0;
        for(int i = index + 1;i < count;i++)
        {
            JB jb1 = (JB) list.get(i);
            if(!jb1.type.equals("I"))
                continue;
            if(jb1.s.equals("("))
            {
                x++;
                continue;
            }
            if(jb1.s.equals(")"))
            {
                if(x == 0)
                    return i - 1;
                x--;
                continue;
            }
        }
        return list.size() - 1;
    }
    /**
     * �ж������������
     * @param list List
     * @param index int
     * @return boolean true �����ǲ��� false �����ǲ���
     */
    public boolean leftOperation(List list,int index)
    {
        if(index == 0)
            return true;
        JB jb1 = (JB) list.get(index - 1);
        if(jb1.type.equals("=") ||
           jb1.type.equals("KEY") ||
           jb1.type.equals("L") ||
           jb1.type.equals("F") ||
           jb1.type.equals("I"))
            return true;
        return false;
    }
    /**
     * �õ�����
     * @param list List
     * @param x1 int
     * @param x2 int
     * @return Object[]
     */
    public Object[] getFParm(List list,int x1,int x2)
    {
        List l = new ArrayList();
        for(int t = x1 + 1;t < x2;t++)
        {
            JB jb = (JB)list.get(t);
            if(jb.type.equals("T"))
                continue;
            l.add(getJBValue(jb));
        }
        return (Object[])l.toArray(new Object[]{});
    }
    /**
     * ��������
     * @param name String
     * @param type String
     * @return boolean
     */
    public boolean createVariable(String name,String type)
    {
        if("int".equals(type))
        {
            s.add(name,0);
            return true;
        }
        if("long".equals(type))
        {
            s.add(name,(long)0);
            return true;
        }
        if("float".equals(type))
        {
            s.add(name,(float)0);
            return true;
        }
        if("double".equals(type))
        {
            s.add(name,(double)0);
            return true;
        }
        if("char".equals(type))
        {
            s.add(name,(char)0);
            return true;
        }
        if("byte".equals(type))
        {
            s.add(name,(byte)0);
            return true;
        }
        if("short".equals(type))
        {
            s.add(name,(short)0);
            return true;
        }
        if("boolean".equals(type))
        {
            s.add(name,false);
            return true;
        }
        s.add(name,null);
        return true;
    }
    /**
     * �õ����(Boolean)
     * @param jb JB
     * @return boolean
     */
    public boolean getJBValueBoolean(JB jb)
    {
        Object obj = getJBValue(jb);
        if(obj == null)
            return false;
        if(!(obj instanceof Boolean))
            return false;
        return (Boolean)obj;
    }
    /**
     * �õ����
     * @param jb JB
     * @return Object
     */
    public Object getJBValue(JB jb)
    {
        if(jb.type.equals("V"))
            return jb.data;
        if(isW(jb.s.charAt(0)))
            return getS(jb.s);
        if(isN(jb.s.charAt(0)))
           return getJBNumber(jb);
       return null;
    }
    /**
     * ���ڱ���
     * @param name String
     * @return boolean
     */
    public boolean existS(String name)
    {
        if(s.exist(name))
            return true;
        if(getParent() != null)
            return getParent().existS(name);
        return false;
    }
    /**
     * �õ�����ֵ
     * @param name String
     * @return Object
     */
    public Object getS(String name)
    {
        if(name.startsWith("this."))
        {
            name = name.substring(5);
            if(getTObject() != null)
                return getTObject().getSValue(name);
            return null;
        }
        int index = name.indexOf(".");
        if(index > 0)
        {
            String sName = name.substring(0,index);
            String cName = name.substring(index + 1);
            Object obj = getS(sName);
            if(obj == null)
                //�쳣
                return null;
            if(obj instanceof TObject)
            {
                TObject tobject = (TObject)obj;
                return tobject.getSValue(cName);
            }
            //�쳣
            return null;
        }
        if(s.exist(name))
            return s.get(name);
        if(getParent() != null && getParent().existS(name))
            return getParent().getS(name);
        if(getTObject() != null)
            return getTObject().getSValue(name);
        return null;
    }
    /**
     * ��������
     * @param name String
     * @param value Object
     * @return boolean
     */
    public boolean setS(String name,Object value)
    {
        if(name.startsWith("this."))
        {
            name = name.substring(5);
            if(getTObject() != null)
                return getTObject().setSValue(name,value);
            return false;
        }
        int index = name.indexOf(".");
        if(index > 0)
        {
            String sName = name.substring(0,index);
            String cName = name.substring(index + 1);
            Object obj = getS(sName);
            if(obj == null)
                //�쳣
                return false;
            if(obj instanceof TObject)
            {
                TObject tobject = (TObject)obj;
                return tobject.setSValue(cName,value);
            }
            //�쳣
            return false;
        }
        if(s.exist(name))
        {
            s.set(name, value);
            return true;
        }
        if(getParent() != null && getParent().existS(name))
            return getParent().setS(name,value);
        if(getTObject() != null)
            return getTObject().setSValue(name,value);
        return false;
    }
    /**
     * �õ�����
     * @param jb JB
     * @return Object
     */
    public Object getJBNumber(JB jb)
    {
        char c = jb.s.charAt(jb.s.length() - 1);
        if(c == 'i' || c == 'I')
            return Integer.parseInt(jb.s.substring(0,jb.s.length() - 2));
        if(c == 'l' || c == 'L')
            return Long.parseLong(jb.s.substring(0,jb.s.length() - 2));
        if(c == 'f' || c == 'F')
            return Float.parseFloat(jb.s.substring(0,jb.s.length() - 2));
        if(c == 'd' || c == 'D')
            return Double.parseDouble(jb.s.substring(0,jb.s.length() - 2));
        if(jb.s.indexOf(".") > 0)
            return Double.parseDouble(jb.s);
        return Integer.parseInt(jb.s);
    }
    /**
     * �Ƿ�������
     * @param c char
     * @return boolean
     */
    public boolean isN(char c)
    {
        return (c >= '0' && c <= '9') || c == '-' || c == '.';
    }
    /**
     * �Ƿ�����ĸ
     * @param c char
     * @return boolean
     */
    public boolean isW(char c)
    {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }
    /**
     * �滻���
     * @param list List
     * @param i1 int
     * @param i2 int
     * @param value Object
     */
    public void rem(List list,int i1,int i2,Object value)
    {
        JB jb = (JB)list.get(i1);
        jb.data = value;
        jb.type = "V";
        jb.c = 0;
        jb.s = "";
        for(int i = i2;i > i1;i--)
            list.remove(i);
    }
    /**
     * �ҵ������
     * @param list List
     * @return int
     */
    public int getMax(List list)
    {
        if(list.size() >= 2 &&
           ((JB)list.get(0)).type.equals("NO") &&
           ((JB)list.get(1)).type.equals("NO"))
            return 0;
        int index = 0;
        int maxc = 0;
        List l = new ArrayList();
        for(int i = 0;i < list.size();i++)
        {
            JB jb = (JB)list.get(i);
            int c = jb.c;
            if(jb.type.equals("L") && (jb.s.equals("||") || jb.s.equals("&&")))
            {
                if(maxc > 1)
                    return index;
                if(jb.s.equals("||") && getJBValueBoolean((JB)list.get(i - 1)))
                    return i;
                if(jb.s.equals("&&") && !getJBValueBoolean((JB)list.get(i - 1)))
                    return i;
            }
            if(maxc < c)
            {
                maxc = c;
                if(jb.type.equals("I") && jb.s.equals("("))
                {
                    l.add(new int[]{jb.c,i});
                    maxc = 0;
                }
                index = i;
                if(jb.type.equals("I") && jb.s.equals(")"))
                {
                    int[] x = (int[])l.remove(l.size() - 1);
                    maxc = x[0];
                    index = x[1];
                }
            }
        }
        return index;
    }
    /**
     * �ҵ���һ������λ��
     * @param list List
     * @param index int
     * @return int
     */
    public int getIEnd(List list,int index)
    {
        for(int i = index + 1;i < list.size();i++)
            if(((JB) list.get(i)).type.equals("I"))
                return i;
        return -1;
    }
    public String[] cl(String s)
    {
        return StringTool.parseLine(s,';',"''\"\"");
    }
    public List fl(String s)
    {
        List list = new ArrayList();
        String v = "";
        int j = 0;
        boolean b = false;
        for(int i = 0;i < s.length();i++)
        {
            char c = s.charAt(i);
            if(b)
            {
                if(c == '\\')
                {
                    v += "" + s.charAt(i + 1);
                    i+=2;
                    continue;
                }
                if(c == '"')
                {
                    JB jb = newJB(v, "V", 0);
                    jb.data = v;
                    list.add(jb);
                    v = "";
                    b = false;
                    continue;
                }
                v += "" + c;
                continue;
            }
            if(c == '"')
            {
                b = true;
                continue;
            }
            if(c == ' ')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                continue;
            }
            if(c == '=')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("=","=",j + E1);
                list.add(jb);
                //==
                if(s.charAt(i + 1) == '=')
                {
                    jb.s = "==";
                    jb.type = "L";
                    jb.c = j + L2;
                    i++;
                }
                continue;
            }
            if(c == '+')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("+","F",j + F2);
                list.add(jb);
                //+=
                if(s.charAt(i + 1) == '=')
                {
                    jb.type = "=";
                    jb.s = "+";
                    i++;
                }
                if(s.charAt(i + 1) == '+')
                {
                    jb.type = "++--";
                    jb.s = "++";
                    jb.c=j + W1;
                    i++;
                }
                continue;
            }
            if(c == '-')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("-","F",j + F2);
                list.add(jb);
                //-=
                if(s.charAt(i + 1) == '=')
                {
                    jb.type = "=";
                    jb.s = "-";
                    i++;
                }
                if(s.charAt(i + 1) == '+')
                {
                    jb.type = "++--";
                    jb.s = "--";
                    jb.c=j + W1;
                    i++;
                }
                continue;
            }
            if(c == '*')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("*","F",j + F3);
                list.add(jb);
                //*=
                if(s.charAt(i + 1) == '=')
                {
                    jb.type = "=";
                    jb.s = "*";
                    i++;
                }
                continue;
            }
            if(c == '/')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("/","F",j + F3);
                list.add(jb);
                ///=
                if(s.charAt(i + 1) == '=')
                {
                    jb.type = "=";
                    jb.s = "/";
                    i++;
                }
                continue;
            }
            if(c == '(')
            {
                if (v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                list.add(newJB("(","I",j + H2));
                j += H1;
                continue;
            }
            if(c == ')')
            {
                if (v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                j -= H1;
                list.add(newJB(")","I",j + H2));
                continue;
            }
            if(c == ',')
            {
                if (v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                list.add(newJB(",","T",0));
                continue;
            }
            if(c == ';')
            {
                if (v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                list.add(newJB(";","T",0));
                continue;
            }
            if(c == '>')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB(">","L",j + L2);
                list.add(jb);
                //>=
                if(s.charAt(i + 1) == '=')
                {
                    jb.s = ">=";
                    i++;
                }
                continue;
            }
            if(c == '<')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("<","L",j + L2);
                list.add(jb);
                //<=
                if(s.charAt(i + 1) == '=')
                {
                    jb.s = "<=";
                    i++;
                }
                continue;
            }
            if(c == '!')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("!","L",j + L2);
                list.add(jb);
                //!=
                if(s.charAt(i + 1) == '=')
                {
                    jb.s = "!=";
                    i++;
                }
                continue;
            }
            if(c == '|')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("|","F",j + F1);
                list.add(jb);
                //||
                if(s.charAt(i + 1) == '|')
                {
                    jb.type = "L";
                    jb.c = j + L1;
                    jb.s = "||";
                    i++;
                }
                continue;
            }
            if(c == '&')
            {
                if(v.length() > 0)
                {
                    list.add(newJB(v,j));
                    v = "";
                }
                JB jb = newJB("&","F",j + F1);
                list.add(jb);
                //&&
                if(s.charAt(i + 1) == '&')
                {
                    jb.type = "L";
                    jb.c = j + L1;
                    jb.s = "&&";
                    i++;
                }
                continue;
            }
            v += "" + c;
        }
        if(v.length() > 0)
            list.add(newJB(v,j));
        return list;
    }
    /**
     * ����JB
     * @param s String
     * @param type String
     * @param c int
     * @return JB
     */
    public JB newJB(String s,String type,int c)
    {
        JB jb = new JB();
        jb.s = s;
        jb.type = type;
        jb.c = c;
        return jb;
    }
    /**
     * ����JB(�жϹؼ���)
     * @param s String
     * @param c int
     * @return JB
     */
    public JB newJB(String s,int c)
    {
        if("true".equals(s))
            return newJBV(true);
        if("false".equals(s))
            return newJBV(false);
        if("null".equals(s))
            return newJBV(null);
        if(isKey(s))
            return newJB(s,"KEY",c + K1);

        char c0 = s.charAt(0);
        char c1 = s.charAt(s.length() - 1);
        boolean b = (c0 >= '0' && c0 <= '9') || c0 == '-' ;
        if(b)
        {
            if (c1 == 'i' || c1 == 'I')
                return newJBV(Integer.parseInt(s.substring(0, s.length() - 2)));
            if (c1 == 'l' || c1 == 'L')
                return newJBV(Long.parseLong(s.substring(0, s.length() - 2)));
            if (c1 == 'f' || c1 == 'F')
                return newJBV(Float.parseFloat(s.substring(0, s.length() - 2)));
            if (c1 == 'd' || c1 == 'D')
                return newJBV(Double.parseDouble(s.substring(0, s.length() - 2)));
            boolean d = false;
            for(int i = 1;i < s.length();i++)
            {
                char cx = s.charAt(i);
                if(cx == '.')
                    if(!d)
                    {
                        d = true;
                        continue;
                    }else
                    {
                        b = false;
                        break;
                    }
                if(cx < '0' || cx > '9')
                {
                    b = false;
                    break;
                }
                if(b)
                    if(d)
                        return newJBV(Double.parseDouble(s));
                    else
                        return newJBV(Integer.parseInt(s));
            }
        }
        return newJB(s,"NO",0);
    }
    /**
     * ����JB(ֵ)
     * @param value Object
     * @return JB
     */
    public JB newJBV(Object value)
    {
        JB jb = new JB();
        jb.s = "";
        jb.type = "V";
        jb.c = 0;
        jb.data = value;
        return jb;
    }
    /**
     * �Ƿ��ǹؼ���
     * @param s String
     * @return boolean
     */
    public boolean isKey(String s)
    {
        for(int i = 0;i < KEY.length;i++)
            if(s.equals(KEY[i]))
                return true;
        return false;
    }
    /**
     * �Ƿ��ǻ�������
     * @param s String
     * @return boolean
     */
    public boolean isBaseType(String s)
    {
        for(int i = 0;i < BASE_TYPE.length;i++)
            if(s.equals(BASE_TYPE[i]))
                return true;
        return false;
    }
    /**
     * ���з���
     * @param name String
     * @param parm Object[]
     * @return Object
     */
    public Object runFunction(String name,Object[] parm)
    {
        int index = name.indexOf(".");
        if(index > 0)
        {
            String sName = name.substring(0, index);
            String cName = name.substring(index + 1);
            Object obj = getS(sName);
            if(obj == null)
                //�쳣
                return null;
            if(obj instanceof TObject)
            {
                TObject tobject = (TObject)obj;
                return tobject.runM(getVM(),cName,parm);
            }
        }
        if(name.equals("out"))
        {
            System.out.println(StringTool.getString(parm));
            return null;
        }
        return null;
    }
    /**
     * ��������
     * @param className String
     * @param parm Object[]
     * @return Object
     */
    public Object newFunction(String className,Object[]parm)
    {
        if(!getVM().getClassLoad().load(className))
            return "����" + className + "ʧ��";
        TClass tClass = getVM().getClassLoad().get(className);
        if(tClass == null)
            return "����������";
        TObject tObject = getVM().getClassLoad().newObject(className);
        return tObject;
    }
    class JB
    {
        public String s;
        public String type;
        public int c;
        public Object data;
        public String toString()
        {
            return s + " " + type + " " + c + " " + data;
        }
        /**
         * ��¡
         * @return JB
         */
        public JB clone()
        {
            JB jb = new JB();
            jb.s = s;
            jb.type = type;
            jb.c = c;
            jb.data = data;
            return jb;
        }
    }
    class Script
    {
        public String name;
        public Object value1;
        public int index;
    }
    class Block
    {
        public int type;
        public int start;
        public int end;
        public String toString()
        {
            return "type=" + type + ",start=" + start + ",end=" + end;
        }
    }
}
