/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.forms.base.display.unitsofmeasure;

import emc.app.components.base.UOMTypeDropDown;
import emc.app.components.emcHelpFile;
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
public class UnitsOfMeasureForm extends BaseInternalFrame {
    
    private emcJPanel pnlUnitsOfMeasure = new emcJPanel();
    
     //DataSource
    private emcDataRelationManagerUpdate dataRelation;
    
    public UnitsOfMeasureForm(EMCUserData userData){
        super("Units of Measure", true, true,true, true,userData);
        this.setBounds(20, 20, 550, 290);
        this.setHelpFile(new emcHelpFile("Base/SetupUnitsOfMeasure.html"));
        try{
            
            dataRelation = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(
                        enumEMCModules.BASE.getId(),new emc.entity.base.BaseUnitsOfMeasure(),userData),userData);
            this.setDataManager(dataRelation);
            //add the form to the data relation
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("unit");
            dataRelation.setFormTextId2("description");
        
        }catch (Exception e){
            e.printStackTrace();
        }
        initFrame();
    }
    
    private void tabUnitsOfMeasure(){
        List keys = new ArrayList();
        keys.add("unit");
        keys.add("description");
        keys.add("decimals");
        keys.add("type");
        emcTableModelUpdate m = new emcTableModelUpdate(this.dataRelation,keys);
        emcJTableUpdate toptable = new emcJTableUpdate(m);
        toptable.setComboBoxLookupToColumn(3, new UOMTypeDropDown());
        dataRelation.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        pnlUnitsOfMeasure.setLayout(new GridLayout(1,1));
        pnlUnitsOfMeasure.add(topscroll);
        this.setTablePanel(topscroll);
    }
    
    private void initFrame(){
        emcJTabbedPane tabbedPanetop = new emcJTabbedPane();
        tabUnitsOfMeasure();
        tabbedPanetop.add("Units of Measure",this.pnlUnitsOfMeasure); 
        this.add(tabbedPanetop);
    }
}
