/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.systemtables;

import emc.app.components.tables.EMCMapComboBoxFactory;
import emc.app.components.emcHelpFile;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.forms.base.display.systemtables.resources.RefreshButton;
import emc.framework.EMCUserData;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.SoftBevelBorder;

/**
 *
 * @author riaan
 */
public class SystemTablesForm extends BaseInternalFrame {

    private emcJPanel pnlSystemTables = new emcJPanel();
    //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    private EMCUserData userData;

    public SystemTablesForm(EMCUserData userData) {
        super("System Tables", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupSystemTables.html"));
        this.userData = userData;
        try {

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.BASE.getId(), new emc.entity.base.BaseSystemTable(), userData), userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("tableName");
            dataRelation.setFormTextId2("description");

        } catch (Exception e) {
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabSystemTables() {
        List keys = new ArrayList();
        keys.add("tableName");
        keys.add("description");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);

        toptable.setEMCMapComboBoxToColumn("tableName", EMCMapComboBoxFactory.getTablesMapComboBox(userData));

        pnlSystemTables.setLayout(new GridLayout(1, 1));
        pnlSystemTables.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private emcJPanel buttonPanel() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new GridBagLayout());
        thePanel.setBorder(new SoftBevelBorder(BevelBorder.RAISED));
        GridBagConstraints gbc;
        int y = 0;
        gbc = emcSetGridBagConstraints.createStandard(0, y, 1.0, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        RefreshButton refressButton = new RefreshButton("Refresh", null, this, 0, false);
        thePanel.add(refressButton, gbc);
        y++;
        thePanel.add(new emcJLabel(), emcSetGridBagConstraints.endPanel(y));
        thePanel.setPreferredSize(new Dimension(120, 25));
        return thePanel;
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        emcJPanel mainPanel = new emcJPanel();
        mainPanel.setLayout(new BorderLayout(1, 1));
        tabSystemTables();
        mainPanel.add(this.pnlSystemTables, BorderLayout.CENTER);
        mainPanel.add(buttonPanel(), BorderLayout.EAST);
        tabbedPanetop.add("System Tables", mainPanel);
        this.add(tabbedPanetop);
    }
}
