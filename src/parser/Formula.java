package parser;

public abstract class Formula {
	public static char AND = '∧', OR = '∨', NOT = '¬';
	public static char FORALL = '∀', EXIST = '∃', IFF = '⇔', IMPLIES = '⇒';

	public abstract Formula negate();

	@Override
	abstract public String toString();

	public static String bracketize(Formula a) {
		String res;
		if (a instanceof Predicate || a instanceof Not || a instanceof Exist || a instanceof ForAll)
			res = a.toString();
		else {
			res = "(" + a.toString() + ")";
		}
		return res;
	}

	public abstract Formula iffElimination();

	public abstract Formula impElimination();
	
	public abstract Formula pushNegation();
}
