package com.sstapels.jfall.acdemo;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@Controller
public class Application extends WebSecurityConfigurerAdapter {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests()
          .antMatchers("/").permitAll()
          .anyRequest()
          .authenticated()
        .and()
          .oauth2Login()
          .defaultSuccessUrl("/user", true)
        .and()
          .logout()
          .logoutSuccessUrl("/");
  }

  /**
   * Custom success page
   * @param model
   * @param authentication
   * @return
   */
  @GetMapping("/user")
  public String getLoginInfo(Model model, OAuth2AuthenticationToken authentication) {

    Map<String, Object> userAttributes = authentication.getPrincipal().getAttributes();

    model.addAttribute("name", userAttributes.get("name"));
    
    return "user-page";
  }

}
