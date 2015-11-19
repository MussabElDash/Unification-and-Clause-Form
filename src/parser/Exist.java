package parser;

import java.util.HashSet;
import java.util.Set;

public class Exist extends Formula {
	private String[] vars;
	private Formula formula;

	public Exist(String[] vars, Formula formula) {
		this.formula = formula;
		this.vars = vars;
	}

	public Exist(String var, Formula formula) {
		this.formula = formula;
		this.vars = var.split(",");
	}

	@Override
	public Formula negate() {
		return new ForAll(vars, formula.negate());
	}

	@Override
	public String toString() {
		String res = "∃" + vars[0];
		for (int i = 1; i < vars.length; i++) {
			res += "," + vars[i];
		}
		return res + "[" + formula.toString() + "]";
	}

	@Override
	public Formula iffElimination() {
		return new Exist(vars, formula.iffElimination());
	}

	@Override
	public Formula impElimination() {
		return new Exist(vars, formula.impElimination());
	}

	@Override
	public Formula pushNegation() {
		return new Exist(vars, formula.pushNegation());
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		HashSet<String> res = new HashSet<String>();
		for (String s : this.vars) {
			res.add(s);
		}
		// formula.standardize(new HashSet<String>())
		res.addAll(formula.standardize(new HashSet<String>()));
		return res;
	}

	@Override
	public Formula rename(String var) {
		String[] newVars = new String[vars.length];
		for (int i = 0; i < newVars.length; i++) {
			String s = vars[i];
			if (s.equals(var)) {
				newVars[i] = s + "'";
			} else {
				newVars[i] = s;
			}
		}
		return new Exist(newVars, formula.rename(var));
	}
}
