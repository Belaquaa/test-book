package belaquaa.crudpr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class BookRequest {

    @NotBlank
    private String vendorCode;

    @NotBlank
    private String title;

    @PositiveOrZero
    private Integer year;

    private String brand;

    @PositiveOrZero
    private Integer stock;

    @Positive
    private Double price;
}