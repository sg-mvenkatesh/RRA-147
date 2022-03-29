package com.tnsss.entity;

import java.util.ArrayList;

public class Data {
	
	private ArrayList<ProductRateDetails> productRateDetails;

    private VehicleDetails vehicleDetails;

    private DealerDetails dealerDetails;

    public ArrayList<ProductRateDetails> getProductRateDetails() {
		return productRateDetails;
	}

	public void setProductRateDetails(ArrayList<ProductRateDetails> productRateDetails) {
		this.productRateDetails = productRateDetails;
	}

	
    @Override
    public String toString()
    {
        return "ClassPojo [productRateDetails = "+productRateDetails+"]";
    }
}
