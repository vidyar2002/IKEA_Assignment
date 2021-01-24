package com.dev.inventory.dto;

public enum ReasonCode {

	ADDING_STOCK(1),
	REDUCE_STOCK(2);
	
	private final int reasonCode;
	
	private ReasonCode(int reasonCode) {
		this.reasonCode = reasonCode;
	}
	
	public int getReasonCode()
	{
		return this.reasonCode;
	}
}
