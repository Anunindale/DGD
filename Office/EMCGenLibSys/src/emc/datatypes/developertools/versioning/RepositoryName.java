/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.datatypes.developertools.versioning;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class RepositoryName extends EMCString {

    public RepositoryName() {
        this.setEmcLabel("Repository Name");
        this.setMandatory(true);
    }
}
