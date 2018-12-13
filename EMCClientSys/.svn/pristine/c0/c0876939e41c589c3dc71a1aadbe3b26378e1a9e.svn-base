/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.action.settlement;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class SettlementForm extends BaseInternalFrame {

    private emcJPanel pnlBrandGroups = new emcJPanel();
    private emcJPanel pnlButtons = new emcJPanel();
    //DataSource
    private SettlementDRM dataRelation;

    public SettlementForm(EMCUserData userData) {
        super("Settlement", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);

        try {

            dataRelation = new SettlementDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.stocksettlement.InventoryStockSettlement(), userData), userData);
            this.setDataManager(dataRelation);

            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("settlementId");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabBrandGroups() {
        List keys = new ArrayList();
        keys.add("settlementId");
        keys.add("description");
        //keys.add("startDate");
        keys.add("endDate");
        keys.add("runStatus");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setColumnEditable("runStatus", false);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlBrandGroups.setLayout(new GridLayout(1, 1));
        pnlBrandGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void tabButtons() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new SettlementRunButton(dataRelation));
        buttonList.add(new SettlementRollBackButton(dataRelation));

        pnlButtons = emcSetGridBagConstraints.createButtonPanel(buttonList);

    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabBrandGroups();
        tabButtons();
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout());
        thePanel.add(pnlBrandGroups, BorderLayout.CENTER);
        thePanel.add(pnlButtons, BorderLayout.EAST);
        tabbedPanetop.add("Settlement Overview", thePanel);
        this.add(tabbedPanetop);
    }
}
