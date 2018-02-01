package org.sara.sarageneticalgorithmsplugin.ga;

import java.util.ArrayList;
import java.util.List;
import org.sara.interfaces.ICore;
import org.sara.interfaces.algorithms.ga.ISpecimen;
import org.sara.interfaces.model.Slot;
import org.sara.sarageneticalgorithmsplugin.ga.models.Specimen;

public class SpecimenFactory {
    
    public static List<ISpecimen> makeSpecimen(int quantity) {
        List<ISpecimen> specimens = new ArrayList<>();
        List<Slot> slots = new ArrayList<>(ICore.getInstance().getModelController().getSlots().values());

        while(quantity > 0) {
            Specimen specimen = new Specimen(slots);
            specimens.add(specimen);
            quantity--;
        }
        return specimens;
    }
    
    private SpecimenFactory() {}
}
