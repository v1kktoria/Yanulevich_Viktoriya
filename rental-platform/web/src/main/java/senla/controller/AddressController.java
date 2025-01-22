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
import senla.model.Address;
import senla.service.AddressService;

@Controller
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    public String createAddress(@ModelAttribute @Valid Address address) {
        addressService.create(address);
        return "redirect:/addresses";
    }

    @GetMapping
    public String getAllAddresses(Model model) {
        model.addAttribute("addresses", addressService.getAll());
        return "addresses";
    }

    @GetMapping("/address")
    public String getAddress(@RequestParam("id") Integer id, Model model) {
        model.addAttribute("address", addressService.getById(id));
        return "addresses";
    }

    @PutMapping("/{id}")
    public String updateAddress(@PathVariable("id") Integer id, @ModelAttribute @Valid Address address) {
        addressService.updateById(id, address);
        return "redirect:/addresses";
    }

    @DeleteMapping("/{id}")
    public String deleteAddress(@PathVariable("id") Integer id) {
        addressService.deleteById(id);
        return "redirect:/addresses";
    }
}
