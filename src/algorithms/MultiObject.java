package algorithms;

import java.util.Arrays;

import org.moeaframework.algorithm.NSGAII;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.variable.RealVariable;

import main.MarkowitzProblem;

public class MultiObject {
	
	private double best_fitness;

    public void multi_object_algorithm( String[] acciones, double[] beneficios, double[][] covarianzas, boolean show ) {
    	
        Problem problem = new MarkowitzProblem( beneficios, covarianzas );
        NSGAII nsgaii = new NSGAII(problem);
        nsgaii.setInitialPopulationSize(1000);
        nsgaii.run(30);
        
        NondominatedPopulation result = nsgaii.getResult();

        Solution optimal = result.get(0);
        double variable_values = 0;
        double[] variables = new double[4];
        
        
        for(int i=0; i<optimal.getNumberOfVariables(); i++ ) {
        	variable_values += ((RealVariable) optimal.getVariable(i)).getValue();
        }
        
        for (int i = 0; i < 4; i++) {
            variables[i] = ((RealVariable) optimal.getVariable(i)).getValue() / variable_values;
        }

        double total = 0.0;
        for (int i = 0; i < 4; i++) {
            total += variables[i];
        }
        
        if( show ) {
	        System.out.println("Multi-object algorithm:\n");
        	
	        System.out.println("Ponderaciones: [" );        
	        for (int i = 0; i < 4; i++) {
	            System.out.printf("\t" + acciones[i] + ": %.4f" + "%%, \n", ((variables[i] / total)*100));
	        }
	        System.out.print("]");
	        
	        System.out.println("\nSolución óptima: " + Arrays.toString(variables));
	        
	        System.out.println("Frente de Pareto:");
	        double[] cv_pareto = new double[4];
	        int i = 0;
	        for (Solution s : result) {
	        	
	        	Solution optimal_pareto = result.get(i);
	        	for(int k=0; k<4; k++) {
	        		cv_pareto[k] = ((RealVariable) optimal_pareto.getVariable(k)).getValue() / variable_values;
	        	}
	            System.out.println( Arrays.toString(s.getObjectives()) + " CV:" + MultiObject.calculate_cv(cv_pareto, beneficios, covarianzas) );
	            i++;
	        }	
        }
        
        this.set_best_fitness(MultiObject.calculate_cv(variables, beneficios, covarianzas));

    }
    
    public double get_best_fitness() {
    	return this.best_fitness;
    }
    
    public void set_best_fitness( double best_fitness ) {
    	this.best_fitness = best_fitness;
    }
    
	public static double calculate_cv(double[] X, double[] beneficios, double[][] covarianza) {
        double port_ret = 0;
        for (int i = 0; i < X.length; i++) {
            port_ret += beneficios[i] * X[i];
        }
        double[][] temp = new double[X.length][X.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X.length; j++) {
                temp[i][j] = covarianza[i][j] * X[i] * X[j];
            }
        }
        double port_risk = Math.sqrt(Arrays.stream(temp).mapToDouble(row -> Arrays.stream(row).sum()).sum());
        return port_ret / (port_risk * port_risk);
    }
	
}
