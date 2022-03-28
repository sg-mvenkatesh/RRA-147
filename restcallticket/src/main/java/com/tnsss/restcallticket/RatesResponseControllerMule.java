package com.tnsss.restcallticket;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

public class RatesResponseControllerMule {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();

		// 1. JSON file to Java object
		try {
			RatesResponse rate = gson.fromJson(new FileReader(
					"C:\\Users\\mohanv\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\restcallticket\\src\\main\\java\\com\\tnsss\\restcallticket\\JavaApiData.json"),
					RatesResponse.class);
			Data data = rate.getData();
			ArrayList<ProductRateDetails> productRateDetails = data.getProductRateDetails();
			
			String productSkuJava = null; 
			String DealerCostJava = null;
			String MultiTypeJava = null;
			String FormNumberJava = null;
			String ClassCodeJava = null;
			String RetailCostJava = null;
			
			String productSkuMule; 
			String DealerCostMule;
			String MultiTypeMule;
			String FormNumberMule;
			String ClassCodeMule;
			String RetailCostMule;
			
			for (ProductRateDetails productRateDetails2 : productRateDetails) {
				 productSkuJava = productRateDetails2.getProductSku();
				 DealerCostJava = productRateDetails2.getDealerCost();
				 MultiTypeJava = productRateDetails2.getMultiType();
				 FormNumberJava = productRateDetails2.getFormNumber();
				 ClassCodeJava = productRateDetails2.getClassCode();
				 RetailCostJava = productRateDetails2.getRetailCost();

				System.out.println("productSku Java : " + productSkuJava);
				System.out.println("DealerCost Java : " + DealerCostJava);
				System.out.println("MultiType Java : " + MultiTypeJava);
				System.out.println("FormNumber Java : " + FormNumberJava);
				System.out.println("ClassCode Java : " + ClassCodeJava);
				System.out.println("RetailCost Java : " + RetailCostJava);

			}
			
			RatesResponseMule rateMule = gson.fromJson(new FileReader(
					"C:\\Users\\mohanv\\Documents\\workspace-spring-tool-suite-4-4.10.0.RELEASE\\restcallticket\\src\\main\\java\\com\\tnsss\\restcallticket\\MuleApiData.json"),
					RatesResponseMule.class);
			Data dataMule = rateMule.getData();
			ArrayList<ProductRateDetails> productRateDetailsMule = dataMule.getProductRateDetails();

			for (ProductRateDetails productRateDetails2 : productRateDetailsMule) {
				 productSkuMule = productRateDetails2.getProductSku();
				 DealerCostMule = productRateDetails2.getDealerCost();
				 MultiTypeMule = productRateDetails2.getMultiType();
				 FormNumberMule = productRateDetails2.getFormNumber();
				 ClassCodeMule = productRateDetails2.getClassCode();
				 RetailCostMule = productRateDetails2.getRetailCost();

				System.out.println("productSku Mule : " + productSkuMule);
				System.out.println("DealerCost Mule : " + DealerCostMule);
				System.out.println("MultiType Mule : " + MultiTypeMule);
				System.out.println("FormNumber Mule : " + FormNumberMule);
				System.out.println("ClassCode Mule : " + ClassCodeMule);
				System.out.println("RetailCost Mule : " + RetailCostMule);
				
				
				
			}
			
			System.out.println(productSkuJava.equals(productRateDetailsMule));
			System.out.println(DealerCostJava.equals(productRateDetailsMule));
			System.out.println(MultiTypeJava.equals(productRateDetailsMule));
			System.out.println(FormNumberJava.equals(productRateDetailsMule));
			System.out.println(ClassCodeJava.equals(productRateDetailsMule));
			System.out.println(RetailCostJava.equals(productRateDetailsMule));
		
			
			
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
