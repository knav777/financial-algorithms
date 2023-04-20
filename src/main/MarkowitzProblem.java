package main;

/**
 * @author knav777
 */

import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;

public class MarkowitzProblem extends AbstractProblem {
	
    double[] beneficios;
    double[][] covarianza;
	
	public MarkowitzProblem( double[] beneficios, double[][] covarianzas ) {
	    super(4, 2);

		this.beneficios = beneficios;
		this.covarianza = covarianzas;
	}

	@Override
	public void evaluate(Solution solution) {
	    double[] x = new double[4];
	    for (int i = 0; i < 4; i++) {
	        x[i] = ((RealVariable) solution.getVariable(i)).getValue();
	    }

	    double R = 0.0;
	    for (int i = 0; i < 4; i++) {
	        R += this.beneficios[i] * x[i];
	    }

	    double V = 0.0;
	    for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 4; j++) {
	            V += x[i] * this.covarianza[i][j] * x[j];
	        }
	    }

	    solution.setObjective(0, -R);
	    solution.setObjective(1, V);
	}

	@Override
	public Solution newSolution() {
	    Solution solution = new Solution(4, 2);
	    for (int i = 0; i < 4; i++) {
	        solution.setVariable(i, new RealVariable(0.0, 1.0));
	    }
	    return solution;
	}

}