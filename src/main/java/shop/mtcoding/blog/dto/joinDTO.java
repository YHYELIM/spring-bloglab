package shop.mtcoding.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class JoinDTO {
    private String username;
    private String encPassword;
    private String email;

}
