package parser;

import java.util.HashSet;
import java.util.Set;

public class Exist extends Sentence {
	private String[] vars;
	private Sentence formula;

	public Exist(String[] vars, Sentence formula) {
		this.formula = formula;
		this.vars = vars;
	}

	public Exist(String var, Sentence formula) {
		this.formula = formula;
		this.vars = var.split(",");
	}

	@Override
	public Sentence negate() {
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
	public Sentence iffElimination() {
		return new Exist(vars, formula.iffElimination());
	}

	@Override
	public Sentence impElimination() {
		return new Exist(vars, formula.impElimination());
	}

	@Override
	public Sentence pushNegation() {
		return new Exist(vars, formula.pushNegation());
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
		return new Exist(newVars, formula.rename(var, false));
	}
	
	public Sentence renameSkolemize(String var, String[] skolems){
		return null;
	}
	
	public Sentence[] getFormulas(){
		Sentence[] formulas = new Sentence[]{formula};
		return formulas;
	}
	
	public void skolemize(Set<String> vars){
		formula.skolemize(vars);
		for(String var : this.vars){
//			formula = formula.renameSkolemize(var, vars);
		}
	}
}
