package org.restapi.springrestapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hc")
public class HealthCheckController {
	@GetMapping
	public ResponseEntity<?> getStatus() {
		return ResponseEntity.ok()
			.body("hello");
	}

}
