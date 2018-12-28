package com.item.microservices.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.item.microservices.domain.Item;
import com.item.microservices.repository.ItemRepository;


@RestController
@RefreshScope
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	private Logger logger = LoggerFactory.getLogger(getClass());
	
    @GetMapping("/service2/items")
    public List<Item> items() {
    	logger.debug("Retrieving items from item repository ");
        return itemRepository.findAll();
    }

    @GetMapping("/service2/itemById/{id}")
    public Item itemById(@PathVariable("id") String id) {
    	logger.debug("Retrieving item by ID from item repository ::"+id);
        return itemRepository.findOne(id);
    }
    
    @GetMapping("/service2/item/{name}")
    public Item itemByName(@PathVariable("name") String name) {
    	
    	logger.debug("Fetching item details from the Item database for itemname ::"+name);
        return itemRepository.findByName(name);
    }

    @PostMapping("/service2/item")
    public ResponseEntity<?> add(@RequestBody Item item) {
    	Item _item = itemRepository.save(item);
        assert _item != null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + _item.getId())
                .buildAndExpand().toUri());

        logger.debug("Adding new items to item table ::"+_item.getId());
       
        return new ResponseEntity<>(_item, httpHeaders, HttpStatus.CREATED);
    }
}

