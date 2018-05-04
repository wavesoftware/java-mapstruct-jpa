package pl.wavesoftware.test.jpa;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;
import javax.persistence.Entity;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
public class PetJPA extends AbstractRecord {
  private String name;
  @Nullable
  private OwnerJPA owner;
  @Nullable
  private ToyJPA toy;
}
