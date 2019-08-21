package com.example.converter;


import com.example.dto.ClassesDTO;
import com.example.dto.UserDTO;
import com.example.entity.ClassesEntity;
import com.example.entity.UserEntity;
import org.modelmapper.ModelMapper;

public class UserConverter {

	private ModelMapper modelMapper;

	public UserConverter() {
		modelMapper = new ModelMapper();
	}

	public UserDTO convertToDto(UserEntity entity) {
		UserDTO result = modelMapper.map(entity, UserDTO.class);
		return result;
	}

	public UserEntity convertToEntity(UserDTO dto) {
		UserEntity result = modelMapper.map(dto, UserEntity.class);
		return result;
	}
}
