package com.leyou.item.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.item.mapper.SercGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificationService {

    @Autowired
    private SercGroupMapper groupMapper;
    @Autowired
    private SpecParamMapper paramMapper;



    public List<SpecGroup> queryGroupByCid(Long cid) {
        //查询条件
        SpecGroup group = new SpecGroup();
        group.setCid(cid);
        //查询
        List<SpecGroup> list = groupMapper.select(group);
        if (CollectionUtils.isEmpty(list)){
            //没查到
            throw new LyException(ExceptionEnum.SPEC_GROUP_NOT_FOND);
        }
        return list;
    }


    public List<SpecParam> queryParamByGid(Long gid,Long cid,Boolean searching) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setGroupId(cid);
        specParam.setSearching(searching);
        List<SpecParam> list = paramMapper.select(specParam);
        if (CollectionUtils.isEmpty(list)){
            //没查到
            throw new LyException(ExceptionEnum.SPEC_PARAM_NOT_FOND);
        }
        return list;
    }

    public void saveGroupByCid(SpecGroup specGroup) {
        specGroup.setId(null);
        int count = groupMapper.insert(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_GROUP_ERROR);
        }
    }

    public void amendGroupByCid(SpecGroup specGroup) {
        int count = groupMapper.updateByPrimaryKey(specGroup);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_PARAM_ERROR);
        }
    }
    public void deleteGroup(Long id) {

        int count = groupMapper.deleteByPrimaryKey(id);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_PARAM_ERROR);
        }
    }

    public void saveSpecParam(SpecParam specParam) {
        int count = paramMapper.insert(specParam);
        if (count != 1) {
            throw new LyException(ExceptionEnum.INSERT_PARAM_ERROR);
        }
    }


    public void updateParam(SpecParam specParam) {
        paramMapper.updateByPrimaryKey(specParam);
    }
    public void deleteParam(Long id) {
        paramMapper.deleteByPrimaryKey(id);
    }

    public List<SpecParam> queryParamList(Long gid, Long cid, Boolean searching) {

        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        List<SpecParam> list = paramMapper.select(param);
        return list;
    }

    public List<SpecGroup> queryByCid(Long cid) {
        //1.查询规格组
        List<SpecGroup> specGroups = queryGroupByCid(cid);
        //2.查询当下分类的参数
        List<SpecParam> specParams = queryParamList(null, cid, null);
        //3.先把规格参数变成map,map的key是规格组id,map的值是组下所有的参数
        Map<Long,List<SpecParam>> map = new HashMap<>();
        for (SpecParam param : specParams) {
            if (!map.containsKey(param.getGroupId())){
                //这个组id在map中不存在，新整一个list
                map.put(param.getGroupId(),new ArrayList<>());
            }
            map.get(param.getGroupId()).add(param);
        }
        return specGroups;
    }

    public List<SpecGroup> querySpecsByCid(Long cid) {
        // 查询规格组
       //List<SpecGroup> groups = this.querySpecGroups(cid);
        List<SpecGroup> groups = this.querySpecsByCid(cid);
        groups.forEach(g -> {
            // 查询组内参数
            g.setParams(this.queryParamList(g.getId(), null, null));
        });
        return groups;
    }
    public List<SpecParam> querySpecParams(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam param = new SpecParam();
        param.setGroupId(gid);
        param.setCid(cid);
        param.setSearching(searching);
        param.setGeneric(generic);
        return this.paramMapper.select(param);
      //  return this.specParamMapper.select(param);
    }
}
