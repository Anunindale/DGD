/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.installation;

import emc.entity.developertools.installations.DevInstallationLines;
import emc.entity.developertools.installations.DevInstallationMaster;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevInstallationMasterBean extends EMCEntityBean implements DevInstallationMasterLocal {

    @EJB
    private DevInstallationLinesLocal linesBean;

    @Override
    public List<String> getInstallationNames(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevInstallationMaster.class);
        query.addField("installationName");
        List<String> installationNames = util.executeGeneralSelectQuery(query, userData);
        return installationNames;
    }

    @Override
    public List<Object[]> getInstallationProperties(String installationName, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevInstallationLines.class);
        query.addAnd("installationName", installationName);
        query.addField("propertyName");
        query.addField("propertyValue");
        List<Object[]> installationProperties = util.executeGeneralSelectQuery(query, userData);
        return installationProperties;
    }

    @Override
    public void saveInstallationProperties(String installationName, Map<String, String> propetiesToSave, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevInstallationLines.class);
        query.addAnd("installationName", installationName);
        List<DevInstallationLines> propertiesList = util.executeGeneralSelectQuery(query, userData);
        String propertyValue;
        for (DevInstallationLines property : propertiesList) {
            propertyValue = propetiesToSave.remove(property.getPropertyName());
            if (propertyValue != null) {
                property.setPropertyValue(propertyValue);
                linesBean.update(property, userData);
            }
        }
        DevInstallationLines property;
        for (Map.Entry<String, String> toSave : propetiesToSave.entrySet()) {
            property = new DevInstallationLines();
            property.setInstallationName(installationName);
            property.setPropertyName(toSave.getKey());
            property.setPropertyValue(toSave.getValue());
            linesBean.insert(property, userData);
        }
    }
}
