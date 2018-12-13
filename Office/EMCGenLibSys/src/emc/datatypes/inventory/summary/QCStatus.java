/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.datatypes.inventory.summary;

import emc.datatypes.EMCString;

/**
 * @Name         : QCStatus
 *
 * @Date         : 02 Sep 2009
 *
 * @Description  : Datattpe for the QCStatus field.
 *
 * @author       : riaan
 */
public class QCStatus extends EMCString {

    /** Creates a new instance of QCStatus. */
    public QCStatus() {
        this.setEmcLabel("QC Status");
        this.setEditable(false);
    }
}
