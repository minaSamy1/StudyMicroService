package com.example.ProductService.service;

import com.example.ProductService.Exception.ProductServiceCustomException;
import com.example.ProductService.entity.Product;
import com.example.ProductService.model.ProductRequest;
import com.example.ProductService.model.ProductResponse;
import com.example.ProductService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.beans.BeanUtils.copyProperties;


@Service
public class ProductServiceImpl implements ProductService {


    @Autowired
    private ProductRepository repository;

//
//    @Override
//    public long addProduct(ProductRequest request) {
//        Product p = Product.builder().
//                productName(request.getName())
//                .quantity(request.getQuantity())
//                .price(request.getPrice()).
//                build();
//
//
//        repository.save(p);
//
//        return p.getProductId();
//
//    }

    @Override
    public ProductResponse getProductById(long productId) {
//        Product product
//                = repository.findById(productId)
//                .orElseThrow(
//                        () -> new RuntimeException("Product with given id not found","PRODUCT_NOT_FOUND"));
        Product p = repository.findById(productId).orElseThrow(() -> new ProductServiceCustomException("Product with given id not found", "PRODUCT_NOT_FOUND"));


        System.out.println(" REaduong  " + p.getProductName());
        ProductResponse response = new ProductResponse();
        copyProperties(p, response);
        return response;
    }

    @Override
    public long addProduct(ProductRequest request) {
        Product p = Product.builder().
                productName(request.getName())
                .quantity(request.getQuantity())
                .price(request.getPrice()).
                build();


        repository.save(p);

        return p.getProductId();
    }

    @Override
    public void reduceQuantity(long productId, long quantity) {
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ");
        Product p = repository.findById(productId).orElseThrow(


                () -> new ProductServiceCustomException("Product Not found ", "NOT_FOUND")
        );
        System.out.println(" >>>>>>>>>>>> "+ p.getQuantity());
        if (p.getQuantity() < quantity)
        {

            throw new ProductServiceCustomException(
                    "Product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }

        p.setQuantity(p.getQuantity()-quantity);
        repository.save(p);

    }
}
