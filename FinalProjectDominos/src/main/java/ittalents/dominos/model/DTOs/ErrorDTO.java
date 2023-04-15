package ittalents.dominos.model.DTOs;

import lombok.*;

import java.time.LocalDateTime;

<<<<<<< HEAD
@Getter
=======
>>>>>>> f91c6bcfd99d704e7e8ff0fdbaf8b25e3ba8d261
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {

    private String msg;
    private int status;
    private LocalDateTime time;
}
