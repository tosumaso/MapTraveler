package MapTraveler.develop.Service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MapTraveler.develop.Entity.Map;
import MapTraveler.develop.Repository.MapRepository;

@Service
@Transactional
public class MapServiceImpl implements MapService{

	@Autowired
	MapRepository mapRepository;
	
	@Override
	public List<Map> findAll() {
		List<Map> markers = mapRepository.findAll();
		return markers;
	}

}
