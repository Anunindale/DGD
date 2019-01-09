package emc.forms.inventory.display.additionaldimensions;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import emc.app.components.documents.EMCStringDocument;
import emc.app.components.emcJButton;
import emc.app.components.emcJPanel;
import emc.app.components.emcJTabbedPane;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.editors.EMCGoToMainTableEditor;
import emc.app.components.emctable.emcGenericDataSourceUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.components.emctable.emcTableModelUpdate;
import emc.app.components.emctable.stock.StockDRMParameters;
import emc.app.components.inventory.emcStockButton;
import emc.app.components.jtablelookup.EMCLookupJTableComponent;
import emc.app.components.lookup.popup.EMCLookupPopup;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.entity.base.BaseUnitsOfMeasure;
import emc.entity.inventory.dimensions.additionaldimensions.datasource.InventoryAdditionalDimensionsDS;
import emc.enums.base.uom.UOMTypes;
import emc.enums.enumQueryTypes;
import emc.enums.modules.enumEMCModules;
import emc.forms.inventory.display.additionaldimensions.resources.AdditionalDimensionsDRM;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.menus.base.menuItems.display.UnitsOfMeasure;
import emc.menus.inventory.menuitems.display.Dimension1;
import emc.menus.inventory.menuitems.display.Dimension2;
import emc.menus.inventory.menuitems.display.Dimension3;
import emc.menus.inventory.menuitems.display.ItemBatch;
import emc.menus.inventory.menuitems.display.ItemMaster;
import emc.menus.inventory.menuitems.display.ItemSerial;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

/**
 * @Name         : AdditionalDimensionsForm.java
 *
 * @Date         : 26 Jun 2009
 * 
 * @Description  : Form used to edit additional dimensions
 *
 * @author       : riaan
 */
public class AdditionalDimensionsForm extends BaseInternalFrame {

    private AdditionalDimensionsDRM drm;
    
    private EMCLookupJTableComponent tblLkpUOM;

    /** Creates a new instance of AdditionalDimensionsForm.  */
    public AdditionalDimensionsForm(EMCUserData userData) {
        super("Additional Dimensions", true, true, true, true, userData);
        this.setBounds(20, 20, 750, 290);

        StockDRMParameters param = new StockDRMParameters("itemId", "dimension1", "dimension2", "dimension3", null);
        param.setBatchField("batch");
        param.setSerialField("serial");
        
        drm = new AdditionalDimensionsDRM(new emcGenericDataSourceUpdate(enumEMCModules.INVENTORY.getId(), new InventoryAdditionalDimensionsDS(), userData), param, userData);

        this.setDataManager(drm);

        drm.setTheForm(this);
        drm.setFormTextId1("batch");
        drm.setFormTextId2("serial");

        initLookups(userData);
        initFrame();
    }

    /** Initializes lookups. */
    private void initLookups(EMCUserData userData) {
        EMCLookupPopup uomPopup = new EMCLookupPopup(new BaseUnitsOfMeasure(), "unit", userData);
        tblLkpUOM = new EMCLookupJTableComponent(new UnitsOfMeasure());
        tblLkpUOM.setPopup(uomPopup);
        //Only allow length units of measure.
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseUnitsOfMeasure.class.getName());
        query.addAnd("type", UOMTypes.LENGTH.toString());
        tblLkpUOM.setTheQuery(query);
    }
    
    /** Initializes the frame. */
    private void initFrame() {
        this.setLayout(new BorderLayout());
        emcJTabbedPane tabs = new emcJTabbedPane();
        tabs.add("Overview", createOverviewPanel());
        this.add(tabs, BorderLayout.CENTER);
        this.add(createButtonsPanel(), BorderLayout.EAST);
    }

    /** Creates the Overview panel. */
    private emcJPanel createOverviewPanel() {
        emcJPanel panel = new emcJPanel();

        List keys = new ArrayList();
        keys.add("itemRef");
        keys.add("dimension1");
        keys.add("dimension3");
        keys.add("dimension2");
        keys.add("batch");
        keys.add("serial");
        keys.add("width");
        keys.add("widthUOM");

        emcTableModelUpdate m = new emcTableModelUpdate(drm, keys);

        emcJTableUpdate toptable = new emcJTableUpdate(m);

        toptable.setColumnCellEditor("itemRef", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemMaster()));
        toptable.setColumnCellEditor("dimension1", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension1()));
        toptable.setColumnCellEditor("dimension2", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension2()));
        toptable.setColumnCellEditor("dimension3", new EMCGoToMainTableEditor(new EMCStringDocument(), new Dimension3()));
        toptable.setColumnCellEditor("batch", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemBatch()));
        toptable.setColumnCellEditor("serial", new EMCGoToMainTableEditor(new EMCStringDocument(), new ItemSerial()));
        
        toptable.setLookupToColumn("widthUOM", tblLkpUOM);
        
        toptable.setColumnEditable("dimension1", false);
        toptable.setColumnEditable("dimension3", false);
        toptable.setColumnEditable("dimension2", false);
        toptable.setColumnEditable("batch", false);
        toptable.setColumnEditable("serial", false);
        
        drm.setMainTableComponent(toptable);
        emcTablePanelUpdate topscroll = new emcTablePanelUpdate(toptable);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(topscroll);
        this.setTablePanel(topscroll);

        return panel;
    }
    
    /** Creates the buttons panel. */
    private emcJPanel createButtonsPanel() {
        List<emcJButton> buttonList = new ArrayList<emcJButton>();
        buttonList.add(new emcStockButton(this, false));
        
        return emcSetGridBagConstraints.createButtonPanel(buttonList);
    }
}
