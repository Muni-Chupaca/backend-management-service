package pe.gob.munichupaca.apiservice.management.infrastructure.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class AuthorizedVehicleResponse {

    private Long id;
    private String licensePlate;
    private String brand;
    private String model;
    private String status;
    private String modality;
    private String circulation;
    private String route;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String issueDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String expirationDate;

    private String fleetNumber;
    private String circulationCard;
    private String ownerName;
    private String documentNumber;
    private String affiliatedCompany;
}
