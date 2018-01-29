package org.sara.sarageneticalgorithmsplugin.ga.models;

import org.sara.interfaces.algorithms.ga.genes.IGene;
import org.sara.interfaces.model.Slot;

public class Gene implements IGene{
    public Gene(Object geneticInformation) {
        if(geneticInformation instanceof Slot)
            this.information = (Slot) geneticInformation;
        else
            this.information = null;
    }
    
    public Object getGeneticInformation() {
        return this.information;
    }
    public boolean hasAllele() {
        return information == null? false : information.isEmpty();
    }
    
    @Override
    public Object getAllele() {
        return information == null? information : information.getSchoolClass();
    }
    
    private final Slot information;
    
    @Override
    public Object clone() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setAllele(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public IGene getRandom() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
