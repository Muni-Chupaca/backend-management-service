package pe.gob.munichupaca.apiservice.management.application.implement;

import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pe.gob.munichupaca.apiservice.management.domain.models.AuthorizedVehicle;
import pe.gob.munichupaca.apiservice.management.domain.services.AuthorizedVehicleService;
import pe.gob.munichupaca.apiservice.management.infrastructure.repositories.AuthorizedVehicleRepository;
import pe.gob.munichupaca.apiservice.management.infrastructure.dto.request.AuthorizedVehicleRequest;
import pe.gob.munichupaca.apiservice.shared.domain.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorizedVehicleImpl implements AuthorizedVehicleService {

    private final AuthorizedVehicleRepository authorizedVehicleRepository;
    private final ModelMapper modelMapper;

    @Override
    public Long createAuthorizedVehicle(AuthorizedVehicleRequest authorizedVehicleRequest) {
        if (authorizedVehicleRepository.existsByLicensePlate(authorizedVehicleRequest.getLicensePlate())) {
            throw new ValidationException("License Plate already exists");
        }
        if (authorizedVehicleRepository.existsByDocumentNumber(authorizedVehicleRequest.getDocumentNumber())) {
            throw new ValidationException("Document Number already exists");
        }
        AuthorizedVehicle authorizedVehicle = new AuthorizedVehicle();
        modelMapper.map(authorizedVehicleRequest, authorizedVehicle);

        return authorizedVehicleRepository.save(authorizedVehicle).getId();
    }

    @Override
    public boolean createBatch(List<AuthorizedVehicle> authorizedVehicleRequests) {
        return !authorizedVehicleRepository.saveAll(authorizedVehicleRequests).isEmpty();
    }

    @Override
    public Optional<AuthorizedVehicle> getAuthorizedVehicleById(Long id) {
        return authorizedVehicleRepository.findById(id);
    }

    @Override
    public AuthorizedVehicle updateAuthorizedVehicle(Long id, AuthorizedVehicleRequest authorizedVehicleRequest) {
        AuthorizedVehicle authorizedVehicleToUpdate = authorizedVehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authorized Vehicle not found with id: " + id));
        modelMapper.map(authorizedVehicleRequest, authorizedVehicleToUpdate);

        return authorizedVehicleRepository.save(authorizedVehicleToUpdate);
    }

    @Override
    public void deleteAuthorizedVehicle(Long id) {
        if (!authorizedVehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Authorized Vehicle not found with id: " + id);
        }
        authorizedVehicleRepository.deleteById(id);
    }

    @Override
    public List<String> getAllLicensePlates() {
        return authorizedVehicleRepository.findAll().stream().map(AuthorizedVehicle::getLicensePlate).toList();
    }

    @Override
    public Optional<AuthorizedVehicle> getAuthorizedVehicleByLicensePlate(String plate) {
        return authorizedVehicleRepository.findByLicensePlate(plate);
    }

    @Override
    public boolean checkByDocumentNumber(String documentNumber) {
        return authorizedVehicleRepository.existsByDocumentNumber(documentNumber);
    }

    @Override
    public boolean checkByLicensePlate(String licensePlate) {
        return authorizedVehicleRepository.existsByLicensePlate(licensePlate);
    }
}
