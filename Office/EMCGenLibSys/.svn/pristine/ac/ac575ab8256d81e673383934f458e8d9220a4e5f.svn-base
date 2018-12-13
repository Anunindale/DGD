/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reporttools.charts.pmm.clientanth;

import emc.reporttools.charts.EMCReportChartEnumIF;

/**
 *
 * @author claudette
 */
public enum ClientAnthReportCharts implements EMCReportChartEnumIF {

    FAT_PERC(0, "FatPerc"),
    BMI(1, "BMI"),
    HR(2, "HR");
    int id;
    String name;

    /** Creates a new instance of ClientAnthReportCharts. */
    ClientAnthReportCharts(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public EMCReportChartEnumIF fromId(int id) {
        for (ClientAnthReportCharts chart : this.values()) {
            if (chart.id == id) {
                return chart;
            }
        }

        return null;
    }
}
