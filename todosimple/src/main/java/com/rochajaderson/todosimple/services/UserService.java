package com.rochajaderson.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rochajaderson.todosimple.models.User;
import com.rochajaderson.todosimple.repositories.TaskRepository;
import com.rochajaderson.todosimple.repositories.UserRepository;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(Long id){
        Optional<User> user = this.userRepository.findById(id);
        return  user.orElseThrow(() -> new RuntimeException(
            "Usuario não encontrado! Id: " + id + ",Tipo: " + User.class.getName()
        ));

    }

    @Transactional
    public User create(User obj){
        obj.setId(null);
        obj = this.userRepository.save(obj);
        this.taskRepository.saveAll(obj.getTasks());
        return obj;

    }

    @Transactional
    public User update(User obj){
        User newobj = findById(obj.getId());
        newobj.setPassword(obj.getPassword());
        return this.userRepository.save(newobj);
    }

    public void delete(Long id){
        findById(id);
        try {
            this.userRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não é possivel excluir pois ha entidades relacionadas!");
        }
    }






    
}
