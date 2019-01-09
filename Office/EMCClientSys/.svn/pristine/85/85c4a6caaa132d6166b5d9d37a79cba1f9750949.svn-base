/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.unitsofmeasureconversion;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.components.jtablelookup.EMCLookupTableCellEditor;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseUOMDetailedConversionTable;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseUOMDetailedConversionTableMI;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class UOMConversionForm extends BaseInternalFrame {

    private emcJPanel pnlUnitsOfMeasure = new emcJPanel();
    private EMCLookupJTableComponent lkpUnit;
    private EMCLookupTableCellEditor unitEditor;
    private EMCUserData copyUD;
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public UOMConversionForm(EMCUserData userData) {
        super("Units of Measure Conversion", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupUnitsOfMeasureConversions.html"));
        try {
            copyUD = userData.copyUserData();

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BaseUOMConversionTable(), userData), userData) {

                @Override
                public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                    EMCUserData generated = super.generateRelatedFormUserData(formUserData, Index);

                    EMCQuery query;
                    List udList;

                    switch (Index) {
                        case 1:
                            query = new EMCQuery(enumQueryTypes.SELECT, BaseUOMDetailedConversionTable.class);
                            query.addAnd("masterRecordID", getLastFieldValueAt("recordID"));

                            udList = new ArrayList();
                            udList.add(query);
                            udList.add(getLastFieldValueAt("recordID"));

                            generated.setUserData(udList);
                    }

                    return generated;
                }
            };
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("unit");
            dataRelation.setFormTextId2("baseUnit");

            setupLookups();

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void setupLookups() {
        List unitKeys = new ArrayList<String>();
        unitKeys.add("unit");
        unitKeys.add("description");

        lkpUnit = new EMCLookupJTableComponent(new emc.menus.base.menuItems.display.UnitsOfMeasure());
        EMCLookupPopup unitPopup = new EMCLookupPopup(enumEMCModules.BASE.getId(), new emc.entity.base.BaseUnitsOfMeasure(),
                "unit", unitKeys, copyUD);
        lkpUnit.setPopup(unitPopup);
        unitEditor = new EMCLookupTableCellEditor(lkpUnit);
    }

    private void tabUnitsOfMeasure() {
        List keys = new ArrayList();
        keys.add("unit");
        keys.add("conversion");
        keys.add("baseUnit");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);

        toptable.setLookupCellEditor(0, unitEditor);
        toptable.setLookupCellEditor(2, unitEditor);

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlUnitsOfMeasure.setLayout(new GridLayout(1, 1));
        pnlUnitsOfMeasure.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabUnitsOfMeasure();
        tabbedPanetop.add("Units of Measure Conversions", this.pnlUnitsOfMeasure);

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tabbedPanetop, BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);

        this.setContentPane(contentPane);
    }

    private emcJPanel buttonPane() {
        BaseUOMDetailedConversionTableMI detailedMI = new BaseUOMDetailedConversionTableMI();
        detailedMI.setDoNotOpenForm(false);
        emcMenuButton btnDetailed = new emcMenuButton("Detailed", detailedMI, this, 1, false);

        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnDetailed);

        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
