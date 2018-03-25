package org.sara.sarageneticalgorithmsplugin.operator.galightswitch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.IGeneration;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.IGALightSwitch;

public class GALightSwitch implements IGALightSwitch {

    public GALightSwitch(int maxGenerationNumber) {
        this.maxGenerationNumber = maxGenerationNumber;
        this.fitnessTimeLine = new TreeMap<>();
    }

    @Override
    public boolean stop(IGeneration generation) {
        ISpecimen bestSpecimen = generation.getPopulation(false).getBestSpecimen(false);

        if (ICore.getInstance().getProjectController().isDebugInfoAGActive()) {
            this.fitnessTimeLine.put(generation.getNumber(), bestSpecimen.getFitness());
        }

        if (bestSpecimen != null && bestSpecimen.getFitness() != Float.NaN
                && (this.bestSpecimenEver == null || bestSpecimen.isBetterThan(bestSpecimenEver))) {
            this.bestSpecimenEver = (ISpecimen) bestSpecimen.clone();
        }

        ICore.getInstance().getUiController().printProgressBar(generation.getNumber(), this.maxGenerationNumber);
        return generation.getNumber() == maxGenerationNumber || !this.keepRunning(generation.getNumber());
    }

    @Override
    public List<IChromosome> getBestSolution() {
        List<IChromosome> slots = new ArrayList<>();
        slots.addAll(Arrays.asList(this.bestSpecimenEver.getChromossomes(false)));
        return slots;
    }

    @Override
    public List<Float> getFitnessTimeLine() {

        List<Float> timeLine = new ArrayList<>();

        for (int i = 1; i <= this.fitnessTimeLine.keySet().size(); i++) {
            timeLine.add(this.fitnessTimeLine.get(i));
        }
        return timeLine;
    }

    @Override
    public Float getBestFitness() {
        return this.bestSpecimenEver.getFitness();
    }

    private boolean keepRunning(int genNumber) {
        try {
            File jsonFile = ICore.getInstance().getModelController().getFileName();
            File auxFile = new File("./outputs/aux_" + jsonFile.getName());

            if (genNumber == 1 && auxFile.exists()) {
                auxFile.delete();
            }

            if (!auxFile.exists()) {
                auxFile.createNewFile();

                try (FileWriter writeFile = new FileWriter(auxFile)) {
                    writeFile.write("1");
                }
            }

            String content;
            BufferedReader bfr = new BufferedReader(new FileReader(auxFile));
            content = bfr.readLine();

            switch (content) {
                //Continue
                case "1":
                    return true;
                //Stop
                case "0":
                    return false;
                //Pause (waiting)
                default: {
                    try {
                        int time = 1000;
                        try {
                            time = Integer.parseInt(content);

                        } finally {
                            Thread.sleep(time);
                        }
                    } catch (InterruptedException ex) {
                        return this.keepRunning(genNumber);
                    }

                    return this.keepRunning(genNumber);
                }
            }

        } catch (IOException ex) {
            System.err.println(this.getClass().getSimpleName() + ": error occurred while creating control file.");
            return false;
        }
    }

    private ISpecimen bestSpecimenEver;
    private final Map<Integer, Float> fitnessTimeLine;
    private final int maxGenerationNumber;
}
