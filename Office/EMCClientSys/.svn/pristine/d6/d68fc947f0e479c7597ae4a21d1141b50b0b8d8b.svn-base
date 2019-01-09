/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.action.emcimportwizard.resources;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author wikus
 */
public class ColumnMap extends HashMap<String, ColumnInfo> implements Serializable{

    private String tableName;
    private List<String> order;
    private boolean flag = true;

    /**
     * Makes a new instance of the map,
     * sets the table name and the list for ordering the data.
     * @param tableName the table who`s info is stored in this map
     */
    public ColumnMap(
            String tableName) {
        this.tableName = tableName;
        this.order = new ArrayList<String>();
    }

    /**
     * @return the table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * Add the parameters to the map,
     * and the column name to the list.
     * @param arg0
     * @param arg1
     * @return
     */
    @Override
    public ColumnInfo put(String arg0, ColumnInfo arg1) {
        if (this.containsKey(arg0)) {
            this.remove(arg0);
            flag = false;

        }
        if (flag) {
            order.add(arg0);
        }
        flag = true;
        return super.put(arg0, arg1);
    }

    /**
     * returns the content of the map in the order that it was added.
     * @return
     */
    @Override
    public Collection<ColumnInfo> values() {
        Collection<ColumnInfo> collect = new ArrayList<ColumnInfo>();
        for (String s : order) {
            collect.add(this.get(s));
        }
        return collect;
    }

    public List<String> getOrder() {
        return order;
    }

    public void setOrder(List<String> order) {
        this.order = order;
    }
    /**
     * Stores the columns to file
     * @param filePath
     */
    public void store(String filePath,ColumnMap toStore){
    try{
          //use buffering
          OutputStream file = new FileOutputStream(filePath);
          OutputStream buffer = new BufferedOutputStream( file );
          ObjectOutput output = new ObjectOutputStream( buffer );
          try{
            output.writeObject(toStore);
          }
          finally{
            output.close();
          }
        }
        catch(IOException ex){

        }
    }
    /**
     * Loads the columnd from file
     * @param file
     */
    public ColumnMap load(String filePath){
        ColumnMap map = null;
         try{
          //use buffering
          InputStream file = new FileInputStream( filePath );
          InputStream buffer = new BufferedInputStream( file );
          ObjectInput input = new ObjectInputStream ( buffer );
          try{
            //deserialize the data
            map  = (ColumnMap)input.readObject();

          }
          finally{
            input.close();
          }
        }
        catch(ClassNotFoundException ex){

        }
        catch(IOException ex){

        }
        return map;
    }
    
    
}
