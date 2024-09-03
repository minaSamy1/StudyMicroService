package com.example.ProductService.controller;


import com.example.ProductService.model.ProductRequest;
import com.example.ProductService.model.ProductResponse;
import com.example.ProductService.repository.ProductRepository;
import com.example.ProductService.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductServiceImpl service;

    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest request) {

        Long id = service.addProduct(request);
        return new ResponseEntity<>(id, HttpStatus.CREATED);

    }


    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable long id) {
        ProductResponse p = service.getProductById(id);
        return new ResponseEntity<>(p, HttpStatus.OK);


    }


    @PutMapping("/reduceQuantity/{id}")
    public ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") long productId,
            @RequestParam long quantity
    ) {

        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>");
        service.reduceQuantity(productId, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
