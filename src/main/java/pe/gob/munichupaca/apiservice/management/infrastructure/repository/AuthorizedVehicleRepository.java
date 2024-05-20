package pe.gob.munichupaca.apiservice.management.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.gob.munichupaca.apiservice.management.domain.models.entity.AuthorizedVehicle;

import java.util.Optional;

@Repository
public interface AuthorizedVehicleRepository extends JpaRepository<AuthorizedVehicle, Long> {
    Optional<AuthorizedVehicle> findByLicensePlate(String plate);
    boolean existsByLicensePlate(String licensePlate);
}
