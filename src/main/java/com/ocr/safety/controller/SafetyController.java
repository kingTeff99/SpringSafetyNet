package com.ocr.safety.controller;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.ocr.safety.model.AllData;
import com.ocr.safety.model.ChildAlert;
import com.ocr.safety.model.CommunityEmail;
import com.ocr.safety.model.CompletePerson;
import com.ocr.safety.model.Fire;
import com.ocr.safety.model.FireStation;
import com.ocr.safety.model.FireStationPlus;
import com.ocr.safety.model.MedicalRecord;
import com.ocr.safety.model.Person;
import com.ocr.safety.model.dto.FireStationDTO;
import com.ocr.safety.service.FirestationService;
import com.ocr.safety.service.MedicalrecordService;
import com.ocr.safety.service.PersonService;

@RestController
public class SafetyController {
	
	@Autowired
	private FirestationService firestationService;
	
	@Autowired
	private PersonService personService;
	
	@Autowired
	private MedicalrecordService medicalrecordService;
	
	
	@GetMapping(value = "/")
	public  AllData ToutesLesDonnées() {
		
		AllData convertData = null;
		
		try( JsonReader reader = new JsonReader(new InputStreamReader(
				new FileInputStream("/Users/kingteff/Documents/workspace-sts/git/safetynet/src/main/resources/data.json"),"UTF-8"))) {
			
    	    Gson gson = new Gson();
    	    
    	    convertData = gson.fromJson(reader, AllData.class);

    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	}
		return convertData;
	}
	
	//URL1 FONCTIONNE!!!
	@GetMapping(value = "/firestation")
	public FireStationPlus getPersonByItsStationNumber(@RequestParam int stationNumber) {
		
		return firestationService.getPersonFromFireStationArea(stationNumber);
		
	}
	
	//URL2 FONCTIONNE!!!
	@GetMapping(value = "/childAlert")
	public ChildAlert getChildrenByAddress(@RequestParam String address) {
		
		return personService.giveChildAlertByAddress(address);
	}
	
	//URL3 FONCTIONNE!!!
	@GetMapping(value = "/phoneAlert")
	public List<String> getPhoneByItsStationNumber(@RequestParam int firestation) {
			
		return firestationService.getPhoneNumberByFireStationNumber(firestation);
			
	}
	
	//URL4 FONCTIONNE!!!
	@GetMapping(value = "/fire")
	public Fire getmama(@RequestParam String address) {
				
		return firestationService.getPersonsByItsAddress(address);
				
	}
	
	//URL5 FONCTIONNE!!!
	@GetMapping(value = "/flood/stations")
	public Fire getpapa(@RequestParam Integer stations) {
					
		return firestationService.getPersonsByItsStationNumberArea(stations);
					
	}
	
	//URL6 FONCTIONNE!!!
	@GetMapping(value = "/personInfo")
	public List<CompletePerson> getPersonsFromNames(@RequestParam(value = "firstName", required=false) String firstName, @RequestParam(value = "lastName", required=true) String lastName) {
						
		return personService.getCompletePersonsByName(firstName, lastName);
						
	}
		
	//URL7 FONCTIONNE!!!
	@GetMapping(value = "/communityEmail")
	public CommunityEmail getEmailsOfTheCity(@RequestParam String city) {
				
		return personService.getCommunityEmailPersonsByCity(city);
				
	}	
		
	@GetMapping(value = "/person")
	public List<Person> listeDesPersonnes() {
		
		List<List<Person>> ensembleDePerson = null;
		
		List<Person> ens = null;
		
		try(JsonReader reader = new JsonReader(new InputStreamReader(
				new FileInputStream("/Users/kingteff/Documents/workspace-sts/git/safetynet/src/main/resources/data.json"),"UTF-8"))){
	
    	    Gson gson = new Gson();
    	    
    	    Map<String, List<Person>> dataJSON = gson.fromJson(reader, Map.class);
    	    
    	    ensembleDePerson 
    	    = dataJSON.entrySet().stream().filter(s -> s.getKey().contains("persons"))
    	    .map(Map.Entry::getValue).collect(Collectors.toList());
    	    
    	    ens = ensembleDePerson.stream().flatMap(e -> e.stream())
    	    		.collect(Collectors.toList());
    	    
    	} catch (Exception ex) {
    	    ex.printStackTrace();
    	}
		return ens;
	}
	

