package com.github.lybgeek.modules.customerservice.converter;


import com.github.lybgeek.modules.customerservice.dto.CustomerserviceWagesDTO;
import com.github.lybgeek.modules.customerservice.entity.CustomerserviceWages;
import com.github.lybgeek.modules.customerservice.vo.CustomerserviceWagesVO;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerserviceWagesConverter {

  CustomerserviceWages convertDTO2DO(CustomerserviceWagesDTO customerserviceWagesDTO);

  List<CustomerserviceWages> convertDTOList2DOList(
      List<CustomerserviceWagesDTO> customerserviceWagesDTOS);

  CustomerserviceWagesDTO convertDO2DTO(CustomerserviceWages customerserviceWages);

  List<CustomerserviceWagesDTO> convertDOList2DTOList(
      List<CustomerserviceWages> customerserviceWages);

  CustomerserviceWagesVO convertDTO2VO(CustomerserviceWagesDTO customerserviceWagesDTO);

  List<CustomerserviceWagesVO> convertDTOList2VOList(
      List<CustomerserviceWagesDTO> customerserviceWagesDTOS);

  CustomerserviceWagesDTO convertVO2DTO(CustomerserviceWagesVO customerserviceWagesVO);

  List<CustomerserviceWagesDTO> convertVOList2DTOList(
      List<CustomerserviceWagesVO> customerserviceWagesVOS);
}
