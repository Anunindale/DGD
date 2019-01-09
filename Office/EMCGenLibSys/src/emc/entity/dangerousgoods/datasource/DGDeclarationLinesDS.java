/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.dangerousgoods.datasource;

import emc.datatypes.EMCDataType;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ConsigneeName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ConsignorName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ContractingPartyName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.OperatorName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ProductCustodianName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ProductManufacturerName;
import emc.datatypes.dangerousgoods.declarationlines.datasource.ProductOwnerName;
import emc.entity.dangerousgoods.DGDeclarationLines;
import java.util.HashMap;

/**
 *
 * @author pj
 */
public class DGDeclarationLinesDS extends DGDeclarationLines{
    
    private String consignorName;   
    private String consigneeName;
    private String operatorName;
    private String productManufacturerName;
    private String productOwnerName;
    private String contractingPartyName;
    private String productCustodianName;
    
    public DGDeclarationLinesDS()
    {
        this.setDataSource(true);
    }
    
    public String getConsignorName(){return consignorName;}
    public String getConsigneeName(){return consigneeName;}
    public String getOperatorName(){return operatorName;}
    public String getProductManufacturerName(){return productManufacturerName;}
    public String getProductOwnerName(){return productOwnerName;}
    public String getContractingPartyName(){return contractingPartyName;}
    public String getProductCustodianName(){return productCustodianName;}
    
    public void setConsignorName(String consignorName){this.consignorName = consignorName;}
    public void setConsigneeName(String consigneeName){this.consigneeName = consigneeName;}
    public void setOperatorName(String operatorName){this.operatorName = operatorName;}
    public void setProductManufacturerName(String productManufacturerName){this.productManufacturerName = productManufacturerName;}
    public void setProductOwnerName(String productOwnerName){this.productOwnerName = productOwnerName;}
    public void setContractingPartyName(String contractingPartyName){this.contractingPartyName = contractingPartyName;}
    public void setProductCustodianName(String productCustodianName){this.productCustodianName = productCustodianName;}

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() 
    {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("consignorName", new ConsignorName());
        toBuild.put("consigneeName", new ConsigneeName());
        toBuild.put("operatorName", new OperatorName());
        toBuild.put("productManufacturerName", new ProductManufacturerName());
        toBuild.put("productOwnerName", new ProductOwnerName());
        toBuild.put("contractingPartyName", new ContractingPartyName());
        toBuild.put("productCustodianName", new ProductCustodianName());

        return toBuild;
    }
    
}
