package unification;

import java.util.Arrays;
import java.util.HashMap;

import clauseForm.Predicate;

public class Unifier {
	private static boolean trace;

	public static void setTrace(boolean t) {
		trace = t;
	}

	public static HashMap<String, String> getMGU(String a, String b) {
		Object o1 = Predicate.listify(a);
		Object o2 = Predicate.listify(b);
		HashMap<String, Object> map = getMGU(o1, o2, new HashMap<String, Object>());
		if (map == null)
			return null;
		HashMap<String, String> res = new HashMap<String, String>();
		for (String s : map.keySet()) {
			res.put(s, Predicate.arraysToString(map.get(s)));
		}
		return res;
	}

	private static HashMap<String, Object> getMGU(Object o1, Object o2, HashMap<String, Object> map) {
		if (map == null)
			return null;
		if (eq(o1, o2))
			return map;
		if (isVar(o1))
			return unifyVar((String) o1, o2, map);
		if (isVar(o2))
			return unifyVar((String) o2, o1, map);
		if (isAtom(o1) || isAtom(o2))
			return null;
		Object[] arr1 = (Object[]) o1;
		Object[] arr2 = (Object[]) o2;
		if (arr1.length != arr2.length)
			return null;
		if (trace)
			System.out.println("Unifying: " + Arrays.deepToString(arr1) + "\twith\t" + Arrays.deepToString(arr2));
		return getMGU(Predicate.rest(arr1), Predicate.rest(arr2),
				getMGU(Predicate.first(arr1), Predicate.first(arr2), map));
	}

	private static boolean eq(Object a, Object b) {
		if (a instanceof String && b instanceof String)
			return a.equals(b);
		else if (a instanceof String)
			return false;
		else if (b instanceof String)
			return false;
		Object[] a1 = (Object[]) a;
		Object[] b1 = (Object[]) b;
		return Arrays.deepEquals(a1, b1);
	}

	private static boolean isVar(Object o) {
		if (o instanceof Object[])
			return false;
		String s = (String) o;
		return s.toLowerCase().equals(s);
	}

	private static boolean isAtom(Object o) {
		if (o instanceof Object[])
			return false;
		String s = (String) o;
		return Character.toUpperCase(s.charAt(0)) == s.charAt(0);
	}

	private static Object subst(HashMap<String, Object> map, Object o) {
		if (o instanceof String) {
			Object res = map.get(o);
			if (res != null)
				return res;
			return o;
		}
		Object[] arr = (Object[]) o;
		Object[] res = new Object[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = subst(map, arr[i]);
		}
		return res;
	}

	private static HashMap<String, Object> unifyVar(String var, Object e, HashMap<String, Object> map) {
		Object o = map.get(var);
		if (o != null && !o.equals(var)) {
			return getMGU(o, e, map);
		}
		o = subst(map, e);
		if (occurs(var, o)) {
			return null;
		}
		HashMap<String, Object> res = new HashMap<String, Object>();
		res.putAll(map);
		res.put(var, o);
		return res;
	}

	private static boolean occurs(String s, Object e) {
		if (e instanceof String)
			return s.equals(e);
		Object[] arr = (Object[]) e;
		for (int i = 0; i < arr.length; i++) {
			if (occurs(s, arr[i]))
				return true;
		}
		return false;
	}

	public static void main(String[] args) {
		// System.out.println("=========================");
		// System.out.println(stringListify("P(x,g(x),g(f(a)))"));
		// System.out.println(Arrays.deepToString(listify("P(x,g(x),g(f(a)))")));
		// System.out.println("=========================");
		//
		// System.out.println(stringListify("P(f(u),v,v)"));
		// System.out.println(Arrays.deepToString(listify("P(f(u),v,v)")));
		// System.out.println("=========================");
		//
		// System.out.println(stringListify("P(a,y,f(y))"));
		// System.out.println(Arrays.deepToString(listify("P(a,y,f(y))")));
		// System.out.println("=========================");
		//
		// System.out.println(stringListify("P(z,z,u)"));
		// System.out.println(Arrays.deepToString(listify("P(z,z,u)")));
		// System.out.println("=========================");
		//
		// System.out.println(stringListify("f(x,g(x),x)"));
		// System.out.println(Arrays.deepToString(listify("f(x,g(x),x)")));
		// System.out.println("=========================");
		//
		// System.out.println(stringListify("f(g(u),g(g(z)),z)"));
		// System.out.println(Arrays.deepToString(listify("f(g(u),g(g(z)),z)")));
		// System.out.println("=========================");

		// System.out.println(isVar(listify("P(x,g(x),g(f(a)))")));
		// System.out.println(isVar("Hello"));
		// System.out.println(isVar("hello"));
		// System.out.println(isVar("hellO"));
		// System.out.println("=========================");
		// System.out.println(isAtom(listify("P(x,g(x),g(f(a)))")));
		// System.out.println(isAtom("Hello"));
		// System.out.println(isAtom("hello"));
		// System.out.println(isAtom("hellO"));

		// HashMap<String, Object> map = new HashMap<String, Object>();
		// Object o;
		//
		// o = listify("f(y)");
		// map.put("x", o);
		// o = listify("P(x,g(x),g(f(a)))");
		// o = subst(map, o);
		// System.out.println(Arrays.deepToString((Object[]) o));
		// Object[] a = new Object[] { 1, 2 }, b = new Object[] { 1, 2 };
		// System.out.println(a == b);
		// System.out.println(a.equals(b));
		// System.out.println(Arrays.deepEquals(a, b));

		// HashMap<String, Object> map;
		// map = getMGU("P(x,g(x),g(f(a)))", "P(f(u),v,v)");
		// map = getMGU("P(a,y,f(y))", "P(z,z,u)");
		// map = getMGU("f(x,g(x),x)", "f(g(u),g(g(z)),z)");
		// if (map != null) {
		// System.out.println(map);
		// } else {
		// System.out.println("fail");
		// }

		// System.out.println(arraysToString(listify("P(x,g(x),g(f(a)))")));
	}
}
