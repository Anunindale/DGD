package emc.bus.creditors.transactions;

import emc.bus.creditors.creditnoteinvoicemaster.CreditorsCreditNoteInvoiceMasterLocal;
import emc.bus.creditors.opentransactions.CreditorsOpenTransactionsLocal;
import emc.bus.creditors.settlementdiscountterms.CreditorsSettlementDiscountTermsLocal;
import emc.entity.creditors.CreditorsCreditNoteInvoiceLines;
import emc.entity.creditors.CreditorsCreditNoteInvoiceMaster;
import emc.entity.creditors.CreditorsSettlementDiscountTerms;
import emc.entity.creditors.CreditorsTransactions;
import emc.enums.creditors.CreditorsTransactionRefTypes;
import emc.enums.debtors.invoicestatus.DebtorsInvoiceStatus;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/** 
 *
 * @author Owner
 */
@Stateless
public class CreditorsTransactionsBean extends EMCEntityBean implements CreditorsTransactionsLocal {

    @EJB
    private CreditorsCreditNoteInvoiceMasterLocal creditNoteInvoiceMasterBean;
    @EJB
    private CreditorsSettlementDiscountTermsLocal settlementDiscTermsBean;
    @EJB
    private CreditorsOpenTransactionsLocal openTransactionBean;

    /** Creates a new instance of CreditorsTransactionsBean. */
    public CreditorsTransactionsBean() {
    }

    public void postInvoiceCreditNote(CreditorsCreditNoteInvoiceMaster masterRecord, List<CreditorsCreditNoteInvoiceLines> linesRecordList, EMCUserData userData) throws EMCEntityBeanException {
        BigDecimal total = getInvoiceTotal(masterRecord.getCreditNoteInvoiceNumber(), userData);
        BigDecimal vat = getInvoiceTotalVAT(masterRecord.getCreditNoteInvoiceNumber(), userData);
        BigDecimal discount = getInvoiceDiscountTotal(masterRecord.getCreditNoteInvoiceNumber(), userData);

        Date now = Functions.nowDate();

        //N & L mod.  Set Invoice date to now date.
        masterRecord.setCreditNoteInvoiceDate(now);

        CreditorsTransactions trans = new CreditorsTransactions();

        trans.setDebit(total.add(vat));
        trans.setReferenceType(CreditorsTransactionRefTypes.INVOICE.toString());
        trans.setVatAmount(vat);
        trans.setReferenceNumber(masterRecord.getCreditNoteInvoiceNumber());
        trans.setTransactionDate(masterRecord.getCreditNoteInvoiceDate());
        trans.setSupplierId(masterRecord.getSupplierId());
        trans.setTransactionSource(masterRecord.getCreditNoteInvoiceNumber());
        trans.setSettlementDiscDate(masterRecord.getSettlementDiscDate());
        trans.setPaymentDueDate(masterRecord.getPaymentDueDate());
        trans.setPurchaseOrderId(masterRecord.getPurchaseOrderId());

        CreditorsSettlementDiscountTerms terms = settlementDiscTermsBean.getSettlementDiscountTerms(masterRecord.getSettlementDiscount(), userData);

        if (terms != null) {
            trans.setSettlementDiscPercentage(util.getBigDecimal(terms.getDiscountPercentage()));
        }

        this.insert(trans, userData);

        masterRecord.setCreditNoteInvoiceStatus(DebtorsInvoiceStatus.POSTED.toString());
        masterRecord.setPostedBy(userData.getUserName());
        masterRecord.setPostedDate(now);
        masterRecord.setPostedTime(now);

        masterRecord.setDiscountTotal(discount);
        masterRecord.setPurchaseTotal(total);
        masterRecord.setVatTotal(vat);
        masterRecord.setInvoiceTotal(trans.getDebit());

        creditNoteInvoiceMasterBean.update(masterRecord, userData);
    }

    public BigDecimal getInvoiceTotal(String invoiceNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceLines.class);
        query.addFieldAggregateFunction("totalCost", "SUM");
        query.addAnd("creditNoteInvoiceNumber", invoiceNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    public BigDecimal getInvoiceTotalVAT(String invoiceNo, EMCUserData userData) {
        //Get sum of lines
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceLines.class);
        query.addFieldAggregateFunction("vatAmount", "SUM");
        query.addAnd("creditNoteInvoiceNumber", invoiceNo);

        BigDecimal total = (BigDecimal) util.executeSingleResultQuery(query, userData);

        return total == null ? new BigDecimal(0) : total;
    }

    public BigDecimal getInvoiceDiscountTotal(String invoiceNo, EMCUserData userData) {
        //Get sum of line discounts
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, CreditorsCreditNoteInvoiceLines.class);
        query.addField("quantity");
        query.addField("unitPrice");
        query.addField("discountPercentage");
        query.addAnd("creditNoteInvoiceNumber", invoiceNo);

        BigDecimal totalDisc = BigDecimal.ZERO;

        List<Object[]> values = (List<Object[]>) util.executeGeneralSelectQuery(query, userData);

        for (Object[] value : values) {
            BigDecimal quantity = (BigDecimal) value[0];
            BigDecimal unitPrice = (BigDecimal) value[1];
            BigDecimal discountPercentage = (BigDecimal) value[2];

            BigDecimal totalBeforeDisc = ((unitPrice.multiply(quantity))).multiply(discountPercentage.divide(new BigDecimal(100)));
            totalDisc = totalDisc.add(totalBeforeDisc);
        }

        return totalDisc;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsTransactions transaction = (CreditorsTransactions) iobject;

        transaction = (CreditorsTransactions) super.insert(transaction, userData);

        openTransactionBean.insertFromTransactions(transaction, userData);

        return transaction;
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return super.update(uobject, userData);
    }

    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        CreditorsTransactions transaction = (CreditorsTransactions) dobject;

        transaction = (CreditorsTransactions) super.delete(transaction, userData);

        openTransactionBean.deleteFromTransactions(transaction, userData);

        return transaction;
    }
}
