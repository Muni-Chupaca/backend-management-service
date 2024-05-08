package pe.gob.munichupaca.apiservice.management.domain.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "authorized_vehicles")
public class AuthorizedVehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "license_plate", unique = true, nullable = false, length = 10)
    private String licensePlate;

    @Column(name = "brand", nullable = false, length = 25)
    private String brand;

    @Column(name = "model", nullable = false, length = 25)
    private String model;

    @Column(name = "status", nullable = false, length = 50)
    private String status;

    @Column(name = "modality", nullable = false, length = 50)
    private String modality;

    @Column(name = "circulation", nullable = false, length = 50)
    private String circulation;

    @Column(name = "route", nullable = false, length = 50)
    private String route;

    @Column(name = "issue_date", nullable = false, length = 10)
    private String issueDate;

    @Column(name = "expiration_date", nullable = false, length = 10)
    private String expirationDate;

    @Column(name = "fleet_number", nullable = false, length = 25)
    private String fleetNumber;

    @Column(name = "circulation_card", nullable = false, length = 25)
    private String circulationCard;

    @Column(name = "owner_name", nullable = false, length = 50)
    private String ownerName;

    @Column(name = "document_number", unique = true, nullable = false, length = 25)
    private String documentNumber;

    @Column(name = "affiliated_company", nullable = false, length = 50)
    private String affiliatedCompany;
}
