package refinery.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import refinery.dao.HotissueDao;
import refinery.model.Hotissue;

@Service
public class HotissueService {
	
	@Autowired
	private HotissueDao hotissueDao;
	
	@Transactional
	public int add(Hotissue hotissue) {
		
		int id = hotissue.hashCode();
		hotissue.setId(id);
		
		try {
			hotissueDao.get(id);
			
		} catch (EmptyResultDataAccessException e) {
			hotissueDao.add(hotissue);

		}
		
		return id;
			
	}

	@Transactional
	public int delete(int id) {
			
		try {		
			
			return hotissueDao.delete(id);
			
		} catch (DataIntegrityViolationException e) {
			// do-nothing
			return 0;
		}

	}

	
}
