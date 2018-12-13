
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.entity.crm;

import emc.datatypes.EMCDataType;
import emc.datatypes.base.employeetable.Surname;
import emc.datatypes.base.language.foreignkey.LanguageFKNM;
import emc.datatypes.base.provence.foreignkeys.ProvenceFKNM;
import emc.datatypes.crm.interest.foreignkey.InterestFKNM;
import emc.datatypes.crm.prospect.CellphoneCRMMan;
import emc.datatypes.crm.prospect.Company;
import emc.datatypes.crm.prospect.PostalAddress1NM;
import emc.datatypes.crm.prospect.PostalCodeNM;
import emc.datatypes.crm.prospect.ProspectId;
import emc.datatypes.crm.prospect.Request;
import emc.datatypes.crm.prospectclosereason.foreignkey.ReasonIdFKNM;
import emc.datatypes.systemwide.EmailAddress;
import emc.datatypes.systemwide.Name;
import emc.datatypes.systemwide.PostalAddress2;
import emc.datatypes.systemwide.PostalAddress3;
import emc.datatypes.systemwide.PostalAddress4;
import emc.datatypes.systemwide.PostalAddress5;
import emc.datatypes.systemwide.Telephone;
import emc.enums.crm.CRMProspectStatus;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.functions.Functions;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author wikus
 */
@Entity
@Table(name = "CRMProspect", uniqueConstraints = {@UniqueConstraint(columnNames = {"prospectId", "name", "surname", "companyId"})})
public class CRMProspect extends EMCEntityClass {

    private String prospectId;
    private String name;
    private String surname;
    private String telNum;
    private String cellNum;
    private String email;
    private String postalAddress1;
    private String postalAddress2;
    private String postalAddress3;
    private String postalAddress4;
    private String postalAddress5;
    private String postalPostalCode;
    private String province;
    private String whereHeard;
    private String interest;
    @Column(length = 10000)
    private String request;
    private String company;
    private String transferedTo;
    private String transferedRecordId;
    private boolean transfered;
    private String prosStatus = CRMProspectStatus.ACTIVE.toString();
    private String school; //UM related field
    private String employeeId;// salesRep
    private String prefLanguage;// UM related
    private String classification1; //user defined field
    private String classification2; //user defined field
    private String classification3; //user defined field
    @Temporal(TemporalType.DATE)
    private Date presentationDate;
    @Temporal(TemporalType.DATE)
    private Date loggedDate = Functions.nowDate(); //date the enquiry was logged
    private String campus;
    private String reasonCode; //reason for closure
    public String getCellNum() {
        return cellNum;
    }

