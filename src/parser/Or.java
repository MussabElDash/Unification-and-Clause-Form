package parser;

import java.util.HashSet;
import java.util.Set;

public class Or extends Sentence {
	private Sentence[] formulas;

	public Or(Sentence[] formulas) {
		this.formulas = formulas;
	}

	@Override
	public Sentence negate() {
		Sentence[] forms = new Sentence[formulas.length];
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
	public Sentence iffElimination() {
		Sentence[] forms = new Sentence[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].iffElimination();
		}
		return new Or(forms);
	}

	@Override
	public Sentence impElimination() {
		Sentence[] forms = new Sentence[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].impElimination();
		}
		return new Or(forms);
	}

	@Override
	public Sentence pushNegation() {
		Sentence[] forms = new Sentence[formulas.length];
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
			Sentence f = formulas[i];
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
	public Sentence rename(String s, boolean toQuantifier) {
		Sentence[] forms = new Sentence[formulas.length];
		for (int i = 0; i < forms.length; i++) {
			forms[i] = formulas[i].rename(s, toQuantifier);
		}
		return new Or(forms);
	}

	@Override
	public Sentence distribute() {
		Sentence a = this.formulas[0];
		Sentence b = this.formulas[1];
		if ((a instanceof Predicate && b instanceof Predicate)
				|| (a instanceof Or && b instanceof Or)
				|| (a instanceof Or && b instanceof Predicate)) {
			return this;
		} else if (a instanceof Or) {
			Sentence b1 = b.getFormulas()[0];
			Sentence b2 = b.getFormulas()[1];
			return new And(
					new Sentence[] {
							new Or(new Sentence[] { a.distribute(),
									b1.distribute() }),
							new Or(new Sentence[] { a.distribute(),
									b2.distribute() }) });
		} else if (b instanceof Predicate) {
			Sentence a1 = a.getFormulas()[0];
			Sentence a2 = a.getFormulas()[1];
			return new And(
					new Sentence[] {
							new Or(new Sentence[] { a1.distribute(),
									b.distribute() }),
							new Or(new Sentence[] { a2.distribute(),
									b.distribute() }) });
		}
		System.out.println(">>> Distribution Error!");
		return null;
	}

}
