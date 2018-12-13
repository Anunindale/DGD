/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.forms.trec.display.treccards.resources;

import java.util.List;

/**
 *
 * @author asd_admin
 */
public interface TRECEditDialogInterface {

    public List<TrecCheckBox> getSelectionCompList();

    public boolean isBusy();
}
