/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.phrases;

import emc.bus.trec.chemicals.TRECChemicalsLocal;
import emc.bus.trec.customerchemicals.TRECCustomerChemicalsLocal;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECChemicalsSuper;
import emc.entity.trec.TRECCustomerChemicals;
import emc.entity.trec.TRECPhrases;
import emc.enums.emcquery.EMCQueryBracketConditions;
import emc.enums.enumQueryTypes;
import emc.enums.trec.TRECTypeEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import emc.helpers.trec.TRECPhraseHelper;
import emc.tables.EMCTable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class TRECPhrasesBean extends EMCEntityBean implements TRECPhrasesLocal {

    private final String special = "tr3ck3y";
    @EJB
    private TRECChemicalsLocal chemicalsBean;
    @EJB
    private TRECCustomerChemicalsLocal customerChemicalsBean;

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);

        super.checkCompanyId(query, userData);

        query = handleEncryptedSearching(query, userData);

        List<TRECPhrases> selectedData = util.executeNativeQuery(query, TRECPhrases.class, userData);
        selectedData = selectedData.subList(start, end > selectedData.size() ? selectedData.size() : end);

        util.detachEntities(userData);

        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        qu.addFieldDecrypt("phrase", special);
        qu.addFieldDecrypt("ergNumber", special);

        for (TRECPhrases ph : selectedData) {
            qu.removeAnd("recordID");
            qu.addAnd("recordID", ph.getRecordID());

            List resultList = util.executeNativeQuery(qu, userData);

            if (resultList.size() > 0) {
                Object[] result = (Object[]) resultList.get(0);

                if (result[0] == null) {
                    ph.setPhrase(null);
                } else if (result[0] instanceof String) {
                    ph.setPhrase((String) result[0]);
                } else {
                    ph.setPhrase(new String((byte[]) result[0]));
                }

                if (result[1] == null) {
                    ph.setErgNumber(null);
                } else if (result[1] instanceof String) {
                    ph.setErgNumber((String) result[1]);
                } else {
                    ph.setErgNumber(new String((byte[]) result[1]));
                }
            }
        }

        return selectedData;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);

        super.checkCompanyId(query, userData);

        query = handleEncryptedSearching(query, userData);

        List dataList = util.executeNativeQuery(query, userData);

        return String.valueOf(dataList.size());
    }

    private EMCQuery handleEncryptedSearching(EMCQuery query, EMCUserData userData) {
        String alias = query.getTableAlias(TRECPhrases.class);

        List<String> whereList = query.getWhereFields();
        List<String> modifiedWhereList = new ArrayList<>();

        for (String where : whereList) {
            if (where.contains(alias + ".phrase ")) {
                where = where.replaceAll(alias + ".phrase ", "CAST(AES_DECRYPT(" + alias + ".phrase, \'" + special + "\') AS CHAR) ");
            } else if (where.contains(alias + ".ergNumber")) {
                where = where.replaceAll(alias + ".ergNumber", "CAST(AES_DECRYPT(" + alias + ".ergNumber, \'" + special + "\') AS CHAR)");
            }

            modifiedWhereList.add(where);
        }

        query.setWhereFields(modifiedWhereList);

        return query;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECPhrases record = (TRECPhrases) iobject;

        if (!isBlank(record.getErgNumber())) {
            updateChemicalsWithPhrase(record, userData);
        }

        setSortCode(record, userData);

        return super.insert(record, userData);
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECPhrases record = (TRECPhrases) uobject;

        updateChemicalsWithPhrase(record, userData);

        return super.update(record, userData);
    }

    @Override
    public boolean doDeleteValidation(EMCTable vobject, EMCUserData userData) throws EMCEntityBeanException {
        boolean valid = super.doDeleteValidation(vobject, userData);

        if (valid) {
            TRECPhrases phrase = (TRECPhrases) vobject;

            if (!isBlank(phrase.getTypeId()) && !isBlank(phrase.getPhraseId())) {
                String chemField = null;

                switch (TRECTypeEnum.fromString(phrase.getTypeId())) {
                    case A:
                        chemField = "aPhrases";
                        break;
                    case D:
                        chemField = "dPhrases";
                        break;
                    case E:
                        chemField = "ePhrases";
                        break;
                    case F:
                        chemField = "fPhrases";
                        break;
                    case H:
                        chemField = "hPhrases";
                        break;
                    case P:
                        chemField = "pPhrases";
                        break;
                    case Q:
                        chemField = "qPhrases";
                        break;
                    case S:
                        chemField = "sPhrases";
                        break;
                }

                EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
                query.addCustomAnd("AES_DECRYPT(" + chemField + ", \'" + special + "\') LIKE \'%" + phrase.getPhraseId() + "%\'");
                query.addField("recordID");

                List chemList = util.executeNativeQuery(query, userData);

                if (chemList != null && !chemList.isEmpty()) {
                    Logger.getLogger("emc").log(Level.SEVERE, "The selected phrase is still being used on some chemicals.", userData);

                    valid = false;
                }
            }
        }

        return valid;
    }

    private void updateChemicalsWithPhrase(TRECPhrases record, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.addAnd("recordID", record.getRecordID());
        query.addFieldDecrypt("ergNumber", special);

        List resultList = util.executeNativeQuery(query, userData);
        String persistedErg = null;

        if (resultList.size() > 0) {

            if (resultList.get(0) == null) {
                persistedErg = "";
            } else if (resultList.get(0) instanceof String) {
                persistedErg = (String) resultList.get(0);
            } else {
                persistedErg = new String((byte[]) resultList.get(0));
            }
        }

        String[] persistedErgSplit = persistedErg.split(";");

        List<String> persistedErgList = new ArrayList<>();

        for (String e : persistedErgSplit) {
            if (!persistedErgList.contains(e.trim())) {
                persistedErgList.add(e.trim());
            }
        }

        String[] ergSplit = (isBlank(record.getErgNumber()) ? "" : record.getErgNumber()).split(";");

        List<String> ergList = new ArrayList<>();

        for (String e : ergSplit) {
            if (!ergList.contains(e.trim())) {
                ergList.add(e.trim());
            }
        }

        Collections.sort(ergList);

        List<Object[]> chemicalList;

        StringBuilder ergString = new StringBuilder();

        String chemicalPhrases;
        String[] chemicalPhrasesSplit;
        List<String> chemicalPhrasesList;
        StringBuilder chemicalPhrasesString;

        for (String erg : ergList) {
            if (!isBlank(erg)) {

                erg = erg.trim();

                if (ergString.length() > 0) {
                    ergString.append("; ");
                }

                ergString.append(erg);

                query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
                query.addAnd("erg", erg);
                query.addField("recordID");
                query.addFieldDecrypt(record.getTypeId().toLowerCase() + "Phrases", special);
                chemicalList = util.executeNativeQuery(query, userData);

                for (Object[] chemical : chemicalList) {
                    if (chemical[1] == null) {
                        chemicalPhrases = "";
                    } else if (chemical[1] instanceof String) {
                        chemicalPhrases = (String) chemical[1];
                    } else {
                        chemicalPhrases = new String((byte[]) chemical[1]);
                    }

                    chemicalPhrasesSplit = chemicalPhrases.split(";");

                    chemicalPhrasesList = new ArrayList<>();

                    for (String p : chemicalPhrasesSplit) {
                        if (!chemicalPhrasesList.contains(p.trim())) {
                            chemicalPhrasesList.add(p.trim());
                        }
                    }

                    if (!chemicalPhrasesList.contains(record.getPhraseId())) {
                        chemicalPhrasesList.add(record.getPhraseId());
                    }

                    Collections.sort(chemicalPhrasesList);

                    chemicalPhrasesString = new StringBuilder();

                    for (String phrase : chemicalPhrasesList) {
                        if (isBlank(phrase)) {
                            continue;
                        }

                        if (chemicalPhrasesString.length() > 0) {
                            chemicalPhrasesString.append("; ");
                        }

                        chemicalPhrasesString.append(phrase);
                    }


                    if (!isBlank(chemicalPhrasesString.toString())) {
                        query = new EMCQuery(enumQueryTypes.UPDATE, TRECChemicals.class);
                        query.addAnd("recordID", chemical[0]);
                        query.addSetEncryption(record.getTypeId().toLowerCase() + "Phrases", special, chemicalPhrasesString.toString());

                        util.executeNativeUpdate(query, userData);
                    }
                }
            }

            persistedErgList.remove(erg); //to check for erg removal
        }

        if (!isBlank(ergString.toString())) {
            record.setErgNumber(ergString.toString());
        }
        //phrase removal from chemical/s
        for (String erg : persistedErgList) {
            if (!isBlank(erg)) {

                erg = erg.trim();

                query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
                query.addAnd("erg", erg);
                query.addField("recordID");
                query.addFieldDecrypt(record.getTypeId().toLowerCase() + "Phrases", special);
                chemicalList = util.executeNativeQuery(query, userData);

                for (Object[] chemical : chemicalList) {
                    if (chemical[1] == null) {
                        chemicalPhrases = "";
                    } else if (chemical[1] instanceof String) {
                        chemicalPhrases = (String) chemical[1];
                    } else {
                        chemicalPhrases = new String((byte[]) chemical[1]);
                    }

                    chemicalPhrasesSplit = chemicalPhrases.split(";");

                    chemicalPhrasesList = new ArrayList<>();

                    for (String p : chemicalPhrasesSplit) {
                        if (!chemicalPhrasesList.contains(p.trim())) {
                            chemicalPhrasesList.add(p.trim());
                        }
                    }

                    chemicalPhrasesList.remove(record.getPhraseId());

                    Collections.sort(chemicalPhrasesList);

                    chemicalPhrasesString = new StringBuilder();

                    for (String phrase : chemicalPhrasesList) {
                        if (isBlank(phrase)) {
                            continue;
                        }

                        if (chemicalPhrasesString.length() > 0) {
                            chemicalPhrasesString.append("; ");
                        }

                        chemicalPhrasesString.append(phrase);
                    }


                    query = new EMCQuery(enumQueryTypes.UPDATE, TRECChemicals.class);
                    query.addAnd("recordID", chemical[0]);
                    query.addSetEncryption(record.getTypeId().toLowerCase() + "Phrases", special, isBlank(chemicalPhrasesString.toString()) ? "" : chemicalPhrasesString.toString());

                    util.executeNativeUpdate(query, userData);
                }
            }
        }
    }

    /**
     * Special method to help with encryption
     *
     * @param phrase
     * @param userData
     */
    public void updateEncryptedFields(TRECPhrases ph, EMCUserData userData) {
        EMCQuery qu = new EMCQuery(enumQueryTypes.UPDATE, TRECPhrases.class);
        qu.addAnd("recordID", ph.getRecordID());
        if (!isBlank(ph.getPhrase())) {
            qu.addSetEncryption("phrase", special, ph.getPhrase());
        }
        if (!isBlank(ph.getErgNumber())) {
            qu.addSetEncryption("ergNumber", special, ph.getErgNumber());
        }
        util.executeNativeUpdate(qu, userData);
    }

    private void setSortCode(TRECPhrases phrase, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.addAnd("typeId", phrase.getTypeId());
        query.addAnd("parentClass", phrase.getParentClass());
        query.addFieldAggregateFunction("sortNumber", "MAX");
        Integer maxSort = (Integer) util.executeSingleResultQuery(query, userData);
        if (maxSort == null) {
            maxSort = 0;
        }

        phrase.setSortNumber(maxSort + 1);
    }

    @Override
    public List<TRECPhraseHelper> getPhrases(String UNNumber, String stringType, EMCUserData userData) {
        TRECTypeEnum type = null;
        List<TRECPhraseHelper> helperList = new ArrayList<>();

        if (isBlank(UNNumber)) {
            logMessage(Level.SEVERE, " Please set UN Number", userData);
            return null;
        }

        if (isBlank(stringType)) {
            logMessage(Level.SEVERE, " Please set type", userData);
            return null;
        }

        type = TRECTypeEnum.fromString(stringType);


        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addAnd("unNumber", UNNumber);

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        List<TRECChemicals> selectedData = (List<TRECChemicals>) chemicalsBean.getDataInRange(TRECChemicals.class, copyUserData, 0, Integer.MAX_VALUE);

        if (selectedData == null || selectedData.isEmpty()) {
            logMessage(Level.SEVERE, "Failed to find chemical for UM Number " + UNNumber, userData);
            return null;
        }

        try {
            switch (type) {
                case A:
                    helperList = getAPhrases(selectedData.get(0), userData);
                    break;
                case D:
                    helperList = getDPhrases(selectedData.get(0), userData);
                    break;
                case E:
                    //E Phrases are special - call seperate method
                    break;
                case F:
                    helperList = getFPhrases(selectedData.get(0), userData);
                    break;
                case H:
                    helperList = getHPhrases(selectedData.get(0), userData);
                    break;
                case P:
                    helperList = getPPhrases(selectedData.get(0), userData);
                    break;
                case Q:
                    helperList = getQPhrases(selectedData.get(0), userData);
                    break;
                case S:
                    helperList = getSPhrases(selectedData.get(0), userData);
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger("emc").log(Level.SEVERE, "Failed to get Phrases from Chemical.", userData);
        }


        return helperList;
    }

    private List<TRECPhraseHelper> getPPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.addAnd("typeId", "P");
        query.addAnd("standardPhrases", true);
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);

        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !isBlank(phrase.getErgNumber()) && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    private List<TRECPhraseHelper> getQPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.addAnd("typeId", "Q");
        query.addAnd("standardPhrases", true);
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);

        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && !isBlank(phrase.getErgNumber()) && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    private List<TRECPhraseHelper> getDPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();
        List<String> phraseIdList = null;

        String phraseString = chemical.getdPhrases();

        if (!isBlank(phraseString)) {
            String[] split = phraseString.split(";");

            if (split.length > 0) {
                phraseIdList = new ArrayList<>();

                for (String s : split) {
                    phraseIdList.add(s.trim());
                }
            }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.openConditionBracket(EMCQueryBracketConditions.NONE);
        query.addAnd("typeId", "D");
        query.addAnd("standardPhrases", true);
        query.closeConditionBracket();

        if (phraseIdList != null && !phraseIdList.isEmpty()) {
            query.openConditionBracket(EMCQueryBracketConditions.OR);
            query.addAndInList("phraseId", phraseIdList, true, false);
            query.closeConditionBracket();
        }

        query.closeConditionBracket();
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);
        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && !isBlank(phrase.getErgNumber()) && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                if (phrase.isStandardPhrases()) {
                    helper.setTicked(true);
                    helper.setMandatory(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    private List<TRECPhraseHelper> getAPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();
        List<String> phraseIdList = null;

        String phraseString = chemical.getaPhrases();

        if (!isBlank(phraseString)) {
            String[] split = phraseString.split(";");

            if (split.length > 0) {
                phraseIdList = new ArrayList<>();

                for (String s : split) {
                    phraseIdList.add(s.trim());
                }
            }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        if (phraseIdList != null && !phraseIdList.isEmpty()) {
            query.addAndInList("phraseId", phraseIdList, true, false);
        } else {
            query.addAnd("phraseId", null);
        }
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);
        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && !isBlank(phrase.getErgNumber()) && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    private List<TRECPhraseHelper> getSPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();
        List<String> phraseIdList = null;

        String phraseString = chemical.getsPhrases();

        if (!isBlank(phraseString)) {
            String[] split = phraseString.split(";");

            if (split.length > 0) {
                phraseIdList = new ArrayList<>();

                for (String s : split) {
                    phraseIdList.add(s.trim());
                }
            }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        if (phraseIdList != null && !phraseIdList.isEmpty()) {
            query.addAndInList("phraseId", phraseIdList, true, false);
        } else {
            query.addAnd("phraseId", null);
        }
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);

        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !isBlank(phrase.getErgNumber()) && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }


        }

        return retList;
    }

    private List<TRECPhraseHelper> getFPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();
        List<String> phraseIdList = null;

        String phraseString = chemical.getfPhrases();

        if (!isBlank(phraseString)) {
            String[] split = phraseString.split(";");

            if (split.length > 0) {
                phraseIdList = new ArrayList<>();

                for (String s : split) {
                    phraseIdList.add(s.trim());
                }
            }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        if (phraseIdList != null && !phraseIdList.isEmpty()) {
            query.addAndInList("phraseId", phraseIdList, true, false);
        } else {
            query.addAnd("phraseId", null);
        }
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);

        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && !isBlank(phrase.getErgNumber()) && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    private List<TRECPhraseHelper> getHPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();
        List<String> phraseIdList = null;

        String phraseString = chemical.gethPhrases();

        if (!isBlank(phraseString)) {
            String[] split = phraseString.split(";");

            if (split.length > 0) {
                phraseIdList = new ArrayList<>();

                for (String s : split) {
                    phraseIdList.add(s.trim());
                }
            }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        if (phraseIdList != null && !phraseIdList.isEmpty()) {
            query.addAndInList("phraseId", phraseIdList, true, false);
        } else {
            query.addAnd("phraseId", null);
        }
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);

        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && !isBlank(phrase.getErgNumber()) && !isBlank(phrase.getErgNumber()) && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    private List<TRECPhraseHelper> getEPhrases(TRECChemicals chemical, EMCUserData userData) {
        List<TRECPhraseHelper> retList = new ArrayList<>();
        List<String> phraseIdList = null;

        String phraseString = chemical.getePhrases();

        if (!isBlank(phraseString)) {
            String[] split = phraseString.split(";");

            if (split.length > 0) {
                phraseIdList = new ArrayList<>();

                for (String s : split) {
                    phraseIdList.add(s.trim());
                }
            }
        }

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        if (phraseIdList != null && !phraseIdList.isEmpty()) {
            query.addAndInList("phraseId", phraseIdList, true, false);
        } else {
            query.addAnd("phraseId", null);
        }
        query.addOrderBy("sortNumber");

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        Collection<TRECPhrases> selectedData = getDataInRange(TRECPhrases.class, copyUserData, 0, Integer.MAX_VALUE);

        for (TRECPhrases phrase : selectedData) {
            if (phrase.isClassSpecific() && !phrase.getParentClass().equalsIgnoreCase(chemical.getClassId())) {
                continue;
            } else {
                TRECPhraseHelper helper = new TRECPhraseHelper();
                helper.setPhraseId(phrase.getPhraseId());
                helper.setPhraseDescription(phrase.getPhrase());

                if (!phrase.isAddedPhrases() && !isBlank(phrase.getErgNumber()) && !isBlank(phrase.getErgNumber()) && phrase.getErgNumber().contains(chemical.getErg())) {
                    helper.setTicked(true);
                }

                retList.add(helper);
            }
        }

        return retList;
    }

    @Override
    public Map<String, List<TRECPhraseHelper>> getEPhrases(TRECCustomerChemicals custChemical, EMCUserData userData) {
        //Cleanup E Phrases
        customerChemicalsBean.checkEPhrases(custChemical, userData);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addAnd("unNumber", custChemical.getUnNumber());

        EMCUserData copyUserData = userData.copyUserData();
        copyUserData.setUserData(0, query);

        List<TRECChemicals> selectedData = (List<TRECChemicals>) chemicalsBean.getDataInRange(TRECChemicals.class, copyUserData, 0, Integer.MAX_VALUE);
        TRECChemicals chemical = selectedData.get(0);

        List<TRECPhraseHelper> aPhrases = getAPhrases(chemical, userData);
        List<TRECPhraseHelper> dPhrases = getDPhrases(chemical, userData);
        List<TRECPhraseHelper> fPhrases = getFPhrases(chemical, userData);
        List<TRECPhraseHelper> hPhrases = getHPhrases(chemical, userData);
        List<TRECPhraseHelper> pPhrases = getPPhrases(chemical, userData);
        List<TRECPhraseHelper> qPhrases = getQPhrases(chemical, userData);
        List<TRECPhraseHelper> sPhrases = getSPhrases(chemical, userData);
        List<TRECPhraseHelper> ePhrases = getEPhrases(chemical, userData);

        Map<String, List<TRECPhraseHelper>> ePraseMap = new TreeMap<>();

        if (!aPhrases.isEmpty()) {
            String phraseString = custChemical.getaPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : aPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.A.toString(), ePhraseList);
        }

        if (!dPhrases.isEmpty()) {
            String phraseString = custChemical.getdPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : dPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.D.toString(), ePhraseList);
        }

        if (!fPhrases.isEmpty()) {
            String phraseString = custChemical.getfPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : fPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.F.toString(), ePhraseList);
        }

        if (!hPhrases.isEmpty()) {
            String phraseString = custChemical.gethPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : hPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.H.toString(), ePhraseList);
        }

        if (!pPhrases.isEmpty()) {
            String phraseString = custChemical.getpPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : pPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.P.toString(), ePhraseList);
        }

        if (!qPhrases.isEmpty()) {
            String phraseString = custChemical.getqPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : qPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.Q.toString(), ePhraseList);
        }

        if (!sPhrases.isEmpty()) {
            String phraseString = custChemical.getsPhrases();
            List<String> selectedPhrases = new ArrayList<>();
            if (!isBlank(phraseString)) {
                String[] split = phraseString.split(";");

                for (String s : split) {
                    selectedPhrases.add(s.trim());
                }
            }

            List<TRECPhraseHelper> ePhraseList = new ArrayList<>();

            for (TRECPhraseHelper h : sPhrases) {
                h.setTicked(false);
                h.setMandatory(false);

                if (!selectedPhrases.contains(h.getPhraseId())) {
                    ePhraseList.add(h);
                }
            }

            ePraseMap.put(TRECTypeEnum.S.toString(), ePhraseList);
        }

        if (!ePhrases.isEmpty()) {
            ePraseMap.put(TRECTypeEnum.E.toString(), ePhrases);
        }

        return ePraseMap;
    }

    @Override
    public void checkTRECPhrases(boolean fixData, EMCUserData userData) {
        EMCUserData copyUD = userData.copyUserData();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        copyUD.setUserData(0, query);

        List<TRECPhrases> phrasesList = (List<TRECPhrases>) getDataInRange(TRECPhrases.class, copyUD, 0, 1000000);

        for (TRECPhrases phrase : phrasesList) {
            if (!isBlank(phrase.getErgNumber())) {
                String ergString = phrase.getErgNumber();
                String[] ergSplit = ergString.split(";");
                List<String> ergStringList = new ArrayList<>();

                for (String s : ergSplit) {
                    ergStringList.add(s.trim());
                }

                query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
                query.addAndInList("erg", ergStringList, true, false);
                copyUD.setUserData(0, query);

                List<TRECChemicals> chemicalList = (List<TRECChemicals>) chemicalsBean.getDataInRange(TRECChemicals.class, copyUD, 0, 1000000);

                String phraseField = null;

                switch (TRECTypeEnum.fromString(phrase.getTypeId())) {
                    case A:
                        phraseField = "aPhrases";
                        break;
                    case D:
                        phraseField = "dPhrases";
                        break;
                    case E:
                        phraseField = "ePhrases";
                        break;
                    case F:
                        phraseField = "fPhrases";
                        break;
                    case H:
                        phraseField = "hPhrases";
                        break;
                    case P:
                        phraseField = "pPhrases";
                        break;
                    case Q:
                        phraseField = "qPhrases";
                        break;
                    case S:
                        phraseField = "sPhrases";
                        break;
                }


                try {
                    Field f = TRECChemicalsSuper.class.getDeclaredField(phraseField);
                    f.setAccessible(true);

                    boolean requireFix = false;

                    for (TRECChemicals chemical : chemicalList) {
                        String chemicalPhraseString = (String) f.get(chemical);

                        if (isBlank(chemicalPhraseString) || !chemicalPhraseString.contains(phrase.getPhraseId())) {
                            requireFix = true;
                            if (!fixData) {
                                Logger.getLogger("emc").log(Level.SEVERE, "Chemical " + chemical.getUnNumber() + " does not contain phrase " + phrase.getPhraseId() + ". Phrase does contain ERG " + chemical.getErg(), userData);
                            }
                        }
                    }

                    if (requireFix && fixData) {
                        updateChemicalsWithPhrase(phrase, userData);
                        Logger.getLogger("emc").log(Level.SEVERE, "Phrase " + phrase.getPhraseId() + " has been added relavent chemicals.", userData);
                    }

                } catch (ReflectiveOperationException e) {
                    Logger.getLogger("emc").log(Level.SEVERE, "Failed to check phrase " + phrase.getPhraseId() + " due to reflection failure.", userData);
                }
            }
        }
    }
}