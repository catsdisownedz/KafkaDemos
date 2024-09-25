package org.example.demokafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DemoKafkaProducerApplication {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@PostMapping("/send")
	public String sendMessage(@RequestParam("username") String username, Model model) {
		kafkaTemplate.send("usernames-topic", username);
		model.addAttribute("message", "Email has been sent to you");
		return "index";
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoKafkaProducerApplication.class, args);
	}
}
