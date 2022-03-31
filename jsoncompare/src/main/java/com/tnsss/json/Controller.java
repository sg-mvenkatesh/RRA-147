//package com.tnsss.json;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.TreeMap;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import com.google.gson.Gson;
//import com.tnsss.entity.ProductRateDetails;
//import com.tnsss.entity.RatesResponseTemplet;
//
//@RestController
//@RequestMapping("/compare")
//public class Controller {
//
//	@Autowired
//	private RestTemplate template;
//
//	static Gson gson = new Gson();
//
//	// rest call to java Api
//	@GetMapping("/javaapi")
//	public Object getJavaApiData(@RequestParam String saleDate, String vin,String dealer,String odometer) {
//		String urlJava = "https://productrates-dev.sgproductsapis.com/api/v1/rates?saleDate=2022-02-07&vin=5NTJCDAE6NH016244&dealer=HYUTX155&odometer=100000";
//		
//		Map<String, String> javaMap = new TreeMap<>();
//		Map<String, String> muleMap = new TreeMap<>();
//		Map<String, Boolean> resultMap = new TreeMap<>();
//		
//		RatesResponseTemplet javRes = template.getForObject(urlJava, RatesResponseTemplet.class);
//		RatesResponseTemplet muleRes = getMuleApi();
//		
//		ArrayList<ProductRateDetails> productRateDetailsJava = javRes.getData().getProductRateDetails();
//		ArrayList<ProductRateDetails> productRateDetailsMule = muleRes.getData().getProductRateDetails();
//		
//		
//		for (ProductRateDetails PRDJavaApi : productRateDetailsJava) {
//			javaMap.put("productSku_Java", PRDJavaApi.getProductSku());
//			javaMap.put("dealerCost_Java", PRDJavaApi.getDealerCost());
//			javaMap.put("multiType_Java", PRDJavaApi.getMultiType());
//			javaMap.put("formNumber_Java", PRDJavaApi.getFormNumber());
//			javaMap.put("retailCost_Java", PRDJavaApi.getRetailCost());
//			for (ProductRateDetails PRDMuleApi : productRateDetailsMule) {
//				muleMap.put("productSku_Mule", PRDMuleApi.getProductSku());
//				muleMap.put("dealerCost_Mule", PRDMuleApi.getDealerCost());
//				muleMap.put("multiType_Mule", PRDMuleApi.getMultiType());
//				muleMap.put("formNumber_Mule", PRDMuleApi.getFormNumber());
//				muleMap.put("retailCost_Mule", PRDMuleApi.getRetailCost());
//				
//				if(PRDJavaApi.getProductSku().equals(PRDMuleApi.getProductSku())) {
//					resultMap.put("productSku", PRDJavaApi.getProductSku().equals(PRDMuleApi.getProductSku()));
//				}
//				if(PRDJavaApi.getDealerCost().equals(PRDMuleApi.getDealerCost())) {
//					resultMap.put("dealerCost", PRDJavaApi.getDealerCost().equals(PRDMuleApi.getDealerCost()));
//				}
//				if(PRDJavaApi.getMultiType().equals(PRDMuleApi.getMultiType())) {
//					resultMap.put("multiType", PRDJavaApi.getMultiType().equals(PRDMuleApi.getMultiType()));
//				}
//				if(PRDJavaApi.getFormNumber().equals(PRDMuleApi.getFormNumber())) {
//					resultMap.put("formNumber", PRDJavaApi.getFormNumber().equals(PRDMuleApi.getFormNumber()));
//				}
//				if(PRDJavaApi.getRetailCost().equals(PRDMuleApi.getRetailCost())) {
//					resultMap.put("retailCost", PRDJavaApi.getRetailCost().equals(PRDMuleApi.getRetailCost()));
//				}
//			}
//		}
//		return javaMap+"\n"+muleMap+"\n"+resultMap;
//	}
//
//	// rest call to mule api
//	@GetMapping("/muleapi")
//	public RatesResponseTemplet getMuleApi() {
//		String url = "http://sgi-ecom-proc-api-uat.us-e1.cloudhub.io/api/v1/ecom/rates?saleDate=2022-02-07&vin=5NTJCDAE6NH016244&dealerCode=HYUTX155&odometer=100000";
////		String url = "http://sgi-ecom-proc-api-uat.us-e1.cloudhub.io/api/v1/ecom/rates?saleDate={saleDate}&vin={vin}&dealerCode={dealer}&odometer={odometer}";
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Client_id", "5b0c387efa6942a297808086528e3393");
//		headers.set("Client_secret", "bf1F8E59d74E4c0FB5D85F8AbC4C150F");
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//		HttpEntity<String> entity = new HttpEntity<>(headers);
//		ResponseEntity<RatesResponseTemplet> exchange = template.exchange(url, HttpMethod.GET, entity, RatesResponseTemplet.class);
//		return exchange.getBody();
//	}
//	
//	@GetMapping("/compareresult")
//	public Object getJavaApiData1() {
//		String urlJava = "https://productrates-dev.sgproductsapis.com/api/v1/rates?saleDate=2022-02-07&vin=5NTJCDAE6NH016244&dealer=HYUTX155&odometer=100000";
////		String urlJava = "https://productrates-dev.sgproductsapis.com/api/v1/rates?saleDate=2022-02-07&vin=5NTJCDAE6NH016244&dealer=HYUTX155&odometer=100000";
//		
//		Map<String, String> javaMap = new TreeMap<>();
//		Map<String, String> muleMap = new TreeMap<>();
//		Map<String, Boolean> resultMap = new TreeMap<>();
//		
//		RatesResponseTemplet javRes = template.getForObject(urlJava, RatesResponseTemplet.class);
//		RatesResponseTemplet muleRes = getMuleApi();
//		
//		ArrayList<ProductRateDetails> productRateDetailsJava = javRes.getData().getProductRateDetails();
//		ArrayList<ProductRateDetails> productRateDetailsMule = muleRes.getData().getProductRateDetails();
//		
//		
//		for (ProductRateDetails PRDMuleApi : productRateDetailsMule) {
//			List<ProductRateDetails> filterList = productRateDetailsJava.stream()
//														.filter(item -> Objects.equals(item.getProductSku(), PRDMuleApi.getProductSku())&Objects.equals(item.getProductSku(), PRDMuleApi.getProductSku()))
//														.collect(Collectors.toList());
//			if(filterList.isEmpty()) {
//				resultMap.put(PRDMuleApi.getProductSku(), false);
//			}else {
//				resultMap.put(PRDMuleApi.getProductSku(), true);
//				
//			}
//			
//		}
//		return resultMap;
//	}
//
//}
