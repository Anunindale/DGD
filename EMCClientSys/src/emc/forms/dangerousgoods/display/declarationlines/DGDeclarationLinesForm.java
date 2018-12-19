/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.dangerousgoods.display.declarationlines;

import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJSplitPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcMenuButton;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.EMCLookupRelationManager;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.dangerousgoods.DGDContacts;
import emc.entity.dangerousgoods.DGDUN;
import emc.entity.dangerousgoods.DGDeclarationLines;
import emc.entity.dangerousgoods.DGDeclarationMaster;
import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.dangerousgoods.display.resources.DGDLRMLines;
import emc.forms.dangerousgoods.display.resources.DGDLRMMaster;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.dangerousgoods.menuitems.display.DGDContactsMI;
import emc.menus.dangerousgoods.menuitems.display.DGDUNMI;
import emc.menus.sop.menuitems.display.SOPCustomersMenu;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JSplitPane;

/**
 *
 * @author pj
 */
public class DGDeclarationLinesForm extends BaseInternalFrame{
    
    private EMCUserData masterUD;
    private emcDataRelationManagerUpdate masterDRM;
    private EMCUserData linesUD;
    private emcDataRelationManagerUpdate linesDRM;
    private EMCLookupRelationManager lrmMaster;
    private EMCLookupRelationManager lrmLines;

