/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.creditors.display.termsofpayment;

import emc.app.components.emcJComboBox;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.enums.creditors.daysmonths.DaysMonths;
import emc.enums.creditors.principle.Principle;
import emc.enums.modules.enumEMCModules;
import emc.framework.EMCUserData;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author riaan
 */
public class TermsOfPaymentForm extends BaseInternalFrame {

    //Panel used on the form
    private emcJPanel pnlTermsOfPayment = new emcJPanel();
    
    //Data relation manager used by the form
    private emcDataRelationManagerUpdate dataRelation;
    
    /** Creates a new instance of VATCodeForm */
    public TermsOfPaymentForm(EMCUserData userData) {
        //Sets up the form
        super("Terms of Payment", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 290);
        
        //Sets up the data relation manager
        dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.CREDITORS.getId(), new emc.entity.creditors.CreditorsTermsOfPayment(), userData),userData);
        
        this.setDataManager(dataRelation);
        
        //Add the form to the data relation
        dataRelation.setTheForm(this);
        dataRelation.setFormTextId1("termsOfPaymentId");
        dataRelation.setFormTextId2("description");
        
        tabDiscountGroups();
        initFrame();
    }
    
     private void tabDiscountGroups(){
        List keys = new ArrayList();
        keys.add("termsOfPaymentId");
        keys.add("description");
        keys.add("principle");
        keys.add("daysOrMonths");
        keys.add("numberOf");
      
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        dataRelation.setMainTableComponent(toptable);

        toptable.setComboBoxLookupToColumn("principle", new emcJComboBox(Principle.values()));
        toptable.setComboBoxLookupToColumn("daysOrMonths", new emcJComboBox(DaysMonths.values()));

        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlTermsOfPayment.setLayout(new GridLayout(1,1));
        pnlTermsOfPayment.add(topscroll);
        dataRelation.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabbedPanetop.add("Terms of Payment", this.pnlTermsOfPayment); 
        this.add(tabbedPanetop);
    }
}
