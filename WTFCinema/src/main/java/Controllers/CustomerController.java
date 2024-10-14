package Controllers;

import Entities.Customer;
import Services.CustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerServices customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody  Customer customer) {  //@Valid no anda
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.registerCustomer(customer));
    }
}
