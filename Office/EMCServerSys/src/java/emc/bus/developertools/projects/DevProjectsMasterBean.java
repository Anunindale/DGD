/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.developertools.projects;

import emc.entity.developertools.DevProjectLines;
import emc.entity.developertools.DevProjectMaster;
import emc.enums.base.emcprojects.EMCProjectsEnum;
import emc.enums.developertools.DevLayerType;
import emc.enums.developertools.DevProjectType;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class DevProjectsMasterBean extends EMCEntityBean implements DevProjectsMasterLocal {

    @EJB
    private DevProjectsLinesLocal linesBean;

    @Override
    public boolean createProject(String emcProjectName, String customer, String project, String projectDescription, List<String> classPaths, EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevProjectMaster.class);
        query.addAnd("customerId", customer);
        query.addAnd("projectName", project);
        DevProjectMaster master = (DevProjectMaster) util.executeSingleResultQuery(query, userData);
        if (master == null) {
            master = new DevProjectMaster();
            master.setCustomerId(customer);
            master.setProjectName(project);
            master.setProjectDescription(projectDescription);
            this.insert(master, userData);
        }
        DevProjectType type;
        DevLayerType layer;

        if (emcProjectName.equals(EMCProjectsEnum.EMC_CLIENT_CORE.toString())) {
            type = DevProjectType.CLIENT;
            layer = DevLayerType.CORE;
        } else if (emcProjectName.equals(EMCProjectsEnum.EMC_CLIENT_SYS.toString())) {
            type = DevProjectType.CLIENT;
            layer = DevLayerType.SYSTEM;
        } else if (emcProjectName.equals(EMCProjectsEnum.EMC_GEN_LIB_CORE.toString())) {
            type = DevProjectType.GENLIB;
            layer = DevLayerType.CORE;
        } else if (emcProjectName.equals(EMCProjectsEnum.EMC_GEN_LIB_SYS.toString())) {
            type = DevProjectType.GENLIB;
            layer = DevLayerType.SYSTEM;
        } else if (emcProjectName.equals(EMCProjectsEnum.EMC_SERVER_CORE.toString())) {
            type = DevProjectType.SERVER;
            layer = DevLayerType.CORE;
        } else if (emcProjectName.equals(EMCProjectsEnum.EMC_SERVER_SYS.toString())) {
            type = DevProjectType.SERVER;
            layer = DevLayerType.SYSTEM;
        } else {
            type = DevProjectType.OTHER;
            layer = DevLayerType.OTHER;
        }

        DevProjectLines line;
        for (String path : classPaths) {
            line = new DevProjectLines();
            line.setMasterRecordID(master.getRecordID());
            line.setClasspath(path);
            line.setProjectType(type.toString());
            line.setProjectLayer(layer.toString());
            linesBean.insert(line, userData);
        }

        return true;
    }

    @Override
    public List<String> getEMCProjectForAdminTool(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevProjectMaster.class);
        query.addField("customerId");
        query.addField("projectName");
        List<Object[]> emcProjects = util.executeGeneralSelectQuery(query, userData);
        List<String> adminProjects = new ArrayList<String>();
        for (Object[] oa : emcProjects) {
            adminProjects.add(oa[0] + " - " + oa[1]);
        }
        return adminProjects;
    }

    @Override
    public String getEMCProjectDescriptionForAdminTool(String projectID, EMCUserData userData) {
        String[] projectSplit = projectID.split(" - ");
        String customer = projectSplit[0];
        String project = projectSplit[1];

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevProjectMaster.class);
        query.addAnd("customerId", customer);
        query.addAnd("projectName", project);
        query.addField("projectDescription");
        return (String) util.executeSingleResultQuery(project, userData);
    }

    @Override
    public List<Object[]> getEMCProjectLinesForAdminTool(String projectID, EMCUserData userData) {
        String[] projectSplit = projectID.split(" - ");
        String customer = projectSplit[0];
        String project = projectSplit[1];

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DevProjectLines.class);
        query.addTableAnd(DevProjectMaster.class.getName(), "masterRecordID", DevProjectLines.class.getName(), "recordID");
        query.addAnd("customerId", customer, DevProjectMaster.class.getName());
        query.addAnd("projectName", project, DevProjectMaster.class.getName());
        query.addField("projectType", DevProjectLines.class.getName());
        query.addField("projectLayer", DevProjectLines.class.getName());
        query.addField("classpath", DevProjectLines.class.getName());
        List<Object[]> projectLines = util.executeGeneralSelectQuery(query, userData);

        return projectLines;
    }
}
