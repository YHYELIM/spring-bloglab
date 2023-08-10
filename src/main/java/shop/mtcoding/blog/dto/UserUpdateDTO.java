package shop.mtcoding.blog.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

//디티오는 공유해서 쓰지 않고 새로 만들어서 쓴다
@Setter
@Getter
public class UserUpdateDTO {

    private String username;
    private String password;
    private String email;

}
