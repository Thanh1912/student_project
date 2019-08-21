package com.example.converter;


import com.example.dto.CourseDTO;
import com.example.entity.CourseEntity;
import org.modelmapper.ModelMapper;

public class CourseConverter {

	private ModelMapper modelMapper;

	public CourseConverter() {
		modelMapper = new ModelMapper();
	}

	public CourseDTO convertToDto(CourseEntity entity) {
		CourseDTO result = modelMapper.map(entity, CourseDTO.class);
		return result;
	}

	public CourseEntity convertToEntity(CourseDTO dto) {
		CourseEntity result = modelMapper.map(dto, CourseEntity.class);
		return result;
	}
}
