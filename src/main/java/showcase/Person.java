package showcase;

import lombok.*;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
public class Person {

    @Id
    private String id;

    private String firstName;
    private String lastName;


}