/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.base.numbersequences;

import emc.entity.base.numbersequences.BaseAvailableSequenceNumbers;
import emc.entity.base.numbersequences.BaseNumberSequence;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumPersistOptions;
import emc.enums.enumQueryTypes;
import emc.functions.BitArray;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import emc.tables.EMCTable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author riaan
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
public class BaseNumberSequenceBean extends EMCEntityBean implements BaseNumberSequenceLocal {

    @EJB
    private BaseNumberSequenceLogicLocal numSeqLogicBean;
    @EJB
    private NumberSequenceConstantsLocal constants;
    @EJB
    private BaseAvailableSequenceNumbersLocal availableNumbersBean;
    @PersistenceContext
    private EntityManager em;

    /** Creates a new instance of BaseNumberSequenceBean */
    public BaseNumberSequenceBean() {
    }

    @Override
    public boolean doInsertValidation(EMCTable vobject, EMCUserData userData) {
        Boolean ret = (Boolean) super.doInsertValidation(vobject, userData);

        BaseNumberSequence toBeTested = (BaseNumberSequence) vobject;

        if (!checkDefault(toBeTested, userData)) {
            Logger.getLogger("emc").log(Level.SEVERE, "A default number sequence has to exist.", userData);
            return false;
        }

        if (toBeTested.getMaxNumber() == 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Max number may not be 0", userData);
            ret = false;
        } else if (toBeTested.isRandom() && toBeTested.getMaxNumber() > 1000000) {
            Logger.getLogger("emc").log(Level.SEVERE, "Random number may not be greater 1000000", userData);
            ret = false;
        }



        if ((isBlank(toBeTested.getPerField()) && !isBlank(toBeTested.getPerValue())) ||
                (!isBlank(toBeTested.getPerField()) && isBlank(toBeTested.getPerValue()))) {
            Logger.getLogger("emc").log(Level.SEVERE, "Per Value is required.", userData);
            ret = false;
        }
        return ret;
    }

