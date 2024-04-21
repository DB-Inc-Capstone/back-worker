package kr.co.dbinc.api;

import java.util.List;

import kr.co.dbinc.dto.WorkerDTO;
import kr.co.dbinc.model.WorkerVO;

public interface WorkerService {
	
	List<WorkerDTO> selectWorkerByUsername(String username, String sort);
	
	List<WorkerDTO> selectWorkerByUsername(String username);
	
	List<WorkerDTO> selectWorkerByNickname(String nickname, String sort);
	
	List<WorkerDTO> selectWorkerByNickname(String nickname);
	
	List<WorkerDTO> selectWorkerByPhoneNumber(String phoneNumber);
	
	List<WorkerDTO> selectWorkerList();
	
	
	boolean loginWorker(WorkerDTO workerDTO);
	
	
	int insertWorker(WorkerDTO workerDTO);
	
	int updateWorker(WorkerDTO workerDTO);
	
	int deleteWorkerById(Long id);
	
	int deleteWorkerByUsername(String username);
}
