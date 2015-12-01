
import java.util.HashMap;
import java.util.HashSet;

import parser.Parser;
import parser.Sentence;
import unification.Unifier;

public class Main {

	public static void main(String[] args) {
		testUnifier(true);
		String s = "";
		// s = "P (x) ∧ Q(x) ∧ Q(y) ∧ R(y, x)";
		// f = parse("∀x,y[P(x,y)]" + Formula.AND + "P(z)");
		// s = Sentence.EXIST + "x, y[P(x, y)]" + Sentence.IMPLIES + "¬(P(z)" +
		// Sentence.OR + Sentence.FORALL + "x,y[P(x,y)∧P(y)])";
		// s = "(P(z)" + Sentence.OR + Sentence.FORALL + "x,y[P(x)])";
		// s = "∃x[P(x)]∧∀x[Q(x)⇒¬P(x)]"; // Corrected From Project
		// s = "∃x[P(x)∧∀x[Q(x)⇒¬P(x)]]"; // Project
		// s = "∀x[P (x) ∧ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])]"; // Project
		s = "∀x[P (x) ⇔ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])] ∧ ∃z[P(z) ∧ ∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]]]";
		// s = "∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]] ∧ ∃z[P(z) ∧ ∀x[Q(x) ∧ ∃y[Q(y) ∧
		// R(y, x)]]";

		ClauseForm(s, true);
	}

	public static void ClauseForm(String sentence, boolean traceMode) {
		Sentence f = null;
		if (traceMode) {
			System.out.println("Original String");
			System.out.println(sentence);
			System.out.println("=========================");

			f = Parser.parse(sentence);
			System.out.println("Parsed Sentence");
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

			f = f.skolemize(new HashSet<String>());
			System.out.println("Skolemized");
			System.out.println(f);
			System.out.println("=========================");

			f = f.discardForAll();
			System.out.println("Dsicarded For All");
			System.out.println(f);
			System.out.println("==========================");

			f = f.distribute();
			System.out.println("Distributed");
			System.out.println(f);
			System.out.println("==========================");
			// System.out.println(f.getClass());

			System.out.println("\nCNF");
			System.out.println("==========================");
			String cnf = f.toCNF();
			cnf = cnf.substring(2, cnf.length() - 2);
			cnf = "(" + cnf + ")";
			System.out.println(cnf);

			System.out.println("\nClause-Form I");
			System.out.println("==========================");
			String clauseFormI = f.toClauseFormI();
			clauseFormI = clauseFormI.substring(2, clauseFormI.length() - 2);
			clauseFormI = "{" + clauseFormI + "}";
			System.out.println(clauseFormI);

		} else {
			f = Parser.parse(sentence);
			f = f.iffElimination();
			f = f.impElimination();
			f = f.pushNegation();
			f.standardize(new HashSet<String>());
			f = f.skolemize(new HashSet<String>());
			f = f.discardForAll();
			f = f.distribute();
		}
		System.out.println("\nClause-Form II");
		System.out.println("==========================");
		String clauseFormII = f.toClauseFormII();
		clauseFormII = clauseFormII.substring(0, clauseFormII.length() - 2);
		clauseFormII = "{" + clauseFormII + "}";
		System.out.println(clauseFormII);

	}

	public static void check(HashMap<String, String> map) {
		if (map != null) {
			System.out.println(map);
		} else {
			System.out.println("fail");
		}
		System.out.println("========================================");
	}

	private static void testUnifier(boolean trace) {
		Unifier.setTrace(trace);
		HashMap<String, String> map;
		map = Unifier.getMGU("P(x,g(x),g(f(A)))", "P(f(u),v,v)");
		check(map);
		map = Unifier.getMGU("P(A,y,f(y))", "P(z,z,u)");
		check(map);
		map = Unifier.getMGU("f(x,g(x),x)", "f(g(u),g(g(z)),z)");
		check(map);
	}

}
