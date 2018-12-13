/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.action;

import emc.bus.base.tables.TablesLocal;
import emc.commands.EMCCommands;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.messages.ServerBaseMessageEnum;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.tables.EMCCloneParameters;
import emc.tables.EMCTableRelation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author rico
 */
@Stateless
public class BaseCloneBean extends EMCBusinessBean implements BaseCloneLocal {

    @EJB
    private EMCCommandManagerLocal commandManager;
    @EJB
    private TablesLocal relations;

    public BaseCloneBean() {
    }

    /**
     * Clones a record and its siblings if setup correclty in clone parameter on the entity class
     * @param recordToClone 
     * @param primaryKey value of the new primary key applicable on tables that has no numberseq
     * @param userData 
     * @throws emc.framework.EMCEntityBeanException
     */
    public boolean cloneRecord(EMCEntityClass recordToClone, Object primaryKey, EMCUserData userData) throws EMCEntityBeanException {
        List toSend;
        Class recordToCloneClass;
        if (recordToClone.isDataSource()) {
            recordToCloneClass = recordToClone.getClass().getSuperclass();
            recordToClone = (EMCEntityClass) util.convertEntityToInstanceOfEntity(recordToClone, recordToCloneClass, userData);
        } else {
            recordToCloneClass = recordToClone.getClass();
        }

        EMCCloneParameters cloneParm = recordToClone.getCloneParameters();
        EMCEntityClass newRec = this.doClone(recordToClone, userData);
        String fieldKey = cloneParm.getPrimaryKey();
        if (primaryKey != null) {
            newRec.setValueForFieldInEntityObject(fieldKey, recordToCloneClass, newRec, primaryKey);
        } else {
            newRec.setValueForFieldInEntityObject(fieldKey, recordToCloneClass, newRec, null);
        }
        //clear fields if set up
        for (String field : cloneParm.getFieldsToClear()) {
            newRec.setValueForFieldInEntityObject(field, recordToCloneClass, newRec, null);
        }
        //resolve module
        enumEMCModules mastModule = Functions.getEMCModule(recordToCloneClass.getName());
        if (mastModule == null) {
            throw new EMCEntityBeanException("Could not resolve EMCmodule for:" + recordToCloneClass.getName() + " in clone routine");
        }
        //master ready post it
        String shortName = recordToCloneClass.getName();
        int index = shortName.lastIndexOf(".");
        shortName = shortName.substring(index + 1, shortName.length());
        EMCCommandClass mstInsertCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), mastModule.getId(), "INSERT_" + shortName);
        toSend = new ArrayList();
        toSend.add(mstInsertCmd);
        toSend.add(newRec);
        List resultList = commandManager.mapCommand(mstInsertCmd, toSend, userData);
        newRec = (EMCEntityClass) resultList.get(1);
        primaryKey = newRec.getValueForFieldInEntityObject(fieldKey, recordToCloneClass, newRec);
        //now check for any lines
        List<String> relatedToClone = cloneParm.getRelatedTableToClone();
        List<EMCTableRelation> tableRelations = relations.getRelations(recordToCloneClass.getName(), fieldKey, userData);
        if (relatedToClone != null && tableRelations != null) {
            for (String child : relatedToClone) {
                //find the relation class
                for (EMCTableRelation tableRel : tableRelations) {
                    if (tableRel.getChildClassPath().equals(child)) {
                        //found the update now do the lines
                        EMCQuery sQuery = new EMCQuery(enumQueryTypes.SELECT, tableRel.getChildClassPath());
                        Object valueToLookForS = recordToClone.getValueForFieldInEntityObject(fieldKey, recordToClone.getClass(), recordToClone);
                        if (valueToLookForS != null) {
                            sQuery.addAnd(tableRel.getChildFieldName(), valueToLookForS.toString());
                            List<EMCEntityClass> qRes = util.executeGeneralSelectQuery(sQuery, userData);
                            //OK now clone and insert the curEntity
                            //resolve module
                            enumEMCModules childModule = Functions.getEMCModule(tableRel.getChildClassPath());
                            shortName = tableRel.getChildClassPath();
                            index = shortName.lastIndexOf(".");
                            shortName = shortName.substring(index + 1, shortName.length());
                            EMCCommandClass childCmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), childModule.getId(), "INSERT_" + shortName);
                            for (EMCEntityClass curEntity : qRes) {
                                EMCEntityClass newChild = this.doClone(curEntity, userData);
                                newChild.setValueForFieldInEntityObject(tableRel.getChildFieldName(), newChild.getClass(), newChild, primaryKey);
                                toSend.removeAll(toSend);
                                toSend.add(childCmd);
                                toSend.add(newChild);
                                resultList = commandManager.mapCommand(childCmd, toSend, userData);
                            }
                        }
                    }
                }
            }
        }//if related to clone
        logMessage(Level.INFO, ServerBaseMessageEnum.UVALFAIL, userData, primaryKey);
        //Logger.getLogger("emc").log(Level.INFO,"Clone succeeded, created new record: "+primaryKey,userData);
        return true;
    }
}
