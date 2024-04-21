package kr.co.dbinc.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.dto.ResponseDTO;
import kr.co.dbinc.dto.WorkerDTO;

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
	@PostMapping
	public ResponseEntity<ResponseDTO> createWorker(@Valid @RequestBody WorkerDTO workerDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		// property 검증 진행
		if(ObjectUtils.isEmpty(workerDTO.getEmail()) ||
		   ObjectUtils.isEmpty(workerDTO.getNickname()) ||
		   ObjectUtils.isEmpty(workerDTO.getPassword()) ||
		   ObjectUtils.isEmpty(workerDTO.getPhoneNumber()) ||
		   ObjectUtils.isEmpty(workerDTO.getUsername())) {
			responseDTO.message = "회원가입 형식을 맞춰 보내주세요.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
		// 패스워드 정규성 검증
		// todo
		
		// 중복되는 id 가 있다면, 409 Conflict 반환 진행.
		List<WorkerDTO> worker = workerService.selectWorkerByUsername(workerDTO.getUsername());
		
		if (worker.size() > 0) {
			responseDTO.message = "중복된 아이디입니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
		}

		Integer result = workerService.insertWorker(workerDTO);

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
		
		// 검색 옵션에 따른 객체 탐색
		if(!ObjectUtils.isEmpty(col) && col.equals("username")) {
			String username;
					
			if(!ObjectUtils.isEmpty(workerDTO) && !ObjectUtils.isEmpty(workerDTO.getUsername())) {
				username = workerDTO.getUsername();
			} else {
				responseDTO.message = "검색 옵션(username)이 유효하지 않습니다.";
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			}
			
			List<WorkerDTO> workerDTOs = workerService.selectWorkerByUsername(username, sort);
			
			responseDTO.message = "성공적으로 불러왔습니다.";
			responseDTO.workers = workerDTOs;
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
			
		} else if(!ObjectUtils.isEmpty(col) && col.equals("nickname")) {
			String nickname;
			
			if(!ObjectUtils.isEmpty(workerDTO) && !ObjectUtils.isEmpty(workerDTO.getNickname())) {
				nickname = workerDTO.getNickname();
			} else {
				responseDTO.message = "검색 옵션(nickname)이 유효하지 않습니다.";
				return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
			}
			
			List<WorkerDTO> workerDTOs = workerService.selectWorkerByNickname(nickname, sort);
			
			responseDTO.message = "성공적으로 불러왔습니다.";
			responseDTO.workers = workerDTOs;
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);

		} else {
			responseDTO.message = "검색 옵션(col)이 유효하지 않습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	/**
	 * 로그인 함수입니다.
	 * 단순히, 아이디, 비밀번호를 바탕으로 확인합니다.
	 * @param workerDTO
	 * @return
	 */
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> workerLogin(@RequestBody(required=true) WorkerDTO workerDTO) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		boolean result = workerService.loginWorker(workerDTO);
		
		if(result == true) {
			responseDTO.message = "로그인에 성공하였습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			responseDTO.message = "로그인에 실패하였습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	/**
	 * 휴대폰 전화번호로 아이디 찾기 (인증 생략)
	 * @param workerDTO
	 * @return
	 */
	@PostMapping("/findid")
	public ResponseEntity<ResponseDTO> findWorkerId(@RequestBody(required=true) WorkerDTO workerDTO) {
		
		ResponseDTO responseDTO = new ResponseDTO();
		
		// 휴대폰 인증 정보가 없을 시 (SMS 인증 대체)
		if(ObjectUtils.isEmpty(workerDTO.getPhoneNumber())) {
			responseDTO.message = "휴대폰 인증 정보가 필요합니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
		List<WorkerDTO> workerDTOs = workerService.selectWorkerByPhoneNumber(workerDTO.getPhoneNumber());
		
		responseDTO.message = "휴대폰 전화번호가 일치하는 계정의 리스트는 아래와 같습니다.";
		responseDTO.workers = workerDTOs;
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		
	}

	
	/**
	 * 아이디와 휴대폰 전화번호 정보를 바탕으로 계정 확인
	 * @param workerDTO
	 * @return
	 */
	@PostMapping("/valid")
	public ResponseEntity<ResponseDTO> validWorkerId(@RequestBody(required=true) WorkerDTO workerDTO) {
		ResponseDTO responseDTO = new ResponseDTO();
		
		// 휴대폰 인증 정보가 없을 시 (SMS 인증 대체)
		if(ObjectUtils.isEmpty(workerDTO.getUsername()) || ObjectUtils.isEmpty(workerDTO.getPhoneNumber())) {
			responseDTO.message = "계정 아이디 및 휴대폰 인증 정보가 필요합니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
		List<WorkerDTO> workerDTOs = workerService.selectWorkerByUsername(workerDTO.getUsername());
		
		// 일치하는 계정 찾기
		boolean isExist = false;
		
		for(WorkerDTO workerDTO_ : workerDTOs) {
			if(workerDTO_.getPhoneNumber().equals(workerDTO.getPhoneNumber())) {
				isExist = true;
			}
		}
		
		// 결과에 따른 반환 진행
		if(isExist) {
			responseDTO.message = "일치하는 계정이 있습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.OK);
		} else {
			responseDTO.message = "일치하는 계정이 없습니다.";
			return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@PostMapping("/resetpw")
	public ResponseEntity<ResponseDTO> resetWorkerPw(@RequestBody(required=true) WorkerDTO workerDTO) {
		return null;
	}
	
	
	/**
	 * 아래 코드는 테스트 용도입니다.
	 *
	 */
	
	
	/**
	 * @return List<WorkerVO>
	 */
	@GetMapping("/selectWorkerTest")
	public ResponseEntity<List<WorkerDTO>> selectWorkerTest() {
		List<WorkerDTO> workerList = workerService.selectWorkerList();
		return new ResponseEntity<>(workerList, HttpStatus.OK);
	}
	
	/**
	 * @param workerVO
	 * @return 성공 여부 반환
	 */
	@PostMapping("/insertWorkerTest")
	public ResponseEntity<Integer> insertWorkerTest(@Valid @RequestBody WorkerDTO workerDTO) {
		Integer result = workerService.insertWorker(workerDTO);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
