package emc.forms.app;

import emc.app.components.emcJButton;
import emc.app.components.emcJCheckBox;
import emc.app.components.emcJLabel;
import emc.app.components.emcJPanel;
import emc.app.components.emcSetGridBagConstraints;
import emc.app.components.emcTablePanelUpdate;
import emc.app.components.emctable.stock.StockDRM;
import emc.app.components.emctable.stock.ViewOnlyStockDRM;
import emc.app.scrollabledesktop.BaseInternalFrame;
import emc.framework.EMCUserData;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.plaf.DimensionUIResource;

/**
 * 
 * @Author wikus
 */
public class InventoryViewForm extends BaseInternalFrame {

    private emcJPanel pane = new emcJPanel();
    private StockDRM parentDRM;
    private emcJCheckBox config;
    private emcJCheckBox colour;
    private emcJCheckBox size;
    private emcJCheckBox warehouse;
    private emcJCheckBox batch;
    private emcJCheckBox serialNo;
    private emcJCheckBox location;
    private emcJCheckBox pallet;

    public InventoryViewForm(EMCUserData userData) {
        super("Columns", true, true, true, true, userData);
        this.setBounds(20, 20, 250, 300);
        initFrame();
    }

    private void initFrame() {
        config = new emcJCheckBox("Config");
        colour = new emcJCheckBox("Colour");
        size = new emcJCheckBox("Size");
        warehouse = new emcJCheckBox("Warehouse");
        batch = new emcJCheckBox("Batch");
        serialNo = new emcJCheckBox("Serial No");
        location = new emcJCheckBox("Location");
        pallet = new emcJCheckBox("Pallet");

        emcJButton ok = new emcJButton("OK") {

            @Override
            public void doActionPerformed(ActionEvent evt) {
                addColumns();
                emcTablePanelUpdate panelUpdate = (emcTablePanelUpdate) parentDRM.getMainTableComponent().getParent().getParent().getParent();
                if (panelUpdate.isSearchVisible()) {
                    panelUpdate.setSearchTable(parentDRM.getMainTableComponent());
                    panelUpdate.makeSearchVisible();
                }
            }
        };

        ok.setSize(new DimensionUIResource(80, 25));

        pane.setLayout(new GridBagLayout());
        int y = 0;
        GridBagConstraints gbc = emcSetGridBagConstraints.createStandard(0, y, 0.1, GridBagConstraints.FIRST_LINE_START);
        gbc.fill = GridBagConstraints.VERTICAL;
        emcJLabel label = new emcJLabel();
        label.setPreferredSize(new Dimension(60, 250));
        pane.add(label, gbc);

        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(config, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(colour, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(size, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(warehouse, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(batch, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(serialNo, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(location, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(pallet, gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(new emcJLabel(), gbc);

        y++;
        gbc = emcSetGridBagConstraints.changePosition(gbc, 2, y, 0.1);
        pane.add(ok, gbc);

        this.add(pane);
    }

    private void addColumns() {

        ActiveDimColumnData columns = new ActiveDimColumnData();

        if (this.config.isSelected()) {
            columns.setDimention1(true);
        }
        if (this.colour.isSelected()) {
            columns.setDimention3(true);
        }
        if (this.size.isSelected()) {
            columns.setDimention2(true);
        }
        if (this.warehouse.isSelected()) {
            columns.setWarehouse(true);
        }
        if (this.batch.isSelected()) {
            columns.setBatch(true);
        }
        if (this.serialNo.isSelected()) {
            columns.setSerialNo(true);
        }
        if (this.location.isSelected()) {
            columns.setLocation(true);
        }
        if (this.pallet.isSelected()) {
            columns.setPallet(true);
        }

        parentDRM.setTableColumns(columns);

        this.dispose();

    }

    public void setParentDRM(Object drm) {
        if (drm instanceof StockDRM) {
            this.parentDRM = (StockDRM) drm;
        }
        if (drm instanceof ViewOnlyStockDRM) {
            this.parentDRM = (ViewOnlyStockDRM) drm;
        }
        List<Boolean> checked = parentDRM.setChecked();
        config.setSelected(checked.get(0));
        colour.setSelected(checked.get(1));
        size.setSelected(checked.get(2));
        warehouse.setSelected(checked.get(3));
        batch.setSelected(checked.get(4));
        serialNo.setSelected(checked.get(5));
        location.setSelected(checked.get(6));
        pallet.setSelected(checked.get(7));

        List<Boolean> contains = parentDRM.containsKey();
        config.setEnabled(contains.get(0));
        colour.setEnabled(contains.get(1));
        size.setEnabled(contains.get(2));
        warehouse.setEnabled(contains.get(3));
        batch.setEnabled(contains.get(4));
        serialNo.setEnabled(contains.get(5));
        location.setEnabled(contains.get(6));
        pallet.setEnabled(contains.get(7));
    }
}
