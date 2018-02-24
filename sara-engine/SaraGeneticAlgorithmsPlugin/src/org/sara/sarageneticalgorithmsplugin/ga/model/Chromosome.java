package org.sara.sarageneticalgorithmsplugin.ga.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.TreeMap;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IModelController;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;

public class Chromosome implements IChromosome {

    public Chromosome(int type, Object cell, Object information) {
        List<Slot> slots = new ArrayList<>();
        IModelController modelControl = ICore.getInstance().getModelController();

        if (cell instanceof List)
            slots = ((List<Slot>) cell);
        else
            System.err.printf("Genetic Load is invalid!");

        Map<Integer, List<Slot>> slotsByRoom = modelControl.separateSlotsByRoom(slots);
        this.type = type;

        this.arms = new ArrayList<>();

        List<Integer> keys = new ArrayList(slotsByRoom.keySet());
        Collections.shuffle(keys);

        keys.stream().map((key) -> slotsByRoom.get(key)).map((slotsList) -> {
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
        TreeMap<Integer, List<ClassSchedule>> pullHash = (TreeMap) modelControl.separateClassSchedulesByTimeInterval(this.pullInformation);

        for (List<IGene> genes : this.arms) {
            //Slot Sala 1, IT1, IT2, IT3...
            for (int i = 0; i < genes.size(); i++) {
                Slot slot = (Slot) genes.get(i).getAllele(true);

                if (!slot.isEmpty())
                    continue;

                List<ClassSchedule> pull = pullHash.get(slot.getSchedule().getTimeInterval());

                //para de tentar preencher os genes pois não há mais opções disponíveis
                if (pull == null || pull.isEmpty())
                    break;

                Collections.shuffle(pull);
                ClassSchedule randomClassSchedule = pull.get(new Random().nextInt(pull.size()));

                boolean hasChange;
                int times = 0;
                int maxTimes = ThreadLocalRandom.current().nextInt(1, pull.size() * 2);
                do {
                    hasChange = false;
                    while (slot.isValid(randomClassSchedule.getSchoolClass())) {
                        hasChange = true;

                        genes.get(i).setAlleleContent((randomClassSchedule.getSchoolClass()));
                        pull.remove(randomClassSchedule);
                        pullHash.put(slot.getSchedule().getTimeInterval(), pull);

                        if (++i >= genes.size())
                            break;

                        //obtém o próximo slot da mesma sala e dia (avança o horário)
                        slot = (Slot) genes.get(i).getAllele(true);
                        //obtém a próxima Aula da mesma turma, baseada no Schedule do Slot
                        randomClassSchedule = randomClassSchedule.getSchoolClass().getClassSchedule(slot.getSchedule());
                        pull = pullHash.get(slot.getSchedule().getTimeInterval());

                        if (randomClassSchedule == null || pull == null || pull.isEmpty())
                            break;

                        Collections.shuffle(pull);
                    }
                    //Caso tenha preenchido um slot, mas o próximo não preencheu, gera uma nova aula randomica para tentar ser alocada
                    if (pull == null || pull.isEmpty())
                        break;

                    randomClassSchedule = pull.get(new Random().nextInt(pull.size()));

                    //tentativas
                    if (times++ >= maxTimes)
                        break;
                } while ((!hasChange || (hasChange && slot.isEmpty())) && i < genes.size());
            }
        }
        this.pullInformation.clear();
    }

    public int getType() {
        return type;
    }

    @Override
    public Object clone() {
        Chromosome chr = new Chromosome();
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
    public List<IGene> getGenesByType(int type, boolean clone) {
        if(clone) {
            List<IGene> cloneList = new ArrayList();
            this.arms.get(type).forEach(g -> cloneList.add((IGene) g.clone()));

            return cloneList;
        } else {
            return this.arms.get(type);
        }
    }

    @Override
    public List<IGene> getGenesRandomByType(boolean clone) {
        return this.getGenesByType(new Random().nextInt(this.arms.size()), clone);
    }

    @Override
    public int groupSize() {
        return this.arms.size();
    }

    private Chromosome() {
        this.arms = new ArrayList<>();
        this.pullInformation = new ArrayList<>();
    }
    
    private List<List<IGene>> arms;
    private List<ClassSchedule> pullInformation;
    private int type;
    private float fitness;
}
