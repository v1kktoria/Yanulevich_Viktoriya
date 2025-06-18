package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import senla.dto.AddressDto;
import senla.service.AddressService;

import java.util.List;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class AddressController {

    private final AddressService addressService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddressDto> createAddress(@RequestBody @Valid AddressDto addressDto) {
        log.info("Создание нового адреса с данными: {}", addressDto);
        AddressDto address = addressService.create(addressDto);
        return ResponseEntity.status(201).body(address);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AddressDto>> getAllAddresses() {
        log.info("Запрос на получение всех адресов");
        List<AddressDto> addresses = addressService.getAll();
        return ResponseEntity.ok(addresses);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<AddressDto> getAddress(@PathVariable("id") Integer id) {
        log.info("Запрос на получение адреса с ID: {}", id);
        AddressDto address = addressService.getById(id);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/{id}")
    @PreAuthorize("@addressSecurityService.hasAccess(authentication, #id)")
    public ResponseEntity<String> updateAddress(@PathVariable("id") Integer id, @RequestBody @Valid AddressDto addressDto) {
        log.info("Обновление адреса с ID: {} с новыми данными: {}", id, addressDto);
        addressService.updateById(id, addressDto);
        return ResponseEntity.ok("Адрес успешно обновлен");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@addressSecurityService.hasAccess(authentication, #id)")
    public ResponseEntity<String> deleteAddress(@PathVariable("id") Integer id) {
        log.info("Удаление адреса с ID: {}", id);
        addressService.deleteById(id);
        return ResponseEntity.ok("Адрес успешно удален");
    }
}
