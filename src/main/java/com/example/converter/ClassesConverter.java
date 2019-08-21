package com.example.converter;


import com.example.dto.ClassesDTO;
import com.example.entity.ClassesEntity;
import org.modelmapper.ModelMapper;

public class ClassesConverter {

	private ModelMapper modelMapper;

	public ClassesConverter() {
		modelMapper = new ModelMapper();
	}

	public ClassesDTO convertToDto(ClassesEntity entity) {
		ClassesDTO result = modelMapper.map(entity, ClassesDTO.class);
		return result;
	}

	public ClassesEntity convertToEntity(ClassesDTO dto) {
		ClassesEntity result = modelMapper.map(dto, ClassesEntity.class);
		return result;
	}
}
