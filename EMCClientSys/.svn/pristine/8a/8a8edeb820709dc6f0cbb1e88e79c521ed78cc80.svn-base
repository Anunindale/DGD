/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.preferredshipname;

import emc.forms.trec.display.loadcompatibility.*;
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
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.datatypes.trec.chemicals.ShippingName;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECLoadCompatibility;
import emc.entity.trec.TRECPreferredShipName;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.developertools.trec.TRECChemicalsMenu;
import emc.menus.developertools.trec.TRECClassesMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class PreferredShipNameForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;
    private long chemicalLink;
    private String unNumber;

    public PreferredShipNameForm(EMCUserData userData) {
        super("Preferred Shipping Name", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        this.userData = userData.copyUserDataAndDataList();
        if(!Functions.checkBlank(userData.getUserData(4))){
        chemicalLink = (Long) userData.getUserData(4);
        }
        
        dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECPreferredShipName(), userData), userData) {
            @Override
            public void updatePersist(int rowIndex) {

                if (rowIndex == -1) {
                    rowIndex = getLastRowAccessed();
                }

                if (this.getFieldValueAt(rowIndex, "chemicalLink") == null) {
                    this.setFieldValueAt(rowIndex, "chemicalLink", chemicalLink);
                }

                super.updatePersist(rowIndex);
            }
        };
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("unNumber");
        dataRelation.setFormTextId2("properShipName");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Overview", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(lookupPane(), BorderLayout.NORTH);
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("properShipName");

        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel lookupPane() {
        emcJTextField txtDesc = new emcJTextField();
        EMCControlLookupComponent lkpClass = new EMCControlLookupComponent(
                new TRECChemicalsMenu(), dataRelation, "unNumber", txtDesc, "shippingName", TRECPreferredShipName.class.getName());
        lkpClass.setPopup(new EMCLookupPopup(new TRECChemicals(), "unNumber", userData));
        dataRelation.setLookup(lkpClass);
        Component[][] comp = {{new emcJLabel("UN "), lkpClass, new emcJLabel("Shipping Name"), txtDesc}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Ship Name");
    }

    @Override
    public void setUserData(EMCUserData userData) {
        this.unNumber = (String) userData.getUserData(3);
        this.chemicalLink = (long) userData.getUserData(4);
        super.setUserData(userData);
    }
}
