/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.dblog;

import emc.entity.base.dblog.BaseDBLogSetup;
import emc.enums.enumPersistOptions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author wikus
 */
public class DBLogMap {

    /**
     * The Start point of this map class
     */
    private HashMap<String, Object> DBLogMap;

    /**
     * Creates a new instance of the DBLogMap Used to keep track of the fields
     * and tables that should be logged
     *
     * @param setupList the list returned from a general select query on the
     * BaseDBLogSetup table
     */
    public DBLogMap(List setupList) {
        DBLogMap = new HashMap<String, Object>();
        HashMap<String, Object> map;
        String key;
        String[] tableArray;
        boolean addField = false;
        for (Object o : setupList) {
            BaseDBLogSetup setup = (BaseDBLogSetup) o;
            map = (HashMap<String, Object>) populateMap(DBLogMap, setup.getType(), null);
            tableArray = setup.getTableName().split("\\.");
            for (int i = 2; i < tableArray.length; i++) {
                key = tableArray[i];
                if (i == tableArray.length - 1 && setup.getType().equals(enumPersistOptions.UPDATE.toString())) {
                    map = (HashMap<String, Object>) populateMap(map, key, null);
                    addField = setup.getType().equals(enumPersistOptions.UPDATE.toString());
                } else if (i == tableArray.length - 1) {
                    populateMap(map, key, setup);
                } else {
                    map = (HashMap<String, Object>) populateMap(map, key, null);
                }
            }
            if (addField) {
                //'*' means that all fields should be logged.
                populateMap(map, isBlank(setup.getFieldName()) ? "*" : setup.getFieldName(), setup);
                addField = false;
            }
        }
    }

    /**
     * Addes a new map to the existing one or null if this path is done
     *
     * @param map the existing map
     * @param key the key for the new map
     * @param done indicates the end of the path
     * @return the new map or null if it was the end of the path
     */
    private Object populateMap(HashMap<String, Object> map, String key, BaseDBLogSetup setup) {
        if (!map.containsKey(key)) {
            if (setup != null) {
                map.put(key, setup);
            } else {
                map.put(key, new HashMap<String, HashMap>());
            }
        }
        return map.get(key);
    }

    /**
     * Checks the map to see if the action should be logged
     *
     * @param type Either Insert, Update or Delete
     * @param tableName the table the action was taken on
     * @param fieldName the field the action was taken on or null if not
     * applicable
     * @return null or BaseDBLogSetup - the setup record that caused this
     */
    public BaseDBLogSetup shouldCreateLog(enumPersistOptions type, String tableName, String fieldName) {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(type.toString());
        String[] tableArray = tableName.split("\\.");
        for (int i = 2; i < tableArray.length; i++) {
            temp.add(tableArray[i]);
        }
        if (type.equals(enumPersistOptions.UPDATE)) {
            temp.add(fieldName);
        }
        Object ret = DBLogMap;
        for (String key : temp.toArray(tableArray)) {
            if (ret instanceof HashMap) {
                ret = ((HashMap) ret).get(key);
            }
            if (ret == null) {
                return null;
            } else if (ret instanceof BaseDBLogSetup) {
                return (BaseDBLogSetup) ret;
            }
        }
        return null;
    }

    /**
     * Returns an array containing the field names for which updates should be
     * logged. If null is returned, nothing should be logged. If an empty array
     * is returned, everything should be logged. Otherwise, log only for the
     * fields contained in the array.
     *
     * @param tableClass Table to check.
     * @return An array indicating the field names for which updates should be
     * logged. If null is returned, nothing should be logged. If an empty array
     * is returned, everything should be logged. Otherwise, log only for the
     * fields contained in the array.
     */
    public HashMap<String, BaseDBLogSetup> getFieldsToLog(Class tableClass) {
        HashMap<String, BaseDBLogSetup> ret = new HashMap<String, BaseDBLogSetup>();
        String[] tableArray = tableClass.getName().split("\\.");

        ArrayList<String> temp = new ArrayList<String>();
        temp.add(enumPersistOptions.UPDATE.toString());

        for (int i = 2; i < tableArray.length; i++) {
            temp.add(tableArray[i]);
        }

        Object map = DBLogMap;
        for (String key : temp.toArray(new String[0])) {
            if (((Map) (map)).containsKey(key)) {
                map = ((Map) (map)).get(key);
            } else {
                //No logging set up for table.
                return null;
            }
            //should not happen
            if (map instanceof BaseDBLogSetup) {
                break;
            }
        }
        if (map instanceof BaseDBLogSetup) {
            //should not happen
            ret.put("*", (BaseDBLogSetup) map);
            return ret;
        } else {
            for (Object mapKey : ((Map) (map)).keySet()) {
                ret.put((String) mapKey, (BaseDBLogSetup) ((Map) map).get(mapKey));
            }
        }
        return ret;
    }

    /**
     * This method checks whether a value is blank. (null or "")
     */
    public boolean isBlank(Object toCheck) {
        if (toCheck == null) {
            return true;
        } else if (toCheck.toString().trim().equals("")) {
            return true;
        }

        return false;
    }
}