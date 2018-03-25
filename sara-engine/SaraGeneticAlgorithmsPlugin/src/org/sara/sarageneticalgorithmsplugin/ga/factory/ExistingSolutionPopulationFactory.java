package org.sara.sarageneticalgorithmsplugin.ga.factory;

import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.ga.factory.IPopulationFactory;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.model.IPopulation;
import org.sara.sarageneticalgorithmsplugin.ga.model.Population;
import org.sara.sarageneticalgorithmsplugin.ga.model.Specimen;

public class ExistingSolutionPopulationFactory implements IPopulationFactory {

    public static ExistingSolutionPopulationFactory getInstance() {
        if (instance == null) {
            instance = new ExistingSolutionPopulationFactory();
        }

        return instance;
    }

    @Override
    public IPopulation makePopulation() {
        return new Population(1, specimens);
    }

    public void setSpecimenData(List<Object> chromossomes) {
        ExistingSolutionPopulationFactory.specimens.add(new Specimen(chromossomes, false));
    }

    protected ExistingSolutionPopulationFactory() {
        ExistingSolutionPopulationFactory.specimens = new ArrayList<>();
    }
    private static ExistingSolutionPopulationFactory instance;
    private static List<ISpecimen> specimens;
}
