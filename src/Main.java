
import java.util.HashMap;

import unification.Unifier;

public class Main {

	public static void main(String[] args) {
		HashMap<String, String> map;
		map = Unifier.getMGU("P(x,g(x),g(f(a)))", "P(f(u),v,v)");
		check(map);
		map = Unifier.getMGU("P(a,y,f(y))", "P(z,z,u)");
		check(map);
		map = Unifier.getMGU("f(x,g(x),x)", "f(g(u),g(g(z)),z)");
		check(map);
	}

	public static void check(HashMap<String, String> map) {
		System.out.println("========================================");
		if (map != null) {
			System.out.println(map);
		} else {
			System.out.println("fail");
		}
	}

}
