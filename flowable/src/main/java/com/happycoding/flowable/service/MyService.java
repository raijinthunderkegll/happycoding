package com.happycoding.flowable.service;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Transactional
    public String startProcess() {
        // 流程变量
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("groups","approve");
        variables.put("assignee_0","david");
        ProcessInstance oneTaskProcess = runtimeService.startProcessInstanceByKey("oneTaskProcess", variables);
        return oneTaskProcess.getId();
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

}
