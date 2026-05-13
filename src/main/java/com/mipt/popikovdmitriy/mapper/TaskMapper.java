package com.mipt.popikovdmitriy.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.mipt.popikovdmitriy.dto.TaskCreateDto;
import com.mipt.popikovdmitriy.dto.TaskResponseDto;
import com.mipt.popikovdmitriy.dto.TaskUpdateDto;
import com.mipt.popikovdmitriy.model.Task;

@Mapper(componentModel = "spring")
public interface TaskMapper {

  Task toEntity(TaskCreateDto createDto);

  TaskResponseDto toResponseDto(Task task);

  void updateEntity(TaskUpdateDto updateDto, @MappingTarget Task task);
}
