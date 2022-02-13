package MapTraveler.develop.Service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Repository.MapRepository;

@ExtendWith(MockitoExtension.class)
class MapServiceImplTest {

	@Mock
	MapRepository mapRepository;
	
	@InjectMocks
	MapServiceImpl mapService;

	@Test
	void FindAll() {
		List<Map> maps = new ArrayList<>() {
			{
				add(new Map());
				add(new Map());
				add(new Map());
			}
		};
		when(mapRepository.findAll()).thenReturn(maps);
		List<Map> returnedMaps = mapService.findAll();
		assertThat(returnedMaps).hasSize(3);
		assertThat(returnedMaps).isNotNull();
		verify(mapRepository).findAll();
	}

}
