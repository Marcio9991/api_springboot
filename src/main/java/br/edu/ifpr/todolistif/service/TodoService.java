package br.edu.ifpr.todolistif.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.ifpr.todolistif.dto.TodoConclusaoRequestDTO;
import br.edu.ifpr.todolistif.dto.TodoRequestDTO;
import br.edu.ifpr.todolistif.dto.TodoResponseDTO;
import br.edu.ifpr.todolistif.model.Todo;
import br.edu.ifpr.todolistif.repository.TodoRepository;

@Service
public class TodoService {
    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }

    public List<TodoResponseDTO> listar() {
        return converter(repository.findAll());
    }

    public List<TodoResponseDTO> listarConcluidas() {
        return converter(repository.findByFinishedAtIsNotNull());
    }

    public List<TodoResponseDTO> listarPendentes() {
        return converter(repository.findByFinishedAtIsNull());
    }

    public TodoResponseDTO criar(TodoRequestDTO dados) {
        Todo tarefa = new Todo();
        tarefa.setTitle(dados.title());
        tarefa.setDeadLine(dados.deadLine());

        Todo salva = repository.save(tarefa);
        return new TodoResponseDTO(salva);
    }

    public void excluir(Long id) {
        repository.deleteById(id);
    }

    public TodoResponseDTO concluir(Long id, TodoConclusaoRequestDTO dados) {
        Todo tarefa = repository.findById(id).orElseThrow();
        tarefa.setFinishedAt(dados.finishedAt());

        Todo atualizada = repository.save(tarefa);
        return new TodoResponseDTO(atualizada);
    }

    private List<TodoResponseDTO> converter(List<Todo> tarefas) {
        List<TodoResponseDTO> resposta = new ArrayList<>();

        for (Todo tarefa : tarefas) {
            resposta.add(new TodoResponseDTO(tarefa));
        }
        return resposta;
    }
}
