package pl.wavesoftware.test.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Getter
@Setter
@NoArgsConstructor
public class PetJPA extends AbstractRecord {
  private String name;
  private OwnerJPA owner;
}
