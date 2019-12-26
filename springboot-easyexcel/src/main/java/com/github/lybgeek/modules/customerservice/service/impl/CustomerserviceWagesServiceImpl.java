package com.github.lybgeek.modules.customerservice.service.impl;


import com.github.lybgeek.modules.customerservice.converter.CustomerserviceWagesConverter;
import com.github.lybgeek.modules.customerservice.dto.CustomerserviceWagesDTO;
import com.github.lybgeek.modules.customerservice.entity.CustomerserviceWages;
import com.github.lybgeek.modules.customerservice.service.CustomerserviceWagesService;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CustomerserviceWagesServiceImpl  implements CustomerserviceWagesService {


  @Autowired
  private CustomerserviceWagesConverter customerserviceWagesConverter;


  @Override
  public boolean saveCustomerserviceWages(List<CustomerserviceWagesDTO> customerserviceWagesDTOS) {
    List<CustomerserviceWages> customerserviceWages = customerserviceWagesConverter.convertDTOList2DOList(customerserviceWagesDTOS);
    System.out.println(customerserviceWages);

    boolean isSuccess = false;
    return isSuccess;
  }

  @Override
  public List<CustomerserviceWagesDTO> listCustomerserviceWages() {
    List<CustomerserviceWages> customerserviceWages = new ArrayList<>();

    for(int i = 0; i < 3; i ++){
      CustomerserviceWages wages = CustomerserviceWages.builder().wages(new BigDecimal(i+20)).wagesId(Long.valueOf(i))
                                   .accumulationFundCost(new BigDecimal(i+30)).company("lyb-geek").continueEducationCost(new BigDecimal(i+40))
                                   .educationCost(new BigDecimal(i+40)).identityCard("1111111"+i).income(new BigDecimal(i+50))
                                   .loanCost(new BigDecimal(i + 60)).loseWorkCost(new BigDecimal(i + 70)).medicalCost(new BigDecimal(i + 80))
                                   .name("张三"+i).otherCost(new BigDecimal(i + 90)).pensionCost(new BigDecimal(i + 100))
                                   .phone("12232343"+i).premiumCost(new BigDecimal(i + 101)).rentCost(new BigDecimal(i + 102)).tax(new BigDecimal(i + 103)).build();
      customerserviceWages.add(wages);
    }
    return customerserviceWagesConverter.convertDOList2DTOList(customerserviceWages);
  }


}
