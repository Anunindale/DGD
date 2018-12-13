/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.base.display.recordInfo;

import emc.app.components.emcHelpFile;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emctable.DataRelationManagerInterface;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import javax.swing.JScrollPane;

/**
 *
 * @author rico
 */
public class recordInfoForm extends BaseInternalFrame {

    emcDataRelationManagerUpdate dataRelation;
    emcJPanel recordInfo = new emcJPanel();
    recordInfoTableModel m;

    public recordInfoForm(EMCUserData userData) {
        super("Record Info", true, true, true, true, userData);
        this.setBounds(20, 20, 350, 390);
        this.setHelpFile(new emcHelpFile("Base/SystemViewRecordInfo.html"));
        this.setRecordInfoForm(true);
        initFrame();
    }

    public void setDRMFromHost(DataRelationManagerInterface drmInt) {
        m.setDataRelation(drmInt);
        this.repaintFrameTitle(drmInt.getEntityClass().getSimpleName());
    }

    private void tabRecordOverview() {
        m = new recordInfoTableModel();
        emcJTableUpdate toptable = new emcJTableUpdate(m, 0);
        JScrollPane topscroll = new JScrollPane(toptable);
        recordInfo.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        recordInfo.setLayout(new GridLayout(1, 1));
        tabRecordOverview();
        tabbedPanetop.add("Record Overview", this.recordInfo);
        this.add(tabbedPanetop);
    }
}
