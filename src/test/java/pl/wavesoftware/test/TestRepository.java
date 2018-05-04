package pl.wavesoftware.test;

import pl.wavesoftware.test.entity.Owner;
import pl.wavesoftware.test.entity.Pet;

/**
 * @author <a href="mailto:krzysztof.suszynski@wavesoftware.pl">Krzysztof Suszynski</a>
 * @since 04.05.18
 */
final class TestRepository {
  Pet createPetNamedAlice() {
    Pet alice = new Pet();
    alice.setName("Alice");
    Owner owner = new Owner();
    alice.setOwner(owner);

    Pet kitie = new Pet();
    kitie.setName("Kitie");

    owner.getPets().add(alice);
    owner.getPets().add(kitie);
    return alice;
  }
}
