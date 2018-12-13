/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.projects;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class ProjectName extends EMCString{

    public ProjectName() {
        this.setEmcLabel("Project Name");
        this.setMandatory(true);
        this.setStringSize(1000);
    }

}
