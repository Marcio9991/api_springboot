package br.edu.ifpr.todolistif.dto;

import java.time.LocalDateTime;

public record TodoRequestDTO(String title, LocalDateTime deadLine) {

}
