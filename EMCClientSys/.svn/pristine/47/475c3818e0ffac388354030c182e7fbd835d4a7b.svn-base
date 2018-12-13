/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.trecclasses;

import emc.app.components.documents.EMCStringFormDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextArea;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECLoadCompatibility;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.developertools.trec.TRECClassesMenu;
import emc.menus.trec.menuitems.display.TRECLoadCompMenu;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rico
 */
public class TrecClassesForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public TrecClassesForm(EMCUserData userData) {
        super("Classes", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 340);
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.TREC.getId(), new TRECClasses(), userData), userData) {

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData userData = super.generateRelatedFormUserData(formUserData, Index);
                EMCQuery query;
                List udList;
                switch (Index) {
                    case 0:
                        query = new EMCQuery(enumQueryTypes.SELECT, TRECLoadCompatibility.class);
                        query.addAnd("classId", dataManager.getLastFieldValueAt("classId"));
                        udList = new ArrayList();
                        udList.add(query);
                        udList.add("");
                        udList.add(dataManager.getLastFieldValueAt("description"));
                        udList.add(dataManager.getLastFieldValueAt("classId"));
                        userData.setUserData(udList);
                        break;
                }
                return userData;
            }
        };
        this.setDataManager(dataManager);
        dataManager.setTheForm(this);
        dataManager.setFormTextId1("classId");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPane = new emcJTabbedPane();
        tabbedPane.add("Forms", tablePane());
        tabbedPane.add("Notes", notesPanePane());
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("classId");
        keys.add("description");
        keys.add("parentClass");
        keys.add("packGrp1Threshold");
        keys.add("packGrp2Threshold");
        keys.add("packGrp3Threshold");
        keys.add("srPackGrp1Threshold");
        keys.add("srPackGrp2Threshold");
        keys.add("srPackGrp3Threshold");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        //Lookups
        EMCLookupJTableComponent lkpParentClass = new EMCLookupJTableComponent(new TRECClassesMenu());
        lkpParentClass.setPopup(new EMCLookupPopup(new TRECClasses(), "classId", dataManager.getUserData()));
        table.setLookupToColumn("parentClass", lkpParentClass);
        //Lookups
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel notesPanePane() {
        emcJTextArea txaShortDesc = new emcJTextArea(new EMCStringFormDocument(dataManager, "shortDescription"));
        emcJTextArea txaNotes = new emcJTextArea(new EMCStringFormDocument(dataManager, "notes"));
        Component[][] comp = {{txaShortDesc, new emcJLabel("Short Description")}, {txaNotes, new emcJLabel("Notes")}};
        return emcSetGridBagConstraints.createSimplePanel(comp, GridBagConstraints.NONE, true);
    }

    private emcJPanel buttonPane() {
        emcMenuButton btnLoadCompatibility = new emcMenuButton("Load Comp", new TRECLoadCompMenu(), this, 0, false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnLoadCompatibility);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
