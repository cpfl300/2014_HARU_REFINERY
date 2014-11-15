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
	public long add(Hotissue hotissue) {
		
		long id = 0;
		Hotissue tmpHotissue = null;
		
		try {
			tmpHotissue = hotissueDao.getByName(hotissue.getName());
			id = tmpHotissue.getId();
			
		} catch (EmptyResultDataAccessException e) {
			id = hotissueDao.add(hotissue);
			
		}
		
		return id;
			
	}

	@Transactional
	public int delete(long id) {
		int delResult = 0;
		
		try {			
			delResult = hotissueDao.delete(id);
			
		} catch (DataIntegrityViolationException e) {
			// do-nothing
		}
		
		return delResult;
		
	}

	
}
