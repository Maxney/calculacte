package com.example.calculacte.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.calculacte.service.CalculatorService;

import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.LocalDate;

@RestController
public class CalculatorController {
    private final Logger logger = LogManager.getLogger(CalculatorController.class);
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    // example http://localhost:8080/calculate?salary=100000&vacationDays=15
    @GetMapping("/calculate")
    public BigDecimal calculatePay(@RequestParam(name = "salary") BigDecimal salary, // Годовая зарплата
                                   @RequestParam(name = "vacationDays") Integer vacationDays,//дней в отпуске
                                   @RequestParam(name = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, // yyyy-mm-dd
                                   @RequestParam(name = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                logger.error("Start date should go before the end date");
                throw new DateTimeException("StartDate > EndDate");
            }
            return calculatorService.calculateVacationPayWithDates(salary, startDate, endDate);
        } else if (startDate != null) {
            return calculatorService.calculateVacationPayWithStartDay(salary, vacationDays, startDate);
        } else {
            return calculatorService.calculateVacationPay(salary, vacationDays);
        }
    }
}
