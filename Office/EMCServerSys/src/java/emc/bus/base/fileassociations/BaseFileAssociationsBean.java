/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.base.fileassociations;

import emc.entity.base.fileassociations.BaseFileAssociations;
import emc.entity.base.fileassociations.BaseUserFileAssociations;
import emc.enums.base.filepaths.OperatingSystems;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class BaseFileAssociationsBean extends EMCEntityBean implements BaseFileAssociationsLocal {

    /** Creates a new instance of BaseFileAssociationsBean. */
    public BaseFileAssociationsBean() {
        
    }

    /**
     * Gets file associations for the specified user.
     * @param userId User id for which associations should be retrieved.
     * @param operatingSystem Operating system for which associations should be retrieved.
     * @param userData User data.
     * @return A map containing file extensions as keys and the commands used to open these extensions as values.
     */
    public Map<String, String> getFileAssociations(String userId, String operatingSystem, EMCUserData userData) {
        Map<String, String> associationsMap = new HashMap<String, String>();

        operatingSystem = String.valueOf(OperatingSystems.getOS(operatingSystem));

        //Select default file associations.
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFileAssociations.class);
        query.addField("fileExtension");
        query.addField("openWith");
        query.addAnd("operatingSystem", operatingSystem);

        List<Object[]> fileAssociations = (List<Object[]>)util.executeGeneralSelectQuery(query, userData);

        for (Object[] association : fileAssociations) {
                associationsMap.put((String)association[0], (String)association[1]);
        }

        //Now override with user-specific associations
        query = new EMCQuery(enumQueryTypes.SELECT, BaseUserFileAssociations.class);
        query.addField("fileExtension");
        query.addField("openWith");
        query.addAnd("operatingSystem", operatingSystem);
        query.addAnd("userId", userId);

        fileAssociations = (List<Object[]>)util.executeGeneralSelectQuery(query, userData);

        for (Object[] association : fileAssociations) {
                associationsMap.put((String)association[0], (String)association[1]);
        }
        
        return associationsMap;
    }
}
