package com.dongyang.wcomponent.expression;

import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

import org.seraph.expression.ExpressionImpl;
import org.seraph.expression.Processor;


/**
 *
 * @author whaosoft
 *
 */
public class WExpression {


	/**  */
	static protected final String VALUE_ERROE = "NaN";

	/**
	 *
	 * @param expString
	 * @return
	 */
	static private ExpressionImpl getExpression(String expString) {

		Map<String, ExpressionImpl> expressionImpls = new HashMap<String, ExpressionImpl>();
		ExpressionImpl expr = expressionImpls.get(expString);
		if (expr == null) {
			expr = Processor.getExpression(expString);
			expr.setMathContext(MathContext.DECIMAL32);
			expressionImpls.put(expString, expr);
		}
		return expr;
	}

	/**
	 *
	 * @param expString
	 * @return
	 */
	static private String getResultValue(String expString) {

		try {
			ExpressionImpl expr = getExpression(expString);
			String result = expr.getResult().toString();
			long start = System.currentTimeMillis();
			//System.out.println(" @#@#@ 表达式返回- " + expr.getResultDataType()+ ":" + result);

			long end = System.currentTimeMillis();
			//System.out.println(" @#@#@ 表达式运行时间：" + (end - start) + "毫秒");
			//System.out.println(" @#@#@ 表达式内容- " + expString);

			return result;
		} catch (Exception e) {
			System.err.println("[表达式语法错误]-原因:" + e.toString());
			return VALUE_ERROE;
		}
	}

	/**
	 *
	 * @param expString
	 * @return
	 */
	static public String getResultNumValue(String expString){

		return getResultValue(expString+";");
	}


	/**
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

        //
		String str = getResultValue("((15+69)-90)*3/2;");
		System.out.println( "#1 "+ str );

		//平均数
		String str2 = getResultValue("avg(1,2,3,4,5,6);");
		System.out.println(  "#2 "+ str2 );

		String str3 = getResultNumValue("9/s+1-9");
		System.out.println(  "#3 "+ str3 );

		String str4 = getResultNumValue("（1+1)*7");
		System.out.println(  "#4 "+ str4 );

		String str5 = getResultNumValue("sum(1,2,3,10,90)");
		System.out.println(  "#5 "+ str5 );
	}

}
