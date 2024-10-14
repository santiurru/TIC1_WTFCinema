package Services;

import Entities.Customer;
import Entities.WebUser;
import Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServices {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer registerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> findByUser(WebUser user) {
        return customerRepository.findByUser(user);
    }
}
