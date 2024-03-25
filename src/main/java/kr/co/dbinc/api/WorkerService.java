package kr.co.dbinc.api;

import java.util.List;
import kr.co.dbinc.model.WorkerVO;

public interface WorkerService {
	List<WorkerVO> selectWorkerList();
	
	List<WorkerVO> selectWorkerByNickname(String nickname);
	
	WorkerVO selectWorkerByUsername(String username);
	
	int insertWorker(WorkerVO workerVO);
	
	int updateWorker(WorkerVO workerVO);
	
	int deleteWorkerById(String id);
}
