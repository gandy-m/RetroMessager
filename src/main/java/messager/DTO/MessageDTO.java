package messager.DTO;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MessageDTO {


    private Long id;


    private String text;


    private UserDTO sender;


    private UserDTO recipient;


    @JsonFormat(pattern = "MM-dd")
    private LocalDate date;


    @JsonFormat(pattern = "HH:mm")
    private LocalTime time;
}
