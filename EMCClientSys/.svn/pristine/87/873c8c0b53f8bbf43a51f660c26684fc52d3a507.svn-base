/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.inventory.display.dimension1enquiry;

import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcDRMViewOnly;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.lookup.EMCLookup;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.app.wsmanager.EMCWSManager;
import emc.entity.inventory.dimension1enquiry.InventoryDimension1Enquiry;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.enums.enumQueryTypes;
import emc.framework.EMCCommandClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.methods.inventory.ServerInventoryMethods;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;

/**
 *
 * @author claudette
 */
public class InventoryDimension1EnquiryForm extends BaseInternalFrame {

    private emcDRMViewOnly dataManager;
    private EMCLookup lkpValue1;
    private EMCLookup lkpValue2;
    private EMCLookup lkpValue3;
    private EMCLookup lkpValue4;
    private EMCLookup lkpValue5;
    private long sessionId;

    public InventoryDimension1EnquiryForm(EMCUserData userData) {
        super("Where Used - Multi Colours", true, true, true, true, userData);
        //this.setHelpFile(new emcHelpFile(""));
        this.setBounds(20, 20, 780, 470);
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Enquiry.class);
        query.addAnd("dimension1", null);
        userData.setUserData(0, query);
        dataManager = new emcDRMViewOnly(new emcGenericDataSourceUpdate(new InventoryDimension1Enquiry(), userData), userData);
        dataManager.setTheForm(this);
        this.setDataManager(dataManager);
        dataManager.setFormTextId1("dimension1");
        dataManager.setFormTextId2("description");
        initFrame();
    }

    @Override
    public boolean doSaveOnClose() {
        boolean ret = super.doSaveOnClose();

        List toSend = new ArrayList();
        toSend.add(sessionId);

        EMCWSManager.executeGenericWS(new EMCCommandClass(ServerInventoryMethods.CLEAR_DIMENSION1ENQUIRYSESSION), toSend, dataManager.getUserData());

        return ret;
    }

    private void initFrame() {

        emcJPanel mainPanel = new emcJPanel(new BorderLayout());
        mainPanel.add(createColourPanel(), BorderLayout.NORTH);
        mainPanel.add(createConfigurationPanel(), BorderLayout.CENTER);
        this.setLayout(new BorderLayout());
        this.add(mainPanel, BorderLayout.CENTER);
        this.add(createButtonPanel(), BorderLayout.EAST);
    }

    private emcJPanel createColourPanel() {

        lkpValue1 = new EMCLookup(new Dimension3());
        lkpValue1.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", getUserData()));
        lkpValue2 = new EMCLookup(new Dimension3());
        lkpValue2.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", getUserData()));
        lkpValue3 = new EMCLookup(new Dimension3());
        lkpValue3.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", getUserData()));
        lkpValue4 = new EMCLookup(new Dimension3());
        lkpValue4.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", getUserData()));
        lkpValue5 = new EMCLookup(new Dimension3());
        lkpValue5.setPopup(new EMCLookupPopup(new InventoryDimension3(), "dimensionId", getUserData()));

        Component[][] components = {
            {new emcJLabel("Colour 1"), lkpValue1, new emcJLabel("Colour 2"), lkpValue2},
            {new emcJLabel("Colour 3"), lkpValue3, new emcJLabel("Colour 4"), lkpValue4},
            {new emcJLabel("Colour 5"), lkpValue5}
        };

        return emcSetGridBagConstraints.createSimplePanel(components, GridBagConstraints.NONE, true, "Colour");
    }

    private emcJPanel createConfigurationPanel() {
        emcJPanel panel = new emcJPanel(new GridLayout(1, 1));

        List<String> keys = new ArrayList<String>();

        keys.add("dimension1");
        keys.add("description");

        emcTableModelUpdate model = new emcTableModelUpdate(dataManager, keys);
        emcJTableUpdate table = new emcJTableUpdate(model) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table.setColumnCellEditor("dimension1", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));


        dataManager.setMainTableComponent(table);
        emcTablePanelUpdate tableScroll = new emcTablePanelUpdate(table);
        dataManager.setTablePanel(tableScroll);

        panel.add(tableScroll);
        panel.setBorder(BorderFactory.createTitledBorder("Configurations"));

        return panel;
    }

    private emcJPanel createButtonPanel() {

        emcJButton btnGetData = new emcJButton("Get Data") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                super.doActionPerformed(evt);
                EMCUserData userData = dataManager.getUserData();

                List values = new ArrayList();
                List toSend = new ArrayList();

                values.add(lkpValue1.getValue());
                values.add(lkpValue2.getValue());
                values.add(lkpValue3.getValue());
                values.add(lkpValue4.getValue());
                values.add(lkpValue5.getValue());

                toSend.add(values);

                toSend.add(sessionId);

                toSend = EMCWSManager.executeGenericWS(new EMCCommandClass(ServerInventoryMethods.POPULATE_INVENTORYDIMENSION1ENQUIRY), toSend, userData);

                if (toSend.size() > 1 && toSend.get(1) instanceof Long) {
                    long sessionId = (Long) toSend.get(1);
                    InventoryDimension1EnquiryForm.this.sessionId = sessionId;
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, InventoryDimension1Enquiry.class);

                    query.addAnd("sessionId", sessionId);
                    query.addOrderBy("dimension1");

                    userData.setUserData(0, query);
                    dataManager.setUserData(userData);
                    dataManager.setOriginalQuery(query);
                }
            }
        };

        List<emcJButton> buttons = new ArrayList<emcJButton>();
        buttons.add(btnGetData);

        return emcSetGridBagConstraints.createButtonPanel(buttons);

    }
}
