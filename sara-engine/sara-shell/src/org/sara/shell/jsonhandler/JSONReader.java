package org.sara.shell.jsonhandler;

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
import org.sara.interfaces.model.GAConfiguration;
import org.sara.interfaces.model.Schedule;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Requirement;
import org.sara.interfaces.model.Slot;

public class JSONReader {

    public JSONReader( String jsonFile ) throws FileNotFoundException, IOException, ParseException, Exception {
        this.requirementHash = new HashMap<>();
        this.schedulesHash = new HashMap<>();
        this.slotsHash = new HashMap<>();
        this.classesHash = new HashMap<>();
        this.roomsHash = new HashMap<>();
        this.classScheduleHash = new HashMap<>();
        this.gaConfig = new GAConfiguration();

        JSONObject jsonObject = (JSONObject) ( ( new JSONParser() ).parse( new FileReader( jsonFile ) ) );

        Object request_type = jsonObject.get( "request_type" );
        if (request_type == null) {
            throw new Exception( "Json File is invalid. There is not ." );
        }

        this.requestType = request_type.toString();

        this.parametersHandler( (JSONObject) jsonObject.get( "ga_config" ) );
        this.requirementsHandler( (JSONArray) jsonObject.get( "requirements" ) );
        this.schedulesHandler( (JSONArray) jsonObject.get( "schedules" ) );
        this.roomsHandler( (JSONArray) jsonObject.get( "rooms" ) );
        this.classesHandler( (JSONArray) jsonObject.get( "classes" ) );
        this.slotsHandler( (JSONArray) jsonObject.get( "slots" ) );
    }

    public String getRequestType() {
        return this.requestType;
    }

    public GAConfiguration getGAConfiguration() {
        return this.gaConfig;
    }

    public HashMap<String, Schedule> getSchedulesHash() {
        return this.schedulesHash;
    }

    public HashMap<String, Slot> getSlotsHash() {
        return this.slotsHash;
    }

    public HashMap<String, SchoolClass> getClassesHash() {
        return this.classesHash;
    }

    public HashMap<String, ClassSchedule> getClassSchedulesHash() {
        return this.classScheduleHash;
    }

    public HashMap<String, Room> getRoomsHash() {
        return this.roomsHash;
    }

    private void parametersHandler( JSONObject configuration ) {
        if (configuration != null) {
            Object populationNumber = configuration.get( "population_number" );
            Object maxGeneration = configuration.get( "max_generation" );
            Object mutationProbability = configuration.get( "mutation_probability" );
            Object selectProbability = configuration.get( "select_probability" );
            Object elitismProbability = configuration.get( "elitism_probability" );

            if (populationNumber != null) {
                try {
                    gaConfig.setPopulationNumber( Integer.parseInt( populationNumber.toString() ) );
                } catch (NumberFormatException ex) {
                    System.out.println( "Json with wrong value to 'population_number' property. The value was ignored..." );
                }
            }

            if (maxGeneration != null) {
                try {
                    gaConfig.setMaxGeneration( Integer.parseInt( maxGeneration.toString() ) );
                } catch (NumberFormatException ex) {
                    System.out.println( "Json with wrong value to 'max_generation' property. The value was ignored..." );
                }
            }

            if (mutationProbability != null) {
                try {
                    gaConfig.setMutationProbability( Double.parseDouble( mutationProbability.toString() ) );
                } catch (NumberFormatException ex) {
                    System.out.println( "Json with wrong value to 'mutation_probability' property. The value was ignored..." );
                }
            }

            if (selectProbability != null) {
                try {
                    gaConfig.setSelectProbability( Double.parseDouble( selectProbability.toString() ) );
                } catch (NumberFormatException ex) {
                    System.out.println( "Json with wrong value to 'select_probability' property. The value was ignored..." );
                }
            }

            if (elitismProbability != null) {
                try {
                    gaConfig.setElitismProbability( Double.parseDouble( elitismProbability.toString() ) );
                } catch (NumberFormatException ex) {
                    System.out.println( "Json with wrong value to 'elitism_probability' property. The value was ignored..." );
                }
            }
        }
    }

