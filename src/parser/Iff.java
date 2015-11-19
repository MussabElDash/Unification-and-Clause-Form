package parser;

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

}
