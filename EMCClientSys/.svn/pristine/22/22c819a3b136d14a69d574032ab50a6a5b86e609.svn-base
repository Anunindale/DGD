/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimension3groupsetup;

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
import emc.app.components.formlookup.controllookup.EMCControlLookupComponentDRM;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.inventory.dimensions.InventoryDimension3GroupSetup;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCDebug;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 *
 * @author riaan
 */
public class Dimension3GroupSetupForm extends BaseInternalFrame {

    private emcJPanel dimension = new emcJPanel();
    private emcJPanel pnlDimensionGroup = new emcJPanel();
    private emcJLabel lblDimensionGroup = new emcJLabel("Colour Group");
    private EMCControlLookupComponent lkpDimensionGroup;
    private emcJLabel lblDimensionGroupDesc = new emcJLabel("Description");
    private emcJTextField txtDimensionGroupDesc = new emcJTextField();
    private EMCLookupJTableComponent lkpDimension;
    private EMCLookupTableCellEditor dimensionEditor;
    private EMCUserData copyUD;
    //DataSource
    private EMCControlLookupComponentDRM dataRelation;

    public Dimension3GroupSetupForm(EMCUserData userData) {
        super("Colour Group Setup", true, true, true, true, userData);
        copyUD = userData.copyUserData();
        this.setBounds(20, 20, 550, 290);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryConfig.html"));
        try {
            dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.datasource.InventoryDimension3GroupSetupDS(), userData), userData);

            lkpDimensionGroup = new EMCControlLookupComponent(new emc.menus.inventory.menuitems.display.Dimension3Groups(), dataRelation, "dimensionGroupId", txtDimensionGroupDesc, "description", InventoryDimension3GroupSetup.class.getName());
            dataRelation.setLookup(lkpDimensionGroup);

            List dimensionGroupKeys = new ArrayList();
            dimensionGroupKeys.add("dimensionGroupId");
            dimensionGroupKeys.add("description");

            EMCLookupPopup dimensionGroupPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3Group(),
                    "dimensionGroupId", dimensionGroupKeys, copyUD);
            lkpDimensionGroup.setPopup(dimensionGroupPopup);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension3GroupSetup.class.getName());
            lkpDimensionGroup.setFormQuery(query);

            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("dimensionId");
            dataRelation.setFormTextId2("dimensionGroupId");

        } catch (Exception e) {
            if (EMCDebug.getDebug()) {
                java.util.logging.Logger.getLogger("emc").log(Level.SEVERE, "Failed to create Colour Group Setup Form", userData);
            }
        }

        setupComponents();
        initFrame();
        //dataRelation.setUserData(userData);
    }

    private void setupComponents() {
        txtDimensionGroupDesc.setEditable(false);

        List dimensionKeys = new ArrayList<String>();
        dimensionKeys.add("dimensionId");
        dimensionKeys.add("description");

        lkpDimension = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension3());
        EMCLookupPopup dimensionPopup = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3(),
                "dimensionId", dimensionKeys, copyUD);
        lkpDimension.setPopup(dimensionPopup);
        dimensionEditor = new EMCLookupTableCellEditor(lkpDimension);
    }

    private void createDimensionGroupPanel() {
        pnlDimensionGroup.setBorder(javax.swing.BorderFactory.createTitledBorder("Colour Group"));
        int y = 0;
        GridBagConstraints localg;
        localg = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        pnlDimensionGroup.add(this.lblDimensionGroup, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 1, y, 0.1);
        pnlDimensionGroup.add(this.lkpDimensionGroup, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        pnlDimensionGroup.add(this.lblDimensionGroupDesc, localg);
        localg = emcSetGridBagConstraints.changePosition(localg, 2, y, 0.1);
        pnlDimensionGroup.add(this.txtDimensionGroupDesc, localg);
    }

    private void tabDimension() {
        int y = 0;
        GridBagConstraints localg;
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m) {

            @Override
            public boolean isCellEditable(int row, int col) {
                if (col == 1) {
                    return false;
                }
                return true;
            }
        };

        dataRelation.setMainTableComponent(toptable);

        toptable.setLookupCellEditor(0, dimensionEditor);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        dimension.setLayout(new GridBagLayout());
        createDimensionGroupPanel();
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.HORIZONTAL;
        dimension.add(pnlDimensionGroup, localg);
        y++;
        localg = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        localg.fill = GridBagConstraints.BOTH;
        localg.weighty = 1.0;
        dimension.add(topscroll, localg);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabDimension();
        tabbedPanetop.add("Colour Group Setup", this.dimension);
        this.add(tabbedPanetop);
    }
}
