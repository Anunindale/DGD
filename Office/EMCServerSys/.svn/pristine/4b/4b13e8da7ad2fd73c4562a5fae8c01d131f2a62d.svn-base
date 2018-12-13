/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package emc.reports.trec.cargocheck;

import emc.entity.trec.TRECCargoCheckLines;
import emc.entity.trec.TRECCargoCheckMaster;
import emc.framework.EMCQuery;
import emc.framework.EMCReportBean;
import emc.framework.EMCUserData;
import emc.functions.Functions;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;

/**
 *
 * @author ivan
 */
@Stateless
public class CargoCheckReportBean extends EMCReportBean implements CargoCheckReportLocal {

    public CargoCheckReportBean(){}

    @Override
    protected EMCQuery manipulateQuery(EMCQuery query, EMCUserData userData) {
            query.addAnd("companyId", userData.getCompanyId());

            query.addField("cargoCheckNumber", TRECCargoCheckMaster.class.getName());//0
            query.addField("description", TRECCargoCheckMaster.class.getName());//1
            query.addField("placeCard", TRECCargoCheckMaster.class.getName());//2
            query.addField("trecRequired", TRECCargoCheckMaster.class.getName());//3
            query.addField("cargo", TRECCargoCheckMaster.class.getName());//4
            query.addField("notes", TRECCargoCheckMaster.class.getName());//5
            query.addField("unNumber", TRECCargoCheckLines.class.getName());//6
            query.addField("properShipping", TRECCargoCheckLines.class.getName());//7
            query.addField("packingGroup", TRECCargoCheckLines.class.getName());//8
            query.addField("quantity", TRECCargoCheckLines.class.getName());//9
            query.addField("allowed", TRECCargoCheckLines.class.getName());//10
            query.addField("createdDate", TRECCargoCheckMaster.class.getName());//11
            query.addOrderBy("cargoCheckNumber", TRECCargoCheckMaster.class.getName());
        return query;
    }

   /* @Override
    public List<Object> getReportResult(List<Object> parameters, EMCReportConfig reportConfig, EMCUserData userData) {
        return super.getReportResult(parameters, reportConfig, userData);
    }*/



    @Override
    public List<Object> manipulateQueryResult(List<Object> queryResult, Map<String, Object> paramMap, EMCUserData userData) {
        List resultList = new ArrayList();
        
        
        CargoCheckReportDS ds;

        for(Object o : queryResult){
           ds = new CargoCheckReportDS();

           ds.setPrintReport(Boolean.TRUE);
            

            Object[] master = (Object[])o;

            ds.setCargoCheckNumber(isBlank((String)master[0])?"":(String)master[0]);
            ds.setDescription(isBlank((String)master[1])?"":(String)master[1]);
            ds.setDate(Functions.date2String((Date)master[11],"dd/MM/yyyy"));

                    if((Boolean)master[4] != null && (Boolean)master[4]){
                        ds.setCargo("OK");
                    }else{
                        ds.setCargo("NOT OK");
                    }

            ds.setPlaceCard(isBlank((String)master[2])?"":(String)master[2]);

            if((Boolean)master[3] != null && (Boolean)master[3]){
                ds.setTrecRequired("Yes");
            }else{
                ds.setTrecRequired("No");
            }
            if(master[5] != null){
                ds.setNotes(isBlank((String)master[5])?"":(String)master[5]);
            }
            ds.setUnNumber(isBlank((String)master[6])?"":(String)master[6]);
            ds.setProperShipping(isBlank((String)master[7])?"":(String)master[7]);
            ds.setPackingGroup(isBlank((String)master[8])?"":(String)master[8]);
            if(master[9] != null){
            ds.setQuantity(String.valueOf((BigDecimal)master[9]));
            }
            if((Boolean)master[10] != null && (Boolean)master[10]){
                    ds.setAllowed("Yes");
            }else{
                    ds.setAllowed("No");
            }
            resultList.add(ds);
        }

        return resultList;
    }



}
