package org.sara.sarageneticalgorithmsplugin.ga.model;

import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;

public class Gene implements IGene {

    public Gene(Object geneticInformation) {
        if (geneticInformation instanceof Slot)
            this.information = (Slot) ((Slot) geneticInformation).clone();
        else
            this.information = null;
    }

    public boolean hasContent() {
        return information == null ? false : information.isEmpty();
    }

    @Override
    public Object getAlleleContent(boolean clone) {
        return information == null ? null : (clone? !this.hasContent()? null: information.getSchoolClass().clone() : information.getSchoolClass());
    }

    @Override
    public void setAlleleContent(Object value) {
        if (this.information != null) {
            if (value == null)
                this.information.toEmpty();
            else 
                this.information.fill((SchoolClass) ((SchoolClass) value).clone());
        }
    }

    @Override
    public Object getAllele(boolean clone) {
        return clone? this.information.clone() :this.information;
    }

    @Override
    public Object clone() {
        return new Gene(this.information);
    }

    private final Slot information;
}
