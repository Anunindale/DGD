/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.framework;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rico
 */
public class EMCDataSourceBean extends EMCEntityBean {
    
    /*
     * The class name of the data source for this bean
     */ 
    private String dataSourceClassName;
    /*
     * The entity class extended by the data source for this bean
     */ 
    private String entityClassName;
    /*
     * Class object representing the data source class
     */ 
    private Class dataSourceClass;
    /*
     * Class object representing the data source entity class
     */ 
    private Class entityClass;
    
    public EMCDataSourceBean(){
        
    }
    
    public void setDataSourceClassName(String dataSourceClassName) {
        try {
            this.dataSourceClassName = dataSourceClassName;
            this.dataSourceClass = Class.forName(dataSourceClassName);
            this.entityClass =  dataSourceClass.getSuperclass();
            this.entityClassName = entityClass.getName();
        } catch (ClassNotFoundException ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Class not found: " + ex.getMessage());
            }
        }
    }
    
    /*
     * Gets all the fields of a given data source (cls) up to the EMCEntityClass level
     */ 
    public List<Field> getFieldListForObject(Class cls, List<Field> theFields){
        Field[] fields = cls.getDeclaredFields();
        theFields.addAll(Arrays.asList(fields));
        if((!cls.equals(EMCEntityClass.class))){
            theFields = getFieldListForObject(cls.getSuperclass(), theFields);
        }
        return theFields;
    }
    
    /*
     * Takes a list of instances of the super class of a data source and returns a list of of objects of the specified subclass.
     */ 
    public List convertSuperToDataSource(Class subClassTableName, List superTableData, EMCUserData userData){
        List convertedData = new ArrayList();
        
        try {
            List<Field> superClassFields = new ArrayList<Field>();
            superClassFields  = this.getFieldListForObject(dataSourceClass.getSuperclass(), superClassFields);

            Object dataSourceInstance = null;
            
            for(Object superClassInstance : superTableData){
                dataSourceInstance = dataSourceClass.newInstance();
                for (Field field : superClassFields) {
                    field.setAccessible(true);
                    field.set(dataSourceInstance, field.get(superClassInstance));
                }
                convertedData.add(dataSourceInstance);
            }
            
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured while trying to convert entity class to data source: " + subClassTableName.getName() + ex.getMessage(), userData);
            }
        }
        
        return convertedData;
    }
    
    /*
     * Takes an instance of the entity class of a data source and returns an instance of the specified subclass.
     */ 
    public Object convertSuperToDataSource(Object superClassInstance, EMCUserData userData){
        Object dataSourceInstance = null;
        
        try {
            List<Field> superClassFields = new ArrayList<Field>();
            superClassFields  = this.getFieldListForObject(dataSourceClass.getSuperclass(), superClassFields);

                dataSourceInstance = dataSourceClass.newInstance();
                for (Field field : superClassFields) {
                    field.setAccessible(true);
                    field.set(dataSourceInstance, field.get(superClassInstance));
                }
            
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured while trying to convert entity class to data source: " + dataSourceClassName + ". " + ex.getMessage(), userData);
            }
        }
        
        return dataSourceInstance;
    }
    
    /*
     * Takes an instance of the data source class and returns an instance of the entity class.
     */ 
    public Object convertDataSourceToSuper(Object dataSourceInstance, EMCUserData userData){
        Object entityInstance = null;
        
        try {
            List<Field> superClassFields = new ArrayList<Field>();
            superClassFields  = this.getFieldListForObject(dataSourceClass.getSuperclass(), superClassFields);

                entityInstance = entityClass.newInstance();
                for (Field field : superClassFields) {
                    field.setAccessible(true);
                    field.set(entityInstance, field.get(dataSourceInstance));
                }
            
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured while trying to convert data source to entity class: " + entityClassName + ". " + ex.getMessage(), userData);
            }
        }
        
        return entityInstance;
    }
    
    /*
     * Gets super class data and converts it to data source data and populates data source fields
     */
    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {

        Collection superData = super.getDataInRange(this.entityClass, userData, start, end);
     
        List thisData = convertSuperToDataSource(theTable, (List)superData, userData);
        
        for (Object instance : thisData) {
           populateDataSourceFields((EMCEntityClass)instance, userData);
        }
        
        return thisData;
        
    }
    
    /*
     * This method is used to populate the fields of a data source object 
     */
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        Logger.getLogger("emc").log(Level.SEVERE, "populateDataSourceFields() not overridden", userData);
        return null;
    }
    
    /**
     * Gets the number of rows in the main entity class
     */
    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return super.getNumRows(entityClass, userData);
    }
    
    /**
     * Cast to super class and call super.insert()
     */ 
    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        Object superClassInstance = entityInsert(iobject, userData);
        EMCEntityClass dataSourceInstance = (EMCEntityClass)convertSuperToDataSource(superClassInstance, userData);
        populateDataSourceFields(dataSourceInstance, userData);
        return dataSourceInstance;
    }
    
    /*
     * Cast to super class and call super.update()
     */ 
    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        Object superClassInstance = entityUpdate(uobject, userData);
        
        if (superClassInstance instanceof EMCEntityClass) {
            EMCEntityClass dataSourceInstance = (EMCEntityClass)convertSuperToDataSource(superClassInstance, userData);
            populateDataSourceFields(dataSourceInstance, userData);
            return dataSourceInstance;
        } else {
            //Update check
            return  superClassInstance;
        }
    }
    
    /*
     * Cast to super class and call super.delete()
     */ 
    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        Object superClassInstance = entityDelete(dobject, userData);
        if (superClassInstance instanceof EMCEntityClass) {
            EMCEntityClass dataSourceInstance = (EMCEntityClass)convertSuperToDataSource(superClassInstance, userData);
            return dataSourceInstance;
        } else {
            //Update check
            return superClassInstance;
        }
    }
    
    /*
     * Blank field validation method
     */
    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        return super.validateField(fieldNameToValidate, convertDataSourceToSuper(theRecord, userData), userData);
    }
    
    /*
     * Override this method when update logic is on the bean of the data source's main entity class
     */
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.update(convertDataSourceToSuper(uobject, userData), userData);
    }
    
    /*
     * Override this method when delete logic is on the bean of the data source's main entity class
     */
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.delete(convertDataSourceToSuper(dobject, userData), userData);
    }
    
    /*
     * Override this method when insert logic is on the bean of the data source's main entity class
     */
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
         return super.insert(convertDataSourceToSuper(iobject, userData), userData);
        
    }
}
