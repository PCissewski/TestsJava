import cissewski.entity.Mage;
import cissewski.controller.MageController;
import cissewski.repository.MageRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class MageControllerTest {
    private MageController controller;

    @Mock
    MageRepository source;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        controller = new MageController(source);
    }

    @Test
    public void shouldReturnBadRequestWhenDeleteNotExisting() {
        doThrow(new IllegalArgumentException()).when(source).delete("notExistMage");
        assertThat(controller.delete("notExistMage")).isEqualTo("bad request");
    }

    @Test
    public void shouldReturnDoneWhenDeleteExisting() {
        Mage mage = Mage.builder().name("ExistMage").level(7).build();
        doThrow(new IllegalArgumentException()).when(source).save(mage);
        doNothing().when(source).delete("ExistMage");
        assertThat(controller.delete("ExistMage")).isEqualTo("done");
    }

    @Test
    public void shouldReturnNotFoundWhenObjectNotExist() {
        when(source.find("notExistMage")).thenReturn(Optional.empty());
        assertThat(controller.find("notExistMage")).isEqualTo("not found");
    }

    @Test
    public void shouldReturnFoundWhenObjectAsStringWhenExist() {
        Mage mage = Mage.builder().name("testMage").level(7).build();
        Optional<Mage> expectedMage = Optional.of(mage);
        when(source.find("testMage")).thenReturn(expectedMage);
        controller.save(mage.getName(), mage.getLevel());
        assertThat(controller.find(mage.getName())).isEqualTo(expectedMage.toString());
    }

    @Test
    public void shouldReturnBadRequestWhenSaveAlreadyExist() {
        Mage mage = Mage.builder().name("name").level(1).build();
        doThrow(new IllegalArgumentException()).when(source).save(mage);
        controller.save("name", 1);
        assertThat(controller.save("name", 1)).isEqualTo("bad request");
    }

    @Test
    public void shouldReturnDoneWhenSaveNewObject() {
        Mage mage = Mage.builder().name("name").level(1).build();
        doNothing().when(source).save(mage);
        assertThat(controller.save("name", 1)).isEqualTo("done");
    }

    @Test
    public void shouldCallRepoMethodWhenSaveNewObject() {
        Mage mage = Mage.builder().name("name").level(1).build();

        controller.save(mage.getName(), mage.getLevel());
        verify(source).save((eq(mage)));
    }

}