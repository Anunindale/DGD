/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.methods.creditors;

import emc.enums.modules.enumEMCModules;
import emc.methods.EMCMethodEnum;

/**
 *
 * @author riaan
 */
public enum ServerCreditorsMethods implements EMCMethodEnum {

    //Terms of payment
    INSERT_CREDITORSTERMSOFPAYMENT(0, "INSERT_CREDITORSTERMSOFPAYMENT"),
    UPDATE_CREDITORSTERMSOFPAYMENT(1, "UPDATE_CREDITORSTERMSOFPAYMENT"),
    DELETE_CREDITORSTERMSOFPAYMENT(2, "DELETE_CREDITORSTERMSOFPAYMENT"),
    GETNUMROWS_CREDITORSTERMSOFPAYMENT(3, "GETNUMROWS_CREDITORSTERMSOFPAYMENT"),
    GETDATA_CREDITORSTERMSOFPAYMENT(4, "GETDATA_CREDITORSTERMSOFPAYMENT"),
    VALIDATEFIELD_CREDITORSTERMSOFPAYMENT(5, "VALIDATEFIELD_CREDITORSTERMSOFPAYMENT"),
    //Settlement discount terms
    INSERT_CREDITORSSETTLEMENTDISCOUNTTERMS(20, "INSERT_CREDITORSSETTLEMENTDISCOUNTTERMS"),
    UPDATE_CREDITORSSETTLEMENTDISCOUNTTERMS(21, "UPDATE_CREDITORSSETTLEMENTDISCOUNTTERMS"),
    DELETE_CREDITORSSETTLEMENTDISCOUNTTERMS(22, "DELETE_CREDITORSSETTLEMENTDISCOUNTTERMS"),
    GETNUMROWS_CREDITORSSETTLEMENTDISCOUNTTERMS(23, "GETNUMROWS_CREDITORSSETTLEMENTDISCOUNTTERMS"),
    GETDATA_CREDITORSSETTLEMENTDISCOUNTTERMS(24, "GETDATA_CREDITORSSETTLEMENTDISCOUNTTERMS"),
    VALIDATEFIELD_CREDITORSSETTLEMENTDISCOUNTTERMS(25, "VALIDATEFIELD_CREDITORSSETTLEMENTDISCOUNTTERMS"),
    //Parameters
    INSERT_CREDITORSPARAMETERS(40, "INSERT_CREDITORSPARAMETERS"),
    UPDATE_CREDITORSPARAMETERS(41, "UPDATE_CREDITORSPARAMETERS"),
    DELETE_CREDITORSPARAMETERS(42, "DELETE_CREDITORSPARAMETERS"),
    GETNUMROWS_CREDITORSPARAMETERS(43, "GETNUMROWS_CREDITORSPARAMETERS"),
    GETDATA_CREDITORSPARAMETERS(44, "GETDATA_CREDITORSPARAMETERS"),
    VALIDATEFIELD_CREDITORSPARAMETERS(45, "VALIDATEFIELD_CREDITORSPARAMETERS"),
    //Transactions
    INSERT_CREDITORSTRANSACTIONS(60, "INSERT_CREDITORSTRANSACTIONS"),
    UPDATE_CREDITORSTRANSACTIONS(61, "UPDATE_CREDITORSTRANSACTIONS"),
    DELETE_CREDITORSTRANSACTIONS(62, "DELETE_CREDITORSTRANSACTIONS"),
    GETNUMROWS_CREDITORSTRANSACTIONS(63, "GETNUMROWS_CREDITORSTRANSACTIONS"),
    GETDATA_CREDITORSTRANSACTIONS(64, "GETDATA_CREDITORSTRANSACTIONS"),
    VALIDATEFIELD_CREDITORSTRANSACTIONS(65, "VALIDATEFIELD_CREDITORSTRANSACTIONS"),
    //TransactionSettlement
    INSERT_CREDITORSTRANSACTIONSETTLEMENT(80, "INSERT_CREDITORSTRANSACTIONSETTLEMENT"),
    UPDATE_CREDITORSTRANSACTIONSETTLEMENT(81, "UPDATE_CREDITORSTRANSACTIONSETTLEMENT"),
    DELETE_CREDITORSTRANSACTIONSETTLEMENT(82, "DELETE_CREDITORSTRANSACTIONSETTLEMENT"),
    GETNUMROWS_CREDITORSTRANSACTIONSETTLEMENT(83, "GETNUMROWS_CREDITORSTRANSACTIONSETTLEMENT"),
    GETDATA_CREDITORSTRANSACTIONSETTLEMENT(84, "GETDATA_CREDITORSTRANSACTIONSETTLEMENT"),
    VALIDATEFIELD_CREDITORSTRANSACTIONSETTLEMENT(85, "VALIDATEFIELD_CREDITORSTRANSACTIONSETTLEMENT"),
    //TransactionSettlementHistory
    INSERT_CREDITORSTRANSACTIONSETTLEMENTHISTORY(100, "INSERT_CREDITORSTRANSACTIONSETTLEMENTHISTORY"),
    UPDATE_CREDITORSTRANSACTIONSETTLEMENTHISTORY(101, "UPDATE_CREDITORSTRANSACTIONSETTLEMENTHISTORY"),
    DELETE_CREDITORSTRANSACTIONSETTLEMENTHISTORY(102, "DELETE_CREDITORSTRANSACTIONSETTLEMENTHISTORY"),
    GETNUMROWS_CREDITORSTRANSACTIONSETTLEMENTHISTORY(103, "GETNUMROWS_CREDITORSTRANSACTIONSETTLEMENTHISTORY"),
    GETDATA_CREDITORSTRANSACTIONSETTLEMENTHISTORY(104, "GETDATA_CREDITORSTRANSACTIONSETTLEMENTHISTORY"),
    VALIDATEFIELD_CREDITORSTRANSACTIONSETTLEMENTHISTORY(105, "VALIDATEFIELD_CREDITORSTRANSACTIONSETTLEMENTHISTORY"),
    //CreditorsCreditNoteInvoiceMaster
    INSERT_CREDITORSCREDITNOTEINVOICEMASTER(120, "INSERT_CREDITORSCREDITNOTEINVOICEMASTER"),
    UPDATE_CREDITORSCREDITNOTEINVOICEMASTER(121, "UPDATE_CREDITORSCREDITNOTEINVOICEMASTER"),
    DELETE_CREDITORSCREDITNOTEINVOICEMASTER(122, "DELETE_CREDITORSCREDITNOTEINVOICEMASTER"),
    GETNUMROWS_CREDITORSCREDITNOTEINVOICEMASTER(123, "GETNUMROWS_CREDITORSCREDITNOTEINVOICEMASTER"),
    GETDATA_CREDITORSCREDITNOTEINVOICEMASTER(124, "GETDATA_CREDITORSCREDITNOTEINVOICEMASTER"),
    VALIDATEFIELD_CREDITORSCREDITNOTEINVOICEMASTER(125, "VALIDATEFIELD_CREDITORSCREDITNOTEINVOICEMASTER"),
    APPROVE_CREDITNOTE_INVOICE_MASTER(126, "APPROVE_CREDITNOTE_INVOICE_MASTER"),
    POST_CREDITNOTE_INVOICE_MASTER(127, "POST_CREDITNOTE_INVOICE_MASTER"),
    //CreditorsCreditNoteInvoiceMasterDS
    INSERT_CREDITORSCREDITNOTEINVOICEMASTERDS(140, "INSERT_CREDITORSCREDITNOTEINVOICEMASTERDS"),
    UPDATE_CREDITORSCREDITNOTEINVOICEMASTERDS(141, "UPDATE_CREDITORSCREDITNOTEINVOICEMASTERDS"),
    DELETE_CREDITORSCREDITNOTEINVOICEMASTERDS(142, "DELETE_CREDITORSCREDITNOTEINVOICEMASTERDS"),
    GETNUMROWS_CREDITORSCREDITNOTEINVOICEMASTERDS(143, "GETNUMROWS_CREDITORSCREDITNOTEINVOICEMASTERDS"),
    GETDATA_CREDITORSCREDITNOTEINVOICEMASTERDS(144, "GETDATA_CREDITORSCREDITNOTEINVOICEMASTERDS"),
    VALIDATEFIELD_CREDITORSCREDITNOTEINVOICEMASTERDS(145, "VALIDATEFIELD_CREDITORSCREDITNOTEINVOICEMASTERDS"),
    //CreditorsCreditNoteInvoiceLines
    INSERT_CREDITORSCREDITNOTEINVOICELINES(160, "INSERT_CREDITORSCREDITNOTEINVOICELINES"),
    UPDATE_CREDITORSCREDITNOTEINVOICELINES(161, "UPDATE_CREDITORSCREDITNOTEINVOICELINES"),
    DELETE_CREDITORSCREDITNOTEINVOICELINES(162, "DELETE_CREDITORSCREDITNOTEINVOICELINES"),
    GETNUMROWS_CREDITORSCREDITNOTEINVOICELINES(163, "GETNUMROWS_CREDITORSCREDITNOTEINVOICELINES"),
    GETDATA_CREDITORSCREDITNOTEINVOICELINES(164, "GETDATA_CREDITORSCREDITNOTEINVOICELINES"),
    VALIDATEFIELD_CREDITORSCREDITNOTEINVOICELINES(165, "VALIDATEFIELD_CREDITORSCREDITNOTEINVOICELINES"),
    //CreditorsCreditNoteInvoiceLinesDS
    INSERT_CREDITORSCREDITNOTEINVOICELINESDS(180, "INSERT_CREDITORSCREDITNOTEINVOICELINESDS"),
    UPDATE_CREDITORSCREDITNOTEINVOICELINESDS(181, "UPDATE_CREDITORSCREDITNOTEINVOICELINESDS"),
    DELETE_CREDITORSCREDITNOTEINVOICELINESDS(182, "DELETE_CREDITORSCREDITNOTEINVOICELINESDS"),
    GETNUMROWS_CREDITORSCREDITNOTEINVOICELINESDS(183, "GETNUMROWS_CREDITORSCREDITNOTEINVOICELINESDS"),
    GETDATA_CREDITORSCREDITNOTEINVOICELINESDS(184, "GETDATA_CREDITORSCREDITNOTEINVOICELINESDS"),
    VALIDATEFIELD_CREDITORSCREDITNOTEINVOICELINESDS(185, "VALIDATEFIELD_CREDITORSCREDITNOTEINVOICELINESDS"),
    //CreditorsApprovalGroups
    INSERT_CREDITORSAPPROVALGROUPS(200, "INSERT_CREDITORSAPPROVALGROUPS"),
    UPDATE_CREDITORSAPPROVALGROUPS(201, "UPDATE_CREDITORSAPPROVALGROUPS"),
    DELETE_CREDITORSAPPROVALGROUPS(202, "DELETE_CREDITORSAPPROVALGROUPS"),
    GETNUMROWS_CREDITORSAPPROVALGROUPS(203, "GETNUMROWS_CREDITORSAPPROVALGROUPS"),
    GETDATA_CREDITORSAPPROVALGROUPS(204, "GETDATA_CREDITORSAPPROVALGROUPS"),
    VALIDATEFIELD_CREDITORSAPPROVALGROUPS(205, "VALIDATEFIELD_CREDITORSAPPROVALGROUPS"),
    //CreditorsApprovalGroupSetup
    INSERT_CREDITORSAPPROVALGROUPSETUP(220, "INSERT_CREDITORSAPPROVALGROUPSETUP"),
    UPDATE_CREDITORSAPPROVALGROUPSETUP(221, "UPDATE_CREDITORSAPPROVALGROUPSETUP"),
    DELETE_CREDITORSAPPROVALGROUPSETUP(222, "DELETE_CREDITORSAPPROVALGROUPSETUP"),
    GETNUMROWS_CREDITORSAPPROVALGROUPSETUP(223, "GETNUMROWS_CREDITORSAPPROVALGROUPSETUP"),
    GETDATA_CREDITORSAPPROVALGROUPSETUP(224, "GETDATA_CREDITORSAPPROVALGROUPSETUP"),
    VALIDATEFIELD_CREDITORSAPPROVALGROUPSETUP(225, "VALIDATEFIELD_CREDITORSAPPROVALGROUPSETUP"),
    //CreditorsApprovalGroupSetupDS
    INSERT_CREDITORSAPPROVALGROUPSETUPDS(240, "INSERT_CREDITORSAPPROVALGROUPSETUPDS"),
    UPDATE_CREDITORSAPPROVALGROUPSETUPDS(241, "UPDATE_CREDITORSAPPROVALGROUPSETUPDS"),
    DELETE_CREDITORSAPPROVALGROUPSETUPDS(242, "DELETE_CREDITORSAPPROVALGROUPSETUPDS"),
    GETNUMROWS_CREDITORSAPPROVALGROUPSETUPDS(243, "GETNUMROWS_CREDITORSAPPROVALGROUPSETUPDS"),
    GETDATA_CREDITORSAPPROVALGROUPSETUPDS(244, "GETDATA_CREDITORSAPPROVALGROUPSETUPDS"),
    VALIDATEFIELD_CREDITORSAPPROVALGROUPSETUPDS(245, "VALIDATEFIELD_CREDITORSAPPROVALGROUPSETUPDS"),
    //Creditors Open Transactions
    INSERT_CREDITORSOPENTRANSACTIONS(260, "INSERT_CREDITORSOPENTRANSACTIONS"),
    UPDATE_CREDITORSOPENTRANSACTIONS(261, "UPDATE_CREDITORSOPENTRANSACTIONS"),
    DELETE_CREDITORSOPENTRANSACTIONS(262, "DELETE_CREDITORSOPENTRANSACTIONS"),
    GETNUMROWS_CREDITORSOPENTRANSACTIONS(263, "GETNUMROWS_CREDITORSOPENTRANSACTIONS"),
    GETDATA_CREDITORSOPENTRANSACTIONS(264, "GETDATA_CREDITORSOPENTRANSACTIONS"),
    VALIDATEFIELD_CREDITORSOPENTRANSACTIONS(265, "VALIDATEFIELD_CREDITORSOPENTRANSACTIONS"),
    //CreditorsInvoiceRegister
    INSERT_CREDITORSINVOICEREGISTER(280, "INSERT_CREDITORSINVOICEREGISTER"),
    UPDATE_CREDITORSINVOICEREGISTER(281, "UPDATE_CREDITORSINVOICEREGISTER"),
    DELETE_CREDITORSINVOICEREGISTER(282, "DELETE_CREDITORSINVOICEREGISTER"),
    GETNUMROWS_CREDITORSINVOICEREGISTER(283, "GETNUMROWS_CREDITORSINVOICEREGISTER"),
    GETDATA_CREDITORSINVOICEREGISTER(284, "GETDATA_CREDITORSINVOICEREGISTER"),
    VALIDATEFIELD_CREDITORSINVOICEREGISTER(285, "VALIDATEFIELD_CREDITORSINVOICEREGISTER");
    private final int id;
    private final String label;

    ServerCreditorsMethods(final int id, final String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return label;
    }

    public static ServerCreditorsMethods fromString(final String str) {
        for (ServerCreditorsMethods e : ServerCreditorsMethods.values()) {
            if (e.toString().equalsIgnoreCase(str)) {
                return e;
            }
        }
        return null;
    }

    public static ServerCreditorsMethods fromId(final int id) {
        for (ServerCreditorsMethods e : ServerCreditorsMethods.values()) {
            if (e.id == id) {
                return e;
            }
        }
        return null;
    }

    public enumEMCModules getEMCModule() {
        return enumEMCModules.CREDITORS;
    }
}
