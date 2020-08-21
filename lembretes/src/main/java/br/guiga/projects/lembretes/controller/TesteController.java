package br.guiga.projects.lembretes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TesteController {
	@RequestMapping("/teste")
	@ResponseBody
	public String teste() {
		return "<h1>Estou funcionado de boas</h1>";
	}
}
