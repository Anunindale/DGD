/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.base.templates.foreignkeys;

import emc.datatypes.base.templates.EmailTemplateId;
import emc.entity.base.BaseEmailTemplates;

/**
 *
 * @author wikus
 */
public class EmailTemplateIdFK extends EmailTemplateId {

    public EmailTemplateIdFK() {
        this.setRelatedTable(BaseEmailTemplates.class.getName());
        this.setRelatedField("templateId");
    }
}
