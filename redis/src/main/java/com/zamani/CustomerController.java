package com.zamani;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @PostMapping
    public Customer add(@RequestBody Customer customer) {
        return repository.save(customer);
    }

    @GetMapping("/{id}")
    public Customer findById(@PathVariable("id") Long id) {
        Optional<Customer> optCustomer = repository.findById(id);
        return optCustomer.orElse(null);
    }

}
