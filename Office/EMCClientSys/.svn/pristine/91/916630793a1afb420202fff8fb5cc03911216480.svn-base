/*
 * employeeform.java
 *
 * Created on 11 December 2007, 08:55
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package emc.forms.base.display.employees;

import emc.app.components.EMCFormComboBox;
import emc.app.components.base.EMCGenderFormDropDown;
import emc.app.components.base.EMCMaritalStatusFormDropDown;
import emc.app.components.dialogs.EMCDialogFactory;
import emc.app.components.documents.EMCDoubleFormDocument;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcpicker.emcdatepicker.EMCDatePickerFormComponent;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.util.utilFunctions;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.language.BaseLanguage;
import emc.entity.hr.HRAlternativeIdType;
import emc.entity.hr.HRBranch;
import emc.entity.hr.HRCitizenStatus;
import emc.entity.hr.HRDisabilityTypes;
import emc.entity.hr.HREmploymentType;
import emc.entity.hr.HREquityCode;
import emc.entity.hr.HRGrade;
import emc.entity.hr.HRLevelOfExperience;
import emc.entity.hr.HRMedicalAid;
import emc.entity.hr.HRNationality;
import emc.entity.hr.HRQualification;
import emc.entity.hr.HRRace;
import emc.entity.hr.HRRemunerantionType;
import emc.entity.hr.HRSection;
import emc.entity.hr.HRSocioEcoStatus;
import emc.entity.hr.HRTerminationType;
import emc.entity.hr.HRUnions;
import emc.enums.base.employees.BaseEmployeeTypeOfConcern;
import emc.enums.base.employees.BaseWorkTimeTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.BaseEmployeeCategoryHistoryMI;
import emc.menus.base.menuItems.display.BaseLanguageMenu;
import emc.menus.hr.menuitems.display.HRAbsenteeismLogMenu;
import emc.menus.hr.menuitems.display.HRAlternativeIdTypeMI;
import emc.menus.hr.menuitems.display.HRBranchMenu;
import emc.menus.hr.menuitems.display.HRCitizenStatusMI;
import emc.menus.hr.menuitems.display.HRDiciplaneryActionMenu;
import emc.menus.hr.menuitems.display.HRDisabilityTypesMenu;
import emc.menus.hr.menuitems.display.HREmployeeDependantsMenu;
import emc.menus.hr.menuitems.display.HREmployeeEducationMenu;
import emc.menus.hr.menuitems.display.HREmployeeTrainingMenu;
import emc.menus.hr.menuitems.display.HREmploymentTypeMenu;
import emc.menus.hr.menuitems.display.HREquityCodeMI;
import emc.menus.hr.menuitems.display.HRGradeMenu;
import emc.menus.hr.menuitems.display.HRGrievencesMenu;
import emc.menus.hr.menuitems.display.HRInternalEmploymentHistoryMenu;
import emc.menus.hr.menuitems.display.HRLeaveLogMenu;
import emc.menus.hr.menuitems.display.HRLevelOfExperienceMI;
import emc.menus.hr.menuitems.display.HRMedicalAidMenu;
import emc.menus.hr.menuitems.display.HRMedicalLogMenu;
import emc.menus.hr.menuitems.display.HRNationalityMenu;
import emc.menus.hr.menuitems.display.HRPreviousEmploymentHistoryMenu;
import emc.menus.hr.menuitems.display.HRQualificationMenu;
import emc.menus.hr.menuitems.display.HRRaceMenu;
import emc.menus.hr.menuitems.display.HRRemunerantionTypeMI;
import emc.menus.hr.menuitems.display.HRSectionMenu;
import emc.menus.hr.menuitems.display.HRSocioEcoStatusMI;
import emc.menus.hr.menuitems.display.HRTerminationTypeMenu;
import emc.menus.hr.menuitems.display.HRUnionsMenu;
import emc.methods.base.ServerBaseMethods;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author rico
 */
public class employeeform extends BaseInternalFrame {

