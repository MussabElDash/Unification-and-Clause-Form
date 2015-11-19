package parser;

import java.util.ArrayList;
import java.util.HashSet;

public class Parser {
	public static Formula parse(String formula) {
		formula = formula.replaceAll(" ", "");
		int i, j, count = 0;
		char connector = 0;
		// System.out.println(formula);
		ArrayList<Formula> formulas = new ArrayList<Formula>();
		for (i = 0; i < formula.length(); i++) {
			char c = formula.charAt(i);
			// System.out.println(c);
			if (c == Formula.FORALL) {
				info infos = forAll(i, formula);
				if (i == 0 && infos.j == formula.length())
					return infos.formula;
				formulas.add(infos.formula);
				i = infos.j - 1;
			} else if (c == Formula.EXIST) {
				info infos = exist(i, formula);
				if (i == 0 && infos.j == formula.length())
					return infos.formula;
				formulas.add(infos.formula);
				i = infos.j - 1;
			} else if (Character.isUpperCase(c)) {
				for (j = i + 1; j < formula.length(); j++)
					if (formula.charAt(j) == '(') {
						count = 1;
						break;
					}
				for (j = j + 1; j < formula.length() && count != 0; j++) {
					if (formula.charAt(j) == '(')
						count++;
					if (formula.charAt(j) == ')')
						count--;
				}
				if (i == 0 && j == formula.length())
					break;
				formulas.add(parse(formula.substring(i, j)));
				i = j - 1;
			} else if (c == '(') {
				count = 1;
				for (j = i + 1; j < formula.length() && count != 0; j++) {
					if (formula.charAt(j) == '(')
						count++;
					if (formula.charAt(j) == ')')
						count--;
				}
				if (i == 0 && j == formula.length())
					return parse(formula.substring(i + 1, j - 1));
				formulas.add(parse(formula.substring(i + 1, j - 1)));
				i = j - 1;
			} else if (c == Formula.NOT) {
				j = getNotNextIndex(i, formula);
				String substring = formula.substring(i + 1, j);
				if (i == 0 && j == formula.length())
					return new Not(parse(substring));
				formulas.add(new Not(parse(substring)));
				i = j;
			} else if (c == Formula.AND) {
				connector = Formula.AND;
			} else if (c == Formula.OR) {
				connector = Formula.OR;
			} else if (c == Formula.IMPLIES) {
				connector = Formula.IMPLIES;
				formulas.add(parse(formula.substring(i + 1)));
				break;
			} else if (c == Formula.IFF) {
				connector = Formula.IFF;
				formulas.add(parse(formula.substring(i + 1)));
				break;
			}
		}
		Formula[] forms = formulas.toArray(new Formula[formulas.size()]);
		if (connector == Formula.AND)
			return new And(forms);
		else if (connector == Formula.OR) {
			return new Or(forms);
		} else if (connector == Formula.IMPLIES)
			return new Implies(forms[0], forms[1]);
		else if (connector == Formula.IFF)
			return new Iff(forms[0], forms[1]);
		// System.out.println(formula);
		return new Predicate(formula);
	}

	private static int getNotNextIndex(int i, String formula) {
		char c = formula.charAt(++i);
		if (c == Formula.EXIST || c == Formula.FORALL) {
			int count = 1;
			for (; i < formula.length(); i++)
				if (formula.charAt(i) == '[')
					break;
			for (i++; i < formula.length() && count != 0; i++) {
				if (formula.charAt(i) == '[')
					count++;
				if (formula.charAt(i) == ']')
					count--;
			}
			return i;
		} else if (Character.isUpperCase(c)) {
			for (; i < formula.length(); i++)
				if (formula.charAt(i) == '(')
					break;
		}
		int count = 1;
		for (i++; i < formula.length() && count != 0; i++) {
			if (formula.charAt(i) == '[')
				count++;
			if (formula.charAt(i) == ']')
				count--;
		}
		return i;
	}

	private static info exist(int i, String formula) {
		info infos = helperExistForAll(i, formula);
		String substring = infos.substring;
		String[] vars = infos.vars;
		infos.formula = new Exist(vars, parse(substring));
		return infos;
	}

	private static info helperExistForAll(int i, String formula) {
		int k, j, count = 1;
		String substring = "";
		for (k = i; k < formula.length(); k++) {
			if (formula.charAt(k) == '[') {
				break;
			}
		}
		for (j = k + 1; j < formula.length() && count != 0; j++) {
			if (formula.charAt(j) == '[')
				count++;
			if (formula.charAt(j) == ']')
				count--;
		}
		substring = formula.substring(k + 1, j - 1);
		String[] vars = formula.substring(i + 1, k).split(",");
		info inf = new info();
		inf.j = j;
		inf.substring = substring;
		inf.vars = vars;
		return inf;
	}

	private static info forAll(int i, String formula) {
		info infos = helperExistForAll(i, formula);
		String substring = infos.substring;
		String[] vars = infos.vars;
		infos.formula = new ForAll(vars, parse(substring));
		return infos;
	}

	private static class info {
		String substring;
		String[] vars;
		int j;
		Formula formula;
	}

	public static void main(String[] args) {
		String s;
		Formula f = null;
		// f = parse("∀x,y[P(x,y)]" + Formula.AND + "P(z)");
//		s = Formula.EXIST + "x, y[P(x, y)]" + Formula.IMPLIES + "¬(P(z)" + Formula.OR + Formula.FORALL
//				+ "x,y[P(x,y)∧P(y)])";
//		 s = "(P(z)" + Formula.OR + Formula.FORALL + "x,y[P(x)])";
//		 s = "∃x[P(x)]∧∀x[Q(x)⇒¬P(x)]"; // Corrected From Project
//		 s = "∃x[P(x)∧∀x[Q(x)⇒¬P(x)]]"; // Project
		 s = "∀x[P (x) ⇔ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])]"; // Project
//		 s = "∀x[P (x) ⇔ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])] ∧ ∃z[P(z) ∧ ∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]]]";
//		 s = "∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]] ∧ ∃z[P(z) ∧ ∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]]]";
		System.out.println("Original String");
		System.out.println(s);
		System.out.println("=========================");

		f = parse(s);
		System.out.println("Parsed Formula");
		System.out.println(f);
		System.out.println("=========================");

		f = f.iffElimination();
		System.out.println("Elimenated ⇔");
		System.out.println(f);
		System.out.println("=========================");

		f = f.impElimination();
		System.out.println("Elimenated ⇒");
		System.out.println(f);
		System.out.println("=========================");

		f = f.pushNegation();
		System.out.println("Pushed Negation");
		System.out.println(f);
		System.out.println("=========================");
		
		f.standardize(new HashSet<String>());
		System.out.println("Standardized");
		System.out.println(f);
		System.out.println("=========================");
		System.out.println(f.getClass());
	}
}
