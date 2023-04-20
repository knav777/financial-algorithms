package algorithms;

import java.util.Random;

import main.Main;
import main.Phase;

public class Genetic {
	
	private double best_fitness;

    public void genetic_algorithm( String[] acciones, double[] beneficios, double[][] covarianzas, boolean show ) {
        
        int num_individuals = 1000;
        int num_generations = 30;
        double mutation_rate = new Random().nextDouble();
    	
    	//Phase class, in it are the methods of the genetic algorithm.
    	Phase phase = new Phase(beneficios, covarianzas);

    	//Population
        double[][] population = new double[num_individuals][4];
        Random rand = new Random();
        
        for (int i = 0; i < num_individuals; i++) {
            double sum = 0;
            for (int j = 0; j < 4; j++) {
                population[i][j] = rand.nextDouble();
                sum += population[i][j];
            }
            for (int j = 0; j < 4; j++) {
                population[i][j] /= sum;
            }
        }
        
        double[] fitness_values = new double[num_individuals];

        //Genetic algorithm
        for (int generation = 0; generation < num_generations; generation++) {
            
        	//Evaluation of the population
            for (int i = 0; i < num_individuals; i++) {
                fitness_values[i] = phase.fitness(population[i]);
            }

            // Selection, crossover and mutation
            double[][] new_population = new double[num_individuals][4];
            for (int i = 0; i < num_individuals; i++) {
                double[] idx1_idx2 = phase.selection(population, fitness_values);
                int idx1 = (int)idx1_idx2[0];
                int idx2 = (int)idx1_idx2[1];
                double[] parent1 = population[idx1];
                double[] parent2 = population[idx2];
                double[][] child1_child2 = phase.crossover(parent1, parent2);
                double[] child1 = phase.mutation(child1_child2[0], mutation_rate);
                double[] child2 = phase.mutation(child1_child2[1], mutation_rate);
                new_population[i] = phase.fitness(child1) > phase.fitness(child2) ? child1 : child2;
                population = new_population;
            }
        }
        

        //The best solution found
        int best_idx = Main.get_idx_major(fitness_values);
        double[] best_portfolio = population[best_idx];
        double best_fitness = fitness_values[best_idx];

        //Results
        if( show ) {
	        System.out.println("Genetic algorithm: \n");
        	
	        System.out.println("Mejor solución encontrada:");
	        double sum_best_portfolio = 0;
	        for (double d : best_portfolio) {
	            sum_best_portfolio += d;
	        }
	        
	        System.out.println("Ponderaciones: [\n" );        
	        for (int i = 0; i < best_portfolio.length; i++) {
	            System.out.printf("\t" + acciones[i] + ": %.4f" + "%%, \n", ((best_portfolio[i] / sum_best_portfolio)*100));
	        }
	        System.out.print("\n]");
	        
	        System.out.println("\nCoeficiente de prima de riesgo: " + best_fitness + "\n\n------------------------------------\n");
        }
        
        this.set_best_fitness(best_fitness);
    }
    
    public double get_best_fitness() {
    	return this.best_fitness;
    }
    
    public void set_best_fitness( double best_fitness ) {
    	this.best_fitness = best_fitness;
    }
}
