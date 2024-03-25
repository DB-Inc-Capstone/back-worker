package kr.co.dbinc.serviceImpl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.mapper.WorkerMapper;
import kr.co.dbinc.model.WorkerVO;

@Service
public class WorkerServiceImpl implements WorkerService {

	private final SqlSession sqlSession;
	
	public WorkerServiceImpl(SqlSession ss) {
		this.sqlSession = ss;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkerVO> selectWorkerList() {
		WorkerMapper wm = this.sqlSession.getMapper(WorkerMapper.class);
		return wm.selectWorkerList();
	}

	@Transactional(readOnly = true)
	@Override
	public List<WorkerVO> selectWorkerByNickname(String nickname) {
		WorkerMapper wm = this.sqlSession.getMapper(WorkerMapper.class);
		return wm.selectWorkerByNickname(nickname);
	}

	@Transactional(readOnly = true)
	@Override
	public WorkerVO selectWorkerByUsername(String username) {
		WorkerMapper wm = this.sqlSession.getMapper(WorkerMapper.class);
		return wm.selectWorkerByUsername(username);
	}

	@Transactional
	@Override
	public int insertWorker(WorkerVO workerVO) {
		WorkerMapper wm = this.sqlSession.getMapper(WorkerMapper.class);
		return wm.insertWorker(workerVO);
	}

	@Transactional
	@Override
	public int updateWorker(WorkerVO workerVO) {
		WorkerMapper wm = this.sqlSession.getMapper(WorkerMapper.class);
		return wm.updateWorker(workerVO);
	}

	@Transactional
	@Override
	public int deleteWorkerById(String username) {
		WorkerMapper wm = this.sqlSession.getMapper(WorkerMapper.class);
		return wm.deleteWorkerByUsername(username);
	}

}
