package org.sara.sarageneticalgorithmsplugin.operator.fitness.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.sara.interfaces.algorithms.ga.model.IChromosome;
import org.sara.interfaces.algorithms.ga.model.ISpecimen;
import org.sara.interfaces.algorithms.ga.operator.fitness.criteria.ICriteria;
import org.sara.interfaces.model.Room;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;

public class ClassRoomExchangeCriteria extends ICriteria {

    public ClassRoomExchangeCriteria() {
        this(false);
    }

    public ClassRoomExchangeCriteria(boolean required) {
        super(required);

        this.specimenEvaluated = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public synchronized float execute(IChromosome chromosome) {
        ScpecimenGrade parent = new ScpecimenGrade(chromosome.getParent());

        if (this.specimenEvaluated.contains(parent)) {
            return this.specimenEvaluated.get(this.specimenEvaluated.indexOf(parent)).grade;
        }

        HashMap<Integer, List<Slot>> usedSlots = new HashMap<>();
        List<SchoolClass> usedSchoolClass = new ArrayList<>();

        if (!this.specimenEvaluated.contains(parent)) {
            parent.sp.getAllGenes(false).stream().map((gene) -> (Slot) gene.getAllele(false)).filter((slot) -> (!slot.isEmpty())).map((slot) -> {
                usedSchoolClass.add(slot.getSchoolClass());
                return slot;
            }).map((slot) -> {
                if (!usedSlots.containsKey(slot.getSchoolClass().getID())) {
                    usedSlots.put(slot.getSchoolClass().getID(), new ArrayList<>());
                }
                return slot;
            }).forEachOrdered((slot) -> {
                usedSlots.get(slot.getSchoolClass().getID()).add(slot);
            });
        }

        float grade = 0;
        List<Integer> totalSchedule = new ArrayList<>();
        grade = usedSchoolClass.stream().map((sc) -> {
            int usedSchoolClassSchedules = sc.getAllSchoolClassSchedules().size();
            totalSchedule.add(usedSchoolClassSchedules);
            HashMap<Integer, Room> usedRooms = new HashMap<>();

            usedSlots.get(sc.getID()).forEach(s -> {
                if (!usedRooms.containsKey(s.getRoom().getID())) {
                    usedRooms.put(s.getRoom().getID(), s.getRoom());
                }
            });

            return usedRooms.isEmpty() ? 0 : ((float) usedSchoolClassSchedules / (float) usedRooms.size());
        }).map((gradeSchoolClass) -> gradeSchoolClass).reduce(grade, (accumulator, _item) -> accumulator + _item);

        int total = 0;
        total = totalSchedule.stream().map((s) -> s).reduce(total, Integer::sum);

        parent.grade = (float) grade / (float) total;
        this.specimenEvaluated.add(parent);
        return parent.grade;
    }

    @Override
    public void clear() {
        this.specimenEvaluated.clear();
    }

    private List<ScpecimenGrade> specimenEvaluated;

    private class ScpecimenGrade {

        public ScpecimenGrade(ISpecimen sp) {
            this.sp = sp;
            this.grade = Float.NaN;
        }

        public ScpecimenGrade(ISpecimen sp, Float grade) {
            this.sp = sp;
            this.grade = grade;
        }

        @Override
        public boolean equals(Object other) {
            try {
                return this.sp.equals(((ScpecimenGrade) other).sp);
            } catch (Exception ex) {
                return false;
            }
        }

        private ISpecimen sp;
        private Float grade;
    }
}
