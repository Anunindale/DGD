package emc.bus.base.helpfiles;



import emc.entity.base.helpfiles.BaseHelpFileMappings;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/** 
*
* @author riaan
*/
@Stateless
public class BaseHelpFileMappingsBean extends EMCEntityBean implements BaseHelpFileMappingsLocal {

    /** Creates a new instance of BaseHelpFileMappingsBean. */
    public BaseHelpFileMappingsBean() {
    }

    /**
     * Returns the URL of a help file for the specified form.  If no help file
     * URL is found, this will return null.
     *
     * @param formClassName Form class name.
     * @param userData User data.
     * @return The URL of a help file for the specified form or null, if no help
     * file URL is found.
     */
    public String getHelpFileURL(String formClassName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseHelpFileMappings.class);
        query.addField("helpFileURL");
        query.addAnd("formClassName", formClassName);

        return (String)util.executeSingleResultQuery(query, userData);
    }
}