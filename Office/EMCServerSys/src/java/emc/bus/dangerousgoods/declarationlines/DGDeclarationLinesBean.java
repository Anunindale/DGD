/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.declarationlines;

import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import javax.ejb.Stateless;

/**
 *
 * @author pj
 */

@Stateless
public class DGDeclarationLinesBean extends EMCEntityBean implements DGDeclarationLinesLocal {
    
    public DGDeclarationLinesBean()
    {
        
    }
    
    /**
     * 
     * @param numberSeq
     * @param userData
     * @return 
     */
    public String findDescriptionByNumber(String lineNumber, EMCUserData userData)
    {
       EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDeclarationLines.class);
       query.addAnd("lineNumber", lineNumber);
       
       DGDeclarationLines result = (DGDeclarationLines) util.executeSingleResultQuery(query, userData);
       
       if(result != null)
           return result.getDescription(); 
       else
           return "";
    }
    
}
