/* 
 * BaseEmployeeTable.java
 * 
 * Created on 11 December 2007, 10:14 
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.  
 */
package emc.entity.base;

import emc.datatypes.base.employeetable.ActualOccupation;
import emc.datatypes.base.employeetable.AltIdNumber;
import emc.datatypes.base.employeetable.CostPricePerHour;
import emc.datatypes.base.employeetable.Department;
import emc.datatypes.base.employeetable.EmployeeId;
import emc.datatypes.base.employeetable.EmployeeIdNumber;
import emc.datatypes.base.employeetable.EmployeeType;
import emc.datatypes.base.employeetable.Forenames;
import emc.datatypes.base.employeetable.HighestQualification;
import emc.datatypes.base.employeetable.InternalTelephone;
import emc.datatypes.base.employeetable.JobTitle;
import emc.datatypes.base.employeetable.KnownAs;
import emc.datatypes.base.employeetable.SellingPricePerHour;
import emc.datatypes.base.employeetable.Surname;
import emc.datatypes.base.language.foreignkey.HomeLanguageFKNM;
import emc.datatypes.hr.alternativeidtype.foreignkeys.AltIdTypeFKNM;
import emc.datatypes.hr.citizenstatus.foreignkeys.CitizenStatusFK;
import emc.datatypes.hr.disabilitytypes.foreignkey.DisabilityTypesFK;
import emc.datatypes.hr.employmenttype.foreignkeys.EmploymentTypeFK;
import emc.datatypes.hr.equitycode.foreignkeys.EquityCodeFK;
import emc.datatypes.hr.grade.foreignkey.GradeFK;
import emc.datatypes.hr.levelofexperience.foreignkeys.LevelOfExpFKNM;
import emc.datatypes.hr.medicalaid.foreignkeys.MedicalAidFK;
import emc.datatypes.hr.nationality.foreignkeys.NationalityFK;
import emc.datatypes.hr.race.foreignkeys.RaceFK;
import emc.datatypes.hr.socioecostatus.foreignkeys.SocioEcoStatusFK;
import emc.datatypes.hr.union.foreignkeys.UnionFK;
import emc.datatypes.systemwide.Cellphone;
import emc.datatypes.systemwide.EmailAddress;
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
import emc.datatypes.systemwide.PostalCode;
import emc.datatypes.systemwide.Telephone;
import emc.datatypes.systemwide.UserId;
import emc.datatypes.workflow.activitypriority.Manager;
import emc.framework.EMCEntityClass;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import emc.datatypes.hr.remunerantiontype.foreignkeys.RemunerationTypeFK;

/**
 *
 * @author rico
 */
@Entity
@Table(name = "BaseEmployee", uniqueConstraints = {@UniqueConstraint(columnNames = {"employeeNumber", "companyId"})})
public class BaseEmployeeTable extends EMCEntityClass implements Serializable {

    private String employeeNumber;
    private String surname;
    private String forenames;
    private String knownAs;
    private String userId;
    private String employeeType;
    private String jobTitle;
    private String department;
    private String emailAddress;
    private String internalPhoneNumber;
    private String cellPhoneNumber;
    private String homePhoneNumber;
    private String addressPhysicalLine1;
    private String addressPhysicalLine2;
    private String addressPhysicalLine3;
    private String addressPhysicalLine4;
    private String addressPhysicalLine5;
    private String addressPhysPostalCode;
    private String postalAddressLine1;
    private String postalAddressLine2;
    private String postalAddressLine3;
    private String postalAddressLine4;
    private String postalAddressLine5;
    private String postalCode;
    private double sellingPricePerHour;
    private double costPricePerHour;
    private String employeeID;
    private String faxNumber;
    private String manager;
    private String employeeGrade;
    private String clockNumber;
    //Fields added for HR Module
    @Temporal(TemporalType.DATE)
    private Date dateStartedInPos;
    private String initials;
    private String passportNumber;
    private String nationality;
    private String taxNumber;
    @Temporal(TemporalType.DATE)
    private Date dateOfEmployment;
    private String typeOfEmployment;
    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
    private String gender;
    private String race;
    private String maritalStatus;
    private String spouse;
    private String branch;
    private String employmentSection;
    private String terminationType;
    @Temporal(TemporalType.DATE)
    private Date terminationDate;
    private String medicalAid;
    private String medicalSchema;
    private String accountNo;
    private int numDependancies;
    private boolean useCompaniesMedical;
    private String unionId;
    private String jobGrade;
    private String bargainCouncilCode;
    private boolean disability;
    private String disabilityType;
    private String workTimeType;
    private boolean workShifts;
    private String typeOfConcern;
    private String vatNo;
    private String webUID;
    private String altIdType;
    private String altIdNumber;
    private String equityCode;
    private String socioEcoStatus;
    private String highestQualification;
    private String homeLanguage;
    private String citizenStatus;
    private String actualOccupation;
    private String remunerationType;
    private String levelOfExperience;

