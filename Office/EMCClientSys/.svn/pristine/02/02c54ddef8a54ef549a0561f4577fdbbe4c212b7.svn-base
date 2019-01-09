/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.app.components.gl.analysiscodes;

import emc.app.components.emcJComboBox;
import emc.app.components.emctable.emcDataRelationManagerUpdate;
import emc.app.components.emctable.emcJTableUpdate;
import emc.app.eventhandling.EMCActionHandlerInterface;
import emc.app.eventhandling.EMCGeneralListener;
import emc.enums.enumQueryTypes;
import emc.enums.gl.GLAnalysisCode;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.awt.event.ActionEvent;

/**
 *
 * @author claudette
 */
public class GLAnalysisCodesDropDown extends emcJComboBox implements EMCActionHandlerInterface {

    private emcDataRelationManagerUpdate dataRelation;
    private static EMCGeneralListener listener = new EMCGeneralListener();

    public GLAnalysisCodesDropDown(emcDataRelationManagerUpdate dataRelation) {
        super(GLAnalysisCode.values());
        this.dataRelation = dataRelation;
        listener.setAction(true);
        this.addActionListener(listener);
    }

    public void doActionPerformed(ActionEvent evt) {
        String result = (String) this.getSelectedItem();
        GLAnalysisCode codes = (GLAnalysisCode.fromString(result));
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, codes.getEntityClassName());

        EMCUserData userData = dataRelation.getUserData();
        userData.setUserData(0, query.toString());
        userData.setUserData(1, query.getCountQuery());

        dataRelation.setUserData(userData);

        emcJTableUpdate theTable = dataRelation.getMainTableComponent();

        int rowCount = theTable.getRowCount();

        if (rowCount != 0) {
            theTable.changeSelection(0, 0, false, false);
        } else {
            theTable.clearSelection();
        }
    }
}
