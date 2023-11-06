package com.sprint4_activity.crm.controller;


import com.sprint4_activity.crm.entity.Client;
import com.sprint4_activity.crm.repository.ClientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@AllArgsConstructor
@Controller
public class ClientController {

    private ClientRepository repository;

    @GetMapping("/Home")
    public String home() {
        return "Home";
    }

    @GetMapping("/CreateClientView")
    public String createClient() {
        return "CreateClientView";
    }

    @PostMapping("/CreateClientView")
    public String create(Client client) {

            repository.save(client);
            System.out.println(client.getId() + "1");
            return "redirect:/ClientList";
    }

    @GetMapping("/ClientList")
    public ModelAndView getClients() {
        ModelAndView mv = new ModelAndView("ClientList");
        mv.addObject("clients", repository.findAll());
        return mv;
    }

    @GetMapping("/editClient/{id}")
    public ModelAndView editClients(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("SaveClient");
        System.out.println(id);
        Client clientfound = repository.findById(id).orElse(null);
        mv.addObject("client", clientfound);
        return mv;
    }

    @GetMapping("/removeClient/{id}")
    public ModelAndView removeClients(@PathVariable("id") long id){
        ModelAndView mv = new ModelAndView("ClientList");
        repository.deleteById(id);
        mv.addObject("clients", repository.findAll());
        return mv;
    }

    @PostMapping("/SaveClient")
    public String updateClient(Client client) {
        if(repository.existsById(client.getId())){
            System.out.println(client.getId() + "3");
            Client clientfound = repository.findById(client.getId()).orElse(null);
            clientfound.setName(client.getName());
            clientfound.setLocal(client.getLocal());
            repository.save(client);
            return "redirect:/ClientList";
        }
        return "redirect:/ClientList";
    }
}
