/*
 * BaseCompanyTable.java
 *
 * Created on 04 December 2007, 07:50
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package emc.entity.base;

import emc.datatypes.base.companies.BankAccount;
import emc.datatypes.base.companies.BankBranch;
import emc.datatypes.base.companies.BankName;
import emc.datatypes.base.companies.CompanyName;
import emc.datatypes.base.companies.Currency;
import emc.datatypes.base.companies.ExportersCode;
import emc.datatypes.base.companies.LetterHeadPath;
import emc.datatypes.base.companies.LogoPath;
import emc.datatypes.systemwide.PhysicalAddress1;
import emc.datatypes.systemwide.PhysicalAddress2;
import emc.datatypes.systemwide.PhysicalAddress3;
import emc.datatypes.systemwide.PhysicalAddress4;
import emc.datatypes.systemwide.PhysicalAddress5;
import emc.datatypes.systemwide.PostalAddress1;
import emc.datatypes.systemwide.PostalAddress2;
import emc.datatypes.systemwide.PostalAddress3;
import emc.datatypes.systemwide.PostalAddress4;
import emc.datatypes.systemwide.PostalAddress5;
import emc.datatypes.base.companies.RegistrationNumber;
import emc.datatypes.base.companies.TradingAs;
import emc.datatypes.base.companies.VatNumber;
import emc.datatypes.systemwide.EmailAddress;
import emc.datatypes.systemwide.PostalCode;
import emc.datatypes.systemwide.Telephone;
import emc.datatypes.systemwide.Website;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseCompany",uniqueConstraints={@UniqueConstraint(columnNames={"companyId"})})

public class BaseCompanyTable extends EMCEntityClass implements Serializable {
    @Column(name = "CompanyName", nullable = false)
    private String companyName;
  
    private String coTradingAs;
    
    private String coPhysAdrs1;
     
   
    private String coPhysAdrs2;
  
    private String coPhysAdrs3;
    
    
    private String coPhysAdrs4;

    private String coPhysAdrs5;
   
    private String coPhysPostCode;
    
    private String coPostAdrs1;
    private String coPostAdrs2;
    private String coPostAdrs3;
    private String coPostAdrs4;
    private String coPostAdrs5;
    private String coPostCode;
    
    private String coRegNr;
    private String coVatRegNr;
    private String coPhoneNr;
    private String coCallCentreNumber;
    private String coEmergencyNumber;
    private String coCellNr;
    private String coFaxNr;
    private String coEmailAdrs;
    private String coWebsite;
    private String coBank;
    private String coBankBranch;
    private String coBankAccNo;
    private String coLogoPath;
    private String coLetterHeadPath;
    //Export
    private String exportersCode;
    private String swiftCode;
    private String currency;

    //Company Logo
    private String companyLogo;
    //Company Letter Head
    private String companyLetterHead;
    
    /** Creates a new instance of BaseCompanyTable */
    public BaseCompanyTable() {
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCoTradingAs() {
        return coTradingAs;
    }

    public void setCoTradingAs(String coTradingAs) {
        this.coTradingAs = coTradingAs;
    }

    public String getCoPhysAdrs1() {
        return coPhysAdrs1;
    }

    public void setCoPhysAdrs1(String coPhysAdrs1) {
        this.coPhysAdrs1 = coPhysAdrs1;
    }

    public String getCoPhysAdrs2() {
        return coPhysAdrs2;
    }

    public void setCoPhysAdrs2(String coPhysAdrs2) {
        this.coPhysAdrs2 = coPhysAdrs2;
    }

    public String getCoPhysAdrs3() {
        return coPhysAdrs3;
    }

    public void setCoPhysAdrs3(String coPhysAdrs3) {
        this.coPhysAdrs3 = coPhysAdrs3;
    }

    public String getCoPhysAdrs4() {
        return coPhysAdrs4;
    }

    public void setCoPhysAdrs4(String coPhysAdrs4) {
        this.coPhysAdrs4 = coPhysAdrs4;
    }

    public String getCoPhysAdrs5() {
        return coPhysAdrs5;
    }

    public void setCoPhysAdrs5(String coPhysAdrs5) {
        this.coPhysAdrs5 = coPhysAdrs5;
    }

    public String getCoPhysPostCode() {
        return coPhysPostCode;
    }

    public void setCoPhysPostCode(String coPhysPostCode) {
        this.coPhysPostCode = coPhysPostCode;
    }

    public String getCoPostAdrs1() {
        return coPostAdrs1;
    }

    public void setCoPostAdrs1(String coPostAdrs1) {
        this.coPostAdrs1 = coPostAdrs1;
    }

    public String getCoPostAdrs2() {
        return coPostAdrs2;
    }

    public void setCoPostAdrs2(String coPostAdrs2) {
        this.coPostAdrs2 = coPostAdrs2;
    }

    public String getCoPostAdrs3() {
        return coPostAdrs3;
    }

    public void setCoPostAdrs3(String coPostAdrs3) {
        this.coPostAdrs3 = coPostAdrs3;
    }

    public String getCoPostAdrs4() {
        return coPostAdrs4;
    }

    public void setCoPostAdrs4(String coPostAdrs4) {
        this.coPostAdrs4 = coPostAdrs4;
    }

    public String getCoPostAdrs5() {
        return coPostAdrs5;
    }

    public void setCoPostAdrs5(String coPostAdrs5) {
        this.coPostAdrs5 = coPostAdrs5;
    }

    public String getCoPostCode() {
        return coPostCode;
    }

    public void setCoPostCode(String coPostCode) {
        this.coPostCode = coPostCode;
    }

    public String getCoRegNr() {
        return coRegNr;
    }

    public void setCoRegNr(String coRegNr) {
        this.coRegNr = coRegNr;
    }

    public String getCoVatRegNr() {
        return coVatRegNr;
    }

    public void setCoVatRegNr(String coVatRegNr) {
        this.coVatRegNr = coVatRegNr;
    }

    public String getCoPhoneNr() {
        return coPhoneNr;
    }

    public void setCoPhoneNr(String coPhoneNr) {
        this.coPhoneNr = coPhoneNr;
    }

    public String getCoCallCentreNumber() {
        return coCallCentreNumber;
    }

    public void setCoCallCentreNumber(String coCallCentreNumber) {
        this.coCallCentreNumber = coCallCentreNumber;
    }

    public String getCoEmergencyNumber() {
        return coEmergencyNumber;
    }

    public void setCoEmergencyNumber(String coEmergencyNumber) {
        this.coEmergencyNumber = coEmergencyNumber;
    }

    public String getCoCellNr() {
        return coCellNr;
    }

    public void setCoCellNr(String coCellNr) {
        this.coCellNr = coCellNr;
    }

    public String getCoFaxNr() {
        return coFaxNr;
    }

    public void setCoFaxNr(String coFaxNr) {
        this.coFaxNr = coFaxNr;
    }

    public String getCoEmailAdrs() {
        return coEmailAdrs;
    }

    public void setCoEmailAdrs(String coEmailAdrs) {
        this.coEmailAdrs = coEmailAdrs;
    }

    public String getCoWebsite() {
        return coWebsite;
    }

    public void setCoWebsite(String coWebsite) {
        this.coWebsite = coWebsite;
    }

    public String getCoBank() {
        return coBank;
    }

    public void setCoBank(String coBank) {
        this.coBank = coBank;
    }

    public String getCoBankBranch() {
        return coBankBranch;
    }

    public void setCoBankBranch(String coBankBranch) {
        this.coBankBranch = coBankBranch;
    }

    public String getCoBankAccNo() {
        return coBankAccNo;
    }

    public void setCoBankAccNo(String coBankAccNo) {
        this.coBankAccNo = coBankAccNo;
    }

    public String getCoLogoPath() {
        return coLogoPath;
    }

    public void setCoLogoPath(String coLogoPath) {
        this.coLogoPath = coLogoPath;
    }

    public String getCoLetterHeadPath() {
        return coLetterHeadPath;
    }

    public void setCoLetterHeadPath(String coLetterHeadPath) {
        this.coLetterHeadPath = coLetterHeadPath;
    }
    
    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = new HashMap();//special do not want relation to self on companyId
       
        toBuild.put("companyName", new CompanyName());
        toBuild.put("coWebsite", new Website());
        toBuild.put("coPhysAdrs1", new PhysicalAddress1());
        toBuild.put("coPhysAdrs2", new PhysicalAddress2());
        toBuild.put("coPhysAdrs3", new PhysicalAddress3());
        toBuild.put("coPhysAdrs4", new PhysicalAddress4());
        toBuild.put("coPhysAdrs5", new PhysicalAddress5());
        toBuild.put("coPostAdrs1", new PostalAddress1());
        toBuild.put("coPostAdrs2", new PostalAddress2());
        toBuild.put("coPostAdrs3", new PostalAddress3());
        toBuild.put("coPostAdrs4", new PostalAddress4());
        toBuild.put("coPostAdrs5", new PostalAddress5());
        PostalCode postalCode = new PostalCode();
        toBuild.put("coPhysPostCode", postalCode);
        toBuild.put("coPostCode", postalCode);
        toBuild.put("coTradingAs", new TradingAs());
        Telephone telephone = new Telephone();
        toBuild.put("coPhoneNr", telephone);
        toBuild.put("coCallCentreNumber", telephone);
        toBuild.put("coEmergencyNumber", telephone);
        toBuild.put("coCellNr", telephone);
        toBuild.put("coFaxNr", telephone);
        toBuild.put("coEmailAdrs", new EmailAddress());
        toBuild.put("coRegNr", new RegistrationNumber());
        toBuild.put("coVatRegNr", new VatNumber());
        toBuild.put("coBank", new BankName());
        toBuild.put("coBankBranch", new BankBranch());
        toBuild.put("coBankAccNo", new BankAccount());
        toBuild.put("coLogoPath", new LogoPath());
        toBuild.put("coLetterHeadPath", new LetterHeadPath());
        toBuild.put("exportersCode", new ExportersCode());
        toBuild.put("currency", new Currency());
        
        return toBuild;
    }

    @Override
    public List<String> buildDefaultLookupFieldList() {
        List<String> fields = super.buildDefaultLookupFieldList();
        
        fields.add("companyName");
        fields.add("coTradingAs");
        
        return fields;
    }

    public String getExportersCode() {
        return exportersCode;
    }

    public void setExportersCode(String exportersCode) {
        this.exportersCode = exportersCode;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCompanyLogo() {
        return companyLogo;
    }

    public void setCompanyLogo(String companyLogo) {
        this.companyLogo = companyLogo;
    }

    public String getCompanyLetterHead() {
        return companyLetterHead;
    }

    public void setCompanyLetterHead(String companyLetterHead) {
        this.companyLetterHead = companyLetterHead;
    }
 
}
