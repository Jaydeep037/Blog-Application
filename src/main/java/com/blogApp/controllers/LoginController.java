package com.blogApp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.exceptions.UsernamePasswordNotFoundException;
import com.blogApp.payload.Genericresponse;
import com.blogApp.payload.JwtAuthRequest;
import com.blogApp.payload.JwtAuthResponse;
import com.blogApp.payload.UserDto;
import com.blogApp.security.JwtTokenHelper;
import com.blogApp.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class LoginController {

	@Autowired
	JwtTokenHelper jwtTokenHelper;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		this.authenticate(request.getUsername(), request.getPassword());

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());

		String token = this.jwtTokenHelper.generateToken(userDetails);

		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);

		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	public void authenticate(String username, String password) throws Exception {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, password);

		try {
			this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
		} catch (Exception e) {
			throw new UsernamePasswordNotFoundException("Username or password not found");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNew(@RequestBody UserDto userDto) {

		UserDto savedUserDto = this.userService.registerNew(userDto);

		return new ResponseEntity<UserDto>(savedUserDto, HttpStatus.CREATED);

	}
	
	
	@PostMapping("/signout")
	  public ResponseEntity<?> logoutUser() {
	    ResponseCookie cookie = jwtTokenHelper.getCleanJwtCookie();
	    System.out.println("cookie" +cookie);
	    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
	        .body(new Genericresponse("You've been signed out!",true));
	  }
}
