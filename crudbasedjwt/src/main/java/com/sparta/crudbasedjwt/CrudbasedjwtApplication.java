package com.sparta.crudbasedjwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //이거 왜 필요하지?? JPA 상속 !!
@SpringBootApplication //하위패키지 빈등록 및 관리 !
public class CrudbasedjwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrudbasedjwtApplication.class, args);
    }

}
