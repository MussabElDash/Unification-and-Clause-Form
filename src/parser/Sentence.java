package parser;

import java.util.Set;

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
	
	public Sentence discardForAll(){
		Sentence[] formulas = this.getFormulas();
		for(int i = 0; i< formulas.length; i++){
			formulas[i] = formulas[i].discardForAll();
		}
		return this;
	}
	
	public boolean isSingle() {
		return this instanceof Predicate || this instanceof Not;
	}
	
}
