/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.tables;

import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 *
 * @author riaan
 */
@Local
public interface TablesLocal {

    public java.util.List<Object> getEMCTables(emc.framework.EMCUserData userData);

    public void testRelations(EMCUserData userData);

    /**
     * Use this method to get Relations for a particular entity class and field
     * 
     * @param entityClassName
     * @param entityClassField
     * @return List containing EMCTableRelation objects that describe related tables
     */
    public java.util.List getRelations(java.lang.String entityClassName, java.lang.String entityClassField, EMCUserData userData);

    /**
     * Use this method to get Relations for a particular entity class all the fields 
     * will be returned in a HashMap
     * @param entityClassName
     * @return Hashmap containing fields as keys and Lists of type EMCTableRelation
     */
    public java.util.HashMap getRelations(java.lang.String entityClassName, EMCUserData userData);

    public Map<Object, String> getEMCTablesAndLabels(EMCUserData userData);

    /** This method is used to retrieve a Map containing all the tables for a given module in EMC and their corresponding labels. */
    public Map<Object, String> getEMCTablesAndLabels(String module, EMCUserData userData);

    /**
     * Returns all number sequence tables.
     * @param userData User data.
     * @return A Map containing all tables supporting number sequences.
     */
    public Map<Object, String> getNumberSequenceTables(EMCUserData userData);

    /**
     * Returns all number sequence tables in the specified module.
     * @param module Module to filter tables on.
     * @param userData User data
     * @return A Map containing all tables supporting number sequences in the specified module.
     */
    public Map<Object, String> getNumberSequenceTables(String module, EMCUserData userData);

    /**
     * Returns a List of tables in which the specified value occurs.  This method can also be
     * used to update the value of columns containing the specified value.
     * @param value Value to search for.
     * @param update Indicates whether value should be changed to newValue.
     * @param newValue New value for columns containing the specified value.
     * @param userData User data.
     * @return A list of tables in which the specified value appears.
     */
    public List findAllTablesWithValue(String value, boolean update, String newValue, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void executeUpdate(EMCQuery query, EMCUserData userData);
}
