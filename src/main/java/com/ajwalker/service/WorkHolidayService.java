package com.ajwalker.service;

import com.ajwalker.dto.request.WorkHolidayRequestDto;
import com.ajwalker.entity.PersonalDocument;
import com.ajwalker.entity.WorkHoliday;
import com.ajwalker.repository.WorkHolidayRepository;
import com.ajwalker.utility.Enum.holiday.EHolidayState;
import com.ajwalker.utility.Enum.holiday.EHolidayType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkHolidayService {
	
	private final WorkHolidayRepository workHolidayRepository;
	private  final PersonalDocumentService personalDocumentService;
	
	public WorkHoliday findById(Long id) {
		return workHolidayRepository.findById(id).orElse(null);
	}
	
	public Boolean createWorkHoliday(WorkHolidayRequestDto dto, Long userId) {
		
		PersonalDocument personalDocument = personalDocumentService.personalFindById(userId);
		
		WorkHoliday workHoliday = WorkHoliday.builder()
		                                     .userId(userId)
		                                     .beginDate(dto.beginDate()) // Tarih için dönüştürme gerekebilir.
		                                     .endDate(dto.endDate())     // Aynı şekilde burada da.
		                                     .holidayType(EHolidayType.valueOf(dto.holidayType())) // Enum'u string olarak aldığımız için dönüştürüyoruz.
		                                     .description(dto.description())
		                                     .holidayState(EHolidayState.PENDING) // İlk kayıt için varsayılan durumu belirtebilirsiniz.
		                                     .build();
		
		
		
		workHolidayRepository.save(workHoliday);
		
		// Entity'den DTO'ya Dönüştürme ve Döndürme
		return true;
	
	}
}