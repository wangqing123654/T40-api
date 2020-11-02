package com.dongyang.ui.util;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Vector;

public class Compare implements Comparator {

	private boolean des;

	private int col;

	public Compare() {
		this(true, 0);
	}

	public Compare(boolean des, int col) {
		this.des = des;
		this.col = col;
	}

	public int compare(Object o1, Object o2) {
		//System.out.println(o1.getClass()+"==o1==="+o1);
		//System.out.println(o1.getClass()+"==o2==="+o2);
		int result = 0;
		if (!(o1 instanceof Vector) && !(o2 instanceof Vector)) {
			return -1;
		} else {
			Vector l1 = (Vector) o1;
			Vector l2 = (Vector) o2;
			//System.out.println("=====l1======"+l1);
			//System.out.println("=====l2======"+l2);
			
			Object oo1 = l1.get(col) ;//[col];
			Object oo2 = l2.get(col);//[oo2]
			//System.out.println("====oo1===="+oo1);
			//System.out.println("====oo2===="+oo2);
			//System.out.println("====oo1 class===="+oo1.getClass());
			//System.out.println("====oo2 class===="+oo2.getClass());
			
			if(oo1==null){
				oo1="";
			}
			if(oo2==null){
				oo2="";
			}
			if (oo1.getClass() == String.class
					&& oo2.getClass() == String.class) {
				String obj1 = (String) oo1;
				String obj2 = (String) oo2;
				result = obj1.compareTo(obj2);
			} else if (oo1.getClass() == Double.class
					&& oo2.getClass() == Double.class) {
				Double obj1 = (Double) oo1;
				Double obj2 = (Double) oo2;
				double t = obj1 - obj2;
				if (t > 0.00000) {
					result = 1;
				} else if (t < 0.00000) {
					result = -1;
				}
			} else if(oo1.getClass() == Integer.class&& oo2.getClass() == Integer.class){
				Integer obj1 = (Integer) oo1;
				Integer obj2 = (Integer) oo2;
				result = obj1 - obj2;
			} else if(oo1.getClass() == Timestamp.class&& oo2.getClass() == Timestamp.class){
				Timestamp obj1 = (Timestamp) oo1;
				Timestamp obj2 = (Timestamp) oo2;
				if(obj1.getTime()>obj2.getTime()){
					result = 1;
				}else{
					result = -1;
				}
				
			}else {
				String obj1="";
				try{
				  obj1 = (String) oo1;
				}catch(Exception e){
					
				}
				String obj2="";
				try{
				 obj2 = (String) oo2;
				}catch(Exception e){
					
				}
				result = obj1.compareTo(obj2);
			}
		}
		if (!des) {
			result = -result;
		}
		return result;
	}

	public boolean isDes() {
		return des;
	}

	public void setDes(boolean des) {
		this.des = des;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

}
