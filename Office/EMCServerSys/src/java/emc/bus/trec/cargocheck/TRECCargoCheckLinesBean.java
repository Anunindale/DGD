/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.cargocheck;

import emc.datatypes.EMCInt;
import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECLoadCompatibility;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.tables.EMCTable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class TRECCargoCheckLinesBean extends EMCEntityBean implements TRECCargoCheckLinesLocal {
    @EJB
    private TRECCargoCheckMasterLocal masterBean;
    private String mixedLoad = "MIXED LOAD";
    private String unNumberHaz = "UN NUMBER MOST HAZ";
    private String noRequired = "NONE REQ";

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECCargoCheckLines record = (TRECCargoCheckLines) vobject;
        return super.doUpdateValidation(vobject, userData) && checkPackingGroup(record, userData);
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        TRECCargoCheckLines record = (TRECCargoCheckLines) vobject;

        return super.doInsertValidation(vobject, userData) && checkPackingGroup(record, userData);
    }
    

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        boolean returnRec = false;
        if (ret) {
           TRECCargoCheckLines record = (TRECCargoCheckLines) theRecord;
           if (fieldNameToValidate.equals("unNumber")) {
                returnRec = true;
                record.setProperShipping(null);
                if (!isBlank(record.getUnNumber())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class.getName());
                    query.addAnd("unNumber", record.getUnNumber());
                    TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(query, userData);
                    if (chemical != null) {
                        record.setProperShipping(chemical.getShippingName());

                    }
                }
            } else if (fieldNameToValidate.equals("packingGroup")) {
                if (!isBlank(record.getUnNumber())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class.getName());
                    query.addAnd("unNumber", record.getUnNumber());
                    query.addAnd("shippingName", record.getProperShipping());
                    TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(query, userData);
                    if (chemical != null) {
                        if (isBlank(chemical.getPackingGroup())) {
                            Logger.getLogger("emc").log(Level.SEVERE, "The selected chemical may not have a packing group.", userData);
                            return false;
                        } else if (!chemical.getPackingGroup().contains(record.getPackingGroup())) {
                            Logger.getLogger("emc").log(Level.WARNING, "The selected packing group is not set up on the chemical. " +
                                    "The chemical has the following packing groups: " + chemical.getPackingGroup(), userData);
                        }
                    }
                }
            }
            else if (fieldNameToValidate.equals("properShipping")) {
                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class.getName());
                query.addAnd("unNumber", record.getUnNumber());
                query.addAnd("shippingName", record.getProperShipping());
                if (!util.exists(query, userData)) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected chemical does not have the selected shipping name.", userData);
                    return false;
                }
            }
           if (returnRec) {
                return record;
            }
        }
        return ret;
    }
    private boolean checkPackingGroup(TRECCargoCheckLines record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class.getName());
        query.addAnd("unNumber", record.getUnNumber());
        query.addAnd("shippingName", record.getProperShipping());
        TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(query, userData);
        if (chemical != null) {
            if (!isBlank(chemical.getPackingGroup()) && isBlank(record.getPackingGroup())) {
                Logger.getLogger("emc").log(Level.SEVERE, "The chemical requires a packing group.", userData);
                return false;
            }
        }
        return true;

    }


    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECCargoCheckLines line = (TRECCargoCheckLines)iobject;
        line = checkLoad(line,userData);
        return super.insert(line, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECCargoCheckLines line = (TRECCargoCheckLines)uobject;
        line = checkLoad(line,userData);
        return super.update(line, userData);
    }


    @Override
    public Object delete(Object dobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECCargoCheckLines line = (TRECCargoCheckLines)dobject;
        line = deleteCheckLoad(line,userData);
        return super.delete(dobject, userData);
    }
    /*private void setMasterOnDelete(TRECCargoCheckLines line,EMCUserData userData) throws EMCEntityBeanException{
        String notes = "";
        boolean trecReq = false;
        String placCardType = noRequired;
        double qtyExemptMultiLoad = 0.0;
        EMCQuery master = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckMaster.class);
        master.addAnd("cargoCheckNumber", line.getCargoCheckNumber());
        TRECCargoCheckMaster masterRec = (TRECCargoCheckMaster) util.executeSingleResultQuery(master, userData);
        if(masterRec == null) throw new EMCEntityBeanException("Failed to find master record:"+ line.getCargoCheckNumber());
        //class notes
        EMCQuery classNoteSelect = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckLines.class);
        classNoteSelect.addAnd("cargoCheckNumber", line.getCargoCheckNumber());
        classNoteSelect.addAnd("recordID", line.getRecordID(), EMCQueryConditions.NOT);
        classNoteSelect.addField("classNote");
        classNoteSelect.addGroupBy("classNote");
        List classNotes = util.executeGeneralSelectQuery(classNoteSelect, userData);
        for(Object curNote:classNotes){
            notes+= curNote.toString();
        }
        if(notes.length()>0)notes+= "\n";
        //now related notes
        classNoteSelect.clearFieldList();
        classNoteSelect.clearGroupByFields();
        masterRec.setCargo(true);
        masterRec.setTrecRequired(false);
        List<TRECCargoCheckLines> existLines  = util.executeGeneralSelectQuery(classNoteSelect, userData);
        String erg = "";
        String hazClass = "";
        for(TRECCargoCheckLines curLine:existLines){
            notes+= curLine.getLineNote();
            List result = processLine(curLine, qtyExemptMultiLoad, userData);
            if((Boolean)result.get(1)) trecReq = true;
            qtyExemptMultiLoad = (Double)result.get(2);
            if(erg.equals("")){
                erg = (String) result.get(3);
                hazClass = (String)result.get(4);
            }else{
                if(!(erg.equals(result.get(3)) && hazClass.equals(result.get(4)) )){
                    placCardType = mixedLoad;
                }
            }
            if(!curLine.isAllowed()) masterRec.setCargo(false);
        }
        if(qtyExemptMultiLoad>=100){
                trecReq = true;
        }
        if(trecReq && placCardType.equals(noRequired)){
            placCardType = unNumberHaz;
        }
        masterRec.setNotes(notes);
        masterRec.setPlaceCard(placCardType);
        masterRec.setTrecRequired(trecReq);
        masterBean.update(masterRec, userData);
    }*/

    private List processLine(TRECCargoCheckLines line,double qtyExemptMultiLoad,EMCUserData userData)throws EMCEntityBeanException{
        List ret = new ArrayList(); //0 - placardReq 1- trecReq 2- qtyExemptMultiLoad qty 3- erg 4- classId 5- unNumber

        EMCQuery uNQuery = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        uNQuery.addAnd("unNumber", line.getUnNumber());
        uNQuery.addAnd("shippingName", line.getProperShipping());
        TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(uNQuery, userData);
        if(chemical == null){
            throw new EMCEntityBeanException("Failed to get Chemical:"+line.getUnNumber());
        }
        //now get the class also check if parent class should be used
        EMCQuery classQ = new EMCQuery(enumQueryTypes.SELECT, TRECClasses.class);
        classQ.addAnd("classId", chemical.getClassId());
        TRECClasses theClass = (TRECClasses) util.executeSingleResultQuery(classQ, userData);
        if(theClass == null) throw new EMCEntityBeanException("Failed to get Class:"+chemical.getClassId());
        if(!isBlank(theClass.getParentClass())){
            classQ.removeAnd("classId");
            classQ.addAnd("classId", theClass.getParentClass());
            theClass = (TRECClasses) util.executeSingleResultQuery(classQ, userData);
            if(theClass == null) throw new EMCEntityBeanException("Failed to get parent Class:"+theClass.getParentClass());
        }
        boolean srisk = false;
        BigDecimal exemptQty = new BigDecimal("0.0");
        if(!isBlank(chemical.getSubsidairyRisk()))srisk = true;

        if(isBlank(line.getPackingGroup()) ||line.getPackingGroup().equals("I")){
            if(srisk){
                exemptQty = new BigDecimal(theClass.getSrPackGrp1Threshold());
            }else{
                exemptQty = new BigDecimal(theClass.getPackGrp1Threshold());
            }
        }else if(line.getPackingGroup().equals("II")){
            if(srisk){
                exemptQty = new BigDecimal(theClass.getSrPackGrp2Threshold());
            }else{
                exemptQty = new BigDecimal(theClass.getPackGrp2Threshold());
            }
        }else if(line.getPackingGroup().equals("III")){
            if(srisk){
                exemptQty = new BigDecimal(theClass.getSrPackGrp3Threshold());
            }else{
                exemptQty = new BigDecimal(theClass.getPackGrp3Threshold());
            }
        }
        //placcard req
        if(line.getQuantity().compareTo(exemptQty) > 0){
            ret.add(0, true);
            ret.add(1,true);
        }else{
             ret.add(0, false);
            ret.add(1,false);
        }
        qtyExemptMultiLoad += 100.0*line.getQuantity().doubleValue()/exemptQty.doubleValue();
        ret.add(2,qtyExemptMultiLoad);
        ret.add(3,chemical.getErg());
        ret.add(4,theClass.getClassId());
        ret.add(5,chemical.getUnNumber());

        return ret;
    }
    //to manage t
    private TRECCargoCheckLines deleteCheckLoad(TRECCargoCheckLines line, EMCUserData userData) throws EMCEntityBeanException{
        boolean trecReq = false;
        boolean placCardReq = false;
        boolean message = false;
        boolean loadOK = true;
        line.setAllowed(true);
        String hazClass = "";
        String eRG = "";
        String placCardType = noRequired;
        String classNotes = "";
        String allLineNotes = "";
        String notes = "";
        HashMap<String,TRECClasses> notesMap = new HashMap();

        double qtyExemptMultiLoad = 0.0;
        EMCQuery loadCompQ = new EMCQuery(enumQueryTypes.SELECT, TRECLoadCompatibility.class);
        //get all the other lines
        EMCQuery cargoLinesQ = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckLines.class);
        cargoLinesQ.addAnd("cargoCheckNumber", line.getCargoCheckNumber());
        cargoLinesQ.addAnd("recordID",line.getRecordID(),EMCQueryConditions.NOT); //not the row to be deleted
        List<TRECCargoCheckLines> cargoLines = util.executeGeneralSelectQuery(cargoLinesQ, userData);
        //now loop through all the rows and determine the trec and placcard requirements.

        for(TRECCargoCheckLines curLine:cargoLines){
            //ignore the current line being saved will add it at the end
            List result = processLine(curLine, qtyExemptMultiLoad, userData);
            if((Boolean)result.get(0)) placCardReq = true;//only set if true want to keep a true status
            if((Boolean)result.get(1)) trecReq = true;
            qtyExemptMultiLoad = (Double)result.get(2);
            if(!placCardReq){
                if(qtyExemptMultiLoad>=100){
                    placCardReq = true;
                    trecReq = true;
                }
            }
            
            cargoLinesQ.removeAnd("recordID");
            cargoLinesQ.addAnd("recordID",line.getRecordID(),EMCQueryConditions.NOT); //not the row to be deleted
            cargoLinesQ.addAnd("recordID",curLine.getRecordID(),EMCQueryConditions.NOT); //also not the current row
            List<TRECCargoCheckLines> tempLines = util.executeGeneralSelectQuery(cargoLinesQ, userData);
            loadCompQ.removeAnd("classId");
            loadCompQ.addAnd("classId", result.get(4)); //set the class id of the line that is processed
            notes = "";
            curLine.setAllowed(true);
            //for loop to determine if this UN Number is compatible with all other lines.
            for(TRECCargoCheckLines otherLine:tempLines){
                //now determine the class of the compared to line
                EMCQuery uNQuery = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
                uNQuery.addAnd("unNumber", otherLine.getUnNumber());
                uNQuery.addAnd("shippingName", otherLine.getProperShipping());
                TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(uNQuery, userData);
                if(chemical == null){
                    throw new EMCEntityBeanException("Failed to get Chemical:"+otherLine.getUnNumber());
                }
                //now get the class also check if parent class should be used
                EMCQuery classQ = new EMCQuery(enumQueryTypes.SELECT, TRECClasses.class);
                classQ.addAnd("classId", chemical.getClassId());
                TRECClasses theClass = (TRECClasses) util.executeSingleResultQuery(classQ, userData);
                if(theClass == null) throw new EMCEntityBeanException("Failed to get Class:"+chemical.getClassId());
                if(!isBlank(theClass.getParentClass())){
                    classQ.removeAnd("classId");
                    classQ.addAnd("classId", theClass.getParentClass());
                    theClass = (TRECClasses) util.executeSingleResultQuery(classQ, userData);
                    if(theClass == null) throw new EMCEntityBeanException("Failed to get parent Class:"+theClass.getParentClass());
                }
                loadCompQ.removeAnd("otherClassId");
                loadCompQ.addAnd("otherClassId", theClass.getClassId());
                TRECLoadCompatibility loadComp = (TRECLoadCompatibility)util.executeSingleResultQuery(loadCompQ, userData);
                if(loadComp == null){
                    throw new EMCEntityBeanException("Failed to get Load Compatibility for:"+result.get(4) + " related Class:"+theClass.getClassId());
                }
                //not allowed
                if(!loadComp.isAllowed()){
                    curLine.setAllowed(false);
                    loadOK = false;
                    message = true;
                    Logger.getLogger("emc").log(Level.INFO, "Incompatible UN Number:" + result.get(5), new Object[]{userData, "incomp", "x"});
                }
                //this line notes
                if(!isBlank(loadComp.getNote())){
                    notes += chemical.getUnNumber() + "(" + theClass.getClassId() + "):" + result.get(5) + "(" + result.get(4) + ") " + loadComp.getNote() +"\n";
                }
            }
            curLine.setLineNote(notes);
            this.doUpdate(curLine, userData);
            //all line notes
            allLineNotes += curLine.getLineNote();
            //placcard type:
            if(!(result.get(3).equals(eRG) && result.get(4).equals(hazClass))){
                placCardType = mixedLoad;
            }
            //class notes
            if(!notesMap.containsKey(result.get(4).toString())){
                notesMap.put(result.get(4).toString(), null);
                classNotes+=curLine.getClassNote() + "\n";
            }
        }
        if(message){
           Logger.getLogger("emc").log(Level.INFO, "List complete.", new Object[]{userData, "/incomp", "x"});
        }
        if(trecReq && placCardType.equals(noRequired)){
            placCardType = unNumberHaz;
        }
        //no placCard needed
        if(!placCardReq && !placCardType.equals(noRequired)){
            placCardType = noRequired;
        }
        //update Master
        EMCQuery master = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckMaster.class);
        master.addAnd("cargoCheckNumber", line.getCargoCheckNumber());
        TRECCargoCheckMaster masterRec = (TRECCargoCheckMaster) util.executeSingleResultQuery(master, userData);
        if(masterRec == null) throw new EMCEntityBeanException("Failed to find master record:"+ line.getCargoCheckNumber());
        masterRec.setNotes(classNotes+"\n"+allLineNotes+notes);
        masterRec.setPlaceCard(placCardType);
        masterRec.setTrecRequired(trecReq);
        masterRec.setCargo(loadOK);
        masterBean.update(masterRec, userData);
        return line;
    }
    private TRECCargoCheckLines checkLoad(TRECCargoCheckLines line,EMCUserData userData) throws EMCEntityBeanException{
        boolean trecReq = false;
        boolean placCardReq = false;
        boolean message = false;
        line.setAllowed(true);
        String hazClass = "";
        String eRG = "";
        String placCardType = noRequired;
        String classNotes = "";
        String allLineNotes = "";
        String notes = "";
        HashMap<String,TRECClasses> notesMap = new HashMap();
        //get the class need the unnumber to get its class this is for the current line
        EMCQuery uNQuery = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        uNQuery.addAnd("unNumber", line.getUnNumber());
        uNQuery.addAnd("shippingName", line.getProperShipping());
        TRECChemicals chemical = (TRECChemicals) util.executeSingleResultQuery(uNQuery, userData);
        if(chemical == null){
            throw new EMCEntityBeanException("Failed to get Chemical:"+line.getUnNumber());
        }
        //now get the class also check if parent class should be used
        EMCQuery classQ = new EMCQuery(enumQueryTypes.SELECT, TRECClasses.class);
        classQ.addAnd("classId", chemical.getClassId());
        TRECClasses theClass = (TRECClasses) util.executeSingleResultQuery(classQ, userData);
        if(theClass == null) throw new EMCEntityBeanException("Failed to get Class:"+chemical.getClassId());
        if(!isBlank(theClass.getParentClass())){
            classQ.removeAnd("classId");
            classQ.addAnd("classId", theClass.getParentClass());
            theClass = (TRECClasses) util.executeSingleResultQuery(classQ, userData);
            if(theClass == null) throw new EMCEntityBeanException("Failed to get parent Class:"+theClass.getParentClass());
        }
        line.setClassNote(theClass.getNotes());
        if(!notesMap.containsKey(theClass.getClassId())){
            notesMap.put(theClass.getClassId(), theClass);
            classNotes+=theClass.getNotes() + "\n";
        }
        hazClass = theClass.getClassId();
        eRG = chemical.getErg();
        //loop through existing lines and determine need for placcard and if this line may be shipped with any of them.
        EMCQuery cargoLinesQ = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckLines.class);
        cargoLinesQ.addAnd("cargoCheckNumber", line.getCargoCheckNumber());
        List<TRECCargoCheckLines> cargoLines = util.executeGeneralSelectQuery(cargoLinesQ, userData);
        EMCQuery loadCompQ = new EMCQuery(enumQueryTypes.SELECT, TRECLoadCompatibility.class);
        loadCompQ.addAnd("classId", theClass.getClassId());
        double qtyExemptMultiLoad = 0.0;
        //class notes

        for(TRECCargoCheckLines curLine:cargoLines){
            //ignore the current line being saved will add it at the end
            if(curLine.getRecordID() != line.getRecordID()){
                List result = processLine(curLine, qtyExemptMultiLoad, userData);
                if((Boolean)result.get(0)) placCardReq = true;//only set if true want to keep a true status
                if((Boolean)result.get(1)) trecReq = true;
                qtyExemptMultiLoad = (Double)result.get(2);
                loadCompQ.removeAnd("otherClassId");
                loadCompQ.addAnd("otherClassId", result.get(4));
                TRECLoadCompatibility loadComp = (TRECLoadCompatibility)util.executeSingleResultQuery(loadCompQ, userData);
                if(loadComp == null){
                    throw new EMCEntityBeanException("Failed to get Load Compatibility for:"+theClass.getClassId() + " related Class:"+result.get(4));
                }
                //not allowed
                if(!loadComp.isAllowed()){
                    line.setAllowed(false);
                    message = true;
                    Logger.getLogger("emc").log(Level.INFO, "Incompatible UN Number:" + result.get(5), new Object[]{userData, "incomp", "x"});
                }
                //this line notes
                if(!isBlank(loadComp.getNote())){
                    notes += chemical.getUnNumber() + "(" + theClass.getClassId() + "):" + result.get(5) + "(" + result.get(4) + ") " + loadComp.getNote() +"\n";
                }
                //all line notes
                allLineNotes += curLine.getLineNote();
                //placcard type:
                if(!(result.get(3).equals(eRG) && result.get(4).equals(hazClass))){
                    placCardType = mixedLoad;
                }
                //class notes
                if(!notesMap.containsKey(result.get(4).toString())){
                    notesMap.put(result.get(4).toString(), null);
                    classNotes+=curLine.getClassNote() + "\n";
                }
            }

        }
        //check new or updated line for exempt qty
        line.setLineNote(notes);
        if(!placCardReq){
            List result = processLine(line, qtyExemptMultiLoad, userData);
            qtyExemptMultiLoad = (Double)result.get(2);
            if((Boolean)result.get(0)) placCardReq = true;//only set if true want to keep a true status
            if((Boolean)result.get(1)) trecReq = true;
            if(qtyExemptMultiLoad>=100){
                placCardReq = true;
                trecReq = true;
            }
        }
        if(message){
           Logger.getLogger("emc").log(Level.INFO, "List complete.", new Object[]{userData, "/incomp", "x"});
        }
        //Placcard Needed
        if(trecReq && placCardType.equals(noRequired)){
            placCardType = unNumberHaz;
        }
        //no placCard needed
        if(!placCardReq && !placCardType.equals(noRequired)){
            placCardType = noRequired;
        }
        //update Master
        EMCQuery master = new EMCQuery(enumQueryTypes.SELECT, TRECCargoCheckMaster.class);
        master.addAnd("cargoCheckNumber", line.getCargoCheckNumber());
        TRECCargoCheckMaster masterRec = (TRECCargoCheckMaster) util.executeSingleResultQuery(master, userData);
        if(masterRec == null) throw new EMCEntityBeanException("Failed to find master record:"+ line.getCargoCheckNumber());
        masterRec.setNotes(classNotes+"\n"+allLineNotes+notes);
        masterRec.setPlaceCard(placCardType);
        masterRec.setTrecRequired(trecReq);
        if(!line.isAllowed()){
            masterRec.setCargo(false);
        }
        masterBean.update(masterRec, userData);
        return line;
    }

}
