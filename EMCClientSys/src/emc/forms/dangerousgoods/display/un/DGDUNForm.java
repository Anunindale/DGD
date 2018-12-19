/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.un;

import emc.app.components.emcJButton;
import emc.app.components.emcJComboBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcMenuButton;
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
import emc.app.wsmanager.EMCWSManager;
import emc.entity.dangerousgoods.DGDUN;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.datasource.UNDS;
import emc.entity.trec.TRECChemicals;
import emc.forms.dangerousgoods.display.resources.DGDUNLRM;
import emc.framework.EMCCommandClass;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.dangerousgoods.menuitems.display.DGDUNMI;
import emc.menus.dangerousgoods.menuitems.display.DGDVehiclesMI;
import emc.menus.dangerousgoods.menuitems.display.DGDeclarationLinesMI;
import emc.menus.developertools.trec.TRECChemicalsMenu;
import emc.methods.trec.ServerTRECMethods;
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
public class DGDUNForm extends BaseInternalFrame {
    
    private EMCUserData userData;
    private EMCControlLookupComponentDRM drm;
    private DGDUNLRM lrm;
    private emcJComboBox cbPackagingGroup = new emcJComboBox();
    EMCControlLookupComponent lineNumlkp;

    public DGDUNForm(EMCUserData userData) {
        super("UN", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 290);
        
        this.userData = userData.copyUserDataAndDataList();
        
        drm = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(new UNDS(), userData), userData)
        {
            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrm != null) {
                    //lrm.doRowChanged(drm);
                    updatePackingGroups(rowIndex);
                }

                //updatePackingGroups(rowIndex);
            }

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);

                if (columnIndex.equals("unNumber")) {
                    if (lrm != null) {
                        //lrm.doRowChanged(drm);
                        updatePackingGroups(rowIndex);
                    }

                    //updatePackingGroups(rowIndex);
                }
            }

            private void updatePackingGroups(int rowIndex) 
            {
                if (cbPackagingGroup != null) 
                {
                    cbPackagingGroup.removeAllItems();
                    String unnumber = (String) drm.getFieldValueAt(rowIndex, "unNumber");

                    if (!Functions.checkBlank(unnumber)) 
                    {

                        EMCCommandClass cmd = new EMCCommandClass(ServerTRECMethods.GET_PACKING_GROUP);

                        List toSend = new ArrayList();
                        toSend.add(unnumber);
                        toSend = EMCWSManager.executeGenericWS(cmd, toSend, getUserData());

                        if (toSend != null && toSend.size() > 1 && toSend.get(1) instanceof String) 
                        {
                            String x = (String) toSend.get(1);
                            if (!Functions.checkBlank(x)) 
                            {
                                if (x.contains(";")) 
                                {
                                    String[] phraseArray = x.split(";");
                                    cbPackagingGroup.removeAllItems();

                                    for (String phrase : phraseArray) 
                                    {
                                        cbPackagingGroup.addItem(phrase);
                                    }
                                } else 
                                {
                                    cbPackagingGroup.addItem(x);
                                }
                            }

                        }
                    }

                }
            }
        };
        
        drm.setTheForm(this);
        drm.setFormTextId1("lineNumber");
        drm.setFormTextId2("description");
        this.setDataManager(drm);
        
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Add UN Item", createUNTab());
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
       
        lineNumlkp = new EMCControlLookupComponent(new DGDeclarationLinesMI(), drm, "lineNumber",
                txtDescription, "description", DGDUN.class.getName());
        lineNumlkp.setPopup(new EMCLookupPopup(new DGDeclarationLines(), "lineNumber", userData));
        
        lrm = new DGDUNLRM();
        lrm.addLookup(lineNumlkp, "lineNumber");
        lrm.initializeLookups();
        
        drm.setLookup(lineNumlkp);
        Component[][] comp = {{new emcJLabel("Line Number"), lineNumlkp, new emcJLabel("Description"), txtDescription}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Line Details");
    }

    private emcJPanel createUNTab() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));
        List<String> keys = new ArrayList<String>();
        keys.add("unNumber");
        keys.add("packingGroup");
        keys.add("packaging");
        keys.add("grossMass");
        keys.add("netMass");
        emcTableModelUpdate model = new emcTableModelUpdate(drm, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        
        /*Lookups*/
        EMCLookupJTableComponent unlkp = new EMCLookupJTableComponent(new TRECChemicalsMenu());
        unlkp.setPopup(new EMCLookupPopup(new TRECChemicals(), "unNumber", userData));
        
        table.setLookupToColumn("unNumber", unlkp);
        table.setComboBoxLookupToColumn("packingGroup", cbPackagingGroup);
        
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        drm.setMainTableComponent(table);
        drm.setTablePanel(tableScroll);
        panel.add(tableScroll);
        return panel;
    }
    
}
