/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.databaseconnection;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.renderers.EMCJPasswordCellRenderer;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseDBConnections;
import emc.enums.base.databaseconnection.DBConnection;
import emc.enums.base.databaseconnection.DBDrivers;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class BaseDataBaseConnectionForm extends BaseInternalFrame {

    private emcDataRelationManagerUpdate dataRelation;

    public BaseDataBaseConnectionForm(EMCUserData userData) {
        super("DB Connection", true, true, true, true, userData);
        this.setBounds(20, 20, 950, 290);
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(new BaseDBConnections(), userData), userData);
        dataRelation.setTheForm(this);
        this.setDataManager(dataRelation);
        dataRelation.setFormTextId1("connectionId");
        dataRelation.setFormTextId2("companyId");
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Connection", tablePane());
        this.add(tabbedPanetop);
    }

    private emcTablePanelUpdate tablePane() {
        List keys = new ArrayList();
        keys.add("connectionId");
        keys.add("connectionType");
        keys.add("databaseVender");
        keys.add("driver");
        keys.add("server");
        keys.add("port");
        keys.add("databaseName");
        keys.add("userName");
        keys.add("dbPassword");
        emcTableModelUpdate model = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        table.setComboBoxLookupToColumn("connectionId", new emcJComboBox(DBConnection.values()));
        table.setComboBoxLookupToColumn("driver", new emcJComboBox(DBDrivers.values()));
        table.setColumnCellRenderer("dbPassword", new EMCJPasswordCellRenderer());
        dataRelation.setMainTableComponent(table);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(table);
        dataRelation.setTablePanel(topscroll);
        return topscroll;
    }
}
