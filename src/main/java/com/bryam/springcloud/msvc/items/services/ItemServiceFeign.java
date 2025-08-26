package com.bryam.springcloud.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.bryam.springcloud.msvc.items.client.ProductFeignClient;
import com.bryam.springcloud.msvc.items.models.Item;

import feign.FeignException;

@Service
public class ItemServiceFeign implements ItemService {
  final private ProductFeignClient productFeignClient;

  public ItemServiceFeign(ProductFeignClient productFeignClient) {
    this.productFeignClient = productFeignClient;
  }

  @Override
  public List<Item> findAll() {
    return productFeignClient
        .findAll()
        .stream()
        .map(product -> new Item(product, new Random().nextInt(10) + 1))
        .collect(Collectors.toList());
  }

  @Override
  public Optional<Item> findById(Long id) {
    try {
      return Optional.ofNullable(new Item(
          productFeignClient.details(id),
          new Random().nextInt(10) + 1));
    } catch (FeignException e) {
      return Optional.empty();
    }
  }
}
