/**
 * 
 */
package main;

/**
 * @author knav777
 * 
 * Integrantes:
 * 
 * Maria Gabriela Amaya. C.I: 25.168.390
 * Samuel Yañez. C.I:30.338.389
 * Kleinmann N. Aponte V. C.I:27.327.519
 */

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import algorithms.Genetic;
import algorithms.MultiObject;


public class Main {
	
    public static void main(String[] args) {
    	
    	//Data
        String[] acciones_bolsa = {"ecopetrol", "prec", "pfbcolom", "gruposura"};
        double[] varianzas = {0.00671900, 0.03438852, 0.00344421, 0.00233944};
        double[] beneficios = {0.00429493, 0.02689857, 0.00827647, 0.00794438};
        double[][] covarianzas = {
        				 	{0.00671900, 0.01193778, 0.00170523, 0.00161020},
		                 	{0.01193778, 0.03438852, 0.00402569, 0.00375060},
		                 	{0.00170523, 0.00402569, 0.00344421, 0.00185332},
		                 	{0.00161020, 0.00375060, 0.00185332, 0.00233944}
		                 };
        
        //Compare
        double[] sample_1 = {0,0,0,0,0};
        double[] sample_2 = {0,0,0,0,0};
    	boolean show = false;
        
        for(int i=0; i<5; i++) {
        	
        	if( i == 4 ) show = true;
        	
	        //Genetic algorithm
	        Genetic g = new Genetic();
	        g.genetic_algorithm( acciones_bolsa, beneficios, covarianzas, show );
	    	
	        //Multi-object algorithm
	        MultiObject mo = new MultiObject();
	        mo.multi_object_algorithm(acciones_bolsa, beneficios, covarianzas, show);

        	sample_1[i] = g.get_best_fitness();
        	sample_2[i] = mo.get_best_fitness();
        }
        
        Main.compare_2_samples_statically(sample_1, sample_2);
    }
    
    public static int get_idx_major(double[] vector) {
    	
    	double max = vector[0];
    	int pos = 0;
    	
    	for (int i = 1; i < vector.length; i++) {
    	    if (vector[i] > max) {
    	        max = vector[i];
    	        pos = i;
    	    }
    	}
    	
    	return pos;
    }
    
    public static void compare_2_samples_statically( double[] sample1, double[] sample2 ) {

        // Calcular la media y la desviación estándar de cada muestra
        SummaryStatistics stats1 = new SummaryStatistics();
        for (double value : sample1) {
           stats1.addValue(value);
        }
        double mean1 = stats1.getMean();
        double sd1 = stats1.getStandardDeviation();

        SummaryStatistics stats2 = new SummaryStatistics();
        for (double value : sample2) {
           stats2.addValue(value);
        }
        double mean2 = stats2.getMean();
        double sd2 = stats2.getStandardDeviation();

        // Imprimir los resultados
        System.out.println("\n\n-------------------------\n");
        
        System.out.println("Media del algoritmo genetico: " + mean1);
        System.out.println("Desviación: " + sd1);
        System.out.println("Media del multiobjeto NSGAII " + mean2);
        System.out.println("Desviación estándar de la muestra 2: " + sd2);
     }
 
}
