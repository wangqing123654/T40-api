package com.dongyang.wcomponent.util;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

/**
 *
 * @author whaosoft
 *
 */
@SuppressWarnings( { "unchecked", "unused" })
public class TiString {

	static String[] nUnit = { "·Ö", "½Ç", "Ôª", "Ê°", "°Û", "Çª", "Íò", "Ê°", "°Û", "Çª",
			"ÒÚ", "Ê°", "°Û", "Çª", "Õ×" };

	static String[] nWords = { "Áã", "Ò¼", "·¡", "Èþ", "ËÁ", "Îé", "Â½", "Æâ", "°Æ", "¾Á" };
	String string;

	public TiString(String string) {
		this.string = string;
	}

	public TiString() {
		this.string = "";
	}

	public static String Big5ToISO88591(String text) {
		try {
			return new String(text.getBytes("Big5"), "ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
		}
		return text;
	}

	public static String[] breakNFixRow(String src, int bre, int fix) {
		return fixRow(breakRow(src, bre), fix);
	}

	public static String breakRow(String src, int size, int shift) {
		StringBuffer tmp = new StringBuffer("");
		tmp.append(space(shift));
		int i = 0;
		int len = 0;
		for (; i < src.length(); i++) {
			char c = src.charAt(i);
			len += getCharSize(c);
			if ("\n".equals(String.valueOf(c))) {
				tmp.append(c);
				tmp.append(space(shift));
				len = 0;
			} else if (size >= len) {
				tmp.append(c);
			} else {
				tmp.append("\n");
				tmp.append(space(shift));
				tmp.append(c);
				len = getCharSize(c);
			}
		}
		return tmp.toString();
	}

	public static String breakRow(String src, int size) {
		return breakRow(src, size, 0);
	}

	public static int countRow(String src) {
		int counter = 1;
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if ("\n".equals(String.valueOf(c))) {
				counter++;
			}
		}
		return counter;
	}

	public static String delLeft(String src, char c) {
		StringBuffer tmp = new StringBuffer(src.trim());
		while (tmp.length() > 0) {
			if (tmp.charAt(0) != c)
				break;
			tmp.deleteCharAt(0);
		}
		return tmp.toString();
	}

	public static String delRight(String src, char c) {
		StringBuffer tmp = new StringBuffer(src.trim());
		while (tmp.length() > 0) {
			if (tmp.charAt(tmp.length() - 1) != c)
				break;
			tmp.deleteCharAt(tmp.length() - 1);
		}
		return tmp.toString();
	}

	public String doubleForamt(String s, int d) {
		int i = s.indexOf(".");
		String s0 = s.substring(0, i);
		String s1 = s.substring(i + 1, s.length());
		if (s1.length() > d)
			return s0 + "." + s1.substring(0, d);
		if (s1.length() < d) {
			int len = d - s1.length();
			for (int j = 0; j < len; j++) {
				s1 = s1 + "0";
			}
			return s0 + "." + s1;
		}
		return s;
	}

	public String doubleForamt(double d, int i) {
		return doubleForamt(String.valueOf(d), i);
	}

	public static String fillLeft(String src, int len, char c) {
		if (len < src.length())
			return src;
		StringBuffer tmp = new StringBuffer(src.trim());
		for (int i = 0; i < len - src.length(); i++) {
			tmp.insert(0, c);
		}
		return tmp.toString();
	}

	public static String fillRight(String src, int len, char c) {
		if (len < src.length())
			return src;
		StringBuffer tmp = new StringBuffer(src.trim());
		for (int i = 0; i < len - src.length(); i++) {
			tmp.append(c);
		}
		return tmp.toString();
	}

	public static String[] fixRow(String string, int size) {
		Vector splitVector = new Vector();
		int index = 0;
		int separatorCount = 0;
		for (int i = 0; i < string.length(); i++) {
			char c = string.charAt(i);
			if ("\n".equals(String.valueOf(c))) {
				separatorCount++;
				if (separatorCount >= size) {
					splitVector.add(string.substring(index, i));
					index = i + 1;
					separatorCount = 0;
				}
			}
		}
		splitVector.add(string.substring(index, string.length()));
		String[] splitArray = new String[splitVector.size()];
		for (int j = 0; j < splitVector.size(); j++) {
			splitArray[j] = ((String) splitVector.get(j));
		}
		return splitArray;
	}

	public static String fixSize(String src, int size) {
		StringBuffer tmp = new StringBuffer("");
		int i = 0;
		int len = 0;
		for (; i < src.length(); i++) {
			char c = src.charAt(i);
			len += getCharSize(c);
			if (size >= len) {
				tmp.append(c);
			} else {
				len -= getCharSize(c);
				break;
			}
		}
		while (size > len) {
			tmp.append(' ');
			len++;
		}
		return tmp.toString();
	}

