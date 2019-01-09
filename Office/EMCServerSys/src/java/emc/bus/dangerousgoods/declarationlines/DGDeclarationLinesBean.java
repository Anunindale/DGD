/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.dangerousgoods.declarationlines;

import emc.entity.dangerousgoods.DGDVehicles;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
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

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) 
    {
        boolean result =  (boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        if(result)
        {
            if(fieldNameToValidate.equals("operator"))
            {
                DGDeclarationLines record = (DGDeclarationLines) theRecord;
                
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDVehicles.class);
                    query.addAnd("contactNumber", record.getOperator());
                    
                List<DGDVehicles> results = util.executeGeneralSelectQuery(query);
                
                if(results.isEmpty())
                {
                    //log message
                    logMessage(Level.SEVERE, "Please select an operator with a vehicle registration number.", userData);
                    return false;
                }
                else if(results.size() > 1)
                {
                    record.setRegistrationNumber("~");
                    return record;
                }
                else //1
                {
                    record.setRegistrationNumber(results.get(0).getRegistrationNumber());
                    return record;
                }
            }
        }
        
        return result;
    }

    /**
     * 
     * @param contactNum
     * @return 
     */
    @Override
    public List<Object> getRegistrationNumbers(String contactNum, EMCUserData userData)
    {
        List<Object> toReturn;
        
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDVehicles.class);
            query.addAnd("contactNumber", contactNum);
            
        toReturn = util.executeGeneralSelectQuery(query, userData);
        
        return toReturn;
    }
    
}
