package com.github.lybgeek.license.aspect;

import com.github.lybgeek.license.annotation.LicenseCheck;
import com.github.lybgeek.config.LicenseProperties;
import com.github.lybgeek.license.util.LicenseUtils;
import lombok.SneakyThrows;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;


@Aspect
public class LicenseCheckAspect {


    @Autowired
    private LicenseProperties licenseProperties;

    @SneakyThrows
    @Around(value = "@annotation(licenseCheck)")
    public Object around(ProceedingJoinPoint pjp, LicenseCheck licenseCheck){
        LicenseUtils.checkLicenseCode(licenseProperties.getCode());
        return pjp.proceed();
    }
}
