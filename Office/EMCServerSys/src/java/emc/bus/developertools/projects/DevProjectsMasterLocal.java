/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.projects;

import emc.framework.EMCEntityBeanLocalInterface;
import javax.ejb.Local;

/**
 *
 * @author wikus
 */
@Local
public interface DevProjectsMasterLocal extends EMCEntityBeanLocalInterface {

    public boolean createProject(java.lang.String emcProjectName, java.lang.String customer, java.lang.String project, java.lang.String projectDescription, java.util.List<java.lang.String> classPaths, emc.framework.EMCUserData userData) throws emc.framework.EMCEntityBeanException;

    public java.util.List<java.lang.String> getEMCProjectForAdminTool(emc.framework.EMCUserData userData);

    public java.util.List<java.lang.Object[]> getEMCProjectLinesForAdminTool(java.lang.String projectID, emc.framework.EMCUserData userData);

    public java.lang.String getEMCProjectDescriptionForAdminTool(java.lang.String projectID, emc.framework.EMCUserData userData);
}
