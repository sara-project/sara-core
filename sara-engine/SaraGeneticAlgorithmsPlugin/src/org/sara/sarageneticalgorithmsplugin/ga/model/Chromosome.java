package org.sara.sarageneticalgorithmsplugin.ga.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IModelController;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;

public class Chromosome implements IChromosome {

    public Chromosome(int type, Object cell, Object information, ISpecimen parent) {
        this.parent = parent;

        List<Slot> slots = new ArrayList<>();
        IModelController modelControl = ICore.getInstance().getModelController();

        if (cell instanceof List)
            slots = ((List<Slot>) cell);
        else
            System.err.printf("Genetic Load is invalid!");

        Map<Integer, List<Slot>> slotsByRoom = modelControl.separateSlotsByRoom(slots);
        this.type = type;

        this.arms = new ArrayList<>();

        slotsByRoom.values().stream().map(slot -> slot).map((slotsList) -> {
            List<IGene> genes = new ArrayList<>();

            // Ordena os slots por Intervalo de Tempo ASC
            slotsList.sort((Slot s1, Slot s2) -> s1.getSchedule().getTimeInterval() - s2.getSchedule().getTimeInterval());
            slotsList.forEach((slot) -> genes.add(new Gene((Slot) slot.clone())));

            return genes;
        }).forEachOrdered((genes) -> this.arms.add(genes));
        
        if(information != null) {
            this.pullInformation = (List<ClassSchedule>) information;
            Collections.shuffle(this.pullInformation);
        }
    }
    
    public void fill() {
        if(this.pullInformation == null)
            return;
        
        IModelController modelControl = ICore.getInstance().getModelController();
        Map<Integer, List<ClassSchedule>> cScheduleByClass = modelControl.separateClassScheduleByClass(this.pullInformation);
        List<Integer> csKeys = new ArrayList<>(cScheduleByClass.keySet());
        Collections.shuffle(csKeys);
        
        //Todos esses genes são do mesmo dia. Cada braço é uma sala e cada posição do braço é um IT (APAGAR Comentário/Test)
        List<Integer> roomIndexes = new ArrayList<>();
        IntStream.rangeClosed(0, this.arms.size() - 1).forEach(ic -> roomIndexes.add(ic));
        Collections.shuffle(roomIndexes);
        
        for(Integer key : csKeys) {
            List<ClassSchedule> classSchedules  = cScheduleByClass.get(key);
            classSchedules.sort((ClassSchedule a, ClassSchedule b) -> a.getSchedule().getTimeInterval() - b.getSchedule().getTimeInterval());
            
            //Embaralha a lista de salas baseado numa probabilidade de 50%
            //if(ThreadLocalRandom.current().nextInt(0, 2) == 1)
            //    Collections.shuffle(roomIndexes);

            for (int i = 0; i < classSchedules.size(); i++) {
            
                for(Integer index : roomIndexes) {
                    if(classSchedules.get(i).isAllocated())
                        break;
                    
                    for (int j = 0; j < this.arms.get(index).size(); j++) {
                        Slot slot = (Slot) this.arms.get(index).get(j).getAllele(false);

                        if(!slot.isEmpty()) {
                            j++;
                            continue;
                        }

                        if(classSchedules.get(i).isAllocated()) {
                            j++;
                            continue;
                        }

                        if(!classSchedules.get(i).getSchedule().equals(slot.getSchedule())) {
                            j++;
                            continue;
                        }

                        while(i < classSchedules.size() &&
                              j < this.arms.get(index).size() &&
                              slot.isValid(classSchedules.get(i).getSchoolClass()))
                        {

                            slot.fill(classSchedules.get(i).getSchoolClass());
                            classSchedules.get(i).allocate();

                            i++; //Avança o IT da Aula da Tuma
                            j++; //Avança o IT da Sala do Slot

                            if(i >= classSchedules.size() || j >= this.arms.get(index).size())
                                break;

                            slot = (Slot) this.arms.get(index).get(j).getAllele(false);
                        }
                        
                        if(i >= classSchedules.size() || j >= this.arms.get(index).size())
                            break;
                    }
                    
                    if(i >= classSchedules.size())
                        break;
                }
            }
        }
        
        //Only for test
//        for(List<ClassSchedule> cs : cScheduleByClass.values()) {
//            for(ClassSchedule c : cs)
//                if(!c.isAllocated())
//                    this.fill() ;
//        }
        
        this.pullInformation = null;
    }
    
    @Override
    public int getType() {
        return this.type;
    }

    @Override
    public Object clone() {
        Chromosome chr = new Chromosome(this.parent);
        chr.type = this.type;
        chr.fitness = this.fitness;
        
        this.arms.forEach(list -> {
            List<IGene> cloneList = new ArrayList<>();
            list.forEach((gene) -> cloneList.add((IGene) gene.clone()));
            chr.arms.add(cloneList);
        });
        
        if(this.pullInformation != null)
            this.pullInformation.forEach(p -> chr.pullInformation.add((ClassSchedule) p.clone()));
        else
            chr.pullInformation = null;

        return chr;
    }

    @Override
    public void setFitness(float value) {
        this.fitness = value;
    }

    @Override
    public float getFitness() {
        return this.fitness;
    }

    @Override
    public void resetFitness() {
        this.fitness = 0;
    }

    @Override
    public int size() {
        int size = 0;
        size = this.arms.stream().map((genes) -> genes.size()).reduce(size, Integer::sum);

        return size;
    }

    @Override
    public IGene getGene(int index, boolean clone) {
        return this.getGenes(clone).get(index);
    }

    @Override
    public void setGene(IGene gene, int index) {
        this.getGenes(false).add(index, (IGene) gene.clone());
    }

    @Override
    public List<IGene> getGenes(boolean clone) {
        List<IGene> allGenes = new ArrayList();
        this.arms.forEach((genes) -> genes.forEach(gene -> allGenes.add(clone? ((IGene) gene.clone()) : gene)));

        return allGenes;
    }

    @Override
    public List<IGene> getGenesByArm(int type, boolean clone) {
        if(clone) {
            List<IGene> cloneList = new ArrayList();
            this.arms.get(type).forEach(g -> cloneList.add((IGene) g.clone()));

            return cloneList;
        } else {
            return this.arms.get(type);
        }
    }

    @Override
    public List<IGene> getGenesRandomByArm(boolean clone) {
        return this.getGenesByArm(new Random().nextInt(this.arms.size()), clone);
    }

    @Override
    public int groupSize() {
        return this.arms.size();
    }

    private Chromosome(ISpecimen parent) {
        this.arms = new ArrayList<>();
        this.pullInformation = new ArrayList<>();
        this.parent = parent;
    }
   
    @Override
    public ISpecimen getParent() {
        return this.parent;
    }
    
    private List<List<IGene>> arms;
    private List<ClassSchedule> pullInformation;
    private int type;
    private float fitness;
    private final ISpecimen parent;
}
