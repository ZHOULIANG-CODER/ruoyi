package com.ruoyi.project.expense.mapper;

import com.ruoyi.project.expense.domain.ExpendDay;
import com.ruoyi.project.expense.domain.QueryDo;

import java.util.List;

public  interface ExpenseMapper {



    List<ExpendDay> listExpensesZl(QueryDo queryDo);

    int insertExpense(ExpendDay expendDay);

    ExpendDay selectExpenseDetailById(Long id);

    int updateExpense(ExpendDay expendDay);

    int deleteExpenseById(Long id);
}
