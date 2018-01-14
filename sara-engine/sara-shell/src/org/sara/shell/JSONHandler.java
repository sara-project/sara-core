package org.sara.shell;

import java.io.FileNotFoundException;
import org.sara.interfaces.model.Room;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.sara.interfaces.model.Schedule;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.Slot;

//http://crunchify.com/how-to-read-json-object-from-file-in-java/
public class JSONHandler {

    public void LoadJson(String jsonFile) throws FileNotFoundException, IOException, ParseException, Exception {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(jsonFile));
        HashMap<String, Room> roomsHash = new HashMap<>();
        HashMap<String, SchoolClass> classesHash = new HashMap<>();
        HashMap<String, Slot> slotsHash = new HashMap<>();

        JSONObject jsonObject = (JSONObject) obj;
        JSONArray rooms = (JSONArray) jsonObject.get("rooms");
        JSONArray classes = (JSONArray) jsonObject.get("classes");
        JSONArray slots = (JSONArray) jsonObject.get("slots");

        Iterator it = rooms.iterator();
        while(it.hasNext()) {
            JSONObject room = (JSONObject) it.next();
            String id = room.get("id").toString();
            
            if(roomsHash.containsKey(id))
                throw new Exception("Json File is invalid. There is Room duplicated (id: "+id+").");
            
            roomsHash.put(id, new Room(room.get("id").toString(),
                                   Integer.parseInt(room.get("capacity").toString())));
        }
        
        it = classes.iterator();
        while(it.hasNext()) {
            JSONObject s_class = (JSONObject) it.next();
            String id = s_class.get("id").toString();
            
            if(classesHash.containsKey(id))
                throw new Exception("Json File is invalid. There is Room duplicated (id: "+id+").");
            
            List<Schedule> schedules = new ArrayList<>();
            JSONArray jschedules = (JSONArray) jsonObject.get("schedules");
            Iterator sch = jschedules.iterator();
            
            while(sch.hasNext()){
                JSONObject schedule = (JSONObject) sch.next();
                schedules.add(new Schedule(Integer.parseInt(schedule.get("id").toString()),
                                           Integer.parseInt(schedule.get("day").toString()),
                                           Integer.parseInt(schedule.get("timeInterval").toString())));
            
            }

            classesHash.put(id, new SchoolClass(s_class.get("id").toString(),
                                   Integer.parseInt(s_class.get("size").toString()), schedules));
        }

        it = slots.iterator();
        while(it.hasNext()) {
            JSONObject slot = (JSONObject) it.next();
            String id = slot.get("id").toString();
            
            if(slotsHash.containsKey(id))
                throw new Exception("Json File is invalid. There is Room duplicated (id: "+id+").");
            
            JSONObject schedule = (JSONObject) slot.get("schedule");
            slotsHash.put(id, new Slot(slot.get("id").toString(), new Schedule(Integer.parseInt(schedule.get("id").toString()),
                                                                    Integer.parseInt(schedule.get("day").toString()),
                                                                    Integer.parseInt(schedule.get("timeInterval").toString()))));
        }
    }
}