    public DGDeclarationLinesForm(EMCUserData userData) {
        super("Dangerous Goods Declarations", true, true, true, true, userData);
        this.setBounds(20, 20, 1000, 600);
        //Master
        masterUD = userData.copyUserDataAndDataList();
        masterDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DG.getId(), new DGDeclarationMaster(), masterUD), masterUD)
        {               
            @Override
            public EMCUserData generateRelatedFormUserData(EMCUserData formUserData, int Index)
            {
                formUserData = super.generateRelatedFormUserData(formUserData, Index);
                
                switch(Index)
                {
                    case 0: 
                        Object decNum = super.getLastFieldValueAt("decNumber");
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DGDeclarationLines.class);
                        if(decNum != null) query.addAnd("decNumber", decNum);
                        
                        EMCQuery query2 = new EMCQuery(enumQueryTypes.SELECT, DGDUN.class);
                        query2.addAnd("lineNumber", null);
                        
                        formUserData.setUserData(0, query2);
                        formUserData.setUserData(9, query);
                    break;
                    default:
                    break;
                }
                return formUserData;
            }
        
            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrmMaster != null) {
                    lrmMaster.doRowChanged(masterDRM);
                }
            }

            @Override
            public void setFieldValueAt(int rowIndex, String columnIndex, Object aValue) {
                super.setFieldValueAt(rowIndex, columnIndex, aValue);
                if (columnIndex.equals("customer") && lrmMaster != null) {
                    lrmMaster.doRowChanged(masterDRM);
                }
            }
        };
        masterDRM.setTheForm(this);
        this.setDataManager(masterDRM);
        masterDRM.setFormTextId1("defConsignor");
        masterDRM.setFormTextId2("defOperator");
        //Lines
        linesUD = userData.copyUserData();
        linesDRM = new emcDataRelationManagerUpdate(new emcGenericDataSourceUpdate(enumEMCModules.DG.getId(), new DGDeclarationLines(), linesUD), linesUD)
        {
            @Override
            public void doRelation(int rowIndex) {
                super.doRelation(rowIndex);
                if (lrmLines != null) {
                    lrmLines.doRowChanged(linesDRM);
                }
            }
        };
        linesDRM.setTheForm(this);
        linesDRM.setFormTextId1("consignee");
        linesDRM.setFormTextId2("operator");
        //Link
        masterDRM.setLinesTable(linesDRM);
        masterDRM.setHeaderColumnID("decNumber");
        linesDRM.setHeaderTable(masterDRM);
        linesDRM.setRelationColumnToHeader("decNumber");
                
        initFrame();
    }

    private void initFrame() {
        emcJTabbedPane masterTableTab = new emcJTabbedPane();
        masterTableTab.add("Customer", masterTablePane());

        emcJPanel contentPane = new emcJPanel(new BorderLayout());
        
        emcJTabbedPane linesTableTab = new emcJTabbedPane();
        linesTableTab.add("Contacts", linestablePane());
       
        emcJSplitPane topBottomSplit = new emcJSplitPane(JSplitPane.VERTICAL_SPLIT, masterTableTab, linesTableTab);
        topBottomSplit.setDividerLocation(350);
       
        contentPane.add(topBottomSplit);
        contentPane.add(createButtons(), BorderLayout.EAST);
        this.setContentPane(contentPane);
    }

    private emcTablePanelUpdate masterTablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("decNumber");
        keys.add("customer");
        keys.add("defConsignor");
        keys.add("defOperator");
        emcTableModelUpdate model = new emcTableModelUpdate(masterDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        
        //Customer lookup
        EMCLookupJTableComponent customerlkp = new EMCLookupJTableComponent(new SOPCustomersMenu());
        customerlkp.setPopup(new EMCLookupPopup(new SOPCustomers(), "customerId", masterUD));
        table.setLookupToColumn("customer", customerlkp);
        
        //Default Consignor and Operator Lookups
        EMCLookupJTableComponent defConsignorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        EMCLookupJTableComponent defOperatorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        lrmMaster = new DGDLRMMaster();
        lrmMaster.addLookup(defConsignorlkp, "defconsignorlkp");
        lrmMaster.addLookup(defOperatorlkp, "defoperatorlkp");
        lrmMaster.initializeLookups();
        
        /*DefConsignor Lookup*/
        
        List<String> consignorlkpKeys = new ArrayList<>();
        consignorlkpKeys.add("contactName");
        consignorlkpKeys.add("company");
        
        EMCLookupPopup defConsignorPop = new EMCLookupPopup(new DGDContacts(), "contactName", consignorlkpKeys, masterUD);        
        defConsignorlkp.setPopup(defConsignorPop);
                
        table.setLookupToColumn("defConsignor", defConsignorlkp);

        /**/
        
        /*DefOperator Lookup*/
        
        List<String> operatorlkpKeys = new ArrayList<>();
        operatorlkpKeys.add("contactName");
        operatorlkpKeys.add("company");
        
        EMCLookupPopup defOperatorPop = new EMCLookupPopup(new DGDContacts(), "contactName", operatorlkpKeys, masterUD);
        defOperatorlkp.setPopup(defOperatorPop);
                
        table.setLookupToColumn("defOperator", defOperatorlkp);
        
        /**/
        
        masterDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        masterDRM.setTablePanel(tableScroll);
        return tableScroll;

    }

    private emcTablePanelUpdate linestablePane() {
        List<String> keys = new ArrayList<String>();
        keys.add("lineNumber");
        keys.add("description");
        keys.add("consignor");
        keys.add("consignee");
        keys.add("operator");
        keys.add("productOwner");
        keys.add("productCustodian");
        keys.add("productManufacturer");
        keys.add("contractingParty");
        
        emcTableModelUpdate model = new emcTableModelUpdate(linesDRM, keys);
        emcJTableUpdate table = new emcJTableUpdate(model);
        
        /*Lookups*/

        List<String> lkpKeys = new ArrayList<>();
        lkpKeys.add("contactName");
        lkpKeys.add("company");
        
        EMCLookupJTableComponent consignorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        consignorlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
        
        EMCLookupJTableComponent consigneelkp = new EMCLookupJTableComponent(new DGDContactsMI());
        consigneelkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
        
        EMCLookupJTableComponent operatorlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        operatorlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
        
        EMCLookupJTableComponent productownerlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        productownerlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
        
        EMCLookupJTableComponent productcustodianlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        productcustodianlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
        
        EMCLookupJTableComponent productmanufacturerlkp = new EMCLookupJTableComponent(new DGDContactsMI());
        productmanufacturerlkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
        
        EMCLookupJTableComponent contractingpartylkp = new EMCLookupJTableComponent(new DGDContactsMI());
        contractingpartylkp.setPopup(new EMCLookupPopup(new DGDContacts(), "contactName", lkpKeys, linesUD));
                
        lrmLines = new DGDLRMLines();
        lrmLines.addLookup(consignorlkp, "consignor");
        lrmLines.addLookup(consigneelkp, "consignee");
        lrmLines.addLookup(operatorlkp, "operator");
        lrmLines.addLookup(productownerlkp, "productowner");
        lrmLines.addLookup(productcustodianlkp, "productcustodian");
        lrmLines.addLookup(productmanufacturerlkp, "productmanufacturer");
        lrmLines.addLookup(contractingpartylkp, "contractingparty");
        
        lrmLines.initializeLookups();
        
        table.setLookupToColumn("consignor", consignorlkp);
        table.setLookupToColumn("consignee", consigneelkp);
        table.setLookupToColumn("operator", operatorlkp);
        table.setLookupToColumn("productOwner", productownerlkp);
        table.setLookupToColumn("productCustodian", productcustodianlkp);
        table.setLookupToColumn("productManufacturer", productmanufacturerlkp);
        table.setLookupToColumn("contractingParty", contractingpartylkp);
        
        linesDRM.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        linesDRM.setTablePanel(tableScroll);
        return tableScroll;
    }

    private emcJPanel createButtons()
    {
        List<emcJButton> buttons = new ArrayList<>();
        
        DGDUNMI unForm = new DGDUNMI();
        buttons.add(0, new emcMenuButton("UN", unForm, this, 0, false));
       
        return emcSetGridBagConstraints.createButtonPanel(buttons);
    }
    
}
