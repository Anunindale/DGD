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
public class ProjectLayer extends EMCString {

    public ProjectLayer() {
        this.setEmcLabel("Layer");
        this.setMandatory(true);
    }
}
