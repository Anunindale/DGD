/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.server.utility;

import emc.bus.base.BaseSystemTableLocal;
import emc.bus.base.systemtables.SystemTableLogicLocal;
import emc.entity.base.BaseDBConnections;
import emc.entity.base.BaseSystemTable;
import emc.entity.base.directinsert.BaseGeneratorDirectInsert;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.base.databaseconnection.DBConnection;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBean;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.functions.math.EMCMath;
import emc.messages.ServerBaseMessageEnum;
import emc.tables.EMCTable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.hibernate.ScrollableResults;

/**
 *
 * @author riaan
 */
@Stateless
public class EMCServerUtility extends EMCBean implements EMCServerUtilityLocal {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private SystemTableLogicLocal sytemTableLogic;
    @EJB
    private BaseSystemTableLocal systemTableBean;
    @Resource
    private EJBContext context;
    private EMCMath math = new EMCMath();
    private final static int MAXCONN = 10;
    private static int connCount = 0;

    public EMCServerUtility() {
    }

    /**
     * Flush the attached entities
     *
     * @throws EMCEntityBeanException
     */
    @Override
    public void flushEntity(EMCUserData userdata) throws EMCEntityBeanException {
        try {
            em.flush();
        } catch (Exception ex) {
            throw new EMCEntityBeanException(ex.getMessage());
        }
    }

    /**
     * Checks that a record exists
     *
     * @param table - on which the record lives - class name
     * @param column - the field that is relevant
     * @param value - the value of that field
     * @param userData - userData for logging
     * @return true if it exists, false otherwise
     */
    @Override
    public boolean exists(String table, String column, String value, EMCUserData userData) {

        //Checks that a value was specified
        if (value != null && !value.equals("")) {
            EMCUserData tableData = userData.copyUserData();

            String companyId = getCompanyId(table, userData);

            //Query String
            String query = "SELECT t.recordID FROM " + table + " t "
                           + "WHERE t." + column + " = '" + value + "'"
                           + (column.equals("companyId") ? "" : "AND "
                                                                + "t.companyId = '" + companyId + "'");

            Query q = em.createQuery(query);

            //Get a list of results from the query
            List results = q.getResultList();

            if (results == null || results.size() < 1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    /**
     * Checks that a record exists. Uses multiple fields in condition.
     */
    @Override
    public boolean exists(String table, String[] columns, String[] values, String orAnd, EMCUserData userData) {

        //Checks that a value was specified
        if ((values != null && values.length != 0) && (columns != null) && (columns.length == values.length)) {

            EMCUserData tableData = userData.copyUserData();

            String companyId = getCompanyId(table, userData);

            //Conditon string
            String condition = "";

            //Build condition
            for (int i = 0; i < columns.length; i++) {
                condition += " " + orAnd + " t." + columns[i] + " = '" + values[i] + "'";
            }

            //Query String
            String query = "SELECT t.recordID FROM " + table + " t "
                           + "WHERE "
                           + "t.companyId = '" + companyId + "' " + condition;

            Query q = em.createQuery(query);

            //Get a list of results from the query
            List results = q.getResultList();

            if (results == null || results.size() < 1) {
                return false;
            } else {
                return true;
            }
        } else {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Invalid parameters passed to exist method.  Must pass 2 String arrays of equal lenght and !null", userData);
            }
            return false;
        }
    }

    /**
     * Checks that a record exists. Uses an EMCQuery.
     */
    @Override
    public boolean exists(EMCQuery query, EMCUserData userData) {

        if (executeGeneralSelectQuery(query, userData).size() != 0) {
            return true;
        } else {
            if (EMCDebug.getDebug()) {
                //           Logger.getLogger("emc").log(Level.WARNING, "No records matching criteria exists", userData);
            }
            return false;
        }
    }

    /**
     * Returns null if exception occurs you MUST add to your Query WHERE ... AND
     * x.companyId = 'something' you SHOULD REALLY use the getCompanyId method
     * to get your companyId
     */
    @Override
    public Object executeSingleResultQuery(String query, EMCUserData userData) {
        Object ret = null;
        try {
            ret = em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                System.out.println("The Query: " + query.toString());
                e.printStackTrace();
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute single Result Query:" + e.getMessage(), userData);
            }
        }
        return ret;
    }

    /**
     * Returns null if exception occurs DO NOT add companyId check to query this
     * will be done by this method
     */
    @Override
    public Object executeSingleResultQuery(EMCQuery query, EMCUserData userData) {
        Object ret = null;
        //Get all tables in query
        super.checkCompanyId(query, userData);
        try {
            ret = em.createQuery(query.toString()).getSingleResult();
        } catch (NoResultException e) {
            //Ignore
        } catch (Exception e) {
            System.out.println("The Query: " + query.toString());
            e.printStackTrace();
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to execute single Result Query:" + e.getMessage(), userData);
            }
        }
        return ret;
    }

    /**
     * Returns the
     */
    @Override
    public String getCompanyId(String entityClassName, EMCUserData userData) {
        String ret = "";
        if (sytemTableLogic.isTableSystemTable(entityClassName)) {
            ret = emc.constants.systemConstants.defaultCompanyId();
        } else {
            ret = userData.getCompanyId();
        }
        return ret;
    }

