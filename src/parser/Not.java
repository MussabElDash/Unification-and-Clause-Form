package parser;

import java.util.Set;

public class Not extends Formula {
	private Formula formula;

	public Not(Formula formula) {
		this.formula = formula;
	}

	@Override
	public Formula negate() {
		return formula;
	}

	@Override
	public String toString() {
		return "¬" + bracketize(formula);
	}

	@Override
	public Formula iffElimination() {
		return new Not(formula.iffElimination());
	}

	@Override
	public Formula impElimination() {
		return new Not(formula.impElimination());
	}

	@Override
	public Formula pushNegation() {
		return formula.negate();
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		return formula.standardize(vars);
	}

	@Override
	public Formula rename(String s, boolean toQuantifier) {
		return new Not(formula.rename(s, toQuantifier));
	}
}
