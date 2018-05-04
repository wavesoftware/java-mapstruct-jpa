package pl.wavesoftware.test.jpa;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Getter
@Setter
abstract class AbstractRecord {
  private Long id;
}