    private emcJPanel overview = new emcJPanel();
    private emcJPanel identification = new emcJPanel();
    private emcJPanel jobDetails = new emcJPanel();
    private emcJPanel contacts = new emcJPanel();
    private emcJPanel costing = new emcJPanel();
    private emcJPanel seta = new emcJPanel();
    private emcJPanel buttonsPanel;
    //id tab
    private emcJLabel employeeIdLabel = new emcJLabel("Employee Number");
    private emcJTextField employeeId = new emcJTextField();
    private emcJLabel surnameLabel = new emcJLabel("Surname");
    private emcJTextField surname = new emcJTextField();
    private emcJLabel fornamesLabel = new emcJLabel("Forenames");
    private emcJTextField fornames = new emcJTextField();
    private emcJLabel knownAsLabel = new emcJLabel("Known As");
    private emcJTextField knownAs = new emcJTextField();
    private emcJLabel idNumberLabel = new emcJLabel("Id Number");
    private emcJTextField idNumber = new emcJTextField();
    private emcJLabel userIDLabel = new emcJLabel("EMC User Id");
    private EMCLookupFormComponent userIDLookup;
    private EMCLookupFormComponent altIdType;
    //Addresstab
    emcJPanel postAdrsPanel = new emcJPanel();
    emcJPanel physAdrsPanel = new emcJPanel();
    private emcJLabel physicalAdrs1Label = new emcJLabel("Address Line1");
    private emcJLabel physicalAdrs2Label = new emcJLabel("Address Line2");
    private emcJLabel physicalAdrs3Label = new emcJLabel("Address Line3");
    private emcJLabel physicalAdrs4Label = new emcJLabel("Address Line4");
    private emcJLabel physicalAdrs5Label = new emcJLabel("Address Line5");
    private emcJTextField physicalAdrs1 = new emcJTextField();
    private emcJTextField physicalAdrs2 = new emcJTextField();
    private emcJTextField physicalAdrs3 = new emcJTextField();
    private emcJTextField physicalAdrs4 = new emcJTextField();
    private emcJTextField physicalAdrs5 = new emcJTextField();
    private emcJLabel postalAdrs1Label = new emcJLabel("Address Line1");
    private emcJLabel postalAdrs2Label = new emcJLabel("Address Line2");
    private emcJLabel postalAdrs3Label = new emcJLabel("Address Line3");
    private emcJLabel postalAdrs4Label = new emcJLabel("Address Line4");
    private emcJLabel postalAdrs5Label = new emcJLabel("Address Line5");
    private emcJTextField postalAdrs1 = new emcJTextField();
    private emcJTextField postalAdrs2 = new emcJTextField();
    private emcJTextField postalAdrs3 = new emcJTextField();
    private emcJTextField postalAdrs4 = new emcJTextField();
    private emcJTextField postalAdrs5 = new emcJTextField();
    private emcJLabel physPostCodeLabel = new emcJLabel("Postal Code");
    private emcJLabel postCodeLabel = new emcJLabel("Postal Code");
//    private emcJTextField postCode = new emcJTextField();
    private EMCLookupFormComponent postCodeLookup;
//    private emcJTextField physPostCode = new emcJTextField();
    private EMCLookupFormComponent physPostCodeLookup;
    //costing
    private emcJLabel sellHrLabel = new emcJLabel("Sell/Hr");
    private emcJTextField sellHr = new emcJTextField();
    private emcJLabel costHrLabel = new emcJLabel("Cost/Hr");
    private emcJTextField costHr = new emcJTextField();
    //Job tab
    private emcJLabel departmentLabel = new emcJLabel("Department");
    //private emcJTextField department = new emcJTextField();
    private EMCLookupFormComponent departmentLookup;
    private emcJLabel jobTitleLabel = new emcJLabel("Job Title");
    private emcJLabel actualOccupation = new emcJLabel("Actual Occupation");
    //private emcJTextField jobTitle = new emcJTextField();
    private EMCLookupFormComponent jobTitleLookup;
    private emcJTextField txtActualOccupation = new emcJTextField();
    private emcJLabel managerLabel = new emcJLabel("Manager");
    private EMCLookupFormComponent manager;
    private emcJLabel employeeTypeLabel = new emcJLabel("Employee Type");
    //private emcJTextField employeeType = new emcJTextField();
    private EMCLookupFormComponent employeeTypeLookup;
    //Contacts Tab
    private emcJLabel emailAdrsLabel = new emcJLabel("Email Address");
    private emcJTextField emailAdrs = new emcJTextField();
    private emcJLabel internalPhoneLabel = new emcJLabel("Internal Phone");
    private emcJTextField internalPhone = new emcJTextField();
    private emcJLabel faxNoLabel = new emcJLabel("Fax No");
    private emcJTextField faxNo = new emcJTextField();
    private emcJLabel cellPhoneLabel = new emcJLabel("Cell Phone");
    private emcJTextField cellPhone = new emcJTextField();
    private emcJLabel homeNumberLabel = new emcJLabel("Home No");
    private emcJTextField homeNumber = new emcJTextField();
    //DataSource
    private employeeDataRelation dataRelation;
    //Copy of user data
    EMCUserData copyUD;

    public employeeform(EMCUserData userData) {
        super("Employees", true, true, true, true, userData);
        this.setBounds(20, 20, 730, 500);
        this.setHelpFile(new emcHelpFile("Base/Employees.html"));
        try {
            //Creates a copy of the given user data
            copyUD = userData.copyUserData();
            dataRelation = new employeeDataRelation(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("employeeNumber");
            dataRelation.setFormTextId2("surname");
            setDocuments();
            initFrame();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                System.out.println("Failed to create dataSource for employee table." + e.getMessage());
            }
        }

    }

