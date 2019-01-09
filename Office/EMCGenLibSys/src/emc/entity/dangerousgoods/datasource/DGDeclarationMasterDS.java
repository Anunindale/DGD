/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ConsignorName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.OperatorName;
import emc.datatypes.dangerousgoods.declarationmaster.datasource.CustomerName;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import java.util.HashMap;

/**
 *
 * @author asd_admin
 */
public class DGDeclarationMasterDS extends DGDeclarationMaster{
    
    private String consignorName;
    private String operatorName;
    private String customerName;

    public String getConsignorName() {
        return consignorName;
    }

    public void setConsignorName(String consignorName) {
        this.consignorName = consignorName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public DGDeclarationMasterDS()
    {
        this.setDataSource(true);
    }
    
    @Override
    protected HashMap<String, EMCDataType> buildFieldList() 
    {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("consignorName", new ConsignorName());
        toBuild.put("operatorName", new OperatorName());
        toBuild.put("customerName", new CustomerName());

        return toBuild;
    }
    
}
