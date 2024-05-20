package pe.gob.munichupaca.apiservice.management.infrastructure.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pe.gob.munichupaca.apiservice.management.domain.models.entity.AuthorizedVehicle;
import pe.gob.munichupaca.apiservice.management.domain.services.AuthorizedVehicleService;
import pe.gob.munichupaca.apiservice.management.infrastructure.dto.request.AuthorizedVehicleRequest;
import pe.gob.munichupaca.apiservice.management.infrastructure.dto.response.AuthorizedVehicleResponse;
import pe.gob.munichupaca.apiservice.shared.domain.exceptions.ResourceNotFoundException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/authorized-vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Authorized Vehicles", description = "The Authorized Vehicles API")
public class AuthorizedVehicleController {

    private final AuthorizedVehicleService authorizedVehicleService;
    private final ModelMapper modelMapper;

    @Transactional(readOnly = true)
    @GetMapping(value = "/get-all/license-plates")
    public ResponseEntity<List<String>> getAllByLicensePlates() {
        List<String> plates = authorizedVehicleService.getAllLicensePlates();

        return ResponseEntity.status(HttpStatus.OK).body(plates);
    }
    @Transactional
    @PostMapping(value = "/search-by/license-plate")
    public ResponseEntity<AuthorizedVehicleResponse> searchByLicensePlate(@RequestBody String licensePlate) {
        AuthorizedVehicle authorizedVehicle = authorizedVehicleService.getAuthorizedVehicleByLicensePlate(licensePlate)
                .orElseThrow(() -> new ResourceNotFoundException("Authorized Vehicle not found with plate: " + licensePlate));
        AuthorizedVehicleResponse authorizedVehicleResponse = modelMapper.map(authorizedVehicle, AuthorizedVehicleResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(authorizedVehicleResponse);
    }
    @Transactional
    @PostMapping(value = "/check-by/license-plate")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> checkByLicensePlate(@RequestBody String licensePlate) {
        boolean existsLicensePlate = authorizedVehicleService.checkByLicensePlate(licensePlate);

        return ResponseEntity.status(HttpStatus.OK).body(existsLicensePlate);
    }
    @Transactional
    @PostMapping(value = "/excel-upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Boolean> createExcelUpload(@Valid @RequestBody List<AuthorizedVehicle> authorizedVehicleRequests) {
        Boolean created = authorizedVehicleService.createUpload(authorizedVehicleRequests);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    @Transactional
    @PostMapping(value = "")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorizedVehicleResponse> create(@Valid @RequestBody AuthorizedVehicleRequest authorizedVehicleRequest) {
        Long id = authorizedVehicleService.createAuthorizedVehicle(authorizedVehicleRequest);
        AuthorizedVehicle authorizedVehicleCreated = authorizedVehicleService.getAuthorizedVehicleById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorized Vehicle not found with id: " + id));
        AuthorizedVehicleResponse authorizedVehicleResponse = modelMapper.map(authorizedVehicleCreated, AuthorizedVehicleResponse.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(authorizedVehicleResponse);
    }
    @Transactional
    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorizedVehicleResponse> update(@PathVariable Long id, @Valid @RequestBody AuthorizedVehicleRequest authorizedVehicleRequest) {
        AuthorizedVehicle authorizedVehicleUpdated = authorizedVehicleService.updateAuthorizedVehicle(id, authorizedVehicleRequest);
        AuthorizedVehicleResponse authorizedVehicleResponse = modelMapper.map(authorizedVehicleUpdated, AuthorizedVehicleResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(authorizedVehicleResponse);
    }
    @Transactional
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        authorizedVehicleService.deleteAuthorizedVehicle(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
