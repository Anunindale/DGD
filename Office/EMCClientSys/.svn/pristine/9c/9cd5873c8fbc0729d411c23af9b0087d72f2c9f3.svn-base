/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.gl.display.currencies;

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
public class CurrenciesForm extends BaseInternalFrame {
    
    //Panel used on the form
    private emcJPanel pnlCurrencies = new emcJPanel();
    
    //Data relation manager used by the form
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of SupplierGroupsForm */
    public CurrenciesForm(EMCUserData userData) {
        
        //Sets up the form
        super("Currancy", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        //Sets up the data relation manager
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.GENERAL_LEDGER.getId(), new emc.entity.gl.GLCurrency(), userData),userData);
        
        this.setDataManager(dataRelation);
        
        //Add the form to the data relation
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("currency");
        dataRelation.setFormTextId2("descriptiveName");
        
        tabCurrencies();
        initFrame();
    }
    
    private void tabCurrencies(){
        List keys = new ArrayList();
        keys.add("currency");
        keys.add("descriptiveName");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlCurrencies.setLayout(new GridLayout(1,1));
        pnlCurrencies.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Currencies", this.pnlCurrencies); 
        this.add(tabbedPanetop);
    }
}
