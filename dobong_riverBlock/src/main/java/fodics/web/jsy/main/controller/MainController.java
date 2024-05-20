package fodics.web.jsy.main.controller;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import fodics.web.jsy.main.model.dto.LineData;
import fodics.web.jsy.main.model.dto.Main;
import fodics.web.jsy.main.model.service.MainService;

@Controller
public class MainController {
	
	
	@Autowired
	private MainService service;
	
	
	private final RestTemplate restTemplate;

    @Autowired
    public MainController(RestTemplate restTemplate) {
      this.restTemplate = restTemplate;
    }

	
	
	@GetMapping("/")
	public String home() {
		return "main";
	}
	
	
	// DB IP가져오기 
	@PostMapping("/ipAddrFetch")
	@ResponseBody
	public String ipAddrFetch(
			@RequestBody  String req
			) {
		System.out.println("req: " + req);
		
		return req;
	}
	

	
	@PostMapping("/sendDate")
	@ResponseBody
	public Map<String, Object> loadData(
			@RequestBody Map<String, Object> paramMap
//			@RequestBody String occuDate
			) {
	    
//		System.out.println("paramMap: " + paramMap);
		 String occuDateString = (String) paramMap.get("occuDate");
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
		 LocalDate occuDate = LocalDate.parse(occuDateString, formatter);
//	    System.out.println("occuDate: " + occuDate);
	    
	    
	    Map<String, Object> map = new HashMap<>();
	    
//	    System.out.println("map : " + map);
	    
	    return map; 
	}
	
	
	
	
	   // Query 1 호출
		// 개문횟수 
		@PostMapping("/openDataList")
		@ResponseBody
		public String openGateListData(
				@RequestBody  String req
				) {
//			     System.out.println("openGate req: " + req);

			    String ipAddress;
			    String port;

			    // MappingJackson2HttpMessageConverter 추가
			    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			    try {
			    	InputStream is = getClass().getResourceAsStream("/server_info.ini");
			        Scanner s = new Scanner(is);
			        ipAddress = s.nextLine();
			        port = s.nextLine();
//			        System.out.println("openGate ipAddress : "+ ipAddress);
//			        System.out.println("openGate port : "+ port);
			        s.close();
			        is.close();

			        // JSON 문자열을 파싱하여 필요한 변수에 할당
			        JSONObject jsonObject = new JSONObject(req);
			        String occuDate = jsonObject.getString("occuDate");
			        String serverip = jsonObject.getString("serverip");
			        String query = jsonObject.getString("query");
//		            System.out.println("openGate occuDate : "+ occuDate);
//		            System.out.println("openGate serverip : "+ serverip);
//		            System.out.println("openGate query : "+ query);

			        String execute_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/execute"; // 외부 RESTful API의 URL select
//			        System.out.println("openGate url : "+ execute_url);

			        // 서버로 전송할 객체 생성
			        Map<String, String> requestBody = new LinkedHashMap<>();
			        requestBody.put("occuDate", occuDate);
			        requestBody.put("query", query);
			        requestBody.put("serverip", serverip);
//			        System.out.println("openGate requestBody : "+ requestBody);

			        // 요청 헤더 설정
			        HttpHeaders headers = new HttpHeaders();
			        headers.setContentType(MediaType.APPLICATION_JSON);

			        // HttpEntity 생성
			        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

			        // post 요청 보내기
			        String execute_url_resp = restTemplate.postForObject(execute_url, requestEntity, String.class);

//			        System.out.print("execute_url_resp"+ execute_url_resp);
			        
			        // 응답 데이터를 클라이언트에 반환
			        return execute_url_resp;
			        
			        
			        
			    } catch (Exception e) {
			       //  //  System.out.println("Read Query Error");
			        e.printStackTrace();
			        return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
			    }
			
		}
		
		
		
		
		
