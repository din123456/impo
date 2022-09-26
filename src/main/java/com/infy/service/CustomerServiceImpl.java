package com.infy.service;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infy.exception.InfyBankException;
import com.infy.repository.CustomerRepository;
import com.infy.entity.Customer;

import com.infy.dto.CustomerDTO;

@Service(value = "customerService")
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public CustomerDTO getCustomer(Integer customerId) throws InfyBankException {
		Optional<Customer> optional = customerRepository.findById(customerId);
		Customer customer = optional.orElseThrow(() -> new InfyBankException("Service.CUSTOMER_NOT_FOUND"));
		CustomerDTO customer2 = new CustomerDTO();
		customer2.setCustomerId(customer.getCustomerId());
		customer2.setDateOfBirth(customer.getDateOfBirth());
		customer2.setEmailId(customer.getEmailId());
		customer2.setName(customer.getName());
		return customer2;
	}

	@Override
	public List<CustomerDTO> getAllCustomers() throws InfyBankException {
		Iterable<Customer> customers = customerRepository.findAll();
		List<CustomerDTO> customers2 = new ArrayList<>();
		customers.forEach(customer -> {
			CustomerDTO cust = new CustomerDTO();
			cust.setCustomerId(customer.getCustomerId());
			cust.setDateOfBirth(customer.getDateOfBirth());
			cust.setEmailId(customer.getEmailId());
			cust.setName(customer.getName());
			customers2.add(cust);
		});
		if (customers2.isEmpty())
			throw new InfyBankException("Service.CUSTOMERS_NOT_FOUND");
		return customers2;
	}

	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws InfyBankException {
		// TODO Auto-generated method stub
		Optional<Customer> customer=customerRepository.findById(customerDTO.getCustomerId());
		if(customer.isPresent()) {
			throw new InfyBankException("Service.VALID_CUSTOMER");
		}
		else {
			Customer customer1=new Customer();
			
			customer1.setName(customerDTO.getName());
			customer1.setEmailId(customerDTO.getEmailId());
			customer1.setDateOfBirth(customerDTO.getDateOfBirth());
			
			customerRepository.save(customer1);
			
			return customer1.getCustomerId();
			
		}
		
		
	}

	@Override
	public void updateCustomer(Integer customerId, String emailId) throws InfyBankException {
		// TODO Auto-generated method stub
		Optional<Customer> optional=customerRepository.findById(customerId);
		Customer customer=optional.orElseThrow(()-> new  InfyBankException ("Service.CUSTOMERS_NOT_FOUND"));
		customer.setEmailId(emailId);
		
	}

	

}
