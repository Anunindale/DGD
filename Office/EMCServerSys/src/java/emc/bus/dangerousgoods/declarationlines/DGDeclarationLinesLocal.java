/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.declarationlines;

import emc.framework.EMCEntityBeanLocalInterface;
import emc.framework.EMCUserData;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author pj
 */

@Local
public interface DGDeclarationLinesLocal extends EMCEntityBeanLocalInterface {
    
    public String findDescriptionByNumber(String lineNumber, EMCUserData userData);
    
    public List<Object> getRegistrationNumbers(String contactNum, EMCUserData userData);
    
}
