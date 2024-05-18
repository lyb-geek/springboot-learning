package com.github.lybgeek.thirdparty.test.repository;


import com.github.lybgeek.thirdparty.repository.ThirdpartyRepository;
import org.springframework.stereotype.Repository;

@Repository
public class CustomRepository extends ThirdpartyRepository {

    @Override
    public String getThirdparty() {
        return "Hello Custom Repository";
    }
}
