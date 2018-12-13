/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.tables;

import emc.datatypes.EMCDataType;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import emc.tables.EMCTableRelation;
import java.lang.reflect.Constructor;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riaan
 */
@Stateless
public class TablesBean extends EMCBusinessBean implements TablesLocal {

    @PersistenceContext
    private EntityManager em;
    private HashMap<String, HashMap> entityList;
    private Map<Object, String> emcTables;
    @EJB
    private TablesLocal thisBean;

    /** Creates a new instance of TablesBean */
    public TablesBean() {
    }

    /**
     * If table has no relations it is not added to the Hashmap
     * @param theTable
     * @param userData
     */
    private void addTable(EMCTable theTable, EMCUserData userData) {
        //loop through the dataTypes
        EMCTable tmpTable;
        HashMap dtMap = theTable.getFieldDataTypeMapper();
        Set<String> dtFields = dtMap.keySet();
        for (String key : dtFields) {
            EMCDataType dt = (EMCDataType) dtMap.get(key);
            if (dt != null && dt.getRelatedTable() != null && dt.getRelatedField() != null) {
                try {
                    //test to see if relation is solid
                    Class tmpCls = Class.forName(dt.getRelatedTable());
                    Constructor c = tmpCls.getConstructor(new Class[]{});
                    Object o = c.newInstance(new Object[]{});
                    tmpTable = (EMCTable) o;
                    //also see if field exists
                    if (tmpTable.isFieldOnTable(dt.getRelatedField())) {
                        //ok now we can add the relation
                        HashMap fkMap = entityList.get(dt.getRelatedTable());
                        if (fkMap == null) {
                            fkMap = new HashMap();
                            List relatedChildren = new ArrayList();
                            relatedChildren.add(new EMCTableRelation(theTable.getClass().getName(), key, dt.getDeleteAction(),
                                    dt.getUpdateAction(), theTable.getEmcLabel(), dt.getCallBeanOptions(), dt.getUpdateBeanCommand(),
                                    dt.getDeleteBeanCommand()));
                            fkMap.put(dt.getRelatedField(), relatedChildren);
                            entityList.put(dt.getRelatedTable(), fkMap);
                        } else {
                            List relatedChildren = (List) fkMap.get(dt.getRelatedField());
                            if (relatedChildren == null) {
                                relatedChildren = new ArrayList();
                                relatedChildren.add(new EMCTableRelation(theTable.getClass().getName(), key, dt.getDeleteAction(),
                                        dt.getUpdateAction(), theTable.getEmcLabel(), dt.getCallBeanOptions(), dt.getUpdateBeanCommand(),
                                        dt.getDeleteBeanCommand()));
                                fkMap.put(dt.getRelatedField(), relatedChildren);
                            } else {
                                relatedChildren.add(new EMCTableRelation(theTable.getClass().getName(), key, dt.getDeleteAction(),
                                        dt.getUpdateAction(), theTable.getEmcLabel(), dt.getCallBeanOptions(), dt.getUpdateBeanCommand(),
                                        dt.getDeleteBeanCommand()));
                            }
                        }
                    } else {
                        Logger.getLogger("emc").log(Level.WARNING, "Related field is invalid: " + dt.getClass().getName() + " " + dt.getRelatedField(), userData);
                    }
                } catch (Exception e) {
                    Logger.getLogger("emc").log(Level.WARNING, "Related Table is invalid: " + dt.getClass().getName() + " " + dt.getRelatedTable(), userData);
                }
            } else if (dt != null && dt.getRelatedField() == null && dt.getRelatedTable() != null) {
                Logger.getLogger("emc").log(Level.WARNING, "Related Table set, no related field: " + dt.getClass().getName(), userData);
            } else if (dt != null && dt.getRelatedField() != null && dt.getRelatedTable() == null) {
                Logger.getLogger("emc").log(Level.WARNING, "Related field set, no related table: " + dt.getClass().getName(), userData);
            }
        }
    }

