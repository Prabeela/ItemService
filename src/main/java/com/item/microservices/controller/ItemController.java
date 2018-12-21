package com.item.microservices.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ItemController {
	
	@Autowired
	ItemRepository itemRepository;
	
    @GetMapping("/service2/items")
    public List<Item> items() {
        return itemRepository.findAll();
    }

    @GetMapping("/service2/item/{id}")
    public Item customer(@PathVariable("id") String id) {
        return itemRepository.findOne(id);
    }

    @PostMapping("/service2/item")
    public ResponseEntity<?> add(@RequestBody Item item) {
    	Item _item = itemRepository.save(item);
        assert _item != null;

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/" + _item.getId())
                .buildAndExpand().toUri());

       
       
        return new ResponseEntity<>(_item, httpHeaders, HttpStatus.CREATED);
    }
}

