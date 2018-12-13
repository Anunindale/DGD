/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.utility;

import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.LockModeType;

/**
 *
 * @author riaan
 */
@Local
public interface EMCServerUtilityLocal {

    public boolean exists(String table, String column, String value, EMCUserData userData);

    public boolean exists(String table, String[] columns, String[] values, String orAnd, EMCUserData userData);

    public boolean exists(EMCQuery query, EMCUserData userData);

    public Object executeSingleResultQuery(String query, EMCUserData userData);

    public String getCompanyId(String entityClassName, EMCUserData userData);

    public List executeGeneralSelectQuery(String query, EMCUserData userData);

    public List executeGeneralSelectQuery(EMCQuery query, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List executeGeneralSelectQueryInNewTx(EMCQuery query, EMCUserData userData);

    public List executeGeneralSelectQuery(EMCQuery query);

    public Object executeSingleResultQuery(EMCQuery query, EMCUserData userData);

    boolean executeUpdate(String query, EMCUserData userData);

    boolean executeUpdate(EMCQuery query, EMCUserData userData);

    @Deprecated
    public Object findPersisted(Class theClass, Object recordId, EMCUserData userData);

    public List<Object> convertToObjectList(List<?> list);

    public List executeLimitedResultGeneralSelectQuery(EMCQuery query, int start, int max, EMCUserData userData);

    public List executeLimitedResultGeneralSelectQuery(String query, int start, int max, EMCUserData userData);

    public List executeGeneralSelectQueryLimit(String query, EMCUserData userData, int start, int end);

    public String getFieldValueFromQuery(String field, String query, EMCUserData userData);

    Object convertEntityToInstanceOfEntity(EMCEntityClass theEntity, Class theClass, EMCUserData userData);

    List<Field> getFieldListForObject(Class cls, Class superClassLevel, List<Field> theFields);

    public void detachEntities(EMCUserData userData);

    public void lockEntity(EMCEntityClass entity, LockModeType lockMode, EMCUserData userData);

    public void refreshEntity(EMCEntityClass entity, EMCUserData userData);

    public boolean containsEntity(EMCEntityClass entity, EMCUserData userData);

    public Object getEntityReference(Class theEntityClass, Object recordId, EMCUserData userData);

    public Object doClone(Object theObjectToClone, Class theObjectClass, EMCUserData userData);

    public Object convertEntityToDatasource(EMCEntityClass theEntity, Class datasourceClass, EMCUserData userData);

    /**
     * This method returns a boolean indicating whether the given object are
     * equal. Two null references are seen as equal. A null reference a nd a
     * non-null reference are seen as not equal. If both objects are not null,
     * the result of the first object's equals() method is returned. An
     * identical method exists in the Functions class for use on the client.
     *
     * @param object Object to compare to second object.
     * @param otherObject Object to compare to first object.
     * @return A boolean indicating whether the objects are equal.
     */
    public boolean checkObjectsEqual(Object object, Object otherObject);

    /**
     * Compares two doubles This method rounds the two values and compares the
     * result if it is equal it returns 0,
     *
     * @param val1
     * @param val2
     * @return 0 if equal, -1 if val1 less than val2, 1 if val1 greater than
     * val2
     */
    public int compareDouble(double val1, double val2);

    /**
     * Rounds a double value to the specified number of digits
     *
     * @param val
     * @param decimals
     * @return
     */
    public double round(double val, int decimals);

    /**
     * Executes a native query on the database. The query string will be passes
     * straight to SQL This method expects a sqlResultSetmapping to help the
     * manager to manage the entities recieved. This mapping is specified on a
     * entityclass. YOU NEED TO SPECIFY THE COMPANYID!
     *
     * @param query = sql string to execute
     * @param sqlResultsetName defined as anotation on a entityclass
     * @param userData
     * @return
     */
//    public List executeNativeQuery(String query, String sqlResultsetName, EMCUserData userData);
    /**
     * Adds outer joins to the dimension tables and returns the results
     * Construct query as normal adding all the tables and order by's including
     * the dim tables NOTE: do not join the dimension tables on a field, it is
     * handled in here.
     *
     * @param query the query to execute
     * @param sqlResultsetName defined as anotation on a entityclass
     * @param dim1Entity the table to join InventoryDimension1 with
     * @param dim1Field the field to join InventoryDimension1.dimensionId with
     * @param dim2Entity the table to join InventoryDimension2 with
     * @param dim2Field the field to join InventoryDimension2.dimensionId with
     * @param dim3Entity the table to join InventoryDimension3 with
     * @param dim3Field the field to join InventoryDimension3.dimensionId with
     * @param addOrderBy Indicates whether an order by clause should be added.
     * @param sortOrder Indicates the order in which results should sorted. E.g.
     * {1, 2, 3} (default)
     * @param userData plain old EMCUserData
     * @return
     */
    public List executeQuerySortedByDimensions(EMCQuery query, Object sqlResultName, Class dim1Entity, String dim1Field,
                                               Class dim2Entity, String dim2Field, Class dim3Entity, String dim3Field, boolean addOrderBy, int[] sortOrder, EMCUserData userData);

    /**
     * Executes a native query on the database. The query string will be passes
     * straight to SQL This method expects a sqlResultSetmapping to help the
     * manager to manage the entities recieved. This mapping is specified on a
     * entityclass.
     *
     * @param query = query to execute
     * @param sqlResultsetName defined as anotation on a entityclass
     * @param userData
     * @return
     */
    public List executeNativeQuery(EMCQuery query, String sqlResultsetName, EMCUserData userData);

    /**
     * Executes a native query on the database. The query string will be passed
     * straight to SQL For this method mapping is not specified, use when you
     * only need columns back and no entities.
     *
     * @param query = query to execute
     * @param sqlResultsetName defined as anotation on a entityclass
     * @param userData
     * @return
     */
    public List executeNativeQuery(EMCQuery query, EMCUserData userData);

    /**
     * Executes a native query on the database. The query string will be passed
     * straight to SQL For this method will return a list of objects of the
     * specified class.
     *
     * @param query Query to execute
     * @param resultClass The entity class that objects in the returned list
     * should belong to.
     * @param userData Userdata.
     * @return A list of objects of the specified class.
     */
    public List executeNativeQuery(EMCQuery query, Class resultClass, EMCUserData userData);

    public String getServerVersionInfo();

    /**
     * Returns a detached entity, which should be an accurate representation of
     * the data in SQL. This method handles both attached and detached entities.
     *
     * @param entity Entity to find a detached entity for.
     * @param userData User data.
     * @return A detached entity representing the SQL version of the specified
     * entity class.
     */
    public EMCEntityClass findDetachedPersisted(EMCEntityClass entity, EMCUserData userData);

    /**
     * Executes a native update query on the database. The query string will be
     * passed straight to SQL For this method mapping is not specified, use when
     * you only need columns back and no entities.
     *
     * @param query = query to execute
     * @param userData
     * @return int number of rows affected
     */
    public int executeNativeUpdate(EMCQuery query, EMCUserData userData);

    /**
     * This method attempts to copy fields from one entity class to another.
     * Fields which are not present on both entity classes are ignored.
     *
     * @param source Source entity. (Copy fields from)
     * @param destination Destination entity. (Copy fields to)
     * @param userData User Data.
     */
    public void copyFields(EMCEntityClass source, EMCEntityClass destination, EMCUserData userData);

    public List executeLimitedNativeQuery(EMCQuery query, Class returnType, int start, int max, EMCUserData userData);

    public void setRollBackOnly();

    public int executeNativeUpdate(String query, EMCUserData userData);

    /**
     * Builds A SQL Script to insert the record in the List NOTE: Save the
     * returned recordID for later use as the normal Hibernate recordID does not
     * apply to this method.
     *
     * @param recordClass The type of the records in the list
     * @param records The records to insert
     * @param populateEntityClassFields Wheather companyId, createdBy,
     * createdDate should be populated
     * @param recordIDStart The first records recordID;
     * @param userData Plain old EMCUserData
     * @return The recordID updated after all the inserts
     */
    public long insertDirect(Class recordClass, List records, boolean populateEntityClassFields, boolean populateRecordID, long recordIDStart, boolean negativeRecordId, EMCUserData userData);

    @Deprecated
    public List executeNativeQuery(String query, String sqlResultsetName, EMCUserData userData);

    @Deprecated
    public List executeNativeQuery(String query, EMCUserData userData);

    /**
     * Fetched a record ID directly from the BaseGenerator Table in qsl. Returns
     * the record ID increased by 'x' amount so the developer needs to subtract
     * from the returned value
     *
     * @param increaseBy The amount to increase the recordID's by
     * @param userData Plain old user data
     * @return The record ID after increasing
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long getRecordId(int increaseBy, EMCUserData userData);

    /**
     * Pad the input value from the left to be the expectedLenth. If padValue =
     * expected length does nothing
     *
     * @param input - sting to pad
     * @param padValue - what to pad with eg "0"
     * @param expectedLength - how long should the string be
     * @return padded string
     */
    public String padStringFromLeft(String input, String padValue, int expectedLength);

    /**
     * Returns a new BigDecimal instance containing the value of the specified
     * double instance, rounded to two decimals using a hard-coded precision of
     * 15 digits.
     *
     * @param d Double to convert to BigDecimal and round.
     * @return A BigDecimal instance representing the specified BigDecimal value
     * rounded to the specified number of decimals.
     */
    public BigDecimal getBigDecimal(double d);

    /**
     * Use when you need to speed up selects - NO update of data supported. The
     * entities returned will not be managed by the container. You may get dirty
     * reads! Currently only supports/tested for single entity selects. Use this
     * method sparingly.
     *
     * @param conn a JDBC connection may be null - will create one and close on
     * return
     * @param theQuery
     * @param userData
     * @return
     */
    public List exJDBCReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Creates a JDBC connection YOU MUST CLOSE the conenction!! e.g.
     * closeConnectionToDB(); TODO read paramters from DB
     *
     * @param userData
     * @return
     */
    public Connection connectToDatabase(EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Closes a given connection to the Database
     *
     * @param conn
     * @param userData
     */
    public void closeConnectionToDB(Connection conn, EMCUserData userData);

    /**
     * Use when you need to speed up selects - NO update of data supported. The
     * entities returned will not be managed by the container. You may get dirty
     * reads! Currently only supports/tested for single entity selects. Use this
     * method sparingly.
     *
     * @param conn a JDBC connection may be null - will create one and close on
     * return
     * @param theQuery
     * @param userData
     * @return Single first result in set else null
     */
    public EMCEntityClass exJDBCSingleReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Use when you need to speed up selects - NO update of data supported. You
     * may get dirty reads! Use this method sparingly.
     *
     * @param conn a JDBC connection may be null - will create one and close on
     * return
     * @param theQuery
     * @param userData
     * @return A list of Object[]
     */
    public List<Object[]> exJDBCFieldReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException;

    /**
     * Use when you need to speed up selects - NO update of data supported. You
     * may get dirty reads! Use this method sparingly.
     *
     * @param conn a JDBC connection may be null - will create one and close on
     * return
     * @param theQuery
     * @param userData
     * @return A list of Objects
     */
    public java.util.List exJDBCSingleFieldReadQuery(java.sql.Connection conn, emc.framework.EMCQuery theQuery, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean executeUpdateInNewTransaction(EMCQuery query, EMCUserData userData);

    public void insertDirect(Class recordClass, List records, EMCUserData userData);

    /**
     * Returns a new BigDecimal instance containing the value of the specified
     * double instance, rounded to the specified number of decimals using a
     * hard-coded precision of 15 digits.
     *
     * @param d Double to convert to BigDecimal and round.
     * @param decimals Number of decimals to round to.
     * @return A BigDecimal instance representing the specified BigDecimal value
     * rounded to the specified number of decimals.
     */
    public BigDecimal getBigDecimal(double d, int decimals);

    /**
     * Flush the attached entities
     *
     * @throws EMCEntityBeanException
     */
    public void flushEntity(emc.framework.EMCUserData userdata) throws emc.framework.EMCEntityBeanException;

    /**
     * Rounds the specified BigDecimal to the two decimals. This method calls
     * EMCServerUtility#roundBigDecimal(BigDecimal, int).
     *
     * @param toRound BigDecimal to round.
     * @return A rounded representation of the specified BigDecimal.
     */
    public BigDecimal roundBigDecimal(BigDecimal toRound);

    /**
     * Rounds the specified BigDecimal to the specified number of decimals. This
     * method used the BigDecimal#setScale(int, RoundingMode) method and rounds
     * up.
     *
     * @param toRound BigDecimal to round.
     * @param decimals Number of decimals to round to.
     * @return A rounded representation of the specified BigDecimal.
     */
    public BigDecimal roundBigDecimal(BigDecimal toRound, int decimals);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean executeUpdateInNewTransaction(String query, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int executeNativeUpdateInNewTransaction(EMCQuery query, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int executeNativeUpdateInNewTransaction(String query, EMCUserData userData);

    @Deprecated
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object executeNativeSelectInNewTx(String query, EMCUserData userData);

    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object executeNativeSelectInNewTx(EMCQuery query, EMCUserData userData);

    public void insertDirect(String tableToPopulate, Class recordClass, List records, EMCUserData userData);

    public long insertDirect(String tableToPopulate, Class recordClass, List records, boolean populateEntityClassFields, boolean populateRecordID, long recordIDStart, boolean negativeRecordId, boolean leaveNullValues, EMCUserData userData);

    public java.util.List<java.lang.Object[]> exJDBCFieldReadQuery(java.sql.Connection conn, java.lang.String theQuery, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List exJDBCSingleFieldReadQuery(java.sql.Connection conn, java.lang.String theQuery, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    /**
     * Checks if the object in pos of user data equals the value
     *
     * @param pos User Data Position To Check
     * @param value The value to compare
     * @param userData Plain Old User Data
     * @return True if the object in pos equals the value.
     */
    public boolean checkUserData(int pos, java.lang.Object value, emc.framework.EMCUserData userData);
    public List executeGeneralSelectReadOnlyQuery(EMCQuery query, EMCUserData userData);
    public List executeGeneralSelectReadOnlyQuery(String query, EMCUserData userData);
    public void setSessionReadOnly(boolean flag);

    public org.hibernate.ScrollableResults executeScrollReadOnlyQuery(emc.framework.EMCQuery query, emc.framework.EMCUserData userData);
}