    @Override
    public boolean doUpdateValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean ret = super.doUpdateValidation(vobject, userData);
        BaseNumberSequence toBeTested = (BaseNumberSequence) vobject;
        if ((isBlank(toBeTested.getPerField()) && !isBlank(toBeTested.getPerValue())) ||
                (!isBlank(toBeTested.getPerField()) && isBlank(toBeTested.getPerValue()))) {
            Logger.getLogger("emc").log(Level.SEVERE, "You need to set both the Per Field and Per Value if you set the one.", userData);
            ret = false;
        }
        return ret;
    }

    private BaseNumberSequence setUniqueConstraint(BaseNumberSequence toSet, EMCUserData userData) {
        toSet.setUniqueDescription(toSet.getRefTable() + toSet.getRefField() + toSet.getPerField() + toSet.getPerValue());
        return toSet;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        BaseNumberSequence numSeq = (BaseNumberSequence) iobject;
        numSeq.setNextAvailableNumber(numSeq.getMinNumber() - 1);
        setUniqueConstraint(numSeq, userData);
        if (numSeq.isRandom()) {
            BitArray randomNumbers = new BitArray(new Long(numSeq.getMaxNumber()).intValue());
            randomNumbers.setZero();
            numSeq.setRandomNumbers(randomNumbers.toBytes());
        }
        super.insert(numSeq, userData);
        return numSeqLogicBean.generateNextNumber(numSeq, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        Object ret = uobject;

        if (userData.getUserData() != null && userData.getUserData().size() >= 6) {
            if (enumPersistOptions.TEST_UPDATE_DELETE.toString().equals(userData.getUserData().get(5))) {
                return super.update(uobject, userData);
            }
        }

        BaseNumberSequence numSeq = (BaseNumberSequence) uobject;
        setUniqueConstraint(numSeq, userData);
        BaseNumberSequence persisted = em.find(BaseNumberSequence.class, numSeq.getRecordID());

        //XML Handler can not encode BitArray so value gets lost between server and client.
        //Field can not be adjusted on client so this should be save
        if (numSeq.getRandomNumbers() == null && persisted.getRandomNumbers() != null) {
            numSeq.setRandomNumbers(persisted.getRandomNumbers());
        }

        String persistedPrefix = persisted.getPrefixString();
        String newPrefix = numSeq.getPrefixString();

        String persistedSuffix = persisted.getSuffixString();
        String newSuffix = numSeq.getSuffixString();

        //Checks whether prefix or suffix has changed
        if (((isBlank(persistedPrefix) && !isBlank(newPrefix)) || (newPrefix != null && !newPrefix.equals(persistedPrefix))) ||
                ((isBlank(persistedSuffix) && !isBlank(newSuffix)) || (newSuffix != null && !newSuffix.equals(persistedSuffix))) ||
                persisted.getMaxNumber() != numSeq.getMaxNumber() || persisted.getMinNumber() != numSeq.getMinNumber()) {

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseAvailableSequenceNumbers.class.getName());
            query.addAnd("sequenceNumberId", numSeq.getNumberSequenceId());

            List<BaseAvailableSequenceNumbers> availableNumbers = (List<BaseAvailableSequenceNumbers>) util.executeGeneralSelectQuery(query, userData);

            for (BaseAvailableSequenceNumbers available : availableNumbers) {
                if (numSeq.getNextAvailableNumber() < numSeq.getMinNumber()) {
                    numSeq.setNextAvailableNumber(numSeq.getMinNumber());
                }
                if (persistedPrefix == null) {
                    persistedPrefix = "";
                }
                if (persistedSuffix == null) {
                    persistedSuffix = "";
                }
                //Extract 'current' number
                String oldSequenceNumber = available.getSequenceNumber();
                String numeric = oldSequenceNumber.substring(persistedPrefix.length(), oldSequenceNumber.length() - persistedSuffix.length());

                available.setSequenceNumber(createNumberWithLeadingZeroes(numSeq, Long.valueOf(numeric)));
                availableNumbersBean.update(available, userData);
            }
        }

        //More numbers available
        if (persisted.getMaxNumber() < numSeq.getMaxNumber() && numSeq.getNextAvailableNumber() == constants.getNumSeqFlag()) {
            numSeq.setNextAvailableNumber(persisted.getMaxNumber());
            ret = numSeqLogicBean.generateNextNumber(numSeq, userData);
        } else {
            ret = super.update(uobject, userData);
        }

        //Prevent numbers from being generated recursively.  There should always be at least 5 numbers.
        if (userData.getUserData(8) != Boolean.TRUE) {
            userData.setUserData(8, true);

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseAvailableSequenceNumbers.class);
            query.addAnd("sequenceNumberId", numSeq.getNumberSequenceId());
            //Only select available numbers.
            query.addAnd("status", 0);

            List<BaseAvailableSequenceNumbers> available = (List<BaseAvailableSequenceNumbers>) util.executeGeneralSelectQuery(query, userData);

            if (available.size() < 5) {
                int toGenerate = 5 - available.size();

                //Ensure that there are at least 5 available numbers.
                for (int i = 0; i < toGenerate; i++) {
                    numSeqLogicBean.generateNextNumber(numSeq, userData);
                }
            }
        }

        return ret;
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord,
            EMCUserData userData) {
        Boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);
        BaseNumberSequence numSeq = (BaseNumberSequence) theRecord;
        if (fieldNameToValidate.equals("perField")) {
            if (!checkDefault(numSeq, userData)) {
                Logger.getLogger("emc").log(Level.SEVERE, "A default number sequence has to exist.", userData);
                return false;
            }
            if (isBlank(numSeq.getPerField())) {
                numSeq.setPerField(null);
                return numSeq;
            }
        }
        if (fieldNameToValidate.equals("perValue")) {
            if (this.isBlank(numSeq.getPerField())) {
                ret = false;
                Logger.getLogger("emc").log(Level.SEVERE, "Cannot set per value before per Field is chosen.", userData);
            }
        }
        if (fieldNameToValidate.equals("minNumber")) {
            ret = ret && validateMinNumber(numSeq, userData);
        }

        if (fieldNameToValidate.equals("maxNumber")) {
            ret = ret && validateMaxNumber(numSeq, userData);
        }

        if (fieldNameToValidate.equals("random") && numSeq.getRecordID() != 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "You may not change the random flag after the number sequence has been created.", userData);
            ret = false;
        }

        return ret;
    }

    private boolean validateMinNumber(BaseNumberSequence numSeq, EMCUserData userData) {
        if (numSeq.getRecordID() != 0 && ((BaseNumberSequence) util.findPersisted(BaseNumberSequence.class, numSeq.getRecordID(), userData)).getMinNumber() > numSeq.getMinNumber()) {
            Logger.getLogger("emc").log(Level.SEVERE, "Min number may not be decreased", userData);
            return false;
        }
        return true;
    }

    private boolean validateMaxNumber(BaseNumberSequence numSeq, EMCUserData userData) {
        if (numSeq.getRecordID() != 0) {
            if (numSeq.getNextAvailableNumber() == constants.getNumSeqFlag()) {
                //Get persisted record and check max
                BaseNumberSequence persisted = (BaseNumberSequence) em.find(BaseNumberSequence.class, numSeq.getRecordID());

                if (numSeq.getMaxNumber() < persisted.getMaxNumber()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Max number is too small.  Already exceeded", userData);
                    return false;
                }
            } else if (numSeq.getMaxNumber() < numSeq.getNextAvailableNumber()) {
                Logger.getLogger("emc").log(Level.SEVERE, "Max number is too small.  Already exceeded", userData);
                return false;
            }
        }

        if (numSeq.getMaxNumber() < 0) {
            Logger.getLogger("emc").log(Level.SEVERE, "Max number may not be negative", userData);
            return false;
        }

        return true;
    }

    private boolean checkDefault(BaseNumberSequence record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseNumberSequence.class.getName());
        query.addAnd("refTable", record.getRefTable());
        query.addAnd("refField", record.getRefField());
        //Ignore the record being validated.  It may exist as a default number sequence.
        query.addAnd("recordID", record.getRecordID(), EMCQueryConditions.NOT);
        List<BaseNumberSequence> list = (List<BaseNumberSequence>) util.executeGeneralSelectQuery(query, userData);
        if (list.size() == 0) {
            if (isBlank(record.getPerField())) {
                return true;
            }
            return false;
        }
        for (BaseNumberSequence defaultRecord : list) {
            if (isBlank(defaultRecord.getPerField())) {
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to add leading zeroes to a number
     */
    private String createNumberWithLeadingZeroes(BaseNumberSequence numSeq, long nextNumber) {

        String zeroes = "";

        int zeroesToAdd = (numSeq.getMaxNumber() + "").length() - (nextNumber + "").length();

        for (int i = 0; i < zeroesToAdd; i++) {
            zeroes += "0";
        }

        return (numSeq.getPrefixString() == null ? "" : numSeq.getPrefixString()) + zeroes + nextNumber + (numSeq.getSuffixString() == null ? "" : numSeq.getSuffixString());
    }

    public void populateModuleId(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, BaseNumberSequence.class.getName());
        List<BaseNumberSequence> numberList = util.executeGeneralSelectQuery(query, userData);
        for (BaseNumberSequence seq : numberList) {
            seq.setModuleId(Functions.getEMCModule(seq.getRefTable()).toString());
            this.update(seq, userData);
        }
        logMessage(Level.INFO, "Done!", userData);
    }

    public boolean regenerateRandomNumbers(BaseNumberSequence numSeq, EMCUserData userData) throws EMCEntityBeanException {
        if (!numSeq.isRandom()) {
            logMessage(Level.SEVERE, "The selected number sequence does not support random numbers.", userData);
        }

        BitArray randomNumbers = new BitArray(new Long(numSeq.getMaxNumber()).intValue());
        randomNumbers.setZero();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, numSeq.getRefTable());
        if (!isBlank(numSeq.getPrefixString())) {
            query.addAnd(numSeq.getRefField(), numSeq.getPrefixString(), EMCQueryConditions.STARTS_WITH);
        }
        if (!isBlank(numSeq.getSuffixString())) {
            //query.addAnd(numSeq.getRefField(), numSeq.getSuffixString(), EMCQueryConditions.STARTS_WITH);
        }
        if (!isBlank(numSeq.getPerField()) && !isBlank(numSeq.getPerValue())) {
            query.addAnd(numSeq.getPerField(), numSeq.getPerValue());
        }
        query.addField(numSeq.getRefField());

        List<String> usedNumberList = util.executeGeneralSelectQuery(query, userData);
        int usedNumberInteger;

        for (String usedNumber : usedNumberList) {
            if (!isBlank(numSeq.getPrefixString())) {
                usedNumber = usedNumber.substring(numSeq.getPrefixString().length());
            }
            if (!isBlank(numSeq.getSuffixString())) {
                usedNumber = usedNumber.substring(0, usedNumber.length() - numSeq.getPrefixString().length() - 1);
            }
            //ICP Adds P to End
            if (usedNumber.endsWith("P")) {
                usedNumber = usedNumber.substring(0, usedNumber.length() - 1 - 1);
            }

            usedNumberInteger = Integer.parseInt(usedNumber);

            randomNumbers.set(usedNumberInteger);
        }
        query = new EMCQuery(enumQueryTypes.DELETE, BaseAvailableSequenceNumbers.class);
        query.addAnd("sequenceNumberId", numSeq.getNumberSequenceId());
        util.executeUpdate(query, userData);

        numSeq.setRandomNumbers(randomNumbers.toBytes());
        
        update(numSeq, userData);

//        numSeqLogicBean.generateNextNumber(numSeq, userData);

        return true;
    }
}
