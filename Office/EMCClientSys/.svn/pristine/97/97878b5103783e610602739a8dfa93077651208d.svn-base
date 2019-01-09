/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.ageingbins;

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
 * @author rico
 */
public class AgeingBinsForm extends BaseInternalFrame {
private emcJPanel pnlBrandGroups = new emcJPanel();

     //DataSource
    private emcDataRelationManagerUpdate dataRelation;

    public AgeingBinsForm(EMCUserData userData){
        super("Age Bins", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);

        try{

            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.agebins.InventoryAgeBins(),userData),userData);
            this.setDataManager(dataRelation);

            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("ageBin");
            dataRelation.setFormTextId2("ageBinPrintDesc");

        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabBrandGroups(){
        List keys = new ArrayList();
        keys.add("ageBin");
        keys.add("ageBinPrintDesc");
        keys.add("numberOfDaysInBin");
        keys.add("binOrder");
        //keys.add("lastBin");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlBrandGroups.setLayout(new GridLayout(1,1));
        pnlBrandGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }

    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabBrandGroups();
        tabbedPanetop.add("Age Bins",this.pnlBrandGroups);
        this.add(tabbedPanetop);
    }
}

