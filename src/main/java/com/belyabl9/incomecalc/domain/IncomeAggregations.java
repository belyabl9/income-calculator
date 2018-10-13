package com.belyabl9.incomecalc.domain;

import com.google.common.collect.ImmutableMap;
import lombok.Data;

import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

@Data
public class IncomeAggregations {
    private final int year;
    private final double total;
    private final Map<Month, Double> monthTotalMap;
    private final Map<YearQuarter, Double> yearQuarterTotalMap;

    public IncomeAggregations(int year,
                              double total,
                              Map<Month, Double> monthTotalMap,
                              Map<YearQuarter, Double> yearQuarterTotalMap) {
        if (year > LocalDate.now().getYear()) {
            throw new IllegalArgumentException("The year must not be in the future.");
        }
        if (total < 0) {
            throw new IllegalArgumentException("Total must be >= 0.");
        }
        verifyYearTotalAndAllQuarterTotalEqual(yearQuarterTotalMap, total);
        
        this.year = year;
        this.total = total;
        this.monthTotalMap = ImmutableMap.copyOf(monthTotalMap);
        this.yearQuarterTotalMap = ImmutableMap.copyOf(yearQuarterTotalMap);
    }

    private static void verifyYearTotalAndAllQuarterTotalEqual(Map<YearQuarter, Double> yearQuarterTotalMap, double total) {
        double allQuarterTotal = 0d;
        for (Map.Entry<YearQuarter, Double> entry: yearQuarterTotalMap.entrySet()) {
            allQuarterTotal += entry.getValue();
        }
        if (Math.abs(allQuarterTotal - total) >= 0.001) {
            throw new IllegalArgumentException("All quarter total sum must be equal to the year's total.");
        }
    }
}
