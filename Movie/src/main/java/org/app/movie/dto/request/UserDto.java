package org.app.movie.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {

    @Size(min = 2, max = 30)
    String name;
    @Size(min = 2, max = 30)
    String surname;
    @Email
    String email;
    @Size(min = 8, max = 15)
    @Pattern(regexp = ".*\\d.*", message = "Password must contain at least one digit")
    String password;
}
