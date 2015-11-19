package parser;

public class And extends Formula {
	private Formula[] formulas;

	public And(Formula[] formulas) {
		this.formulas = formulas;
	}

	@Override
	public Formula negate() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].negate();
		}
		return new Or(forms);
	}

	@Override
	public String toString() {
		if (formulas.length == 0)
			return "";
		String res = bracketize(formulas[0]);
		for (int i = 1; i < formulas.length; i++) {
			res += " ∧ " + bracketize(formulas[i]);
		}
		return res;
	}

	@Override
	public Formula iffElimination() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].iffElimination();
		}
		return new And(forms);
	}

	@Override
	public Formula impElimination() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].impElimination();
		}
		return new And(forms);
	}

	@Override
	public Formula pushNegation() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			// if (formulas[i] instanceof Not) {
			// System.out.println(formulas[i].pushNegation());
			// }
			forms[i] = formulas[i].pushNegation();
		}
		return new And(forms);
	}

}
