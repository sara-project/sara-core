package org.sara.shell.jsonhandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.sara.interfaces.ICore;
import org.sara.interfaces.model.InfoSolution;
import org.sara.interfaces.model.Slot;

public class JSONWriter {
    
    public void writeResult( File jsonFile, InfoSolution info, String requestType) throws IOException, ParseException, Exception {
        this.info = info;
        this.requestType = requestType;
        switch (requestType) {
            case "class_assignment":
                this.writeSolution( jsonFile, info );
                break;
            case "eval_solution":
                this.writeSolutionEvaluate( jsonFile, info );
                break;
            default:
                System.err.println( "Wrong request type was passed to JSONWriter." );
                System.exit( 1 );
        }
    }
    
    public void writeSolution( File jsonFile, InfoSolution info) throws IOException, ParseException, Exception {
        JSONArray slotArray = new JSONArray();
        
        info.getBestSolution().forEach( s -> {
            Slot slot = (Slot) s;
            //Adiciona apenas Slots que possuem turmas alocadas
            if(slot.getSchoolClass() != null) {
                JSONObject slotObj = new JSONObject();
                slotObj.put("day", slot.getSchedule().getDay());
                slotObj.put("time_interval", slot.getSchedule().getTimeInterval());
                slotObj.put("room", slot.getRoom().getID());            
                slotObj.put("s_class", slot.getSchoolClass().getID());
                slotArray.add( slotObj);
            }
        } );
        
       this.dumpFile(jsonFile, "slots", slotArray);
    }
    
    public void writeSolutionEvaluate( File jsonFile, InfoSolution info) throws IOException, ParseException, Exception {
        this.dumpFile(jsonFile, "best_fitness", info.getFitnessOfTheBestSolution());
    }
    
    private void writeAdditionalInformation(JSONObject rootObject) {
        JSONArray infoArray = new JSONArray();
        JSONObject infoObj = new JSONObject();

        infoObj.put("request_type", requestType);
        
        if(info.getExecutionTime() != -1)
            infoObj.put("execution_time", info.getExecutionTime());
        
        if(info.getTotalMemoryUsed() != null)
            infoObj.put("total_memory_used", info.getTotalMemoryUsed());
        
        if(info.getTimeOfGenerateInitialPopulation() != -1)
            infoObj.put("time_generate_initial_population", info.getTimeOfGenerateInitialPopulation());

        if(info.getAverageTimeOfFitness() != -1)
            infoObj.put("average_time_fitness", info.getAverageTimeOfFitness());
        
        if(info.getAverageTimeOfSelection() != -1)
            infoObj.put("average_time_selection", info.getAverageTimeOfSelection());

        if(info.getAverageTimeOfCrossover() != -1)
            infoObj.put("average_time_crossover", info.getAverageTimeOfCrossover());
        
        if(info.getAverageTimeOfMutation() != -1)
            infoObj.put("average_time_mutation", info.getAverageTimeOfMutation());
        
        if(info.getAverageTimeOfRefreshPopulation() != -1)
            infoObj.put("average_time_refresh_population", info.getAverageTimeOfRefreshPopulation());
        
        if(info.getFitnessTimeLine() != null ) {
            JSONArray fitnessArray = new JSONArray();
            fitnessArray.addAll( info.getFitnessTimeLine() );
            infoObj.put("fitness_time_line", fitnessArray);
        }
        
        infoObj.put("best_solution_fitness", info.getFitnessOfTheBestSolution());
         
        infoArray.add(infoObj);
        rootObject.put("info", infoArray);
    }
    
    private void dumpFile(File outputFile, String key, Object contentObject) throws IOException, ParseException, Exception {
        JSONArray rootArray = new JSONArray();
        JSONObject rootObject = new JSONObject();
        
        if(contentObject instanceof JSONArray)
            rootObject.put(key, (JSONArray) contentObject);
        else if(contentObject instanceof JSONObject)
            rootObject.put(key, (JSONObject) contentObject);
        else
            rootObject.put(key, contentObject);
        
        //Add additional information if debug was true
        if(ICore.getInstance().getProjectController().isDebugInfoAGActive())
            this.writeAdditionalInformation(rootObject);
        
        rootArray.add(rootObject);
        
        try (FileWriter writeFile = new FileWriter(outputFile)) {
            writeFile.write(rootArray.toJSONString());
        }
    }
    
    private InfoSolution info;
    private String requestType;
}
