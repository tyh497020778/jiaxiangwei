package com.shuanghui.jiaxiangwei.workflow.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.util.List;

@Controller
@RequestMapping("models")
public class ModelController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 获取所有模型
     * @return
     */
    @RequestMapping("modelist")
    public String modelList(org.springframework.ui.Model model){
        List<Model> models = repositoryService.createModelQuery().orderByCreateTime().desc().list();
        model.addAttribute("models",models);
        return "model/list";
    }

    /**
     *  新建模型
     * @return
     * @throws Exception
     */
    @GetMapping(value = "/newModel")
    public void createFlow(HttpServletRequest request, HttpServletResponse response) throws Exception{

        //初始化一个空模型
        Model model = repositoryService.newModel();
        //设置一些默认信息
        String name = "new-process";
        String description = "";
        int revision = 1;
        String key = "process";

        ObjectNode modelNode = objectMapper.createObjectNode();
        modelNode.put(ModelDataJsonConstants.MODEL_NAME,name);
        modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
        modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

        model.setName(name);
        model.setKey(key);
        model.setMetaInfo(modelNode.toString());

        repositoryService.saveModel(model);
        String id = model.getId();

        //完善ModelEditorSource
        ObjectNode editorNode = objectMapper.createObjectNode();
        editorNode.put("id", "canvas");
        editorNode.put("resourceId", "canvas");
        ObjectNode stencilSetNode = objectMapper.createObjectNode();
        stencilSetNode.put("namespace",
                "http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset", stencilSetNode);

        repositoryService.addModelEditorSource(id,editorNode.toString().getBytes("utf-8"));
        response.sendRedirect(request.getContextPath()+"/static/modeler.html?modelId="+id);
    }

    /**
     * 删除未发布的流程
     * @param id
     */
    @ResponseBody
    @GetMapping(value = "/flowDelete")
    public void flowDelete(@RequestParam(name = "id") String id){
        System.out.println("id = "+id);
        repositoryService.deleteModel(id);
    }

    /**
     * 删除已发布的流程
     * @param id
     */
    @ResponseBody
    @GetMapping(value = "/deleteDeploy")
    public void deleteDeploy(@RequestParam(name = "id") String id){
        repositoryService.deleteDeployment(id);
    }

    /**
     * 发布流程
     * @param id
     * @return
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/deploy")
    public Object deploy(@RequestParam(name = "id") String id) throws Exception{
        //获取模型
        Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
        if (null == bytes){
            return "模型数据为空，请先设计流程并成功保存，再进行发布。";
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0){
            return "数据模型不符合要求，请至少设计一条主线程流。";
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);

        //发布流程
        String processName = modelData.getName() + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelData.getName())
                .addString(processName, new String(bpmnBytes, "UTF-8"))
                .deploy();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);
        return "success";
    }

    /**
     * 工作流导出
     * @param request
     * @param response
     * @throws Exception
     */
    @ResponseBody
    @GetMapping(value = "/export")
    public void exportActiviti(HttpServletRequest request,HttpServletResponse response)throws Exception{
        String id = request.getParameter("id");
        BufferedOutputStream bos = null;
        try {
            //获取模型
            Model modelData = repositoryService.getModel(id);
            byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());
            JsonNode modelNode = new ObjectMapper().readTree(bytes);
            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);
                // 封装输出流
                bos = new BufferedOutputStream(response.getOutputStream());
                bos.write(bpmnBytes);// 写入流
                String filename = modelData.getName() + ".bpmn20.xml";
                response.setContentType("application/x-msdownload;");
                response.setHeader("Content-Disposition",
                        "attachment; filename=" + filename);
                response.flushBuffer();
        } catch (Exception e) {
            System.out.println("流程导出失败");
            e.printStackTrace();
        }finally {
            bos.flush();
            bos.close();
        }
    }



}
