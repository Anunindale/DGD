/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimensions;

import emc.app.components.emcJButton;
import emc.app.components.emcJColorChooserFormComponent;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.components.menulistbutton.emcMenuButtonList;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.lines.InventoryDimension1Lines;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.InventoryDimension3WhereUsedMenu;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class Dimension3Form extends BaseInternalFrame {

    private emcJPanel pnlDimension = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    private EMCLookupJTableComponent lkpColourDesign;
    private EMCLookupTableCellEditor editColourDesign;
    private EMCLookupJTableComponent lkpColour;
    private EMCLookupTableCellEditor editColour;
    private EMCUserData copyUD;

    public Dimension3Form(EMCUserData userData) {
        super("Colours", true, true, true, true, userData);
        this.setBounds(20, 20, 700, 340);
        //this.setHelpFile(new emcHelpFile("Inventory/InventoryCostingGroups.html"));
        try {
            copyUD = userData.copyUserData();
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3(), userData), userData) {

                @Override
                public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                    formUserData = super.generateRelatedFormUserData(formUserData, Index);

                    switch (Index) {
                        case 1:
                            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1.class);

                            EMCQuery nestedQuery = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Lines.class);
                            nestedQuery.addField("dimension1Id");
                            nestedQuery.addAnd("dimension3Id", this.getLastFieldValueAt("dimensionId"));

                            query.addAnd("dimensionId", nestedQuery, EMCQueryConditions.IN);

                            formUserData.setUserData(0, query);
                            break;
                        case 2:
                            List udList = new ArrayList();
                            udList.add(getLastFieldValueAt("dimensionId"));
                            udList.add("");
                            udList.add(getLastFieldValueAt("description"));
                            udList.add(getLastFieldValueAt("dimensionId"));
                            formUserData.setUserData(udList);
                            break;
                    }

                    return formUserData;
                }
            };
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("dimensionId");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabDimension() {
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        keys.add("sortCode");
        keys.add("sourceColRef");
        keys.add("designNo");
        keys.add("baseColour");
        keys.add("colourWay");
        keys.add("catagory");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setLookupCellEditor(3, editColour);
        toptable.setLookupCellEditor(4, editColourDesign);
        toptable.setColumnEditable("sortCode", false);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlDimension.setLayout(new GridLayout(1, 1));
        pnlDimension.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void setupLookups() {
        List keys = new ArrayList();
        keys.add("dimensionId");
        keys.add("description");
        lkpColour = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.Dimension3());
        EMCLookupPopup colourPop = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.dimensions.InventoryDimension3(),
                "dimensionId", keys, copyUD);
        lkpColour.setPopup(colourPop);
        editColour = new EMCLookupTableCellEditor(lkpColour);

        keys = new ArrayList();
        keys.add("designNo");
        keys.add("description");
        lkpColourDesign = new EMCLookupJTableComponent(new emc.menus.inventory.menuitems.display.ColourDesignMaster());
        EMCLookupPopup colourDesignPop = new EMCLookupPopup(enumEMCModules.INVENTORY.getId(), new emc.entity.inventory.colourdisignmaster.InventoryColourDesignMaster(),
                "designNo", keys, copyUD);
        lkpColourDesign.setPopup(colourDesignPop);
        editColourDesign = new EMCLookupTableCellEditor(lkpColourDesign);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        setupLookups();
        tabDimension();
        tabbedPanetop.add("Colours", this.pnlDimension);
        tabbedPanetop.add("Setup", colourSetupPane());
        emcJPanel contentPanel = new emcJPanel(new BorderLayout());
        contentPanel.add(tabbedPanetop, BorderLayout.CENTER);
        contentPanel.add(masterButtons(), BorderLayout.EAST);
        this.setContentPane(contentPanel);
    }

    private emcJPanel masterButtons() {
        emcJButton btnSort = new emcJButton("Sort") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.SET_SORT_CODE_DIMENSION3.toString());
                EMCWSManager.executeGenericWS(cmd, new ArrayList<Object>(), dataRelation.getUserData());
                dataRelation.refreshData();
            }
        };
        emcMenuButtonList btnWhereUsed = new emcMenuButtonList("Where Used", this);
        btnWhereUsed.addMenuItem("Config", new Dimension1(), 1, false);
        btnWhereUsed.addMenuItem("Item", new InventoryDimension3WhereUsedMenu(), 2, false);

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnSort);
        buttonList.add(btnWhereUsed);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);

    }

    private emcJPanel colourSetupPane() {
        emcJColorChooserFormComponent colorChooser = new emcJColorChooserFormComponent(dataRelation, "color");
        Component[][] comp = {{new emcJLabel("Colour"), colorChooser}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }
}
