package kr.co.dbinc.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.model.ResponseDTO;
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
	 * @param keyword
	 * @param col
	 * @param sort
	 * @return 검색 결과 반환
	 */
	@GetMapping("/{keyword}")
	public ResponseEntity<ResponseDTO> searchWorker(@Valid @PathVariable String keyword, 
													@Valid @RequestParam(value="col") String col, 
													@Valid @RequestParam(value="sort") String sort) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<WorkerVO> workerList = new ArrayList<WorkerVO>();
		
		if(col.equals("id")) {
			WorkerVO worker = workerService.selectWorkerById(keyword);
			if(worker != null) 
				workerList.add(worker);
		} else if(col.equals("name")) {
			List<WorkerVO> workerList_ = workerService.selectWorkerByName(keyword);
			workerList.addAll(workerList_);
		} else {
			responseDTO.success = false;
			responseDTO.message = "검색 옵션(col)이 유효하지 않습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
		if(sort.equals("desc")) {
			Collections.sort(workerList, new Comparator<WorkerVO>() {
				@Override
				public int compare(WorkerVO worker1, WorkerVO worker2) {
					return worker2.getName().compareTo(worker1.getName());
				}
			});
		} else {
			Collections.sort(workerList, new Comparator<WorkerVO>() {
				@Override
				public int compare(WorkerVO worker1, WorkerVO worker2) {
					return worker1.getName().compareTo(worker2.getName());
				}
			});
		}

		responseDTO.success = true;
		responseDTO.message = "성공적으로 불러왔습니다.";
		responseDTO.workerList = workerList;
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
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
