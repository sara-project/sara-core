package org.sara.shell;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.sara.interfaces.model.Slot;


public class JSONWriter {

    public void writeSolution( String jsonFile, List<Slot> slots ) throws IOException, ParseException, Exception {

        JSONArray rootArray = new JSONArray();
        JSONObject rootObject = new JSONObject();
        JSONArray slotArray = new JSONArray();
        FileWriter writeFile;
        
        slots.forEach( (slot) -> {
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
        rootObject.put( "info", slotArray);
        
        rootArray.add(rootObject);
        
        writeFile = new FileWriter(jsonFile);
        writeFile.write(rootArray.toJSONString());
        writeFile.close();
    }
}
