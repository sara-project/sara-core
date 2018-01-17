package org.sara.interfaces.model;

import org.sara.interfaces.algorithms.ga.crossover.ICrossover;
import org.sara.interfaces.algorithms.ga.fitness.IFitness;
import org.sara.interfaces.algorithms.ga.galightswitch.IGALightSwitch;
import org.sara.interfaces.algorithms.ga.mutation.IMutation;
import org.sara.interfaces.algorithms.ga.selection.ISelection;


public class GAConfiguration {
    
    @SuppressWarnings("WARNING: The default parameters should be checked!")
    public GAConfiguration() {
         GAConfiguration.isDefault = true;
        
        if(!GAConfiguration.hasInitialized) {
            System.out.println();
            System.out.println("WARNING: The default parameters should be checked!");
            System.out.println();

            this.populationNumber = 5000;
             this.maxGeneration = 4000;
             this.mutationProbability = 0.1;
             this.crossoverProbability  = 0.1;
             this.selectProbability = 0.1;
             this.elitismProbability = 0.1;
             GAConfiguration.hasInitialized = true;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Getters e Setters"> 
    
    public int getPopulationNumber() {
        return populationNumber;
    }

    public void setPopulationNumber(int populationNumber) {
        if( this.populationNumber == populationNumber)
            return;
        
         GAConfiguration.isDefault = false;
         this.populationNumber = populationNumber;
    }

    public int getMaxGeneration() {
        return maxGeneration;
    }

    public void setMaxGeneration(int maxGeneration) {
        if( this.maxGeneration == maxGeneration)
            return;

         GAConfiguration.isDefault = false;
         this.maxGeneration = maxGeneration;
    }

    public double getMutationProbability() {
        return mutationProbability;
    }

    public void setMutationProbability(double mutationProbability) {
        if( this.mutationProbability == mutationProbability)
            return;

         GAConfiguration.isDefault = false;
         this.mutationProbability = mutationProbability;
    }

    public double getCrossoverProbability() {
        return crossoverProbability;
    }

    public void setCrossoverProbability(double crossoverProbability) {
        if( this.crossoverProbability == crossoverProbability)
            return;

         GAConfiguration.isDefault = false;
         this.crossoverProbability = crossoverProbability;
    }

    public double getSelectProbability() {
        return selectProbability;
    }

    public void setSelectProbability(double selectProbability) {
        if( this.selectProbability == selectProbability)
            return;

         GAConfiguration.isDefault = false;
         this.selectProbability = selectProbability;
    }

    public double getElitismProbability() {
        return elitismProbability;
    }

    public void setElitismProbability(double elitismProbability) {
        if( this.elitismProbability == elitismProbability)
            return;

         GAConfiguration.isDefault = false;
         this.elitismProbability = elitismProbability;
    }
    
    public IFitness getFitness() {
        return fitness;
    }

    public void setFitness(IFitness fitness) {
         this.fitness = fitness;
    }

    public IMutation getMutation() {
        return mutation;
    }

    public void setMutation(IMutation mutation) {
         this.mutation = mutation;
    }

    public ICrossover getCrossover() {
        return crossover;
    }

    public void setCrossover(ICrossover crossover) {
         this.crossover = crossover;
    }

    public ISelection getSelection() {
        return selection;
    }

    public void setSelection(ISelection selection) {
         this.selection = selection;
    }

    public IGALightSwitch getGaLightSwitch() {
        return gaLightSwitch;
    }

    public void setGaLightSwitch(IGALightSwitch gaLightSwitch) {
         this.gaLightSwitch = gaLightSwitch;
    }
    //<editor-fold>

    public boolean isDefault() {
        return isDefault;
    }
    
    @Override
    public String toString() {
        String string = "";
        
        if( GAConfiguration.isDefault)
            string = "<<Default Configuration>>\n";
        
        string += "Parameters: \n";
        string += " - Population Number: " +  this.populationNumber + "\n";
        string += " - Max Generation: " +  this.maxGeneration + "\n";
        string += " - Mutation Probability: " +  this.mutationProbability + "\n";
        string += " - Mutation Probability: " +  this.crossoverProbability + "\n";
        string += " - Select Probability: " +  this.selectProbability + "\n";
        string += " - Elitism Probability: " +  this.elitismProbability;
        
        return string;
    }
    
    //GA Operators
    private IFitness fitness;
    private IMutation mutation;
    private ICrossover crossover;
    private ISelection selection;
    private IGALightSwitch gaLightSwitch;
    
    //Parameters
    private static boolean hasInitialized;
    private static boolean isDefault;
    private int populationNumber;
    private int maxGeneration;
    private double mutationProbability;
    private double crossoverProbability;
    private double selectProbability;
    private double elitismProbability;
}