    @Override
    public HashMap buildFieldList() {
        HashMap toBuild = super.buildFieldList();

        toBuild.put("employeeNumber", new EmployeeId());
        toBuild.put("surname", new Surname());
        toBuild.put("forenames", new Forenames());
        toBuild.put("internalPhoneNumber", new InternalTelephone());
        toBuild.put("emailAddress", new EmailAddress());

        PostalCode postalCodeDT = new PostalCode();

        toBuild.put("addressPhysPostalCode", postalCodeDT);
        toBuild.put("postalCode", postalCodeDT);

        toBuild.put("userId", new UserId());
        // private String employeeType;
        //private String jobTitle;
        toBuild.put("department", new Department());
        toBuild.put("jobTitle", new JobTitle());
        toBuild.put("manager", new Manager());
        toBuild.put("employeeType", new EmployeeType());
        toBuild.put("knownAs", new KnownAs());
        toBuild.put("homePhoneNumber", new Telephone());
        toBuild.put("cellPhoneNumber", new Cellphone());

        toBuild.put("addressPhysicalLine1", new PhysicalAddress1());
        toBuild.put("addressPhysicalLine2", new PhysicalAddress2());
        toBuild.put("addressPhysicalLine3", new PhysicalAddress3());
        toBuild.put("addressPhysicalLine4", new PhysicalAddress4());
        toBuild.put("addressPhysicalLine5", new PhysicalAddress5());
        toBuild.put("postalAddressLine1", new PostalAddress1());
        toBuild.put("postalAddressLine2", new PostalAddress2());
        toBuild.put("postalAddressLine3", new PostalAddress3());
        toBuild.put("postalAddressLine4", new PostalAddress4());
        toBuild.put("postalAddressLine5", new PostalAddress5());
        toBuild.put("sellingPricePerHour", new SellingPricePerHour());
        toBuild.put("costPricePerHour", new CostPricePerHour());
        toBuild.put("faxNumber", new Telephone());
        toBuild.put("employeeID", new EmployeeIdNumber());

        toBuild.put("nationality", new NationalityFK());
        toBuild.put("race", new RaceFK());
        toBuild.put("typeOfEmployment", new EmploymentTypeFK());

        toBuild.put("medicalAid", new MedicalAidFK());
        toBuild.put("unionId", new UnionFK());

        toBuild.put("jobGrade", new GradeFK());
        toBuild.put("disabilityType", new DisabilityTypesFK());

        toBuild.put("altIdType", new AltIdTypeFKNM());
        toBuild.put("altIdNumber", new AltIdNumber());
        toBuild.put("equityCode", new EquityCodeFK());
        toBuild.put("socioEcoStatus", new SocioEcoStatusFK());
        toBuild.put("highestQualification", new HighestQualification());
        toBuild.put("homeLanguage", new HomeLanguageFKNM());
        toBuild.put("citizenStatus", new CitizenStatusFK());
        toBuild.put("actualOccupation", new ActualOccupation());
        toBuild.put("remunerationType", new RemunerationTypeFK());
        toBuild.put("levelOfExperience", new LevelOfExpFKNM());
        return toBuild;
    }

    @Override
    protected List<String> buildDefaultLookupFieldList() {
        List toBuild = super.buildDefaultLookupFieldList();
        toBuild.add("employeeNumber");
        toBuild.add("forenames");
        toBuild.add("surname");
        return toBuild;
    }

