/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.template;

import emc.entity.base.BaseEmailTemplates;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class BaseEmailTemplateBean extends EMCEntityBean implements BaseEmailTemplateLocal {

    @Override
    public BaseEmailTemplates checkTemplate(String templateId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class.getName());
        query.addAnd("templateId", templateId);
        BaseEmailTemplates template = (BaseEmailTemplates) util.executeSingleResultQuery(query, userData);
        if (template == null) {
            Logger.getLogger("emc").log(Level.SEVERE, "The selected template does not exist, please reselect.", userData);
            return null;
        }
        return template;
    }
    
    @Override
    public BaseEmailTemplates fetchTemplate(String templateId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmailTemplates.class.getName());
        query.addAnd("templateId", templateId);
        BaseEmailTemplates template = (BaseEmailTemplates) util.executeSingleResultQuery(query, userData);
        
        return template;
    }
}
