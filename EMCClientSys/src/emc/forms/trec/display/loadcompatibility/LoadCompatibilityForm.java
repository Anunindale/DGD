/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.loadcompatibility;

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
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECLoadCompatibility;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
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
public class LoadCompatibilityForm extends BaseInternalFrame {

    private EMCControlLookupComponentDRM dataRelation;
    private EMCUserData userData;

    public LoadCompatibilityForm(EMCUserData userData) {
        super("Load Compatibility Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 350);
        this.userData = userData.copyUserDataAndDataList();
        dataRelation = new EMCControlLookupComponentDRM(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECLoadCompatibility(), userData), userData);
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("classId");
        dataRelation.setFormTextId2("otherClassId");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Compatibility", tablePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(lookupPane(), BorderLayout.NORTH);
        contentPane.add(tabbed, BorderLayout.CENTER);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("otherClassId");
        keys.add("note");
        keys.add("allowed");
        emcTableModelUpdate model = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpClass = new EMCLookupJTableComponent(new TRECClassesMenu());
        lkpClass.setPopup(new EMCLookupPopup(new TRECClasses(), "classId", userData));
        table.setLookupToColumn("otherClassId", lkpClass);
        //Lookups
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel lookupPane() {
        emcJTextField txtDesc = new emcJTextField();
        EMCControlLookupComponent lkpClass = new EMCControlLookupComponent(
                new TRECClassesMenu(), dataRelation, "classId", txtDesc, "description", TRECLoadCompatibility.class.getName());
        lkpClass.setPopup(new EMCLookupPopup(new TRECClasses(), "classId", userData));
        dataRelation.setLookup(lkpClass);
        Component[][] comp = {{new emcJLabel("Class"), lkpClass, new emcJLabel("Description"), txtDesc}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true, "Class");
    }
}

