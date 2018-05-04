package pl.wavesoftware.test.mapper;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.utils.mapstruct.jpa.AbstractCompositeContextMapping;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.Mapping;
import pl.wavesoftware.utils.mapstruct.jpa.MappingProvider;

import javax.inject.Inject;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@RequiredArgsConstructor(onConstructor = @__(@Inject))
final class PetMappingProvider implements MappingProvider<Pet, PetJPA, CompositeContext> {

  private final PetMapper petMapper;

  @Override
  public Mapping<pl.wavesoftware.test.entity.Pet,pl.wavesoftware.test.jpa.PetJPA,CompositeContext> provide() {
    return AbstractCompositeContextMapping.mapperFor(
      Pet.class, PetJPA.class,
      petMapper::updateFromPet
    );
  }
}
