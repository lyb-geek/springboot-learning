package com.github.lybgeek.thirdparty.service;


import com.github.lybgeek.thirdparty.repository.ThirdpartyRepository;

public class ThirdpartyService {

    private ThirdpartyRepository thirdpartyRepository;

    public ThirdpartyService(ThirdpartyRepository thirdpartyRepository) {
        this.thirdpartyRepository = thirdpartyRepository;
    }

    public String getThirdparty(){
        return thirdpartyRepository.getThirdparty();
    }
}
