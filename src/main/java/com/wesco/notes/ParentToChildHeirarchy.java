package com.wesco.notes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class ParentToChildHeirarchy {

	public static void main(String[] args) throws JsonProcessingException {
		Map<Integer, Integer> childToParent = new HashMap<>();
		childToParent.put(1, 1);
		childToParent.put(2, 1);
		childToParent.put(3, 1);
		childToParent.put(4, 2);
		childToParent.put(5, 2);
		
		
		
		Map<Integer, List<Integer>> parentToChildren = childToParent.entrySet().stream()
		        .collect(Collectors.groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
		
		Map<Integer, Company> idToCompany = childToParent.keySet().stream()
		        .map(integer -> new Company(integer,  null))
		        .collect(Collectors.toMap(Company::getId, company -> company));
		
		idToCompany.values().forEach(company -> {
		    List<Integer> childrenIds = parentToChildren.get(company.getId());

		    if(childrenIds != null){
		        List<Company> children = childrenIds.stream()
		                .filter(childId -> childId != company.getId()) // a company can't be a parent of itself
		                .map(idToCompany::get)
		                .collect(Collectors.toList());
		        company.setChildren(children);
		    }
		});
		
		List<Company> companiesHeads = childToParent.entrySet().stream()
		        .filter(childIdToParentId -> childIdToParentId.getKey().equals(childIdToParentId.getValue()))
		        .map(Map.Entry::getKey)
		        .map(idToCompany::get)
		        .collect(Collectors.toList());
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		System.out.println(mapper.writeValueAsString(companiesHeads));

	}

}
