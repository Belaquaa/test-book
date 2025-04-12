package belaquaa.crudpr.dto;

import lombok.Data;

@Data
public class BookRequest {
    private String vendorCode;
    private String title;
    private Integer year;
    private String brand;
    private Integer stock;
    private Double price;
}