package com.bryam.springcloud.msvc.items.controllers;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bryam.springcloud.msvc.items.models.Item;
import com.bryam.springcloud.msvc.items.services.ItemService;

@RestController
public class ItemController {

  private final ItemService itemService;

  public ItemController(@Qualifier("itemServiceWebClient") ItemService itemService) {
    this.itemService = itemService;
  }

  @GetMapping
  public List<Item> getAll() {
    return itemService.findAll();
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> details(@PathVariable Long id) {
    Optional<Item> itemOptional = itemService.findById(id);

    if (itemOptional.isPresent()) {
      return ResponseEntity.ok(itemOptional.get());
    }

    return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(
            Collections.singletonMap(
                "message",
                "No existe el producto en el microservicio msvc-products"));
  }
}
