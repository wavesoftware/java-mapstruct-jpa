package pl.wavesoftware.utils.mapstruct.jpa;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.utils.mapstruct.jpa.Mappings.MappingsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszyński</a>
 * @since 2018-05-03
 */
@RequiredArgsConstructor
final class MappingsBuilderImpl<C> implements MappingsBuilder<C> {
  private final Class<C> contextClass;
  private final List<Mapping<?,?,?>> mappings = new ArrayList<>();

  @Override
  public void addMapping(Mapping<?,?,?> mapping) {
    mappings.add(mapping);
  }

  @Override
  public Mappings<C> build() {
    return new MappingsImpl<>(contextClass, mappings);
  }

}
