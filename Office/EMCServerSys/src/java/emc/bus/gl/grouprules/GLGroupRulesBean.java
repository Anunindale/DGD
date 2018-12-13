package emc.bus.gl.grouprules;



import emc.entity.gl.rules.GLGroupRules;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/** 
*
* @author claudette
*/
@Stateless
public class GLGroupRulesBean extends EMCEntityBean implements GLGroupRulesLocal {

    /** Creates a new instance of GLGroupRulesBean. */
    public GLGroupRulesBean() {
    }

    /**
     * Returns the specified group rules.
     * @param groupRules Rule ID.
     * @param userData User data.
     * @return The specified group rules, or null if no group rules record
     * with the specified ID exists.
     */
    public GLGroupRules getGroupRules(String groupRules, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, GLGroupRules.class);
        query.addAnd("groupRules", groupRules);

        return (GLGroupRules)util.executeSingleResultQuery(query, userData);
    }
}