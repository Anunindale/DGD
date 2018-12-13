/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimensions;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.factory.EMCDimensionLookupFactory;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.dimensions.datasource.InventoryDimension1LinesDS;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.InventoryDimension1WhereUsedMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author riaan
 */
public class Dimension1Form extends BaseInternalFrame {

    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCLookupJTableComponent tblLkpColour;

    public Dimension1Form(EMCUserData userData) {
        super("Configurations", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 450);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryCostingGroups.html"));
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension1(), userData), userData) {

                @Override
                public EMCUserData generateRelatedLinesUserData(EMCUserData linesUserData) {
                    linesUserData = super.generateRelatedLinesUserData(linesUserData);

                    //Sort by sequence
                    EMCQuery query = (EMCQuery) linesUserData.getUserData(0);
                    query.addOrderBy("seq");
                    return linesUserData;
                }

                @Override
                public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                    EMCUserData generatedUD = super.generateRelatedFormUserData(formUserData, Index);
                    List udList;
                    switch (Index) {
                        case 0:
                            udList = new ArrayList();
                            udList.add(getLastFieldValueAt("dimensionId"));
                            udList.add("");
                            udList.add(getLastFieldValueAt("description"));
                            udList.add(getLastFieldValueAt("dimensionId"));
                            generatedUD.setUserData(udList);
                            break;
                    }
                    return generatedUD;
                }

                @Override
                public void updatePersist(int rowIndex) {
                    super.updatePersist(rowIndex);
                    if (getLastUpdateStatus()) {
                        refreshRecord(rowIndex);
                    }
                }

                @Override
                public boolean storeInHeaderLinesSetup() {
                    boolean ret = super.storeInHeaderLinesSetup();

                    if (ret) {
                        //Sort code is updated again on server.  Refresh record.
                        refreshRecordNoDoRelation(this.getLastRowAccessed());
                    }

                    return ret;
                }
            };

            this.setDataManager(dataRelation);

            this.linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new InventoryDimension1LinesDS(), userData), userData) {

                @Override
                public void updatePersist(int rowIndex) {
                    super.updatePersist(rowIndex);

                    if (getLastUpdateStatus()) {
                        this.getHeaderTable().refreshRecordNoDoRelation(-1);
                    }
                }
            };

            dataRelation.setHeaderColumnID("dimensionId");
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("dimensionId");
            dataRelation.setFormTextId2("description");
            dataRelation.setLinesTable(linesDRM);

            linesDRM.setTheForm(this);
            linesDRM.setFormTextId1("dimension1Id");
            linesDRM.setFormTextId2("dimension3Id");
            linesDRM.setHeaderTable(dataRelation);
            linesDRM.setRelationColumnToHeader("dimension1Id");

        } catch (Exception e) {
            e.printStackTrace();
        }

        initLookups(userData);
        initFrame();
    }

    private emcJPanel createDimensionPanel() {
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        keys.add("sortCode");
        keys.add("active");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);

        emcJPanel pnlDimension = new emcJPanel(new GridLayout(1, 1));
        pnlDimension.add(topscroll);
        dataRelation.setTablePanel(topscroll);

        return pnlDimension;
    }

    private void initFrame() {
        this.setLayout(new GridLayout(1, 1));

        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, createMasterPanel(), createLinesPanel());
        topBottomSplit.setDividerSize(8);
        topBottomSplit.setContinuousLayout(true);
        topBottomSplit.setOneTouchExpandable(false);
        topBottomSplit.setDividerLocation(220);

        this.add(topBottomSplit);
    }

    private emcJPanel createMasterPanel() {
        emcJPanel masterPanel = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Configurations", this.createDimensionPanel());
        masterPanel.add(tabbedPanetop, BorderLayout.CENTER);
        masterPanel.add(masterButtons(), BorderLayout.EAST);

        return masterPanel;
    }

    private emcJPanel createLinesPanel() {
        emcJPanel linesPanel = new emcJPanel(new BorderLayout());

        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Colours", createLinesOverview());
        linesPanel.add(tabbedPanetop, BorderLayout.CENTER);

        return linesPanel;
    }

    private emcJPanel createLinesOverview() {
        List keys = new ArrayList();
        keys.add("dimension3Id");
        keys.add("dim3Desc");
        keys.add("seq");
        emcTableModelUpdate m = new emcTableModelUpdate(this.linesDRM, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        linesDRM.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);

        toptable.setLookupToColumn("dimension3Id", tblLkpColour);

        emcJPanel pnlOverview = new emcJPanel(new GridLayout(1, 1));
        pnlOverview.add(topscroll);
        linesDRM.setTablePanel(topscroll);

        return pnlOverview;
    }

    private void initLookups(EMCUserData userData) {
        tblLkpColour = EMCDimensionLookupFactory.createDimension3JTableLookup(userData);
    }

    private emcJPanel masterButtons() {
        emcJButton btnSort = new emcJButton("Sort") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.SET_SORT_CODE_DIMENSION1.toString());
                EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), dataRelation.getUserData());
                dataRelation.refreshData();
            }
        };
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnSort);
        buttonList.add(new emcMenuButton("Where Used", new InventoryDimension1WhereUsedMenu(), this, 0, false));
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
