package parser;

import java.util.HashSet;
import java.util.Set;

public class Or extends Formula {
	private Formula[] formulas;

	public Or(Formula[] formulas) {
		this.formulas = formulas;
	}

	@Override
	public Formula negate() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].negate();
		}
		return new And(forms);
	}

	@Override
	public String toString() {
		if (formulas.length == 0)
			return "";
		String res = bracketize(formulas[0]);
		for (int i = 1; i < formulas.length; i++) {
			res += " ∨ " + bracketize(formulas[i]);
		}
		return res;
	}

	@Override
	public Formula iffElimination() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].iffElimination();
		}
		return new Or(forms);
	}

	@Override
	public Formula impElimination() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].impElimination();
		}
		return new Or(forms);
	}

	@Override
	public Formula pushNegation() {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			// if (formulas[i] instanceof Not) {
			// System.out.println(formulas[i]);
			// System.out.println(formulas[i].pushNegation());
			// }
			forms[i] = formulas[i].pushNegation();
		}
		return new Or(forms);
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		HashSet<String> res = new HashSet<String>(vars);
		for (int i = 0; i < formulas.length; i++) {
			Formula f = formulas[i];
			for (String s : res) {
				f = f.rename(s, false);
			}
			Set<String> temp = f.standardize(new HashSet<String>());
			res.addAll(temp);
			formulas[i] = f;
		}
		return res;
	}

	@Override
	public Formula rename(String s, boolean toQuantifier) {
		Formula[] forms = new Formula[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].rename(s, toQuantifier);
		}
		return new Or(forms);
	}

}
