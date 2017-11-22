package info.alex.nagging.learn.lombok.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;

@Data
@Log4j
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String id;
    private String name;
    private String identity;
    private Long salary;
}
