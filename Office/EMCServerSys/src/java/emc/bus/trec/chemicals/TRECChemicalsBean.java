/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.chemicals;

import emc.bus.trec.phrases.TRECPhrasesLocal;
import emc.entity.trec.TRECChemicals;
import emc.entity.trec.TRECClasses;
import emc.entity.trec.TRECErgMaster;
import emc.entity.trec.TRECPhraseCombinations;
import emc.entity.trec.TRECPhrases;
import emc.enums.emcquery.EMCQueryConditions;
import emc.enums.enumQueryTypes;
import emc.enums.trec.TRECTypeEnum;
import emc.framework.EMCEntityBean;
import emc.framework.EMCEntityBeanException;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.Column;

/**
 *
 * @author wikus
 */
@Stateless
public class TRECChemicalsBean extends EMCEntityBean implements TRECChemicalsLocal {

    private final String special = "tr3ck3y";
    @EJB
    private TRECPhrasesLocal phrasesBean;

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        EMCQuery query = (EMCQuery) userData.getUserData(0);

        super.checkCompanyId(query, userData);

        query = handleEncryptedSearching(query, userData);

        List<TRECChemicals> selectedData = util.executeNativeQuery(query, TRECChemicals.class, userData);
        selectedData = selectedData.subList(start, end > selectedData.size() ? selectedData.size() : end);

        util.detachEntities(userData);

        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        qu.addFieldDecrypt("phrasesRefNum", special);
        qu.addFieldDecrypt("hPhrases", special);
        qu.addFieldDecrypt("pPhrases", special);
        qu.addFieldDecrypt("qPhrases", special);
        qu.addFieldDecrypt("dPhrases", special);
        qu.addFieldDecrypt("sPhrases", special);
        qu.addFieldDecrypt("fPhrases", special);
        qu.addFieldDecrypt("aPhrases", special);
        qu.addFieldDecrypt("ePhrases", special);

