/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.vehicles;

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
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDVehicles;
import emc.entity.dangerousgoods.datasource.VehiclesDS;
import emc.framework.EMCUserData;
import emc.menus.dangerousgoods.menuitems.display.DGDContactsMI;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author pj
 */
public class DGDVehiclesForm extends BaseInternalFrame {
    
    private EMCUserData userData;
    private EMCControlLookupComponentDRM drm;
    EMCControlLookupComponent contactNumlkp;

    public DGDVehiclesForm(EMCUserData userData) {
        super("Vehicles", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        this.userData = userData.copyUserDataAndDataList();
        drm = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new VehiclesDS(), userData), userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        drm.setFormTextId1("contactNumber");
        drm.setFormTextId2("registrationNumber");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Vehicle Registration", createRegistrationTab());
        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        mainPanel.add(selectionPane(), BorderLayout.NORTH);
        mainPanel.add(tabs, BorderLayout.CENTER);

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(mainPanel, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcJPanel selectionPane() {
        emcJTextField txtDescription = new emcJTextField();
        txtDescription.setEditable(false);
       
        contactNumlkp = new EMCControlLookupComponent(new DGDContactsMI(), drm, "contactNumber",
                txtDescription, "company", DGDVehicles.class.getName());
        contactNumlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactNumber", userData));
        drm.setLookup(contactNumlkp);
        Component[][] comp = {{new emcJLabel("Contact Number"), contactNumlkp, new emcJLabel("Company"), txtDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Contact Details");
    }

    private emcJPanel createRegistrationTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("registrationNumber");
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
    
}
