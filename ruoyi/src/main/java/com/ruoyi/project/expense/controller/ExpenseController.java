package com.ruoyi.project.expense.controller;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.framework.config.RuoYiConfig;
import com.ruoyi.framework.redis.RedisCache;
import com.ruoyi.framework.web.controller.BaseController;
import com.ruoyi.framework.web.domain.AjaxResult;
import com.ruoyi.framework.web.page.TableDataInfo;
import com.ruoyi.project.expense.domain.ExpendDay;
import com.ruoyi.project.expense.domain.QueryDo;
import com.ruoyi.project.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/expense")
public class ExpenseController extends BaseController {

    @Autowired
    private  RedisCache redisCache;
    @Autowired
    private ExpenseService expenseService;
    RuoYiConfig ruoYiConfig;
    @GetMapping("/listZl")
    public TableDataInfo zlExpense(QueryDo queryDo) {
     List<ExpendDay> listEx =   expenseService.listExpensesZl(queryDo);
        return  getDataTable(listEx);
    }


    @PostMapping("/add")
    public AjaxResult add(@Validated @RequestBody ExpendDay expendDay){
        processData(expendDay);
        int count;
        if (expendDay.getId() !=null ){
            // update
            count= expenseService.updateExpense(expendDay);
        }else {
            // insert
            count = expenseService.insertExpense(expendDay);
        }
        return   toAjax(count);
    }

    @PostMapping("/addPlan")
    public void addPlan(@Validated @RequestBody ExpendDay expendDay) throws IOException {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b =new byte[0] ;
        if(expendDay.getContent().contains("data:image/png;base64")){
            //Base64解码
            b=decoder.decodeBuffer(expendDay.getContent().replace("data:image/png;base64,", ""));
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            String imgFilePath  = RuoYiConfig.getProfile() + UUID.randomUUID() + ".jpg";
            OutputStream out2 = new FileOutputStream(imgFilePath);
            out2.write(b);
            out2.flush();
            out2.close();

        }
    }
    private void processData(@Validated @RequestBody ExpendDay expendDay) {
        expendDay.setMaicai(format(expendDay.getMaicai()));
        expendDay.setShuiguo(format(expendDay.getShuiguo()));
        expendDay.setJiaotong(format(expendDay.getJiaotong()));
        expendDay.setGuangjie(format(expendDay.getGuangjie()));
        expendDay.setWanggou(format(expendDay.getWanggou()));
        expendDay.setLingshi(format(expendDay.getLingshi()));
        expendDay.setJiacan(format(expendDay.getJiacan()));
        expendDay.setQingke(format(expendDay.getQingke()));
        expendDay.setQita(format(expendDay.getQita()));

        BigDecimal add = convert(expendDay.getMaicai()).add(convert(expendDay.getShuiguo())).add(convert(expendDay.getJiaotong()))
                .add(convert(expendDay.getGuangjie())).add(convert(expendDay.getWanggou()))
                .add(convert(expendDay.getLingshi())).add(convert(expendDay.getJiacan())).add(convert(expendDay.getQingke()))
                .add(convert(expendDay.getQingke()));
        expendDay.setAmount(add.toString());
        expendDay.setCreateTime(new Date());
    }


    /**
     * detail
     */
    @GetMapping(value = { "/", "/{id}" })
    public AjaxResult getInfo(@PathVariable(value = "id", required = true) Long id) {
        AjaxResult ajax = AjaxResult.success();
        ExpendDay  expendDay  = expenseService.selectExpenseDetailById(id);
        ajax.put("expenseDay",expendDay);
        return ajax;
    }

    @DeleteMapping("/{id}" )
    public AjaxResult delete(@PathVariable(value = "id") Long id) {
      int count  = expenseService.deleteExpenseById(id);
        return toAjax(count);
    }

    private String format(String inputData){
       return StringUtils.isBlank(inputData)?"0":inputData;
    }
    private BigDecimal convert(String toConvert){
        return  new BigDecimal(toConvert);
    }
}
