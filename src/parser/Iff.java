package parser;

import java.util.HashSet;
import java.util.Set;

public class Iff extends Sentence {
	private Sentence a, b;

	public Iff(Sentence a, Sentence b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Sentence negate() {
		return new Not(this);
	}

	public Sentence iffElimination() {
		return new And(new Sentence[] { new Implies(a, b), new Implies(b, a) });
	}

	@Override
	public String toString() {
		return bracketize(a) + " ⇔ " + bracketize(b);
	}

	@Override
	public Sentence impElimination() {
		return this;
	}

	@Override
	public Sentence pushNegation() {
		return this;
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		return new HashSet<String>();
	}

	@Override
	public Sentence rename(String s, boolean toQuantifier) {
		return this;
	}
	
	public Sentence[] getFormulas(){
		Sentence[] formulas = new Sentence[]{a,b};
		return formulas;
	}

}
