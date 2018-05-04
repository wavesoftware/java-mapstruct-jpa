package pl.wavesoftware.test.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.Nullable;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@Setter
@Getter
@NoArgsConstructor
public class Pet extends AbstractEntity {
  private String name;
  @Nullable
  private Owner owner;
}
