package kr.co.dbinc.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.model.ResponseDTO;
import kr.co.dbinc.model.WorkerDTO;
import kr.co.dbinc.model.WorkerVO;

@CrossOrigin
@RestController
@RequestMapping("/worker")
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
		
		// property 검증 진행
		if(ObjectUtils.isEmpty(workerVO.getEmail()) ||
		   ObjectUtils.isEmpty(workerVO.getNickname()) ||
		   ObjectUtils.isEmpty(workerVO.getPassword()) ||
		   ObjectUtils.isEmpty(workerVO.getPhoneNumber()) ||
		   ObjectUtils.isEmpty(workerVO.getUsername())) {
			responseDTO.message = "회원가입 형식을 맞춰 보내주세요.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
		// 패스워드 정규성 검증
		
		// 전화번호 정규성 검증
				
		// 이메일 정규성 검증
		
		// 중복되는 id 가 있다면, 409 Conflict 반환 진행.
		WorkerVO worker = workerService.selectWorkerByUsername(workerVO.getUsername());
		if (worker != null) {
			responseDTO.message = "중복된 아이디입니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
		}

		Integer result = workerService.insertWorker(workerVO);

		if(result != 0) {
			responseDTO.message = "회원가입에 성공하였습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			responseDTO.message = "서버 오류로 회원가입에 실패하였습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);			
		}
	}
	
	/**
	 * @param keyword
	 * @param col
	 * @param sort
	 * @return 검색 결과 반환
	 */
	@GetMapping
	public ResponseEntity<ResponseDTO> searchWorker(@RequestBody(required=false) WorkerDTO workerDTO,
													@Valid @RequestParam(value="col") String col, 
													@Valid @RequestParam(value="sort", required=false) String sort) {
		ResponseDTO responseDTO = new ResponseDTO();
		List<WorkerDTO> workerList = new ArrayList<WorkerDTO>();
		
		// 검색 옵션에 따른 객체 탐색
		if(!ObjectUtils.isEmpty(col) && col.equals("username")) {
			String username;
			
			if(!ObjectUtils.isEmpty(workerDTO) && !ObjectUtils.isEmpty(workerDTO.getUsername())) {
				username = workerDTO.getUsername();
			} else {
				responseDTO.message = "검색 옵션(username)이 유효하지 않습니다.";
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			}
			
			WorkerVO workerVO = workerService.selectWorkerByUsername(username);
			
			if(workerVO != null) {
				// DTO 형식으로 반환 (username 이기 때문에 세부 정보 조회까지 커버 가능)
				WorkerDTO workerDTO_ = WorkerDTO.builder()
											   .id(workerVO.getId())
											   .nickname(workerVO.getNickname())
											   /*
											   .username(workerVO.getUsername())
											   .phoneNumber(workerVO.getPhoneNumber())
											   .email(workerVO.getEmail())
											   */
											   .build();
				workerList.add(workerDTO_);
			}
			
		} else if(!ObjectUtils.isEmpty(col) && col.equals("nickname")) {
			String nickname;
			
			if(!ObjectUtils.isEmpty(workerDTO) && !ObjectUtils.isEmpty(workerDTO.getNickname())) {
				nickname = workerDTO.getNickname();
			} else {
				responseDTO.message = "검색 옵션(nickname)이 유효하지 않습니다.";
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			}
			
			List<WorkerVO> workerVOList_ = workerService.selectWorkerByNickname(nickname);
			
			for(WorkerVO worker: workerVOList_) {
				workerList.add(WorkerDTO.builder()
										.id(worker.getId())
										.nickname(worker.getNickname())
										.build());
			}
			
		} else {
			responseDTO.message = "검색 옵션(col)이 유효하지 않습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
		// 정렬 옵션에 따른 결과물 출력 변환
		if(!ObjectUtils.isEmpty(sort) && sort.equals("desc")) {
			Collections.sort(workerList, new Comparator<WorkerDTO>() {
				@Override
				public int compare(WorkerDTO worker1, WorkerDTO worker2) {
					return worker2.getNickname().compareTo(worker1.getNickname());
				}
			});
		} else {
			Collections.sort(workerList, new Comparator<WorkerDTO>() {
				@Override
				public int compare(WorkerDTO worker1, WorkerDTO worker2) {
					return worker1.getNickname().compareTo(worker2.getNickname());
				}
			});
		}

		responseDTO.message = "성공적으로 불러왔습니다.";
		responseDTO.workerList = workerList;
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	
	
	
	/**
	 * 아래 코드는 테스트 용도입니다.
	 *
	 */
	
	
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