    private void setDocuments() {
        //id lookup
        userIDLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.users(),
                dataRelation, "userId");
        List userkeys = new ArrayList();
        userkeys.add("userId");
        userkeys.add("userName");
        EMCLookupPopup userIDPopUp = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.Usertable(),
                "userId", userkeys, copyUD);
        userIDLookup.setPopup(userIDPopUp);

        //Alt Type Id

//        emcJTextField txtPhysicalStreetAdress = new emcJTextField(new EMCStringFormDocument(dataManager, "physicalAddress1"));
//        EMCLookupFormComponent lkpPhysicalSuburb = new EMCLookupFormComponent(new PostalCodes(), dataManager, "physicalAddress2");
//        lkpPhysicalSuburb.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "suburb", dataManager, fieldMap, userData));


        altIdType = new EMCLookupFormComponent(new HRAlternativeIdTypeMI(),
                dataRelation, "altIdType");
        altIdType.setPopup(new EMCLookupPopup(new HRAlternativeIdType(), "altIdType", copyUD));

        //Postal code lookups
        physPostCodeLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "addressPhysPostalCode");

        postCodeLookup = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.PostalCodes(),
                dataRelation, "postalCode");

        List postalCodeKeys = new ArrayList();
        postalCodeKeys.add("code");
        postalCodeKeys.add("suburb");

        EMCLookupPopup postCodePopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BasePostalCodes(),
                "code", postalCodeKeys, copyUD);
        physPostCodeLookup.setPopup(postCodePopup);
        postCodeLookup.setPopup(postCodePopup);

        //Department lookup
        departmentLookup = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.Departments(),
                dataRelation, "department");
        List departmentKeys = new ArrayList();
        departmentKeys.add("department");
        departmentKeys.add("description");
        EMCLookupPopup departmentPopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowDepartment(),
                "department", departmentKeys, copyUD);
        departmentLookup.setPopup(departmentPopup);

        //Job title lookup
        jobTitleLookup = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.JobTitles(),
                dataRelation, "jobTitle");
        List jobTitleKeys = new ArrayList();
        jobTitleKeys.add("jobTitle");
        jobTitleKeys.add("description");
        EMCLookupPopup jobTitlePopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowJobTitles(),
                "jobTitle", jobTitleKeys, copyUD);
        jobTitleLookup.setPopup(jobTitlePopup);

        //Manager lookup
        manager = new EMCLookupFormComponent(new emc.menus.base.menuItems.display.employees(),
                dataRelation, "manager");
        List employeeKeys = new ArrayList();
        employeeKeys.add("employeeNumber");
        employeeKeys.add("surname");
        employeeKeys.add("forenames");
        employeeKeys.add("emailAddress");
        employeeKeys.add("internalPhoneNumber");
        EMCLookupPopup managerPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseEmployeeTable(),
                "employeeNumber", employeeKeys, copyUD);
        manager.setPopup(managerPopup);

        //Employee type lookup
        employeeTypeLookup = new EMCLookupFormComponent(new emc.menus.workflow.menuitems.display.EmployeeTypes(),
                dataRelation, "employeeType");
        List employeeTypeKeys = new ArrayList();
        employeeTypeKeys.add("employeeType");
        employeeTypeKeys.add("description");
        EMCLookupPopup empTypePopup = new EMCLookupPopup(enumEMCModules.WORKFLOW.getId(), new emc.entity.workflow.WorkFlowEmployeeType(),
                "employeeType", employeeTypeKeys, copyUD);
        employeeTypeLookup.setPopup(empTypePopup);

        this.physicalAdrs1.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine1"));
        this.physicalAdrs2.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine2"));
        this.physicalAdrs3.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine3"));
        this.physicalAdrs4.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine4"));
        this.physicalAdrs5.setDocument(new EMCStringFormDocument(dataRelation, "addressPhysicalLine5"));
        this.postalAdrs1.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine1"));
        this.postalAdrs2.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine2"));
        this.postalAdrs3.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine3"));
        this.postalAdrs4.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine4"));
        this.postalAdrs5.setDocument(new EMCStringFormDocument(dataRelation, "postalAddressLine5"));
        this.emailAdrs.setDocument(new EMCStringFormDocument(dataRelation, "emailAddress"));
        this.internalPhone.setDocument(new EMCStringFormDocument(dataRelation, "internalPhoneNumber"));
        this.faxNo.setDocument(new EMCStringFormDocument(dataRelation, "faxNumber"));
        this.cellPhone.setDocument(new EMCStringFormDocument(dataRelation, "cellPhoneNumber"));
        this.homeNumber.setDocument(new EMCStringFormDocument(dataRelation, "homePhoneNumber"));
        this.sellHr.setDocument(new EMCDoubleFormDocument(dataRelation, "sellingPricePerHour"));
        this.costHr.setDocument(new EMCDoubleFormDocument(dataRelation, "costPricePerHour"));
        this.employeeId.setDocument(new EMCStringFormDocument(dataRelation, "employeeNumber"));
        this.surname.setDocument(new EMCStringFormDocument(dataRelation, "surname"));
        this.fornames.setDocument(new EMCStringFormDocument(dataRelation, "forenames"));
        this.knownAs.setDocument(new EMCStringFormDocument(dataRelation, "knownAs"));
        this.txtActualOccupation.setDocument(new EMCStringFormDocument(dataRelation, "actualOccupation"));

        EMCStringFormDocument idDoc = new EMCStringFormDocument(dataRelation, "employeeID");
        this.idNumber.setDocument(idDoc);
    }

    private void tabOverview() {
        List keys = new ArrayList();
        keys.add("employeeNumber");
        keys.add("surname");
        keys.add("forenames");
        keys.add("emailAddress");
        keys.add("internalPhoneNumber");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        overview.setLayout(new GridLayout(1, 1));
        overview.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void tabIdentification() {
        identification.setLayout(new GridBagLayout());
        identification.setBorder(javax.swing.BorderFactory.createTitledBorder("Identification"));
        int y = 0;
        GridBagConstraints localg;
        //employeeid
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(this.employeeIdLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(this.employeeId, localg);
        //Surname
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(this.surnameLabel, localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 1, y, 0.9, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        identification.add(this.surname, localg);
        //Forenames
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(this.fornamesLabel, localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 1, y, 0.9, GridBagConstraints.LINE_START,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        identification.add(this.fornames, localg);
        //Initials
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel("Initials"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(new emcJTextField(new EMCStringFormDocument(dataRelation, "initials")), localg);
        //Known As
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(this.knownAsLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(this.knownAs, localg);
        //id nr
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(this.idNumberLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(this.idNumber, localg);
        //Alt Id Type
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel("Alt. ID Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(this.altIdType, localg);
        //Alt Id Number
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel("Alt. ID Number"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(new emcJTextField(new EMCStringFormDocument(dataRelation, "altIdNumber")), localg);
        //tax nr
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel("Tax No"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(new emcJTextField(new EMCStringFormDocument(dataRelation, "taxNumber")), localg);
        //user id
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(this.userIDLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(this.userIDLookup, localg);
        //Clock Number
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel("Clock Number"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(new emcJTextField(new EMCStringFormDocument(dataRelation, "clockNumber")), localg);
        //Blank space
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel(), localg);
        //Type of concern
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel(dataRelation.getColumnName("typeOfConcern")), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);

        EMCFormComboBox cmbTypeOfConcern = new EMCFormComboBox(BaseEmployeeTypeOfConcern.values(), dataRelation, "typeOfConcern");
        identification.add(cmbTypeOfConcern, localg);
        //Type of concern
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        identification.add(new emcJLabel(dataRelation.getColumnName("vatNo")), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        identification.add(new emcJTextField(new EMCStringFormDocument(dataRelation, "vatNo")), localg);
        //
        y++;
        identification.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));

    }

    private void tabJobDetails() {
        jobDetails.setLayout(new GridBagLayout());
        jobDetails.setBorder(javax.swing.BorderFactory.createTitledBorder("Job Details"));
        int y = 0;
        GridBagConstraints localg;
        //Date Of Employment
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Employment Date"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(new EMCDatePickerFormComponent(dataRelation, "dateOfEmployment"), localg);
        //Termination Date
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        jobDetails.add(new emcJLabel("Termination Date"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        jobDetails.add(new EMCDatePickerFormComponent(dataRelation, "terminationDate"), localg);
        //Type Of Employment
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Employment Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        EMCLookupFormComponent lkpTypeOfEmployment = new EMCLookupFormComponent(new HREmploymentTypeMenu(), dataRelation, "typeOfEmployment");
        lkpTypeOfEmployment.setPopup(new EMCLookupPopup(new HREmploymentType(), "remunerationType", copyUD));
        jobDetails.add(lkpTypeOfEmployment, localg);
        //Remuneration
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Remuneration Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        EMCLookupFormComponent lkpRemunerationType = new EMCLookupFormComponent(new HRRemunerantionTypeMI(), dataRelation, "remunerationType");
        lkpRemunerationType.setPopup(new EMCLookupPopup(new HRRemunerantionType(), "type", copyUD));
        jobDetails.add(lkpRemunerationType, localg);
        //Termination Type
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        jobDetails.add(new emcJLabel("Termination Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        EMCLookupFormComponent lkpTerminationType = new EMCLookupFormComponent(new HRTerminationTypeMenu(), dataRelation, "terminationType");
        lkpTerminationType.setPopup(new EMCLookupPopup(new HRTerminationType(), "terminationType", copyUD));
        jobDetails.add(lkpTerminationType, localg);
        //Department
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(this.departmentLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(this.departmentLookup, localg);
        //Work Tyme Type
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        jobDetails.add(new emcJLabel("Work Time Type"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        jobDetails.add(new EMCFormComboBox(BaseWorkTimeTypes.values(), dataRelation, "workTimeType"), localg);
        //Branch
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Branch"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        EMCLookupFormComponent lkpBranch = new EMCLookupFormComponent(new HRBranchMenu(), dataRelation, "branch");
        lkpBranch.setPopup(new EMCLookupPopup(new HRBranch(), "branch", copyUD));
        jobDetails.add(lkpBranch, localg);
        //Shift Work
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        jobDetails.add(new emcJLabel("Shift Work"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        jobDetails.add(new emcYesNoComponent(dataRelation, "workShifts"), localg);
        //Section
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Section"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        EMCLookupFormComponent lkpSection = new EMCLookupFormComponent(new HRSectionMenu(), dataRelation, "employmentSection");
        lkpSection.setPopup(new EMCLookupPopup(new HRSection(), "sectionId", copyUD));
        jobDetails.add(lkpSection, localg);
        //Job Title
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(this.jobTitleLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(this.jobTitleLookup, localg);
        //Level of Experience
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Level of Exp"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        EMCLookupFormComponent lkpLevelExp = new EMCLookupFormComponent(new HRLevelOfExperienceMI(), dataRelation, "levelOfExperience");
        lkpLevelExp.setPopup(new EMCLookupPopup(new HRLevelOfExperience(), "levelOfExp", copyUD));
        jobDetails.add(lkpLevelExp, localg);
        //Actual Occupation
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(this.actualOccupation, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(this.txtActualOccupation, localg);
        //manager
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(this.managerLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(this.manager, localg);
        //employee Type
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(this.employeeTypeLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(this.employeeTypeLookup, localg);
        //Employee Category
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Employee Category"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        //Grade
        EMCLookupFormComponent lkpGrade = new EMCLookupFormComponent(new HRGradeMenu(), dataRelation, "jobGrade");
        lkpGrade.setPopup(new EMCLookupPopup(new HRGrade(), "grade", copyUD));
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        jobDetails.add(new emcJLabel("Grade"), localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        jobDetails.add(lkpGrade, localg);
        //End
        y++;
        jobDetails.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private emcJPanel tabSeta() {
        final EMCLookupFormComponent lkpDisabilityType = new EMCLookupFormComponent(new HRDisabilityTypesMenu(), dataRelation, "disabilityType");
        lkpDisabilityType.setPopup(new EMCLookupPopup(new HRDisabilityTypes(), "type", copyUD));
        emcYesNoComponent ynDisability = new emcYesNoComponent(dataRelation, "disability") {

            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if ("Yes".equals(anObject)) {
                    lkpDisabilityType.setEnabled(true);
                } else if ("No".equals(anObject)) {
                    lkpDisabilityType.setEnabled(false);
                }
            }
        };

        EMCLookupFormComponent lkpEquityCode = new EMCLookupFormComponent(new HREquityCodeMI(), dataRelation, "equityCode");
        List<String> lookupFields = new ArrayList<String>();
        lookupFields.add("equityCode");
        lookupFields.add("description");
        lkpEquityCode.setPopup(new EMCLookupPopup(new HREquityCode(), "equityCode", lookupFields, copyUD));

        EMCLookupFormComponent lkpSocioEconStatus = new EMCLookupFormComponent(new HRSocioEcoStatusMI(), dataRelation, "socioEcoStatus");
        lkpSocioEconStatus.setPopup(new EMCLookupPopup(new HRSocioEcoStatus(), "socioEcoStatus", copyUD));

        EMCLookupFormComponent lkpHigestQualification = new EMCLookupFormComponent(new HRQualificationMenu(), dataRelation, "highestQualification");
        lkpHigestQualification.setPopup(new EMCLookupPopup(new HRQualification(), "qualification", copyUD));

        Component[][] comp = {{new emcJLabel("Disability"), ynDisability},
            {new emcJLabel("Disability Status"), lkpDisabilityType},
            {new emcJLabel("Equity Code"), lkpEquityCode},
            {new emcJLabel("Socio Econ Status"), lkpSocioEconStatus},
            {new emcJLabel("Highest Qualification"), lkpHigestQualification}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "SETA");
    }

    private void tabContacts() {
        contacts.setLayout(new GridBagLayout());
        contacts.setBorder(javax.swing.BorderFactory.createTitledBorder("Contacts"));
        int y = 0;
        GridBagConstraints localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);

        //Postal address panel
        int panelY = 0;
        GridBagConstraints panelg;
        postAdrsPanel.setLayout(new GridBagLayout());

        //Empty space
        panelg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(new emcJLabel(), panelg);

        //Postal Address Label
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(new emcJLabel("Postal Address"), panelg);

        //Line 1
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.postalAdrs1Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(postalAdrs1, panelg);

        //Line 2
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.postalAdrs2Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(postalAdrs2, panelg);

        //Line 3
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.postalAdrs3Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(postalAdrs3, panelg);

        //Line 4
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.postalAdrs4Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(postalAdrs4, panelg);

        //Line 5
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.postalAdrs5Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        postAdrsPanel.add(postalAdrs5, panelg);

        //Postal code
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        postAdrsPanel.add(this.postCodeLabel, panelg);
        panelg = emcSetGridBagConstraints.createStandard(1, panelY, 0.1, GridBagConstraints.LINE_START);
        postCodeLookup.setPreferredSize(new java.awt.Dimension(80, 25));
        postAdrsPanel.add(postCodeLookup, panelg);

        //Physical address panel
        panelY = 0;
        physAdrsPanel.setLayout(new GridBagLayout());

        //Empty space
        panelg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(new emcJLabel(), panelg);

        //Postal Address Label
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(new emcJLabel("Physical Address"), panelg);

        //Line 1
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.physicalAdrs1Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(physicalAdrs1, panelg);

        //Line 2
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.physicalAdrs2Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(physicalAdrs2, panelg);

        //Line 3
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.physicalAdrs3Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(physicalAdrs3, panelg);

        //Line 4
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.physicalAdrs4Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(physicalAdrs4, panelg);

        //Line 5
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.physicalAdrs5Label, panelg);
        panelg = emcSetGridBagConstraints.changePosPlusFill(panelg, 1, panelY, 0.1, GridBagConstraints.LINE_END,
                GridBagConstraints.HORIZONTAL, GridBagConstraints.REMAINDER);
        physAdrsPanel.add(physicalAdrs5, panelg);

        //Postal code
        panelY++;
        panelg = emcSetGridBagConstraints.createStandard(0, panelY, 0.1, GridBagConstraints.LINE_START);
        physAdrsPanel.add(this.physPostCodeLabel, panelg);
        panelg = emcSetGridBagConstraints.createStandard(1, panelY, 0.1, GridBagConstraints.LINE_START);
        physPostCodeLookup.setPreferredSize(new java.awt.Dimension(80, 25));
        physAdrsPanel.add(physPostCodeLookup, panelg);

        //Add the panels
        y = 0;
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 0, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.NONE, 2);
        contacts.add(this.postAdrsPanel, localg);
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 2, y, 0.1, GridBagConstraints.FIRST_LINE_START,
                GridBagConstraints.NONE, 2);
        contacts.add(this.physAdrsPanel, localg);


        y++;
        localg = emcSetGridBagConstraints.changePosPlusFill(localg, 0, y, 0.1, GridBagConstraints.LINE_START,
                GridBagConstraints.NONE, 1);
        contacts.add(new emcJLabel(), localg);
        y++;
        localg = emcSetGridBagConstraints.changePosition(localg, 0, y, 0.1);
        contacts.add(new emcJLabel(), localg);

        //Email Address
        y++;
        localg = emcSetGridBagConstraints.changePosition(localg, 0, y, 0.1);
        contacts.add(this.emailAdrsLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        contacts.add(this.emailAdrs, localg);
        //int phone
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        contacts.add(this.internalPhoneLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        contacts.add(this.internalPhone, localg);
        //fax no
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        contacts.add(this.faxNoLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        contacts.add(this.faxNo, localg);
        //cell phone
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.9);
        contacts.add(this.cellPhoneLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 3, y, 0.9);
        contacts.add(this.cellPhone, localg);
        //home nr
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        contacts.add(this.homeNumberLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        contacts.add(this.homeNumber, localg);
        //
        y++;
        contacts.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));

    }

    private void tabCosting() {
        costing.setLayout(new GridBagLayout());
        costing.setBorder(javax.swing.BorderFactory.createTitledBorder("Costing"));
        int y = 0;
        GridBagConstraints localg;

        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costing.add(this.sellHrLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costing.add(this.sellHr, localg);
        //costHr
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.LINE_START);
        costing.add(this.costHrLabel, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.9);
        costing.add(this.costHr, localg);
        //
        y++;
        costing.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
    }

    private emcJPanel tabPersonal() {
        emcJTextField txtPassportNumber = new emcJTextField(new EMCStringFormDocument(dataRelation, "passportNumber"));
        EMCLookupFormComponent lkpNationality = new EMCLookupFormComponent(new HRNationalityMenu(), dataRelation, "nationality");
        lkpNationality.setPopup(new EMCLookupPopup(new HRNationality(), "nationality", copyUD));
        EMCDatePickerFormComponent dpDateOfBirth = new EMCDatePickerFormComponent(dataRelation, "dateOfBirth");
        EMCGenderFormDropDown genderDropDown = new EMCGenderFormDropDown(dataRelation, "gender");
        EMCLookupFormComponent lkpRace = new EMCLookupFormComponent(new HRRaceMenu(), dataRelation, "race");
        lkpRace.setPopup(new EMCLookupPopup(new HRRace(), "race", copyUD));
        EMCMaritalStatusFormDropDown maritalStatusDropDown = new EMCMaritalStatusFormDropDown(dataRelation, "maritalStatus");
        emcJTextField txtSpouse = new emcJTextField(new EMCStringFormDocument(dataRelation, "spouse"));
        EMCLookupFormComponent lkpLanguage = new EMCLookupFormComponent(new BaseLanguageMenu(), dataRelation, "homeLanguage");
        lkpLanguage.setPopup(new EMCLookupPopup(new BaseLanguage(), "languageId", copyUD));

        EMCLookupFormComponent lkpCitizenStatus = new EMCLookupFormComponent(new HRCitizenStatusMI(), dataRelation, "citizenStatus");
        List<String> lookupFields = new ArrayList<String>();
        lookupFields.add("citizenStatus");
        lookupFields.add("description");
        lkpCitizenStatus.setPopup(new EMCLookupPopup(new HRCitizenStatus(), "citizenStatus", lookupFields, copyUD));

        Component[][] comp = {{new emcJLabel("Date Of Birth"), dpDateOfBirth},
            {new emcJLabel("Gender"), genderDropDown},
            {new emcJLabel("Race"), lkpRace},
            {new emcJLabel("Home Language"), lkpLanguage},
            {new emcJLabel("Marital Status"), maritalStatusDropDown},
            {new emcJLabel("Spouse"), txtSpouse},
            {new emcJLabel("Nationality"), lkpNationality},
            {new emcJLabel("Citizen Status"), lkpCitizenStatus},
            {new emcJLabel("Passport No"), txtPassportNumber}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Personal");
    }

    private emcJPanel tabMember() {
        EMCLookupFormComponent lkpMedicalAid = new EMCLookupFormComponent(new HRMedicalAidMenu(), dataRelation, "medicalAid");
        lkpMedicalAid.setPopup(new EMCLookupPopup(new HRMedicalAid(), "medicalAid", copyUD));
        emcJTextField txtSchema = new emcJTextField(new EMCStringFormDocument(dataRelation, "medicalSchema"));
        emcJTextField txtAccount = new emcJTextField(new EMCStringFormDocument(dataRelation, "accountNo"));
        emcJTextField txtNumDependancies = new emcJTextField(new EMCIntegerFormDocument(dataRelation, "numDependancies"));
        emcYesNoComponent ynUseCompanyMedical = new emcYesNoComponent(dataRelation, "useCompaniesMedical");
        EMCLookupFormComponent lkpUnion = new EMCLookupFormComponent(new HRUnionsMenu(), dataRelation, "unionId");
        lkpUnion.setPopup(new EMCLookupPopup(new HRUnions(), "unionId", copyUD));
        emcJTextField txtBargainCouncilNo = new emcJTextField(new EMCStringFormDocument(dataRelation, "bargainCouncilCode"));
        Component[][] comp = {{new emcJLabel("Medical Aid"), lkpMedicalAid, new emcJLabel("Union"), lkpUnion},
            {new emcJLabel("Scheme"), txtSchema, new emcJLabel("Bargain Council No"), txtBargainCouncilNo},
            {new emcJLabel("Membership No"), txtAccount},
            {new emcJLabel("Use Company's"), ynUseCompanyMedical},
            {new emcJLabel("No. Dependants"), txtNumDependancies}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Member");
    }

    private void initButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        //Absent Button
        emcMenuButton btnAbsent = new emcMenuButton("Absent", new HRAbsenteeismLogMenu(), this, 14, false);
        buttonList.add(btnAbsent);
        //Leave Button
        emcMenuButton btnLeave = new emcMenuButton("Leave", new HRLeaveLogMenu(), this, 3, false);
        buttonList.add(btnLeave);
        //Skills
        emcMenuButton btnSkills = new emcMenuButton("Skills", new emc.menus.workflow.menuitems.display.employeeSkills(), this, 0, false);
        buttonList.add(btnSkills);
        //Educ & Train
        emcMenuButtonList btnEducTrain = new emcMenuButtonList("Educ & Train", this);
        btnEducTrain.addMenuItem("Education", new HREmployeeEducationMenu(), 7, false);
        btnEducTrain.addMenuItem("Training", new HREmployeeTrainingMenu(), 9, false);
        btnEducTrain.addMenuItem("Category", new BaseEmployeeCategoryHistoryMI(), 16, false);
        buttonList.add(btnEducTrain);
        //Actions
        emcMenuButtonList btnActions = new emcMenuButtonList("Actions", this);
        btnActions.addMenuItem("Disciplinary", new HRDiciplaneryActionMenu(), 5, false);
        btnActions.addMenuItem("Grievances", new HRGrievencesMenu(), 6, false);
        buttonList.add(btnActions);
        //Emp History
        emcMenuButtonList btnEmpHistory = new emcMenuButtonList("Emp History", this);
        //btnEmpHistory.addMenuItem("Allocation", new EngineeringOperationAllocationHistoryMenu(), 2, false);
        btnEmpHistory.addMenuItem("Internal", new HRInternalEmploymentHistoryMenu(), 10, false);
        btnEmpHistory.addMenuItem("Previous", new HRPreviousEmploymentHistoryMenu(), 11, false);
        buttonList.add(btnEmpHistory);
        //Dependants
        emcMenuButton btnDependants = new emcMenuButton("Dependants", new HREmployeeDependantsMenu(), this, 12, false);
        buttonList.add(btnDependants);
        //MedicalLog
        emcMenuButton btnMedicalLog = new emcMenuButton("Medical Log", new HRMedicalLogMenu(), this, 13, false);
        buttonList.add(btnMedicalLog);
        //MedicalLog
        emcJButton btnWeb = new emcJButton("Web User") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if ((Long) dataRelation.getLastFieldValueAt("recordID") == 0d) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected record has not been saved yet.", copyUD);
                    return;
                }
                if (Functions.checkBlank(dataRelation.getLastFieldValueAt("emailAddress"))) {
                    Logger.getLogger("emc").log(Level.SEVERE, "An email address is required for a web user.", copyUD);
                    return;
                }
                if (EMCDialogFactory.createQuestionDialog(utilFunctions.findEMCDesktop(this), "Create Web User", "Are you sure you want to create a web user for the selected employee?") == JOptionPane.YES_OPTION) {
                    EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.CREATE_WEB_USER_FOR_EMP);

                    List toSend = new ArrayList();
                    toSend.add(dataRelation.getRowCache(dataRelation.getLastRowAccessed()));

                    toSend = EMCWSManager.executeGenericWS(cmd, toSend, copyUD);
                    if (toSend.size() > 1 && toSend.get(1) instanceof Boolean && (Boolean) toSend.get(1)) {
                        Logger.getLogger("emc").log(Level.INFO, "Web user created for the employee.", dataRelation.getUserData());
                        dataRelation.refreshRecord(dataRelation.getLastRowAccessed());
                    } else {
                        Logger.getLogger("emc").log(Level.SEVERE, "Failed to create a web user for the employee.", dataRelation.getUserData());
                    }
                }
            }
        };
        buttonList.add(btnWeb);

        buttonsPanel = emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        int y = 0;
        GridBagConstraints localg;
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.setTabLayoutPolicy(emcJTabbedPane.SCROLL_TAB_LAYOUT);
        initButtons();
        tabOverview();
        tabIdentification();
        tabJobDetails();
        tabContacts();
        tabCosting();
        tabbedPanetop.addTab("Overview", overview);
        tabbedPanetop.addTab("Identification", identification);
        tabbedPanetop.addTab("Personal", tabPersonal());
        tabbedPanetop.addTab("Job Details", jobDetails);
        tabbedPanetop.addTab("SETA", tabSeta());
        tabbedPanetop.addTab("Contacts", contacts);
        tabbedPanetop.addTab("Costing", costing);
        tabbedPanetop.addTab("Member", tabMember());
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.99, GridBagConstraints.FIRST_LINE_START);
        localg.weighty = 1.0;
        localg.fill = GridBagConstraints.BOTH;
        thePanel.add(tabbedPanetop, localg);
        localg = emcSetGridBagConstraints.createStandard(1, y, 0.01, GridBagConstraints.FIRST_LINE_START);
        //localg.weighty = 1.0;
        localg.fill = GridBagConstraints.BOTH;
        thePanel.add(buttonsPanel, localg);
        this.add(thePanel);
    }
}
