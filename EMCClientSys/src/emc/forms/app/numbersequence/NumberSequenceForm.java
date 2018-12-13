/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.app.numbersequence;

import emc.app.components.EMCFormComboBox;
import emc.app.components.base.EMCModuleDropDown;
import emc.app.components.tables.EMCFieldsMapComboBox;
import emc.app.components.tables.EMCMapComboBoxFactory;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.documents.EMCLongFormDocument;
import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.datatypes.EMCDataType;
import emc.entity.base.numbersequences.BaseNumberSequence;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCDebug;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.methods.base.ServerBaseMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riaan
 */
public class NumberSequenceForm extends BaseInternalFrame {

    private emcJPanel pnlNumberSequences = new emcJPanel();
    private emcJPanel pnlDetails = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    private emcJPanel pnlPerValue = new emcJPanel();
    private emcJLabel lblMinValue = new emcJLabel("Min Value");
    private emcJLabel lblMaxValue = new emcJLabel("Max Value");
    private emcJLabel lblPrefix = new emcJLabel("Prefix");
    private emcJLabel lblSuffix = new emcJLabel("Suffix");
    private emcJLabel lblAllowManualEntry = new emcJLabel("Allow Manual");
    private emcJLabel lblRandomy = new emcJLabel("Random");
    private emcJLabel lblSample = new emcJLabel("Sample No");
    private EMCLookupFormComponent lkpPerValue;
    private emcJTextField txtMinValue = new emcJTextField();
    private emcJTextField txtMaxValue = new emcJTextField();
    private emcJTextField txtPrefix = new emcJTextField();
    private emcJTextField txtSuffix = new emcJTextField();
    private emcJTextField txtSample = new emcJTextField();
    private emcYesNoComponent ysnAllowManualEntry;
    private emcYesNoComponent ysnRandom;
    //DataSource
    private NumberSequenceDRM dataRelation;
    private EMCUserData userData;
    private enumEMCModules moduleId;

