package emc.entity.gl.transactions;

import emc.datatypes.EMCDataType;
import emc.datatypes.gl.transactionssummary.AnalysisCode1;
import emc.datatypes.gl.transactionssummary.AnalysisCode2;
import emc.datatypes.gl.transactionssummary.AnalysisCode3;
import emc.datatypes.gl.transactionssummary.AnalysisCode4;
import emc.datatypes.gl.transactionssummary.AnalysisCode5;
import emc.datatypes.gl.transactionssummary.AnalysisCode6;
import emc.datatypes.gl.chartofaccounts.Text;
import emc.datatypes.gl.chartofaccounts.foreignkeys.AccountNumberFK;
import emc.datatypes.gl.currency.foreignkeys.CurrencyFK;
import emc.datatypes.gl.transactionssummary.GroupDate;
import emc.datatypes.gl.transactionssummary.GroupGranularity;
import emc.datatypes.gl.transactionssummary.GroupPeriod;
import emc.datatypes.gl.transactionssummary.GroupWeek;
import emc.datatypes.gl.transactionssuper.Credit;
import emc.datatypes.gl.transactionssuper.Debit;
import emc.datatypes.gl.transactionssuper.ExternalReference;
import emc.datatypes.gl.transactionssuper.ForeignCurrencyCredit;
import emc.datatypes.gl.transactionssuper.ForeignCurrencyDebit;
import emc.datatypes.gl.transactionssuper.Processed;
import emc.datatypes.gl.transactionssuper.SourceReference;
import emc.datatypes.gl.transactionssuper.TransSource;
import emc.datatypes.gl.transactionssuper.TransactionDate;
import emc.datatypes.gl.transactionssuper.TransactionNumber;
import emc.datatypes.gl.vatcodes.foreignkeys.VATCodeFKNotMandatory;
import java.util.Date;
import java.util.HashMap;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/** 
 *
 * @author claudette
 */
@Entity
@Table(name = "GLTransactionsSummary")
public class GLTransactionsSummary extends GLTransactionsSuper {

    private String groupGranularity;
    @Temporal(TemporalType.DATE)
    private Date groupDate;

    /** Creates a new instance of GLTransactionsSummary. */
    public GLTransactionsSummary() {
    }

    @Override
    public HashMap<String, EMCDataType> buildFieldList() {
        HashMap<String, EMCDataType> toBuild = super.buildFieldList();
        toBuild.put("groupGranularity", new GroupGranularity());
        toBuild.put("groupDate", new GroupDate());
        toBuild.put("groupWeek", new GroupWeek());
        toBuild.put("groupPeriod", new GroupPeriod());
        toBuild.put("accountNumber", new AccountNumberFK());
        toBuild.put("transactionSource", new TransSource());
        toBuild.put("sourceReference", new SourceReference());
        toBuild.put("transactionNumber", new TransactionNumber());
        toBuild.put("transactionDate", new TransactionDate());
        toBuild.put("text", new Text());
        toBuild.put("foreignCurrency", new CurrencyFK());
        toBuild.put("vatCode", new VATCodeFKNotMandatory());
        toBuild.put("analysisCode1", new AnalysisCode1());
        toBuild.put("analysisCode2", new AnalysisCode2());
        toBuild.put("analysisCode3", new AnalysisCode3());
        toBuild.put("analysisCode4", new AnalysisCode4());
        toBuild.put("analysisCode5", new AnalysisCode5());
        toBuild.put("analysisCode6", new AnalysisCode6());
        toBuild.put("debit", new Debit());
        toBuild.put("credit", new Credit());
        toBuild.put("foreignCurrencyDebit", new ForeignCurrencyDebit());
        toBuild.put("foreignCurrencyCredit", new ForeignCurrencyCredit());
        toBuild.put("processed", new Processed());
        toBuild.put("externalReference", new ExternalReference());
        return toBuild;
    }

    public String getGroupGranularity() {
        return this.groupGranularity;
    }

    public void setGroupGranularity(String groupGranularity) {
        this.groupGranularity = groupGranularity;
    }

    public Date getGroupDate() {
        return this.groupDate;
    }

    public void setGroupDate(Date groupDate) {
        this.groupDate = groupDate;
    }
}
