/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.projects.foreignkeys;

import emc.datatypes.developertools.projects.ProjectName;
import emc.entity.developertools.DevProjectMaster;

/**
 *
 * @author wikus
 */
public class ProjectNameFK extends ProjectName {

    public ProjectNameFK() {
        this.setRelatedTable(DevProjectMaster.class.getName());
        this.setRelatedField("projectName");
    }
}
