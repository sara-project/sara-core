package org.sara.interfaces.algorithms.ga;

import java.util.Collection;
import org.sara.interfaces.model.InfoSolution;

public interface IGAEngine {

    public InfoSolution startCycle();

    public InfoSolution evalSolution(Collection specimens);
}
