package unification;

import java.util.ArrayList;
import java.util.Arrays;

public class Unifier {
	public static void getMGU(String a, String b) {
		// TODO MGU Main
	}

	private static void getMGU() {
		// TODO MGU Helper
	}

	private static String stringListify(String sentence) {
		String substring = "";
		String res = "";
		int i;
		for (i = 0; i < sentence.length(); i++) {
			char c = sentence.charAt(i);
			if (c == '(') {
				break;
			} else if (c == ',') {
				substring = sentence.substring(0, i);
				if (!substring.isEmpty()) {
					res = substring + " ";
				}
				res += stringListify(sentence.substring(i + 1));
				return res;
			}
		}

		if (i == sentence.length())
			return sentence;

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
			res = stringListify(substring);

		substring = sentence.substring(i + 1, j - 1);
		substring = substring.trim();
		if (!substring.isEmpty())
			res = stringListify(substring).trim() + "] " + res;

		substring = sentence.substring(0, i);
		substring = substring.trim();
		if (!substring.isEmpty())
			res = "[" + substring + " " + res;
		return res.trim();
	}

	private static ArrayList<Object> listify1(String sentence) {
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

	private static Object[] listify(String sentence) {
		return (Object[]) (((Object[]) ListToArray(listify1(sentence)))[0]);
	}

	@SuppressWarnings("unchecked")
	private static Object ListToArray(Object l) {
		if (l instanceof String)
			return l;
		ArrayList<Object> li = (ArrayList<Object>) l;
		Object[] res = new Object[li.size()];
		for (int i = 0; i < li.size(); i++) {
			res[i] = ListToArray(li.get(i));
		}
		return res;
	}

	public static void main(String[] args) {
		System.out.println("=========================");
		System.out.println(stringListify("P(x,g(x),g(f(a)))"));
		System.out.println(Arrays.deepToString(listify("P(x,g(x),g(f(a)))")));
		System.out.println("=========================");

		System.out.println(stringListify("P(f(u),v,v)"));
		System.out.println(Arrays.deepToString(listify("P(f(u),v,v)")));
		System.out.println("=========================");

		System.out.println(stringListify("P(a,y,f(y))"));
		System.out.println(Arrays.deepToString(listify("P(a,y,f(y))")));
		System.out.println("=========================");

		System.out.println(stringListify("P(z,z,u)"));
		System.out.println(Arrays.deepToString(listify("P(z,z,u)")));
		System.out.println("=========================");

		System.out.println(stringListify("f(x,g(x),x)"));
		System.out.println(Arrays.deepToString(listify("f(x,g(x),x)")));
		System.out.println("=========================");

		System.out.println(stringListify("f(g(u),g(g(z)),z)"));
		System.out.println(Arrays.deepToString(listify("f(g(u),g(g(z)),z)")));
		System.out.println("=========================");

	}
}
