/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.timebyday;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
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
import emc.entity.base.BaseTimeByDay;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseGenerateTimeByDayMenu;
import emc.menus.gl.menuitems.display.GLFinancialPeriods;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseTimeByDayForm extends BaseInternalFrame {

    private EMCUserData userData;
    private emcDataRelationManagerUpdate dataManager;

    public BaseTimeByDayForm(EMCUserData userData) {
        super("Time By Day", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 310);
        this.userData = userData.copyUserDataAndDataList();
        dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseTimeByDay(), userData), userData) {

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData generated = super.generateRelatedFormUserData(formUserData, Index);
                switch (Index) {
                    case 0:
                        generated.setUserData(0, this);
                        break;
                }
                return generated;
            }
        };
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("actualDate");
        dataManager.setFormTextId2("quarter");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        contentPane.add(tablePane(), BorderLayout.CENTER);
        contentPane.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("actualDate");
        keys.add("dayOfMonth");
        keys.add("dayOfWeek");
        keys.add("monthOfYear");
        keys.add("theYear");
        keys.add("weekOfYear");
        keys.add("quarter");
        keys.add("financialPeriod");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        setupLookups(table);
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);
        return tableScroll;
    }

    private void setupLookups(emcJTableUpdate table) {
        EMCLookupJTableComponent lkpFinancialPeriod = new EMCLookupJTableComponent(new GLFinancialPeriods());
        lkpFinancialPeriod.setPopup(new EMCLookupPopup(new emc.entity.gl.GLFinancialPeriods(), "periodId", userData));
        table.setLookupToColumn("financialPeriod", lkpFinancialPeriod);

        table.setColumnEditable("dayOfMonth", false);
        table.setColumnEditable("dayOfWeek", false);
        table.setColumnEditable("monthOfYear", false);
        table.setColumnEditable("theYear", false);
        table.setColumnEditable("weekOfYear", false);
    }

    private emcJPanel buttonPane() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        BaseGenerateTimeByDayMenu menu = new BaseGenerateTimeByDayMenu();
        menu.setDoNotOpenForm(false);
        emcMenuButton btnGenerate = new emcMenuButton("Generate", menu, this, 0, false);
        buttonList.add(btnGenerate);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
