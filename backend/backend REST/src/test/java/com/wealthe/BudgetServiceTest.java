package com.wealthe;

import com.wealthe.dto.request.BudgetRequest;
import com.wealthe.dto.response.BudgetResponse;
import com.wealthe.entity.Budget;
import com.wealthe.entity.Transaction;
import com.wealthe.entity.User;
import com.wealthe.enumeration.BudgetPeriod;
import com.wealthe.enumeration.TransactionType;
import com.wealthe.repository.BudgetRepository;
import com.wealthe.repository.TransactionRepository;
import com.wealthe.service.BudgetService;
import com.wealthe.service.UserService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BudgetServiceTest {

    @Test
    void budgetSpendOnlyIncludesExpensesInItsCategoryAndPeriod() {
        BudgetRepository budgets = mock(BudgetRepository.class);
        UserService users = mock(UserService.class);
        TransactionRepository transactions = mock(TransactionRepository.class);
        User user = new User();
        when(users.getById(1L)).thenReturn(user);
        when(budgets.save(any(Budget.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(transactions.findByAccountUserIdOrderByTransactionDateDesc(null)).thenReturn(List.of(
                expense("food", "125.00", LocalDate.of(2026, 11, 5)),
                expense("transport", "50.00", LocalDate.of(2026, 11, 6)),
                expense("food", "75.00", LocalDate.of(2026, 12, 1))
        ));

        BudgetService service = new BudgetService(budgets, users, transactions);
        BudgetResponse response = service.create(new BudgetRequest(
                1L,
                "Food",
                new BigDecimal("500.00"),
                BudgetPeriod.MONTHLY,
                LocalDate.of(2026, 11, 1),
                LocalDate.of(2026, 11, 30)
        ));

        assertEquals(new BigDecimal("125.00"), response.spent());
        assertEquals(new BigDecimal("375.00"), response.remaining());
    }

    private Transaction expense(String category, String amount, LocalDate date) {
        Transaction transaction = new Transaction();
        transaction.setCategory(category);
        transaction.setType(TransactionType.EXPENSE);
        transaction.setAmount(new BigDecimal(amount));
        transaction.setTransactionDate(date);
        return transaction;
    }
}
