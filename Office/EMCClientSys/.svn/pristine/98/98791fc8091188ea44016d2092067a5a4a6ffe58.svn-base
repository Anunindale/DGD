/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.qualitytest; 

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author wikus
 */
public class QualityTestTypeForm extends BaseInternalFrame {

    emcDataRelationManagerUpdate dataRelation;

    public QualityTestTypeForm(EMCUserData userData) {
        super("Quality Test Types", true, true, true, true, userData);
        setBounds(20, 20, 550, 290);
        try {
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                    enumEMCModules.POP.getId(), new emc.entity.pop.POPQualityTestType(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("testTypeId");
            dataRelation.setFormTextId2("description");
        }catch (Exception e) {
            
        }
        initFrame();
    }
    
    private void initFrame() {
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Quality Test Type", mainPane());
        this.add(tabbedPanetop);
    }

    private emcJPanel mainPane() {
        emcJPanel thePanel = new emcJPanel();

        List keys = new ArrayList();
        keys.add("testTypeId");
        keys.add("description");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation, keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        thePanel.setLayout(new GridLayout(1, 1));
        thePanel.add(topscroll);

        return thePanel;
    }
}
