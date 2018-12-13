package emc.bus.debtors.basket;

import emc.entity.debtors.DebtorsBasketLines;
import emc.entity.debtors.DebtorsBasketMaster;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;

/**
 *
 * @Author stuart
 */
@Stateless
public class DebtorsBasketLinesBean extends EMCEntityBean implements DebtorsBasketLinesLocal {

    @Override
    public boolean ReReleaseTrec(Long val, EMCUserData userData) {

        if (!isBlank(val)) {
            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, DebtorsBasketLines.class);
            query.addOr("recordID", val);
            DebtorsBasketLines basketLine = (DebtorsBasketLines) util.executeSingleResultQuery(query, userData);
            if (basketLine != null) {
                if (basketLine.getPrintQty() > 0 && basketLine.getPrintQty() <= basketLine.getQuantity().intValue()) {
                    basketLine.setPrintQty(basketLine.getPrintQty() - 1);
                    basketLine.setPrintExtraQty(basketLine.getPrintExtraQty() +1);
                    try {
                        this.update(basketLine, userData);
                        return true;
                    } catch (EMCEntityBeanException ex) {
                        Logger.getLogger(DebtorsBasketLinesBean.class.getName()).log(Level.SEVERE, "Failed to update printQty", ex);
                    }


                } else {
                    Logger.getLogger(DebtorsBasketLinesBean.class.getName()).log(Level.SEVERE, "Failed to Re realease Trec card please check Print Qty");
                    return false;
                }

            }
            return false;


        } else {
            Logger.getLogger("emc").log(Level.SEVERE, "Trec card line not found", userData);

        }
        return true;

    }
}
