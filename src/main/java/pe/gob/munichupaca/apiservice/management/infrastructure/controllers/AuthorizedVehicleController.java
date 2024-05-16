package pe.gob.munichupaca.apiservice.management.infrastructure.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.gob.munichupaca.apiservice.management.domain.models.AuthorizedVehicle;
import pe.gob.munichupaca.apiservice.management.domain.services.AuthorizedVehicleService;
import pe.gob.munichupaca.apiservice.management.infrastructure.dto.request.AuthorizedVehicleRequest;
import pe.gob.munichupaca.apiservice.management.infrastructure.dto.response.AuthorizedVehicleResponse;
import pe.gob.munichupaca.apiservice.shared.domain.exceptions.ResourceNotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/authorized-vehicle", produces = "application/json")
@Tag(name = "Authorized Vehicles", description = "The Authorized Vehicles API")
public class AuthorizedVehicleController {

    private final AuthorizedVehicleService authorizedVehicleService;
    private final ModelMapper modelMapper;

    @Transactional
    @PostMapping("/check/license-plate")
    public ResponseEntity<Boolean> checkByLicensePlate(@RequestBody String licensePlate) {
        boolean existsLicensePlate = authorizedVehicleService.checkByLicensePlate(licensePlate);

        return ResponseEntity.status(HttpStatus.OK).body(existsLicensePlate);
    }
    @Transactional
    @PostMapping("/check/document-number")
    public ResponseEntity<Boolean> checkByDocumentNumber(@RequestBody String documentNumber) {
        boolean existsDocumentNumber = authorizedVehicleService.checkByDocumentNumber(documentNumber);

        return ResponseEntity.status(HttpStatus.OK).body(existsDocumentNumber);
    }
    @Transactional(readOnly = true)
    @GetMapping("/all/license-plates")
    public ResponseEntity<List<String>> getAllLicensePlates() {
        List<String> plates = authorizedVehicleService.getAllLicensePlates();

        return ResponseEntity.status(HttpStatus.OK).body(plates);
    }
    @Transactional
    @PostMapping("/by/license-plate")
    public ResponseEntity<AuthorizedVehicleResponse> getByLicensePlate(@RequestBody String licensePlate) {
        AuthorizedVehicle authorizedVehicle = authorizedVehicleService.getAuthorizedVehicleByLicensePlate(licensePlate)
                .orElseThrow(() -> new ResourceNotFoundException("Authorized Vehicle not found with plate: " + licensePlate));
        AuthorizedVehicleResponse authorizedVehicleResponse = modelMapper.map(authorizedVehicle, AuthorizedVehicleResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(authorizedVehicleResponse);
    }
    @Transactional
    @PostMapping("/batch")
    public ResponseEntity<Boolean> createBatch(@Valid @RequestBody List<AuthorizedVehicle> authorizedVehicleRequests) {
        Boolean created = authorizedVehicleService.createBatch(authorizedVehicleRequests);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @Transactional
    @PostMapping
    public ResponseEntity<AuthorizedVehicleResponse> create(@Valid @RequestBody AuthorizedVehicleRequest authorizedVehicleRequest) {
        Long id = authorizedVehicleService.createAuthorizedVehicle(authorizedVehicleRequest);
        AuthorizedVehicle authorizedVehicleCreated = authorizedVehicleService.getAuthorizedVehicleById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorized Vehicle not found with id: " + id));
        AuthorizedVehicleResponse authorizedVehicleResponse = modelMapper.map(authorizedVehicleCreated, AuthorizedVehicleResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(authorizedVehicleResponse);
    }
    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity<AuthorizedVehicleResponse> update(@PathVariable Long id, @Valid @RequestBody AuthorizedVehicleRequest authorizedVehicleRequest) {
        AuthorizedVehicle authorizedVehicleUpdated = authorizedVehicleService.updateAuthorizedVehicle(id, authorizedVehicleRequest);
        AuthorizedVehicleResponse authorizedVehicleResponse = modelMapper.map(authorizedVehicleUpdated, AuthorizedVehicleResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(authorizedVehicleResponse);
    }
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorizedVehicleService.deleteAuthorizedVehicle(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
