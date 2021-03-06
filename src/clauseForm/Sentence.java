package clauseForm;

import java.util.Set;

import parser.Parser;

public abstract class Sentence {
	public static char AND = '∧', OR = '∨', NOT = '¬';
	public static char FORALL = '∀', EXIST = '∃', IFF = '⇔', IMPLIES = '⇒';

	public abstract Sentence negate();

	@Override
	abstract public String toString();

	public static String bracketize(Sentence a) {
		String res;
		if (a instanceof Predicate || a instanceof Not || a instanceof Exist || a instanceof ForAll)
			res = a.toString();
		else {
			res = "(" + a.toString() + ")";
		}
		return res;
	}

	public abstract Sentence iffElimination();

	public abstract Sentence impElimination();

	public abstract Sentence pushNegation();

	public abstract Set<String> standardize(Set<String> vars);

	public abstract Sentence rename(String s, boolean toQuantifier);

	public abstract Sentence renameSkolemize(String s, Set<String> skolems, boolean toQuantitifer);

	public abstract Sentence[] getFormulas();

	public Sentence skolemize(Set<String> vars) {
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			formulas[i] = formulas[i].skolemize(vars);
		}
		return this;
	}

	public Sentence distribute() {
		return this;
	}

	public abstract String getString();

	public Sentence discardForAll() {
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			formulas[i] = formulas[i].discardForAll();
		}
		return this;
	}

	public boolean isSingle() {
		return this instanceof Predicate || this instanceof Not;
	}

	public static String toFunction(String var, Set<String> skolems) {
		String functionSymbol;
		if (Parser.varTosym.containsKey(var)) {
			functionSymbol = Parser.varTosym.get(var);
		} else {
			functionSymbol = Parser.functionSymbols.get(0);
			Parser.functionSymbols.remove(0);
			Parser.varTosym.put(var, functionSymbol);
		}
		Parser.functionSymbols.remove(0);
		String s = functionSymbol + "(";
		for (String skolem : skolems) {
			s += skolem + ", ";
		}
		s = s.substring(0, s.length() - 2);
		s += ")";
		return s;
	}

	public String toCNF() {
		String cnf = "";
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			Sentence f = formulas[i];
			if (f.getClass() == And.class) {
				cnf += f.toCNF();

			} else {
				cnf += "∧ (" + f.toStringCNF() + ")\n";
			}
		}
		return cnf;
	}

	public String toClauseFormIII() {
		String cn3 = "";
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			if (formulas[i].getClass() == And.class) {
				cn3 += formulas[i].toClauseFormIII();
			} else {
				cn3 += "{" + formulas[i].standardize(Parser.StandardizationCounter) + "},\n";
				Parser.StandardizationCounter += 1;
			}
		}
		return cn3;
	}

	public String standardize(int sc) {
		return this.toString();
	}

	public String toClauseFormI() {
		String clauseFormI = "";
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			Sentence f = formulas[i];
			if (f.getClass() == And.class) {
				clauseFormI += f.toClauseFormI();

			} else {
				clauseFormI += "∧ {" + f.toStringClauseForm() + "}\n";
			}
		}
		return clauseFormI;
	}

	public String toClauseFormII() {
		String clauseFormII = "";
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			Sentence f = formulas[i];
			if (f.getClass() == And.class) {
				clauseFormII += f.toClauseFormII();

			} else {
				clauseFormII += "{" + f.toStringClauseForm() + "},\n";
			}
		}
		return clauseFormII;
	}

	public String toStringClauseForm() {
		return this.toString();
	}

	public String toStringCNF() {
		return this.toString();
	}
}
