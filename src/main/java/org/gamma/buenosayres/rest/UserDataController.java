package org.gamma.buenosayres.rest;

import org.gamma.buenosayres.exception.ServiceException;
import org.gamma.buenosayres.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/userdata")
public class UserDataController {
	@Autowired
	private UserService userService;
	@GetMapping
	public ResponseEntity<?> get(Authentication authentication)
	{
		try {
			return ResponseEntity.ok(userService.get(authentication));
		} catch (ServiceException e) {
			return ResponseEntity.status(e.getCode()).body(e.getMessage());
		}
	}
}
