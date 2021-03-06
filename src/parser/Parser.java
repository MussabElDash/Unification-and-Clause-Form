package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import clauseForm.And;
import clauseForm.Exist;
import clauseForm.ForAll;
import clauseForm.Iff;
import clauseForm.Implies;
import clauseForm.Not;
import clauseForm.Or;
import clauseForm.Predicate;
import clauseForm.Sentence;

public class Parser {
	public static ArrayList<String> functionSymbols = new ArrayList<String>(Arrays.asList("f", "g", "h", "i", "j", "k","l","m","n","o","s","t"));
	public static ArrayList<String> constans = new ArrayList<String>(Arrays.asList("a", "b", "c", "d", "e", "p", "q", "r", "u", "v", "w", "x", "y", "z"));
	public static HashMap<String,String> varTosym = new HashMap<String,String>();
	public static int StandardizationCounter = 1;
	
	public static Sentence parse(String formula) {
		formula = formula.replaceAll(" ", "");
		int i, j, count = 0;
		char connector = 0;
		// System.out.println(formula);
		ArrayList<Sentence> formulas = new ArrayList<Sentence>();
		for (i = 0; i < formula.length(); i++) {
			char c = formula.charAt(i);
			// System.out.println(c);
			if (c == Sentence.FORALL) {
				info infos = forAll(i, formula);
				if (i == 0 && infos.j == formula.length())
					return infos.formula;
				formulas.add(infos.formula);
				i = infos.j - 1;
			} else if (c == Sentence.EXIST) {
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
			} else if (c == Sentence.NOT) {
				j = getNotNextIndex(i, formula);
				String substring = formula.substring(i + 1, j);
				if (i == 0 && j == formula.length())
					return new Not(parse(substring));
				formulas.add(new Not(parse(substring)));
				i = j;
			} else if (c == Sentence.AND) {
				connector = Sentence.AND;
			} else if (c == Sentence.OR) {
				connector = Sentence.OR;
			} else if (c == Sentence.IMPLIES) {
				connector = Sentence.IMPLIES;
				formulas.add(parse(formula.substring(i + 1)));
				break;
			} else if (c == Sentence.IFF) {
				connector = Sentence.IFF;
				formulas.add(parse(formula.substring(i + 1)));
				break;
			}
		}
		Sentence[] forms = formulas.toArray(new Sentence[formulas.size()]);
		if (connector == Sentence.AND)
			return And.getFormula(0, forms);
		else if (connector == Sentence.OR) {
			return Or.getFormula(0, forms);
		} else if (connector == Sentence.IMPLIES)
			return new Implies(forms[0], forms[1]);
		else if (connector == Sentence.IFF)
			return new Iff(forms[0], forms[1]);
		// System.out.println(formula);
		return new Predicate(formula);
	}

	private static int getNotNextIndex(int i, String formula) {
		char c = formula.charAt(++i);
		if (c == Sentence.EXIST || c == Sentence.FORALL) {
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
		Sentence formula;
	}

	public static void main(String[] args) {

	}
}
