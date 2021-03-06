package pl.wavesoftware.test.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class AbstractEntity {
  private Object reference;
}
