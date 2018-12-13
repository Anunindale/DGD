/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.inventory.transactions;

/**
 *
 * @author rico
 */
public enum TransactionType {

    //POP
    POP_POST_POLINE,
    POP_POSTMASTER,
    POP_POSTMASTER_RETURN,
    POP_UPDATE_BLANCKET_ORDER,
    //Inventory
    IVENT_POST_JRLINE,
    IVENT_POST_JRMASTER,
    IVENT_MOVEMENT_LOCATION,
    IVENT_RESERVED_MOVEMENT_LOCATION,
    IVENT_POST_PICK_LIST,
    IVENT_RE_RESERVE_PICK_LIST,
    IVENT_UNRESERVE_JRLINE,
    IVENT_UNRESERVE_AND_REDORDER_JRLINE,
    IVENT_CANCEL_PICK_LIST_LINE,
    //Production
    PROD_RETURN_STOCK,
    PROD_POST_WO,
    PROD_POST_WOBOM,
    PROD_RESERVE_WOBOMSTOCK,
    PROD_POST_BUNDLES,
    PROD_POST_AWOBOM,
    PROD_RESERVE_AWOBOMSTOCK,
    PROD_CANCEL_WO_TX_ON_COMPLETE,
    PROD_CANCEL_AGGWO_TX_ON_COMPLETE,
    PROD_CANCEL_AGGWO_TX,
    PROD_UNRESERVE_AWOBOM_STOCK,
    PROD_UNRESERVE__REORDER_AWOBOM_STOCK,
    PROD_RESERVE_BOM_JOURNAL,
    PROD_RESERVE_AWO_BOM_LINE,
    PROD_POST_ASSEMBLED_ORDER,
    PROD_KILL_AWO_BOM_ORDERED_TX,
    PROD_ALLOCATE_TX_TO_MTO,
    PROD_RESERVE_TX_TO_MTO,
    //SOP
    SOP_ORDER_SOLINE,
    SOP_RESERVE_SOLINE,
    SOP_ORDER_BLANKET_ORDER_LINE,
    SOP_REALLOCATE_LINE,
    //Debtors
    DEBTORS_POST_INVOICE_LINE,
    DEBTORS_POST_INVOICE,
    DEBTORS_CANCEL_INVOICE_LINE,
    DEBTORS_POST_CREDIT_NOTE_LINE,
    DEBTORS_POST_CREDIT_NOTE,
    DEBTORS_CANCEL_CREDIT_NOTE_LINE,
    //Creditors
    CREDITORS_POST_INVOICE_LINE,
    CREDITORS_POST_CREDIT_NOTE_LINE,
    CREDITORS_POST_INVOICE,
    CREDITORS_POST_CREDIT_NOTE;
}