package com.github.lybgeek.modules.customerservice.service;

import com.github.lybgeek.modules.customerservice.dto.CustomerserviceWagesDTO;
import java.util.List;


public interface CustomerserviceWagesService {

  boolean saveCustomerserviceWages(List<CustomerserviceWagesDTO> customerserviceWagesDTOS);

  List<CustomerserviceWagesDTO> listCustomerserviceWages();

}
