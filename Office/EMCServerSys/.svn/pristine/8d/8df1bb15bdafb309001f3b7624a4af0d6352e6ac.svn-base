/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.systemtables;

import java.util.HashMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author rico
 */
@Stateless
public class SystemTableLogicBean implements SystemTableLogicLocal {

    @PersistenceContext
    private EntityManager em;
    private static HashMap<String, Boolean> systemTables;

    public SystemTableLogicBean() {

    }

    public boolean isTableSystemTable(String className) {
        if (systemTables == null) {
            systemTables = new HashMap<String, Boolean>();
            constructMap(systemTables);
        }
        if (systemTables.get(className) != null) {
            return true;
        }
        return false;

    /*  for(int j = 0; j < systemTables.size(); j++){
    if(systemTables.get(j).equals(className)){
    return true;
    }
    }
    return false;
     * 
     */
    }

    public boolean refresh() {
        HashMap newMap = new HashMap<String, Boolean>();
        constructMap(newMap);
        systemTables = newMap;
        return true;
    }

    private void constructMap(HashMap<String, Boolean> map) {
        String queryStr = "SELECT c.tableName FROM BaseSystemTable c";

        Query q = em.createQuery(queryStr);
        for (Object x : q.getResultList()) {
            map.put(x.toString(), true);
        }
    }
}
