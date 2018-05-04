package pl.wavesoftware.test.mapper;

import pl.wavesoftware.test.entity.Pet;
import pl.wavesoftware.test.jpa.PetJPA;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
public interface MapperFacade {
  PetJPA map(Pet pet);
  Pet map(PetJPA jpa);
}
