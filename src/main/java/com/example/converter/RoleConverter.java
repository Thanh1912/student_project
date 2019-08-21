package com.example.converter;

import com.example.entity.RoleEntity;
import com.example.dto.RoleDTO;
import org.modelmapper.ModelMapper;

public class RoleConverter {

		private ModelMapper modelMapper;

		public RoleConverter() {
			modelMapper = new ModelMapper();
		}

		public RoleDTO convertToDto(RoleEntity entity) {
			RoleDTO result = modelMapper.map(entity, RoleDTO.class);
			return result;
	}

	public RoleEntity convertToEntity(RoleDTO dto) {
		RoleEntity result = modelMapper.map(dto, RoleEntity.class);
		return result;
	}
}
