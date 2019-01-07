/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.dangerousgoods;

/**
 *
 * @author pj
 */
public class DeclarationReportDS {
    
    private String customerNum;
    
    private String consignorName;
    private String consignorCompany;
    private String consignorAddress1;
    private String consignorAddress2;
    private String consignorAddress3;
    private String consignorAddress4;
    private String consignorAddress5;
    private String consignorAddressCode;
    private String consignorTel; //telephone
    
    private String operatorName;
    private String operatorCompany;
    private String operatorAddress1;
    private String operatorAddress2;
    private String operatorAddress3;
    private String operatorAddress4;
    private String operatorAddress5;
    private String operatorAddressCode;
    private String operatorTel;
    private String vehicleReg;
    
    private String productMan; //manufacturer
    private String productOwner;
    private String productCust; //custodian
    
    private String consigneeName;
    private String consigneeCompany;
    private String consigneeAddress1;
    private String consigneeAddress2;
    private String consigneeAddress3;
    private String consigneeAddress4;
    private String consigneeAddress5;
    private String consigneeAddressCode;
    private String consigneeTel;
    
    private String contractingPartyName;
    private String contractingPartyCompany;
    private String contractingPartyAddress1;
    private String contractingPartyAddress2;
    private String contractingPartyAddress3;
    private String contractingPartyAddress4;
    private String contractingPartyAddress5;
    private String contractingPartyAddressCode;
    private String contractingPartyTel;
    
    private String shippingName;
    private String unNumber;
    private String hazClass;
    private String packingGroup;
    private String packaging;
    private String grossMass;
    private String netMass;
    
    private String additionalInfo;
    
    private String lineNumber;
    
    public DeclarationReportDS()
    {
        
    }
    
    /*Getters*/
    
    public String getCustomerNum(){return customerNum;}
    public String getConsignorName(){return consignorName;}
    public String getConsignorCompany(){return consignorCompany;}
    public String getConsignorAddress1(){return consignorAddress1;}
    public String getConsignorAddress2(){return consignorAddress2;}
    public String getConsignorAddress3(){return consignorAddress3;}
    public String getConsignorAddress4(){return consignorAddress4;}
    public String getConsignorAddress5(){return consignorAddress5;}
    public String getConsignorAddressCode(){return consignorAddressCode;}
    public String getConsignorTel(){return consignorTel;}
    public String getOperatorName(){return operatorName;}
    public String getOperatorCompany(){return operatorCompany;}
    public String getOperatorAddress1(){return operatorAddress1;}
    public String getOperatorAddress2(){return operatorAddress2;}
    public String getOperatorAddress3(){return operatorAddress3;}
    public String getOperatorAddress4(){return operatorAddress4;}
    public String getOperatorAddress5(){return operatorAddress5;}
    public String getOperatorAddressCode(){return operatorAddressCode;}
    public String getOperatorTel(){return operatorTel;}
    public String getVehicleReg(){return vehicleReg;}
    public String getProductMan(){return productMan;}
    public String getProductOwner(){return productOwner;}
    public String getProductCust(){return productCust;}
    public String getConsigneeName(){return consigneeName;}
    public String getConsigneeCompany(){return consigneeCompany;}
    public String getConsigneeAddress1(){return consigneeAddress1;}
    public String getConsigneeAddress2(){return consigneeAddress2;}
    public String getConsigneeAddress3(){return consigneeAddress3;}
    public String getConsigneeAddress4(){return consigneeAddress4;}
    public String getConsigneeAddress5(){return consigneeAddress5;}
    public String getConsigneeAddressCode(){return consigneeAddressCode;}
    public String getConsigneeTel(){return consigneeTel;}
    public String getContractingPartyName(){return contractingPartyName;}
    public String getContractingPartyAddress1(){return contractingPartyAddress1;}
    public String getContractingPartyAddress2(){return contractingPartyAddress2;}
    public String getContractingPartyAddress3(){return contractingPartyAddress3;}
    public String getContractingPartyAddress4(){return contractingPartyAddress4;}
    public String getContractingPartyAddress5(){return contractingPartyAddress5;}
    public String getContractingPartyAddressCode(){return contractingPartyAddressCode;}
    public String getContractingPartyCompany(){return contractingPartyCompany;}
    public String getContractingPartyTel(){return contractingPartyTel;}
    public String getShippingName(){return shippingName;}
    public String getUNNumber(){return unNumber;}
    public String getHazClass(){return hazClass;}
    public String getPackingGroup(){return packingGroup;}
    public String getPackaging(){return packaging;}
    public String getGrossMass(){return grossMass;}
    public String getNetMass(){return netMass;}
    public String getAdditionalInfo(){return additionalInfo;}
    public String getLineNumber(){return lineNumber;}

    /*Setters*/
    
