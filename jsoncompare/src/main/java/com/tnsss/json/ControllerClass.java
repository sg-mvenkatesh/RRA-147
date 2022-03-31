package com.tnsss.json;


import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

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
	private String muleAPIContextPath ;
	
	@Value("${java-api.url}")
	private String javaAPIUrl;
	
	@Value("${java-api.context-path}")
	private String javaAPIContextPath;
	
	
	@Autowired
	private RestTemplate template;
	
	static Gson gson = new Gson();
	
	@GetMapping("/javaapi")
	public ProductRateDetails[] getJavaApiData(@RequestParam String saleDate, String vin, String dealerCode, String odometer) {
		String urlJava = javaAPIUrl+javaAPIContextPath+"?saleDate={saleDate}&vin={vin}&dealer={dealerCode}&odometer={odometer}";
		RatesResponseTemplet JavaApiDatainJavaObj = template.getForObject(urlJava, RatesResponseTemplet.class,saleDate, vin, dealerCode, odometer);
		ProductRateDetails[] productRateDetails = JavaApiDatainJavaObj.getData().getProductRateDetails();
		return productRateDetails;
	}
	
	@GetMapping("/muleapi")
	private ProductRateDetails[] getMuleApiData(@RequestParam String saleDate, String vin, String dealerCode, String odometer) {
		String urlMule = muleAPIUrl + muleAPIContextPath +"?saleDate={saleDate}&vin={vin}&dealerCode={dealerCode}&odometer={odometer}";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Client_id", "5b0c387efa6942a297808086528e3393");
		headers.set("Client_secret", "bf1F8E59d74E4c0FB5D85F8AbC4C150F");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<RatesResponseTemplet> exchange = template.exchange(urlMule, HttpMethod.GET, entity, RatesResponseTemplet.class,saleDate, vin, dealerCode, odometer);
		ProductRateDetails[] productRateDetails = exchange.getBody().getData().getProductRateDetails();
		
		return productRateDetails;
	}
	
	@GetMapping("/compare")
	private ResultClass[] getCompare(@RequestParam String saleDate, String vin, String dealerCode, String odometer) {
		ProductRateDetails[] javaApiData = getJavaApiData(saleDate, vin, dealerCode, odometer);
		ProductRateDetails[] muleApi = getMuleApiData(saleDate, vin, dealerCode, odometer);
		ArrayList<ResultClass> resultArray = null;
		
		ResultClass result = new ResultClass();
		int j;
		for(int i=0; i<=javaApiData.length-1;i++) {
			result.setProductSku_java(javaApiData[i].getProductSku());
			result.setDealerCost_java(javaApiData[i].getDealerCost());
			result.setMultiType_java(javaApiData[i].getMultiType());
			result.setFormNumber_java(javaApiData[i].getFormNumber());
			result.setRetailCost_java(javaApiData[i].getRetailCost());
			
			
			for(j=0; j<=muleApi.length-1;j++) {
				result.setProductSku_mule(muleApi[j].getProductSku());
				result.setDealerCost_mule(muleApi[i].getDealerCost());
				result.setMultiType_mule(muleApi[i].getMultiType());
				result.setFormNumber_mule(muleApi[i].getFormNumber());
				result.setRetailCost_mule(muleApi[i].getRetailCost());
				
				result.setProductSku(result.getProductSku_java().contentEquals(result.getProductSku_mule()));
				result.setDealerCost(result.getDealerCost_java().contentEquals(result.getDealerCost_mule()));
				result.setMultiType(result.getMultiType_java().contentEquals(result.getMultiType_mule()));
				result.setFormNumber(result.getFormNumber_java().contentEquals(result.getFormNumber_mule()));
				result.setRetailCost(result.getRetailCost_java().contentEquals(result.getRetailCost_mule()));
				
			}
			j=0;
		}
		resultArray.add(result);
		ResultClass[] array = (ResultClass[]) resultArray.toArray();
		System.out.println(array);
		return array;		
	}
	
	@GetMapping("/dynamicurl")
	private ProductRateDetails[] getDynamicUrl(@RequestParam String saleDate, String vin, String dealerCode, String odometer) {
		String urlMule = muleAPIUrl + muleAPIContextPath +"?saleDate={saleDate}&vin={vin}&dealerCode={dealerCode}&odometer={odometer}";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Client_id", "5b0c387efa6942a297808086528e3393");
		headers.set("Client_secret", "bf1F8E59d74E4c0FB5D85F8AbC4C150F");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		ResponseEntity<RatesResponseTemplet> exchange = template.exchange(urlMule, HttpMethod.GET, entity, RatesResponseTemplet.class, saleDate, vin, dealerCode, odometer);
		ProductRateDetails[] productRateDetails = exchange.getBody().getData().getProductRateDetails();
		return productRateDetails;
		
	}


}
