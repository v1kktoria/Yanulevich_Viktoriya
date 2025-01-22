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
import senla.model.Property;
import senla.service.PropertyService;

@Controller
@RequestMapping("/properties")
@RequiredArgsConstructor
public class PropertyController {

    private final PropertyService propertyService;

    @PostMapping
    public String createProperty(@ModelAttribute @Valid Property property) {
        propertyService.create(property);
        return "redirect:/properties";
    }

    @GetMapping
    public String getAllProperties(Model model) {
        model.addAttribute("properties", propertyService.getAll());
        return "properties";
    }

    @GetMapping("/property")
    public String getProperty(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("property", propertyService.getById(id));
        return "properties";
    }

    @PutMapping("/{id}")
    public String updateProperty(@PathVariable("id") Integer id, @ModelAttribute @Valid Property property) {
        propertyService.updateById(id, property);
        return "redirect:/properties";
    }

    @DeleteMapping("/{id}")
    public String deleteProperty(@PathVariable("id") Integer id) {
        propertyService.deleteById(id);
        return "redirect:/properties";
    }
}