		// Query 1 받기
		// 개문횟수 
		@PostMapping("/openDataList01")
		@ResponseBody
		public String openGateListData01(
				@RequestBody  String req
				) {
//			 System.out.println("openGate01 req: " + req);
			
			String ipAddress;
			String port;
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			
			try {
				InputStream is = getClass().getResourceAsStream("/server_info.ini");
				Scanner s = new Scanner(is);
				ipAddress = s.nextLine();
				port = s.nextLine();
//				System.out.println("openGate01 ipAddress : "+ ipAddress);
//				System.out.println("openGate01 port : "+ port);
				s.close();
				is.close();
				
				// JSON 문자열을 파싱하여 필요한 변수에 할당
				JSONObject jsonObject = new JSONObject(req);
				String occuDate = jsonObject.getString("occuDate");
				String serverip = jsonObject.getString("serverip");
				String query = jsonObject.getString("query");
//				System.out.println("openGate01 occuDate : "+ occuDate);
//				System.out.println("openGate01 serverip : "+ serverip);
//				System.out.println("openGate01 query : "+ query);
				
				String select_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/select"; // 외부 RESTful API의 URL select
//				System.out.println("openGate url : "+ select_url);
				
				// 서버로 전송할 객체 생성
				Map<String, String> requestBody = new LinkedHashMap<>();
				requestBody.put("occuDate", occuDate);
				requestBody.put("query", query);
				requestBody.put("serverip", serverip);
//				System.out.println("openGate01 requestBody : "+ requestBody);
				
				// 요청 헤더 설정
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
				// HttpEntity 생성
				HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
				
				// post 요청 보내기
				String select_url_resp = restTemplate.postForObject(select_url, requestEntity, String.class);
				
//				System.out.print("select_url_resp"+ select_url_resp);
				
				// 응답 데이터를 클라이언트에 반환
				return select_url_resp;
				
				
				
			} catch (Exception e) {
				//  System.out.println("Read Query Error");
				e.printStackTrace();
				return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
			}
			
		}
		
		
		
		
		
		
		
