package parser;

import java.util.HashSet;
import java.util.Set;

public class Iff extends Formula {
	private Formula a, b;

	public Iff(Formula a, Formula b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Formula negate() {
		return new Not(this);
	}

	public Formula iffElimination() {
		return new And(new Formula[] { new Implies(a, b), new Implies(b, a) });
	}

	@Override
	public String toString() {
		return bracketize(a) + " ⇔ " + bracketize(b);
	}

	@Override
	public Formula impElimination() {
		return this;
	}

	@Override
	public Formula pushNegation() {
		return this;
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		return new HashSet<String>();
	}

	@Override
	public Formula rename(String s) {
		return this;
	}

}
