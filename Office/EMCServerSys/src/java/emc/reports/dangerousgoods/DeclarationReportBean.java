/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.dangerousgoods;

import emc.entity.dangerousgoods.DGDUN;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.reporttools.EMCReportConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author pj
 */

@Stateless
public class DeclarationReportBean extends EMCReportBean implements DeclarationReportLocal {
    
    public DeclarationReportBean()
    {
        
    }
 
    @Override 
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData)
    {
        query.addField("consignor", DGDeclarationLines.class.getName());        //0
        query.addField("consignee", DGDeclarationLines.class.getName());        //1
        query.addField("operator", DGDeclarationLines.class.getName());         //2
        query.addField("productManufacturer", DGDeclarationLines.class.getName()); //3
        query.addField("productOwner", DGDeclarationLines.class.getName());     //4
        query.addField("contractingParty", DGDeclarationLines.class.getName()); //5
        query.addField("productCustodian", DGDeclarationLines.class.getName()); //6
                
        return query;
    }
    
//    @Override 
//    public List<Object> getReportResult(List<Object> queryList, EMCReportConfig reportConfig, EMCUserData userData)
//    {
//        Map<String, Object> parameters = reportConfig.getParameters();
//        userData.setUserData(4, parameters.get(""));
//        
//        return super.getReportResult(queryList, reportConfig, userData);
//    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) 
    {
        List<Object> ret = new ArrayList<>();
        
        for(Object result : queryResult)
        {
            Object[] declarationInfo = (Object[]) result;
            
            DeclarationReportDS ds = new DeclarationReportDS();
            ds.setConsignorName((String) declarationInfo[0]);
            ds.setConsigneeName((String) declarationInfo[1]);
            ds.setOperatorName((String) declarationInfo[2]);
            ds.setProductMan((String) declarationInfo[3]);
            ds.setProductOwner((String) declarationInfo[4]);
            ds.setContractingPartyName((String) declarationInfo[5]);
            ds.setProductCust((String) declarationInfo[6]);

            //queries to populate rest of datasource fields
            
            ret.add(ds);
        }
        
        return ret;
        
    }
    
}
