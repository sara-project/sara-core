package org.sara.sarageneticalgorithmsplugin.flyweight;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FlyIntFactory {

    public static FlyIntFactory getFlyIntFactory(){
            if(flyIntegerFactory == null)
                    flyIntegerFactory = new FlyIntFactory();
            return flyIntegerFactory;
    }	

    public FlyInteger getFlyInteger(int allele){
            FlyInteger flyInteger = this.flyIntegerPool.get(Integer.valueOf(allele));
            if(flyInteger == null){
                    flyInteger = new FlyInteger(allele);
                    this.flyIntegerPool.put(Integer.valueOf(allele), flyInteger);
            }
            return flyInteger;
    }

    public FlyInteger getFlyInteger(){
            return this.getFlyInteger((int)(this.random.nextInt()));
    }

    public FlyInteger getFlyIntegerWithRange(int maxValue){
            FlyInteger flyInteger = this.getFlyInteger((int)(this.random.nextInt(maxValue)));
            flyInteger.setMaxValue(maxValue);
            return flyInteger;
    }
    
    private FlyIntFactory(){
            this.flyIntegerPool = new HashMap<>();
            this.random = new Random();
    }

    private Map<Integer, FlyInteger> flyIntegerPool;
    private Random random;
    private static FlyIntFactory flyIntegerFactory;
}
