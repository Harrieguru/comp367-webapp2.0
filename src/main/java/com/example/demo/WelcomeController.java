package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalTime;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String welcome(@RequestParam(value = "name", defaultValue = "Harriet") String name, Model model) {
        LocalTime currentTime = LocalTime.now();
        String greeting = getGreeting(currentTime);

        model.addAttribute("name", name);
        model.addAttribute("greeting", greeting);

        return "welcome";
    }

    private String getGreeting(LocalTime time) {
        int hour = time.getHour();

        if (hour >= 6 && hour < 12) {
            return "Good morning";
        } else if (hour >= 12 && hour < 18) {
            return "Good afternoon";
        } else {
            return "Good evening";
        }
    }
}
