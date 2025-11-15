package com.abhee.company.controller;

import java.io.ByteArrayOutputStream;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.abhee.company.entity.ProfileEntity;
import com.abhee.company.service.EmailService;
import com.abhee.company.service.ExcelService;
import com.abhee.company.service.ExpenseService;
import com.abhee.company.service.IncomeService;
import com.abhee.company.service.ProfileService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
	
	
	private final ExcelService excelService;
	private final IncomeService incomeService;
	private final ExpenseService expenseService;
	private final EmailService emailService;
	private final ProfileService profileService;
	
	
	@SneakyThrows
	@GetMapping("/income-excel")
	public ResponseEntity<Void> emailIncomeExcel(){

		ProfileEntity profile = profileService.getCurrentProfile();
		if(profile == null || profile.getEmail() == null || profile.getEmail().isEmpty()) {
		    throw new IllegalArgumentException("Current user profile or email is missing!");
		}

		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeIncomeToExcel(baos, incomeService.getCurrentMonthIncomesForCurrentUser());
		emailService.sendEmailWithAttachment(profile.getEmail(),
				                            "Income Report Summary",
				                            "I’ve attached your income report — take a look.",
				                            baos.toByteArray(), "income.xlsx");
		return ResponseEntity.ok(null);
	}
	
	
	
	@SneakyThrows
	@GetMapping("/expense-excel")
	public ResponseEntity<Void> emailExpenseExcel(){
		
		
		ProfileEntity profile = profileService.getCurrentProfile();
		if(profile == null || profile.getEmail() == null || profile.getEmail().isEmpty()) {
		    throw new IllegalArgumentException("Current user profile or email is missing!");
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeExpenseToExcel(baos, expenseService.getCurrentMonthExpensesForCurrentUser());
		emailService.sendEmailWithAttachment(profile.getEmail(),
				                            "Expense Report Summary",
				                            "I’ve attached your expense report — take a look.",
				                            baos.toByteArray(), "expense.xlsx");
		return ResponseEntity.ok().build();
	}
	
	

}











