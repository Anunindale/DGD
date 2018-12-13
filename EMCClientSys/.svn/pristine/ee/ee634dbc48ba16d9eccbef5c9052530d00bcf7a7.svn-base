/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.pop.display.posting.postserialbatch;

import emc.app.components.documents.EMCDoubleDocument;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcJScrollPane;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcJTextField;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.commands.EMCCommands;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.inventory.serialbatch.InventoryRemoveSerialBatch;
import emc.entity.inventory.serialbatch.InventorySerialBatch;
import emc.entity.inventory.transactions.InventorySummary;
import emc.entity.inventory.transactions.datasource.InventorySummaryDS;
import emc.enums.base.uom.UOMTypes;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.enums.pop.posting.DocumentTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import emc.menus.inventory.menuitems.display.OnHand;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author wikus
 */
public class PostSerialBatchForm extends BaseInternalFrame {

    private SerialBatchDRM dataRelation;
    private emcJTextField edtItem;
    private emcJTextField edtDescription;
    private emcJTextField edtDimension1;
    private emcJTextField edtDimension3;
    private emcJTextField edtDimension2;
    private emcJTextField edtWarehouse;
    private emcJTextField edtTotalQty;
    private emcJTextField edtRegQty;
    private EMCUserData userData;
    private int classRep = 0;

    public PostSerialBatchForm(EMCUserData userData) {
        super("Register", true, true, true, true, userData);
        this.setBounds(20, 20, 550, 490);
        classRep = 0; // 0:InventorySerialBatch; 1:InventoryRemoveSerialBatch;
        if (userData.getUserData() != null && userData.getUserData().size() >= 8 && !Functions.checkBlank(userData.getUserData(8))) {
            if (userData.getUserData(8).equals(DocumentTypes.RETURN_GOODS.toString())) {
                classRep = 1;
            }
        }
        try {
            this.userData = userData.copyUserDataAndDataList();
            dataRelation = new SerialBatchDRM(new emcGenericDataSourceUpdate(
                    enumEMCModules.INVENTORY.getId(), classRep == 0 ? new InventorySerialBatch() : new InventoryRemoveSerialBatch(), userData), userData);
            this.setDataManager(dataRelation);
            dataRelation.setTheForm(this);
            dataRelation.setFormTextId1("batch");
            dataRelation.setFormTextId2("serial");
        } catch (Exception e) {
        }
        initFrame();
        setEditValues(userData);
    }

    private void addLookups(emcJTableUpdate table, EMCUserData userData) {
        List<String> keys = new ArrayList<String>();
        keys.add("batch");
        keys.add("serialNo");
        keys.add("location");
        keys.add("physicalAvailable");
        keys.add("qcAvailable");
        keys.add("recAvailable");
        keys.add("QCStatus");

        final Map<String, String> keysMap = new HashMap<String, String>();
        keysMap.put("batch", "batch");
        keysMap.put("serial", "serialNo");
        keysMap.put("location", "location");
        keysMap.put("quantity", "");

        OnHand onHandMenu = new OnHand();
        InventorySummaryDS summaryEntity = new InventorySummaryDS();

        EMCUserData popupUD = userData.copyUserData();
        popupUD.setUserData(11, true);
        popupUD.setUserData(12, true);
        popupUD.setUserData(13, true);
        popupUD.setUserData(15, true);
        popupUD.setUserData(16, true);
        popupUD.setUserData(17, true);

        EMCLookupJTableComponent lkpBatch = new EMCLookupJTableComponent(onHandMenu);
        ReturnMultiValuePopup batchPop = new ReturnMultiValuePopup(summaryEntity, "batch", keys, dataRelation, keysMap, popupUD);
        lkpBatch.setPopup(batchPop);
        table.setLookupToColumn("batch", lkpBatch);

        EMCLookupJTableComponent lkpSerial = new EMCLookupJTableComponent(onHandMenu);
        ReturnMultiValuePopup serialPop = new ReturnMultiValuePopup(summaryEntity, "serialNo", keys, dataRelation, keysMap, popupUD);
        lkpSerial.setPopup(serialPop);
        table.setLookupToColumn("serial", lkpSerial);

        EMCLookupJTableComponent lkpLocation = new EMCLookupJTableComponent(onHandMenu);
        ReturnMultiValuePopup locationPop = new ReturnMultiValuePopup(summaryEntity, "location", keys, dataRelation, keysMap, popupUD);
        lkpLocation.setPopup(locationPop);
        table.setLookupToColumn("location", lkpLocation);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventorySummary.class);
        query.addAnd("itemId", userData.getUserData(14));
        query.addAnd("dimension1", userData.getUserData(10));
        query.addAnd("dimension2", userData.getUserData(11));
        query.addAnd("dimension3", userData.getUserData(12));
        query.addAnd("warehouse", userData.getUserData(13));
        query.addGroupBy("QCStatus");

