/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package emc.reporttools.charts.pmm.subjectcomponent;

import emc.reporttools.charts.EMCReportChartEnumIF;

/**
 *
 * @author riaan
 */
public enum SubjectComponentReportCharts implements EMCReportChartEnumIF {

    OVERVIEW(0, "Overview"),
    MEASURE_1(1, "Measurement_1"),
    MEASURE_2(2, "Measurement_2"),
    MEASURE_3(3, "Measurement_3"),
    MEASURE_4(4, "Measurement_4"),
    MEASURE_5(5, "Measurement_5"),
    MEASURE_6(6, "Measurement_6");
    int id;
    String name;

    /** Creates a new instance of SubjectComponentReportCharts. */
    SubjectComponentReportCharts(int id, String name) {
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
        for (SubjectComponentReportCharts chart : this.values()) {
            if (chart.id == id) {
                return chart;
            }
        }

        return null;
    }
}
