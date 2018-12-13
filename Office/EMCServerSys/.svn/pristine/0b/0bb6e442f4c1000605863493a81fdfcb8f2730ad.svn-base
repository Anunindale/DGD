/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.trec.phrasecombinations;

import emc.entity.trec.TRECPhraseCombinations;
import emc.enums.enumQueryTypes;
import emc.framework.EMCEntityBean;
import emc.framework.EMCQuery;
import emc.framework.EMCUserData;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class TRECPhraseCombinationsBean extends EMCEntityBean implements TRECPhraseCombinationsLocal {
    private final String special = "tr3ck3y";
    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        Collection<TRECPhraseCombinations> res = super.getDataInRange(theTable, userData, start, end);
        EMCQuery qu = new EMCQuery(enumQueryTypes.SELECT, TRECPhraseCombinations.class);
        qu.addFieldDecrypt("a", special);
        qu.addFieldDecrypt("d", special);
        qu.addFieldDecrypt("e", special);
        qu.addFieldDecrypt("f", special);
        qu.addFieldDecrypt("h", special);
        qu.addFieldDecrypt("p", special);
        qu.addFieldDecrypt("q", special);
        qu.addFieldDecrypt("s", special);
        for(TRECPhraseCombinations ph:res){
            qu.removeAnd("recordID");
            qu.addAnd("recordID", ph.getRecordID());
            List result = util.executeNativeQuery(qu, userData);
            if(result.size()>0){
                Object[] values = (Object[]) result.get(0);
                if(values[0] instanceof String){
                    ph.setA((String)values[0]);
                    ph.setD((String)values[1]);
                    ph.setE((String)values[2]);
                    ph.setF((String)values[3]);
                    ph.setH((String)values[4]);
                    ph.setP((String)values[5]);
                    ph.setQ((String)values[6]);
                    ph.setS((String)values[7]);
                }else{
                    ph.setA(new String((byte[])values[0]));
                    ph.setD(new String((byte[])values[1]));
                    ph.setE(new String((byte[])values[2]));
                    ph.setF(new String((byte[])values[3]));
                    ph.setH(new String((byte[])values[4]));
                    ph.setP(new String((byte[])values[5]));
                    ph.setQ(new String((byte[])values[6]));
                    ph.setS(new String((byte[])values[7]));
                }
            }
        }
        util.detachEntities(userData);
        return res;
    }

    /**
     * Special method to help with encryption
     * @param phrase
     * @param userData
     */
    public void updateEncryptedFields(TRECPhraseCombinations ph,EMCUserData userData){
        EMCQuery qu = new EMCQuery(enumQueryTypes.UPDATE, TRECPhraseCombinations.class);
        qu.addAnd("recordID", ph.getRecordID());
        qu.addSetEncryption("a",special, ph.getA());
        qu.addSetEncryption("d",special, ph.getD());
        qu.addSetEncryption("e",special, ph.getE());
        qu.addSetEncryption("f",special, ph.getF());
        qu.addSetEncryption("h",special, ph.getH());
        qu.addSetEncryption("p",special, ph.getP());
        qu.addSetEncryption("q",special, ph.getQ());
        qu.addSetEncryption("s",special, ph.getS());
        util.executeNativeUpdate(qu, userData);
    }
}
