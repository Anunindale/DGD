/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.genericreport;

import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import emc.server.commandmanager.EMCCommandManagerLocal;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseGenericReportBean extends EMCEntityBean implements BaseGenericReportLocal {

    @EJB
    private EMCCommandManagerLocal comandManager;

    @Override
    public List fetchReportData(String tableName, EMCCommandClass cmd, int from, int max, EMCUserData userData) throws EMCEntityBeanException {
        try {
            List commandList = new ArrayList();
            commandList.add(Class.forName(tableName));
            commandList.add(from);
            commandList.add(from + max);
            commandList = comandManager.mapCommand(cmd, commandList, userData);
            commandList.remove(0);
            return commandList;
        } catch (Exception ex) {
            throw new EMCEntityBeanException(ex);
        }
    }

    @Override
    public List fetchReportData(List<String> fieldName, String tableName, EMCCommandClass cmd, int from, int max, EMCUserData userData) throws EMCEntityBeanException {
        try {
            EMCEntityClass entityClass = (EMCEntityClass) Class.forName(tableName).newInstance();
            List<Field> entityFields = entityClass.getAllTableFields();

            List commandList = new ArrayList();
            commandList.add(entityClass);
            commandList.add(from);
            commandList.add(from + max);
            commandList = comandManager.mapCommand(cmd, commandList, userData);
            commandList.remove(0);

            List<Object[]> reducedEntityList = new ArrayList<Object[]>();
            Object[] reducedEntity;
            int fieldCount = 0;
            Map<String, Field> fieldMap = new HashMap<String, Field>();
            Field f;

            for (Object entity : commandList) {
                reducedEntity = new Object[fieldName.size()];
                fieldCount = 0;

                for (String field : fieldName) {
                    f = fieldMap.get(field);
                    if (f == null) {
                        for (Field ef : entityFields) {
                            if (field.equals(ef.getName())) {
                                f = ef;
                                break;
                            }
                        }
                        if (f == null) {
                            throw new IOException("Failed to find field " + field + " on entity.");
                        }
                        f.setAccessible(true);
                        fieldMap.put(field, f);
                    }

                    reducedEntity[fieldCount] = f.get(entity);
                    if (reducedEntity[fieldCount] == null) {
                        reducedEntity[fieldCount] = "";
                    }
                    fieldCount++;
                }

                reducedEntityList.add(reducedEntity);
            }
            return reducedEntityList;
        } catch (Exception ex) {
            throw new EMCEntityBeanException(ex);
        }
    }
}
