package com.leyou.item.web;

import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import com.leyou.item.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("spec")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;


    /**
     * 根据分类ID查询规格组
     * @param cid
     * @return
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupByCid(@PathVariable("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryGroupByCid(cid));
    }

    /**
     *
     * @param gid
     * @param cid 分类id
     * @param searching 是否搜索
     * @return
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>> queryParamList(
            @RequestParam(value = "gid",required = false)Long gid,
            @RequestParam(value = "cid",required = false)Long cid,
            @RequestParam(value = "searching",required = false)Boolean searching
    ){
        return ResponseEntity.ok(specificationService.queryParamList(gid,cid,searching));
    }

    @PostMapping("group")
    public ResponseEntity<Void> saveGroup(@RequestBody SpecGroup specGroup){
            specificationService.saveGroupByCid(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("group")
    public ResponseEntity<Void> amendGroup(@RequestBody SpecGroup specGroup){
        specificationService.amendGroupByCid(specGroup);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("group")
    public ResponseEntity<Void> deleteGroup(@RequestParam("id")Long id){
        specificationService.deleteGroup(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PostMapping("param")
    public ResponseEntity<Void> saveParam(@RequestBody SpecParam specParam){
        specificationService.saveSpecParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("param")
    public ResponseEntity<Void> updateParam(@RequestBody SpecParam specParam){
        specificationService.updateParam(specParam);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @DeleteMapping("param")
    public ResponseEntity<Void> deleteParam(@RequestParam("id") Long id){
        specificationService.deleteParam(id);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("group")
    public ResponseEntity<List<SpecGroup>> queryListByCid(@RequestParam("cid")Long cid){
        return ResponseEntity.ok(specificationService.queryByCid(cid));
    }
    @GetMapping("{cid}")
    public ResponseEntity<List<SpecGroup>> querySpecsByCid(@PathVariable("cid") Long cid){
        List<SpecGroup> list = this.specificationService.querySpecsByCid(cid);
        if(list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);
    }
    @GetMapping("/paramsc")
    public ResponseEntity<List<SpecParam>> querySpecSpecParam(
            @RequestParam(value = "gid",required = false) Long gid,
            @RequestParam(value="cid", required = false) Long cid,
            @RequestParam(value="searching", required = false) Boolean searching,
            @RequestParam(value="generic", required = false) Boolean generic
    ){
        List<SpecParam> list = this.specificationService.querySpecParams(gid,cid,searching,generic);
        if (list == null || list.size() == 0){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(list);

    }



}



















