package org.gamma.buenosayres.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/familias")
public class FamiliaViewController {
	@GetMapping
	public String get()
	{
		return "familias/familias";
	}
}