    public void setCustomerNum(String customerNum){this.customerNum = customerNum;}
    public void setConsignorName(String consignorName){this.consignorName = consignorName;}
    public void setConsignorCompany(String consignorCompany){this.consignorCompany = consignorCompany;}
    public void setConsignorAddress1(String consignorAddress1){this.consignorAddress1 = consignorAddress1;}
    public void setConsignorAddress2(String consignorAddress2){this.consignorAddress2 = consignorAddress2;}
    public void setConsignorAddress3(String consignorAddress3){this.consignorAddress3 = consignorAddress3;}
    public void setConsignorAddress4(String consignorAddress4){this.consignorAddress4 = consignorAddress4;}
    public void setConsignorAddress5(String consignorAddress5){this.consignorAddress5 = consignorAddress5;}
    public void setConsignorAddressCode(String consignorAddressCode){this.consignorAddressCode = consignorAddressCode;}
    public void setConsignorTel(String consignorTel){this.consignorTel = consignorTel;}
    public void setOperatorName(String operatorName){this.operatorName = operatorName;}
    public void setOperatorCompany(String operatorCompany){this.operatorCompany = operatorCompany;}
    public void setOperatorAddress1(String operatorAddress1){this.operatorAddress1 = operatorAddress1;}
    public void setOperatorAddress2(String operatorAddress2){this.operatorAddress2 = operatorAddress2;}
    public void setOperatorAddress3(String operatorAddress3){this.operatorAddress3 = operatorAddress3;}
    public void setOperatorAddress4(String operatorAddress4){this.operatorAddress4 = operatorAddress4;}
    public void setOperatorAddress5(String operatorAddress5){this.operatorAddress5 = operatorAddress5;}
    public void setOperatorAddressCode(String operatorAddressCode){this.operatorAddressCode = operatorAddressCode;}
    public void setOperatorTel(String operatorTel){this.operatorTel = operatorTel;}
    public void setVehicleReg(String vehicleReg){this.vehicleReg = vehicleReg;}
    public void setProductMan(String productMan){this.productMan = productMan;}
    public void setProductOwner(String productOwner){this.productOwner = productOwner;}
    public void setProductCust(String productCust){this.productCust = productCust;}
    public void setConsigneeName(String consigneeName){this.consigneeName = consigneeName;}
    public void setConsigneeCompany(String consigneeCompany){this.consigneeCompany = consigneeCompany;}
    public void setConsigneeAddress1(String consigneeAddress1){this.consigneeAddress1 = consigneeAddress1;}
    public void setConsigneeAddress2(String consigneeAddress2){this.consigneeAddress2 = consigneeAddress2;}
    public void setConsigneeAddress3(String consigneeAddress3){this.consigneeAddress3 = consigneeAddress3;}
    public void setConsigneeAddress4(String consigneeAddress4){this.consigneeAddress4 = consigneeAddress4;}
    public void setConsigneeAddress5(String consigneeAddress5){this.consigneeAddress5 = consigneeAddress5;}
    public void setConsigneeAddressCode(String consigneeAddressCode){this.consigneeAddressCode = consigneeAddressCode;}
    public void setConsigneeTel(String consigneeTel){this.consigneeTel = consigneeTel;}
    public void setContractingPartyName(String contractingPartyName){this.contractingPartyName = contractingPartyName;}
    public void setContractingPartyAddress1(String contractingPartyAddress1){this.contractingPartyAddress1 = contractingPartyAddress1;}
    public void setContractingPartyAddress2(String contractingPartyAddress2){this.contractingPartyAddress2 = contractingPartyAddress2;}
    public void setContractingPartyAddress3(String contractingPartyAddress3){this.contractingPartyAddress3 = contractingPartyAddress3;}
    public void setContractingPartyAddress4(String contractingPartyAddress4){this.contractingPartyAddress4 = contractingPartyAddress4;}
    public void setContractingPartyAddress5(String contractingPartyAddress5){this.contractingPartyAddress5 = contractingPartyAddress5;}
    public void setContractingPartyAddressCode(String contractingPartyAddressCode){this.contractingPartyAddressCode = contractingPartyAddressCode;}
    public void setContractingPartyCompany(String contractingPartyCompany){this.contractingPartyCompany = contractingPartyCompany;}
    public void setContractingPartyTel(String contractingPartyTel){this.contractingPartyTel = contractingPartyTel;}
    public void setShippingName(String shippingName){this.shippingName = shippingName;}
    public void setUNNumber(String unNumber){this.unNumber = unNumber;}
    public void setHazClass(String hazClass){this.hazClass = hazClass;}
    public void setPackingGroup(String packingGroup){this.packingGroup = packingGroup;}
    public void setPackaging(String packaging){this.packaging = packaging;}
    public void setGrossMass(String grossMass){this.grossMass = grossMass;}
    public void setNetMass(String netMass){this.netMass = netMass;}
    public void setAdditionalInfo(String additionalInfo){this.additionalInfo = additionalInfo;}
    public void setLineNumber(String lineNumber){this.lineNumber = lineNumber;}
}
