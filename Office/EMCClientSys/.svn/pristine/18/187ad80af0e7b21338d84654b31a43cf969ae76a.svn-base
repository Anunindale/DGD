/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.numbersequence.availablenumbers;

import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.controllookup.EMCControlLookupComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class AvailableNumbersForm extends BaseInternalFrame {

    private emcJPanel pnlTop = new emcJPanel();
    private emcJPanel pnlAll = new emcJPanel();
    private emcJLabel lblnumberId = new emcJLabel("Number Id");
    private emcJLabel lblDescrition = new emcJLabel("Description");
    private emcJTextField numberDescription = new emcJTextField();
    //DataSource
    private AvailableNumbersDRM dataRelation;
    private EMCControlLookupComponent lookupNumberSeq;
    private emcTableModelUpdate m;

    public AvailableNumbersForm(EMCUserData userData) {
        super("Available Numbers", true, true, true, true, userData);
        EMCUserData copyUD = userData.copyUserData();
        this.setBounds(20, 20, 550, 290);
        try {
            dataRelation = new AvailableNumbersDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.numbersequences.BaseAvailableSequenceNumbers(), userData), userData);
            this.setDataManager(dataRelation);
            lookupNumberSeq = new EMCControlLookupComponent(new emc.menus.base.menuItems.display.BaseNumberSequencesMenu(), dataRelation, "sequenceNumberId", numberDescription, "description", emc.entity.base.numbersequences.BaseAvailableSequenceNumbers.class.getName());
            dataRelation.setLookup(lookupNumberSeq);
            List numberSeqKeys = new ArrayList();
            numberSeqKeys.add("numberSequenceId");
            numberSeqKeys.add("description");
            EMCLookupPopup numberSeqPopup = new EMCLookupPopup(new emc.entity.base.numbersequences.BaseNumberSequence(), "numberSequenceId", numberSeqKeys, copyUD);
            lookupNumberSeq.setPopup(numberSeqPopup);

            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("sequenceNumberId");
            dataRelation.setFormTextId2("sequenceNumber");
        } catch (Exception e) {
            e.printStackTrace();
        }
        numberDescription.setEditable(false);
        initFrame();
    }

    private void tabTopPane() {
        pnlTop.setBorder(javax.swing.BorderFactory.createTitledBorder("Item"));
        GridBagConstraints gbc;
        gbc = emcSetGridBagConstraints.createStandard(0, 0, 0.1, GridBagConstraints.FIRST_LINE_START);
        pnlTop.add(this.lblnumberId, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 1, 0, 0.1);
        pnlTop.add(this.lookupNumberSeq, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, 0, 0.1);
        pnlTop.add(this.lblDescrition, gbc);
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, 0, 0.1);
        pnlTop.add(this.numberDescription, gbc);
    }

    private void tabTablePane() {
        GridBagConstraints gbc;
        List keys = new ArrayList();
        keys.add("sequenceNumber");
        keys.add("status");
        m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int arg0, int arg1) {
                return false;
            }

        };
        
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        pnlAll.setLayout(new GridBagLayout());
        
        gbc = emcSetGridBagConstraints.createStandard(0, 0, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        pnlAll.add(pnlTop, gbc);

        gbc = emcSetGridBagConstraints.changePosition(gbc, 0, 1, 1.0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        pnlAll.add(tableScroll, gbc);
        this.setTablePanel(tableScroll);
    }
    
    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabTopPane();
        tabTablePane();
        tabbedPane.add("Available Numbers", this.pnlAll);
        this.add(tabbedPane);
    }
}
