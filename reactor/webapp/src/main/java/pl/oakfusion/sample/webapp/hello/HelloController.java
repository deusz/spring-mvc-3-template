package pl.oakfusion.sample.webapp.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oakfusion.sample.core.HelloEntity;
import pl.oakfusion.sample.core.HelloService;

import java.util.List;

@Controller
public class HelloController {

	private final HelloService service;

	@Autowired
	public HelloController(HelloService service) {
		this.service = service;
	}

	@RequestMapping("/")
	public String getHello() {
		List<HelloEntity> all = service.findAll();
		return "hello";
	}
}
