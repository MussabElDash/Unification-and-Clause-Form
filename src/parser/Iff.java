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

	public Formula convert() {
		return new And(new Formula[] { new Implies(a, b), new Implies(b, a) });
	}

	@Override
	public String toString() {
		return bracketize(a) + " ⇔ " + bracketize(b);
	}

}