    /**
     * 
     * @param userData
     */
    private void generateRelations(EMCUserData userData) {
        Set<Class<?>> classSet = null;

        entityList = new HashMap();

        emcTables = new TreeMap();

        EMCTable theTable;
        try {
            classSet = emc.functions.classreader.GetClassesForPackage.getClasses("emc.entity.");
        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct Relations", userData);
            ex.printStackTrace();
        }
        if (classSet != null) {
            for (Class pClass : classSet) {
                theTable = null;
                try {
                    Constructor c = pClass.getConstructor(new Class[]{});
                    Object o = c.newInstance(new Object[]{});
                    theTable = (EMCTable) o;

                    //Added to get emc labels as well as classnames
                    emcTables.put(pClass.getName(), theTable.getEmcLabel());
                } catch (Exception e) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct Table to build Relations: " + pClass.getName() + " " + e.getMessage(), userData);
                }
                if (theTable != null && !theTable.isDataSource()) {
                    addTable(theTable, userData);
                }
            }
        }
    }

    /**
     * Use this method to get Relations for a particular entity class and field
     * 
     * @param entityClassName
     * @param entityClassField
     * @return List containing EMCTableRelation objects that describe related tables
     */
    public List getRelations(String entityClassName, String entityClassField, EMCUserData userData) {
        if (entityList == null) {
            generateRelations(userData);
        }
        HashMap map = entityList.get(entityClassName);
        return map == null ? null : (List) map.get(entityClassField);
    }

    /**
     * Use this method to get Relations for a particular entity class all the fields 
     * will be returned in a HashMap
     * @param entityClassName
     * @return Hashmap containing fields as keys and Lists of type EMCTableRelation
     */
    public HashMap getRelations(String entityClassName, EMCUserData userData) {
        if (entityList == null) {
            generateRelations(userData);
        }
        return (HashMap) (entityList.get(entityClassName));
    }

    public void testRelations(EMCUserData userData) {
        EMCQuery query;
        generateRelations(userData);
        Set<String> tableKeys = entityList.keySet();
        for (String table : tableKeys) {
            query = new EMCQuery(enumQueryTypes.SELECT, table);
            try {
                em.createQuery(query.toString()).setMaxResults(1).getResultList();

            } catch (Exception ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Table issue:" + table + " " + ex.getMessage(), userData);
            }
            //also do the children
            HashMap<String, List<EMCTableRelation>> fk = entityList.get(table);
            if (fk != null) {
                //loop through fields on parent
                Set<String> parentKeys = fk.keySet();
                for (String parentFieldKey : parentKeys) {
                    List<EMCTableRelation> children = fk.get(parentFieldKey);
                    if (children != null) {
                        //loop through children
                        for (EMCTableRelation relation : children) {
                            query = new EMCQuery(enumQueryTypes.SELECT, relation.getChildClassPath());
                            try {
                                em.createQuery(query.toString()).setMaxResults(1).getResultList();

                            } catch (Exception ex) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Table issue:" + relation.getChildClassPath() + " " + ex.getMessage(), userData);
                            }
                        }
                    }
                }
            }
        }
        Logger.getLogger("emc").log(Level.INFO, "Relation Test complete.", userData);
    }

    /**
     * This method is used to retrieve a list of all the tables in EMC
     */
    public List<Object> getEMCTables(EMCUserData userData) {
        if (emcTables == null) {
            generateRelations(userData);
        }
        List tmp = new ArrayList();
        tmp.addAll(emcTables.keySet());
        return tmp;
    }

    /** This method is used to retrieve a Map containing all the tables in EMC and their corresponding labels. */
    public Map<Object, String> getEMCTablesAndLabels(EMCUserData userData) {
        if (emcTables == null) {
            generateRelations(userData);
        }

        Map tmp = new HashMap();
        tmp.putAll(emcTables);

        return tmp;
    }

    /** This method is used to retrieve a Map containing all the tables for a given module in EMC and their corresponding labels. */
    public Map<Object, String> getEMCTablesAndLabels(String module, EMCUserData userData) {
        if (emcTables == null) {
            generateRelations(userData);
        }

        Map tmp = new HashMap();
        Iterator e = emcTables.keySet().iterator();
        while (e.hasNext()) {
            String key = e.next().toString();
            if (module.equals(Functions.getEMCModule(key).toString())) {
                tmp.put(key, emcTables.get(key));
            }
        }

        return tmp;
    }

    /**
     * Returns all number sequence tables.
     * @param userData User data.
     * @return A Map containing all tables supporting number sequences.
     */
    public Map<Object, String> getNumberSequenceTables(EMCUserData userData) {
        Map<Object, String> tables = getEMCTablesAndLabels(userData);

        Map<Object, String> temp = new TreeMap<Object, String>();

        table:
        for (Object key : tables.keySet()) {
            try {
                Class<? extends EMCEntityClass> entityClass = (Class<? extends EMCEntityClass>) Class.forName(key.toString());

                EMCEntityClass entityClassInstance = entityClass.newInstance();

                //Do not allow for number sequences on data sources.
                if (entityClassInstance.isDataSource()) {
                    continue;
                }

                //Check all entity class fields
                for (EMCDataType dt : entityClassInstance.getFieldDataTypeMapper().values()) {
                    if (dt.isNumberSeqAllowed()) {
                        temp.put(key, tables.get(key));
                        //Continue outer loop.
                        continue table;
                    }
                }
            } catch (Exception ex) {
                //Do nothing.  Continue loop.
            }
        }

        return temp;
    }

    /**
     * Returns all number sequence tables in the specified module.
     * @param module Module to filter tables on.
     * @param userData User data
     * @return A Map containing all tables supporting number sequences in the specified module.
     */
    public Map<Object, String> getNumberSequenceTables(String module, EMCUserData userData) {
        Map<Object, String> tables = getEMCTablesAndLabels(module, userData);
        Map<Object, String> temp = new TreeMap<Object, String>();

        table:
        for (Object key : tables.keySet()) {
            try {
                Class<? extends EMCEntityClass> entityClass = (Class<? extends EMCEntityClass>) Class.forName(key.toString());

                EMCEntityClass entityClassInstance = entityClass.newInstance();

                //Do not allow for number sequences on data sources.
                if (entityClassInstance.isDataSource()) {
                    continue;
                }

                //Check all entity class fields
                for (EMCDataType dt : entityClassInstance.getFieldDataTypeMapper().values()) {
                    if (dt.isNumberSeqAllowed()) {
                        temp.put(key, tables.get(key));
                        //Continue outer loop.
                        continue table;
                    }
                }
            } catch (Exception ex) {
                //Do nothing.  Continue loop.
            }
        }

        return temp;
    }

    @Override
    public List findAllTablesWithValue(String value, boolean update, String newValue, EMCUserData userData) {
        List<String> tables = util.executeNativeQuery("SELECT TABLE_NAME FROM information_schema.TABLES WHERE TABLE_SCHEMA = 'emc'", userData);
        List<String> rettables = new ArrayList<String>();
        for (String t : tables) {
            List<Object[]> columns = util.executeNativeQuery("SELECT column_name, data_type FROM information_schema.COLUMNS where table_name = '" + t + "' and NOT column_name = 'companyId'", userData);
            String q = "";
            String firstField = null;

            StringBuilder select = new StringBuilder();

            List<String> columnList = new ArrayList<String>();
            boolean addOr = false;
            for (Object[] columnInfo : columns) {
                if ("varchar".equals(columnInfo[1])) {
                    if (addOr) {
                        q += " OR ";
                    } else {
                        firstField = (String) columnInfo[0];
                        select.append("SELECT COUNT(*) ");
                    }

                    if (update) {
                        //Only add column if necessary.
                        columnList.add((String) columnInfo[0]);
                    }

                    q += "t." + columnInfo[0] + " = '" + value + "'";
                    addOr = true;
                }
            }

            List results = util.executeNativeQuery(select + " FROM " + t + " t WHERE " + q, userData);

            if (results.size() > 0 && ((BigInteger) results.get(0)).compareTo(BigInteger.ZERO) > 0) {
                System.out.println(t);
                rettables.add(t);

                if (update) {
                    //Update all columns on table.
                    for (String column : columnList) {
                        //Execute query per field
                        EMCQuery updateQuery = new EMCQuery(enumQueryTypes.UPDATE, t);
                        updateQuery.addAnd(column, value);
                        updateQuery.addSet(column, newValue);

                        thisBean.executeUpdate(updateQuery, userData);

                        System.out.println("Update query: " + updateQuery.getNativeQuery());
                    }
                }
            }
        }
        return rettables;
    }

    /** Executes an update in a new transaction
     *
     * @param query Update query to execute.
     * @param userData User data.
     */
    @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void executeUpdate(EMCQuery query, EMCUserData userData) {
        util.executeNativeUpdate(query, userData);
    }
}
