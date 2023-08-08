package shop.mtcoding.blog.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyWriteDTO {
    private Integer boardId;// integer로 해야 null 체크가능
    private String comment;

}
