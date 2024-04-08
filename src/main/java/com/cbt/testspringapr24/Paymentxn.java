package com.cbt.testspringapr24;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "paymentxns")
public class Paymentxn {
    @Id
    @Column(name = "txnid", nullable = false, length = 500)
    private String txnid;

    @Column(name = "paymenttype", length = 10)
    private String paymenttype;

    @Column(name = "pymntrefid", length = 500)
    private String pymntrefid;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payerwallet", referencedColumnName = "walletid")
    private Usernamewalletlink payerwallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payeewallet", referencedColumnName = "walletid")
    private Usernamewalletlink payeewallet;

    @Column(name = "\"time\"")
    private Instant time;

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getPaymenttype() {
        return paymenttype;
    }

    public void setPaymenttype(String paymenttype) {
        this.paymenttype = paymenttype;
    }

    public String getPymntrefid() {
        return pymntrefid;
    }

    public void setPymntrefid(String pymntrefid) {
        this.pymntrefid = pymntrefid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Usernamewalletlink getPayerwallet() {
        return payerwallet;
    }

    public void setPayerwallet(Usernamewalletlink payerwallet) {
        this.payerwallet = payerwallet;
    }

    public Usernamewalletlink getPayeewallet() {
        return payeewallet;
    }

    public void setPayeewallet(Usernamewalletlink payeewallet) {
        this.payeewallet = payeewallet;
    }

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

}