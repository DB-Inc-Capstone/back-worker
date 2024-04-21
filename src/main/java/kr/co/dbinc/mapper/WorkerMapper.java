package kr.co.dbinc.mapper;

import java.util.List;
import org.springframework.stereotype.Repository;

import kr.co.dbinc.model.WorkerVO;

@Repository
public interface WorkerMapper {
	List<WorkerVO> selectWorkerList();
	
	List<WorkerVO> selectWorkerByUsername(String username);
	
	List<WorkerVO> selectWorkerByNickname(String nickname);
	
	int insertWorker(WorkerVO workerVO);
	
	int updateWorker(WorkerVO workerVO);
	
	int deleteWorkerByUsername(String username);
	
	int deleteWorkerById(Long id);
}
