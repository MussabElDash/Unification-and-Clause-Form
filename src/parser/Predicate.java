package parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Predicate extends Formula {
	private String string;

	public Predicate(String s) {
		string = s;
	}

	@Override
	public Formula negate() {
		return new Not(this);
	}

	public static Object first(Object[] o) {
		if (o.length == 0)
			return null;
		return o[0];
	}

	public static Object[] rest(Object[] o) {
		if (o.length < 2)
			return new Object[] {};
		Object[] res = new Object[o.length - 1];
		for (int i = 1; i < o.length; i++) {
			res[i - 1] = o[i];
		}
		return res;
	}

	private static ArrayList<Object> listify1(String sentence) {
		sentence = sentence.trim();
		ArrayList<Object> res = new ArrayList<Object>();
		String substring = "";
		int i;
		for (i = 0; i < sentence.length(); i++) {
			char c = sentence.charAt(i);
			if (c == '(') {
				break;
			} else if (c == ',') {
				substring = sentence.substring(0, i);
				res = listify1(sentence.substring(i + 1));
				if (!substring.isEmpty()) {
					res.add(0, substring);
				}
				return res;
			}
		}

		if (i == sentence.length()) {
			res.add(sentence);
			return res;
		}

		int j = i + 1;
		for (int count = 1; j < sentence.length() && count != 0; j++) {
			char c = sentence.charAt(j);
			if (c == '(') {
				count++;
			} else if (c == ')') {
				count--;
			}
		}

		substring = sentence.substring(j);
		substring = substring.trim();
		if (!substring.isEmpty())
			res = listify1(substring);

		ArrayList<Object> temp = new ArrayList<Object>();
		substring = sentence.substring(i + 1, j - 1);
		substring = substring.trim();
		if (!substring.isEmpty())
			temp = listify1(substring);

		substring = sentence.substring(0, i);
		substring = substring.trim();
		if (!substring.isEmpty())
			temp.add(0, substring);
		if (!temp.isEmpty())
			res.add(0, temp);
		return res;

	}

	public static Object[] listify(String sentence) {
		return (Object[]) (((Object[]) ListToArray(listify1(sentence)))[0]);
	}

	private static Object ListToArray(Object l) {
		if (l instanceof String)
			return l;
		@SuppressWarnings("unchecked")
		ArrayList<Object> li = (ArrayList<Object>) l;
		Object[] res = new Object[li.size()];
		for (int i = 0; i < li.size(); i++) {
			res[i] = ListToArray(li.get(i));
		}
		return res;
	}

	public static String arraysToString(Object o) {
		if (o instanceof String)
			return (String) o;
		Object[] arr = (Object[]) o;
		if (arr.length == 0)
			return "";
		String res = (String) arr[0];
		res += "(" + arraysToString1(rest(arr));
		return res + ")";
	}

	private static String arraysToString1(Object[] a) {
		String res = arraysToString(a[0]);
		for (int i = 1; i < a.length; i++) {
			res += "," + arraysToString(a[i]);
		}
		return res;
	}

	@Override
	public String toString() {
		return string;
	}

	@Override
	public Formula iffElimination() {
		return this;
	}

	@Override
	public Formula impElimination() {
		return this;
	}

	@Override
	public Formula pushNegation() {
		return this;
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		return new HashSet<String>();
	}

	@Override
	public Formula rename(String st, boolean toQuantifier) {
		String s = string;
		s = s.replaceAll(st + "\\)", st + "')");
		s = s.replaceAll(st + ",", st + "',");
		return new Predicate(s);
	}

}
