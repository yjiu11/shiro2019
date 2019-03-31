package com.yjiu.shiro;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

@SpringBootApplication
public class App
{
  public static void main(String[] args)
  {
    SpringApplication.run(App.class, args);
  }
  @Bean
  public Converter<String, Date> addNewConvert() {
      return new Converter<String, Date>() {
          @Override
          public Date convert(String source) {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
              Date date = null;
              try {
                  date = sdf.parse( source);
              } catch (Exception e) {
                  e.printStackTrace();
              }
              return date;
          }
      };
  }
}