    private void requirementsHandler( JSONArray requirements ) throws Exception {
        Iterator it;

        if (requirements != null && !requirements.isEmpty()) {
            it = requirements.iterator();
            while (it.hasNext()) {
                JSONObject requirement = (JSONObject) it.next();
                Object id = requirement.get( "id" );
                Object type = requirement.get( "type" );
                Object priority = requirement.get( "priority" );

                if (id == null || type == null || priority == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, type or priority) to Requirement model." );
                }

                if (requirementHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a Requirement duplicated (id: " + id + ")." );
                }

                try {
                    requirementHash.put( id.toString(), new Requirement( Integer.parseInt( id.toString() ),
                            Integer.parseInt( type.toString() ),
                            Integer.parseInt( priority.toString() ) ) );
                } catch (NumberFormatException ex) {
                    throw new Exception( "Json File is invalid. Some value is wrong. " + ex.getMessage() );
                }
            }
        }

    }

    private void schedulesHandler( JSONArray schedules ) throws Exception {
        Iterator it;

        if (schedules != null && !schedules.isEmpty()) {
            it = schedules.iterator();

            while (it.hasNext()) {
                JSONObject schedule = (JSONObject) it.next();
                Object id = schedule.get( "id" );
                Object day = schedule.get( "day" );
                Object timeInterval = schedule.get( "time_interval" );

                if (id == null || day == null || timeInterval == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, day or time_interval) to Schedule model." );
                }

                if (schedulesHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a Schedule duplicated (id: " + id + ")." );
                }

                try {
                    schedulesHash.put( id.toString(), new Schedule( Integer.parseInt( id.toString() ),
                            Integer.parseInt( day.toString() ),
                            Integer.parseInt( timeInterval.toString() ) ) );
                } catch (NumberFormatException ex) {
                    throw new Exception( "Json File is invalid. Some value is wrong. " + ex.getMessage() );
                }
            }
        }
    }

    private void roomsHandler( JSONArray rooms ) throws Exception {
        Iterator it;

        if (rooms != null && !rooms.isEmpty()) {
            it = rooms.iterator();
            while (it.hasNext()) {
                JSONObject room = (JSONObject) it.next();
                Object id = room.get( "id" );
                Object capacity = room.get( "capacity" );
                Object area = room.get( "area" );
                Object type = room.get( "type" );

                JSONArray specifications = (JSONArray) room.get( "specifications" );

                if (id == null || capacity == null || specifications == null || area == null || type == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, capacity, areas, type or specifications) to Room model." );
                }

                if (roomsHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a Room duplicated (id: " + id + ")." );
                }

                //Adding Specifications to Room
                Iterator reqit = specifications.iterator();
                List<Requirement> requirements = new ArrayList<>();
                while (reqit.hasNext()) {
                    String requirementID = reqit.next().toString();

                    Requirement reqObj = requirementHash.get( requirementID );
                    if (requirementID == null) {
                        throw new Exception( "Json File is invalid. There is any Requirement with id = " + requirementID + "." );
                    }

                    requirements.add( reqObj );
                }

                try {

                    roomsHash.put( id.toString(), new Room( Integer.parseInt( id.toString() ),
                            Integer.parseInt( capacity.toString() ),
                            Integer.parseInt( area.toString() ),
                            requirements, Integer.parseInt( type.toString() ) ) );
                } catch (NumberFormatException ex) {
                    throw new Exception( "Json File is invalid. Some value is wrong. " + ex.getMessage() );
                }
            }
        }
    }

    private void slotsHandler( JSONArray slots ) throws Exception {
        Iterator it;

        if (slots != null && !slots.isEmpty()) {
            it = slots.iterator();

            while (it.hasNext()) {
                JSONObject slot = (JSONObject) it.next();
                Object id = slot.get( "id" );
                Object room = slot.get( "room" );
                Object schedule = slot.get( "schedule" );
                Object s_class = slot.get( "s_class" );

                if (id == null || room == null || schedule == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, room, or schedule) to Slot model." );
                }

                if (slotsHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a Slot duplicated (id: " + id + ")." );
                }

                try {
                    Schedule scheduleObj = schedulesHash.get( schedule.toString() );
                    Room roomObj = roomsHash.get( room.toString() );

                    if (scheduleObj == null) {
                        throw new Exception( "Json File is invalid. There is any Schedule with id = " + schedule + "." );
                    }

                    if (roomObj == null) {
                        throw new Exception( "Json File is invalid. There is any Room with id = " + room + "." );
                    }

                    Slot slotObj = new Slot( Integer.parseInt( id.toString() ), scheduleObj, roomObj );
                    if (s_class != null) {
                        SchoolClass schoolClass = classesHash.get( s_class.toString() );
                        if (scheduleObj == null) {
                            throw new Exception( "Json File is invalid. There is any SchoolClass with id = " + schoolClass + "." );
                        }
                        slotObj.fill( schoolClass );
                    }
                    slotsHash.put( id.toString(), slotObj );
                } catch (NumberFormatException ex) {
                    throw new Exception( "Json File is invalid. Some value is wrong. " + ex.getMessage() );
                }
            }
        }
    }

    private void classesHandler( JSONArray classes ) throws Exception {
        Iterator it;

        if (classes != null && !classes.isEmpty()) {
            it = classes.iterator();

            while (it.hasNext()) {
                JSONObject s_class = (JSONObject) it.next();
                Object id = s_class.get( "id" );
                Object size = s_class.get( "size" );
                Object schedules = s_class.get( "schedules" );
                JSONArray requirementsArray = (JSONArray) s_class.get( "requirements" );
                JSONArray typeRoomsWantedArray = (JSONArray) s_class.get( "type_rooms_wanted" );

                if (id == null || size == null || schedules == null || requirementsArray == null || typeRoomsWantedArray == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, size, schedules, or requirements, type_rooms_wanted) to School Class model." );
                }

                if (classesHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a School Class duplicated (id: " + id + ")." );
                }

                //Adding Specifications to Room
                Iterator reqit = requirementsArray.iterator();
                List<Requirement> requirements = new ArrayList<>();
                while (reqit.hasNext()) {
                    String requirementID = reqit.next().toString();

                    Requirement reqObj = requirementHash.get( requirementID );
                    if (requirementID == null) {
                        throw new Exception( "Json File is invalid. There is any Requirement with id = " + requirementID + "." );
                    }

                    requirements.add( reqObj );
                }

                Iterator typeRoomsIt = typeRoomsWantedArray.iterator();
                List<Integer> typeRoomsWanted = new ArrayList<>();
                while (reqit.hasNext()) {
                    String typeRoomsWantedID = typeRoomsIt.next().toString();

                    if (!roomsHash.values().stream().anyMatch( r -> r.getType() == Integer.parseInt( typeRoomsWantedID ))) {
                        throw new Exception( "Json File is invalid. There is any Room Type with id = " + typeRoomsWantedID + "." );
                    }

                    typeRoomsWanted.add( Integer.parseInt(typeRoomsWantedID) );
                }

                //Get School Class Schedules
                JSONArray jschedules = (JSONArray) schedules;
                Iterator sch = jschedules.iterator();
                SchoolClass sClass = new SchoolClass( Integer.parseInt( id.toString() ), Integer.parseInt( size.toString() ) );
                sClass.addRequirements( requirements );
                sClass.addTypeRoomsWanted( typeRoomsWanted );

                while (sch.hasNext()) {
                    String scheduleID = sch.next().toString();

                    Schedule schObj = schedulesHash.get( scheduleID );

                    if (schObj == null) {
                        throw new Exception( "Json File is invalid. There is any Schedule with id = " + scheduleID + "." );
                    }

                    sClass.addSchedule( schObj );
                    ClassSchedule cSchedule = new ClassSchedule( sClass, schObj );
                    this.classScheduleHash.put( Integer.toString( cSchedule.getID() ), cSchedule );
                }

                classesHash.put( id.toString(), sClass );
            }
        }
    }

    private final HashMap<String, Requirement> requirementHash;
    private final HashMap<String, Schedule> schedulesHash;
    private final HashMap<String, Slot> slotsHash;
    private final HashMap<String, SchoolClass> classesHash;
    private final HashMap<String, Room> roomsHash;
    private final HashMap<String, ClassSchedule> classScheduleHash;
    private final GAConfiguration gaConfig;
    private final String requestType;
}
