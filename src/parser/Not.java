package parser;

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
}
