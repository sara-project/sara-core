package org.sara.sarageneticalgorithmsplugin.ga.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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

        if (cell instanceof List) {
            slots = ((List<Slot>) cell);
        } else {
            System.err.printf("Genetic Load is invalid!");
        }

        HashMap<Integer, List<Slot>> slotsByRoom = modelControl.separateSlotsByRoom(slots);
        this.type = type;

        this.arms = new ArrayList<>();

        List<Integer> keys = new ArrayList(slotsByRoom.keySet());
        Collections.shuffle(keys);

        keys.stream().map((key) -> slotsByRoom.get(key)).map((slotsList) -> {
            List<IGene> genes = new ArrayList<>();

            // Ordena os slots por Intervalo de Tempo ASC
            slotsList.sort((Slot s1, Slot s2) -> s1.getSchedule().getTimeInterval() - s2.getSchedule().getTimeInterval());
            slotsList.forEach((slot) -> genes.add(new Gene(slot)));

            return genes;
        }).forEachOrdered((genes) -> this.arms.add(genes));

        this.pullInformation = (List<ClassSchedule>) information;
        Collections.shuffle(this.pullInformation);
    }

    public void fill() {
        IModelController modelControl = ICore.getInstance().getModelController();
        TreeMap<Integer, List<ClassSchedule>> pullHash = (TreeMap) modelControl.separateClassSchedulesByTimeInterval(this.pullInformation);

        for (List<IGene> genes : this.arms) {
            //Slot Sala 1, IT1, IT2, IT3...
            for (int i = 0; i < genes.size(); i++) {
                Slot slot = (Slot) genes.get(i).getAllele();

                if (!slot.isEmpty()) {
                    continue;
                }

                List<ClassSchedule> pull = pullHash.get(slot.getSchedule().getTimeInterval());

                //para de tentar preencher os genes pois não há mais opções disponíveis
                if (pull == null || pull.isEmpty()) {
                    break;
                }

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

                        if (++i >= genes.size()) {
                            break;
                        }

                        //obtém o próximo slot da mesma sala e dia (avança o horário)
                        slot = (Slot) genes.get(i).getAllele();
                        //obtém a próxima Aula da mesma turma, baseada no Schedule do Slot
                        randomClassSchedule = randomClassSchedule.getSchoolClass().getClassSchedule(slot.getSchedule());
                        pull = pullHash.get(slot.getSchedule().getTimeInterval());

                        if (randomClassSchedule == null || pull == null || pull.isEmpty()) {
                            break;
                        }

                        Collections.shuffle(pull);
                    }
                    //Caso tenha preenchido um slot, mas o próximo não preencheu, gera uma nova aula randomica para tentar ser alocada
                    if (pull == null || pull.isEmpty()) {
                        break;
                    }
                    randomClassSchedule = pull.get(new Random().nextInt(pull.size()));

                    //tentativas
                    if (times++ >= maxTimes) {
                        break;
                    }
                } while (!hasChange || (hasChange && slot.isEmpty()));
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
        chr.arms = new ArrayList<>(this.arms);
        chr.pullInformation = new ArrayList<>(this.pullInformation);

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
    public IGene getGene(int index) {
        return this.getGenes().get(index);
    }

    @Override
    public void setGene(IGene gene, int index) {
        this.getGenes().add(index, gene);
    }

    @Override
    public List<IGene> getGenes() {
        List<IGene> allGenes = new ArrayList();
        this.arms.forEach((genes) -> allGenes.addAll(genes));

        return allGenes;
    }

    @Override
    public List<IGene> getGenesByType(int type) {
        return this.arms.get(type);
    }

    @Override
    public List<IGene> getGenesRandomByType() {
        return this.arms.get(new Random().nextInt(this.arms.size()));
    }

    @Override
    public int groupSize() {
        return this.arms.size();
    }

    private Chromosome() {
    }
    private List<List<IGene>> arms;
    private List<ClassSchedule> pullInformation;
    private int type;
    private float fitness;
}
