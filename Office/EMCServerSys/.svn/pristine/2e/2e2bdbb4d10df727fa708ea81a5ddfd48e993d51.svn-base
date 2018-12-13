/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.bus.sop.salesbysizeenquiry.datasource;

import emc.bus.sop.salesbysizeenquiry.SOPSalesBySizeEnquiryLocal;
import emc.entity.sop.datasource.SOPSalesBySizeEnquiryDS;
import emc.framework.EMCDataSourceBean;
import emc.framework.EMCEntityClass;
import emc.framework.EMCUserData;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author wikus
 */
@Stateless
public class SOPSalesBySizeEnquiryDSBean extends EMCDataSourceBean implements SOPSalesBySizeEnquiryDSLocal {

    @EJB
    private SOPSalesBySizeEnquiryLocal masterBean;

    public SOPSalesBySizeEnquiryDSBean() {
        this.setDataSourceClassName(SOPSalesBySizeEnquiryDS.class.getName());
    }

    @Override
    public EMCEntityClass populateDataSourceFields(EMCEntityClass dataSourceInstance, EMCUserData userData) {
        SOPSalesBySizeEnquiryDS ds = (SOPSalesBySizeEnquiryDS) dataSourceInstance;

        if (ds.getQuantity1() != null && ds.getQuantity1().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage1(ds.getQuantity1().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage1(BigDecimal.ZERO);
        }
        if (ds.getQuantity2() != null && ds.getQuantity2().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage2(ds.getQuantity2().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage2(BigDecimal.ZERO);
        }
        if (ds.getQuantity3() != null && ds.getQuantity3().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage3(ds.getQuantity3().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage3(BigDecimal.ZERO);
        }
        if (ds.getQuantity4() != null && ds.getQuantity4().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage4(ds.getQuantity4().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage4(BigDecimal.ZERO);
        }
        if (ds.getQuantity5() != null && ds.getQuantity5().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage5(ds.getQuantity5().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage5(BigDecimal.ZERO);
        }
        if (ds.getQuantity6() != null && ds.getQuantity6().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage6(ds.getQuantity6().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage6(BigDecimal.ZERO);
        }
        if (ds.getQuantity7() != null && ds.getQuantity7().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage7(ds.getQuantity7().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage7(BigDecimal.ZERO);
        }
        if (ds.getQuantity8() != null && ds.getQuantity8().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage8(ds.getQuantity8().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage8(BigDecimal.ZERO);
        }
        if (ds.getQuantity9() != null && ds.getQuantity9().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage9(ds.getQuantity9().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage9(BigDecimal.ZERO);
        }
        if (ds.getQuantity10() != null && ds.getQuantity10().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage10(ds.getQuantity10().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage10(BigDecimal.ZERO);
        }
        if (ds.getQuantity11() != null && ds.getQuantity11().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage11(ds.getQuantity11().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage11(BigDecimal.ZERO);
        }
        if (ds.getQuantity12() != null && ds.getQuantity12().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage12(ds.getQuantity12().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage12(BigDecimal.ZERO);
        }
        if (ds.getQuantity13() != null && ds.getQuantity13().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage13(ds.getQuantity13().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage13(BigDecimal.ZERO);
        }
        if (ds.getQuantity14() != null && ds.getQuantity14().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage14(ds.getQuantity14().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage14(BigDecimal.ZERO);
        }
        if (ds.getQuantity15() != null && ds.getQuantity15().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage15(ds.getQuantity15().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage15(BigDecimal.ZERO);
        }
        if (ds.getQuantity16() != null && ds.getQuantity16().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage16(ds.getQuantity16().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage16(BigDecimal.ZERO);
        }
        if (ds.getQuantity17() != null && ds.getQuantity17().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage17(ds.getQuantity17().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage17(BigDecimal.ZERO);
        }
        if (ds.getQuantity18() != null && ds.getQuantity18().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage18(ds.getQuantity18().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage18(BigDecimal.ZERO);
        }
        if (ds.getQuantity19() != null && ds.getQuantity19().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage19(ds.getQuantity19().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage19(BigDecimal.ZERO);
        }
        if (ds.getQuantity20() != null && ds.getQuantity20().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage20(ds.getQuantity20().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage20(BigDecimal.ZERO);
        }
        if (ds.getQuantity21() != null && ds.getQuantity21().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage21(ds.getQuantity21().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage21(BigDecimal.ZERO);
        }
        if (ds.getQuantity22() != null && ds.getQuantity22().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage22(ds.getQuantity22().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage22(BigDecimal.ZERO);
        }
        if (ds.getQuantity23() != null && ds.getQuantity23().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage23(ds.getQuantity23().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage23(BigDecimal.ZERO);
        }
        if (ds.getQuantity24() != null && ds.getQuantity24().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage24(ds.getQuantity24().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage24(BigDecimal.ZERO);
        }
        if (ds.getQuantity25() != null && ds.getQuantity25().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage25(ds.getQuantity25().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage25(BigDecimal.ZERO);
        }
        if (ds.getQuantity26() != null && ds.getQuantity26().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage26(ds.getQuantity26().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage26(BigDecimal.ZERO);
        }
        if (ds.getQuantity27() != null && ds.getQuantity27().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage27(ds.getQuantity27().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage27(BigDecimal.ZERO);
        }
        if (ds.getQuantity28() != null && ds.getQuantity28().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage28(ds.getQuantity28().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage28(BigDecimal.ZERO);
        }
        if (ds.getQuantity29() != null && ds.getQuantity29().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage29(ds.getQuantity29().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage29(BigDecimal.ZERO);
        }
        if (ds.getQuantity30() != null && ds.getQuantity30().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage30(ds.getQuantity30().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage30(BigDecimal.ZERO);
        }
        if (ds.getQuantity31() != null && ds.getQuantity31().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage31(ds.getQuantity31().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage31(BigDecimal.ZERO);
        }
        if (ds.getQuantity32() != null && ds.getQuantity32().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage32(ds.getQuantity32().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage32(BigDecimal.ZERO);
        }
        if (ds.getQuantity33() != null && ds.getQuantity33().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage33(ds.getQuantity33().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage33(BigDecimal.ZERO);
        }
        if (ds.getQuantity34() != null && ds.getQuantity34().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage34(ds.getQuantity34().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage34(BigDecimal.ZERO);
        }
        if (ds.getQuantity35() != null && ds.getQuantity35().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage35(ds.getQuantity35().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage35(BigDecimal.ZERO);
        }
        if (ds.getQuantity36() != null && ds.getQuantity36().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage36(ds.getQuantity36().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage36(BigDecimal.ZERO);
        }
        if (ds.getQuantity37() != null && ds.getQuantity37().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage37(ds.getQuantity37().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage37(BigDecimal.ZERO);
        }
        if (ds.getQuantity38() != null && ds.getQuantity38().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage38(ds.getQuantity38().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage38(BigDecimal.ZERO);
        }
        if (ds.getQuantity39() != null && ds.getQuantity39().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage39(ds.getQuantity39().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage39(BigDecimal.ZERO);
        }
        if (ds.getQuantity40() != null && ds.getQuantity40().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage40(ds.getQuantity40().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage40(BigDecimal.ZERO);
        }
        if (ds.getQuantity41() != null && ds.getQuantity41().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage41(ds.getQuantity41().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage41(BigDecimal.ZERO);
        }
        if (ds.getQuantity42() != null && ds.getQuantity42().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage42(ds.getQuantity42().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage42(BigDecimal.ZERO);
        }
        if (ds.getQuantity43() != null && ds.getQuantity43().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage43(ds.getQuantity43().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage43(BigDecimal.ZERO);
        }
        if (ds.getQuantity44() != null && ds.getQuantity44().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage44(ds.getQuantity44().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage44(BigDecimal.ZERO);
        }
        if (ds.getQuantity45() != null && ds.getQuantity45().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage45(ds.getQuantity45().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage45(BigDecimal.ZERO);
        }
        if (ds.getQuantity46() != null && ds.getQuantity46().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage46(ds.getQuantity46().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage46(BigDecimal.ZERO);
        }
        if (ds.getQuantity47() != null && ds.getQuantity47().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage47(ds.getQuantity47().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage47(BigDecimal.ZERO);
        }
        if (ds.getQuantity48() != null && ds.getQuantity48().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage48(ds.getQuantity48().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage48(BigDecimal.ZERO);
        }
        if (ds.getQuantity49() != null && ds.getQuantity49().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage49(ds.getQuantity49().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage49(BigDecimal.ZERO);
        }
        if (ds.getQuantity50() != null && ds.getQuantity50().compareTo(BigDecimal.ZERO) > 0) {
            ds.setPercentage50(ds.getQuantity50().divide(ds.getTotalQuantity(), 4, RoundingMode.HALF_UP).multiply(util.getBigDecimal(100d)));
        } else {
            ds.setPercentage50(BigDecimal.ZERO);
        }

        return ds;
    }

    @Override
    public Collection getDataInRange(Class theTable, EMCUserData userData, int start, int end) {
        Collection superData = masterBean.getDataInRange(theTable, userData, start, end);

        List thisData = convertSuperToDataSource(theTable, (List) superData, userData);

        for (Object instance : thisData) {
            populateDataSourceFields((EMCEntityClass) instance, userData);
        }

        return thisData;
    }

    @Override
    public String getNumRows(Class theTable, EMCUserData userData) {
        return masterBean.getNumRows(theTable, userData);
    }
}
