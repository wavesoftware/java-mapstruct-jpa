package pl.wavesoftware.test.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Getter
@Setter
@NoArgsConstructor
public class Owner extends AbstractEntity {
  private String name;
  private List<Pet> pets = new ArrayList<>();
}
