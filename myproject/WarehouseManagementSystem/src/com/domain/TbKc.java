package com.domain;



/**
 * TbKc entity. @author MyEclipse Persistence Tools
 */

public class TbKc  implements java.io.Serializable {


    // Fields    

     private Integer kno;
     private TbStorage tbStorage;
     private TbGood tbGood;
     private Integer knum;
     private String kdate;


    // Constructors

    /** default constructor */
    public TbKc() {
    }

    
    /** full constructor */
    public TbKc(TbStorage tbStorage, TbGood tbGood, Integer knum, String kdate) {
        this.tbStorage = tbStorage;
        this.tbGood = tbGood;
        this.knum = knum;
        this.kdate = kdate;
    }

   
    // Property accessors

    public Integer getKno() {
        return this.kno;
    }
    
    public void setKno(Integer kno) {
        this.kno = kno;
    }

    public TbStorage getTbStorage() {
        return this.tbStorage;
    }
    
    public void setTbStorage(TbStorage tbStorage) {
        this.tbStorage = tbStorage;
    }

    public TbGood getTbGood() {
        return this.tbGood;
    }
    
    public void setTbGood(TbGood tbGood) {
        this.tbGood = tbGood;
    }

    public Integer getKnum() {
        return this.knum;
    }
    
    public void setKnum(Integer knum) {
        this.knum = knum;
    }

    public String getKdate() {
        return this.kdate;
    }
    
    public void setKdate(String kdate) {
        this.kdate = kdate;
    }
   








}