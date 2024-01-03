package com.project.shopApp.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank(message = "name is required")
    @Size(max = 50, min = 5, message = "name must be between 5 to 50 characters")
    private String name;

    private String thumbnail;

    @Size(min = 5, max = 256, message = "decription must be between 5 to 256 characters")
    private String decription;

    @Min(value = 0, message = "price must be greater than equal to 0")
    @Max(value = 1000000000, message = "Price must be less than or equal to 1,000,000,000")
    private Float price;

    @JsonProperty("category_id")
    private Long categoryId;
}
