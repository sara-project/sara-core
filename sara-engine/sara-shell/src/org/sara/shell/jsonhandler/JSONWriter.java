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
        
        info.getBestSolution().forEach( chromossome -> {
            chromossome.getGenes(false).forEach((gene) -> {
                Slot slot = (Slot) gene.getAllele(false);
               
                //Adds only Slots that have allocated classes
                if(slot.getSchoolClass() != null) {
                    JSONObject slotObj = new JSONObject();
                    slotObj.put("day", slot.getSchedule().getDay());
                    slotObj.put("time_interval", slot.getSchedule().getTimeInterval());
                    slotObj.put("room", slot.getRoom().getID());            
                    slotObj.put("s_class", slot.getSchoolClass().getID());
                    slotArray.add( slotObj);
                }
            });
        } );
        
       this.dumpFile(jsonFile, "slots", slotArray);
    }
    
    public void writeSolutionEvaluate( File jsonFile, InfoSolution info) throws IOException, ParseException, Exception {
        this.dumpFile(jsonFile, "best_fitness", info.getFitnessOfTheBestSolution());
    }
    
    private void writeAdditionalInformation(JSONObject rootObject) {
        JSONArray infoArray = new JSONArray();
        JSONObject infoObj = new JSONObject();

        infoObj.put("1_request_type", requestType);
        
        if(info.getExecutionTime() != -1)
            infoObj.put("2_execution_time", info.getExecutionTime());
        
        if(info.getTotalMemoryUsed() != null)
            infoObj.put("3_total_memory_used", info.getTotalMemoryUsed());
        
        if(info.getTimeOfGenerateInitialPopulation() != -1)
            infoObj.put("4_time_generate_initial_population", info.getTimeOfGenerateInitialPopulation());

        if(info.getAverageTimeOfFitness() != -1)
            infoObj.put("5_average_time_fitness", info.getAverageTimeOfFitness());
        
        if(info.getAverageTimeOfSelection() != -1)
            infoObj.put("6_average_time_selection", info.getAverageTimeOfSelection());

        if(info.getAverageTimeOfCrossover() != -1)
            infoObj.put("7_average_time_crossover", info.getAverageTimeOfCrossover());
        
        if(info.getAverageTimeOfMutation() != -1)
            infoObj.put("8_average_time_mutation", info.getAverageTimeOfMutation());
        
        if(info.getAverageTimeOfRefreshPopulation() != -1)
            infoObj.put("9_average_time_refresh_population", info.getAverageTimeOfRefreshPopulation());
        ///////
        //Accessibility Info
        infoObj.put("10_doesnt_meets_accessibility_requirement", info.getTotalDoesntMeetsAccessibilityRequirement(info.getBestSolution()));
        infoObj.put("11_meets_accessibility_requirement", info.getTotalMeetsAccessibilityRequirement(info.getBestSolution()));
        infoObj.put("12_total_accessibility_requirement", info.getTotalAccessibilityRequirement(info.getBestSolution()));

        //Slots info
        infoObj.put("13_empty_slots", info.getTotalEmptySlots(info.getBestSolution()));
        infoObj.put("14_filled_slots", info.getTotalFilledSlots(info.getBestSolution()));
        infoObj.put("15_duplicate_allocations", info.getTotalDuplicateAllocation(info.getBestSolution()));
        infoObj.put("16_unused_places", info.getUnusedPlaces(info.getBestSolution()));
        infoObj.put("17_overload_Rooms", info.getOverLoadRooms(info.getBestSolution()));

        //Class Schedules info
        infoObj.put("18_unallocated_class_schedules", info.getUnallocatedClassSchedules(info.getBestSolution()));
        infoObj.put("19_allocated_class_schedules", info.getAllocatedClassSchedules(info.getBestSolution()));
        ///////

        infoObj.put("20_best_solution_fitness", info.getFitnessOfTheBestSolution());
        
        if(info.getFitnessTimeLine() != null ) {
            JSONArray fitnessArray = new JSONArray();
            fitnessArray.addAll( info.getFitnessTimeLine() );
            infoObj.put("21_fitness_time_line", fitnessArray);
        }

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
