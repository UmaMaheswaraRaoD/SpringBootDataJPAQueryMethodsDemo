package com.vlp.restApi.app.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.vlp.restApi.app.entities.NoasVLPInventory;

public interface AnthresholdServiceDao extends CrudRepository<NoasVLPInventory, String>{
	List<NoasVLPInventory> findById(String accessNum);//80012878282
	
}