    public void setCellNum(String cellNum) {
        this.cellNum = cellNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalAddress1() {
        return postalAddress1;
    }

    public void setPostalAddress1(String postalAddress1) {
        this.postalAddress1 = postalAddress1;
    }

    public String getPostalAddress2() {
        return postalAddress2;
    }

    public void setPostalAddress2(String postalAddress2) {
        this.postalAddress2 = postalAddress2;
    }

    public String getPostalAddress3() {
        return postalAddress3;
    }

    public void setPostalAddress3(String postalAddress3) {
        this.postalAddress3 = postalAddress3;
    }

    public String getPostalAddress4() {
        return postalAddress4;
    }

    public void setPostalAddress4(String postalAddress4) {
        this.postalAddress4 = postalAddress4;
    }

    public String getPostalAddress5() {
        return postalAddress5;
    }

    public void setPostalAddress5(String postalAddress5) {
        this.postalAddress5 = postalAddress5;
    }

    public String getPostalPostalCode() {
        return postalPostalCode;
    }

    public void setPostalPostalCode(String postalPostalCode) {
        this.postalPostalCode = postalPostalCode;
    }

    public String getProspectId() {
        return prospectId;
    }

    public void setProspectId(String prospectId) {
        this.prospectId = prospectId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelNum() {
        return telNum;
    }

    public void setTelNum(String telNum) {
        this.telNum = telNum;
    }

    public String getWhereHeard() {
        return whereHeard;
    }

    public void setWhereHeard(String whereHeard) {
        this.whereHeard = whereHeard;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTransferedTo() {
        return transferedTo;
    }

    public void setTransferedTo(String transferedTo) {
        this.transferedTo = transferedTo;
    }

    public String getTransferedRecordId() {
        return transferedRecordId;
    }

    public void setTransferedRecordId(String transferedRecordId) {
        this.transferedRecordId = transferedRecordId;
    }

    public boolean isTransfered() {
        return transfered;
    }

    public void setTransfered(boolean transfered) {
        this.transfered = transfered;
    }

    @Override
    protected HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("prospectId", new ProspectId());
        toBuild.put("name", new Name());
        toBuild.put("surname", new Surname());
        toBuild.put("telNum", new Telephone());
        toBuild.put("cellNum", new CellphoneCRMMan());
        toBuild.put("email", new EmailAddress());
        toBuild.put("postalAddress1", new PostalAddress1NM());
        toBuild.put("postalAddress2", new PostalAddress2());
        toBuild.put("postalAddress3", new PostalAddress3());
        toBuild.put("postalAddress4", new PostalAddress4());
        toBuild.put("postalAddress5", new PostalAddress5());
        toBuild.put("postalPostalCode", new PostalCodeNM());
        toBuild.put("province", new ProvenceFKNM());
        toBuild.put("interest", new InterestFKNM());
        toBuild.put("request", new Request());
        toBuild.put("company", new Company());
        toBuild.put("prefLanguage",new LanguageFKNM());
        toBuild.put("reasonCode",new ReasonIdFKNM());

        return toBuild;
    }

    @Override
    public EMCQuery buildQuery() {
        EMCQuery query = super.buildQuery();
        query.addAnd("transfered", false);
        query.addAnd("prosStatus", CRMProspectStatus.ACTIVE.toString());
        return query;
    }

    /**
     * @return the school
     */
    public String getSchool() {
        return school;
    }

    /**
     * @param school the school to set
     */
    public void setSchool(String school) {
        this.school = school;
    }

    /**
     * @return the employeeId
     */
    public String getEmployeeId() {
        return employeeId;
    }

    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    /**
     * @return the languageId
     */
    public String getPrefLanguage() {
        return prefLanguage;
    }

    /**
     * @param languageId the languageId to set
     */
    public void setPrefLanguage(String prefLanguage) {
        this.prefLanguage = prefLanguage;
    }

    /**
     * @return the classification1
     */
    public String getClassification1() {
        return classification1;
    }

    /**
     * @param classification1 the classification1 to set
     */
    public void setClassification1(String classification1) {
        this.classification1 = classification1;
    }

    /**
     * @return the presentationDate
     */
    public Date getPresentationDate() {
        return presentationDate;
    }

    /**
     * @param presentationDate the presentationDate to set
     */
    public void setPresentationDate(Date presentationDate) {
        this.presentationDate = presentationDate;
    }

    /**
     * @return the classification2
     */
    public String getClassification2() {
        return classification2;
    }

    /**
     * @param classification2 the classification2 to set
     */
    public void setClassification2(String classification2) {
        this.classification2 = classification2;
    }

    /**
     * @return the classification3
     */
    public String getClassification3() {
        return classification3;
    }

    /**
     * @param classification3 the classification3 to set
     */
    public void setClassification3(String classification3) {
        this.classification3 = classification3;
    }

    /**
     * @return the campus
     */
    public String getCampus() {
        return campus;
    }

    /**
     * @param campus the campus to set
     */
    public void setCampus(String campus) {
        this.campus = campus;
    }

    /**
     * @return the prosStatus
     */
    public String getProsStatus() {
        return prosStatus;
    }

    /**
     * @param prosStatus the prosStatus to set
     */
    public void setProsStatus(String prosStatus) {
        this.prosStatus = prosStatus;
    }

    /**
     * @return the reasonCode
     */
    public String getReasonCode() {
        return reasonCode;
    }

    /**
     * @param reasonCode the reasonCode to set
     */
    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    /**
     * @return the loggedDate
     */
    public Date getLoggedDate() {
        return loggedDate;
    }

    /**
     * @param loggedDate the loggedDate to set
     */
    public void setLoggedDate(Date loggedDate) {
        this.loggedDate = loggedDate;
    }
}
