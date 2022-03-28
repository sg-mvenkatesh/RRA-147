package com.tnsss.restcallticket;

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

	public VehicleDetails getVehicleDetails ()
    {
        return vehicleDetails;
    }

    public void setVehicleDetails (VehicleDetails vehicleDetails)
    {
        this.vehicleDetails = vehicleDetails;
    }

    public DealerDetails getDealerDetails ()
    {
        return dealerDetails;
    }

    public void setDealerDetails (DealerDetails dealerDetails)
    {
        this.dealerDetails = dealerDetails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [productRateDetails = "+productRateDetails+", vehicleDetails = "+vehicleDetails+", dealerDetails = "+dealerDetails+"]";
    }
}
