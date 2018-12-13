/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.server.utility.support;

import emc.framework.EMCUserData;
import emc.helpers.um.PhonenumberHelper;
import javax.ejb.Local;

/**
 * This bean offers various utility method used by thing such as validations throughout EMC.
 *
 * @author riaan
 */
@Local
public interface SupportLogicLocal {

    /**
     * Checks that the given parameter matches the format of a standard South African ID number.
     * @param idNumber Id number to check.
     * @param userData User data.
     * @return A boolean indicating whether the given parameter is a valid South African ID number.
     */
    public boolean validateSouthAfricanIDNumber(java.lang.String idNumber, emc.framework.EMCUserData userData);
    
    public PhonenumberHelper validateTelephone(String number, EMCUserData userData);

}
