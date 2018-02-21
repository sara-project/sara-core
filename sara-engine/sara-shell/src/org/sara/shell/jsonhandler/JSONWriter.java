package org.sara.shell.jsonhandler;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.sara.interfaces.ICore;
import org.sara.interfaces.model.Slot;

public class JSONWriter {

    public void writeSolution( String jsonFile, List<Object> solution, Float[] fitnessTimeLine ) throws IOException, ParseException, Exception {

        JSONArray rootArray = new JSONArray();
        JSONObject rootObject = new JSONObject();
        JSONArray slotArray = new JSONArray();
        JSONArray infoArray = new JSONArray();
        FileWriter writeFile;
        
        solution.forEach( s -> {
            Slot slot = (Slot) s;
            //Adiciona apenas Slots que possuem turmas alocadas
            if(slot.getSchoolClass() != null) {
                JSONObject slotObj = new JSONObject();
                slotObj.put("day", slot.getSchedule().getDay());
                slotObj.put("time_interval", slot.getSchedule().getTimeInterval());
                slotObj.put("room", slot.getRoom());            
                slotObj.put("s_class", slot.getSchoolClass().getID());
                slotArray.add( slotObj);
            }
        } );
        rootObject.put( "slots", slotArray);
        
        if(ICore.getInstance().getProjectController().isDebugInfoAGActive()) {
            JSONObject infoObj = new JSONObject();
            JSONArray fitnessArray = new JSONArray();
            
            fitnessArray.addAll( Arrays.asList( fitnessTimeLine ) );
            infoObj.put("fitness_time_line", fitnessArray);
            infoArray.add( infoObj);
            rootObject.put( "info", infoArray);
        }
        
        rootArray.add(rootObject);
        
        writeFile = new FileWriter(jsonFile);
        writeFile.write(rootArray.toJSONString());
        writeFile.close();
    }
}
