/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.action;

import emc.entity.workflow.WorkFlowActivity;
import emc.enums.enumQueryTypes;
import emc.framework.EMCBean;
import emc.framework.EMCCommandClass;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.ServerBaseMessageEnum;
import emc.server.commandmanager.EMCCommandManagerLocal;
import emc.tables.EMCTable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseImportBean extends EMCBean implements BaseImportLocal {

    @EJB
    private EMCCommandManagerLocal commandManagerBean;

    @Override
    public Object importRecord(List recordData, EMCUserData userData) throws EMCEntityBeanException {
        int insertMethod = (Integer) recordData.get(1);
        boolean validateField = (Boolean) recordData.get(2);
        Object record = recordData.get(3);
        EMCCommandClass cmd = (EMCCommandClass) recordData.get(5);
        Class recordsClass;
        try {
            recordsClass = Class.forName((String) recordData.get(4));
        } catch (ClassNotFoundException ex) {
            return null;
        }

        switch (insertMethod) {
            case 0:
                return normalInsert((EMCTable) record, cmd, validateField, userData);
            case 1:
                return directInsert(recordsClass, (List<EMCTable>) record, cmd, validateField, userData);
            default:
                return null;
        }
    }

    

    private boolean normalInsert(EMCTable record, EMCCommandClass cmd, boolean validateField, EMCUserData userData) throws EMCEntityBeanException {
        List theList = null;
        String cmdMethod;

        cmdMethod = cmd.getMethodId();

        boolean insert = true;

        if (validateField) {
            insert = validDateFields(record, cmd, userData);
        }

        if (insert) {
            cmd.setMethodId(cmdMethod);
            theList = new ArrayList();
            theList.add(cmd);
            theList.add(record);
            if (commandManagerBean.mapCommand(cmd, theList, userData).size() == 2) {
                return true;
            }
        }
        return false;
    }

    private List<Integer> directInsert(Class recordsClass, List<EMCTable> recordList, EMCCommandClass cmd, boolean validateField, EMCUserData userData) {
        List<EMCTable> toInsert;
        List<Integer> validationFailed;

        if (validateField) {
            toInsert = new ArrayList<EMCTable>();
            validationFailed = new ArrayList<Integer>();
            EMCTable record;

            for (int i = 0; i < recordList.size(); i++) {
                record = recordList.get(i);

                if (validDateFields(record, cmd, userData)) {
                    toInsert.add(record);
                } else {
                    validationFailed.add(i);
                }
            }
        } else {
            toInsert = recordList;
            validationFailed = new ArrayList<Integer>();
        }
        if(recordsClass.getName().equals(WorkFlowActivity.class.getName())){
            util.insertDirect("WFActivities", recordsClass, toInsert, userData);
        }else{
            util.insertDirect(recordsClass, toInsert, userData);
        }

        return validationFailed;
    }


    private boolean validDateFields(EMCTable record, EMCCommandClass cmd, EMCUserData userData) {
        String cmdMethod = cmd.getMethodId();
        cmd.setMethodId("VALIDATEFIELD" + cmdMethod.substring(cmdMethod.indexOf("_")).toUpperCase());

        List theList;
        Object vResult;

        Field[] fields = record.getTableFields();
        for (Field f : fields) {
            try {
                f.setAccessible(true);
                if (!this.isBlank(f.get(record))) {
                    theList = new ArrayList();
                    theList.add(cmd);
                    theList.add(f.getName());
                    theList.add(record);
                    vResult = commandManagerBean.mapCommand(cmd, theList, userData).get(1);
                    if (vResult instanceof Boolean) {
                        if (!(Boolean) vResult) {
                            return false;
                        }
                    } else if (vResult != null) {
                        record = (EMCTable) vResult;
                    } else {
                        return false;
                    }
                }
            } catch (Exception ex) {
                logMessage(Level.SEVERE, ServerBaseMessageEnum.IMPNOTACCFIELD, userData, record.getDisplayLabelForField(f.getName(), userData));
            }
        }
        return true;
    }
}


