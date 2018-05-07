package pl.wavesoftware.test.mapper;

import lombok.RequiredArgsConstructor;
import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;
import pl.wavesoftware.utils.mapstruct.jpa.CompositeContext;
import pl.wavesoftware.utils.mapstruct.jpa.MapStructContextProvider;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
@RequiredArgsConstructor
final class MapperFacadeImpl implements MapperFacade {

  private final PetMapper petMapper;
  private final MapStructContextProvider<CompositeContext> contextProvider;

  @Override
  public PetJPA map(Pet pet) {
    return petMapper.map(pet, contextProvider.createNewContext());
  }

  @Override
  public Pet map(PetJPA jpa) {
    return petMapper.map(jpa, contextProvider.createNewContext());
  }
}
