package kr.co.dbinc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.boot.jackson.JsonObjectSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.model.ResponseDTO;
import kr.co.dbinc.model.ResponseJson;
import kr.co.dbinc.model.WorkerVO;

@RequestMapping("/worker")
@RestController
public class WorkerController {
	
	private final WorkerService workerService;
	
	public WorkerController(WorkerService ws) {
		this.workerService = ws;
	}
	
	/**
	 * @param workerVO
	 * @return 회원가입 여부
	 */
	@PostMapping()
	public ResponseEntity<ResponseDTO> createWorker(@Valid @RequestBody WorkerVO workerVO) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		WorkerVO worker = workerService.selectWorkerById(workerVO.getId());
		
		// 중복되는 id 가 있다면, 409 Conflict 반환 진행.
		if (worker != null) {
			responseDTO.success = false;
			responseDTO.message = "중복된 아이디입니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
		}

		// 패스워드 정규성 검증
		
		// 전화번호 정규성 검증
		
		// 이메일 정규성 검증
		
		// 계정 상태 초기화 (기본값 설정)
		workerVO.setAcc_status(true);
		
		Integer result = workerService.insertWorker(workerVO);

		if(result != 0) {
			responseDTO.success = true;
			responseDTO.message = "회원가입에 성공하였습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			responseDTO.success = false;
			responseDTO.message = "서버 오류로 회원가입에 실패하였습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);			
		}
	}

	/**
	 * @return List<WorkerVO>
	 */
	@GetMapping("/selectWorkerTest")
	public ResponseEntity<List<WorkerVO>> selectWorkerTest() {
		List<WorkerVO> workerList = workerService.selectWorkerList();
		return new ResponseEntity<>(workerList, HttpStatus.OK);
	}
	
	/**
	 * @param workerVO
	 * @return 성공 여부 반환
	 */
	@PostMapping("/insertWorkerTest")
	public ResponseEntity<Integer> insertWorkerTest(@Valid @RequestBody WorkerVO workerVO) {
		Integer result = workerService.insertWorker(workerVO);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
