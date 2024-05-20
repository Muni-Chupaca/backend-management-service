package pe.gob.munichupaca.apiservice.management.domain.services;

import pe.gob.munichupaca.apiservice.management.domain.models.entity.AuthorizedVehicle;
import pe.gob.munichupaca.apiservice.management.infrastructure.dto.request.AuthorizedVehicleRequest;

import java.util.List;
import java.util.Optional;

public interface AuthorizedVehicleService {
    Long createAuthorizedVehicle(AuthorizedVehicleRequest authorizedVehicleRequest);
    boolean createUpload(List<AuthorizedVehicle> authorizedVehicleRequests);
    Optional<AuthorizedVehicle> getAuthorizedVehicleById(Long id);
    AuthorizedVehicle updateAuthorizedVehicle(Long id, AuthorizedVehicleRequest authorizedVehicleRequest);
    void deleteAuthorizedVehicle(Long id);
    List<String> getAllLicensePlates();
    Optional<AuthorizedVehicle> getAuthorizedVehicleByLicensePlate(String plate);
    boolean checkByLicensePlate(String licensePlate);
}