    /** Creates a new instance of BaseEmployeeTable */
    public BaseEmployeeTable() {
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getForenames() {
        return forenames;
    }

    public void setForenames(String forenames) {
        this.forenames = forenames;
    }

    public String getKnownAs() {
        return knownAs;
    }

    public void setKnownAs(String knownAs) {
        this.knownAs = knownAs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getInternalPhoneNumber() {
        return internalPhoneNumber;
    }

    public void setInternalPhoneNumber(String internalPhoneNumber) {
        this.internalPhoneNumber = internalPhoneNumber;
    }

    public String getCellPhoneNumber() {
        return cellPhoneNumber;
    }

    public void setCellPhoneNumber(String cellPhoneNumber) {
        this.cellPhoneNumber = cellPhoneNumber;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getAddressPhysicalLine1() {
        return addressPhysicalLine1;
    }

    public void setAddressPhysicalLine1(String addressPhysicalLine1) {
        this.addressPhysicalLine1 = addressPhysicalLine1;
    }

    public String getAddressPhysicalLine2() {
        return addressPhysicalLine2;
    }

    public void setAddressPhysicalLine2(String addressPhysicalLine2) {
        this.addressPhysicalLine2 = addressPhysicalLine2;
    }

    public String getAddressPhysicalLine3() {
        return addressPhysicalLine3;
    }

    public void setAddressPhysicalLine3(String addressPhysicalLine3) {
        this.addressPhysicalLine3 = addressPhysicalLine3;
    }

    public String getAddressPhysicalLine4() {
        return addressPhysicalLine4;
    }

    public void setAddressPhysicalLine4(String addressPhysicalLine4) {
        this.addressPhysicalLine4 = addressPhysicalLine4;
    }

    public String getAddressPhysicalLine5() {
        return addressPhysicalLine5;
    }

    public void setAddressPhysicalLine5(String addressPhysicalLine5) {
        this.addressPhysicalLine5 = addressPhysicalLine5;
    }

    public String getPostalAddressLine1() {
        return postalAddressLine1;
    }

    public void setPostalAddressLine1(String postalAddressLine1) {
        this.postalAddressLine1 = postalAddressLine1;
    }

    public String getPostalAddressLine2() {
        return postalAddressLine2;
    }

    public void setPostalAddressLine2(String postalAddressLine2) {
        this.postalAddressLine2 = postalAddressLine2;
    }

    public String getPostalAddressLine3() {
        return postalAddressLine3;
    }

    public void setPostalAddressLine3(String postalAddressLine3) {
        this.postalAddressLine3 = postalAddressLine3;
    }

    public String getPostalAddressLine5() {
        return postalAddressLine5;
    }

    public void setPostalAddressLine5(String postalAddressLine5) {
        this.postalAddressLine5 = postalAddressLine5;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public double getSellingPricePerHour() {
        return sellingPricePerHour;
    }

    public void setSellingPricePerHour(double sellingPricePerHour) {
        this.sellingPricePerHour = sellingPricePerHour;
    }

    public double getCostPricePerHour() {
        return costPricePerHour;
    }

    public void setCostPricePerHour(double costPricePerHour) {
        this.costPricePerHour = costPricePerHour;
    }

    public String getAddressPhysPostalCode() {
        return addressPhysPostalCode;
    }

    public void setAddressPhysPostalCode(String addressPhysPostalCode) {
        this.addressPhysPostalCode = addressPhysPostalCode;
    }

    public String getPostalAddressLine4() {
        return postalAddressLine4;
    }

    public void setPostalAddressLine4(String postalAddressLine4) {
        this.postalAddressLine4 = postalAddressLine4;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(String faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getClockNumber() {
        return clockNumber;
    }

    public void setClockNumber(String clockNumber) {
        this.clockNumber = clockNumber;
    }

    public String getEmployeeGrade() {
        return employeeGrade;
    }

    public void setEmployeeGrade(String employeeCategory) {
        this.employeeGrade = employeeCategory;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public String getTypeOfEmployment() {
        return typeOfEmployment;
    }

    public void setTypeOfEmployment(String typeOfEmployment) {
        this.typeOfEmployment = typeOfEmployment;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEmploymentSection() {
        return employmentSection;
    }

    public void setEmploymentSection(String employmentSection) {
        this.employmentSection = employmentSection;
    }

    public Date getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(Date terminationDate) {
        this.terminationDate = terminationDate;
    }

    public String getTerminationType() {
        return terminationType;
    }

    public void setTerminationType(String terminationType) {
        this.terminationType = terminationType;
    }

    public Date getDateStartedInPos() {
        return dateStartedInPos;
    }

    public void setDateStartedInPos(Date dateStartedInPos) {
        this.dateStartedInPos = dateStartedInPos;
    }

    public String getMedicalAid() {
        return medicalAid;
    }

    public void setMedicalAid(String medicalAid) {
        this.medicalAid = medicalAid;
    }

    public String getMedicalSchema() {
        return medicalSchema;
    }

    public void setMedicalSchema(String medicalSchema) {
        this.medicalSchema = medicalSchema;
    }

    public int getNumDependancies() {
        return numDependancies;
    }

    public void setNumDependancies(int numDependancies) {
        this.numDependancies = numDependancies;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public boolean isUseCompaniesMedical() {
        return useCompaniesMedical;
    }

    public void setUseCompaniesMedical(boolean useCompaniesMedical) {
        this.useCompaniesMedical = useCompaniesMedical;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBargainCouncilCode() {
        return bargainCouncilCode;
    }

    public void setBargainCouncilCode(String bargainCouncilCode) {
        this.bargainCouncilCode = bargainCouncilCode;
    }

    public boolean isDisability() {
        return disability;
    }

    public void setDisability(boolean disability) {
        this.disability = disability;
    }

    public String getDisabilityType() {
        return disabilityType;
    }

    public void setDisabilityType(String disabilityType) {
        this.disabilityType = disabilityType;
    }

    public String getJobGrade() {
        return jobGrade;
    }

    public void setJobGrade(String jobGrade) {
        this.jobGrade = jobGrade;
    }

    public boolean isWorkShifts() {
        return workShifts;
    }

    public void setWorkShifts(boolean workShifts) {
        this.workShifts = workShifts;
    }

    public String getWorkTimeType() {
        return workTimeType;
    }

    public void setWorkTimeType(String workTimeType) {
        this.workTimeType = workTimeType;
    }

    public String getTypeOfConcern() {
        return typeOfConcern;
    }

    public void setTypeOfConcern(String typeOfConcern) {
        this.typeOfConcern = typeOfConcern;
    }

    public String getVatNo() {
        return vatNo;
    }

    public void setVatNo(String vatNo) {
        this.vatNo = vatNo;
    }

    public String getWebUID() {
        return webUID;
    }

    public void setWebUID(String webUID) {
        this.webUID = webUID;
    }

    public String getAltIdType() {
        return altIdType;
    }

    public void setAltIdType(String altIdType) {
        this.altIdType = altIdType;
    }

    public String getAltIdNumber() {
        return altIdNumber;
    }

    public void setAltIdNumber(String altIdNumber) {
        this.altIdNumber = altIdNumber;
    }

    public String getEquityCode() {
        return equityCode;
    }

    public void setEquityCode(String equityCode) {
        this.equityCode = equityCode;
    }

    public String getSocioEcoStatus() {
        return socioEcoStatus;
    }

    public void setSocioEcoStatus(String socioEcoStatus) {
        this.socioEcoStatus = socioEcoStatus;
    }

    public String getHighestQualification() {
        return highestQualification;
    }

    public void setHighestQualification(String highestQualification) {
        this.highestQualification = highestQualification;
    }

    public String getHomeLanguage() {
        return homeLanguage;
    }

    public void setHomeLanguage(String homeLanguage) {
        this.homeLanguage = homeLanguage;
    }

    public String getCitizenStatus() {
        return citizenStatus;
    }

    public void setCitizenStatus(String citizenStatus) {
        this.citizenStatus = citizenStatus;
    }

    public String getActualOccupation() {
        return actualOccupation;
    }

    public void setActualOccupation(String actualOccupation) {
        this.actualOccupation = actualOccupation;
    }

    public String getRemunerationType() {
        return remunerationType;
    }

    public void setRemunerationType(String remunerationType) {
        this.remunerationType = remunerationType;
    }

    public String getLevelOfExperience() {
        return levelOfExperience;
    }

    public void setLevelOfExperience(String levelOfExperience) {
        this.levelOfExperience = levelOfExperience;
    }
}
