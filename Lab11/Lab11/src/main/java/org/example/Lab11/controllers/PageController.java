package org.example.Lab11.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
// @GetMapping(value = "/page")
// public String demoPage() {
// return "demo"; //demo.html must exist in resources/templates
// }

 @GetMapping(value = "/page")
 public ModelAndView demo() {
  ModelAndView modelAndView = new ModelAndView();
  modelAndView.setViewName("demo");
  modelAndView.addObject("user", "Duke");
  return modelAndView;
 }

 @GetMapping("/account/login")
 public String loginPage() {
  return "login";
 }

 @GetMapping("/account/register")
 public String registerPage() {
  return "register";
 }
}
