/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.dangerousgoods;

import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDUN;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.enums.enumQueryTypes;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
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

    public DeclarationReportBean() {
    }

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("consignor", DGDeclarationLines.class.getName());        //0
        query.addField("operator", DGDeclarationLines.class.getName());        //1
        query.addField("consignee", DGDeclarationLines.class.getName());        //2
        query.addField("productManufacturer", DGDeclarationLines.class.getName());        //3
        query.addField("productOwner", DGDeclarationLines.class.getName());        //4
        query.addField("contractingParty", DGDeclarationLines.class.getName());        //5
        query.addField("productCustodian", DGDeclarationLines.class.getName());        //6
        query.addField("lineNumber", DGDeclarationLines.class.getName());        //7
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
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List<Object> ret = new ArrayList<>();

        for (Object result : queryResult) {
            Object[] declarationInfo = (Object[]) result;

            DeclarationReportDS ds = new DeclarationReportDS();
            
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[0]);
            DGDContacts consignorContact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            List<String> addressList = new ArrayList<>();

            if (consignorContact != null) {
                ds.setConsignorName(consignorContact.getContactName());
                ds.setConsignorCompany(consignorContact.getCompany());
                
                addressList = concertinaAddress(consignorContact.getPhysicalAddress1(), consignorContact.getPhysicalAddress2(), consignorContact.getPhysicalAddress3(), consignorContact.getPhysicalAddress4(), consignorContact.getPhysicalAddress5(), consignorContact.getPostalCode());
                ds.setConsignorAddress1(addressList.get(0));
                ds.setConsignorAddress2(addressList.get(1));
                ds.setConsignorAddress3(addressList.get(2));
                ds.setConsignorAddress4(addressList.get(3));
                ds.setConsignorAddress5(addressList.get(4));
                ds.setConsignorAddressCode(addressList.get(5));
                
                ds.setConsignorTel(consignorContact.getTelephone());
            }

            query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[1]);
            DGDContacts operatorContact = (DGDContacts) util.executeSingleResultQuery(query, userData);

            if (operatorContact != null) {
                ds.setOperatorName(operatorContact.getContactName());
                ds.setOperatorCompany(operatorContact.getCompany());
                
                addressList = concertinaAddress(operatorContact.getPhysicalAddress1(), operatorContact.getPhysicalAddress2(), operatorContact.getPhysicalAddress3(), operatorContact.getPhysicalAddress4(), operatorContact.getPhysicalAddress5(), operatorContact.getPostalCode());
                ds.setOperatorAddress1(addressList.get(0));
                ds.setOperatorAddress2(addressList.get(1));
                ds.setOperatorAddress3(addressList.get(2));
                ds.setOperatorAddress4(addressList.get(3));
                ds.setOperatorAddress5(addressList.get(4));
                ds.setOperatorAddressCode(addressList.get(5));
                
                ds.setOperatorTel(operatorContact.getTelephone());
            }
            
            query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[2]);
            DGDContacts consigneeContact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            
            if(consigneeContact != null)
            {
                ds.setConsigneeName(consigneeContact.getContactName());
                ds.setConsigneeCompany(consigneeContact.getCompany());
                
                addressList = concertinaAddress(consigneeContact.getPhysicalAddress1(), consigneeContact.getPhysicalAddress2(), consigneeContact.getPhysicalAddress3(), consigneeContact.getPhysicalAddress4(), consigneeContact.getPhysicalAddress5(), consigneeContact.getPostalCode());
                ds.setConsigneeAddress1(addressList.get(0));
                ds.setConsigneeAddress2(addressList.get(1));
                ds.setConsigneeAddress3(addressList.get(2));
                ds.setConsigneeAddress4(addressList.get(3));
                ds.setConsigneeAddress5(addressList.get(4));
                ds.setConsigneeAddressCode(addressList.get(5));
                
                ds.setConsigneeTel(consigneeContact.getTelephone());
            }
            
            
            query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[3]);
            DGDContacts productManContact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            
            if(productManContact != null)
            {
                ds.setProductMan(productManContact.getContactName());
            }
            
            query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[4]);
            DGDContacts productOwnerContact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            
            if(productOwnerContact != null)
            {
                ds.setProductOwner(productOwnerContact.getContactName());
            }
            
            query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[5]);
            DGDContacts contractingPartyContact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            
            if(contractingPartyContact != null)
            {
                ds.setContractingPartyName(contractingPartyContact.getContactName());
                ds.setContractingPartyCompany(contractingPartyContact.getCompany());
                ds.setContractingPartyTel(contractingPartyContact.getTelephone());
                
                addressList = concertinaAddress(contractingPartyContact.getPhysicalAddress1(), contractingPartyContact.getPhysicalAddress2(), contractingPartyContact.getPhysicalAddress3(), contractingPartyContact.getPhysicalAddress4(), contractingPartyContact.getPhysicalAddress5(), contractingPartyContact.getPostalCode());
                ds.setContractingPartyAddress1(addressList.get(0));
                ds.setContractingPartyAddress2(addressList.get(1));
                ds.setContractingPartyAddress3(addressList.get(2));
                ds.setContractingPartyAddress4(addressList.get(3));
                ds.setContractingPartyAddress5(addressList.get(4));
                ds.setContractingPartyAddressCode(addressList.get(5));
            }
            
            query = new EMCQuery(enumQueryTypes.SELECT, DGDContacts.class);
            query.addAnd("contactNumber", declarationInfo[6]);
            DGDContacts productCustContact = (DGDContacts) util.executeSingleResultQuery(query, userData);
            
            if(productCustContact != null)
            {
                ds.setProductCust(productCustContact.getContactName());
            }
            
//            query = new EMCQuery(enumQueryTypes.SELECT, DGDUN.class);
//            query.addAnd("lineNumber", declarationInfo[7]);
//            List<DGDUN> unItems = new ArrayList<>();
//            unItems = util.executeGeneralSelectQuery(query, userData);
            
            ret.add(ds);
        }

        return ret;

    }
}
