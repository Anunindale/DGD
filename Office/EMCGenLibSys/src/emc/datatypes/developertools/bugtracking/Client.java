/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.developertools.bugtracking;

import emc.datatypes.EMCString;

/**
 *
 * @author wikus
 */
public class Client extends EMCString{

    public Client() {
        this.setEmcLabel("Client");
    	this.setColumnWidth(40);
        this.setMandatory(true);
    }

}
