package org.sara.sarageneticalgorithmsplugin.ga.factory;

import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.sarageneticalgorithmsplugin.ga.model.Specimen;

public class SpecimenFactory {

    public static List<ISpecimen> makeSpecimen(int quantity) {
        List<ISpecimen> specimens = new ArrayList<>();

        while (quantity > 0) {
            Specimen specimen = new Specimen(ICore.getInstance().getModelController().getSlots().values());
            specimens.add(specimen);
            quantity--;
        }
        return specimens;
    }

    private SpecimenFactory() {
    }
}
