package com.finance;
import com.finance.dto.request.CustomerRequest;
import com.finance.dto.response.CustomerResponse;
import com.finance.entity.Customer;
import com.finance.mapper.CustomerMapper;
import com.finance.repository.CustomerRepository;
import com.finance.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {
 @Mock CustomerRepository repository;
 @Mock CustomerMapper mapper;
 @InjectMocks CustomerServiceImpl service;

 @Test void createCustomerPersistsCustomer(){
  Customer saved=new Customer();
  saved.setId(1L); saved.setFullName("Test User");
  saved.setEmail("test@example.com"); saved.setPhone("9999999999");
  when(repository.save(any(Customer.class))).thenReturn(saved);
  when(mapper.toResponse(saved)).thenReturn(new CustomerResponse(
   1L,"Test User","test@example.com","9999999999","Test address",LocalDateTime.now(),LocalDateTime.now()));
  var response=service.create(new CustomerRequest("Test User","test@example.com","9999999999","Test address"));
  assertEquals(1L,response.id());
  verify(repository).save(any(Customer.class));
 }
}
