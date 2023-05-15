package com.example.calculacte.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;

import static com.example.calculacte.data.Holidays.getHolidayDaysList;


@Service
public class HolidaysService {

    /**
     * Считает кол-во рабочих дней в промежутке между датами начала и конца
     *
     * @param startDate дата ухода в отпуск
     * @param endDate   дата возращения из отпуска
     * @return количество дней
     */
    public int getVacationDaysDates(LocalDate startDate, LocalDate endDate) {
        int vacationDaysWithHolidays = 0;
        Map<Month, List<Integer>> holidayDays = getHolidayDaysList();

        endDate = endDate.plusDays(1);// включительно
        while (!startDate.equals(endDate) && startDate.isBefore(endDate)) {
            // подсчёт кол-ва отпускных дней с учётом праздников и выходных
            int dayOfWeek = startDate.getDayOfWeek().getValue();

            if(dayOfWeek >=1 && dayOfWeek <= 5 && !holidayDays.get(startDate.getMonth())
                    .contains(startDate.getDayOfMonth())) {
                vacationDaysWithHolidays++;
            }
            startDate = startDate.plusDays(1);
        }
        return vacationDaysWithHolidays;
    }

    /**
     * Считает кол-во рабочих дней с начальной даты по кол-ву выходных дней
     *
     * @param startDate    дата ухода в отпуск
     * @param vacationDays кол-во выходных дней
     * @return количество дней
     */
    public int getVacationDaysWithHolidaysAndStartDay(LocalDate startDate, Integer vacationDays) {
        int vacationDaysWithHolidays = 0;//количестов рабочих дней который должна оплатить компания с учетом праздников
        Map<Month, List<Integer>> holidayDays = getHolidayDaysList();

        if(vacationDays < 1) {
            throw new NumberFormatException("The number of vacation days cannot be less than 1");
        }
        while (vacationDays > 0) {
            // подсчёт кол-ва отпускных дней с учётом праздников и выходных
            int dayOfWeek = startDate.getDayOfWeek().getValue();
            if(dayOfWeek >=1 && dayOfWeek <= 5 && !holidayDays.get(startDate.getMonth())
                    .contains(startDate.getDayOfMonth())) {
                vacationDaysWithHolidays++;
            }
            startDate = startDate.plusDays(1);
        }
        return vacationDaysWithHolidays;
    }
}
