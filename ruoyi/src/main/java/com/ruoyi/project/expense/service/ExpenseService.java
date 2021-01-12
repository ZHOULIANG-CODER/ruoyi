package com.ruoyi.project.expense.service;


import com.ruoyi.project.expense.domain.ExpendDay;
import com.ruoyi.project.expense.domain.QueryDo;
import com.ruoyi.project.expense.mapper.ExpenseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {


    @Autowired
    private ExpenseMapper expenseMapper;
    public List<ExpendDay> listExpensesZl(QueryDo queryDo) {
        return  expenseMapper.listExpensesZl(queryDo);
    }

    public int insertExpense(ExpendDay expendDay) {
        return expenseMapper.insertExpense(expendDay);
    }

    public ExpendDay selectExpenseDetailById(Long id) {
        return  expenseMapper.selectExpenseDetailById(id);
    }

    public int updateExpense(ExpendDay expendDay) {
        return  expenseMapper.updateExpense(expendDay);
    }

    public int deleteExpenseById(Long id) {
        return  expenseMapper.deleteExpenseById(id);
    }
}
