package com.project.shopApp.web.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
public class CategoryDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Size(min = 5, max = 256, message = "category name must be between 5 to 256 character")
    @NotEmpty
    private String name;
}
