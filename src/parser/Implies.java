package parser;

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

	public Formula convert() {
		return new Or(new Formula[] { a.negate(), b });
	}

	@Override
	public String toString() {
		return bracketize(a) + " ⇒ " + bracketize(b);
	}

}
