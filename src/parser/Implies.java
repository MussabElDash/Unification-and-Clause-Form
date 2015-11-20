package parser;

import java.util.HashSet;
import java.util.Set;

public class Implies extends Formula {
	private Formula a, b;

	public Implies(Formula a, Formula b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Formula negate() {
		return new Not(this);
	}

	public Formula impElimination() {
		return new Or(new Formula[] { new Not(a), b });
	}

	@Override
	public String toString() {
		return bracketize(a) + " ⇒ " + bracketize(b);
	}

	@Override
	public Formula iffElimination() {
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
	public Formula rename(String s, boolean toQuantifier) {
		return this;
	}

}
