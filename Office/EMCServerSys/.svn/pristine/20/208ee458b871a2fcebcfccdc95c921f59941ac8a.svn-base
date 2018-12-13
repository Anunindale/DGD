/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.debtors.debtorscnfinishedgoodslabels;

import emc.bus.debtors.creditnotes.DebtorsCreditNoteMasterLocal;
import emc.bus.inventory.InventoryReferenceLocal;
import emc.bus.inventory.dimensions.InventoryDimension1Local;
import emc.bus.inventory.dimensions.InventoryDimension2Local;
import emc.bus.inventory.dimensions.InventoryDimension3Local;
import emc.entity.debtors.DebtorsCreditNoteLines;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.creditnoteregister.DebtorsCreditNoteRegister;
import emc.entity.inventory.dimensions.InventoryDimension1;
import emc.entity.inventory.dimensions.InventoryDimension2;
import emc.entity.inventory.dimensions.InventoryDimension3;
import emc.entity.sop.SOPCustomers;
import emc.enums.enumQueryTypes;
import emc.enums.inventory.inventorytables.InventoryReferenceTypes;
import emc.enums.inventory.register.RegisterFromTypeEnum;
import emc.framework.EMCBusinessBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsCNFinishedGoodsLabelsBean extends EMCBusinessBean implements DebtorsCNFinishedGoodsLabelsLocal {

    @EJB
    private DebtorsCreditNoteMasterLocal creditNoteMasterBean;
    @EJB
    private InventoryReferenceLocal itemReferenceBean;
    @EJB
    private InventoryDimension1Local dimension1Bean;
    @EJB
    private InventoryDimension2Local dimension2Bean;
    @EJB
    private InventoryDimension3Local dimension3Bean;

    /** Creates a new instance of DebtorsCNFinishedGoodsLabelsBean. */
    public DebtorsCNFinishedGoodsLabelsBean() {
        
    }

    @Override
    public String printFinishedGoodsBoxLabels(List<String> creditNoteIds, List<String> batchIds, EMCUserData userData) {
        DebtorsCreditNoteMaster creditNoteMaster = null;
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteLines.class);
        query.addAndInList("invCNNumber", creditNoteIds, true, false);
        query.addOrderBy("invCNNumber");
        List<DebtorsCreditNoteLines> creditNoteLines = util.executeGeneralSelectQuery(query, userData);
        StringBuilder builder = new StringBuilder();
        if (!creditNoteLines.isEmpty()) {
            Map<String, List<String>> itemMap = new HashMap<String, List<String>>();
            List<String> item;
            Map<String, InventoryDimension1> dimension1Map = new HashMap<String, InventoryDimension1>();
            InventoryDimension1 dimension1;
            Map<String, InventoryDimension2> dimension2Map = new HashMap<String, InventoryDimension2>();
            InventoryDimension2 dimension2;
            Map<String, InventoryDimension3> dimension3Map = new HashMap<String, InventoryDimension3>();
            InventoryDimension3 dimension3;

            Map<String, String> customerNames = new HashMap<String, String>();

            String colour;
            String colourDesc;
            List<DebtorsCreditNoteRegister> registerList;
            for (DebtorsCreditNoteLines creditNoteLine : creditNoteLines) {
                if (creditNoteMaster == null || !creditNoteMaster.getInvCNNumber().equals(creditNoteLine.getInvCNNumber())) {
                    creditNoteMaster = creditNoteMasterBean.getCreditNote(creditNoteLine.getInvCNNumber(), userData);
                }

                item = itemMap.get(creditNoteLine.getItemId());
                if (item == null) {
                    item = itemReferenceBean.findReferenceAndDesc(creditNoteLine.getItemId(), InventoryReferenceTypes.PRIMARY, userData);
                    itemMap.put(creditNoteLine.getItemId(), item);
                }
                dimension1 = dimension1Map.get(creditNoteLine.getDimension1());
                if (dimension1 == null) {
                    dimension1 = dimension1Bean.getDimension1(creditNoteLine.getDimension1(), userData);
                    dimension1Map.put(creditNoteLine.getDimension1(), dimension1);
                }
                dimension2 = dimension2Map.get(creditNoteLine.getDimension2());
                if (dimension2 == null) {
                    dimension2 = dimension2Bean.findDimension2(creditNoteLine.getDimension2(), userData);
                    dimension2Map.put(creditNoteLine.getDimension2(), dimension2);
                }
                dimension3 = dimension3Map.get(creditNoteLine.getDimension3());
                if (dimension3 == null) {
                    dimension3 = dimension3Bean.getDimension3Record(creditNoteLine.getDimension3(), userData);
                    dimension3Map.put(creditNoteLine.getDimension3(), dimension3);
                }
                colour = dimension1 == null ? dimension3 == null ? "" : dimension3.getDimensionId() : dimension1.getDimensionId();
                colourDesc = dimension1 == null ? dimension3 == null ? "" : dimension3.getDescription() : dimension1.getDescription();

                query = new EMCQuery(enumQueryTypes.SELECT, DebtorsCreditNoteRegister.class);
                query.addAnd("type", RegisterFromTypeEnum.RETURN.toString());
                query.addAnd("masterId", creditNoteLine.getInvCNNumber());
                query.addAnd("transId", creditNoteLine.getInventTransId());
                if (batchIds != null && !batchIds.isEmpty()) {
                    query.addAndInList("batch", batchIds, true, false);
                }
                registerList = util.executeGeneralSelectQuery(query, userData);

                String customerName = customerNames.get(creditNoteMaster.getCustomerNo());

                if (customerName == null) {
                    EMCQuery customerQuery = new EMCQuery(enumQueryTypes.SELECT, SOPCustomers.class);
                    customerQuery.addAnd("customerId", creditNoteMaster.getCustomerNo());
                    customerQuery.addField("customerName");

                    customerName = (String)util.executeSingleResultQuery(customerQuery, userData);
                    customerNames.put(creditNoteMaster.getCustomerNo(), customerName);
                }

                for (DebtorsCreditNoteRegister reg : registerList) {
                    builder.append(generateLabelString(customerName, creditNoteMaster.getInvCNNumber(), reg.getNewBatch(), item.get(0), item.get(1),
                            colour, colourDesc, dimension2.getDimensionId(), dimension2.getDescription(),
                            creditNoteMaster.getSalesOrderNo(), util.getBigDecimal(reg.getQuantity())));
                }
            }
        }
        return builder.toString();
    }

    private String generateLabelString(String customerName, String piNumber, String boxNumber, String itemReference, String itemDescription,
            String config, String configDescription, String size, String sizeDescription, String salesOrderNumber, BigDecimal quantity) {
        StringBuilder builder = new StringBuilder();
        builder.append("~BARCODESTART~L");
        builder.append("H20");
        builder.append("~BARCODERETURN~");
        builder.append("PG");
        builder.append("~BARCODERETURN~");
        builder.append("D11");
        builder.append("~BARCODERETURN~");
        builder.append("142200004150001" + (isBlank(customerName) ? "" : customerName.length() > 10 ? customerName.substring(0, 10) : customerName));
        builder.append("~BARCODERETURN~");
        builder.append("212200004050198P.I. No. " + (isBlank(piNumber) ? "" : piNumber));
        builder.append("~BARCODERETURN~");
        builder.append("232200004050173Box No. " + (isBlank(boxNumber) ? "" : boxNumber));
        builder.append("~BARCODERETURN~");
        builder.append("222200004050148Selling Code " + (isBlank(itemReference) ? "" : itemReference));
        builder.append("~BARCODERETURN~");
        builder.append("211100002490088" + (isBlank(itemDescription) ? "" : itemDescription));
        builder.append("~BARCODERETURN~");
        builder.append("222200004050123Colour " + (isBlank(config) ? "" : config));
        builder.append("~BARCODERETURN~");
        builder.append("211100002490078" + (isBlank(configDescription) ? "" : configDescription));
        builder.append("~BARCODERETURN~");
        builder.append("222200004050098Size " + (isBlank(size) ? "" : size));
        builder.append("~BARCODERETURN~");
        builder.append("221100002490068" + (isBlank(sizeDescription) ? "" : sizeDescription));
        builder.append("~BARCODERETURN~");
        builder.append("222100004050044Order No. " + (isBlank(salesOrderNumber) ? "" : salesOrderNumber));
        builder.append("~BARCODERETURN~");
        builder.append("222200004050018Packs in Box " + quantity.toString());
        builder.append("~BARCODERETURN~");
        builder.append("1A5004000040010" + (isBlank(boxNumber) ? "" : boxNumber));
        builder.append("~BARCODERETURN~");
        builder.append("E");
        builder.append("~BARCODERETURN~");
        return builder.toString();
    }
}
