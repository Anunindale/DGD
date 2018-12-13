/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.bus.gl.transactions.logic;

import emc.enums.modules.enumEMCModules;

/**
 *
 * @author riaan
 */
public enum GLTransactionPostType {

    //GL
    POST_GL_JOURNAL(enumEMCModules.GENERAL_LEDGER),
    //Debtors
    POST_DEBTORS_CUSTOMER_INVOICE(enumEMCModules.DEBTORS),
    POST_DEBTORS_CREDIT_NOTE(enumEMCModules.DEBTORS),
    //Production
    POST_PRODUCTION_PICKING_LIST(enumEMCModules.PRODUCTION);
    
    //Enum variables
    private final enumEMCModules module;

    /** Creates a new instance of  GLTransactionPostType*/
    GLTransactionPostType(final enumEMCModules module) {
        this.module = module;
    }

    public enumEMCModules getModule() {
        return module;
    }
}
