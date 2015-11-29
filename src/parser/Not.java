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
	
	public String toStringClauseForm(){
		return "¬" + formula.toStringClauseForm();
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

	public Sentence[] getFormulas() {
		Sentence[] formulas = new Sentence[] { formula };
		return formulas;
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sentence renameSkolemize(String s, Set<String> skolems, boolean toQuantifier) {
		return new Not(formula.renameSkolemize(s, skolems, toQuantifier));
	}

}
