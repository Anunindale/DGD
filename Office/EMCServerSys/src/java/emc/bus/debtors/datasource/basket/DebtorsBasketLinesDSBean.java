/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.debtors.datasource.basket;

import com.sun.org.apache.xpath.internal.functions.Function;
import emc.bus.debtors.basket.DebtorsBasketLinesLocal;
import emc.bus.debtors.creditnotes.DebtorsCreditNoteLinesLocal;
import emc.bus.debtors.datasource.creditheldmaster.*;
import emc.bus.sop.customers.SOPCustomersLocal;
import emc.entity.base.journals.BaseJournalDefinitionTable;
import emc.entity.debtors.DebtorsCreditNoteMaster;
import emc.entity.debtors.DebtorsCustomerInvoiceLines;
import emc.entity.debtors.DebtorsCustomerInvoiceMaster;
import emc.entity.debtors.datasource.DebtorsBasketLinesDS;
import emc.entity.debtors.datasource.DebtorsCreditNoteLinesDS;
import emc.entity.debtors.datasource.DebtorsCustomerInvoiceLinesDS;
import emc.entity.inventory.journals.datasource.InventoryJournalMasterDS;
import emc.entity.sop.SOPCustomers;
import emc.entity.sop.SOPSalesOrderLines;
import emc.entity.sop.SOPSalesOrderMaster;
import emc.entity.trec.TRECTrecCardsLines;
import emc.enums.debtors.creditheld.DebtorsCreditHeldRefType;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCEntityClass;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author riaan
 */
@Stateless
public class DebtorsBasketLinesDSBean extends EMCDataSourceBean implements DebtorsBasketLinesDSLocal {

    @EJB
    private DebtorsBasketLinesLocal linesBean;

    public DebtorsBasketLinesDSBean() {
        this.setDataSourceClassName(DebtorsBasketLinesDS.class.getName());
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        Object ret = linesBean.validateField(fieldNameToValidate, (convertDataSourceToSuper(theRecord, userData)), userData);

        return ret instanceof Boolean ? ret : convertSuperToDataSource(ret, userData);
    }

    @Override
    public Object entityUpdate(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.update(convertDataSourceToSuper(uobject, userData), userData);
    }

    @Override
    public Object entityDelete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.delete(convertDataSourceToSuper(dobject, userData), userData);
    }

    @Override
    public Object entityInsert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        return linesBean.insert(convertDataSourceToSuper(iobject, userData), userData);
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        DebtorsBasketLinesDS ds = (DebtorsBasketLinesDS) dataSourceInstance;
        if (!Functions.checkBlank(ds.getTrecCardLink())) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECTrecCardsLines.class);
            query.addAnd("recordID", ds.getTrecCardLink());
            TRECTrecCardsLines lines = (TRECTrecCardsLines) util.executeSingleResultQuery(query, userData);
            if (!Functions.checkBlank(lines)) {
                ds.setUnNumber(lines.getUnNumber());
            }

        }
        return ds;
    }
}
