package com.zamani.controller;

import com.zamani.configurations.security.CustomUserDetailsService;
import com.zamani.configurations.security.IUserRepository;
import com.zamani.configurations.security.JwtUtil;
import com.zamani.entity.MyUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
public class AuthenticationController extends BaseController {

    private AuthenticationManager authenticationManager;
    private CustomUserDetailsService userDetailsService;
    private IUserRepository iUserRepository;
    private JwtUtil jwtUtil;

    public AuthenticationController(AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService, IUserRepository iUserRepository, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.iUserRepository = iUserRepository;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/authenticate")
    @ResponseBody
    public ResponseEntity<MyUser> createAuthenticationToken(@RequestBody Map<String, String> loginInfo, HttpServletRequest request) {
        MyUser myUser = iUserRepository.findByUsernameEquals(loginInfo.get("username"));
        try {
            this.checkUser(myUser);
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(myUser.getUsername(), loginInfo.get("password")));
        } catch (DisabledException e) {
            throw new RuntimeException("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("INVALID_CREDENTIALS", e);
        }

        UserDetails userdetails = userDetailsService.loadUserByUsername(loginInfo.get("username"));
        myUser.setToken(jwtUtil.generateToken(userdetails));
        return ResponseEntity.ok().body(myUser);
    }

    public void checkUser(MyUser myUser) {
        if (myUser == null)
            throw new RuntimeException("نام کاربری یا رمز عبور اشتباه است.");
        if (!myUser.isEnabled())
            throw new RuntimeException("کاربر محترم، حساب کاربری شما غیر فعال شده است.");
        if (!myUser.isAccountNonLocked())
            throw new RuntimeException("کاربر محترم، در اثر 5 بار ورود رمز اشتباه، حساب کاربری شما مسدود شده است.");
    }

}
