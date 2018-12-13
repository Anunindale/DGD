/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reports.pop.purchaseorders.servicesreceived;

import emc.entity.inventory.InventoryItemMaster;
import emc.entity.pop.journals.POPSupplierReceivedJournalLines;
import emc.entity.pop.journals.POPSupplierReceivedJournalMaster;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author claudette
 */
@Stateless
public class POPServicesReceivedBean extends EMCReportBean implements POPServicesReceivedLocal {

    public POPServicesReceivedBean() {
    }

    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, java.util.Map<java.lang.String, java.lang.Object> paramMap, EMCUserData userData) {
        List<Object> reportData = new ArrayList<Object>();
        POPServicesReceivedDS ds;
        boolean post = false;


        if (userData.getUserData().size() > 6) {
            //Position 7 is kept to keep track whether or not the note is copied.
            post = (Boolean) userData.getUserData(7);
        }

        for (Object result : queryResult) {
            Object[] ret = (Object[]) result;
            ds = new POPServicesReceivedDS();
            ds.setItemId((String) ret[2]);
            ds.setPurchaseOrder((String) ret[0]);
            ds.setQtyReceived((Double) ret[3]);
            ds.setSupplier((String) ret[2]);
            ds.setUom((String) ret[4]);
            ds.setValueReceived((Double) ret[5]);
            ds.setDeliveryDate(dateHandler.date2String((Date) ret[6]));
            ds.setItemDescription((String) ret[7]);
            ds.setItemPrice((Double) ret[8]);
            reportData.add(ds);
        }
        return reportData;
    }

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
        query.addField("purchaseOrderId", POPSupplierReceivedJournalMaster.class.getName());//0
        query.addField("supplierId", POPSupplierReceivedJournalMaster.class.getName());//1
        query.addField("itemReference", InventoryItemMaster.class.getName());//2
        query.addField("quantity", POPSupplierReceivedJournalLines.class.getName());//3
        query.addField("uom", POPSupplierReceivedJournalLines.class.getName());//4
        query.addField("lineAmount", POPSupplierReceivedJournalLines.class.getName());//5
        query.addField("receivedDate", POPSupplierReceivedJournalLines.class.getName());//6
        query.addField("description", InventoryItemMaster.class.getName()); //7
        query.addField("purchasePrice", InventoryItemMaster.class.getName()); //8
        return super.manipulateQuery(query, userData);
    }
}
