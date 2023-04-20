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

import java.util.Arrays;
import java.util.Random;

public class Phase {
	
    double[] beneficios;
    double[][] covarianza;
	
	public Phase( double[] beneficios, double[][] covarianzas ){
		this.beneficios = beneficios;
		this.covarianza = covarianzas;
	}

	//
	public double fitness(double[] X) {
        double port_ret = 0;
        for (int i = 0; i < X.length; i++) {
            port_ret += this.beneficios[i] * X[i];
        }
        double[][] temp = new double[X.length][X.length];
        for (int i = 0; i < X.length; i++) {
            for (int j = 0; j < X.length; j++) {
                temp[i][j] = this.covarianza[i][j] * X[i] * X[j];
            }
        }
        double port_risk = Math.sqrt(Arrays.stream(temp).mapToDouble(row -> Arrays.stream(row).sum()).sum());
        return port_ret / (port_risk * port_risk);
    }
	
	//
    public double[] selection(double[][] population, double[] fitness) {
        Random rand = new Random();
        int idx1 = rand.nextInt(population.length);
        int idx2 = rand.nextInt(population.length);
        if (fitness[idx1] > fitness[idx2]) {
            return population[idx1];
        } else {
            return population[idx2];
        }
    }
    
    //
    public double[][] crossover(double[] parent1, double[] parent2) {
        Random rand = new Random();
        int crossover_point = rand.nextInt(parent1.length - 1) + 1;
        double[] child1 = new double[parent1.length];
        double[] child2 = new double[parent1.length];
        for (int i = 0; i < parent1.length; i++) {
            if (i < crossover_point) {
                child1[i] = parent1[i];
                child2[i] = parent2[i];
            } else {
                child1[i] = parent2[i];
                child2[i] = parent1[i];
            }
        }
        return new double[][]{child1, child2};
    }

    //
    public double[] mutation(double[] chromosome, double mutation_rate) {
        Random rand = new Random();
        while (true) {
            for (int i = 0; i < chromosome.length; i++) {
                if (rand.nextDouble() < mutation_rate) {
                    chromosome[i] = rand.nextDouble();
                }
            }
            if (Arrays.stream(chromosome).sum() >= 0.95 && Arrays.stream(chromosome).sum() <= 1) break;
        }
        return chromosome;
    }
  
	
}
