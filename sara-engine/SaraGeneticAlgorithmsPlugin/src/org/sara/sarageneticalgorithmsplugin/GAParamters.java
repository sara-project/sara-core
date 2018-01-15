package org.sara.sarageneticalgorithmsplugin;

import org.sara.interfaces.algorithms.ga.core.IGAParameters;

public class GAParamters implements IGAParameters {
   
    public GAParamters() {}
    
    @Override
    public int getPopulationSize() {
        return this.popNum;
    }
    
    @Override
    public float getSelectionPercent() {
        return this.selectPerc;
    }
    
    @Override
    public float getMutationPercent() {
        return this.mutaPerc;
    }
    
    public float getNumeroElitismo(){
        return this.elitePerc;
    }
    public int getGeracaoTotal(){
        return this.geraNum;
    }
    
    private int popNum;
    private int geraNum;
    private float selectPerc;
    private float mutaPerc;
    private float elitePerc;
}