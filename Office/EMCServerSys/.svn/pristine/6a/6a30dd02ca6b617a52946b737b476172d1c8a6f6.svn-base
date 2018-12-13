package emc.bus.base.helpfiles;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import javax.ejb.Local;

@Local
/** 
*
* @author riaan
*/
public interface BaseHelpFileMappingsLocal extends EMCEntityBeanLocalInterface {

    /**
     * Returns the URL of a help file for the specified form.  If no help file
     * URL is found, this will return null.
     *
     * @param formClassName Form class name.
     * @param userData User data.
     * @return The URL of a help file for the specified form or null, if no help
     * file URL is found.
     */
    public String getHelpFileURL(String formClassName, EMCUserData userData);
}