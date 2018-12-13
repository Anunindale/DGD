/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.action.copytabledata.resources;

import emc.app.components.emcJButton;
import emc.app.components.emcJDialog;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.frame.EMCDesktop;
import emc.entity.base.BaseCompanyTable;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.companies;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class BaseCopyTableDataCopanySelectionDialog  extends emcJDialog {

    private EMCUserData userData;
    private EMCLookup lkpCompany;

    public BaseCopyTableDataCopanySelectionDialog(EMCDesktop owner, EMCUserData userData) {
        super(owner, "Copy Table Data", true);
        this.userData = userData.copyUserData();
        initDialog();
        this.pack();
        this.setVisible(true);
    }

    private void initDialog() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Copy/Clear", selectionPane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbed, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        lkpCompany = new EMCLookup(new companies());
        lkpCompany.setPopup(new EMCLookupPopup(new BaseCompanyTable(), "companyId", userData));

        Component[][] comp = {{new emcJLabel("Company"), lkpCompany}};

        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcJButton btnOK = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                if (Functions.checkBlank(lkpCompany.getValue())) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Please selecct a company.", userData);
                    return;
                }
                BaseCopyTableDataCopanySelectionDialog.this.dispose();
            }
        };
        emcJButton btnCancel = new emcJButton("Cancel") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                BaseCopyTableDataCopanySelectionDialog.this.dispose();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnOK);
        buttonList.add(btnCancel);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }

    public String getCompany() {
        return (String)lkpCompany.getValue();
    }

}
