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
        this(geneticLoad, true);
    }

    public Specimen(Object geneticLoad, boolean isToFill) {
        IModelController modelControl = ICore.getInstance().getModelController();

        if (!(geneticLoad instanceof Collection)) {
            System.err.printf("Genetic Load is invalid!");
            System.exit(3);
        }
        
        List<Slot> slots = new ArrayList<>((Collection) geneticLoad);

        //Os slots estaram sempre ordenados pelo ID do Dia, facilitando a operação de Crossover
        TreeMap<Integer, List<Slot>> slotsByDay = (TreeMap<Integer, List<Slot>>) modelControl.separateSlotsByDay(slots);
        this.chromossomes = new Chromosome[slotsByDay.size()];
        
        this.pullClassSchedule = (HashMap<Integer, List<ClassSchedule>>) modelControl.separateClassScheduleByDay(new ArrayList<>(modelControl.getClassSchedule().values()));

        int i = 0;
        for (Integer day : slotsByDay.keySet()) {
            if(isToFill) {
                Collections.shuffle(slotsByDay.get(day));
                Collections.shuffle(this.pullClassSchedule.get(day));
                this.chromossomes[i++] = new Chromosome(day, slotsByDay.get(day), this.pullClassSchedule.get(day), this);
            }
            else {
                this.chromossomes[i++] = new Chromosome(day, slotsByDay.get(day), null, this);
            }
        }
        
        if(isToFill)
            this.fill();
        
        this.pullClassSchedule.clear();
    }
    
    public Specimen(int chromossomeNumber) {
        this.chromossomes = new Chromosome[chromossomeNumber];
        this.pullClassSchedule = new HashMap<>();
    }

    @Override
    public float getFitness() {
        float fitness = 0;

        for (IChromosome c : this.getChromossomes(false))
            fitness += c == null? 0: c.getFitness();

        return fitness;
    }
    
    @Override
    public Object clone() {
        Specimen clone = new Specimen();
        clone.chromossomes = this.getChromossomes(true);
        
        HashMap<Integer, List<ClassSchedule>> clonePullClassSchedule = new HashMap<>();
        this.pullClassSchedule.keySet().forEach(keySet -> {
            List<ClassSchedule> listClone = new ArrayList<>();
            this.pullClassSchedule.get(keySet).forEach(l -> listClone.add((ClassSchedule) l.clone()));
            clonePullClassSchedule.put(keySet, listClone);
        });
        clone.pullClassSchedule = clonePullClassSchedule;
        return clone;
    }

    @Override
    public IChromosome getRandomChromosome(boolean clone) {
        int limit = this.chromossomes.length;
        IChromosome chr = this.chromossomes[(new Random()).nextInt(limit)];
        return clone? (IChromosome) chr.clone() : chr;
    }

    @Override
    public IChromosome[] getChromossomes(boolean clone) {
        if(!clone)
            return this.chromossomes;
        
        int size = this.chromossomes.length;
        IChromosome[] cloneList = new IChromosome[size];
        for(int i = 0; i < size; i++)
            cloneList[i] = (IChromosome) this.chromossomes[i].clone();
            
        return cloneList;
    }

    @Override
    public IChromosome getChromossome(int index, boolean clone) {
        IChromosome chr = this.chromossomes[index];
        return clone? (IChromosome) chr.clone() : chr;
    }

    @Override
    public void setChromosome(IChromosome chromosome, int index) {
        this.chromossomes[index] = (IChromosome) chromosome.clone();
    }

    @Override
    public List<IGene> getAllGenes(boolean clone) {
        List<IGene> genes = new ArrayList<>();
        for (IChromosome chromosome : this.chromossomes) {
            genes.addAll(chromosome.getGenes(clone));
        }

        return genes;
    }
    
    @Override
    public boolean isBetterThan(ISpecimen other) {
        return Float.compare(this.getFitness(), other.getFitness()) > 0;
    }

    private void fill() {
        for (IChromosome chromossome : this.chromossomes) {
            ((Chromosome) chromossome).fill();
        }
    }

    private Specimen() {}
    private IChromosome[] chromossomes;
    private HashMap<Integer, List<ClassSchedule>> pullClassSchedule;
}
