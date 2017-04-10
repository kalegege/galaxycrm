package com.wasu.ptyw.galaxycrm.web.controller;

import java.util.Date;

public class Ypxx {
   

    public String getBm() {
		return bm;
	}

	public void setBm(String bm) {
		this.bm = bm;
	}

	public String getMc() {
		return mc;
	}

	public void setMc(String mc) {
		this.mc = mc;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public Ypxx(String bm, String mc, float price) {
		super();
		this.bm = bm;
		this.mc = mc;
		this.price = price;
	}

	private String bm;

    private String mc;
    
    private  float price;
    

  

}