        for (TRECChemicals ch : selectedData) {
            qu.removeAnd("recordID");
            qu.addAnd("recordID", ch.getRecordID());
            List resultList = util.executeNativeQuery(qu, userData);
            if (resultList.size() > 0) {
                Object[] result = (Object[]) resultList.get(0);

                if (result[0] == null || (result[0] instanceof String && isBlank((String) result[0]) || isBlank(new String((byte[]) result[0])))) {
                    ch.setPhrasesRefNum(null);
                } else if (result[0] instanceof String) {
                    ch.setPhrasesRefNum((String) result[0]);
                } else {
                    ch.setPhrasesRefNum(new String((byte[]) result[0]));
                }

                if (result[1] == null || (result[1] instanceof String && isBlank((String) result[1]) || isBlank(new String((byte[]) result[1])))) {
                    ch.sethPhrases(null);
                } else if (result[1] instanceof String) {
                    ch.sethPhrases((String) result[1]);
                } else {
                    ch.sethPhrases(new String((byte[]) result[1]));
                }

                if (result[2] == null || (result[2] instanceof String && isBlank((String) result[2]) || isBlank(new String((byte[]) result[2])))) {
                    ch.setpPhrases(null);
                } else if (result[2] instanceof String) {
                    ch.setpPhrases((String) result[2]);
                } else {
                    ch.setpPhrases(new String((byte[]) result[2]));
                }

                if (result[3] == null || (result[3] instanceof String && isBlank((String) result[3]) || isBlank(new String((byte[]) result[3])))) {
                    ch.setqPhrases(null);
                } else if (result[3] instanceof String) {
                    ch.setqPhrases((String) result[3]);
                } else {
                    ch.setqPhrases(new String((byte[]) result[3]));
                }

                if (result[4] == null || (result[4] instanceof String && isBlank((String) result[4]) || isBlank(new String((byte[]) result[4])))) {
                    ch.setdPhrases(null);
                } else if (result[4] instanceof String) {
                    ch.setdPhrases((String) result[4]);
                } else {
                    ch.setdPhrases(new String((byte[]) result[4]));
                }

                if (result[5] == null || (result[5] instanceof String && isBlank((String) result[5]) || isBlank(new String((byte[]) result[5])))) {
                    ch.setsPhrases(null);
                } else if (result[5] instanceof String) {
                    ch.setsPhrases((String) result[5]);
                } else {
                    ch.setsPhrases(new String((byte[]) result[5]));
                }

                if (result[6] == null || (result[6] instanceof String && isBlank((String) result[6]) || isBlank(new String((byte[]) result[6])))) {
                    ch.setfPhrases(null);
                } else if (result[6] instanceof String) {
                    ch.setfPhrases((String) result[6]);
                } else {
                    ch.setfPhrases(new String((byte[]) result[6]));
                }

                if (result[7] == null || (result[7] instanceof String && isBlank((String) result[7]) || isBlank(new String((byte[]) result[7])))) {
                    ch.setaPhrases(null);
                } else if (result[7] instanceof String) {
                    ch.setaPhrases((String) result[7]);
                } else {
                    ch.setaPhrases(new String((byte[]) result[7]));
                }

                if (result[8] == null || (result[8] instanceof String && isBlank((String) result[8]) || isBlank(new String((byte[]) result[8])))) {
                    ch.setePhrases(null);
                } else if (result[8] instanceof String) {
                    ch.setePhrases((String) result[8]);
                } else {
                    ch.setePhrases(new String((byte[]) result[8]));
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
        String alias = query.getTableAlias(TRECChemicals.class);

        List<String> whereList = query.getWhereFields();
        List<String> modifiedWhereList = new ArrayList<>();

        for (String where : whereList) {
            if (where.contains(alias + ".phrasesRefNum")) {
                where = where.replaceAll(alias + ".phrasesRefNum", "CAST(AES_DECRYPT(" + alias + ".phrasesRefNum, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".hPhrases")) {
                where = where.replaceAll(alias + ".hPhrases", "CAST(AES_DECRYPT(" + alias + ".hPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".pPhrases")) {
                where = where.replaceAll(alias + ".pPhrases", "CAST(AES_DECRYPT(" + alias + ".pPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".qPhrases")) {
                where = where.replaceAll(alias + ".qPhrases", "CAST(AES_DECRYPT(" + alias + ".qPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".dPhrases")) {
                where = where.replaceAll(alias + ".dPhrases", "CAST(AES_DECRYPT(" + alias + ".dPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".sPhrases")) {
                where = where.replaceAll(alias + ".sPhrases", "CAST(AES_DECRYPT(" + alias + ".sPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".fPhrases")) {
                where = where.replaceAll(alias + ".fPhrases", "CAST(AES_DECRYPT(" + alias + ".fPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".aPhrases")) {
                where = where.replaceAll(alias + ".aPhrases", "CAST(AES_DECRYPT(" + alias + ".aPhrases, \'" + special + "\') AS CHAR)");
            } else if (where.contains(alias + ".ePhrases")) {
                where = where.replaceAll(alias + ".ePhrases", "CAST(AES_DECRYPT(" + alias + ".ePhrases, \'" + special + "\') AS CHAR)");
            }

            modifiedWhereList.add(where);
        }

        query.setWhereFields(modifiedWhereList);

        return query;
    }

    @Override
    public Object insert(Object iobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECChemicals record = (TRECChemicals) iobject;

        Map<String, List<TRECPhrases>> phrasesMap = getPhrasesMapforERG(record.getErg(), userData);

        try {
            List<TRECPhrases> HPhrases = phrasesMap.get("H");
            if (!HPhrases.isEmpty()) {
                for (TRECPhrases phrase : HPhrases) {
                    if (!isBlank(record.gethPhrases())) {
                        record.sethPhrases(record.gethPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.sethPhrases(phrase.getPhraseId());
                    }
                }
            }

            List<TRECPhrases> PPhrases = phrasesMap.get("P");
            if (!PPhrases.isEmpty()) {
                for (TRECPhrases phrase : PPhrases) {
                    if (!isBlank(record.getpPhrases())) {
                        record.setpPhrases(record.getpPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setpPhrases(phrase.getPhraseId());
                    }
                }
            }

            List<TRECPhrases> QPhrases = phrasesMap.get("Q");
            if (!QPhrases.isEmpty()) {
                for (TRECPhrases phrase : QPhrases) {
                    if (!isBlank(record.getqPhrases())) {
                        record.setqPhrases(record.getqPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setqPhrases(phrase.getPhraseId());
                    }
                }
            }
            List<TRECPhrases> DPhrases = phrasesMap.get("D");
            if (!DPhrases.isEmpty()) {
                for (TRECPhrases phrase : DPhrases) {
                    if (!isBlank(record.getdPhrases())) {
                        record.setdPhrases(record.getdPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setdPhrases(phrase.getPhraseId());
                    }
                }
            }
            List<TRECPhrases> SPhrases = phrasesMap.get("S");
            if (!SPhrases.isEmpty()) {
                for (TRECPhrases phrase : SPhrases) {
                    if (!isBlank(record.getsPhrases())) {
                        record.setsPhrases(record.getsPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setsPhrases(phrase.getPhraseId());
                    }
                }
            }

            List<TRECPhrases> FPhrases = phrasesMap.get("F");
            if (!FPhrases.isEmpty()) {
                for (TRECPhrases phrase : FPhrases) {
                    if (!isBlank(record.getfPhrases())) {
                        record.setfPhrases(record.getfPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setfPhrases(phrase.getPhraseId());
                    }
                }
            }

            List<TRECPhrases> APhrases = phrasesMap.get("A");
            if (!APhrases.isEmpty()) {
                for (TRECPhrases phrase : APhrases) {
                    if (!isBlank(record.getaPhrases())) {
                        record.setaPhrases(record.getaPhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setaPhrases(phrase.getPhraseId());
                    }
                }
            }

            List<TRECPhrases> EPhrases = phrasesMap.get("E");
            if (!EPhrases.isEmpty()) {
                for (TRECPhrases phrase : EPhrases) {
                    if (!isBlank(record.getePhrases())) {
                        record.setePhrases(record.getePhrases() + ";" + phrase.getPhraseId());
                    } else {
                        record.setePhrases(phrase.getPhraseId());
                    }
                }
            }

            if (!isBlank(record.gethPhrases())) {
                updatePhrasesWithChemicalErg(record, "hPhrases", userData);
            }
            if (!isBlank(record.getpPhrases())) {
                updatePhrasesWithChemicalErg(record, "pPhrases", userData);
            }
            if (!isBlank(record.getqPhrases())) {
                updatePhrasesWithChemicalErg(record, "qPhrases", userData);
            }
            if (!isBlank(record.getdPhrases())) {
                updatePhrasesWithChemicalErg(record, "dPhrases", userData);
            }
            if (!isBlank(record.getsPhrases())) {
                updatePhrasesWithChemicalErg(record, "sPhrases", userData);
            }
            if (!isBlank(record.getfPhrases())) {
                updatePhrasesWithChemicalErg(record, "fPhrases", userData);
            }
            if (!isBlank(record.getaPhrases())) {
                updatePhrasesWithChemicalErg(record, "aPhrases", userData);
            }
            if (!isBlank(record.getePhrases())) {
                updatePhrasesWithChemicalErg(record, "ePhrases", userData);
            }
        } catch (ReflectiveOperationException ex) {
            logMessage(Level.SEVERE, "Failed to update Phrases with Chemical ERG", userData);
            throw new EMCEntityBeanException("Failed to update Phrases with Chemical ERG");
        }
        if (isBlank(record.getChemicalId()) || record.getChemicalId() == 0) {
            record.setChemicalId(getNextChemicalID(userData));
        }

        return super.insert(record, userData);
    }

    private Map getPhrasesMapforERG(String ERG, EMCUserData userData) {
        List<TRECPhrases> HPhrasesList = new ArrayList<>();
        List<TRECPhrases> PPhrasesList = new ArrayList<>();
        List<TRECPhrases> QPhrasesList = new ArrayList<>();
        List<TRECPhrases> DPhrasesList = new ArrayList<>();
        List<TRECPhrases> SPhrasesList = new ArrayList<>();
        List<TRECPhrases> FPhrasesList = new ArrayList<>();
        List<TRECPhrases> APhrasesList = new ArrayList<>();
        List<TRECPhrases> EPhrasesList = new ArrayList<>();
        Map<String, List<TRECPhrases>> phrasesMap = new HashMap<>();

        if (!isBlank(ERG)) {


            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
            query.addAnd("ergNumber", ERG, EMCQueryConditions.LIKE);
//            List<TRECPhrases> phrases = util.executeGeneralSelectQuery(query, userData);
            userData.setUserData(0, query);

            List<TRECPhrases> ph = (List<TRECPhrases>) phrasesBean.getDataInRange(TRECPhrases.class, userData, 0, Integer.MAX_VALUE);

            for (TRECPhrases phrase : ph) {
                if (TRECTypeEnum.A.toString().equals(phrase.getTypeId())) {
                    APhrasesList.add(phrase);
                } else if (TRECTypeEnum.D.toString().equals(phrase.getTypeId())) {
                    DPhrasesList.add(phrase);
                } else if (TRECTypeEnum.E.toString().equals(phrase.getTypeId())) {
                    EPhrasesList.add(phrase);
                } else if (TRECTypeEnum.F.toString().equals(phrase.getTypeId())) {
                    FPhrasesList.add(phrase);
                } else if (TRECTypeEnum.H.toString().equals(phrase.getTypeId())) {
                    HPhrasesList.add(phrase);
                } else if (TRECTypeEnum.P.toString().equals(phrase.getTypeId())) {
                    PPhrasesList.add(phrase);
                } else if (TRECTypeEnum.Q.toString().equals(phrase.getTypeId())) {
                    QPhrasesList.add(phrase);
                } else if (TRECTypeEnum.S.toString().equals(phrase.getTypeId())) {
                    SPhrasesList.add(phrase);
                }
            }


            phrasesMap.put("H", HPhrasesList);

            phrasesMap.put("P", PPhrasesList);

            phrasesMap.put("Q", QPhrasesList);

            phrasesMap.put("D", DPhrasesList);

            phrasesMap.put("S", SPhrasesList);

            phrasesMap.put("F", FPhrasesList);

            phrasesMap.put("A", APhrasesList);

            phrasesMap.put("E", EPhrasesList);


            return phrasesMap;
        } else {
            logMessage(Level.SEVERE, "ERG not selected", userData);
            return null;
        }
    }

    @Override
    public Object update(Object uobject, EMCUserData userData) throws EMCEntityBeanException {
        TRECChemicals record = (TRECChemicals) uobject;
        TRECChemicals persisted = (TRECChemicals) util.findDetachedPersisted(record, userData);
        if (!isBlank(record.getErg()) && !isBlank(persisted.getErg())) {
            if (!record.getErg().equalsIgnoreCase(persisted.getErg())) {
                Map<String, List<TRECPhrases>> phrasesMap = getPhrasesMapforERG(record.getErg(), userData);
                // clear phrases first
                record.sethPhrases("");
                record.setpPhrases("");
                record.setqPhrases("");
                record.setdPhrases("");
                record.setsPhrases("");
                record.setfPhrases("");
                record.setaPhrases("");
                record.setePhrases("");

                // set phrases for newly selected phrases
                List<TRECPhrases> HPhrases = phrasesMap.get("H");
                if (!HPhrases.isEmpty()) {
                    for (TRECPhrases phrase : HPhrases) {
                        if (!isBlank(record.gethPhrases())) {
                            record.sethPhrases(record.gethPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.sethPhrases(phrase.getPhraseId());
                        }
                    }
                }

                List<TRECPhrases> PPhrases = phrasesMap.get("P");
                if (!PPhrases.isEmpty()) {
                    for (TRECPhrases phrase : PPhrases) {
                        if (!isBlank(record.getpPhrases())) {
                            record.setpPhrases(record.getpPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setpPhrases(phrase.getPhraseId());
                        }
                    }
                }

                List<TRECPhrases> QPhrases = phrasesMap.get("Q");
                if (!QPhrases.isEmpty()) {
                    for (TRECPhrases phrase : QPhrases) {
                        if (!isBlank(record.getqPhrases())) {
                            record.setqPhrases(record.getqPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setqPhrases(phrase.getPhraseId());
                        }
                    }
                }
                List<TRECPhrases> DPhrases = phrasesMap.get("D");
                if (!DPhrases.isEmpty()) {
                    for (TRECPhrases phrase : DPhrases) {
                        if (!isBlank(record.getdPhrases())) {
                            record.setdPhrases(record.getdPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setdPhrases(phrase.getPhraseId());
                        }
                    }
                }
                List<TRECPhrases> SPhrases = phrasesMap.get("S");
                if (!SPhrases.isEmpty()) {
                    for (TRECPhrases phrase : SPhrases) {
                        if (!isBlank(record.getsPhrases())) {
                            record.setsPhrases(record.getsPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setsPhrases(phrase.getPhraseId());
                        }
                    }
                }

                List<TRECPhrases> FPhrases = phrasesMap.get("F");
                if (!FPhrases.isEmpty()) {
                    for (TRECPhrases phrase : FPhrases) {
                        if (!isBlank(record.getfPhrases())) {
                            record.setfPhrases(record.getfPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setfPhrases(phrase.getPhraseId());
                        }
                    }
                }

                List<TRECPhrases> APhrases = phrasesMap.get("A");
                if (!APhrases.isEmpty()) {
                    for (TRECPhrases phrase : APhrases) {
                        if (!isBlank(record.getaPhrases())) {
                            record.setaPhrases(record.getaPhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setaPhrases(phrase.getPhraseId());
                        }
                    }
                }

                List<TRECPhrases> EPhrases = phrasesMap.get("E");
                if (!EPhrases.isEmpty()) {
                    for (TRECPhrases phrase : EPhrases) {
                        if (!isBlank(record.getePhrases())) {
                            record.setePhrases(record.getePhrases() + ";" + phrase.getPhraseId());
                        } else {
                            record.setePhrases(phrase.getPhraseId());
                        }
                    }
                }
            }
        }


        try {
            updatePhrasesWithChemicalErg(record, "hPhrases", userData);
            updatePhrasesWithChemicalErg(record, "pPhrases", userData);
            updatePhrasesWithChemicalErg(record, "qPhrases", userData);
            updatePhrasesWithChemicalErg(record, "dPhrases", userData);
            updatePhrasesWithChemicalErg(record, "sPhrases", userData);
            updatePhrasesWithChemicalErg(record, "fPhrases", userData);
            updatePhrasesWithChemicalErg(record, "aPhrases", userData);
            updatePhrasesWithChemicalErg(record, "ePhrases", userData);
        } catch (ReflectiveOperationException ex) {
            logMessage(Level.SEVERE, "Failed to update Phrases with Chemical ERG", userData);
            throw new EMCEntityBeanException("Failed to update Phrases with Chemical ERG");
        }
        if (isBlank(record.getChemicalId()) || record.getChemicalId() == 0) {
            record.setChemicalId(getNextChemicalID(userData));
        }

        return super.update(record, userData);
    }

    private void updatePhrasesWithChemicalErg(TRECChemicals record, String phraseField, EMCUserData userData) throws ReflectiveOperationException {
        Field f = TRECChemicals.class.getSuperclass().getDeclaredField(phraseField);
        f.setAccessible(true);

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addAnd("recordID", record.getRecordID());
        query.addFieldDecrypt(phraseField, special);

        List resultList = util.executeNativeQuery(query, userData);
        String persistedCurrentPhrase = null;

        if (resultList.size() > 0) {
            if (resultList.get(0) == null) {
                persistedCurrentPhrase = "";
            } else if (resultList.get(0) instanceof String) {
                persistedCurrentPhrase = (String) resultList.get(0);
            } else {
                persistedCurrentPhrase = new String((byte[]) resultList.get(0));
            }
        }

        List<String> persistedPhraseList = new ArrayList<>();

        if (persistedCurrentPhrase != null) {
            String[] persistedPhraseSplit = persistedCurrentPhrase.split(";");

            for (String p : persistedPhraseSplit) {
                if (!persistedPhraseList.contains(p.trim())) {
                    persistedPhraseList.add(p.trim());
                }
            }
        }

        List<String> phraseList = new ArrayList<>();

        String currentPhrase = (String) f.get(record);

        if (currentPhrase != null) {
            String[] phraseSplit = currentPhrase.split(";");

            for (String p : phraseSplit) {
                if (!phraseList.contains(p.trim())) {
                    phraseList.add(p.trim());
                }
            }
        }

        Collections.sort(phraseList);

        List<Object[]> phraseRecList;

        StringBuilder phraseString = new StringBuilder();

        String phraseErg;
        String[] phraseErgSplit;
        List<String> phraseErgList;
        StringBuilder phraseErgString;

        for (String phrase : phraseList) {
            if (!isBlank(phrase)) {

                phrase = phrase.trim();

                if (phraseString.length() > 0) {
                    phraseString.append("; ");
                }

                phraseString.append(phrase);

                query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
                query.addAnd("phraseId", phrase);
                query.addField("recordID");
                query.addFieldDecrypt("ergNumber", special);
                phraseRecList = util.executeNativeQuery(query, userData);

                for (Object[] phraseRec : phraseRecList) {
                    if (phraseRec[1] == null) {
                        phraseErg = "";
                    } else if (phraseRec[1] instanceof String) {
                        phraseErg = (String) phraseRec[1];
                    } else {
                        phraseErg = new String((byte[]) phraseRec[1]);
                    }

                    if (!phraseErg.contains(record.getErg())) {
                        phraseErgSplit = phraseErg.split(";");

                        phraseErgList = new ArrayList<>();

                        for (String e : phraseErgSplit) {
                            if (!phraseErgList.contains(e.trim())) {
                                phraseErgList.add(e.trim());
                            }
                        }

                        if (!phraseErgList.contains(record.getErg())) {
                            phraseErgList.add(record.getErg());
                        }

                        Collections.sort(phraseErgList);

                        phraseErgString = new StringBuilder();

                        for (String erg : phraseErgList) {
                            if (isBlank(erg)) {
                                continue;
                            }

                            if (phraseErgString.length() > 0) {
                                phraseErgString.append("; ");
                            }

                            phraseErgString.append(erg);
                        }

                        if (!isBlank(phraseErgString.toString())) {
                            query = new EMCQuery(enumQueryTypes.UPDATE, TRECPhrases.class);
                            query.addAnd("recordID", phraseRec[0]);
                            query.addSetEncryption("ergNumber", special, phraseErgString.toString());

                            util.executeNativeUpdate(query, userData);
                        }
                    }
                }
            }

            persistedPhraseList.remove(phrase);
        }

        if (!isBlank(phraseString)) {
            f.set(record, phraseString.toString());
        }

        for (String phrase : persistedPhraseList) {
            if (!isBlank(phrase)) {
                phrase = phrase.trim();

                query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
                query.addAnd("phraseId", phrase);
                query.addField("recordID");
                query.addFieldDecrypt("ergNumber", special);
                phraseRecList = util.executeNativeQuery(query, userData);

                for (Object[] phraseRec : phraseRecList) {
                    if (phraseRec[1] == null) {
                        phraseErg = "";
                    } else if (phraseRec[1] instanceof String) {
                        phraseErg = (String) phraseRec[1];
                    } else {
                        phraseErg = new String((byte[]) phraseRec[1]);
                    }

                    if (phraseErg.contains(record.getErg())) {
                        phraseErgSplit = phraseErg.split(";");

                        phraseErgList = new ArrayList<>();

                        for (String e : phraseErgSplit) {
                            if (!phraseErgList.contains(e.trim())) {
                                phraseErgList.add(e.trim());
                            }
                        }

                        phraseErgList.remove(record.getErg());

                        Collections.sort(phraseErgList);

                        phraseErgString = new StringBuilder();

                        for (String erg : phraseErgList) {
                            if (isBlank(erg)) {
                                continue;
                            }

                            if (phraseErgString.length() > 0) {
                                phraseErgString.append("; ");
                            }

                            phraseErgString.append(erg);
                        }

                        query = new EMCQuery(enumQueryTypes.UPDATE, TRECPhrases.class);
                        query.addAnd("recordID", phraseRec[0]);
                        query.addSetEncryption("ergNumber", special, isBlank(phraseErgString.toString()) ? "" : phraseErgString.toString());

                        util.executeNativeUpdate(query, userData);

                    }
                }
            }
        }
    }

    /**
     * Special method to help with encryption
     *
     * @param chemical
     * @param originalRef
     * @param userData
     */
    @Override
    public void updateEncryptedFields(TRECChemicals chemical, EMCUserData userData) {
        EMCQuery qu = new EMCQuery(enumQueryTypes.UPDATE, TRECChemicals.class);
        qu.addAnd("recordID", chemical.getRecordID());

        if (!isBlank(chemical.getPhrasesRefNum())) {
            qu.addSetEncryption("phrasesRefNum", special, chemical.getPhrasesRefNum());
        }
        if (!isBlank(chemical.gethPhrases())) {
            qu.addSetEncryption("hPhrases", special, chemical.gethPhrases());
        }
        if (!isBlank(chemical.getpPhrases())) {
            qu.addSetEncryption("pPhrases", special, chemical.getpPhrases());
        }
        if (!isBlank(chemical.getqPhrases())) {
            qu.addSetEncryption("qPhrases", special, chemical.getqPhrases());
        }
        if (!isBlank(chemical.getdPhrases())) {
            qu.addSetEncryption("dPhrases", special, chemical.getdPhrases());
        }
        if (!isBlank(chemical.getsPhrases())) {
            qu.addSetEncryption("sPhrases", special, chemical.getsPhrases());
        }
        if (!isBlank(chemical.getfPhrases())) {
            qu.addSetEncryption("fPhrases", special, chemical.getfPhrases());
        }
        if (!isBlank(chemical.getaPhrases())) {
            qu.addSetEncryption("aPhrases", special, chemical.getaPhrases());
        }
        if (!isBlank(chemical.getePhrases())) {
            qu.addSetEncryption("ePhrases", special, chemical.getePhrases());
        }

        qu.addSet("unNumber", chemical.getUnNumber());

        util.executeNativeUpdate(qu, userData);
    }

    @Override
    public boolean restructureChemicals(EMCUserData userData) throws EMCEntityBeanException {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addField("recordID");
        query.addField("erg");
        query.addFieldDecrypt("phrasesRefNum", special);

        List<Object[]> chemicalList = util.executeNativeQuery(query, userData);
        long chemRecId;
        String chemERG;
        String phrasesRef;

        Map<String, Object[]> combinationMap = new HashMap<>();
        List<Object[]> combinationList;
        Object[] combination;

        Map<String, String> phrasesMap = new HashMap<>();
        String pNumber;

        Map<String, List<String>> phrasesERGMap = new HashMap<>();
        List<String> ergList;

        String[] phrasesSplit;

        boolean aPhrases;
        boolean dPhrases;
        boolean ePhrases;
        boolean fPhrases;
        boolean hPhrases;
        boolean pPhrases;
        boolean qPhrases;
        boolean sPhrases;

        for (Object[] chemicalInfo : chemicalList) {
            chemRecId = Long.valueOf(chemicalInfo[0].toString());
            chemERG = (String) chemicalInfo[1];
            phrasesRef = new String((byte[]) chemicalInfo[2]);

            combination = combinationMap.get(phrasesRef);
            if (combination == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, TRECPhraseCombinations.class);
                query.addAnd("phraseRefNum", phrasesRef);
                query.addFieldDecrypt("a", special);
                query.addFieldDecrypt("d", special);
                query.addFieldDecrypt("e", special);
                query.addFieldDecrypt("f", special);
                query.addFieldDecrypt("h", special);
                query.addFieldDecrypt("p", special);
                query.addFieldDecrypt("q", special);
                query.addFieldDecrypt("s", special);

                combinationList = util.executeNativeQuery(query, userData);

                if (combinationList != null && !combinationList.isEmpty()) {
                    combination = combinationList.get(0);

                    combinationMap.put(phrasesRef, combination);
                }
            }

            if (combination != null) {
                query = new EMCQuery(enumQueryTypes.UPDATE, TRECChemicals.class);
                query.addAnd("recordID", chemRecId);

                aPhrases = false;
                dPhrases = false;
                ePhrases = false;
                fPhrases = false;
                hPhrases = false;
                pPhrases = false;
                qPhrases = false;
                sPhrases = false;

                //A Phrases
                if (combination[0] != null && !isBlank(new String((byte[]) combination[0]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[0]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("aPhrases", special, pNumber);
                        hPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    if (!ergList.contains(chemERG)) {
                                        ergList.add(chemERG);
                                    }
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!hPhrases) {
                    query.addSet("aPhrases", null);
                }

                //D Phrases
                if (combination[1] != null && !isBlank(new String((byte[]) combination[1]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[1]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("dPhrases", special, pNumber);
                        dPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!dPhrases) {
                    query.addSet("dPhrases", null);
                }

                //E Phrases
                if (combination[2] != null && !isBlank(new String((byte[]) combination[2]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[2]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("ePhrases", special, pNumber);
                        ePhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!ePhrases) {
                    query.addSet("ePhrases", null);
                }

                //F Phrases
                if (combination[3] != null && !isBlank(new String((byte[]) combination[3]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[3]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("fPhrases", special, pNumber);
                        fPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!fPhrases) {
                    query.addSet("fPhrases", null);
                }

                //H Phrases
                if (combination[4] != null && !isBlank(new String((byte[]) combination[4]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[4]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("hPhrases", special, pNumber);
                        hPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!hPhrases) {
                    query.addSet("hPhrases", null);
                }

                //P Phrases
                if (combination[5] != null && !isBlank(new String((byte[]) combination[5]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[5]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("pPhrases", special, pNumber);
                        pPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!pPhrases) {
                    query.addSet("pPhrases", null);
                }

                //Q Phrases
                if (combination[6] != null && !isBlank(new String((byte[]) combination[6]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[6]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("qPhrases", special, pNumber);
                        qPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!qPhrases) {
                    query.addSet("qPhrases", null);
                }

                //S Phrases
                if (combination[7] != null && !isBlank(new String((byte[]) combination[7]))) {
                    pNumber = getPhrasesFromCombination(new String((byte[]) combination[7]), phrasesMap, userData);
                    if (!isBlank(pNumber)) {
                        query.addSetEncryption("sPhrases", special, pNumber);
                        sPhrases = true;

                        if (!isBlank(chemERG)) {
                            if (pNumber.contains(";")) {
                                phrasesSplit = pNumber.split(";");
                            } else {
                                phrasesSplit = new String[1];
                                phrasesSplit[0] = pNumber;
                            }

                            for (String phrase : phrasesSplit) {
                                phrase = phrase.trim();

                                ergList = phrasesERGMap.get(phrase);
                                if (ergList == null) {
                                    ergList = new ArrayList<>();
                                }

                                if (!ergList.contains(chemERG)) {
                                    ergList.add(chemERG);
                                }

                                phrasesERGMap.put(phrase, ergList);
                            }
                        }
                    }
                }

                if (!sPhrases) {
                    query.addSet("sPhrases", null);
                }


                util.executeUpdate(query, userData);
            }
        }

        //Set Phrases ERG
        StringBuilder phrasesERG;
        List<String> phraseErgList = new ArrayList<>();
        for (Map.Entry<String, List<String>> ergEntry : phrasesERGMap.entrySet()) {
            phrasesERG = new StringBuilder();
            phraseErgList = ergEntry.getValue();

            Collections.sort(phraseErgList);

            for (String erg : ergEntry.getValue()) {
                if (phrasesERG.length() > 0) {
                    phrasesERG.append("; ");
                }

                phrasesERG.append(erg);
            }

            query = new EMCQuery(enumQueryTypes.UPDATE, TRECPhrases.class);
            query.addAnd("phraseId", ergEntry.getKey());
            query.addSetEncryption("ergNumber", special, phrasesERG.toString());

            util.executeUpdate(query, userData);
        }

        return true;
    }

    private String getPhrasesFromCombination(String phrasesString, Map<String, String> phrasesMap, EMCUserData userData) {
        String[] phrasesSplit;
        String pParent;
        String pType;
        int pSort;
        String pNumber;

        EMCQuery query;

        List<String> chemicalPhrasesList = new ArrayList<>();

        if (phrasesString.contains(";")) {
            phrasesSplit = phrasesString.toString().split(";");
        } else {
            phrasesSplit = new String[1];
            phrasesSplit[0] = phrasesString.toString();
        }

        for (String phrase : phrasesSplit) {
            phrase = phrase.trim();

            pParent = phrase.substring(0, 1);
            pType = phrase.substring(1, 2);

            try {
                pSort = Integer.valueOf(phrase.substring(2, phrase.length()));
            } catch (NumberFormatException ex) {
                Logger.getLogger("emc").log(Level.WARNING, "Failed to set decode phrase " + phrase, userData);
                continue;
            }


            pNumber = phrasesMap.get(pParent + pType + pSort);
            if (pNumber == null) {
                query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
                query.addAnd("parentClass", pParent);
                query.addAnd("typeId", pType);
                query.addAnd("sortNumber", pSort);
                query.addField("phraseId");

                pNumber = (String) util.executeSingleResultQuery(query, userData);

                phrasesMap.put(pParent + pType + pSort, pNumber);
            }

            if (pNumber != null) {
                if (!chemicalPhrasesList.contains(pNumber)) {
                    chemicalPhrasesList.add(pNumber);
                }
            }
        }

        Collections.sort(chemicalPhrasesList);

        StringBuilder chemicalPhrases = new StringBuilder();

        for (String phrase : chemicalPhrasesList) {
            if (chemicalPhrases.length() > 0) {
                chemicalPhrases.append("; ");
            }
            chemicalPhrases.append(phrase);
        }

        return chemicalPhrases.toString();
    }

    @Override
    public TRECChemicals fetchChemical(int chemicalId, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addAnd("chemicalId", chemicalId);

        List<TRECChemicals> chemList = util.executeNativeQuery(query, TRECChemicals.class, userData);
        util.detachEntities(userData);

        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        qu.addFieldDecrypt("phrasesRefNum", special);
        qu.addFieldDecrypt("hPhrases", special);
        qu.addFieldDecrypt("pPhrases", special);
        qu.addFieldDecrypt("qPhrases", special);
        qu.addFieldDecrypt("dPhrases", special);
        qu.addFieldDecrypt("sPhrases", special);
        qu.addFieldDecrypt("fPhrases", special);
        qu.addFieldDecrypt("aPhrases", special);
        qu.addFieldDecrypt("ePhrases", special);

        for (TRECChemicals ch : chemList) {
            qu.removeAnd("recordID");
            qu.addAnd("recordID", ch.getRecordID());
            List resultList = util.executeNativeQuery(qu, userData);
            if (resultList.size() > 0) {
                Object[] result = (Object[]) resultList.get(0);

                if (result[0] == null) {
                    ch.setPhrasesRefNum(null);
                } else if (result[0] instanceof String) {
                    ch.setPhrasesRefNum((String) result[0]);
                } else {
                    ch.setPhrasesRefNum(new String((byte[]) result[0]));
                }

                if (result[1] == null) {
                    ch.sethPhrases(null);
                } else if (result[1] instanceof String) {
                    ch.sethPhrases((String) result[1]);
                } else {
                    ch.sethPhrases(new String((byte[]) result[1]));
                }

                if (result[2] == null) {
                    ch.setpPhrases(null);
                } else if (result[2] instanceof String) {
                    ch.setpPhrases((String) result[2]);
                } else {
                    ch.setpPhrases(new String((byte[]) result[2]));
                }

                if (result[3] == null) {
                    ch.setqPhrases(null);
                } else if (result[3] instanceof String) {
                    ch.setqPhrases((String) result[3]);
                } else {
                    ch.setqPhrases(new String((byte[]) result[3]));
                }

                if (result[4] == null) {
                    ch.setdPhrases(null);
                } else if (result[4] instanceof String) {
                    ch.setdPhrases((String) result[4]);
                } else {
                    ch.setdPhrases(new String((byte[]) result[4]));
                }

                if (result[5] == null) {
                    ch.setsPhrases(null);
                } else if (result[5] instanceof String) {
                    ch.setsPhrases((String) result[5]);
                } else {
                    ch.setsPhrases(new String((byte[]) result[5]));
                }

                if (result[6] == null) {
                    ch.setfPhrases(null);
                } else if (result[6] instanceof String) {
                    ch.setfPhrases((String) result[6]);
                } else {
                    ch.setfPhrases(new String((byte[]) result[6]));
                }

                if (result[7] == null) {
                    ch.setaPhrases(null);
                } else if (result[7] instanceof String) {
                    ch.setaPhrases((String) result[7]);
                } else {
                    ch.setaPhrases(new String((byte[]) result[7]));
                }

                if (result[8] == null) {
                    ch.setePhrases(null);
                } else if (result[8] instanceof String) {
                    ch.setePhrases((String) result[8]);
                } else {
                    ch.setePhrases(new String((byte[]) result[8]));
                }
            }
        }

        return chemList.get(0);
    }

    @Override
    public boolean validatePhraseToBeAdded(String phraseId, String phraseType, EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.addAnd("phraseId", phraseId);
        query.addAnd("typeId", phraseType);

        return util.exists(query, userData);
    }

    @Override
    public void fixChemicalPhrases(EMCUserData userData) {
        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
        query.addField("phraseId");

        List<String> phrasesList = util.executeGeneralSelectQuery(query, userData);

        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        qu.addField("recordID");
        qu.addFieldDecrypt("hPhrases", special);
        qu.addFieldDecrypt("pPhrases", special);
        qu.addFieldDecrypt("qPhrases", special);
        qu.addFieldDecrypt("dPhrases", special);
        qu.addFieldDecrypt("sPhrases", special);
        qu.addFieldDecrypt("fPhrases", special);
        qu.addFieldDecrypt("aPhrases", special);
        qu.addFieldDecrypt("ePhrases", special);

        List resultList = util.executeNativeQuery(qu, userData);
        Object[] result;
        String phraseString;

        EMCQuery updateQu;
        boolean update;

        for (Object o : resultList) {
            result = (Object[]) o;

            update = false;

            updateQu = new EMCQuery(enumQueryTypes.UPDATE, TRECChemicals.class);
            updateQu.addAnd("recordID", ((BigInteger) result[0]).longValue());

            if (!(result[1] == null || (result[1] instanceof String && isBlank((String) result[1]) || isBlank(new String((byte[]) result[1]))))) {
                if (result[1] instanceof String) {
                    phraseString = ((String) result[1]);
                } else {
                    phraseString = (new String((byte[]) result[1]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("hPhrases", special, phraseString);

                update = true;
            }

            if (!(result[2] == null || (result[2] instanceof String && isBlank((String) result[2]) || isBlank(new String((byte[]) result[2]))))) {
                if (result[2] instanceof String) {
                    phraseString = ((String) result[2]);
                } else {
                    phraseString = (new String((byte[]) result[2]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("pPhrases", special, phraseString);

                update = true;
            }

            if (!(result[3] == null || (result[3] instanceof String && isBlank((String) result[3]) || isBlank(new String((byte[]) result[3]))))) {
                if (result[3] instanceof String) {
                    phraseString = ((String) result[3]);
                } else {
                    phraseString = (new String((byte[]) result[3]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("qPhrases", special, phraseString);

                update = true;
            }

            if (!(result[4] == null || (result[4] instanceof String && isBlank((String) result[4]) || isBlank(new String((byte[]) result[4]))))) {
                if (result[4] instanceof String) {
                    phraseString = ((String) result[4]);
                } else {
                    phraseString = (new String((byte[]) result[4]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("dPhrases", special, phraseString);

                update = true;
            }

            if (!(result[5] == null || (result[5] instanceof String && isBlank((String) result[5]) || isBlank(new String((byte[]) result[5]))))) {
                if (result[5] instanceof String) {
                    phraseString = ((String) result[5]);
                } else {
                    phraseString = (new String((byte[]) result[5]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("sPhrases", special, phraseString);

                update = true;
            }

            if (!(result[6] == null || (result[6] instanceof String && isBlank((String) result[6]) || isBlank(new String((byte[]) result[6]))))) {
                if (result[6] instanceof String) {
                    phraseString = ((String) result[6]);
                } else {
                    phraseString = (new String((byte[]) result[6]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("fPhrases", special, phraseString);

                update = true;
            }


            if (!(result[7] == null || (result[7] instanceof String && isBlank((String) result[7]) || isBlank(new String((byte[]) result[7]))))) {

                if (result[7] instanceof String) {
                    phraseString = ((String) result[7]);
                } else {
                    phraseString = (new String((byte[]) result[7]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("aPhrases", special, phraseString);

                update = true;
            }

            if (!(result[8] == null || (result[8] instanceof String && isBlank((String) result[8]) || isBlank(new String((byte[]) result[8]))))) {
                if (result[8] instanceof String) {
                    phraseString = ((String) result[8]);
                } else {
                    phraseString = (new String((byte[]) result[8]));
                }
                phraseString = checkChemicalPhrases(((BigInteger) result[0]).longValue(), phraseString, phrasesList);
                updateQu.addSetEncryption("ePhrases", special, phraseString);

                update = true;
            }

            if (update) {
                util.executeNativeUpdate(updateQu, userData);
            }
        }
    }

    private String checkChemicalPhrases(long chemRecId, String phraseString, List<String> validPhrases) {
        String[] phrasesSplit = phraseString.split(";");

        StringBuilder retPhraseString = new StringBuilder();

        for (String p : phrasesSplit) {
            if (validPhrases.contains(p.trim())) {
                if (retPhraseString.length() > 0) {
                    retPhraseString.append("; ");
                }

                retPhraseString.append(p);
            } else {
                System.out.println(chemRecId + " - " + phraseString);
            }
        }

        return retPhraseString.toString();
    }

    @Override
    public Object validateField(String fieldNameToValidate, Object theRecord, EMCUserData userData) {
        boolean ret = (Boolean) super.validateField(fieldNameToValidate, theRecord, userData);

        if (ret) {
            TRECChemicals record = (TRECChemicals) theRecord;
            if (fieldNameToValidate.equals("erg")) {
                boolean exists = false;
                if (!isBlank(record.getErg())) {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECErgMaster.class);
                    query.addAnd("erg", record.getErg());
                    exists = util.exists(query, userData);
                    if (!exists) {
                        logMessage(Level.SEVERE, "ERG Does not exist in ERG Table", userData);
                        return false;
                    } else {
                    }
                }

            }
            if (fieldNameToValidate.equals("subsidairyRisk")) {

                if (record.getSubsidairyRisk().contains(";")) {
                    String[] phraseSplit = record.getSubsidairyRisk().split(";");

                    for (String risk : phraseSplit) {
                        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECClasses.class);
                        query.addAnd("classId", risk);
                        boolean exists = util.exists(query, userData);
                        if (!exists) {
                            logMessage(Level.SEVERE, "Risk " + risk + " does not exist in Subsidiary Risk table ", userData);
                            return false;
                        }

                    }

                } else {
                    EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECClasses.class);
                    query.addAnd("classId", record.getSubsidairyRisk());
                    boolean exists = util.exists(query, userData);
                    if (!exists) {
                        logMessage(Level.SEVERE, "Risk " + record.getSubsidairyRisk() + " does not exist in Subsidiary Risk table ", userData);
                        return false;
                    }

                }
            }
        }
        return ret;
    }

    private int getNextChemicalID(EMCUserData userData) {
        int chemId = 0;

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        query.addFieldAggregateFunction("chemicalId", "MAX");
        int res = (int) util.executeSingleResultQuery(query, userData);

        if (isBlank(res) || res == 0) {
            chemId = chemId + 1;
        } else {
            chemId = res + 10;
        }

        return chemId;

    }

    @Override
    public void checkTRECChemicals(EMCUserData userData) {
        EMCUserData copyUD = userData.copyUserData();

        EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECChemicals.class);
        copyUD.setUserData(0, query);

        List<TRECChemicals> chemicalList = (List<TRECChemicals>) getDataInRange(TRECChemicals.class, copyUD, 0, 1000000);

        for (TRECChemicals chemical : chemicalList) {
            if (isBlank(chemical.getErg())) {
                Logger.getLogger("emc").log(Level.WARNING, "Chemical " + chemical.getUnNumber() + " does not have an ERG set.", userData);
            } else {
                String phrases = chemical.gethPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getpPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getqPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getdPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getsPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getfPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getaPhrases();
                checkTRECPhrases(chemical, phrases, userData);

                phrases = chemical.getePhrases();
                checkTRECPhrases(chemical, phrases, userData);
            }
        }
    }

    private void checkTRECPhrases(TRECChemicals chemical, String phraseString, EMCUserData userData) {
        if (!isBlank(phraseString)) {
            EMCUserData copyUD = userData.copyUserData();

            String[] phraseSplit = phraseString.split(";");
            List<String> phraseStringList = new ArrayList<>();

            for (String s : phraseSplit) {
                phraseStringList.add(s.trim());
            }

            EMCQuery query = new EMCQuery(enumQueryTypes.SELECT, TRECPhrases.class);
            query.addAndInList("phraseId", phraseStringList, true, false);

            copyUD.setUserData(0, query);

            List<TRECPhrases> phrasesList = (List<TRECPhrases>) phrasesBean.getDataInRange(TRECPhrases.class, copyUD, 0, 1000000);

            for (TRECPhrases phrase : phrasesList) {
                if (isBlank(phrase.getErgNumber()) || !phrase.getErgNumber().contains(chemical.getErg().trim())) {
                    Logger.getLogger("emc").log(Level.WARNING, "Phrase " + phrase.getPhraseId() + " does not have ERG " + chemical.getErg() + " set on it. Chemical " + chemical.getUnNumber() + " uses the phrase.", userData);
                }
            }
        }
    }
}
