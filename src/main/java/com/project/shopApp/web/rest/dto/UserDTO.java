package com.project.shopApp.web.rest.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.shopApp.domain.Role;
import com.project.shopApp.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO representing a user, with only the public attributes.
 */
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @JsonProperty("username")
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @JsonProperty("password")
    private String password;

    @Size(max = 30)
    private String firstName;

    @Size(max = 30)
    private String lastName;

    @Email
    @Size(min = 5, max = 256)
    private String email;

    @JsonProperty("isActivated")
    private boolean activated;

    @JsonProperty("image_url")
    private String imageUrl;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();

    @JsonProperty("roleId")
    private long role;
}
