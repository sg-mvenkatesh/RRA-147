package com.tnsss.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.tnss.result.ResultClass;
import com.tnsss.entity.ProductRateDetails;
import com.tnsss.entity.RatesResponseTemplet;

@RestController
@RequestMapping("/comparejson")
public class ControllerClass {

	@Value("${mule-api.url}")
	private String muleAPIUrl;

	@Value("${mule-api.context-path}")
	private String muleAPIContextPath;

	@Value("${java-api.url}")
	private String javaAPIUrl;

	@Value("${java-api.context-path}")
	private String javaAPIContextPath;

	@Autowired
	private RestTemplate template;

	static Gson gson = new Gson();

	@GetMapping("/javaapi")
	public ProductRateDetails[] getJavaApiData(@RequestParam String saleDate, String vin, String dealerCode,
			@RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

		String urlJava = javaAPIUrl + javaAPIContextPath + "?saleDate={saleDate}&vin={vin}&dealer={dealerCode}";

		if (odometer != null)
			urlJava += "&odometer={odometer}";
		if (vehicleCondition != null)
			urlJava += "&vehicleCondition={vehicleCondition}";

		RatesResponseTemplet JavaApiDatainJavaObj = template.getForObject(urlJava, RatesResponseTemplet.class, saleDate,
				vin, dealerCode, odometer, vehicleCondition);
		ProductRateDetails[] productRateDetails = JavaApiDatainJavaObj.getData().getProductRateDetails();
		return productRateDetails;
	}

	@GetMapping("/muleapi")
	public ProductRateDetails[] getMuleApiData(@RequestParam String saleDate, String vin, String dealerCode,
//			@RequestParam(required = false) String odometer,
			@RequestParam(required = false) String vehicleCondition) {

		String urlMule = muleAPIUrl + muleAPIContextPath + "?saleDate={saleDate}&vin={vin}&dealerCode={dealerCode}";

//		if (odometer != null)
//			urlMule += "&odometer={odometer}";
		if (vehicleCondition != null)
			urlMule += "&vehicleCondition={vehicleCondition}";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Client_id", "5b0c387efa6942a297808086528e3393");
		headers.set("Client_secret", "bf1F8E59d74E4c0FB5D85F8AbC4C150F");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<RatesResponseTemplet> exchange = template.exchange(urlMule, HttpMethod.GET, entity,
				RatesResponseTemplet.class, saleDate, vin, dealerCode, vehicleCondition);
		ProductRateDetails[] productRateDetails = exchange.getBody().getData().getProductRateDetails();
		return productRateDetails;
	}

	@GetMapping("/compare")
	public Object[] getCompare(@RequestParam String saleDate, String vin, String dealerCode,
			@RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

		ProductRateDetails[] javaApiData = getJavaApiData(saleDate, vin, dealerCode, odometer, vehicleCondition);
		ProductRateDetails[] muleApi = getMuleApiData(saleDate, vin, dealerCode, vehicleCondition);

		ArrayList<ResultClass> resultArray = new ArrayList<ResultClass>();

		Object[] array = null;

		for (int i = 0; i <= javaApiData.length - 1; i++) {
			String productSkuJavaApi = javaApiData[i].getProductSku();

			for (int j = 0; j <= muleApi.length - 1; j++) {
				String productSkuMuleApi = muleApi[j].getProductSku();

				try {
					if (productSkuJavaApi.contentEquals(productSkuMuleApi)) {
						ResultClass result = new ResultClass();
						result.setProductSku_java(javaApiData[i].getProductSku());
						result.setDealerCost_java(javaApiData[i].getDealerCost());
						result.setMultiType_java(javaApiData[i].getMultiType());
						result.setFormNumber_java(javaApiData[i].getFormNumber());
						result.setRetailCost_java(javaApiData[i].getRetailCost());

						result.setProductSku_mule(muleApi[j].getProductSku());
						result.setDealerCost_mule(muleApi[j].getDealerCost());
						result.setMultiType_mule(muleApi[j].getMultiType());
						result.setFormNumber_mule(muleApi[j].getFormNumber());
						result.setRetailCost_mule(muleApi[j].getRetailCost());

						result.setProductSku(result.getProductSku_java().contentEquals(result.getProductSku_mule()));
						result.setDealerCost(
								String.valueOf(Math.abs(Double.parseDouble(result.getDealerCost_java()))).contentEquals(
										String.valueOf(Math.abs(Double.parseDouble(result.getDealerCost_mule())))));
						result.setMultiType(result.getMultiType_java().contentEquals(result.getMultiType_mule()));
						result.setFormNumber(result.getFormNumber_java().contentEquals(result.getFormNumber_mule()));
						result.setRetailCost(
								String.valueOf(Math.abs(Double.parseDouble(result.getRetailCost_java()))).contentEquals(
										String.valueOf(Math.abs(Double.parseDouble(result.getRetailCost_mule())))));

						resultArray.add(result);
						j = muleApi.length - 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		array = resultArray.toArray();
		return array;
	}

	@GetMapping("/notmatched")
	public Object[] getNotMachedWithMule(@RequestParam String saleDate, String vin, String dealerCode,
			@RequestParam(required = false) String odometer, @RequestParam(required = false) String vehicleCondition) {

		ProductRateDetails[] javaApiData = getJavaApiData(saleDate, vin, dealerCode, odometer, vehicleCondition);
		ProductRateDetails[] muleApi = getMuleApiData(saleDate, vin, dealerCode, vehicleCondition);

		Map<String, String> responseMap = new TreeMap<>();
		ArrayList<String> allJavaSku = new ArrayList<String>();
		ArrayList<String> allMuleSku = new ArrayList<String>();

		// get java and mule count
		int javaObjectCount = javaApiData.length;
		int muleObjectCount = muleApi.length;

		responseMap.put("java Object Count", String.valueOf(javaObjectCount));
		responseMap.put("mule Object Count", String.valueOf(muleObjectCount));

		// get extra products
		for (int i = 0; i <= javaApiData.length - 1; i++) {
			allJavaSku.add(javaApiData[i].getProductSku());
		}

		for (int j = 0; j <= muleApi.length - 1; j++) {
			allMuleSku.add(muleApi[j].getProductSku());
		}
		// checking weather both has same size of objs in java and mule
		if (javaObjectCount == muleObjectCount) {
			// if same checking all the sku is matching with both are not.
			boolean checkJavaWithMule = allMuleSku.containsAll(allJavaSku);// t r f
			boolean checkMuleWithJava = allJavaSku.containsAll(allMuleSku);// t r f
			// if yes then say no extra products.
			if (checkJavaWithMule == true && checkMuleWithJava == true) {
				responseMap.put("Extra Products: ",
						" No Extra products all the products are matching with each other response..!");
			}
		} 
		
		if(javaObjectCount != muleObjectCount){
//			 take an array of any 1 and compare others each sku with all that array 
//			if nothing is matching then return that sku as a extra product
					for (int k = 0; k <= allMuleSku.size()-1; k++) {
						String mule = allMuleSku.get(k);
						if (!allJavaSku.contains(mule)) {
							responseMap.put(mule, " : not matched with any of the sku of java API.");
						}
					}
			}
		Object[] array = responseMap.entrySet().toArray();
		return array;

	}

}
