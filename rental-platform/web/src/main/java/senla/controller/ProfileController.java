package senla.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.senla.aop.MeasureExecutionTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senla.dto.ProfileDto;
import senla.service.ProfileService;

import java.util.List;

@RestController
@RequestMapping("/profiles")
@RequiredArgsConstructor
@Slf4j
@MeasureExecutionTime
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ProfileDto> createProfile(@RequestBody @Valid ProfileDto profileDto) {
        log.info("Создание нового профиля с данными: {}", profileDto);
        ProfileDto profile = profileService.create(profileDto);
        return ResponseEntity.status(201).body(profile);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProfileDto>> getAllProfiles() {
        log.info("Запрос на получение всех профилей");
        List<ProfileDto> profiles = profileService.getAll();
        return ResponseEntity.ok(profiles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ProfileDto> getProfile(@PathVariable("id") Integer id) {
        log.info("Запрос на получение профиля с ID: {}", id);
        ProfileDto profile = profileService.getById(id);
        return ResponseEntity.ok(profile);
    }

    @PutMapping("/{id}")
    @PreAuthorize("#profileDto.userId == authentication.principal.id")
    public ResponseEntity<String> updateProfile(@PathVariable("id") Integer id, @RequestBody @Valid ProfileDto profileDto) {
        log.info("Обновление профиля с ID: {} с новыми данными: {}", id, profileDto);
        profileService.updateById(id, profileDto);
        return ResponseEntity.ok("Профиль успешно обновлен");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@profileServiceImpl.getById(#id).userId == authentication.principal.id")
    public ResponseEntity<String> deleteProfile(@PathVariable("id") Integer id) {
        log.info("Удаление профиля с ID: {}", id);
        profileService.deleteById(id);
        return ResponseEntity.ok("Профиль успешно удален");
    }
}