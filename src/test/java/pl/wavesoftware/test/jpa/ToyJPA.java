package pl.wavesoftware.test.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ToyJPA extends AbstractRecord {
  private String name;
}
