package parser;

import java.util.HashSet;
import java.util.Set;

public class ForAll extends Sentence {
	private String[] vars;
	private Sentence formula;

	public ForAll(String[] vars, Sentence formula) {
		this.formula = formula;
		this.vars = vars;
	}

	public ForAll(String var, Sentence formula) {
		this.formula = formula;
		this.vars = var.split(",");
	}

	@Override
	public Sentence negate() {
		return new Exist(vars, formula.negate());
	}

	@Override
	public String toString() {
		String res = "∀" + vars[0];
		for (int i = 1; i < vars.length; i++) {
			res += "," + vars[i];
		}
		return res + "[" + formula.toString() + "]";
	}

	@Override
	public Sentence iffElimination() {
		return new ForAll(vars, formula.iffElimination());
	}

	@Override
	public Sentence impElimination() {
		return new ForAll(vars, formula.impElimination());
	}

	@Override
	public Sentence pushNegation() {
		return new ForAll(vars, formula.pushNegation());
	}

	@Override
	public Set<String> standardize(Set<String> vars) {
		HashSet<String> res = new HashSet<String>();
		for (String s : this.vars) {
			res.add(s);
		}
		Set<String> temp;
		temp = formula.standardize(new HashSet<String>());
		// temp = formula.standardize(res);
		for (String var : temp) {
			if (res.contains(var)) {
				this.vars = renameVar(var);
				formula = formula.rename(var, true);
			}
		}
		res.addAll(temp);
		return res;
	}

	private String[] renameVar(String var) {
		String[] newVars = new String[vars.length];
		for (int i = 0; i < newVars.length; i++) {
			String s = vars[i];
			if (s.equals(var)) {
				newVars[i] = s + "'";
			} else {
				newVars[i] = s;
			}
		}
		return newVars;
	}

	@Override
	public Sentence rename(String var, boolean toQuantifier) {
		if (toQuantifier)
			return this;
		String[] newVars = renameVar(var);
		return new ForAll(newVars, formula.rename(var, false));
	}

	public Sentence[] getFormulas() {
		Sentence[] formulas = new Sentence[] { formula };
		return formulas;
	}

	public Sentence skolemize(Set<String> vars) {
		for (String var : this.vars) {
			vars.add(var);
		}
		Sentence[] formulas = this.getFormulas();
		for (int i = 0; i < formulas.length; i++) {
			formulas[i] = formulas[i].skolemize(vars);
		}
		for (String var : this.vars) {
			vars.remove(var);
		}
		return this;
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sentence renameSkolemize(String var, Set<String> skolems, boolean toQuantifier) {
		if (toQuantifier)
			return this;
		String[] newVars = renameVar(var);
		return new ForAll(newVars, formula.renameSkolemize(var, skolems, false));
	}

	@Override
	public Sentence discardForAll() {
		return this.formula.discardForAll();
	}

}
