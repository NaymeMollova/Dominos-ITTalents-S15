package ittalents.dominos.model.DTOs;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDTO {

    private String msg;
    private int status;
    private LocalDateTime time;
}
