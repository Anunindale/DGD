/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.messages;

import emc.entity.base.messages.BaseMessages;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.messages.EMCMessageEnum;
import emc.messages.ServerBaseMessageEnum;
import emc.messages.ServerDebtorsMessageEnum;
import emc.messages.ServerGLMessageEnum;
import emc.messages.ServerHRMessageEnum;
import emc.messages.ServerInventoryMessageEnum;
import emc.messages.ServerMRPMessageEnum;
import emc.messages.ServerPMMMessageEnum;
import emc.messages.ServerProductionMessageEnum;
import emc.messages.ServerSOPMessageEnum;
import emc.messages.ServerUMMessageEnum;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 * @name        BaseMessagesBean
 *
 * @date        20 Apr 2009
 *
 * @desciption  Entity bean for BaseMessages entity.
 *
 * @author      riaan
 */
@Stateless
public class BaseMessagesBean extends EMCEntityBean implements BaseMessagesLocal {

    /** Creates a new instance of BaseMessagesBean. */
    public BaseMessagesBean() {
    }

    /**
     * Populates BaseMessage records in SQL with server messages from the various server message enums.  This method will not overwrite existing records.
     * @param moduleString Module for which messages should be populated.  May not be blank.
     * @param userData User data.
     * @return A boolean indicating success.
     */
    public boolean populateServerMessages(String moduleString, EMCUserData userData) throws EMCEntityBeanException {
        if (isBlank(moduleString)) {
            throw new EMCEntityBeanException("No module specified!");
        }

        enumEMCModules module = enumEMCModules.fromString(moduleString);

        if (module == null) {
            throw new EMCEntityBeanException("Module not found: " + moduleString);
        }

        EMCMessageEnum[] messages = null;

        switch (module) {
            case BASE:
                messages = ServerBaseMessageEnum.values();
                break;
            case DEVELOPERTOOLS:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case COSTING:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case CP:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case CREDITORS:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case CRM:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case DEBTORS:
                messages = ServerDebtorsMessageEnum.values();
                break;
            case DEMANDMANAGEMENT:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case ENGINEERING:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case FA:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case FAM:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case GENERAL_LEDGER:
                messages = ServerGLMessageEnum.values();
                break;
            case HR:
                messages = ServerHRMessageEnum.values();
                break;
            case INVENTORY:
                messages = ServerInventoryMessageEnum.values();
                break;
            case MASTERPRODSCH:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case MATERIALPLAN:
                messages = ServerMRPMessageEnum.values();
                break;
            case PMM:
                messages = ServerPMMMessageEnum.values();
                break;
            case POP:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case PRODUCTION:
                messages = ServerProductionMessageEnum.values();
                break;
            case PROGRESS:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case QC:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case RE:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case SOP:
                messages = ServerSOPMessageEnum.values();
                break;
            case TREC:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case UM:
                messages = ServerUMMessageEnum.values();
                break;
            case WORKFLOW:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            case WORKSTUDY:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
                break;
            default:
                Logger.getLogger("emc").log(Level.WARNING, "Module not supported yet: " + moduleString, userData);
        }

        EMCQuery existsQuery = new EMCQuery(enumQueryTypes.SELECT, BaseMessages.class.getName());
        existsQuery.addAnd("messageModule", moduleString);

        for (EMCMessageEnum message : messages) {
            String messageId = message.toString();

            //Check if message exists
            existsQuery.removeAnd("messageId");
            existsQuery.addAnd("messageId", messageId);

            if (!util.exists(existsQuery, userData)) {
                //Create new BaseMessage record
                BaseMessages baseMessage = new BaseMessages();
                baseMessage.setMessageId(messageId);
                baseMessage.setMessageModule(moduleString);
                baseMessage.setMessage(message.getDefaultMessage());
                baseMessage.setDescription(message.getDefaultDescription());

                insert(baseMessage, userData);
            }
        }

        return true;
    }
}
