package com.example.calculacte;

import com.example.calculacte.controller.CalculatorController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CalculacteApplicationTests {

    @Autowired
    private CalculatorController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void vacationPayWithoutStartAndEndDates() {
        // given
        BigDecimal averageSalary = BigDecimal.valueOf(600000);
        int vacationDays = 30;
        BigDecimal result = BigDecimal.valueOf(5119440, 2);

        // when
        var resultController = controller.calculatePay(averageSalary, vacationDays, null, null);

        // then
        assertThat(resultController).isEqualTo(result);
    }

    @Test
    void vacationPayWithStartAndEndDates() {
        // given
        BigDecimal averageSalary = BigDecimal.valueOf(600000);
        BigDecimal result = BigDecimal.valueOf(2901016, 2);

        // when
        BigDecimal resultController = controller.calculatePay(averageSalary, null,
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 31));
        // then
        assertThat(resultController).isEqualTo(result);
    }

    @Test
    void vacationPayWithStartDate() {
        // given
        BigDecimal averageSalary = BigDecimal.valueOf(600000);
        BigDecimal result = BigDecimal.valueOf(3412960, 2);

        // when
        BigDecimal resultController = controller.calculatePay(averageSalary, 31,
                LocalDate.of(2023, 2, 23), null);

        // then
        assertThat(resultController).isEqualTo(result);
    }

    @Test
    void vacationPayPerOnlyHolidays() {
        // given
        BigDecimal averageSalary = BigDecimal.valueOf(600000);
        BigDecimal result = BigDecimal.valueOf(0, 2);

        // when
        BigDecimal resultController = controller.calculatePay(averageSalary, null,
                LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 6));

        // then
        assertThat(resultController).isEqualTo(result);
    }

}
