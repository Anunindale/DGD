/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.hr;

import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.util.ArrayList;

/**
 *
 * @author wikus
 */
public class HREmployeeAccessManager {

    public static EMCQuery getEmployeeAccessQuery(EMCUserData userData) {
        EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.BASE.getId(), ServerBaseMethods.CHECK_ACCESS_GROUP.toString());
        EMCQuery query = (EMCQuery) EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData).get(1);
        return query;
    }
}
