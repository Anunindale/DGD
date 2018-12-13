/*
 * To change this template,choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.sop.display.pricearangement.resources;

import emc.app.components.emcJButton;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.enums.sop.pricearangement.PricingCustomerType;
import emc.functions.Functions;
import java.awt.event.ActionEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author wikus
 */
public class ExportPriceArangementsButton extends emcJButton {

    private emcDataRelationManagerUpdate dataManager;

    public ExportPriceArangementsButton(emcDataRelationManagerUpdate dataManager) {
        super("Export");
        this.dataManager = dataManager;
    }

    @Override
    public void doActionPerformed(ActionEvent evt) {
        super.doActionPerformed(evt);
        String path = new ImportExportFileDialog(JFileChooser.DIRECTORIES_ONLY).getPath();
        if (path == null) {
            return;
        }
        exportFile(path);
    }

    private void exportFile(String path) {
        File theFile = null;
        BufferedWriter out = null;
        try {
            theFile = new File(path);
            out = new BufferedWriter(new FileWriter(theFile));
            StringBuilder toWrite = new StringBuilder();
            //Header
            toWrite.append("\"Type\"" + ",");
            toWrite.append("\"Customer/Group\"" + ",");
            toWrite.append("\"Item\"" + ",");
            toWrite.append("\"Config\"" + ",");
            toWrite.append("\"Colour\"" + ",");
            toWrite.append("\"Size\"" + ",");
            toWrite.append("\"From\"" + ",");
            toWrite.append("\"To\"" + ",");
            toWrite.append("\"Quantity\"" + ",");
            toWrite.append("\"Price\"");
            toWrite.append("\n");
            writeLine(toWrite, out);
            //Export
            int rowCount = dataManager.getRowCount();
            String type;
            for (int row = 0; row < rowCount; row++) {
                toWrite = new StringBuilder();
                type = getValue(row, "customerType");
                toWrite.append(type + ",");
                if (type.replaceAll("\"", "").equals(PricingCustomerType.ALL.toString())) {
                    toWrite.append("\"\"" + ",");
                } else if (type.replaceAll("\"", "").equals(PricingCustomerType.CUSTOMER.toString())) {
                    toWrite.append(getValue(row, "customerId") + ",");
                } else if (type.replaceAll("\"", "").equals(PricingCustomerType.GROUP.toString())) {
                    toWrite.append(getValue(row, "priceGroup") + ",");
                }
                toWrite.append(getValue(row, "itemId") + ",");
                toWrite.append(getValue(row, "dimension1") + ",");
                toWrite.append(getValue(row, "dimension3") + ",");
                toWrite.append(getValue(row, "dimension2") + ",");
                toWrite.append(getValue(row, "fromDate") + ",");
                toWrite.append(getValue(row, "toDate") + ",");
                toWrite.append(getValue(row, "quantity") + ",");
                toWrite.append(getValue(row, "price"));
                toWrite.append("\n");
                writeLine(toWrite, out);
            }
        } catch (IOException ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to export Price List: " + ex.getMessage(), dataManager.getUserData());
            if (theFile != null) {
                if (theFile.exists()) {
                    theFile.delete();
                }
            }
            return;
        } finally {
            try {
                if (out != null) {
                    out.flush();
                    out.close();
                }
            } catch (IOException ex) {
                Logger.getLogger("emc").log(Level.SEVERE, "Failed to close stream: " + ex.getMessage(), dataManager.getUserData());
            }
        }
        Logger.getLogger("emc").log(Level.INFO, "Price List exported.", dataManager.getUserData());
    }

    private void writeLine(StringBuilder theLine, BufferedWriter out) throws IOException {
        String toWrite = theLine.toString();
        out.write(toWrite, 0, toWrite.length());
        out.flush();
    }

    private String getValue(int row, String column) {
        Object value = dataManager.getFieldValueAt(row, column);
        if (value instanceof String) {
            if (Functions.checkBlank(value)) {
                return "\"\"";
            } else {
                return "\"" + value.toString() + "\"";
            }
        } else if (value instanceof BigDecimal) {
            if (value == null) {
                return "\"0\"";
            } else {
                return "\"" + String.valueOf(value) + "\"";
            }
        } else if (value instanceof Date) {
            if (value == null) {
                return "\"\"";
            } else {
                return "\"" + Functions.date2String((Date) value, "yyyy/MM/dd") + "\"";
            }
        }
        return "\"\"";
    }
}
