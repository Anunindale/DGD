/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.contacts;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.formlookup.EMCLookupFormComponent;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.lookup.popup.EMCMultiValuePopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BasePostalCodes;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.sop.SOPCustomers;
import emc.enums.dangerousgoods.ContactType;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.PostalCodes;
import emc.menus.dangerousgoods.menuitems.display.DGDVehiclesMI;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pj
 */
public class DGDContactsForm extends BaseInternalFrame{
    
    private emcDataRelationManagerUpdate drm;
    
    public DGDContactsForm(EMCUserData userData)
    {
        super("Contacts", true, true, true, true, userData);
        this.setBounds(20, 20, 650,290);
        drm = new emcDataRelationManagerUpdate
                (new emcGenericDataSourceUpdate(new DGDContacts(), userData), 
                userData);
        drm.setTheForm(this);
        this.setDataManager(drm);
        //formtext? contact, company name
        drm.setFormTextId1("contactName");
        drm.setFormTextId2("company");
        this.initFrame();
    }
    
    private void initFrame()
    {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewTab());
        tabs.add("Address", createAddressTab());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtons(), BorderLayout.EAST);
    }
    
    private emcJPanel createOverviewTab()
    {
        emcJPanel panel = new emcJPanel(new GridLayout(1,1));
        
        List<String> keys = new ArrayList<String>();
        keys.add("contactNumber");
        keys.add("customer");
        keys.add("type");
        keys.add("contactName");
        keys.add("company");
        keys.add("telephone");
        
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        
        drm.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        
        EMCLookupJTableComponent customerlkp = new EMCLookupJTableComponent(new SOPCustomersMenu());
        customerlkp.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", getUserData()));
        table.setLookupToColumn("customer", customerlkp);
        
        table.setComboBoxLookupToColumn("type", new emcJComboBox(ContactType.values()));
        
        return panel;
    }
    
    private emcJPanel createAddressTab()
    {
        Map<String, String> fieldMap = new HashMap<String, String>();
        fieldMap.put("physicalAddress2", "suburb");
        fieldMap.put("physicalAddress3", "city");
        fieldMap.put("physicalAddress4", "provence");
        fieldMap.put("physicalAddress5", "country");
        fieldMap.put("postalCode", "code");
       
        emcJTextField txtPhysicalStreetAdress1 = new emcJTextField(new EMCStringFormDocument(drm, "physicalAddress1"));
                
        EMCLookupFormComponent lkpPhysicalSuburb = new EMCLookupFormComponent(new PostalCodes(), drm, "physicalAddress2");
        lkpPhysicalSuburb.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "suburb", drm, fieldMap, getUserData()));
        
        EMCLookupFormComponent lkpPhysicalCity = new EMCLookupFormComponent(new PostalCodes(), drm, "physicalAddress3");
        lkpPhysicalCity.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "city", drm, fieldMap, getUserData()));
        
        EMCLookupFormComponent lkpPhysicalProvence = new EMCLookupFormComponent(new PostalCodes(), drm, "physicalAddress4");
        lkpPhysicalProvence.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "provence", drm, fieldMap, getUserData()));
        
        EMCLookupFormComponent lkpPhysicalCountry = new EMCLookupFormComponent(new PostalCodes(), drm, "physicalAddress5");
        lkpPhysicalCountry.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "country", drm, fieldMap, getUserData()));
        
        EMCLookupFormComponent lkpPhysicalCode = new EMCLookupFormComponent(new PostalCodes(), drm, "postalCode");
        lkpPhysicalCode.setPopup(new EMCMultiValuePopup(new BasePostalCodes(), "code", drm, fieldMap, getUserData()));
        
        Component[][] leftCom = {{new emcJLabel("Street"), txtPhysicalStreetAdress1},
            {new emcJLabel("Suburb"), lkpPhysicalSuburb},
            {new emcJLabel("City"), lkpPhysicalCity},
            {new emcJLabel("Province"), lkpPhysicalProvence},
            {new emcJLabel("Country"), lkpPhysicalCountry},
            {new emcJLabel("Postal Code"), lkpPhysicalCode}};
        
        emcJPanel panel = emcSetGridBagConstraints.createSimplePanel(leftCom, GridBagConstraints.NONE, true, "Physical Address");
        
        return panel;
    }
    
    private emcJPanel createButtons()
    {
        List<emcJButton> buttons = new ArrayList<>();
        
        DGDVehiclesMI vehiclesForm = new DGDVehiclesMI();
        buttons.add(new emcMenuButton("Register Vehicle", vehiclesForm, this, 1, false));
       
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
    
}
