/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.crm.display.documents;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emcpicker.emcfilepicker.EMCFilePicker;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.crm.CRMDocuments;
import emc.enums.crm.CRMDocumentTypeEnum;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class CRMDocumentsForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public CRMDocumentsForm(EMCUserData userData) {
        super("Documents", true, true, true, true, userData);
        this.setBounds(20, 20, 600, 320);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.CRM.getId(), new CRMDocuments(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("documentId");
            dataManager.setFormTextId2("type");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Documents", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("documentId");
        keys.add("type");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setComboBoxLookupToColumn("type", new emcJComboBox(CRMDocumentTypeEnum.values()));
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcJButton btnAttach = new emcJButton("Attach File") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                CRMDocumentsForm.this.getDeskTop().doDocumentHandler();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnAttach);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
