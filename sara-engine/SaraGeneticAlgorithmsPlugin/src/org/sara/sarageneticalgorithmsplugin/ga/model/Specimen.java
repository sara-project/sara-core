package org.sara.sarageneticalgorithmsplugin.ga.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.TreeMap;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IModelController;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;

public class Specimen implements ISpecimen {

    public Specimen(Object geneticLoad) {
        List<Slot> slots = new ArrayList<>();
        IModelController modelControl = ICore.getInstance().getModelController();

        if (geneticLoad instanceof Collection) {
            slots = new ArrayList<>((Collection) geneticLoad);
        } else {
            System.err.printf("Genetic Load is invalid!");
        }

        //Os slots estaram sempre ordenados pelo ID do Dia, facilitando a operação de Crossover
        TreeMap<Integer, List<Slot>> slotsByDay = (TreeMap<Integer, List<Slot>>) modelControl.separateSlotsByDay(slots);
        this.chromossomes = new Chromosome[slotsByDay.size()];

        this.pullClassSchedule = (HashMap<Integer, List<ClassSchedule>>) modelControl.separateClassScheduleByDay(new ArrayList<>(modelControl.getClassSchedule().values()));

        int i = 0;
        for (Integer day : slotsByDay.keySet()) {
            Collections.shuffle(slotsByDay.get(day));
            Collections.shuffle(this.pullClassSchedule.get(day));

            this.chromossomes[i++] = new Chromosome(day, slotsByDay.get(day), this.pullClassSchedule.get(day));
        }
        this.fill();
        this.pullClassSchedule.clear();
    }

    @Override
    public float getFitness() {
        float fitness = 0;

        for (IChromosome c : this.getChromossomes()) {
            fitness += c.getFitness();
        }

        return fitness;
    }

    @Override
    public Object clone() {
        Specimen clone = new Specimen();
        clone.chromossomes = this.chromossomes.clone();
        clone.pullClassSchedule = (HashMap<Integer, List<ClassSchedule>>) this.pullClassSchedule.clone();
        return clone;
    }

    @Override
    public IChromosome getRandomChromosome() {
        int limit = this.chromossomes.length;
        return this.chromossomes[(new Random()).nextInt(limit)];
    }

    @Override
    public IChromosome[] getChromossomes() {
        return (IChromosome[]) this.chromossomes.clone();
    }

    @Override
    public IChromosome getChromossome(int index) {
        return (IChromosome) this.chromossomes[index].clone();
    }

    @Override
    public void setChromosome(IChromosome chromosome, int index) {
        this.chromossomes[index] = chromosome;
    }

    @Override
    public List<IGene> getAllGenes() {
        List<IGene> genes = new ArrayList<>();
        for (IChromosome chromosome : this.chromossomes) {
            genes.addAll(chromosome.getGenes());
        }

        return genes;
    }

    private void fill() {
        for (IChromosome chromossome : this.chromossomes) {
            ((Chromosome) chromossome).fill();
        }
    }

    private Specimen() {
    }
    private IChromosome[] chromossomes;
    private HashMap<Integer, List<ClassSchedule>> pullClassSchedule;
}
