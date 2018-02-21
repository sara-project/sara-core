package org.sara.sarageneticalgorithmsplugin.operator.galightswitch;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IGeneration;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.IGALightSwitch;
import org.sara.interfaces.model.Slot;

public class GALightSwitch implements IGALightSwitch {

    public GALightSwitch(int maxGenerationNumber) {
        this.maxGenerationNumber = maxGenerationNumber;
        this.fitnessTimeLine =  new TreeMap<>();
    }

    @Override
    public boolean stop(IGeneration generation) {
        ISpecimen bestSpecimen = generation.getPopulation(false).getBestSpecimen(false);
        if(ICore.getInstance().getProjectController().isDebugInfoAGActive())
            this.fitnessTimeLine.put(generation.getNumber(), bestSpecimen.getFitness());
        
        if(this.bestSpecimenEver == null || bestSpecimen.isBetterThan(bestSpecimenEver)) {
            this.bestSpecimenEver = (ISpecimen) bestSpecimen.clone();
        }
        
        ICore.getInstance().getUiController().printProgressBar(generation.getNumber(), this.maxGenerationNumber);
        return generation.getNumber() == maxGenerationNumber;
    }
    
    @Override
    public List<Object> getBestSolution() {
        List<Object> slots = new ArrayList<>();
        this.bestSpecimenEver.getAllGenes(false).forEach((gene) -> slots.add((Slot) gene.getAllele(false)));
        return slots;
    }
    
    @Override
    public List<Float> getFitnessTimeLine() {
        return new ArrayList<>(this.fitnessTimeLine.values());
    }
    
    private ISpecimen bestSpecimenEver;
    private final Map<Integer, Float> fitnessTimeLine;
    private final int maxGenerationNumber;
}
