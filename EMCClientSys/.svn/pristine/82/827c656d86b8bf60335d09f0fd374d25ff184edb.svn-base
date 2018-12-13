/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.parameters;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJPasswordField;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.base.BaseParameters;
import emc.entity.base.calendar.BaseCalendar;
import emc.enums.base.sms.SMSFileOutputFields;
import emc.enums.base.sms.SMSSendingOptions;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.CalendarMenu;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;
    private EMCUserData userData;

    public BaseParametersForm(EMCUserData userData) {
        super("Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 650);
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseParameters(), userData), userData);
            dataManager.setDummyForm(this);
            this.setDataManager(dataManager);
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Process", formPathPane());
        tabbed.add("Calendar", calendarPane());
        tabbed.add("FTP", ftpPane());
        tabbed.add("Email/SMS", smsPane());
        tabbed.add("Rollback", transactionRollback());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcJPanel formPathPane() {
        emcJTextField txtMaxMultiProcessingThreads = new emcJTextField(new EMCIntegerFormDocument(dataManager, "maxMultiProcessingThread"));
        emcJTextField txtMaxMultiProcessingThreadsPerClient = new emcJTextField(new EMCIntegerFormDocument(dataManager, "reservePerClient"));
        Component[][] comp = {{new emcJLabel("Total Multi Processing Threads"), txtMaxMultiProcessingThreads},
            {new emcJLabel("Multi Processing Threads Per Client"), txtMaxMultiProcessingThreadsPerClient}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnReset = new emcJButton("Reset") {
            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.RESET_MULTI_PROCESSING_VARIABLES);
                EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), userData);
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnReset);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    private emcJPanel calendarPane() {

        EMCFormComboBox cbFirstDayOfWeek = new FirstDayOfWeekComboBox(dataManager, "firstDayOfWeek");

        emcJTextField txtMinDaysInFirstWeek = new emcJTextField(new EMCIntegerFormDocument(dataManager, "minDaysInFirstWeek"));

        EMCLookupFormComponent lkpStandardCalander = new EMCLookupFormComponent(new CalendarMenu(), dataManager, "standardCalendarId");
        lkpStandardCalander.setPopup(new EMCLookupPopup(new BaseCalendar(), "calendarId", userData));

        EMCLookupFormComponent lkpSalesStandardCalander = new EMCLookupFormComponent(new CalendarMenu(), dataManager, "salesStandardCalendarId");
        lkpSalesStandardCalander.setPopup(new EMCLookupPopup(new BaseCalendar(), "calendarId", userData));

        EMCLookupFormComponent lkpProductionStandardCalander = new EMCLookupFormComponent(new CalendarMenu(), dataManager, "productionStandardCalendarId");
        lkpProductionStandardCalander.setPopup(new EMCLookupPopup(new BaseCalendar(), "calendarId", userData));

        Component[][] comp = {
            {new emcJLabel("First Day Of The Week"), cbFirstDayOfWeek},
            {new emcJLabel("Minimum Days In First Week"), txtMinDaysInFirstWeek},
            {new emcJLabel()},
            {new emcJLabel("Standard Company Calendar"), lkpStandardCalander},
            {new emcJLabel()},
            {new emcJLabel("Standard Production Calendar"), lkpProductionStandardCalander},
            {new emcJLabel()},
            {new emcJLabel("Standard Sales Calendar"), lkpSalesStandardCalander}};


        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Calendar");
    }

    private emcJPanel transactionRollback() {
        emcJTextField txtAsAdmin = new emcJTextField(new EMCStringFormDocument(dataManager, "asadminPath"));
        emcJTextField txtFullPassfile = new emcJTextField(new EMCStringFormDocument(dataManager, "fullPassFilePath"));
        emcYesNoComponent ynMonitorTransaction = new emcYesNoComponent(dataManager, "monitorTransactions");
        emcJTextField txtKeepTransactionDays = new emcJTextField(new EMCIntegerFormDocument(dataManager, "keepTransactionDays"));

        Component[][] comp = {{new emcJLabel("Full asadmin path"), txtAsAdmin},
            {new emcJLabel("Full path to password file"), txtFullPassfile},
            {new emcJLabel("Monitor Transactions"), ynMonitorTransaction},
            {new emcJLabel("Keep detail for days"), txtKeepTransactionDays}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Transactions");
    }

    private emcJPanel ftpPane() {
        final emcJTextField txtRootDirectoryPath = new emcJTextField(new EMCStringFormDocument(dataManager, "rootDirectoryPath"));
        final emcJTextField txtServer = new emcJTextField(new EMCStringFormDocument(dataManager, "ftpServer"));
        final emcJTextField txtUserName = new emcJTextField(new EMCStringFormDocument(dataManager, "ftpuserName"));
        final emcJPasswordField txtPassword = new emcJPasswordField(new EMCStringFormDocument(dataManager, "ftpPassword"));
        emcYesNoComponent ynUseRemoteFTP = new emcYesNoComponent(dataManager, "useRemoteFTP") {
            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if (anObject.equals("Yes")) {
                    txtRootDirectoryPath.setEditable(false);

                    txtServer.setEditable(true);
                    txtUserName.setEditable(true);
                    txtPassword.setEditable(true);
                } else {
                    txtRootDirectoryPath.setEditable(true);

                    txtServer.setEditable(false);
                    txtUserName.setEditable(false);
                    txtPassword.setEditable(false);
                }
            }
        };

        Component[][] comp = {{new emcJLabel("Use Remote FTP"), ynUseRemoteFTP},
            {new emcJLabel("Root File Directory"), txtRootDirectoryPath},
            {new emcJLabel("Server"), txtServer},
            {new emcJLabel("User Name"), txtUserName},
            {new emcJLabel("Password"), txtPassword}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "FTP");
    }

    private emcJPanel smsPane() {
        emcJTextField txtMaxSMSEmailAllowed = new emcJTextField(new EMCStringFormDocument(dataManager, "maxAllowedSMSEmailWithoutPassword"));
        emcJPasswordField txtSMSEmailApprovalPassword = new emcJPasswordField(new EMCStringFormDocument(dataManager, "smsEmailApprovalPassword"));
        
        emcYesNoComponent ynTest = new emcYesNoComponent(dataManager, "testSMS");
        final emcJTextField txtEmail = new emcJTextField(new EMCStringFormDocument(dataManager, "smsEmail"));
        final EMCFormComboBox cbFileOutput1 = new EMCFormComboBox(SMSFileOutputFields.values(), dataManager, "smsFileOutputField1");
        final EMCFormComboBox cbFileOutput2 = new EMCFormComboBox(SMSFileOutputFields.values(), dataManager, "smsFileOutputField2");
        final EMCFormComboBox cbFileOutput3 = new EMCFormComboBox(SMSFileOutputFields.values(), dataManager, "smsFileOutputField3");
        final emcJTextField txtURL = new emcJTextField(new EMCStringFormDocument(dataManager, "smsURL"));
        final emcJTextField numberURL = new emcJTextField(new EMCStringFormDocument(dataManager, "smsNumberURLParm"));
        final emcJTextField msgURL = new emcJTextField(new EMCStringFormDocument(dataManager, "smsMessageURLParm"));
        EMCFormComboBox cbSendingOption = new EMCFormComboBox(SMSSendingOptions.values(), dataManager, "smsSendingOption") {
            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if (SMSSendingOptions.EMAIL_SENDING.toString().equals(anObject)) {
                    txtEmail.setEditable(true);
                    cbFileOutput1.setEnabled(false);
                    cbFileOutput2.setEnabled(false);
                    cbFileOutput3.setEnabled(false);
                    txtURL.setEnabled(false);
                    numberURL.setEnabled(false);
                    msgURL.setEnabled(false);
                } else if (SMSSendingOptions.FILE_SENDING.toString().equals(anObject)) {
                    txtEmail.setEditable(false);
                    txtURL.setEnabled(false);
                    numberURL.setEnabled(false);
                    msgURL.setEnabled(false);
                    cbFileOutput1.setEnabled(true);
                    cbFileOutput2.setEnabled(true);
                    cbFileOutput3.setEnabled(true);
                } else if (SMSSendingOptions.URL_SENDING.toString().equals(anObject)) {
                    txtEmail.setEditable(false);
                    cbFileOutput1.setEnabled(false);
                    cbFileOutput2.setEnabled(false);
                    cbFileOutput3.setEnabled(false);
                    txtURL.setEnabled(true);
                    numberURL.setEnabled(true);
                    msgURL.setEnabled(true);

                }
            }
        };

        final emcJTextField txtDefaultCountryCode = new emcJTextField(new EMCStringFormDocument(dataManager, "defaultCountryCode"));
        emcYesNoComponent ynIncludeCountryCode = new emcYesNoComponent(dataManager, "includeCountryCodeInNumber") {
            @Override
            public void setSelectedItem(Object anObject) {
                super.setSelectedItem(anObject);
                if ("Yes".equals(anObject)) {
                    txtDefaultCountryCode.setEditable(true);
                } else {
                    txtDefaultCountryCode.setEditable(false);
                }
            }
        };
        emcJTextField txtMaxMessageLength = new emcJTextField(new EMCIntegerFormDocument(dataManager, "maxMessageLength"));

        Component[][] comp = {{new emcJLabel("Testing SMS"), ynTest},
            {new emcJLabel("SMS Sending Option"), cbSendingOption},
            {new emcJLabel("SMS via Email")},
            {new emcJLabel("SMS Email"), txtEmail},
            {new emcJLabel("SMS via manual File upload")},
            {new emcJLabel("SMS Output File Column 1"), cbFileOutput1},
            {new emcJLabel("SMS Output File Column 2"), cbFileOutput2},
            {new emcJLabel("SMS Output File Column 3"), cbFileOutput3},
            {new emcJLabel("SMS via Web Post")},
            {new emcJLabel("Full URL"), txtURL},
            {new emcJLabel("Number Field name"), numberURL},
            {new emcJLabel("Message Field name"), msgURL},
            {new emcJLabel("General settings")},
            {new emcJLabel("Include Country Code"), ynIncludeCountryCode},
            {new emcJLabel("Default Country Code"), txtDefaultCountryCode},
            {new emcJLabel("Max Message Length"), txtMaxMessageLength},
            {new emcJLabel()},
            {new emcJLabel("Max number of SMS/Email to be sent without password"), txtMaxSMSEmailAllowed},
            {new emcJLabel("SMS/Email Approval Password"), txtSMSEmailApprovalPassword}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "SMS");


    }
}