    /**
     * returns new ArrayList() if exception occurs Use this method if your query
     * is too complex to be handled by EMCQuery you MUST add to your Query WHERE
     * ... AND x.companyId = 'something' you SHOULD REALLY use the getCompanyId
     * method to get your companyId
     */
    @Override
    public List executeGeneralSelectQuery(String query, EMCUserData userData) {
        List ret = new ArrayList();
        try {
            ret = em.createQuery(query).getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);

            }
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * returns new ArrayList() if exception occurs Use this method if your query
     * is too complex to be handled by EMCQuery you MUST add to your Query WHERE
     * ... AND x.companyId = 'something' you SHOULD REALLY use the getCompanyId
     * method to get your companyId. Results can be limited.
     */
    @Override
    public List executeGeneralSelectQueryLimit(String query, EMCUserData userData, int start, int end) {
        List ret = new ArrayList();
        try {
            Query q = em.createQuery(query);
            q.setFirstResult(start);
            q.setMaxResults(end);
            ret = q.getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
            }
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * returns new ArrayList() if exception occurs use this method if you have a
     * relatively simple query that can be constructed by EMCQuery DO NOT add
     * companyId check to query this will be done by this method
     */
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public List executeGeneralSelectQueryInNewTx(EMCQuery query, EMCUserData userData) {
        return executeGeneralSelectQuery(query, userData);
    }
    /**
     * Set session to read only 
     * @param flag 
     */
    @Override
    public void setSessionReadOnly(boolean flag){
        org.hibernate.Session session = ((org.hibernate.ejb.EntityManagerImpl) em.getDelegate()).getSession();
        session.setDefaultReadOnly(flag);
    }
    @Override
    public ScrollableResults executeScrollReadOnlyQuery(EMCQuery query,EMCUserData userData){
        org.hibernate.Session session = ((org.hibernate.ejb.EntityManagerImpl) em.getDelegate()).getSession();
        try{
            return session.createQuery(query.toString()).setReadOnly(true).scroll();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }
    /**
     * returns new ArrayList() if exception occurs use this method if you have a
     * relatively simple query that can be constructed by EMCQuery DO NOT add
     * companyId check to query this will be done by this method
     * Sets Hibernate to read only
     */
    @Override
    public List executeGeneralSelectReadOnlyQuery(String query, EMCUserData userData) {
        List ret = new ArrayList();
        try {
            
            Query x = em.createQuery(query);
            x.setHint("org.hibernate.readOnly","true");
	    //x.setHint("org.hibernate.flushMode","MANUAL");
            ret = x.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
            }
        }
        return ret;
    }
    /**
     * returns new ArrayList() if exception occurs use this method if you have a
     * relatively simple query that can be constructed by EMCQuery DO NOT add
     * companyId check to query this will be done by this method
     * Sets Hibernate to read only
     */
    @Override
    public List executeGeneralSelectReadOnlyQuery(EMCQuery query, EMCUserData userData) {
        List ret = new ArrayList();
        super.checkCompanyId(query, userData);
        try {
            Query x = em.createQuery(query.toString());
            x.setHint("org.hibernate.readOnly","true");
	    //x.setHint("org.hibernate.flushMode","MANUAL");
            ret = x.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
            }
        }
        return ret;
    }
    /**
     * returns new ArrayList() if exception occurs use this method if you have a
     * relatively simple query that can be constructed by EMCQuery DO NOT add
     * companyId check to query this will be done by this method
     */
    @Override
    public List executeGeneralSelectQuery(EMCQuery query, EMCUserData userData) {
        List ret = new ArrayList();
        super.checkCompanyId(query, userData);
        try {
            ret = em.createQuery(query.toString()).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
            }
        }
        return ret;
    }

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
    @Override
    @Deprecated
    public List executeNativeQuery(String query, String sqlResultsetName, EMCUserData userData) {
        List ret = new ArrayList();
        try {
            ret = em.createNativeQuery(query, sqlResultsetName).getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);

            }
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public int executeNativeUpdate(String query, EMCUserData userData) {
        try {
            return em.createNativeQuery(query).executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute native update:" + e.getMessage(), userData);
            }
        }
        return -1;
    }

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
    @Override
    public List executeNativeQuery(EMCQuery query, String sqlResultsetName, EMCUserData userData) {
        super.checkCompanyId(query, userData);
        return executeNativeQuery(query.getNativeQuery(), sqlResultsetName, userData);
    }

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
    @Override
    public List executeNativeQuery(EMCQuery query, EMCUserData userData) {
        super.checkCompanyId(query, userData);
        List ret = new ArrayList();
        try {
            ret = em.createNativeQuery(query.getNativeQuery()).getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);

            }
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    @Deprecated
    public List executeNativeQuery(String query, EMCUserData userData) {
        List ret = new ArrayList();
        try {
            ret = em.createNativeQuery(query).getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);

            }
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List executeLimitedNativeQuery(EMCQuery query, Class returnType, int start, int max, EMCUserData userData) {
        List ret = new ArrayList();
        try {
            Query q = em.createNativeQuery(query.getNativeQuery(), returnType);
            q.setFirstResult(start);
            q.setMaxResults(max);
            ret = q.getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);

            }
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Executes a native update query on the database. The query string will be
     * passed straight to SQL For this method mapping is not specified, use when
     * you only need columns back and no entities.
     *
     * @param query = query to execute
     * @param userData
     * @return int number of rows affected or -1 if query failed
     */
    @Override
    public int executeNativeUpdate(EMCQuery query, EMCUserData userData) {
        super.checkCompanyId(query, userData);
        int ret = 0;
        try {
            ret = em.createNativeQuery(query.getNativeQuery()).executeUpdate();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
            }
            e.printStackTrace();
            
            ret = -1;
        }
        return ret;
    }

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
    @Override
    public List executeNativeQuery(EMCQuery query, Class resultClass, EMCUserData userData) {
        super.checkCompanyId(query, userData);
        List ret = new ArrayList();
        try {
            ret = em.createNativeQuery(query.getNativeQuery(), resultClass).getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);

            }
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Adds outer joins to the dimension tables and returns the results
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
     * {1, 2, 3} (default). The default will be used if null is passed to this
     * method.
     * @param userData plain old EMCUserData
     * @return
     */
    @Override
    public List executeQuerySortedByDimensions(EMCQuery query, Object sqlResultName, Class dim1Entity, String dim1Field,
                                               Class dim2Entity, String dim2Field, Class dim3Entity, String dim3Field, boolean addOrderBy, int[] sortOrder, EMCUserData userData) {

        if (sortOrder == null) {
            sortOrder = new int[]{1, 3, 2};
        }

        for (int sort : sortOrder) {
            if (sort == 1 && dim1Entity != null && dim1Field != null) {
                query.addLeftOuterJoin(dim1Entity, dim1Field, InventoryDimension1.class, "dimensionId");
                if (addOrderBy) {
                    query.addOrderBy("sortCode", InventoryDimension1.class.getName());
                }
            } else {
                if (sort == 2 && dim2Entity != null && dim2Field != null) {
                    query.addLeftOuterJoin(dim2Entity, dim2Field, InventoryDimension2.class, "dimensionId");
                    if (addOrderBy) {
                        query.addOrderBy("sortCode", InventoryDimension2.class.getName());
                    }
                } else {
                    if (sort == 3 && dim3Entity != null && dim3Field != null) {
                        query.addLeftOuterJoin(dim3Entity, dim3Field, InventoryDimension3.class, "dimensionId");
                        if (addOrderBy) {
                            query.addOrderBy("sortCode", InventoryDimension3.class.getName());
                        }
                    }
                }
            }
        }

        if (sqlResultName == null) {
            return executeNativeQuery(query, userData);
        } else {
            if (sqlResultName instanceof Class) {
                return executeNativeQuery(query, (Class) sqlResultName, userData);
            } else {
                return executeNativeQuery(query, (String) sqlResultName, userData);
            }
        }
    }

    /**
     * returns new ArrayList() if exception occurs use this method if you have a
     * relatively simple query that can be constructed by EMCQuery
     */
    @Override
    public List executeGeneralSelectQuery(EMCQuery query) {
        List ret = new ArrayList();
        try {
            ret = em.createQuery(query.toString()).getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), e);
            }
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * returns new ArrayList() if exception occurs use this method if you have a
     * relatively simple query that can be constructed by EMCQuery DO NOT add
     * companyId check to query this will be done by this method This method
     * returns list of results in the specified range. (max records, starting at
     * position start)
     */
    @Override
    public List executeLimitedResultGeneralSelectQuery(EMCQuery query, int start, int max, EMCUserData userData) {
        List ret = new ArrayList();
        query.removeAnd("companyId");
        query.addAnd("companyId", this.getCompanyId(query.getEntityClassName(), userData));
        try {
            Query q = em.createQuery(query.toString());
            q.setFirstResult(start);
            q.setMaxResults(max);
            ret = q.getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * returns new ArrayList() if exception occurs Use this method if your query
     * is too complex to be handled by EMCQuery you MUST add to your Query WHERE
     * ... AND x.companyId = 'something' you SHOULD REALLY use the getCompanyId
     * method to get your companyId This method returns list of results in the
     * specified range. (max records, starting at position start)
     */
    @Override
    public List executeLimitedResultGeneralSelectQuery(String query, int start, int max, EMCUserData userData) {
        List ret = new ArrayList();
        try {
            Query q = em.createQuery(query.toString());
            q.setFirstResult(start);
            q.setMaxResults(max);
            ret = q.getResultList();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute query:" + e.getMessage(), userData);
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Executes an update. Returns a boolean indicating whether the update was
     * successful Include companyId in query
     */
    @Override
    public boolean executeUpdate(String query, EMCUserData userData) {
        try {
            em.createQuery(query).executeUpdate();
            return true;
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute update:" + e.getMessage(), userData);
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Executes an update. Returns a boolean indicating whether the update was
     * successful Include companyId in query
     */
    @Override
    public boolean executeUpdate(EMCQuery query, EMCUserData userData) {
        try {
            em.createQuery(query.toString()).executeUpdate();
            return true;
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to execute update:" + e.getMessage(), userData);
            }
            e.printStackTrace();
            return false;
        }
    }

    /**
     * checks if an enity is managed by the container
     *
     * @param entity
     * @param userData
     * @return
     */
    @Override
    public boolean containsEntity(EMCEntityClass entity, EMCUserData userData) {
        boolean ret = false;
        try {
            ret = em.contains(entity);
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to check if entity in in container:" + entity + " " + e.getMessage(), userData);
                e.printStackTrace();
            }
        }
        return ret;
    }

    /**
     * Gets a reference to the Entity does go to the DB
     *
     * @param theEntityClass
     * @param recordId
     * @param userData
     * @return
     */
    @Override
    public Object getEntityReference(Class theEntityClass, Object recordId, EMCUserData userData) {
        try {
            return em.getReference(theEntityClass, recordId);
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to retrieve persisted object:" + e.getMessage(), userData);
                e.printStackTrace();
            }
            return null;
        }
    }

    /**
     * Finds a persisted record Returns null if an exception occurs
     */
    @Override
    @Deprecated
    public Object findPersisted(Class theClass, Object recordId, EMCUserData userData) {
        try {
            return em.find(theClass, recordId);
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to retrieve persisted object:" + e.getMessage(), userData);
                e.printStackTrace();
            }
            return null;
        }

    }

    /**
     * Locks the entity class for read or write
     *
     * @param entity
     * @param lockMode
     * @param userData
     */
    @Override
    public void lockEntity(EMCEntityClass entity, LockModeType lockMode, EMCUserData userData) {
        try {
            em.lock(entity, lockMode);
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to lock entity:" + entity + " " + e.getMessage(), userData);
                e.printStackTrace();
            }
        }
    }

    /**
     * Refersh the given entity all changes will be lost
     *
     * @param entity
     * @param userData
     */
    @Override
    public void refreshEntity(EMCEntityClass entity, EMCUserData userData) {
        try {
            em.refresh(entity);
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to refresh entity:" + entity + " " + e.getMessage(), userData);
                e.printStackTrace();
            }
        }

    }

    /**
     * Detach entities
     *
     * @param userData
     */
    @Override
    public void detachEntities(EMCUserData userData) {
        em.clear();
    }

    /**
     * Converts a list of any type to a list of objects
     *
     */
    @Override
    public List<Object> convertToObjectList(List<?> list) {
        List<Object> ret = new ArrayList<Object>();

        for (Object ob : list) {
            ret.add(ob);
        }

        return ret;
    }

    /**
     * Extracts the value of a specified field from a specified query.
     */
    @Override
    public String getFieldValueFromQuery(String field, String query, EMCUserData userData) {
        StringBuilder ret = new StringBuilder("");

        int pos = query.indexOf(field);

        if (pos != -1) {
            String subString = query.substring(pos);

            pos = subString.indexOf("'");

            for (int i = pos + 1; pos < subString.length(); i++) {
                if (subString.charAt(i) != '\'') {
                    ret.append(subString.charAt(i));
                } else {
                    if (subString.substring(i, i + 1).equals("''")) {
                        //Single quotes escaped
                        ret.append("'");
                    } else {
                        break;
                    }
                }

            }
        }

        return ret.toString();
    }

    /**
     * Converts the given entity class to an instance of the specified class. An
     * exception is thrown if the object cannot be converted. For a class to be
     * converted, the class specified must contain all of the fields on the
     * class of which the given object is an instance.
     */
    @Override
    public Object convertEntityToInstanceOfEntity(EMCEntityClass theEntity, Class theClass, EMCUserData userData) {

        if (!EMCEntityClass.class.isAssignableFrom(theClass)) {
            throw new IllegalArgumentException("Class passed to convertEntityToInstanceOfEntity() must be an entity class");
        }

        Object newInstance = null;

        try {
            List<Field> objectClassFields = new ArrayList<Field>();
            objectClassFields = this.getFieldListForObject(theClass, EMCEntityClass.class, objectClassFields);

            newInstance = theClass.newInstance();
            Class sourceClass = theEntity.getClass();
            Field sourceField;
            for (Field field : objectClassFields) {
                Class tempSourceClass = sourceClass;
                sourceField = null;

                //This is a hack.  Updates made to this method for requirements planning broke cancellation of Picking Lists lines and Purchase Orders.
                while (sourceField == null && !EMCTable.class.equals(tempSourceClass)) {
                    try {
                        sourceField = tempSourceClass.getDeclaredField(field.getName());
                        break;
                    } catch (NoSuchFieldException ex) {
                        //This is a hack.  Updates made to this method for requirements planning broke cancellation of Picking Lists lines and Purchase Orders.
                        tempSourceClass = tempSourceClass.getSuperclass();
                    }
                }

                if (sourceField != null) {
                    sourceField.setAccessible(true);
                    field.setAccessible(true);
                    field.set(newInstance, sourceField.get(theEntity));
                } else {
                    throw new Exception("Field " + field.getName() + " is not found on both classes.");
                }
            }

        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured while trying to convert entity class to data source: " + ex.getMessage(), userData);
                ex.printStackTrace();
            }
        }
        return newInstance;
    }

    /**
     * Gets all the fields of a class up to the specified class level.
     */
    @Override
    public List<Field> getFieldListForObject(Class cls, Class superClassLevel, List<Field> theFields) {
        Field[] fields = cls.getDeclaredFields();
        theFields.addAll(Arrays.asList(fields));
        if ((!cls.equals(superClassLevel))) {
            theFields = getFieldListForObject(cls.getSuperclass(), superClassLevel, theFields);
        }

        return theFields;
    }

    /**
     * Coppies an Object.
     *
     * @param theObjectToClone The Object To Copy
     * @param theObjectClass The Class Of the Object to copy
     * @param userData Normal old user data
     * @return A Copy of the Object
     */
    @Override
    public Object doClone(
            Object theObjectToClone, Class theObjectClass, EMCUserData userData) {
        try {
            Object retObject = theObjectClass.newInstance();
            Field[] fields = theObjectClass.getDeclaredFields();
            Object value;

            for (Field f : fields) {
                f.setAccessible(true);
                value =
                        f.get(theObjectToClone);
                if (value != null) {
                    f.set(retObject, value);
                }

            }
            return retObject;
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed To Clone Object", userData);
                ex.printStackTrace();
            }

            return null;
        }

    }

    @Override
    public Object convertEntityToDatasource(
            EMCEntityClass theEntity, Class datasourceClass, EMCUserData userData) {

        if (!EMCEntityClass.class.isAssignableFrom(datasourceClass)) {


            throw new IllegalArgumentException(
                    "Class passed to convertEntityToDatasource() must be an entity class");
        }

        Object newInstance = null;

        try {
            List<Field> objectClassFields = new ArrayList<Field>();
            objectClassFields =
                    this.getFieldListForObject(
                    datasourceClass, EMCEntityClass.class, objectClassFields);

            newInstance = datasourceClass.newInstance();






            for (Field field : objectClassFields) {
                field.setAccessible(true);
                try {
                    field.set(newInstance, field.get(theEntity));
                } catch (IllegalArgumentException iae) {
                }
            }
        } catch (Exception ex) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Exception occured while trying to convert entity class to data source: " + ex.getMessage(), userData);
                ex.printStackTrace();
            }

        }

        return newInstance;
    }

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
    @Override
    public boolean checkObjectsEqual(Object object, Object otherObject) {
        if (object == null && otherObject == null) {
            //Both objects are null
            return true;
        } else {
            if (object == null || otherObject == null) {
                //One of the objects is null
                return false;
            } else {
                if (object instanceof String && otherObject instanceof String) {
                    return ((String) object).equalsIgnoreCase((String) otherObject);
                } else {
                    if (object instanceof BigDecimal && otherObject instanceof BigDecimal) {
                        return ((BigDecimal) object).compareTo((BigDecimal) otherObject) == 0;
                    } else {
                        //Both objects are not null
                        return object.equals(otherObject);
                    }
                }
            }
        }

    }

    /**
     * Calculates a number to the specified power
     */
    private double pow(double value, int power) {
        double ret = 1;

        for (int i = 1; i
                        <= power; i++) {
            ret = ret * value;
        }

        return ret;
    }

    /**
     * Compares two double values. This method rounds the two values and
     * compares the result if it is equal it returns 0,
     *
     * @param val1
     * @param val2
     * @return 0 if equal, -1 if val1 less than val2, 1 if val1 greater than
     * val2
     */
    @Override
    public int compareDouble(double val1, double val2) {
        return math.compareDouble(val1, val2);
    }

    /**
     * Rounds a double value to the specified number of digits
     *
     * @param val
     * @param decimals
     * @return
     */
    @Override
    public double round(double val, int decimals) {
        return Math.round(val * this.pow(10d, decimals)) / pow(10, decimals);
    }

    @Override
    public String getServerVersionInfo() {
        try {
            StringBuilder versionInfo = new StringBuilder();
            String lineRead;

            BufferedReader versionReader;
            /*= new BufferedReader(new InputStreamReader(
             EMCServerUtility.class.getResourceAsStream("/servercore-buildversion.num")));

             for (int i = 0;
             i <
             3; i++) {
             lineRead = versionReader.readLine();
             if (i == 0) {
             continue;
             } else {
             versionInfo.append(lineRead + "|");
             }*/
            versionInfo.append("#Tue Jan 08 10:18:45 SAST 2013" + "|");
            versionInfo.append("build.number=1382" + "|");
            versionReader = new BufferedReader(new InputStreamReader(
                    EMCServerUtility.class.getResourceAsStream("/serversys-buildversion.num")));
            for (int i = 0;
                 i
                 < 3; i++) {
                lineRead = versionReader.readLine();
                if (i == 0) {
                    continue;
                } else {
                    versionInfo.append(lineRead + "|");
                }
            }
            versionReader = new BufferedReader(new InputStreamReader(
                    EMCServerUtility.class.getResourceAsStream("/genlibcore-buildversion.num")));
            for (int i = 0;
                 i
                 < 3; i++) {
                lineRead = versionReader.readLine();
                if (i == 0) {
                    continue;
                } else {
                    versionInfo.append(lineRead + "|");
                }
            }
            versionReader = new BufferedReader(new InputStreamReader(
                    EMCServerUtility.class.getResourceAsStream("/genlibsys-buildversion.num")));
            for (int i = 0;
                 i
                 < 3; i++) {
                lineRead = versionReader.readLine();
                if (i == 0) {
                    continue;
                } else {
                    versionInfo.append(lineRead + "|");
                }
            }
            return versionInfo.toString();
        } catch (IOException ex) {
            if (EMCDebug.getDebug()) {
                System.out.println("Failed to get Server Version Info: " + ex.getMessage());
            }

            return "Development";
        }

    }

    /**
     * Returns a detached entity, which should be an accurate representation of
     * the data in SQL. This method handles both attached and detached entities.
     *
     * @param entity Entity to find a detached entity for.
     * @param userData User data.
     * @return A detached entity representing the SQL version of the specified
     * entity class.
     */
    @Override
    public EMCEntityClass findDetachedPersisted(
            EMCEntityClass entity, EMCUserData userData) {
        if (containsEntity(entity, userData)) {
            return findSQLVersionForEntity(entity, userData);
        } else {
            return (EMCEntityClass) findPersisted(entity.getClass(), entity.getRecordID(), userData);
        }

    }

    /**
     * This method attempts to copy fields from one entity class to another.
     * Fields which are not present on both entity classes are ignored.
     *
     * @param source Source entity. (Copy fields from)
     * @param destination Destination entity. (Copy fields to)
     * @param userData User Data.
     */
    @Override
    public void copyFields(EMCEntityClass source, EMCEntityClass destination, EMCUserData userData) {
        Class theClass = source.getClass();

        while (!theClass.equals(EMCEntityClass.class)) {
            Field[] fields = theClass.getDeclaredFields();








            for (Field field : fields) {
                try {
                    field.setAccessible(true);
                    field.set(destination, field.get(source));
                } catch (Exception ex) {
                    //Only print stack trace in debug mode.
                    if (EMCDebug.getDebug()) {
                        ex.printStackTrace();
                    }
                }
            }
            theClass = theClass.getSuperclass();
        }

    }

    @Override
    public void setRollBackOnly() {
        context.setRollbackOnly();
    }

    @Override
    public void insertDirect(Class recordClass, List records, EMCUserData userData) {
        insertDirect(null, recordClass, records, userData);
    }

    @Override
    public void insertDirect(String tableToPopulate, Class recordClass, List records, EMCUserData userData) {
        long recId = util.getRecordId(records.size(), userData);
        insertDirect(tableToPopulate, recordClass, records, true, true, recId, true, false, userData);
    }

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
    @Override
    public long insertDirect(Class recordClass, List records, boolean populateEntityClassFields, boolean populateRecordID, long recordIDStart, boolean negativeRecordId, EMCUserData userData) {
        return insertDirect(null, recordClass, records, populateEntityClassFields, populateRecordID, recordIDStart, negativeRecordId, false, userData);
    }

    @Override
    public long insertDirect(String tableToPopulate, Class recordClass, List records, boolean populateEntityClassFields, boolean populateRecordID, long recordIDStart, boolean negativeRecordId, boolean leaveNullValues, EMCUserData userData) {
        if (records != null && !records.isEmpty()) {
            StringBuilder insertString = null;
            String insertTable = isBlank(tableToPopulate) ? recordClass.getSimpleName() : tableToPopulate;
            List<Field> tableFields = new ArrayList<Field>();
            do {
                tableFields.addAll(Arrays.asList(recordClass.getDeclaredFields()));
                recordClass =
                        recordClass.getSuperclass();
            } while (!recordClass.getName().equals(EMCTable.class.getName()));
            Object value;
            boolean addValue;
            boolean addComma = false;
            boolean addFieldsComma;
            boolean createBuilder = true;
            int count = 0;
            int insertSize = 100;
            for (Object rec : records) {
                if (createBuilder) {
                    insertString = new StringBuilder("INSERT INTO ");
                    insertString.append(insertTable);
                    insertString.append("(");
                    for (Field f : tableFields) {
                        insertString.append(f.getName());
                        if (tableFields.indexOf(f) != tableFields.size() - 1) {
                            insertString.append(", ");
                        }
                    }
                    insertString.append(") VALUES ");
                    addComma = false;
                    addFieldsComma = false;
                    createBuilder = false;
                }
                if (negativeRecordId) {
                    recordIDStart--;
                } else {
                    recordIDStart++;
                }
                if (addComma) {
                    insertString.append(", ");
                }
                insertString.append("(");
                addFieldsComma = false;
                for (Field f : tableFields) {
                    addValue = true;
                    if (addFieldsComma) {
                        insertString.append(", ");
                    }
                    if (f.getName().equals("recordID")) {
                        try {
                            f.setAccessible(true);
                            insertString.append(populateRecordID ? recordIDStart : f.get(rec));
                        } catch (Exception ex) {
                            insertString.append("0");
                        }
                        addValue = false;
                    }
                    if (populateEntityClassFields && addValue) {
                        if (f.getName().equals("companyId")) {
                            insertString.append("\'");
                            insertString.append(userData.getCompanyId());
                            insertString.append("\'");
                            addValue = false;
                        } else if (f.getName().equals("createdBy")) {
                            insertString.append("\'");
                            insertString.append(userData.getUserName());
                            insertString.append("\'");
                            addValue = false;
                        } else if (f.getName().equals("createdDate")) {
                            insertString.append("\'");
                            insertString.append(Functions.date2SQLString(Functions.nowDate()));
                            insertString.append("\'");
                            addValue = false;
                        } else if (f.getName().equals("createdTime")) {
                            insertString.append("\'");
                            insertString.append(Functions.date2String(Functions.nowDate(), "HH:mm:ss"));
                            insertString.append("\'");
                            addValue = false;
                        } else if (f.getName().equals("modifiedDate")) {
                            insertString.append("null");
                            addValue = false;
                        } else if (f.getName().equals("modifiedTime")) {
                            insertString.append("null");
                            addValue = false;
                        }
                    }
                    if (addValue) {
                        try {
                            f.setAccessible(true);
                            value = f.get(rec);
                            if (value instanceof Number) {
                                if (value == null && !leaveNullValues) {
                                    insertString.append(0);
                                } else {
                                    insertString.append(value);
                                }
                            } else {
                                if (value instanceof Boolean) {
                                    if (value == null && !leaveNullValues) {
                                        insertString.append(false);
                                    } else {
                                        insertString.append(value);
                                    }
                                } else {
                                    if (value == null) {
                                        insertString.append("null");
                                    } else {
                                        if (value instanceof Date) {
                                            insertString.append("\'");
                                            insertString.append(Functions.date2SQLString((Date) value));
                                            insertString.append("\'");
                                        } else {
                                            insertString.append("\'");
                                            insertString.append(value);
                                            insertString.append("\'");
                                        }
                                    }
                                }
                            }
                        } catch (Exception ex) {
                            insertString.append("null");
                        }
                    }
                    addFieldsComma = true;
                }
                insertString.append(")");
                addComma = true;
                count++;
                if (count == insertSize) {
                    insertString.append(";");
                    executeNativeUpdate(insertString.toString(), userData);
                    insertString = null;
                    createBuilder = true;
                    count = 0;
                }
            }
            if (insertString != null && insertString.length() != 0) {
                insertString.append(";");
                executeNativeUpdate(insertString.toString(), userData);
            }
        }

        if (negativeRecordId) {
            recordIDStart--;
        } else {
            recordIDStart++;
        }

        return recordIDStart;
    }

    /**
     * Pad the input value from the left to be the expectedLenth. If padValue =
     * expected length does nothing
     *
     * @param input - sting to pad
     * @param padValue - what to pad with eg "0"
     * @param expectedLength - how long should the string be
     * @return padded string
     */
    @Override
    public String padStringFromLeft(
            String input, String padValue, int expectedLength) {
        StringBuilder builder = new StringBuilder(input);
        //If customer id is too short, pad with zeroes up to 6 characters/
        if (builder.length() < expectedLength) {
            int pad = expectedLength - builder.length();

            for (int i = 0; i
                            < pad; i++) {
                builder.insert(0, padValue);
            }

        }
        return builder.toString();
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public long getRecordId(int increaseBy, EMCUserData userData) {
        try {
            //ensure that baseGeneratorDirectInsert is a system table
            if (!sytemTableLogic.isTableSystemTable(BaseGeneratorDirectInsert.class.getName())) {
                //make it one
                BaseSystemTable sys = new BaseSystemTable();
                sys.setDescription("Base Generator Direct");
                sys.setTableName(BaseGeneratorDirectInsert.class.getName());
                systemTableBean.insert(sys, userData);
                sytemTableLogic.refresh();//reload the map of system tables
            }
            boolean insert = false;
            BaseGeneratorDirectInsert baseGen = null;
            //read from BaseGeneratorDirectInsert and block reads to table;
            EMCQuery resHQ = new EMCQuery(enumQueryTypes.SELECT, BaseGeneratorDirectInsert.class);
            resHQ.setNativeForUpdate(true); //this blocks for update
            List<BaseGeneratorDirectInsert> resList = executeNativeQuery(resHQ, BaseGeneratorDirectInsert.class, userData);
            if (resList == null || resList.size() == 0) {
                //insert an instance
                baseGen = new BaseGeneratorDirectInsert(); //will load the default starting at 6Gig
                insert = true;
            } else {
                baseGen = resList.get(0);
            }
            baseGen.setNextRecordID(baseGen.getNextRecordID() + increaseBy);
            if (insert) {
                systemTableBean.insert(baseGen, userData); //lazy use of super to persist
            } else {
                systemTableBean.update(baseGen, userData);
            }
            return baseGen.getNextRecordID();
        } catch (Exception e) {
            logMessage(Level.SEVERE, ServerBaseMessageEnum.DRCT_ISRT_RECID_FAIL, userData);
        }
        return 0; //failed
    }

    /**
     * Use when you need to speed up selects - NO update of data supported. The
     * entities returned will not be managed by the container. You may get dirty
     * reads! Currently only supports/tested for single entity selects. Use this
     * method sparingly. Preferably not at all!!!! NO AGG Functions
     * supported!!!!! NO Multiple table select supported!!!!!
     *
     * @param conn a JDBC connection may be null - will create one and close on
     * return
     * @param theQuery
     * @param userData
     * @return Single first result in set else null
     */
    @Override
    public EMCEntityClass exJDBCSingleReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException {
        super.checkCompanyId(theQuery, userData);
        EMCEntityClass toReturn = null;
        boolean mustCloseConnection = false;
        if (conn == null) {
            conn = connectToDatabase(userData);
            mustCloseConnection = true;
        }
        try {
            Statement st = conn.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(theQuery.getNativeQuery(true));
            EMCEntityClass tmpTable = new EMCEntityClass();
            //Create Entity Instance
            try {
                Class tmpCls = Class.forName(theQuery.getEntityClassName());
                Constructor c = tmpCls.getConstructor(new Class[]{});
                Object o = c.newInstance(new Object[]{});
                tmpTable = (EMCEntityClass) o;
            } catch (Exception e) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct entity:" + theQuery.getEntityClassName(), e);
                }
                e.printStackTrace();
            }
            List<String> qfields = theQuery.getFieldList();
            List<Field> fields = tmpTable.getAllTableFields();
            HashMap<String, Field> fieldsMap = new HashMap();
            if (qfields != null && qfields.size() > 0) {
                for (Field f : fields) {
                    fieldsMap.put(f.getName(), f);
                }
            }
            if (rs.next()) {
                EMCEntityClass curEntity = this.doClone(tmpTable, userData);
                if (qfields != null && qfields.size() > 0) {
                    for (String field : qfields) {
                        Object toSet = MapResult(rs, fieldsMap.get(field.substring(field.lastIndexOf(".") + 1, field.length())), userData);
                        curEntity.setValueForFieldInEntityObject(field.substring(field.lastIndexOf(".") + 1, field.length()), curEntity.getClass(), curEntity, toSet);
                    }

                } else {
                    for (Field f : fields) {
                        Object toSet = MapResult(rs, f, userData);
                        curEntity.setValueForFieldInEntityObject(f.getName(), curEntity.getClass(), curEntity, toSet);
                    }

                }
                toReturn = curEntity;
            }
            rs.close();
            st.close();
        } catch (Exception se) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC select threw a SQLException." + se.getMessage(), se);
            }
            se.printStackTrace();
        } finally {
            if (mustCloseConnection) {
                closeConnectionToDB(conn, userData);
            }
        }
        return toReturn;
    }

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
    @Override
    public List exJDBCReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException {
        super.checkCompanyId(theQuery, userData);
        List ret = new ArrayList();
        boolean mustCloseConnection = false;
        if (conn == null) {
            conn = connectToDatabase(userData);
            mustCloseConnection = true;
        }
        try {
            Statement st = conn.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(theQuery.getNativeQuery(true));
            EMCEntityClass tmpTable = new EMCEntityClass();
            //Create Entity Instance
            try {
                Class tmpCls = Class.forName(theQuery.getEntityClassName());
                Constructor c = tmpCls.getConstructor(new Class[]{});
                Object o = c.newInstance(new Object[]{});
                tmpTable = (EMCEntityClass) o;
            } catch (Exception e) {
                if (EMCDebug.getDebug()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct entity:" + theQuery.getEntityClassName(), e);
                }
                e.printStackTrace();
            }
            List<String> qfields = theQuery.getFieldList();
            List<Field> fields = tmpTable.getAllTableFields();
            HashMap<String, Field> fieldsMap = new HashMap();
            if (qfields != null && qfields.size() > 0) {
                for (Field f : fields) {
                    fieldsMap.put(f.getName(), f);
                }
            }
            while (rs.next()) {
                EMCEntityClass curEntity = this.doClone(tmpTable, userData);
                if (qfields != null && qfields.size() > 0) {
                    for (String field : qfields) {
                        Object toSet = MapResult(rs, fieldsMap.get(field.substring(field.lastIndexOf(".") + 1, field.length())), userData);
                        if (toSet != null) {
                            curEntity.setValueForFieldInEntityObject(field.substring(field.lastIndexOf(".") + 1, field.length()), curEntity.getClass(), curEntity, toSet);
                        }
                    }
                } else {
                    for (Field f : fields) {
                        Object toSet = MapResult(rs, f, userData);
                        curEntity.setValueForFieldInEntityObject(f.getName(), curEntity.getClass(), curEntity, toSet);
                    }
                }
                ret.add(curEntity);
            }
            rs.close();
            st.close();
        } catch (Exception se) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC select threw a SQLException." + se.getMessage(), se);
            }
            se.printStackTrace();
        } finally {
            if (mustCloseConnection) {
                closeConnectionToDB(conn, userData);
            }
        }
        return ret;
    }

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
    @Override
    public List<Object[]> exJDBCFieldReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException {
        super.checkCompanyId(theQuery, userData);
        return exJDBCFieldReadQuery(conn, theQuery.getNativeQuery(true), userData);
    }

    @Override
    public List<Object[]> exJDBCFieldReadQuery(Connection conn, String theQuery, EMCUserData userData) throws EMCEntityBeanException {
        List<Object[]> ret = new ArrayList<Object[]>();
        boolean mustCloseConnection = false;
        if (conn == null) {
            conn = connectToDatabase(userData);
            mustCloseConnection = true;
        }
        try {
            Statement st = conn.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(theQuery);
            ResultSetMetaData md = rs.getMetaData();
            Object[] selectedData;
            while (rs.next()) {
                selectedData = new Object[md.getColumnCount()];
                for (int col = 0; col
                                  < md.getColumnCount(); col++) {
                    selectedData[col] = rs.getObject(col + 1);
                }
                ret.add(selectedData);
            }
            rs.close();
            st.close();
        } catch (Exception se) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC select threw a SQLException." + se.getMessage(), se);
            }
            se.printStackTrace();
        } finally {
            if (mustCloseConnection) {
                closeConnectionToDB(conn, userData);
            }
        }
        return ret;
    }

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
    @Override
    public List exJDBCSingleFieldReadQuery(Connection conn, EMCQuery theQuery, EMCUserData userData) throws EMCEntityBeanException {
        super.checkCompanyId(theQuery, userData);
        return exJDBCSingleFieldReadQuery(conn, theQuery.getNativeQuery(true), userData);
    }

    @Override
    public List exJDBCSingleFieldReadQuery(Connection conn, String theQuery, EMCUserData userData) throws EMCEntityBeanException {
        List ret = new ArrayList();

        boolean mustCloseConnection = false;
        if (conn == null) {
            conn = connectToDatabase(userData);
            mustCloseConnection = true;
        }

        try {
            Statement st = conn.createStatement(ResultSet.FETCH_FORWARD, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery(theQuery);
            ResultSetMetaData md = rs.getMetaData();
            Object selectedData;

            while (rs.next()) {
                selectedData = rs.getObject(1);

                ret.add(selectedData);
            }

            rs.close();
            st.close();
        } catch (Exception se) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC select threw a SQLException." + se.getMessage(), se);
            }
            se.printStackTrace();
        } finally {
            if (mustCloseConnection) {
                closeConnectionToDB(conn, userData);
            }
        }

        return ret;
    }

    /**
     * Maps the return fields by invoking correct get statement based on Field
     * type
     *
     * @param rs
     * @param field
     * @param userData
     * @return
     * @throws SQLException
     */
    private Object MapResult(ResultSet rs, Field field, EMCUserData userData) throws SQLException {
        if (field == null) {
            return null;
        }

        if (field.getType().equals(String.class)) {
            return rs.getString(field.getName());
        }

        if (field.getType().equals(boolean.class) || field.getType().equals(Boolean.class)) {
            return rs.getBoolean(field.getName());
        }

        if (field.getType().equals(double.class) || field.getType().equals(Double.class)) {
            return rs.getDouble(field.getName());
        }

        if (field.getType().equals(Date.class)) {
            return rs.getDate(field.getName());
        }

        if (field.getType().equals(long.class) || field.getType().equals(Long.class)) {
            return rs.getLong(field.getName());
        }

        if (field.getType().equals(int.class) || field.getType().equals(Integer.class)) {
            return rs.getInt(field.getName());
        }

        if (field.getType().equals(BigDecimal.class)) {
            return rs.getBigDecimal(field.getName());
        }

        return null;
    }

    /**
     * Creates a JDBC connection YOU MUST CLOSE the conenction!! e.g.
     * closeConnectionToDB(); TODO read paramters from DB
     *
     * @param userData
     * @return
     */
    @Override
    public Connection connectToDatabase(EMCUserData userData) throws EMCEntityBeanException {
        Connection conn = null;
        long loopCount = 0;
        while (connCount >= MAXCONN) {
            if (loopCount == 40) {
                throw new EMCEntityBeanException("Failed to connect to DB: All available threads in use.");
            }
            loopCount++;
            try {
                Thread.sleep(250);
            } catch (InterruptedException ex) {
                throw new EMCEntityBeanException("Failed to connect to DB: " + ex.getMessage());
            }
        }
        try {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseDBConnections.class);
            query.addAnd("connectionId", DBConnection.EMC.toString());
            BaseDBConnections connInfo = (BaseDBConnections) util.executeSingleResultQuery(query, userData);
            if (connInfo == null) {
                Logger.getLogger("emc").log(Level.SEVERE, "Connection Info not set up.", userData);
                return null;
            }

            Class.forName(connInfo.getDriver());
            String url = connInfo.getConnectionType() + ":" + connInfo.getDatabaseVender() + "://" + connInfo.getServer() + ":"
                         + connInfo.getPort() + "/" + connInfo.getDatabaseName();
            conn = DriverManager.getConnection(url, connInfo.getUserName(), connInfo.getDbPassword());
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Direct JDBC connection." + e.getMessage(), userData);
            }
            e.printStackTrace();
            throw new EMCEntityBeanException("Failed to connect to DB: " + e.getMessage());
        }
        connCount++;
        return conn;
    }

    /**
     * Closes a given connection to the Database
     *
     * @param conn
     * @param userData
     */
    @Override
    public void closeConnectionToDB(Connection conn, EMCUserData userData) {
        try {
            conn.close();
            connCount--;
        } catch (Exception close) {
            if (EMCDebug.getDebug()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to close JDBC Connection." + close.getMessage(), close);
            }
            close.printStackTrace();
        }
    }

    /**
     * Returns a new BigDecimal instance containing the value of the specified
     * double instance, rounded to two decimals using a hard-coded precision of
     * 15 digits.
     *
     * @param d Double to convert to BigDecimal and round.
     * @return A BigDecimal instance representing the specified BigDecimal value
     * rounded to the specified number of decimals.
     */
    @Override
    public BigDecimal getBigDecimal(double d) {
        return getBigDecimal(d, 2);
    }

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
    @Override
    public BigDecimal getBigDecimal(double d, int decimals) {
        String s = String.valueOf(d);
        int i = s.indexOf(".");

        if (i == -1) {
            i = 0;
        }

        return new BigDecimal(d, new MathContext(i + 15)).setScale(decimals, RoundingMode.HALF_UP);
    }

    /**
     * Rounds the specified BigDecimal to the two decimals. This method calls
     * EMCServerUtility#roundBigDecimal(BigDecimal, int).
     *
     * @param toRound BigDecimal to round.
     * @return A rounded representation of the specified BigDecimal.
     */
    @Override
    public BigDecimal roundBigDecimal(BigDecimal toRound) {
        return roundBigDecimal(toRound, 2);
    }

    /**
     * Rounds the specified BigDecimal to the specified number of decimals. This
     * method used the BigDecimal#setScale(int, RoundingMode) method and rounds
     * up.
     *
     * @param toRound BigDecimal to round.
     * @param decimals Number of decimals to round to.
     * @return A rounded representation of the specified BigDecimal.
     */
    @Override
    public BigDecimal roundBigDecimal(BigDecimal toRound, int decimals) {
        BigDecimal bd = new BigDecimal(Double.toString(toRound.doubleValue()));
        bd = bd.setScale(decimals, BigDecimal.ROUND_HALF_UP);
        return bd;
//        int scale = toRound.scale();
//        while (scale > decimals) {
//            toRound = toRound.setScale(--scale, RoundingMode.HALF_UP);
//        }
//        return toRound;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean executeUpdateInNewTransaction(EMCQuery query, EMCUserData userData) {
        return executeUpdate(query, userData);
    }

    @Override
    @Deprecated
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object executeNativeSelectInNewTx(String query, EMCUserData userData) {
        return util.executeNativeQuery(query, userData);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public Object executeNativeSelectInNewTx(EMCQuery query, EMCUserData userData) {
        return util.executeNativeQuery(query, userData);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public boolean executeUpdateInNewTransaction(String query, EMCUserData userData) {
        return executeUpdate(query, userData);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int executeNativeUpdateInNewTransaction(EMCQuery query, EMCUserData userData) {
        return executeNativeUpdate(query, userData);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public int executeNativeUpdateInNewTransaction(String query, EMCUserData userData) {
        return executeNativeUpdate(query, userData);
    }

    /**
     * Checks if the object in pos of user data equals the value
     *
     * @param pos User Data Position To Check
     * @param value The value to compare
     * @param userData Plain Old User Data
     * @return True if the object in pos equals the value.
     */
    @Override
    public boolean checkUserData(int pos, Object value, EMCUserData userData) {
        List udList = userData.getUserData();
        if (udList != null) {
            if (udList.size() > pos) {
                Object udObj = udList.get(pos);

                return checkObjectsEqual(udObj, value);
            }
        }
        return false;
    }
}