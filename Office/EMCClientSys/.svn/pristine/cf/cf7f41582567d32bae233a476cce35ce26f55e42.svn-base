/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.pop.display.extrachargegroups;

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
 * @author riaan
 */
public class ExtraChargeGroupsForm extends BaseInternalFrame {

    //Panel used on the form
    private emcJPanel pnlExtraChargeGroups = new emcJPanel();
    
    //Data relation manager used by the form
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of VATCodeForm */
    public ExtraChargeGroupsForm(EMCUserData userData) {
        //Sets up the form
        super("Extra Charge Groups", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        //Sets up the data relation manager
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.POP.getId(), new emc.entity.pop.POPExtraChargeGroup(), userData),userData);
        
        this.setDataManager(dataRelation);
        
        //Add the form to the data relation
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("extraChargeGroupId");
        dataRelation.setFormTextId2("description");
        
        tabDiscountGroups();
        initFrame();
    }
    
     private void tabDiscountGroups(){
        List keys = new ArrayList();
        keys.add("extraChargeGroupId");
        keys.add("description");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlExtraChargeGroups.setLayout(new GridLayout(1,1));
        pnlExtraChargeGroups.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Discount Groups", this.pnlExtraChargeGroups); 
        this.add(tabbedPanetop);
    }
}
