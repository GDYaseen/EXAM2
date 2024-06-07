    package com.example.demo3.controllers;
    
    import com.example.demo3.controllers.auth.AuthenticationRequest;
    import com.example.demo3.controllers.auth.AuthenticationResponse;
    import com.example.demo3.controllers.auth.RegisterRequest;
    import com.example.demo3.models.Post;
    import com.example.demo3.models.Employee;
    import com.example.demo3.services.AuthenticationService;
    import com.example.demo3.services.EmployeeService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import com.example.demo3.services.*;
    import lombok.RequiredArgsConstructor;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.security.core.Authentication;
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.servlet.ModelAndView;

    @Controller
    @CrossOrigin
    @RequiredArgsConstructor
    @RequestMapping("/api/auth")
    public class AuthController {

        private final AuthenticationService service;

        @PostMapping("/register")
        @ResponseBody
        public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request){
            System.out.println("*********\t\tAccessing /api/auth/register");
            return ResponseEntity.ok(service.register(request));
        }
        @PostMapping("/login")
        public String login(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {
            System.out.println("*********\t\tAccessing /api/auth/login");
            AuthenticationRequest request = new AuthenticationRequest(email, password);
            AuthenticationResponse authResponse = service.login(request);
            Cookie cookie = new Cookie("jwt",authResponse.getToken());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
            // service.login(request)// this returns the jwt token;
            return "redirect:/students";
        }

        @GetMapping("/login")
        public String loginPage(Model mav){
            mav.addAttribute("testing", "hello");
            return "login";
        }
    }
