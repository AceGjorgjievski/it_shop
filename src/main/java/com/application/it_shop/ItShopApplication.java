package com.application.it_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
//@ComponentScan({"com.application.it_shop"})
public class ItShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ItShopApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

}

/*
https://setec.mk/index.php?route=product/category&path=10019_10020&mfp=path[10021]
https://setec.mk/index.php?route=product/category&path=10019_10020



kako se vika toj draggable bar za cena?

responsive nav bar: https://www.youtube.com/watch?v=TWiy3dGSmgk
responsive reg & log page

todo:
valid email
valid pass/ strong pass
hover error msg

search by brand/manufacturer

osven list na users
da ima i view na shopping carts?



dynamic call for the brands?..

20min
===============================================

https://www.youtube.com/watch?v=74GFtYRepHY&t=2254s
@20:00 ResponeEntity.ok() ....
===============================================


dockers:
https://hub.docker.com/repositories/ace0fhearts

https://docs.docker.com/get-started/09_image_best/
https://docs.docker.com/get-started/overview/#docker-objects
https://docs.docker.com/engine/reference/builder/#entrypoint

https://www.youtube.com/watch?v=pTFZFxd4hOI&t=1320s

https://www.skillshare.com/en/classes/Full-Stack-Web-Developer-in-2023-Java-Spring-Boot-MySQL-Docker/1879653599/projects?via=search-layout-grid
https://www.skillshare.com/en/classes/Containeriser-une-Application-Spring-Boot-avec-Docker/1079650764/classroom/discussions

errors:
https://stackoverflow.com/questions/53090590/why-do-i-get-linkageerror-when-only-one-version-of-java-seems-to-be-available


docker compose:
https://www.youtube.com/watch?v=2ezNqqaSjq8
https://www.youtube.com/watch?v=3v5VLS-oWKs


microservices:
https://developer.okta.com/blog/2019/05/22/java-microservices-spring-boot-spring-cloud
https://www.youtube.com/watch?v=Z7MAApC4mGw



 */
