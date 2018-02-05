package org.sara.shell;

import java.io.FileNotFoundException;
import org.sara.interfaces.model.Room;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.sara.interfaces.model.GAConfiguration;
import org.sara.interfaces.model.Schedule;
import org.sara.interfaces.model.SchoolClass;
import org.sara.interfaces.model.ClassSchedule;
import org.sara.interfaces.model.Slot;

public class JSONReader {

    public JSONReader( String jsonFile ) throws FileNotFoundException, IOException, ParseException, Exception {
        this.schedulesHash = new HashMap<>();
        this.slotsHash = new HashMap<>();
        this.classesHash = new HashMap<>();
        this.roomsHash = new HashMap<>();
        this.classScheduleHashHash = new HashMap<>();
        this.gaConfig = new GAConfiguration();

        JSONObject jsonObject = (JSONObject) ( ( new JSONParser() ).parse( new FileReader( jsonFile ) ) );

        this.parametersHandler( (JSONObject) jsonObject.get( "ga_config" ) );
        this.schedulesHandler( (JSONArray) jsonObject.get( "schedules" ) );
        this.roomsHandler( (JSONArray) jsonObject.get( "rooms" ) );
        this.slotsHandler( (JSONArray) jsonObject.get( "slots" ) );
        this.classesHandler( (JSONArray) jsonObject.get( "classes" ) );
    }

    public GAConfiguration getGAConfiguration() {
        return gaConfig;
    }

    public HashMap<String, Schedule> getSchedulesHash() {
        return schedulesHash;
    }

    public HashMap<String, Slot> getSlotsHash() {
        return slotsHash;
    }

    public HashMap<String, SchoolClass> getClassesHash() {
        return classesHash;
    }

    public HashMap<String, ClassSchedule> getClassSchedulesHash() {
        return classScheduleHashHash;
    }

    public HashMap<String, Room> getRoomsHash() {
        return roomsHash;
    }

    private void parametersHandler( JSONObject configuration ) {
        if (configuration != null) {
            Object populationNumber = configuration.get( "population_number" );
            Object maxGeneration = configuration.get( "max_generation" );
            Object mutationProbability = configuration.get( "mutation_probability" );
            Object crossoverProbability = configuration.get( "crossover_probability" );
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

            if (crossoverProbability != null) {
                try {
                    gaConfig.setCrossoverProbability( Double.parseDouble( crossoverProbability.toString() ) );
                } catch (NumberFormatException ex) {
                    System.out.println( "Json with wrong value to 'crossover_probability' property. The value was ignored..." );
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
                Object specifications = room.get( "specifications" );

                if (id == null || capacity == null || specifications == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, capacity or specifications) to Room model." );
                }

                if (roomsHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a Room duplicated (id: " + id + ")." );
                }

                roomsHash.put( id.toString(), new Room( Integer.parseInt( id.toString() ),
                        Integer.parseInt( capacity.toString() ) ) );
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
                Object capacity = slot.get( "capacity" );
                Object room = slot.get( "room" );
                Object schedule = slot.get( "schedule" );

                if (id == null || capacity == null || room == null || schedule == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, capacity, room, or schedule) to Slot model." );
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

                    slotsHash.put( id.toString(), new Slot( Integer.parseInt( id.toString() ), scheduleObj, roomObj ) );
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
                Object requirements = s_class.get( "requirements" );

                if (id == null || size == null || schedules == null || requirements == null) {
                    throw new Exception( "Json File is invalid. There is a missing key (id, size, schedules, or requirements) to School Class model." );
                }

                if (classesHash.containsKey( id.toString() )) {
                    throw new Exception( "Json File is invalid. There is a School Class duplicated (id: " + id + ")." );
                }

                //Get School Class Schedules
                JSONArray jschedules = (JSONArray) schedules;
                Iterator sch = jschedules.iterator();
                SchoolClass sClass = new SchoolClass( Integer.parseInt( id.toString() ), Integer.parseInt( size.toString() ) );

                while (sch.hasNext()) {
                    String scheduleID = sch.next().toString();

                    Schedule schObj = schedulesHash.get( scheduleID );

                    if (schObj == null) {
                        throw new Exception( "Json File is invalid. There is any Schedule with id = " + scheduleID + "." );
                    }

                    sClass.addSchedule( schObj );
                    ClassSchedule cSchedule = new ClassSchedule( sClass, schObj );
                    this.classScheduleHashHash.put( Integer.toString( cSchedule.getID() ), cSchedule );
                }

                classesHash.put( id.toString(), sClass );
            }
        }
    }

    private final HashMap<String, Schedule> schedulesHash;
    private final HashMap<String, Slot> slotsHash;
    private final HashMap<String, SchoolClass> classesHash;
    private final HashMap<String, Room> roomsHash;
    private final HashMap<String, ClassSchedule> classScheduleHashHash;
    private final GAConfiguration gaConfig;
}
