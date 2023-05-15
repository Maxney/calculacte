package com.example.calculacte.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class CalculatorService {
    private final HolidaysService holidaysService;

    public CalculatorService(HolidaysService holidaysService) {
        this.holidaysService = holidaysService;
    }

    /**
     * Считает зарплаты за 1 рабочий день
     *
     * @param salary зарплата за 12 месяцев
     * @return зп за рабочий день
     */
    private BigDecimal calculateSalaryDay(BigDecimal salary) {
        return salary
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP)
                .divide(BigDecimal.valueOf(29.3), 2, RoundingMode.HALF_UP);
        // годовую зарплату делим на 12, и делим на среднее кол-во дней в месяце 29,3
    }


    /**
     * Расчёт отпускных, когда известна дата начала и конца отпуска
     *
     * @param salary зарплата за 12 месяцев
     * @param startDate     дата начала отпуска
     * @param endDate       дата конца отпуска
     * @return сумма отпускных
     */
    public BigDecimal calculateVacationPayWithDates(BigDecimal salary, LocalDate startDate, LocalDate endDate) {
        int vacationDaysWithHolidays = holidaysService.getVacationDaysDates(startDate, endDate);
        return  calculateSalaryDay(salary).multiply(BigDecimal.valueOf(vacationDaysWithHolidays));
    }

    /**
     * Расчёт отпускных, когда известна дата начала и кол-во дней отпуска
     *
     * @param salary зарплата за 12 месяцев
     * @param vacationDays  кол-во отпускных дней
     * @param startDate     дата начала отпуска
     * @return сумма отпускных
     */
    public BigDecimal calculateVacationPayWithStartDay(BigDecimal salary, Integer vacationDays, LocalDate startDate) {
        int vacationDaysWithHolidays = holidaysService.getVacationDaysWithHolidaysAndStartDay(startDate, vacationDays);
        // зарплату за 1 день умножаем на количество отпускных дней
        return calculateSalaryDay(salary).multiply(BigDecimal.valueOf(vacationDaysWithHolidays));
    }

    /**
     * Расчёт отпускных, когда известно только кол-во отпускных дней
     *
     * @param salary  годовая зарплата
     * @param vacationDays  кол-во отпускных дней
     * @return сумма отпускных
     */
    public BigDecimal calculateVacationPay(BigDecimal salary, Integer vacationDays) {
        return calculateSalaryDay(salary).multiply(BigDecimal.valueOf(vacationDays));
    }
}
