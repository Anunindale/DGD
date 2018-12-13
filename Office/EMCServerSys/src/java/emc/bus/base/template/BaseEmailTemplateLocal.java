/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.template;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface BaseEmailTemplateLocal extends EMCEntityBeanLocalInterface {

    public emc.entity.base.BaseEmailTemplates checkTemplate(java.lang.String templateId, emc.framework.EMCUserData userData);

    public emc.entity.base.BaseEmailTemplates fetchTemplate(java.lang.String templateId, emc.framework.EMCUserData userData);
}
