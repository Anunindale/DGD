/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.parameters;

import emc.app.components.EMCFormComboBox;
import emc.app.components.documents.EMCBigDecimalFormDocument;
import emc.app.components.documents.EMCIntegerFormDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcYesNoComponent;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECParameters;
import emc.enums.modules.enumEMCModules;
import emc.enums.trec.TRECSpecialPrintEnum;
import emc.framework.EMCUserData;
import java.awt.Component;
import java.awt.GridBagConstraints;

/**
 *
 * @author wikus
 */
public class TRECParametersForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TRECParametersForm(EMCUserData userData) {
        super("Parameters", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECParameters(), userData), userData);
            dataManager.setDummyForm(this);
            this.setDataManager(dataManager);
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Parameters", formPathPane());
        this.setContentPane(tabbed);
    }

    private emcJPanel formPathPane() {
        emcYesNoComponent ynPrintExpiryDate = new emcYesNoComponent(dataManager, "printExpiryDate");
        ynPrintExpiryDate.setEnabled(false);
        EMCFormComboBox cmbHazZone = new EMCFormComboBox(TRECSpecialPrintEnum.values(), dataManager, "printHazZone");
        emcJTextField maxPhrase = new emcJTextField(new EMCIntegerFormDocument(dataManager, "maxPhrases"));
        
        Component[][] comp = {{new emcJLabel("Print Expiry Date"), ynPrintExpiryDate},
        {new emcJLabel("Print Placard"), cmbHazZone},
        {new emcJLabel("Max Phrases Selected"), maxPhrase}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Parameters");
    }
}
