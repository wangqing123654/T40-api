package com.dongyang.wcomponent.expression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.dongyang.tui.text.ECapture;
import com.dongyang.tui.text.ECheckBoxChoose;
import com.dongyang.tui.text.EComponent;
import com.dongyang.tui.text.EFixed;
import com.dongyang.tui.text.ESingleChoose;
import com.dongyang.tui.text.MPage;
import com.dongyang.ui.TWord;

import com.dongyang.wcomponent.util.TiString;


/**
 *
 * @author whaosoft
 *
 */
public class ExpressionUtil {


	/**
	 * 处理单个表达式
	 * @param page
	 * @param ec
	 * @return
	 */
	static public boolean doProcessExpression(MPage page, EComponent ec){

		return ExpressionUtil.getInstance().doProcessSingleExpression(page, ec);
	}

	/**
	 * 处理全部表达式
	 * @param page
	 * @param expressionList
	 */
	static public void doProcessExpression(MPage page,List<EFixed> expressionList){

		ExpressionUtil.getInstance().doProcessAllExpression(page, expressionList);
	}

	/**
	 *
	 * @param page
	 * @param ec
	 * @return
	 */
	private boolean doProcessSingleExpression(MPage page, EComponent ec) {

		//System.out.println(" ~~~ processing " + ec);

		if ( ec instanceof EFixed ) {
			 EFixed ef = (EFixed) ec;

			 if ( ef.isExpression() ) {

				this.doProcessExpression(page, ef);
				page.reset();
				return true;
			}
		}

		return false;
	}


	/**
	 *
	 * @param page
	 * @param expressionList
	 * @return
	 */
	private void doProcessAllExpression(MPage page,List<EFixed> expressionList){

		Iterator<EFixed> it  = expressionList.iterator();

		AA: while( it.hasNext() ){

			EFixed efTmp = it.next();

			//
			if( efTmp.findIndex()<0 ){
				it.remove();
                continue AA;
			}else{

				this.doProcessExpression(page, efTmp);
			}
		}

		//
		page.reset();
		page.update();
	}

	/**
	 *
	 * @param page
	 * @param ef
	 */
	private void doProcessExpression(MPage page, EFixed ef) {

		String edesc = ef.getExpressionDesc();
		
		//System.out.println("----111edesc111-----"+edesc);
		if( TiString.isEmpty(edesc) ){
        	this.doProcessEFixed(ef, WExpression.VALUE_ERROE );
    		return;
		}

		//
		String sb = new StringBuilder(edesc).toString();

		List<String> list = this.getEComponent(ef);
		for( String name:list ){

             String cv = this.getValue(page, name);
             if( TiString.isEmpty(cv) ){
            	 //System.err.println( "<表达式解析错误> 控件'"+ name+"'值为空!" );

            	 //
            	this.doProcessEFixed(ef, WExpression.VALUE_ERROE );
        		return;
             }
             //System.out.println("["+name+"] 替换为：=="+cv);
             sb = sb.replace("["+name+"]", cv);
		}

		//
		this.doProcessEFixed(ef, WExpression.getResultNumValue(sb));
	}

    /**
     *
     * @param ef
     * @param str
     */
	private void doProcessEFixed(EFixed ef,String str){
		//System.out.println("--EFixed--"+ef.getName());
		//System.out.println("--str--"+str);
		ef.setText(str);
		ef.setFocusLast();
		ef.reset();
	}

	/**
	 *
	 * @param name
	 * @param withnotDelText
	 * @return
	 */
	private String getValue(MPage page, String name) {
		//
		ECapture capture = (ECapture) findObject(page, name,
				EComponent.CAPTURE_TYPE);
		if (capture != null) {
			return capture.getValue();
		}
		//
		ESingleChoose singleChoose = (ESingleChoose) findObject(page, name,
				EComponent.SINGLE_CHOOSE_TYPE);
		if (singleChoose != null) {
			return singleChoose.getSelectedValue();
		}		
		//
		ECheckBoxChoose checkBoxChoose = (ECheckBoxChoose) findObject(page, name,
				EComponent.CHECK_BOX_CHOOSE_TYPE);
		//
		if (checkBoxChoose != null) {
			//
			if(checkBoxChoose.isChecked()){
				return checkBoxChoose.getCbValue();
				//return "1";
			}
			return "0";
		}
		
		return null;
		//return capture.getValue();

	}

	/**
	 * 查找对象
	 *
	 * @param name
	 *            String 名称
	 * @param type
	 *            int 类型
	 * @return EComponent
	 */
	private EComponent findObject(MPage page, String name, int type) {
		return page.findObject(name, type);
	}

    /**
     * 是否存在此表达式
     * @param word
     * @param ef
     * @param eName
     * @return
     */
	static public boolean isExistExpression(TWord word, EFixed ef,String eName){

		return ExpressionUtil.getInstance().doIsExistExpression(word, ef,eName);
	}

	/**
	 *
	 * @param word
	 * @param ef
	 * @param eName
	 * @return
	 */
	private boolean doIsExistExpression(TWord word, EFixed ef,String eName){

		Iterator<EFixed> it  = word.getExpressionList().iterator();

		AA: while( it.hasNext() ){

			EFixed efTmp = it.next();

			//
			if( efTmp.findIndex()<0 ){
				it.remove();
                continue AA;
			}else{

				if( ! ( efTmp.getStart()==ef.getStart() &&
						efTmp.getEnd()==ef.getEnd() &&
						efTmp.getRowID()==ef.getRowID() ) ){
					//
	                if( efTmp.getName().equals( eName )){
	                	return true;
	                }
				}

			}
		}

		return false;
	}

	/**
	 *
	 * @param ef
	 * @return
	 */
    private List<String> getEComponent(EFixed ef){

    	List<String> list = new ArrayList<String>();

    	//
    	String regEx_script = "\\[(\\w*)\\]";

    	try{

    		Pattern pattern = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
    		Matcher matcher = pattern.matcher( ef.getExpressionDesc() );
    		while (matcher.find()) {
    			list.add( matcher.group(1) );
    		}

    	}catch (Exception e) {

    		ef.setText( WExpression.VALUE_ERROE );

    		System.err.println( "<表达式解析错误> 取得组件名称时错误,请检查'表达式 - "+ef.getName()+"'!" );
		}

    	return list;
    }

    /**
	 * 是否为表达式对象
	 * @param ec
	 * @return
	 */
	static public boolean isExpression(EComponent ec) {

		return ExpressionUtil.getInstance().doIsExpression(ec);
	}

    /**
	 *
	 * @param ec
	 * @return
	 */
    private boolean doIsExpression(EComponent ec) {

		if (ec instanceof EFixed) {
			EFixed ef = (EFixed) ec;
			if (ef.isExpression()) {
				return true;
			}
		}
		return false;
	}


	// ** singleton ** //

	/**
	 *
	 * @return
	 */
	static public ExpressionUtil getInstance() {

		return ExpressionUtilSub.expressionUtil;
	}

	/** */
	private ExpressionUtil() {
	}

	/** */
	static private class ExpressionUtilSub {
		static private ExpressionUtil expressionUtil = new ExpressionUtil();
	}

}
