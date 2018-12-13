/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.safetystock.resources;

import emc.entity.inventory.InventorySafetyStock;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author wikus
 */
public class SafetyStockMap extends HashMap<Integer, HashMap> {

    private List<String> itemList;
    private List<String> dim1List;
    private List<String> dim2List;
    private List<String> dim3List;
    private List<String> serialList;
    private List<Date> dateList;

    public SafetyStockMap() {
        itemList = new ArrayList<String>();
        dim1List = new ArrayList<String>();
        dim2List = new ArrayList<String>();
        dim3List = new ArrayList<String>();
        serialList = new ArrayList<String>();
        dateList = new ArrayList<Date>();
    }

    @Override
    @Deprecated
    public HashMap put(Integer key, HashMap value) {
        return super.put(key, value);
    }

    @Override
    @Deprecated
    public HashMap get(Object key) {
        return super.get((Integer) key);
    }

    public void put(String item, String dimension1, String dimension2, String dimension3, String serialNo, Date startDate, InventorySafetyStock value) {
        //Fetch Item
        int itemPos = itemList.indexOf(item);
        if (itemPos == -1) {
            itemList.add(item);
            itemPos = itemList.size() - 1;
        }
        HashMap<Integer, HashMap> dim1Map = this.get(itemPos);
        if (dim1Map == null) {
            dim1Map = new HashMap<Integer, HashMap>();
            this.put(itemPos, dim1Map);
        }
        //Fetch Dim 1
        int dim1Pos = dim1List.indexOf(dimension1);
        if (dim1Pos == -1) {
            dim1List.add(dimension1);
            dim1Pos = dim1List.size() - 1;
        }
        HashMap<Integer, HashMap> dim2Map = dim1Map.get(dim1Pos);
        if (dim2Map == null) {
            dim2Map = new HashMap<Integer, HashMap>();
            dim1Map.put(dim1Pos, dim2Map);
        }
        //Fetch Dim 2
        int dim2Pos = dim2List.indexOf(dimension2);
        if (dim2Pos == -1) {
            dim2List.add(dimension2);
            dim2Pos = dim2List.size() - 1;
        }
        HashMap<Integer, HashMap> dim3Map = dim2Map.get(dim2Pos);
        if (dim3Map == null) {
            dim3Map = new HashMap<Integer, HashMap>();
            dim2Map.put(dim2Pos, dim3Map);
        }
        //Fetch Dim 3
        int dim3Pos = dim3List.indexOf(dimension3);
        if (dim3Pos == -1) {
            dim3List.add(dimension3);
            dim3Pos = dim3List.size() - 1;
        }
        HashMap<Integer, HashMap> serialMap = dim3Map.get(dim3Pos);
        if (serialMap == null) {
            serialMap = new HashMap<Integer, HashMap>();
            dim3Map.put(dim3Pos, serialMap);
        }
        //Fetch Serial No
        int serialPos = serialList.indexOf(serialNo);
        if (serialPos == -1) {
            serialList.add(serialNo);
            serialPos = serialList.size() - 1;
        }
        HashMap<Integer, InventorySafetyStock> dateMap = serialMap.get(serialPos);
        if (dateMap == null) {
            dateMap = new HashMap<Integer, InventorySafetyStock>();
            serialMap.put(serialPos, dateMap);
        }
        //Fetch Date
        int datePos = dateList.indexOf(startDate);
        if (datePos == -1) {
            dateList.add(startDate);
            datePos = dateList.size() - 1;
        }
        dateMap.put(datePos, value);
    }

    public InventorySafetyStock get(String item, String dimension1, String dimension2, String dimension3, String serialNo, Date startDate) {
        //Fetch Item
        int itemPos = itemList.indexOf(item);
        if (itemPos == -1) {
            return null;
        }
        HashMap<Integer, HashMap> dim1Map = this.get(itemPos);
        if (dim1Map == null) {
            return null;
        }
        //Fetch Dim 1
        int dim1Pos = dim1List.indexOf(dimension1);
        if (dim1Pos == -1) {
            return null;
        }
        HashMap<Integer, HashMap> dim2Map = dim1Map.get(dim1Pos);
        if (dim2Map == null) {
            return null;
        }
        //Fetch Dim 2
        int dim2Pos = dim2List.indexOf(dimension2);
        if (dim2Pos == -1) {
            return null;
        }
        HashMap<Integer, HashMap> dim3Map = dim2Map.get(dim2Pos);
        if (dim3Map == null) {
            return null;
        }
        //Fetch Dim 3
        int dim3Pos = dim3List.indexOf(dimension3);
        if (dim3Pos == -1) {
            return null;
        }
        HashMap<Integer, HashMap> serialMap = dim3Map.get(dim3Pos);
        if (serialMap == null) {
            return null;
        }
        //Fetch Serial
        int serialPos = serialList.indexOf(serialNo);
        if (serialPos == -1) {
            return null;
        }
        HashMap<Integer, InventorySafetyStock> dateMap = serialMap.get(serialPos);
        if (dateMap == null) {
            return null;
        }
        //Fetch Date
        int datePos = dateList.indexOf(startDate);
        if (datePos == -1) {
            return null;
        }
        return dateMap.get(datePos);
    }

    public List<List<InventorySafetyStock>> getValuesByDate() {
        List<List<InventorySafetyStock>> returnList = new ArrayList<List<InventorySafetyStock>>();

        if (dateList.isEmpty()) {
            return returnList;
        }
        
        List<InventorySafetyStock> valuesByDate;
        
        List<Date> orderedDates = new ArrayList<Date>(dateList);
        Collections.sort(orderedDates);

        int datePos;
        InventorySafetyStock ss;

        //Loop through Items
        for (HashMap<Integer, HashMap> dim1Map : this.values()) {
            //Loop Through Dim1
            for (HashMap<Integer, HashMap> dim2Map : dim1Map.values()) {
                //Loop Through Dim2
                for (HashMap<Integer, HashMap> dim3Map : dim2Map.values()) {
                    //Loop Through Dim3
                    for (HashMap<Integer, HashMap> serialMap : dim3Map.values()) {
                        //Loop Through Serial Number
                        for (HashMap<Integer, InventorySafetyStock> dateMap : serialMap.values()) {
                            //List in Ret List
                            valuesByDate = new ArrayList<InventorySafetyStock>();
                            //Loop Through ordered Dated
                            for (Date d : orderedDates) {
                                //Get Position
                                datePos = dateList.indexOf(d);
                                if (datePos != -1) {
                                    //Get Value from Map
                                    ss = dateMap.get(datePos);
                                    if (ss != null) {
                                        //Add to List
                                        valuesByDate.add(ss);
                                    }
                                }
                            }
                            //Add to Return List
                            returnList.add(valuesByDate);
                        }
                    }
                }
            }
        }
        return returnList;
    }
}