	public static int getCharSize(char c) {
		return new String(new char[] { c }).getBytes().length;
	}

	public static int getSeparatorCount(String string, String separator) {
		int index = -1;
		int separatorCount = 0;
		String temp = string;
		for (int i = 0; i < string.length();) {
			index = string.indexOf(separator, i);
			if (index == -1)
				break;
			temp = string.substring(i, index);
			separatorCount++;
			i = separator.length() + index;
		}

		return separatorCount;
	}

	public static int[] getSeparatorIndex(String string, String separator) {
		Vector indexVector = new Vector();

		int index = -1;
		int separatorCount = 0;
		String temp = string;
		for (int i = 0; i < string.length();) {
			index = string.indexOf(separator, i);
			if (index == -1)
				break;
			temp = string.substring(i, index);
			separatorCount++;
			i = separator.length() + index;
			indexVector.add(new Integer(i - 1));
		}

		int[] indexArray = new int[indexVector.size()];
		for (int j = 0; j < indexVector.size(); j++) {
			indexArray[j] = ((Integer) indexVector.get(j)).intValue();
		}
		return indexArray;
	}

	public static void main(String[] avgs) {
		System.out.println(numberToWord(100000.0D));
	}

	public static String numberToWord(double num) {
		StringBuffer result = new StringBuffer();
		boolean isPlus = num >= 0.0D;
		num = Math.abs(num);
		num = Math.round(num * 100.0D);
		int tmp = -1;
		for (int i = 0; i < 15; i++) {
			int x = (int) (num % Math.pow(10.0D, i + 1) / Math.pow(10.0D, i));
			if ((int) Math.pow(10.0D, i) > num) {
				break;
			}
			if (x == 0) {
				if ((i == 2) || (i == 6) || (i == 10) || (i == 14))
					result.insert(0, nUnit[i]);
				else if (i == 1) {
					if (tmp != 0)
						result.insert(0, nWords[x] + nUnit[i]);
				} else if (i != 0) {
					if (tmp != 0)
						result.insert(0, nWords[x]);
				}
			} else {
				result.insert(0, nWords[x] + nUnit[i]);
			}
			tmp = x;
		}
		if (!isPlus)
			result.insert(0, "¸º");
		return result.toString() + "Õû";
	}

	public static String replace(String src, String find, String repl) {
		StringBuffer sb = new StringBuffer(src);
		int index = src.indexOf(find);
		while (index != -1) {
			sb.delete(index, index + find.length());
			sb.insert(index, repl);
			index = sb.toString().indexOf(find, index + repl.length());
		}
		return sb.toString();
	}

	public static String replaceAt(String src, int at, String ins) {
		StringBuffer sb = new StringBuffer(src);
		try {
			sb.replace(at, ins.length(), ins);
			return sb.toString();
		} catch (Exception e) {
		}
		return src;
	}

	public static String shift(String src, int shift) {
		StringBuffer tmp = new StringBuffer("");
		tmp.append(space(shift));
		for (int i = 0; i < src.length(); i++) {
			char c = src.charAt(i);
			if ("\n".equals(String.valueOf(c))) {
				tmp.append(c);
				tmp.append(space(shift));
			} else {
				tmp.append(c);
			}
		}
		return tmp.toString();
	}

	public static String space(int n) {
		StringBuffer tmp = new StringBuffer("");
		for (int i = 0; i < n; i++) {
			tmp.append(' ');
		}
		return tmp.toString();
	}

	public static String[] split(String string, String separator) {
		Vector splitVector = new Vector();
		int index = -1;
		int separatorCount = 0;
		String temp = string;
		for (int i = 0; i < string.length();) {
			index = string.indexOf(separator, i);

			if (index != -1) {
				temp = string.substring(i, index);
				separatorCount++;
				splitVector.add(temp);

				i = separator.length() + index;
			} else {
				temp = string.substring(i, string.length());

				splitVector.add(temp);
				break;
			}
			if (index == string.length() - 1) {
				splitVector.add("");
			}
		}

		String[] splitArray = new String[splitVector.size()];
		for (int j = 0; j < splitVector.size(); j++) {
			splitArray[j] = ((String) splitVector.get(j));
		}
		return splitArray;
	}

	public static String toBig5(String text) {
		try {
			return new String(text.getBytes("ISO-8859-1"), "Big5");
		} catch (UnsupportedEncodingException e) {
		}
		return text;
	}

	public static String toSql(String src) {
		return replace(src, "'", "''");
	}


	/**
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {

		String str = String.valueOf(obj);

		return (obj == null) || (str.trim().length() <= 0)
				|| (str.equals("null"));
	}

	/**
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

	/**
	 *
	 * @param o
	 * @return
	 */
	public static Object trim(Object o) {

		if ((o != null) && (o.toString().trim().length() > 0))
			return o.toString().trim();
		return o;
	}

}