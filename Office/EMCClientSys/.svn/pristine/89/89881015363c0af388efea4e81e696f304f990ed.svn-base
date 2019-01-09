/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.mailsetup;

import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCJPasswordCellEditor;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.renderers.EMCJPasswordCellRenderer;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseMailSetup;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseMailSetupForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataManager;

    public BaseMailSetupForm(EMCUserData userData) {
        super("Mail Setup", true, true, true, true, userData);
        this.setBounds(20, 20, 650, 320);
        try {
            dataManager = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.BASE.getId(), new BaseMailSetup(), userData), userData);
            this.setDataManager(dataManager);
            dataManager.setTheForm(this);
            dataManager.setFormTextId1("companyId");
            dataManager.setFormTextId2("smtpAddress");
        } catch (Exception ex) {
        }
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Mail Setup", tablePane());
        this.setContentPane(tabbed);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("smtpAuthenticationRequired");
        keys.add("smtpPort");
        keys.add("smtpAddress");
        keys.add("smtpUserName");
        keys.add("smtpPassword");
        keys.add("fromEmailAddress");
        keys.add("logToEmailAddress");
        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setColumnCellEditor("smtpPassword", new EMCJPasswordCellEditor());
        table.setColumnCellRenderer("smtpPassword", new EMCJPasswordCellRenderer());
        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        this.setTablePanel(tableScroll);
        return tableScroll;
    }
}
