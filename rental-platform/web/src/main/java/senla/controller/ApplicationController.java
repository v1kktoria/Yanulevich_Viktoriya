package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import senla.model.Application;
import senla.service.ApplicationService;

@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public String createApplication(@ModelAttribute @Valid Application application) {
        applicationService.create(application);
        return "redirect:/applications";
    }

    @GetMapping
    public String getAllApplications(Model model) {
        model.addAttribute("applications", applicationService.getAll());
        return "applications";
    }

    @GetMapping("/application")
    public String getApplication(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("application", applicationService.getById(id));
        return "applications";
    }

    @PutMapping("/{id}")
    public String updateApplication(@PathVariable("id") Integer id, @ModelAttribute @Valid Application application) {
        applicationService.updateById(id, application);
        return "redirect:/applications";
    }

    @DeleteMapping("/{id}")
    public String deleteApplication(@PathVariable("id") Integer id) {
        applicationService.deleteById(id);
        return "redirect:/applications";
    }
}

