/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.debtors.debtorscnfinishedgoodslabels;

import javax.ejb.Local;

/**
 *
 * @author riaan
 */
@Local
public interface DebtorsCNFinishedGoodsLabelsLocal {

    public java.lang.String printFinishedGoodsBoxLabels(java.util.List<java.lang.String> creditNoteIds, java.util.List<java.lang.String> batchIds, emc.framework.EMCUserData userData);

}
