package com.example.erp.global.config.argumentresolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


// 파라미터에만 적용
@Target(ElementType.PARAMETER)
// 런타임까지 에노테이션정보가 남아있도록
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
