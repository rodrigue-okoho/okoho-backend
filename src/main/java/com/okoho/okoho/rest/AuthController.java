package com.okoho.okoho.rest;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.okoho.okoho.rest.errors.TypeAccountExeption;
import com.okoho.okoho.security.jwt.AuthTokenFilter;
import com.okoho.okoho.security.jwt.JwtUtils;
import com.okoho.okoho.security.services.UserDetailsImpl;
import com.okoho.okoho.service.UserAccountService;
import com.okoho.okoho.service.dto.JwtRequest;
import com.okoho.okoho.service.dto.JwtResponse;
import com.okoho.okoho.service.dto.RegisterRequest;



@RestController
@CrossOrigin
public class AuthController {


    @Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private AuthenticationManagerBuilder authenticationManagerBuilder;

	@Autowired
	private UserAccountService userServiceInterface;
	@Autowired
	JwtUtils jwtUtils;
	@PostMapping("/auth/signin")
	public ResponseEntity<JwtResponse> authenticateUser(@Validated @RequestBody JwtRequest loginRequest) {
		System.out.println("******************************************");
		System.out.println(loginRequest);
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.add("Value token", "Bearer " + jwt);
		return new ResponseEntity<>(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles),httpHeaders, HttpStatus.OK);
	}

	@PostMapping("/auth/signup")
	public ResponseEntity<?> registerUser(@Validated @RequestBody RegisterRequest registerRequest) {
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
				if (registerRequest.getUser_type() == null) {
                throw new TypeAccountExeption(new Exception("pb"));
        }
		
		var account=userServiceInterface.register(registerRequest);
		return new ResponseEntity<>(account,httpHeaders, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(),httpHeaders, HttpStatus.BAD_GATEWAY);
		}
	
	}
	@GetMapping("/auth/forgotpassword/{email}")
    public ResponseEntity<?> resetPassword(@PathVariable("email") String email) {
        var existingItemOptional = userServiceInterface.resetpassword(email);
        return ResponseEntity.ok(existingItemOptional);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}