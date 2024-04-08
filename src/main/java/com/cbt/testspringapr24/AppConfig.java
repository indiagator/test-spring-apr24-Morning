package com.cbt.testspringapr24;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.util.UUID;

@Configuration
public class AppConfig {

    @Value("${admin.username}")
    private String admin_username;

    @Bean("admin_username")
    public String getAdmin_username() {
        return admin_username;
    }

    @Bean("MyNumBean1")
    @Lazy
    @Scope("prototype")
    public MyNumber getMyNumOne()
    {
        MyNumber myNumber =  new MyNumber();
        myNumber.setNum(76);
        return myNumber;
    }

    @Bean
    @Lazy
    @Scope("prototype")
    public MyNumber getMyNumTwo()
    {
        MyNumber myNumber =  new MyNumber();
        myNumber.setNum(89);
        return myNumber;
    }

  @Bean
  public Wallet wallet()
   {
       Wallet wallet = new Wallet();
       //wallet.setWalletid(String.valueOf(UUID.randomUUID()));
       wallet.setBalance(5000000);
        wallet.setState("VALID");
        return wallet;
   }

    @Bean
    @Scope("prototype")
    Usernamewalletlink usernamewalletlink()
    {
        Usernamewalletlink usernamewalletlink = new Usernamewalletlink();
        usernamewalletlink.setWalletid(String.valueOf(UUID.randomUUID()));

        return usernamewalletlink;
    }

    @Bean
    @Scope("prototype")
    Usertypelink usertypelink()
    {
        Usertypelink usertypelink = new Usertypelink();
        usertypelink.setLinkid(String.valueOf(UUID.randomUUID()));
        return usertypelink;
    }



}
