package pl.wavesoftware.test.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Setter
@Getter
@Entity
@NoArgsConstructor
public class OwnerJPA extends AbstractRecord {
  private String name;
  private String surname;
  private Set<PetJPA> pets = new HashSet<>();
}
