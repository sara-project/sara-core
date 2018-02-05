package org.sara.sarageneticalgorithmsplugin.ga.model;

import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;

public class Gene implements IGene {

    public Gene(Object geneticInformation) {
        if (geneticInformation instanceof Slot) {
            this.information = (Slot) geneticInformation;
        } else {
            this.information = null;
        }
    }

    public boolean hasContent() {
        return information == null ? false : information.isEmpty();
    }

    @Override
    public Object getAlleleContent() {
        return information == null ? null : information.getSchoolClass();
    }

    @Override
    public void setAlleleContent(Object value) {
        if (this.information != null) {
            if (value == null) {
                this.information.toEmpty();
            } else {
                this.information.fill((SchoolClass) value);
            }
        }
    }

    @Override
    public Object getAllele() {
        return this.information;
    }

    @Override
    public Object clone() {
        return new Gene(this.information.clone());
    }

    private final Slot information;
}
