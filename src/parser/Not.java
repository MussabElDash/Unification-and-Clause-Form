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
}
