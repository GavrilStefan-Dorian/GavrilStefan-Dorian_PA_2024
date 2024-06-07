package com.example.ProjectJava.controllers;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/pages")
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

// @GetMapping("/account/login")
// public String loginPage() {
//  return "login";
// }
//
// @GetMapping("/account/register")
// public String registerPage() {
//  return "register";
// }


 @GetMapping("/question")
 public String questionPage(@RequestParam Long quizId, @RequestParam int questionIndex, Model model) {
  model.addAttribute("quizId", quizId);
  model.addAttribute("questionIndex", questionIndex);
  return "question";
 }

 @GetMapping("/final_question")
 public String finalQuestionPage() {
  return "final_question";
 }

 @GetMapping("/domains")
 public String domainsPage() {
  return "domains";
 }

 @GetMapping("/scoreboard")
 public String scoreboardPage() {
  return "clasament_sport";
 }

 @GetMapping("/login")
 public String loginPage() {
  return "login";
 }

 @GetMapping("/register")
 public String registerPage() {
  return "register";
 }

 @GetMapping("/start")
 public String startPage() {
  return "start";
 }


}
