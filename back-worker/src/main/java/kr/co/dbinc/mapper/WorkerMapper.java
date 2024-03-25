package kr.co.dbinc.mapper;

import java.util.List;
import org.springframework.stereotype.Repository;
import kr.co.dbinc.model.WorkerVO;

@Repository
public interface WorkerMapper {
	List<WorkerVO> selectWorkerList();
	
	List<WorkerVO> selectWorkerByNickname(String nickname);
	
	WorkerVO selectWorkerByUsername(String username);
	
	int insertWorker(WorkerVO workerVO);
	
	int updateWorker(WorkerVO workerVO);
	
	int deleteWorkerByUsername(String username);
}
