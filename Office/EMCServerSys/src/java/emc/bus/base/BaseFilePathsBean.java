/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor. 
 */
package emc.bus.base;

import emc.entity.base.BaseFilePaths;
import emc.enums.base.filepaths.OperatingSystems;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 * 
 * @author riaan
 */
@Stateless
public class BaseFilePathsBean extends EMCEntityBean implements BaseFilePathsLocal {

    /** Creates a new instance of BaseFilePathsBean */
    public BaseFilePathsBean() {
    }

    public BaseFilePaths getModuleFilePath(String moduleId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addAnd("formModule", moduleId);
        return (BaseFilePaths) util.executeSingleResultQuery(query, userData);
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {

        BaseFilePaths bfp = (BaseFilePaths) theRecord;

        if (fieldNameToValidate.equals("operatingSystem")) {
            if (bfp.getOperatingSystem() == null || bfp.getOperatingSystem().equals("")) {
                Logger.getLogger("emc").log(Level.SEVERE, "Operating System is a required field", userData);
                return false;
            }
        } else if (fieldNameToValidate.equals("filePath")) {
            if (bfp.getFilePath() == null || bfp.getFilePath().equals("")) {
                Logger.getLogger("emc").log(Level.SEVERE, "File Path is a required field", userData);
                return false;
            }
        }
        return true;
    }

    /**
     * Fetches and returns the system file path (which should point to a file server) for the OS specified in userData.
     * @param operatingSystemStr Java String identifier for an operating system.
     * @param userData User data.  This specifies the OS of the client application which sent the request.
     * @return A system file path.
     */
    public String getOSFilePath(String operatingSystemStr, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseFilePaths.class);
        query.addField("filePath");

        OperatingSystems operatingSystem = OperatingSystems.fromString(operatingSystemStr);

        query.addAnd("operatingSystem", operatingSystem);

        return (String) util.executeSingleResultQuery(query, userData);
    }
}
