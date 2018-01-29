package org.sara.sarageneticalgorithmsplugin.ga.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import org.sara.interfaces.ICore;
import org.sara.interfaces.IModelController;
import org.sara.interfaces.algorithms.ga.chromosome.IChromosome;
import org.sara.interfaces.algorithms.ga.genes.IGene;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Schedule;
import org.sara.interfaces.model.Slot;


public class Chromosome implements IChromosome{
    
    public Chromosome(int type, Object cell, Object information) {
        Slot[] slots = null;
        IModelController modelControl = ICore.getInstance().getModelController();
        
        if(cell instanceof List)
            slots = ((List<Slot>) cell).toArray(new Slot[0]);
        else
            System.err.printf("Genetic Load is invalid!");
        
        Object[] slotsByTimeInterval = modelControl.separateSlotsByTimeInterval(slots).values().toArray();
        this.type = type;
        
        //Os braços dos cromossomos sempre estaram ordenados (crescente) pelo ID do Intervalo de Tempo
        this.arms = new ArrayList<>();
        
        for(int i = 0; i < slotsByTimeInterval.length; i++) {
            List<Slot> slotsList = (List) slotsByTimeInterval[i];
            List<Gene> genes = new ArrayList<>();
            
            Collections.shuffle(slotsList);
            
            for(Slot slot : slotsList)
                genes.add(new Gene(slot));
            
            Collections.shuffle(genes);
            this.arms.add(genes);
        }
        
        this.pullInformation = (List<ClassSchedule>) information;
        Collections.shuffle(this.pullInformation);
    }
    
    public void fill() {
        IModelController modelControl = ICore.getInstance().getModelController();
        TreeMap<Integer, List<ClassSchedule>> pullHash =  (TreeMap<Integer, List<ClassSchedule>>) modelControl.separateClassSchedulesByTimeInterval(this.pullInformation.toArray(new ClassSchedule[0]));
        //Preenche a primeira fileira do braço e pula a segunda, uma vez que as aulas do IFBA geralmente são agrupadas
        for(int i = 0;  i < this.arms.size(); i = i+2) {
            //estes genes são slots agrupados por um dia e agrupados por intervalo de tempo;
            for(Gene gene : this.arms.get(i)) {
                Slot slot = (Slot) gene.getGeneticInformation();
                //
                if(!slot.isEmpty())
                    continue;
                
                List<ClassSchedule> pull = pullHash.get(slot.getSchedule().getTimeInterval());
                pullHash.remove(slot.getSchedule().getTimeInterval());
                
                //para de tentar preencher os genes pois não há mais opções disponíveis
                if(pull == null || pull.size() <= 0)
                    break;
                
                ClassSchedule randomClassSchedule = pull.get(new Random().nextInt(pull.size()));
                boolean wasFilled = slot.fill(randomClassSchedule.getSchoolClass());
                //se foi preenchido significa que aquela aula já está sendo usada, logo devo remover no pull de possiveis aulas
                if(wasFilled) {
                    pull.remove(randomClassSchedule);
                    
                    //atualiza o pull
                    pullHash.put(slot.getSchedule().getTimeInterval(), pull);
                    
                    this.fillNext(slot, modelControl.getNextSchedule(slot.getSchedule()), modelControl.getNextClassSchedule(randomClassSchedule), pullHash);
                    //como o ifba geralmente são duas aulas seguidas
                    //pegamos o próximo slot da mesma sala (avançando apenas o intervalo de tempo)
                    //depois pegamos a proxima aula da mesma turma (avançando apenas o intervalo de tempo)
                }
                else { //atualiza o pull
                    pullHash.put(slot.getSchedule().getTimeInterval(), pull);
                }
            }
        }
        this.pullInformation.clear();
    }
    
    private void fillNext(Slot slot, Schedule nextSchedule, ClassSchedule nextClassSchedule, Map<Integer, List<ClassSchedule>> pullHash) {
         for(List<Gene> genes : this.arms) {
            for(Gene gene : genes) {
                Slot next = (Slot) gene.getGeneticInformation();
                if(next.getRoom() == slot.getRoom() && next.getSchedule() == nextSchedule) {
                    if(nextClassSchedule == null || nextClassSchedule.getSchoolClass() == null)
                        return;
                    next.fill(nextClassSchedule.getSchoolClass());
                   
                   List<ClassSchedule> pull = pullHash.get(next.getSchedule().getTimeInterval());
                   pullHash.remove(next.getSchedule().getTimeInterval());
                   pull.remove(nextClassSchedule);
                   pullHash.put(next.getSchedule().getTimeInterval(), pull);
                   return;
                } else if(next.getSchedule() != nextSchedule)
                    break;
            }
        }
    }

    public int getType() {
        return type;
    }

    private final List<List<Gene>> arms;
    private final List<ClassSchedule> pullInformation;
    private final int type;
    
    @Override
    public Object clone(){
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setFitness(float value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float getFitness() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void resetFitness() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSize() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGene getGene(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setGene(IGene gene, int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValidation(boolean valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getValidation() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IChromosome getRandom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGene[] getGenes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
