package belaquaa.crudpr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookResponse {
    private Long id;
    private String vendorCode;
    private String title;
    private Integer year;
    private String brand;
    private Integer stock;
    private Double price;
}