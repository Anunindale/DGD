/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.helpers.inventory;

/**
 *
 * @author wikus
 */
public class JournalGeneratorHelper {

    //Master Fields
    private String journalDefinition;
    private String journalDescription;
    //From Fields
    private String fromItem;
    private String fromDimension1;
    private String fromDimension2;
    private String fromDimension3;
    private String fromWarehouse;
    private String fromLocation;
    private String fromBatch;
    private String fromSerial;
    private String fromPallet;
    //To Fields
    private String toItem;
    private String toDimension1;
    private String toDimension2;
    private String toDimension3;
    private String toWarehouse;
    private String toLocation;
    private String toBatch;
    private String toSerial;
    private String toPallet;
    //Additional Selection
    private String selectionTable1;
    private String selectionField1;
    private String selectionValue1;
    private String selectionTable2;
    private String selectionField2;
    private String selectionValue2;
    private String selectionTable3;
    private String selectionField3;
    private String selectionValue3;
    private String selectionTable4;
    private String selectionField4;
    private String selectionValue4;
    private String selectionTable5;
    private String selectionField5;
    private String selectionValue5;

    public JournalGeneratorHelper() {
    }

    public String getFromBatch() {
        return fromBatch;
    }

    public void setFromBatch(String fromBatch) {
        this.fromBatch = fromBatch;
    }

    public String getFromDimension1() {
        return fromDimension1;
    }

    public void setFromDimension1(String fromDimension1) {
        this.fromDimension1 = fromDimension1;
    }

    public String getFromDimension2() {
        return fromDimension2;
    }

    public void setFromDimension2(String fromDimension2) {
        this.fromDimension2 = fromDimension2;
    }

    public String getFromDimension3() {
        return fromDimension3;
    }

    public void setFromDimension3(String fromDimension3) {
        this.fromDimension3 = fromDimension3;
    }

    public String getFromItem() {
        return fromItem;
    }

    public void setFromItem(String fromItem) {
        this.fromItem = fromItem;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getFromPallet() {
        return fromPallet;
    }

    public void setFromPallet(String fromPallet) {
        this.fromPallet = fromPallet;
    }

    public String getFromSerial() {
        return fromSerial;
    }

    public void setFromSerial(String fromSerial) {
        this.fromSerial = fromSerial;
    }

    public String getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(String fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public String getJournalDefinition() {
        return journalDefinition;
    }

    public void setJournalDefinition(String journalDefinition) {
        this.journalDefinition = journalDefinition;
    }

    public String getJournalDescription() {
        return journalDescription;
    }

    public void setJournalDescription(String journalDescription) {
        this.journalDescription = journalDescription;
    }

    public String getToBatch() {
        return toBatch;
    }

    public void setToBatch(String toBatch) {
        this.toBatch = toBatch;
    }

    public String getToDimension1() {
        return toDimension1;
    }

    public void setToDimension1(String toDimension1) {
        this.toDimension1 = toDimension1;
    }

    public String getToDimension2() {
        return toDimension2;
    }

    public void setToDimension2(String toDimension2) {
        this.toDimension2 = toDimension2;
    }

    public String getToDimension3() {
        return toDimension3;
    }

    public void setToDimension3(String toDimension3) {
        this.toDimension3 = toDimension3;
    }

    public String getToItem() {
        return toItem;
    }

    public void setToItem(String toItem) {
        this.toItem = toItem;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public String getToPallet() {
        return toPallet;
    }

    public void setToPallet(String toPallet) {
        this.toPallet = toPallet;
    }

    public String getToSerial() {
        return toSerial;
    }

    public void setToSerial(String toSerial) {
        this.toSerial = toSerial;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public String getSelectionField1() {
        return selectionField1;
    }

    public void setSelectionField1(String selectionField1) {
        this.selectionField1 = selectionField1;
    }

    public String getSelectionField2() {
        return selectionField2;
    }

    public void setSelectionField2(String selectionField2) {
        this.selectionField2 = selectionField2;
    }

    public String getSelectionField3() {
        return selectionField3;
    }

    public void setSelectionField3(String selectionField3) {
        this.selectionField3 = selectionField3;
    }

    public String getSelectionField4() {
        return selectionField4;
    }

    public void setSelectionField4(String selectionField4) {
        this.selectionField4 = selectionField4;
    }

    public String getSelectionField5() {
        return selectionField5;
    }

    public void setSelectionField5(String selectionField5) {
        this.selectionField5 = selectionField5;
    }

    public String getSelectionTable1() {
        return selectionTable1;
    }

    public void setSelectionTable1(String selectionTable1) {
        this.selectionTable1 = selectionTable1;
    }

    public String getSelectionTable2() {
        return selectionTable2;
    }

    public void setSelectionTable2(String selectionTable2) {
        this.selectionTable2 = selectionTable2;
    }

    public String getSelectionTable3() {
        return selectionTable3;
    }

    public void setSelectionTable3(String selectionTable3) {
        this.selectionTable3 = selectionTable3;
    }

    public String getSelectionTable4() {
        return selectionTable4;
    }

    public void setSelectionTable4(String selectionTable4) {
        this.selectionTable4 = selectionTable4;
    }

    public String getSelectionTable5() {
        return selectionTable5;
    }

    public void setSelectionTable5(String selectionTable5) {
        this.selectionTable5 = selectionTable5;
    }

    public String getSelectionValue1() {
        return selectionValue1;
    }

    public void setSelectionValue1(String selectionValue1) {
        this.selectionValue1 = selectionValue1;
    }

    public String getSelectionValue2() {
        return selectionValue2;
    }

    public void setSelectionValue2(String selectionValue2) {
        this.selectionValue2 = selectionValue2;
    }

    public String getSelectionValue3() {
        return selectionValue3;
    }

    public void setSelectionValue3(String selectionValue3) {
        this.selectionValue3 = selectionValue3;
    }

    public String getSelectionValue4() {
        return selectionValue4;
    }

    public void setSelectionValue4(String selectionValue4) {
        this.selectionValue4 = selectionValue4;
    }

    public String getSelectionValue5() {
        return selectionValue5;
    }

    public void setSelectionValue5(String selectionValue5) {
        this.selectionValue5 = selectionValue5;
    }
}
