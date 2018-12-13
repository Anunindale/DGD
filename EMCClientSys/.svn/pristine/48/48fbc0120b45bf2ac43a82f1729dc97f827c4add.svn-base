package emc.forms.base.display.employeeaccessgroups;

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
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.employeeaccessgroup.BaseEmployeeAccessGroup;
import emc.entity.base.employeeaccessgroupemployees.BaseEmployeeAccessGroupEmployees;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.BaseEmployeeAccessGroupEmployeesMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseEmployeeAccessGroupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public BaseEmployeeAccessGroupForm(EMCUserData userData) {
        super("Access Group", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(),
                new BaseEmployeeAccessGroup(), userData), userData) {

            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index) {
                EMCUserData ud = super.generateRelatedFormUserData(formUserData, Index);
                Object accessGroup = dataRelation.getLastFieldValueAt("accessGroup");
                Object description = dataRelation.getLastFieldValueAt("description");
                switch (Index) {
                    case 0:
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseEmployeeAccessGroupEmployees.class.getName());
                        query.addAnd("accessGroup", accessGroup);
                        List udList = new ArrayList();
                        udList.add(query);
                        udList.add("");
                        udList.add(description);
                        udList.add(accessGroup);
                        ud.setUserData(udList);
                }
                return ud;
            }
        };
        this.setDataManager(dataRelation);
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("accessGroup");
        dataRelation.setFormTextId2("description");
        initFrame();
    }

    private void initFrame() {
        emcJPanel contentPanel = new emcJPanel(new BorderLayout());
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Access Group", tablePane());
        contentPanel.add(tabbed, BorderLayout.CENTER);
        contentPanel.add(buttonPane(), BorderLayout.EAST);
        this.setContentPane(contentPanel);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("accessGroup");
        keys.add("description");
        keys.add("accessAll");
        emcTableModelUpdate model = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel buttonPane() {
        emcMenuButton btnEmployees = new emcMenuButton("Employees", new BaseEmployeeAccessGroupEmployeesMenu(), this, 0, false);
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(btnEmployees);
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
