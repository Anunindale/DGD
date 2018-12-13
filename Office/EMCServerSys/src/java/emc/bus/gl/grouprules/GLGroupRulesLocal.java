package emc.bus.gl.grouprules;

import emc.entity.gl.rules.GLGroupRules;
import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

@Local
/** 
*
* @author claudette
*/
public interface GLGroupRulesLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the specified group rules.
     * @param groupRules Rule ID.
     * @param userData User data.
     * @return The specified group rules, or null if no group rules record
     * with the specified ID exists.
     */
    public GLGroupRules getGroupRules(String groupRules, EMCUserData userData);
}