    public NumberSequenceForm(enumEMCModules module, EMCUserData userData) {
        super("Number Sequences", true, true, true, true, userData);
        this.moduleId = module;
        this.setBounds(20, 20, 750, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupNumberSequences.html"));
        try {
            this.userData = userData.copyUserDataAndDataList();
            txtSample.setEditable(false);

            dataRelation = new NumberSequenceDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.numbersequences.BaseNumberSequence(), userData), userData);
            //Set DRM Query
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseNumberSequence.class.getName());
            if (!enumEMCModules.BASE.toString().equals(getModuleId().toString())) {
                query.addAnd("moduleId", getModuleId().toString());
            }
            userData.setUserData(0, query);
            dataRelation.setUserData(userData);

            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("numberSequenceId");
            dataRelation.setFormTextId2("nextAvailableNumber");
            setDocuments();
            initFrame();
        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to construct Number Sequence Form", userData);
            }
        }
    }

    private void setDocuments() {
        this.txtMinValue.setDocument(new EMCLongFormDocument(dataRelation, "minNumber"));
        this.txtMaxValue.setDocument(new EMCLongFormDocument(dataRelation, "maxNumber"));
        this.txtPrefix.setDocument(new EMCStringFormDocument(dataRelation, "prefixString"));
        this.txtSuffix.setDocument(new EMCStringFormDocument(dataRelation, "suffixString"));
    }

    private void tabDetails() {
        ysnAllowManualEntry = new emcYesNoComponent(dataRelation, "allowManualEntry");
        ysnRandom = new emcYesNoComponent(dataRelation, "random");
        Component[][] components = new Component[][]{
            {lblMinValue, txtMinValue, lblPrefix, txtPrefix},
            {lblMaxValue, txtMaxValue, lblSuffix, txtSuffix},
            {lblAllowManualEntry, ysnAllowManualEntry, lblRandomy, ysnRandom},
            {new emcJLabel()},
            {lblSample, txtSample}
        };

        pnlDetails = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    private void tabPerValue() {
        emcJLabel lblPerField = new emcJLabel(this.dataRelation.getFieldEmcLabel("perField"));
        emcJLabel lblPerValue = new emcJLabel(this.dataRelation.getFieldEmcLabel("perValue"));
        EMCFormComboBox cbPerField = new EMCFormComboBox(new String[]{}, dataRelation, "perField");

        dataRelation.setCbPerField(cbPerField);
        lkpPerValue = new EMCLookupFormComponent(null, dataRelation, "perValue") {

            @Override
            public boolean isFocusOwner() {
                if (this.getPopup() == null) {
                    return false;
                } else {
                    return super.isFocusOwner();
                }
            }

            @Override
            public boolean hasFocus() {
                if (this.getPopup() == null) {
                    return false;
                } else {
                    return super.hasFocus();
                }
            }
        };

        dataRelation.setLkpPerValue(lkpPerValue);
        Component[][] components = new Component[][]{
            {lblPerField, cbPerField, lblPerValue, lkpPerValue}
        };
        pnlPerValue = emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true);
    }

    private void tabSystemTables() {
        List keys = new ArrayList();
        keys.add("moduleId");
        keys.add("numberSequenceId");
        keys.add("description");
        keys.add("refTable");
        keys.add("refField");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);

        //Add combobox
        if (!moduleId.equals(enumEMCModules.BASE)) {
            toptable.setColumnEditable("moduleId", false);
        }
        toptable.setComboBoxLookupToColumn("moduleId", new EMCModuleDropDown());
        toptable.setEMCMapComboBoxToColumn("refTable", moduleId.equals(enumEMCModules.BASE) ? EMCMapComboBoxFactory.getNumSeqTablesMapComboBox(null, getUserData()) : EMCMapComboBoxFactory.getNumSeqTablesMapComboBox(moduleId.toString(), getUserData()));
        //Special.  Only number sequence fields are to be added
        EMCFieldsMapComboBox box = new EMCFieldsMapComboBox() {

            @Override
            public void updateFields(String entityClassName, EMCUserData userData) {
                if (entityClassName != null && !entityClassName.equals(lastEntityClassName)) {
                    lastEntityClassName = entityClassName;
                    this.removeAllItems();

                    try {
                        EMCEntityClass entityClass = (EMCEntityClass) (Class.forName(entityClassName).newInstance());

                        List<Field> fields = entityClass.getAllTableFields();
                        String fieldName = null;
                        EMCDataType dt = null;

                        for (Field field : fields) {
                            fieldName = field.getName();
                            dt = entityClass.getDataType(fieldName, userData);

                            if (dt != null && dt.isNumberSeqAllowed()) {
                                this.addField(fieldName, dt.getEmcLabel());
                            }
                        }
                    } catch (Exception ex) {
                        System.out.println(entityClassName);
                        if (EMCDebug.getDebug()) {
                            java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Invalid entity class - Not found.", ex);
                        }
                    }
                }
            }
        };

        toptable.setEMCFieldsMapComboBoxToColumn("refField", box, dataRelation, "refTable", getUserData());

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlNumberSequences.setLayout(new GridLayout(1, 1));
        pnlNumberSequences.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabSystemTables();
        tabDetails();
        tabPerValue();
        initButton();
        tabbedPanetop.add("Number Sequences", this.pnlNumberSequences);
        tabbedPanetop.add("Details", this.pnlDetails);
        tabbedPanetop.add("Subject To", pnlPerValue);
        this.setLayout(new BorderLayout());
        this.add(tabbedPanetop, BorderLayout.CENTER);
        this.add(pnlButtons, BorderLayout.EAST);
    }

    public void setSampleText(String sample) {
        txtSample.setText(sample);
    }

    private void initButton() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new emcMenuButton("Available Numbers", new emc.menus.base.menuItems.display.AvailableNumbers(), this, 0, false));

        if (moduleId.equals(enumEMCModules.BASE) && "emc".equals(userData.getUserName())) {
            buttonList.add(new emcJButton("Regen Random") {

                @Override
                public void doActionPerformed(ActionEvent evt) {
                    super.doActionPerformed(evt);

                    if ((Long) dataRelation.getLastFieldValueAt("recordID") != 0l) {
                        EMCCommandClass cmd = new EMCCommandClass(ServerBaseMethods.REGENERATE_RANDOM_NUMBERS);

                        List toSend = new ArrayList();
                        toSend.add(dataRelation.getRowCache(dataRelation.getLastRowAccessed()));

                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, userData);
                        if (toSend.size() > 1 && (Boolean) toSend.get(1)) {
                            Logger.getLogger("emc").log(Level.INFO, "Successfuly regenerated random numbers.", userData);
                        } else {
                            Logger.getLogger("emc").log(Level.SEVERE, "Failed to regenerate random numbers.", userData);
                        }
                    }

                }
            });
        }

        pnlButtons = emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public enumEMCModules getModuleId() {
        return moduleId;
    }
}