	@GetMapping(value = "/medicalRecord")
	public List<MedicalRecord> infosMedicales() {
		
		List<List<MedicalRecord>> antecedantsMedicals = null;
		
		List<MedicalRecord> medicalRecords = null;
		
		try(JsonReader reader = new JsonReader(new InputStreamReader(
				new FileInputStream("/Users/kingteff/Documents/workspace-sts/git/safetynet/src/main/resources/data.json"),"UTF-8"))){
			
    	    Gson gson = new Gson();
    	    
    	    Map<String, List<MedicalRecord>> dataJSON = gson.fromJson(reader, Map.class);
    	    
    	    antecedantsMedicals
    	    = dataJSON.entrySet().stream().filter(s -> s.getKey().contains("medicalrecords"))
    	    .map(Map.Entry::getValue).collect(Collectors.toList());
    	    
    	    medicalRecords = antecedantsMedicals.stream().flatMap(e -> e.stream())
    	    		.collect(Collectors.toList());

    	} catch (Exception ex) {
    		
    	    ex.printStackTrace();
    	}
		
		return medicalRecords;
		
	}
	
	
	@PostMapping(value = "/person")
	List<Person> ajouterUnePersonne(@RequestBody Person person) {
		
		List<List<Person>> ensembleDePerson = null;
		
		List<Person> ens = null;
		
		try(JsonReader reader = new JsonReader(new InputStreamReader(
				new FileInputStream("/Users/kingteff/Documents/workspace-sts/git/safetynet/src/main/resources/data.json"),"UTF-8"))){
	
    	    Gson gson = new Gson();
    	    
    	    Map<String, List<Person>> dataJSON = gson.fromJson(reader, Map.class);
    	    
    	    ensembleDePerson 
    	    = dataJSON.entrySet().stream().filter(s -> s.getKey().contains("persons"))
    	    .map(Map.Entry::getValue).collect(Collectors.toList());
    	    
    	    ens = ensembleDePerson.stream().flatMap(e -> e.stream())
    	    		.collect(Collectors.toList());
    	    
    	     ens.add(person);
    	     
    	    
    	} catch (Exception ex) {
    		
    	    ex.printStackTrace();
    	}
		
	     return ens;

	}
	
//	@GetMapping(value = "/firestation")
//	public List<FireStation> listeDesCasernesFiltrees(FireStationDTO input) {
//		
//		List<List<FireStation>> ensembleDesCasernes = null;
//		
//		List<FireStation> fireStations = null;
//
//		try(JsonReader reader = new JsonReader(new InputStreamReader(
//				new FileInputStream("/Users/kingteff/Documents/workspace-sts/git/safetynet/src/main/resources/data.json"),"UTF-8"))){
//			
//    	    Gson gson = new Gson();
//    	    
//    	    Type fireStationType = new TypeToken<HashMap<String, List<FireStation>>>(){}.getType();
//    	    
//    	    HashMap<String, List<FireStation>> dataJSON = gson.fromJson(reader, fireStationType);
//    	    
//    	    ensembleDesCasernes = dataJSON.entrySet().stream().filter(s -> s.getKey().contains("firestations"))
//    	    .map(Map.Entry::getValue).collect(Collectors.toList());
//    	    
//    	    
//    	    fireStations = ensembleDesCasernes.stream().flatMap(e -> e.stream())
//    	    		.collect(Collectors.toList());
//    	    
//    	    if(!input.getAddress().isEmpty()) {
//    	    	
//    	    	fireStations = fireStations.stream()
//    	    	.filter(e -> e.getAddress() == input.getAddress())
//    	    	.collect(Collectors.toList());
// 
////    	    	(input.getStationId() != null) || 
////    	    	 e.getStation() == input.getStationId() ||
//    	    }
//
//    	} catch(Exception ex) {
//    		
//    	    ex.printStackTrace();
//    	}
//		
//		System.out.println(input);
//
//		return fireStations;
//	}
}