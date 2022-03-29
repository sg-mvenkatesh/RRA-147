package com.tnsss.json;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.tnsss.entity.Data;
import com.tnsss.entity.ProductRateDetails;
import com.tnsss.entity.RatesResponse;

@RestController
@RequestMapping("/compare")
public class Controller {

	@Autowired
	private RestTemplate template;

	static Gson gson = new Gson();

	// rest call to java api
	@GetMapping("/javaapi")
	public Object getJavaApiData() {
		String urlJava = "https://productrates-dev.sgproductsapis.com/api/v1/rates?saleDate=2022-02-07&vin=5NTJCDAE6NH016244&dealer=HYUTX155&odometer=100000";

		RatesResponse javRes = template.getForObject(urlJava, RatesResponse.class);
		RatesResponse muleRes = getMuleApi();
		
		ArrayList<ProductRateDetails> productRateDetailsJava = javRes.getData().getProductRateDetails();
		ArrayList<ProductRateDetails> productRateDetailsMule = muleRes.getData().getProductRateDetails();

		for (ProductRateDetails PRDJavaApi : productRateDetailsJava) {
			for (ProductRateDetails PRDMuleApi : productRateDetailsMule) {
				if (PRDJavaApi.getProductSku().equals(PRDMuleApi.getProductSku())) {
					System.out.println("True");
					System.out.println("productSku Java : " + PRDJavaApi.getProductSku());
					System.out.println("productSku Mule : " + PRDMuleApi.getProductSku());
				}
				if (PRDJavaApi.getDealerCost().equals(PRDMuleApi.getDealerCost())) {
					System.out.println("True");
					System.out.println("DealerCost Java : " + PRDJavaApi.getDealerCost());
					System.out.println("DealerCost Mule : " + PRDMuleApi.getDealerCost());
				}
				if (PRDJavaApi.getMultiType().equals(PRDMuleApi.getMultiType())) {
					System.out.println("True");
					System.out.println("MultiType Java : " + PRDJavaApi.getMultiType());
					System.out.println("MultiType Mule : " + PRDMuleApi.getMultiType());
				}
				if (PRDJavaApi.getFormNumber().equals(PRDMuleApi.getFormNumber())) {
					System.out.println("True");
					System.out.println("FormNumber Java : " + PRDJavaApi.getFormNumber());
					System.out.println("FormNumber Mule : " + PRDMuleApi.getFormNumber());
				}
				if (PRDJavaApi.getRetailCost().equals(PRDMuleApi.getRetailCost())) {
					System.out.println("True");
					System.out.println("RetailCost Java : " + PRDJavaApi.getRetailCost());
					System.out.println("RetailCost Mule : " + PRDMuleApi.getRetailCost());
				}

			}
		}
		return javRes;
	}

	// rest call to mule api
	@GetMapping("/muleapi")
	public RatesResponse getMuleApi() {
		String url = "http://sgi-ecom-proc-api-uat.us-e1.cloudhub.io/api/v1/ecom/rates?saleDate=2022-02-07&vin=5NTJCDAE6NH016244&dealerCode=HYUTX155&odometer=100000";
		HttpHeaders headers = new HttpHeaders();
		headers.set("Client_id", "5b0c387efa6942a297808086528e3393");
		headers.set("Client_secret", "bf1F8E59d74E4c0FB5D85F8AbC4C150F");
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		ResponseEntity<RatesResponse> exchange = template.exchange(url, HttpMethod.GET, entity, RatesResponse.class);
		return exchange.getBody();
	}

}
