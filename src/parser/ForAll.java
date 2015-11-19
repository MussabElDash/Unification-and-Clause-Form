package parser;

public class ForAll extends Formula {
	private String[] vars;
	private Formula formula;

	public ForAll(String[] vars, Formula formula) {
		this.formula = formula;
		this.vars = vars;
	}

	public ForAll(String var, Formula formula) {
		this.formula = formula;
		this.vars = var.split(",");
	}

	@Override
	public Formula negate() {
		return new Exist(vars, formula.negate());
	}

	@Override
	public String toString() {
		String res = "∀" + vars[0];
		for (int i = 1; i < vars.length; i++) {
			res += "," + vars[i];
		}
		return res + "[" + formula.toString() + "]";
	}

	@Override
	public Formula iffElimination() {
		return new ForAll(vars, formula.iffElimination());
	}

	@Override
	public Formula impElimination() {
		return new ForAll(vars, formula.impElimination());
	}

	@Override
	public Formula pushNegation() {
		return new ForAll(vars, formula.pushNegation());
	}
}