        batchPop.setQuery(query);
        serialPop.setQuery(query);
        locationPop.setQuery(query);
    }

    private void initFrame() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Register"));
        thePanel.setLayout(new BorderLayout(1, 1));
        thePanel.add(pnlTop(), BorderLayout.NORTH);
        thePanel.add(pnlBottom(), BorderLayout.CENTER);
        emcJTabbedPane tabbed = new emcJTabbedPane();
        tabbed.add("Register", thePanel);
        this.add(tabbed);
    }

    private emcJPanel pnlTop() {
        edtItem = new emcJTextField();
        edtItem.setEditable(false);
        edtDescription = new emcJTextField();
        edtDescription.setEditable(false);
        edtTotalQty = new emcJTextField();
        edtDimension1 = new emcJTextField();
        edtDimension1.setEditable(false);
        edtDimension2 = new emcJTextField();
        edtDimension2.setEditable(false);
        edtDimension3 = new emcJTextField();
        edtDimension3.setEditable(false);
        edtWarehouse = new emcJTextField();
        edtWarehouse.setEditable(false);
        edtTotalQty.setEditable(false);
        edtRegQty = new emcJTextField();
        edtRegQty.setEditable(false);
        edtRegQty.setDocumentIgnorePermissions(new EMCDoubleDocument());
        Component[][] components = {{new emcJLabel("Item"), edtItem, new emcJLabel("Description"), edtDescription},
                                    {new emcJLabel("Config"), edtDimension1, new emcJLabel("Colour"), edtDimension3},
                                    {new emcJLabel("Size"), edtDimension2, new emcJLabel("Warehouse"), edtWarehouse},
                                    {new emcJLabel("Total Qty"), edtTotalQty, new emcJLabel("Registered Qty"), edtRegQty}};
        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.BOTH, false);
    }

    private emcJPanel pnlBottom() {
        emcJPanel thePanel = new emcJPanel();
        thePanel.setLayout(new BorderLayout(1, 1));
        List keys = new ArrayList();
        keys.add("batch");
        keys.add("serial");
        if (classRep == 1) keys.add("location");
        if (classRep == 0) keys.add("width");
        if (classRep == 0) keys.add("widthUOM");
        keys.add("quantity");

        emcTableModelUpdate m = new emcTableModelUpdate(dataRelation, keys);
        emcJTableUpdate table = new emcJTableUpdate(m);
        if (classRep == 1) {
            addLookups(table, userData);
        } else {
            //Create UOM lookup
            EMCLookupJTableComponent lkpUom = new EMCLookupJTableComponent(new UnitsOfMeasure());
            EMCLookupPopup uomPopup = new EMCLookupPopup(new BaseUnitsOfMeasure(), "unit", userData);
            lkpUom.setPopup(uomPopup);
            EMCQuery uomQuery = new EMCQuery(enumQueryTypes.SELECT, BaseUnitsOfMeasure.class.getName());
            uomQuery.addAnd("type", UOMTypes.LENGTH.toString());
            lkpUom.setTheQuery(uomQuery);
            table.setLookupToColumn("widthUOM", lkpUom);
        }

        dataRelation.setMainTableComponent(table);
        emcJScrollPane tablescroll = new emcJScrollPane(table);
        thePanel.add(tablescroll);
        return thePanel;
    }

    private void setEditValues(EMCUserData userData) {
        edtItem.setText(userData.getUserData().get(2).toString());
        edtDescription.setText(userData.getUserData().get(3).toString());
        edtTotalQty.setText(userData.getUserData().get(4).toString());
        dataRelation.setTransId(userData.getUserData().get(5).toString());
        dataRelation.setRegQty();
        edtDimension1.setText((String) userData.getUserData().get(10));
        edtDimension2.setText((String) userData.getUserData().get(11));
        edtDimension3.setText((String) userData.getUserData().get(12));
        edtWarehouse.setText((String) userData.getUserData().get(13));

    }

    public void setRegQty(double qty) {
        this.edtRegQty.setText(String.valueOf(qty));
    }

    @Override
    public boolean doSaveOnClose() {
        boolean ret = super.doSaveOnClose();
        if (ret) {
            Object masterId = dataRelation.getFieldValueAt(0, "masterId");
            Object transId = dataRelation.getFieldValueAt(0, "transId");
            if (!(Functions.checkBlank(transId) || Functions.checkBlank(masterId)) && classRep == 1) {
                EMCCommandClass cmd = new EMCCommandClass(EMCCommands.SERVER_GENERAL_COMMAND.getId(), enumEMCModules.INVENTORY.getId(), ServerInventoryMethods.CHECK_REMOVED_REG.toString());
                List toSend = new ArrayList();
                toSend.add(masterId);
                toSend.add(transId);
                ret = (Boolean) EMCWSManager.executeGenericWS(cmd, toSend, dataRelation.getUserData()).get(1);
                if (!ret) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The registered and total quantities don't match.", dataRelation.getUserData());
                }
            }
        }
        return ret;
    }

    @Override
    public void populateUserDataForPermissions(EMCUserData userData) {
        for (int i = 0; i < 13; i++) {
            userData.setUserData(i, "");

        }
    }

    public int getClassRep() {
        return classRep;
    }
}
