/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.deploymentupdatelog;

import emc.entity.developertools.DevDeploymentUpdateLog;
import emc.entity.developertools.DevDeploymentUpdateLogLines;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevDeploymentUpdateLogBean extends EMCEntityBean implements DevDeploymentUpdateLogLocal {

    @EJB
    private DevDeploymentUpdateLogLinesLocal linesBean;

    @Override
    public void logUpdate(String installationName, int svnHead, List<Object[]> projects, EMCUserData userData) throws EMCEntityBeanException {
        DevDeploymentUpdateLog log = new DevDeploymentUpdateLog();
        log.setInstallationName(installationName);
        log.setSubversionHeader(svnHead);
        log.setDateUpdated(Functions.nowDate());
        this.insert(log, userData);

        DevDeploymentUpdateLogLines line;
        for (Object[] poa : projects) {
            line = new DevDeploymentUpdateLogLines();
            line.setMasterRecordID(log.getRecordID());
            line.setProject(poa[0].toString());
            line.setProjectDescription(poa[1].toString());
            linesBean.insert(line, userData);
        }
    }
}
