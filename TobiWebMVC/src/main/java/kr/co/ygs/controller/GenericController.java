package kr.co.ygs.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

public abstract class GenericController<T, P, S> {

    S service;

    @RequestMapping("/add")
    public void add(T entity){
    }

    @RequestMapping("/update")
    public void update(T entity){
    }

    @RequestMapping("/view")
    public T view(P id){
        return null;
    }

    @RequestMapping("/delete")
    public void delete(P id){
    }

    @RequestMapping("/list")
    public List<T> list(){
        return null;
    }


}
