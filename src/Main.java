
import java.util.HashMap;
import java.util.HashSet;

import parser.Parser;
import parser.Sentence;
import unification.Unifier;

public class Main {

	public static void main(String[] args) {
		String s = "";
		Sentence f = null;
		// s = "P (x) ∧ Q(x) ∧ Q(y) ∧ R(y, x)";
		// f = parse("∀x,y[P(x,y)]" + Formula.AND + "P(z)");
//		s = Sentence.EXIST + "x, y[P(x, y)]" + Sentence.IMPLIES + "¬(P(z)" + Sentence.OR + Sentence.FORALL + "x,y[P(x,y)∧P(y)])";
//		 s = "(P(z)" + Sentence.OR + Sentence.FORALL + "x,y[P(x)])";
//		 s = "∃x[P(x)]∧∀x[Q(x)⇒¬P(x)]"; // Corrected From Project
//		 s = "∃x[P(x)∧∀x[Q(x)⇒¬P(x)]]"; // Project
//		 s = "∀x[P (x) ∧ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])]"; // Project
		 s = "∀x[P (x) ⇔ (Q(x) ∧ ∃y[Q(y) ∧ R(y, x)])] ∧ ∃z[P(z) ∧ ∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]]]";
//		 s = "∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]] ∧ ∃z[P(z) ∧ ∀x[Q(x) ∧ ∃y[Q(y) ∧ R(y, x)]]";
		System.out.println("Original String");
		System.out.println(s);
		System.out.println("=========================");

		f = Parser.parse(s);
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
//		System.out.println(f.getClass());
		
		System.out.println("\nCNF");
		System.out.println("==========================");
		String cnf = f.toCNF();
		cnf = cnf.substring(2, cnf.length()-2);
		cnf = "{" + cnf + "}";
		System.out.println(cnf);

		System.out.println("\nClause-Form");
		System.out.println("==========================");
		String clauseForm = f.toClauseForm();
		clauseForm = clauseForm.substring(0, clauseForm.length()-2);
		clauseForm = "{" + clauseForm + "}";
		System.out.println(clauseForm);
		
//		HashMap<String, String> map;
//		map = Unifier.getMGU("P(x,g(x),g(f(a)))", "P(f(u),v,v)");
//		check(map);
//		map = Unifier.getMGU("P(a,y,f(y))", "P(z,z,u)");
//		check(map);
//		map = Unifier.getMGU("f(x,g(x),x)", "f(g(u),g(g(z)),z)");
//		check(map);
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
