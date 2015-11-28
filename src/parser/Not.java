package parser;

import java.util.Set;

public class Not extends Sentence {
	private Sentence formula;

	public Not(Sentence formula) {
		this.formula = formula;
	}

	@Override
	public Sentence negate() {
		return formula;
	}

	@Override
	public String toString() {
		return "¬" + bracketize(formula);
	}

	@Override
	public Sentence iffElimination() {
		return new Not(formula.iffElimination());
	}

	@Override
	public Sentence impElimination() {
		return new Not(formula.impElimination());
	}

	@Override
	public Sentence pushNegation() {
		return formula.negate();
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		return formula.standardize(vars);
	}

	@Override
	public Sentence rename(String s, boolean toQuantifier) {
		return new Not(formula.rename(s, toQuantifier));
	}
}