		 // Query 2 호출
		// 폐문횟수 
		@PostMapping("/closeDataList")
		@ResponseBody
		public String closeGateListData(
				@RequestBody  String req
				) {
			
//			System.out.println("closeGate req: " + req);

		    String ipAddress;
		    String port;

		    // MappingJackson2HttpMessageConverter 추가
		    restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

		    try {
		    	InputStream is = getClass().getResourceAsStream("/server_info.ini");
		        Scanner s = new Scanner(is);
		        ipAddress = s.nextLine();
		        port = s.nextLine();
//		        System.out.println("closeGate ipAddress : "+ ipAddress);
//		        System.out.println("closeGate port : "+ port);
		        s.close();
		        is.close();

		        // JSON 문자열을 파싱하여 필요한 변수에 할당
		        JSONObject jsonObject = new JSONObject(req);
		        String occuDate = jsonObject.getString("occuDate");
		        String serverip = jsonObject.getString("serverip");
		        String query = jsonObject.getString("query");
//	            System.out.println("closeGate occuDate : "+ occuDate);
//	            System.out.println("closeGate serverip : "+ serverip);
//	            System.out.println("closeGate query : "+ query);

		        String execute_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/execute"; // 외부 RESTful API의 URL select
//		        System.out.println("closeGate url : "+ execute_url);

		        // 서버로 전송할 객체 생성
		        Map<String, String> requestBody = new LinkedHashMap<>();
		        requestBody.put("occuDate", occuDate);
		        requestBody.put("query", query);
		        requestBody.put("serverip", serverip);
//		        System.out.println("closeGate requestBody : "+ requestBody);

		        // 요청 헤더 설정
		        HttpHeaders headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);

		        // HttpEntity 생성
		        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

		        // post 요청 보내기
		        String execute_url_resp = restTemplate.postForObject(execute_url, requestEntity, String.class);

//		        System.out.print("execute_url_resp"+ execute_url_resp);
		        
		        // 응답 데이터를 클라이언트에 반환
		        return execute_url_resp;
		        
		        
		        
		    } catch (Exception e) {
//		        System.out.println("Read Query Error");
		        e.printStackTrace();
		        return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
		    }
		
		}
		
		

		
		// Query 2 받기
		// 폐문횟수 
		@PostMapping("/closeDataList01")
		@ResponseBody
		public String closeGateListData01(
				@RequestBody  String req
				) {
			
//			System.out.println("closeGate01 req: " + req);
			
			String ipAddress;
			String port;
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			
			try {
				InputStream is = getClass().getResourceAsStream("/server_info.ini");
				Scanner s = new Scanner(is);
				ipAddress = s.nextLine();
				port = s.nextLine();
//			    System.out.println("closeGate01 ipAddress : "+ ipAddress);
//			    System.out.println("closeGate01 port : "+ port);
				s.close();
				is.close();
				
				// JSON 문자열을 파싱하여 필요한 변수에 할당
				JSONObject jsonObject = new JSONObject(req);
				String occuDate = jsonObject.getString("occuDate");
				String serverip = jsonObject.getString("serverip");
				String query = jsonObject.getString("query");
//			    System.out.println("closeGate01 occuDate : "+ occuDate);
//			    System.out.println("closeGate01 serverip : "+ serverip);
//			    System.out.println("closeGate01 query : "+ query);
				
				String select_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/select"; // 외부 RESTful API의 URL select
//				System.out.println("closeGate01 url : "+ select_url);
				
				// 서버로 전송할 객체 생성
				Map<String, String> requestBody = new LinkedHashMap<>();
				requestBody.put("occuDate", occuDate);
				requestBody.put("query", query);
				requestBody.put("serverip", serverip);
//				System.out.println("closeGate01 requestBody : "+ requestBody);
				
				// 요청 헤더 설정
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
				// HttpEntity 생성
				HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
				
				// post 요청 보내기
				String select_url_resp = restTemplate.postForObject(select_url, requestEntity, String.class);
				
//				System.out.print("select_url_resp"+ select_url_resp);
				
				// 응답 데이터를 클라이언트에 반환
				return select_url_resp;
				
				
				
			} catch (Exception e) {
//				System.out.println("Read Query Error");
				e.printStackTrace();
				return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
			}
			
		}
		
		
		
		

		
	    // Query 3 호출
		//테이블
		@PostMapping("/tableDataList")
		@ResponseBody
		public String tableData(
				@RequestBody  String req
				) {
			
//		    System.out.println("tableData req: " + req);
			
			String ipAddress;
			String port;
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			
			try {
				InputStream is = getClass().getResourceAsStream("/server_info.ini");
				Scanner s = new Scanner(is);
				ipAddress = s.nextLine();
				port = s.nextLine();
//			    System.out.println("tableData ipAddress : "+ ipAddress);
//			    System.out.println("tableData port : "+ port);
				s.close();
				is.close();
				
				// JSON 문자열을 파싱하여 필요한 변수에 할당
				JSONObject jsonObject = new JSONObject(req);
				String serverip = jsonObject.getString("serverip");
				String query = jsonObject.getString("query");
//				System.out.println("tableData serverip : "+ serverip);
//				System.out.println("tableData query : "+ query);
				
				String execute_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/execute"; // 외부 RESTful API의 URL execute
//				System.out.println("tableData url : "+ execute_url);
				
				// 서버로 전송할 객체 생성
				Map<String, String> requestBody = new LinkedHashMap<>();
				requestBody.put("query", query);
				requestBody.put("serverip", serverip);
//				System.out.println("tableData requestBody : "+ requestBody);
				
				// 요청 헤더 설정
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
				// HttpEntity 생성
				HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
				
				// post 요청 보내기
				String execute_url_resp = restTemplate.postForObject(execute_url, requestEntity, String.class);
				
//				System.out.print("execute_url_resp"+ execute_url_resp);
				
				// 응답 데이터를 클라이언트에 반환
				return execute_url_resp;
				
				
				
			} catch (Exception e) {
				//  System.out.println("Read Query Error");
				e.printStackTrace();
				return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
			}
		}
		
		
		
			
			
		
		
		
		// Query 3 받기
		//테이블
		@PostMapping(value="/tableDataList01", produces="text/plain; charset=UTF-8")
		@ResponseBody
		public String tableData01(
				@RequestBody  String req
				) {
			
//		   System.out.println("tableData01 req: " + req);
			
			String ipAddress;
			String port;
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			
			try {
				InputStream is = getClass().getResourceAsStream("/server_info.ini");
				Scanner s = new Scanner(is);
				ipAddress = s.nextLine();
				port = s.nextLine();
//				  System.out.println("tableData01 ipAddress : "+ ipAddress);
//				  System.out.println("tableData01 port : "+ port);
				s.close();
				is.close();
				
				// JSON 문자열을 파싱하여 필요한 변수에 할당
				JSONObject jsonObject = new JSONObject(req);
				String serverip = jsonObject.getString("serverip");
				String query = jsonObject.getString("query");
//				  System.out.println("tableData01 serverip : "+ serverip);
//				  System.out.println("tableData01 query : "+ query);
				
				String select_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/select"; // 외부 RESTful API의 URL execute
				//  System.out.println("tableData01 url : "+ select_url);
				
				// 서버로 전송할 객체 생성
				Map<String, String> requestBody = new LinkedHashMap<>();
				requestBody.put("query", query);
				requestBody.put("serverip", serverip);
				//  System.out.println("tableData01 requestBody : "+ requestBody);
				
				// 요청 헤더 설정
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				
				// HttpEntity 생성
				HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
				
				// post 요청 보내기
				String select_url_resp = restTemplate.postForObject(select_url, requestEntity, String.class);
				
//				  System.out.print("select_url_resp"+ select_url_resp);
				
				// 응답 데이터를 클라이언트에 반환
				return select_url_resp;
				
				
				
			} catch (Exception e) {
				//  System.out.println("Read Query Error");
				e.printStackTrace();
				return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
			}
		}
		
		
		
		
		
		// Query 5 호출
		//라이브
		@PostMapping("/liveDataList")
		@ResponseBody
		public String liveDataList(
				@RequestBody  String req
				) {
			
			//  System.out.println("liveData req: " + req);
			
				String ipAddress;
				String port;
				
				// MappingJackson2HttpMessageConverter 추가
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				
				try {
					InputStream is = getClass().getResourceAsStream("/server_info.ini");
					Scanner s = new Scanner(is);
					ipAddress = s.nextLine();
					port = s.nextLine();
					//  System.out.println("liveData ipAddress : "+ ipAddress);
					//  System.out.println("liveData port : "+ port);
					s.close();
					is.close();
					
					// JSON 문자열을 파싱하여 필요한 변수에 할당
					JSONObject jsonObject = new JSONObject(req);
					String serverip = jsonObject.getString("serverip");
					String query = jsonObject.getString("query");
					//  System.out.println("liveData serverip : "+ serverip);
					//  System.out.println("liveData query : "+ query);
					
					String execute_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/execute"; // 외부 RESTful API의 URL execute
					//  System.out.println("liveData url : "+ execute_url);
					
					// 서버로 전송할 객체 생성
					Map<String, String> requestBody = new LinkedHashMap<>();
					requestBody.put("query", query);
					requestBody.put("serverip", serverip);
					//  System.out.println("liveData requestBody : "+ requestBody);
					
					// 요청 헤더 설정
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					
					// HttpEntity 생성
					HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
					
					// post 요청 보내기
					String execute_url_resp = restTemplate.postForObject(execute_url, requestEntity, String.class);
					
					//  System.out.print("execute_url_resp"+ execute_url_resp);
					
					// 응답 데이터를 클라이언트에 반환
					return execute_url_resp;
					
					
					
				} catch (Exception e) {
					//  System.out.println("Read Query Error");
					e.printStackTrace();
					return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
				}
		}
		
		
		
		
		
	    // Query 5 받기
		//라이브
		@PostMapping("/liveDataList01")
		@ResponseBody
		public String liveDataList01(
				@RequestBody  String req
				) {
				//  System.out.println("liveData01 req: " + req);
			
				String ipAddress;
				String port;
				
				// MappingJackson2HttpMessageConverter 추가
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				
				try {
					InputStream is = getClass().getResourceAsStream("/server_info.ini");
					Scanner s = new Scanner(is);
					ipAddress = s.nextLine();
					port = s.nextLine();
					//  System.out.println("liveData01 ipAddress : "+ ipAddress);
					//  System.out.println("liveData01 port : "+ port);
					s.close();
					is.close();
					
					// JSON 문자열을 파싱하여 필요한 변수에 할당
					JSONObject jsonObject = new JSONObject(req);
					String serverip = jsonObject.getString("serverip");
					String query = jsonObject.getString("query");
					//  System.out.println("liveData01 serverip : "+ serverip);
					//  System.out.println("liveData01 query : "+ query);
					
					String select_url = "http://"+ipAddress+":"+port+"/fnvr/request/query/select"; // 외부 RESTful API의 URL execute
					//  System.out.println("liveData01 url : "+ select_url);
					
					// 서버로 전송할 객체 생성
					Map<String, String> requestBody = new LinkedHashMap<>();
					requestBody.put("query", query);
					requestBody.put("serverip", serverip);
					//  System.out.println("liveData01 requestBody : "+ requestBody);
					
					// 요청 헤더 설정
					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);
					
					// HttpEntity 생성
					HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
					
					// post 요청 보내기
					String select_url_resp = restTemplate.postForObject(select_url, requestEntity, String.class);
					
					//  System.out.print("select_url_resp"+ select_url_resp);
					
					// 응답 데이터를 클라이언트에 반환
					return select_url_resp;
					
					
					
				} catch (Exception e) {
					//  System.out.println("Read Query Error");
					e.printStackTrace();
					return ""; // 예외가 발생하면 빈 문자열을 반환하도록 수정
				}
			
		}
		
		
		
		
		
		
		/* ↓↓↓↓↓↓↓↓ 프로시저와 쿼리문 받으면 수정해야 함 ↓↓↓↓↓↓↓↓↓ */
		
		
		
		// 라인차트 
		@PostMapping("/sendLineQuery")
		@ResponseBody
		public String lineChartData(
				@RequestBody  String req
				) {
			
//			System.out.println("라인차트 req: " + req);
			
			// MappingJackson2HttpMessageConverter 추가
//			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			
			
			// JSON 문자열을 파싱하여 필요한 변수에 할당
//			JSONObject jsonObject = new JSONObject(req);
//		    String serverip = jsonObject.getString("serverip");
//		    String query = jsonObject.getString("query");
//		    System.out.println("serverip : "+ serverip);
//		    System.out.println("query : "+ query);
			
//		    String url = "http://172.16.103.34:8988/fnvr/request/query/select"; // 외부 RESTful API의 URL select
	       
		    
	       
	       //서버로 전송할 객체 생성
//	       Map<String, String> requestBody = new LinkedHashMap<>();
//	       requestBody.put("serverip", serverip);
//	       requestBody.put("query", query);
//	       System.out.println("requestBody : "+ requestBody);

	       // 요청 헤더 설정
//	       HttpHeaders headers = new HttpHeaders();
//	       headers.setContentType(MediaType.APPLICATION_JSON);

	       // HttpEntity 생성
//	       HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

	       // post 요청 보내기
//	       String sendLineQuery_resp = restTemplate.postForObject(url, requestEntity, String.class);
	       
	       
//	       System.out.print("sendLineQuery_resp"+ sendLineQuery_resp);

	       // 응답 데이터를 클라이언트에 반환
//	       return sendLineQuery_resp;
	       return req;
			
			

		}
		
		
		
			
			
		
		
		
		
		//게이트 현황(왼쪽)
		@PostMapping("/gateLiveList")
		@ResponseBody
		public String gateLiveListData(
				@RequestBody  String req
				) {
			
//			System.out.println("req: " + req);
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			
			
			// JSON 문자열을 파싱하여 필요한 변수에 할당
			JSONObject jsonObject = new JSONObject(req);
		    String serverip = jsonObject.getString("serverip");
		    String query = jsonObject.getString("query");
//		    System.out.println("serverip : "+ serverip);
//		    System.out.println("query : "+ query);
//			
		    String url = "http://172.16.103.34:8988/fnvr/request/query/select"; // 외부 RESTful API의 URL select
	       
		    
	       
	       //서버로 전송할 객체 생성
	       Map<String, String> requestBody = new LinkedHashMap<>();
	       requestBody.put("serverip", serverip);
	       requestBody.put("query", query);
//	       System.out.println("requestBody : "+ requestBody);

	       // 요청 헤더 설정
	       HttpHeaders headers = new HttpHeaders();
	       headers.setContentType(MediaType.APPLICATION_JSON);

	       // HttpEntity 생성
	       HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

	       // post 요청 보내기
	       String gateLiveList_resp = restTemplate.postForObject(url, requestEntity, String.class);
	       
	       
	       System.out.print("gateLiveList_resp"+ gateLiveList_resp);

	       // 응답 데이터를 클라이언트에 반환
	       return gateLiveList_resp;

		}
		
		
		
		
		
		

		//카메라 개수 (왼쪽)
		@PostMapping("/cameraCount")
		@ResponseBody
		public String cameraCountData(
				@RequestBody  String req
				) {
			
			System.out.println("req: " + req);
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

			
			
			// JSON 문자열을 파싱하여 필요한 변수에 할당
			JSONObject jsonObject = new JSONObject(req);
		    String serverip = jsonObject.getString("serverip");
		    String query = jsonObject.getString("query");
		    System.out.println("serverip : "+ serverip);
		    System.out.println("query : "+ query);
			
		    String url = "http://172.16.103.34:8988/fnvr/request/query/select"; // 외부 RESTful API의 URL select
	       
		    
	       
	       //서버로 전송할 객체 생성
	       Map<String, String> requestBody = new LinkedHashMap<>();
	       requestBody.put("serverip", serverip);
	       requestBody.put("query", query);
	       System.out.println("requestBody : "+ requestBody);

	       // 요청 헤더 설정
	       HttpHeaders headers = new HttpHeaders();
	       headers.setContentType(MediaType.APPLICATION_JSON);

	       // HttpEntity 생성
	       HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

	       // post 요청 보내기
	       String cameraCount_resp = restTemplate.postForObject(url, requestEntity, String.class);
	       System.out.print("cameraCount_resp"+ cameraCount_resp);
	       
	       

	       // 응답 데이터를 클라이언트에 반환
	       return cameraCount_resp;

		}
		
		
		
		
		//카메라 ip(왼쪽)
		@PostMapping("/cameraIpList")
		@ResponseBody
		public String cameraIpListData(
				@RequestBody  String req
				) {
			
//			System.out.println("req: " + req);
			
			// MappingJackson2HttpMessageConverter 추가
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			
			
			
			// JSON 문자열을 파싱하여 필요한 변수에 할당
			JSONObject jsonObject = new JSONObject(req);
			String serverip = jsonObject.getString("serverip");
			String query = jsonObject.getString("query");
//			System.out.println("serverip : "+ serverip);
//			System.out.println("query : "+ query);
			
		    String url = "http://172.16.103.34:8988/fnvr/request/query/select"; // 외부 RESTful API의 URL select
			
			
			
			//서버로 전송할 객체 생성
			Map<String, String> requestBody = new LinkedHashMap<>();
			requestBody.put("serverip", serverip);
			requestBody.put("query", query);
//			System.out.println("requestBody : "+ requestBody);
			
			// 요청 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			
			// HttpEntity 생성
			HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);
			
			// post 요청 보내기
			String cameraIpList_resp = restTemplate.postForObject(url, requestEntity, String.class);
			
			
			System.out.print("cameraIpList_resp"+ cameraIpList_resp);
			
			// 응답 데이터를 클라이언트에 반환
			return cameraIpList_resp;
			
		}
	
	
	
	
	
	
	
}
