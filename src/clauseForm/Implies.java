package clauseForm;

import java.util.HashSet;
import java.util.Set;

public class Implies extends Sentence {
	private Sentence a, b;

	public Implies(Sentence a, Sentence b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public Sentence negate() {
		return new Not(this);
	}

	public Sentence impElimination() {
		return new Or(new Sentence[] { new Not(a), b });
	}

	@Override
	public String toString() {
		return bracketize(a) + " ⇒ " + bracketize(b);
	}

	@Override
	public Sentence iffElimination() {
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

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sentence renameSkolemize(String s, Set<String> skolems, boolean toQuantifier) {
		return this;
	}

}