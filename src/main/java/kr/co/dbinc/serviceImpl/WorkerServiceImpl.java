package kr.co.dbinc.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import kr.co.dbinc.api.WorkerService;
import kr.co.dbinc.dto.WorkerDTO;
import kr.co.dbinc.mapper.WorkerMapper;
import kr.co.dbinc.model.WorkerVO;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
public class WorkerServiceImpl implements WorkerService {

	private final SqlSession sqlSession;
	private final WorkerMapper workerMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	public WorkerServiceImpl(SqlSession ss) {
		this.sqlSession = ss;
		workerMapper = this.sqlSession.getMapper(WorkerMapper.class);
		passwordEncoder = getPasswordEncoder();
	}
	
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(12);
	}
	
	
	@Override
	public List<WorkerDTO> selectWorkerByUsername(String username, String sort) {
		
		List<WorkerDTO> workerList = this.selectWorkerByUsername(username);
		
		// 정렬 옵션에 따른 결과물 출력 변환
		if(!ObjectUtils.isEmpty(sort) && sort.equals("desc")) {
			Collections.sort(workerList, new Comparator<WorkerDTO>() {
				@Override
				public int compare(WorkerDTO worker1, WorkerDTO worker2) {
					return worker2.getUsername().compareTo(worker1.getUsername());
				}
			});
		} else {
			Collections.sort(workerList, new Comparator<WorkerDTO>() {
				@Override
				public int compare(WorkerDTO worker1, WorkerDTO worker2) {
					return worker1.getUsername().compareTo(worker2.getUsername());
				}
			});
		}
		
		return workerList;
		
	}
	
	@Override
	public List<WorkerDTO> selectWorkerByNickname(String nickname, String sort) {
		
		List<WorkerDTO> workerList = this.selectWorkerByNickname(nickname);
		
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
		
		return workerList;
		
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkerDTO> selectWorkerByUsername(String username) {

		List<WorkerVO> workerVOs = workerMapper.selectWorkerByUsername(username);
		List<WorkerDTO> workerDTOs = new ArrayList<WorkerDTO>();
		
		for(WorkerVO workerVO : workerVOs) {
			WorkerDTO workerDTO = WorkerDTO.builder()
					.id(workerVO.getId())
					.username(workerVO.getUsername())
					.nickname(workerVO.getNickname())
					.email(workerVO.getEmail())
					.phoneNumber(workerVO.getPhone_number())
					.build();
			
			workerDTOs.add(workerDTO);
		}
		
		return workerDTOs;
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkerDTO> selectWorkerByNickname(String nickname) {
		
		List<WorkerVO> workerVOs = workerMapper.selectWorkerByNickname(nickname);
		List<WorkerDTO> workerDTOs = new ArrayList<WorkerDTO>();
		
		for(WorkerVO workerVO : workerVOs) {
			WorkerDTO workerDTO = WorkerDTO.builder()
					.id(workerVO.getId())
					.username(workerVO.getUsername())
					.nickname(workerVO.getNickname())
					.email(workerVO.getEmail())
					.phoneNumber(workerVO.getPhone_number())
					.build();
			
			workerDTOs.add(workerDTO);
		}
		
		return workerDTOs;
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkerDTO> selectWorkerByPhoneNumber(String phoneNumber) {
		
		List<WorkerVO> workerVOs = workerMapper.selectWorkerByPhoneNumber(phoneNumber);
		List<WorkerDTO> workerDTOs = new ArrayList<WorkerDTO>();
		
		// pk 와 username 만 알려줌
		for(WorkerVO workerVO : workerVOs) {
			WorkerDTO workerDTO = WorkerDTO.builder()
					.id(workerVO.getId())
					.username(workerVO.getUsername())
					.build();
			
			workerDTOs.add(workerDTO);
		}
		
		return workerDTOs; 
	}
	
	
	@Transactional(readOnly = true)
	@Override
	public List<WorkerDTO> selectWorkerList() {
		
		List<WorkerVO> workerVOs = workerMapper.selectWorkerList();
		List<WorkerDTO> workerDTOs = new ArrayList<WorkerDTO>();
		
		for(WorkerVO workerVO : workerVOs) {
			WorkerDTO workerDTO = WorkerDTO.builder()
					.id(workerVO.getId())
					.username(workerVO.getUsername())
					.nickname(workerVO.getNickname())
					.email(workerVO.getEmail())
					.phoneNumber(workerVO.getPhone_number())
					.build();
			
			workerDTOs.add(workerDTO);
		}
		
		return workerDTOs; 
	}
	
	
	@Override
	public boolean loginWorker(WorkerDTO workerDTO) {
		
		// 형식 맞지 않는다면 실패
		if(ObjectUtils.isEmpty(workerDTO.getUsername()) || ObjectUtils.isEmpty(workerDTO.getPassword())) {
			return false;
		}
		
		List<WorkerVO> workerVOs = workerMapper.selectWorkerByUsername(workerDTO.getUsername());
		
		// 일치하는 아이디가 없어서 실패
		if(workerVOs.size() == 0) return false;
		
		// 검색된 아이디 가져오기
		WorkerVO searchedWorker = workerVOs.get(0);

		// 비밀번호 확인하기
		if(passwordEncoder.matches(workerDTO.getPassword(), searchedWorker.getPassword())) {
			return true;
		} else {
			return false;			
		}
	}
	
	
	
	@Transactional
	@Override
	public int insertWorker(WorkerDTO workerDTO) {
		
		WorkerVO workerVO = WorkerVO.builder().username(workerDTO.getUsername())
					.password(passwordEncoder.encode(workerDTO.getPassword()))
					.nickname(workerDTO.getNickname())
					.phone_number(workerDTO.getPhoneNumber())
					.email(workerDTO.getEmail())
					.build();
		
		return workerMapper.insertWorker(workerVO);
	}

	@Transactional
	@Override
	public int updateWorker(WorkerDTO workerDTO) {
		
		WorkerVO workerVO = WorkerVO.builder().username(workerDTO.getUsername())
				  .password(workerDTO.getPassword())
				  .nickname(workerDTO.getNickname())
				  .phone_number(workerDTO.getPhoneNumber())
				  .email(workerDTO.getEmail())
				  .build();
		
		return workerMapper.updateWorker(workerVO);
	}
	
	@Transactional
	@Override
	public int updatePasswordOfWorker(WorkerDTO workerDTO) {
		WorkerVO workerVO = WorkerVO.builder().username(workerDTO.getUsername())
				.password(passwordEncoder.encode(workerDTO.getPassword()))
				.build();
		
		return workerMapper.updatePasswordOfWorker(workerVO);
	}

	@Transactional
	@Override
	public int deleteWorkerByUsername(String username) {
		
		return workerMapper.deleteWorkerByUsername(username);
	}
	
	@Transactional
	@Override
	public int deleteWorkerById(Long id) {
		
		return workerMapper.deleteWorkerById(id);
	}

}
