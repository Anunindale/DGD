/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.inventory.display.settlement;

import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDRMViewOnly;
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
public class SettlementHistoryForm extends BaseInternalFrame {
    private emcJPanel pnlBrandGroups = new emcJPanel();

     //DataSource
    private emcDRMViewOnly dataRelation;

    public SettlementHistoryForm(EMCUserData userData){
        super("Settlement History", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);

        try{

            dataRelation = new emcDRMViewOnly(new emcGenericDataSourceUpdate(
                        enumEMCModules.INVENTORY.getId(),new emc.entity.inventory.stocksettlement.InventoryStockSettlementHistory(),userData),userData);
            this.setDataManager(dataRelation);

            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("settlementId");
            dataRelation.setFormTextId2("lineNumber");

        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }

    private void tabBrandGroups(){
        List keys = new ArrayList();
        keys.add("lineNumber");
        keys.add("settlementId");
        keys.add("inTxRecordId");
        keys.add("outTxUsedInSettlement");
        keys.add("wasInClosed");
        keys.add("wasOutClosed");

        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys){

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }

        };
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
        tabbedPanetop.add("Settlement History",this.pnlBrandGroups);
        this.add(tabbedPanetop);
    }
}
