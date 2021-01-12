package com.ruoyi.project.expense.domain;

import com.ruoyi.framework.aspectj.lang.annotation.Excel;
import com.ruoyi.framework.web.domain.BaseEntity;

import java.util.Date;


public class ExpendDay extends BaseEntity {

    private  String content;
    private  Integer id		  ;

    @Excel(name ="总金额")
    private  String amount	  ;
    @Excel(name ="备注")
    private  String remark	  ;
    private  String maicai	  ;
    private  String shuiguo	  ;
    private  String jiaotong ;
    private  String guangjie ;
    private  String wanggou	  ;
    private  String lingshi	  ;
    private  String jiacan	  ;
    private  String qingke	  ;
    private  String qita	  ;
    private  char  status	  ;
    private Date  createTime;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        this.status = status;
    }

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMaicai() {
        return maicai;
    }

    public void setMaicai(String maicai) {
        this.maicai = maicai;
    }

    public String getShuiguo() {
        return shuiguo;
    }

    public void setShuiguo(String shuiguo) {
        this.shuiguo = shuiguo;
    }

    public String getJiaotong() {
        return jiaotong;
    }

    public void setJiaotong(String jiaotong) {
        this.jiaotong = jiaotong;
    }

    public String getGuangjie() {
        return guangjie;
    }

    public void setGuangjie(String guangjie) {
        this.guangjie = guangjie;
    }

    public String getWanggou() {
        return wanggou;
    }

    public void setWanggou(String wanggou) {
        this.wanggou = wanggou;
    }

    public String getLingshi() {
        return lingshi;
    }

    public void setLingshi(String lingshi) {
        this.lingshi = lingshi;
    }

    public String getJiacan() {
        return jiacan;
    }

    public void setJiacan(String jiacan) {
        this.jiacan = jiacan;
    }

    public String getQingke() {
        return qingke;
    }

    public void setQingke(String qingke) {
        this.qingke = qingke;
    }

    public String getQita() {
        return qita;
    }

    public void setQita(String qita) {
        this.qita = qita;
    }

    @Override
    public String toString() {
        return "ExpendDay{" +
                "id=" + id +
                ", amount='" + amount + '\'' +
                ", remark='" + remark + '\'' +
                ", maicai='" + maicai + '\'' +
                ", shuiguo='" + shuiguo + '\'' +
                ", jiaotong='" + jiaotong + '\'' +
                ", guangjie='" + guangjie + '\'' +
                ", wanggou='" + wanggou + '\'' +
                ", lingshi='" + lingshi + '\'' +
                ", jiacan='" + jiacan + '\'' +
                ", qingke='" + qingke + '\'' +
                ", qita='" + qita + '\'' +
                '}';
    }
}
