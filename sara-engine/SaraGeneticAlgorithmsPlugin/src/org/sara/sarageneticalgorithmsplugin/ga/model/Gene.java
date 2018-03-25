package org.sara.sarageneticalgorithmsplugin.ga.model;

import org.sara.interfaces.algorithms.ga.model.IGene;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;

public class Gene implements IGene {

    public Gene(Object geneticInformation) {
        if (geneticInformation instanceof Slot) {
            this.information = (Slot) ((Slot) geneticInformation).clone();
        } else {
            this.information = null;
        }
    }

    public boolean hasContent() {
        return information == null ? false : !information.isEmpty();
    }

    @Override
    public Object getAlleleContent(boolean clone) {
        return clone ? (this.hasContent() ? information.getSchoolClass().clone() : null) : information.getSchoolClass();
    }

    @Override
    public boolean setAlleleContent(Object value) {
        if (this.information != null) {
            if (value == null) {
                this.information.toEmpty();
                return true;
            } else {
                return this.information.fill((SchoolClass) ((SchoolClass) value).clone());
            }
        }

        return false;
    }

    @Override
    public Object getAllele(boolean clone) {
        return clone ? this.information.clone() : this.information;
    }

    @Override
    public Object clone() {
        return new Gene(this.information.clone());
    }

    private final Slot information;
}
